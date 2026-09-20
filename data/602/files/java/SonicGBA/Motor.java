package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Motor extends EnemyObject {
   private static final int ALERT_HEIGHT = 20;
   private static final int ALERT_RANGE = 6144;
   private static final int ALERT_WIDTH = 88;
   private static final int COLLISION_HEIGHT = 2048;
   private static final int COLLISION_WIDTH = 2048;
   private static final int STATE_ATTACK = 1;
   private static final int STATE_MOVE = 0;
   private static Animation motorAnimation;
   private int alert_state;
   private int attck_cnt = 0;
   private int limitLeftX;
   private int limitRightX;
   private int release_cnt = 0;
   private int release_cnt_max = 5;
   private int starty;
   private int state;
   private int velocity = 192;

   protected Motor(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.limitLeftX = this.posX;
      this.limitRightX = this.posX + this.mWidth;
      this.starty = this.posY;
      this.posX = this.limitLeftX + (this.mWidth >> 1);
      if (motorAnimation == null) {
         motorAnimation = new Animation("/animation/motor");
      }

      this.drawer = motorAnimation.getDrawer(1, true, 0);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(motorAnimation);
      motorAnimation = null;
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
         this.alert_state = this.checkPlayerInEnemyAlertRange(var1 >> 6, var2 >> 6, 88, 20);
         var2 = this.posX;
         var1 = this.posY;
         switch(this.state) {
         case 0:
            if (this.velocity > 0) {
               this.posX += this.velocity;
               this.drawer.setActionId(0);
               this.drawer.setTrans(2);
               if (this.posX >= this.limitRightX) {
                  this.posX = this.limitRightX;
                  this.velocity = -this.velocity;
                  this.drawer.setActionId(1);
                  this.drawer.setTrans(0);
               }
            } else {
               this.posX += this.velocity;
               this.drawer.setActionId(0);
               this.drawer.setTrans(0);
               if (this.posX <= this.limitLeftX) {
                  this.posX = this.limitLeftX;
                  this.velocity = -this.velocity;
                  this.drawer.setActionId(1);
                  this.drawer.setTrans(0);
               }
            }

            if (this.release_cnt < this.release_cnt_max) {
               ++this.release_cnt;
            }

            if (this.alert_state == 0 && this.release_cnt == this.release_cnt_max && this.posX != this.limitLeftX && this.posX != this.limitRightX) {
               if (this.posX < player.getCheckPositionX()) {
                  if (this.drawer.getTransId() == 2 && this.drawer.getActionId() == 0) {
                     this.state = 1;
                     this.attck_cnt = 0;
                  }
               } else if (this.posX > player.getCheckPositionX() && this.drawer.getTransId() == 0 && this.drawer.getActionId() == 0) {
                  this.state = 1;
                  this.attck_cnt = 0;
               }
            }

            this.posY = this.getGroundY(this.posX, this.posY);
            this.checkWithPlayer(var2, var1, this.posX, this.posY);
            break;
         case 1:
            if (this.drawer.getTransId() == 2 && this.drawer.getActionId() == 0) {
               if (this.velocity < 0) {
                  this.velocity = -this.velocity;
               }

               if (this.attck_cnt < 5) {
                  ++this.attck_cnt;
               } else {
                  this.posX += this.velocity * 3;
                  if (this.posX >= this.limitRightX) {
                     this.posX = this.limitRightX;
                  }
               }
            }

            if (this.drawer.getTransId() == 0 && this.drawer.getActionId() == 0) {
               if (this.velocity > 0) {
                  this.velocity = -this.velocity;
               }

               if (this.attck_cnt < 5) {
                  ++this.attck_cnt;
               } else {
                  this.posX += this.velocity * 3;
                  if (this.posX <= this.limitLeftX) {
                     this.posX = this.limitLeftX;
                  }
               }
            }

            if (this.posX == this.limitLeftX || this.posX == this.limitRightX) {
               this.state = 0;
               this.release_cnt = 0;
            }

            this.posY = this.getGroundY(this.posX, this.posY);
            this.checkWithPlayer(var2, var1, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1024, var2 - 2048, 2048, 2048);
   }
}
