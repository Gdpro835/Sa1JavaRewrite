package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BarHorbinH extends BarHorbinV {
   private int functionDirection;

   protected BarHorbinH(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (barImage == null) {
         try {
            barImage = MFImage.createImage("/gimmick/bar.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      this.functionDirection = 1;
      if (this.iLeft == 0) {
         this.functionDirection = 0;
      }

   }

   public static void releaseAllResource() {
      barImage = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1 == player) {
         player.beStop(0, var2, this);
         soundInstance.playSe(24);
         switch(var2) {
         case 0:
         case 1:
            if ((this.iLeft == 0 || var2 != 0) && (this.iLeft != 0 || var2 != 1)) {
               if (player.getCheckPositionX() <= this.collisionRect.getCenterX()) {
                  player.bePop(1152, 3);
                  if (this.functionDirection == 1) {
                     this.hobinCal.startHobin(0, 45, 10);
                  } else {
                     this.hobinCal.startHobin(0, -45, 10);
                  }
               } else {
                  player.bePop(1152, 2);
                  if (this.functionDirection == 1) {
                     this.hobinCal.startHobin(0, 135, 10);
                  } else {
                     this.hobinCal.startHobin(0, 225, 10);
                  }
               }

               player.bePop(1152, var2);
            }
            break;
         case 2:
            player.bePop(1152, var2);
            player.bePop(1152, this.functionDirection);
            if (this.functionDirection == 1) {
               this.hobinCal.startHobin(0, 135, 10);
            } else {
               this.hobinCal.startHobin(0, 225, 10);
            }
            break;
         case 3:
            player.bePop(1152, var2);
            player.bePop(1152, this.functionDirection);
            if (this.functionDirection == 1) {
               this.hobinCal.startHobin(0, 45, 10);
            } else {
               this.hobinCal.startHobin(0, -45, 10);
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      int var2;
      int var3;
      int var4;
      int var5;
      int var6;
      int var7;
      MFImage var8;
      if (this.iLeft == 0) {
         var8 = barImage;
         var3 = MyAPI.zoomIn(barImage.getWidth());
         var5 = MyAPI.zoomIn(barImage.getHeight());
         var7 = this.posX;
         var2 = this.hobinCal.getPosOffsetX();
         var4 = this.posY;
         var6 = this.hobinCal.getPosOffsetY();
         this.drawInMap(var1, var8, 0, 0, var3, var5, 3, var7 + var2, var4 + var6, 3);
      } else {
         var8 = barImage;
         var4 = MyAPI.zoomIn(barImage.getWidth());
         var2 = MyAPI.zoomIn(barImage.getHeight());
         var5 = this.posX;
         var7 = this.hobinCal.getPosOffsetX();
         var6 = this.posY;
         var3 = this.hobinCal.getPosOffsetY();
         this.drawInMap(var1, var8, 0, 0, var4, var2, 0, var5 + var7, var6 + var3, 3);
      }

      this.hobinCal.logic();
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1280, var2 + 512 - 704, 2560, 704);
   }
}
