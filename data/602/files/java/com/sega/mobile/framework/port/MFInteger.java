package com.sega.mobile.framework.port;

public class MFInteger {
    public static int MAX_VALUE = Integer.MAX_VALUE;
    private int val;

    public MFInteger(int i) {
        this.val = i;
    }

    public static int parseInt(String str) {
        return Integer.parseInt(str);
    }

    public int intValue() {
        return this.val;
    }

    public boolean equals(Object object) {
        if (!(object instanceof MFInteger)) {
            return false;
        }
        if (this.val == ((MFInteger) object).val) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.val;
    }
}
