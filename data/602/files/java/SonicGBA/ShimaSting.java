package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;

class ShimaSting extends GimmickObject {
   private static final int ATTACK_HEIGHT = 576;
   private static final int COLLISION_WIDTH = 3072;
   private static final int OFFSET_HEIGHT = 1024;
   private static final int STATE_BOTTOM = 2;
   private static final int STATE_NONE = 0;
   private static final int STATE_TOP = 1;
   private int attackState = 0;

   protected ShimaSting(int var1, int var2) {
      super(0, var1, var2, 0, 0, 0, 0);
      this.posX = var1;
      this.posY = var2;
      this.attackState = 0;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1 == player && this.attackState != 0) {
         var1.beHurt();
      }

   }

   public void draw(MFGraphics var1) {
      this.drawCollisionRect(var1);
   }

   public void logic(int var1, int var2, int var3) {
      int var4 = this.posX;
      int var5 = this.posY;
      this.posX = var1;
      this.posY = var2;
      if (var3 == 0) {
         this.attackState = 1;
      } else if (var3 == 4) {
         this.attackState = 2;
      } else {
         this.attackState = 0;
      }

      this.refreshCollisionRect(this.posX, this.posY);
      this.checkWithPlayer(var4, var5, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      if (this.attackState != 0) {
         CollisionRect var4 = this.collisionRect;
         short var3;
         if (this.attackState == 1) {
            var3 = -1600;
         } else {
            var3 = 1024;
         }

         var4.setRect(var1 - 1536, var3 + var2, 3072, 576);
      } else {
         this.collisionRect.setRect(var1, var2, 1, 1);
      }

   }
}
