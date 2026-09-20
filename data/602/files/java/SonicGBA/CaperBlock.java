package SonicGBA;

import GameEngine.Key;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class CaperBlock extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 2048;
   private static final int COLLISION_Y_OFFSET = 192;
   private static MFImage blockImage;
   private HobinCal hobinCal;

   protected CaperBlock(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posY -= 512;
      if (blockImage == null) {
         StringBuilder var8;
         if (StageManager.getCurrentZoneId() != 6) {
            var8 = new StringBuilder("/gimmick/caper_block_");
            var8 = var8.append(StageManager.getCurrentZoneId()).append(".png");
            blockImage = MFImage.createImage(var8.toString());
         } else {
            var8 = new StringBuilder("/gimmick/caper_block_");
            var8 = var8.append(StageManager.getCurrentZoneId()).append(StageManager.getStageID() - 9).append(".png");
            blockImage = MFImage.createImage(var8.toString());
         }
      }

      this.hobinCal = new HobinCal();
   }

   public static void releaseAllResource() {
      blockImage = null;
   }

   public void close() {
      this.hobinCal = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.hobinCal.isStop()) {
         var1.beStop(this.collisionRect.x0, var2, this);
         if (Key.repeat(Key.gLeft)) {
            player.faceDirection = false;
         } else if (Key.repeat(Key.gRight)) {
            player.faceDirection = true;
         }

         switch(var2) {
         case 1:
            if (var1 == player) {
               player.beSpring(1350, 1);
               this.hobinCal.startHobin(400, 90, 10);
               if (player instanceof PlayerKnuckles && (player.getCharacterAnimationID() == 19 || player.getCharacterAnimationID() == 20 || player.getCharacterAnimationID() == 21 || player.getCharacterAnimationID() == 22)) {
                  player.setAnimationId(1);
               } else {
                  player.setAnimationId(1);
               }

               if (player.faceDirection) {
                  player.degreeForDraw = 1;
                  player.degreeRotateMode = 1;
               } else {
                  player.degreeForDraw = 359;
                  player.degreeRotateMode = 2;
               }

               soundInstance.playSe(37);
            }
         case 2:
         case 3:
         default:
            break;
         case 4:
            if (var1.getMoveDistance().y > 0 && var1.getCollisionRect().y1 < this.collisionRect.y1 && var1 == player) {
               player.beSpring(1350, 1);
               this.hobinCal.startHobin(400, 90, 10);
               player.setAnimationId(1);
               if (player.faceDirection) {
                  player.degreeForDraw = 1;
                  player.degreeRotateMode = 1;
               } else {
                  player.degreeForDraw = 359;
                  player.degreeRotateMode = 2;
               }

               soundInstance.playSe(37);
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      MFImage var6 = blockImage;
      int var5 = this.posX;
      int var4 = this.hobinCal.getPosOffsetX();
      int var2 = this.posY;
      int var3 = this.hobinCal.getPosOffsetY();
      this.drawInMap(var1, var6, var5 + var4, var2 + var3, 17);
      this.hobinCal.logic();
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1024, var2 + 192, 2048, 1024);
   }
}
