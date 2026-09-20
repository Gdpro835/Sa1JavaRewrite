package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.MyAPI;
import Lib.crlFP32;
import com.sega.mobile.framework.device.MFGraphics;

class Bee extends EnemyObject {
   private static final int BEE_BULLET_SPEED_ABS = 432;
   private static final int COLLISION_HEIGHT = 1536;
   private static final int COLLISION_WIDTH = 1536;
   private static final int HEIGHT_OFFSET = 16;
   private static final int STATE_ATTACK = 1;
   private static final int STATE_MOVE = 0;
   private static Animation beeAnimation;
   private int ALERT_HEIGHT = 108;
   private int ALERT_WIDTH = 128;
   private int ATTACK_MAX_SCALE = 373;
   private int ATTACK_MIN_SCALE = 36;
   private int alert_state;
   private int attack_base_range = -960;
   private int attack_cnt;
   private int attack_cnt_max = 35;
   private boolean attack_flag = false;
   private int bullset_v_x = 0;
   private int bullset_v_y = 0;
   private int enemyid;
   private int limitLeftX;
   private int limitRightX;
   private int state;
   private int velocity = 192;

   protected Bee(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3 - 16, var4, var5, var6, var7);
      this.limitLeftX = this.posX;
      this.limitRightX = this.posX + this.mWidth;
      if (beeAnimation == null) {
         beeAnimation = new Animation("/animation/bee");
      }

      this.drawer = beeAnimation.getDrawer(1, true, 0);
      this.enemyid = var1;
   }

   private boolean IsFacePlayer() {
      boolean var1;
      if ((this.posX <= player.getFootPositionX() || this.velocity >= 0) && (this.posX >= player.getFootPositionX() || this.velocity <= 0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private int beeBulletVx(int var1, int var2) {
      var2 = crlFP32.actTanDegree(Math.abs(var1), Math.abs(var2));
      byte var3;
      if (var1 <= 0) {
         var3 = -1;
      } else {
         var3 = 1;
      }

      return var3 * 432 * MyAPI.dSin(var2) / 100;
   }

   private int beeBulletVy(int var1, int var2) {
      int var3 = crlFP32.actTanDegree(Math.abs(var1), Math.abs(var2));
      byte var4;
      if (var2 <= 0) {
         var4 = -1;
      } else {
         var4 = 1;
      }

      return var4 * 432 * MyAPI.dCos(var3) / 100;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(beeAnimation);
      beeAnimation = null;
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
         int var2 = this.posY;
         int var3 = this.ALERT_WIDTH;
         int var1 = this.ALERT_HEIGHT;
         this.alert_state = this.checkPlayerInEnemyAlertRange(var4 >> 6, var2 >> 6, var3, var1);
         var2 = this.posX;
         var1 = this.posY;
         int var5;
         int var6;
         switch(this.state) {
         case 0:
            if (this.velocity > 0) {
               this.posX = GameTime.advancePosition(this, "posX", this.posX, "velocity", this.velocity);
               this.drawer.setActionId(0);
               this.drawer.setTrans(2);
               this.drawer.setLoop(true);
               if (this.posX >= this.limitRightX) {
                  this.posX = this.limitRightX;
                  this.velocity = -this.velocity;
                  this.drawer.setActionId(1);
                  this.drawer.setTrans(0);
                  this.drawer.setLoop(false);
                  this.attack_flag = false;
               }
            } else {
               this.posX = GameTime.advancePosition(this, "posX", this.posX, "velocity", this.velocity);
               this.drawer.setActionId(0);
               this.drawer.setTrans(0);
               this.drawer.setLoop(true);
               if (this.posX <= this.limitLeftX) {
                  this.posX = this.limitLeftX;
                  this.velocity = -this.velocity;
                  this.drawer.setActionId(1);
                  this.drawer.setTrans(0);
                  this.drawer.setLoop(false);
                  this.attack_flag = false;
               }
            }

            if (this.alert_state == 0 && this.posY + 1536 < player.getCheckPositionY() && !this.attack_flag) {
               var3 = this.posX;
               var5 = this.posY;
               var6 = this.ATTACK_MIN_SCALE;
               var4 = this.ATTACK_MAX_SCALE;
               if (this.checkPlayerInEnemyAlertRangeScale(var3 >> 6, var5 >> 6, var6, var4) && this.IsFacePlayer()) {
                  if (this.posX < player.getCheckPositionX()) {
                     if (this.velocity > 0) {
                        this.drawer.setActionId(2);
                        this.drawer.setTrans(2);
                        this.drawer.setLoop(false);
                        this.state = 1;
                        this.attack_cnt = 0;
                        var5 = player.getCheckPositionX();
                        var4 = this.posX;
                        var3 = player.getCheckPositionY();
                        var6 = this.posY;
                        this.bullset_v_x = this.beeBulletVx(var5 - var4, var3 - var6);
                        var4 = player.getCheckPositionX();
                        var5 = this.posX;
                        var3 = player.getCheckPositionY();
                        var6 = this.posY;
                        this.bullset_v_y = this.beeBulletVy(var4 - var5, var3 - var6);
                     }
                  } else if (this.posX > player.getCheckPositionX() && this.velocity < 0) {
                     this.drawer.setActionId(2);
                     this.drawer.setTrans(0);
                     this.drawer.setLoop(false);
                     this.state = 1;
                     this.attack_cnt = 0;
                     var3 = player.getCheckPositionX();
                     var6 = this.posX;
                     var4 = player.getCheckPositionY();
                     var5 = this.posY;
                     this.bullset_v_x = this.beeBulletVx(var3 - var6, var4 - var5);
                     var5 = player.getCheckPositionX();
                     var4 = this.posX;
                     var6 = player.getCheckPositionY();
                     var3 = this.posY;
                     this.bullset_v_y = this.beeBulletVy(var5 - var4, var6 - var3);
                  }
               }
            }

            this.checkWithPlayer(var2, var1, this.posX, this.posY);
            break;
         case 1:
            if (this.drawer.checkEnd()) {
               var3 = this.enemyid;
               var4 = this.posX;
               int var7 = this.posY;
               var6 = this.bullset_v_x;
               var5 = this.bullset_v_y;
               BulletObject.addBullet(var3, var4, var7 + 768, var6, var5);
               this.state = 0;
               this.attack_flag = true;
            }

            this.checkWithPlayer(var2, var1, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 768, var2 - 768, 1536, 1536);
   }
}
