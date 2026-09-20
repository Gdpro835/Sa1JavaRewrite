package com.sega.mobile.framework.utility;

import State.StringIndex;
import com.sega.mobile.define.MDPhone;
import com.sega.mobile.framework.device.MFGamePad;

public class MFMath {
    public static final int MAX_VALUE = Integer.MAX_VALUE;
    public static final int MIN_VALUE = -2147483647;
    private static int _digits = 4;
    private static int _dmul = 10000;
    private static int _fbits = 12;
    private static int _flt = 0;
    private static int _fmask = 4095;
    private static int _one = MFGamePad.KEY_NUM_6;
    private static int _pi = 12868;
    private static int[] e = {_one, 11134, 30266, 82270, 223636};
    public static int E = e[1];
    public static int PI = _pi;
    
    public static int setPrecision(int i) {
        if (i > 12 || i < 0) {
            return _digits;
        }
        _fbits = i;
        _one = 1 << i;
        _flt = 12 - i;
        _digits = 0;
        _dmul = 1;
        _fmask = _one - 1;
        PI = _pi >> _flt;
        E = e[1] >> _flt;
        int j = _one;
        while (j != 0) {
            j /= 10;
            _digits++;
            _dmul *= 10;
        }
        return _digits;
    }

    public static int getPrecision() {
        return _fbits;
    }

    public static int toInt(int i) {
        return round(i, 0) >> _fbits;
    }

    public static int toFP(int i) {
        return i << _fbits;
    }

    public static int convert(int i, int j) {
        byte byte0 = (byte) (i >= 0 ? 1 : -1);
        if (abs(j) >= 13) {
            return i;
        }
        if (_fbits < j) {
            return (((1 << ((j - _fbits) >> 1)) * byte0) + i) >> (j - _fbits);
        }
        return i << (_fbits - j);
    }

    public static int toFP(String s) {
        int i = 0;
        if (s.charAt(0) == '-') {
            i = 1;
        }
        String s1 = "-1";
        int j = s.indexOf(46);
        if (j >= 0) {
            s1 = s.substring(j + 1, s.length());
            while (s1.length() < _digits) {
                s1 = String.valueOf(s1) + "0";
            }
            if (s1.length() > _digits) {
                s1 = s1.substring(0, _digits);
            }
        } else {
            j = s.length();
        }
        int k = 0;
        if (i != j) {
            k = Integer.parseInt(s.substring(i, j));
        }
        int i1 = (k << _fbits) + (((Integer.parseInt(s1) + 1) << _fbits) / _dmul);
        if (i == 1) {
            return -i1;
        }
        return i1;
    }

    public static String toString(int i) {
        boolean flag = false;
        if (i < 0) {
            flag = true;
            i = -i;
        }
        int j = i >> _fbits;
        String s = Integer.toString((_dmul * (_fmask & i)) >> _fbits);
        while (s.length() < _digits) {
            s = "0" + s;
        }
        return String.valueOf(flag ? "-" : "") + Integer.toString(j) + "." + s;
    }

    public static String toString(int i, int j) {
        if (j > _digits) {
            j = _digits;
        }
        String s = toString(round(i, j));
        return s.substring(0, (s.length() - _digits) + j);
    }

    public static int max(int i, int j) {
        return i >= j ? i : j;
    }

    public static int min(int i, int j) {
        return j >= i ? i : j;
    }

    public static int round(int i, int j) {
        int k = 10;
        for (int l = 0; l < j; l++) {
            k *= 10;
        }
        int k2 = div(toFP(5), toFP(k));
        if (i < 0) {
            k2 = -k2;
        }
        return i + k2;
    }

    public static int mul(int i, int j) {
        boolean flag = false;
        int k = _fbits;
        int l = _fmask;
        if ((i & l) == 0) {
            return (i >> k) * j;
        }
        if ((j & l) == 0) {
            return (j >> k) * i;
        }
        if ((i < 0 && j > 0) || (i > 0 && j < 0)) {
            flag = true;
        }
        if (i < 0) {
            i = -i;
        }
        if (j < 0) {
            j = -j;
        }
        while (max(i, j) >= (1 << (31 - k))) {
            i >>= 1;
            j >>= 1;
            l >>= 1;
            k--;
        }
        int i1 = (((((i >> k) * (j >> k)) << k) + ((((i & l) * (j & l)) >> k) + ((((l ^ -1) & i) * (j & l)) >> k))) + (((i & l) * ((l ^ -1) & j)) >> k)) << (_fbits - k);
        if (i1 >= 0) {
            return flag ? -i1 : i1;
        }
        throw new ArithmeticException("Overflow");
    }

    public static int div(int i, int j) {
        boolean flag = false;
        int k = _fbits;
        if (j == _one) {
            return i;
        }
        if ((_fmask & j) == 0) {
            return i / (j >> k);
        }
        if ((i < 0 && j > 0) || (i > 0 && j < 0)) {
            flag = true;
        }
        if (i < 0) {
            i = -i;
        }
        if (j < 0) {
            j = -j;
        }
        while (max(i, j) >= (1 << (31 - k))) {
            i >>= 1;
            j >>= 1;
            k--;
        }
        int l = ((i << k) / j) << (_fbits - k);
        if (flag) {
            return -l;
        }
        return l;
    }

    public static int add(int i, int j) {
        return i + j;
    }

    public static int sub(int i, int j) {
        return i - j;
    }

    public static int abs(int i) {
        if (i < 0) {
            return -i;
        }
        return i;
    }

    public static int sqrt(int i, int j) {
        if (i < 0) {
            throw new ArithmeticException("Bad Input");
        } else if (i == 0) {
            return 0;
        } else {
            int k = (_one + i) >> 1;
            for (int l = 0; l < j; l++) {
                k = (div(i, k) + k) >> 1;
            }
            if (k >= 0) {
                return k;
            }
            throw new ArithmeticException("Overflow");
        }
    }

    public static int sqrt(int i) {
        return sqrt(i, 16);
    }

    public static int sin(int i) {
        int j = mul(i, div(toFP((int) 180), PI)) % toFP((int) 360);
        if (j < 0) {
            j += toFP((int) 360);
        }
        int k = j;
        if (j >= toFP(90) && j < toFP(270)) {
            k = toFP((int) 180) - j;
        } else if (j >= toFP(270) && j < toFP((int) 360)) {
            k = -(toFP((int) 360) - j);
        }
        int l = k / 90;
        int i1 = mul(l, l);
        return mul(mul(mul(mul(-18 >> _flt, i1) + (326 >> _flt), i1) - (2646 >> _flt), i1) + (6434 >> _flt), l);
    }

    public static int asin(int i) {
        if (abs(i) > _one) {
            throw new ArithmeticException("Bad Input");
        }
        boolean flag = i < 0;
        if (i < 0) {
            i = -i;
        }
        int k = (PI / 2) - mul(sqrt(_one - i), mul(mul(mul(mul(35 >> _flt, i) - (StringIndex.STR_RESET_RECORD >> _flt), i) + (347 >> _flt), i) - (877 >> _flt), i) + (6434 >> _flt));
        return flag ? -k : k;
    }

    public static int cos(int i) {
        return sin((PI / 2) - i);
    }

    public static int acos(int i) {
        return (PI / 2) - asin(i);
    }

    public static int tan(int i) {
        return div(sin(i), cos(i));
    }

    public static int cot(int i) {
        return div(cos(i), sin(i));
    }

    public static int atan(int i) {
        return asin(div(i, sqrt(_one + mul(i, i))));
    }

    public static int exp(int i) {
        if (i == 0) {
            return _one;
        }
        boolean flag = i < 0;
        int i2 = abs(i);
        int j = i2 >> _fbits;
        int k = _one;
        for (int l = 0; l < j / 4; l++) {
            k = mul(k, e[4] >> _flt);
        }
        if (j % 4 > 0) {
            k = mul(k, e[j % 4] >> _flt);
        }
        int i3 = i2 & _fmask;
        if (i3 > 0) {
            int i1 = _one;
            int j1 = 0;
            int k1 = 1;
            for (int l1 = 0; l1 < 16; l1++) {
                j1 += i1 / k1;
                i1 = mul(i1, i3);
                k1 *= l1 + 1;
                if (k1 > i1 || i1 <= 0 || k1 <= 0) {
                    break;
                }
            }
            k = mul(k, j1);
        }
        if (flag) {
            k = div(_one, k);
        }
        return k;
    }

    public static int log(int i) {
        int k;
        if (i <= 0) {
            throw new ArithmeticException("Bad Input");
        }
        int j = 0;
        int l = 0;
        while (i >= (_one << 1)) {
            i >>= 1;
            l++;
        }
        int i1 = l * (2839 >> _flt);
        int j1 = 0;
        if (i < _one) {
            return -log(div(_one, i));
        }
        int i2 = i - _one;
        for (int k1 = 1; k1 < 20; k1++) {
            if (j == 0) {
                k = i2;
            } else {
                k = mul(j, i2);
            }
            if (k == 0) {
                break;
            }
            j1 += ((k1 % 2 != 0 ? 1 : -1) * k) / k1;
            j = k;
        }
        return i1 + j1;
    }

    public static int pow(int i, int j) {
        int k;
        boolean flag = j < 0;
        int k2 = _one;
        int j2 = abs(j);
        int l = j2 >> _fbits;
        while (true) {
            int l2 = l;
            l = l2 - 1;
            if (l2 <= 0) {
                break;
            }
            k2 = mul(k2, i);
        }
        if (k2 < 0) {
            throw new ArithmeticException("Overflow");
        }
        if (i != 0) {
            k = mul(k2, exp(mul(log(i), _fmask & j2)));
        } else {
            k = 0;
        }
        if (flag) {
            return div(_one, k);
        }
        return k;
    }

    public static int atan2(int y, int x) {
        if (y == 0 && x == 0) {
            return 0;
        }
        if (x > 0) {
            return atan(div(y, x));
        }
        if (x >= 0) {
            return y >= 0 ? PI / 2 : (-PI) / 2;
        }
        if (y < 0) {
            return (-PI) + atan(div(y, x));
        }
        return PI - atan(abs(div(y, x)));
    }
}
