package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import com.sega.mobile.framework.device.MFGraphics;

class BossBroken extends EnemyObject {
   private static final int COLLISION_HEIGHT = 1;
   private static final int COLLISION_WIDTH = 1;
   private static final int MAX_BOOM = 8;
   private static Animation PartsAni;
   private static int frame;
   private boolean IsEnd;
   private int[] Vix = new int[]{750, -300, -150, 150, 450, -750, 600, 300, -450};
   private int[] Viy = new int[]{-1200, -1050, -900, -750, -600};
   private int[] back_offset;
   private int boomCount;
   private int[][] boomPos;
   private AnimationDrawer[] boomdrawers;
   private int enemyid;
   private int jump_time = 10;
   private int offsety = 0;
   private AnimationDrawer partsdrawer;
   private int[][] pos;
   private int range = 6400;
   private int startx;
   private int starty;
   private int time_cnt;
   private int totalBoom;
   private int total_cnt;
   private int total_cnt_max = 8;

   protected BossBroken(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      int[] var8 = new int[]{-15, -10, -5, 0, 5, 10, 15};
      this.back_offset = var8;
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.enemyid = var1;
      switch(var1) {
      case 22:
         this.offsety = 1728;
         break;
      case 23:
         this.offsety = 960;
         break;
      case 24:
      case 25:
         this.offsety = 0;
      }

      this.startx = this.posX;
      this.starty = this.posY - this.offsety;
      if (BoomAni == null) {
         BoomAni = new Animation("/animation/boom");
      }

      this.boomdrawers = new AnimationDrawer[8];

      for(var2 = 0; var2 < 8; ++var2) {
         this.boomdrawers[var2] = BoomAni.getDrawer(0, false, 0);
      }

      this.boomPos = new int[8][2];
      this.boomCount = 0;
      this.totalBoom = 0;
      if (var1 != 26) {
         if (var1 == 27) {
            if (PartsAni == null) {
               PartsAni = new Animation("/animation/boss6_parts");
            }
         } else if (PartsAni == null) {
            PartsAni = new Animation("/animation/parts");
         }

         this.partsdrawer = PartsAni.getDrawer(0, true, 0);
      }

      this.pos = new int[3][5];

      for(var1 = 0; var1 < this.pos.length; ++var1) {
         this.pos[var1][0] = this.startx;
         this.pos[var1][1] = this.starty;
         this.pos[var1][2] = 0;
         this.pos[var1][3] = 0;
         this.pos[var1][4] = 0;
      }

      this.total_cnt_max = 8;
      this.jump_time = 10;
      frame = 1;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(BoomAni);
      Animation.closeAnimation(PartsAni);
      BoomAni = null;
      PartsAni = null;
   }

   public void close() {
      for(int var1 = 0; var1 < 8; ++var1) {
         this.boomdrawers[var1] = null;
      }

      this.boomdrawers = null;
      this.partsdrawer = null;
      this.pos = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
   }

   public void draw(MFGraphics var1) {
      if (this.total_cnt < this.total_cnt_max) {
         int var2;
         if (!IsGamePause) {
            ++this.time_cnt;
            if (this.time_cnt % this.jump_time == 0 || this.time_cnt == 0) {
               ++this.total_cnt;

               for(var2 = 0; var2 < this.pos.length; ++var2) {
                  this.pos[var2][0] = this.startx;
                  this.pos[var2][1] = this.starty;
                  this.pos[var2][2] = this.Vix[MyRandom.nextInt(this.Vix.length)];
                  this.pos[var2][3] = this.Viy[MyRandom.nextInt(this.Viy.length)];
                  this.pos[var2][4] = MyRandom.nextInt(6);
               }
            }

            for(var2 = 0; var2 < this.pos.length; ++var2) {
               int[] var3 = this.pos[var2];
               var3[0] += this.pos[var2][2];
               var3 = this.pos[var2];
               var3[3] += GRAVITY >> 1;
               var3 = this.pos[var2];
               var3[1] += this.pos[var2][3];
            }

            if (this.total_cnt >= this.total_cnt_max) {
               this.IsEnd = true;
            }

            ++this.boomCount;
            this.boomCount %= 2;
            if (this.boomCount == 1) {
               this.boomPos[this.totalBoom][0] = this.startx + (MyRandom.nextInt(60) - 30 << 6);
               this.boomPos[this.totalBoom][1] = this.starty + (MyRandom.nextInt(60) - 30 << 6);
               this.boomdrawers[this.totalBoom].restart();
               ++this.totalBoom;
            }

            if (this.totalBoom == 8) {
               this.totalBoom = 0;
            }
         }

         for(var2 = 0; var2 <= this.totalBoom; ++var2) {
            if (!this.boomdrawers[var2].checkEnd()) {
               this.drawInMap(var1, this.boomdrawers[var2], this.boomPos[var2][0], this.boomPos[var2][1]);
            }
         }

         if (this.enemyid != 26) {
            for(var2 = 0; var2 < this.pos.length; ++var2) {
               this.partsdrawer.setActionId(this.pos[var2][4]);
               this.drawInMap(var1, this.partsdrawer, this.pos[var2][0], this.pos[var2][1]);
            }
         }
      }

   }

   public boolean getEndState() {
      return this.IsEnd;
   }

   public void logic() {
   }

   public void logicBoom(int var1, int var2) {
      if (!IsGamePause) {
         if (this.total_cnt <= this.total_cnt_max >> 1) {
            this.startx = var1;
            this.starty = var2;
         } else {
            this.startx = (this.back_offset[MyRandom.nextInt(this.back_offset.length)] << 6) + var1;
            int var3 = this.back_offset[MyRandom.nextInt(this.back_offset.length)];
            var1 = this.offsety;
            this.starty = var2 - 1344 + (var3 << 6) - var1;
         }

         ++frame;
         frame %= 11;
         if (frame % 2 == 0) {
            soundInstance.playSe(35);
         }
      }

      AnimationDrawer.setAllPause(IsGamePause);
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1, var2, 1, 1);
   }

   public void setJumpTime(int var1) {
      this.jump_time = var1;
   }

   public void setTotalCntMax(int var1) {
      this.total_cnt_max = var1;
   }
}
