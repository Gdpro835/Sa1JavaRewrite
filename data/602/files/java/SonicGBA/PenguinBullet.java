package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class PenguinBullet extends BulletObject {
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 1024;
   private boolean broken;
   private MapObject mapObj;

   protected PenguinBullet(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4, false);
      if (penguinbulletAnimation == null) {
         penguinbulletAnimation = new Animation("/animation/pen_bullet");
      }

      this.drawer = penguinbulletAnimation.getDrawer(0, true, 0);
      this.posX = var1;
      this.posY = var2;
      this.velX = var3;
      this.velY = var4;
      this.mapObj = new MapObject(this.posX, this.posY, 0, 0, this, 1);
      this.mapObj.setPosition(this.posX, this.posY, this.velX, 0, this);
      this.mapObj.setCrashCount(2);
      this.broken = false;
   }

   public void bulletLogic() {
      if (!this.drawer.checkEnd() && !this.IsHitted() && (this.mapObj.getVelX() != 0 || this.mapObj.getVelY() != 0)) {
         if (!this.mapObj.chkCrash()) {
            this.mapObj.logic();
            this.checkWithPlayer(this.posX, this.posY, this.mapObj.getPosX(), this.mapObj.getPosY());
            this.posX = this.mapObj.getPosX();
            this.posY = this.mapObj.getPosY();
         } else {
            this.drawer.setActionId(1);
            this.drawer.setLoop(false);
         }
      } else {
         this.broken = true;
      }

   }

   public boolean chkDestroy() {
      boolean var1;
      if (!this.broken && this.isInCamera()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void draw(MFGraphics var1) {
      if (!this.drawer.checkEnd()) {
         this.drawInMap(var1, this.drawer);
      }

      this.drawCollisionRect(var1);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 512, var2 - 1024, 1024, 1024);
   }
}
