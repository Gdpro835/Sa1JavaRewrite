package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Bat extends EnemyObject {
   private static final int ALERT_RANGE = 7168;
   private static final int COLLISION_HEIGHT = 1792;
   private static final int COLLISION_WIDTH = 1792;
   private static final int FLY_TOP = 1536;
   private static final int STATE_ATTACK = 1;
   private static final int STATE_FLY = 0;
   private static Animation batAnimation;
   private int alert_state;
   private int attack_cnt;
   private int attack_cnt_max = 30;
   private int emenyid;
   private int limitBottomY;
   private int limitLeftX;
   private int limitRightX;
   private int limitTopY;
   private int offsety = 384;
   private int state;
   private int velocity = 128;

   protected Bat(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posY -= 1024;
      this.limitLeftX = this.posX;
      this.limitRightX = this.posX + this.mWidth;
      this.limitTopY = this.posY - 1536;
      this.limitBottomY = this.posY;
      if (batAnimation == null) {
         batAnimation = new Animation("/animation/bat");
      }

      this.drawer = batAnimation.getDrawer(0, true, 0);
      this.emenyid = var1;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(batAnimation);
      batAnimation = null;
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
         this.alert_state = this.checkPlayerInEnemyAlertRange(this.posX >> 6, this.posY >> 6, 7168);
         switch(this.state) {
         case 0:
            if (this.velocity > 0) {
               this.posX += this.velocity;
               if (this.posX >= this.limitRightX) {
                  this.posX = this.limitRightX;
                  this.velocity = -this.velocity;
               }
            } else {
               this.posX += this.velocity;
               if (this.posX <= this.limitLeftX) {
                  this.posX = this.limitLeftX;
                  this.velocity = -this.velocity;
               }
            }

            if (this.offsety > 0) {
               this.posY += this.offsety;
               if (this.posY >= this.limitBottomY) {
                  this.posY = this.limitBottomY;
                  this.offsety = -this.offsety;
               }
            } else {
               this.posY += this.offsety;
               if (this.posY <= this.limitTopY) {
                  this.posY = this.limitTopY;
                  this.offsety = -this.offsety;
               }
            }

            int var2 = Math.abs(player.getCheckPositionX() - this.posX);
            if (var2 < 1792 && this.alert_state == 0) {
               if (this.attack_cnt >= this.attack_cnt_max) {
                  this.state = 1;
               } else {
                  ++this.attack_cnt;
               }
            }

            this.checkWithPlayer(var1, var3, this.posX, this.posY);
            break;
         case 1:
            this.drawer.setActionId(1);
            this.drawer.setLoop(false);
            if (this.drawer.checkEnd()) {
               BulletObject.addBullet(this.emenyid, this.posX, this.posY, 0, 0);
               this.state = 0;
               this.drawer.setActionId(0);
               this.drawer.setLoop(true);
               this.attack_cnt = 0;
            }

            this.checkWithPlayer(var1, var3, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 896, var2 - 896 - 320, 1792, 1792);
   }
}
