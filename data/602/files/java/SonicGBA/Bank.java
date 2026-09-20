package SonicGBA;

class Bank extends GimmickObject {
   private boolean touching;

   protected Bank(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1.onBank) {
         var1.onBank = false;
         var1.bankwalking = false;
      } else if ((this.objId == 28 && var2 == 3 || this.objId == 29 && var2 == 2) && var1.collisionState == 0) {
         this.touching = true;
         player.bankwalking = true;
      }

   }

   public void doWhileNoCollision() {
      if (this.touching) {
         if (this.objId == 28 && player.getVelX() > 500 || this.objId == 29 && player.getVelX() < -500) {
            player.setBank();
         }

         this.touching = false;
      }

   }
}
