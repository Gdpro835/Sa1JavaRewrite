package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class AirRoot extends GimmickObject {
   private static final int COLLISION_HEIGHT = 320;
   private static final int COLLISION_WIDTH = 1792;
   private static final int CORNER_LEFT_BOTTOM = 2;
   private static final int CORNER_LEFT_TOP = 0;
   private static final int CORNER_RIGHT_BOTTOM = 3;
   private static final int CORNER_RIGHT_TOP = 1;
   private static final int IMAGE_HEIGHT = 1792;
   private static final int IMAGE_WIDTH = 1792;
   private static MFImage image;
   private int imageHeight = 0;
   private int imageWidth;
   private int type;

   protected AirRoot(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (image == null) {
         image = MFImage.createImage("/gimmick/airroot.png");
      }

      if (image != null) {
         this.imageWidth = MyAPI.zoomIn(image.getWidth());
         this.imageHeight = MyAPI.zoomIn(image.getHeight());
      }

      this.type = var4;
   }

   public static void releaseAllResource() {
      image = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!player.piping) {
         if (var1.getBodyPositionY() < this.collisionRect.y0) {
            var1.setFootPositionY(this.collisionRect.y0 - 64);
            var2 = 1;
         }

         if (var2 == 1) {
            var1.beStop(this.collisionRect.x0, var2, this);
         }
      }

   }

   public void draw(MFGraphics var1) {
      byte var2 = 0;
      switch(this.type) {
      case 0:
         var2 = 0;
         break;
      case 1:
         var2 = 5;
         break;
      case 2:
         var2 = 6;
         break;
      case 3:
         var2 = 3;
      }

      MFImage var7 = image;
      int var3 = this.imageWidth;
      int var5 = this.imageHeight;
      int var4 = this.posX;
      int var6 = this.posY;
      this.drawInMap(var1, var7, 0, 0, var3, var5, var2, var4 - 896, var6 - 896, 0);
      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3;
      if (this.type != 0 && this.type != 1) {
         if (this.type == 2 || this.type == 3) {
            var3 = this.collisionRect;
            var3.setRect(var1 - 896, var2 - 896 + 1472, 1792, 320);
         }
      } else {
         var3 = this.collisionRect;
         var3.setRect(var1 - 896, var2 - 896 + 192, 1792, 320);
      }

   }
}
