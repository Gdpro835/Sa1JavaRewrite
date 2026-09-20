package SonicGBA;

import Lib.MyAPI;
import Lib.crlFP32;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BallHobin extends HexHobin {
   private static final int COLLISION_HEIGHT = 1536;
   private static final int COLLISION_WIDTH = 1536;
   private static final int HOBIN_POWER = 1100;
   private static MFImage ballHobinImage = null;
   private boolean CanAddScore = true;
   private int initPos;
   private boolean isH;
   private int offset_distance;

   protected BallHobin(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (ballHobinImage == null) {
         try {
            ballHobinImage = MFImage.createImage("/gimmick/ball_hobin.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      if (this.mWidth >= this.mHeight) {
         this.isH = true;
      } else {
         this.isH = false;
      }

      if (this.isH) {
         var1 = this.posX;
      } else {
         var1 = this.posY;
      }

      this.initPos = var1;
   }

   public static void releaseAllResource() {
      ballHobinImage = null;
   }

   public void close() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.firstTouch) {
         player.pipeOut();
         if (player.collisionState == 1 && var1.animationID != 4) {
            var1.beStop(this.collisionRect.y0, var2, this);
         }

         if (var2 == 3) {
            player.rightStopped = false;
            if (player instanceof PlayerKnuckles && (player.getCharacterAnimationID() == 19 || player.getCharacterAnimationID() == 20 || player.getCharacterAnimationID() == 21 || player.getCharacterAnimationID() == 22 || player.getCharacterAnimationID() == 29 || player.getCharacterAnimationID() == 30 || player.getCharacterAnimationID() == 31 || player.getCharacterAnimationID() == 32 || player.getCharacterAnimationID() == 33) || player instanceof PlayerTails && (player.getCharacterAnimationID() == 12 || player.getCharacterAnimationID() == 13 || player.getCharacterAnimationID() == 14)) {
               player.animationID = 4;
            }
         } else if (var2 == 2) {
            player.leftStopped = false;
            if (player instanceof PlayerKnuckles && (player.getCharacterAnimationID() == 19 || player.getCharacterAnimationID() == 20 || player.getCharacterAnimationID() == 21 || player.getCharacterAnimationID() == 22 || player.getCharacterAnimationID() == 29 || player.getCharacterAnimationID() == 30 || player.getCharacterAnimationID() == 31 || player.getCharacterAnimationID() == 32 || player.getCharacterAnimationID() == 33) || player instanceof PlayerTails && (player.getCharacterAnimationID() == 12 || player.getCharacterAnimationID() == 13 || player.getCharacterAnimationID() == 14)) {
               player.animationID = 4;
            }
         }

         if (player.collisionState != 0) {
            player.collisionState = 1;
         }

         player.dashRolling = false;
         var2 = player.getCheckPositionX() - this.collisionRect.getCenterX();
         int var3 = player.getCheckPositionY() - this.collisionRect.getCenterY();
         if (var2 != 0 || var3 != 0) {
            var2 = crlFP32.actTanDegree(var3, var2);
            var3 = (var2 + 360) % 360;
            this.hobinCal.startHobin(0, var3 + 180, 10);
            if (this.CanAddScore) {
               player.getBallHobinScore();
               this.CanAddScore = false;
            }

            var2 = MyAPI.dCos(var3) * 1100 / 100;
            var3 = MyAPI.dSin(var3) * 1100 / 100;
            player.setVelX(var2);
            player.setVelY(var3);
            if (var1.animationID != 4) {
               player.doWalkPoseInAir();
            }

            soundInstance.playSe(54);
         }
      }

   }

   public void doWhileNoCollision() {
      this.CanAddScore = true;
   }

   public void draw(MFGraphics var1) {
      MFImage var6 = ballHobinImage;
      int var3 = this.posX;
      int var5 = this.hobinCal.getPosOffsetX();
      int var2 = this.posY;
      int var4 = this.hobinCal.getPosOffsetY();
      this.drawInMap(var1, var6, var3 + var5, var2 + var4, 3);
      this.hobinCal.logic();
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      this.moveCal.logic();
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
      var3.setRect(var1 - 768, var2 - 768, 1536, 1536);
   }
}
