package SonicGBA;

import Common.WaveInvertEffect;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BackManagerStage4 extends BackGroundManager {
   private static int IMAGE_HEIGHT = 512;
   private static int IMAGE_WIDTH = 392;
   private MFImage image;
   private int speedx = 0;
   private int speedy = 0;

   public BackManagerStage4(int var1) {
      try {
         this.image = MFImage.createImage("/map/stage4_bg.png");
      } catch (Exception var3) {
      }

      if (var1 == 0) {
         this.speedx = 637;
         this.speedy = 9;
      } else {
         this.speedx = 535;
         this.speedy = 7;
      }

   }

   public void close() {
      this.image = null;
   }

   public void draw(MFGraphics var1) {
      int var5 = MapManager.getCamera().x;
      int var4 = MapManager.getCamera().y;
      int var6 = StageManager.getWaterLevel();

      int var2;
      int var3;
      int var7;
      MFImage var9;
      for(var2 = 0; var2 < MapManager.CAMERA_HEIGHT; var2 += IMAGE_HEIGHT) {
         for(var3 = 0; var3 < MapManager.CAMERA_WIDTH; var3 += IMAGE_WIDTH) {
            var9 = this.image;
            var7 = var5 / this.speedx;
            int var8 = var4 / this.speedy;
            WaveInvertEffect.drawImage(var1, var9, 0 - var7 + var3, 0 - var8, 0, 0, 300, 40, 2);
         }
      }

      var1.setClip(0, 0, SCREEN_WIDTH, var6 - var4);

      for(var2 = 0; var2 < MapManager.CAMERA_HEIGHT; var2 += IMAGE_HEIGHT) {
         for(var3 = 0; var3 < MapManager.CAMERA_WIDTH; var3 += IMAGE_WIDTH) {
            var9 = this.image;
            var7 = var5 / this.speedx;
            var6 = var4 / this.speedy;
            MyAPI.drawImage(var1, var9, 0 - var7 + var3, 0 - var6, 0);
         }
      }

      var1.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
   }
}
