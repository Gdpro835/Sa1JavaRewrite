package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Mole extends EnemyObject {
   private static int COLLISION_HEIGHT = 1600;
   private static int COLLISION_WIDTH = 1024;
   private static final int HEIGHT_OFFSET = 2;
   private static final int STATE_UP = 1;
   private static final int STATE_WAIT = 0;
   private static Animation moleAnimation;
   private int state;
   private int wait_cnt;
   private int wait_cnt_max = 16;

   protected Mole(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3 + 2, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.refreshCollisionRect(this.posX, this.posY);
      if (moleAnimation == null) {
         moleAnimation = new Animation("/animation/mole");
      }

      this.drawer = moleAnimation.getDrawer(0, true, 0);
      this.wait_cnt = 0;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(moleAnimation);
      moleAnimation = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (!this.dead && var1 == player && this.state == 1) {
         this.beAttack();
         player.doAttackPose(this, var2);
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && var1 == player) {
         if (player.isAttackingEnemy()) {
            if (this.state == 1) {
               this.beAttack();
               player.doAttackPose(this, var2);
            }
         } else if (this.state == 1) {
            player.beHurt();
         }
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
      this.drawCollisionRect(var1);
   }

   public void logic() {
      if (this.dead) {
         this.drawer.setActionId(0);
      } else {
         int var2 = this.posX;
         int var1 = this.posY;
         switch(this.state) {
         case 0:
            if (this.wait_cnt < this.wait_cnt_max) {
               ++this.wait_cnt;
            } else if (this.posX < player.getCheckPositionX()) {
               this.drawer.setActionId(1);
               this.drawer.setTrans(2);
               this.drawer.setLoop(false);
               this.state = 1;
            } else {
               this.drawer.setActionId(1);
               this.drawer.setTrans(0);
               this.drawer.setLoop(false);
               this.state = 1;
            }

            this.checkWithPlayer(var2, var1, this.posX, this.posY);
            break;
         case 1:
            if (this.drawer.checkEnd()) {
               this.drawer.setActionId(0);
               this.drawer.setLoop(true);
               this.state = 0;
               this.wait_cnt = 0;
            }

            this.checkWithPlayer(var2, var1, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var7 = this.collisionRect;
      int var3 = COLLISION_WIDTH;
      int var4 = COLLISION_HEIGHT;
      int var5 = COLLISION_WIDTH;
      int var6 = COLLISION_HEIGHT;
      var7.setRect(var1 - (var3 >> 1), var2 - var4, var5, var6);
   }
}
