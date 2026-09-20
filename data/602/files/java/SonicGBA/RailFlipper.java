package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class RailFlipper extends GimmickObject {
   private static Animation railFlipperAnimation = null;
   private AnimationDrawer drawer;

   protected RailFlipper(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (railFlipperAnimation == null) {
         railFlipperAnimation = new Animation("/animation/rail_flipper");
      }

      this.drawer = railFlipperAnimation.getDrawer(0, false, 0);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(railFlipperAnimation);
      railFlipperAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileRail(PlayerObject var1, int var2) {
      if (this.firstTouch) {
         player.setRailFlip();
         this.drawer.setActionId(1);
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
      if (this.drawer.getActionId() == 1 && this.drawer.checkEnd()) {
         this.drawer.setActionId(0);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(this.posX, this.posY - 512, 512, 512);
   }
}
