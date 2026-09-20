package com.sega.mobile.framework.device;

import com.sega.mobile.define.MDPhone;

public class MFGamePad {
    private static int KEY = 0;
    public static final int KEY_A = 2112;
    public static final int KEY_ANYKEY = -1;
    public static final int KEY_B = 131072;
    public static final int KEY_BACK = 524288;
    public static final int KEY_C = 65536;
    public static final int KEY_D = 262144;
    public static final int KEY_DOWN = 16392;
    public static final int KEY_DOWN_LEFT = 8192;
    public static final int KEY_DOWN_RIGHT = 32768;
    public static final int KEY_JOYSTICK_L1 = 1073741824;
    public static final int KEY_JOYSTICK_MENU = 134217728;
    public static final int KEY_JOYSTICK_O = 8388608;
    public static final int KEY_JOYSTICK_R1 = Integer.MIN_VALUE;
    public static final int KEY_JOYSTICK_RECT = 33554432;
    public static final int KEY_JOYSTICK_SELECT = 268435456;
    public static final int KEY_JOYSTICK_START = 536870912;
    public static final int KEY_JOYSTICK_TRIANGLE = 67108864;
    public static final int KEY_JOYSTICK_X = 16777216;
    public static final int KEY_LEFT = 1040;
    public static final int KEY_MENU = 0;
    public static final int KEY_NULL = 0;
    public static final int KEY_NUM_0 = 131072;
    public static final int KEY_NUM_1 = 128;
    public static final int KEY_NUM_2 = 256;
    public static final int KEY_NUM_3 = 512;
    public static final int KEY_NUM_4 = 1024;
    public static final int KEY_NUM_5 = 2048;
    public static final int KEY_NUM_6 = 4096;
    public static final int KEY_NUM_7 = 8192;
    public static final int KEY_NUM_8 = 16384;
    public static final int KEY_NUM_9 = 32768;
    public static final int KEY_NUM_POUND = 262144;
    public static final int KEY_NUM_STAR = 65536;
    public static final int KEY_PAD_CONFIRM = 64;
    public static final int KEY_PAD_DOWN = 8;
    public static final int KEY_PAD_LEFT = 16;
    public static final int KEY_PAD_RIGHT = 32;
    public static final int KEY_PAD_UP = 4;
    private static int KEY_R = 0;
    public static final int KEY_RIGHT = 4128;
    public static final int KEY_S1 = 1;
    public static final int KEY_S2 = 2;
    public static final int KEY_SEARCH = 3;
    public static final int KEY_UP = 260;
    public static final int KEY_UP_LEFT = 128;
    public static final int KEY_UP_RIGHT = 512;
    public static final int KEY_VOLUME_DOWN = 2;
    public static final int KEY_VOLUME_UP = 1;
    private static int KeyOld = 0;
    private static int KeyPress = 0;
    private static int KeyRelease = 0;
    private static int KeyRepeat = 0;
    private static int deviceKeyValue = 0;
    private static boolean lastTrackballAvailiable = true;
    private static boolean trackballAvailiable = true;
    private static int trackballKey;

    public static final void resetKeys() {
        KEY = 0;
        KEY_R = 0;
        KeyPress = 0;
        KeyRepeat = 0;
        KeyRelease = 0;
        KeyOld = 0;
        deviceKeyValue = 0;
    }

    public static final boolean isKeyPress(int key) {
        return (KeyPress & key) != 0;
    }

    public static final boolean isKeyRepeat(int key) {
        return (KeyRepeat & key) != 0;
    }

    public static final boolean isKeyRelease(int key) {
        return (KeyRelease & key) != 0;
    }

    public static final int getKeyPress() {
        return KeyPress;
    }

    public static final int getKeyRepeat() {
        return KeyRepeat;
    }

    public static final int getKeyRelease() {
        return KeyRelease;
    }

    protected static final void keyTick() {
        KeyRepeat = KEY;
        KeyPress = (KeyOld ^ -1) & KeyRepeat;
        KEY &= KEY_R ^ -1;
        KeyOld = KeyRepeat;
        KeyRepeat = KEY;
        KeyRelease = KeyOld & (KeyRepeat ^ -1);
        KeyOld = KeyRepeat;
        KEY_R = 0;
        if (lastTrackballAvailiable) {
            if (trackballKey != 0) {
                releaseVisualKey(decodeKey(trackballKey));
                trackballKey = 0;
            }
            trackballAvailiable = true;
        }
        lastTrackballAvailiable = trackballAvailiable;
    }

    protected static final void keyPressed(int keyCode) {
        pressVisualKey(decodeKey(keyCode));
    }

    protected static final void keyReleased(int keyCode) {
        releaseVisualKey(decodeKey(keyCode));
    }

    protected static final void trackballMoved(int keyCode) {
        if (trackballAvailiable) {
            trackballAvailiable = false;
            pressVisualKey(decodeKey(keyCode));
            trackballKey = keyCode;
        }
    }

    public static final void pressVisualKey(int key) {
        KEY |= key;
    }
    
    public static final void pressKey(int key) {
        KEY |= key;
    }
    
    public static final void releaseKey(int key) {
        KEY_R |= key;
    }

    public static final void releaseVisualKey(int key) {
        KEY_R |= key;
    }

    public static final int decodeSystemKey(int keyCode) {
        switch (keyCode) {
            case 24:
                return 1;
            case 25:
                return 2;
            case MDPhone.KEY_CODE_SEARCH:
                return 3;
            default:
                return 0;
        }
    }

    private static final int decodeKey(int keyCode) {
        switch (keyCode) {
            case 4:
                return 524288;
            case 19:
                return 4;
            case 20:
                return 8;
            case 21:
                return 16;
            case 22:
                return 32;
            case 52:
                return 2;
            case 57:
                return 8388608;
            case 62:
                return 16777216;
            case 66:
                return 536870912;
            case 67:
                return 524288;
            default:
                return 0;
        }
    }

    public static int DEBUG_GET_DEVICE_KEY_VALUE() {
        return 0;
    }
}
