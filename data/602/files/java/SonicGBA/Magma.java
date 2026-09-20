package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Magma extends EnemyObject {
   private static final int COLLISION_HEIGHT = 1280;
   private static final int COLLISION_WIDTH = 1280;
   private static boolean IsFire = false;
   private static final int STATE_UP = 0;
   private static final int STATE_WAIT = 1;
   private static Animation magmaAnimation;
   private static AnimationDrawer magmaDrawer;
   private static int state;
   private static int velocity = -768;
   private static int wait_cnt;
   private static int wait_cnt_max = 64;
   private int emenyid;
   private int fire_start_speed = 384;
   private int limitBottomY;
   private int limitTopY;

   protected Magma(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posY += 1920;
      this.limitTopY = this.posY - this.mHeight;
      this.limitBottomY = this.posY;
      if (magmaAnimation == null) {
         magmaAnimation = new Animation("/animation/magma");
      }

      magmaDrawer = magmaAnimation.getDrawer(0, false, 0);
      wait_cnt = 0;
      this.emenyid = var1;
      magmaDrawer.setPause(true);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(magmaAnimation);
      magmaAnimation = null;
   }

   public static void staticlogic() {
      magmaDrawer.moveOn();
      switch(state) {
      case 0:
         if (magmaDrawer.getCurrentFrame() < 6) {
            velocity = -768;
            IsFire = false;
         } else if (magmaDrawer.getCurrentFrame() == 6) {
            velocity = 0;
            IsFire = true;
         } else {
            velocity = 0;
            IsFire = false;
         }

         if (magmaDrawer.checkEnd()) {
            state = 1;
            wait_cnt = 0;
         }
         break;
      case 1:
         if (wait_cnt < wait_cnt_max) {
            ++wait_cnt;
         }
      }

   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && var1 == player && state == 0) {
         player.beHurt();
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead && !magmaDrawer.checkEnd()) {
         this.drawInMap(var1, magmaDrawer);
         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var1 = this.posX;
         int var2 = this.posY;
         switch(state) {
         case 0:
            this.posY += velocity;
            if (IsFire) {
               BulletObject.addBullet(this.emenyid, this.posX, this.posY, -this.fire_start_speed, -this.fire_start_speed * 2);
               BulletObject.addBullet(this.emenyid, this.posX, this.posY, this.fire_start_speed, -this.fire_start_speed * 2);
            }

            this.checkWithPlayer(var1, var2, this.posX, this.posY);
            break;
         case 1:
            this.posY = this.limitBottomY;
            if (wait_cnt == wait_cnt_max) {
               state = 0;
               magmaDrawer.restart();
               IsFire = false;
            }

            this.checkWithPlayer(var1, var2, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 640, var2 - 640, 1280, 1280);
   }
}
