package com.sega.engine.lib;

import com.sega.mobile.define.MDPhone;

public class MyAPI {
    public static final int FIXED_TWO_BASE = 7;
    public static final int[] sinData2;

    static {
        int[] iArr = new int[91];
        iArr[1] = 224;
        iArr[2] = 446;
        iArr[3] = 669;
        iArr[4] = 893;
        iArr[5] = 1116;
        iArr[6] = 1337;
        iArr[7] = 1560;
        iArr[8] = 1781;
        iArr[9] = 2001;
        iArr[10] = 2222;
        iArr[11] = 2442;
        iArr[12] = 2661;
        iArr[13] = 2878;
        iArr[14] = 3096;
        iArr[15] = 3312;
        iArr[16] = 3527;
        iArr[17] = 3742;
        iArr[18] = 3955;
        iArr[19] = 4167;
        iArr[20] = 4377;
        iArr[21] = 4587;
        iArr[22] = 4794;
        iArr[23] = 5000;
        iArr[24] = 5205;
        iArr[25] = 5409;
        iArr[26] = 5611;
        iArr[27] = 5811;
        iArr[28] = 6009;
        iArr[29] = 6205;
        iArr[30] = 6400;
        iArr[31] = 6592;
        iArr[32] = 6782;
        iArr[33] = 6970;
        iArr[34] = 7157;
        iArr[35] = 7342;
        iArr[36] = 7523;
        iArr[37] = 7703;
        iArr[38] = 7879;
        iArr[39] = 8055;
        iArr[40] = 8227;
        iArr[41] = 8396;
        iArr[42] = 8564;
        iArr[43] = 8729;
        iArr[44] = 8890;
        iArr[45] = 9050;
        iArr[46] = 9207;
        iArr[47] = 9360;
        iArr[48] = 9511;
        iArr[49] = 9660;
        iArr[50] = 9804;
        iArr[51] = 9946;
        iArr[52] = 10086;
        iArr[53] = 10222;
        iArr[54] = 10355;
        iArr[55] = 10484;
        iArr[56] = 10611;
        iArr[57] = 10735;
        iArr[58] = 10854;
        iArr[59] = 10972;
        iArr[60] = 11084;
        iArr[61] = 11194;
        iArr[62] = 11301;
        iArr[63] = 11404;
        iArr[64] = 11504;
        iArr[65] = 11600;
        iArr[66] = 11692;
        iArr[67] = 11782;
        iArr[68] = 11868;
        iArr[69] = 11950;
        iArr[70] = 12028;
        iArr[71] = 12102;
        iArr[72] = 12172;
        iArr[73] = 12240;
        iArr[74] = 12304;
        iArr[75] = 12363;
        iArr[76] = 12419;
        iArr[77] = 12472;
        iArr[78] = 12519;
        iArr[79] = 12564;
        iArr[80] = 12605;
        iArr[81] = 12642;
        iArr[82] = 12675;
        iArr[83] = 12704;
        iArr[84] = 12729;
        iArr[85] = 12751;
        iArr[86] = 12769;
        iArr[87] = 12782;
        iArr[88] = 12792;
        iArr[89] = 12797;
        iArr[90] = 12800;
        sinData2 = iArr;
    }

    public static int dSin(int tDeg) {
        while (tDeg < 0) {
            tDeg += 360;
        }
        int tsh = tDeg % 360;
        if (tsh >= 0 && tsh <= 90) {
            return sinData2[tsh] >>> 7;
        }
        if (tsh > 90 && tsh <= 180) {
            return sinData2[90 - (tsh - 90)] >>> 7;
        }
        if (tsh > 180 && tsh <= 270) {
            return (sinData2[tsh - 180] >>> 7) * -1;
        }
        if (tsh <= 270 || tsh > 359) {
            return 0;
        }
        return (sinData2[90 - (tsh - 270)] >>> 7) * -1;
    }

    public static int dCos(int tDeg) {
        return dSin(90 - tDeg);
    }
}
