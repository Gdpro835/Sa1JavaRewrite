package SonicGBA;

import Lib.MyAPI;
import Lib.SoundSystem;
import Lib.crlFP32;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class RopeStart extends GimmickObject {
   private static final int DEGREE = crlFP32.actTanDegree(1, 2);
   private static final int DRAW_HEIGHT = 24;
   private static final int DRAW_WIDTH = 16;
   private static final int MAX_VELOCITY = 1800;
   private static MFImage hookImage2;
   private boolean controlling;
   public int degree;
   private boolean initFlag = false;
   private int posOriginalX;
   private int posOriginalY;
   private int velocity;

   protected RopeStart(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (StageManager.getCurrentZoneId() == 4) {
         if (hookImage2 == null) {
            try {
               hookImage2 = MFImage.createImage("/gimmick/hook_4.png");
            } catch (Exception var10) {
               var10.printStackTrace();
            }
         }
      } else if (hookImage == null) {
         try {
            hookImage = MFImage.createImage("/gimmick/hook.png");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }

      this.used = false;
      this.controlling = false;
      this.initFlag = false;
      if (this.iLeft == 0) {
         this.degree = 180 - DEGREE;
      } else {
         this.degree = DEGREE;
      }

      this.posOriginalX = this.posX;
      this.posOriginalY = this.posY;
   }

   public static void releaseAllResource() {
      hookImage2 = null;
   }

   public void doInitWhileInCamera() {
      this.posX = this.posOriginalX;
      this.posY = this.posOriginalY;
      this.used = false;
      this.controlling = false;
      if (this.iLeft == 0) {
         this.degree = 180 - DEGREE;
      } else {
         this.degree = DEGREE;
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.initFlag) {
         if (!this.used) {
            isGotRings = false;
         }

         if (!this.used) {
            this.velocity = Math.abs(var1.getVelX());
            this.used = true;
            this.controlling = true;
            var1.setOutOfControl(this);
            var1.railing = true;
            var1.setCollisionState((byte)1);
            var1.faceDirection = true;
            player.doPullMotion(this.posX, this.posY + 1408);
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.initFlag) {
         int var2;
         int var3;
         MFImage var4;
         if (StageManager.getCurrentZoneId() == 4) {
            var4 = hookImage2;
            var3 = this.posX;
            var2 = this.posY;
            this.drawInMap(var1, var4, 0, 0, 16, 24, 0, var3, var2, 17);
         } else {
            var4 = hookImage;
            var3 = this.posX;
            var2 = this.posY;
            this.drawInMap(var1, var4, 0, 0, 16, 24, 0, var3, var2, 17);
         }
      }

   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (this.initFlag) {
         this.refreshCollisionRect(this.posX, this.posY);
         if (!screenRect.collisionChk(this.collisionRect)) {
            this.initFlag = false;
         }
      } else if (player.outOfControl && player.outOfControlObject == this) {
         this.velocity += GRAVITY * MyAPI.dSin(this.degree) / 100;
         if (this.velocity > 0) {
            this.velocity -= 30;
            if (this.velocity < 0) {
               this.velocity = 0;
            }
         }

         if (this.velocity < 0) {
            this.velocity += 30;
            if (this.velocity > 0) {
               this.velocity = 0;
            }
         }

         this.posX += this.velocity * MyAPI.dCos(this.degree) / 100;
         this.posY += this.velocity * MyAPI.dSin(this.degree) / 100;
         this.refreshCollisionRect(this.posX, this.posY);
         if (player.outOfControl) {
            int var3 = player.getFootPositionX();
            int var4 = player.getFootPositionY();
            player.doPullMotion(this.posX, this.posY + 1408);
            player.setVelX(this.velocity * MyAPI.dCos(this.degree) / 100);
            player.setVelY(this.velocity * MyAPI.dSin(this.degree) / 100);
            PlayerObject var5 = player;
            int var2 = player.getFootPositionX();
            int var1 = player.getFootPositionY();
            var5.checkWithObject(var3, var4, var2, var1);
            if (!isGotRings) {
               SoundSystem.getInstance().playSequenceSe(50);
            }
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1, var2, 1024, 2560);
   }

   public void turn() {
      this.degree = 180 - this.degree;
      this.velocity = -this.velocity;
      int var1 = Math.abs(this.velocity);
      if (var1 > 1800) {
         if (this.velocity < 0) {
            this.velocity = -1800;
         } else {
            this.velocity = 1800;
         }
      }

   }
}
