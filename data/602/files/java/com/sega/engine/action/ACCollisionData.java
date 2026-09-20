//
// Decompiled by Jadx - 677ms
//
package com.sega.engine.action;

class ACCollisionData implements ACParam {
    public int chkPointID;
    public int collisionX;
    public int collisionY;
    public int newPosX;
    public int newPosY;
    public ACBlock reBlock;

    public ACCollisionData() {
        reset();
    }

    public void reset() {
        this.newPosX = -1000;
        this.newPosY = -1000;
        this.reBlock = null;
    }

    public boolean isNoCollision() {
        return this.newPosX == -1000 && this.newPosY == -1000;
    }
}
