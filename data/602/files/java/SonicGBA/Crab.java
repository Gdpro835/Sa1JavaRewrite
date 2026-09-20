package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Crab extends EnemyObject {
   private static final int COLLISION_HEIGHT = 1792;
   private static final int COLLISION_WIDTH = 2688;
   private static final int STATE_ATTACK = 1;
   private static final int STATE_WALK = 0;
   private static Animation crabAnimation;
   private int emenyid;
   private int fire_cnt;
   private int fire_start_speed = 300;
   private int limitLeftX;
   private int limitRightX;
   private int state;
   private int velocity = 192;

   protected Crab(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.limitLeftX = this.posX;
      this.limitRightX = this.posX + this.mWidth;
      if (crabAnimation == null) {
         crabAnimation = new Animation("/animation/crab");
      }

      this.drawer = crabAnimation.getDrawer(0, true, 0);
      this.emenyid = var1;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(crabAnimation);
      crabAnimation = null;
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
         switch(this.state) {
         case 0:
            if (this.velocity > 0) {
               this.posX = GameTime.advancePosition(this, "posX", this.posX, "velocity", this.velocity);
               if (this.posX >= this.limitRightX) {
                  this.posX = this.limitRightX;
                  this.velocity = -this.velocity;
                  this.state = 1;
                  this.drawer.setActionId(1);
                  this.drawer.setLoop(false);
                  this.fire_cnt = GameTime.set(this, "fire_cnt", 0);
               }
            } else {
               this.posX = GameTime.advancePosition(this, "posX", this.posX, "velocity", this.velocity);
               if (this.posX <= this.limitLeftX) {
                  this.posX = this.limitLeftX;
                  this.velocity = -this.velocity;
                  this.state = 1;
                  this.drawer.setActionId(1);
                  this.drawer.setLoop(false);
                  this.fire_cnt = GameTime.set(this, "fire_cnt", 0);
               }
            }

            this.posY = this.getGroundY(this.posX, this.posY);
            this.checkWithPlayer(var1, var2, this.posX, this.posY);
            break;
         case 1:
            if (GameTime.event(this, "fire_cnt", "initialShot", this.fire_cnt == 0)) {
               int var5 = this.emenyid;
               int var3 = this.posX;
               int var6 = this.posY;
               int var4 = -this.fire_start_speed;
               int var7 = -this.fire_start_speed;
               BulletObject.addBullet(var5, var3 - 1344, var6 - 1792, var4, var7);
               var4 = this.emenyid;
               var6 = this.posX;
               var5 = this.posY;
               var3 = this.fire_start_speed;
               var7 = -this.fire_start_speed;
               BulletObject.addBullet(var4, var6 + 1344, var5 - 1792, var3, var7);
            }

            this.fire_cnt = GameTime.advance(this, "fire_cnt", this.fire_cnt, 1);
            if (this.drawer.checkEnd() || this.fire_cnt > 2) {
               this.state = 0;
               this.drawer.setActionId(0);
               this.drawer.setLoop(true);
            }

            this.posY = this.getGroundY(this.posX, this.posY);
            this.checkWithPlayer(var1, var2, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1344, var2 - 1792, 2688, 1792);
   }
}
