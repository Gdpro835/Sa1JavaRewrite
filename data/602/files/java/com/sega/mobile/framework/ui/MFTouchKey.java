package com.sega.mobile.framework.ui;

import com.sega.mobile.framework.device.MFComponent;
import com.sega.mobile.framework.device.MFGamePad;
import com.sega.mobile.framework.utility.MFUtility;
import State.State;

public class MFTouchKey implements MFComponent {
    private int mHeight;
    private int mWidth;
    private int mX;
    private int mY;
    private int pointId;
    private int pointX;
    private int pointY;
    private int vKey;

    public MFTouchKey(int x, int y, int width, int height, int visualKeyValue) {
        setPosition(x, y);
        setSize(width, height);
        setVisualKey(visualKeyValue);
        reset();
    }

    public void reset() {
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

    public final void setVisualKey(int visualKey) {
        this.vKey = visualKey;
    }

    public void tick() {
        synchronized (this) {
        }
    }

    public void pointerDragged(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == pId) {
                if (!MFUtility.pointInRegion(x, y, this.mX, this.mY, this.mWidth, this.mHeight)) {
                    MFGamePad.releaseVisualKey(this.vKey);
                    this.pointId = -1;
                    this.pointX = -1;
                    this.pointY = -1;
                } else {
                    this.pointX = x;
                    this.pointY = y;
                }
            }
        }
    }

    public void pointerPressed(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == -1) {
                if (MFUtility.pointInRegion(x, y, this.mX, this.mY, this.mWidth, this.mHeight)) {
                    MFGamePad.pressVisualKey(this.vKey);
                    State.initTouchkeyBoard();
                    this.pointId = pId;
                    this.pointX = x;
                    this.pointY = y;
                }
            }
        }
    }

    public void pointerReleased(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == pId) {
                MFGamePad.releaseVisualKey(this.vKey);
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
