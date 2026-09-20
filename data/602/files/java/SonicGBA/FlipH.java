package SonicGBA;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Line;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class FlipH extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1536;
   private static final int COLLISION_WIDTH = 2880;
   private static final int FLIP_POWER = 2450;
   private static final int FLIP_POWER_MAX = 2858;
   private static final int FLIP_POWER_MIN = 2163;
   private static final int FLIP_POWER_RANGE = 659;
   private static final int FLIP_POWER_X = 200;
   private static Animation flipAnimation;
   private Line collisionLine;
   private AnimationDrawer drawer;
   private boolean isUp;
   private boolean justPop = false;
   private int leftBorder;
   private boolean noTouch = true;
   private int rightBorder;

   protected FlipH(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (flipAnimation == null) {
         flipAnimation = new Animation("/animation/flip");
      }

      Animation var8 = flipAnimation;
      byte var9;
      if (this.iLeft == 0) {
         var9 = 2;
      } else {
         var9 = 0;
      }

      this.drawer = var8.getDrawer(2, true, var9);
      if (this.iLeft == 0) {
         this.rightBorder = this.posX;
         this.leftBorder = this.posX - 2432;
      } else {
         this.leftBorder = this.posX;
         this.rightBorder = this.posX + 2432;
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(flipAnimation);
      flipAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1 == player) {
         if (this.noTouch && !this.justPop) {
            if (var2 != 1 && var2 != 2 && var2 != 3) {
               if (var2 == 0) {
                  this.isUp = false;
                  this.noTouch = false;
               }
            } else {
               this.isUp = true;
               this.noTouch = false;
               if (this.firstTouch) {
                  if (player.getFootPositionX() < this.leftBorder) {
                     player.setFootPositionX(this.leftBorder);
                  } else if (player.getFootPositionX() > this.rightBorder) {
                     player.setFootPositionX(this.rightBorder);
                  }

                  if (!(player instanceof PlayerAmy) && this.firstTouch) {
                     soundInstance.playSe(4);
                  }
               }
            }
         }

         if (!this.noTouch) {
            int var3 = this.collisionLine.getY(player.getFootPositionX());
            int var4 = player.getFootPositionY();
            if (this.isUp && var4 >= var3 && player.getMoveDistance().y >= 0) {
               player.setNoKey();
               player.beStop(var3, 1, this);
               player.setFootPositionY(var3);
               var1 = player;
               short var5;
               if (this.iLeft == 0) {
                  var5 = -128;
               } else {
                  var5 = 128;
               }

               var1.setVelX(var5);
               player.setAnimationId(4);
            }
         }

         if (var2 == 0) {
            player.beStop(0, 0, this);
         }
      }

   }

   public void doWhileNoCollision() {
      this.noTouch = true;
   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
      if (this.drawer.checkEnd()) {
         this.drawer.setActionId(2);
         this.drawer.setLoop(true);
      }

      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (this.justPop) {
         this.justPop = false;
      }

      if (!this.noTouch && Key.press(Key.gUp | 16777216)) {
         this.drawer.setActionId(3);
         this.drawer.setLoop(false);
         int var2 = player.getFootPositionX();
         int var1;
         if (this.iLeft == 0) {
            var1 = this.posX;
         } else {
            var1 = this.posX;
         }

         var1 = Math.abs(var2 - var1) * 659 / 2880;
         var1 += 2163;
         player.bePop(var1, 1);
         player.setAnimationId(4);
         PlayerObject var3 = player;
         if (this.iLeft == 0) {
            var1 = -var1 / 10;
         } else {
            var1 /= 10;
         }

         var3.setVelX(var1);
         this.noTouch = true;
         this.justPop = true;
         SoundSystem.getInstance().playSe(54);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3;
      if (this.iLeft == 0) {
         var3 = this.collisionRect;
         var3.setTwoPosition(var1 + 512, var2 - 512 + 1, var1 + 512 - 2880, var2 - 512 + 1 + 1536);
      } else {
         var3 = this.collisionRect;
         var3.setRect(var1 - 512, var2 - 512 + 1, 2880, 1536);
      }

      if (this.collisionLine == null) {
         this.collisionLine = new Line();
      }

      Line var4;
      if (this.iLeft == 0) {
         var4 = this.collisionLine;
         var4.setProperty(var1, var2 - 512, var1 - 2432, var2 + 704);
      } else {
         var4 = this.collisionLine;
         var4.setProperty(var1, var2 - 512, var1 + 2432, var2 + 704);
      }

   }
}
