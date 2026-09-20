package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class RabbitFish extends EnemyObject {
   private static final int ALERT_RANGE = 80;
   private static final int COLLISION_HEIGHT = 1536;
   private static final int COLLISION_WIDTH = 1536;
   private static final int STATE_DEFEND = 1;
   private static final int STATE_MOVE = 0;
   private static Animation fishAnimation;
   private int alert_state;
   private int defend_cnt = 0;
   private int defend_frame = 16;
   private int limitLeftX;
   private int limitRightX;
   private int move_cnt = 0;
   private int starty;
   private int state;
   private int velocity = 140;

   protected RabbitFish(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3 - 8, var4, var5, var6, var7);
      this.mWidth = 4096;
      this.limitLeftX = this.posX;
      this.limitRightX = this.posX + this.mWidth;
      this.posX += this.mWidth >> 1;
      this.starty = this.posY;
      if (fishAnimation == null) {
         fishAnimation = new Animation("/animation/rabbit_fish");
      }

      this.drawer = fishAnimation.getDrawer(0, true, 0);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(fishAnimation);
      fishAnimation = null;
   }

   public boolean IsFacetoPlayer() {
      boolean var1;
      if ((this.posX <= player.getFootPositionX() || this.velocity >= 0) && (this.posX >= player.getFootPositionX() || this.velocity <= 0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (!this.dead && var1 == player && this.state == 0) {
         if (player.isAttackingEnemy()) {
            player.doAttackPose(this, var2);
            this.beAttack();
         } else {
            player.beHurt();
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && var1 == player) {
         if (this.state == 0) {
            if (!(player instanceof PlayerSonic) || player.getCharacterAnimationID() != 4 && player.getCharacterAnimationID() != 5 && player.getCharacterAnimationID() != 6) {
               if (player instanceof PlayerKnuckles && (player.getCharacterAnimationID() == 4 || player.getCharacterAnimationID() == 5 || player.getCharacterAnimationID() == 6)) {
                  player.doAttackPose(this, var2);
                  this.beAttack();
               } else if (!(player instanceof PlayerTails) || player.getCharacterAnimationID() != 5 && player.getCharacterAnimationID() != 6 && player.getCharacterAnimationID() != 4) {
                  player.beHurt();
               } else {
                  player.doAttackPose(this, var2);
                  this.beAttack();
               }
            } else {
               player.doAttackPose(this, var2);
               this.beAttack();
            }
         } else {
            player.beHurt();
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.drawer);
         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var1 = this.posX;
         int var3 = this.posY;
         switch(this.state) {
         case 0:
            int var4 = this.posX;
            int var2 = this.posY;
            this.alert_state = this.checkPlayerInEnemyAlertRange(var4 >> 6, var2 >> 6, 80, 80);
            if (this.velocity > 0) {
               this.posX += this.velocity;
               this.move_cnt += Math.abs(this.velocity);
               this.drawer.setActionId(0);
               this.drawer.setTrans(2);
               if (this.posX >= this.limitRightX) {
                  this.posX = this.limitRightX;
                  this.velocity = -this.velocity;
               }
            } else {
               this.posX += this.velocity;
               this.move_cnt += Math.abs(this.velocity);
               this.drawer.setActionId(0);
               this.drawer.setTrans(0);
               if (this.posX <= this.limitLeftX) {
                  this.posX = this.limitLeftX;
                  this.velocity = -this.velocity;
               }
            }

            if (this.alert_state == 0 && this.IsFacetoPlayer() && this.move_cnt >= 5120) {
               this.state = 1;
               this.drawer.setActionId(1);
               this.defend_cnt = 0;
               this.move_cnt = 0;
               if (this.velocity > 0) {
                  this.drawer.setTrans(2);
               } else {
                  this.drawer.setTrans(0);
               }
            }

            this.checkWithPlayer(var1, var3, this.posX, this.posY);
            break;
         case 1:
            if (this.defend_cnt < this.defend_frame) {
               ++this.defend_cnt;
            } else {
               this.state = 0;
               this.drawer.setActionId(0);
               if (this.velocity > 0) {
                  this.drawer.setTrans(2);
               } else {
                  this.drawer.setTrans(0);
               }
            }

            this.checkWithPlayer(var1, var3, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 768, var2 - 768, 1536, 1536);
   }
}
