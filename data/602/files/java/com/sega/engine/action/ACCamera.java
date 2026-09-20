//
// Decompiled by Jadx - 1794ms
//
package com.sega.engine.action;

public class ACCamera {
    private static ACCamera instance;
    private ACFocusable focusObj;
    public int showHeight;
    public int showWidth;
    private ACWorld world;
    public int x;
    public int y;

    public static ACCamera getInstance() {
        if (instance == null) {
            instance = new ACCamera();
        }
        return instance;
    }

    private ACCamera() {
    }

    public void init(ACWorld world, ACFocusable focusObj, int showWidth, int showHeight) {
        setWorld(world);
        setFocusObj(focusObj);
        setShowSize(showWidth, showHeight);
    }

    public void setWorld(ACWorld world) {
        this.world = world;
    }

    public void setFocusObj(ACFocusable focusObj) {
        this.focusObj = focusObj;
    }

    public void setShowSize(int width, int height) {
        this.showWidth = width;
        this.showHeight = height;
    }

    public void logic() {
        if (this.focusObj != null && this.world != null) {
            this.x = this.focusObj.getFocusX() - (this.showWidth >> 1);
            this.y = this.focusObj.getFocusY() - (this.showHeight >> 1);
            if (this.x < 0) {
                this.x = 0;
            }
            if (this.x > this.world.getWorldWidth() - this.showWidth) {
                this.x = this.world.getWorldWidth() - this.showWidth;
            }
            if (this.y < 0) {
                this.y = 0;
            }
            if (this.y > this.world.getWorldHeight() - this.showHeight) {
                this.y = this.world.getWorldHeight() - this.showHeight;
            }
        }
    }
}
