package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class LightFont extends GimmickObject {
   private static final int CLOSE_FRAME = 72;
   private static final int COLLISION_HEIGHT = 1792;
   private static final int COLLISION_WIDTH = 1408;
   private static final int FONT_A = 7;
   private static final int FONT_C = 4;
   private static final int FONT_E = 5;
   private static final int FONT_G = 6;
   private static final int FONT_I = 3;
   private static final int FONT_N = 2;
   private static final int FONT_NUM = 8;
   private static final int FONT_O = 1;
   private static final int FONT_S = 0;
   private static final int OPENING_FRAME = 50;
   private static Animation[] lightFontAnimation;
   public AnimationDrawer drawer;
   private int logicDelay;

   protected LightFont(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (lightFontAnimation == null) {
         lightFontAnimation = new Animation[8];
      }

      if (lightFontAnimation[var4] == null) {
         label28: {
            Animation[] var10;
            Animation var12;
            try {
               StringBuilder var8 = new StringBuilder("/animation/light_font_");
               var8 = var8.append(var4).append(".png");
               MFImage var9 = MFImage.createImage(var8.toString());
               var10 = lightFontAnimation;
               var12 = new Animation(var9, "/animation/light_font");
            } catch (Exception var11) {
               var11.printStackTrace();
               break label28;
            }

            var10[var4] = var12;
         }
      }

      this.drawer = lightFontAnimation[var4].getDrawer(1, false, 0);
      this.drawer.setPause(true);
      this.logicDelay = var6;
   }

   public static void releaseAllResource() {
      Animation.closeAnimationArray(lightFontAnimation);
      lightFontAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.drawer.getActionId() == 3) {
         var1.cancelFootObject(this);
      } else {
         var1.beStop(this.collisionRect.x0, var2, this);
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      this.drawer.moveOn();
      if (this.drawer.checkEnd()) {
         if (this.drawer.getActionId() == 0) {
            this.drawer.setActionId(1);
         } else if (this.drawer.getActionId() == 2) {
            this.drawer.setActionId(3);
         }
      }

      long var1 = systemClock;
      var1 = (long)this.logicDelay;
      long var3 = systemClock;
      var1 = (long)this.logicDelay;
      if ((var3 + 50L + 72L - var1) % 122L < 72L) {
         if (this.drawer.getActionId() == 1) {
            this.drawer.setActionId(2);
         }
      } else if (this.drawer.getActionId() == 3) {
         this.drawer.setActionId(0);
      }

      this.refreshCollisionRect(this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 704, var2 - 896, 1408, 1792);
   }
}
