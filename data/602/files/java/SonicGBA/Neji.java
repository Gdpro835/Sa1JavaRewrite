package SonicGBA;

import GameEngine.time.GameTime;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Neji extends GimmickObject {
   private static final int PLAYER_OFFSET = 0;
   private static final int STAD_GMK_NEJI_ADD_SPEED = 48;
   private static final int STAD_GMK_NEJI_MAX_SPEED = 2304;
   private static final int STAD_GMK_NEJI_MIN_SPEED = 232;
   private static final int VELOCITY = 300;
   private static final int VELOCITY_CHANGE = 200;
   public static Animation nejiAnimation;
   public AnimationDrawer drawer;
   private int frameCnt = 0;
   private boolean touching = false;
   private int velocity;

   protected Neji(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.frameCnt = GameTime.set(this, "frameCnt", 0);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(nejiAnimation);
      nejiAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.firstTouch) {
         isGotRings = false;
         player.degreeForDraw = 0;
      }

      if (this.firstTouch && !this.touching && !player.hurtNoControl) {
         var1.setOutOfControl(this);
         var1.setAnimationId(25);
         var1.setFootPositionY(this.posY + 0);
         this.touching = true;
         this.velocity = 0;
      }

   }

   public void draw(MFGraphics var1) {
      this.drawCollisionRect(var1);
   }

   public void logic() {
      if (this.touching) {
         this.frameCnt = GameTime.advance(this, "frameCnt", this.frameCnt, 1);
         if (this.velocity < 232) {
            this.velocity = GameTime.advance(this, "velocity", this.velocity, 48);
         }

         if (this.velocity > 2304) {
            this.velocity = 2304;
         }

         if (Key.repeat(Key.gLeft)) {
            if (this.velocity > 232) {
               this.velocity = GameTime.advance(this, "velocity", this.velocity, -(48));
            }
         } else if (Key.repeat(Key.gRight)) {
            this.velocity = GameTime.advance(this, "velocity", this.velocity, 48);
         }

         int var2 = player.getFootPositionX() + this.velocity;
         int var1 = var2;
         if (var2 > this.collisionRect.x1) {
            var1 = this.collisionRect.x1;
         }

         PlayerObject var5 = player;
         int var4 = player.getFootPositionX();
         var2 = player.getFootPositionY();
         int var3 = player.getFootPositionY();
         var5.checkWithObject(var4, var2, var1, var3);
         player.setFootPositionX(var1);
         player.setFootPositionY(this.posY + 0);
         boolean var7 = false;
         boolean var6 = false;
         if (Key.press(16777216)) {
            var7 = true;
         }

         if (!Key.repeat(Key.gUp | 4) && !Key.repeat(Key.gDown | 16392)) {
            var3 = player.drawer.getCurrentFrame();
            if (player.getAnimationId() == 25) {
               if (var3 < 4) {
                  var6 = true;
               }
            } else {
               player.getAnimationId();
            }
         } else if (Key.repeat(Key.gUp | 4)) {
            var6 = true;
         } else if (Key.repeat(Key.gDown | 16392)) {
            var6 = false;
         }

         if (var7) {
            player.outOfControl = false;
            player.setVelX(0);
            player.collisionState = 1;
            var5 = player;
            byte var8;
            if (var6) {
               var8 = -1;
            } else {
               var8 = 1;
            }

            var5.setVelY(var8 * 1800);
            player.setFootPositionY(this.posY + 768);
            player.setAnimationId(4);
            if (!(player instanceof PlayerAmy) && this.firstTouch) {
               soundInstance.playSe(4);
            }

            this.touching = false;
         }

         this.firstTouch = false;
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var5 = this.collisionRect;
      var2 = this.posX;
      int var3 = this.iLeft;
      var1 = this.posY;
      int var4 = this.mWidth;
      var5.setRect(var2 + var3 * 512, var1, var4, 512);
   }
}
