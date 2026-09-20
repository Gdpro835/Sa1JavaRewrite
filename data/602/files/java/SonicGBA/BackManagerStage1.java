package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BackManagerStage1 extends BackGroundManager {
   private static int[] BG_CUT_HEIGHT;
   private static final int[] BG_CUT_HEIGHT_N = new int[]{8, 16, 16, 56, 2, 4, 5, 6, 7, 8, 9, 11, 12};
   private static final int[] BG_CUT_HEIGHT_W;
   private static int[] CAMERA_POS_X_SPEED;
   private static final int[] CAMERA_POS_X_SPEED_N;
   private static final int[] CAMERA_POS_X_SPEED_W;
   private static final String FILE_NAME = "/stage1_bg.png";
   private static int IMAGE_WIDTH;
   private static int[] POS_X_SPEED;
   private static final int POS_X_SPEED_DIVIDE = 64;
   private static final int[] POS_X_SPEED_N;
   private static final int[] POS_X_SPEED_W;
   private MFImage image;
   private MFImage[] imageBG;
   private int[] posX;

   static {
      int var8 = MyAPI.zoomIn(36, true);
      int var1 = MyAPI.zoomIn(32, true);
      int var3 = MyAPI.zoomIn(32, true);
      int var5 = MyAPI.zoomIn(112, true);
      int var7 = MyAPI.zoomIn(32, true);
      int var6 = MyAPI.zoomIn(32, true);
      int var0 = MyAPI.zoomIn(32, true);
      int var2 = MyAPI.zoomIn(32, true);
      int var4 = MyAPI.zoomIn(20, true);
      BG_CUT_HEIGHT_W = new int[]{var8, var1, var3, var5, var7, var6, var0, var2, var4};
      int[] var9 = new int[13];
      var9[0] = 32;
      var9[1] = 16;
      var9[2] = 8;
      POS_X_SPEED_N = var9;
      var9 = new int[9];
      var9[0] = MyAPI.zoomIn(32, true);
      var9[1] = MyAPI.zoomIn(16, true);
      var9[2] = MyAPI.zoomIn(8, true);
      POS_X_SPEED_W = var9;
      var9 = new int[13];
      var9[4] = 4;
      var9[5] = 5;
      var9[6] = 6;
      var9[7] = 7;
      var9[8] = 8;
      var9[9] = 10;
      var9[10] = 12;
      var9[11] = 16;
      var9[12] = 22;
      CAMERA_POS_X_SPEED_N = var9;
      var9 = new int[9];
      var9[4] = MyAPI.zoomIn(4, true);
      var9[5] = MyAPI.zoomIn(8, true);
      var9[6] = MyAPI.zoomIn(12, true);
      var9[7] = MyAPI.zoomIn(16, true);
      var9[8] = MyAPI.zoomIn(20, true);
      CAMERA_POS_X_SPEED_W = var9;
      BG_CUT_HEIGHT = BG_CUT_HEIGHT_N;
      POS_X_SPEED = POS_X_SPEED_N;
      CAMERA_POS_X_SPEED = CAMERA_POS_X_SPEED_N;
      IMAGE_WIDTH = 256;
   }

   public BackManagerStage1() {
      this.posX = new int[BG_CUT_HEIGHT.length];

      Exception var10000;
      label45: {
         boolean var10001;
         try {
            this.imageBG = new MFImage[4];
         } catch (Exception var7) {
            var10000 = var7;
            var10001 = false;
            break label45;
         }

         try {
            this.imageBG[0] = MFImage.createImage("/map/stage1_bg/#1.png");
         } catch (Exception var6) {
            var10000 = var6;
            var10001 = false;
            break label45;
         }

         for(int var1 = 1; var1 < 4; ++var1) {
            try {
               MFImage[] var2 = this.imageBG;
               StringBuilder var3 = new StringBuilder("/map/stage1_bg/#");
               var2[var1] = MFImage.createImage(var3.append(var1 + 1).append(".png").toString());
            } catch (Exception var5) {
               var10000 = var5;
               var10001 = false;
               break label45;
            }
         }

         try {
            IMAGE_WIDTH = MyAPI.zoomIn(this.imageBG[0].getWidth(), true);
            return;
         } catch (Exception var4) {
            var10000 = var4;
            var10001 = false;
         }
      }

      Exception var8 = var10000;
      var8.printStackTrace();
   }

   public void close() {
      this.posX = null;
      if (this.imageBG != null) {
         for(int var1 = 0; var1 < this.imageBG.length; ++var1) {
            this.imageBG[var1] = null;
         }
      }

      this.imageBG = null;
   }

   public void draw(MFGraphics var1) {
      int var6 = MapManager.getCamera().x;
      int var3 = 0;
      int var5 = frame % 16 / 4;

      for(int var2 = 0; var2 < BG_CUT_HEIGHT.length; ++var2) {
         if (!GameObject.IsGamePause) {
            int[] var10 = this.posX;
            var10[var2] += POS_X_SPEED[var2];
         }

         int var4 = CAMERA_POS_X_SPEED[var2] * var6 / 64;
         int var7 = this.posX[var2];
         int var8 = IMAGE_WIDTH;
         var7 = -((var4 + (var7 >> 6)) % var8);

         for(var4 = 0; var4 < MapManager.CAMERA_WIDTH - var7; var4 += IMAGE_WIDTH) {
            MFImage var11 = this.imageBG[var5];
            int var9 = IMAGE_WIDTH;
            var8 = BG_CUT_HEIGHT[var2];
            MyAPI.drawRegion(var1, var11, 0, var3, var9, var8, 0, var7 + var4, var3, 20);
         }

         var3 += BG_CUT_HEIGHT[var2];
      }

   }
}
