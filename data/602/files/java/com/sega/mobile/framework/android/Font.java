package com.sega.mobile.framework.android;

import android.graphics.Paint;

public class Font {
    public static final int FACE_SYSTEM = 0;
    public static final int SIZE_LARGE = 16;
    public static final int SIZE_MEDIUM = 0;
    public static final int SIZE_SMALL = 8;
    public static final int STYLE_PLAIN = 0;
    int mFontSize;
    private Paint mPaint = new Paint();

    private Font(int size) {
        this.mFontSize = size;
        this.mPaint.setTextSize((float) size);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
    }

    public static Font getFont(int face, int style, int size) {
        switch (size) {
            case 0:
                return new Font(26);
            case 16:
                return new Font(30);
            default:
                return new Font(24);
        }
    }

    public static Font getFont(int size) {
        return new Font(size);
    }

    public void setSize(int size) {
        this.mFontSize = size;
        this.mPaint.setTextSize((float) size);
    }

    public int getHeight() {
        return this.mFontSize;
    }

    public int stringWidth(String str) {
        return (int) this.mPaint.measureText(str);
    }

    public int getFontAscent() {
        return this.mPaint.getFontMetricsInt().ascent;
    }
}
