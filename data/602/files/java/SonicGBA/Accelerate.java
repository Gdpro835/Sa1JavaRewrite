package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Accelerate extends GimmickObject {
   private static final int ACCELERATE_POWER = 3072;
   private static final int COLLISION_HEIGHT = 640;
   private static final int COLLISION_WIDTH = 1536;
   private static Animation accelerate2Animation;
   private static Animation accelerateAnimation;
   private AnimationDrawer drawer;
   private boolean touching = false;
   private boolean transMirror = false;

   protected Accelerate(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      boolean var8;
      if (this.iTop != 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      this.transMirror = var8;
      Animation var9;
      byte var10;
      if (this.objId == 100) {
         if (accelerate2Animation == null) {
            accelerate2Animation = new Animation("/animation/accelerate2");
         }

         var9 = accelerate2Animation;
         if (this.transMirror) {
            var10 = 2;
         } else {
            var10 = 0;
         }

         this.drawer = var9.getDrawer(0, true, var10);
         this.posY += 512;
      } else if (this.objId == 47) {
         if (accelerateAnimation == null) {
            accelerateAnimation = new Animation("/animation/accelerate");
         }

         var9 = accelerateAnimation;
         if (this.transMirror) {
            var10 = 7;
         } else {
            var10 = 6;
         }

         this.drawer = var9.getDrawer(0, true, var10);
      } else {
         if (accelerateAnimation == null) {
            accelerateAnimation = new Animation("/animation/accelerate");
         }

         var9 = accelerateAnimation;
         var2 = this.objId;
         if (this.transMirror) {
            var10 = 2;
         } else {
            var10 = 0;
         }

         this.drawer = var9.getDrawer(var2 - 31, true, var10);
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(accelerateAnimation);
      Animation.closeAnimation(accelerate2Animation);
      accelerateAnimation = null;
      accelerate2Animation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.touching && var1 == player) {
         byte var3;
         if (this.objId != 47) {
            var1 = player;
            if (this.transMirror) {
               var3 = -1;
            } else {
               var3 = 1;
            }

            if (var1.beAccelerate(var3 * 3072, true, this)) {
               this.touching = true;
               soundInstance.playSe(44);
            }
         } else {
            var1 = player;
            if (this.transMirror) {
               var3 = -1;
            } else {
               var3 = 1;
            }

            if (var1.beAccelerate(var3 * 3072, false, this)) {
               this.touching = true;
               soundInstance.playSe(44);
            }
         }
      }

   }

   public void doWhileNoCollision() {
      this.touching = false;
   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 768, var2 - 640, 1536, 640);
   }
}
