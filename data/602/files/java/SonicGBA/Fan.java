package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Fan extends GimmickObject {
   private static Animation animation;
   private AnimationDrawer drawer;

   protected Fan(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (animation == null) {
         animation = new Animation("/animation/bigfan");
      }

      if (animation != null) {
         this.drawer = animation.getDrawer(0, true, 0);
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(animation);
      animation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
      this.drawCollisionRect(var1);
   }
}
