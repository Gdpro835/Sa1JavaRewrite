package SonicGBA;

import GameEngine.time.GameTime;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class RollPlatformSpeedA extends GimmickObject {
   public static final int COLLISION_HEIGHT = 960;
   public static final int COLLISION_OFFSET_Y = 256;
   public static final int COLLISION_WIDTH = 3072;
   public static final int DEGREE_VELOCITY = 230;
   public static final int DRAW_OFFSET_Y = 768;
   private static final int RING_RANGE = 16;
   public static int degree;
   private int centerX;
   private int centerY;
   private boolean direction;
   private int offsetY2;
   private int radius;
   private int ring1PosX;
   private int ring1PosY;
   private int ring2PosX;
   private int ring2PosY;

   protected RollPlatformSpeedA(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (this.mWidth > this.mHeight) {
         this.radius = this.mWidth;
         this.direction = false;
      } else {
         this.radius = this.mHeight;
         this.direction = true;
      }

      this.centerX = this.posX;
      this.centerY = this.posY;
      if (rolllinkImage == null) {
         rolllinkImage = MFImage.createImage("/gimmick/roll_ring.png");
      }

      if (platformImage == null) {
         try {
            StringBuilder var8;
            if (StageManager.getCurrentZoneId() != 6) {
               var8 = new StringBuilder("/gimmick/platform");
               var8 = var8.append(StageManager.getCurrentZoneId()).append(".png");
               platformImage = MFImage.createImage(var8.toString());
            } else {
               var8 = new StringBuilder("/gimmick/platform");
               var8 = var8.append(StageManager.getCurrentZoneId()).append(StageManager.getStageID() - 9).append(".png");
               platformImage = MFImage.createImage(var8.toString());
            }
         } catch (Exception var10) {
            var10.printStackTrace();

            try {
               platformImage = MFImage.createImage("/gimmick/platform0.png");
            } catch (Exception var9) {
               var9.printStackTrace();
            }
         }
      }

   }

   public static void staticLogic() {
      degree = GameTime.advance(RollPlatformSpeedA.class, "degree", degree, 230);
      degree = GameTime.wrap(RollPlatformSpeedA.class, "degree", degree, 23040);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!player.isFootOnObject(this)) {
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

   }

   public void draw(MFGraphics var1) {
      if (StageManager.getCurrentZoneId() == 6 || StageManager.getCurrentZoneId() == 4) {
         MFImage var4 = rolllinkImage;
         int var2 = this.centerX;
         int var3 = this.centerY;
         this.drawInMap(var1, var4, 0, 0, 16, 16, 0, var2, var3, 3);
         var4 = rolllinkImage;
         var3 = this.ring1PosX;
         var2 = this.ring1PosY;
         this.drawInMap(var1, var4, 16, 0, 16, 16, 0, var3, var2, 3);
         var4 = rolllinkImage;
         var3 = this.ring2PosX;
         var2 = this.ring2PosY;
         this.drawInMap(var1, var4, 16, 0, 16, 16, 0, var3, var2, 3);
      }

      this.drawInMap(var1, platformImage, this.posX, this.posY, 3);
      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (StageManager.getCurrentZoneId() >= 3 && StageManager.getCurrentZoneId() <= 6) {
         this.offsetY2 = -256;
      }

      int var2 = degree;
      int var1 = this.iLeft * 23040 / 16;
      int var3 = this.posX;
      int var4 = this.posY;
      var2 = (var2 + var1 + 23040) % 23040;
      var1 = var2;
      if (!this.direction) {
         var1 = -var2;
         var1 = (var1 + 23040) % 23040;
      }

      this.posX = (this.centerX * 100 + this.radius * MyAPI.dCos(var1 >> 6)) / 100;
      this.posY = (this.centerY * 100 + this.radius * MyAPI.dSin(var1 >> 6)) / 100;
      this.ring1PosX = (this.centerX * 100 + this.radius / 3 * MyAPI.dCos(var1 >> 6)) / 100;
      this.ring1PosY = (this.centerY * 100 + this.radius / 3 * MyAPI.dSin(var1 >> 6)) / 100;
      this.ring2PosX = (this.centerX * 100 + this.radius * 2 / 3 * MyAPI.dCos(var1 >> 6)) / 100;
      this.ring2PosY = (this.centerY * 100 + this.radius * 2 / 3 * MyAPI.dSin(var1 >> 6)) / 100;
      this.checkWithPlayer(var3, var4, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var4 = this.collisionRect;
      int var3 = this.offsetY2;
      var4.setRect(var1 - 1536, var2 + 256 - 768 + var3, 3072, 960);
   }
}
