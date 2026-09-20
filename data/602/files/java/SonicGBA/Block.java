package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;

class Block extends GimmickObject {
   private static final int Y_OFFSET = 1024;
   private int collisionHeight;
   private int collisionWidth;
   private int direct;
   private int height = 0;
   private boolean isActive;
   private int width;

   protected Block(int var1, int var2, int var3, int var4, int var5) {
      super(99, var1, var2, 0, 0, var3, var4);
      this.posX = var1;
      this.posY = var2 + 1024;
      this.width = var3 * 8 << 6;
      this.collisionWidth = this.width;
      this.height = var4 * 4 << 6;
      this.direct = var5;
      this.isActive = false;
   }

   protected Block(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.direct = this.iLeft;
      this.isActive = false;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var2 == 1 || var2 == 0) {
         var1.beStop(0, var2, this);
      }

      if (var2 == 2 || var2 == 3) {
         if (var1.getFootPositionY() < this.collisionRect.y0 + this.mHeight * 3 / 4) {
            if (var1.getVelX() > 0 && var2 == 3) {
               var1.setFootPositionY(this.collisionRect.y0 - 64);
            } else if (var1.getVelX() < 0 && var2 == 2) {
               var1.setFootPositionY(this.collisionRect.y0 - 64);
            }
         } else {
            var1.beStop(0, var2, this);
         }
      }

   }

   public void doWhileNoCollision() {
   }

   public void draw(MFGraphics var1) {
      this.drawCollisionRect(var1);
   }

   public void logic() {
      if (this.collisionRect.collisionChk(player.getCollisionRect()) && player.collisionState == 2) {
         PlayerObject var4 = player;
         int var2 = player.footPointX;
         short var1;
         if (this.direct == 0) {
            var1 = -128;
         } else {
            var1 = 128;
         }

         int var3 = player.footPointY;
         var4.moveOnObject(var2 + var1, var3);
         this.isActive = true;
         player.isOnBlock = true;
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1, var2, this.mWidth, this.mHeight);
   }
}
