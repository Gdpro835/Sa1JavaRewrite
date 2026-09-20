package SonicGBA;

import Lib.Animation;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;

class LadyBug extends EnemyObject {
   private static final int ALERT_HEIGHT = 64;
   private static final int ALERT_RANGE = 4992;
   private static final int ALERT_WIDTH = 72;
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 1664;
   private static final int STATE_ATTACK = 2;
   private static final int STATE_FLY = 1;
   private static final int STATE_WAIT = 0;
   private static Animation ladybugAnimation;
   private int alert_state;
   private int attack_cnt = 0;
   private int attack_cnt_max = 4;
   private int circleCenterX;
   private int circleCenterY;
   private int dg = 10;
   private int emenyid;
   private int fire_start_speed = 300;
   private int plus = 1;
   private int plus_cnt = 0;
   private int state;
   private int wait_cnt = 0;
   private int wait_cnt_max = 16;

   protected LadyBug(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.circleCenterX = this.posX + (this.mWidth >> 1);
      this.circleCenterY = this.posY;
      this.plus_cnt = 0;
      if (ladybugAnimation == null) {
         ladybugAnimation = new Animation("/animation/ladybug");
      }

      this.drawer = ladybugAnimation.getDrawer(0, false, 0);
      this.emenyid = var1;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(ladybugAnimation);
      ladybugAnimation = null;
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
         this.alert_state = this.checkPlayerInEnemyAlertRange(var2 >> 6, var1 >> 6, 72, 64);
         var2 = this.posX;
         var1 = this.posY;
         switch(this.state) {
         case 0:
            if (this.alert_state == 0) {
               this.state = 2;
               this.drawer.setActionId(2);
               this.drawer.setLoop(true);
               this.attack_cnt = 0;
            } else if (this.wait_cnt < this.wait_cnt_max) {
               ++this.wait_cnt;
            } else {
               this.state = 1;
            }

            this.checkWithPlayer(var2, var1, this.posX, this.posY);
            break;
         case 1:
            this.drawer.setActionId(1);
            this.drawer.setLoop(true);
            if (this.plus > 0) {
               this.plus_cnt += this.plus;
               if (this.plus_cnt >= 180 / this.dg) {
                  this.plus_cnt = 180 / this.dg;
                  this.plus = -this.plus;
                  this.state = 0;
                  this.drawer.setActionId(0);
                  this.drawer.setLoop(true);
                  this.wait_cnt = 0;
               }
            } else {
               this.plus_cnt += this.plus;
               if (this.plus_cnt <= 0) {
                  this.plus_cnt = 0;
                  this.plus = -this.plus;
                  this.state = 0;
                  this.drawer.setActionId(0);
                  this.drawer.setLoop(true);
                  this.wait_cnt = 0;
               }
            }

            this.posX = this.circleCenterX - (this.mWidth >> 1) * MyAPI.dCos(this.dg * this.plus_cnt) / 100;
            this.posY = this.circleCenterY + (this.mWidth >> 1) * MyAPI.dSin(this.dg * this.plus_cnt) / 100;
            this.checkWithPlayer(var2, var1, this.posX, this.posY);
            break;
         case 2:
            if (this.attack_cnt < this.attack_cnt_max) {
               ++this.attack_cnt;
            } else {
               int var3 = this.emenyid;
               int var7 = this.posX;
               int var6 = this.posY;
               int var5 = -this.fire_start_speed;
               int var4 = -this.fire_start_speed;
               BulletObject.addBullet(var3, var7 - 832, var6, var5, var4);
               var6 = this.emenyid;
               var7 = this.posX;
               var3 = this.posY;
               var4 = this.fire_start_speed;
               var5 = -this.fire_start_speed;
               BulletObject.addBullet(var6, var7 + 832, var3, var4, var5);
               this.state = 1;
            }

            this.checkWithPlayer(var2, var1, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 832, var2, 1664, 1024);
   }
}
