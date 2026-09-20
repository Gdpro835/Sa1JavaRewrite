package com.sega.mobile.platform;

public interface ChargeListener {
    void chargeExit(int i);

    void chargeFailed(int i);

    void chargeStart(int i);

    void chargeSuccessed(int i);
}
