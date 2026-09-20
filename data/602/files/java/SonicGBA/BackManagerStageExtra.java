package SonicGBA;

import GameEngine.time.GameTime;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BackManagerStageExtra extends BackGroundManager {
   private static final int[][] DIVIDE_PARAM;
   private static final int[] DIVIDE_VELOCITY;
   private static MFImage bgImage;
   private int[] xOffset;

   static {
      int[] var10 = new int[]{0, 0, 284, 91};
      int[] var1 = new int[]{0, 91, 256, 10};
      int[] var0 = new int[]{0, 101, 256, 5};
      int[] var7 = new int[]{0, 106, 256, 7};
      int[] var6 = new int[]{0, 113, 256, 15};
      int[] var4 = new int[]{0, 128, 256, 2};
      int[] var5 = new int[]{0, 130, 256, 3};
      int[] var11 = new int[]{0, 133, 256, 4};
      int[] var8 = new int[]{0, 137, 256, 5};
      int[] var9 = new int[]{0, 142, 256, 5};
      int[] var2 = new int[]{0, 147, 256, 6};
      int[] var3 = new int[]{0, 153, 256, 7};
      DIVIDE_PARAM = new int[][]{var10, var1, var0, var7, var6, var4, var5, var11, var8, var9, var2, var3};
      var0 = new int[]{0, 15, 30, 60, 120, 240, 360, 480, 600, 720, 840, 960};
      DIVIDE_VELOCITY = var0;
   }

   public BackManagerStageExtra() {
      bgImage = MFImage.createImage("/map/ex_bg_1.png");
      this.xOffset = new int[DIVIDE_VELOCITY.length];
   }

   public void close() {
      bgImage = null;
      this.xOffset = null;
   }

   public void draw(MFGraphics var1) {
      var1.setColor(0);
      MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

      for(int var2 = 0; var2 < DIVIDE_VELOCITY.length; ++var2) {
         for(int var3 = 0; var3 < 4; ++var3) {
            MFImage var11 = bgImage;
            int var4 = DIVIDE_PARAM[var2][0];
            int var6 = DIVIDE_PARAM[var2][1];
            int var10 = DIVIDE_PARAM[var2][2];
            int var8 = DIVIDE_PARAM[var2][3];
            int var9 = -this.xOffset[var2] / 256;
            int var5 = SCREEN_WIDTH / 2;
            int var7 = DIVIDE_PARAM[var2][1];
            MyAPI.drawImage(var1, var11, var4, var6, var10, var8, 0, (var3 - 1) * 256 + var9 + var5, var7, 17);
         }

         if (!GameObject.IsGamePause) {
            int[] var12 = this.xOffset;
            var12[var2] = GameTime.advance(var12, String.valueOf(var2), var12[var2], DIVIDE_VELOCITY[var2]);
            var12 = this.xOffset;
            var12[var2] %= 65536;
         }
      }

   }
}
