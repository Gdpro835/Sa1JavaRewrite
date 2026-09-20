package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Terminal extends GimmickObject {
   private static final int COLLISION_HEIGHT = 9216;
   private static final int COLLISION_HEIGHT_61 = 1;
   private static final int COLLISION_WIDTH = 1024;
   private static final int COLLISION_WIDTH_61 = 1024;
   private static AnimationDrawer drawer;
   private boolean showDrawer;
   private int type;

   protected Terminal(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      boolean var8;
      if (this.iLeft == 1 && StageManager.getStageID() % 2 == 0 && StageManager.getStageID() != 10) {
         var8 = true;
      } else {
         var8 = false;
      }

      this.showDrawer = var8;
      if (this.showDrawer) {
         drawer = null;
         if (drawer == null) {
            drawer = (new Animation("/animation/terminal")).getDrawer(0, false, 0);
         }
      } else {
         this.posX += 512;
      }

      this.type = 0;
      if (StageManager.getStageID() == 10) {
         this.type = 1;
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimationDrawer(drawer);
      drawer = null;
   }

   public void close() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!PlayerObject.isTerminal) {
         switch(this.type) {
         case 0:
            if (drawer != null) {
               if (this.iLeft != 1) {
                  player.setTerminalSingle(this.type);
               } else if ((PlayerObject.getCharacterID() != 0 || player.getCharacterAnimationID() != 18 && player.getCharacterAnimationID() != 17) && player.getAnimationId() != 4) {
                  var1.setTerminal(this.type);
               } else {
                  player.setTerminalSingle(this.type);
               }

               drawer.setActionId(1);
               soundInstance.playSe(26);
            }
            break;
         case 1:
            if (this.iLeft == 0) {
               var1.setTerminal(this.type);
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (this.showDrawer) {
         this.drawInMap(var1, drawer);
      }

   }

   public int getPaintLayer() {
      return 0;
   }

   public void refreshCollisionRect(int var1, int var2) {
      if (this.showDrawer) {
         this.collisionRect.setRect(var1, var2 - this.mHeight, this.mWidth, this.mHeight);
      } else {
         this.collisionRect.setRect(var1, var2, 1024, 9216);
      }

      if (StageManager.getStageID() == 10) {
         CollisionRect var3 = this.collisionRect;
         var3.setRect(var1 - 512, var2, 1024, 1);
      }

   }
}
