package com.x.y.sdk.gt;

public class GtConfig {
    private static final String gtId = "3121871559b2c1c5c405475623878055";
    private static final String gtKey = "190b6c02d513b2dee6d9f028f855e13f";
    private static final boolean newFailBack = true;

    public static String getGtId() {
        return gtId;
    }

    public static String getGtKey() {
        return gtKey;
    }

    public static boolean isNewFailBack() {
        return newFailBack;
    }
}