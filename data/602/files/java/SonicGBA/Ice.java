package SonicGBA;

import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Ice extends GimmickObject {
   private static final int ICE_SIZE = 2560;
   private static MFImage image;

   protected Ice(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (image == null) {
         image = MFImage.createImage("/gimmick/ice.png");
      }

      this.used = false;
   }

   public static void releaseAllResource() {
      image = null;
   }

   public void close() {
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (!this.used) {
         this.used = true;
         if (this.used) {
            Effect.showEffect(destroyEffectAnimation, 0, this.posX >> 6, this.posY - 1280 >> 6, 0);
            Effect.showEffect(iceBreakAnimation, 0, this.posX >> 6, this.posY - 1280 >> 6, 0);
            SoundSystem.getInstance().playSe(61);
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.used) {
         switch(var2) {
         case 0:
            var1.beStop(0, var2, this);
            break;
         case 1:
            if (var1 == player && var1.isAttackingEnemy() && this.firstTouch) {
               this.used = true;
               var1.setVelY(0);
               var1.animationID = 1;
               break;
            }

            var1.beStop(this.collisionRect.y0, var2, this);
            break;
         case 2:
         case 3:
            if (var1 == player && var1.isAttackingEnemy() && this.firstTouch && player.collisionState != 0) {
               this.used = true;
               var1.setVelY(0);
            } else {
               var1.beStop(this.collisionRect.y0, var2, this);
            }
         }

         if (this.used) {
            Effect.showEffect(destroyEffectAnimation, 0, this.posX >> 6, this.posY - 1280 >> 6, 0);
            Effect.showEffect(iceBreakAnimation, 0, this.posX >> 6, this.posY - 1280 >> 6, 0);
            SoundSystem.getInstance().playSe(61);
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.used) {
         this.drawInMap(var1, image, this.posX, this.posY, 33);
      }

   }

   public int getPaintLayer() {
      return 2;
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 1280, var2 - 2560, 2560, 2560);
   }
}
