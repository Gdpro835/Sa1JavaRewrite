package SonicGBA;

class ExtraRing extends MoveRingObject {
   protected ExtraRing(int var1, int var2, int var3, int var4, int var5, long var6, int var8) {
      super(var1, var2, var3, var4, var5, var6, var8);
   }

   public int getGravity() {
      return GRAVITY >> 2;
   }
}
