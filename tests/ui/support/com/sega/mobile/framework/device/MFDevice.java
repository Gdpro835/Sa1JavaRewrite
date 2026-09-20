package com.sega.mobile.framework.device;

import java.io.*;
import java.util.*;

/** Test-only host services. The menu, input components and animation code remain production code. */
public final class MFDevice {
    public static int resourceReads;
    public static boolean preScaleZoomInFlag, preScaleZoomOutFlag, useClearFont;
    public static int preScaleShift;
    public static MFGraphics fontGraphics;
    public static MFImage fontImage;
    public static final Vector<MFComponent> componentVector = new Vector<MFComponent>();
    public static InputStream getResourceAsStream(String name) {
        resourceReads++;
        File file = new File(System.getProperty("sa1.assets"), name.startsWith("/") ? name.substring(1) : name);
        try { return new FileInputStream(file); } catch (FileNotFoundException e) { return null; }
    }
    public static MFInputStream getResourceAsMFStream(String name) {
        InputStream in = getResourceAsStream(name);
        return in == null ? null : new MFInputStream(in);
    }
    public static int getScreenWidth() { return 284; }
    public static int getScreenHeight() { return 160; }
    public static int getDeviceWidth() { return 1136; }
    public static int getDeviceHeight() { return 640; }
    public static void addComponent(MFComponent c) { c.reset(); componentVector.add(c); }
    public static void removeComponent(MFComponent c) { componentVector.remove(c); }
    public static void removeAllComponents() { componentVector.clear(); }
    public static void tick() {
        MFGamePad.keyTick();
        for (MFComponent c : new ArrayList<MFComponent>(componentVector)) c.tick();
    }
    public static void disableLayer(int layer) { }
    public static void enableLayer(int layer) { }
    public static byte[] loadRecord(String name) { return null; }
    public static void saveRecord(String name, byte[] value) { }
    public static void setResponseInterruptFlag(boolean enabled) { }
    public static void stopVibrate() { }
    public static void vibrateByTime(int ms) { }
}
