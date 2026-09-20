package SonicGBA;

class RopeTurn extends GimmickObject {
   protected RopeTurn(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public void doWhileNoCollision() {
      this.used = false;
   }

   public void doWhileRail(PlayerObject var1, int var2) {
      if (!this.used && var1.outOfControl && var1.outOfControlObject instanceof RopeStart) {
         RopeStart var3 = (RopeStart)var1.outOfControlObject;
         if (var3.degree > 90) {
            var3.posX = this.posX;
            var3.posY = this.posY;
            var3.turn();
            this.used = true;
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 512, var2, 1024, 2560);
   }
}
