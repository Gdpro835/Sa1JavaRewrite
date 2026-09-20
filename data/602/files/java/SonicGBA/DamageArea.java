package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class DamageArea extends GimmickObject {
   private static AnimationDrawer magmaDrawer;
   private AnimationDrawer drawer;
   private boolean isActived;

   protected DamageArea(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (StageManager.getCurrentZoneId() == 2) {
         if (magmaDrawer == null) {
            magmaDrawer = (new Animation("/animation/magma_bg")).getDrawer(0, true, 0);
            magmaDrawer.setPause(true);
         }

         this.drawer = magmaDrawer;
      }

      this.isActived = false;
   }

   public static void releaseAllResource() {
      Animation.closeAnimationDrawer(magmaDrawer);
      magmaDrawer = null;
   }

   public static void staticLogic() {
      if (magmaDrawer != null) {
         magmaDrawer.moveOn();
      }

   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.iLeft == 0) {
         var1.beHurt();
      } else {
         isDamageSandActive = true;
         this.isActived = true;
         var1.setDie(false);
      }

   }

   public void doWhileNoCollision() {
      if (this.isActived) {
         isDamageSandActive = false;
         this.isActived = false;
      }

   }

   public void draw(MFGraphics var1) {
      if (this.drawer != null) {
         this.drawInMap(var1, magmaDrawer, this.posX, this.posY - 1024);
         if (this.mWidth > 6144) {
            this.drawInMap(var1, magmaDrawer, this.posX + 6144, this.posY - 1024);
         }
      }

   }
}
