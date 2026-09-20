package SonicGBA;

import GameEngine.time.GameTime;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;

class BossF3Ray extends BulletObject {
   private static final int ATTACK_PLUS_HEIGHT = 512;
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 18432;
   private int directFlag;
   private int fadevalue;
   private int frame_cn;

   protected BossF3Ray(int var1, int var2, int var3) {
      super(var1, var2, 0, 0, false);
      this.directFlag = var3;
      this.posX = var1;
      this.posY = var2;
      this.frame_cn = GameTime.set(this, "frame_cn", 0);
      this.fadevalue = 200;
   }

   public void bulletLogic() {
      this.frame_cn = GameTime.advance(this, "frame_cn", this.frame_cn, 1);
      this.refreshCollisionRect(this.posX, this.posY);
   }

   public boolean chkDestroy() {
      boolean var1;
      if (this.frame_cn >= 8) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void draw(MFGraphics var1) {
      int var3 = MapManager.getCamera().x;
      int var2 = MapManager.getCamera().y;
      int var4;
      int var5;
      int var6;
      int var7;
      if (this.directFlag == 0) {
         var5 = this.fadevalue;
         var6 = this.posX;
         var4 = this.posY;
         var7 = this.frame_cn;
         MyAPI.drawFadeRange(var1, var5, (var6 - 18432 >> 6) - var3, (var4 - 512 >> 6) - var2, var7);
      } else {
         var7 = this.fadevalue;
         var5 = this.posX;
         var6 = this.posY;
         var4 = this.frame_cn;
         MyAPI.drawFadeRange(var1, var7, (var5 >> 6) - var3, (var6 - 512 >> 6) - var2, var4);
      }

      this.drawCollisionRect(var1);
   }

   public void refreshCollisionRect(int var1, int var2) {
      if (this.directFlag == 0) {
         this.collisionRect.setRect(var1 - 18432, var2 - 1024, 18432, 1536);
      } else {
         this.collisionRect.setRect(var1, var2 - 1024, 18432, 1536);
      }

   }
}
