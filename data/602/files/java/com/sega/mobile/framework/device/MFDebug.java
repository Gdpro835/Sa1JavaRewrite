package com.sega.mobile.framework.device;

import android.util.Log;
import java.util.Iterator;
import java.util.Vector;

public class MFDebug {
    private static final String TAG = "MFDebug";
    static boolean debugFlag = false;
    static Vector<String> imageList = new Vector<>();
    static Vector<String> soundList = new Vector<>();

    public static void setDebugFlag(boolean b) {
        debugFlag = b;
    }

    public static void loadImage(String url) {
        if (debugFlag) {
            Log.i(TAG, "image load -> " + url);
            imageList.add(url);
        }
    }

    public static void releaseImage(String url) {
        if (debugFlag) {
            Log.i(TAG, "image release -> " + url);
            imageList.remove(url);
        }
    }

    public static void showImageList() {
        if (debugFlag) {
            Log.i(TAG, "--------------------Image List--------------------");
            Iterator<String> it = imageList.iterator();
            while (it.hasNext()) {
                Log.i(TAG, it.next());
            }
            Log.i(TAG, "--------------------------------------------------");
        }
    }

    public static void loadSound(String url) {
        if (debugFlag) {
            Log.i(TAG, "sound load -> " + url);
            soundList.add(url);
        }
    }

    public static void releaseSound(String url) {
        if (debugFlag) {
            Log.i(TAG, "sound release -> " + url);
            soundList.remove(url);
        }
    }

    public static void showSoundList() {
        if (debugFlag) {
            Log.i(TAG, "--------------------Sound List--------------------");
            Iterator<String> it = soundList.iterator();
            while (it.hasNext()) {
                Log.i(TAG, it.next());
            }
            Log.i(TAG, "--------------------------------------------------");
        }
    }
}
