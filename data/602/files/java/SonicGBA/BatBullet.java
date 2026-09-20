package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class BatBullet extends BulletObject {
   private static final int COLLISION_HEIGHT = 1536;
   private static final int COLLISION_WIDTH = 1536;
   private boolean isboom;

   protected BatBullet(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4, false);
      if (batbulletAnimation == null) {
         batbulletAnimation = new Animation("/animation/bat_bullet");
      }

      this.drawer = batbulletAnimation.getDrawer(0, true, 0);
      this.isboom = false;
   }

   public void bulletLogic() {
      int var1 = this.posX;
      int var2 = this.posY;
      this.velY = GameTime.advance(this, "velY", this.velY, GRAVITY);
      this.posY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
      if (this.posY >= this.getGroundY(this.posX, this.posY) - 768) {
         this.posY = this.getGroundY(this.posX, this.posY) - 768;
         this.drawer.setActionId(1);
         this.drawer.setLoop(false);
         this.isboom = true;
         this.velY = 0;
      } else if (this.IsHitted()) {
         this.drawer.setActionId(1);
         this.drawer.setLoop(false);
         this.isboom = true;
         this.velY = 20;
      }

      this.checkWithPlayer(var1, var2, this.posX, this.posY);
   }

   public boolean chkDestroy() {
      boolean var1;
      if (!super.chkDestroy() && !this.drawer.checkEnd()) {
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

      this.collisionRect.draw(var1, camera);
   }

   public void refreshCollisionRect(int var1, int var2) {
      if (!this.isboom) {
         CollisionRect var3 = this.collisionRect;
         var3.setRect(var1 - 768, var2 - 768, 1536, 1536);
      }

   }
}
