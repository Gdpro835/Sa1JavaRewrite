package com.sega.mobile.framework.ui;

import com.sega.mobile.framework.device.MFComponent;
import com.sega.mobile.framework.utility.MFUtility;

public class MFButton implements MFComponent {
    private static final int BUTTON_PRESS = 0;
    private static final int BUTTON_RELEASE = 2;
    private static final int BUTTON_REPEAT = 1;
    private boolean[] buttonFlag;
    private boolean[] buttonTmpFlag;
    private int mHeight;
    private int mWidth;
    private int mX;
    private int mY;
    private int pointId;
    private int pointX;
    private int pointY;

    public MFButton(int x, int y, int width, int height) {
        setPosition(x, y);
        setSize(width, height);
        reset();
    }

    public void reset() {
        this.buttonFlag = new boolean[3];
        this.buttonTmpFlag = new boolean[3];
        for (int i = 0; i < 3; i++) {
            this.buttonFlag[i] = false;
            this.buttonTmpFlag[i] = false;
        }
        this.pointId = -1;
    }

    public final void setPosition(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public final int getX() {
        return this.mX;
    }

    public final int getY() {
        return this.mY;
    }

    public final int getWidth() {
        return this.mWidth;
    }

    public final int getHeight() {
        return this.mHeight;
    }

    public final void setSize(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public final boolean isPress() {
        boolean z;
        synchronized (this) {
            z = this.buttonFlag[0];
        }
        return z;
    }

    public final boolean isRepeat() {
        boolean z;
        synchronized (this) {
            z = this.buttonFlag[1];
        }
        return z;
    }

    public final boolean isRelease() {
        boolean z;
        synchronized (this) {
            z = this.buttonFlag[2];
        }
        return z;
    }

    public void tick() {
        synchronized (this) {
            for (int i = 0; i < this.buttonFlag.length; i++) {
                if (this.buttonFlag[i]) {
                    this.buttonFlag[i] = false;
                }
                if (this.buttonTmpFlag[i]) {
                    this.buttonFlag[i] = true;
                    if (i != 1) {
                        this.buttonTmpFlag[i] = false;
                    }
                }
            }
        }
    }

    public void pointerDragged(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == -1 || this.pointId == pId) {
                if (MFUtility.pointInRegion(x, y, this.mX, this.mY, this.mWidth, this.mHeight)) {
                    if (this.pointId == pId && !this.buttonTmpFlag[1]) {
                        this.buttonTmpFlag[0] = true;
                        this.buttonTmpFlag[1] = true;
                    }
                    this.pointX = x;
                    this.pointY = y;
                } else {
                    this.buttonTmpFlag[1] = false;
                    this.pointId = -1;
                }
            }
        }
    }

    public void pointerPressed(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == -1) {
                if (MFUtility.pointInRegion(x, y, this.mX, this.mY, this.mWidth, this.mHeight)) {
                    this.buttonTmpFlag[0] = true;
                    this.buttonTmpFlag[1] = true;
                    this.pointX = x;
                    this.pointY = y;
                }
                this.pointId = pId;
            }
        }
    }

    public void pointerReleased(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == pId) {
                if (this.buttonTmpFlag[1]) {
                    if (MFUtility.pointInRegion(x, y, this.mX, this.mY, this.mWidth, this.mHeight)) {
                        this.buttonTmpFlag[2] = true;
                    }
                }
                this.buttonTmpFlag[1] = false;
                this.pointId = -1;
                this.pointX = -1;
                this.pointY = -1;
            }
        }
    }

    public int getPointerID() {
        return this.pointId;
    }

    public int getPointerX() {
        return this.pointX;
    }

    public int getPointerY() {
        return this.pointY;
    }
}
