//
// Decompiled by FernFlower - 573ms
//
package Lib;

public class Direction {
    public static final int DOWN = 1;
    public static final int LEFT = -1;
    public static final int NONE = 0;
    public static final int RIGHT = 1;
    public static final int UP = -1;
    private int directionX;
    private int directionY;

    public Direction(int var1, int var2) {
        this.directionX = var1;
        this.directionY = var2;
    }

    public Direction(int var1, int var2, int var3, int var4) {
        if (var1 > var3) {
            this.directionX = -1;
        } else if (var1 < var3) {
            this.directionX = 1;
        } else {
            this.directionX = 0;
        }

        if (var2 > var4) {
            this.directionY = -1;
        } else if (var2 < var4) {
            this.directionY = 1;
        } else {
            this.directionY = 0;
        }

    }

    public Direction getReverse() {
        return new Direction(-this.directionX, -this.directionY);
    }

    public int getValueX(int var1) {
        return this.directionX * Math.abs(var1);
    }

    public int getValueY(int var1) {
        return this.directionY * Math.abs(var1);
    }

    public boolean sameDirectionX(int var1) {
        boolean var2;
        if (this.directionX * var1 < 0) {
            var2 = false;
        } else {
            var2 = true;
        }

        return var2;
    }

    public boolean sameDirectionY(int var1) {
        boolean var2;
        if (this.directionY * var1 < 0) {
            var2 = false;
        } else {
            var2 = true;
        }

        return var2;
    }
}

