package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Lizard extends EnemyObject {
   private static final int COLLISION_HEIGHT = 2048;
   private static final int COLLISION_WIDTH = 1728;
   private static final int STATE_ATTACK = 1;
   private static final int STATE_MOVE = 0;
   private static Animation lizardAnimation;
   private boolean IsFired;
   private int enemyid;
   private int fire_start_speed = 1200;
   private int limitLeftX;
   private int limitRightX;
   private int state;
   private int velocity = 150;

   protected Lizard(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.mWidth = 4096;
      this.limitLeftX = this.posX;
      this.limitRightX = this.posX + this.mWidth;
      this.posX += this.mWidth >> 1;
      if (lizardAnimation == null) {
         lizardAnimation = new Animation("/animation/lizard");
      }

      this.drawer = lizardAnimation.getDrawer(0, true, 0);
      this.IsFired = false;
      this.enemyid = var1;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(lizardAnimation);
      lizardAnimation = null;
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
         int var3;
         switch(this.state) {
         case 0:
            var3 = (this.limitLeftX + (this.mWidth >> 1)) / 150;
            if (this.velocity > 0) {
               this.posX += this.velocity;
               this.drawer.setActionId(0);
               this.drawer.setTrans(2);
               this.drawer.setLoop(true);
               if (this.posX >= this.limitRightX) {
                  this.posX = this.limitRightX;
                  this.velocity = -this.velocity;
                  this.IsFired = false;
               }

               if (this.posX / 150 == var3 && !this.IsFired) {
                  this.state = 1;
                  this.drawer.setActionId(1);
                  this.drawer.setTrans(2);
                  this.drawer.setLoop(false);
                  this.IsFired = true;
               }
            } else {
               this.posX += this.velocity;
               this.drawer.setActionId(0);
               this.drawer.setTrans(0);
               this.drawer.setLoop(true);
               if (this.posX <= this.limitLeftX) {
                  this.posX = this.limitLeftX;
                  this.velocity = -this.velocity;
                  this.IsFired = false;
               }

               if (this.posX / 150 == var3 && !this.IsFired) {
                  this.state = 1;
                  this.drawer.setActionId(1);
                  this.drawer.setTrans(0);
                  this.drawer.setLoop(false);
                  this.IsFired = true;
               }
            }

            this.posY = this.getGroundY(this.posX, this.posY);
            this.checkWithPlayer(var1, var2, this.posX, this.posY);
            break;
         case 1:
            if (this.drawer.checkEnd()) {
               int var6 = this.enemyid;
               var3 = this.posX;
               int var5 = this.posY;
               int var4 = -this.fire_start_speed;
               BulletObject.addBullet(var6, var3, var5 - 1024, 0, var4);
               this.state = 0;
               if (this.velocity > 0) {
                  this.drawer.setActionId(0);
                  this.drawer.setTrans(2);
               } else {
                  this.drawer.setActionId(0);
                  this.drawer.setTrans(0);
               }
            }

            this.posY = this.getGroundY(this.posX, this.posY);
            this.checkWithPlayer(var1, var2, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 864, var2 - 2048, 1728, 2048);
   }
}
