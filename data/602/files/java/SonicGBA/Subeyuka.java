package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Subeyuka extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1920;
   private static final int COLLISION_WIDTH = 3072;
   private static final int DRAW_OFFSET_Y = 64;
   private static int frame;
   private static MFImage image;
   private boolean dead;
   private int deadCount;
   private int flyCount = 0;
   private MapObject mapObj;
   private boolean moving;
   private int startPosX;
   private int startPosY;
   private int tmpSpeedX = 0;
   private int tmpX = 0;
   private int tmpY = 0;

   protected Subeyuka(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (image == null) {
         if (StageManager.getCurrentZoneId() != 4) {
            image = MFImage.createImage("/gimmick/subeyuka_5.png");
         } else {
            image = MFImage.createImage("/gimmick/subeyuka_4.png");
         }
      }

      this.startPosX = this.posX;
      this.startPosY = this.posY;
      this.moving = false;
      this.mapObj = new MapObject(this.startPosX, this.startPosY, 0, 0, this, this.iLeft);
      this.flyCount = 0;
   }

   public static void releaseAllResource() {
      image = null;
   }

   public boolean checkInit() {
      return false;
   }

   public void close() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead) {
         switch(var2) {
         case 1:
            if (!this.moving) {
               this.moving = true;
               this.mapObj.setPosition(this.posX, this.posY, 0, 0, this);
               if (StageManager.getCurrentZoneId() != 4) {
                  this.mapObj.setCrashCount(2);
               } else {
                  this.mapObj.setCrashCount(2);
               }

               if (StageManager.getCurrentZoneId() != 4 && StageManager.getCurrentZoneId() != 5) {
                  soundInstance.playSe(64);
               }

               isGotRings = false;
               frame = 1;
            }

            var1.beStop(0, var2, this);
         case 2:
         case 3:
         default:
            break;
         case 4:
            if (var1.getMoveDistance().y > 0 && var1.getCollisionRect().y1 < this.collisionRect.y1) {
               if (!this.moving) {
                  this.moving = true;
                  this.mapObj.setPosition(this.posX, this.posY, 0, 0, this);
                  this.mapObj.setCrashCount(2);
                  if (StageManager.getCurrentZoneId() != 4 && StageManager.getCurrentZoneId() != 5) {
                     soundInstance.playSe(64);
                  }

                  isGotRings = false;
                  frame = 1;
               }

               var1.beStop(0, 1, this);
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, image, this.posX, this.posY + 64, 33);
         this.drawCollisionRect(var1);
      }

   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (this.moving && !this.dead) {
         if ((StageManager.getCurrentZoneId() == 4 || StageManager.getCurrentZoneId() == 5) && this.iLeft == 1 && this.mapObj.getCurrentCrashCount() < 2) {
            int var1 = this.flyCount + 1;
            this.flyCount = var1;
            if (var1 < 4) {
               if (this.flyCount == 2) {
                  soundInstance.playSe(64);
               }

               if (StageManager.getCurrentZoneId() != 5) {
                  this.velX = this.flyCount * 6 << 6;
                  this.mapObj.setVel(this.velX, this.velY);
               }
            }
         }

         if (frame == 4 && StageManager.getCurrentZoneId() != 4 && StageManager.getCurrentZoneId() != 5) {
            soundInstance.playLoopSe(65);
         }

         ++frame;
         this.mapObj.logic();
         this.posX = this.mapObj.getPosX();
         this.posY = this.mapObj.getPosY();
         if (this.mapObj.getCurrentCrashCount() != 0) {
            this.tmpSpeedX = this.mapObj.getVelX();
            this.checkWithPlayer(this.posX, this.posY, this.mapObj.getPosX(), this.mapObj.getPosY());
            this.tmpX = this.mapObj.getPosX();
            this.tmpY = this.mapObj.getPosY();
         } else {
            this.mapObj.setPosition(this.tmpX, this.tmpY);
            this.posX = this.tmpX;
            this.posY = this.tmpY;
         }

         System.out.println("mapObj.getCurrentCrashCount()=" + this.mapObj.getCurrentCrashCount());
         if (this.mapObj.chkCrash()) {
            this.dead = true;
            if (player.isFootOnObject(this)) {
               player.setVelX(this.tmpSpeedX);
               player.doJump();
            }

            if (StageManager.getCurrentZoneId() == 4) {
               Effect.showEffect(iceBreakAnimation, 0, this.posX >> 6, this.posY >> 6, 0);
            } else if (StageManager.getCurrentZoneId() == 5) {
               Effect.showEffect(platformBreakAnimation, 0, this.posX >> 6, this.posY >> 6, 0);
            } else {
               Effect.showEffect(rockBreakAnimation, 0, this.posX >> 6, this.posY >> 6, 0);
            }

            soundInstance.playSe(34);
         }
      }

      if (StageManager.getCurrentZoneId() != 4 && this.dead && soundInstance.getPlayingLoopSeIndex() == 65) {
         soundInstance.stopLoopSe();
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1536, var2 - 1920, 3072, 1920);
   }
}
