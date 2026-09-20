package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class CageButton extends GimmickObject {
   private static final int COLLISION_HEIGHT = 896;
   private static final int COLLISION_WIDTH = 2176;
   private static final int PUSH_OFFSET_Y = 320;
   private static MFImage cageButtonImage = null;
   private int posOriginalY;

   protected CageButton(int var1, int var2) {
      super(0, var1, var2, 0, 0, 0, 0);
      if (cageButtonImage == null) {
         try {
            cageButtonImage = MFImage.createImage("/gimmick/cage_button.png");
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

   }

   public static void releaseAllResource() {
      cageButtonImage = null;
   }

   public void close() {
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (var1 instanceof PlayerAmy && var1.myAnimationID != 7 && !this.used) {
         this.used = true;
         isUnlockCage = true;
         this.posOriginalY = this.posY;
         var1.setCelebrate();
         boolean var4;
         if (var1.faceDirection) {
            var4 = false;
         } else {
            var4 = true;
         }

         var1.faceDirection = var4;
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1 instanceof PlayerKnuckles && var1.myAnimationID >= 19 && var1.myAnimationID <= 22) {
         var1.beStop(0, var2, this);
      } else if (!this.used) {
         if (var2 != 0) {
            var1.beStop(0, var2, this);
         }

         if (var2 == 1 && !this.used) {
            this.used = true;
            isUnlockCage = true;
            this.posOriginalY = this.posY;
            var1.setCelebrate();
         }
      }

   }

   public void drawButton(MFGraphics var1) {
      this.drawInMap(var1, cageButtonImage, 33);
   }

   public void logic() {
      if (this.used) {
         this.checkWithPlayer(this.posX, this.posY, this.posX, this.posOriginalY + 320);
         this.posY = this.posOriginalY + 320;
      } else {
         this.checkWithPlayer(this.posX, this.posY, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1088, var2 - 896, 2176, 896);
   }
}
