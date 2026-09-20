package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import com.sega.mobile.framework.device.MFGraphics;

class DrownBubble extends EnemyObject {
   private static final int BUBBLE_UP_SPEED = 64;
   private static Animation BubbleAnimation;
   private static final int COLLISION_HEIGHT = 512;
   private static final int COLLISION_WIDTH = 512;
   private AnimationDrawer bubbledrawer;
   private int waterLevel;

   protected DrownBubble(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (BubbleAnimation == null) {
         BubbleAnimation = new Animation("/animation/bubble_up");
      }

      Animation var8 = BubbleAnimation;
      byte var9;
      if (MyRandom.nextInt(0, 10) > 5) {
         var9 = 1;
      } else {
         var9 = 2;
      }

      this.bubbledrawer = var8.getDrawer(var9, true, 0);
      this.waterLevel = StageManager.getWaterLevel() << 6;
      this.posX = var2;
      this.posY = var3;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(BubbleAnimation);
      BubbleAnimation = null;
   }

   public void close() {
      this.bubbledrawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.bubbledrawer);
      }

   }

   public int getPaintLayer() {
      return 1;
   }

   public void logic() {
      if (!this.dead) {
         if (!this.isInCamera() || this.posY - 256 < this.waterLevel) {
            this.dead = true;
         }

         this.posY -= 64;
         this.refreshCollisionRect(this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 256, var2 - 256, 512, 512);
   }
}
