package com.sega.mobile.framework.ui;

import com.sega.mobile.framework.device.MFComponent;
import com.sega.mobile.framework.utility.MFUtility;

public class MFSliderBar implements MFComponent {
    public static final int HORIZONTAL = 1;
    public static final int VERTICAL = 0;
    private int inSliderOffset;
    private boolean onTouch;
    private int pointId;
    private int pointX;
    private int pointY;
    private int sHeight;
    private int sOrientation;
    private int sWidth;
    private int sX;
    private int sY;
    private int sliderBarLength;
    private int sliderPosition;

    public MFSliderBar(int sliderBarX, int sliderBarY, int sliderBarLength2, int sliderBarOrientation, int sliderWidth, int sliderHeight) {
        setPosition(sliderBarX, sliderBarY);
        setSize(sliderWidth, sliderHeight);
        setOrientation(sliderBarOrientation);
        setLength(sliderBarLength2);
        reset();
    }

    public void reset() {
        this.inSliderOffset = 0;
        this.onTouch = false;
        this.pointId = -1;
    }

    public final void setPosition(int x, int y) {
        this.sX = x;
        this.sY = y;
    }

    public final void setSize(int width, int height) {
        this.sWidth = width;
        this.sHeight = height;
    }

    public final void setOrientation(int sliderBarOrientation) {
        this.sOrientation = sliderBarOrientation;
    }

    public void setLength(int length) {
        this.sliderBarLength = length;
        if (this.sliderBarLength < 1) {
            this.sliderBarLength = 1;
        }
    }

    public int getLength() {
        return this.sliderBarLength;
    }

    public int getSliderPosition() {
        return this.sliderPosition;
    }

    public void setSliderPosition(int position) {
        this.sliderPosition = position;
        if (this.sliderPosition < 0) {
            this.sliderPosition = 0;
        }
        if (this.sliderPosition > this.sliderBarLength) {
            this.sliderPosition = this.sliderBarLength;
        }
    }

    public void tick() {
        synchronized (this) {
        }
    }

    public void pointerDragged(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == pId && this.onTouch) {
                if (this.sOrientation == 0) {
                    if (MFUtility.pointInRegion(x, y, this.sX - (this.sWidth >> 1), (this.sY + this.inSliderOffset) - (this.sHeight >> 1), this.sWidth, this.sHeight + this.sliderBarLength)) {
                        setSliderPosition((y - this.sY) - this.inSliderOffset);
                        this.pointX = x;
                        this.pointY = y;
                    }
                } else {
                    if (MFUtility.pointInRegion(x, y, (this.sX + this.inSliderOffset) - (this.sWidth >> 1), this.sY - (this.sHeight >> 1), this.sWidth + this.sliderBarLength, this.sHeight)) {
                        setSliderPosition((x - this.sX) - this.inSliderOffset);
                        this.pointX = x;
                        this.pointY = y;
                    }
                }
            }
        }
    }

    public void pointerPressed(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == -1) {
                if (this.sOrientation == 0) {
                    if (MFUtility.pointInRegion(x, y, this.sX - (this.sWidth >> 1), (this.sY - (this.sHeight >> 1)) + this.sliderPosition, this.sWidth, this.sHeight)) {
                        this.inSliderOffset = (y - this.sY) - this.sliderPosition;
                        this.onTouch = true;
                        this.pointId = pId;
                        this.pointX = x;
                        this.pointY = y;
                    }
                } else {
                    if (MFUtility.pointInRegion(x, y, (this.sX - (this.sWidth >> 1)) + this.sliderPosition, this.sY - (this.sHeight >> 1), this.sWidth, this.sHeight)) {
                        this.inSliderOffset = (x - this.sX) - this.sliderPosition;
                        this.onTouch = true;
                        this.pointId = pId;
                        this.pointX = x;
                        this.pointY = y;
                    }
                }
            }
        }
    }

    public void pointerReleased(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == pId) {
                this.onTouch = false;
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
