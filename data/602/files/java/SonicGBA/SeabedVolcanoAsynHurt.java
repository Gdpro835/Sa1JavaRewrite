package SonicGBA;

class SeabedVolcanoAsynHurt extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1152;
   private static final int COLLISION_WIDTH = 3072;
   private SeabedVolcanoAsynBase sb;

   public SeabedVolcanoAsynHurt(int var1, int var2, SeabedVolcanoAsynBase var3) {
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
      var2 = SeabedVolcanoAsynPlatform.sPosY;
      var1 = var2;
      if (var2 > 0) {
         var1 = 0;
      }

      var2 = var1;
      if (var1 < -1152) {
         var2 = -1152;
      }

      CollisionRect var5 = this.collisionRect;
      int var4 = this.posX;
      int var3 = this.posY;
      var1 = Math.abs(var2);
      var5.setRect(var4 - 1536, var3 + var2, 3072, var1);
   }
}
