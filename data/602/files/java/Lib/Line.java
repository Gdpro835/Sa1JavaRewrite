//
// Decompiled by FernFlower - 945ms
//
package Lib;

public class Line {
    private static final int[] DIVISOR = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23};
    private static Line line1 = new Line();
    private static Line line2 = new Line();
    public int A;
    public int B;
    public int C;

    public Line() {
    }

    public Line(int var1, int var2, int var3) {
        this.A = var1;
        this.B = var2;
        this.C = var3;
    }

    public Line(int var1, int var2, int var3, int var4) {
        this.setProperty(var1, var2, var3, var4);
    }

    private void dealDivisor() {
        if (this.A != 0 || this.B != 0) {
            for(int var1 = 0; var1 < DIVISOR.length; ++var1) {
                for(int var2 = DIVISOR[var1]; this.A % var2 == 0 && this.B % var2 == 0 && this.C % var2 == 0; this.C /= var2) {
                    this.A /= var2;
                    this.B /= var2;
                }
            }
        }

    }

    public static void getCrossPoint(CrossPoint var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        var0.reset();
        line1.setProperty(var1, var2, var3, var4);
        line2.setProperty(var5, var6, var7, var8);
        line1.getCrossPoint(var0, line2);
        if (var0.hasPoint && (var0.x < Math.min(var1, var3) || var0.x > Math.max(var1, var3) || var0.x < Math.min(var5, var7) || var0.x > Math.max(var5, var7) || var0.y < Math.min(var2, var4) || var0.y > Math.max(var2, var4) || var0.y < Math.min(var6, var8) || var0.y > Math.max(var6, var8))) {
            var0.hasPoint = false;
        }

    }

    private void getCrossPoint(CrossPoint var1, Line var2) {
        var1.reset();
        if (var2 != null && var2.isLegal() && (!this.isHorizontal() || !var2.isHorizontal()) && (!this.isVertical() || !var2.isVertical()) && this.A * var2.B - this.B * var2.A != 0) {
            int var3 = (this.C * var2.B - var2.C * this.B) / (this.A * var2.B - this.B * var2.A);
            int var4 = (var2.C * this.A - this.C * var2.A) / (this.A * var2.B - this.B * var2.A);
            var1.hasPoint = true;
            var1.x = var3;
            var1.y = var4;
        }

    }

    public int cos(int var1) {
        byte var2 = 0;

        try {
            var1 = Math.abs((this.B * var1 << 3) / crlFP32.sqrt(this.A * this.A + this.B * this.B));
        } catch (Exception var4) {
            var4.toString();
            var1 = var2;
        }

        return var1;
    }

    public boolean directRatio() {
        boolean var1;
        if (this.B * this.A < 0) {
            var1 = true;
        } else {
            var1 = false;
        }

        return var1;
    }

    public void getCrossPoint(CrossPoint var1, int var2, int var3, int var4, int var5) {
        var1.reset();
        line2.setProperty(var2, var3, var4, var5);
        this.getCrossPoint(var1, line2);
        if (var1.hasPoint && (var1.x < Math.min(var2, var4) || var1.x > Math.max(var2, var4) || var1.y < Math.min(var3, var5) || var1.y > Math.max(var3, var5))) {
            var1.hasPoint = false;
        }

    }

    public Direction getOneDirection() {
        Direction var1;
        if (this.isHorizontal()) {
            var1 = new Direction(-1, 0);
        } else if (this.isVertical()) {
            var1 = new Direction(0, -1);
        } else if (this.directRatio()) {
            var1 = new Direction(1, 1);
        } else if (!this.directRatio()) {
            var1 = new Direction(-1, 1);
        } else {
            var1 = new Direction(0, 0);
        }

        return var1;
    }

    public Line getPlumbLine() {
        return new Line(-this.B, this.A, 0);
    }

    public int getX(int var1) {
        if (this.isHorizontal()) {
            var1 = 0;
        } else {
            var1 = (this.C - this.B * var1) / this.A;
        }

        return var1;
    }

    public int getY(int var1) {
        if (this.isVertical()) {
            var1 = 0;
        } else {
            var1 = (this.C - this.A * var1) / this.B;
        }

        return var1;
    }

    public boolean isHorizontal() {
        boolean var1;
        if (this.A == 0) {
            var1 = true;
        } else {
            var1 = false;
        }

        return var1;
    }

    public boolean isLegal() {
        boolean var1;
        if (this.isHorizontal() && this.isVertical()) {
            var1 = false;
        } else {
            var1 = true;
        }

        return var1;
    }

    public boolean isVertical() {
        boolean var1;
        if (this.B == 0) {
            var1 = true;
        } else {
            var1 = false;
        }

        return var1;
    }

    public void setProperty(int var1, int var2, int var3, int var4) {
        this.A = var4 - var2;
        this.B = var1 - var3;
        this.C = this.A * var1 + this.B * var2;
        this.dealDivisor();
    }

    public int sin(int var1) {
        byte var2 = 0;

        try {
            var1 = Math.abs((this.A * var1 << 3) / crlFP32.sqrt(this.A * this.A + this.B * this.B));
        } catch (Exception var4) {
            var4.toString();
            var1 = var2;
        }

        return var1;
    }
    public static class CrossPoint {
    public boolean hasPoint;
    public int x;
    public int y;

    public void reset() {
        this.hasPoint = false;
    }
    }
}

