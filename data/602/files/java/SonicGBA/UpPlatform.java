package SonicGBA;

import GameEngine.time.GameTime;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class UpPlatform extends Platform {
   private boolean initFlag;
   private int offsetY = 256;
   private int posOriginalY;
   private int velocity = 128;

   protected UpPlatform(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posOriginalY = this.posY;
      this.initFlag = false;
   }

   public void doInitWhileInCamera() {
      this.posY = this.posOriginalY;
      this.used = false;
      this.initFlag = true;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.initFlag) {
         if (player.isFootOnObject(this) && this.worldInstance.getWorldY(var1.collisionRect.x0, var1.footPointY - 1536, 1, 0) != -1000 && this.worldInstance.getWorldY(var1.collisionRect.x1, var1.footPointY - 1536, 1, 0) != -1000) {
            var1.setDie(false);
         }

         super.doWhileCollision(var1, var2);
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.initFlag) {
         MFImage var5 = platformImage;
         int var3 = this.posX;
         int var2 = this.posY;
         int var4 = this.offsetY;
         this.drawInMap(var1, var5, var3, var2 + 768 + var4, 33);
      }

   }

   public void logic() {
      int var1 = this.posX;
      int var2 = this.posY;
      if (this.initFlag) {
         this.refreshCollisionRect(this.posX, this.posY);
         if (!screenRect.collisionChk(this.collisionRect)) {
            this.initFlag = false;
         }
      } else {
         if (player.isFootOnObject(this)) {
            this.checkWithPlayer(this.posX, this.posY, this.posX, this.posY + this.velocity);
            this.posY = GameTime.advance(this, "posY", this.posY, -(this.velocity));
         } else {
            this.checkWithPlayer(this.posX, this.posY, this.posX, this.posY + this.velocity);
            if (this.posY < this.posOriginalY) {
               this.posY = GameTime.advancePosition(this, "posY", this.posY, "velocity", this.velocity);
            } else {
               this.posY = this.posOriginalY;
            }
         }

         this.checkWithPlayer(var1, var2, this.posX, this.posY);
      }

   }
}
