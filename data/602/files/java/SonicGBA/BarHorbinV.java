package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class BarHorbinV extends GimmickObject {
   public static final int COLLISION_HEIGHT = 704;
   public static final int COLLISION_OFFSET = 512;
   public static final int COLLISION_WIDTH = 2560;
   public static final int HOBIN_POWER = 1152;
   public static MFImage barImage;
   private int functionDirection;
   public HobinCal hobinCal;

   protected BarHorbinV(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (barImage == null) {
         try {
            barImage = MFImage.createImage("/gimmick/bar.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      this.functionDirection = 2;
      if (this.iLeft == 0) {
         this.functionDirection = 3;
      }

      this.hobinCal = new HobinCal();
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
            player.bePop(1152, var2);
            player.bePop(1152, this.functionDirection);
            if (this.functionDirection == 2) {
               this.hobinCal.startHobin(0, 225, 10);
            } else {
               this.hobinCal.startHobin(0, -45, 10);
            }
            break;
         case 1:
            player.bePop(1152, var2);
            player.bePop(1152, this.functionDirection);
            if (this.functionDirection == 2) {
               this.hobinCal.startHobin(0, 135, 10);
            } else {
               this.hobinCal.startHobin(0, 45, 10);
            }
            break;
         case 2:
         case 3:
            if ((this.iLeft == 0 || var2 != 3) && (this.iLeft != 0 || var2 != 2)) {
               if (player.getCheckPositionY() <= this.collisionRect.getCenterY()) {
                  player.bePop(1152, 1);
                  if (this.functionDirection == 2) {
                     this.hobinCal.startHobin(0, 135, 10);
                  } else {
                     this.hobinCal.startHobin(0, 45, 10);
                  }
               } else {
                  player.bePop(1152, 0);
                  if (this.functionDirection == 2) {
                     this.hobinCal.startHobin(0, 225, 10);
                  } else {
                     this.hobinCal.startHobin(0, -45, 10);
                  }
               }

               player.bePop(1152, var2);
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
         var2 = MyAPI.zoomIn(barImage.getWidth());
         var4 = MyAPI.zoomIn(barImage.getHeight());
         var7 = this.posX;
         var3 = this.hobinCal.getPosOffsetX();
         var6 = this.posY;
         var5 = this.hobinCal.getPosOffsetY();
         this.drawInMap(var1, var8, 0, 0, var2, var4, 6, var7 + var3, var6 + var5, 3);
      } else {
         var8 = barImage;
         var3 = MyAPI.zoomIn(barImage.getWidth());
         var5 = MyAPI.zoomIn(barImage.getHeight());
         var4 = this.posX;
         var7 = this.hobinCal.getPosOffsetX();
         var6 = this.posY;
         var2 = this.hobinCal.getPosOffsetY();
         this.drawInMap(var1, var8, 0, 0, var3, var5, 5, var4 + var7, var6 + var2, 3);
      }

      this.hobinCal.logic();
   }

   public int getPaintLayer() {
      return 0;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 512, var2 - 1280, 704, 2560);
   }
}
