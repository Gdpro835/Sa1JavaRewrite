package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class ShipSystem extends GimmickObject {
   private static final int RADIUS = 5632;
   private static final int RING_NUM = 6;
   private int degree = 11520;
   private int lineVelocity = 0;
   private GimmickObject ship;

   protected ShipSystem(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.ship = new Ship(var1, var2, var3, this);
      GameObject.addGameObject(this.ship, this.posX, this.posY);
      GameObject.addGameObject(new ShipBase(var2, var3), this.posX, this.posY);
      if (shipRingImage == null) {
         try {
            shipRingImage = MFImage.createImage("/gimmick/ship_ring.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

   }

   public static void releaseAllResource() {
   }

   public void close() {
      this.ship = null;
   }

   public void draw(MFGraphics var1) {
      for(int var2 = 0; var2 < 6; ++var2) {
         MFImage var7 = shipRingImage;
         int var3 = this.posX;
         int var4 = var2 * 5632 / 5 * MyAPI.dCos(this.degree >> 6) / 100;
         int var5 = this.posY;
         int var6 = var2 * 5632 / 5 * MyAPI.dSin(this.degree >> 6) / 100;
         this.drawInMap(var1, var7, var3 + var4, var5 + var6, 3);
      }

   }

   public void getNewShipPosition(Ship var1) {
      this.lineVelocity += GRAVITY * 2 * MyAPI.dSin((this.degree >> 6) - 90) / 100 / 3;
      this.degree -= ((this.lineVelocity << 6) / 5632 << 6) * 180 / 201;
      int var2 = var1.posX;
      int var3 = var1.posY;
      var1.posX = this.posX + MyAPI.dCos(this.degree >> 6) * 5632 / 100;
      var1.posY = this.posY + MyAPI.dSin(this.degree >> 6) * 5632 / 100;
      var1.checkWithPlayer(var2, var3, var1.posX, var1.posY);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 5632, var2, 11264, 5632);
   }
}
