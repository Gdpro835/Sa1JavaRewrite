package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class LizardBullet extends BulletObject {
   private static final int COLLISION_HEIGHT = 768;
   private static final int COLLISION_WIDTH = 768;
   private boolean isboom;

   protected LizardBullet(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4, true);
      if (lizardbulletAnimation == null) {
         lizardbulletAnimation = new Animation("/animation/lizard_bullet");
      }

      this.drawer = lizardbulletAnimation.getDrawer(0, true, 0);
      this.isboom = false;
   }

   public void bulletLogic() {
      int var2 = this.posX;
      int var1 = this.posY;
      if (this.velY < 0) {
         this.velY = GameTime.advance(this, "velY", this.velY, GRAVITY);
         this.posY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
      } else {
         this.velY = 0;
         this.drawer.setActionId(1);
         this.drawer.setLoop(false);
         this.isboom = true;
      }

      this.checkWithPlayer(var2, var1, this.posX, this.posY);
   }

   public boolean chkDestroy() {
      return this.drawer.checkEnd();
   }

   public void draw(MFGraphics var1) {
      if (!this.drawer.checkEnd()) {
         this.drawInMap(var1, this.drawer);
      }

      this.collisionRect.draw(var1, camera);
   }

   public void refreshCollisionRect(int var1, int var2) {
      if (!this.isboom) {
         CollisionRect var3 = this.collisionRect;
         var3.setRect(var1 - 384, var2 - 384, 768, 768);
      }

   }
}
