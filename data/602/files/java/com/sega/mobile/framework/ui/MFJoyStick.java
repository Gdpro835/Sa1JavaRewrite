package com.sega.mobile.framework.ui;

import com.sega.mobile.framework.device.MFComponent;
import com.sega.mobile.framework.device.MFGamePad;
import com.sega.mobile.framework.utility.MFUtility;
import State.State;

public class MFJoyStick implements MFComponent {
    private static final int[][] DIRECTIONS = {new int[]{MFGamePad.KEY_UP, 4388, 4388, MFGamePad.KEY_RIGHT, MFGamePad.KEY_RIGHT, 20520, 20520, MFGamePad.KEY_DOWN, MFGamePad.KEY_DOWN, 17432, 17432, MFGamePad.KEY_LEFT, MFGamePad.KEY_LEFT, 1300, 1300, MFGamePad.KEY_UP}, new int[]{MFGamePad.KEY_UP, 512, 512, MFGamePad.KEY_RIGHT, MFGamePad.KEY_RIGHT, 32768, 32768, MFGamePad.KEY_DOWN, MFGamePad.KEY_DOWN, 8192, 8192, MFGamePad.KEY_LEFT, MFGamePad.KEY_LEFT, 128, 128, MFGamePad.KEY_UP}};
    private static final int[] KEY_FOR_RELEASE = {21820, 63420};
    public static final int TYPE_4_DIRECTION = 0;
    public static final int TYPE_8_DIRECTION = 1;
    private int cX;
    private int cY;
    private int keyIndex;
    private int lastKeyIndex;
    private int pR;
    private int pointId;
    private int pointX;
    private int pointY;
    private int rSize = (this.pR * 2);
    private int rX = (this.cX - this.pR);
    private int rY = (this.cY - this.pR);
    private int sMs = (this.pR / 10);
    private int sR;
    private int sRs = (this.sR * this.sR);
    private int sType;
    private int sX;
    private int sY;

    public MFJoyStick(int x, int y, int padRadius, int stickRaidus, int type) {
        this.cX = x;
        this.cY = y;
        this.pR = padRadius;
        this.sR = stickRaidus;
        if (this.sMs < 10) {
            this.sMs = 10;
        }
        this.sMs *= this.sMs;
        this.sType = type;
        this.lastKeyIndex = -1;
        this.sX = 0;
        this.sY = 0;
    }

    private void stickMoved(int pId, int x, int y) {
        int i;
        int i2;
        int i3;
        int i4;
        if (MFUtility.pointInRegion(x, y, this.rX, this.rY, this.rSize, this.rSize)) {
            int tmpX = x - this.cX;
            int tmpY = y - this.cY;
            int powXY = (tmpX * tmpX) + (tmpY * tmpY);
            if (powXY < this.sMs) {
                this.sX = 0;
                this.sY = 0;
            } else if (powXY <= this.sRs) {
                this.sX = tmpX;
                this.sY = tmpY;
            } else {
                if (tmpX <= 0) {
                    tmpX = -tmpX;
                }
                if (tmpY <= 0) {
                    tmpY = -tmpY;
                }
                if (tmpX <= tmpY) {
                    if (tmpY != 0) {
                        this.sY = 0;
                        while (this.sY < tmpY) {
                            this.sX = (this.sY * tmpX) / tmpY;
                            if ((this.sX * this.sX) + (this.sY * this.sY) >= this.sRs) {
                                break;
                            }
                            this.sY++;
                        }
                    } else {
                        this.sY = 0;
                        this.sX = tmpX > this.sR ? this.sR : tmpX;
                    }
                } else if (tmpX != 0) {
                    this.sX = 0;
                    while (this.sX < tmpX) {
                        this.sY = (this.sX * tmpY) / tmpX;
                        if ((this.sX * this.sX) + (this.sY * this.sY) >= this.sRs) {
                            break;
                        }
                        this.sX++;
                    }
                } else {
                    this.sX = 0;
                    if (tmpY > this.sR) {
                        i2 = this.sR;
                    } else {
                        i2 = tmpY;
                    }
                    this.sY = i2;
                }
                this.sX = x - this.cX > 0 ? this.sX : -this.sX;
                if (y - this.cY > 0) {
                    i = this.sY;
                } else {
                    i = -this.sY;
                }
                this.sY = i;
            }
            if (this.sX == 0 && this.sY == 0) {
                reset();
            } else {
                int tan = this.sY == 0 ? 99 : (this.sX * 10) / this.sY;
                if (tan < 0) {
                    tan = -tan;
                }
                if (tan < 4) {
                    if (this.sY < 0) {
                        if (this.sX > 0) {
                            this.keyIndex = 0;
                        } else {
                            this.keyIndex = 15;
                        }
                    } else if (this.sX > 0) {
                        this.keyIndex = 7;
                    } else {
                        this.keyIndex = 8;
                    }
                } else if (tan < 10) {
                    if (this.sY < 0) {
                        if (this.sX > 0) {
                            this.keyIndex = 1;
                        } else {
                            this.keyIndex = 14;
                        }
                    } else if (this.sX > 0) {
                        this.keyIndex = 6;
                    } else {
                        this.keyIndex = 9;
                    }
                } else if (tan < 24) {
                    if (this.sY < 0) {
                        if (this.sX > 0) {
                            this.keyIndex = 2;
                        } else {
                            this.keyIndex = 13;
                        }
                    } else if (this.sX > 0) {
                        this.keyIndex = 5;
                    } else {
                        this.keyIndex = 10;
                    }
                } else if (this.sY < 0) {
                    if (this.sX > 0) {
                        this.keyIndex = 3;
                    } else {
                        this.keyIndex = 12;
                    }
                } else if (this.sX > 0) {
                    this.keyIndex = 4;
                } else {
                    this.keyIndex = 11;
                }
                if (this.lastKeyIndex != this.keyIndex) {
                    int i5 = DIRECTIONS[this.sType][this.keyIndex];
                    if (this.lastKeyIndex == -1) {
                        i3 = 0;
                    } else {
                        i3 = DIRECTIONS[this.sType][this.lastKeyIndex];
                    }
                    MFGamePad.pressVisualKey(i5 & (i3 ^ -1));
                    State.initTouchkeyBoard();
                    if (this.lastKeyIndex == -1) {
                        i4 = 0;
                    } else {
                        i4 = DIRECTIONS[this.sType][this.lastKeyIndex] & (DIRECTIONS[this.sType][this.keyIndex] ^ -1);
                    }
                    MFGamePad.releaseVisualKey(i4);
                    this.lastKeyIndex = this.keyIndex;
                }
            }
            this.pointId = pId;
            this.pointX = x;
            this.pointY = y;
            return;
        }
        outOfRange();
    }

    private void outOfRange() {
        MFGamePad.releaseVisualKey(KEY_FOR_RELEASE[this.sType]);
        this.lastKeyIndex = -1;
        this.sX = 0;
        this.sY = 0;
    }

    public int getStickPosX() {
        return this.sX;
    }

    public int getStickPosY() {
        return this.sY;
    }

    public void reset() {
        outOfRange();
        this.pointId = -1;
    }

    public void tick() {
    }

    public void pointerDragged(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == pId) {
                stickMoved(pId, x, y);
            }
        }
    }

    public void pointerPressed(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == -1) {
                stickMoved(pId, x, y);
            }
        }
    }

    public void pointerReleased(int pId, int x, int y) {
        synchronized (this) {
            if (this.pointId == pId) {
                reset();
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

    public final void setPosition(int x, int y) {
        this.cX = x;
        this.cY = y;
        this.rX = this.cX - this.pR;
        this.rY = this.cY - this.pR;
        this.lastKeyIndex = -1;
        this.sX = 0;
        this.sY = 0;
    }

    public final int getX() {
        return this.cX;
    }

    public final int getY() {
        return this.cY;
    }
}
