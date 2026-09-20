//
// Decompiled by FernFlower - 650ms
//
package com.sega.engine.action;

import java.util.Vector;

public abstract class ACObject implements ACParam {
    private Vector collisionVec;
    protected int height;
    public int posX;
    public int posY;
    protected int posZ;
    public int velX;
    public int velY;
    protected int width;
    protected ACWorld worldInstance;
    protected final int worldZoom;

    public ACObject(ACWorld var1) {
        this.worldInstance = var1;
        this.worldZoom = var1.getZoom();
        this.collisionVec = new Vector();
    }

    public void addCollision(ACCollision var1) {
        this.collisionVec.addElement(var1);
    }

    public abstract void doBeforeCollisionCheck();

    public void doCheckCollisionWithObj(ACObject var1, int var2, int var3) {
        for(int var4 = 0; var4 < this.collisionVec.size(); ++var4) {
            ACCollision var7 = (ACCollision)this.collisionVec.elementAt(var4);
            Vector var6 = var1.getCollisionVec();

            for(int var5 = 0; var5 < var6.size(); ++var5) {
                ACCollision var8 = (ACCollision)var6.elementAt(var5);
                var7.doCheckCollisionWithCollision(var8, var2, var3);
            }
        }

    }

    public abstract void doWhileCollision(ACObject var1, ACCollision var2, int var3, int var4, int var5, int var6, int var7);

    public Vector getCollisionVec() {
        return this.collisionVec;
    }

    public int getObjHeight() {
        return this.height;
    }

    public int getObjWidth() {
        return this.width;
    }

    public ACWorld getWorld() {
        return this.worldInstance;
    }

    public int getX() {
        return this.posX;
    }

    public int getY() {
        return this.posY;
    }

    public void removeCollision(int var1) {
        if (var1 >= 0 && var1 < this.collisionVec.size()) {
            this.collisionVec.removeElementAt(var1);
        }

    }

    public void setPosition(int var1, int var2) {
        this.posX = var1;
        this.posY = var2;
    }

    public void setRect(int var1, int var2) {
        this.width = var1;
        this.height = var2;
    }
}

