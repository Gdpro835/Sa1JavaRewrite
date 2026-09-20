package SonicGBA;

import GameEngine.time.GameTime;

import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BreakWall extends GimmickObject {
   private static final int BREAK_NUM_HEIGHT = 9;
   private static final int BREAK_NUM_WIDTH = 4;
   private static final int BREAK_WIDTH = 448;
   private static final int COLLISION_HEIGHT = 4096;
   private static final int COLLISION_WIDTH = 2048;
   private static final int DRAW_BREAK_WIDTH = 7;
   private static MFImage image;
   private int breakCount;
   private int breakLimitLine;
   private boolean breakOver = false;
   private int[][][] breakPosition;
   private boolean breaking = false;
   private boolean positiveDirection;

   protected BreakWall(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      var2 = this.posX;
      short var10;
      if (this.iLeft == 0) {
         var10 = 0;
      } else {
         var10 = 2048;
      }

      this.posX = var2 - var10;
      int[][][] var8 = new int[4][9][3];
      this.breakPosition = var8;

      for(var1 = 0; var1 < 4; ++var1) {
         for(var2 = 0; var2 < 9; ++var2) {
            this.breakPosition[var1][var2][0] = this.posX + var1 * 448;
            this.breakPosition[var1][var2][1] = this.posY + var2 * 448;
            this.breakPosition[var1][var2][2] = -GRAVITY;
         }
      }

      this.breakLimitLine = this.posY + 4096 + 2048;
      if (image == null) {
         try {
            StringBuilder var11 = new StringBuilder("/gimmick/breakWall_");
            var11 = var11.append(StageManager.getCurrentZoneId()).append(".png");
            image = MFImage.createImage(var11.toString());
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

   }

   public static void releaseAllResource() {
      image = null;
   }

   public void close() {
      this.breakPosition = null;
   }

   public void doInitWhileInCamera() {
      this.breaking = false;
      this.breakOver = false;
      this.breakCount = GameTime.set(this, "breakCount", 0);

      for(int var1 = 0; var1 < 4; ++var1) {
         for(int var2 = 0; var2 < 9; ++var2) {
            this.breakPosition[var1][var2][0] = this.posX + var1 * 448;
            this.breakPosition[var1][var2][1] = this.posY + var2 * 448;
            this.breakPosition[var1][var2][2] = -GRAVITY;
         }
      }

   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      this.breaking = false;
      if (false) {
         this.breakCount = GameTime.set(this, "breakCount", 0);
      }

      this.breaking = true;
      this.breakOver = false;
      if (var2 == 3) {
         this.positiveDirection = true;
      }

      if (var2 == 2) {
         this.positiveDirection = false;
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1.isAttackingEnemy()) {
         this.doWhileBeAttack(var1, var2, 0);
      } else if (!this.breaking) {
         var1.beStop(0, var2, this);
      }

   }

   public void draw(MFGraphics var1) {
      this.drawCollisionRect(var1);
      if (!this.breakOver) {
         if (this.breaking) {
            for(int var2 = 0; var2 < 4; ++var2) {
               for(int var3 = 0; var3 < 9; ++var3) {
                  MFImage var6 = image;
                  int var5 = this.breakPosition[var2][var3][0];
                  int var4 = this.breakPosition[var2][var3][1];
                  this.drawInMap(var1, var6, var2 * 7, var3 * 7, 7, 7, 0, var5, var4, 20);
               }
            }
         } else {
            this.drawInMap(var1, image, 20);
         }
      }

   }

   public void logic() {
      if (this.breaking && !this.breakOver) {
         int var2 = 0;
         this.breakOver = true;
         if (!IsGamePause) {
            int var1;
            if (this.positiveDirection) {
               var1 = 0;
            } else {
               var1 = 3;
            }

            label59:
            while(true) {
               if (this.positiveDirection) {
                  if (var1 >= 4) {
                     break;
                  }
               } else if (var1 < 0) {
                  break;
               }

               for(int var3 = 8; var3 >= 0; --var3) {
                  int[] var6 = this.breakPosition[var1][var3];
                  int var5 = var6[0];
                  byte var4;
                  if (this.positiveDirection) {
                     var4 = 50;
                  } else {
                     var4 = -50;
                  }

                  var6[0] = GameTime.advance(var6, "0", var5, var4);
                  var6 = this.breakPosition[var1][var3];
                  var6[2] = GameTime.advance(var6, String.valueOf(2), var6[2], GRAVITY);
                  var6 = this.breakPosition[var1][var3];
                  var6[1] = GameTime.advance(var6, String.valueOf(1), var6[1], this.breakPosition[var1][var3][2]);
                  if (this.breakPosition[var1][var3][1] < this.breakLimitLine) {
                     this.breakOver = false;
                  }

                  ++var2;
                  if (this.breakCount == 0) {
                     SoundSystem.getInstance().playSe(45);
                  }

                  if (var2 >= this.breakCount) {
                     this.breakCount = GameTime.advance(this, "breakCount", this.breakCount, 1);
                     break label59;
                  }
               }

               byte var7;
               if (this.positiveDirection) {
                  var7 = 1;
               } else {
                  var7 = -1;
               }

               var1 += var7;
            }
         }

         if (this.breakCount < 36 && this.breakOver) {
            this.breakOver = false;
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1, var2, 2048, 4096);
   }
}
