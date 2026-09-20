package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;

class Boss5FlyDefence extends EnemyObject {
   private static boolean Hurt = false;
   private static boolean IsAvailable = false;
   private int AttackDirection;
   private int COLLISION_HEIGHT = 1472;
   private int COLLISION_WIDTH = 1920;

   protected Boss5FlyDefence(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      Hurt = false;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (IsAvailable) {
         if (var1 == player) {
            if (player.isAttackingEnemy()) {
               Hurt = true;
            } else {
               Hurt = false;
            }
         }
      } else {
         Hurt = false;
      }

   }

   public void draw(MFGraphics var1) {
      this.drawCollisionRect(var1);
   }

   public boolean getHurtState() {
      return Hurt;
   }

   public void logic() {
   }

   public void logic(int var1, int var2, int var3) {
      this.posX = var1;
      this.posY = var2;
      var1 = this.posX;
      var2 = this.posY;
      this.AttackDirection = var3;
      if (IsAvailable) {
         this.COLLISION_WIDTH = 1920;
         this.COLLISION_HEIGHT = 1472;
      } else {
         this.COLLISION_WIDTH = 0;
         this.COLLISION_HEIGHT = 0;
      }

      this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
      this.checkWithPlayer(var1, var2, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      int var3;
      int var4;
      int var5;
      CollisionRect var6;
      if (this.AttackDirection > 0) {
         var6 = this.collisionRect;
         var3 = this.COLLISION_HEIGHT;
         var5 = this.COLLISION_WIDTH;
         var4 = this.COLLISION_HEIGHT;
         var6.setRect(var1 - 448, var2 - var3, var5, var4);
      } else {
         var6 = this.collisionRect;
         var4 = this.COLLISION_HEIGHT;
         var5 = this.COLLISION_WIDTH;
         var3 = this.COLLISION_HEIGHT;
         var6.setRect(var1 - 1408, var2 - var4, var5, var3);
      }

   }

   public void setCollAvailable(boolean var1) {
      IsAvailable = var1;
   }

   public void setHurtState(boolean var1) {
      Hurt = var1;
   }
}
