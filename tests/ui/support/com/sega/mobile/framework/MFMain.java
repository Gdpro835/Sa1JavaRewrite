package com.sega.mobile.framework;

/** Android Activity boundary substituted only in the headless menu suite. */
public class MFMain {
    public static boolean browser, multiplayer, cheat, SUPERINNORMALSTAGE;
    public static int tails, tapCount;
    public static SonicGBA.PlayerObject playermulti;
    public static MFMain getInstance() { throw new AssertionError("Unexpected Activity access in menu test"); }
    public android.os.Handler getHandler() { throw new AssertionError("Unexpected UI message in menu test"); }
}
