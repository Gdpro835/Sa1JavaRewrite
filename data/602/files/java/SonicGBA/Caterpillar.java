package SonicGBA;

import Lib.Animation;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;

class Caterpillar extends EnemyObject {
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 1024;
   private static final int STATE_MOVE = 0;
   private static Animation caterpillarAnimation;
   private CaterpillarBody[] body;
   private int circleCenterX;
   private int circleCenterY;
   private int dg = 10;
   private int leftx;
   private int lefty;
   private int plus = 1;
   private int plus_cnt = 0;
   private int[][] pos;
   private int rightx;
   private int righty;
   private int state;

   protected Caterpillar(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posY += 2560;
      this.circleCenterX = this.posX + (this.mWidth >> 1);
      this.circleCenterY = this.posY;
      this.plus_cnt = 0;
      if (caterpillarAnimation == null) {
         caterpillarAnimation = new Animation("/animation/caterpillar");
      }

      this.drawer = caterpillarAnimation.getDrawer(0, false, 2);
      this.pos = new int[5][2];
      this.body = new CaterpillarBody[5];
      this.body[0] = new CaterpillarBody(var1, var2, var3, var4, var5, var6, var7, true, this);

      for(int var8 = 1; var8 < this.body.length; ++var8) {
         this.body[var8] = new CaterpillarBody(var1, var2, var3, var4, var5, var6, var7, false, this);
         addGameObject(this.body[var8], var2, var3);
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(caterpillarAnimation);
      caterpillarAnimation = null;
   }

   public void close() {
      this.pos = null;
      if (this.body != null) {
         for(int var1 = 0; var1 < this.body.length; ++var1) {
            this.body[var1] = null;
         }
      }

      this.body = null;
      super.close();
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.dead) {
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         int var2;
         for(var2 = this.pos.length - 1; var2 >= 0; --var2) {
            switch(var2) {
            case 0:
               this.drawer.setActionId(0);
               break;
            case 1:
            case 2:
               this.drawer.setActionId(1);
               break;
            case 3:
            case 4:
               this.drawer.setActionId(2);
            }

            if (this.pos[var2][0] == this.circleCenterX - (this.mWidth >> 1)) {
               this.drawer.setTrans(2);
            } else if (this.pos[var2][0] == this.circleCenterX + (this.mWidth >> 1)) {
               this.drawer.setTrans(0);
            }

            this.drawInMap(var1, this.drawer, this.pos[var2][0], this.pos[var2][1]);
         }

         for(var2 = 0; var2 < this.body.length; ++var2) {
            this.body[var2].draw(var1);
         }

         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var2 = this.posX;
         int var3 = this.posY;
         int var1;
         switch(this.state) {
         case 0:
            this.plus_cnt += this.plus;

            for(var1 = 0; var1 < this.pos.length; ++var1) {
               this.pos[var1][0] = this.circleCenterX - (this.mWidth >> 1) * MyAPI.dCos(this.dg * (this.plus_cnt - var1 * 3)) / 100;
               this.pos[var1][1] = this.circleCenterY + (this.mWidth >> 1) * MyAPI.dSin(this.dg * (this.plus_cnt - var1 * 3)) / 200;
            }

            this.posX = this.pos[0][0];
            this.posY = this.pos[0][1];

            for(var1 = 0; var1 < this.body.length; ++var1) {
               this.body[var1].logic(this.pos[var1][0], this.pos[var1][1]);
            }

            this.checkWithPlayer(var2, var3, this.posX, this.posY);
         }

         for(var1 = 1; var1 < this.body.length; ++var1) {
            this.body[var1].dead = this.body[0].dead;
         }

         this.dead = this.body[0].dead;
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      if (this.pos == null) {
         this.pos = new int[5][2];
      }

      this.leftx = this.pos[0][0] - 512;
      this.lefty = this.pos[0][1] - 512;
      this.rightx = this.pos[0][0] + 512;
      this.righty = this.pos[0][1] + 512;

      for(var1 = 1; var1 < this.pos.length; ++var1) {
         if (this.pos[var1][0] - 512 < this.leftx) {
            this.leftx = this.pos[var1][0] - 512;
         }

         if (this.pos[var1][1] - 512 < this.lefty) {
            this.lefty = this.pos[var1][1] - 512;
         }

         if (this.pos[var1][0] + 512 > this.rightx) {
            this.rightx = this.pos[var1][0] + 512;
         }

         if (this.pos[var1][1] + 512 > this.righty) {
            this.righty = this.pos[var1][1] + 512;
         }
      }

      this.collisionRect.setRect(this.leftx, this.lefty, this.rightx - this.leftx, this.righty - this.lefty);
   }

   public void setDead() {
      for(int var1 = 0; var1 < this.body.length; ++var1) {
         this.body[var1].dead = true;
      }

      this.dead = true;
   }
}
