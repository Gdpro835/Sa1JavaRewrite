package SonicGBA;

import GameEngine.Key;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Furiko extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_OFFSET_Y = 960;
   private static final int COLLISION_WIDTH = 1024;
   private static final int DRAW_HEIGHT = 24;
   private static final int DRAW_WIDTH = 16;
   private static final int LEAVE_COUNT = 10;
   private static final int RADIUS = 4928;
   private static final int RING_NUM = 3;
   private static final int RING_SPACE = 960;
   private static int degree = 2240;
   private static int lineVelocity = 0;
   private int centerX;
   private int centerY;
   private int leaveCount = 0;
   private int thisDegree;
   private boolean touching = false;

   protected Furiko(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (Arm.baseImage == null) {
         try {
            Arm.baseImage = MFImage.createImage("/gimmick/part_1_2.png");
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }

      if (hookImage == null) {
         try {
            hookImage = MFImage.createImage("/gimmick/hook.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      this.centerX = this.posX;
      this.centerY = this.posY;
   }

   public static void releaseAllResource() {
   }

   public static void staticLogic() {
      lineVelocity += (GRAVITY - 60) * MyAPI.dSin((degree >> 6) - 90) / 100;
      degree -= ((lineVelocity << 6) / 4928 << 6) * 180 / 201;
   }

   public void close() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.touching && this.firstTouch && this.leaveCount == 0) {
         this.touching = true;
         var1.setOutOfControl(this);
      }

   }

   public void draw(MFGraphics var1) {
      int var2;
      int var3;
      int var4;
      int var5;
      MFImage var7;
      for(var2 = 0; var2 < 3; ++var2) {
         var4 = this.centerX;
         var5 = (var2 + 1) * 960 * MyAPI.dCos(this.thisDegree) / 100;
         var3 = this.centerY;
         int var6 = (var2 + 1) * 960 * MyAPI.dSin(this.thisDegree) / 100;
         var7 = Arm.baseImage;
         this.drawInMap(var1, var7, 8, 0, 8, 8, 0, var4 + var5, var3 + var6, 3);
      }

      var1.saveCanvas();
      var4 = this.posX;
      var5 = camera.x;
      var2 = this.posY;
      var3 = camera.y;
      var1.translateCanvas((var4 >> 6) - var5, (var2 >> 6) - var3);
      var1.rotateCanvas((float)(this.thisDegree - 90));
      var7 = hookImage;
      MyAPI.drawRegion(var1, var7, 16, 0, 16, 24, 0, 0, -22, 17);
      var1.restoreCanvas();
      this.drawCollisionRect(var1);
   }

   public void logic() {
      if (this.leaveCount > 0) {
         --this.leaveCount;
      }

      this.thisDegree = degree >> 6;
      if (this.iLeft != 0) {
         this.thisDegree = 180 - this.thisDegree;
      }

      this.posX = this.centerX + MyAPI.dCos(this.thisDegree) * 4928 / 100;
      this.posY = this.centerY + MyAPI.dSin(this.thisDegree) * 4928 / 100;
      this.refreshCollisionRect(this.posX, this.posY);
      if (this.touching) {
         player.doPullMotion(this.posX, this.posY);
         if (Key.press(Key.gUp | 16777216)) {
            player.outOfControl = false;
            this.touching = false;
            player.doJump();
            player.setFurikoOutVelX(this.thisDegree);
            this.leaveCount = 10;
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 512, var2 - 512, 1024, 1024);
   }
}
