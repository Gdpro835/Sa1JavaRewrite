package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.MyRandom;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BackManagerStage3 extends BackGroundManager {
   private static int[] CLIP_SPEED_N;
   private static final int[] CLIP_SPEED_N1;
   private static final int[] CLIP_SPEED_N2;
   private static final String FILE_NAME = "/stage3_bg.png";
   private static int[][] IMAGE_CUT;
   private static final int[][] IMAGE_CUT_N;
   private static final int[][] IMAGE_CUT_W;
   private static int IMAGE_WIDTH;
   private AnimationDrawer firework;
   private int fireworkx;
   private int fireworky;
   private MFImage[] imageBG;
   private boolean isDrawFireWork;

   static {
      int[] var0 = new int[]{0, 0, 300, 128};
      int[] var3 = new int[]{0, 128, 256, 12};
      int[] var1 = new int[]{0, 140, 256, 12};
      int[] var4 = new int[]{0, 152, 256, 16};
      int[] var2 = new int[]{0, 168, 256, 24};
      int[] var5 = new int[]{0, 192, 256, 64};
      IMAGE_CUT_N = new int[][]{var0, var3, var1, var4, var2, var5};
      var2 = new int[]{0, 0, MyAPI.zoomIn(672, true), MyAPI.zoomIn(276, true)};
      var5 = new int[]{0, MyAPI.zoomIn(276, true), MyAPI.zoomIn(672, true), MyAPI.zoomIn(24, true)};
      var4 = new int[]{0, MyAPI.zoomIn(300, true), MyAPI.zoomIn(672, true), MyAPI.zoomIn(24, true)};
      var3 = new int[]{0, MyAPI.zoomIn(324, true), MyAPI.zoomIn(672, true), MyAPI.zoomIn(32, true)};
      var0 = new int[]{0, MyAPI.zoomIn(356, true), MyAPI.zoomIn(672, true), MyAPI.zoomIn(48, true)};
      var1 = new int[]{0, MyAPI.zoomIn(404, true), MyAPI.zoomIn(672, true), MyAPI.zoomIn(148, true)};
      IMAGE_CUT_W = new int[][]{var2, var5, var4, var3, var0, var1};
      IMAGE_CUT = IMAGE_CUT_N;
      CLIP_SPEED_N1 = new int[]{427, 32, 16, 8, 4, 2};
      CLIP_SPEED_N2 = new int[]{463, 32, 16, 8, 4, 2};
      IMAGE_WIDTH = 256;
   }

   public BackManagerStage3() {
      boolean var10001;
      try {
         this.imageBG = new MFImage[7];
      } catch (Exception var16) {
         var10001 = false;
         return;
      }

      try {
         this.imageBG[0] = MFImage.createImage("/map/stage3_bg/#1.png");
      } catch (Exception var15) {
         var10001 = false;
         return;
      }

      int var1 = 1;

      while(true) {
         try {
            if (var1 >= this.imageBG.length) {
               break;
            }
         } catch (Exception var14) {
            var10001 = false;
            return;
         }

         try {
            MFImage[] var3 = this.imageBG;
            StringBuilder var2 = new StringBuilder("/map/stage3_bg/#");
            var3[var1] = MFImage.createPaletteImage(var2.append(var1 + 1).append(".pal").toString());
         } catch (Exception var13) {
            var10001 = false;
            return;
         }

         ++var1;
      }

      label79: {
         label103: {
            try {
               if (stageId == 4) {
                  break label103;
               }
            } catch (Exception var12) {
               var10001 = false;
               return;
            }

            try {
               if (stageId != 5) {
                  break label79;
               }
            } catch (Exception var11) {
               var10001 = false;
               return;
            }

            try {
               CLIP_SPEED_N = CLIP_SPEED_N2;
               break label79;
            } catch (Exception var9) {
               var10001 = false;
               return;
            }
         }

         try {
            CLIP_SPEED_N = CLIP_SPEED_N1;
         } catch (Exception var10) {
            var10001 = false;
            return;
         }
      }

      label64: {
         try {
            if (this.firework != null) {
               break label64;
            }
         } catch (Exception var8) {
            var10001 = false;
            return;
         }

         try {
            Animation var17 = new Animation("/map/stage3_bg/st3_firework");
            this.firework = var17.getDrawer(0, false, 0);
         } catch (Exception var7) {
            var10001 = false;
            return;
         }
      }

      try {
         this.fireworkx = IMAGE_CUT[0][0] - MapManager.getCamera().x / CLIP_SPEED_N[0] % IMAGE_WIDTH + MyRandom.nextInt(0, 316);
      } catch (Exception var6) {
         var10001 = false;
         return;
      }

      try {
         this.fireworky = IMAGE_CUT[0][1] - MapManager.getCamera().y / 26 + MyRandom.nextInt(0, 112);
      } catch (Exception var5) {
         var10001 = false;
         return;
      }

      try {
         this.isDrawFireWork = true;
      } catch (Exception var4) {
         var10001 = false;
      }

   }

   public void close() {
      if (this.imageBG != null) {
         for(int var1 = 0; var1 < this.imageBG.length; ++var1) {
            this.imageBG[var1] = null;
         }
      }

      this.imageBG = null;
   }

   public void draw(MFGraphics var1) {
      int var5 = MapManager.getCamera().x;
      int var3 = MapManager.getCamera().y;
      int var2 = -(var3 / 29);
      if (stageId == 4) {
         var2 = -(var3 / 26);
      } else if (stageId == 5) {
         var2 = -(var3 / 29);
      }

      int var6 = frame % (this.imageBG.length * 2) / 2;

      for(var3 = 0; var3 < IMAGE_CUT.length; ++var3) {
         int var7 = -(var5 / CLIP_SPEED_N[var3] % IMAGE_WIDTH);

         for(int var4 = 0; var4 < MapManager.CAMERA_WIDTH - var7; var4 += IMAGE_WIDTH) {
            MFImage var14 = this.imageBG[var6];
            int var12 = IMAGE_CUT[var3][0];
            int var8 = IMAGE_CUT[var3][1];
            int var11 = IMAGE_CUT[var3][2];
            int var10 = IMAGE_CUT[var3][3];
            int var9 = IMAGE_CUT[var3][1];
            MyAPI.drawImage(var1, var14, var12, var8, var11, var10, 0, var7 + var4, var9 + var2 + 0, 20);
         }
      }

      boolean var13;
      if (this.isDrawFireWork && this.firework.checkEnd()) {
         if (MyRandom.nextInt(0, 10) > 5) {
            var13 = true;
         } else {
            var13 = false;
         }

         this.isDrawFireWork = var13;
         if (GameObject.IsGamePause) {
            this.isDrawFireWork = false;
         }

         if (this.isDrawFireWork) {
            this.fireworkx = IMAGE_CUT[0][0] - var5 / CLIP_SPEED_N[0] % IMAGE_WIDTH + MyRandom.nextInt(0, 316);
            this.fireworky = IMAGE_CUT[0][1] + var2 + MyRandom.nextInt(0, 112);
            AnimationDrawer var15 = this.firework;
            byte var16;
            if (MyRandom.nextInt(0, 10) > 5) {
               var16 = 0;
            } else {
               var16 = 1;
            }

            var15.setActionId(var16);
            this.firework.restart();
         }
      } else if (this.isDrawFireWork && !this.firework.checkEnd()) {
         this.firework.draw(var1, this.fireworkx, this.fireworky);
      } else if (!this.isDrawFireWork) {
         if (MyRandom.nextInt(0, 10) > 5) {
            var13 = true;
         } else {
            var13 = false;
         }

         this.isDrawFireWork = var13;
         if (GameObject.IsGamePause) {
            this.isDrawFireWork = false;
         }
      }

   }
}
