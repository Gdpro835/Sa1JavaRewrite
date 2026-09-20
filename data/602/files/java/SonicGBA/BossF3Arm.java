package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.device.MFGraphics;

class BossF3Arm extends EnemyObject {
   private static final int COLLISION_HEIGHT = 1280;
   private static final int COLLISION_WIDTH = 1280;
   private static Animation armAni;
   private int aniState;
   private AnimationDrawer armDrawer;
   private int catchState;
   private int collisionOffsetX;
   private int collisionOffsetY;
   private int degree;
   private int directFlag;
   private boolean isAvaliable;
   private boolean isCaught;
   private boolean isShaking;

   protected BossF3Arm(int var1, int var2, int var3) {
      super(30, var1, var2, 0, 0, 0, 0);
      this.directFlag = var3;
      this.posX = var1;
      this.posY = var2;
      if (armAni == null) {
         armAni = new Animation("/animation/bossf3_arm");
      }

      Animation var4 = armAni;
      byte var5;
      if (this.directFlag == 0) {
         var5 = 0;
      } else {
         var5 = 2;
      }

      this.armDrawer = var4.getDrawer(0, true, var5);
      this.isAvaliable = false;
      this.isCaught = false;
      this.isShaking = false;
      this.catchState = 0;
      this.collisionOffsetX = 0;
      this.collisionOffsetY = 0;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(armAni);
      armAni = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.isAvaliable) {
         if (var1 == player && this.catchState == 0 && player.canBeHurt()) {
            var1 = player;
            if (PlayerObject.getRingNum() > 0) {
               player.changeVisible(false);
               player.setAnimationId(52);
            } else if (!this.isShaking) {
               player.setDie(false);
            } else {
               player.setAnimationId(52);
            }

            this.isCaught = true;
            if (player instanceof PlayerTails) {
               ((PlayerTails)player).stopFly();
               player.animationID = 52;
               player.myAnimationID = 47;
            }
         }
      } else if (this.catchState == 0) {
         var1 = player;
         if (!PlayerObject.isTerminal && player.canBeHurt()) {
            player.beHurt();
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (this.isCaught && !player.isDead) {
         player.degreeForDraw = this.degree;
         player.drawCharacter(var1);
      }

      byte var2;
      int var3;
      if (this.degree != 0 && this.degree != 360) {
         Graphics var6 = (Graphics)var1.getSystemGraphics();
         var6.save();
         if (this.isShaking) {
            var6.translate((float)((this.posX >> 6) - camera.x), (float)((this.posY >> 6) - camera.y));
         } else {
            var3 = this.posX;
            int var4 = camera.x;
            if (this.directFlag == 0) {
               var2 = 16;
            } else {
               var2 = -16;
            }

            var6.translate((float)((var3 >> 6) - var4 + var2), (float)((this.posY >> 6) - camera.y));
         }

         var6.rotate((float)this.degree);
         if (this.isShaking) {
            if (this.directFlag == 0) {
               var2 = 16;
            } else {
               var2 = -16;
            }

            var6.translate((float)var2, 0.0F);
         }

         this.armDrawer.draw(var1, 0, 0);
         var6.restore();
      } else if (this.isShaking) {
         this.drawInMap(var1, this.armDrawer, this.posX, this.posY);
      } else {
         AnimationDrawer var5 = this.armDrawer;
         var3 = this.posX;
         if (this.directFlag == 0) {
            var2 = 16;
         } else {
            var2 = -16;
         }

         this.drawInMap(var1, var5, var3 + (var2 << 6), this.posY);
      }

      this.drawCollisionRect(var1);
   }

   public boolean getCaughtState() {
      return this.isCaught;
   }

   public void logic() {
   }

   public void logic(int var1, int var2) {
      this.posX = var1;
      this.posY = var2;
      var1 = this.posX;
      var2 = this.posY;
      if (this.isCaught && !player.isDead) {
         player.setFootPositionX(this.posX);
         player.setFootPositionY(this.posY);
         player.setVelX(0);
         player.setVelY(0);
      }

      this.refreshCollisionRect(this.posX, this.posY);
      this.checkWithPlayer(var1, var2, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 640 + this.collisionOffsetX, var2 - 640 + this.collisionOffsetY, 1280, 1280);
   }

   public void releasePlayer() {
      player.changeVisible(true);
      player.setMeetingBoss(true);
      player.beHurtNoRingLose();
      PlayerObject var2 = player;
      short var1;
      if (this.directFlag == 0) {
         var1 = 800;
      } else {
         var1 = -800;
      }

      var2.setVelX(var1);
      player.setVelY(-1200);
      player.isSharked = false;
   }

   public void setAniState(int var1) {
      this.aniState = var1;
      this.armDrawer.setActionId(var1);
      this.armDrawer.setLoop(false);
   }

   public void setAvaliable(boolean var1) {
      this.isAvaliable = var1;
   }

   public void setCatchState(int var1) {
      this.catchState = var1;
   }

   public void setCaughtFlag(boolean var1) {
      this.isCaught = var1;
   }

   public void setDegree(int var1) {
      this.degree = var1;
      int var3 = MyAPI.dCos(var1) * 15 / 100;
      byte var2;
      if (this.directFlag == 0) {
         var2 = -1;
      } else {
         var2 = 1;
      }

      this.collisionOffsetX = var3 * var2 << 6;
      int var5 = MyAPI.dSin(var1) * 40 / 100;
      byte var4;
      if (this.directFlag == 0) {
         var4 = -1;
      } else {
         var4 = 1;
      }

      this.collisionOffsetY = var5 * var4 << 6;
   }

   public void setShakeState(boolean var1) {
      this.isShaking = var1;
      player.isSharked = var1;
   }
}
