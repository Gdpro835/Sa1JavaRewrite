package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Penguin extends EnemyObject {
   private static final int ALERT_HEIGHT = 60;
   private static final int ALERT_WIDTH = 60;
   private static final int ATTACK_SPEED = 640;
   private static final int COLLISION_HEIGHT = 1600;
   private static final int COLLISION_WIDTH = 1024;
   private static final int SPEED = 128;
   private static final int STATE_ATTACK = 1;
   private static final int STATE_PATROL = 0;
   private static final int WAIT_MAX = 10;
   private static Animation penguinAnimation;
   private boolean IsFire = false;
   private int alert_state;
   private boolean dir = false;
   private int endPosX;
   private int iTrans;
   private int release_cnt = 0;
   private int release_cnt_max = 10;
   private int startPosX;
   private int state;
   private int wait_cn;

   protected Penguin(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (penguinAnimation == null) {
         penguinAnimation = new Animation("/animation/pen");
      }

      this.drawer = penguinAnimation.getDrawer(0, true, 2);
      this.startPosX = this.posX;
      this.endPosX = this.posX + this.mWidth;
      this.posY = this.getGroundY(this.posX, this.posY);
      this.dir = false;
      this.state = 0;
      this.wait_cn = 0;
      this.IsFire = false;
      this.release_cnt = 0;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(penguinAnimation);
      penguinAnimation = null;
   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.drawer);
      }

   }

   public void logic() {
      if (!this.dead) {
         this.alert_state = this.checkPlayerInEnemyAlertRange(this.posX >> 6, this.posY >> 6, 60, 60);
         int var2 = this.posX;
         int var3 = this.posY;
         switch(this.state) {
         case 0:
            if (!this.dir) {
               this.posX += 128;
               if (this.posX >= this.endPosX) {
                  this.dir = true;
                  this.posX = this.endPosX;
                  this.drawer.setTrans(0);
               }
            } else {
               this.posX -= 128;
               if (this.posX <= this.startPosX) {
                  this.dir = false;
                  this.posX = this.startPosX;
                  this.drawer.setTrans(2);
               }
            }

            if (this.release_cnt < this.release_cnt_max) {
               ++this.release_cnt;
            }

            if (this.alert_state == 0 && this.posX != this.startPosX && this.posX != this.endPosX && this.release_cnt == this.release_cnt_max) {
               this.state = 1;
               this.iTrans = this.drawer.getTransId();
               this.drawer.setActionId(1);
               this.drawer.setLoop(false);
               this.IsFire = true;
            }

            this.checkWithPlayer(var2, var3, this.posX, this.posY);
            break;
         case 1:
            if (this.wait_cn < 10) {
               ++this.wait_cn;
               if (this.drawer.checkEnd() && this.IsFire) {
                  this.IsFire = false;
                  int var4 = this.posX;
                  int var5 = this.posY;
                  short var1;
                  if (this.iTrans == 0) {
                     var1 = -640;
                  } else {
                     var1 = 640;
                  }

                  BulletObject.addBullet(18, var4, var5, var1, 0);
               }
            } else {
               this.wait_cn = 0;
               this.drawer.setActionId(0);
               this.drawer.setTrans(this.iTrans);
               this.drawer.setLoop(true);
               this.state = 0;
               this.release_cnt = 0;
            }

            this.checkWithPlayer(var2, var3, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 512, var2 - 1600, 1024, 1600);
   }
}
