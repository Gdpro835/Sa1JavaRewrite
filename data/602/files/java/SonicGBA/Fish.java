package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Fish extends EnemyObject {
   private static final int ALERT_RANGE = 60;
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 2560;
   private static final int FISH_XY_MAX_SPEED = 432;
   private static final int STATE_ATTACK = 2;
   private static final int STATE_BREAK = 1;
   private static final int STATE_READY = 0;
   private static Animation fishAnimation;
   private int alert_state;
   private int iTrans;
   private int state;
   private int velX;
   private int velY;

   protected Fish(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (fishAnimation == null) {
         fishAnimation = new Animation("/animation/ice_fish");
      }

      this.drawer = fishAnimation.getDrawer(0, true, 0);
      this.posX = var2 << 6;
      this.posY = var3 << 6;
   }

   private int checkEnemyFace() {
      byte var1;
      if (this.posX - player.getFootPositionX() >= 0) {
         var1 = 0;
      } else {
         var1 = 2;
      }

      return var1;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(fishAnimation);
      fishAnimation = null;
   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.drawer, this.posX, this.posY);
         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var1 = this.posX;
         int var2 = this.posY;
         this.alert_state = this.checkPlayerInEnemyAlertRange(var1 >> 6, var2 >> 6, 60, 60);
         var2 = this.posX;
         var1 = this.posY;
         switch(this.state) {
         case 0:
            if (this.alert_state == 0) {
               this.state = 1;
               this.drawer.setActionId(1);
               this.drawer.setLoop(false);
               this.drawer.setTrans(0);
               this.iTrans = this.checkEnemyFace();
            }
            break;
         case 1:
            if (this.drawer.checkEnd()) {
               this.state = 2;
               this.drawer.setActionId(2);
               this.drawer.setLoop(true);
               this.drawer.setTrans(this.iTrans);
               this.velX = -(this.posX - player.getCheckPositionX()) / 8;
               this.velY = -(this.posY - player.getCheckPositionY()) / 8;
               if (this.velX > 432) {
                  this.velX = 432;
               } else if (this.velX < -432) {
                  this.velX = -432;
               }

               if (this.velY > 432) {
                  this.velY = 432;
               } else if (this.velY < -432) {
                  this.velY = -432;
               }

               if (Math.abs(this.velY) > Math.abs(this.velX)) {
                  if (this.velY > 0) {
                     this.velY = Math.abs(this.velX);
                  } else if (this.velY < 0) {
                     this.velY = -Math.abs(this.velX);
                  }
               }
            }
            break;
         case 2:
            this.posX += this.velX;
            this.posY += this.velY;
         }

         this.checkWithPlayer(var2, var1, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1280, var2 - 512, 2560, 1024);
   }
}
