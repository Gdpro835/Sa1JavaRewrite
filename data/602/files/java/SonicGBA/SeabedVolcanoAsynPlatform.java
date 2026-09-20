package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class SeabedVolcanoAsynPlatform extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1536;
   private static final int COLLISION_OFFSET_Y = 0;
   private static final int COLLISION_WIDTH = 3072;
   private static final int LAUNCH_VEL = -1400;
   private static int endCount;
   private static MFImage image;
   private static boolean moving;
   public static int sPosY;
   private static int velocity;
   private int posOriginalY;
   private SeabedVolcanoAsynBase sb;

   protected SeabedVolcanoAsynPlatform(int var1, int var2, SeabedVolcanoAsynBase var3) {
      super(0, var1, var2, 0, 0, 0, 0);
      this.posOriginalY = this.posY;
      if (image == null) {
         image = MFImage.createImage("/gimmick/platform4_fire_mt.png");
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
      velocity = -1400;
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
               velocity = -velocity / 2;
            }
         }
      }

   }

   public void close() {
      this.sb = null;
   }

   public boolean collisionChkWithObject(PlayerObject var1) {
      CollisionRect var8 = var1.getCollisionRect();
      CollisionRect var7 = this.getCollisionRect();
      CollisionRect var6 = rectV;
      int var4 = var8.x0;
      int var2 = var8.y0;
      int var5 = var8.getWidth();
      int var3 = var8.getHeight();
      var6.setRect(var4 + 192, var2, var5 - 384, var3);
      return var7.collisionChk(rectV);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var2 == 1 || var2 == 4 && var1.getVelY() > 0) {
         var1.beStop(0, 1, this);
      }

      if (var2 == 4 && player instanceof PlayerKnuckles && player.myAnimationID == 33) {
         var1 = player;
         var1.posY -= 268;
      }

   }

   public void drawPlatform(MFGraphics var1) {
      MFImage var4 = image;
      int var3 = this.posX;
      int var2 = this.posY;
      this.drawInMap(var1, var4, var3, var2 + 768, 33);
      this.drawCollisionRect(var1);
   }

   public void logic() {
      if (player.isFootOnObject(this) && player instanceof PlayerKnuckles) {
         ((PlayerKnuckles)player).setFloating(false);
      }

      int var1 = this.posY;
      this.posY = this.posOriginalY + sPosY;
      this.checkWithPlayer(this.posX, var1, this.posX, this.posY);
      this.sb.sh.refreshCollisionRect(this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1536, var2 - 768 - 0, 3072, 1536);
   }
}
