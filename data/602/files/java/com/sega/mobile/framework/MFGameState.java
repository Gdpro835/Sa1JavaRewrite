package com.sega.mobile.framework;

import com.sega.mobile.framework.device.MFGraphics;

public interface MFGameState {
    public static final int VERSION = 104;

    int getFrameTime();

    void onEnter();

    void onExit();

    void onKeyDown(int i);

    void onPause();

    void onRender(MFGraphics mFGraphics);

    void onRender(MFGraphics mFGraphics, int i);

    void onResume();

    void onTick();
}
