package com.sega.engine.lib;

import com.sega.mobile.define.MDPhone;

public class CrlFP32 {
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

    public static final int toInt(int l) {
        return round(l) >> 6;
    }

    private static int round(int i) {
        int k = 32;
        if (i < 0) {
            k = -32;
        }
        return i + k;
    }

    public static final int toFP(int i) {
        return i << 6;
    }

    public static final int mul(int i, int j) {
        boolean z;
        int i2;
        boolean z2 = false;
        if ((i & 63) == 0) {
            return (i >> 6) * j;
        }
        if ((j & 63) == 0) {
            return (j >> 6) * i;
        }
        if (i < 0) {
            z = true;
        } else {
            z = false;
        }
        if (j < 0) {
            z2 = true;
        }
        boolean flag = z ^ z2;
        if (i < 0) {
            i = -i;
        }
        if (j < 0) {
            j = -j;
        }
        int k = 6;
        int l = 63;
        while (true) {
            if (i >= j) {
                i2 = i;
            } else {
                i2 = j;
            }
            if (i2 < (1 << (31 - k))) {
                break;
            }
            i >>= 1;
            j >>= 1;
            l >>= 1;
            k--;
        }
        int i1 = (((((i >> k) * (j >> k)) << k) + ((((i & l) * (j & l)) >> k) + ((((l ^ -1) & i) * (j & l)) >> k))) + (((i & l) * ((l ^ -1) & j)) >> k)) << (6 - k);
        if (i1 < 0) {
            errorCode = 2;
        }
        if (flag) {
            return -i1;
        }
        return i1;
    }

    public static int abs(int i) {
        return i < 0 ? -i : i;
    }

    public static final int div(int i, int j) {
        boolean z;
        int i2;
        boolean z2 = false;
        if (j == 64) {
            return i;
        }
        if ((j & 63) == 0) {
            return i / (j >> 6);
        }
        if (i < 0) {
            z = true;
        } else {
            z = false;
        }
        if (j < 0) {
            z2 = true;
        }
        boolean flag = z ^ z2;
        if (i < 0) {
            i = -i;
        }
        if (j < 0) {
            j = -j;
        }
        int k = 6;
        while (true) {
            if (i > j) {
                i2 = i;
            } else {
                i2 = j;
            }
            if (i2 < (1 << (31 - k))) {
                break;
            }
            i >>= 1;
            j >>= 1;
            k--;
        }
        int l = ((i << k) / j) << (6 - k);
        if (flag) {
            return -l;
        }
        return l;
    }

    public static final int sqrt(int i) {
        int k;
        if (i < 0) {
            errorCode = 1;
        }
        if (i == 0) {
            return 0;
        }
        if (i > 630400) {
            k = mul(i, 15) + 4096;
        } else if (i > 128000) {
            k = mul(i, 32) + 1856;
        } else {
            k = mul(i, 55) + 1152;
        }
        int oldVal = 0;
        int count = 6;
        while (true) {
            int val = div(i, k);
            k = (k + val) >> 1;
            if (k == oldVal || count == 0) {
                break;
            }
            oldVal = k;
            count--;
        }
        if (k < 0) {
            errorCode = 2;
        }
        return k;
    }

    public static int actTan(int radian) {
        if (Math.abs(radian) <= 64) {
            return div(radian, mul(DIGIT_0Dot28d, sqr(radian)) + 64);
        }
        int retval = div(-radian, sqr(radian) + DIGIT_0Dot28d);
        if (radian < -1) {
            return retval - (PI / 2);
        }
        return retval + (PI / 2);
    }

    public static int sqr(int x) {
        return mul(x, x);
    }

    public static int actTan(int y, int x) {
        if (y == 0 && x == 0) {
            return 0;
        }
        if (x > 0) {
            return actTan(div(y, x));
        }
        if (x >= 0) {
            return y >= 0 ? PI / 2 : (-PI) / 2;
        }
        if (y < 0) {
            return -(PI - actTan(div(y, x)));
        }
        return PI - actTan(div(-y, x));
    }

    public static int actTanDegree(int y, int x) {
        int re = (actTan(y, x) * 180) / PI;
        while (re < 0) {
            re += 360;
        }
        return re % 360;
    }
}
