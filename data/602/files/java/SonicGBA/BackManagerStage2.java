package SonicGBA;

import GameEngine.Def;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BackManagerStage2 extends BackGroundManager {
   private static final String FILE_NAME_1 = "/stage2_bg_0.png";
   private static final String FILE_NAME_2 = "/stage2_bg_1.png";
   private static final int[][] IMAGE_2_CUT_N;
   private static int IMAGE_WIDTH = 256;
   private static final int[] LINE_2_OFFSET_N;
   private static int STAGE_BG_WIDTH = 256;
   private static final int X_DRAW_NUM;
   private MFImage image1;
   private MFImage[] imageBG2;

   static {
      int[] var1 = new int[]{0, 0, 256, 64};
      int[] var2 = new int[]{0, 64, 256, 16};
      int[] var0 = new int[]{0, 80, 256, 24};
      IMAGE_2_CUT_N = new int[][]{var1, var2, var0};
      LINE_2_OFFSET_N = new int[]{64, 64, 128};
      X_DRAW_NUM = (Def.SCREEN_WIDTH + (STAGE_BG_WIDTH - 1) * 2) / STAGE_BG_WIDTH;
   }

   public BackManagerStage2() {
      boolean var10001;
      label67: {
         try {
            if (stageId != 2) {
               break label67;
            }
         } catch (Exception var11) {
            var10001 = false;
            return;
         }

         try {
            this.image1 = MFImage.createImage("/map/stage2_bg_0.png");
         } catch (Exception var10) {
            var10001 = false;
            return;
         }

         try {
            IMAGE_WIDTH = MyAPI.zoomIn(this.image1.getWidth(), true);
         } catch (Exception var9) {
            var10001 = false;
            return;
         }
      }

      try {
         this.imageBG2 = new MFImage[8];
      } catch (Exception var8) {
         var10001 = false;
         return;
      }

      try {
         this.imageBG2[0] = MFImage.createImage("/map/stage2_bg_1/#1.png");
      } catch (Exception var7) {
         var10001 = false;
         return;
      }

      int var1 = 1;

      while(true) {
         try {
            if (var1 >= this.imageBG2.length) {
               break;
            }
         } catch (Exception var6) {
            var10001 = false;
            return;
         }

         try {
            MFImage[] var2 = this.imageBG2;
            StringBuilder var3 = new StringBuilder("/map/stage2_bg_1/#");
            var2[var1] = MFImage.createPaletteImage(var3.append(var1 + 1).append(".pal").toString());
         } catch (Exception var5) {
            var10001 = false;
            return;
         }

         ++var1;
      }

      try {
         STAGE_BG_WIDTH = MyAPI.zoomIn(this.imageBG2[0].getWidth(), true);
      } catch (Exception var4) {
         var10001 = false;
      }

   }

   public void close() {
      this.image1 = null;
      if (this.imageBG2 != null) {
         for(int var1 = 0; var1 < this.imageBG2.length; ++var1) {
            this.imageBG2[var1] = null;
         }
      }

      this.imageBG2 = null;
   }

   public void draw(MFGraphics var1) {
      if (stageId == 2) {
         this.drawStage1(var1);
      } else {
         this.drawStage2(var1);
      }

   }

   public void drawStage1(MFGraphics var1) {
      int var3 = MapManager.getCamera().x;
      if (var3 <= 1456) {
         var1.setColor(1054752);
         MyAPI.fillRect(var1, DRAW_X, DRAW_Y, DRAW_WIDTH, DRAW_HEIGHT);

         for(int var2 = 0; var2 < MapManager.CAMERA_WIDTH; var2 += IMAGE_WIDTH) {
            MFImage var6 = this.image1;
            int var5 = var3 / 74;
            int var4 = SCREEN_HEIGHT;
            MyAPI.drawImage(var1, var6, 0 - var5 + var2, var4 >> 1, 6);
         }
      } else {
         this.drawType2(var1);
      }

   }

   public void drawStage2(MFGraphics var1) {
      this.drawType2(var1);
   }

   public void drawType2(MFGraphics var1) {
      int var5 = frame % (this.imageBG2.length * 3) / 3;
      int var7 = MapManager.getCamera().x;
      int var6 = MapManager.getCamera().y;
      int var8 = -(var7 / 4 % STAGE_BG_WIDTH);
      int var4 = -(var6 / 4 % 64);

      int var2;
      int var3;
      int var9;
      int var10;
      int var11;
      int var12;
      int var13;
      MFImage var15;
      for(var2 = 0; var2 < X_DRAW_NUM; ++var2) {
         for(var3 = 0; var3 < 6; ++var3) {
            var15 = this.imageBG2[var5];
            var12 = IMAGE_2_CUT_N[0][0];
            var10 = IMAGE_2_CUT_N[0][1];
            var9 = IMAGE_2_CUT_N[0][2];
            var13 = IMAGE_2_CUT_N[0][3];
            var11 = STAGE_BG_WIDTH;
            MyAPI.drawImage(var1, var15, var12, var10, var9, var13, 0, var11 * var2 + var8, var3 * 64 + var4, 20);
         }
      }

      var8 = -(var7 / 2 % STAGE_BG_WIDTH);
      var9 = -(var6 / 2 % 256);

      for(var2 = 0; var2 < X_DRAW_NUM; ++var2) {
         var3 = 0;

         for(var4 = var9 + 112; var4 < DRAW_HEIGHT + 16; var3 = (var3 + 1) % LINE_2_OFFSET_N.length) {
            if (var4 > -16) {
               var15 = this.imageBG2[var5];
               var13 = IMAGE_2_CUT_N[1][0];
               var10 = IMAGE_2_CUT_N[1][1];
               var11 = IMAGE_2_CUT_N[1][2];
               int var14 = IMAGE_2_CUT_N[1][3];
               var12 = STAGE_BG_WIDTH;
               MyAPI.drawImage(var1, var15, var13, var10, var11, var14, 0, var12 * var2 + var8, var4, 20);
            }

            var4 += LINE_2_OFFSET_N[var3];
         }
      }

      var4 = -(var7 * 2 / 3 % STAGE_BG_WIDTH);
      var6 = -(var6 * 2 / 3 % 256);

      for(var2 = 0; var2 < X_DRAW_NUM; ++var2) {
         for(var3 = var6 + 200; var3 < DRAW_HEIGHT + 24; var3 += 256) {
            if (var3 > -24) {
               var15 = this.imageBG2[var5];
               var8 = IMAGE_2_CUT_N[2][0];
               var10 = IMAGE_2_CUT_N[2][1];
               var7 = IMAGE_2_CUT_N[2][2];
               var11 = IMAGE_2_CUT_N[2][3];
               var9 = STAGE_BG_WIDTH;
               MyAPI.drawImage(var1, var15, var8, var10, var7, var11, 0, var9 * var2 + var4, var3, 20);
            }
         }
      }

   }
}
