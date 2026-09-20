package com.sega.mobile.framework.ui;

import com.sega.mobile.framework.device.MFComponent;
import com.sega.mobile.framework.utility.MFUtility;

public class MFSlideSensor implements MFComponent {
    public static final int DIRETION_DOWN = 2;
    public static final int DIRETION_LEFT = 4;
    public static final int DIRETION_RIGHT = 8;
    public static final int DIRETION_UP = 1;
    private int directionOffsetX;
    private int directionOffsetY;
    private int directionState;
    private int pX;
    private int pY;
    private int pointId;
    private int pointX;
    private int pointY;
    private int sAccuracy;
    private int sHeight;
    private int sWidth;
    private int sX;
    private int sY;
    private boolean sliding = false;

    public MFSlideSensor(int sensorX, int sensorY, int sensorW, int sensorH, int sensorAccuracy) {
        setPosition(sensorX, sensorY);
        setSize(sensorW, sensorH);
        setAccuracy(sensorAccuracy);
        reset();
    }

    public void reset() {
        this.directionState = 0;
        this.pX = -1;
        this.pY = -1;
        this.pointX = -1;
        this.pointY = -1;
        this.pointId = -1;
        this.directionOffsetX = 0;
        this.directionOffsetY = 0;
        this.sliding = false;
    }

    public final void setPosition(int x, int y) {
        this.sX = x;
        this.sY = y;
    }

    public final void setSize(int width, int height) {
        this.sWidth = width;
        this.sHeight = height;
    }

    public final void setAccuracy(int sensorAccuracy) {
        this.sAccuracy = sensorAccuracy;
        if (this.sAccuracy < 1) {
            this.sAccuracy = 1;
        }
    }

    public final boolean isSlide(int direction) {
        return (this.directionState & direction) != 0;
    }

    public int getOffsetX() {
        return this.pointX - (this.pX + (this.directionOffsetX * this.sAccuracy));
    }

    public int getOffsetY() {
        return this.pointY - (this.pY + (this.directionOffsetY * this.sAccuracy));
    }

    public boolean isSliding() {
        return this.sliding;
    }

    public void tick() {
        synchronized (this) {
            this.directionState = 0;
            if (this.pX != -1) {
                int tmpOffsetX = (this.pointX - this.pX) / this.sAccuracy;
                if (this.directionOffsetX < tmpOffsetX) {
                    this.directionState |= 8;
                } else if (this.directionOffsetX > tmpOffsetX) {
                    this.directionState |= 4;
                }
                this.directionOffsetX = tmpOffsetX;
                int tmpOffsetY = (this.pointY - this.pY) / this.sAccuracy;
                if (this.directionOffsetY < tmpOffsetY) {
                    this.directionState |= 2;
                } else if (this.directionOffsetY > tmpOffsetY) {
                    this.directionState |= 1;
                }
                this.directionOffsetY = tmpOffsetY;
            }
        }
    }

    public void pointerDragged(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == pId) {
                if (MFUtility.pointInRegion(x, y, this.sX, this.sY, this.sWidth, this.sHeight)) {
                    this.pointX = x;
                    this.pointY = y;
                } else {
                    reset();
                }
            }
        }
    }

    public void pointerPressed(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == -1) {
                if (MFUtility.pointInRegion(x, y, this.sX, this.sY, this.sWidth, this.sHeight)) {
                    this.pointId = pId;
                    this.pX = x;
                    this.pY = y;
                    this.pointX = x;
                    this.pointY = y;
                    this.sliding = true;
                }
            }
        }
    }

    public void pointerReleased(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == pId) {
                reset();
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
