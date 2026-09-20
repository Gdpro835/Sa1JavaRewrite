package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class BossF3Bomb extends BulletObject {
   private static final int BOOM_V1 = 720;
   private static final int BOOM_V2 = 360;
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 1024;
   private int vel_x;
   private int vel_y;
   private int velorg_y;

   protected BossF3Bomb(int var1, int var2, int var3, int var4) {
      super(var1, var2, 0, 0, false);
      if (bossf3bombAnimation == null) {
         bossf3bombAnimation = new Animation("/animation/bossf3_bullet");
      }

      this.drawer = bossf3bombAnimation.getDrawer(0, true, 0);
      if (var4 == 1) {
         if (var3 == 0) {
            this.vel_x = 720;
            this.vel_y = -720;
            this.velorg_y = -720;
         } else if (var3 == 1) {
            this.vel_x = 360;
            this.vel_y = -1080;
            this.velorg_y = -1080;
         }
      } else if (var4 == 0) {
         if (var3 == 0) {
            this.vel_x = -720;
            this.vel_y = -720;
            this.velorg_y = -720;
         } else if (var3 == 1) {
            this.vel_x = -360;
            this.vel_y = -1080;
            this.velorg_y = -1080;
         }
      }

      this.posX = var1;
      this.posY = var2;
   }

   public void bulletLogic() {
      this.posX = GameTime.advancePosition(this, "posX", this.posX, "vel_x", this.vel_x);
      if (this.posY + this.vel_y >= this.getGroundY(this.posX, this.posY + this.vel_y)) {
         this.posY = this.getGroundY(this.posX, this.posY + this.vel_y);
         this.velorg_y = this.velorg_y * 7 / 8;
         this.vel_y = this.velorg_y;
      } else {
         this.vel_y = GameTime.advance(this, "vel_y", this.vel_y, GRAVITY);
         this.posY = GameTime.advancePosition(this, "posY", this.posY, "vel_y", this.vel_y);
      }

      this.refreshCollisionRect(this.posX, this.posY);
   }

   public boolean chkDestroy() {
      boolean var1;
      if (this.isInCamera() && !this.isFarAwayCamera()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 512, var2 - 1024, 1024, 1024);
   }
}
