package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class CaperBed extends GimmickObject {
   private static final int COLLISION_HEIGHT = 768;
   private static final int COLLISION_WIDTH = 2944;
   private static final int MAX_POWER = 2948;
   private static final int MIN_POWER = 1082;
   private static Animation caperAnimation;
   private AnimationDrawer drawer;

   protected CaperBed(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (caperAnimation == null) {
         if (StageManager.getCurrentZoneId() != 6) {
            caperAnimation = new Animation("/animation/se_toramporin_" + StageManager.getCurrentZoneId());
         } else {
            caperAnimation = new Animation("/animation/se_toramporin_2");
         }
      }

      this.drawer = caperAnimation.getDrawer(0, false, 0);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(caperAnimation);
      caperAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      int var5 = player.getVelY();
      var1.beStop(this.collisionRect.y0, var2, this);
      int var3;
      if (var2 == 4 && var1 instanceof PlayerKnuckles && var1.myAnimationID == 33) {
         int var4 = Math.abs(var5) * 4 / 3;
         var3 = var4;
         if (var4 > 2948) {
            var3 = 2948;
         }

         var4 = var3;
         if (var3 < 1082) {
            var4 = 1082;
         }

         player.beSpring(var4, 1);
         this.drawer.setActionId(1);
         player.setAnimationId(14);
         SoundSystem.getInstance().playSe(48);
      }

      switch(var2) {
      case 1:
         if (var1 == player) {
            var3 = Math.abs(var5) * 4 / 3;
            var2 = var3;
            if (var3 > 2948) {
               var2 = 2948;
            }

            var3 = var2;
            if (var2 < 1082) {
               var3 = 1082;
            }

            player.beSpring(var3, 1);
            this.drawer.setActionId(1);
            player.setAnimationId(14);
            SoundSystem.getInstance().playSe(48);
         }
      default:
      }
   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
      if (this.drawer.checkEnd()) {
         this.drawer.setActionId(0);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1472, var2 - 768, 2944, 768);
   }
}
