package com.sega.mobile.framework.port;

public class MFStringType {
    private String str;

    public MFStringType() {
        this.str = null;
    }

    public MFStringType(String s) {
        this.str = s;
    }

    public String stringValue() {
        return this.str;
    }

    public boolean equals(Object object) {
        if (this.str == null || !(object instanceof MFStringType)) {
            return false;
        }
        if (this.str != null) {
            return this.str.equals(((MFStringType) object).str);
        }
        if (((MFStringType) object).str == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.str.hashCode();
    }
}
