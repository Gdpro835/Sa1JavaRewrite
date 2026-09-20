package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class MiraBullet extends BulletObject {
   private static final int COLLISION_HEIGHT = 640;
   private static final int COLLISION_WIDTH = 640;

   protected MiraBullet(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4, false);
      if (mirabulletAnimation == null) {
         mirabulletAnimation = new Animation("/animation/mira_bullet");
      }

      this.drawer = mirabulletAnimation.getDrawer(0, true, 0);
      this.posX = var1;
      this.posY = var2;
      this.velX = var3;
      this.velY = var4;
   }

   public void bulletLogic() {
      this.checkWithPlayer(this.posX, this.posY, this.posX + this.velX, this.posY);
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
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 320, var2 - 320, 640, 640);
   }
}
