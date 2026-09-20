package com.sega.mobile.framework.device;

import com.sega.mobile.framework.android.Font;

public class MFFont {
    public static final int FACE_SYSTEM = 0;
    public static final int SIZE_LARGE = 16;
    public static final int SIZE_MEDIUM = 0;
    public static final int SIZE_SMALL = 8;
    public static final int STYLE_PLAIN = 0;
    Font mFont;

    private MFFont(int size) {
        this.mFont = Font.getFont(size);
    }

    public static MFFont getFont(int size) {
        return new MFFont(size);
    }

    public static MFFont getFont(int face, int style, int size) {
        switch (size) {
            case 0:
                return new MFFont(26);
            case 16:
                return new MFFont(30);
            default:
                return new MFFont(24);
        }
    }

    public int stringWidth(String str) {
        return this.mFont.stringWidth(str);
    }

    public int getHeight() {
        return this.mFont.getHeight();
    }
}
