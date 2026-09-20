package SonicGBA;

class ChangeRectRegion extends GimmickObject {
   private boolean isChanged;

   protected ChangeRectRegion(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1.animationID == 4 && var1.collisionState == 1) {
         var1.changeRectHeight = true;
         this.isChanged = true;
      } else {
         var1.changeRectHeight = false;
         this.isChanged = false;
      }

   }

   public void doWhileNoCollision() {
      if (this.isChanged && player.changeRectHeight) {
         player.changeRectHeight = false;
         this.isChanged = false;
      }

   }
}
