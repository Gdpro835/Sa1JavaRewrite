package com.sega.engine.ext;

import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;

public class ACRoundCollision extends ACCollision {
    private int centerX;
    private int centerY;
    private int radius;

    public ACRoundCollision(ACObject acObj, int centerX2, int centerY2, int radius2) {
        super(acObj, acObj.getWorld());
        setProperty(centerX2, centerY2, radius2);
    }

    public void setProperty(int centerX2, int centerY2, int radius2) {
        this.centerX = centerX2;
        this.centerY = centerY2;
        this.radius = radius2;
    }

    public void update() {
    }

    public int getCollisionXFromLeft(int y) {
        int y2 = y - this.radius;
        int doubleY = y2 * y2;
        int doubleR = this.radius * this.radius;
        int x = -this.radius;
        while (x < 0) {
            if ((x * x) + doubleY <= doubleR) {
                return this.radius + x;
            }
            x += 1 << this.worldZoom;
        }
        return -1000;
    }

    public int getCollisionXFromRight(int y) {
        int y2 = y - this.radius;
        int doubleY = y2 * y2;
        int doubleR = this.radius * this.radius;
        int x = this.radius;
        while (x > 0) {
            if ((x * x) + doubleY <= doubleR) {
                return this.radius + x;
            }
            x -= 1 << this.worldZoom;
        }
        return -1000;
    }

    public int getCollisionYFromDown(int x) {
        int x2 = x - this.radius;
        int doubleX = x2 * x2;
        int doubleR = this.radius * this.radius;
        int y = this.radius;
        while (y > 0) {
            if ((y * y) + doubleX <= doubleR) {
                return this.radius + y;
            }
            y -= 1 << this.worldZoom;
        }
        return -1000;
    }

    public int getCollisionYFromUp(int x) {
        int x2 = x - this.radius;
        int doubleX = x2 * x2;
        int doubleR = this.radius * this.radius;
        int y = -this.radius;
        while (y < 0) {
            if ((y * y) + doubleX <= doubleR) {
                return this.radius + y;
            }
            y += 1 << this.worldZoom;
        }
        return -1000;
    }

    public int getLeftX() {
        return this.centerX - this.radius;
    }

    public int getTopY() {
        return this.centerY - this.radius;
    }

    public int getWidth() {
        return this.radius << 1;
    }

    public int getHeight() {
        return this.radius << 1;
    }
}
