package SonicGBA;

import Lib.Coordinate;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class FreeFallBar extends GimmickObject {
   private static final int COLLISION_HEIGHT = 2560;
   private static final int COLLISION_WIDTH = 1024;
   private static final int PLAYER_OFFSET = -640;
   private static MFImage barImage;
   private static int frameCnt = 0;
   private boolean isLeftEnter;
   private int posXorg;
   private int posYorg;
   private FreeFallSystem system;

   protected FreeFallBar(FreeFallSystem var1, int var2, int var3) {
      super(0, var2, var3, 0, 0, 0, 0);
      this.system = var1;
      if (barImage == null) {
         try {
            barImage = MFImage.createImage("/gimmick/freefall_bar.png");
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

      frameCnt = 1;
      this.isLeftEnter = false;
      Coordinate var6 = var1.getBarPosition();
      this.posXorg = var6.x;
      this.posYorg = var6.y;
   }

   public static void releaseAllResource() {
      barImage = null;
   }

   public void barLogic() {
      Coordinate var1 = this.system.getBarPosition();
      this.posX = var1.x;
      this.posY = var1.y;
      this.refreshCollisionRect(this.posX, this.posY);
      if (this.system.moving) {
         player.setFootPositionY(this.posY + 640);
         player.setFootPositionX(this.posX);
         player.faceDirection = true;
         System.out.println("bar posX:" + (this.posX >> 6));
      }

   }

   public void close() {
      this.system = null;
   }

   public void doInitInCamera() {
      this.used = false;
      frameCnt = 0;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.system.isSystemReady() && (!(var1 instanceof PlayerTails) || var1.getCharacterAnimationID() < 12 || var1.getCharacterAnimationID() > 14) && !this.used && !this.system.moving) {
         this.used = true;
         this.system.moving = true;
         FreeFallSystem var4 = this.system;
         boolean var3;
         if (player.getVelX() > 0) {
            var3 = true;
         } else {
            var3 = false;
         }

         var4.shootDirection = var3;
         SoundSystem.getInstance().playSe(73);
         player.changeVisible(false);
         player.setOutOfControl(this);
         switch(var2) {
         case 2:
            player.setAnimationId(22);
            this.isLeftEnter = true;
            break;
         case 3:
            player.setAnimationId(23);
            this.isLeftEnter = false;
         }

         player.cancelFootObject();
         player.collisionChkBreak = true;
         player.faceDirection = true;
         player.collisionState = 1;
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.system.initFlag) {
         if (this.system.moving) {
            ++frameCnt;
            if (player.getAnimationId() == 22) {
               this.drawInMap(var1, barImage, 33);
               player.draw(var1, true);
            } else if (player.getAnimationId() == 23) {
               player.draw(var1, true);
               this.drawInMap(var1, barImage, 33);
            } else {
               player.setAnimationId(23);
            }

            System.out.println("player posX:" + (player.footPointX >> 6));
         } else {
            this.drawInMap(var1, barImage, 33);
         }
      }

   }

   public int getPaintLayer() {
      return 0;
   }

   public void init() {
      this.used = false;
      frameCnt = 0;
      this.posX = this.posXorg;
      this.posY = this.posYorg;
      this.refreshCollisionRect(this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 512, var2 - 2560, 1024, 2560);
   }
}
