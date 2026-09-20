package SonicGBA;

import GameEngine.time.GameTime;

import Lib.MyAPI;

class MoveCalculator {
   private static final int DEGREE_VELOCITY = 5;
   private static final int HALF_MOVE_TIME = 17;
   private static final int MOVE_TIME = 34;
   private static int degree;
   public static boolean direction;
   private static boolean isSide = false;
   private static int moveCount;
   private static int moveCount2;
   private int centerPosition;
   private int position;
   private int radius;
   private boolean ratio;

   public MoveCalculator(int var1, int var2, boolean var3) {
      this.centerPosition = var1;
      this.radius = var2;
      this.ratio = var3;
      this.position = var1;
      isSide = false;
   }

   public static void staticLogic() {
      if (direction) {
         isSide = false;
         moveCount2 = GameTime.advance(MoveCalculator.class, "moveCount2", moveCount2, 1);
         if (moveCount2 > 34) {
            moveCount2 = GameTime.set(MoveCalculator.class, "moveCount2", 34);
            direction = false;
            isSide = true;
         }
      } else {
         isSide = false;
         moveCount2 = GameTime.advance(MoveCalculator.class, "moveCount2", moveCount2, -(1));
         if (moveCount2 < 0) {
            moveCount2 = GameTime.set(MoveCalculator.class, "moveCount2", 0);
            direction = true;
            isSide = true;
         }
      }

      moveCount = moveCount2 - 17;
      degree = GameTime.advance(MoveCalculator.class, "degree", degree, 5);
      degree = GameTime.wrap(MoveCalculator.class, "degree", degree, 360);
   }

   public int getPosition() {
      int var2 = this.centerPosition;
      int var1 = MyAPI.dSin(degree) * this.radius / 100;
      return var2 + var1;
   }

   public boolean getSide() {
      boolean var1;
      if (degree != 90 && degree != 270) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void logic() {
   }
}
