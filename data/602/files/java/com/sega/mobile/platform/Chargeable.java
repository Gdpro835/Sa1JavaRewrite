package com.sega.mobile.platform;

import java.util.Hashtable;

public interface Chargeable {
    void charge(int i);

    void exitCharge();

    String getChargeText(int i);

    String getConnectText(int i);

    String getInfoText(int i);

    void initCharge();

    int loadConfig(Hashtable<String, String>[] hashtableArr);
}
