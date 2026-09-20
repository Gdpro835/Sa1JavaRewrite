package com.sega.mobile.framework.device;

import SonicGBA.StageManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import com.sega.mobile.define.MDPhone;
import com.sega.mobile.framework.MFGameState;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.framework.android.Canvas;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.android.Image;
import com.sega.mobile.framework.ui.MFTouchKey;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import MFLib.MainState;
import SonicGBA.GameObject;
import SonicGBA.GlobalResource;
import SonicGBA.StageManager;
import State.GameState;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.sega.mobile.framework.utility.MFScreen;
import android.view.View;

public final class MFDevice {
    public static final int APN_CMNET = 2;
    public static final int APN_CMWAP = 1;
    public static final int APN_NONE = 0;
    private static final int MAX_LAYER = 1;
    private static final byte[] NULL_RECORD = new byte[5];
    private static final int PER_VIBRATION_TIME = 500;
    private static final String RECORD_NAME = "rms";
    public static final int SIM_CMCC = 1;
    public static final int SIM_NONE = 0;
    public static final int SIM_TELECOM = 3;
    public static final int SIM_UNICOM = 2;
    private static final String VERSION_INFO = "104_RELEASE";
    private static int apnType = -1;
    static int bufferHeight;
    /* access modifiers changed from: private */
    public static Image bufferImage;
    static int bufferWidth;
    static boolean clearBuffer = true;
    /* access modifiers changed from: private */
    public static Vector<MFComponent> componentVector;
    /* access modifiers changed from: private */
    public static MFGameState currentState;
    /* access modifiers changed from: private */
    public static long currentSystemTime;
    static int deviceHeight;
    private static int deviceKeyValue = 0;
    static int deviceWidth;
    static Rect drawRect;
    private static boolean enableCustomBack = false;
    public static boolean enableTrackBall = true;
    private static boolean enableVolumeKey = true;
    /* access modifiers changed from: private */
    public static volatile boolean exitFlag;
    static MFGraphics fontGraphics;
    /* access modifiers changed from: private */
    public static Image fontImage;
    /* access modifiers changed from: private */
    public static MFGraphics graphics;
    static int horizontalOffset;
    /* access modifiers changed from: private */
    public static boolean inSuspendFlag;
    /* access modifiers changed from: private */
    public static boolean inVibrationFlag;
    private static MFTouchKey interruptConfirm;
    /* access modifiers changed from: private */
    public static boolean interruptPauseFlag;
    /* access modifiers changed from: private */
    public static long lastSystemTime;
    private static boolean logicTrace;
    protected static MyGameCanvas mainCanvas = new MyGameCanvas(MFMain.getInstance());
    public static volatile boolean tickFlag = true;
    public static boolean newth = false;
    public static int ii = 0;
    public static int ii2 = 0;
    public static boolean started = false;
    public static long ls = 0;
    public static double delta = 0;
    public static double delta2 = 0;
    public static ScheduledExecutorService executor;
    public static volatile boolean isPaused = false;
    //public static final Object inputLock = new Object();
    /*public static boolean showFPS = false;
    public static int lastFPS = 0;
    public static volatile String FPSString = "FPS: ";
    public static volatile int fw = 0;
    public static volatile int fh = 0;
    public static volatile boolean render;*/

    protected static Runnable mainRunnable = new Runnable() {
        public final void run() {
            MFDevice.interruptPauseFlag = false;
            MFDevice.responseInterrupt = true;
            MFDevice.exitFlag = false;
            MFDevice.lastSystemTime = System.currentTimeMillis();
            MFDevice.initRecords();
            MFGraphics.init();
            MFSound.init();
            MFSensor.init();
            MFGamePad.resetKeys();
            MFDevice.vibrator = (Vibrator) MFMain.getInstance().getSystemService("vibrator");
            MFDevice.componentVector = new Vector();
            MFDevice.vibraionFlag = true;
            MFDevice.inVibrationFlag = false;
            
            /*double amountOfTicks = MainState.getms();
            double ns = 1000000000 / amountOfTicks;
            long timer = System.currentTimeMillis();
            int frames = 0;
            while (!MFDevice.exitFlag) {
            render = false;
            MFDevice.currentSystemTime = System.nanoTime();
            MFDevice.delta += (MFDevice.currentSystemTime - MFDevice.lastSystemTime) / ns;
            MFDevice.lastSystemTime = MFDevice.currentSystemTime;
            while (isPaused) {
            }
            if (StageManager.loadStep == 0 || GameState.isLoadingSkipped == false) {
                long timePassed = MFDevice.currentSystemTime - MFDevice.lastSystemTime;
                MFDevice.delta -= timePassed / ns;
            }
            if (currentState == null || currentSystemTime - lastSystemTime <= 0L) {
                continue;
            }
            if (delta >= 1) render = true;
            while (delta >= 1) {
            tick();
            MFDevice.delta--;
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
            timer += 1000;
            lastFPS = frames;
            frames = 0;
            }
            }*/
            /*while (!MFDevice.exitFlag) {
                if (MFDevice.tickFlag) {
                    tick();
                    MFDevice.tickFlag = false;
                }
                if (!MFDevice.newth) {
                    new Thread(new Runnable() {
                        public void run() {
                            while (!MFDevice.exitFlag) {
                                int i = 0;
                                if (!(StageManager.loadStep == 0 || GameState.isLoadingSkipped == false)) {
                                    i = MainState.getms() - 16;
                                }
                                try {
                                    Thread.sleep((long) (MainState.getms() - i));
                                    MFDevice.tickFlag = true;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                    MFDevice.exitFlag = true;
                                }
                            }
                        }
                    }).start();
                    MFDevice.newth = true;
                }
            }*/
            //long timer = System.currentTimeMillis();
            //int frames = 0;
        for(; !exitFlag; lastSystemTime = System.currentTimeMillis()) {
            while (isPaused) {
            try {
                Thread.sleep(5);
            } catch (Exception e) {
            }
            }
            this.tick();
            /*frames++;
            if (System.currentTimeMillis() - timer > 1000) {
            timer += 1000;
            lastFPS = frames;
            frames = 0;
            }
            render = true;*/
            long var1;
            long var3;
            MFGameState var7;
            do {
                if (StageManager.loadStep == 0 || GameState.isLoadingSkipped == false && GlobalResource.loadingTipsConfig == 0) {
                    currentSystemTime = System.currentTimeMillis();
                }
                if (currentState == null || currentSystemTime - lastSystemTime <= 0L) {
                    break;
                }
                var1 = currentSystemTime;
                var3 = lastSystemTime;
                var7 = currentState;
            } while(var1 - var3 < (long)var7.getFrameTime());
        }
        /*for(int i = 0; !exitFlag; lastSystemTime = System.nanoTime()) {
            if (started && (StageManager.loadStep == 0 || GameState.isLoadingSkipped == false)) {
               currentSystemTime = System.nanoTime();
            }
            if (!started || currentState == null || currentSystemTime - lastSystemTime <= 0L) {
            this.tick();
            ls = lastSystemTime;
            started = true;
            continue;
            }
            long delta = currentSystemTime - ls;
            if (delta >= currentState.getFrameTime() * 1_000_000L) {
            this.tick();
            ls = lastSystemTime;
            } else {
            try {
            long sleepTime = currentState.getFrameTime() * 1_000_000L - delta;
            if (sleepTime > 0) Thread.sleep(sleepTime / 1_000_000L, (int) (sleepTime % 1_000_000L));
            } catch (InterruptedException e) {
            }
            }
        }*/
        
/*while (!exitFlag) {
    lastSystemTime = System.nanoTime();
    this.tick();
    long delta;
    long frameIntervalms = MFLib.MainState.getms();
    do {
    if (StageManager.loadStep == 0 || GameState.isLoadingSkipped == false) {
       currentSystemTime = System.nanoTime();
    }
    if (currentState == null || currentSystemTime - lastSystemTime <= 0L) {
        break;
    }
    delta = (currentSystemTime - lastSystemTime) / 1_000_000L;
    long sleepTime = frameIntervalms - delta;
    if (sleepTime < 0) break;
    try {
        Thread.sleep(sleepTime);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        break;
    }
    } while(delta < frameIntervalms);
}*/
        /*lastSystemTime = System.currentTimeMillis();//System.nanoTime();
        executor = Executors.newSingleThreadScheduledExecutor();
while (!exitFlag) {

    if (MFDevice.inSuspendFlag || !mainCanvas.getHolder().getSurface().isValid()) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException ignored) {
        }
        continue;
    }

if (MainState.fps60) {
        fps();
        if (StageManager.loadStep != 0 && GameState.isLoadingSkipped != false) {
        this.tick();
        }
        delta2 += delta;
        double interval = 1.0 / MFLib.MainState.getfps();
        if (delta2 >= interval) {
        this.tick();
        delta2 -= interval;
        }
        } else {
        if (tickFlag) {
        this.tick();
        tickFlag = false;
        }
    boolean z = !MainState.fps60 && (GameState.state == 4 || 
    (GameState.state == 5 && GameState.isLoadingSkipped != false) || 
    (GameState.state != 5 && (StageManager.characterFromGame == -1 || 
    StageManager.stageIDFromGame == -1)));
    ii = z ? 16 : MainState.getms();
    if (started && ii != ii2) {
       executor.shutdown();
       executor = Executors.newSingleThreadScheduledExecutor();
       started = false;
       newth = false;
    }
    ii2 = ii;
if (newth) continue;
executor.scheduleAtFixedRate(new Runnable() {
    @Override
    public void run() {
                tickFlag = true;
                if (exitFlag) newth = false;
    }
}, 0, ii, TimeUnit.MILLISECONDS);
if (!exitFlag) newth = true;
started = true;
}*/
    /*long frameTimeNanos = (long) (1_000_000_000L / MainState.getms());
    long sleepTimeNanos = frameTimeNanos - elapsedNanos;

    if (sleepTimeNanos > 0L) {
        long sleepMillis = sleepTimeNanos / 1_000_000L;
        int sleepNanos = (int) (sleepTimeNanos % 1_000_000L);

        try {
            Thread.sleep(sleepMillis, sleepNanos);
        } catch (InterruptedException e) {
            e.printStackTrace();
            exitFlag = true;
        }

        while (System.nanoTime() - lastNanoTime < frameTimeNanos) {
            Thread.yield();
        }
    }

    lastNanoTime = System.nanoTime();
*/
    /*if ((StageManager.loadStep != 0 || GameState.isLoadingSkipped != false &&
         StageManager.loadStep == 0) && (StageManager.characterFromGame == -1 || 
         StageManager.stageIDFromGame == -1)) {
        MainState.tipfps60 = true;
    } else {
        MainState.tipfps60 = false;
    }*/
//}
        /*while (!exitFlag) {
    long lastSystemTime = System.currentTimeMillis();

    this.tick(); // lógica do jogo

    if (currentState == null) {
        continue;
    }

    long frameTime = currentState.getFrameTime();
    long elapsed = System.currentTimeMillis() - lastSystemTime;

    if (elapsed < frameTime) {
        try {
            Thread.sleep(frameTime - elapsed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}*/
        //executor.shutdown();
        MFDevice.currentState.onExit();
        MFMain.getInstance().notifyDestroyed();
    }
    
    /*public void fps() {
    currentSystemTime = System.currentTimeMillis();//System.nanoTime();
    long elapsed = currentSystemTime - lastSystemTime;
    lastSystemTime = currentSystemTime;
    delta = Math.min(0.05, elapsed / 1000.0);
    }*/

        private void tick() {
            //synchronized (MFDevice.inputLock) {
            if (MFMain.browser) return;
            if (MFDevice.mainCanvas.initialized()) {
                synchronized (MFDevice.mainRunnable) {
                    MFGamePad.keyTick();
                    for (int i = 0; i < MFDevice.componentVector.size(); i++) {
                        ((MFComponent) MFDevice.componentVector.elementAt(i)).tick();
                    }
                }
                MFSound.tick();
                if (MFDevice.vibraionFlag) {
                    if (MFDevice.vibrateTime > 0 && System.currentTimeMillis() - MFDevice.vibrateStartTime > ((long) MFDevice.vibrateTime)) {
                        MFDevice.vibrateTime = 0;
                        MFDevice.inVibrationFlag = false;
                    }
                    if (MFDevice.inVibrationFlag) {
                        MFDevice.vibrationImpl(500);
                    }
                }
                if (MFDevice.nextState != null) {
                    if (MFDevice.currentState != null) {
                        MFDevice.currentState.onExit();
                    }
                    MFDevice.currentState = MFDevice.nextState;
                    MFDevice.currentState.onEnter();
                    MFDevice.nextState = null;
                }
                MFDevice.graphics.reset();
                if (MFDevice.clearBuffer) {
                    MFDevice.clearScreen();
                }
                if (MFDevice.interruptPauseFlag) {
                    if (!MFDevice.inSuspendFlag && MFMain.getInstance().logicDeviceSuspend()) {
                        MFDevice.notifyResume();
                    }
                    MFMain.getInstance().drawDeviceSuspend(MFDevice.graphics);
                } else {
                    MFDevice.currentState.onTick();
                    if (!MFDevice.exitFlag) {
                        for (int i2 = 1; i2 > 0; i2--) {
                            if (MFDevice.preLayerGraphics[i2 - 1] != null) {
                                MFDevice.currentState.onRender(MFDevice.preLayerGraphics[i2 - 1], -i2);
                            }
                        }
                        MFDevice.currentState.onRender(MFDevice.graphics);
                        for (int i3 = 1; i3 <= 1; i3++) {
                            if (MFDevice.postLayerGraphics[i3 - 1] != null) {
                                MFDevice.currentState.onRender(MFDevice.postLayerGraphics[i3 - 1], i3);
                            }
                        }
                    }
                }
                MFDevice.mainCanvas.repaint();
            }
            //}
        }
    };
    public static Thread mainThread;
    private static boolean methodCallTrace;
    /* access modifiers changed from: private */
    public static MFGameState nextState;
    /* access modifiers changed from: private */
    public static MFGraphics[] postLayerGraphics = new MFGraphics[1];
    /* access modifiers changed from: private */
    public static Image[] postLayerImage = new Image[1];
    /* access modifiers changed from: private */
    public static MFGraphics[] preLayerGraphics = new MFGraphics[1];
    /* access modifiers changed from: private */
    public static Image[] preLayerImage = new Image[1];
    public static int preScaleShift = 0;
    public static boolean preScaleZoomInFlag = false;
    public static boolean preScaleZoomOutFlag = false;
    private static Hashtable<String, byte[]> records;
    /* access modifiers changed from: private */
    public static boolean responseInterrupt;
    static float scaleFactor;
    static int screenHeight;
    static int screenWidth;
    static boolean shieldInput = true;
    private static int simType = -1;
    static boolean useClearFont;
    static int verticvalOffset;
    /* access modifiers changed from: private */
    public static boolean vibraionFlag;
    /* access modifiers changed from: private */
    public static long vibrateStartTime;
    /* access modifiers changed from: private */
    public static int vibrateTime;
    /* access modifiers changed from: private */
    public static Vibrator vibrator;
    private static String webPageUrl;

    static class MyGameCanvas extends Canvas {
        protected MyGameCanvas(Context context) {
            super(context);
        }
        
        public Graphics getGraphics() {
            return this.mGraphics;
        }

        public final void keyPressed(int keyCode) {
            synchronized (MFDevice.mainRunnable) {
                MFGamePad.keyPressed(keyCode);
            }
        }

        public final void keyReleased(int keyCode) {
            synchronized (MFDevice.mainRunnable) {
                MFGamePad.keyReleased(keyCode);
            }
        }

        public final void paint(Graphics g) {
            synchronized (MFDevice.mainRunnable) {
                for (int i = 1; i > 0; i--) {
                    if (MFDevice.preLayerImage[i - 1] != null) {
                        g.drawImage(MFDevice.preLayerImage[i - 1], 0, 0, 0);
                    }
                }
                if (MFDevice.bufferImage != null) {
                    g.drawScreen(MFDevice.bufferImage, (Rect) null, new Rect(0, 0, MFDevice.deviceWidth, MFDevice.deviceHeight));
                }
                for (int i2 = 1; i2 <= 1; i2++) {
                    if (MFDevice.postLayerImage[i2 - 1] != null) {
                        g.drawImage(MFDevice.postLayerImage[i2 - 1], 0, 0, 0);
                    }
                }
                if (MFDevice.fontImage != null) {
                    g.drawImage(MFDevice.fontImage, 0, 0, 0);
                }
                /*if (MFDevice.showFPS && MFDevice.render) {
                   g.setColor(255, 255, 255);
                   MFDevice.FPSString = "FPS: " + lastFPS;
                   MFDevice.fw = g.getFont().stringWidth(MFDevice.FPSString);
                   MFDevice.fh = g.getFont().getHeight();
                   g.drawString(MFDevice.FPSString, 10, 20, Graphics.TOP | Graphics.LEFT);
                   render = false;
                }*/
            }
        }

        public final void pointerDragged(int id, int x, int y) {
            if (MFDevice.preScaleZoomOutFlag) {
                x <<= MFDevice.preScaleShift;
                y <<= MFDevice.preScaleShift;
            } else if (MFDevice.preScaleZoomInFlag) {
                x >>= MFDevice.preScaleShift;
                y >>= MFDevice.preScaleShift;
            }
            if (MFDevice.drawRect != null) {
                x = ((x - MFDevice.drawRect.left) * MFDevice.screenWidth) / MFDevice.drawRect.width();
                y = ((y - MFDevice.drawRect.top) * MFDevice.screenHeight) / MFDevice.drawRect.height();
            }
            if (MFDevice.componentVector != null) {
                for (int i = 0; i < MFDevice.componentVector.size(); i++) {
                    ((MFComponent) MFDevice.componentVector.elementAt(i)).pointerDragged(id, x, y);
                }
            }
        }

        public final void pointerPressed(int id, int x, int y) {
            if (MFDevice.preScaleZoomOutFlag) {
                x <<= MFDevice.preScaleShift;
                y <<= MFDevice.preScaleShift;
            } else if (MFDevice.preScaleZoomInFlag) {
                x >>= MFDevice.preScaleShift;
                y >>= MFDevice.preScaleShift;
            }
            if (MFDevice.drawRect != null) {
                x = ((x - MFDevice.drawRect.left) * MFDevice.screenWidth) / MFDevice.drawRect.width();
                y = ((y - MFDevice.drawRect.top) * MFDevice.screenHeight) / MFDevice.drawRect.height();
            }
            if (MFDevice.componentVector != null) {
                for (int i = 0; i < MFDevice.componentVector.size(); i++) {
                    ((MFComponent) MFDevice.componentVector.elementAt(i)).pointerPressed(id, x, y);
                }
            }
        }

        public final void pointerReleased(int id, int x, int y) {
            if (MFDevice.preScaleZoomOutFlag) {
                x <<= MFDevice.preScaleShift;
                y <<= MFDevice.preScaleShift;
            } else if (MFDevice.preScaleZoomInFlag) {
                x >>= MFDevice.preScaleShift;
                y >>= MFDevice.preScaleShift;
            }
            if (MFDevice.drawRect != null) {
                x = ((x - MFDevice.drawRect.left) * MFDevice.screenWidth) / MFDevice.drawRect.width();
                y = ((y - MFDevice.drawRect.top) * MFDevice.screenHeight) / MFDevice.drawRect.height();
            }
            if (MFDevice.componentVector != null) {
                for (int i = 0; i < MFDevice.componentVector.size(); i++) {
                    ((MFComponent) MFDevice.componentVector.elementAt(i)).pointerReleased(id, x, y);
                }
            }
        }
        
        public final void hideNotify() {
            if (!MFDevice.inSuspendFlag) {
                MFDevice.inSuspendFlag = true;
                MFDevice.notifyPause();
            }
        }

        public final void showNotify() {
            if (MFDevice.inSuspendFlag) {
               MFDevice.notifyResume();
            }
               MFMain.getInstance().drawDeviceSuspend(MFDevice.graphics);   
               //MFDevice.inSuspendFlag = false;
        }

        public final void trackballMoved(int keyCode) {
            synchronized (MFDevice.mainRunnable) {
                MFGamePad.trackballMoved(keyCode);
            }
        }

    }

    public static final void addComponent(MFComponent component) {
        if (componentVector != null && !componentVector.contains(component)) {
            component.reset();
            componentVector.addElement(component);
        }
    }

    public static void changeState(MFGameState gameState) {
        nextState = gameState;
    }

    public static final void clearScreen() {
        for (int i = 1; i > 0; i--) {
            if (preLayerImage[i - 1] != null) {
                preLayerImage[i - 1].earseColor(0);
            }
        }
        if (bufferImage != null) {
            bufferImage.earseColor(0);
        }
        for (int i2 = 1; i2 <= 1; i2++) {
            if (postLayerImage[i2 - 1] != null) {
                postLayerImage[i2 - 1].earseColor(0);
            }
        }
        if (fontImage != null) {
            fontImage.earseColor(0);
        }
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static final void DEBUG_ENABLE_LOGIC_TRACE(boolean enable) {
    }

    public static final void DEBUG_ENABLE_METHOD_TRACE(boolean enable) {
    }

    public static int DEBUG_GET_DEVICE_KEY_VALUE() {
        return 0;
    }

    public static final void DEBUG_PAINT_LOGIC_TRACE(String traceLog) {
    }

    public static final void DEBUG_PAINT_MESSAGE(String message) {
    }

    public static final void DEBUG_PAINT_METHOD_TRACE(String methodInfo) {
    }

    public static final void DEBUG_SHOW_ERROR(Throwable t) {
    }

    public static final void deleteRecord(String recordName) {
        records.remove(recordName);
        updateRecords();
    }

    public static void disableExceedBoundary() {
        graphics.disableExceedBoundary();
    }

    public static void enableExceedBoundary() {
        graphics.enableExceedBoundary();
    }

    public static int getApnType() {
        apnType = 0;
        Cursor cr = MFMain.getInstance().getContentResolver().query(Uri.parse("content://telephony/carriers/preferapn"), (String[]) null, (String) null, (String[]) null, (String) null);
        String pxy = "";
        while (cr != null && cr.moveToNext()) {
            pxy = cr.getString(cr.getColumnIndex("proxy"));
        }
        if (pxy != null && !pxy.equals("")) {
            apnType = 1;
        }
        return apnType;
    }

    public static final int getDeviceHeight() {
        return deviceHeight;
    }

    public static final int getDeviceWidth() {
        return deviceWidth;
    }

    public static boolean getEnableCustomBack() {
        return enableCustomBack;
    }

    public static boolean getEnableTrackBall() {
        return enableTrackBall;
    }

    public static boolean getEnableVolumeKey() {
        return enableVolumeKey;
    }

    public static MFGraphics getGraphics() {
        return graphics;
    }

    public static Thread getMainThread() {
        return mainThread;
    }

    public static int getNativeCanvasBottom() {
        if (preScaleZoomOutFlag) {
            return (bufferHeight - verticvalOffset) << preScaleShift;
        }
        if (preScaleZoomInFlag) {
            return (bufferHeight - verticvalOffset) >> preScaleShift;
        }
        return bufferHeight + verticvalOffset;
    }

    public static int getNativeCanvasHeight() {
        if (preScaleZoomOutFlag) {
            return bufferHeight << preScaleShift;
        }
        if (preScaleZoomInFlag) {
            return bufferHeight >> preScaleShift;
        }
        return bufferHeight;
    }

    public static int getNativeCanvasLeft() {
        if (preScaleZoomOutFlag) {
            return -(horizontalOffset << preScaleShift);
        }
        if (preScaleZoomInFlag) {
            return -(horizontalOffset >> preScaleShift);
        }
        return -horizontalOffset;
    }

    public static int getNativeCanvasRight() {
        if (preScaleZoomOutFlag) {
            return (bufferWidth - horizontalOffset) << preScaleShift;
        }
        if (preScaleZoomInFlag) {
            return (bufferWidth - horizontalOffset) >> preScaleShift;
        }
        return bufferWidth + horizontalOffset;
    }

    public static int getNativeCanvasTop() {
        if (preScaleZoomOutFlag) {
            return -(verticvalOffset << preScaleShift);
        }
        if (preScaleZoomInFlag) {
            return -(verticvalOffset >> preScaleShift);
        }
        return -verticvalOffset;
    }

    public static int getNativeCanvasWidth() {
        if (preScaleZoomOutFlag) {
            return bufferWidth << preScaleShift;
        }
        if (preScaleZoomInFlag) {
            return bufferWidth >> preScaleShift;
        }
        return bufferWidth;
    }

    public static String getPackageVersion() {
        try {
            return MFMain.getInstance().getPackageManager().getPackageInfo(MFMain.getInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static final MFInputStream getResourceAsMFStream(String url) {
        String str;
        try {
            AssetManager assets = MFMain.getInstance().getAssets();
            if (url.startsWith("/")) {
                str = url.substring(1);
            } else {
                str = url;
            }
            return new MFInputStream(assets.open(str));
        } catch (Exception e) {
            Exception exc = e;
            System.out.println("Error on loading: " + url);
            return null;
        }
    }

    public static final InputStream getResourceAsStream(String url) {
        String str;
        try {
            AssetManager assets = MFMain.getInstance().getAssets();
            if (url.startsWith("/")) {
                str = url.substring(1);
            } else {
                str = url;
            }
            return assets.open(str);
        } catch (Exception e) {
            Exception exc = e;
            System.out.println("Error on loading: " + url);
            return null;
        }
    }

    public static final int getScreenHeight() {
        if (preScaleZoomOutFlag) {
            return screenHeight << preScaleShift;
        }
        if (preScaleZoomInFlag) {
            return screenHeight >> preScaleShift;
        }
        return screenHeight;
    }

    public static final int getScreenWidth() {
        if (preScaleZoomOutFlag) {
            return screenWidth << preScaleShift;
        }
        if (preScaleZoomInFlag) {
            return screenWidth >> preScaleShift;
        }
        return screenWidth;
    }

    public static int getSimType() {
        if (simType < 0) {
            simType = 0;
            String operator = ((TelephonyManager) MFMain.getInstance().getSystemService("phone")).getSimOperator();
            if (operator.equals("46000") || operator.equals("46002")) {
                simType = 1;
            } else if (operator.equals("46001")) {
                simType = 2;
            } else if (operator.equals("46003")) {
                simType = 3;
            }
        }
        return simType;
    }

    public static final Object getSystemDisplayable() {
        return mainCanvas;
    }
    
    public static final String getVersion() {
        return VERSION_INFO;
    }

    /* access modifiers changed from: private */
    public static final void initRecords() {
        Exception e;
        if (records == null) {
            records = new Hashtable<>();
            DataInputStream dis = null;
            ByteArrayInputStream bis = null;
            try {
                ByteArrayInputStream bis2 = new ByteArrayInputStream(openRecordStore(RECORD_NAME));
                try {
                    DataInputStream dis2 = new DataInputStream(bis2);
                    try {
                        int recordStoreNumber = dis2.readInt();
                        for (int i = 0; i < recordStoreNumber; i++) {
                            String name = dis2.readUTF();
                            int offset = 0;
                            int dataSize = dis2.readInt();
                            byte[] data = new byte[dataSize];
                            do {
                                offset = dis2.read(data, offset, dataSize - offset);
                            } while (offset < dataSize);
                            records.put(name, data);
                        }
                        bis = bis2;
                        dis = dis2;
                    } catch (Exception e2) {
                        e = e2;
                        bis = bis2;
                        dis = dis2;
                        e.printStackTrace();
                        dis.close();
                        bis.close();
                    }
                } catch (Exception e3) {
                    e = e3;
                    bis = bis2;
                    e.printStackTrace();
                    dis.close();
                    bis.close();
                }
            } catch (Exception e4) {
                e = e4;
                e.printStackTrace();
                try {
                dis.close();
                bis.close();
                } catch (Exception e7) {
                }
            }
            try {
                dis.close();
            } catch (Exception e5) {
            }
            try {
                bis.close();
            } catch (Exception e6) {
            }
        }
    }

    public static final byte[] loadRecord(String recordName) {
        return records.get(recordName);
    }

    public static final void notifyExit() {
        exitFlag = true;
    }

    public static final void notifyPause() {
        synchronized (mainRunnable) {
            if (responseInterrupt && !interruptPauseFlag) {
                /*stopVibrate();
                MFGamePad.resetKeys();
                MFSound.deviceInterrupt();*/
                if (currentState != null) {
                    currentState.onPause();
                }
                MFDevice.delta = 0;
                MFDevice.delta2 = 0;
                //MFDevice.lastSystemTime = System.nanoTime();
                MFDevice.tickFlag = true;
                MFDevice.started = false;
                MFDevice.newth = false;
                interruptPauseFlag = true;
                if (componentVector != null) {
                    for (int i = 0; i < componentVector.size(); i++) {
                        componentVector.elementAt(i).reset();
                    }
                    interruptConfirm = new MFTouchKey(0, screenHeight - 50, 100, 50, 2112);
                    addComponent(interruptConfirm);
                }
            }
        }
    }

    public static final void notifyResume() {
        synchronized (mainRunnable) {
            if (responseInterrupt && interruptPauseFlag) {
                /*MFGamePad.resetKeys();
                MFSound.deviceResume();*/
                if (currentState != null) {
                    currentState.onResume();
                }
                interruptPauseFlag = false;
                if (componentVector != null) {
                    /*for (int i = 0; i < componentVector.size(); i++) {
                        componentVector.elementAt(i).reset();
                    }*/
                    removeComponent(interruptConfirm);
                }
                MFDevice.inSuspendFlag = false;
            }
        }
    }

    public static final void notifyStart(int width, int height) {
        if (mainThread == null) {
            if (MFMain.getInstance().getRequestedOrientation() == 1) {
                screenHeight = MFScreen.getScreenHeight(MFMain.getInstance());
                screenWidth = MFScreen.getScreenWidth(MFMain.getInstance());
                if (width > height) {
                    deviceWidth = height;
                    deviceHeight = width;
                } else {
                    deviceWidth = width;
                    deviceHeight = height;
                }
            } else {
                /*screenHeight*/screenWidth = MFScreen.getScreenWidth(MFMain.getInstance());
                /*screenWidth*/screenHeight = MFScreen.getScreenHeight(MFMain.getInstance());
                if (width < height) {
                    deviceWidth = height;
                    deviceHeight = width;
                } else {
                    deviceWidth = width;
                    deviceHeight = height;
                }
            }
            System.out.println("screenwidth:" + deviceWidth + ",screenheight:" + deviceHeight);
            changeState(MFMain.getInstance().getEntryGameState());
            //setFullscreenMode(false);
            startThread();
        }
    }

    public static final void notifyKeyPressed(int keyCode) {
        currentState.onKeyDown(MFGamePad.decodeSystemKey(keyCode));
    }

    private static byte[] openRecordStore(String str) {
        byte[] ret;
        byte[] ret2 = null;
        try {
            DataInputStream dis = new DataInputStream(MFMain.getInstance().openFileInput(String.valueOf(str) + ".rms"));
            try {
                int t = dis.readInt();
                if (t == 0) {
                    ret = NULL_RECORD;
                } else {
                    ret = new byte[t];
                    for (int i = 0; i < ret.length; i++) {
                        ret[i] = dis.readByte();
                    }
                }
                dis.close();
                DataInputStream dataInputStream = dis;
                return ret;
            } catch (FileNotFoundException e) {
                FileNotFoundException fileNotFoundException = e;
                DataInputStream dataInputStream2 = dis;
                try {
                    System.out.println("Create new save file.");
                    ret2 = NULL_RECORD;
                    DataOutputStream dos = new DataOutputStream(MFMain.getInstance().openFileOutput(String.valueOf(str) + ".rms", 0));
                    dos.write(NULL_RECORD, 0, NULL_RECORD.length);
                    dos.flush();
                    dos.close();
                    return ret2;
                } catch (IOException e2) {
                    IOException iOException = e2;
                    System.out.println("RMS ERROR : Can't create rms file.");
                    return ret2;
                }
            } catch (IOException e3) {
                IOException iOException2 = e3;
                DataInputStream dataInputStream3 = dis;
                System.out.println("RMS ERROR : Can't read rms file.");
                return ret2;
            }
        } catch (FileNotFoundException e4) {
            FileNotFoundException fileNotFoundException2 = e4;
            System.out.println("Create new save file.");
            ret2 = NULL_RECORD;
            try {
            DataOutputStream dos2 = new DataOutputStream(MFMain.getInstance().openFileOutput(String.valueOf(str) + ".rms", 0));
            dos2.write(NULL_RECORD, 0, NULL_RECORD.length);
            dos2.flush();
            dos2.close();
            } catch (IOException e6) {
            }
            return ret2;
        } catch (IOException e5) {
            IOException iOException3 = e5;
            System.out.println("RMS ERROR : Can't read rms file.");
            return ret2;
        }
    }

    public static final void openUrl() {
        if (webPageUrl != null) {
            try {
                MFMain.browser = true;
                MFMain.getInstance().platformRequest(webPageUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static final void openUrl(String url, boolean needClose) {
        if (needClose) {
            webPageUrl = url;
            notifyExit();
            return;
        }
        try {
            MFMain.browser = true;
            MFMain.getInstance().platformRequest(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final void removeAllComponents() {
        if (componentVector != null) {
            componentVector.removeAllElements();
        }
    }

    public static final void removeComponent(MFComponent component) {
        if (componentVector != null) {
            componentVector.removeElement(component);
        }
    }

    public static final void saveRecord(String recordName, byte[] record) {
        records.put(recordName, record);
        updateRecords();
    }

    public static void setAntiAlias(boolean b) {
        mainCanvas.setAntiAlias(b);
    }

    public static void setCanvasSize(int w, int h) {
        screenWidth = w;
        screenHeight = h;
    }

    public static void setClearBuffer(boolean b) {
        clearBuffer = b;
    }

    public static void setEnableCustomBack(boolean b) {
        enableCustomBack = b;
    }

    public static void setEnableTrackBall(boolean b) {
        enableTrackBall = b;
    }

    public static void setEnableVolumeKey(boolean b) {
        enableVolumeKey = b;
    }

    public static void enableClearFont() {
        useClearFont = true;
        if (fontImage == null) {
            fontImage = Image.createImage(deviceWidth, deviceHeight);
            fontGraphics = MFGraphics.createMFFontGraphics(fontImage.getGraphics(), deviceWidth, deviceHeight);
        }
    }

    public static void disableClearFont() {
        useClearFont = false;
        fontImage = null;
        fontGraphics = null;
    }

    public static void enableLayer(int layer) {
        if (layer <= 0 || layer > 1) {
            if (layer < 0 && layer >= -1 && preLayerImage[(-layer) - 1] == null) {
                preLayerImage[(-layer) - 1] = Image.createImage(deviceWidth, deviceHeight);
                preLayerGraphics[(-layer) - 1] = MFGraphics.createMFGraphics(preLayerImage[(-layer) - 1].getGraphics(), deviceWidth, deviceHeight);
            }
        } else if (postLayerImage[layer - 1] == null) {
            postLayerImage[layer - 1] = Image.createImage(deviceWidth, deviceHeight);
            postLayerGraphics[layer - 1] = MFGraphics.createMFGraphics(postLayerImage[layer - 1].getGraphics(), deviceWidth, deviceHeight);
        }
    }

    public static void disableLayer(int layer) {
        if (layer > 0 && layer <= 1) {
            postLayerImage[layer - 1] = null;
            postLayerGraphics[layer - 1] = null;
        } else if (layer < 0 && layer >= -1) {
            preLayerImage[(-layer) - 1] = null;
            preLayerGraphics[(-layer) - 1] = null;
        }
    }

    public static void setFilterBitmap(boolean b) {
        mainCanvas.setFilterBitmap(b);
    }

    public static void setFullscreenMode(boolean b) {
        if (b) {
            screenHeight = MFScreen.getScreenHeight(MFMain.getInstance());
            screenWidth = MFScreen.getScreenWidth(MFMain.getInstance());
            drawRect = new Rect(0, 0, deviceWidth, deviceHeight);
            bufferImage = Image.createImage(deviceWidth, deviceHeight);
            graphics = MFGraphics.createMFGraphics(bufferImage.getGraphics(), deviceWidth, deviceHeight);
            bufferWidth = bufferImage.getWidth();
            bufferHeight = bufferImage.getHeight();
            horizontalOffset = (drawRect.left * bufferWidth) / deviceWidth;
            verticvalOffset = (drawRect.top * bufferHeight) / deviceHeight;
            preScaleZoomInFlag = false;
            preScaleZoomOutFlag = false;
            preScaleShift = 0;
            scaleFactor = 1.0f;
        } else {
        if (((float) deviceWidth) / ((float) screenWidth) > ((float) deviceHeight) / ((float) screenHeight)) {
            if (preScaleZoomOutFlag && screenHeight > deviceHeight) {
                preScaleShift = 0;
                int tmpHeight = screenHeight;
                while (tmpHeight > deviceHeight && tmpHeight - deviceHeight > deviceHeight - (tmpHeight / 2)) {
                    tmpHeight /= 2;
                    screenWidth /= 2;
                    screenHeight /= 2;
                    preScaleShift++;
                }
                if (preScaleShift == 0) {
                    preScaleZoomOutFlag = false;
                }
                preScaleZoomInFlag = false;
            }
            if (preScaleZoomInFlag && screenHeight < deviceHeight) {
                preScaleShift = 0;
                int tmpHeight2 = screenHeight;
                while (tmpHeight2 < deviceHeight && deviceHeight - tmpHeight2 > (tmpHeight2 * 2) - deviceHeight) {
                    tmpHeight2 *= 2;
                    screenWidth *= 2;
                    screenHeight *= 2;
                    preScaleShift++;
                }
                if (preScaleShift == 0) {
                    preScaleZoomInFlag = false;
                }
                preScaleZoomOutFlag = false;
            }
            int h = deviceHeight;
            int w = (screenWidth * h) / screenHeight;
            int x = (deviceWidth - w) / 2;
            drawRect = new Rect(x, 0, x + w, 0 + h);
            if (preScaleZoomOutFlag) {
                bufferImage = Image.createImage(((screenHeight * deviceWidth) / deviceHeight) << preScaleShift, screenHeight << preScaleShift);
            } else if (preScaleZoomInFlag) {
                bufferImage = Image.createImage(((screenHeight * deviceWidth) / deviceHeight) >> preScaleShift, screenHeight >> preScaleShift);
            } else {
                bufferImage = Image.createImage((screenHeight * deviceWidth) / deviceHeight, screenHeight);
            }
            scaleFactor = ((float) deviceHeight) / ((float) screenHeight);
            graphics = MFGraphics.createMFGraphics(bufferImage.getGraphics(), (screenHeight * deviceWidth) / deviceHeight, screenHeight);
        } else {
            if (preScaleZoomOutFlag && screenWidth > deviceWidth) {
                preScaleShift = 0;
                int tmpWidth = screenWidth;
                while (tmpWidth > deviceWidth && ((float) tmpWidth) - ((float) deviceWidth) > ((float) (deviceWidth - (tmpWidth / 2)))) {
                    tmpWidth /= 2;
                    screenWidth /= 2;
                    screenHeight /= 2;
                    preScaleShift++;
                }
                if (preScaleShift == 0) {
                    preScaleZoomOutFlag = false;
                }
                preScaleZoomInFlag = false;
            }
            if (preScaleZoomInFlag && screenWidth < deviceWidth) {
                preScaleShift = 0;
                int tmpWidth2 = screenWidth;
                while (tmpWidth2 < deviceWidth && deviceWidth - tmpWidth2 < (tmpWidth2 * 2) - deviceWidth) {
                    tmpWidth2 *= 2;
                    screenWidth *= 2;
                    screenHeight *= 2;
                    preScaleShift++;
                }
                if (preScaleShift == 0) {
                    preScaleZoomInFlag = false;
                }
                preScaleZoomOutFlag = false;
            }
            int w2 = deviceWidth;
            int h2 = (screenHeight * w2) / screenWidth;
            int y = (deviceHeight - h2) / 2;
            scaleFactor = (float) (deviceWidth / screenWidth);
            drawRect = new Rect(0, y, 0 + w2, y + h2);
            if (preScaleZoomOutFlag) {
                bufferImage = Image.createImage(screenWidth << preScaleShift, ((screenWidth * deviceHeight) / deviceWidth) << preScaleShift);
            } else if (preScaleZoomInFlag) {
                bufferImage = Image.createImage(screenWidth >> preScaleShift, ((screenWidth * deviceHeight) / deviceWidth) >> preScaleShift);
            } else {
                bufferImage = Image.createImage(screenWidth, (screenWidth * deviceHeight) / deviceWidth);
            }
            graphics = MFGraphics.createMFGraphics(bufferImage.getGraphics(), screenWidth, (screenWidth * deviceHeight) / deviceWidth);
        }
        bufferWidth = bufferImage.getWidth() >> preScaleShift;
        bufferHeight = bufferImage.getHeight() >> preScaleShift;
        horizontalOffset = (drawRect.left * bufferWidth) / deviceWidth;
        verticvalOffset = (drawRect.top * bufferHeight) / deviceHeight;
        
        }
        System.out.println("deviceWidth:" + deviceWidth + ",deviceHeight:" + deviceHeight);
        System.out.println("bufferWidth:" + bufferWidth + ",bufferHeight:" + bufferHeight);
        System.out.println("screenWidth:" + screenWidth + ",screenHeight:" + screenHeight);
        System.out.println("horizontalOffset:" + horizontalOffset + ",verticvalOffset:" + verticvalOffset);
        System.out.println("scaleFactor:" + scaleFactor);
        System.out.println("drawRect:" + drawRect);
        graphics.g.getCanvas().translate((float) horizontalOffset, (float) verticvalOffset);
    }

    public static void setPreScale(boolean zoomIn, boolean zoomOut) {
        preScaleZoomInFlag = zoomIn;
        preScaleZoomOutFlag = zoomOut;
    }

    private static void setRecord(String str, byte[] data, int len) {
        try {
            DataOutputStream dos = new DataOutputStream(MFMain.getInstance().openFileOutput(String.valueOf(str) + ".rms", 0));
            dos.writeInt(len);
            for (int i = 0; i < len; i++) {
                dos.writeByte(data[i]);
            }
            dos.flush();
            dos.close();
        } catch (Exception e) {
            Exception exc = e;
            System.out.println("RMS ERROR : Can't save rms file.");
        }
    }

    public static final void setResponseInterruptFlag(boolean flag) {
        responseInterrupt = flag;
    }

    public static final void setShieldInput(boolean b) {
        shieldInput = b;
    }

    public static void setUseMultitouch(boolean b) {
        mainCanvas.setUseMultitouch(b);
    }

    public static final void setVibrationFlag(boolean enable) {
        vibraionFlag = enable;
        if (!vibraionFlag) {
            stopVibrate();
        }
    }

    public static final void startThread() {
        if (mainThread == null) {
            mainThread = new Thread(mainRunnable);
            mainThread.start();
        }
    }

    public static final void startVibrate() {
        if (vibraionFlag) {
            inVibrationFlag = true;
            ((Vibrator) MFMain.getInstance().getSystemService("vibrator")).vibrate(10000);
        }
    }

    public static final void stopVibrate() {
        if (inVibrationFlag) {
            inVibrationFlag = false;
            ((Vibrator) MFMain.getInstance().getSystemService("vibrator")).cancel();
        }
    }

    private static final void updateRecords() {
        Exception e;
        DataOutputStream out = null;
        DataOutputStream out2 = null;
        ByteArrayOutputStream bos = null;
        try {
            ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
            try {
                out = new DataOutputStream(bos2);
            } catch (Exception e2) {
                e = e2;
                bos = bos2;
                e.printStackTrace();
                out2.close();
                bos.close();
            }
            try {
                out.writeInt(records.size());
                Enumeration<String> e3 = records.keys();
                for (int i = 0; i < records.size(); i++) {
                    String name = e3.nextElement();
                    byte[] tmp = records.get(name);
                    out.writeUTF(name);
                    out.writeInt(tmp.length);
                    out.write(tmp);
                }
                out.flush();
                byte[] tmp2 = bos2.toByteArray();
                setRecord(RECORD_NAME, tmp2, tmp2.length);
                bos = bos2;
                out2 = out;
            } catch (Exception e4) {
                e = e4;
                bos = bos2;
                out2 = out;
                e.printStackTrace();
                out2.close();
                bos.close();
            }
        } catch (Exception e5) {
            e = e5;
            e.printStackTrace();
            try {
            out2.close();
            bos.close();
            } catch (IOException e8) {
            }
        }
        try {
            out2.close();
        } catch (Exception e6) {
        }
        try {
            bos.close();
        } catch (Exception e7) {
        }
    }

    public static final void vibrateByTime(int time) {
        if (vibraionFlag) {
            vibrationImpl(time);
        }
    }

    /* access modifiers changed from: private */
    public static final void vibrationImpl(int time) {
        vibrator.vibrate((long) time);
    }
}
