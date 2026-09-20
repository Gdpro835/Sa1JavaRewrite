package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class RailIn extends GimmickObject {
   private static final int COLLISION_HEIGHT = 2560;
   private static final int COLLISION_WIDTH = 2560;
   private static final int IMAGE_HEIGHT = 1536;
   public static MFImage railInOutImage;

   protected RailIn(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (railInOutImage == null) {
         railInOutImage = MFImage.createImage("/gimmick/gimmick_67_68.png");
      }

   }

   public static void releaseAllResource() {
      railInOutImage = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      switch(var2) {
      case 1:
         if (var1 == player && !this.used) {
            player.railIn(this.posX, this.posY);
            this.used = true;
         }
         break;
      case 2:
      case 3:
         var1.beStop(0, var2, this);
      }

   }

   public void doWhileNoCollision() {
      this.used = false;
   }

   public void doWhileRail(PlayerObject var1, int var2) {
      var1.setAnimationId(21);
   }

   public void draw(MFGraphics var1) {
      MFImage var4 = railInOutImage;
      int var2 = this.posX;
      int var3 = this.posY;
      this.drawInMap(var1, var4, var2, var3 - 2560 + 1536, 17);
      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 3;
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 1280, var2 - 2560, 2560, 2560);
   }
}
