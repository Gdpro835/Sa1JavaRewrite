package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class Poal extends GimmickObject {
   private static final int COLLISION_HEIGHT = 640;
   private static final int COLLISION_WIDTH = 1600;
   private static final int[] PULL_OFFSET;
   private static final byte STATE_POP = 2;
   private static final byte STATE_PULL = 1;
   private static final byte STATE_STAY = 0;
   private static Animation poalAnimation;
   private int drawIdStart;
   private AnimationDrawer drawer;
   private boolean isH;
   private int offsetCount;
   private int restCount;
   private byte state;

   static {
      int[] var0 = new int[]{0, 256, 320};
      PULL_OFFSET = var0;
   }

   protected Poal(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (poalAnimation == null) {
         poalAnimation = new Animation("/animation/poal_" + StageManager.getCurrentZoneId());
      }

      this.isH = false;
      CollisionRect var8;
      switch(this.objId) {
      case 42:
         if (this.iTop >= 0) {
            this.drawer = poalAnimation.getDrawer(3, false, 0);
         } else {
            this.drawer = poalAnimation.getDrawer(3, false, 2);
         }

         this.drawIdStart = 3;
         var8 = this.collisionRect;
         var2 = this.posX;
         var1 = this.posY;
         var8.setRect(var2 - 320, var1 - 1600, 640, 1600);
         this.isH = true;
      case 43:
      default:
         break;
      case 44:
         this.drawer = poalAnimation.getDrawer(0, false, 0);
         this.collisionRect.setRect(this.posX, this.posY, 1600, 640);
         this.drawIdStart = 0;
         break;
      case 45:
         this.drawer = poalAnimation.getDrawer(0, false, 2);
         var8 = this.collisionRect;
         var1 = this.posX;
         var2 = this.posY;
         var8.setRect(var1 - 1600, var2, 1600, 640);
         this.drawIdStart = 0;
      }

      this.state = 0;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(poalAnimation);
      poalAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.state == 0 && var1 == player) {
         int var3;
         boolean var5;
         switch(this.objId) {
         case 42:
            this.offsetCount = 0;
            var1 = player;
            var2 = this.posX;
            var3 = this.posY;
            if (this.iTop >= 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var1.doPoalMotion2(var2, var3, var5)) {
               this.state = 1;
               SoundSystem.getInstance().playSe(47);
            }
            break;
         default:
            this.offsetCount = 0;
            var1 = player;
            var3 = this.collisionRect.x0;
            int var4 = this.collisionRect.y0;
            var2 = PULL_OFFSET[this.offsetCount];
            if (this.objId == 44) {
               var5 = true;
            } else {
               var5 = false;
            }

            if (var1.doPoalMotion(var3 + 800, var4 + var2, var5)) {
               this.state = 1;
               SoundSystem.getInstance().playSe(47);
               player.setFaceDegree(0);
               player.degreeForDraw = player.faceDegree;
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      this.drawer.setActionId(this.drawIdStart + this.state);
      this.drawInMap(var1, this.drawer);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      switch(this.state) {
      case 1:
         PlayerObject var5;
         if (this.drawer.getActionId() == this.drawIdStart + 1 && this.drawer.checkEnd()) {
            this.restCount = GameTime.set(this, "restCount", 10);
            this.state = 2;
            var5 = player;
            byte var6;
            if (this.isH) {
               if (this.iTop >= 0) {
                  var6 = 2;
               } else {
                  var6 = 3;
               }
            } else {
               var6 = 1;
            }

            var5.bePop(2400, var6);
         } else {
            int var1;
            int var2;
            boolean var4;
            if (this.isH) {
               var5 = player;
               var1 = this.posX;
               var2 = this.posY;
               if (this.iTop >= 0) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               var5.doPoalMotion2(var1, var2, var4);
            } else {
               var5 = player;
               int var3 = this.collisionRect.x0;
               var1 = this.collisionRect.y0;
               var2 = PULL_OFFSET[this.offsetCount];
               if (this.objId == 44) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               var5.doPoalMotion(var3 + 800, var1 + var2, var4);
               this.offsetCount = GameTime.advance(this, "offsetCount", this.offsetCount, 1);
               if (this.offsetCount >= PULL_OFFSET.length) {
                  this.offsetCount = PULL_OFFSET.length - 1;
               }
            }
         }
         break;
      case 2:
         if (this.restCount > 0) {
            this.restCount = Math.max(0, GameTime.advance(this, "restCount", this.restCount, -(1)));
         }

         if (this.restCount == 0 && this.drawer.checkEnd()) {
            this.state = 0;
         }
      }

   }
}
