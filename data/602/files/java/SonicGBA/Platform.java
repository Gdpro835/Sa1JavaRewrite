package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Platform extends GimmickObject {
   public static int COLLISION_HEIGHT = 1280;
   public static int COLLISION_OFFSET_Y = 256;
   public static final int COLLISION_WIDTH = 3072;
   public static final int DRAW_OFFSET_Y = 768;
   public static final int STAND_OFFSET = 192;
   public int COLLISION_HEIGHT_OFFSET = 0;
   public boolean IsDisplay = true;
   private int initPosx;
   private int initPosy;
   public boolean isH;
   public MoveCalculator moveCal;
   public int offsetY = 0;
   public int offsetY2 = 0;

   protected Platform(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (platformImage == null) {
         try {
            StringBuilder var9;
            if (StageManager.getCurrentZoneId() != 6) {
               var9 = new StringBuilder("/gimmick/platform");
               var9 = var9.append(StageManager.getCurrentZoneId()).append(".png");
               platformImage = MFImage.createImage(var9.toString());
            } else {
               var9 = new StringBuilder("/gimmick/platform");
               var9 = var9.append(StageManager.getCurrentZoneId());
               var9 = var9.append(StageManager.getStageID() - 9).append(".png");
               platformImage = MFImage.createImage(var9.toString());
            }
         } catch (Exception var11) {
            var11.printStackTrace();

            try {
               platformImage = MFImage.createImage("/gimmick/platform0.png");
            } catch (Exception var10) {
               var10.printStackTrace();
            }
         }
      }

      if (StageManager.getStageID() == 5 && var4 == 5312) {
         this.COLLISION_HEIGHT_OFFSET = -768;
      }

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

      this.moveCal = new MoveCalculator(var1, var2, var8);
      this.initPosx = this.posX;
      this.initPosy = this.posY;
      this.IsDisplay = true;
   }

   public boolean collisionChkWithObject(PlayerObject var1) {
      CollisionRect var8 = var1.getCollisionRect();
      CollisionRect var9 = this.getCollisionRect();
      CollisionRect var7 = rectV;
      int var4 = var8.x0;
      int var2 = var8.y0;
      int var5 = this.offsetY2;
      int var3 = var8.getWidth();
      int var6 = var8.getHeight();
      var7.setRect(var4 + 192, var2 + var5, var3 - 384, var6);
      return var9.collisionChk(rectV);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.IsDisplay && !player.isFootOnObject(this)) {
         switch(var2) {
         case 0:
            if (player.isAntiGravity) {
               var1.beStop(this.collisionRect.y0, 0, this);
               this.used = true;
            }
            break;
         case 1:
            if (!player.isAntiGravity) {
               var1.beStop(this.collisionRect.y0, 1, this);
               this.used = true;
            }
         case 2:
         case 3:
         default:
            break;
         case 4:
            if (!player.isAntiGravity && var1.getMoveDistance().y > 0) {
               if ((var1.getCollisionRect().y0 + var1.getCollisionRect().y1) / 2 - 640 < this.collisionRect.y1) {
                  var1.beStop(this.collisionRect.y0, 1, this);
                  this.used = true;
               }
            } else if (player.isAntiGravity && var1.getMoveDistance().y < 0 && var1.getCollisionRect().y0 > this.collisionRect.y0) {
               var1.beStop(this.collisionRect.y0, 0, this);
               this.used = true;
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (this.IsDisplay) {
         MFImage var5 = platformImage;
         int var4 = this.posX;
         int var3 = this.posY;
         int var2 = this.offsetY;
         this.drawInMap(var1, var5, var4, var3 + 768 + var2, 33);
         this.drawCollisionRect(var1);
      }

   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (this.IsDisplay) {
         this.moveCal.logic();
         int var2 = this.posX;
         int var1 = this.posY;
         int var3;
         int var4;
         if (this.isH) {
            if (this.iLeft == 0) {
               this.posX = this.moveCal.getPosition();
            } else {
               var3 = this.initPosx;
               var4 = this.moveCal.getPosition();
               this.posX = this.initPosx + (var3 - var4);
            }
         } else if (this.iTop == 0) {
            this.posY = this.moveCal.getPosition();
         } else {
            var4 = this.initPosy;
            var3 = this.moveCal.getPosition();
            this.posY = this.initPosy + (var4 - var3);
         }

         if (player.isFootOnObject(this)) {
            this.offsetY = 192;
            if (player instanceof PlayerKnuckles) {
               ((PlayerKnuckles)player).setFloating(false);
            }
         } else {
            this.offsetY = 0;
         }

         if (StageManager.getCurrentZoneId() >= 3 && StageManager.getCurrentZoneId() <= 6) {
            this.offsetY2 = -256;
         }

         this.checkWithPlayer(var2, var1, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var8 = this.collisionRect;
      int var3 = COLLISION_OFFSET_Y;
      int var4 = this.offsetY;
      int var7 = this.offsetY2;
      int var5 = COLLISION_HEIGHT;
      int var6 = this.COLLISION_HEIGHT_OFFSET;
      var8.setRect(var1 - 1536, var3 + var2 - 768 + var4 + var7, 3072, var5 + var6);
      if (this.COLLISION_HEIGHT_OFFSET != 0) {
         System.out.println("COLLISION_HEIGHT+COLLISION_HEIGHT_OFFSET=" + (COLLISION_HEIGHT + this.COLLISION_HEIGHT_OFFSET));
      }

   }

   public void setDisplay(boolean var1) {
      this.IsDisplay = var1;
   }
}
