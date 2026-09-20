package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Boss6Block extends GimmickObject {
   public static final int COLLISION2_HEIGHT = 1664;
   public static final int COLLISION_HEIGHT = 1536;
   public static final int COLLISION_WIDTH = 2176;
   private static MFImage blockImage;
   private boolean IsDisplay;

   protected Boss6Block(int var1, int var2) {
      super(122, var1, var2, 0, 0, 0, 0);
      if (blockImage == null) {
         blockImage = MFImage.createImage("/gimmick/boss6_block.png");
      }

      this.IsDisplay = true;
      this.used = false;
      this.posX = var1;
      this.posY = var2;
   }

   public static void releaseAllResource() {
      blockImage = null;
   }

   public boolean collisionChkWithObject(PlayerObject var1) {
      CollisionRect var8 = var1.getCollisionRect();
      CollisionRect var6 = this.getCollisionRect();
      CollisionRect var7 = rectV;
      int var4 = var8.x0;
      int var2 = var8.y0;
      int var3 = var8.getWidth();
      int var5 = var8.getHeight();
      var7.setRect(var4 + 192, var2, var3 - 384, var5);
      return var6.collisionChk(rectV);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.IsDisplay) {
         switch(var2) {
         case 1:
            var1.beStop(this.collisionRect.y0, 1, this);
            this.used = true;
            break;
         case 2:
         case 3:
         default:
            var1.beStop(this.collisionRect.y0, 1, this);
            this.used = true;
            break;
         case 4:
            var1.beStop(this.collisionRect.y0, 1, this);
            this.used = true;
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (this.IsDisplay) {
         this.drawInMap(var1, blockImage, this.posX, this.posY, 3);
         this.drawCollisionRect(var1);
      }

   }

   public boolean getUsedState() {
      return this.used;
   }

   public void logic(int var1, int var2) {
      if (this.IsDisplay) {
         this.posX = var1;
         this.posY = var2;
         var2 = this.posX;
         var1 = this.posY;
         this.checkWithPlayer(var2, var1, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      if (this.IsDisplay) {
         CollisionRect var3 = this.collisionRect;
         var3.setRect(var1 - 1088, var2 - 768, 2176, 1536);
      }

   }

   public void setDisplayState(boolean var1) {
      this.IsDisplay = var1;
   }

   public void setUsedState(boolean var1) {
      this.used = var1;
   }
}
