//
// Decompiled by Jadx - 1141ms
//
package GameEngine;

import Lib.MyAPI;
import Lib.crlFP32;
import com.sega.mobile.framework.device.MFComponent;
import com.sega.mobile.framework.device.MFGamePad;

public class TouchDirectKey implements MFComponent {
    private static boolean IsDragged;
    private static boolean IsPressed;
    private static boolean IsReleased;
    private static int PressedX;
    private static int PressedY;
    private int CenterX;
    private int CenterY;
    private int CurrentVKey;
    private int ExceptCircleR;
    private int NoneCircleR;
    private int OrgVKey;
    private int OutCircleR;
    private int PointDegree;
    private static int posID = -1;
    private static int B_NONE = 0x40000000;
    private static int type = -1;

    public TouchDirectKey() {
        this(-1, -1, 1, 0, -1);
    }

    public TouchDirectKey(int centerx, int centery, int outr, int noner, int type2) {
        setCenterPoint(centerx, centery);
        setCircleRadius(outr);
        setNoneCircleRadius(noner);
        type = type2;
        reset();
    }

    public final void setCenterPoint(int x, int y) {
        this.CenterX = x;
        this.CenterY = y;
    }

    public void setPressedPoint(int x, int y) {
        PressedX = MyAPI.zoomIn(x);
        PressedY = MyAPI.zoomIn(y);
    }

    public static int getPressedPointX() {
        return PressedX;
    }

    public static int getPressedPointY() {
        return PressedY;
    }

    private int pointer2Key(int x, int y) {
        int reset_x = x - this.CenterX;
        int reset_y = y - this.CenterY;
        int degree = (crlFP32.actTanDegree(reset_y, reset_x) + 360) % 360;
        if ((reset_x * reset_x) + (reset_y * reset_y) >= this.OutCircleR * this.OutCircleR) {
            return 0;
        }
        if ((reset_x * reset_x) + (reset_y * reset_y) <= this.NoneCircleR * this.NoneCircleR) {
            return B_NONE;
        }
        if (type == 0) {
            if (degree >= 235 && degree < 305) {
                return 260;
            }
            if (degree >= 305 && degree < 337) {
                return 512;
            }
            if ((degree >= 0 && degree < 22) || (degree >= 337 && degree <= 360)) {
                return 4128;
            }
            if (degree >= 22 && degree < 54) {
                return 32768;
            }
            if (degree >= 54 && degree < 126) {
                return 16392;
            }
            if (degree >= 126 && degree < 157) {
                return 8192;
            }
            if (degree < 157 || degree >= 202) {
                return (degree < 202 || degree >= 235) ? 0 : 128;
            }
            return 1040;
        }
        if (type != 1) {
            return 0;
        }
        if (degree >= 235 && degree < 305) {
            return 0x02000000;
        }
        if ((degree >= 0 && degree < 54) || (degree >= 305 && degree <= 360)) {
            return 4128;
        }
        if (degree < 54 || degree >= 126) {
            return (degree < 126 || degree >= 235) ? 0 : 1040;
        }
        return 16392;
    }

    private int getPointerDegree(int x, int y) {
        int reset_x = x - this.CenterX;
        int reset_y = y - this.CenterY;
        int degree = crlFP32.actTanDegree(reset_y, reset_x);
        return (degree + 360) % 360;
    }

    public int getDegree() {
        return this.PointDegree;
    }

    public final void setCircleRadius(int r) {
        this.OutCircleR = r;
    }

    public final void setNoneCircleRadius(int r) {
        this.NoneCircleR = r;
    }

    public final void setExceptionCircleRadius(int r) {
        this.ExceptCircleR = r;
    }

    public static boolean IsKeyDragged() {
        return IsDragged;
    }

    public static boolean IsKeyPressed() {
        return IsPressed;
    }

    public static boolean IsKeyReleased() {
        return IsReleased;
    }

    public void reset() {
        IsReleased = true;
    }

    public void tick() {
    }

    private boolean rangeChk(int x, int y) {
        return this.CenterX - this.OutCircleR < x && this.CenterX + this.OutCircleR > x && this.CenterY - this.OutCircleR < y && this.CenterY + this.OutCircleR > y;
    }

    public void pointerPressed(int num, int x, int y) {
        synchronized (this) {
            if (rangeChk(x, y)) {
                if (posID == -1) {
                    posID = num;
                }
                if (posID == num) {
                    setPressedPoint(x, y);
                    this.OrgVKey = pointer2Key(x, y);
                    this.PointDegree = getPointerDegree(x, y);
                    MFGamePad.pressVisualKey(this.OrgVKey);
                    if (this.OrgVKey != 0) {
                        IsPressed = true;
                        IsReleased = false;
                    }
                }
            }
        }
    }

    public void pointerReleased(int num, int x, int y) {
        synchronized (this) {
            if (posID == num) {
                MFGamePad.releaseVisualKey(pointer2Key(x, y));
                MFGamePad.releaseVisualKey(this.OrgVKey);
                MFGamePad.releaseVisualKey(this.CurrentVKey);
                IsPressed = false;
                IsReleased = true;
                posID = -1;
            }
        }
    }

    public void pointerDragged(int num, int x, int y) {
        synchronized (this) {
            if (rangeChk(x, y)) {
                if (posID == -1) {
                    posID = num;
                }
                if (posID == num) {
                    this.CurrentVKey = pointer2Key(x, y);
                    if (this.CurrentVKey == 0) {
                        setPressedPoint(x, y);
                        MFGamePad.releaseVisualKey(this.OrgVKey);
                        IsDragged = false;
                        IsPressed = false;
                        IsReleased = true;
                    } else if (this.CurrentVKey != this.OrgVKey) {
                        setPressedPoint(x, y);
                        MFGamePad.releaseVisualKey(this.OrgVKey);
                        this.PointDegree = getPointerDegree(x, y);
                        setPressedPoint(x, y);
                        this.OrgVKey = pointer2Key(x, y);
                        this.PointDegree = getPointerDegree(x, y);
                        MFGamePad.pressVisualKey(this.OrgVKey);
                        if (this.OrgVKey != 0) {
                            IsPressed = true;
                            IsReleased = false;
                        }
                        IsDragged = true;
                        IsReleased = false;
                    } else {
                        setPressedPoint(x, y);
                        setPressedPoint(x, y);
                        this.OrgVKey = pointer2Key(x, y);
                        this.PointDegree = getPointerDegree(x, y);
                        MFGamePad.pressVisualKey(this.OrgVKey);
                        if (this.OrgVKey != 0) {
                            IsPressed = true;
                            IsReleased = false;
                        }
                        if (this.CurrentVKey != 0) {
                            this.PointDegree = getPointerDegree(x, y);
                        }
                        IsDragged = true;
                        IsReleased = false;
                    }
                }
            } else if (posID == num) {
                MFGamePad.releaseVisualKey(pointer2Key(x, y));
                MFGamePad.releaseVisualKey(this.OrgVKey);
                MFGamePad.releaseVisualKey(this.CurrentVKey);
                IsPressed = false;
                IsReleased = true;
                posID = -1;
            }
        }
    }

    public int getPointerID() {
        return posID;
    }

    public int getPointerX() {
        return 0;
    }

    public int getPointerY() {
        return 0;
    }
}
