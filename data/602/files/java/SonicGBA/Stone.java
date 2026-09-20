package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Stone extends GimmickObject {
   private static final int STONE_HEIGHT = 1728;
   private static final int STONE_OFFSETY = -128;
   private static final int STONE_WIDTH = 2624;
   private static MFImage image;

   public Stone(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (image == null) {
         try {
            image = MFImage.createImage("/gimmick/stone.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      this.used = false;
   }

   public static void releaseAllResource() {
      image = null;
   }

   public void close() {
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (!this.used && var3 != 7 && var3 != 6 && var3 != 22 && var3 != 19 && var3 != 12) {
         var1.doAttackPose(this, var2);
         soundInstance.playSe(29);
         this.used = true;
         Effect.showEffect(rockBreakAnimation, 0, this.posX >> 6, this.posY >> 6, 0);
         player.cancelFootObject();
         player.setVelY(GRAVITY);
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
               var1.doAttackPose(this, var2);
               soundInstance.playSe(29);
               break;
            }

            var1.beStop(this.collisionRect.y0, var2, this);
            break;
         case 2:
         case 3:
            if (var1 == player && var1.isAttackingItem() && this.firstTouch && player.collisionState != 0) {
               this.used = true;
               var1.doAttackPose(this, var2);
               soundInstance.playSe(29);
            } else {
               var1.beStop(this.collisionRect.y0, var2, this);
            }
         }

         if (this.used) {
            Effect.showEffect(rockBreakAnimation, 0, this.posX >> 6, this.posY >> 6, 0);
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.used) {
         this.drawInMap(var1, image, this.posX, this.posY - 128, 33);
      }

   }

   public int getPaintLayer() {
      return 0;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1312, var2 - 1728, 2624, 1728);
   }
}
