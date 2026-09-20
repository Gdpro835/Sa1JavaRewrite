package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class DekaPlatform extends GimmickObject {
   private static final int VELOCITY = 250;
   private static MFImage image;
   private static MFImage image2;
   private int COLLISION_HEIGHT1;
   private int COLLISION_HEIGHT2;
   private int COLLISION_WIDTH1;
   private int COLLISION_WIDTH2;
   private int initPos;
   private boolean isActived = false;
   private boolean isDirectionDown;
   private boolean isH;
   private int lastDirection = 4;
   private MoveCalculator mCalc;
   private int offset_distance;

   protected DekaPlatform(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      boolean var8;
      if (this.mWidth >= this.mHeight) {
         this.isH = true;
         if (this.iLeft == 0) {
            var8 = false;
         } else {
            var8 = true;
         }
      } else {
         this.isH = false;
         if (this.iTop == 0) {
            var8 = false;
         } else {
            var8 = true;
         }
      }

      if (this.isH) {
         var1 = this.posX;
      } else {
         var1 = this.posY;
      }

      if (this.isH) {
         var2 = this.mWidth;
      } else {
         var2 = this.mHeight;
      }

      this.mCalc = new MoveCalculator(var1, var2, var8);
      if (this.isH) {
         var1 = this.posX;
      } else {
         var1 = this.posY;
      }

      this.initPos = var1;
      StringBuilder var9;
      if (StageManager.getCurrentZoneId() == 6) {
         if (this.iTop == 1) {
            if (image == null) {
               var9 = new StringBuilder("/gimmick/deka_platform_");
               var9 = var9.append(StageManager.getCurrentZoneId());
               var9 = var9.append(StageManager.getStageID() - 9).append("_2.png");
               image = MFImage.createImage(var9.toString());
            }

            this.COLLISION_WIDTH1 = MyAPI.zoomIn(image.getWidth()) << 6;
            this.COLLISION_HEIGHT1 = MyAPI.zoomIn(image.getHeight()) << 6;
         } else {
            if (image2 == null) {
               var9 = new StringBuilder("/gimmick/deka_platform_");
               var9 = var9.append(StageManager.getCurrentZoneId());
               var9 = var9.append(StageManager.getStageID() - 9);
               var9 = var9.append(".png");
               image2 = MFImage.createImage(var9.toString());
            }

            this.COLLISION_WIDTH2 = MyAPI.zoomIn(image2.getWidth()) << 6;
            this.COLLISION_HEIGHT2 = MyAPI.zoomIn(image2.getHeight()) << 6;
         }
      } else {
         if (image == null) {
            var9 = new StringBuilder("/gimmick/deka_platform_");
            var9 = var9.append(StageManager.getCurrentZoneId()).append(".png");
            image = MFImage.createImage(var9.toString());
         }

         this.COLLISION_WIDTH1 = MyAPI.zoomIn(image.getWidth()) << 6;
         this.COLLISION_HEIGHT1 = MyAPI.zoomIn(image.getHeight()) << 6;
      }

      if (StageManager.getCurrentZoneId() == 5 && this.iTop == 0 && this.iLeft == 0) {
         if (image2 == null) {
            try {
               var9 = new StringBuilder("/gimmick/deka_platform_");
               var9 = var9.append(StageManager.getCurrentZoneId());
               var9 = var9.append("_2.png");
               image2 = MFImage.createImage(var9.toString());
            } catch (Exception var10) {
               var10.printStackTrace();
            }
         }

         this.COLLISION_WIDTH1 = MyAPI.zoomIn(image2.getWidth()) << 6;
         this.COLLISION_HEIGHT1 = MyAPI.zoomIn(image2.getHeight()) << 6;
      }

   }

   public static void releaseAllResource() {
      image = null;
      image2 = null;
   }

   public void close() {
      this.mCalc = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var2 == 4) {
         var2 = this.lastDirection;
      } else {
         this.lastDirection = var2;
      }

      if (var2 == 1) {
         this.isDirectionDown = true;
      } else {
         this.isDirectionDown = false;
      }

      int var3;
      label76: {
         if (var1 instanceof PlayerAmy) {
            var3 = var2;
            if (((PlayerAmy)var1).skipBeStop) {
               break label76;
            }
         }

         var3 = var2;
         if (var2 == 4) {
            var3 = var2;
            if (var1.collisionRect.x0 < this.collisionRect.x1) {
               var3 = var2;
               if (var1.collisionRect.x1 > this.collisionRect.x1) {
                  var3 = 2;
               }
            }
         }

         if ((var1.velX >= 0 || var3 != 3) && (var1.velX <= 0 || var3 != 2) || var1.collisionRect.x1 + var1.velX + 1024 / 2 >= this.collisionRect.x0 && var1.collisionRect.x0 + var1.velX - 1024 / 2 <= this.collisionRect.x1) {
            var1.beStop(0, var3, this, this.isDirectionDown);
         } else {
            var1.posX += var1.velX;
         }
      }

      if (var3 == 3 || var3 == 2) {
         this.isActived = true;
         player.isSidePushed = var3;
         if (player.movedSpeedX > 0) {
            player.movedSpeedX = 1;
         } else if (player.movedSpeedX < 0) {
            player.movedSpeedX = -1;
         }
      }

   }

   public void doWhileNoCollision() {
      this.lastDirection = 4;
      if (this.isActived) {
         player.movedSpeedX = 0;
         this.isActived = false;
      }

      if (player.isSidePushed != 4) {
         player.isSidePushed = 4;
      }

   }

   public void draw(MFGraphics var1) {
      if (StageManager.getCurrentZoneId() == 5 && this.iTop == 0 && this.iLeft == 0) {
         this.drawInMap(var1, image2, 3);
      } else if (StageManager.getCurrentZoneId() == 6) {
         if (this.iTop == 1) {
            this.drawInMap(var1, image, 3);
         } else {
            this.drawInMap(var1, image2, 3);
         }
      } else {
         this.drawInMap(var1, image, 3);
      }

   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      this.mCalc.logic();
      int var1 = this.posX;
      int var2 = this.posY;
      if (this.isH) {
         if (this.iLeft == 0) {
            this.posX = this.mCalc.getPosition();
         } else {
            this.offset_distance = this.initPos - this.mCalc.getPosition();
            this.posX = this.initPos + this.offset_distance;
         }
      } else if (this.iTop == 0) {
         this.posY = this.mCalc.getPosition();
      } else {
         this.offset_distance = this.initPos - this.mCalc.getPosition();
         this.posY = this.initPos + this.offset_distance;
      }

      if (player.isFootOnObject(this) && this.worldInstance.getWorldY(player.collisionRect.x0, player.collisionRect.y0, 1, 0) != -1000 && this.worldInstance.getWorldY(player.collisionRect.x1, player.collisionRect.y0, 1, 0) != -1000) {
         player.setDie(false);
      } else {
         this.checkWithPlayer(var1, var2, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      int var3;
      int var4;
      int var5;
      int var6;
      CollisionRect var7;
      if (StageManager.getCurrentZoneId() == 6) {
         if (this.iTop == 1) {
            var7 = this.collisionRect;
            var6 = this.COLLISION_WIDTH1;
            var5 = this.COLLISION_HEIGHT1;
            var4 = this.COLLISION_WIDTH1;
            var3 = this.COLLISION_HEIGHT1;
            var7.setRect(var1 - (var6 >> 1), var2 - (var5 >> 1), var4, var3);
         } else {
            var7 = this.collisionRect;
            var4 = this.COLLISION_WIDTH2;
            var3 = this.COLLISION_HEIGHT2;
            var5 = this.COLLISION_WIDTH2;
            var6 = this.COLLISION_HEIGHT2;
            var7.setRect(var1 - (var4 >> 1), var2 - (var3 >> 1), var5, var6);
         }
      } else {
         var7 = this.collisionRect;
         var4 = this.COLLISION_WIDTH1;
         var5 = this.COLLISION_HEIGHT1;
         var3 = this.COLLISION_WIDTH1;
         var6 = this.COLLISION_HEIGHT1;
         var7.setRect(var1 - (var4 >> 1), var2 - (var5 >> 1), var3, var6);
      }

   }
}
