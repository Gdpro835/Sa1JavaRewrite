package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Cement extends EnemyObject {
   private static final int ALERT_RANGE = 30;
   private static final int COLLISION_HEIGHT = 1152;
   private static final int COLLISION_WIDTH = 1152;
   private static final int DEFENCE_HEIGHT = 2560;
   private static final int DEFENCE_WIDTH = 2560;
   private static final int PATROL_FRAME = 136;
   private static final int SPEED = 60;
   private static final int STATE_DEFENCE = 1;
   private static final int STATE_PATROL = 0;
   private static final int WAIT_FRAME = 32;
   private static Animation cementAnimation;
   private int alert_state;
   private boolean dir = false;
   private int endPosX;
   private int patrol_cn;
   private int startPosX;
   private int state;
   private int wait_cn;

   protected Cement(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (cementAnimation == null) {
         cementAnimation = new Animation("/animation/kura");
      }

      this.drawer = cementAnimation.getDrawer(0, true, 0);
      this.startPosX = this.posX;
      this.endPosX = this.posX + this.mWidth;
      this.dir = false;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(cementAnimation);
      cementAnimation = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (!this.dead) {
         switch(this.state) {
         case 0:
            this.beAttack();
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead) {
         switch(this.state) {
         case 0:
            if (player.isAttackingEnemy()) {
               player.doAttackPose(this, var2);
               this.beAttack();
            } else {
               player.beHurt();
            }
            break;
         case 1:
            if (var1 == player) {
               player.beHurt();
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.drawer);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var2 = this.posX;
         int var1 = this.posY;
         switch(this.state) {
         case 0:
            if (!this.dir) {
               this.posX = GameTime.advance(this, "posX", this.posX, 60);
               if (this.posX >= this.endPosX) {
                  this.dir = true;
                  this.posX = this.endPosX;
               }
            } else {
               this.posX = GameTime.advance(this, "posX", this.posX, -(60));
               if (this.posX <= this.startPosX) {
                  this.dir = false;
                  this.posX = this.startPosX;
               }
            }

            if (this.patrol_cn < 136) {
               this.patrol_cn = Math.min(136, GameTime.advance(this, "patrol_cn", this.patrol_cn, 1));
            } else {
               this.patrol_cn = GameTime.set(this, "patrol_cn", 0);
               this.wait_cn = GameTime.set(this, "wait_cn", 0);
               this.state = 1;
               this.drawer.setActionId(1);
               this.drawer.setLoop(true);
            }
            break;
         case 1:
            if (this.wait_cn < 32) {
               this.wait_cn = Math.min(32, GameTime.advance(this, "wait_cn", this.wait_cn, 1));
            } else {
               this.patrol_cn = GameTime.set(this, "patrol_cn", 0);
               this.wait_cn = GameTime.set(this, "wait_cn", 0);
               this.drawer.setActionId(0);
               this.drawer.setTrans(0);
               this.drawer.setLoop(true);
               this.state = 0;
            }
         }

         this.checkWithPlayer(var2, var1, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      switch(this.state) {
      case 0:
         this.collisionRect.setRect(var1 - 576, var2 - 384, 1152, 1152);
         break;
      case 1:
         this.collisionRect.setRect(var1 - 1280, var2 - 1280, 2560, 2560);
      }

   }
}
