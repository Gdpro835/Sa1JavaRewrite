//
// Decompiled by FernFlower - 588ms
//
package com.sega.engine.action;

import com.sega.engine.lib.MyAPI;

public class ACUtilities {
    public static int getNewXFromDegree(int var0, int var1) {
        return MyAPI.dCos(var1) * var0 / 100;
    }

    public static int getNewXFromDegree(int var0, int var1, int var2) {
        var0 = getTotalFromDegree(var0, var1, var2);
        return MyAPI.dCos(var2) * var0 / 100;
    }

    public static int getNewYFromDegree(int var0, int var1) {
        return MyAPI.dSin(var1) * var0 / 100;
    }

    public static int getNewYFromDegree(int var0, int var1, int var2) {
        var0 = getTotalFromDegree(var0, var1, var2);
        return MyAPI.dSin(var2) * var0 / 100;
    }

    public static int getQuaParam(int var0, int var1) {
        if (var0 > 0) {
            var0 /= var1;
        } else {
            var0 = (var0 - (var1 - 1)) / var1;
        }

        return var0;
    }

    public static int getRelativePointX(int var0, int var1, int var2, int var3) {
        var1 = MyAPI.dCos(var3) * var1 / 100;
        var2 = MyAPI.dSin(var3) * var2 / 100;
        return var1 + var0 - var2;
    }

    public static int getRelativePointY(int var0, int var1, int var2, int var3) {
        var1 = MyAPI.dSin(var3) * var1 / 100;
        var2 = MyAPI.dCos(var3) * var2 / 100;
        return var1 + var0 + var2;
    }

    public static int getTotalFromDegree(int var0, int var1, int var2) {
        int var3 = MyAPI.dCos(var2);
        var2 = MyAPI.dSin(var2);
        var0 = (var3 * var0 + var2 * var1) / 100;
        return var0;
    }
}

