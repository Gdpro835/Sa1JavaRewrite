//
// Decompiled by FernFlower - 776ms
//
package com.sega.engine.action;

import com.sega.engine.lib.MyAPI;

public class ACMoveCalculator {
    protected ACObject acObj;
    private int chkPointX;
    private int chkPointY;
    protected int moveDistanceX;
    protected int moveDistanceY;
    protected ACMoveCalUser user;
    protected ACWorld worldInstance;

    public ACMoveCalculator(ACObject var1, ACMoveCalUser var2) {
        this.acObj = var1;
        this.user = var2;
        this.worldInstance = var1.worldInstance;
    }

    private void checkInMap() {
        if (this.moveDistanceX == 0 && this.moveDistanceY == 0) {
            this.user.didAfterEveryMove(0, 0);
        }

        while(this.moveDistanceX != 0 || this.moveDistanceY != 0) {
            this.chkPointX = this.acObj.posX;
            this.chkPointY = this.acObj.posY;
            int var1 = this.chkPointX;
            int var2 = this.chkPointY;
            this.checkInSky();
            this.acObj.posX = this.chkPointX;
            this.acObj.posY = this.chkPointY;
            this.user.didAfterEveryMove(this.chkPointX - var1, this.chkPointY - var2);
        }

    }

    private void checkInSky() {
        boolean var1;
        if (Math.abs(this.moveDistanceX) > Math.abs(this.moveDistanceY)) {
            var1 = true;
        } else {
            var1 = false;
        }

        int var2 = this.chkPointX;
        int var3 = this.chkPointY;
        int var4;
        int var5;
        if (var1) {
            if (Math.abs(this.moveDistanceX) > this.worldInstance.getTileWidth()) {
                var4 = this.chkPointX;
                if (this.moveDistanceX > 0) {
                    var5 = 1;
                } else {
                    var5 = -1;
                }

                this.chkPointX = var4 + var5 * this.worldInstance.getTileWidth();
                this.chkPointY += this.moveDistanceY * this.worldInstance.getTileWidth() / Math.abs(this.moveDistanceX);
            } else {
                this.chkPointX += this.moveDistanceX;
                this.chkPointY += this.moveDistanceY;
            }
        } else if (Math.abs(this.moveDistanceY) > this.worldInstance.getTileHeight()) {
            var4 = this.chkPointY;
            if (this.moveDistanceY > 0) {
                var5 = 1;
            } else {
                var5 = -1;
            }

            this.chkPointY = var4 + var5 * this.worldInstance.getTileHeight();
            this.chkPointX += this.moveDistanceX * this.worldInstance.getTileHeight() / Math.abs(this.moveDistanceY);
        } else {
            this.chkPointX += this.moveDistanceX;
            this.chkPointY += this.moveDistanceY;
        }

        var5 = this.moveDistanceX;
        var4 = this.moveDistanceY;
        this.moveDistanceX -= this.chkPointX - var2;
        this.moveDistanceY -= this.chkPointY - var3;
        if (this.moveDistanceX * var5 <= 0) {
            this.moveDistanceX = 0;
        }

        if (this.moveDistanceY * var4 <= 0) {
            this.moveDistanceY = 0;
        }

    }

    public void actionLogic(int var1, int var2) {
        this.moveDistanceX = var1;
        this.moveDistanceY = var2;
        this.checkInMap();
    }

    public void stopMove() {
        this.stopMoveX();
        this.stopMoveY();
    }

    public void stopMove(int var1) {
        int var2 = (this.moveDistanceX * MyAPI.dCos(var1) + this.moveDistanceY * MyAPI.dSin(var1)) / 1;
        int var3 = (-this.moveDistanceX * MyAPI.dSin(var1) + this.moveDistanceY * MyAPI.dCos(var1)) / 1;
        if (var2 > 0) {
            this.moveDistanceX = -var3 * MyAPI.dSin(var1) / 10000;
            this.moveDistanceY = MyAPI.dCos(var1) * var3 / 10000;
        }

    }

    public void stopMoveX() {
        this.moveDistanceX = 0;
    }

    public void stopMoveY() {
        this.moveDistanceY = 0;
    }
}

