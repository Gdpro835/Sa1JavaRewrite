package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class Hari extends GimmickObject {
   public static final int IMAGE_COLLISION_OFFSET = 192;
   private static final int OFFFSET = 128;
   private static Animation hariAnimation;
   private AnimationDrawer drawer;
   private int firstCollisionDirection = 4;
   private byte hariId;

   protected Hari(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (hariAnimation == null) {
         hariAnimation = new Animation("/animation/se_hari");
      }

      this.hariId = (byte)(var1 - 1);
      this.drawer = hariAnimation.getDrawer(this.hariId, true, 0);
      this.mWidth = this.drawer.getCurrentFrameWidth() << 6;
      this.mHeight = this.drawer.getCurrentFrameHeight() << 6;
      this.drawer.setPause(true);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(hariAnimation);
      hariAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      int var3 = var2;
      if (this.firstCollisionDirection != 4) {
         var3 = var2;
         if (var2 == 4) {
            var3 = this.firstCollisionDirection;
         }
      }

      if (this.firstCollisionDirection == 4 && var3 != 4) {
         this.firstCollisionDirection = var3;
      }

      switch(this.objId) {
      case 5:
      case 6:
         if (this.drawer.getCurrentFrameHeight() != 0) {
            var1.beStop(this.collisionRect.x0, var3, this);
         }
         break;
      default:
         var1.beStop(this.collisionRect.x0, var3, this);
      }

      if (player instanceof PlayerKnuckles && var3 == 4 && player.canAttackByHari) {
         PlayerObject var4 = player;
         if (PlayerObject.getRingNum() > 0) {
            SoundSystem.getInstance().playSe(43);
         }

         player.beHurt();
         player.canAttackByHari = false;
         player.beAttackByHari = true;
      }

      if (var1 == player && player.canBeHurt()) {
         switch(this.objId) {
         case 1:
         case 5:
            if (var3 == 1 && this.drawer.getCurrentFrameHeight() != 0) {
               var1 = player;
               if (PlayerObject.getRingNum() > 0) {
                  SoundSystem.getInstance().playSe(43);
               }

               player.beHurt();
               player.beAttackByHari = true;
            }
            break;
         case 2:
         case 6:
            if (var3 == 0 && this.drawer.getCurrentFrameHeight() != 0) {
               var1 = player;
               if (PlayerObject.getRingNum() > 0) {
                  SoundSystem.getInstance().playSe(43);
               }

               player.beHurt();
               player.beAttackByHari = true;
            }
            break;
         case 3:
            if (var3 == 3 && player.velX > 0) {
               var1 = player;
               if (PlayerObject.getRingNum() > 0) {
                  SoundSystem.getInstance().playSe(43);
               }

               player.beHurt();
               player.beAttackByHari = true;
            } else if (var3 == 3 && player.getAnimationId() == 0) {
               var1 = player;
               if (PlayerObject.getRingNum() > 0) {
                  SoundSystem.getInstance().playSe(43);
               }

               player.beHurt();
               player.beAttackByHari = true;
            }
            break;
         case 4:
            if (var3 == 2 && player.velX < 0) {
               var1 = player;
               if (PlayerObject.getRingNum() > 0) {
                  SoundSystem.getInstance().playSe(43);
               }

               player.beHurt();
               player.beAttackByHari = true;
            } else if (var3 == 2 && player.getAnimationId() == 0) {
               var1 = player;
               if (PlayerObject.getRingNum() > 0) {
                  SoundSystem.getInstance().playSe(43);
               }

               player.beHurt();
               player.beAttackByHari = true;
            }
         }
      }

   }

   public void doWhileNoCollision() {
      this.firstCollisionDirection = 4;
   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer, this.posX, this.posY);
      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      this.drawer.moveOn();
      this.checkWithPlayer(this.posX, this.posY, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      int var3;
      int var4;
      int var5;
      int var6;
      CollisionRect var7;
      switch(this.objId) {
      case 1:
      case 5:
         var7 = this.collisionRect;
         var6 = this.mWidth;
         var3 = this.drawer.getCurrentFrameHeight();
         var4 = this.mWidth;
         var5 = this.drawer.getCurrentFrameHeight();
         var7.setRect(var1 - (var6 >> 1), var2 + 128 - (var3 << 6) + 192, var4, (var5 << 6) - 192);
         break;
      case 2:
      case 6:
         var7 = this.collisionRect;
         var5 = this.mWidth;
         var4 = this.mWidth;
         var3 = this.drawer.getCurrentFrameHeight();
         var7.setRect(var1 - (var5 >> 1), var2 - 128, var4, (var3 << 6) - 192);
         break;
      case 3:
         var7 = this.collisionRect;
         var5 = this.drawer.getCurrentFrameWidth();
         var6 = this.drawer.getCurrentFrameHeight();
         var3 = this.drawer.getCurrentFrameWidth();
         var4 = this.drawer.getCurrentFrameHeight();
         var7.setRect(var1 - (var5 << 6) + 128, var2 + 128 - (var6 << 6), var3 << 6, var4 << 6);
         break;
      case 4:
         var7 = this.collisionRect;
         var4 = this.drawer.getCurrentFrameHeight();
         var5 = this.drawer.getCurrentFrameWidth();
         var3 = this.drawer.getCurrentFrameHeight();
         var7.setRect(var1 - 128, var2 + 128 - (var4 << 6), var5 << 6, var3 << 6);
      }

   }
}
