//
// Decompiled by FernFlower - 539ms
//
package Lib;

import java.util.Random;

public class MyRandom {
    private static Random rnd;

    private static void checkInstance() {
        if (rnd == null) {
            rnd = new Random(System.currentTimeMillis());
        }

    }

    public static int nextInt() {
        checkInstance();
        return rnd.nextInt();
    }

    public static int nextInt(int var0) {
        checkInstance();
        return Math.abs(rnd.nextInt()) % var0;
    }

    public static int nextInt(int var0, int var1) {
        checkInstance();
        return Math.abs(rnd.nextInt()) % (Math.abs(var1 - var0) + 1) + Math.min(var0, var1);
    }
}

