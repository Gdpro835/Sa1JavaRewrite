//
// Decompiled by Jadx - 1675ms
//
package com.sega.engine.action;

public abstract class ACBlock extends ACCollision {
    public static int downSide;
    public static int leftSide;
    public static int rightSide;
    public static int upSide;
    protected int height;
    protected int posX;
    protected int posY;
    protected int width;

    public abstract int getCollisionXFromLeft(int i);

    public abstract int getCollisionXFromRight(int i);

    public abstract int getCollisionYFromDown(int i);

    public abstract int getCollisionYFromUp(int i);

    public ACBlock(ACWorld worldInstance) {
        super((ACObject) null, worldInstance);
        this.width = worldInstance.getTileWidth();
        this.height = worldInstance.getTileHeight();
        leftSide = 0;
        upSide = 0;
        rightSide = worldInstance.getTileWidth() - 1;
        downSide = worldInstance.getTileHeight() - 1;
    }

    public final void doWhileCollision(ACObject arg0, ACCollision collision, int arg1, int x, int y, int objX, int objY) {
    }

    public final void update() {
    }

    public final void setPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
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
