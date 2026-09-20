package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class SteamPlatform extends GimmickObject {
   private static final int COLLISION_HEIGHT = 448;
   private static final int COLLISION_WIDTH = 1280;
   private static int endCount;
   private static MFImage image;
   private static boolean moving;
   public static int sPosY;
   private static int velocity;
   private int originalPosY;
   private SteamBase sb;

   protected SteamPlatform(int var1, int var2, SteamBase var3) {
      super(0, var1, var2, 0, 0, 0, 0);
      this.originalPosY = this.posY;
      if (image == null) {
         try {
            image = MFImage.createImage("/gimmick/steam_platform.png");
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

      this.sb = var3;
   }

   public static boolean isShotting() {
      boolean var0;
      if (moving && endCount == 3) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static void releaseAllResource() {
      image = null;
   }

   public static void shot() {
      moving = true;
      velocity = -1450;
      endCount = 3;
   }

   public static void staticLogic() {
      if (moving) {
         velocity += GRAVITY;
         sPosY += velocity;
         if (sPosY >= 0) {
            sPosY = 0;
            --endCount;
            if (endCount <= 0) {
               moving = false;
            } else {
               velocity = -velocity / 3;
            }
         }
      }

   }

   public void close() {
      this.sb = null;
   }

   public boolean collisionChkWithObject(PlayerObject var1) {
      CollisionRect var7 = var1.getCollisionRect();
      CollisionRect var8 = this.getCollisionRect();
      CollisionRect var6 = rectV;
      int var2 = var7.x0;
      int var3 = var7.y0;
      int var4 = var7.getWidth();
      int var5 = var7.getHeight();
      var6.setRect(var2 + 192, var3, var4 - 384, var5);
      return var8.collisionChk(rectV);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var2 == 1 || var2 == 4) {
         var1.beStop(0, 1, this);
      }

   }

   public void drawPlatform(MFGraphics var1) {
      this.drawInMap(var1, image, 33);
   }

   public void logic() {
      int var1 = this.posY;
      this.posY = this.originalPosY + sPosY;
      this.checkWithPlayer(this.posX, var1, this.posX, this.posY);
      this.sb.sh.refreshCollisionRect(this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 640, var2 - 448, 1280, 448);
   }
}
