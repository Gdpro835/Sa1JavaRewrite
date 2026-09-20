package SonicGBA;

public abstract class MoveObject extends GameObject {
   protected int totalVelocity;

   public int getVelX() {
      return this.velX;
   }

   public int getVelY() {
      return this.velY;
   }

   public void setVelX(int var1) {
      this.velX = var1;
   }

   public void setVelY(int var1) {
      this.velY = var1;
   }
}
