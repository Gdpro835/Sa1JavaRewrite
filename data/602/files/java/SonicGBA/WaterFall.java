//
// Decompiled by Procyon - 1556ms
//
package SonicGBA;

import GameEngine.time.GameTime;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import Lib.Animation;
import Lib.AnimationDrawer;

public class WaterFall extends GimmickObject
{
    private static int frame;
    public static AnimationDrawer waterFallDrawer1;
    public static AnimationDrawer waterFallDrawer2;
    public static AnimationDrawer waterFallDrawer3;
    public static AnimationDrawer waterFallDrawer4;
    private int drawSpace;
    private AnimationDrawer drawer;
    private boolean isActived;
    public static boolean isFirstTouchedSandFall;
    private boolean isTouchSand;
    
    protected WaterFall(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        super(n, n2, n3, n4, n5, n6, n7);
        this.isTouchSand = false;
        isFirstTouchedSandFall = false;
        WaterFall.player.initWaterFall();
        this.isActived = false;
        WaterFall.player.isCrashFallingSand = false;
        isFirstTouchedSandFall = false;
        if (StageManager.getCurrentZoneId() == 1) {
            this.isTouchSand = false;
        }
        else if (StageManager.getCurrentZoneId() == 5) {
            this.isTouchSand = false;
            WaterFall.frame = 0;
        }
    }
    
    public static void releaseAllResource() {
        Animation.closeAnimationDrawer(WaterFall.waterFallDrawer1);
        Animation.closeAnimationDrawer(WaterFall.waterFallDrawer2);
        Animation.closeAnimationDrawer(WaterFall.waterFallDrawer3);
        Animation.closeAnimationDrawer(WaterFall.waterFallDrawer4);
        WaterFall.waterFallDrawer1 = null;
        WaterFall.waterFallDrawer2 = null;
        WaterFall.waterFallDrawer3 = null;
        WaterFall.waterFallDrawer4 = null;
    }
    
    public static void staticLogic() {
        if (WaterFall.waterFallDrawer1 != null) {
            WaterFall.waterFallDrawer1.moveOn();
        }
        if (WaterFall.waterFallDrawer2 != null) {
            WaterFall.waterFallDrawer2.moveOn();
        }
        if (WaterFall.waterFallDrawer3 != null) {
            WaterFall.waterFallDrawer3.moveOn();
        }
        if (WaterFall.waterFallDrawer4 != null) {
            WaterFall.waterFallDrawer4.moveOn();
        }
    }
    
    public void close() {
        this.drawer = null;
    }
    
    public void doWhileCollision(final PlayerObject playerObject, int checkPositionY) {
        if (playerObject == WaterFall.player) {
            final CollisionRect collisionRect = this.collisionRect;
            final int checkPositionX = WaterFall.player.getCheckPositionX();
            checkPositionY = WaterFall.player.getCheckPositionY();
            if (collisionRect.collisionChk(checkPositionX, checkPositionY)) {
                if (StageManager.getCurrentZoneId() == 5) {
                    this.isActived = true;
                    if (WaterFall.player.collisionState == 1) {
                        WaterFall.player.collisionState = 3;
                    }
                    if (!this.isTouchSand) {
                        this.isTouchSand = true;
                    }
                    WaterFall.player.isCrashFallingSand = true;
                    if (WaterFall.player instanceof PlayerKnuckles && ((PlayerKnuckles)WaterFall.player).flying) {
                        WaterFall.player.setVelX(0);
                        ((PlayerKnuckles)WaterFall.player).flying = false;
                    }
                    if (WaterFall.player.getVelX() >= 498) {
                        WaterFall.player.setVelX(498);
                    }
                    else if (WaterFall.player.getVelX() <= -498) {
                        WaterFall.player.setVelX(-498);
                    }
                    if (!isFirstTouchedSandFall) {
                        WaterFall.soundInstance.playSe(70);
                        isFirstTouchedSandFall = true;
                        WaterFall.frame = 0;
                    }
                    if (isFirstTouchedSandFall) {
                        frame = GameTime.advanceOnce(WaterFall.class, "frame", frame, 1);
                        if (frame > 4 && !IsGamePause) {
                            soundInstance.playLoopSe(71);
                            frame = 0;
                        }
                    }
                }
                else if (StageManager.getCurrentZoneId() != 1) {
                    this.isActived = true;
                    WaterFall.frame = GameTime.advanceOnce(WaterFall.class, "frame", WaterFall.frame, 1);
                    WaterFall.frame %= 11;
                    if (!this.isTouchSand) {
                        this.isTouchSand = true;
                    }
                    WaterFall.player.isCrashFallingSand = false;
                }
                else {
                    WaterFall.player.isCrashFallingSand = false;
                }
                WaterFall.player.beWaterFall();
            }
        }
    }
    
    public void doWhileNoCollision() {
        if (this.isActived) {
            if (isFirstTouchedSandFall) {
                if (WaterFall.soundInstance.getPlayingLoopSeIndex() == 71) {
                    WaterFall.soundInstance.stopLoopSe();
                }
                isFirstTouchedSandFall = false;
            }
            WaterFall.player.isCrashFallingSand = false;
            this.isActived = false;
        }
    }
    
    public void draw(final MFGraphics mfGraphics) {
        if (this.drawer != null) {
            MyAPI.setClip(mfGraphics, (this.collisionRect.x0 >> 6) - WaterFall.camera.x, (this.collisionRect.y0 >> 6) - WaterFall.camera.y, this.collisionRect.getWidth() >> 6, this.collisionRect.getHeight() >> 6);
            for (int i = this.collisionRect.y0; i < this.collisionRect.y1; i += this.drawSpace) {
                this.drawInMap(mfGraphics, this.drawer, this.posX, i);
            }
            MyAPI.setClip(mfGraphics, 0, 0, WaterFall.SCREEN_WIDTH, WaterFall.SCREEN_HEIGHT);
        }
    }
    
    public int getPaintLayer() {
        return 2;
    }
    
    public void refreshCollisionRect(final int n, final int n2) {
        this.collisionRect.setRect(n, this.iTop * 512 + n2, this.mWidth, this.mHeight);
    }
}