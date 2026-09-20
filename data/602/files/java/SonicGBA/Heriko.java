package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Heriko extends EnemyObject {
   private static final int ALERT_HEIGHT = 128;
   private static final int ALERT_WIDTH = 64;
   private static final int BULLET_SPEED = 512;
   private static final int COLLISION_HEIGHT = 1856;
   private static final int COLLISION_WIDTH = 1280;
   private static final int DISTANCE = 2560;
   private static final int HEIGHT_OFFSET = 4;
   private static final int SPEED = 128;
   private static final int STATE_ATTACK = 1;
   private static final int STATE_PATROL = 0;
   private static final int WAIT_MAX = 30;
   private static Animation herikoAnimation;
   private boolean IsFire = false;
   private int alert_state;
   private boolean dir = false;
   private int endPosX;
   private int journey;
   private int startPosX;
   private int state;
   private int wait_cn;

   protected Heriko(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3 - 4, var4, var5, var6, var7);
      if (herikoAnimation == null) {
         herikoAnimation = new Animation("/animation/heriko");
      }

      this.drawer = herikoAnimation.getDrawer(0, true, 0);
      this.startPosX = this.posX;
      this.endPosX = this.posX + this.mWidth;
      this.dir = false;
      this.state = 0;
      this.wait_cn = GameTime.set(this, "wait_cn", 0);
      this.IsFire = false;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(herikoAnimation);
      herikoAnimation = null;
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
         this.alert_state = this.checkPlayerInEnemyAlertRange(var2 >> 6, var1 >> 6, 64, 128);
         var2 = this.posX;
         var1 = this.posY;
         switch(this.state) {
         case 0:
            if (!this.dir) {
               this.posX = GameTime.advance(this, "posX", this.posX, 128);
               this.journey = GameTime.advance(this, "journey", this.journey, 128);
               if (this.posX >= this.endPosX) {
                  this.dir = true;
                  this.posX = this.endPosX;
               }
            } else {
               this.posX = GameTime.advance(this, "posX", this.posX, -(128));
               this.journey = GameTime.advance(this, "journey", this.journey, 128);
               if (this.posX <= this.startPosX) {
                  this.dir = false;
                  this.posX = this.startPosX;
               }
            }

            if (this.journey >= 2560 && this.alert_state == 0) {
               this.state = 1;
               this.journey = 0;
               this.drawer.setActionId(1);
               this.drawer.setLoop(false);
               this.IsFire = true;
            }

            this.checkWithPlayer(var2, var1, this.posX, this.posY);
            break;
         case 1:
            if (this.wait_cn < 30) {
               this.wait_cn = Math.min(30, GameTime.advance(this, "wait_cn", this.wait_cn, 1));
               if (this.drawer.checkEnd() && this.IsFire) {
                  this.IsFire = false;
                  BulletObject.addBullet(12, this.posX, this.posY, 0, 512);
               }
            } else {
               this.wait_cn = GameTime.set(this, "wait_cn", 0);
               this.drawer.setActionId(0);
               this.drawer.setTrans(0);
               this.drawer.setLoop(true);
               this.state = 0;
            }

            this.checkWithPlayer(var2, var1, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 640, var2 - 1664, 1280, 1856);
   }
}
