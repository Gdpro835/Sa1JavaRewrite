package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class Balloon extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1600;
   private static final int COLLISION_WIDTH = 1408;
   private static final int POP_POWER = 1600;
   private static Animation balloonAnimation;
   private AnimationDrawer drawer;

   protected Balloon(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (balloonAnimation == null) {
         balloonAnimation = new Animation("/animation/balloon");
      }

      this.drawer = balloonAnimation.getDrawer(Math.abs(this.iLeft) % 3 * 2, true, 0);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(balloonAnimation);
      balloonAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doInitWhileInCamera() {
      this.used = false;
      this.drawer.setActionId(Math.abs(this.iLeft) % 3 * 2);
      this.drawer.setLoop(true);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.used) {
         player.bePop(1600, 1);
         this.used = true;
         this.drawer.setActionId(Math.abs(this.iLeft) % 3 * 2 + 1);
         this.drawer.setLoop(false);
         SoundSystem.getInstance().playSe(57);
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 704, var2 - 1600, 1408, 1600);
   }
}
