package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Boom extends EnemyObject {
   private static final int COLLISION_HEIGHT = 64;
   private static final int COLLISION_WIDTH = 64;
   private AnimationDrawer boomdrawer;

   protected Boom(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (BoomAni == null) {
         BoomAni = new Animation("/animation/boom");
      }

      this.boomdrawer = BoomAni.getDrawer(0, false, 0);
      this.posX = var2;
      this.posY = var3;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(BoomAni);
      BoomAni = null;
   }

   public void close() {
      this.boomdrawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.boomdrawer, this.posX, this.posY);
      }

   }

   public void logic() {
      this.refreshCollisionRect(this.posX, this.posY);
      if (this.boomdrawer.checkEnd()) {
         this.dead = true;
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 32, var2 - 32, 64, 64);
   }
}
