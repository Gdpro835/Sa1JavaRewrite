package com.sega.engine.action;

public interface ACWorldCalUser extends ACMoveCalUser {
    void doWhileLand(int i);

    void doWhileLeaveGround();

    void doWhileTouchWorld(int i, int i2);

    int getBodyDegree();

    int getBodyOffset();

    int getFootOffset();

    int getFootX();

    int getFootY();

    int getMinDegreeToLeaveGround();

    int getPressToGround();
}
