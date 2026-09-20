package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class SpringIsland extends GimmickObject {
   private static final int COLLISION_HEIGHT = 2688;
   private static final int COLLISION_WIDTH = 1920;
   private static final int COLLSION_OFFSET_Y = 1856;
   private static int SPRING_POWER;
   private static Animation animation;
   private int debugCollisionHeight = 0;
   private AnimationDrawer drawer;
   private int initPos;
   private boolean isH;
   private MoveCalculator moveCal;
   private int offset_distance;

   static {
      SPRING_POWER = Spring.SPRING_POWER[0];
   }

   protected SpringIsland(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (animation == null) {
         if (StageManager.getCurrentZoneId() != 4 && StageManager.getCurrentZoneId() != 6) {
            animation = new Animation("/animation/spring_island");
         } else {
            StringBuilder var9 = new StringBuilder("/animation/spring_island");
            String var10 = var9.append(StageManager.getCurrentZoneId()).toString();
            animation = new Animation(var10);
         }
      }

      this.drawer = animation.getDrawer(0, false, 0);
      boolean var8;
      if (this.mWidth >= this.mHeight) {
         this.isH = true;
         if (this.iLeft == 0) {
            var8 = false;
         } else {
            var8 = true;
         }
      } else {
         this.isH = false;
         if (this.iTop == 0) {
            var8 = false;
         } else {
            var8 = true;
         }
      }

      if (this.isH) {
         var1 = this.posX;
      } else {
         var1 = this.posY;
      }

      this.initPos = var1;
      if (this.posX == 69632 && this.posY == 35328) {
         this.debugCollisionHeight = 3200;
      }

      if (this.isH) {
         var1 = this.posX;
      } else {
         var1 = this.posY;
      }

      if (this.isH) {
         var2 = this.mWidth;
      } else {
         var2 = this.mHeight;
      }

      this.moveCal = new MoveCalculator(var1, var2, var8);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(animation);
      animation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (PlayerObject.getCharacterID() == 3 && (var1.myAnimationID < 6 || var1.myAnimationID > 7) && (var2 == 4 || var2 == 1)) {
         var1.beSpring(SPRING_POWER * 13 / 10, 1);
         ((PlayerAmy)var1).skipBeStop = true;
         this.drawer.setActionId(1);
         soundInstance.playSe(37);
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var2 == 2) {
         var1.beStop(0, var2, this);
      } else if (var2 == 3) {
         var1.beStop(0, var2, this);
      } else if (var2 == 4) {
         if (var1.animationID != 9) {
            if (var1.velX < 0) {
               var1.beStop(0, 2, this);
            } else if (var1.velX > 0) {
               var1.beStop(0, 3, this);
            }
         }
      } else if (var2 == 1) {
         var1.beSpring(SPRING_POWER, var2);
         this.drawer.setActionId(1);
         soundInstance.playSe(37);
      } else if (var2 == 0) {
         var1.beStop(0, var2, this);
      }

   }

   public void draw(MFGraphics var1) {
      int var2;
      if (player.isInWater) {
         var2 = Spring.SPRING_INWATER_POWER[0];
      } else {
         var2 = Spring.SPRING_POWER[0];
      }

      SPRING_POWER = var2;
      this.drawInMap(var1, this.drawer);
      if (this.drawer.checkEnd() && this.drawer.getActionId() == 1) {
         this.drawer.setActionId(0);
      }

   }

   public void logic() {
      int var2 = this.posX;
      int var1 = this.posY;
      if (this.isH) {
         if (this.iLeft == 0) {
            this.posX = this.moveCal.getPosition();
         } else {
            this.offset_distance = this.initPos - this.moveCal.getPosition();
            this.posX = this.initPos + this.offset_distance;
         }
      } else if (this.iTop == 0) {
         this.posY = this.moveCal.getPosition();
      } else {
         this.offset_distance = this.initPos - this.moveCal.getPosition();
         this.posY = this.initPos + this.offset_distance;
      }

      this.checkWithPlayer(var2, var1, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var4 = this.collisionRect;
      int var3;
      if (this.debugCollisionHeight == 0) {
         var3 = 2688;
      } else {
         var3 = this.debugCollisionHeight;
      }

      var4.setRect(var1 - 960, var2 + 1856 - 2688, 1920, var3);
   }
}
