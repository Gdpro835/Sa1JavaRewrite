package SonicGBA;

import Lib.Coordinate;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class FreeFallPlatform extends GimmickObject {
   private static final int COLLISION_HEIGHT = 28;
   private static final int DRAW_HEIGHT = 40;
   private static final int DRAW_WIDTH = 96;
   private static MFImage image = null;
   private int posXorg;
   private int posYorg;
   private int rollCount;
   private FreeFallSystem system;

   protected FreeFallPlatform(FreeFallSystem var1, int var2, int var3) {
      super(0, var2, var3, 0, 0, 0, 0);
      this.system = var1;
      if (image == null) {
         try {
            image = MFImage.createImage("/gimmick/freefall_platform.png");
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

      Coordinate var6 = var1.getBarPosition();
      this.posXorg = var6.x;
      this.posYorg = var6.y;
   }

   public static void releaseAllResource() {
      image = null;
   }

   public void close() {
      this.system = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var2 != 0) {
         var1.beStop(0, var2, this);
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.system.initFlag) {
         if (this.system.moving) {
            this.drawInMap(var1, image, (int)(systemClock / 5L % 2L) * 96, 0, 96, 40, 0, 17);
         } else {
            this.drawInMap(var1, image, 0, 0, 96, 40, 0, 17);
         }

         this.drawCollisionRect(var1);
      }

   }

   public int getPaintLayer() {
      return 0;
   }

   public void init() {
      this.posX = this.posXorg;
      this.posY = this.posYorg;
      this.refreshCollisionRect(this.posX, this.posY);
   }

   public void platformLogic() {
      Coordinate var1 = this.system.getBarPosition();
      this.checkWithPlayer(this.posX, this.posY, var1.x, var1.y);
      this.posX = var1.x;
      this.posY = var1.y;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 3072, var2 + 768, 6144, 1792);
   }
}
