package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import State.GameState;
import com.sega.mobile.framework.device.MFGraphics;

class SpSpring extends Spring {
   private static final int COLLISION_HEIGHT = 2048;
   private static final int COLLISION_WIDTH = 1920;
   private static final int ENTERED_SP_SPRING_POWER = 2096;
   private static final int SP_SPRING_POWER = 2800;
   private static final int VELOCITY_MINUS = 7;
   public static Animation springAnimation = null;
   private AnimationDrawer drawer;
   private boolean spEntered;

   public SpSpring(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (springAnimation == null) {
         springAnimation = new Animation("/animation/sp_bane");
      }

      this.springPower = 2800;
      this.drawer = springAnimation.getDrawer(0, false, 0);
      this.spEntered = GameState.isBackFromSpStage;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(springAnimation);
      springAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (PlayerObject.getCharacterID() == 3 && var3 != 6 && var3 != 7 && var2 == 4) {
         if (this.spEntered) {
            player.beSpring(2724, 1);
            player.setAnimationId(14);
            soundInstance.playSe(37);
         } else {
            player.beSpSpring(this.springPower, var2);
            StageManager.saveSpecialStagePoint(this.posX - 1920, this.posY);
         }

         this.drawer.setActionId(1);
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.used) {
         var1.beStop(this.collisionRect.x0, var2, this);
         if (var1 == player) {
            switch(var2) {
            case 1:
               if (this.spEntered) {
                  player.beSpring(2096, var2);
                  player.setAnimationId(14);
                  soundInstance.playSe(37);
               } else {
                  player.beSpSpring(this.springPower, var2);
                  StageManager.saveSpecialStagePoint(this.posX - 1920, this.posY);
               }

               this.drawer.setActionId(1);
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer, this.posX, this.posY);
      if (this.drawer.checkEnd()) {
         this.drawer.setActionId(0);
      }

      this.drawCollisionRect(var1);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 960, var2 - 2048, 1920, 2048);
   }
}
