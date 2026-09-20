package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class FlipV extends GimmickObject {
   private static final int ACCELERATE_POWER = 3000;
   private static final int COLLISION_HEIGHT = 3072;
   private static final int COLLISION_HEIGHT_OFFSET = 512;
   private static final int COLLISION_WIDTH = 1088;
   private static Animation flipAnimation;
   private int count = 0;
   private AnimationDrawer drawer;

   protected FlipV(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (flipAnimation == null) {
         flipAnimation = new Animation("/animation/flip");
      }

      this.drawer = flipAnimation.getDrawer(0, true, 0);
      this.count = 0;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(flipAnimation);
      flipAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1 == player && this.drawer.getActionId() == 0) {
         switch(var2) {
         case 1:
            player.beStop(this.collisionRect.y0, var2, this);
            break;
         case 2:
            if (var1.collisionState == 1) {
               var1.beStop(this.collisionRect.y0, var2, this);
            } else if (this.count == 0) {
               if (player.beAccelerate(3000, true, this)) {
                  if (player instanceof PlayerAmy) {
                     ((PlayerAmy)player).resetAttackLevel();
                     ((PlayerAmy)player).setCannotAttack(true);
                  }

                  this.drawer.setActionId(1);
                  this.drawer.setLoop(false);
                  SoundSystem.getInstance().playSe(54);
               }

               ++this.count;
            }
            break;
         case 3:
            if (var1.collisionState == 1) {
               var1.beStop(this.collisionRect.y0, var2, this);
            } else if (this.count == 0) {
               if (player.beAccelerate(-3000, true, this)) {
                  if (player instanceof PlayerAmy) {
                     ((PlayerAmy)player).resetAttackLevel();
                     ((PlayerAmy)player).setCannotAttack(true);
                  }

                  this.drawer.setActionId(1);
                  this.drawer.setLoop(false);
                  this.drawer.setTrans(2);
                  SoundSystem.getInstance().playSe(54);
               }

               ++this.count;
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (this.count != 0) {
         ++this.count;
      }

      if (this.count == 5 && player instanceof PlayerAmy) {
         ((PlayerAmy)player).setCannotAttack(false);
      }

      if (this.count >= 10) {
         this.count = 0;
      }

      this.drawInMap(var1, this.drawer);
      if (this.drawer.checkEnd()) {
         this.drawer.setActionId(0);
         this.drawer.setLoop(true);
         this.drawer.setTrans(0);
      }

      this.drawCollisionRect(var1);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 544, var2 - 3072 + 512, 1088, 3072);
   }
}
