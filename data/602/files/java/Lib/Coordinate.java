//
// Decompiled by FernFlower - 925ms
//
package Lib;

public class Coordinate {
    private static Coordinate returnInstance;
    public int x;
    public int y;

    public Coordinate() {
        this.setValue(0, 0);
    }

    public Coordinate(int var1, int var2) {
        this.setValue(var1, var2);
    }

    public static Coordinate returnCoordinate(int var0, int var1) {
        if (returnInstance == null) {
            returnInstance = new Coordinate(var0, var1);
        } else {
            returnInstance.setValue(var0, var1);
        }

        return returnInstance;
    }

    public boolean equals(Object var1) {
        boolean var2;
        if (var1 instanceof Coordinate && this.x == ((Coordinate)var1).x && this.y == ((Coordinate)var1).y) {
            var2 = true;
        } else {
            var2 = false;
        }

        return var2;
    }

    public boolean inTheArea(Coordinate[] var1) {
        int var2 = 0;

        boolean var3;
        while(true) {
            if (var2 >= var1.length) {
                var3 = false;
                break;
            }

            if (this.equals(var1[var2])) {
                var3 = true;
                break;
            }

            ++var2;
        }

        return var3;
    }

    public void setValue(int var1, int var2) {
        this.x = var1;
        this.y = var2;
    }
}

