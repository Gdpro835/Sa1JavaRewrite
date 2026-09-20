//
// Decompiled by FernFlower - 1744ms
//
package Lib;

import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import SonicGBA.GlobalResource;
import java.util.Vector;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;

public class MyAPI implements Def {
    private static final int[] ARROW;
    public static final int BMF_COLOR_GRAY = 3;
    public static final int BMF_COLOR_GREEN = 2;
    public static final int BMF_COLOR_WHITE = 0;
    public static final int BMF_COLOR_YELLOW = 1;
    public static final int FIXED_TWO_BASE = 7;
    private static final int FLIP_X = 2;
    private static final int FLIP_Y = 4;
    private static final int[] GRAY_PALETTE_PARAM;
    private static final int[] GREEN_PALETTE_PARAM;
    public static final boolean NOKIA_DRAW = false;
    private static final int[][] OFFSET;
    private static final int[][] OFFSET2;
    private static final int PAGE_WAIT = 5;
    private static final int RAY_HEIGHT = 3;
    private static final int RAY_WIDTH = 288;
    public static final boolean RGB_DRAW = false;
    private static final int ROTATE_90 = 1;
    private static final char[] Symbol;
    private static final char[] Symbol_CH = new char[]{'。', '，', '！', ':', '；'};
    private static final char[] Symbol_EN = new char[]{'.', ',', '!', ':', ';'};
    private static final short[] TRANMODIF;
    private static final byte[] TRANMODIF_2;
    private static final int[] YELLOW_PALETTE_PARAM;
    public static final int ZOOM_OUT_MOVE = 0;
    public static String anchor;
    public static int backColor = 0;
    public static BitmapFont bmFont;
    public static BitmapFont bmFontGray;
    public static BitmapFont bmFontGreen;
    public static BitmapFont bmFontYellow;
    public static int borderColor = 0;
    private static BitmapFont currentBmFont;
    public static boolean downPermit;
    private static int[] rayRGB;
    public static int scrollPageWait;
    public static final int[] sinData2;
    public static int stringCursol;
    public static boolean upPermit;

    static {
        Symbol = Symbol_CH;
        int[] var0 = new int[]{0, 224, 446, 669, 893, 1116, 1337, 1560, 1781, 2001, 2222, 2442, 2661, 2878, 3096, 3312, 3527, 3742, 3955, 4167, 4377, 4587, 4794, 5000, 5205, 5409, 5611, 5811, 6009, 6205, 6400, 6592, 6782, 6970, 7157, 7342, 7523, 7703, 7879, 8055, 8227, 8396, 8564, 8729, 8890, 9050, 9207, 9360, 9511, 9660, 9804, 9946, 10086, 10222, 10355, 10484, 10611, 10735, 10854, 10972, 11084, 11194, 11301, 11404, 11504, 11600, 11692, 11782, 11868, 11950, 12028, 12102, 12172, 12240, 12304, 12363, 12419, 12472, 12519, 12564, 12605, 12642, 12675, 12704, 12729, 12751, 12769, 12782, 12792, 12797, 12800};
        sinData2 = var0;
        stringCursol = 0;
        anchor = "";
        ARROW = new int[]{1, 3, 5, 7, 7};
        int[] var3 = new int[]{-1, 0};
        int[] var1 = new int[]{0, 1};
        int[] var2 = new int[]{1, 0};
        var0 = new int[]{0, -1};
        OFFSET = new int[][]{var3, var1, var2, var0};
        var1 = new int[]{-1, 0};
        var0 = new int[]{0, 1};
        var2 = new int[]{1, 0};
        var3 = new int[]{0, -1};
        OFFSET2 = new int[][]{var1, var0, var2, var3, {-1, 1}, {1, 1}, {1, -1}, {-1, -1}};
        var0 = new int[]{16777215, 16763904, 16776960, 0};
        YELLOW_PALETTE_PARAM = var0;
        var0 = new int[]{16777215, 776448, 65280, 0};
        GREEN_PALETTE_PARAM = var0;
        GRAY_PALETTE_PARAM = new int[]{16711935, 15263976, 15263976, 14211288};
        short[] var4 = new short[]{0, 90, 8192, 16474, 16384, 16654, 180, 270};
        TRANMODIF = var4;
        byte[] var5 = new byte[]{0, 4, 2, 6, 3, 7, 1, 5};
        TRANMODIF_2 = var5;
        rayRGB = new int[864];
    }

    public static void FillQua(MFGraphics var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        var0.fillTriangle(var1, var2, var3, var4, var5, var6);
        var0.fillTriangle(var1, var2, var5, var6, var7, var8);
        var0.fillTriangle(var1, var2, var3, var4, var7, var8);
    }

    public static void SystemOut(String var0) {
    }

    public static int calNextPosition(double var0, double var2, int var4, int var5) {
        return calNextPosition(var0, var2, var4, var5, 1.0);
    }

    public static int calNextPosition(double var0, double var2, int var4, int var5, double var6) {
        return (int)calNextPositionD(var0, var2, var4, var5, var6);
    }

    public static double calNextPositionD(double var0, double var2, int var4, int var5) {
        return (double)calNextPosition(var0, var2, var4, var5, 1.0);
    }

    public static double calNextPositionD(double var0, double var2, int var4, int var5, double var6) {
        if (var5 > var4) {
            double var10 = (var2 - var0) * 100.0 * (double)var4 / (double)var5;
            double var12 = var10 / 100.0;
            double var8;
            if (var10 == 0.0) {
                var8 = 0.0;
            } else {
                var8 = var6;
                if (!(var10 > 0.0)) {
                    var8 = -var6;
                }
            }

            var6 = var0 + var12 + var8;
            var0 = var6;
            if ((long)var10 * ((long)var2 * 100L - (long)var6 * 100L) <= 0L) {
                var0 = var2;
            }
        }

        return var0;
    }

    public static float calNextPositionF(double var0, double var2, int var4, int var5, double var6) {
        return (float)calNextPositionD(var0, var2, var4, var5, var6);
    }

    public static int calNextPositionReverse(int var0, int var1, int var2, int var3, int var4) {
        return calNextPositionReverse(var0, var1, var2, var3, var4, 1);
    }

    public static int calNextPositionReverse(int var0, int var1, int var2, int var3, int var4, int var5) {
        if (var4 > var3 && var0 != var2) {
            var1 = Math.abs(var0 - var1);
            var3 = var1 * var4 / (var4 - var3) >> 1;
            var1 = var3;
            if (var3 == 0) {
                var1 = var5;
            }

            if (var0 < var2) {
                var1 += var0;
                var0 = var1;
                if (var1 > var2) {
                    var0 = var2;
                }
            } else {
                var1 = var0 - var1;
                var0 = var1;
                if (var1 < var2) {
                    var0 = var2;
                }
            }
        }

        return var0;
    }

    public static int dCos(int var0) {
        return dSin(90 - var0);
    }

    public static int dSin(int var0) {
        while(var0 < 0) {
            var0 += 360;
        }

        var0 %= 360;
        if (var0 >= 0 && var0 <= 90) {
            var0 = sinData2[var0] >>> 7;
        } else if (var0 > 90 && var0 <= 180) {
            var0 = sinData2[90 - (var0 - 90)] >>> 7;
        } else if (var0 > 180 && var0 <= 270) {
            var0 = (sinData2[var0 - 180] >>> 7) * -1;
        } else if (var0 > 270 && var0 <= 359) {
            var0 = (sinData2[90 - (var0 - 270)] >>> 7) * -1;
        } else {
            var0 = 0;
        }

        return var0;
    }

    private static String[] divideText(String var0) {
        String[] var6;
        if (var0 == null) {
            var6 = null;
        } else {
            String[] var4 = (String[])null;
            int var1 = getTextLineNum(var0);
            var4 = new String[var1];
            var1 = 0;
            int var2 = 0;

            while(true) {
                int var3 = var0.indexOf(13, var2);
                if (var3 == -1) {
                    if (var4[0] != null && var4[0].length() > 0) {
                        char[] var7 = var4[0].toCharArray();
                        var0 = new String(var7);
                        StringBuffer var5 = new StringBuffer(var0);
                        Integer var8 = new Integer(var5.charAt(0));
                        if (var8.hashCode() == 63 || var8.hashCode() == 65279 || var8.hashCode() == -257) {
                            var5.deleteCharAt(0);
                        }

                        var4[0] = var5.toString();
                    }

                    var6 = var4;
                    break;
                }

                var4[var1] = var0.substring(var2, var3);
                var2 = var3 + 2;
                ++var1;
            }
        }

        return var6;
    }

    public static final void drawArc(MFGraphics var0, int var1, int var2, int var3, int var4, int var5, int var6) {
        var1 = zoomOut(var1);
        var2 = zoomOut(var2);
        var3 = zoomOut(var3);
        var4 = zoomOut(var4);
        var0.fillArc(var1, var2, var3, var4, var5, var6);
    }

    public static void drawArrow(MFGraphics var0, int var1, int var2, boolean var3, boolean var4) {
        int var5 = zoomOut(var1);
        int var6 = zoomOut(var2);

        for(var1 = 0; var1 < ARROW.length; ++var1) {
            if (var3) {
                var2 = ARROW[var1];
            } else {
                var2 = ARROW[ARROW.length - var1 - 1];
            }

            int var7;
            int var8;
            int var9;
            int var10;
            if (var4) {
                var10 = ARROW.length / 2;
                var7 = var2 / 2;
                var8 = ARROW.length / 2;
                var9 = var2 / 2;
                var0.drawLine(var5 - var10 + var1, var6 - var7 - 1, var5 - var8 + var1, var6 - var9 - 1 + var2);
            } else {
                var9 = var2 / 2;
                var7 = ARROW.length / 2;
                var8 = var2 / 2;
                var10 = ARROW.length / 2;
                var0.drawLine(var5 - var9 - 1, var6 - var7 + var1, var5 - var8 - 1 + var2, var6 - var10 + var1);
            }
        }

    }

    public static void drawBoldString(MFGraphics var0, String var1, int var2, int var3, int var4, int var5) {
        drawBoldString(var0, var1, var2, var3, var4, var5, 0, var5);
    }

    public static void drawBoldString(MFGraphics var0, String var1, int var2, int var3, int var4, int var5, int var6) {
        drawBoldString(var0, var1, var2, var3, var4, var5, var6, var5);
    }

    public static void drawBoldString(MFGraphics var0, String var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        if (var1 != null) {
            var7 = zoomOut(var2);
            var3 = zoomOut(var3);
            var0.setColor(var6);

            for(var2 = 0; var2 < OFFSET.length; ++var2) {
                var0.drawString(var1, OFFSET[var2][0] + var7, OFFSET[var2][1] + var3, var4);
            }

            var0.setColor(var5);
            var0.drawString(var1, var7, var3, var4);
        }

    }

    public static void drawBoldString2(MFGraphics var0, String var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        if (var1 != null) {
            var7 = zoomOut(var2);
            var3 = zoomOut(var3);
            var0.setColor(var6);

            for(var2 = 0; var2 < OFFSET2.length; ++var2) {
                var0.drawString(var1, OFFSET2[var2][0] + var7, OFFSET2[var2][1] + var3, var4);
            }

            var0.setColor(var5);
            var0.drawString(var1, var7, var3, var4);
        }

    }

    public static void drawBoldStrings(MFGraphics var0, String[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        drawStrings(var0, var1, var2, var3, var4, var5, 0, true, var6, var7, var8);
    }

    public static void drawBoldStringsNarrow(MFGraphics var0, String[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        drawStringsNarrow(var0, var1, var2, var3, var4, var5, var6, 0, true, var7, var8, var9);
    }

    public static void drawFadeRange(MFGraphics var0, int var1, int var2, int var3, int var4) {
        int var5;
        for(var5 = 0; var5 < 864; ++var5) {
            rayRGB[var5] = 16777215;
        }

        for(var5 = 0; var5 < 288; ++var5) {
            for(int var6 = 0; var6 < 3; ++var6) {
                int[] var8 = rayRGB;
                int var7 = rayRGB[var6 * 288 + var5];
                var8[var6 * 288 + var5] = var1 << 24 & -16777216 | var7 & 16777215;
            }
        }

        switch (var4) {
            case 0:
            case 7:
                var0.drawRGB(rayRGB, 0, 288, var2, var3, 288, 3, true);
                break;
            case 1:
            case 6:
                var0.drawRGB(rayRGB, 0, 288, var2, var3, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 - 3, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 + 3, 288, 3, true);
                break;
            case 2:
            case 5:
                var0.drawRGB(rayRGB, 0, 288, var2, var3, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 - 3, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 + 3, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 - 6, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 + 6, 288, 3, true);
                break;
            case 3:
            case 4:
                var0.drawRGB(rayRGB, 0, 288, var2, var3, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 - 3, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 + 3, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 - 6, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 + 6, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 - 9, 288, 3, true);
                var0.drawRGB(rayRGB, 0, 288, var2, var3 + 9, 288, 3, true);
        }

    }

    public static final void drawImage(MFGraphics var0, MFImage var1, int var2, int var3, int var4) {
        var2 = zoomOut(var2);
        var3 = zoomOut(var3);
        var0.drawImage(var1, var2, var3, var4);
    }

    public static final void drawImage(MFGraphics var0, MFImage var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        var2 = zoomOut(var2);
        var3 = zoomOut(var3);
        var4 = zoomOut(var4);
        var5 = zoomOut(var5);
        var7 = zoomOut(var7);
        var8 = zoomOut(var8);
        drawRegionPrivate(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
    }

    public static void drawImageSetClip(MFGraphics var0, MFImage var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
        int var9;
        if ((var8 & 1) != 0) {
            var9 = var6 - var4 / 2;
        } else {
            var9 = var6;
            if ((var8 & 8) != 0) {
                var9 = var6 - var4;
            }
        }

        if ((var8 & 32) != 0) {
            var6 = var7 - var5;
        } else {
            var6 = var7;
            if ((var8 & 2) != 0) {
                var6 = var7 - var5 / 2;
            }
        }

        var0.setClip(Math.max(var9, 0), Math.max(var6, 0), Math.min(var4 + var9, 0 + 240) - Math.max(var9, 0), Math.min(var5 + var6, 0 + 320) - Math.max(var6, 0));
        var0.drawImage(var1, var9 - var2, var6 - var3, 0);
        var0.setClip(0, 0, 240, 320);
    }

    public static final void drawImageWithoutZoom(MFGraphics var0, MFImage var1, int var2, int var3, int var4) {
        var0.drawImage(var1, var2, var3, var4);
    }

    public static final void drawImageWithoutZoom(MFGraphics var0, MFImage var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        drawRegionPrivate(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
    }

    public static final void drawLine(MFGraphics var0, int var1, int var2, int var3, int var4) {
        var1 = zoomOut(var1);
        var2 = zoomOut(var2);
        var3 = zoomOut(var3);
        var4 = zoomOut(var4);
        var0.drawLine(var1, var2, var3, var4);
    }

    public static final void drawRect(MFGraphics var0, int var1, int var2, int var3, int var4) {
        var1 = zoomOut(var1);
        var2 = zoomOut(var2);
        var3 = zoomOut(var3);
        var4 = zoomOut(var4);
        var0.drawRect(var1, var2, var3, var4);
    }

    public static final void drawRectBold(MFGraphics var0, int var1, int var2, int var3, int var4) {
        var0.setColor(backColor);
        var0.drawRect(var1, var2, var3, var4);
        var0.drawRect(var1 - 1, var2 - 1, var3 + 2, var4 + 2);
        var0.drawRect(var1 + 1, var2 + 1, var3 - 2, var4 - 2);
        var0.setColor(borderColor);
        var0.drawRect(var1 - 2, var2 - 2, var3 + 4, var4 + 4);
        var0.drawRect(var1 + 2, var2 + 2, var3 - 4, var4 - 4);
    }

    public static final void drawRegion(MFGraphics var0, MFImage var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        drawImage(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
    }

    public static void drawRegionDebug(MFGraphics var0, MFImage var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        int var10;
        if ((var9 & 1) != 0) {
            var10 = var7 - var4 / 2;
        } else {
            var10 = var7;
            if ((var9 & 8) != 0) {
                var10 = var7 - var4;
            }
        }

        if ((var9 & 32) != 0) {
            var7 = var8 - var5;
        } else {
            var7 = var8;
            if ((var9 & 2) != 0) {
                var7 = var8 - var5 / 2;
            }
        }

        if (var6 == 0) {
            var0.drawRegion(var1, var2, var3, var4, var5, var6, var10, var7, 20);
        } else {
            int[] var12 = new int[var4 * var5];
            int[] var11 = new int[var4 * var5];
            var1.getRGB(var12, 0, var4, var2, var3, var4, var5);
            if (var6 == 2) {
                for(var2 = 0; var2 < var4; ++var2) {
                    for(var3 = 0; var3 < var5; ++var3) {
                        var11[var4 - var2 - 1 + var3 * var4] = var12[var3 * var4 + var2];
                    }
                }
            } else if (var6 == 5) {
                for(var2 = 0; var2 < var4; ++var2) {
                    for(var3 = 0; var3 < var5; ++var3) {
                        var11[var5 - var3 - 1 + var2 * var5] = var12[var3 * var4 + var2];
                    }
                }
            } else if (var6 == 3) {
                for(var2 = 0; var2 < var4; ++var2) {
                    for(var3 = 0; var3 < var5; ++var3) {
                        var11[var4 - var2 - 1 + (var5 - var3 - 1) * var4] = var12[var3 * var4 + var2];
                    }
                }
            } else if (var6 == 6) {
                for(var2 = 0; var2 < var4; ++var2) {
                    for(var3 = 0; var3 < var5; ++var3) {
                        var11[var3 + (var4 - var2 - 1) * var5] = var12[var3 * var4 + var2];
                    }
                }
            } else if (var6 == 7) {
                for(var2 = 0; var2 < var4; ++var2) {
                    for(var3 = 0; var3 < var5; ++var3) {
                        var11[var5 - var3 - 1 + (var4 - var2 - 1) * var5] = var12[var3 * var4 + var2];
                    }
                }
            } else if (var6 == 1) {
                for(var2 = 0; var2 < var4; ++var2) {
                    for(var3 = 0; var3 < var5; ++var3) {
                        var11[var2 + (var5 - var3 - 1) * var4] = var12[var3 * var4 + var2];
                    }
                }
            } else if (var6 == 4) {
                for(var2 = 0; var2 < var4; ++var2) {
                    for(var3 = 0; var3 < var5; ++var3) {
                        var11[var3 + var2 * var5] = var12[var3 * var4 + var2];
                    }
                }
            }

            switch (var6) {
                case 4:
                case 5:
                case 6:
                case 7:
                    var0.drawRGB(var11, 0, var5, var10, var7, var5, var4, true);
                    break;
                default:
                    var0.drawRGB(var11, 0, var4, var10, var7, var4, var5, true);
            }
        }

    }

    public static void drawRegionNokia(MFGraphics var0, MFImage var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
    }

    private static final void drawRegionPrivate(MFGraphics var0, MFImage var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        var0.drawRegion(var1, var2, var3, var4, var5, var6, var7, var8, var9);
    }

    public static void drawScaleAni(MFGraphics var0, AnimationDrawer var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8) {
        var1.setActionId(var2);
        Graphics var9 = (Graphics)var0.getSystemGraphics();
        var9.save();
        var9.translate((float)var3, (float)var4);
        var9.scale(var5, var6, var7, var8);
        var1.draw(var0, 0, 0);
        var9.scale(1.0F / var5, 1.0F / var6);
        var9.translate((float)(-var3), (float)(-var4));
        var9.restore();
    }

    public static void drawSelectBox(MFGraphics var0, Object[] var1, int var2, int var3, int var4, int var5, int var6) {
        fillRectBold(var0, var2, var3, var4, LINE_SPACE * var5);
        if (var1.length == 0) {
            var4 /= 2;
            var5 = LINE_SPACE * var5 / 2;
            var0.drawString("无可选项", var2 + var4, var3 + var5, 17);
        } else if (var1.length <= var5) {
            for(var4 = 0; var4 < var1.length; ++var4) {
                if (var6 == var4) {
                    var0.setColor(16711680);
                } else {
                    var0.setColor(16777215);
                }

                String var10 = var1[var4].toString();
                var5 = LINE_SPACE;
                var0.drawString(var10, var2 + 8, var5 * var4 + var3, 20);
            }
        } else {
            int var9 = LINE_SPACE * var5 * var5 / var1.length;
            int var7;
            if (var6 < var5 / 2) {
                var7 = 0;
            } else if (var5 / 2 + var6 < var1.length) {
                var7 = var6 - var5 / 2;
            } else {
                var7 = var1.length - var5;
            }

            for(int var8 = 0; var8 < var5; ++var8) {
                if (var6 == var8 + var7) {
                    var0.setColor(16711680);
                } else {
                    var0.setColor(16777215);
                }

                var0.drawString(var1[var8 + var7].toString(), var2 + 8, LINE_SPACE * var8 + var3, 20);
            }

            var0.setColor(16777215);
            var0.fillRect(var4 - 8, var3, 8, LINE_SPACE * var5);
            var0.setColor(8421504);
            var2 = LINE_SPACE * var5 * var7 / var1.length;
            var0.fillRect(var4 - 7, var2 + var3, 6, var9);
            var0.setColor(16777215);
            var0.drawRect(var4 - 8, var3, 7, LINE_SPACE * var5 - 1);
        }

    }

    public static final void drawString(MFGraphics var0, String var1, int var2, int var3, int var4) {
        var2 = zoomOut(var2);
        var3 = zoomOut(var3);
        var0.drawString(var1, var2, var3, var4);
    }

    public static void drawStrings(MFGraphics var0, String[] var1, int var2, int var3, int var4, int var5) {
        drawStrings(var0, var1, var2, var3, var4, var5, 0, false, 0, 0, 0);
    }

    public static void drawStrings(MFGraphics var0, String[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        drawStrings(var0, var1, var2, var3, var4, var5, var6, 0, true, var7, var8, var9);
    }

    public static void drawStrings(MFGraphics var0, String[] var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, int var9, int var10, int var11) {
        if (var1 != null) {
            int var12 = var2;
            downPermit = false;
            if (stringCursol > 0) {
                upPermit = true;
            } else {
                upPermit = false;
            }

            for(int var13 = var7; var13 < var1.length; ++var13) {
                if ((var13 - stringCursol - var7) * LINE_SPACE + FONT_H > var5) {
                    downPermit = true;
                    break;
                }

                String var14;
                if (var1[var13].indexOf("<") != -1 && var1[var13].indexOf(">") != -1) {
                    anchor = var1[var13].substring(var1[var13].indexOf("<") + 1, var1[var13].indexOf(">"));
                    var14 = var1[var13].substring(var1[var13].indexOf(">") + 1);
                } else {
                    var14 = var1[var13];
                }

                if (var13 - var7 >= stringCursol) {
                    if (anchor.indexOf("H") != -1) {
                        var12 = var2 + (var4 - zoomIn(getStringWidth(14, var14))) / 2;
                    } else if (anchor.indexOf("L") != -1) {
                        var12 = var2;
                    } else if (anchor.indexOf("R") != -1) {
                        var12 = var2 + var4 - zoomIn(getStringWidth(14, var14));
                    }

                    if (var8) {
                        drawBoldString2(var0, var14, var12, var3 + (var13 - stringCursol - var7) * var6, 20, var9, var10, var11);
                    } else {
                        drawString(var0, var14, var12, (var13 - stringCursol - var7) * var6 + var3, 20);
                    }
                }
            }
        }

    }

    public static void drawStrings(MFGraphics var0, String[] var1, int var2, int var3, int var4, int var5, int var6, boolean var7, int var8, int var9, int var10) {
        if (var1 != null) {
            int var11 = var2;
            downPermit = false;
            if (stringCursol > 0) {
                upPermit = true;
            } else {
                upPermit = false;
            }

            for(int var12 = var6; var12 < var1.length; ++var12) {
                if ((var12 - stringCursol - var6) * LINE_SPACE + FONT_H > var5) {
                    downPermit = true;
                    break;
                }

                String var13;
                if (var1[var12].indexOf("<") != -1 && var1[var12].indexOf(">") != -1) {
                    anchor = var1[var12].substring(var1[var12].indexOf("<") + 1, var1[var12].indexOf(">"));
                    var13 = var1[var12].substring(var1[var12].indexOf(">") + 1);
                } else {
                    var13 = var1[var12];
                }

                if (var12 - var6 >= stringCursol) {
                    if (anchor.indexOf("H") != -1) {
                        var11 = var2 + (var4 - zoomIn(getStringWidth(14, var13))) / 2;
                    } else if (anchor.indexOf("L") != -1) {
                        var11 = var2;
                    } else if (anchor.indexOf("R") != -1) {
                        var11 = var2 + var4 - zoomIn(getStringWidth(14, var13));
                    }

                    if (var7) {
                        drawBoldString2(var0, var13, var11, var3 + (var12 - stringCursol - var6) * LINE_SPACE, 20, var8, var9, var10);
                    } else {
                        drawString(var0, var13, var11, (var12 - stringCursol - var6) * LINE_SPACE + var3, 20);
                    }
                }
            }
        }

    }

    public static void drawStrings(MFGraphics var0, String[] var1, int var2, int var3, int var4, int var5, int var6, boolean var7, int var8, int var9, int var10, int var11) {
        if (var1 != null) {
            int var12 = var2;
            downPermit = false;
            if (stringCursol > 0) {
                upPermit = true;
            } else {
                upPermit = false;
            }

            for(int var13 = var6; var13 < var1.length; ++var13) {
                if ((var13 - stringCursol - var6) * LINE_SPACE + FONT_H > var5) {
                    downPermit = true;
                    break;
                }

                String var14;
                if (var1[var13].indexOf("<") != -1 && var1[var13].indexOf(">") != -1) {
                    anchor = var1[var13].substring(var1[var13].indexOf("<") + 1, var1[var13].indexOf(">"));
                    var14 = var1[var13].substring(var1[var13].indexOf(">") + 1);
                } else {
                    var14 = var1[var13];
                }

                if (var13 - var6 >= stringCursol) {
                    if (anchor.indexOf("H") != -1) {
                        var12 = var2 + (var4 - zoomIn(getStringWidth(var11, var14))) / 2;
                    } else if (anchor.indexOf("L") != -1) {
                        var12 = var2;
                    } else if (anchor.indexOf("R") != -1) {
                        var12 = var2 + var4 - zoomIn(getStringWidth(var11, var14));
                    }

                    if (var7) {
                        drawBoldString2(var0, var14, var12, var3 + (var13 - stringCursol - var6) * LINE_SPACE, 20, var8, var9, var10);
                    } else {
                        drawString(var0, var14, var12, (var13 - stringCursol - var6) * LINE_SPACE + var3, 20);
                    }
                }
            }
        }

    }

    public static void drawStringsContinue(MFGraphics var0, String[] var1, int var2, int var3, int var4, int var5, int var6, boolean var7, int var8, int var9, int var10) {
        if (var1 != null) {
            var5 = var2;
            downPermit = false;
            if (stringCursol > 0) {
                upPermit = true;
            } else {
                upPermit = false;
            }

            for(int var11 = var6; var11 < var1.length; ++var11) {
                String var12;
                if (var1[var11].indexOf("<") != -1 && var1[var11].indexOf(">") != -1) {
                    anchor = var1[var11].substring(var1[var11].indexOf("<") + 1, var1[var11].indexOf(">"));
                    var12 = var1[var11].substring(var1[var11].indexOf(">") + 1);
                } else {
                    var12 = var1[var11];
                }

                if (anchor.indexOf("H") != -1) {
                    var5 = var2 + (var4 - zoomIn(getStringWidth(14, var12))) / 2;
                } else if (anchor.indexOf("L") != -1) {
                    var5 = var2;
                } else if (anchor.indexOf("R") != -1) {
                    var5 = var2 + var4 - zoomIn(getStringWidth(14, var12));
                }

                if (var7) {
                    drawBoldString(var0, var12, var5, var3 + (var11 - stringCursol - var6) * LINE_SPACE, 20, var8, var9, var10);
                } else {
                    drawString(var0, var12, var5, (var11 - stringCursol - var6) * LINE_SPACE + var3, 20);
                }
            }
        }

    }

    public static void drawStringsNarrow(MFGraphics var0, String[] var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, int var9, int var10, int var11) {
        if (var1 != null) {
            int var12 = var2;
            downPermit = false;
            if (stringCursol > 0) {
                upPermit = true;
            } else {
                upPermit = false;
            }

            for(int var13 = var7; var13 < var1.length; ++var13) {
                if ((var13 - stringCursol - var7) * var6 + FONT_H > var5) {
                    downPermit = true;
                    break;
                }

                String var14;
                if (var1[var13].indexOf("<") != -1 && var1[var13].indexOf(">") != -1) {
                    anchor = var1[var13].substring(var1[var13].indexOf("<") + 1, var1[var13].indexOf(">"));
                    var14 = var1[var13].substring(var1[var13].indexOf(">") + 1);
                } else {
                    var14 = var1[var13];
                }

                if (var13 - var7 >= stringCursol) {
                    if (anchor.indexOf("H") != -1) {
                        var12 = var2 + (var4 - zoomIn(getStringWidth(14, var14))) / 2;
                    } else if (anchor.indexOf("L") != -1) {
                        var12 = var2;
                    } else if (anchor.indexOf("R") != -1) {
                        var12 = var2 + var4 - zoomIn(getStringWidth(14, var14));
                    }

                    if (var8) {
                        drawBoldString(var0, var14, var12, var3 + (var13 - stringCursol - var7) * var6, 20, var9, var10, var11);
                    } else {
                        drawString(var0, var14, var12, (var13 - stringCursol - var7) * var6 + var3, 20);
                    }
                }
            }
        }

    }

    public static final void drawSubstring(MFGraphics var0, String var1, int var2, int var3, int var4, int var5, int var6) {
        zoomOut(var4);
        zoomOut(var5);
    }

    public static void drawTxtArrows(MFGraphics var0, int var1, int var2) {
        if (downPermit) {
            drawArrow(var0, var1 + 10, var2, false, false);
        }

        if (stringCursol > 0) {
            drawArrow(var0, var1 - 10, var2, true, false);
        }

    }

    public static final void fillArc(MFGraphics var0, int var1, int var2, int var3, int var4, int var5, int var6) {
        var1 = zoomOut(var1);
        var2 = zoomOut(var2);
        var3 = zoomOut(var3);
        var4 = zoomOut(var4);
        var0.fillArc(var1, var2, var3, var4, var5, var6);
    }

    public static final void fillRect(MFGraphics var0, int var1, int var2, int var3, int var4) {
        var1 = zoomOut(var1);
        var2 = zoomOut(var2);
        var3 = zoomOut(var3);
        var4 = zoomOut(var4);
        var0.fillRect(var1, var2, var3, var4);
    }

    public static final void fillRectBold(MFGraphics var0, int var1, int var2, int var3, int var4) {
        var0.setColor(backColor);
        var0.fillRect(var1, var2, var3, var4);
        var0.setColor(borderColor);
        var0.drawRect(var1, var2, var3, var4);
        var0.drawRect(var1 - 1, var2 - 1, var3 + 2, var4 + 2);
    }

    public static final void fillRoundRectBold(MFGraphics var0, int var1, int var2, int var3, int var4, int var5, int var6) {
        var1 = zoomOut(var1);
        var2 = zoomOut(var2);
        var3 = zoomOut(var3);
        var4 = zoomOut(var4);
        var0.setColor(var5);
        var0.fillRoundRect(var1, var2, var3, var4, 10, 10);
        var0.setColor(var6);
        var0.drawRoundRect(var1, var2, var3 - 1, var4, 10, 10);
    }

    public static Object[] getArray(Vector var0) {
        Object[] var2;
        if (var0.size() == 0) {
            var2 = null;
        } else {
            Object[] var1 = new Object[var0.size()];
            var0.copyInto(var1);
            var2 = var1;
        }

        return var2;
    }

    public static String[] getEnStrings(String var0, int var1) {
        Vector var7 = new Vector();
        String var5 = "";
        int var2 = 0;
        int var3 = 0;
        boolean var10 = false;

        while(var3 >= 0 && var3 < var0.length()) {
            var1 = var3 + 1;
            String var8 = var0.substring(var2, var1);
            if (!var8.equals("^") && !var8.equals(" ")) {
                if (!var8.equals("|") && !var8.equals("\n")) {
                    String var6 = var5 + var8;
                    if (var8.equals("<")) {
                        var5 = var6;
                        var3 = var1;
                    } else {
                        int var4 = 0;

                        while(true) {
                            var5 = var6;
                            var3 = var1;
                            if (var4 >= Symbol.length) {
                                break;
                            }

                            if (var8.charAt(0) == Symbol[var4]) {
                                var5 = var6;
                                var2 = var1;
                                var3 = var1;
                                if (var1 == var0.length()) {
                                    var7.addElement(var6);
                                    var5 = var6;
                                    var2 = var1;
                                    var3 = var1;
                                }
                                break;
                            }

                            ++var4;
                        }
                    }
                } else {
                    var7.addElement(var5);
                    var5 = "";
                    var3 = var1;
                }
            } else {
                var3 = var1;
            }
        }

        String[] var9 = new String[var7.size()];
        var7.copyInto(var9);
        return var9;
    }

    public static String getFileName(String var0) {
        for(String var1 = ""; var0.indexOf("/") != -1; var0 = var0.substring(var0.indexOf("/") + 1)) {
            var1 = var1 + var0.substring(0, var0.indexOf("/") + 1);
        }

        return var0;
    }

    public static String getPath(String var0) {
        String var1;
        for(var1 = ""; var0.indexOf("/") != -1; var0 = var0.substring(var0.indexOf("/") + 1)) {
            var1 = var1 + var0.substring(0, var0.indexOf("/") + 1);
        }

        return var1;
    }

    public static int getRelativePointX(int var0, int var1, int var2, int var3) {
        var1 = dCos(var3) * var1 / 100;
        var2 = dSin(var3) * var2 / 100;
        return var1 + var0 - var2;
    }

    public static int getRelativePointY(int var0, int var1, int var2, int var3) {
        var1 = dSin(var3) * var1 / 100;
        var2 = dCos(var3) * var2 / 100;
        return var1 + var0 + var2;
    }

    private static byte[] getResource(String fileName) {
        InputStream is = null;
        byte[] re = (byte[]) null;
        try {
            try {
                is = MFDevice.getResourceAsStream(fileName);
                if (is != null) {
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    DataOutputStream ds = new DataOutputStream(bs);
                    for (int readByte = is.read(); readByte >= 0; readByte = is.read()) {
                        ds.writeByte(readByte);
                    }
                    re = bs.toByteArray();
                    bs.close();
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
            return re;
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    private static final Coordinate getRoteCoordinate(int var0, int var1, int var2) {
        Coordinate var3;
        switch (var2) {
            case 3:
                var3 = Coordinate.returnCoordinate(-var0, -var1);
                break;
            case 4:
            default:
                var3 = Coordinate.returnCoordinate(var0, var1);
                break;
            case 5:
                var3 = Coordinate.returnCoordinate(var1, -var0);
                break;
            case 6:
                var3 = Coordinate.returnCoordinate(-var1, var0);
        }

        return var3;
    }

    public static final String getStringToDraw(String var0) {
        if (var0.indexOf("<") != -1 && var0.indexOf(">") != -1) {
            anchor = var0.substring(var0.indexOf("<") + 2, var0.indexOf(">"));
            var0 = var0.substring(var0.indexOf(">") + 1);
        }

        return var0;
    }

    public static int getStringWidth(int var0, String var1) {
        return MFGraphics.stringWidth(var0, var1);
    }

    public static String[] getStrings(String var0, int var1) {
        var1 = zoomOut(var1);
        int var8 = var1;
        if (var1 > 8) {
            var8 = var1 - 8;
        }

        Vector var14 = new Vector();
        String var12 = "";
        boolean var4 = false;
        int var2 = 0;
        int var3 = 0;
        int var6 = 0;
        int var5 = 0;
        boolean var11 = false;

        label113:
        while(var3 >= 0 && var3 < var0.length()) {
            var1 = var3 + 1;
            String var15 = var0.substring(var2, var1);
            boolean var7;
            boolean var10;
            String var13;
            if (var15.equals("^")) {
                var2 -= var5;
                var10 = false;
                var3 = var5;
                var7 = var4;
            } else if (!var15.equals("|") && !var15.equals("\n")) {
                var13 = var12 + var15;
                if (var15.equals("<")) {
                    var4 = true;
                    var2 = var1;
                    var12 = var13;
                    var3 = var1;
                    continue;
                }

                int var9 = 0;

                while(true) {
                    var2 = var6;
                    var12 = var13;
                    var7 = var4;
                    var10 = var11;
                    var3 = var5;
                    if (var9 >= Symbol.length) {
                        break;
                    }

                    if (var15.charAt(0) == Symbol[var9]) {
                        var12 = var13;
                        var2 = var1;
                        var3 = var1;
                        if (var1 == var0.length()) {
                            var14.addElement(var13);
                            var12 = var13;
                            var2 = var1;
                            var3 = var1;
                        }
                        continue label113;
                    }

                    ++var9;
                }
            } else {
                var2 = 0;
                var14.addElement(var12);
                var12 = "";
                var3 = var1;
                var7 = false;
                var10 = var11;
            }

            var5 = getStringWidth(14, var12);
            int var21 = var5;
            if (var7) {
                var21 = var5 - getStringWidth(14, "<H>");
            }

            if (var21 >= var8 && var1 < var0.length()) {
                boolean var10001;
                if (var2 == 0) {
                    label80: {
                        label79: {
                            label78: {
                                try {
                                    var14.addElement(var12.substring(0, var12.length() - 1));
                                } catch (Exception var17) {
                                    var10001 = false;
                                    break label78;
                                }

                                try {
                                    var13 = var12.substring(var12.length() - 1);
                                    break label79;
                                } catch (Exception var16) {
                                    var10001 = false;
                                }
                            }

                            SystemOut("answerWord" + var12);
                            break label80;
                        }

                        var12 = var13;
                        var3 = var1 - 1;
                    }
                } else {
                    byte var22;
                    label121: {
                        label89: {
                            label122: {
                                try {
                                    var14.addElement(var12.substring(0, var2));
                                } catch (Exception var19) {
                                    var10001 = false;
                                    break label122;
                                }

                                if (var10) {
                                    var22 = 1;
                                } else {
                                    var22 = 0;
                                }

                                try {
                                    var13 = var12.substring(var22 + var2);
                                    break label89;
                                } catch (Exception var18) {
                                    var10001 = false;
                                }
                            }

                            SystemOut("answerWord" + var12);
                            SystemOut("ConcealEnterPosition" + var2);
                            var2 = var3;
                            break label121;
                        }

                        var12 = var13;
                        if (var10) {
                            var22 = 1;
                        } else {
                            var22 = 0;
                        }

                        var2 = var2 + var22 + var3;
                    }

                    var22 = 0;
                    var3 = var2;
                    var2 = var22;
                }

                var4 = false;
                var5 = var3;
                var13 = var12;
                var6 = var2;
            } else {
                var6 = var2;
                var13 = var12;
                var4 = var7;
                var5 = var3;
                if (var1 == var0.length()) {
                    var14.addElement(var12);
                    var6 = var2;
                    var13 = var12;
                    var4 = var7;
                    var5 = var3;
                }
            }

            var2 = var1;
            var12 = var13;
            var3 = var1;
            var11 = var10;
        }

        String[] var20 = new String[var14.size()];
        var14.copyInto(var20);
        return var20;
    }

    public static String[] getStrings(String var0, int var1, int var2) {
        var2 = zoomOut(var2);
        int var9 = var2;
        if (var2 > 8) {
            var9 = var2 - 8;
        }

        Vector var15 = new Vector();
        String var13 = "";
        boolean var5 = false;
        int var3 = 0;
        int var4 = 0;
        int var7 = 0;
        int var6 = 0;
        boolean var12 = false;

        label113:
        while(var4 >= 0 && var4 < var0.length()) {
            var2 = var4 + 1;
            String var16 = var0.substring(var3, var2);
            boolean var8;
            boolean var11;
            String var14;
            if (var16.equals("^")) {
                var3 -= var6;
                var11 = false;
                var4 = var6;
                var8 = var5;
            } else if (!var16.equals("|") && !var16.equals("\n")) {
                var14 = var13 + var16;
                if (var16.equals("<")) {
                    var5 = true;
                    var3 = var2;
                    var13 = var14;
                    var4 = var2;
                    continue;
                }

                int var10 = 0;

                while(true) {
                    var3 = var7;
                    var13 = var14;
                    var8 = var5;
                    var11 = var12;
                    var4 = var6;
                    if (var10 >= Symbol.length) {
                        break;
                    }

                    if (var16.charAt(0) == Symbol[var10]) {
                        var13 = var14;
                        var3 = var2;
                        var4 = var2;
                        if (var2 == var0.length()) {
                            var15.addElement(var14);
                            var13 = var14;
                            var3 = var2;
                            var4 = var2;
                        }
                        continue label113;
                    }

                    ++var10;
                }
            } else {
                var3 = 0;
                var15.addElement(var13);
                var13 = "";
                var4 = var2;
                var8 = false;
                var11 = var12;
            }

            var6 = getStringWidth(var1, var13);
            int var22 = var6;
            if (var8) {
                var22 = var6 - getStringWidth(var1, "<H>");
            }

            if (var22 >= var9 && var2 < var0.length()) {
                boolean var10001;
                if (var3 == 0) {
                    label80: {
                        label79: {
                            label78: {
                                try {
                                    var15.addElement(var13.substring(0, var13.length() - 1));
                                } catch (Exception var18) {
                                    var10001 = false;
                                    break label78;
                                }

                                try {
                                    var14 = var13.substring(var13.length() - 1);
                                    break label79;
                                } catch (Exception var17) {
                                    var10001 = false;
                                }
                            }

                            SystemOut("answerWord" + var13);
                            break label80;
                        }

                        var13 = var14;
                        var4 = var2 - 1;
                    }
                } else {
                    byte var23;
                    label121: {
                        label89: {
                            label122: {
                                try {
                                    var15.addElement(var13.substring(0, var3));
                                } catch (Exception var20) {
                                    var10001 = false;
                                    break label122;
                                }

                                if (var11) {
                                    var23 = 1;
                                } else {
                                    var23 = 0;
                                }

                                try {
                                    var14 = var13.substring(var23 + var3);
                                    break label89;
                                } catch (Exception var19) {
                                    var10001 = false;
                                }
                            }

                            SystemOut("answerWord" + var13);
                            SystemOut("ConcealEnterPosition" + var3);
                            var3 = var4;
                            break label121;
                        }

                        var13 = var14;
                        if (var11) {
                            var23 = 1;
                        } else {
                            var23 = 0;
                        }

                        var3 = var3 + var23 + var4;
                    }

                    var23 = 0;
                    var4 = var3;
                    var3 = var23;
                }

                var5 = false;
                var6 = var4;
                var14 = var13;
                var7 = var3;
            } else {
                var7 = var3;
                var14 = var13;
                var5 = var8;
                var6 = var4;
                if (var2 == var0.length()) {
                    var15.addElement(var13);
                    var7 = var3;
                    var14 = var13;
                    var5 = var8;
                    var6 = var4;
                }
            }

            var3 = var2;
            var13 = var14;
            var4 = var2;
            var12 = var11;
        }

        String[] var21 = new String[var15.size()];
        var15.copyInto(var21);
        return var21;
    }

    private static int getTextLineNum(String var0) {
        int var1 = 0;
        int var2 = 0;

        while(true) {
            var2 = var0.indexOf("\n", var2);
            if (var2 == -1) {
                return var1;
            }

            ++var2;
            ++var1;
        }
    }

    public static String getTypeName(String var0, String var1) {
        if (var0.indexOf(".") != -1) {
            var0 = var0.substring(0, var0.indexOf(".")) + var1;
        } else {
            var0 = var0 + var1;
        }

        return var0;
    }

    public static void initString() {
        stringCursol = 0;
        anchor = "";
    }

    public static String[] loadString(String filename) {
        DataInputStream in = null;
        String[] outdata = (String[]) null;
        try {
            DataInputStream in2 = new DataInputStream(MFDevice.getResourceAsStream(filename));
            try {
                outdata = new String[in2.readInt()];
                for (int i = 0; i < outdata.length; i++) {
                    outdata[i] = in2.readUTF();
                }
                if (in2 != null) {
                    try {
                        in2.close();
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
                in = in2;
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e3) {
                    }
                }
                return outdata;
            } catch (Throwable th) {
                th = th;
                in = in2;
                if (in != null) {
                    try {
                        in.close();
                    } catch (Exception e4) {
                    }
                }
                throw th;
            }
        } catch (Exception e5) {
        } catch (Throwable th2) {
        }
        return outdata;
    }

    public static final String[] loadText(String var0) {
        String[] var3;
        try {
            String var1 = new String(getResource(var0), "UTF-8");
            var3 = divideText(var1);
        } catch (Exception var2) {
            var2.printStackTrace();
            var3 = null;
        }

        return var3;
    }

    public static void logicString(boolean var0, boolean var1) {
        if (var0) {
            if (downPermit && (scrollPageWait == 5 || scrollPageWait == 0)) {
                ++stringCursol;
            }

            if (scrollPageWait > 0) {
                --scrollPageWait;
            }
        } else if (var1) {
            if (upPermit && (scrollPageWait == 5 || scrollPageWait == 0)) {
                --stringCursol;
            }

            if (scrollPageWait > 0) {
                --scrollPageWait;
            }
        } else {
            scrollPageWait = 5;
        }

    }

    public static void setBackColor(int var0) {
        backColor = var0;
    }

    public static void setBmfColor(int var0) {
        switch (var0) {
            case 0:
                currentBmFont = bmFont;
                break;
            case 1:
                currentBmFont = bmFontYellow;
                break;
            case 2:
                currentBmFont = bmFontGreen;
                break;
            case 3:
                currentBmFont = bmFontGray;
        }

    }

    public static void setBorderColor(int var0) {
        borderColor = var0;
    }

    public static final void setClip(MFGraphics var0, int var1, int var2, int var3, int var4) {
        var1 = zoomOut(var1);
        var2 = zoomOut(var2);
        var3 = zoomOut(var3);
        var4 = zoomOut(var4);
        var0.setClip(var1, var2, var3, var4);
    }

    public static void vibrate() {
        if (GlobalResource.vibrationConfig == 1) {
            MFDevice.vibrateByTime(100);
        } else if (GlobalResource.vibrationConfig == 2) {
            MFDevice.vibrateByTime(300);
        } else if (GlobalResource.vibrationConfig == 3) {
            MFDevice.vibrateByTime(600);
        }

    }

    public static int zoomIn(int var0) {
        return var0 << 0;
    }

    public static int zoomIn(int var0, boolean var1) {
        if (!var1) {
            var0 = zoomIn(var0);
        } else {
            var0 <<= 0;
        }

        return var0;
    }

    public static int zoomOut(int var0) {
        return var0 >> 0;
    }
    
    public static int zoomIn2(int var0) {
        return var0 << 1;
    }

    public static int zoomIn2(int var0, boolean var1) {
        if (!var1) {
            var0 = zoomIn(var0);
        } else {
            var0 <<= 1;
        }

        return var0;
    }

    public static int zoomOut2(int var0) {
        return var0 >> 1;
    }
}

