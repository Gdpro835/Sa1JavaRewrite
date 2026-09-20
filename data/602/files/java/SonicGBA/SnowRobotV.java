package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class SnowRobotV extends EnemyObject {
   private static final int ALERT_HEIGHT = 10;
   private static final int ALERT_WIDTH = 200;
   private static final int COLLISION_HEIGHT = 1152;
   private static final int COLLISION_WIDTH = 1600;
   private static final int LAUNCH_SPEED = 960;
   private static final int SPEED = 128;
   private static final int STATE_ATTACK = 1;
   private static final int STATE_PATROL = 0;
   private static final int WAIT_MAX = 30;
   private boolean IsFire = false;
   private int alert_state;
   private boolean dir = false;
   private int endPosY;
   private int iLeft;
   private int iTrans;
   private int startPosY;
   private int state;
   private int wait_cn;

   protected SnowRobotV(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (snowrobotAnimation == null) {
         snowrobotAnimation = new Animation("/animation/yukimal");
      }

      this.iLeft = var4;
      byte var8;
      if (this.iLeft == 0) {
         var8 = 5;
      } else {
         var8 = 6;
      }

      this.iTrans = var8;
      this.drawer = snowrobotAnimation.getDrawer(0, true, this.iTrans);
      this.startPosY = this.posY;
      this.endPosY = this.posY + this.mHeight;
      this.dir = false;
      this.state = 0;
      this.wait_cn = 0;
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
         this.alert_state = this.checkPlayerInEnemyAlertRange(var1 >> 6, var2 >> 6, 200, 10);
         int var5 = this.posX;
         int var4 = this.posY;
         switch(this.state) {
         case 0:
            if (!this.dir) {
               this.posY += 128;
               if (this.posY >= this.endPosY) {
                  this.dir = true;
                  this.posY = this.endPosY;
               }
            } else {
               this.posY -= 128;
               if (this.posY <= this.startPosY) {
                  this.dir = false;
                  this.posY = this.startPosY;
               }
            }

            if (this.alert_state == 0) {
               label64: {
                  if (this.iLeft == 0) {
                     if (this.posX >= player.getFootPositionX()) {
                        break label64;
                     }
                  } else if (this.posX <= player.getFootPositionX()) {
                     break label64;
                  }

                  if (this.posY != this.startPosY && this.posY != this.endPosY) {
                     this.state = 1;
                     this.drawer.setActionId(1);
                     this.drawer.setTrans(this.iTrans);
                     this.drawer.setLoop(false);
                     this.IsFire = true;
                  }
               }
            }

            this.checkWithPlayer(var5, var4, this.posX, this.posY);
            break;
         case 1:
            if (this.wait_cn < 30) {
               ++this.wait_cn;
               if (this.drawer.checkEnd() && this.IsFire) {
                  this.IsFire = false;
                  int var3 = this.posX;
                  short var7;
                  if (this.iLeft == 0) {
                     var7 = 1600;
                  } else {
                     var7 = -1600;
                  }

                  int var6 = this.posY;
                  short var8;
                  if (this.iLeft == 0) {
                     var8 = 960;
                  } else {
                     var8 = -960;
                  }

                  BulletObject.addBullet(17, var3 + var7, var6, var8, 0);
               }
            } else {
               this.wait_cn = 0;
               this.drawer.setActionId(0);
               this.drawer.setTrans(this.iTrans);
               this.drawer.setLoop(true);
               this.state = 0;
            }

            this.checkWithPlayer(var5, var4, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var4 = this.collisionRect;
      short var3;
      if (this.iLeft == 0) {
         var3 = 0;
      } else {
         var3 = -1600;
      }

      var4.setRect(var3 + var1, var2, 1600, 1152);
   }
}
