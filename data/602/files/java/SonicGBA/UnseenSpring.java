package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class UnseenSpring extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 64;
   private static int SPRING_POWER;
   private AnimationDrawer drawer;

   static {
      SPRING_POWER = Spring.SPRING_POWER[0];
   }

   protected UnseenSpring(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (Spring.springAnimation == null) {
         Spring.springAnimation = new Animation("/animation/se_bane_kiro");
      }

      this.drawer = Spring.springAnimation.getDrawer(16, false, 0);
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (player.collisionState == 0) {
         player.beSpring(SPRING_POWER, 1);
         this.drawer.setActionId(17);
         soundInstance.playSe(37);
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
      if (this.drawer.getActionId() == 17) {
         this.drawInMap(var1, this.drawer, this.posX, this.posY);
         if (this.drawer.checkEnd()) {
            this.drawer.setActionId(16);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 32, var2 - 512, 64, 1024);
   }
}
