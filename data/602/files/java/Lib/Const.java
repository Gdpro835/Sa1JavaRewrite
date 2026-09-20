//
// Decompiled by FernFlower - 617ms
//
package Lib;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import java.io.InputStream;

public class Const {
    public static final short FLIP_X = 8192;
    public static final short FLIP_Y = 16384;
    public static final short ROTATE_180 = 24576;
    public static final short ROTATE_270 = 4096;
    public static final short[] TRANS;

    static {
        short[] var0 = new short[]{0, 16384, 8192, 24576, 20480, 28672, 4096, 12288};
        TRANS = var0;
    }

    public static void DrawImage(MFGraphics var0, int var1, int var2, MFImage var3, int var4, int var5, int var6, int var7, int var8) {
        if (var3 != null) {
            int var12 = var3.getWidth();
            int var11 = var3.getHeight();
            if (var4 < var12 && var5 < var11) {
                int var10 = 0;
                int var9;
                if ((var8 & 4096) != 0) {
                    var9 = 0 | 6;
                    var10 = var9;
                    if ((var8 & 8192) != 0) {
                        var10 = var9 ^ 1;
                    }

                    var9 = var10;
                    if ((var8 & 16384) != 0) {
                        var9 = var10 ^ 2;
                    }
                } else {
                    if ((var8 & 8192) != 0) {
                        var10 = 0 | 2;
                    }

                    var9 = var10;
                    if ((var8 & 16384) != 0) {
                        var9 = var10 | 1;
                    }
                }

                var8 = var6;
                if (var4 + var6 > var12) {
                    var8 = var12 - var4;
                }

                var6 = var7;
                if (var5 + var7 > var11) {
                    var6 = var11 - var5;
                }

                MyAPI.drawImageWithoutZoom(var0, var3, var4, var5, var8, var6, var9, var1, var2, 0);
            }
        }

    }

    public static void DrawImage(MFGraphics var0, MFImage var1, int var2, int var3, int var4, int var5) {
        int var9 = var1.getWidth();
        int var8 = var1.getHeight();
        int var7 = 0;
        int var6;
        if ((var5 & 4096) != 0) {
            var6 = 0 | 6;
            var7 = var6;
            if ((var5 & 8192) != 0) {
                var7 = var6 ^ 1;
            }

            var6 = var7;
            if ((var5 & 16384) != 0) {
                var6 = var7 ^ 2;
            }
        } else {
            if ((var5 & 8192) != 0) {
                var7 = 0 | 2;
            }

            var6 = var7;
            if ((var5 & 16384) != 0) {
                var6 = var7 | 1;
            }
        }

        try {
            MyAPI.drawImageWithoutZoom(var0, var1, 0, 0, var9, var8, var6, var2, var3, var4);
        } catch (Exception var10) {
            var10.printStackTrace();
        }

    }

    public static int ReadInt(InputStream var0) {
        int var2 = 0;
        int[] var4 = new int[4];

        for(int var1 = 0; var1 < 4; ++var1) {
            try {
                var4[var1] = (short)var0.read();
            } catch (Exception var5) {
                var5.printStackTrace();
                break;
            }

            int var3 = var4[var1];
            var2 += var3 << var1 * 8;
        }

        return var2;
    }

    public static short ReadShort(InputStream var0) {
        short var1 = 0;
        short[] var4 = new short[2];

        try {
            var4[0] = (short)var0.read();
            var4[1] = (short)var0.read();
        } catch (Exception var5) {
            var5.printStackTrace();
            return var1;
        }

        short var3 = var4[1];
        short var2 = var4[0];
        var1 = (short)((var3 << 8) + var2);
        return var1;
    }

    boolean RectIntersect(int[] var1, int[] var2) {
        boolean var3;
        if (var2[0] < var1[0] + var1[2] && var2[0] + var2[2] > var1[0] && var2[1] < var1[1] + var1[3] && var2[1] + var2[3] > var1[1]) {
            var3 = true;
        } else {
            var3 = false;
        }

        return var3;
    }
}

