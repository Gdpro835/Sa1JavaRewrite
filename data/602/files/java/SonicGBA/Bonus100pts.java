package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Bonus100pts extends EnemyObject {
   private static Animation BonusAnimation;
   private static final int COLLISION_HEIGHT = 64;
   private static final int COLLISION_WIDTH = 64;
   private AnimationDrawer bonusdrawer;
   private int frame;
   private int movement = 240;

   protected Bonus100pts(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (BonusAnimation == null) {
         BonusAnimation = new Animation("/animation/100pts");
      }

      this.bonusdrawer = BonusAnimation.getDrawer(0, false, 0);
      this.frame = 0;
      this.posX = var2;
      this.posY = var3;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(BonusAnimation);
      BonusAnimation = null;
   }

   public void close() {
      this.bonusdrawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.bonusdrawer, this.posX, this.posY);
      }

   }

   public void logic() {
      if (!this.dead) {
         if (this.frame > 24) {
            this.dead = true;
         }

         ++this.frame;
         if (this.frame < 16) {
            this.posY -= this.movement;
         }

         this.refreshCollisionRect(this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 32, var2 - 32, 64, 64);
   }
}
