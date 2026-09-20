package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Shatter extends GimmickObject {
   private static final int COLLISION_HEIGHT = 3072;
   private static final int COLLISION_WIDTH = 1792;
   private static final int VELOCITY = 600;
   private static MFImage image;
   private int posYOriginal;
   private boolean trigger = false;

   protected Shatter(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.trigger = false;
      this.posYOriginal = this.posY - 3072;
      if (image == null) {
         try {
            image = MFImage.createImage("/gimmick/shatter.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

   }

   public static void releaseAllResource() {
      image = null;
   }

   public void close() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.trigger) {
         CollisionRect var4 = this.collisionRect;
         int var3 = var1.getBodyPositionX();
         var2 = var1.getBodyPositionY();
         var4.collisionChk(var3, var2);
         this.trigger = true;
         this.posY = this.posYOriginal;
         this.refreshCollisionRect(this.posX, this.posY);
      } else if (var2 != 2 && var2 != 3) {
         var1.beStop(0, 2, this);
      } else {
         var1.beStop(0, var2, this);
      }

   }

   public void doWhileNoCollision() {
      if (this.trigger && !this.isInCamera()) {
         this.trigger = false;
         this.posY = this.posYOriginal + 3072;
         this.refreshCollisionRect(this.posX, this.posY);
      }

   }

   public void draw(MFGraphics var1) {
      if (this.trigger) {
         this.drawInMap(var1, image, 17);
      }

      this.drawCollisionRect(var1);
   }

   public void logic() {
      if (this.trigger) {
         int var1 = this.posY;
         this.posY += 600;
         if (this.posY >= this.posYOriginal + 3072) {
            this.posY = this.posYOriginal + 3072;
         }

         this.checkWithPlayer(this.posX, var1, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      if (!this.trigger) {
         this.collisionRect.setRect(var1 + 512, var2, this.mWidth, this.mHeight);
      } else {
         CollisionRect var3 = this.collisionRect;
         var3.setRect(var1 - 896, var2, 1792, 3072);
      }

   }
}
