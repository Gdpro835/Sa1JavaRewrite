package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.utility.MFMath;

class Boss6Bullet extends BulletObject {
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 1024;
   private static final int TOTAL_SPEED = 240;

   protected Boss6Bullet(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4, false);
      if (boss6bulletAnimation == null) {
         boss6bulletAnimation = new Animation("/animation/boss6_bullet");
      }

      this.drawer = boss6bulletAnimation.getDrawer(0, true, 0);
      this.posX = var1;
      this.posY = var2;
      var2 = this.posX;
      int var5 = this.posX;
      int var6 = this.posY;
      var1 = this.posY;
      var1 = MFMath.sqrt((var3 - var2) * (var3 - var5) + (var4 - var6) * (var4 - var1)) >> 6;
      this.velX = (var3 - this.posX) * 240 / var1;
      this.velY = (var4 - this.posY) * 240 / var1;
   }

   public void bulletLogic() {
      int nextX = GameTime.advancePosition(this, "posX", this.posX, "velX", this.velX);
      int nextY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
      this.checkWithPlayer(this.posX, this.posY, nextX, nextY);
      this.posX = nextX;
      this.posY = nextY;
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
      this.collisionRect.setRect(var1 - 512, var2 - 512, 1024, 1024);
   }
}
