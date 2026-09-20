package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class BreatheBubble extends UpBubble {
   private static final int BUBBLE_LIFE = 8;
   private static final int BUBBLE_RANGE = 1280;
   private static Animation bubbleAnimation;
   private boolean CanBreathe = false;
   private final int LIFE_RANGE = 6400;
   private int breathCnt = 0;
   private int direct = 0;
   private AnimationDrawer drawer;
   private boolean initFlag;
   private boolean isBeginPlayStageBGM = false;
   private boolean isFirstUp = true;
   private int posOriginalY;
   private int velx = 0;
   private int vely = -120;

   public BreatheBubble(int var1, int var2) {
      super(0, var1, var2, 0, 0, 0, 0);
      if (bubbleAnimation == null) {
         bubbleAnimation = new Animation("/animation/bubble_up");
      }

      if (bubbleAnimation != null) {
         this.drawer = bubbleAnimation.getDrawer(3, false, 0);
         this.direct = MyRandom.nextInt(-1, 1);
      }

      this.posOriginalY = this.posY;
      this.initFlag = false;
      this.CanBreathe = false;
      this.isFirstUp = true;
      this.breathCnt = 0;
   }

   protected BreatheBubble(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(bubbleAnimation);
      bubbleAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.CanBreathe) {
         if (var1.doBreatheBubble()) {
            if (this.isFirstUp) {
               SoundSystem.getInstance().playSe(59);
               this.isFirstUp = false;
            }

            this.CanBreathe = false;
            ++this.breathCnt;
         }

         if (this.isBeginPlayStageBGM) {
            var2 = SoundSystem.getInstance().getPlayingBGMIndex();
            if (var2 != StageManager.getBgmId()) {
               if (PlayerObject.IsInvincibility()) {
                  SoundSystem.getInstance().playBgm(44);
               } else {
                  SoundSystem.getInstance().playBgm(StageManager.getBgmId());
                  this.isBeginPlayStageBGM = false;
               }
            }
         }
      }

   }

   public void doWhileNoCollision() {
      this.isBeginPlayStageBGM = true;
   }

   public void draw(MFGraphics var1) {
      if (!this.used && !this.initFlag) {
         if (this.drawer.checkEnd()) {
            this.drawer = bubbleAnimation.getDrawer(4, true, 0);
         }

         this.drawInMap(var1, this.drawer, this.posX, this.posY);
         this.drawCollisionRect(var1);
      }

   }

   public int getPaintLayer() {
      return 1;
   }

   public void logic() {
      if (!this.used && !this.initFlag) {
         if (this.direct >= 0) {
            this.velx = MyRandom.nextInt(0, 20);
         } else {
            this.velx = MyRandom.nextInt(-20, 0);
         }

         if (this.breathCnt >= 1) {
            ++this.breathCnt;
         }

         if (this.breathCnt < 8) {
            this.posX += this.velx;
            this.posY += this.vely;
         } else {
            this.used = true;
         }

         this.refreshCollisionRect(this.posX, this.posY);
         if (this.drawer != null && this.drawer.getCurrentFrame() == 5) {
            this.CanBreathe = true;
         }

         if (!this.isInCamera()) {
            this.used = true;
            this.CanBreathe = false;
         }

         if (this.posY <= (StageManager.getWaterLevel() << 6) + 640) {
            this.initFlag = true;
            this.CanBreathe = false;
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      if (this.CanBreathe) {
         this.collisionRect.setRect(var1 - 640, var2 - 640, 1280, 1280);
      }

   }
}
