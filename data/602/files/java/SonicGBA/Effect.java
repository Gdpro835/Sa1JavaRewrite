package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

public class Effect {
   public static final int EFFECT_LAYER_NUM = 2;
   public static final int EFFECT_LAYER_PLAYER = 1;
   public static final int EFFECT_LAYER_TOP = 0;
   public static final int EFFECT_NUM = 10;
   public static Effect[][] effectArray = new Effect[2][10];
   private AnimationDrawer drawer = null;
   private int mPosX;
   private int mPosY;

   static {
      for(int var0 = 0; var0 < 2; ++var0) {
         for(int var1 = 0; var1 < 10; ++var1) {
            effectArray[var0][var1] = new Effect((Animation)null, 0, 0, 0, 0);
         }
      }

   }

   private Effect(Animation var1, int var2, int var3, int var4, int var5) {
      if (var1 != null) {
         this.drawer = var1.getDrawer(var2, false, var5);
         this.mPosX = var3;
         this.mPosY = var4;
      }

   }

   public static void draw(MFGraphics var0, int var1) {
      if (var1 >= 0 && var1 < 2) {
         for(int var2 = 0; var2 < 10; ++var2) {
            effectArray[var1][var2].mDraw(var0);
         }
      }

   }

   private boolean mDraw(MFGraphics var1) {
      boolean var2;
      if (this.drawer == null) {
         var2 = true;
      } else {
         this.drawer.draw(var1, this.mPosX - MapManager.getCamera().x, this.mPosY - MapManager.getCamera().y);
         if (this.drawer.checkEnd()) {
            this.drawer = null;
            var2 = true;
         } else {
            var2 = false;
         }
      }

      return var2;
   }

   private void setDrawer(Animation var1, int var2, int var3, int var4, int var5) {
      if (var1 != null) {
         this.drawer = var1.getDrawer(var2, false, var5);
         this.mPosX = var3;
         this.mPosY = var4;
      }

   }

   public static void showEffect(Animation var0, int var1, int var2, int var3, int var4) {
      showEffect(var0, var1, var2, var3, var4, 0);
   }

   public static void showEffect(Animation var0, int var1, int var2, int var3, int var4, int var5) {
      if (var5 >= 0 && var5 < 2) {
         for(int var6 = 0; var6 < 10; ++var6) {
            if (effectArray[var5][var6].drawer == null) {
               effectArray[var5][var6].setDrawer(var0, var1, var2, var3, var4);
               break;
            }
         }
      }

   }

   public static void showEffectPlayer(Animation var0, int var1, int var2, int var3, int var4) {
      showEffect(var0, var1, var2, var3, var4, 1);
   }
}
