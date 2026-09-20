package SonicGBA;

import Lib.MyAPI;

public class HobinCal implements SonicDef {
   private static final int MAX_DISTANCE = 512;
   private static final int MAX_POWER = 800;
   private int degree;
   private int distance;
   private int power;
   private int timeCount;

   public int getPosOffsetX() {
      return this.distance * MyAPI.dCos(this.degree) / 100;
   }

   public int getPosOffsetY() {
      return this.distance * MyAPI.dSin(this.degree) / 100;
   }

   public boolean isStop() {
      boolean var1;
      if (this.timeCount == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void logic() {
      if (this.timeCount > 0) {
         --this.timeCount;
      }

      if (this.timeCount > 0) {
         if (this.timeCount == 9) {
            this.distance = this.power;
         } else {
            this.distance = -this.distance >> 1;
         }

         if (this.timeCount == 1) {
            this.distance = 0;
            this.power = 0;
         }
      }

   }

   public void startHobin(int var1, int var2, int var3) {
      for(this.power = 1200; var2 < 0; var2 += 360) {
      }

      this.degree = var2 % 360;
      this.timeCount = 10;
   }
}
