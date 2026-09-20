package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Frog extends EnemyObject {
   private static final int ALERT_HEIGHT = 160;
   private static final int ALERT_RANGE = 3072;
   private static final int ALERT_WIDTH = 55;
   private static final int COLLISION_HEIGHT = 1280;
   private static final int COLLISION_HEIGHT_JUMP = 768;
   private static final int COLLISION_WIDTH = 1024;
   private static final int COLLISION_WIDTH_JUMP = 896;
   private static final int DRIP_ACC;
   private static final int FROG_JUMP_START_SPEED = -1050;
   private static final int STATE_COAXAR = 2;
   private static final int STATE_JUMP = 1;
   private static final int STATE_WAIT = 0;
   private static Animation frogAnimation;
   private boolean IsFirstCoaxar = false;
   private int alert_state;
   private int attack_state;
   private boolean beRight = false;
   private int frog_time = 0;
   private int interval = 1;
   private int limitLeftX;
   private int limitRightX;
   private int starty;
   private int state;
   private int velY = 0;
   private int velocity = 75;

   static {
      DRIP_ACC = GRAVITY - 25;
   }

   protected Frog(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.mWidth = 5120;
      this.limitLeftX = this.posX;
      this.limitRightX = this.posX + this.mWidth;
      if (frogAnimation == null) {
         frogAnimation = new Animation("/animation/frog");
      }

      this.drawer = frogAnimation.getDrawer(0, true, 0);
      this.starty = this.posY;
      this.posX = this.posX + (this.mWidth >> 1) + 2048;
      this.posY = this.getGroundY(this.posX, this.posY);
      this.IsFirstCoaxar = false;
      this.beRight = false;
   }

   private boolean jumpChk() {
      boolean var1;
      if (this.alert_state == 0) {
         var1 = true;
      } else if (this.posX < player.getCheckPositionX()) {
         if (player.getVelX() < 0 && player.getVelX() > -488 && player.getFootPositionX() - this.posX < 3520 && player.getFootPositionX() - this.posX > 0) {
            var1 = true;
         } else if (player.getVelX() <= -488 && player.getVelX() > -672 && player.getFootPositionX() - this.posX < 7040 && player.getFootPositionX() - this.posX >= 3520) {
            var1 = true;
         } else if (player.getVelX() <= -672 && player.getFootPositionX() - this.posX < 10560 && player.getFootPositionX() - this.posX >= 7040) {
            var1 = true;
         } else {
            var1 = false;
         }
      } else if (player.getVelX() > 0 && player.getVelX() < 488 && this.posX - player.getFootPositionX() < 3520 && this.posX - player.getFootPositionX() > 0) {
         var1 = true;
      } else if (player.getVelX() >= 488 && player.getVelX() < 672 && this.posX - player.getFootPositionX() < 7040 && this.posX - player.getFootPositionX() >= 3520) {
         var1 = true;
      } else if (player.getVelX() >= 672 && this.posX - player.getFootPositionX() < 10560 && this.posX - player.getFootPositionX() >= 7040) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void jumpInit() {
      this.state = 1;
      if (this.drawer.getTransId() == 2) {
         this.drawer.setActionId(1);
         this.drawer.setTrans(2);
         this.drawer.setLoop(false);
         this.beRight = false;
      } else if (this.drawer.getTransId() == 0) {
         this.drawer.setActionId(1);
         this.drawer.setTrans(0);
         this.drawer.setLoop(false);
         this.beRight = true;
      }

      this.velY = -1050;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(frogAnimation);
      frogAnimation = null;
   }

   public void doInitWhileInCamera() {
      this.posX = this.limitLeftX + (this.mWidth >> 1) + 2048;
      this.posY = this.getGroundY(this.posX, this.posY);
      this.IsFirstCoaxar = false;
      this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
      this.beRight = false;
   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.drawer);
         this.drawCollisionRect(var1);
         if (SonicDebug.showCollisionRect) {
            this.drawAlertRangeLine(var1, this.alert_state, this.posX >> 6, this.posY >> 6, MapManager.getCamera());
         }
      }

   }

   public void logic() {
      if (!this.dead) {
         int var4 = this.posX;
         int var1 = this.posY;
         int var2 = this.limitLeftX;
         int var3 = this.limitRightX;
         this.alert_state = this.checkPlayerInEnemyAlertRange(var4 >> 6, var1 >> 6, 55, 160, var2 >> 6, var3 >> 6, 16);
         var2 = this.posX;
         var1 = this.posY;
         switch(this.state) {
         case 0:
            if (this.posX < player.getCheckPositionX()) {
               this.drawer.setActionId(0);
               this.drawer.setTrans(2);
               this.drawer.setLoop(true);
            } else {
               this.drawer.setActionId(0);
               this.drawer.setTrans(0);
               this.drawer.setLoop(true);
            }

            if (this.isInCamera() && !this.IsFirstCoaxar) {
               this.frog_time = PlayerObject.getTimeCount();
               this.IsFirstCoaxar = true;
            }

            if (this.IsFirstCoaxar) {
               this.interval = (PlayerObject.getTimeCount() - this.frog_time + 1) % 5000;
               if (this.interval <= 100) {
                  this.state = 2;
                  if (this.drawer.getTransId() == 2) {
                     this.drawer.setActionId(2);
                     this.drawer.setTrans(2);
                     this.drawer.setLoop(false);
                  } else if (this.drawer.getTransId() == 0) {
                     this.drawer.setActionId(2);
                     this.drawer.setTrans(0);
                     this.drawer.setLoop(false);
                  }
               }
            }

            if (this.jumpChk()) {
               if (this.posX < player.getCheckPositionX()) {
                  if (this.velocity < 0) {
                     this.velocity = -this.velocity;
                  }
               } else if (this.velocity > 0) {
                  this.velocity = -this.velocity;
               }

               if (this.posX + this.velocity * 10 > this.limitLeftX && this.posX + this.velocity * 10 < this.limitLeftX + this.mWidth) {
                  this.jumpInit();
               }
            }

            this.posY = this.getGroundY(this.posX, this.posY);
            this.checkWithPlayer(var2, var1, this.posX, this.posY);
            break;
         case 1:
            this.posX = GameTime.advancePosition(this, "posX", this.posX, "velocity", this.velocity);
            if (this.posY + GameTime.displacement(GameTime.integratedVelocity(this, "velY", this.velY)) > this.getGroundY(this.posX, this.posY)) {
               this.posY = this.getGroundY(this.posX, this.posY);
               this.state = 0;
               if (this.posX < player.getCheckPositionX()) {
                  this.drawer.setActionId(2);
                  this.drawer.setTrans(2);
                  this.drawer.setLoop(false);
               } else {
                  this.drawer.setActionId(2);
                  this.drawer.setTrans(0);
                  this.drawer.setLoop(false);
               }
            } else {
               this.velY = GameTime.advance(this, "velY", this.velY, DRIP_ACC);
               this.posY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
            }

            if (this.drawer.checkEnd()) {
               this.state = 0;
               if (this.posX < player.getCheckPositionX()) {
                  this.drawer.setActionId(2);
                  this.drawer.setTrans(2);
                  this.drawer.setLoop(false);
               } else {
                  this.drawer.setActionId(2);
                  this.drawer.setTrans(0);
                  this.drawer.setLoop(false);
               }
            }

            this.checkWithPlayer(var2, var1, this.posX, this.posY);
            break;
         case 2:
            if (this.drawer.checkEnd()) {
               this.state = 0;
               if (this.drawer.getTransId() == 2) {
                  this.drawer.setActionId(0);
                  this.drawer.setTrans(2);
                  this.drawer.setLoop(true);
               } else if (this.drawer.getTransId() == 0) {
                  this.drawer.setActionId(0);
                  this.drawer.setTrans(0);
                  this.drawer.setLoop(true);
               }
            }

            if (this.jumpChk()) {
               if (this.posX < player.getCheckPositionX()) {
                  if (this.velocity < 0) {
                     this.velocity = -this.velocity;
                  }
               } else if (this.velocity > 0) {
                  this.velocity = -this.velocity;
               }

               this.jumpInit();
            }
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3;
      if (this.state == 1) {
         var3 = this.collisionRect;
         var3.setRect(var1 - 448, var2 - 1280, 896, 768);
      } else {
         var3 = this.collisionRect;
         var3.setRect(var1 - 512, var2 - 1280, 1024, 1280);
      }

   }
}
