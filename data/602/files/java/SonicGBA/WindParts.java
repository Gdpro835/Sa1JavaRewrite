package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class WindParts extends GimmickObject {
   private static final int MOVE_FRAME = 8;
   private static final int VELOCITY = 500;
   private static Animation animation;
   private AnimationDrawer drawer;
   private int moveCount;
   private int posOriginalY;

   protected WindParts(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (animation == null) {
         animation = new Animation("/animation/wind_parts");
      }

      this.drawer = animation.getDrawer(2 - this.iLeft, true, 0);
      this.posOriginalY = this.posY;
      this.moveCount = GameTime.set(this, "moveCount", 0);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(animation);
      animation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
   }

   public void logic() {
      this.moveCount = GameTime.advance(this, "moveCount", this.moveCount, 1);
      if (this.moveCount >= 8) {
         this.moveCount = GameTime.set(this, "moveCount", 0);
         this.posY = this.posOriginalY;
      } else {
         this.posY = GameTime.advance(this, "posY", this.posY, -(500));
      }

      this.refreshCollisionRect(this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1, var2, 1, 1);
   }
}
