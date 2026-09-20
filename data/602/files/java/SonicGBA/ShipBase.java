package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class ShipBase extends PlatformObject {
   private static final int COLLISION_HEIGHT = 1280;
   private static final int COLLISION_WIDTH = 1280;
   private static MFImage shipBaseImage;

   protected ShipBase(int var1, int var2) {
      super(var1, var2);
      if (shipBaseImage == null) {
         try {
            shipBaseImage = MFImage.createImage("/gimmick/ship_base.png");
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      this.refreshCollisionRect(this.posX, this.posY);
   }

   public static void releaseAllResource() {
      shipBaseImage = null;
   }

   public void close() {
   }

   public void draw(MFGraphics var1) {
      if (shipBaseImage != null) {
         this.drawInMap(var1, shipBaseImage, 3);
         this.drawCollisionRect(var1);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 640, var2 - 640, 1280, 1280);
   }
}
