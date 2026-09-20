package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.MyAPI;
import Lib.crlFP32;
import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;
import com.sega.mobile.framework.device.MFGraphics;
import java.util.Vector;

public class RingObject extends GameObject {
   private static final int DEGREE_CAL_START = 270;
   private static final int DEGREE_SPACE = 20;
   private static final int DEGREE_START = 20;
   private static final int MAX_RING_POP = 50;
   private static final int MAX_VELOCITY = 4200;
   private static final int VELOCITY_ADD = 300;
   private static final int VELOCITY_CHANGE = 300;
   private static final int VELOCITY_OFFSET_Y;
   private static final int VELOCITY_OFFSET_Y2;
   private static final int VELOCITY_START = 800;
   private static Vector moveRingVec;
   private static Animation ringAnimation;
   private boolean beAttractive;
   private boolean used;
   private int velocity = 0;

   static {
      VELOCITY_OFFSET_Y = GRAVITY * -3;
      VELOCITY_OFFSET_Y2 = GRAVITY * -4;
      moveRingVec = new Vector();
   }

   protected RingObject(int var1, int var2) {
      this.posX = var1;
      this.posY = var2;
      if (ringDrawer == null) {
         ringAnimation = new Animation("/animation/ring");
         ringDrawer = ringAnimation.getDrawer();
         ringDrawer.setPause(true);
      }

      this.used = false;
      this.beAttractive = false;
      this.mWidth = 1024;
      this.mHeight = 1024;
   }

   public static RingObject getNewInstance(int var0, int var1) {
      RingObject var2 = new RingObject(var0 << 6, var1 << 6);
      var2.refreshCollisionRect(var2.posX, var2.posY);
      return var2;
   }

   public static void hurtRingExplosion(int var0, int var1, int var2, int var3, boolean var4) {
      int var6 = var0;
      if (var0 > 50) {
         var6 = 50;
      }

      int var5 = var2;
      if (var4) {
         var5 = var2 + 3072;
      }

      boolean var7 = false;
      short var14;
      if (var4) {
         var14 = -800;
      } else {
         var14 = 800;
      }

      long var11;
      MoveRingObject var13;
      if (var6 == 1) {
         var2 = 0 * 20;
         var6 = var2 / 170;
         if (var4) {
            var14 = -800;
         } else {
            var14 = 800;
         }

         var6 = var14 + var6 * 300;
         var2 = (var2 % 170 + 270 + 10) % 360;
         var0 = -var6 * MyAPI.dCos(var2) / 100;
         var6 = MyAPI.dSin(var2) * var6 / 100;
         var2 = VELOCITY_OFFSET_Y2;
         var11 = systemClock;
         var13 = new MoveRingObject(var1, var5, var0, var6 + var2, var3, var11);
         addGameObject(var13);
      } else {
         byte var8 = 0;
         var2 = var14;

         for(var0 = var8; var0 < var6 / 2; ++var0) {
            int var16 = var0 * 20;
            int var17 = var16 / 170;
            short var15;
            if (var4) {
               var15 = -800;
            } else {
               var15 = 800;
            }

            var2 = var17 * 300 + var15;
            var17 = var16 % 170;
            int var9 = (var17 + 270 + 10) % 360;
            var16 = MyAPI.dCos(var9) * var2 / 100;
            int var10 = MyAPI.dSin(var9) * var2 / 100;
            var9 = VELOCITY_OFFSET_Y;
            var11 = systemClock;
            var13 = new MoveRingObject(var1, var5, var16, var10 + var9, var3, var11);
            addGameObject(var13);
            var16 = (270 - var17 - 10) % 360;
            var17 = MyAPI.dCos(var16) * var2 / 100;
            var9 = MyAPI.dSin(var16) * var2 / 100;
            var10 = VELOCITY_OFFSET_Y;
            var11 = systemClock;
            var13 = new MoveRingObject(var1, var5, var17, var9 + var10, var3, var11);
            addGameObject(var13);
         }

         if (var6 % 2 == 1) {
            var6 = (var0 * 20 % 170 + 270 + 10) % 360;
            var0 = MyAPI.dCos(var6) * var2 / 100;
            var2 = MyAPI.dSin(var6) * var2 / 100;
            var11 = systemClock;
            var13 = new MoveRingObject(var1, var5, var0, var2, var3, var11);
            addGameObject(var13);
         }
      }

      soundInstance.playSe(13);
   }

   public static void ringDraw(MFGraphics var0) {
      for(int var1 = 0; var1 < moveRingVec.size(); ++var1) {
         RingObject var2 = (RingObject)moveRingVec.elementAt(var1);
         var2.draw(var0);
      }

   }

   public static void ringInit() {
      moveRingVec.removeAllElements();
   }

   public static void ringLogic() {
      int var1;
      for(int var0 = 0; var0 < moveRingVec.size(); var0 = var1 + 1) {
         RingObject var2 = (RingObject)moveRingVec.elementAt(var0);
         var2.ringMoveLogic();
         var1 = var0;
         if (var2.objectChkDestroy()) {
            moveRingVec.removeElementAt(var0);
            var1 = var0 - 1;
         }
      }

   }

   public void beAttract() {
      if (!this.beAttractive) {
         this.beAttractive = true;
         moveRingVec.addElement(this);
      }

   }

   public void close() {
   }

   public void doBeforeCollisionCheck() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.used && !var1.hurtNoControl && !var1.isSharked) {
         this.used = true;
         Effect.showEffect(ringAnimation, 1, this.posX >> 6, this.posY >> 6, 0);
         PlayerObject.getRing(1);
         soundInstance.playSe(12);
         isGotRings = true;
      }

   }

   public void doWhileCollision(ACObject var1, ACCollision var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void doWhileRail(PlayerObject var1, int var2) {
      this.doWhileCollision(var1, var2);
   }

   public void draw(MFGraphics var1) {
      if (!this.used) {
         this.drawInMap(var1, ringDrawer);
      }

   }

   public void logic() {
   }

   public boolean objectChkDestroy() {
      return this.used;
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - (this.mWidth >> 1), var2 - this.mHeight, this.mWidth, this.mHeight + 128);
   }

   public void ringMoveLogic() {
      if (this.beAttractive) {
         this.velocity = GameTime.advance(this, "velocity", this.velocity, 300);
         if (this.velocity > 4200) {
            this.velocity = 4200;
         }

         int var2 = player.getCheckPositionX() - this.posX;
         int var3 = player.getCheckPositionY() - this.posY;
         if (var2 == 0 && var3 == 0) {
            this.doWhileCollision(player, 4);
         } else {
            int var1 = crlFP32.actTanDegree(var3, var2);
            var1 = (var1 + 360) % 360;
            int var5 = this.velocity * MyAPI.dCos(var1) / 100;
            int var4 = this.velocity * MyAPI.dSin(var1) / 100;
            var1 = var5;
            if (Math.abs(var5) > Math.abs(var2)) {
               var1 = var2;
            }

            var2 = var4;
            if (Math.abs(var4) > Math.abs(var3)) {
               var2 = var3;
            }

            this.posX = GameTime.advance(this, "posX", this.posX, var1);
            this.posY = GameTime.advance(this, "posY", this.posY, var2);
            this.refreshCollisionRect(this.posX, this.posY);
            if (this.collisionChkWithObject(player)) {
               this.doWhileCollision(player, 4);
            }
         }
      }

   }
}
