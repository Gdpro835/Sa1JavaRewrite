package com.sega.mobile.framework.port;

public class MFByteArray {
    public byte[] array;
    public int len;

    public MFByteArray(int length) {
        this.array = new byte[length];
        this.len = length;
    }
}
