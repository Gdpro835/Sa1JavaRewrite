package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import com.sega.mobile.framework.device.MFGraphics;

class BreakingParts extends EnemyObject {
   private static final int COLLISION_HEIGHT = 64;
   private static final int COLLISION_WIDTH = 64;
   private static Animation PartsAni;
   private static int frame;
   private int[] Vix = new int[]{750, -300, -150, 150, 450, -750, 600, 300, -450};
   private int[] Viy = new int[]{-1200, -1050, -900, -750, -600};
   private AnimationDrawer partsdrawer;
   private int[][] pos;

   protected BreakingParts(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (PartsAni == null) {
         PartsAni = new Animation("/animation/parts");
      }

      this.partsdrawer = PartsAni.getDrawer(0, false, 0);
      this.pos = new int[3][5];
      this.posX = var2;
      this.posY = var3;

      for(var1 = 0; var1 < this.pos.length; ++var1) {
         this.pos[var1][0] = this.posX;
         this.pos[var1][1] = this.posY;
         this.pos[var1][2] = this.Vix[MyRandom.nextInt(this.Vix.length)];
         this.pos[var1][3] = this.Viy[MyRandom.nextInt(this.Viy.length)];
         this.pos[var1][4] = MyRandom.nextInt(5);
      }

      frame = 0;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(PartsAni);
      PartsAni = null;
   }

   public void close() {
      this.partsdrawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         for(int var2 = 0; var2 < this.pos.length; ++var2) {
            this.partsdrawer.setActionId(this.pos[var2][4]);
            this.drawInMap(var1, this.partsdrawer, this.pos[var2][0], this.pos[var2][1]);
         }
      }

   }

   public void logic() {
      if (!IsGamePause) {
         ++frame;

         for(int var1 = 0; var1 < this.pos.length; ++var1) {
            int[] var2 = this.pos[var1];
            var2[0] += this.pos[var1][2];
            var2 = this.pos[var1];
            var2[3] += GRAVITY >> 1;
            var2 = this.pos[var1];
            var2[1] += this.pos[var1][3];
         }

         if (frame >= 50) {
            this.dead = true;
         }
      }

      this.refreshCollisionRect(this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 32, var2 - 32, 64, 64);
   }
}
