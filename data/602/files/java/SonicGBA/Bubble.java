package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Bubble extends GimmickObject {
   private static final int BREATHE_BUBBLE_TIME = 2500;
   private static final int UP_BUBBLE_TIME = 1000;
   private static Animation baseAnimation;
   private static final int[] createPuyo = new int[]{70, 80, 32, 54, 32, 80, 64, 32};
   private AnimationDrawer baseDrawer;
   private boolean breatheBubbleFlag = false;
   private int createPuyoCount = 0;
   private int createPuyoType = 0;
   private boolean upBubbleFlag = false;

   protected Bubble(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (baseAnimation == null) {
         baseAnimation = new Animation("/animation/bubble_base");
      }

      if (baseAnimation != null) {
         this.baseDrawer = baseAnimation.getDrawer(0, true, 0);
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(baseAnimation);
      baseAnimation = null;
   }

   public void close() {
      this.baseDrawer = null;
   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.baseDrawer);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (GameTime.milliseconds() / 1000L % 2L == 0L && !this.upBubbleFlag) {
         addGameObject(new UpBubble(this.posX, this.posY), this.posX, this.posY);
         this.upBubbleFlag = true;
      } else if (GameTime.milliseconds() / 1000L % 2L != 0L) {
         this.upBubbleFlag = false;
      }

      this.createPuyoCount = GameTime.advance(this, "createPuyoCount", this.createPuyoCount, 1);
      if (this.createPuyoType >= createPuyo.length) {
         this.createPuyoType = 0;
      }

      if (this.createPuyoCount >= createPuyo[this.createPuyoType]) {
         this.createPuyoCount = GameTime.set(this, "createPuyoCount", 0);
         ++this.createPuyoType;
         addGameObject(new BreatheBubble(this.posX, this.posY), this.posX, this.posY);
         this.breatheBubbleFlag = true;
      } else if (GameTime.milliseconds() / 2500L % 2L != 0L) {
         this.breatheBubbleFlag = false;
      }

   }
}
