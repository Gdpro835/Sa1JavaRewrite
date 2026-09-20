package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Belt extends GimmickObject {
   private static final int DRAW_Y_OFFSET = 1024;
   private static final int MOVE_SPEED = 128;
   private static final int SNOW_MAX_SPEED = 118;
   private static final int SPIN_DASH_ATTENUATE_PERCENTAGE = 82;
   public static Animation beltAnimation;
   private AnimationDrawer drawer;
   private boolean isActived;

   protected Belt(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      switch(StageManager.getCurrentZoneId()) {
      case 4:
         this.isActived = false;
         break;
      default:
         if (beltAnimation == null) {
            StringBuilder var9 = (new StringBuilder("/animation/conveyor_")).append(StageManager.getCurrentZoneId());
            String var8;
            if (StageManager.getCurrentZoneId() == 6) {
               var8 = "" + (StageManager.getStageID() - 9);
            } else {
               var8 = "";
            }

            var8 = var9.append(var8).toString();
            beltAnimation = new Animation(var8);
         }

         if (beltAnimation != null) {
            byte var10;
            byte var11;
            if (StageManager.getCurrentZoneId() == 6) {
               if (var6 / 2 < 6) {
                  var10 = 0;
               } else {
                  var10 = 2;
               }

               if (this.iLeft == 0) {
                  var11 = 0;
               } else {
                  var11 = 1;
               }

               var1 = var10 + var11;
            } else {
               if (var6 / 2 == 6) {
                  var10 = 0;
               } else {
                  var10 = 2;
               }

               if (this.iLeft == 0) {
                  var11 = 0;
               } else {
                  var11 = 1;
               }

               var1 = var10 + var11;
            }

            this.drawer = beltAnimation.getDrawer(var1, true, 0);
         }

         this.isActived = false;
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(beltAnimation);
      beltAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (player instanceof PlayerKnuckles && player.collisionState == 4) {
         player.collisionState = 1;
         player.animationID = 1;
         soundInstance.stopLoopSe();
      }

   }

   public void doWhileNoCollision() {
      if (this.isActived) {
         label27: {
            switch(StageManager.getCurrentZoneId()) {
            case 4:
               player.isInSnow = false;
               player.isStopByObject = false;
               break label27;
            }

            if (player.getAnimationId() == 47 || player.getAnimationId() == 48) {
               player.setAnimationId(0);
            }

            player.speedLock = false;
         }

         this.isActived = false;
      }

   }

   public void draw(MFGraphics var1) {
      int var2;
      int var3;
      int var4;
      AnimationDrawer var5;
      switch(StageManager.getCurrentZoneId()) {
      case 4:
         break;
      case 5:
      default:
         var5 = this.drawer;
         var4 = this.posX;
         var3 = this.mWidth;
         var2 = this.posY;
         this.drawInMap(var1, var5, var4 + (var3 >> 1), var2 + 1024);
         break;
      case 6:
         if (this.iTop != 1) {
            var5 = this.drawer;
            var3 = this.posX;
            var2 = this.mWidth;
            var4 = this.posY;
            this.drawInMap(var1, var5, var3 + (var2 >> 1), var4 + 1024);
         }
      }

      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      PlayerObject var4;
      switch(StageManager.getCurrentZoneId()) {
      case 4:
         if (this.collisionRect.collisionChk(player.getCollisionRect()) && player.collisionState == 0) {
            player.isStopByObject = true;
            int var1 = player.getAnimationId();
            var4 = player;
            if (var1 == 4) {
               if (player.getVelX() > 118) {
                  player.setVelXPercent(82);
               } else if (player.getVelX() < -118) {
                  player.setVelXPercent(82);
               }
            } else if ((!(player instanceof PlayerSonic) || player.getCharacterAnimationID() < 13 || player.getCharacterAnimationID() > 15) && (!(player instanceof PlayerAmy) || player.myAnimationID != 7)) {
               if (player.getVelX() > 118) {
                  player.setVelX(118);
               } else if (player.getVelX() < -118) {
                  player.setVelX(-118);
               }
            }

            this.isActived = true;
            player.isInSnow = true;
         }
         break;
      default:
         if (StageManager.getStageID() != 10 && this.collisionRect.collisionChk(player.getCollisionRect()) && player.collisionState == 0) {
            player.speedLock = true;
            var4 = player;
            int var2 = player.footPointX;
            short var5;
            if (this.iLeft == 0) {
               var5 = -128;
            } else {
               var5 = 128;
            }

            int var3 = player.footPointY;
            var4.moveOnObject(var2 + var5, var3);
            if (player.getAnimationId() != 47) {
               player.getAnimationId();
            }

            this.isActived = true;
         }
      }

   }
}
