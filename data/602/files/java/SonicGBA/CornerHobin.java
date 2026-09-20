package SonicGBA;

import Lib.Line;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class CornerHobin extends GimmickObject {
   private static final int COLLISION_HEIGHT = 2816;
   private static final int COLLISION_WIDTH = 2816;
   private static final int POP_POWER = 1152;
   private static MFImage barCornerImage;
   private Line checkLine;
   private int funcDirectionH;
   private int funcDirectionV;
   private HobinCal hobinCal;
   private int hobinDegree;
   private int trans;

   protected CornerHobin(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (barCornerImage == null) {
         try {
            barCornerImage = MFImage.createImage("/gimmick/barCorner.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      this.hobinDegree = -45;
      this.trans = 0;
      this.funcDirectionH = 2;
      if (this.iLeft == 0) {
         this.funcDirectionH = 3;
         this.trans |= 2;
         this.hobinDegree = 180 - this.hobinDegree;
      }

      this.funcDirectionV = 1;
      if (this.iTop == 0) {
         this.funcDirectionV = 0;
         this.trans |= 1;
         this.hobinDegree = -this.hobinDegree;
      }

      this.hobinCal = new HobinCal();
   }

   public static void releaseAllResource() {
      barCornerImage = null;
   }

   public void close() {
      this.checkLine = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var2 == 4 && var1 == player) {
         boolean var3 = false;
         if (this.funcDirectionV == 1) {
            if (this.checkLine.getY(player.getFootPositionX()) < player.getFootPositionY()) {
               player.setFootPositionY(this.checkLine.getY(player.getFootPositionX()));
               var3 = true;
            }
         } else if (this.checkLine.getY(player.getFootPositionX()) > player.getHeadPositionY()) {
            player.setHeadPositionY(this.checkLine.getY(player.getFootPositionX()));
            var3 = true;
         }

         if (var3) {
            player.bePop(1152, this.funcDirectionV);
            player.bePop(1152, this.funcDirectionH);
            player.dashRolling = false;
            this.hobinCal.startHobin(0, this.hobinDegree + 180, 10);
            soundInstance.playSe(24);
         }
      }

   }

   public void draw(MFGraphics var1) {
      MFImage var11 = barCornerImage;
      int var7 = MyAPI.zoomIn(barCornerImage.getWidth());
      int var4 = MyAPI.zoomIn(barCornerImage.getHeight());
      int var9 = this.trans;
      int var5 = this.posX;
      int var10 = this.hobinCal.getPosOffsetX();
      int var8 = this.posY;
      int var6 = this.hobinCal.getPosOffsetY();
      byte var2;
      if (this.funcDirectionH == 2) {
         var2 = 4;
      } else {
         var2 = 8;
      }

      byte var3;
      if (this.funcDirectionV == 1) {
         var3 = 32;
      } else {
         var3 = 16;
      }

      this.drawInMap(var1, var11, 0, 0, var7, var4, var9, var5 + var10, var8 + var6, var2 | var3);
      this.hobinCal.logic();
   }

   public int getPaintLayer() {
      return 0;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var5 = this.collisionRect;
      short var3;
      if (this.funcDirectionH == 3) {
         var3 = 2816;
      } else {
         var3 = 0;
      }

      short var4;
      if (this.funcDirectionV == 1) {
         var4 = 2816;
      } else {
         var4 = 0;
      }

      var5.setRect(var1 - var3, var2 - var4, 2816, 2816);
      if (this.checkLine == null) {
         this.checkLine = new Line();
      }

      int var6;
      int var7;
      Line var8;
      if ((this.funcDirectionH != 2 || this.funcDirectionV != 1) && (this.funcDirectionH != 3 || this.funcDirectionV != 0)) {
         var8 = this.checkLine;
         var1 = this.collisionRect.x0;
         var2 = this.collisionRect.y1;
         var6 = this.collisionRect.x1;
         var7 = this.collisionRect.y0;
         var8.setProperty(var1, var2, var6, var7);
      } else {
         var8 = this.checkLine;
         var6 = this.collisionRect.x0;
         var2 = this.collisionRect.y0;
         var1 = this.collisionRect.x1;
         var7 = this.collisionRect.y1;
         var8.setProperty(var6, var2, var1, var7);
      }

   }
}
