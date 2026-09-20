//
// Decompiled by FernFlower - 1249ms
//
package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;

class WaterSlip extends GimmickObject {
    private static final int COLLISION_HEIGHT = 2048;
    private static final int COLLISION_WIDTH = 4096;
    private static final int DRAW_OFFSET_X = -1024;
    private static AnimationDrawer drawer;
    private static AnimationDrawer drawer2;
    private static AnimationDrawer drawer3;
    private static AnimationDrawer drawer4;
    private static int frame;
    private boolean isActive;
    private boolean isTouchSand = false;
    private int spaceX = -6144;
    private int spaceY = 3072;

    protected WaterSlip(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
        super(var1, var2, var3, var4, var5, var6, var7);
        if (StageManager.getCurrentZoneId() == 1) {
            if (drawer == null) {
                drawer = (new Animation("/animation/water_fall_02")).getDrawer(0, true, 0);
                drawer.setPause(true);
            }
        } else if (StageManager.getCurrentZoneId() == 5) {
            Animation var8;
            if (this.iLeft == 1) {
                if (drawer == null || drawer2 == null) {
                    var8 = new Animation("/animation/sand_06");
                    drawer = var8.getDrawer(1, true, 0);
                    drawer.setPause(true);
                    drawer2 = var8.getDrawer(0, true, 0);
                    drawer2.setPause(true);
                }

                this.spaceY = 3072;
                this.spaceX = -6144;
            } else if (this.iLeft == 0) {
                if (drawer3 == null || drawer4 == null) {
                    var8 = new Animation("/animation/sand_04");
                    drawer3 = var8.getDrawer(0, true, 0);
                    drawer3.setPause(true);
                    drawer4 = var8.getDrawer(1, true, 0);
                    drawer4.setPause(true);
                }

                this.spaceY = 4096;
                this.spaceX = 4096;
            }
        }

        this.isTouchSand = false;
        this.isActive = false;
    }

    public static void releaseAllResource() {
        Animation.closeAnimationDrawer(drawer);
        Animation.closeAnimationDrawer(drawer2);
        Animation.closeAnimationDrawer(drawer3);
        Animation.closeAnimationDrawer(drawer4);
        drawer = null;
        drawer2 = null;
        drawer3 = null;
        drawer4 = null;
    }

    public static void staticLogic() {
        if (drawer != null) {
            drawer.moveOn();
        }

        if (drawer2 != null) {
            drawer2.moveOn();
        }

        if (drawer3 != null) {
            drawer3.moveOn();
        }

        if (drawer4 != null) {
            drawer4.moveOn();
        }

    }

    public void doWhileCollision(PlayerObject var1, int var2) {
        if (var1.collisionState == 0) {
            var1.setSlip();
            ++frame;
            this.isActive = true;
            if (this.iLeft == 0) {
                player.fallinSandSlipState = 1;
            } else if (this.iLeft == 1) {
                player.fallinSandSlipState = 2;
            }
        }

    }

    public void doWhileNoCollision() {
        super.doWhileNoCollision();
        this.isTouchSand = false;
        if (this.isActive) {
            player.fallinSandSlipState = 0;
            this.isActive = false;
        }

    }

    public void draw(MFGraphics var1) {
        if (StageManager.getCurrentZoneId() == 1) {
            this.drawInMap(var1, drawer);
        } else if (StageManager.getCurrentZoneId() == 5) {
            int var2 = 0;
            int var4 = 0;
            int var3;
            if (this.iLeft == 1) {
                var3 = this.collisionRect.y0 + 1024;

                for(var2 = var4; var3 < this.collisionRect.y1 - 1536; var2 += this.spaceX) {
                    if (var3 == this.collisionRect.y0 + 1024 && this.iTop == 0) {
                        this.drawInMap(var1, drawer2, this.collisionRect.x1 - 1024 + var2, var3);
                    } else {
                        this.drawInMap(var1, drawer, this.collisionRect.x1 - 1024 + var2, var3);
                    }

                    var3 += this.spaceY;
                }
            } else {
                int var7 = this.collisionRect.x0;
                int var5 = camera.x;
                int var8 = this.collisionRect.y0;
                var4 = camera.y;
                int var6 = this.collisionRect.getWidth();
                var3 = this.collisionRect.getHeight();
                MyAPI.setClip(var1, (var7 >> 6) - var5, (var8 >> 6) - var4, var6 >> 6, var3 >> 6);

                for(var3 = this.collisionRect.y0 + 1024; var3 < this.collisionRect.y1; var2 += this.spaceX) {
                    this.drawInMap(var1, drawer3, this.collisionRect.x0 + var2, var3);
                    var3 += this.spaceY;
                }

                var3 = SCREEN_WIDTH;
                var2 = SCREEN_HEIGHT;
                MyAPI.setClip(var1, 0, 0, var3, var2);
            }
        }

        this.drawCollisionRect(var1);
    }
}

