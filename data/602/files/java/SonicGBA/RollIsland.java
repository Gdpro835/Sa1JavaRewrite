package SonicGBA;

import GameEngine.time.GameTime;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class RollIsland extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1792;
   private static final int COLLISION_WIDTH = 1792;
   public static final int[][] DIRECTION;
   private static final int MAX_VELOCITY = 1280;
   private static final int MOVE_POWER = 44;
   private static final int ROLL_MAX_VELOCITY = 256;
   private static final int VELOCITY = 250;
   private static final int VELOCITY_X = 200;
   private static Animation animation = null;
   private boolean controlling;
   private AnimationDrawer drawer;
   private int frame;
   private boolean isActive;
   private int moveDistance;
   private byte noCollisionCount;
   private int posOriginalX;
   private int posOriginalY;
   private int velocity;

   static {
      int[] var2 = new int[]{1, 0};
      int[] var0 = new int[]{0, 1};
      int[] var3 = new int[]{-1, 0};
      int[] var1 = new int[]{0, -1};
      DIRECTION = new int[][]{var2, {1, 1}, var0, {-1, 1}, var3, {-1, -1}, var1, {1, -1}};
   }

   protected RollIsland(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX += 256;
      this.posY += 256;
      if (animation == null) {
         animation = new Animation("/animation/roll_island");
      }

      this.drawer = animation.getDrawer(0, true, 0);
      this.moveDistance = var6 * 42 * 4 + var5 * 4;
      if (this.iLeft % 2 == 1) {
         this.moveDistance >>= 1;
      }

      this.posOriginalX = this.posX;
      this.posOriginalY = this.posY;
      this.controlling = false;
      this.isActive = false;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(animation);
      animation = null;
   }

   private boolean spinCheck() {
      boolean var2 = false;
      boolean var1 = var2;
      if (player.faceDirection) {
         var1 = var2;
         if (this.velocity > 0) {
            var1 = true;
         }
      }

      var2 = var1;
      if (!player.faceDirection) {
         var2 = var1;
         if (this.velocity < 0) {
            var2 = true;
         }
      }

      return var2;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.noCollisionCount <= 0) {
         var1.beStop(0, var2, this);
         if (var2 == 1) {
            var1.setOutOfControl(this);
            this.controlling = true;
         }

         this.isActive = true;
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (this.noCollisionCount > 0) {
         this.noCollisionCount = (byte) Math.max(0, GameTime.advance(this, "noCollisionCount", this.noCollisionCount, -(1)));
      }

      if (player.outOfControl && player.outOfControlObject == this) {
         if (Key.repeat(Key.gRight)) {
            this.velocity = GameTime.advance(this, "velocity", this.velocity, 44);
            if (this.velocity > 1280) {
               this.velocity = 1280;
            }

            player.faceDirection = true;
         } else if (Key.repeat(Key.gLeft)) {
            this.velocity = GameTime.advance(this, "velocity", this.velocity, -(44));
            if (this.velocity < -1280) {
               this.velocity = -1280;
            }

            player.faceDirection = false;
         } else if (this.posX == this.posOriginalX && this.posY == this.posOriginalY) {
            player.spinLogic2();
            if (player instanceof PlayerAmy) {
               player.dashRollingLogicCheck();
            }

            if (Math.abs(player.getVelX()) >= 64 && player.isAfterSpinDash) {
               this.velocity = player.getVelX();
               player.isAfterSpinDash = false;
            }
         } else {
            this.velocity = GameTime.advance(this, "velocity", this.velocity, -(44));
         }

         this.rollLogic();
         int var3 = player.getFootPositionX();
         int var1 = player.getFootPositionY();
         player.setFootPositionX(this.posX);
         player.setFootPositionY(this.collisionRect.y0);
         PlayerObject var5 = player;
         int var2 = player.getFootPositionX();
         int var4 = player.getFootPositionY();
         var5.checkWithObject(var3, var1, var2, var4);
         if (player.outOfControl && player.outOfControlObject == this) {
            if (!Key.repeat(Key.gUp | 4)) {
               if (player.getAnimationId() == 4) {
                  if (!this.spinCheck()) {
                     if (Math.abs(this.velocity) == 0) {
                        if (!Key.repeat(Key.gDown)) {
                           player.setAnimationId(0);
                        }
                     } else if (Math.abs(this.velocity) < PlayerObject.SPEED_LIMIT_LEVEL_1) {
                        player.setAnimationId(1);
                     } else if (Math.abs(this.velocity) < PlayerObject.SPEED_LIMIT_LEVEL_2) {
                        player.setAnimationId(2);
                     } else {
                        player.setAnimationId(3);
                     }
                  }
               } else if (Math.abs(this.velocity) == 0) {
                  if (!Key.repeat(Key.gDown)) {
                     player.setAnimationId(0);
                  }
               } else if (Math.abs(this.velocity) < PlayerObject.SPEED_LIMIT_LEVEL_1) {
                  player.setAnimationId(1);
               } else if (Math.abs(this.velocity) < PlayerObject.SPEED_LIMIT_LEVEL_2) {
                  player.setAnimationId(2);
               } else {
                  player.setAnimationId(3);
               }
            }

            if (Key.press(16777216) && !Key.repeat(Key.gDown)) {
               player.outOfControl = false;
               this.controlling = false;
               if (player.getAnimationId() == 4) {
                  player.doJumpV();
               } else {
                  player.doJump();
               }

               if (this.velocity > 0) {
                  player.setVelX(200);
               } else if (this.velocity < 0) {
                  player.setVelX(-200);
               } else {
                  player.setVelX(0);
               }
            }
         }

         if (this.posX == this.posOriginalX || this.posY == this.posOriginalY) {
            player.lookUpCheck();
         }
      } else {
         this.velocity = GameTime.advance(this, "velocity", this.velocity, -(308));
         this.rollLogic();
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 896, var2 - 896, 1792, 1792);
   }

   public boolean releaseWhileBeHurt() {
      this.noCollisionCount = (byte) GameTime.set(this, "noCollisionCount", 0);
      return true;
   }

   public void rollLogic() {
      int var2 = DIRECTION[this.iLeft][0] * this.velocity * 20 / 100;
      int var3 = DIRECTION[this.iLeft][1] * this.velocity * 20 / 100;
      int var1;
      if (var2 > 0) {
         var1 = var2;
         if (var2 > 256) {
            var1 = 256;
         }
      } else {
         var1 = var2;
         if (var2 < 0) {
            var1 = var2;
            if (var2 < -256) {
               var1 = -256;
            }
         }
      }

      if (var3 > 0) {
         var2 = var3;
         if (var3 > 256) {
            var2 = 256;
         }
      } else {
         var2 = var3;
         if (var3 < 0) {
            var2 = var3;
            if (var3 < -256) {
               var2 = -256;
            }
         }
      }

      this.posX = GameTime.advance(this, "posX", this.posX, var1);
      this.posY = GameTime.advance(this, "posY", this.posY, var2);
      if (this.velocity > 0) {
         if (DIRECTION[this.iLeft][0] > 0) {
            if (this.posX > this.posOriginalX + this.moveDistance) {
               this.posX = this.posOriginalX + this.moveDistance;
            }
         } else if (DIRECTION[this.iLeft][0] < 0 && this.posX < this.posOriginalX - this.moveDistance) {
            this.posX = this.posOriginalX - this.moveDistance;
         }

         if (DIRECTION[this.iLeft][1] > 0) {
            if (this.posY > this.posOriginalY + this.moveDistance) {
               this.posY = this.posOriginalY + this.moveDistance;
            }
         } else if (DIRECTION[this.iLeft][1] < 0 && this.posY < this.posOriginalY - this.moveDistance) {
            this.posY = this.posOriginalY - this.moveDistance;
         }

         this.drawer.setActionId(1);
      } else {
         if (DIRECTION[this.iLeft][0] > 0) {
            if (this.posX < this.posOriginalX) {
               this.posX = this.posOriginalX;
            }
         } else if (DIRECTION[this.iLeft][0] < 0 && this.posX > this.posOriginalX) {
            this.posX = this.posOriginalX;
         }

         if (DIRECTION[this.iLeft][1] > 0) {
            if (this.posY < this.posOriginalY) {
               this.posY = this.posOriginalY;
            }
         } else if (DIRECTION[this.iLeft][1] < 0 && this.posY > this.posOriginalY) {
            this.posY = this.posOriginalY;
         }

         this.drawer.setActionId(1);
      }

      this.drawer.setActionId(1);
      if (this.posX == this.posOriginalX && this.posY == this.posOriginalY) {
         this.velocity = 0;
         if (this.isActive) {
            soundInstance.stopLoopSe();
            this.isActive = false;
         }
      }

      if (this.velocity == 0) {
         this.drawer.setActionId(0);
      }

      if (this.posX != this.posOriginalX || this.posY != this.posOriginalY) {
         this.frame = GameTime.advance(this, "frame", this.frame, 1);
         if (Math.abs(this.velocity) != 0) {
            if (Math.abs(this.velocity) < 160) {
               if (GameTime.periodic(this, "frame", "rollLogic:311", this.frame, 7, 0)) {
                  soundInstance.playSe(76);
               }
            } else if (Math.abs(this.velocity) < 1020) {
               if (GameTime.periodic(this, "frame", "rollLogic:315", this.frame, 4, 0)) {
                  soundInstance.playSe(77);
               }
            } else if (GameTime.periodic(this, "frame", "rollLogic:318", this.frame, 2, 0)) {
               soundInstance.playSe(78);
            }
         }
      }

      this.refreshCollisionRect(this.posX, this.posY);
   }
}
