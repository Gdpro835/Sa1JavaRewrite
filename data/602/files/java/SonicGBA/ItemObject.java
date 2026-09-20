package SonicGBA;

import GameEngine.time.GameTime;

import Lib.MyAPI;
import Lib.SoundSystem;
import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

public class ItemObject extends GameObject {
   private static final int COLLISION_HEIGHT = 1728;
   private static final int COLLISION_WIDTH = 2048;
   private static final int MOVE_COUNT = 20;
   private static int gridWidth;
   public static MFImage itemBoxImage;
   public static MFImage itemContentImage;
   public static MFImage itemHeadImage;
   private boolean isActive;
   private MapObject mapObj;
   private int moveCount;
   private int objId;
   private int originalY;
   private boolean poping = false;
   private int posYoffset;
   private boolean used;

   private ItemObject(int var1, int var2, int var3) {
      this.objId = var1;
      this.mWidth = 2048;
      this.mHeight = 1728;
      this.posX = var2;
      this.posYoffset = 0;
      var1 = var3 + 256;
      this.posY = var1;
      this.originalY = var1;
      if (itemBoxImage == null) {
         try {
            itemBoxImage = MFImage.createImage("/item/item_box.png");
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }

      if (itemContentImage == null) {
         try {
            itemContentImage = MFImage.createImage("/item/item_content.png");
         } catch (Exception var5) {
            var5.printStackTrace();
         }

         gridWidth = MyAPI.zoomIn(itemContentImage.getWidth(), true) >> 3;
      }

      if (itemHeadImage == null) {
         itemHeadImage = MFImage.createImage("/item/item_head.png");
      }

      this.used = false;
      this.isActive = false;
   }

   public static void closeItem() {
      itemBoxImage = null;
      itemContentImage = null;
   }

   private void doFunction(PlayerObject var1, int var2) {
      this.doFunction(var1, var2, true);
   }

   private void doFunction(PlayerObject var1, int var2, boolean var3) {
      if (var3) {
         var1.doItemAttackPose(this, var2);
      }

      this.used = true;
      this.moveCount = GameTime.set(this, "moveCount", 20);
      player.getPreItem(this.objId);
      SoundSystem.getInstance().playSe(29);
   }

   public static ItemObject getNewInstance(int var0, int var1, int var2) {
      ItemObject var3;
      if (var0 == 0 && stageModeState == 1) {
         var3 = null;
      } else {
         var3 = new ItemObject(var0, var1 << 6, var2 << 6);
         var3.refreshCollisionRect(var3.posX, var3.posY);
      }

      return var3;
   }

   public void close() {
   }

   public void doBeforeCollisionCheck() {
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (!this.used && !var1.piping) {
         boolean var4;
         if (var3 != 12) {
            var4 = true;
         } else {
            var4 = false;
         }

         this.doFunction(var1, var2, var4);
         Effect.showEffect(destroyEffectAnimation, 0, this.posX >> 6, this.posY - (this.mHeight >> 1) >> 6, 0);
         if (player instanceof PlayerTails && player.myAnimationID == 12 && var2 == 1) {
            player.velY = -600;
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.used && !var1.piping) {
         if (var2 != 4) {
            switch(var2) {
            case 0:
               if (!player.isAntiGravity) {
                  this.poping = true;
                  this.velY = -512;
                  this.mapObj.setPosition(this.posX, player.getCollisionRect().y0, 0, this.velY, this);
               } else if (var1 == player && var1.isAttackingItem() && this.firstTouch && player.isAntiGravity && (!(player instanceof PlayerKnuckles) || player.animationID == 4)) {
                  this.doFunction(var1, var2);
               }
               break;
            case 1:
               if (var1 == player && var1.isAttackingItem() && this.firstTouch && !player.isAntiGravity) {
                  this.doFunction(var1, var2);
               }

               if (var1 == player && !player.isAntiGravity) {
                  this.isActive = true;
                  player.IsStandOnItems = true;
               }
               break;
            case 2:
            case 3:
               if (var1 == player && var1.isAttackingItem() && var1.getVelX() != 0) {
                  this.doFunction(var1, var2);
               }
            }

            if (!this.used && (!(player instanceof PlayerKnuckles) || var2 != 0 || player.isAntiGravity)) {
               var1.beStop(0, var2, this);
            }
         }

         if (this.used) {
            Effect.showEffect(destroyEffectAnimation, 0, this.posX >> 6, this.posY - (this.mHeight >> 1) >> 6, 0);
         }
      }

   }

   public void doWhileCollision(ACObject var1, ACCollision var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void doWhileNoCollision() {
      if (this.isActive) {
         player.IsStandOnItems = false;
         this.isActive = false;
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.used || this.moveCount >= 0) {
         int var2;
         int var3;
         int var4;
         int var5;
         int var6;
         int var7;
         int var8;
         MFImage var9;
         if (this.objId != 0) {
            var9 = itemContentImage;
            var8 = this.objId;
            var6 = gridWidth;
            var5 = gridWidth;
            var7 = gridWidth;
            var3 = this.posX;
            var4 = this.posY;
            if (this.poping) {
               var2 = this.posYoffset;
            } else {
               var2 = 0;
            }

            this.drawInMap(var1, var9, var8 * var6, 0, var5, var7, 0, var3, var4 - 896 + var2, 3);
         } else if (PlayerObject.getCharacterID() == 0) {
            var9 = itemContentImage;
            var6 = gridWidth;
            var4 = gridWidth;
            var3 = this.posX;
            var5 = this.posY;
            if (this.poping) {
               var2 = this.posYoffset;
            } else {
               var2 = 0;
            }

            this.drawInMap(var1, var9, 0, 0, var6, var4, 0, var3, var5 - 896 + var2, 3);
         } else {
            var9 = itemHeadImage;
            var7 = PlayerObject.getCharacterID();
            var5 = gridWidth;
            var3 = gridWidth;
            var8 = gridWidth;
            var6 = this.posX;
            var4 = this.posY;
            if (this.poping) {
               var2 = this.posYoffset;
            } else {
               var2 = 0;
            }

            this.drawInMap(var1, var9, (var7 - 1) * var5, 0, var3, var8, 0, var6, var4 - 896 + var2, 3);
         }
      }

      if (!this.used) {
         this.drawInMap(var1, itemBoxImage, 33);
      }

      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (this.used) {
         if (this.moveCount > 0) {
            this.moveCount = Math.max(0, GameTime.advance(this, "moveCount", this.moveCount, -(1)));
            if (GameTime.event(this, "moveCount", "logic:244", GameTime.crosses(this, "moveCount", this.moveCount, 19) && this.objId >= 5 && this.objId <= 7)) {
               PlayerObject.getTmpRing(this.objId);
            }

            this.posY = GameTime.advance(this, "posY", this.posY, -(200));
            this.posYoffset = GameTime.advance(this, "posYoffset", this.posYoffset, -(200));
         }

         if (this.moveCount == 0) {
            this.moveCount = GameTime.set(this, "moveCount", -1);
            if (this.objId >= 5 && this.objId <= 7) {
               soundInstance.playSe(12);
            }
         }
      }

      if (this.poping) {
         if (this.mapObj == null) {
            this.mapObj = new MapObject(this.posX + 2048, this.posY, 0, 0, this, 1);
            this.mapObj.setPosition(this.posX, this.posY, 0, this.velY, this);
            this.mapObj.setCrashCount(1);
         }

         this.mapObj.logic2();
         this.checkWithPlayer(this.posX, this.posY, this.mapObj.getPosX(), this.mapObj.getPosY());
         this.posX = this.mapObj.getPosX();
         this.posY = this.mapObj.getPosY();
         this.refreshCollisionRect(this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - (this.mWidth >> 1), var2 - this.mHeight, this.mWidth, this.mHeight);
   }
}
