package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import com.sega.mobile.framework.device.MFGraphics;

class StarEffect implements SonicDef {
   private static final int MAX_VELOCITY = -720;
   private static final int MOVE_POWER = -360;
   private AnimationDrawer drawer;
   private int velX;
   private int x;
   private int y;

   public StarEffect(Animation var1, int var2, int var3, int var4) {
      this.drawer = var1.getDrawer(var2, false, 0);
      this.x = var3;
      this.y = var4;
      this.velX = 0;
   }

   public void close() {
      this.drawer = null;
   }

   public boolean draw(MFGraphics var1) {
      if (!GameObject.IsGamePause) {
         this.velX -= 360;
         if (this.velX < -720) {
            this.velX = -720;
         }

         this.x += this.velX;
      }

      Coordinate var2 = MapManager.getCamera();
      this.drawer.draw(var1, (this.x >> 6) - var2.x, (this.y >> 6) - var2.y);
      return this.drawer.checkEnd();
   }
}
