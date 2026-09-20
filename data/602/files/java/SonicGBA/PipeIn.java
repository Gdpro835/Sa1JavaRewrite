package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.engine.action.ACWorldCollisionCalculator;
import com.sega.mobile.framework.device.MFGraphics;

class PipeIn extends GimmickObject {
   private static int COLLISION_HEIGHT;
   private static int COLLISION_WIDTH;
   private static final int[] TRANS;
   public static Animation pipeAnimation;
   private int actionID;
   private int dirRect;
   private int direction;
   private AnimationDrawer drawer;
   private int transID;
   private int velx;
   private int vely;

   static {
      int[] var0 = new int[]{0, 3, 6, 5, 2, 1};
      TRANS = var0;
      COLLISION_WIDTH = 2304;
      COLLISION_HEIGHT = 2560;
   }

   protected PipeIn(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (pipeAnimation == null) {
         if (StageManager.getCurrentZoneId() != 6) {
            pipeAnimation = new Animation("/animation/pipe_in");
         } else {
            StringBuilder var8 = new StringBuilder("/animation/pipe_in");
            String var9 = var8.append(StageManager.getCurrentZoneId()).append(StageManager.getStageID() - 9).toString();
            pipeAnimation = new Animation(var9);
         }
      }

      this.direction = var6;
      switch(var6) {
      case 0:
         this.direction = 0;
         this.dirRect = 0;
         this.transID = 0;
         this.actionID = 0;
         break;
      case 1:
         this.direction = 1;
         this.dirRect = 1;
         this.transID = 5;
         this.actionID = 0;
         break;
      case 2:
         this.direction = 2;
         this.dirRect = 2;
         this.transID = 0;
         this.actionID = 2;
         break;
      case 3:
         this.direction = 3;
         this.dirRect = 3;
         this.transID = 4;
         this.actionID = 2;
      }

      this.velx = var4 * 96 >> 6;
      this.vely = var5 * 96 >> 6;
      if (StageManager.getCurrentZoneId() != 2 && StageManager.getStageID() != 10) {
         if (StageManager.getStageID() == 11) {
            if (this.actionID == 0) {
               COLLISION_WIDTH = 3712;
               COLLISION_HEIGHT = 5504;
            } else if (this.actionID == 2) {
               COLLISION_WIDTH = 6912;
               COLLISION_HEIGHT = 3456;
            }
         }
      } else if (this.actionID == 0) {
         COLLISION_WIDTH = 2944;
         COLLISION_HEIGHT = 5632;
      } else if (this.actionID == 2) {
         COLLISION_WIDTH = 6144;
         COLLISION_HEIGHT = 2432;
      }

      this.drawer = pipeAnimation.getDrawer(this.actionID, true, TRANS[this.transID]);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(pipeAnimation);
      pipeAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!player.piping) {
         if (this.direction == var2) {
            switch(var2) {
            case 0:
               player.collisionState = 1;
               ACWorldCollisionCalculator var4 = player.worldCal;
               ACWorldCollisionCalculator var3 = player.worldCal;
               var4.actionState = 1;
               player.posX = this.posX;
               break;
            case 1:
               player.posX = this.posX;
               break;
            case 2:
               player.posY = this.posY + 768;
               break;
            case 3:
               player.posY = this.posY + 768;
            }

            player.pipeIn(this.posX, this.posY, this.velx, this.vely);
            var1 = player;
            if (PlayerObject.getCharacterID() == 3) {
               SoundSystem.getInstance().playSe(25);
            } else {
               SoundSystem.getInstance().playSe(4);
            }

            player.setAnimationId(4);
            player.stopMove();
         } else {
            var1.beStop(0, var2, this);
         }
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 2;
   }

   public void refreshCollisionRect(int var1, int var2) {
      if (StageManager.getCurrentZoneId() != 2 && StageManager.getStageID() != 10) {
         if (StageManager.getStageID() == 11) {
            if (this.actionID == 0) {
               COLLISION_WIDTH = 3712;
               COLLISION_HEIGHT = 5504;
            } else if (this.actionID == 2) {
               COLLISION_WIDTH = 6912;
               COLLISION_HEIGHT = 3456;
            }
         }
      } else if (this.actionID == 0) {
         COLLISION_WIDTH = 2944;
         COLLISION_HEIGHT = 5632;
      } else if (this.actionID == 2) {
         COLLISION_WIDTH = 6144;
         COLLISION_HEIGHT = 2432;
      }

      CollisionRect var7 = this.collisionRect;
      int var6 = COLLISION_WIDTH;
      int var5 = COLLISION_HEIGHT;
      int var3 = COLLISION_WIDTH;
      int var4 = COLLISION_HEIGHT;
      var7.setRect(var1 - (var6 >> 1), var2 - (var5 >> 1), var3, var4);
   }
}
