package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BackManagerStage62 extends BackGroundManager {
   private static int IMAGE_HEIGHT = 232;
   private static int IMAGE_WIDTH = 256;
   private static final int[][] LIGHT_PARAM;
   private static MFImage[] bgImage;
   private int[] lightX;

   static {
      int[] var1 = new int[]{0, 120, 256, 16};
      int[] var0 = new int[]{0, 176, 256, 16};
      LIGHT_PARAM = new int[][]{var1, var0};
   }

   public BackManagerStage62() {
      this.lightX = new int[LIGHT_PARAM.length];
      if (bgImage == null) {
         bgImage = new MFImage[4];

         for(int var1 = 0; var1 < 4; ++var1) {
            bgImage[var1] = MFImage.createImage("/map/stage6_bg_3/#" + (var1 + 1) + ".png");
         }

         IMAGE_WIDTH = bgImage[0].getWidth();
         IMAGE_HEIGHT = bgImage[0].getHeight();
      }

   }

   public void close() {
      if (bgImage != null) {
         for(int var1 = 0; var1 < 4; ++var1) {
            bgImage[var1] = null;
         }
      }

      bgImage = null;
   }

   public void draw(MFGraphics var1) {
      int var4 = MapManager.getCamera().x;
      int var5 = MapManager.getCamera().y;
      int var6 = frame % (bgImage.length * 2) / 2;
      MFImage var15 = bgImage[var6];
      int var3 = IMAGE_WIDTH;
      int var2 = IMAGE_HEIGHT;
      int var7 = -var4 / 46;
      int var8 = -var5 / 33;
      MyAPI.drawImage(var1, var15, 0, 0, var3, var2, 0, var7, var8, 0);
      var15 = bgImage[var6];
      var2 = IMAGE_WIDTH;
      var7 = IMAGE_HEIGHT;
      var3 = -var4 / 46;
      var8 = IMAGE_WIDTH;
      int var9 = -var5 / 33;
      MyAPI.drawImage(var1, var15, 0, 0, var2, var7, 2, var8 + var3, var9, 0);

      for(var2 = 0; var2 < LIGHT_PARAM.length; ++var2) {
         for(var3 = 0; var3 < (SCREEN_WIDTH + 255) / 256 + 2; ++var3) {
            var15 = bgImage[var6];
            int var14 = LIGHT_PARAM[var2][0];
            var8 = LIGHT_PARAM[var2][1];
            var7 = LIGHT_PARAM[var2][2];
            int var10 = LIGHT_PARAM[var2][3];
            int var11 = -(var4 / 38);
            var9 = this.lightX[var2];
            int var13 = -var5 / 33;
            int var12 = LIGHT_PARAM[var2][1];
            MyAPI.drawImage(var1, var15, var14, var8, var7, var10, 0, (var3 - 1) * 256 + var11 % 256 + (var9 >> 6), var12 + var13, 0);
         }

         if (!GameObject.IsGamePause) {
            int[] var16;
            if (var2 == 0) {
               var16 = this.lightX;
               var16[var2] += 120;
            } else {
               var16 = this.lightX;
               var16[var2] -= 120;
            }

            var16 = this.lightX;
            var16[var2] += 16384;
            var16 = this.lightX;
            var16[var2] %= 16384;
         }
      }

   }
}
