package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Mira extends EnemyObject {
   private static final int ALERT_HEIGHT = 64;
   private static final int ALERT_WIDTH = 128;
   private static final int ATTACK_WIDTH = 2048;
   private static final int BULLET_SPEED = 480;
   private static final int COLLISION_HEIGHT = 1920;
   private static final int DEFENCE_WIDTH = 1920;
   private static final int DISTANCE = 2560;
   private static final int SPEED = 128;
   private static final int STATE_ATTACK = 1;
   private static final int STATE_PATROL = 0;
   private static final int WAIT_MAX = 30;
   private static Animation miraAnimation;
   private boolean IsFire = false;
   private int alert_state;
   private boolean dir = false;
   private int endPosX;
   private int journey;
   private int startPosX;
   private int state;
   private int trans;
   private int wait_cn;

   protected Mira(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (miraAnimation == null) {
         miraAnimation = new Animation("/animation/mira");
      }

      this.drawer = miraAnimation.getDrawer(0, true, 0);
      this.startPosX = this.posX;
      this.endPosX = this.posX + this.mWidth;
      this.dir = false;
      this.state = 0;
      this.trans = 0;
      this.wait_cn = 0;
      this.IsFire = false;
      this.posY -= 960;
   }

   private int checkEnemyFace() {
      byte var1;
      if (this.posX - player.getFootPositionX() >= 0) {
         var1 = 0;
      } else {
         var1 = 2;
      }

      return var1;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(miraAnimation);
      miraAnimation = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (!this.dead) {
         switch(this.state) {
         case 0:
         default:
            break;
         case 1:
            if (player.isAttackingEnemy()) {
               player.doAttackPose(this, var2);
               this.beAttack();
            } else if (!(player instanceof PlayerTails) || player.getCharacterAnimationID() != 12 && player.getCharacterAnimationID() != 13 && player.getCharacterAnimationID() != 14) {
               player.beHurt();
            } else {
               this.beAttack();
            }
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead) {
         switch(this.state) {
         case 0:
            if (var1 == player) {
               player.beHurt();
            }
            break;
         case 1:
            if (player.isAttackingEnemy()) {
               player.doAttackPose(this, var2);
               this.beAttack();
            } else {
               player.beHurt();
            }
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
         int var2 = this.posY;
         this.alert_state = this.checkPlayerInEnemyAlertRange(var1 >> 6, var2 >> 6, 128, 64);
         int var3 = this.posX;
         int var4 = this.posY;
         switch(this.state) {
         case 0:
            if (!this.dir) {
               this.posX += 128;
               this.journey += 128;
               if (this.posX >= this.endPosX) {
                  this.dir = true;
                  this.posX = this.endPosX;
               }
            } else {
               this.posX -= 128;
               this.journey += 128;
               if (this.posX <= this.startPosX) {
                  this.dir = false;
                  this.posX = this.startPosX;
               }
            }

            if (this.journey >= 2560 && this.alert_state == 0) {
               this.state = 1;
               this.journey = 0;
               this.trans = this.checkEnemyFace();
               this.drawer.setActionId(1);
               this.drawer.setTrans(this.trans);
               this.drawer.setLoop(false);
               this.IsFire = true;
            }

            this.checkWithPlayer(var3, var4, this.posX, this.posY);
            break;
         case 1:
            if (this.wait_cn < 30) {
               ++this.wait_cn;
               if (this.drawer.checkEnd() && this.IsFire) {
                  this.IsFire = false;
                  int var5 = this.posX;
                  short var7;
                  if (this.trans == 0) {
                     var7 = -128;
                  } else {
                     var7 = 128;
                  }

                  int var6 = this.posY;
                  short var8;
                  if (this.trans == 0) {
                     var8 = -480;
                  } else {
                     var8 = 480;
                  }

                  BulletObject.addBullet(12, var5 + var7, var6, var8, 0);
               }
            } else {
               this.wait_cn = 0;
               this.drawer.setActionId(0);
               this.drawer.setTrans(0);
               this.drawer.setLoop(true);
               this.state = 0;
            }

            this.checkWithPlayer(var3, var4, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3;
      switch(this.state) {
      case 0:
         var3 = this.collisionRect;
         var3.setRect(var1 - 960, var2 - 960, 1920, 1920);
         break;
      case 1:
         var3 = this.collisionRect;
         var3.setRect(var1 - 1024, var2 - 960, 2048, 1920);
      }

   }
}
