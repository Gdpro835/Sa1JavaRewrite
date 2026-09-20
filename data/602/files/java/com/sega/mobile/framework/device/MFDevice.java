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
import GameEngine.time.GameTime;
import com.sega.MFLib.Main;
import com.sega.mobile.framework.opengl.GLGraphics;

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
    protected static volatile MyGameCanvas mainCanvas;
    public static final Object GAME_LOCK = new Object();
    public static volatile boolean isPaused;
    private static boolean engineInitialized;
    private static boolean fullscreen;
    private static GLGraphics gpu;

    public static void shutdown() {
        synchronized (GAME_LOCK) {
            if (!engineInitialized) return;
            if (currentState != null) currentState.onExit();
            State.State.shutdown();
            MFSound.releaseAllSound();
            MFSensor.release();
            stopVibrate();
            componentVector.clear();
            preLayerGraphics[0] = postLayerGraphics[0] = null;
            currentState = nextState = null;
            gpu = null;
            graphics = null;
            engineInitialized = false;
            exitFlag = false;
            mainThread = null;
            GameTime.reset();
        }
    }

    public static Canvas attach(Context context) {
        synchronized (GAME_LOCK) {
            // Rebind legacy UI adapters without reinitializing game/charge records.
            com.sega.mobile.platform.ChargePlatform.mContext = context;
            if (context instanceof Main) PlatformStandard.Standard2.getMain((Main) context);
            mainCanvas = new MyGameCanvas(context);
            return mainCanvas;
        }
    }

    public static boolean isCurrentCanvas(Canvas canvas) {
        return mainCanvas == canvas;
    }

    public static void bindSurface(Canvas canvas, GLGraphics graphics, int width, int height) {
        if (!isCurrentCanvas(canvas)) return;
        synchronized (GAME_LOCK) {
            mainThread = Thread.currentThread();
            gpu = graphics;
            deviceWidth = width;
            deviceHeight = height;
            if (!engineInitialized) {
                exitFlag = false;
                responseInterrupt = true;
                initRecords();
                MFGraphics.init();
                MFSound.init();
                MFSensor.init();
                MFGamePad.resetKeys();
                vibrator = (Vibrator) MFMain.getInstance().getSystemService(Context.VIBRATOR_SERVICE);
                componentVector = new Vector<MFComponent>();
                vibraionFlag = true;
                changeState(MFMain.getInstance().getEntryGameState());
                engineInitialized = true;
            }
            configureViewport();
        }
    }

    /** One variable-delta simulation update, then one GPU frame; no tick catch-up loop. */
    public static void renderFrame(double seconds) {
        synchronized (GAME_LOCK) {
            if (!engineInitialized || gpu == null) return;
            double delta = isPaused || MFMain.browser ? 0.0 : seconds * (Main.BULLET_TIME ? 1.0 / 16.0 : 1.0);
            GameTime.beginFrame(delta);
            if (nextState != null) {
                if (currentState != null) currentState.onExit();
                currentState = nextState;
                nextState = null;
                currentState.onEnter();
            }
            if (currentState == null) return;
            if (exitFlag) {
                currentState.onExit();
                currentState = null;
                MFMain.getInstance().runOnUiThread(new Runnable() {
                    public void run() { MFMain.getInstance().notifyDestroyed(); }
                });
                return;
            }
            if (delta > 0.0) {
                MFGamePad.keyTick();
                for (int i = 0; i < componentVector.size(); i++) componentVector.elementAt(i).tick();
                MFSound.tick();
                if (interruptPauseFlag && !inSuspendFlag && MFMain.getInstance().logicDeviceSuspend()) notifyResume();
                if (!interruptPauseFlag) currentState.onUpdate(delta);
                if (inVibrationFlag && vibrateTime > 0 && System.currentTimeMillis() - vibrateStartTime >= vibrateTime) {
                    stopVibrate();
                }
            }
            if (preLayerGraphics[0] != null) {
                gpu.viewport(deviceWidth, deviceHeight, 1f, 0f, 0f);
                preLayerGraphics[0].reset();
                currentState.onRender(preLayerGraphics[0], -1);
            }
            gpu.viewport(deviceWidth, deviceHeight, scaleFactor, drawRect.left, drawRect.top);
            graphics.reset();
            graphics.setClip(0, 0, screenWidth, screenHeight);
            if (interruptPauseFlag) MFMain.getInstance().drawDeviceSuspend(graphics);
            else currentState.onRender(graphics);
            if (postLayerGraphics[0] != null) {
                gpu.viewport(deviceWidth, deviceHeight, 1f, 0f, 0f);
                postLayerGraphics[0].reset();
                currentState.onRender(postLayerGraphics[0], 1);
            }
        }
    }

    private static void configureViewport() {
        if (gpu == null || deviceWidth <= 0 || deviceHeight <= 0) return;
        screenWidth = Math.max(1, screenWidth);
        screenHeight = Math.max(1, screenHeight);
        scaleFactor = Math.min(deviceWidth / (float) screenWidth, deviceHeight / (float) screenHeight);
        int w = Math.round(screenWidth * scaleFactor);
        int h = Math.round(screenHeight * scaleFactor);
        drawRect = new Rect((deviceWidth - w) / 2, (deviceHeight - h) / 2,
                (deviceWidth - w) / 2 + w, (deviceHeight - h) / 2 + h);
        bufferWidth = Math.round(deviceWidth / scaleFactor);
        bufferHeight = Math.round(deviceHeight / scaleFactor);
        horizontalOffset = Math.round(drawRect.left / scaleFactor);
        verticvalOffset = Math.round(drawRect.top / scaleFactor);
        bufferImage = null;
        fontImage = null;
        fontGraphics = null;
        useClearFont = false;
        graphics = MFGraphics.createMFGraphics(gpu, screenWidth, screenHeight);
        if (preLayerGraphics[0] != null) preLayerGraphics[0] = MFGraphics.createMFGraphics(gpu, deviceWidth, deviceHeight);
        if (postLayerGraphics[0] != null) postLayerGraphics[0] = MFGraphics.createMFGraphics(gpu, deviceWidth, deviceHeight);
    }

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
            synchronized (MFDevice.GAME_LOCK) {
                MFGamePad.keyPressed(keyCode);
            }
        }

        public final void keyReleased(int keyCode) {
            synchronized (MFDevice.GAME_LOCK) {
                MFGamePad.keyReleased(keyCode);
            }
        }

        public final void paint(Graphics g) {
            // Rendering is issued directly by renderFrame() on the GLES thread.
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
        
        public final void hideNotify() { super.hideNotify(); }

        public final void showNotify() { super.showNotify(); }

        public final void trackballMoved(int keyCode) {
            synchronized (MFDevice.GAME_LOCK) {
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
        synchronized (GAME_LOCK) { nextState = gameState; }
    }

    public static final void clearScreen() {
        if (graphics != null) graphics.clearScreen(0);
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
        if (mainCanvas == null) attach(MFMain.getInstance());
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
        synchronized (GAME_LOCK) { exitFlag = true; }
    }

    public static final void notifyPause() {
        synchronized (GAME_LOCK) {
            isPaused = true;
            if (engineInitialized) MFSensor.release();
            inSuspendFlag = true;
            stopVibrate();
            MFGamePad.resetKeys();
            if (componentVector != null) for (MFComponent component : componentVector) component.reset();
            if (!interruptPauseFlag && currentState != null) currentState.onPause();
            interruptPauseFlag = true;
        }
    }

    public static final void notifyResume() {
        synchronized (GAME_LOCK) {
            MFGamePad.resetKeys();
            if (engineInitialized) MFSensor.init();
            if (interruptPauseFlag && currentState != null) currentState.onResume();
            interruptPauseFlag = false;
            inSuspendFlag = false;
            isPaused = false;
        }
    }

    public static final void notifyStart(int width, int height) {
        if (gpu != null) bindSurface(mainCanvas, gpu, width, height);
    }

    public static final void notifyKeyPressed(int keyCode) {
        if (currentState != null) currentState.onKeyDown(MFGamePad.decodeSystemKey(keyCode));
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
        // GPU glyph textures are composited with the scene; no extra CPU overlay.
        useClearFont = false;
    }

    public static void disableClearFont() {
        useClearFont = false;
        fontImage = null;
        fontGraphics = null;
    }

    public static void enableLayer(int layer) {
        if (gpu == null) return;
        if (layer == -1) preLayerGraphics[0] = MFGraphics.createMFGraphics(gpu, deviceWidth, deviceHeight);
        else if (layer == 1) postLayerGraphics[0] = MFGraphics.createMFGraphics(gpu, deviceWidth, deviceHeight);
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
        fullscreen = b;
        if (b) { screenWidth = deviceWidth; screenHeight = deviceHeight; }
        preScaleZoomInFlag = preScaleZoomOutFlag = false;
        preScaleShift = 0;
        configureViewport();
    }

    public static void setPreScale(boolean zoomIn, boolean zoomOut) {
        // GLES scales the logical viewport, never the source bitmaps.
        preScaleZoomInFlag = preScaleZoomOutFlag = false;
        preScaleShift = 0;
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
        // GLSurfaceView owns the only simulation/render thread.
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
