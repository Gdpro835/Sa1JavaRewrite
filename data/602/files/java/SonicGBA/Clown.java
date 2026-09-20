package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Clown extends EnemyObject {
   private static final int COLLISION_HEIGHT = 3072;
   private static final int COLLISION_WIDTH = 1792;
   private static Animation clownAnimation;
   private int limitLeftX;
   private int limitRightX;
   private boolean touching;
   private int velocity = 192;

   protected Clown(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.limitLeftX = this.posX;
      this.limitRightX = this.posX + this.mWidth;
      if (clownAnimation == null) {
         clownAnimation = new Animation("/animation/clown");
      }

      this.drawer = clownAnimation.getDrawer(0, true, 0);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(clownAnimation);
      clownAnimation = null;
   }

   public void PlayerHurtBall(PlayerObject var1, int var2) {
      int var4 = -player.getVelX();
      if (this.velocity > 0) {
         if (player.getVelX() < 0) {
            this.velocity = -this.velocity;
            this.drawer.setActionId(0);
            this.drawer.setTrans(2);
         }
      } else if (player.getVelX() > 0) {
         this.velocity = -this.velocity;
         this.drawer.setActionId(0);
         this.drawer.setTrans(0);
      }

      int var3 = player.getAnimationId();
      switch(var2) {
      case 0:
         var1.beStop(this.collisionRect.y1, var2, this);
         break;
      case 1:
      case 4:
         var1.beStop(this.collisionRect.y0, var2, this);
         break;
      case 2:
         var1.beStop(this.collisionRect.x1, var2, this);
         break;
      case 3:
         var1.beStop(this.collisionRect.x0, var2, this);
      }

      player.setVelX(var4);
      player.setAnimationId(var3);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && var1 == player && !this.touching) {
         if (player.isAttackingEnemy()) {
            if (player.isOnGound()) {
               this.PlayerHurtBall(var1, var2);
            } else {
               player.doAttackPose(this, var2);
               this.beAttack();
            }
         } else {
            player.beHurt();
         }

         this.touching = true;
      }

   }

   public void doWhileNoCollision() {
      this.touching = false;
   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.drawer);
         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var2 = this.posX;
         int var1 = this.posY;
         if (this.velocity > 0) {
            this.posX = GameTime.advancePosition(this, "posX", this.posX, "velocity", this.velocity);
            this.drawer.setActionId(0);
            this.drawer.setTrans(2);
            if (this.posX >= this.limitRightX) {
               this.posX = this.limitRightX;
               this.velocity = -this.velocity;
            }
         } else {
            this.posX = GameTime.advancePosition(this, "posX", this.posX, "velocity", this.velocity);
            this.drawer.setActionId(0);
            this.drawer.setTrans(0);
            if (this.posX <= this.limitLeftX) {
               this.posX = this.limitLeftX;
               this.velocity = -this.velocity;
            }
         }

         this.posY = this.getGroundY(this.posX, this.posY);
         this.checkWithPlayer(var2, var1, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 896, var2 - 3072, 1792, 3072);
   }
}
