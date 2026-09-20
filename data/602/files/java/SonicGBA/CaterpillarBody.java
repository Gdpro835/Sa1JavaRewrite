package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;

class CaterpillarBody extends EnemyObject {
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 1024;
   private Caterpillar controller;
   private boolean isHead = false;

   protected CaterpillarBody(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, Caterpillar var9) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.isHead = var8;
      this.controller = var9;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (!this.dead) {
         if (this.controller.dead) {
            this.dead = true;
         } else if (var1 == player) {
            if (!this.isHead) {
               player.beHurt();
            } else {
               player.doAttackPose(this, var2);
               this.beAttack();
               this.controller.setDead();
               this.dead = true;
            }
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead) {
         if (this.controller.dead) {
            this.dead = true;
         } else if (var1 == player) {
            if (!this.isHead) {
               player.beHurt();
            } else if (player.isAttackingEnemy()) {
               player.doAttackPose(this, var2);
               this.beAttack();
               this.controller.setDead();
               this.dead = true;
            } else {
               player.beHurt();
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
   }

   public void logic(int var1, int var2) {
      if (!this.dead) {
         if (this.controller.dead) {
            this.dead = true;
         }

         int var4 = this.posX;
         int var3 = this.posY;
         this.posX = var1;
         this.posY = var2;
         this.checkWithPlayer(var4, var3, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      if (!this.dead) {
         CollisionRect var3 = this.collisionRect;
         var3.setRect(var1 - 512, var2 - 512, 1024, 1024);
      }

   }
}
