package SonicGBA;

import GameEngine.time.GameTime;

import GameEngine.Def;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BackManagerStage5 extends BackGroundManager {
   private static int BG_WIDTH = 256;
   private static final int CLOUD_Y = 0;
   private static int IMAGE_WIDTH = 256;
   private MFImage backImage;
   private MFImage cloudImage;
   private int cloudPosX;

   public BackManagerStage5() {
      try {
         this.backImage = MFImage.createImage("/map/stage5_bg_0.png");
         this.cloudImage = MFImage.createImage("/map/stage5_bg_1.png");
         IMAGE_WIDTH = MyAPI.zoomIn(this.cloudImage.getWidth(), true);
         BG_WIDTH = MyAPI.zoomIn(this.backImage.getWidth(), true);
      } catch (Exception var2) {
      }

   }

   public void close() {
      this.backImage = null;
      this.cloudImage = null;
   }

   public void draw(MFGraphics var1) {
      MyAPI.drawImage(var1, this.backImage, 0, 0, 20);
      if (BG_WIDTH < Def.SCREEN_WIDTH) {
         MyAPI.drawImage(var1, this.backImage, BG_WIDTH, 0, 20);
      }

      if (!GameObject.IsGamePause) {
         this.cloudPosX = GameTime.advance(this, "cloudPosX", this.cloudPosX, 1);
         this.cloudPosX = GameTime.wrap(this, "cloudPosX", this.cloudPosX, IMAGE_WIDTH);
      }

      for(int var2 = -IMAGE_WIDTH; var2 < MapManager.CAMERA_WIDTH + this.cloudPosX; var2 += IMAGE_WIDTH) {
         MyAPI.drawImage(var1, this.cloudImage, this.cloudPosX + var2, 0, 20);
      }

   }
}
