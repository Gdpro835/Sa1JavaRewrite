package SonicGBA;

import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Arm extends GimmickObject {
   private static final int ARM_DRAW_HEIGHT_1 = 31;
   private static final int ARM_DRAW_HEIGHT_2 = 36;
   private static final int ARM_DRAW_WIDTH = 36;
   private static final int BACK_WAIT_COUNT = 4;
   private static final int BAR_LENGTH = 1536;
   private static final int BAR_WIDTH = 384;
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_OFFSET_Y = 2304;
   private static final int COLLISION_WIDTH = 2048;
   private static final int PULL_POINT_OFFSET = -192;
   private static final byte STATE_BACK = 3;
   private static final byte STATE_BACK_WAIT = 5;
   private static final byte STATE_DOWN = 4;
   private static final byte STATE_PULLING = 2;
   private static final byte STATE_UP = 1;
   private static final byte STATE_WAIT = 0;
   private static final int UP_DISTANCE = 3328;
   private static final int VELOCITY = 300;
   public static MFImage armImage;
   public static MFImage armplusImage;
   public static MFImage barImage;
   public static MFImage baseImage;
   private static int frame;
   private int count;
   private int posXOriginal;
   private int posYOriginal;
   private byte state;

   protected Arm(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posYOriginal = this.posY;
      this.posXOriginal = this.posX;
      this.posY = this.posYOriginal + 3328;
      this.state = 0;
      if (armImage == null) {
         try {
            armImage = MFImage.createImage("/gimmick/arm.png");
         } catch (Exception var12) {
            var12.printStackTrace();
         }
      }

      if (barImage == null) {
         try {
            barImage = MFImage.createImage("/gimmick/arm_bar.png");
         } catch (Exception var11) {
            var11.printStackTrace();
         }
      }

      if (armplusImage == null) {
         try {
            armplusImage = MFImage.createImage("/gimmick/arm_plus.png");
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }

      if (baseImage == null) {
         try {
            baseImage = MFImage.createImage("/gimmick/part_1_2.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

   }

   public static void releaseAllResource() {
   }

   public boolean catching() {
      boolean var1;
      if (this.state != 1 && this.state != 2) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void close() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      switch(this.state) {
      case 0:
         SoundSystem.getInstance().playSe(51);
         this.state = 1;
         player.setOutOfControl(this);
         player.doWalkPoseInAir();
         player.degreeForDraw = player.faceDegree;
         isGotRings = false;
         frame = 1;
      default:
      }
   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, baseImage, 0, 0, 8, 8, 0, 17);

      int var2;
      for(var2 = 0; var2 < this.posY - this.posYOriginal - 1536; var2 += 1536) {
         this.drawInMap(var1, barImage, this.posX, this.posYOriginal + var2, 17);
      }

      MFImage var7 = barImage;
      int var4 = this.posY;
      int var6 = this.posYOriginal;
      int var3 = this.posX;
      int var5 = this.posYOriginal;
      this.drawInMap(var1, var7, 0, 0, 6, var4 - var6 - var2 >> 6, 0, var3, var5 + var2, 17);
      var7 = armplusImage;
      var3 = this.posX;
      var2 = this.posYOriginal;
      this.drawInMap(var1, var7, 0, 0, 8, 8, 1, var3, var2 - 256, 17);
      var7 = armImage;
      byte var8;
      if (this.catching()) {
         var8 = 0;
      } else {
         var8 = 31;
      }

      byte var9;
      if (this.catching()) {
         var9 = 31;
      } else {
         var9 = 36;
      }

      this.drawInMap(var1, var7, 0, var8, 36, var9, 0, 17);
      this.drawCollisionRect(var1);
   }

   public void logic() {
      if (this.count > 0) {
         --this.count;
      }

      switch(this.state) {
      case 1:
         this.posY -= 300;
         if (this.posY <= this.posYOriginal) {
            this.posY = this.posYOriginal;
            this.state = 2;
         }

         ++frame;
         if (frame == 5) {
            soundInstance.playLoopSe(52);
         }
         break;
      case 2:
         this.posX += 300;
         ++frame;
         if (this.posX >= this.posXOriginal + this.mWidth) {
            this.posX = this.posXOriginal + this.mWidth;
            player.outOfControl = false;
            player.collisionState = 1;
            player.velY = 0;
            player.doWalkPoseInAir();
            this.count = 4;
            this.state = 5;
            soundInstance.stopLoopSe();
         }
         break;
      case 3:
         this.posX -= 300;
         if (this.posX <= this.posXOriginal) {
            this.posX = this.posXOriginal;
            this.state = 4;
         }
         break;
      case 4:
         this.posY += 300;
         if (this.posY >= this.posYOriginal + 3328) {
            this.posY = this.posYOriginal + 3328;
            this.state = 0;
         }
         break;
      case 5:
         if (this.count == 0) {
            this.state = 3;
         }
      }

      this.refreshCollisionRect(this.posX, this.posY);
      if (player.outOfControl && player.outOfControlObject == this) {
         int var2 = player.getFootPositionX();
         int var3 = player.getFootPositionY();
         player.doPullMotion(this.posX, this.posY + 2304 - 192);
         PlayerObject var5 = player;
         int var1 = player.getFootPositionX();
         int var4 = player.getFootPositionY();
         var5.checkWithObject(var2, var3, var1, var4);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1024, var2 + 2304 - 1024, 2048, 1024);
   }
}
