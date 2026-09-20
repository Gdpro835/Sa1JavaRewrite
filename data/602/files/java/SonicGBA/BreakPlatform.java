package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Coordinate;
import Lib.MyAPI;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BreakPlatform extends GimmickObject {
   private static final int BREAK_HEIGHT = 8;
   private static final int BREAK_WIDTH = 8;
   private static final int COLLISION_HEIGHT = 2048;
   private static final int COLLISION_WIDTH = 4096;
   private static final int REST_FRAME = 3;
   private static MFImage platformImage = null;
   private int blockNumX;
   private int blockNumY;
   private int breakCount;
   private boolean breakFlag = false;
   private int[][] breakVelY;
   private int[][] breakY;

   protected BreakPlatform(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (platformImage == null) {
         StringBuilder var8;
         if (StageManager.getCurrentZoneId() != 6) {
            var8 = new StringBuilder("/gimmick/break_platform_");
            var8 = var8.append(StageManager.getCurrentZoneId()).append(".png");
            platformImage = MFImage.createImage(var8.toString());
         } else {
            var8 = new StringBuilder("/gimmick/break_platform_");
            var8 = var8.append(StageManager.getCurrentZoneId()).append(StageManager.getStageID() - 9).append(".png");
            platformImage = MFImage.createImage(var8.toString());
         }
      }

   }

   private void breakingDraw(MFGraphics var1, Coordinate var2) {
      if (this.breakFlag) {
         int var3;
         int var4;
         int var5;
         int var6;
         int[] var16;
         if (!GameObject.IsGamePause) {
            for(var3 = 0; var3 < this.breakCount; ++var3) {
               var16 = this.breakVelY[this.blockNumX - 1 - var3 % this.blockNumX];
               var5 = var3 / this.blockNumX;
               var4 = var16[var5];
               var6 = GRAVITY;
               var16[var5] = GameTime.advance(var16, String.valueOf(var5), var4, var6);
            }

            this.breakCount = GameTime.advance(this, "breakCount", this.breakCount, 1);
            if (this.breakCount > this.blockNumX * this.blockNumY) {
               this.breakCount = GameTime.set(this, "breakCount", this.blockNumX * this.blockNumY);
            }
         }

         for(var3 = 0; var3 < this.blockNumX * this.blockNumY; ++var3) {
            if (!GameObject.IsGamePause) {
               var16 = this.breakY[var3 % this.blockNumX];
               var4 = var3 / this.blockNumX;
               var5 = var16[var4];
               var6 = this.breakVelY[var3 % this.blockNumX][var3 / this.blockNumX];
               var16[var4] = GameTime.advance(var16, String.valueOf(var4), var5, var6);
            }

            MFImage var17 = platformImage;
            int var8 = this.blockNumX;
            int var7 = this.blockNumY;
            int var9 = var3 / this.blockNumX;
            byte var18;
            if (this.iLeft == 0) {
               var18 = 0;
            } else {
               var18 = 2;
            }

            byte var19;
            if (this.iLeft == 0) {
               var19 = 1;
            } else {
               var19 = -1;
            }

            int var14 = this.blockNumX;
            int var11 = this.posX;
            int var12 = this.blockNumY;
            int var13 = var3 / this.blockNumX;
            int var10 = this.posY;
            int var15 = this.breakY[var3 % this.blockNumX][var3 / this.blockNumX];
            byte var20;
            if (this.iLeft == 0) {
               var20 = 4;
            } else {
               var20 = 8;
            }

            this.drawInMap(var1, var17, var3 % var8 * 8, (var7 - 1 - var9) * 8, 8, 8, var18, var19 * (var3 % var14) * 512 + var11, (var12 - 1 - var13) * 512 + var10 + var15, var20 | 16);
         }
      }

   }

   private void initialBreaking(MFImage var1) {
      this.blockNumX = MyAPI.zoomIn(var1.getWidth() / 8);
      this.blockNumY = MyAPI.zoomIn(var1.getHeight() / 8);
      int var2 = this.blockNumX;
      int var3 = this.blockNumY;
      this.breakY = new int[var2][var3];
      var3 = this.blockNumX;
      var2 = this.blockNumY;
      this.breakVelY = new int[var3][var2];
      this.breakCount = GameTime.set(this, "breakCount", 0);
      this.breakFlag = true;
   }

   private boolean isBreakingOver() {
      boolean var1;
      if (this.breakCount >= this.blockNumX * this.blockNumY) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private boolean isStandingOver() {
      boolean var1;
      if (this.breakCount >= this.blockNumX * (this.blockNumY - 1)) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static void releaseAllResource() {
      platformImage = null;
   }

   public void close() {
   }

   public void doInitWhileInCamera() {
      this.used = false;
      this.breakFlag = false;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.used || !this.isStandingOver()) {
         switch(var2) {
         case 1:
            var1.beStop(this.collisionRect.y0, 1, this);
            if (!this.used) {
               this.used = true;
               this.initialBreaking(platformImage);
               SoundSystem.getInstance().playSe(45);
            }
         case 2:
         case 3:
         default:
            break;
         case 4:
            if (var1.getMoveDistance().y > 0 && var1.getCollisionRect().y1 < this.collisionRect.y1) {
               var1.beStop(this.collisionRect.y0, 1, this);
               if (!this.used) {
                  this.used = true;
                  this.initialBreaking(platformImage);
                  SoundSystem.getInstance().playSe(45);
               }
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (this.breakFlag) {
         this.breakingDraw(var1, camera);
      } else {
         MFImage var8 = platformImage;
         int var4 = MyAPI.zoomIn(platformImage.getWidth());
         int var5 = MyAPI.zoomIn(platformImage.getHeight());
         byte var2;
         if (this.iLeft == 0) {
            var2 = 0;
         } else {
            var2 = 2;
         }

         int var7 = this.posX;
         int var6 = this.posY;
         byte var3;
         if (this.iLeft == 0) {
            var3 = 4;
         } else {
            var3 = 8;
         }

         this.drawInMap(var1, var8, 0, 0, var4, var5, var2, var7, var6, var3 | 16);
      }

   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (this.used && this.isStandingOver()) {
         player.cancelFootObject(this);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      if (this.iLeft == 0) {
         this.collisionRect.setRect(var1, var2, 4096, 2048);
      } else {
         this.collisionRect.setRect(var1 - 4096, var2, 4096, 2048);
      }

   }
}
