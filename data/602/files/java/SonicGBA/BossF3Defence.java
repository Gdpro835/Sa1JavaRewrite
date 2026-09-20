package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;

class BossF3Defence extends EnemyObject {
   private static final int COLLISION_HEIGHT = 2432;
   private static final int COLLISION_WIDTH = 2688;
   private boolean isAvaliable;
   private boolean isHurt;
   private BossF3 mboss;

   protected BossF3Defence(int var1, int var2, BossF3 var3) {
      super(30, var1, var2, 0, 0, 0, 0);
      this.posX = var1;
      this.posY = var2;
      this.isHurt = false;
      this.isAvaliable = true;
      this.mboss = var3;
   }

   private boolean isCircleNoneUpDefenceState() {
      boolean var1;
      if (this.mboss != null) {
         if (this.mboss.pro_state != 1 && this.mboss.pro_state != 2 && this.mboss.pro_state != 3 && this.mboss.pro_state != 4) {
            var1 = false;
         } else {
            var1 = true;
         }
      } else {
         var1 = false;
      }

      return var1;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (this.isAvaliable) {
         if (var1 == player) {
            if (!this.isCircleNoneUpDefenceState() || var2 == 1 || player.canBeHurt()) {
               player.beHurt();
               this.isHurt = true;
            }
         } else {
            this.isHurt = false;
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.isAvaliable) {
         if (var1 == player) {
            if (!this.isCircleNoneUpDefenceState() || var2 == 1 || player.canBeHurt()) {
               player.beHurt();
               this.isHurt = true;
            }
         } else {
            this.isHurt = false;
         }
      }

   }

   public void draw(MFGraphics var1) {
      this.drawCollisionRect(var1);
   }

   public boolean getHurtState() {
      return this.isHurt;
   }

   public void logic() {
   }

   public void logic(int var1, int var2) {
      this.posX = var1;
      this.posY = var2;
      var2 = this.posX;
      var1 = this.posY;
      this.refreshCollisionRect(this.posX, this.posY);
      this.checkWithPlayer(var2, var1, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1344, var2 - 960, 2688, 2432);
   }

   public void resetHurtState() {
      this.isHurt = false;
   }

   public void setAvaliable(boolean var1) {
      this.isAvaliable = var1;
   }
}
