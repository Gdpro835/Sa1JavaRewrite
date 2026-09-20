package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class StoneBall extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1792;
   private static final int COLLISION_WIDTH = 1792;
   public static final int OUT_VELOCITY = 300;
   private static final int STARTUP_HEIGHT = 9216;
   private static final int STARTUP_WIDTH = 16384;
   public static final byte STATE_GO = 2;
   public static final byte STATE_NONE = 0;
   public static final byte STATE_OUT = 1;
   private static Animation animation;
   private final int WAIT = 20;
   private AnimationDrawer drawer;
   private MapObject mapObj;
   private boolean noBall;
   private int originalX;
   private int originalY;
   public byte state;
   private int waitCount;

   protected StoneBall(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (animation == null) {
         animation = new Animation("/animation/stone_ball");
      }

      this.drawer = animation.getDrawer(0, true, 0);
      this.noBall = true;
      this.originalX = this.posX;
      this.originalY = this.posY;
      this.mapObj = new MapObject(this.posX + 2048, this.posY, 0, 0, this, this.iLeft);
   }

   private boolean inScreen() {
      Coordinate var2 = MapManager.getCamera();
      boolean var1;
      if (this.posX >> 6 > var2.x && this.posX >> 6 < var2.x + SCREEN_WIDTH && this.posY >> 6 > var2.y && this.posY >> 6 < var2.y + SCREEN_HEIGHT) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(animation);
      animation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.state != 0) {
         var1.beHurt();
      }

   }

   public void draw(MFGraphics var1) {
      if (this.state != 0) {
         this.drawInMap(var1, this.drawer);
         this.drawCollisionRect(var1);
      }

   }

   public void drawCollisionRect(MFGraphics var1) {
      super.drawCollisionRect(var1);
      if (SonicDebug.showCollisionRect) {
         var1.setColor(16711680);
         int var2 = this.originalX;
         int var4 = camera.x;
         int var5 = this.originalY;
         int var3 = camera.y;
         var1.drawRect((var2 - 16384 >> 6) - var4, (var5 - 9216 >> 6) - var3, 512, 288);
         var5 = this.originalX;
         var2 = camera.x;
         var4 = this.originalY;
         var3 = camera.y;
         var1.drawRect((var5 - 16384 >> 6) - var2 + 1, (var4 - 9216 >> 6) - var3 + 1, 510, 286);
      }

   }

   public void logic() {
      int var1 = this.posX;
      int var2 = this.posY;
      if (this.waitCount > 0) {
         --this.waitCount;
      }

      switch(this.state) {
      case 0:
         this.posX = this.originalX;
         this.posY = this.originalY;
         this.transportTo(this.posX, this.posY);
         if (this.waitCount == 0 && player.getFootPositionX() > this.originalX - 16384 && player.getFootPositionX() < this.originalX + 16384 && player.getFootPositionY() > this.originalY - 9216 && player.getFootPositionY() < this.originalY + 9216) {
            this.state = 1;
         }
         break;
      case 1:
         this.posX += 300;
         if (this.posX > this.originalX + 2048) {
            this.state = 2;
            this.mapObj.setPosition(this.posX, this.posY, 300, 0, this);
            this.mapObj.setCrashCount(2);
         }

         this.checkWithPlayer(var1, var2, this.posX, this.posY);
         break;
      case 2:
         this.mapObj.logic();
         this.checkWithPlayer(this.posX, this.posY, this.mapObj.getPosX(), this.mapObj.getPosY());
         this.posX = this.mapObj.getPosX();
         this.posY = this.mapObj.getPosY();
         if (this.mapObj.chkCrash()) {
            this.state = 0;
            this.waitCount = 20;
            Effect.showEffect(rockBreakAnimation, 0, this.posX >> 6, this.posY >> 6, 0);
            if (this.inScreen()) {
               SoundSystem.getInstance().playSe(35);
            }
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 896, var2 - 1792, 1792, 1792);
   }
}
