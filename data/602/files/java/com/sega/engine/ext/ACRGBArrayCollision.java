package com.sega.engine.ext;

import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;

public class ACRGBArrayCollision extends ACCollision {
    private int height;
    private int posX;
    private int posY;
    private int[] rgbInfo;
    private int width;

    public ACRGBArrayCollision(ACObject acObj, int[] rgb, int x, int y, int width2, int height2) {
        super(acObj, acObj.getWorld());
        setProperty(rgb, x, y, width2, height2);
    }

    public void setProperty(int[] rgb, int x, int y, int width2, int height2) {
        this.rgbInfo = rgb;
        this.posX = x;
        this.posY = y;
        this.width = width2;
        this.height = height2;
    }

    public void update() {
    }

    public int getCollisionXFromRight(int y) {
        int x = this.width - (1 << this.worldZoom);
        while (x >= 0) {
            if ((-16777216 & this.rgbInfo[(x >> this.worldZoom) + ((y >> this.worldZoom) * (this.width >> this.worldZoom))]) != 0) {
                return x;
            }
            x -= 1 << this.worldZoom;
        }
        return -1000;
    }

    public int getCollisionXFromLeft(int y) {
        int x = 0;
        while (x < this.width) {
            if ((-16777216 & this.rgbInfo[(x >> this.worldZoom) + ((y >> this.worldZoom) * (this.width >> this.worldZoom))]) != 0) {
                return x;
            }
            x += 1 << this.worldZoom;
        }
        return -1000;
    }

    public int getCollisionYFromUp(int x) {
        int y = 0;
        while (y < this.height) {
            if ((-16777216 & this.rgbInfo[(x >> this.worldZoom) + ((y >> this.worldZoom) * (this.width >> this.worldZoom))]) != 0) {
                return y;
            }
            y += 1 << this.worldZoom;
        }
        return -1000;
    }

    public int getCollisionYFromDown(int x) {
        int y = this.height - (1 << this.worldZoom);
        while (y >= 0) {
            if ((-16777216 & this.rgbInfo[(x >> this.worldZoom) + ((y >> this.worldZoom) * (this.width >> this.worldZoom))]) != 0) {
                return y;
            }
            y -= 1 << this.worldZoom;
        }
        return -1000;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getLeftX() {
        return this.posX;
    }

    public int getTopY() {
        return this.posY;
    }
}
