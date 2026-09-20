package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BackManagerStage61 extends BackGroundManager {
   private static final int[][] IMAGE_DIVIDE_PARAM;
   private static final int STATE_EVE = 0;
   private static final int STATE_PASSING = 1;
   private static final int STATE_STAR = 2;
   private int bgY;
   private int cloudY;
   private MFImage image0;
   private MFImage image1;
   private MFImage image2;
   private int passingY;
   private int starY1;
   private int starY2;
   public int state;

   static {
      int[] var4 = new int[]{0, 120, 373, 373};
      int[] var1 = new int[]{0, 0, 373, 120};
      int[] var3 = new int[]{0, 256, 373, 124};
      int[] var2 = new int[]{0, 380, 373, 128};
      int[] var0 = new int[]{0, 0, 373, 256};
      IMAGE_DIVIDE_PARAM = new int[][]{var4, var1, var3, var2, var0};
   }

   public BackManagerStage61() {
      try {
         this.image0 = MFImage.createImage("/map/stage6_bg_0.png");
         this.image1 = MFImage.createImage("/map/stage6_bg_1.png");
         this.image2 = MFImage.createImage("/map/stage6_bg_2.png");
      } catch (Exception var2) {
      }

      this.state = 0;
      this.starY1 = 0;
      this.starY2 = 32768;
   }

   private void drawStarWorld(MFGraphics var1, int var2, boolean var3) {
      var1.setColor(528448);
      MyAPI.fillRect(var1, 0, var2 + 0, SCREEN_WIDTH, 100);

      int var4;
      int var5;
      int var6;
      int var7;
      int var8;
      int var9;
      MFImage var10;
      for(var4 = 0; var4 < 2; ++var4) {
         var10 = this.image2;
         var6 = IMAGE_DIVIDE_PARAM[4][0];
         var5 = IMAGE_DIVIDE_PARAM[4][1];
         var8 = IMAGE_DIVIDE_PARAM[4][2];
         var9 = IMAGE_DIVIDE_PARAM[4][3];
         var7 = (this.starY2 + 65536 * (var4 - 1)) / 256;
         MyAPI.drawImage(var1, var10, var6, var5, var8, var9, 0, -128, var7 + var2, 0);
         var10 = this.image2;
         var6 = IMAGE_DIVIDE_PARAM[4][0];
         var7 = IMAGE_DIVIDE_PARAM[4][1];
         var8 = IMAGE_DIVIDE_PARAM[4][2];
         var5 = IMAGE_DIVIDE_PARAM[4][3];
         var9 = (this.starY2 + 65536 * (var4 - 1)) / 256;
         MyAPI.drawImage(var1, var10, var6, var7, var8, var5, 0, 128, var9 + var2, 0);
      }

      for(var4 = 0; var4 < 2; ++var4) {
         var10 = this.image2;
         var9 = IMAGE_DIVIDE_PARAM[4][0];
         var6 = IMAGE_DIVIDE_PARAM[4][1];
         var5 = IMAGE_DIVIDE_PARAM[4][2];
         var8 = IMAGE_DIVIDE_PARAM[4][3];
         var7 = (this.starY1 + 65536 * (var4 - 1)) / 256;
         MyAPI.drawImage(var1, var10, var9, var6, var5, var8, 0, 0, var7 + var2, 0);
      }

      var10 = this.image2;
      var5 = IMAGE_DIVIDE_PARAM[2][0];
      var4 = IMAGE_DIVIDE_PARAM[2][1];
      var6 = IMAGE_DIVIDE_PARAM[2][2];
      var7 = IMAGE_DIVIDE_PARAM[2][3];
      MyAPI.drawImage(var1, var10, var5, var4, var6, var7, 0, 0, var2 + 100, 0);
      if (!var3) {
         MyAPI.setClip(var1, 0, var2 + 104, SCREEN_WIDTH, SCREEN_HEIGHT - 104);

         for(var4 = 0; var4 < (SCREEN_HEIGHT - 104 + 127) / 128 + 1; ++var4) {
            var10 = this.image2;
            var6 = IMAGE_DIVIDE_PARAM[3][0];
            var8 = IMAGE_DIVIDE_PARAM[3][1];
            var9 = IMAGE_DIVIDE_PARAM[3][2];
            var5 = IMAGE_DIVIDE_PARAM[3][3];
            var7 = (this.cloudY + '耀' * (var4 - 1)) / 256;
            MyAPI.drawImage(var1, var10, var6, var8, var9, var5, 0, 0, var7 + 104 + var2, 0);
         }

         MyAPI.setClip(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
      }

      if (!GameObject.IsGamePause) {
         this.cloudY -= 30;
         this.cloudY += 32768;
         this.cloudY %= 32768;
         this.starY1 -= 120;
         this.starY1 += 65536;
         this.starY1 %= 65536;
         this.starY2 -= 15;
         this.starY2 += 65536;
         this.starY2 %= 65536;
      }

   }

   public void close() {
      this.image0 = null;
      this.image1 = null;
      this.image2 = null;
   }

   public void draw(MFGraphics var1) {
      int var2 = MapManager.getCamera().x;
      int var3;
      int var4;
      int var5;
      int var6;
      int var7;
      MFImage var9;
      switch(this.state) {
      case 0:
         if (var2 <= 1058) {
            MyAPI.drawImage(var1, this.image0, -var2 / 51, 0, 0);
         } else {
            for(var2 = 0; var2 < (SCREEN_HEIGHT + 255) / 256 + 1; ++var2) {
               var9 = this.image1;
               var7 = IMAGE_DIVIDE_PARAM[0][0];
               var4 = IMAGE_DIVIDE_PARAM[0][1];
               var3 = IMAGE_DIVIDE_PARAM[0][2];
               var6 = IMAGE_DIVIDE_PARAM[0][3];
               var5 = this.bgY;
               MyAPI.drawImage(var1, var9, var7, var4, var3, var6, 0, 0, var5 - 16384 + var2 * 16384 >> 6, 0);
            }

            if (!GameObject.IsGamePause) {
               this.bgY += 480;
               this.bgY %= 16384;
            }
         }
         break;
      case 1:
         var1.setColor(16777215);
         MyAPI.fillRect(var1, 0, (this.passingY >> 6) + 224, SCREEN_WIDTH, 8);
         this.drawStarWorld(var1, this.passingY >> 6, true);
         MyAPI.setClip(var1, 0, (this.passingY >> 6) + 104, SCREEN_WIDTH, 128);

         for(var2 = 0; var2 < 2; ++var2) {
            var9 = this.image2;
            int var8 = IMAGE_DIVIDE_PARAM[3][0];
            var7 = IMAGE_DIVIDE_PARAM[3][1];
            var5 = IMAGE_DIVIDE_PARAM[3][2];
            var6 = IMAGE_DIVIDE_PARAM[3][3];
            var3 = (this.cloudY + '耀' * (var2 - 1)) / 256;
            var4 = this.passingY;
            MyAPI.drawImage(var1, var9, var8, var7, var5, var6, 0, 0, (var4 >> 6) + var3 + 104, 0);
         }

         MyAPI.setClip(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
         var9 = this.image1;
         var6 = IMAGE_DIVIDE_PARAM[1][0];
         var5 = IMAGE_DIVIDE_PARAM[1][1];
         var4 = IMAGE_DIVIDE_PARAM[1][2];
         var3 = IMAGE_DIVIDE_PARAM[1][3];
         var2 = this.passingY;
         MyAPI.drawImage(var1, var9, var6, var5, var4, var3, 0, 0, (var2 >> 6) + 224 + 8, 0);

         for(var2 = 0; var2 < (SCREEN_HEIGHT + 255) / 256 + 1; ++var2) {
            var9 = this.image1;
            var7 = IMAGE_DIVIDE_PARAM[0][0];
            var6 = IMAGE_DIVIDE_PARAM[0][1];
            var3 = IMAGE_DIVIDE_PARAM[0][2];
            var4 = IMAGE_DIVIDE_PARAM[0][3];
            var5 = this.passingY;
            MyAPI.drawImage(var1, var9, var7, var6, var3, var4, 0, 0, var2 * 256 + (var5 >> 6) + 224 + 8 + 120, 0);
         }

         if (!GameObject.IsGamePause) {
            if (this.passingY < -960) {
               this.passingY += 480;
            } else {
               this.passingY = MyAPI.calNextPosition((double)this.passingY, 0.0D, 1, 2);
            }
         }

         if (this.passingY == 0) {
            this.state = 2;
         }
         break;
      case 2:
         this.drawStarWorld(var1, 0, false);
      }

   }

   public void nextState() {
      this.state = 1;
      this.passingY = -('頀' - this.bgY);
   }
}
