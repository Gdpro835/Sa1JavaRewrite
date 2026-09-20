package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Chameleon extends EnemyObject {
   private static final int STATE_ATTACK = 2;
   private static final int STATE_MOVE = 0;
   private static final int STATE_TURN = 1;
   private static final int attack_cnt_max = 10;
   private static Animation chameleonAnimation;
   private int ALERT_RANGE = 5760;
   private int COLLISION_HEIGHT = 1408;
   private int COLLISION_WIDTH = 2880;
   private int alert_state;
   private int attack_cnt;
   private int collision_offset_x = 1280;
   private int limitLeftX;
   private int limitRightX;
   private int state;
   private int velocity = 128;

   protected Chameleon(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.limitLeftX = this.posX;
      this.limitRightX = this.posX + this.mWidth;
      if (chameleonAnimation == null) {
         chameleonAnimation = new Animation("/animation/chameleon");
      }

      this.drawer = chameleonAnimation.getDrawer(0, true, 0);
      this.attack_cnt = 0;
      this.collision_offset_x = 0;
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

   public static void releaseAllResource() {
      Animation.closeAnimation(chameleonAnimation);
      chameleonAnimation = null;
   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.drawer);
         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var6 = this.posX;
         int var4 = this.posY;
         int var1 = this.ALERT_RANGE;
         int var5 = this.limitLeftX;
         int var3 = this.limitRightX;
         int var2 = this.COLLISION_WIDTH;
         this.alert_state = this.checkPlayerInEnemyAlertRange(var6 >> 6, var4 >> 6, var1 >> 6, var5 >> 6, var3 >> 6, var2 >> 6);
         var2 = this.posX;
         var1 = this.posY;
         switch(this.state) {
         case 0:
            if (this.velocity > 0) {
               this.posX += this.velocity;
               this.drawer.setActionId(0);
               this.drawer.setTrans(2);
               this.drawer.setLoop(true);
               if (this.posX >= this.limitRightX) {
                  this.posX = this.limitRightX;
                  this.velocity = -this.velocity;
                  this.drawer.setActionId(1);
                  this.drawer.setTrans(2);
                  this.drawer.setLoop(false);
                  this.state = 1;
               }
            } else {
               this.posX += this.velocity;
               this.drawer.setActionId(0);
               this.drawer.setTrans(0);
               this.drawer.setLoop(true);
               if (this.posX <= this.limitLeftX) {
                  this.posX = this.limitLeftX;
                  this.velocity = -this.velocity;
                  this.drawer.setActionId(1);
                  this.drawer.setTrans(0);
                  this.drawer.setLoop(false);
                  this.state = 1;
               }
            }

            if (this.attack_cnt < 10) {
               ++this.attack_cnt;
            }

            if (this.alert_state == 0 && this.attack_cnt == 10 && this.IsFacePlayer()) {
               this.state = 2;
               if (this.posX < player.getCheckPositionX()) {
                  this.drawer.setActionId(2);
                  this.drawer.setTrans(2);
                  this.drawer.setLoop(false);
               } else {
                  this.drawer.setActionId(2);
                  this.drawer.setTrans(0);
                  this.drawer.setLoop(false);
               }

               this.attack_cnt = 0;
            }

            this.posY = this.getGroundY(this.posX, this.posY);
            this.checkWithPlayer(var2, var1, this.posX, this.posY);
            break;
         case 1:
            if (this.drawer.checkEnd()) {
               this.state = 0;
            }

            this.attack_cnt = 0;
            this.posY = this.getGroundY(this.posX, this.posY);
            this.checkWithPlayer(var2, var1, this.posX, this.posY);
            break;
         case 2:
            if (this.drawer.getCurrentFrame() == 3) {
               if (this.posX < player.getCheckPositionX()) {
                  this.collision_offset_x = 640;
               } else {
                  this.collision_offset_x = -640;
               }

               this.COLLISION_WIDTH = 3840;
               this.refreshCollisionRect(this.posX, this.posY);
            } else {
               this.COLLISION_WIDTH = 2880;
               this.collision_offset_x = 0;
               this.refreshCollisionRect(this.posX, this.posY);
            }

            if (this.drawer.checkEnd()) {
               this.state = 0;
               this.collision_offset_x = 0;
               this.COLLISION_WIDTH = 2880;
            }

            this.posY = this.getGroundY(this.posX, this.posY);
            this.refreshCollisionRect(this.posX, this.posY);
            this.checkWithPlayer(var2, var1, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var8 = this.collisionRect;
      int var7 = this.COLLISION_WIDTH;
      int var3 = this.collision_offset_x;
      int var5 = this.COLLISION_HEIGHT;
      int var6 = this.COLLISION_WIDTH;
      int var4 = this.COLLISION_HEIGHT;
      var8.setRect(var1 - (var7 >> 1) + var3, var2 - var5, var6, var4);
   }
}
