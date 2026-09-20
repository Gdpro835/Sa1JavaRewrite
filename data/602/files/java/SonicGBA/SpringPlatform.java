package SonicGBA;

import GameEngine.time.GameTime;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class SpringPlatform extends GimmickObject {
   private static final int COLLISION_HEIGHT = 2304;
   private static final int COLLISION_WIDTH = 2048;
   private static final int FAR_DISTANCE = 3840;
   private static final int MOST_ACCELATE = 150;
   private static MFImage image;
   private int centerY;
   private int velY;

   protected SpringPlatform(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (image == null) {
         try {
            image = MFImage.createImage("/gimmick/spring_platform.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      this.centerY = this.posY;
      this.velY = 0;
   }

   public static void releaseAllResource() {
      image = null;
   }

   public boolean collisionChkWithObject(PlayerObject var1) {
      CollisionRect var8 = var1.getCollisionRect();
      CollisionRect var7 = this.getCollisionRect();
      CollisionRect var6 = rectV;
      int var3 = var8.x0;
      int var4 = var8.y0;
      int var5 = var8.getWidth();
      int var2 = var8.getHeight();
      var6.setRect(var3 + 192, var4, var5 - 384, var2);
      return var7.collisionChk(rectV);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      switch(var2) {
      case 1:
         if (this.firstTouch) {
            this.velY += var1.getVelY();
         }

         var1.beStop(0, var2, this);
      case 2:
      case 3:
      default:
         break;
      case 4:
         if (var1.getVelY() > 0 && var1.getCollisionRect().y1 < this.collisionRect.y1) {
            var1.beStop(0, 1, this);
         }
      }

      if (player.isFootOnObject(this)) {
         player.setSqueezeEnable(false);
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, image, 3);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      int var1 = (this.centerY - this.posY) * 150 / 3840;
      int var3 = this.posX;
      int var2 = this.posY;
      this.velY = GameTime.advance(this, "velY", this.velY, var1);
      if (this.velY > 0) {
         this.velY = GameTime.advance(this, "velY", this.velY, -(10));
      } else if (this.velY < 0) {
         this.velY = GameTime.advance(this, "velY", this.velY, 10);
      }

      this.posY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
      if (this.posY > this.centerY + 3840) {
         this.posY = this.centerY + 3840;
         this.velY = 0;
      }

      if (this.posY < this.centerY - 3840) {
         this.posY = this.centerY - 3840;
         this.velY = 0;
      }

      this.checkWithPlayer(var3, var2, this.posX, this.posY);
      if (!player.isFootOnObject(this)) {
         player.setSqueezeEnable(true);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1024, var2 - 1152, 2048, 2304);
   }
}
