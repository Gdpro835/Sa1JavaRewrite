package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BackManagerStageFinal extends BackGroundManager {
   private static MFImage bgImage;
   private int height;

   public BackManagerStageFinal() {
      if (bgImage == null) {
         bgImage = MFImage.createImage("/map/final_bg.png");
      }

      this.height = 20;
   }

   public void close() {
      bgImage = null;
   }

   public void draw(MFGraphics var1) {
      int var2 = -12;
      var1.setColor(0);
      MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
      int var4 = MapManager.getCamera().x;
      int var3 = MapManager.getCamera().y;
      if (-var4 / 43 >= -12) {
         var2 = -var4 / 43;
      }

      if (-var3 / 18 + this.height > 0) {
         var3 = 0;
      } else {
         var3 = -var3 / 18 + this.height;
      }

      MyAPI.drawImage(var1, bgImage, var2 - 373 + SCREEN_WIDTH, var3, 0);
   }
}
