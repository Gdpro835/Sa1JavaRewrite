package SonicGBA;

abstract class PlatformObject extends GimmickObject {
   protected PlatformObject(int var1, int var2) {
      super(0, var1, var2, 0, 0, 0, 0);
   }

   protected PlatformObject(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      switch(var2) {
      case 1:
         var1.beStop(this.collisionRect.y0, 1, this);
      case 2:
      case 3:
      default:
         break;
      case 4:
         if (var1.getMoveDistance().y > 0 && var1.getCollisionRect().y1 < this.collisionRect.y1) {
            var1.beStop(this.collisionRect.y0, 1, this);
         }
      }

   }
}
