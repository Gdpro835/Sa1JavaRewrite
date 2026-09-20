package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class TorchFire extends GimmickObject {
   private static AnimationDrawer drawer;
   private static AnimationDrawer drawer2;

   protected TorchFire(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (drawer == null) {
         if (StageManager.getCurrentZoneId() != 6) {
            drawer = (new Animation("/animation/torch_fire")).getDrawer(0, true, 0);
         } else {
            drawer = (new Animation("/animation/light")).getDrawer(0, true, 0);
            drawer2 = (new Animation("/animation/light")).getDrawer(1, true, 0);
            drawer2.setPause(true);
         }

         drawer.setPause(true);
      }

      this.posX += 256;
      this.posY += 1024;
   }

   public static void releaseAllResource() {
      Animation.closeAnimationDrawer(drawer);
      drawer = null;
      Animation.closeAnimationDrawer(drawer2);
      drawer2 = null;
   }

   public static void staticLogic() {
      drawer.moveOn();
      if (StageManager.getCurrentZoneId() == 6 && drawer2 != null) {
         drawer2.moveOn();
      }

   }

   public void draw(MFGraphics var1) {
      if (StageManager.getCurrentZoneId() != 6) {
         this.drawInMap(var1, drawer);
      } else if (this.iLeft == 0) {
         this.drawInMap(var1, drawer, this.posX - 256, this.posY - 1280);
      } else {
         this.drawInMap(var1, drawer2, this.posX - 256, this.posY - 1280);
      }

   }

   public int getPaintLayer() {
      return 0;
   }
}
