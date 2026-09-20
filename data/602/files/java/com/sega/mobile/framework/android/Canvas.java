package com.sega.mobile.framework.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.opengl.GLGraphics;
import com.sega.mobile.framework.opengl.SpriteBatch;
import GameEngine.time.FrameClock;
import SonicGBA.GameObject;
import SonicGBA.StageManager;
import State.State;
import java.util.Arrays;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/** Android surface, input queue and GLES lifecycle. Simulation is owned by the GL thread. */
public class Canvas extends GLSurfaceView implements GLSurfaceView.Renderer {
    protected Graphics mGraphics;
    private final SpriteBatch batch = new SpriteBatch();
    private final FrameClock clock = new FrameClock();
    private boolean useMultiTouch;
    private volatile boolean focused;
    private volatile boolean resumed;
    private volatile boolean disposed;
    private volatile boolean surfaceReady;
    private int[] pointers = new int[16];
    public static int screenWidth, screenHeight;

    public Canvas(Context context) {
        super(context);
        Arrays.fill(pointers, -1);
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 0, 0);
        setPreserveEGLContextOnPause(true);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override public void onSurfaceCreated(GL10 ignored, EGLConfig config) {
        surfaceReady = false;
        if (disposed || !MFDevice.isCurrentCanvas(this)) return;
        if (mGraphics instanceof GLGraphics) ((GLGraphics) mGraphics).release();
        batch.create();
        mGraphics = new GLGraphics(batch);
        clock.reset();
    }

    @Override public void onSurfaceChanged(GL10 ignored, int width, int height) {
        if (disposed || mGraphics == null || !MFDevice.isCurrentCanvas(this)) return;
        screenWidth = width; screenHeight = height;
        batch.resize(width, height);
        MFDevice.bindSurface(this, (GLGraphics) mGraphics, width, height);
        surfaceReady = true;
        clock.reset();
    }

    @Override public void onDrawFrame(GL10 ignored) {
        if (disposed || !surfaceReady || !MFDevice.isCurrentCanvas(this)) return;
        double seconds = clock.advance(System.nanoTime());
        if (!focused || !resumed || MFMain.browser) {
            clock.reset();
            seconds = 0.0;
        }
        batch.begin();
        try {
            MFDevice.renderFrame(seconds);
        } finally {
            batch.end();
        }
    }

    public Graphics getGraphics() { return mGraphics; }
    public SpriteBatch getRendererStats() { return batch; }
    public boolean initialized() { return focused && resumed && surfaceReady && !disposed; }
    public void setFilterBitmap(final boolean enabled) {
        queueEvent(new Runnable() { public void run() { if (mGraphics != null) mGraphics.setFilterBitmap(enabled); } });
    }
    public void setAntiAlias(final boolean enabled) {
        queueEvent(new Runnable() { public void run() { if (mGraphics != null) mGraphics.setAntiAlias(enabled); } });
    }
    public void setUseMultitouch(boolean enabled) { useMultiTouch = enabled; }

    public void pauseGame() {
        resumed = false;
        hideNotify();
        super.onPause();
    }
    public void resumeGame() {
        resumed = true;
        super.onResume();
        if (focused) showNotify();
    }
    public void dispose() {
        disposed = true;
        surfaceReady = false;
        queueEvent(new Runnable() {
            public void run() {
                clock.reset();
                resetPointers();
                if (mGraphics instanceof GLGraphics) ((GLGraphics) mGraphics).release();
                // EGL may already have been lost. GLSurfaceView destroys its remaining GL resources.
            }
        });
    }
    @Override public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        focused = hasFocus;
        if (hasFocus && resumed) showNotify(); else hideNotify();
    }
    public void hideNotify() {
        queueEvent(new Runnable() {
            public void run() {
                clock.reset(); resetPointers();
                if (MFDevice.isCurrentCanvas(Canvas.this)) MFDevice.notifyPause();
            }
        });
    }
    public void showNotify() {
        queueEvent(new Runnable() {
            public void run() {
                clock.reset(); resetPointers();
                if (MFDevice.isCurrentCanvas(Canvas.this)) MFDevice.notifyResume();
            }
        });
    }

    @Override public boolean onTouchEvent(MotionEvent event) { return touchEvent(event); }
    public boolean touchEvent(MotionEvent event) {
        final MotionEvent copy = MotionEvent.obtain(event);
        queueEvent(new Runnable() {
            public void run() {
                try {
                    if (initialized() && MFDevice.isCurrentCanvas(Canvas.this)) processTouch(copy);
                } finally { copy.recycle(); }
            }
        });
        return true;
    }
    private void ensurePointer(int id) {
        if (id < pointers.length) return;
        int old = pointers.length;
        pointers = Arrays.copyOf(pointers, Math.max(old * 2, id + 1));
        Arrays.fill(pointers, old, pointers.length, -1);
    }
    private void resetPointers() {
        for (int id : pointers) if (id >= 0) pointerReleased(id, 0, 0);
        Arrays.fill(pointers, -1);
    }
    private void processTouch(MotionEvent event) {
        int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_CANCEL) { resetPointers(); return; }
        if (action == MotionEvent.ACTION_DOWN) resetPointers();
        int index = event.getActionIndex();
        if (index < 0 || index >= event.getPointerCount()) return;
        int id = useMultiTouch ? event.getPointerId(index) : 0;
        ensurePointer(id);
        int x = (int) event.getX(index), y = (int) event.getY(index);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if (!useMultiTouch && action != MotionEvent.ACTION_DOWN) break;
                pointers[id] = id;
                pointerPressed(id, x, y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (!useMultiTouch && action != MotionEvent.ACTION_UP) break;
                if (pointers[id] >= 0) pointerReleased(id, x, y);
                pointers[id] = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                int count = useMultiTouch ? event.getPointerCount() : 1;
                for (int i = 0; i < count; i++) {
                    id = useMultiTouch ? event.getPointerId(i) : 0;
                    ensurePointer(id);
                    // A missing DOWN (focus loss/cancel) is not another finger's identity.
                    if (pointers[id] >= 0) pointerDragged(id, (int) event.getX(i), (int) event.getY(i));
                }
                break;
        }
        if (action == MotionEvent.ACTION_DOWN) {
            if (StageManager.loadStep == 0) State.initTouchkeyBoard();
            if (x > screenWidth * 0.5f && y < screenHeight * 0.5f
                    && !MFMain.multiplayer && GameObject.player != null && GameObject.player2 != null) {
                if (++MFMain.tapCount >= 3) { MFMain.switchPlayerFocus(); MFMain.tapCount = 0; }
            }
        }
    }

    public boolean keyDown(final int keyCode, KeyEvent event) {
        queueEvent(new Runnable() { public void run() { keyPressed(keyCode); } });
        return true;
    }
    public boolean keyUp(final int keyCode, KeyEvent event) {
        queueEvent(new Runnable() { public void run() { keyReleased(keyCode); } });
        return true;
    }
    @Override public boolean onTrackballEvent(MotionEvent event) {
        if (!MFDevice.enableTrackBall) return true;
        final int action = event.getActionMasked();
        final int code = action == MotionEvent.ACTION_MOVE
                ? (Math.abs(event.getX()) > Math.abs(event.getY())
                    ? (event.getX() > 0 ? 22 : 21) : (event.getY() > 0 ? 20 : 19)) : 23;
        queueEvent(new Runnable() {
            public void run() {
                if (action == MotionEvent.ACTION_DOWN) keyPressed(code);
                else if (action == MotionEvent.ACTION_UP) keyReleased(code);
                else if (action == MotionEvent.ACTION_MOVE) trackballMoved(code);
            }
        });
        return true;
    }
    public void keyPressed(int keyCode) { }
    public void keyReleased(int keyCode) { }
    public void trackballMoved(int keyCode) { }
    public void pointerPressed(int id, int x, int y) { }
    public void pointerReleased(int id, int x, int y) { }
    public void pointerDragged(int id, int x, int y) { }
    public void paint(Graphics graphics) { }
    public void repaint() { requestRender(); }
    public void serviceRepaints() { }
    public void setFullScreenMode(boolean enabled) { }
}
