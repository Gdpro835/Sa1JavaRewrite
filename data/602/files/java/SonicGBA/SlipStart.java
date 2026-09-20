package SonicGBA;

import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;

class SlipStart extends GimmickObject {
   private static final int ACTIVE_SPEED_X = 128;
   private static final int ACTIVE_SPEED_Y = 128;
   private static final int COLLISION_HEIGHT = 1792;
   private static final int COLLISION_OFFSET_Y = -128;
   private boolean isActived;

   protected SlipStart(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (player.slipping && (player instanceof PlayerSonic || player instanceof PlayerAmy)) {
         player.setSlip0();
      }

      if (!this.isActived) {
         if (player instanceof PlayerSonic) {
            PlayerSonic var3 = (PlayerSonic)player;
            if (!this.isActived) {
               if (player.getVelX() > 128 && player.getVelY() > 128 && this.collisionRect.collisionChk(player.getFootPositionX(), player.getFootPositionY()) && player.currentLayer == 1) {
                  var1 = player;
                  PlayerObject.slidingFrame = 1;
                  var3.slipStart();
               }

               this.isActived = true;
            }
         } else if (player instanceof PlayerAmy) {
            PlayerAmy var4 = (PlayerAmy)player;
            if ((player.getCharacterAnimationID() < 4 || player.getCharacterAnimationID() > 8) && (player.getCharacterAnimationID() < 11 || player.getCharacterAnimationID() > 12) && !this.isActived) {
               if (player.getVelX() > 128 && player.getVelY() > 128 && this.collisionRect.collisionChk(player.getFootPositionX(), player.getFootPositionY()) && player.currentLayer == 1) {
                  PlayerObject var5 = player;
                  PlayerObject.slidingFrame = 1;
                  var4.slipStart();
               }

               this.isActived = true;
            }
         }
      }

   }

   public void doWhileCollision(ACObject var1, ACCollision var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void doWhileNoCollision() {
      if (this.isActived) {
         if (player.slipping) {
            player.faceDegree = 45;
            player.calDivideVelocity();
            player.calTotalVelocity();
         }

         player.worldCal.setMovedState(false);
         this.isActived = false;
      }

   }

   public void logic() {
   }
}
