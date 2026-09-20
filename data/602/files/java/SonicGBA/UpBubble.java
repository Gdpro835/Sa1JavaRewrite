package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import com.sega.mobile.framework.device.MFGraphics;

class UpBubble extends GimmickObject {
   private static Animation bubbleAnimation;
   private final int LIFE_RANGE = 6400;
   private int direct = 0;
   private AnimationDrawer drawer;
   private int[] group;
   private boolean initFlag;
   private int posOriginalY;
   private int velx = 0;
   private int vely = -120;

   public UpBubble(int var1, int var2) {
      super(0, var1, var2, 0, 0, 0, 0);
      int[] var3 = new int[12];
      var3[1] = 1;
      var3[2] = 2;
      var3[4] = 1;
      var3[5] = 2;
      var3[7] = 1;
      var3[8] = 2;
      var3[10] = 1;
      var3[11] = 2;
      this.group = var3;
      if (bubbleAnimation == null) {
         bubbleAnimation = new Animation("/animation/bubble_up");
      }

      if (bubbleAnimation != null) {
         this.drawer = bubbleAnimation.getDrawer(this.group[MyRandom.nextInt(this.group.length)], true, 0);
         this.direct = MyRandom.nextInt(-1, 1);
      }

      this.posOriginalY = this.posY;
      this.initFlag = false;
   }

   protected UpBubble(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      int[] var8 = new int[12];
      var8[1] = 1;
      var8[2] = 2;
      var8[4] = 1;
      var8[5] = 2;
      var8[7] = 1;
      var8[8] = 2;
      var8[10] = 1;
      var8[11] = 2;
      this.group = var8;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(bubbleAnimation);
      bubbleAnimation = null;
   }

   public boolean IsDie() {
      return this.initFlag;
   }

   public void close() {
      this.drawer = null;
   }

   public void draw(MFGraphics var1) {
      if (!this.initFlag) {
         this.drawInMap(var1, this.drawer, this.posX, this.posY);
      }

   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (!this.initFlag) {
         if (this.direct >= 0) {
            this.velx = MyRandom.nextInt(0, 20);
         } else {
            this.velx = MyRandom.nextInt(-20, 0);
         }

         this.posX += this.velx;
         this.posY += this.vely;
         this.refreshCollisionRect(this.posX, this.posY);
         if (this.posY <= StageManager.getWaterLevel() << 6) {
            this.initFlag = true;
         }
      }

   }

   public boolean objectChkDestroy() {
      return this.initFlag;
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 5, var2 - 5, 10, 10);
   }
}
