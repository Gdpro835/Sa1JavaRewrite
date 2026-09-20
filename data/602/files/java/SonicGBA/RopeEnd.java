package SonicGBA;

class RopeEnd extends GimmickObject {
   protected RopeEnd(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public void doWhileRail(PlayerObject var1, int var2) {
      if (var1.outOfControl) {
         if (var1.outOfControlObject instanceof RopeStart) {
            RopeStart var3 = (RopeStart)var1.outOfControlObject;
            var3.posX = this.posX;
            var3.posY = this.posY;
         }

         if (player.getFootPositionX() < this.posX) {
            player.setFootPositionX(this.posX);
         }

         player.setFootPositionY(this.collisionRect.y1);
         var1.outOfControl = false;
         var1.setCollisionState((byte)1);
         var1.stopMove();
         var1.collisionChkBreak = true;
         var1.railing = false;
         var2 = var1.velX;
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1, var2, 1024, 2560);
   }
}
