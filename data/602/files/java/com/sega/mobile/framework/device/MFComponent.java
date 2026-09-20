package com.sega.mobile.framework.device;

public interface MFComponent {
    int getPointerID();

    int getPointerX();

    int getPointerY();

    void pointerDragged(int i, int i2, int i3);

    void pointerPressed(int i, int i2, int i3);

    void pointerReleased(int i, int i2, int i3);

    void reset();

    void tick();
}
