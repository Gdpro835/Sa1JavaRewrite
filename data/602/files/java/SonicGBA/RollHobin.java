package SonicGBA;

import GameEngine.time.GameTime;

import Lib.MyAPI;

class RollHobin extends BallHobin {
   private static final int DEGREE_VELOCITY = -4;
   private static int degree;
   private int centerX;
   private int centerY;
   private int degreeOffset;
   private int radius;

   protected RollHobin(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (this.mWidth > this.mHeight) {
         var1 = this.mWidth;
      } else {
         var1 = this.mHeight;
      }

      this.radius = var1;
      this.centerX = this.posX;
      this.centerY = this.posY;
      this.calHobinPosition();
      this.degreeOffset = this.iLeft * 360 / 8;
   }

   private void calHobinPosition() {
      this.posX = this.centerX + this.radius * MyAPI.dCos(degree + this.degreeOffset) / 100;
      this.posY = this.centerY + this.radius * MyAPI.dSin(degree + this.degreeOffset) / 100;
   }

   public static void releaseAllResource() {
   }

   public static void staticLogic() {
      degree = GameTime.advance(RollHobin.class, "degree", degree, -(4));
      degree += 360;
      degree = GameTime.wrap(RollHobin.class, "degree", degree, 360);
   }

   public void close() {
   }

   public void logic() {
      int var2 = this.posX;
      int var1 = this.posY;
      this.calHobinPosition();
      this.checkWithPlayer(var2, var1, this.posX, this.posY);
   }
}
