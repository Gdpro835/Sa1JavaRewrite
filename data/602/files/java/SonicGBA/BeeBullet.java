package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class BeeBullet extends BulletObject {
   private static final int COLLISION_HEIGHT = 512;
   private static final int COLLISION_WIDTH = 512;

   protected BeeBullet(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4, true);
      if (beebulletAnimation == null) {
         beebulletAnimation = new Animation("/animation/bee_bullet");
      }

      this.drawer = beebulletAnimation.getDrawer(0, true, 0);
   }

   public void bulletLogic() {
      this.checkWithPlayer(this.posX, this.posY, this.posX + this.velX, this.posY + this.velY);
      this.posX += this.velX;
      this.posY += this.velY;
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
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 256, var2 - 256, 512, 512);
   }
}
