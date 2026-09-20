package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class DoubleGravityFlashBullet extends BulletObject {
   private int COLLISION_HEIGHT = 768;
   private int COLLISION_WIDTH = 768;

   protected DoubleGravityFlashBullet(int var1, int var2, int var3, int var4, int var5) {
      super(var1, var2, var3, var4, true);
      switch(var5) {
      case 0:
         this.COLLISION_WIDTH = 768;
         this.COLLISION_HEIGHT = 768;
         doublegravityflashbulletAnimation = null;
         doublegravityflashbulletAnimation = new Animation("/animation/dgfa_bullet");
         break;
      case 1:
         this.COLLISION_WIDTH = 640;
         this.COLLISION_HEIGHT = 640;
         doublegravityflashbulletAnimation = null;
         doublegravityflashbulletAnimation = new Animation("/animation/dgfn_bullet");
      }

      this.drawer = doublegravityflashbulletAnimation.getDrawer(0, true, 0);
   }

   public void bulletLogic() {
      int var2 = this.posX;
      int var1 = this.posY;
      this.posX = GameTime.advancePosition(this, "posX", this.posX, "velX", this.velX);
      this.velY = GameTime.advance(this, "velY", this.velY, GRAVITY);
      this.posY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
      this.checkWithPlayer(var2, var1, this.posX, this.posY);
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
      this.collisionRect.draw(var1, camera);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var7 = this.collisionRect;
      int var5 = this.COLLISION_WIDTH;
      int var6 = this.COLLISION_HEIGHT;
      int var4 = this.COLLISION_WIDTH;
      int var3 = this.COLLISION_HEIGHT;
      var7.setRect(var1 - (var5 >> 1), var2 - (var6 >> 1), var4, var3);
   }
}
