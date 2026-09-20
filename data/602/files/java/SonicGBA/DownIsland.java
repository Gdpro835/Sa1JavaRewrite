package SonicGBA;

import GameEngine.time.GameTime;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class DownIsland extends GimmickObject {
   private static int COLLISION_HEIGHT = 2560;
   private static int COLLISION_WIDTH = 2560;
   private static final int VELOCITY = 400;
   private static MFImage image;
   private int posYOriginal;

   protected DownIsland(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posYOriginal = this.posY;
      if (image == null) {
         if (StageManager.getCurrentZoneId() != 4) {
            image = MFImage.createImage("/gimmick/down_island.png");
         } else {
            image = MFImage.createImage("/gimmick/drip_island_4.png");
         }
      }

   }

   public static void releaseAllResource() {
      image = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      switch(var2) {
      case 1:
         var1.beStop(this.collisionRect.y0, 1, this);
         this.used = true;
         break;
      case 2:
      case 3:
         if ((var1.faceDirection && var2 == 3 || !var1.faceDirection && var2 == 2) && var1.getCollisionRect().y1 < this.collisionRect.y1) {
            var1.beStop(this.collisionRect.y0, 1, this);
            this.used = true;
         }
      case 4:
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, image, 3);
   }

   public int getPaintLayer() {
      return 3;
   }

   public void logic() {
      int var1 = this.posY;
      if (player.isFootOnObject(this)) {
         if (player instanceof PlayerKnuckles) {
            ((PlayerKnuckles)player).setFloating(false);
         }

         this.posY = GameTime.advance(this, "posY", this.posY, 400);
         this.checkWithMap(this.posX, var1, this.posX, this.posY);
         if (this.posY + (COLLISION_HEIGHT >> 1) >= this.getGroundY(this.posX, this.posY)) {
            this.posY = this.getGroundY(this.posX, this.posY) - (COLLISION_HEIGHT >> 1);
         }
      } else {
         this.posY = GameTime.advance(this, "posY", this.posY, -(400));
         if (this.posY <= this.posYOriginal) {
            this.posY = this.posYOriginal;
         }
      }

      this.checkWithPlayer(this.posX, var1, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var7 = this.collisionRect;
      int var5 = COLLISION_WIDTH;
      int var6 = COLLISION_HEIGHT;
      int var3 = COLLISION_WIDTH;
      int var4 = COLLISION_HEIGHT;
      var7.setRect(var1 - (var5 >> 1), var2 - (var6 >> 1), var3, var4);
   }
}
