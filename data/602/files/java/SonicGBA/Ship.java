package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Ship extends GimmickObject {
   private static final int COLLISION_HEIGHT = 192;
   private static final int COLLISION_OFFSET_Y = 768;
   private static final int COLLISION_WIDTH = 4224;
   private static MFImage shipImage;
   private ShipSystem system;

   protected Ship(int var1, int var2, int var3, ShipSystem var4) {
      super(var1, var2, var3, 0, 0, 0, 0);
      this.system = var4;
      if (shipImage == null) {
         try {
            shipImage = MFImage.createImage("/gimmick/ship.png");
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

   }

   public static void releaseAllResource() {
      shipImage = null;
   }

   public void close() {
      this.system = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!player.isFootOnObject(this)) {
         switch(var2) {
         case 1:
            var1.beStop(this.collisionRect.y0, var2, this);
         }
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, shipImage, this.posX, this.posY + 768 + 192, 40);
      MFImage var6 = shipImage;
      int var4 = MyAPI.zoomIn(shipImage.getWidth());
      int var3 = MyAPI.zoomIn(shipImage.getHeight());
      int var5 = this.posX;
      int var2 = this.posY;
      this.drawInMap(var1, var6, 0, 0, var4, var3, 2, var5, var2 + 768 + 192, 36);
      this.drawCollisionRect(var1);
   }

   public void logic() {
      this.system.getNewShipPosition(this);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 2112, var2 + 768, 4224, 192);
   }
}
