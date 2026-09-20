package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class SnowRobotH extends EnemyObject {
   private static final int ALERT_HEIGHT = 100;
   private static final int ALERT_WIDTH = 2;
   private static final int COLLISION_HEIGHT = 1600;
   private static final int COLLISION_WIDTH = 1152;
   private static final int LAUNCH_SPEED = -1280;
   private static final int SPEED = 128;
   private static final int STATE_ATTACK = 1;
   private static final int STATE_PATROL = 0;
   private static final int WAIT_MAX = 20;
   private boolean IsFire = false;
   private int alert_state;
   private boolean dir = false;
   private int endPosX;
   private int startPosX;
   private int state;
   private int wait_cn;

   protected SnowRobotH(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (snowrobotAnimation == null) {
         snowrobotAnimation = new Animation("/animation/yukimal");
      }

      this.drawer = snowrobotAnimation.getDrawer(0, true, 0);
      this.startPosX = this.posX;
      this.endPosX = this.posX + this.mWidth;
      this.posY = this.getGroundY(this.posX, this.posY);
      this.dir = false;
      this.state = 0;
      this.wait_cn = GameTime.set(this, "wait_cn", 0);
      this.IsFire = false;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(snowrobotAnimation);
      snowrobotAnimation = null;
   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.drawer);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var1 = this.posX;
         int var2 = this.posY;
         this.alert_state = this.checkPlayerInEnemyAlertRange(var1 >> 6, var2 >> 6, 2, 100);
         var1 = this.posX;
         var2 = this.posY;
         switch(this.state) {
         case 0:
            if (!this.dir) {
               this.posX = GameTime.advance(this, "posX", this.posX, 128);
               if (this.posX >= this.endPosX) {
                  this.dir = true;
                  this.posX = this.endPosX;
               }
            } else {
               this.posX = GameTime.advance(this, "posX", this.posX, -(128));
               if (this.posX <= this.startPosX) {
                  this.dir = false;
                  this.posX = this.startPosX;
               }
            }

            if (this.alert_state == 0 && this.posX != this.startPosX && this.posX != this.endPosX) {
               this.state = 1;
               this.drawer.setActionId(1);
               this.drawer.setLoop(false);
               this.IsFire = true;
            }

            this.checkWithPlayer(var1, var2, this.posX, this.posY);
            break;
         case 1:
            if (this.wait_cn < 20) {
               this.wait_cn = Math.min(20, GameTime.advance(this, "wait_cn", this.wait_cn, 1));
               if (this.drawer.checkEnd() && this.IsFire) {
                  this.IsFire = false;
                  int var4 = this.posX;
                  int var3 = this.posY;
                  BulletObject.addBullet(17, var4, var3 - 1600, 0, -1280);
               }
            } else {
               this.wait_cn = GameTime.set(this, "wait_cn", 0);
               this.drawer.setActionId(0);
               this.drawer.setTrans(0);
               this.drawer.setLoop(true);
               this.state = 0;
            }

            this.checkWithPlayer(var1, var2, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 576, var2 - 1600, 1152, 1600);
   }
}
