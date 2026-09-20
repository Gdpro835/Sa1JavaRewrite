package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Monkey extends EnemyObject {
   private static final int ALERT_RANGE = 9216;
   private static final int COLLISION_HEIGHT = 1280;
   private static final int COLLISION_WIDTH = 1536;
   private static final int STATE_ATTACK = 1;
   private static final int STATE_CLIMB = 0;
   private static Animation monkeyAnimation;
   private int ALERT_HEIGHT = 160;
   private int ALERT_WIDTH = 256;
   private int BOMB_MAX_SPEED_X = 384;
   private int alert_state;
   private int attack_cnt = 0;
   private int enemyid;
   private int limitBottomY;
   private int limitTopY;
   private int posXl;
   private int posXr;
   private int state;
   private int velocity = 320;

   protected Monkey(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posY -= 1024;
      this.posXl = this.posX;
      this.posXr = this.posX - this.velocity * 2;
      this.limitTopY = this.posY;
      this.limitBottomY = this.posY + this.mHeight;
      if (monkeyAnimation == null) {
         monkeyAnimation = new Animation("/animation/monkey");
      }

      this.drawer = monkeyAnimation.getDrawer(0, true, 0);
      this.enemyid = var1;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(monkeyAnimation);
      monkeyAnimation = null;
   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.drawer);
         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var4 = this.posX;
         int var1 = this.posY;
         int var2 = this.ALERT_WIDTH;
         int var3 = this.ALERT_HEIGHT;
         this.alert_state = this.checkPlayerInEnemyAlertRange(var4 >> 6, var1 >> 6, var2, var3);
         var1 = this.posX;
         var3 = this.posY;
         if (this.posX < player.getCheckPositionX()) {
            this.posX = this.posXr;
         } else {
            this.posX = this.posXl;
         }

         var4 = this.posX;
         switch(this.state) {
         case 0:
            if (this.alert_state == 0 || this.alert_state == 1) {
               if (this.posX < player.getCheckPositionX()) {
                  this.drawer.setTrans(2);
               } else {
                  this.drawer.setTrans(0);
               }
            }

            if (this.velocity > 0) {
               this.posY += this.velocity;
               if (this.posY >= this.limitBottomY) {
                  this.posY = this.limitBottomY;
                  this.velocity = -this.velocity;
               }
            } else {
               this.posY += this.velocity;
               if (this.posY <= this.limitTopY) {
                  this.posY = this.limitTopY;
                  this.velocity = -this.velocity;
               }
            }

            if (this.posY == this.limitTopY && this.alert_state == 0) {
               ++this.attack_cnt;
               if (this.attack_cnt == 2) {
                  this.state = 1;
                  this.attack_cnt = 0;
               }
            }

            this.checkWithPlayer(var4, var3, this.posX, this.posY);
            break;
         case 1:
            this.drawer.setActionId(1);
            this.drawer.setLoop(false);
            if (this.posX < player.getCheckPositionX()) {
               this.drawer.setTrans(2);
            } else {
               this.drawer.setTrans(0);
            }

            if (this.drawer.checkEnd()) {
               var2 = -(this.posX - player.getCheckPositionX()) / 12;
               var1 = var2;
               if (Math.abs(var2) > this.BOMB_MAX_SPEED_X) {
                  if (var2 > 0) {
                     var1 = this.BOMB_MAX_SPEED_X;
                  } else {
                     var1 = -this.BOMB_MAX_SPEED_X;
                  }
               }

               BulletObject.addBullet(this.enemyid, this.posX, this.posY, var1, 0);
               this.state = 0;
               this.drawer.setActionId(0);
               this.drawer.setLoop(true);
            }

            this.checkWithPlayer(var4, var3, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 768, var2, 1536, 1280);
   }
}
