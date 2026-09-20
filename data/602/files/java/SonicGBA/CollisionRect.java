package SonicGBA;

import Lib.Coordinate;
import Lib.Line;
import Lib.MyAPI;
import Lib.Line.CrossPoint;
import com.sega.mobile.framework.device.MFGraphics;

public class CollisionRect {
   private static CrossPoint point = new CrossPoint();
   public int centerX;
   public int centerY;
   public int degree;
   public int[] rX = new int[4];
   public int[] rY = new int[4];
   public int x0;
   public int x1;
   public int y0;
   public int y1;

   public CollisionRect() {
   }

   public CollisionRect(int var1, int var2, int var3, int var4) {
      this.setTwoPosition(var1, var2, var3, var4);
   }

   public CollisionRect(int var1, int var2, int var3, int var4, boolean var5) {
      this.setRect(var1, var2, var3, var4);
   }

   public void addVelocity(int var1, int var2) {
      if (var1 < 0) {
         this.x0 += var1;
      } else if (var1 > 0) {
         this.x1 += var1;
      }

      if (var2 < 0) {
         this.y0 += var2;
      } else if (var2 > 0) {
         this.y1 += var2;
      }

   }

   public boolean collisionChk(int var1, int var2) {
      boolean var3;
      if (this.y1 > var2 && this.y0 <= var2) {
         if (this.x1 > var1 && this.x0 <= var1) {
            var3 = true;
         } else {
            var3 = false;
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean collisionChk(CollisionRect var1) {
      boolean var2;
      if (this.degree == 0 && var1.degree == 0) {
         if (this.y1 > var1.y0 && this.y0 <= var1.y1) {
            if (this.x1 > var1.x0 && this.x0 <= var1.x1) {
               var2 = true;
            } else {
               var2 = false;
            }
         } else {
            var2 = false;
         }
      } else {
         var2 = this.collisionChkWithDegree(var1);
      }

      return var2;
   }

   public boolean collisionChkWidth(CollisionRect var1) {
      boolean var2;
      if (this.degree == 0 && var1.degree == 0) {
         if (this.x1 > var1.x0 && this.x0 <= var1.x1) {
            var2 = true;
         } else {
            var2 = false;
         }
      } else {
         var2 = this.collisionChkWithDegree(var1);
      }

      return var2;
   }

   public boolean collisionChkWithDegree(CollisionRect var1) {
      boolean var12;
      for(int var2 = 0; var2 < 4; ++var2) {
         for(int var3 = 0; var3 < 4; ++var3) {
            CrossPoint var13 = point;
            int var8 = this.rX[var2];
            int var7 = this.rY[var2];
            int var6 = this.rX[(var2 + 1) % 4];
            int var9 = this.rY[(var2 + 1) % 4];
            int var10 = var1.rX[var3];
            int var5 = var1.rY[var3];
            int var4 = var1.rX[(var3 + 1) % 4];
            int var11 = var1.rY[(var3 + 1) % 4];
            Line.getCrossPoint(var13, var8, var7, var6, var9, var10, var5, var4, var11);
            if (point.hasPoint) {
               var12 = true;
               return var12;
            }
         }
      }

      var12 = false;
      return var12;
   }

   public void draw(MFGraphics var1, Coordinate var2) {
      var1.setColor(16711680);
      var1.drawRect(this.x0 - var2.x, this.y0 - var2.y, this.x1 - this.x0, this.y1 - this.y0);
   }

   public int getCenterX() {
      return this.x0 + this.x1 >> 1;
   }

   public int getCenterY() {
      return this.y0 + this.y1 >> 1;
   }

   public CollisionRect getClone(int var1, int var2) {
      return new CollisionRect(this.x0 + var1, this.y0 + var2, this.x1 + var1, this.y1 + var2);
   }

   public int getHeight() {
      return this.y1 - this.y0;
   }

   public int getWidth() {
      return this.x1 - this.x0;
   }

   public boolean isDownOf(CollisionRect var1) {
      return this.isDownOf(var1, 0);
   }

   public boolean isDownOf(CollisionRect var1, int var2) {
      boolean var3;
      if (this.y0 >= var1.y1 - var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isLeftOf(CollisionRect var1) {
      return this.isLeftOf(var1, 0);
   }

   public boolean isLeftOf(CollisionRect var1, int var2) {
      boolean var3;
      if (this.x1 <= var1.x0 + var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isRightOf(CollisionRect var1) {
      return this.isRightOf(var1, 0);
   }

   public boolean isRightOf(CollisionRect var1, int var2) {
      boolean var3;
      if (this.x0 >= var1.x1 - var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean isUpOf(CollisionRect var1) {
      return this.isUpOf(var1, 0);
   }

   public boolean isUpOf(CollisionRect var1, int var2) {
      boolean var3;
      if (this.y1 <= var1.y0 + var2) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void setRect(int var1, int var2, int var3, int var4) {
      if (var3 < 0) {
         this.x0 = var1 + var3;
         this.x1 = var1;
      } else {
         this.x0 = var1;
         this.x1 = var1 + var3;
      }

      if (var4 < 0) {
         this.y0 = var2 + var4;
         this.y1 = var2;
      } else {
         this.y0 = var2;
         this.y1 = var2 + var4;
      }

      this.setRotate(this.degree, 0, 0);
   }

   public void setRotate(int var1, int var2, int var3) {
      this.degree = var1;
      if (var1 == 0) {
         this.rX[0] = this.x0;
         this.rY[0] = this.y0;
         this.rX[1] = this.x1;
         this.rY[1] = this.y0;
         this.rX[2] = this.x1;
         this.rY[2] = this.y1;
         this.rX[3] = this.x0;
         this.rY[3] = this.y1;
      }

      int var4 = this.x0 + var2;
      int var5 = this.y0 + var3;
      this.rX[0] = MyAPI.getRelativePointX(var4, -var2, -var3, var1);
      this.rY[0] = MyAPI.getRelativePointY(var5, -var2, -var3, var1);
      this.rX[1] = MyAPI.getRelativePointX(var4, this.x1 - this.x0 - var2, -var3, var1);
      this.rY[1] = MyAPI.getRelativePointY(var5, this.x1 - this.x0 - var2, -var3, var1);
      this.rX[2] = MyAPI.getRelativePointX(var4, this.x1 - this.x0 - var2, this.y1 - this.y0 - var3, var1);
      this.rY[2] = MyAPI.getRelativePointY(var5, this.x1 - this.x0 - var2, this.y1 - this.y0 - var3, var1);
      this.rX[3] = MyAPI.getRelativePointX(var4, -var2, this.y1 - this.y0 - var3, var1);
      this.rY[3] = MyAPI.getRelativePointY(var5, -var2, this.y1 - this.y0 - var3, var1);
   }

   public void setTwoPosition(int var1, int var2, int var3, int var4) {
      this.x0 = Math.min(var1, var3);
      this.x1 = Math.max(var1, var3);
      this.y0 = Math.min(var2, var4);
      this.y1 = Math.max(var2, var4);
      this.setRotate(this.degree, 0, 0);
   }

   public String toString() {
      return "x0:" + this.x0 + "|x1:" + this.x1 + "|y0:" + this.y0 + "|y1:" + this.y1;
   }
}
