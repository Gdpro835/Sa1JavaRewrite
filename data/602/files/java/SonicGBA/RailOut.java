package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class RailOut extends GimmickObject {
   private static final int COLLISION_HEIGHT = 2560;
   private static final int COLLISION_WIDTH = 2688;
   private static final int IMAGE_HEIGHT = 1536;

   protected RailOut(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (RailIn.railInOutImage == null) {
         RailIn.railInOutImage = MFImage.createImage("/gimmick/gimmick_67_68.png");
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      switch(var2) {
      case 1:
      case 2:
      case 3:
         var1.beStop(0, var2, this);
      default:
      }
   }

   public void doWhileNoCollision() {
      this.used = false;
   }

   public void doWhileRail(PlayerObject var1, int var2) {
      if (!this.used) {
         CollisionRect var5 = this.collisionRect;
         var2 = player.getBodyPositionX();
         int var3 = player.getBodyPositionY();
         PlayerObject var4 = player;
         if (!var5.collisionChk(var2, var3 - (1536 >> 1))) {
            var5 = this.collisionRect;
            var3 = player.getBodyPositionX();
            var2 = player.getBodyPositionY();
            if (!var5.collisionChk(var3, var2)) {
               return;
            }
         }

         player.railOut(this.posX, this.posY);
         this.used = true;
      }

   }

   public void draw(MFGraphics var1) {
      MFImage var4 = RailIn.railInOutImage;
      int var3 = this.posX;
      int var2 = this.posY;
      this.drawInMap(var1, var4, var3, var2 - 2560 + 1536, 17);
      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 3;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1344, var2 - 2560, 2688, 2560);
   }
}
