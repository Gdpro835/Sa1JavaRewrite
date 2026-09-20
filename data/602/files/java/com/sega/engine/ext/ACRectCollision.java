package com.sega.engine.ext;

import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;

public class ACRectCollision extends ACCollision {
    private int height;
    private int posX;
    private int posY;
    private int width;

    public ACRectCollision(ACObject acObj, int x, int y, int w, int h) {
        super(acObj, acObj.getWorld());
        setProperty(x, y, w, h);
    }

    public void setProperty(int x, int y, int w, int h) {
        this.posX = x;
        this.posY = y;
        this.width = w;
        this.height = h;
    }

    public void update() {
    }

    public int getCollisionXFromLeft(int y) {
        return 0;
    }

    public int getCollisionXFromRight(int y) {
        return this.width;
    }

    public int getCollisionYFromUp(int x) {
        return 0;
    }

    public int getCollisionYFromDown(int x) {
        return this.height;
    }

    public int getLeftX() {
        return this.posX;
    }

    public int getTopY() {
        return this.posY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
