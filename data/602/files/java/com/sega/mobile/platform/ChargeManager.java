package com.sega.mobile.platform;

public class ChargeManager {
    private static Chargeable content;

    public static Chargeable getChargeContent() {
        content = new TeleSmsChargeContent();
        return content;
    }
}
