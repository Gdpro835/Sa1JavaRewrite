package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import State.GameState;
import com.sega.mobile.framework.device.MFGraphics;

class Marker extends GimmickObject {
   private static final int MARKER_HEIGHT = 3200;
   private static final int MARKER_HEIGHT_61 = 16384;
   private static final int MARKER_HEIGHT_61_2 = 2560;
   private static final int MARKER_WIDTH = 640;
   private static final int MARKER_WIDTH_61 = 9216;
   private static Animation markerAnimation;
   private int MarkID = 0;
   private AnimationDrawer drawer;
   private boolean isStage61;

   public Marker(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.used = false;
      if (markerAnimation == null) {
         markerAnimation = new Animation("/animation/se_marker");
      }

      this.drawer = markerAnimation.getDrawer(0, true, 0);
      this.MarkID = var4;
      boolean var8;
      if (StageManager.getStageID() == 10) {
         var8 = true;
      } else {
         var8 = false;
      }

      this.isStage61 = var8;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(markerAnimation);
      markerAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1 instanceof PlayerKnuckles) {
         ((PlayerKnuckles)var1).dripDownUnderWater();
      }

      if (!this.used) {
         var1 = player;
         if (PlayerObject.currentMarkId < this.iLeft) {
            this.used = true;
            this.drawer.setActionId(1);
            this.drawer.setLoop(false);
            if (stageModeState == 0) {
               StageManager.saveCheckPoint(this.posX, this.posY);
            }

            if (this.isStage61) {
               if (player.faceDegree == 0) {
                  PlayerObject.currentMarkId = this.iLeft;
                  RocketSeparateEffect.getInstance().init(this.iLeft - 1);
               } else {
                  this.used = false;
               }
            } else {
               PlayerObject.currentMarkId = this.iLeft;
               if (!GameState.PreGame) {
                  soundInstance.playSe(42);
               }
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (StageManager.getStageID() != 10) {
         this.drawInMap(var1, this.drawer, this.posX, this.posY);
         if (this.drawer.checkEnd()) {
            this.drawer.setActionId(2);
            this.drawer.setLoop(true);
         }

         this.drawCollisionRect(var1);
      }

   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (!this.used) {
         PlayerObject var1 = player;
         if (PlayerObject.currentMarkId >= this.iLeft) {
            this.used = true;
            this.drawer.setActionId(2);
            this.drawer.setLoop(true);
            var1 = player;
            if (PlayerObject.currentMarkId == this.iLeft && StageManager.getStageID() == 10) {
               RocketSeparateEffect.getInstance().functionSecond(this.iLeft - 1);
            }
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 320, var2 - 3200, 640, 3200);
      if (StageManager.getStageID() == 10) {
         switch(this.iLeft - 1) {
         case 0:
            this.collisionRect.setRect(var1 - 320, var2 - 16384, 640, 16384);
            break;
         default:
            this.collisionRect.setRect(var1 - 4608, var2 - 3200, 9216, 2560);
         }
      }

   }
}
