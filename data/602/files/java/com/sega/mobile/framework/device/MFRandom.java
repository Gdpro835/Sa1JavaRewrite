package com.sega.mobile.framework.device;

import java.util.Random;

public class MFRandom {
    private Random mRandom;

    public MFRandom() {
        this.mRandom = new Random();
    }

    public MFRandom(long seed) {
        this.mRandom = new Random(seed);
    }

    public int nextInt() {
        return this.mRandom.nextInt();
    }

    public long nextLong() {
        return this.mRandom.nextLong();
    }
}
