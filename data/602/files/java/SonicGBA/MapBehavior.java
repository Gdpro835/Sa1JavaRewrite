package SonicGBA;

public interface MapBehavior {
   void doWhileToucRoof(int var1, int var2);

   void doWhileTouchGround(int var1, int var2);

   int getGravity();

   boolean hasDownCollision();

   boolean hasSideCollision();

   boolean hasTopCollision();
}
