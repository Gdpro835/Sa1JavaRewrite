package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class IronBall extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1920;
   private static final int COLLISION_WIDTH = 1920;
   private static MFImage image;
   private int initPos;
   private boolean isH;
   private MoveCalculator moveCal = null;
   private int offset_distance;

   protected IronBall(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      boolean var8;
      if (this.mWidth >= this.mHeight) {
         this.isH = true;
         if (this.iLeft == 0) {
            var8 = false;
         } else {
            var8 = true;
         }
      } else {
         this.isH = false;
         if (this.iTop == 0) {
            var8 = false;
         } else {
            var8 = true;
         }
      }

      if (this.isH) {
         var1 = this.posX;
      } else {
         var1 = this.posY;
      }

      this.initPos = var1;
      if (this.moveCal == null) {
         if (this.isH) {
            var1 = this.posX;
         } else {
            var1 = this.posY;
         }

         if (this.isH) {
            var2 = this.mWidth;
         } else {
            var2 = this.mHeight;
         }

         this.moveCal = new MoveCalculator(var1, var2, var8);
      }

      if (image == null) {
         if (StageManager.getCurrentZoneId() != 6) {
            image = MFImage.createImage("/gimmick/iron_ball.png");
         } else {
            image = MFImage.createImage("/gimmick/iron_ball6.png");
         }
      }

   }

   public static void releaseAllResource() {
      image = null;
   }

   public static void staticLogic() {
   }

   public void close() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      var1.beHurt();
   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, image, 3);
   }

   public void logic() {
      int var2 = this.posX;
      int var1 = this.posY;
      if (this.isH) {
         if (this.iLeft == 0) {
            this.posX = this.moveCal.getPosition();
         } else {
            this.offset_distance = this.initPos - this.moveCal.getPosition();
            this.posX = this.initPos + this.offset_distance;
         }
      } else if (this.iTop == 0) {
         this.posY = this.moveCal.getPosition();
      } else {
         this.offset_distance = this.initPos - this.moveCal.getPosition();
         this.posY = this.initPos + this.offset_distance;
      }

      this.checkWithPlayer(var2, var1, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 960, var2 - 960, 1920, 1920);
   }
}
