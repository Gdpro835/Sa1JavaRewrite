package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class LaserDamage extends BulletObject {
   private boolean isDead;
   private byte[] rect;

   protected LaserDamage(int var1, int var2) {
      super(var1, var2, 0, 0, false);
      if (laserAnimation == null) {
         laserAnimation = new Animation("/animation/boss_extra_bullet");
      }

      this.drawer = laserAnimation.getDrawer(0, false, 0);
      this.drawer.setPause(true);
      this.isDead = false;
      var1 = this.getGroundY(this.posX, this.posY);
      this.posY = var1;
   }

   public void bulletLogic() {
      if (this.drawer.checkEnd()) {
         this.isDead = true;
      } else {
         this.drawer.moveOn();
         this.rect = this.drawer.getARect();
         this.refreshCollisionRect(this.posX, this.posY);
         this.doWhileCollisionWrapWithPlayer();
      }

   }

   public boolean chkDestroy() {
      return this.isDead;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!player.isAttackingEnemy()) {
         player.beHurt();
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
   }

   public void refreshCollisionRect(int var1, int var2) {
      if (this.rect != null) {
         CollisionRect var7 = this.collisionRect;
         byte var5 = this.rect[0];
         byte var3 = this.rect[1];
         byte var4 = this.rect[2];
         byte var6 = this.rect[3];
         var7.setRect((var5 << 6) + var1, (var3 << 6) + var2, var4 << 6, var6 << 6);
      }

   }
}
