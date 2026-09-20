package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class Door extends GimmickObject {
   private static final int COLLISION_HEIGHT = 2048;
   private static final int COLLISION_H_OFFSET = 512;
   private static final int COLLISION_V_OFFSET = 512;
   private static final int COLLISION_WIDTH = 1024;
   private int actionId;
   private AnimationDrawer drawer;
   private boolean isActived;

   protected Door(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (doorAnimation == null) {
         doorAnimation = new Animation("/animation/door");
      }

      switch(this.objId) {
      case 63:
         this.actionId = 0;
         break;
      case 64:
         this.actionId = 2;
      }

      this.drawer = doorAnimation.getDrawer(this.actionId, false, 0);
      this.isActived = false;
   }

   public static void releaseAllResource() {
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      label44:
      switch(this.objId) {
      case 63:
         switch(var2) {
         case 2:
            if (this.iLeft == 0) {
               var1.beStop(this.collisionRect.y0, var2, this);
            } else {
               this.drawer.setActionId(this.actionId + 1);
               this.isActived = true;
               SoundSystem.getInstance().playSe(56);
               if (var1.getVelX() <= 0 && var1.getVelX() >= -PlayerObject.MOVE_POWER) {
                  var1.outOfControl = true;
                  var1.setFootPositionX(var1.posX + 1024);
                  var1.outOfControl = false;
               }
            }
            break label44;
         case 3:
            if (this.iLeft == 0) {
               this.drawer.setActionId(this.actionId + 1);
               this.isActived = true;
               SoundSystem.getInstance().playSe(56);
               if (var1.getVelX() >= 0 && var1.getVelX() <= PlayerObject.MOVE_POWER) {
                  var1.outOfControl = true;
                  var1.setFootPositionX(var1.posX - 1024);
                  var1.outOfControl = false;
               }
            } else {
               var1.beStop(this.collisionRect.y0, var2, this);
            }
         default:
            break label44;
         }
      case 64:
         switch(var2) {
         case 0:
            if (this.iLeft == 0) {
               var1.beStop(this.collisionRect.y0, var2, this);
            } else {
               this.drawer.setActionId(this.actionId + 1);
               this.isActived = true;
               SoundSystem.getInstance().playSe(56);
            }
            break;
         case 1:
            if (this.iLeft == 0) {
               this.drawer.setActionId(this.actionId + 1);
               this.isActived = true;
               SoundSystem.getInstance().playSe(56);
            } else {
               var1.beStopbyDoor(this.collisionRect.y0, var2, this);
            }
         }
      }

      if (this.firstTouch) {
         player.pipeOut();
      }

   }

   public void doWhileNoCollision() {
      if (!this.isInCameraSmaller() && this.isActived) {
         this.isActived = false;
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
      if (this.drawer.checkEnd() && this.isActived) {
         this.drawer.setActionId(this.actionId);
         this.isActived = false;
      }

      this.drawCollisionRect(var1);
   }

   public void refreshCollisionRect(int var1, int var2) {
      switch(this.objId) {
      case 63:
         this.collisionRect.setRect(var1 - 512, var2, 1024, 2048);
         break;
      case 64:
         this.collisionRect.setRect(var1, var2 - 512, 2048, 1024);
      }

   }
}
