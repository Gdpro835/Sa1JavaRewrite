package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class PipeOut extends GimmickObject {
   private static int COLLISION_HEIGHT;
   private static int COLLISION_WIDTH;
   private static final int OUT_SPEED_1 = 1440;
   private static final int OUT_SPEED_2 = 1824;
   private static final int PIPE_OUT_VELOCITY = 28;
   private static final int[] TRANS;
   private int actionID;
   private int dirRect;
   private int direction;
   private AnimationDrawer drawer;
   private int outVel;
   private boolean touching;
   private int transID;

   static {
      int[] var0 = new int[]{0, 3, 6, 5, 2, 1};
      TRANS = var0;
      COLLISION_WIDTH = 2304;
      COLLISION_HEIGHT = 2560;
   }

   protected PipeOut(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (PipeIn.pipeAnimation == null) {
         if (StageManager.getCurrentZoneId() != 6) {
            PipeIn.pipeAnimation = new Animation("/animation/pipe_in");
         } else {
            StringBuilder var8 = new StringBuilder("/animation/pipe_in");
            String var10 = var8.append(StageManager.getCurrentZoneId()).append(StageManager.getStageID() - 9).toString();
            PipeIn.pipeAnimation = new Animation(var10);
         }
      }

      this.direction = var6;
      switch(var6) {
      case 0:
         this.direction = 0;
         this.dirRect = 0;
         this.transID = 0;
         this.actionID = 1;
         break;
      case 1:
         this.direction = 1;
         this.dirRect = 1;
         this.transID = 5;
         this.actionID = 1;
         break;
      case 2:
         this.direction = 2;
         this.dirRect = 2;
         this.transID = 0;
         this.actionID = 3;
         break;
      case 3:
         this.direction = 3;
         this.dirRect = 3;
         this.transID = 4;
         this.actionID = 3;
      }

      short var9;
      if (var5 == 20) {
         if (this.direction != 1 && this.direction != 3) {
            var9 = 1440;
         } else {
            var9 = -1440;
         }

         this.outVel = var9;
      } else if (var5 == 30) {
         if (this.direction != 1 && this.direction != 3) {
            var9 = 1824;
         } else {
            var9 = -1824;
         }

         this.outVel = var9;
      }

      if (StageManager.getCurrentZoneId() != 2 && StageManager.getStageID() != 10) {
         if (StageManager.getStageID() == 11) {
            if (this.actionID == 1) {
               COLLISION_WIDTH = 3712;
               COLLISION_HEIGHT = 5504;
            } else if (this.actionID == 3) {
               COLLISION_WIDTH = 6912;
               COLLISION_HEIGHT = 3456;
            }
         }
      } else if (this.actionID == 1) {
         COLLISION_WIDTH = 2944;
         COLLISION_HEIGHT = 5632;
      } else if (this.actionID == 3) {
         COLLISION_WIDTH = 6144;
         COLLISION_HEIGHT = 2432;
      }

      this.drawer = PipeIn.pipeAnimation.getDrawer(this.actionID, true, TRANS[this.transID]);
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.firstTouch && player.piping) {
         this.touching = true;
         switch(this.direction) {
         case 0:
         case 1:
            if (player.velX != 0) {
               player.pipeSet(this.posX, this.posY, 0, this.outVel);
            } else {
               player.velY = this.outVel;
               player.velX = 0;
            }
            break;
         case 2:
         case 3:
            if (player.velY != 0) {
               player.pipeSet(this.posX, this.posY, this.outVel, 0);
            } else {
               player.velX = this.outVel;
               player.velY = 0;
            }

            if (this.direction == 2) {
               player.faceDirection = true;
            }

            if (this.direction == 3) {
               player.faceDirection = false;
            }
         }
      }

      if (player.piping && (this.direction == 2 || this.direction == 3)) {
         PlayerObject var4 = player;
         int var3 = this.posY;
         var1 = player;
         var4.footPointY = var3 + 1536 / 2;
         player.changeVisible(false);
      }

      if (this.touching && player.piping) {
         switch(var2) {
         case 0:
            if (this.direction == 1) {
            }
            break;
         case 1:
            if (this.direction == 0) {
            }
            break;
         case 2:
            if (this.direction == 3) {
            }
            break;
         case 3:
            if (this.direction == 2) {
            }
         }
      } else {
         player.beStop(0, var2, this);
      }

   }

   public void doWhileNoCollision() {
      if (this.touching && player.piping) {
         player.pipeOut();
         player.changeVisible(true);
         this.touching = false;
         if (this.direction != 1) {
            player.setVelY(0);
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
            if (this.actionID == 1) {
               COLLISION_WIDTH = 3712;
               COLLISION_HEIGHT = 5504;
            } else if (this.actionID == 3) {
               COLLISION_WIDTH = 6912;
               COLLISION_HEIGHT = 3456;
            }
         }
      } else if (this.actionID == 1) {
         COLLISION_WIDTH = 2944;
         COLLISION_HEIGHT = 5632;
      } else if (this.actionID == 3) {
         COLLISION_WIDTH = 6144;
         COLLISION_HEIGHT = 2432;
      }

      CollisionRect var7 = this.collisionRect;
      int var6 = COLLISION_WIDTH;
      int var5 = COLLISION_HEIGHT;
      int var4 = COLLISION_WIDTH;
      int var3 = COLLISION_HEIGHT;
      var7.setRect(var1 - (var6 >> 1), var2 - (var5 >> 1), var4, var3);
   }
}
