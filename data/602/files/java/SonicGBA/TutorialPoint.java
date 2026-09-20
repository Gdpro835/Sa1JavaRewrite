package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class TutorialPoint extends GimmickObject {
   private static final int COLLISION_WIDTH = 2048;
   private static Animation tutorialAnimation;
   private AnimationDrawer drawer;

   protected TutorialPoint(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(tutorialAnimation);
      tutorialAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void draw(MFGraphics var1) {
   }

   public int getPaintLayer() {
      return 0;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1024, var2 - 1024, 2048, 2048);
   }
}
