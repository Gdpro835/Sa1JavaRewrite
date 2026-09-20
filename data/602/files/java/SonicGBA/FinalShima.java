package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class FinalShima extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1536;
   private static final int COLLISION_WIDTH = 1024;
   private static final int DROP_STATE_READY = 1;
   private static final int DROP_STATE_START = 2;
   private static final int DROP_STATE_WAIT = 0;
   private static MFImage image;
   private long currentTime;
   private int dropState = 0;
   private long frameTime;
   private int posOriginalY;
   private long startTime;
   private int velY = 0;

   protected FinalShima(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (image == null) {
         image = MFImage.createImage("/gimmick/yuka_fi.png");
      }

      this.posOriginalY = this.posY;
      this.dropState = 0;
   }

   public static void releaseAllResource() {
      image = null;
   }

   private void resetShima() {
      this.posY = this.posOriginalY;
      this.velY = 0;
      this.dropState = 0;
      this.used = false;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      switch(var2) {
      case 1:
         var1.beStop(this.collisionRect.y0, 1, this);
         this.used = true;
      case 2:
      case 3:
      default:
         break;
      case 4:
         if (var1.getMoveDistance().y > 0 && var1.getCollisionRect().y1 < this.collisionRect.y1) {
            var1.beStop(this.collisionRect.y0, 1, this);
            this.used = true;
         }
      }

   }

   public void doWhileNoCollision() {
      if (player.collisionState == 1 || player.collisionState == 0) {
         this.used = false;
      }

   }

   public void draw(MFGraphics var1) {
      MFImage var4 = image;
      int var3 = this.posX;
      int var2 = this.posY;
      this.drawInMap(var1, var4, var3, var2 - 768, 17);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (this.dropState != 2) {
         if (this.used) {
            this.posY = MyAPI.calNextPosition((double)this.posY, (double)(this.posOriginalY + 768), 1, 6);
            if (this.posY > this.posOriginalY + 192 && this.dropState == 0) {
               this.dropState = 1;
               this.startTime = System.currentTimeMillis();
            }

            if (this.dropState == 1) {
               this.dropState = 2;
            }
         } else {
            int var2 = this.posY;
            int var3 = this.posOriginalY;
            int var1 = this.posOriginalY;
            this.posY = MyAPI.calNextPositionReverse(var2, var3 + 768, var1, 1, 6);
         }
      } else {
         this.currentTime = System.currentTimeMillis();
         if (this.currentTime - this.startTime >= 1000L) {
            this.velY += GRAVITY;
            this.posY += this.velY;
         }
      }

      if (this.isAwayFromCameraInWidth()) {
         this.resetShima();
      }

      this.refreshCollisionRect(this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 512, var2 - 768, 1024, 1536);
   }
}
