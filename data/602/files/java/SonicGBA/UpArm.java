package SonicGBA;

import GameEngine.Key;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class UpArm extends GimmickObject {
   private static final int ARM_HEIGHT = 19;
   private static final int ARM_OFFSET = 14;
   private static final int ARM_PULL_OFFSET_Y = 128;
   private static final int ARM_WIDTH = 24;
   private static final int ARM_X = 0;
   private static final int ARM_Y = 32;
   private static final int BAR_HEIGHT = 32;
   private static final int BAR_WIDTH = 4;
   private static final int BAR_X = 10;
   private static final int BAR_Y = 0;
   private static final int COLLISION_HEIGHT = 320;
   private static final int COLLISION_WIDTH = 896;
   private static final int DOWNVELOCITY = 960;
   private static final int DRAW_HEIGHT = 3264;
   private static final int DRAW_WIDTH = 1536;
   private static final int MOVE_DISTANCE = 7680;
   private static final byte STATE_PULL = 1;
   private static final byte STATE_RETURN = 3;
   private static final byte STATE_WAIT = 0;
   private static final byte STATE_WAIT_2 = 2;
   private static final int UPVELOCITY = 480;
   private static final int WAIT_COUNT_2 = 15;
   private static int frame;
   private static MFImage image;
   private CollisionRect collisionRect2;
   private byte state;
   private int upLimit;
   private int waitCount;

   protected UpArm(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.upLimit = this.posY - 7680;
      this.posX += 128;
      if (image == null) {
         try {
            image = MFImage.createImage("/gimmick/up_arm.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.collisionRect2.collisionChk(var1.collisionRect)) {
         switch(this.state) {
         case 0:
            this.state = 1;
            player.resetPlayerDegree();
            var1.setOutOfControl(this);
            player.doPullMotion(this.posX, this.posY + 128);
            SoundSystem.getInstance().playSe(51);
            frame = 1;
         }
      }

   }

   public void draw(MFGraphics var1) {
      int var2;
      for(var2 = this.upLimit; var2 < this.posY - 896 - 2048; var2 += 2048) {
         this.drawInMap(var1, image, 10, 0, 4, 32, 0, this.posX, var2, 17);
      }

      int var3;
      MFImage var5;
      if (var2 < this.posY - 896) {
         var5 = image;
         int var4 = this.posY;
         var3 = this.posX;
         this.drawInMap(var1, var5, 10, 0, 4, var4 - 896 - var2 >> 6, 0, var3, var2, 17);
      }

      var5 = image;
      var3 = this.posX;
      var2 = this.posY;
      this.drawInMap(var1, var5, 0, 32, 24, 19, 0, var3, var2 - 896, 17);
   }

   public void logic() {
      if (this.waitCount > 0) {
         --this.waitCount;
      }

      switch(this.state) {
      case 1:
         ++frame;
         frame %= 100;
         this.posY -= 480;
         if (this.posY < this.upLimit) {
            this.posY = this.upLimit;
            soundInstance.stopLoopSe();
         } else if (frame == 4) {
            soundInstance.playLoopSe(52);
         }

         player.doPullMotion(this.posX, this.posY + 128);
         if (this.posY == this.upLimit && Key.press(16777216)) {
            player.outOfControl = false;
            player.doJump();
            player.isOnlyJump = true;
            this.state = 2;
            this.waitCount = 15;
            soundInstance.stopLoopSe();
         }
         break;
      case 2:
         if (this.waitCount == 0) {
            this.state = 3;
         }
         break;
      case 3:
         this.posY += 960;
         if (this.posY >= this.upLimit + 7680) {
            this.posY = this.upLimit + 7680;
            this.state = 0;
            player.isOnlyJump = false;
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var5 = this.collisionRect;
      int var4 = this.upLimit;
      int var3;
      if (var2 - this.upLimit <= 0) {
         var3 = 1;
      } else {
         var3 = var2 - this.upLimit;
      }

      var5.setRect(var1 - 768, var4, 1536, var3);
      if (this.collisionRect2 == null) {
         this.collisionRect2 = new CollisionRect();
      }

      var5 = this.collisionRect2;
      var5.setRect(var1 - 448, var2, 896, 320);
   }
}
