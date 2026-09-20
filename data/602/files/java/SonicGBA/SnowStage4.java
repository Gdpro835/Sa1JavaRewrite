package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class SnowStage4 extends BackGroundManager {
   private static int IMAGE_HEIGHT = 256;
   private static int IMAGE_WIDTH = 256;
   private static int SPEED_X = -1;
   private static int SPEED_Y = 4;
   private static int posX = 0;
   private static int posY = 0;
   private AnimationDrawer snowDrawer;
   private MFImage waterImage;
   private AnimationDrawer waterSurface;

   public SnowStage4() {
      try {
         Animation var1 = new Animation("/map/snow");
         this.snowDrawer = var1.getDrawer(0, true, 0);
         this.snowDrawer.setPause(true);
         this.waterImage = MFImage.createImage("/water/water_filter.png");
         var1 = new Animation("/water/stage6_water_surface");
         this.waterSurface = var1.getDrawer(0, true, 0);
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public void close() {
      this.snowDrawer = null;
      this.waterImage = null;
   }

   public void draw(MFGraphics var1) {
      if (!GameObject.IsGamePause) {
         posX += SPEED_X;
         posX %= IMAGE_WIDTH;
         posY += SPEED_Y;
         posY %= IMAGE_HEIGHT;
      }

      this.drawWater(var1);
   }

   public void drawWater(MFGraphics var1) {
      int var6 = MapManager.getCamera().x;
      int var4 = MapManager.getCamera().y;
      int var5 = StageManager.getWaterLevel();
      int var2 = var5;
      if (var5 > 0) {
         var2 = var5;
         if (var4 > var5) {
            var2 = var4;
         }
      }

      int var3;
      while(var2 < SCREEN_HEIGHT + var4) {
         for(var3 = 0; var3 < SCREEN_WIDTH; var3 += 96) {
            MyAPI.drawImage(var1, this.waterImage, var3, var2 - var4, 20);
         }

         var2 += 96;
      }

      if (var5 > var4 - 2 && var5 < SCREEN_HEIGHT + var4 + 2) {
         for(var2 = 0; var2 < SCREEN_WIDTH; ++var2) {
            this.waterSurface.draw(var1, var2, var5 - var4);
         }
      }

      if (var4 < var5) {
         var1.setClip(0, 0, SCREEN_WIDTH, var5 - var4);

         for(var2 = var4 / IMAGE_HEIGHT - 1; var2 < var4 / IMAGE_HEIGHT + 3; ++var2) {
            for(var3 = var6 / IMAGE_WIDTH; var3 < var6 / IMAGE_WIDTH + 4; ++var3) {
               AnimationDrawer var10 = this.snowDrawer;
               int var7 = posX;
               var5 = IMAGE_WIDTH;
               int var8 = posY;
               int var9 = IMAGE_HEIGHT;
               var10.draw(var1, var7 + var5 * var3 - var6, var8 + var9 * var2 - var4);
            }
         }

         if (!GameObject.IsGamePause) {
            this.snowDrawer.moveOn();
         }

         var1.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
      }

   }
}
