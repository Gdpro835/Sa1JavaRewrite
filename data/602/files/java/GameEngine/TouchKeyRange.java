//
// Decompiled by Jadx - 884ms
//
package GameEngine;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFComponent;

public class TouchKeyRange implements MFComponent {
    private int press_x;
    private int press_y;
    public int range_h;
    public int range_w;
    public int range_x;
    public int range_y;
    public boolean result = false;
    private boolean IsDrag = false;
    private boolean IsIn = false;
    private int keyState = 0;

    public TouchKeyRange(int x, int y, int w, int h) {
        this.range_x = MyAPI.zoomOut(x);
        this.range_y = MyAPI.zoomOut(y);
        this.range_w = MyAPI.zoomOut(w);
        this.range_h = MyAPI.zoomOut(h);
        reset();
    }

    public void setStartX(int x) {
        this.range_x = MyAPI.zoomOut(x);
    }

    public void setStartY(int y) {
        this.range_y = MyAPI.zoomOut(y);
    }

    public void tick() {
        synchronized (this) {
        }
    }

    public void reset() {
        this.result = false;
        this.IsDrag = false;
        this.keyState = 0;
    }

    public boolean Isin() {
        return this.result;
    }

    public boolean IsinRange() {
        return this.IsIn;
    }

    public boolean IsClick() {
        return this.result && !this.IsDrag;
    }

    public boolean IsButtonPress() {
        if (this.keyState != 2) {
            return false;
        }
        this.keyState = 0;
        return true;
    }

    public void resetKeyState() {
        this.keyState = 0;
        reset();
    }

    public void pointerPressed(int num, int x, int y) {
        synchronized (this) {
            if (x - this.range_x > 0 && y - this.range_y > 0 && this.range_x + this.range_w > x && this.range_y + this.range_h > y) {
                this.press_x = x;
                this.press_y = y;
                this.result = true;
                if (this.keyState == 0) {
                    this.keyState = 1;
                }
                this.IsIn = true;
            } else {
                this.result = false;
                this.IsIn = false;
            }
            this.IsDrag = false;
        }
    }

    public void pointerReleased(int num, int x, int y) {
        synchronized (this) {
            if (x - this.range_x > 0 && y - this.range_y > 0 && this.range_x + this.range_w > x && this.range_y + this.range_h > y) {
                this.result = false;
                if (this.keyState == 1) {
                    this.keyState = 2;
                }
            }
        }
    }

    public void pointerDragged(int num, int x, int y) {
        synchronized (this) {
            if (x - this.range_x > 0 && y - this.range_y > 0 && this.range_x + this.range_w > x && this.range_y + this.range_h > y) {
                this.result = true;
                if (x - this.press_x > 2 || x - this.press_x < -2 || y - this.press_y > 2 || y - this.press_y < -2) {
                    this.IsDrag = true;
                } else {
                    this.IsDrag = false;
                }
                this.IsIn = true;
                if (this.keyState == 0) {
                    this.keyState = 1;
                }
            } else {
                this.result = false;
                this.IsDrag = false;
                this.IsIn = false;
            }
        }
    }

    public int getPointerID() {
        return 0;
    }

    public int getPointerX() {
        return 0;
    }

    public int getPointerY() {
        return 0;
    }
}
