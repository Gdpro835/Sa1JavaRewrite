//
// Decompiled by FernFlower - 799ms
//
package Lib;

public class crlFP32 {
    private static final int DIGITS = 4;
    public static int DIGIT_0Dot28d = div(1792, 6400);
    private static final int DIGIT_MULTIPLIER = 10000;
    public static final int ERROR_BAD_INPUT = 1;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_OVERFLOW = 2;
    public static final int FIXED_1 = 64;
    private static final int FIXED_MASK = 63;
    public static final int FIXED_POINT_PRECISION = 6;
    public static final int MAX_VALUE = Integer.MAX_VALUE;
    static final int MBOOSTER_MAX_INSTANCES = 0;
    public static final int PI = div(20096, 6400);
    public static final int SHIFT_SCALE = 18;
    public static int errorCode = 0;
    public static int objCount;

    public static int abs(int var0) {
        if (var0 < 0) {
            var0 = -var0;
        }

        return var0;
    }

    public static int actTan(int var0) {
        if (Math.abs(var0) <= 64) {
            var0 = div(var0, mul(DIGIT_0Dot28d, sqr(var0)) + 64);
        } else {
            int var1 = div(-var0, sqr(var0) + DIGIT_0Dot28d);
            if (var0 < -1) {
                var0 = var1 - PI / 2;
            } else {
                var0 = var1 + PI / 2;
            }
        }

        return var0;
    }

    public static int actTan(int var0, int var1) {
        if (var0 == 0 && var1 == 0) {
            var0 = 0;
        } else if (var1 > 0) {
            var0 = actTan(div(var0, var1));
        } else if (var1 < 0) {
            if (var0 < 0) {
                var0 = -(PI - actTan(div(var0, var1)));
            } else {
                var0 = PI - actTan(div(-var0, var1));
            }
        } else if (var0 >= 0) {
            var0 = PI / 2;
        } else {
            var0 = -PI / 2;
        }

        return var0;
    }

    public static int actTanDegree(int var0, int var1) {
        var0 = actTan(var0, var1);
        return var0 * 180 / PI;
    }

    public static final int div(int var0, int var1) {
        boolean var4 = false;
        if (var1 != 64) {
            if ((var1 & 63) == 0) {
                var0 /= var1 >> 6;
            } else {
                boolean var3;
                if (var0 < 0) {
                    var3 = true;
                } else {
                    var3 = false;
                }

                if (var1 < 0) {
                    var4 = true;
                }

                int var2 = var0;
                if (var0 < 0) {
                    var2 = -var0;
                }

                var0 = var1;
                if (var1 < 0) {
                    var0 = -var1;
                }

                int var5 = 6;
                var1 = var2;
                var2 = var5;

                while(true) {
                    if (var1 > var0) {
                        var5 = var1;
                    } else {
                        var5 = var0;
                    }

                    if (var5 < 1 << 31 - var2) {
                        var0 = (var1 << var2) / var0 << 6 - var2;
                        if (var3 ^ var4) {
                            var0 = -var0;
                        }
                        break;
                    }

                    var1 >>= 1;
                    var0 >>= 1;
                    --var2;
                }
            }
        }

        return var0;
    }

    public static final int mul(int var0, int var1) {
        boolean var4 = false;
        if ((var0 & 63) == 0) {
            var0 = (var0 >> 6) * var1;
        } else if ((var1 & 63) == 0) {
            var0 = (var1 >> 6) * var0;
        } else {
            boolean var3;
            if (var0 < 0) {
                var3 = true;
            } else {
                var3 = false;
            }

            if (var1 < 0) {
                var4 = true;
            }

            int var2 = var0;
            if (var0 < 0) {
                var2 = -var0;
            }

            var0 = var1;
            if (var1 < 0) {
                var0 = -var1;
            }

            int var6 = 6;
            int var5 = 63;
            var1 = var2;
            var2 = var6;

            while(true) {
                if (var1 >= var0) {
                    var6 = var1;
                } else {
                    var6 = var0;
                }

                if (var6 < 1 << 31 - var2) {
                    var0 = ((var1 >> var2) * (var0 >> var2) << var2) + ((var1 & var5) * (var0 & var5) >> var2) + ((~var5 & var1) * (var0 & var5) >> var2) + ((var1 & var5) * (~var5 & var0) >> var2) << 6 - var2;
                    if (var0 < 0) {
                        errorCode = 2;
                    }

                    if (var3 ^ var4) {
                        var0 = -var0;
                    }
                    break;
                }

                var1 >>= 1;
                var0 >>= 1;
                var5 >>= 1;
                --var2;
            }
        }

        return var0;
    }

    private static int round(int var0) {
        int var1 = 32;
        if (var0 < 0) {
            var1 = -32;
        }

        return var0 + var1;
    }

    public static int sqr(int var0) {
        return mul(var0, var0);
    }

    public static final int sqrt(int var0) {
        if (var0 < 0) {
            errorCode = 1;
        }

        if (var0 == 0) {
            var0 = 0;
        } else {
            int var1;
            if (var0 > 630400) {
                var1 = mul(var0, 15) + 4096;
            } else if (var0 > 128000) {
                var1 = mul(var0, 32) + 1856;
            } else {
                var1 = mul(var0, 55) + 1152;
            }

            int var2 = 0;
            int var3 = 6;

            while(true) {
                int var4 = div(var0, var1);
                var1 = var1 + var4 >> 1;
                if (var1 == var2 || var3 == 0) {
                    if (var1 < 0) {
                        errorCode = 2;
                    }

                    var0 = var1;
                    break;
                }

                var2 = var1;
                --var3;
            }
        }

        return var0;
    }

    public static final int toFP(int var0) {
        return var0 << 6;
    }

    public static final int toInt(int var0) {
        return round(var0) >> 6;
    }
}

