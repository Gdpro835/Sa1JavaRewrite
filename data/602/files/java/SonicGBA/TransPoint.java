package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class TransPoint extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 1024;
   public static Animation caveAnimation;
   private int desX;
   private int desY;
   private AnimationDrawer drawer;

   protected TransPoint(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.desX = this.iLeft * 32 * 512 + (var6 << 6);
      this.desY = this.iTop * 32 * 512 + (var7 << 6);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      var1.beTrans(this.desX, this.desY);
      var1.pipeOut();
      SoundSystem.getInstance().playSe(37);
      player.setVelX(0);
      player.setVelY(0);
   }

   public void draw(MFGraphics var1) {
   }

   public int getPaintLayer() {
      return 0;
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 512, var2 - 512, 1024, 1024);
   }
}
