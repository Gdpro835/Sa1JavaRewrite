package SonicGBA;

import GameEngine.time.GameTime;

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
      if (this.timeCount <= 0) return;
      this.timeCount = Math.max(0, GameTime.advance(this, "timeCount", this.timeCount, -1));
      double elapsed = 10.0 - GameTime.precise(this, "timeCount", this.timeCount);
      this.distance = elapsed >= 9.0 ? 0 : (int) (this.power * Math.pow(0.5, Math.max(0, elapsed - 1.0))
              * Math.cos(Math.PI * Math.max(0, elapsed - 1.0)));
      if (this.timeCount == 0) { this.power = 0; this.distance = 0; }
   }

   public void startHobin(int var1, int var2, int var3) {
      for(this.power = 1200; var2 < 0; var2 += 360) {
      }

      this.degree = var2 % 360;
      this.timeCount = GameTime.set(this, "timeCount", 10);
   }
}
