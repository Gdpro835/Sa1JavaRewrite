package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class AspirateBubble extends EnemyObject {
   private static final int BUBBLE_SHAKE_SPEED = 16;
   private static final int BUBBLE_UP_SPEED = 120;
   private static Animation BubbleAnimation;
   private static final int COLLISION_HEIGHT = 512;
   private static final int COLLISION_WIDTH = 512;
   private AnimationDrawer bubbledrawer;
   private int frame;
   private int velx;
   private int waterLevel;

   protected AspirateBubble(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (BubbleAnimation == null) {
         BubbleAnimation = new Animation("/animation/aspirate");
      }

      this.bubbledrawer = BubbleAnimation.getDrawer(0, true, 0);
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
         if (!this.isInCamera() || this.posY - 256 < StageManager.getWaterLevel() << 6) {
            this.dead = true;
         }

         ++this.frame;
         this.frame %= 96;
         this.posY -= 120;
         byte var1;
         if (this.frame <= 48) {
            var1 = 16;
         } else {
            var1 = -16;
         }

         this.velx = var1;
         this.posX += this.velx;
         this.refreshCollisionRect(this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 256, var2 - 256, 512, 512);
   }
}
