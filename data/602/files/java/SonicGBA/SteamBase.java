package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class SteamBase extends GimmickObject {
   private static final int COLLISION_HEIGHT = 768;
   private static final int COLLISION_WIDTH = 1280;
   private static final int PLATFORM_OFFSET_Y = -768;
   private static Animation animation;
   private static byte count;
   private static boolean shotFlag;
   private AnimationDrawer drawer;
   public SteamHurt sh;
   public SteamPlatform sp;

   protected SteamBase(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (animation == null) {
         animation = new Animation("/animation/steam");
      }

      this.drawer = animation.getDrawer(0, true, 0);
      this.sh = new SteamHurt(this.posX, this.posY, this);
      this.sp = new SteamPlatform(this.posX, this.posY - 768, this);
      GameObject.addGameObject(this.sh, this.posX, this.posY);
      GameObject.addGameObject(this.sp, this.posX, this.posY);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(animation);
      animation = null;
   }

   public static void staticLogic() {
      if (count > 0) {
         --count;
      }

      if (count == 0) {
         SteamPlatform.shot();
         count = 50;
      }

      SteamPlatform.staticLogic();
   }

   public void close() {
      this.drawer = null;
      this.sp = null;
      this.sh = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      var1.beStop(0, var2, this);
   }

   public void draw(MFGraphics var1) {
      AnimationDrawer var3 = this.drawer;
      byte var2;
      if (SteamPlatform.isShotting()) {
         var2 = 1;
      } else {
         var2 = 0;
      }

      var3.setActionId(var2);
      this.drawInMap(var1, this.drawer);
      this.sp.drawPlatform(var1);
      if (this.isInCamera() && count == 50) {
         SoundSystem.getInstance().playSe(53);
         count = 49;
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 640, var2 - 768, 1280, 768);
   }
}
