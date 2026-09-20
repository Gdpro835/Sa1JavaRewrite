package SonicGBA;

class SteamHurt extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_OFFSET_Y = -1280;
   private static final int COLLISION_WIDTH = 128;
   private SteamBase sb;

   protected SteamHurt(int var1, int var2, SteamBase var3) {
      super(0, var1, var2, 0, 0, 0, 0);
      this.sb = var3;
   }

   public void close() {
      this.sb = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.collisionRect.getHeight() != 0 && !var1.isFootOnObject(this.sb.sp)) {
         var1.beHurt();
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      var2 = SteamPlatform.sPosY + 768;
      var1 = var2;
      if (var2 > 0) {
         var1 = 0;
      }

      var2 = var1;
      if (var1 < -1024) {
         var2 = -1024;
      }

      CollisionRect var5 = this.collisionRect;
      var1 = this.posX;
      int var3 = this.posY;
      int var4 = Math.abs(var2);
      var5.setRect(var1 - 64, var3 - 1280 + var2, 128, var4);
   }
}
