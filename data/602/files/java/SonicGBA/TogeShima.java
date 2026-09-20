package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class TogeShima extends GimmickObject {
   private static final int COLLISION_HEIGHT = 2048;
   private static final int COLLISION_WIDTH = 3072;
   private static Animation animation;
   private boolean dir = false;
   private AnimationDrawer drawer;
   private int frame;
   public MoveCalculator moveCal;
   private int posEndY;
   private int posOriginalY;
   private int range;
   private ShimaSting ss;
   private int velY = 192;

   protected TogeShima(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (animation == null) {
         animation = new Animation("/animation/togeshima");
      }

      if (animation != null) {
         this.drawer = animation.getDrawer(0, true, 0);
      }

      this.range = var7 * 8 << 6;
      this.posOriginalY = this.posY;
      this.posEndY = this.posY + this.range;
      this.ss = new ShimaSting(var2, var3);
      GameObject.addGameObject(this.ss);
      this.dir = false;
      boolean var8;
      if (this.iTop == 0) {
         var8 = false;
      } else {
         var8 = true;
      }

      this.moveCal = new MoveCalculator(this.posY, this.mHeight, var8);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(animation);
      animation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var2 == 1 || var2 == 0) {
         var1.beStop(0, var2, this);
      }

      if (var2 == 2 || var2 == 3) {
         if (var1.getFootPositionY() < this.collisionRect.y0 + this.mHeight * 3 / 4) {
            if (var1.getVelX() > 0 && var2 == 3) {
               var1.setFootPositionY(this.collisionRect.y0 - 64);
            } else if (var1.getVelX() < 0 && var2 == 2) {
               var1.setFootPositionY(this.collisionRect.y0 - 64);
            }
         } else {
            var1.beStop(0, var2, this);
         }
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer, this.posX, this.posY);
      this.drawCollisionRect(var1);
   }

   public void logic() {
      this.frame = this.drawer.getCurrentFrame();
      this.moveCal.logic();
      int var3 = this.posX;
      int var4 = this.posY;
      if (this.iTop < 0) {
         this.posY = this.moveCal.getPosition();
      } else {
         int var2 = this.posOriginalY;
         int var1 = this.moveCal.getPosition();
         this.posY = this.posOriginalY + (var2 - var1);
      }

      this.refreshCollisionRect(this.posX, this.posY);
      this.ss.logic(this.posX, this.posY, this.frame);
      this.checkWithPlayer(var3, var4, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1536, var2 - 1024, 3072, 2048);
   }
}
