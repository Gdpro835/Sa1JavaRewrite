package com.sega.engine.lib;

public class Line {
    private static final int[] DIVISOR = {2, 3, 5, 7, 11, 13, 17, 19, 23};
    private static Line line1 = new Line();
    private static Line line2 = new Line();
    public int A;
    public int B;
    public int C;

    public Line(int x0, int y0, int x1, int y1) {
        setProperty(x0, y0, x1, y1);
    }

    public Line(int A2, int B2, int C2) {
        this.A = A2;
        this.B = B2;
        this.C = C2;
    }

    public Line() {
    }

    public void setProperty(int x0, int y0, int x1, int y1) {
        this.A = y1 - y0;
        this.B = x0 - x1;
        this.C = (this.A * x0) + (this.B * y0);
        dealDivisor();
    }

    public int getX(int y) {
        if (isHorizontal()) {
            return 0;
        }
        return (this.C - (this.B * y)) / this.A;
    }

    public int getY(int x) {
        if (isVertical()) {
            return 0;
        }
        return (this.C - (this.A * x)) / this.B;
    }

    public boolean isHorizontal() {
        if (this.A == 0) {
            return true;
        }
        return false;
    }

    public boolean isVertical() {
        if (this.B == 0) {
            return true;
        }
        return false;
    }

    public boolean isLegal() {
        if (!isHorizontal() || !isVertical()) {
            return true;
        }
        return false;
    }

    private void dealDivisor() {
        if (this.A != 0 || this.B != 0) {
            for (int i = 0; i < DIVISOR.length; i++) {
                int d = DIVISOR[i];
                while (this.A % d == 0 && this.B % d == 0 && this.C % d == 0) {
                    this.A /= d;
                    this.B /= d;
                    this.C /= d;
                }
            }
        }
    }

    public void getCrossPoint(CrossPoint re, int x0, int y0, int x1, int y1) {
        re.reset();
        line2.setProperty(x0, y0, x1, y1);
        getCrossPoint(re, line2);
        if (!re.hasPoint) {
            return;
        }
        if (re.x < Math.min(x0, x1) || re.x > Math.max(x0, x1) || re.y < Math.min(y0, y1) || re.y > Math.max(y0, y1)) {
            re.hasPoint = false;
        }
    }

    private void getCrossPoint(CrossPoint re, Line l) {
        re.reset();
        if (l != null && l.isLegal()) {
            if (isHorizontal() && l.isHorizontal()) {
                return;
            }
            if ((!isVertical() || !l.isVertical()) && (this.A * l.B) - (this.B * l.A) != 0) {
                int x = ((this.C * l.B) - (l.C * this.B)) / ((this.A * l.B) - (this.B * l.A));
                re.hasPoint = true;
                re.x = x;
                re.y = ((l.C * this.A) - (this.C * l.A)) / ((this.A * l.B) - (this.B * l.A));
            }
        }
    }

    public static void getCrossPoint(CrossPoint re, int x0, int y0, int x1, int y1, int x2, int y2, int x3, int y3) {
        re.reset();
        line1.setProperty(x0, y0, x1, y1);
        line2.setProperty(x2, y2, x3, y3);
        line1.getCrossPoint(re, line2);
        if (!re.hasPoint) {
            return;
        }
        if (re.x < Math.min(x0, x1) || re.x > Math.max(x0, x1) || re.x < Math.min(x2, x3) || re.x > Math.max(x2, x3) || re.y < Math.min(y0, y1) || re.y > Math.max(y0, y1) || re.y < Math.min(y2, y3) || re.y > Math.max(y2, y3)) {
            re.hasPoint = false;
        }
    }

    public Line getPlumbLine() {
        return new Line(-this.B, this.A, 0);
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
