package SonicGBA;

import GameEngine.time.GameTime;

import Lib.MyAPI;
import Lib.MyRandom;
import com.sega.mobile.framework.device.MFGraphics;

class PatrolAnimal extends SmallAnimal {
   private static final int FLY_HEIGHT = 6400;
   private static final int FLY_RANGE = 640;
   private boolean direction;
   private int flyDegree;
   private int flyLimit;
   private int leftLimit;
   private int rightLimit;

   public PatrolAnimal(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5);
      boolean var8;
      if (MyRandom.nextInt(2) == 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      this.direction = var8;
      this.leftLimit = var6;
      this.rightLimit = var7;
      if (this.mObj != null) {
         MapObject var9 = this.mObj;
         var2 = this.posX;
         var3 = this.posY;
         byte var10;
         if (this.direction) {
            var10 = 1;
         } else {
            var10 = -1;
         }

         var9.setPosition(var2, var3, var10 * MyRandom.nextInt(300), 0, this);
      }

      this.flyLimit = var4 - 6400;
   }

   public void doWhileTouchGround(int var1, int var2) {
      if (this.type == 1) {
         boolean var3;
         if (!this.direction) {
            if (this.mObj.getPosX() < this.leftLimit) {
               if (this.direction) {
                  var3 = false;
               } else {
                  var3 = true;
               }

               this.direction = var3;
            }
         } else if (this.mObj.getPosX() > this.rightLimit) {
            if (this.direction) {
               var3 = false;
            } else {
               var3 = true;
            }

            this.direction = var3;
         }

         MapObject var4 = this.mObj;
         byte var5;
         if (this.direction) {
            var5 = 1;
         } else {
            var5 = -1;
         }

         var4.doJump(var5 * 300, -1000);
      }

   }

   public void draw(MFGraphics var1) {
      if (this.direction) {
         this.drawer.setTrans(2);
      } else {
         this.drawer.setTrans(0);
      }

      if (this.type == 1) {
         this.drawInMap(var1, this.drawer);
      } else {
         this.drawInMap(var1, this.drawer, this.posX, this.posY + MyAPI.dSin(this.flyDegree) * 640 / 100);
      }

   }

   public void logic() {
      if (this.type != 2) {
         if (this.mObj != null) {
            this.mObj.logic();
            this.posX = this.mObj.getPosX();
            this.posY = this.mObj.getPosY();
         }
      } else {
         boolean var3;
         if (!this.direction) {
            if (this.posX < this.leftLimit) {
               if (this.direction) {
                  var3 = false;
               } else {
                  var3 = true;
               }

               this.direction = var3;
            }
         } else if (this.posX > this.rightLimit) {
            if (this.direction) {
               var3 = false;
            } else {
               var3 = true;
            }

            this.direction = var3;
         }

         int var2 = this.posX;
         byte var1;
         if (this.direction) {
            var1 = -1;
         } else {
            var1 = 1;
         }

         this.posX = GameTime.advance(this, "posX", var2, var1 * -250);
         this.posY = GameTime.advance(this, "posY", this.posY, -(300));
         if (this.posY < this.flyLimit) {
            this.posY = this.flyLimit;
         }

         this.flyDegree = GameTime.advance(this, "flyDegree", this.flyDegree, 30);
         this.flyDegree = GameTime.wrap(this, "flyDegree", this.flyDegree, 360);
      }

      this.refreshCollisionRect(this.posX, this.posY);
   }
}
