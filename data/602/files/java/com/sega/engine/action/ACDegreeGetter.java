//
// Decompiled by Jadx - 728ms
//
package com.sega.engine.action;

public abstract class ACDegreeGetter {
    public abstract void getDegreeFromCollisionByPosition(DegreeReturner degreeReturner, ACCollision aCCollision, int i, int i2, int i3, int i4);

    public abstract void getDegreeFromWorldByPosition(DegreeReturner degreeReturner, int i, int i2, int i3, int i4);
    public static class DegreeReturner {
    public int degree;
    public boolean degreeCalSuccess;
    public int newX;
    public int newY;

    public void reset(int x, int y, int degree) {
        this.newX = x;
        this.newY = y;
        this.degree = degree;
        this.degreeCalSuccess = false;
    }
    }
}
