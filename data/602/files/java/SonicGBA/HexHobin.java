package SonicGBA;

import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class HexHobin extends GimmickObject {
   private static final int COLLISION_HEIGHT = 2176;
   private static final int COLLISION_WIDTH = 3072;
   private static final int HOBIN_POWER = 1152;
   private static final int VELOCITY = 250;
   private static MFImage hexHobinImage = null;
   public HobinCal hobinCal;
   private boolean isH;
   public MoveCalculator moveCal;

   protected HexHobin(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (hexHobinImage == null) {
         try {
            hexHobinImage = MFImage.createImage("/gimmick/hex_hobin.png");
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }

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

      if (this.isH) {
         var2 = this.mWidth;
      } else {
         var2 = this.mHeight;
      }

      this.moveCal = new MoveCalculator(var1, var2, var8);
      this.hobinCal = new HobinCal();
   }

   public static void releaseAllResource() {
      hexHobinImage = null;
   }

   public void close() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      var1.beStop(this.collisionRect.y0, var2, this);
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
      switch(var2) {
      case 0:
         player.setVelY(1152);
         if (player.collisionRect.x0 + player.collisionRect.x1 >> 1 < this.collisionRect.x0 + this.collisionRect.x1 >> 1) {
            player.setVelX(-1152);
            this.hobinCal.startHobin(400, 135, 10);
         } else if (player.collisionRect.x0 + player.collisionRect.x1 >> 1 > this.collisionRect.x0 + this.collisionRect.x1 >> 1) {
            player.setVelX(1152);
            this.hobinCal.startHobin(400, 45, 10);
         }

         SoundSystem.getInstance().playSe(54);
         break;
      case 1:
         player.setVelY(-1152);
         if (player.collisionRect.x0 + player.collisionRect.x1 >> 1 < this.collisionRect.x0 + this.collisionRect.x1 >> 1) {
            player.setVelX(-1152);
            this.hobinCal.startHobin(400, 225, 10);
         } else if (player.collisionRect.x0 + player.collisionRect.x1 >> 1 > this.collisionRect.x0 + this.collisionRect.x1 >> 1) {
            player.setVelX(1152);
            this.hobinCal.startHobin(400, -45, 10);
         }

         SoundSystem.getInstance().playSe(54);
         break;
      case 2:
         player.setVelX(1152);
         this.hobinCal.startHobin(400, 180, 10);
         SoundSystem.getInstance().playSe(54);
         break;
      case 3:
         player.setVelX(-1152);
         this.hobinCal.startHobin(400, 0, 10);
         SoundSystem.getInstance().playSe(54);
      }

   }

   public void draw(MFGraphics var1) {
      MFImage var6 = hexHobinImage;
      int var4 = this.posX;
      int var3 = this.hobinCal.getPosOffsetX();
      int var5 = this.posY;
      int var2 = this.hobinCal.getPosOffsetY();
      this.drawInMap(var1, var6, var4 + var3, var5 + var2, 3);
      this.hobinCal.logic();
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      this.moveCal.logic();
      int var1 = this.posX;
      int var2 = this.posY;
      if (this.isH) {
         this.posX = this.moveCal.getPosition();
      } else {
         this.posY = this.moveCal.getPosition();
      }

      this.checkWithPlayer(var1, var2, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1536, var2 - 1088, 3072, 2176);
   }
}
