package SonicGBA;

class SeabedVolcanoHurt extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1152;
   private static final int COLLISION_WIDTH = 3072;
   private SeabedVolcanoBase sb;

   public SeabedVolcanoHurt(int var1, int var2, SeabedVolcanoBase var3) {
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
      var2 = SeabedVolcanoPlatform.sPosY;
      var1 = var2;
      if (var2 > 0) {
         var1 = 0;
      }

      var2 = var1;
      if (var1 < -1152) {
         var2 = -1152;
      }

      CollisionRect var5 = this.collisionRect;
      int var3 = this.posX;
      int var4 = this.posY;
      var1 = Math.abs(var2);
      var5.setRect(var3 - 1536, var4 + var2, 3072, var1);
   }
}
