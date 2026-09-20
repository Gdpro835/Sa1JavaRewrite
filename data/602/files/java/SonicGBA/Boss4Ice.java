package SonicGBA;

import GameEngine.time.GameTime;

import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Boss4Ice extends Platform {
   public static final int COLLISION_HEIGHT = 2112;
   public static final int COLLISION_OFFSET_Y = 192;
   public static final int COLLISION_WIDTH = 1536;
   public static final int DRAW_OFFSET_Y = 2112;
   public static final int STAND_OFFSET = 192;
   private static MFImage iceImage;
   public static boolean isEnd;
   private Boss4 boss4;
   private int drop_vel;
   private int endPos;
   private int iceDownCounter;

   protected Boss4Ice(int var1, int var2, int var3, int var4, Boss4 var5) {
      super(121, var1, var2, 0, 0, 0, 0);
      if (iceImage == null) {
         iceImage = MFImage.createImage("/gimmick/boss4_ice.png");
      }

      this.iceDownCounter = GameTime.set(this, "iceDownCounter", 0);
      this.drop_vel = var3;
      this.endPos = var4;
      this.IsDisplay = true;
      isEnd = false;
      this.boss4 = var5;
   }

   public static void releaseAllResource() {
      iceImage = null;
   }

   public boolean chkDestroy() {
      boolean var1;
      if (this.isInCamera() && !this.isFarAwayCamera() && this.posY <= this.endPos && !this.boss4.isNoneIce) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.IsDisplay && !player.isFootOnObject(this)) {
         switch(var2) {
         case 1:
         case 2:
         case 3:
            if (var1.getMoveDistance().y > 0) {
               if (var1.getCollisionRect().y1 < this.collisionRect.y1) {
                  var1.beStop(this.collisionRect.y0, 1, this);
               }
            } else {
               var1.beStop(this.collisionRect.y0, var2, this);
            }

            this.used = true;
            break;
         case 4:
            if (var1.getMoveDistance().y > 0 && var1.getCollisionRect().y1 < this.collisionRect.y1) {
               var1.beStop(this.collisionRect.y0, 1, this);
               this.used = true;
            }
            break;
         default:
            if (var1 == player) {
               if (this.boss4.isNoneIce) {
                  var1.beStop(this.collisionRect.y0, 1, this);
               } else {
                  player.beHurt();
               }
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (this.IsDisplay) {
         MFImage var5 = iceImage;
         int var3 = this.posX;
         int var4 = this.posY;
         int var2 = this.offsetY;
         this.drawInMap(var1, var5, var3, var4 + 2112 + var2, 33);
         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (this.IsDisplay) {
         this.iceDownCounter = GameTime.advance(this, "iceDownCounter", this.iceDownCounter, 1);
         int var2 = this.posX;
         int var1 = this.posY;
         if (player.isFootOnObject(this)) {
            this.offsetY = 192;
         } else {
            this.offsetY = 0;
         }

         if (!this.boss4.dead && this.posY >= StageManager.getWaterLevel() << 6) {
            this.posY = GameTime.advancePosition(this, "posY", this.posY, "drop_vel", this.drop_vel);
         } else {
            this.posY = GameTime.advance(this, "posY", this.posY, this.drop_vel * 6);
         }

         this.checkWithPlayer(var2, var1, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var4 = this.collisionRect;
      int var3 = this.offsetY;
      var4.setRect(var1 - 768, var3 + var2, 1536, 2112);
   }
}
