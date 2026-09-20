package SonicGBA;

import GameEngine.time.GameTime;

import com.sega.mobile.framework.device.MFGraphics;

class MoveRingObject extends RingObject implements MapBehavior {
   private static final int LIFE_COUNT = 90;
   private long appearTime;
   private final boolean isAntiGravity;
   private MapObject mapObj;
   private int popVelX;
   private int popVelY;
   private boolean poping;
   private int reserveVelX;
   private int unTouchCount;

   protected MoveRingObject(int var1, int var2, int var3, int var4, int var5, long var6) {
      super(var1, var2);
      this.mapObj = new MapObject(var1, var2, var3, var4, this, var5);
      this.mapObj.setBehavior(this);
      this.reserveVelX = var3;
      this.appearTime = var6;
      this.isAntiGravity = player.isAntiGravity;
      this.mapObj.setAntiGravity(this.isAntiGravity);
      this.unTouchCount = GameTime.set(this, "unTouchCount", 0);
   }

   protected MoveRingObject(int var1, int var2, int var3, int var4, int var5, long var6, int var8) {
      this(var1, var2, var3, var4, var5, var6);
      this.unTouchCount = GameTime.set(this, "unTouchCount", var8);
   }

   public boolean canBeInit() {
      return false;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.unTouchCount <= 0) {
         super.doWhileCollision(var1, var2);
      }

   }

   public void doWhileToucRoof(int var1, int var2) {
      if (!this.poping) {
         this.poping = true;
         var1 = var2;
         if (Math.abs(var2) < 500) {
            var1 = -500;
         }

         this.reserveVelX = this.reserveVelX * 9 / 10;
         this.popVelX = this.reserveVelX;
         this.popVelY = -var1 * 9 / 10;
      }

   }

   public void doWhileTouchGround(int var1, int var2) {
      var1 = var2;
      if (Math.abs(var2) < 500) {
         var1 = 500;
      }

      this.reserveVelX = this.reserveVelX * 9 / 10;
      this.mapObj.doJump(this.reserveVelX, -var1 * 9 / 10);
   }

   public void draw(MFGraphics var1) {
      if (systemClock - this.appearTime > 60L) {
         if (systemClock % 2L == 0L) {
            super.draw(var1);
         }
      } else {
         super.draw(var1);
      }

   }

   public CollisionRect getCollisionRect() {
      return this.collisionRect;
   }

   public int getGravity() {
      return GRAVITY;
   }

   public boolean hasDownCollision() {
      boolean var1;
      if (this.isAntiGravity) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean hasSideCollision() {
      return false;
   }

   public boolean hasTopCollision() {
      return this.isAntiGravity;
   }

   public void logic() {
      if (this.poping) {
         this.mapObj.doJump(this.popVelX, this.popVelY, true);
         this.poping = false;
      }

      this.mapObj.logic();
      this.checkWithPlayer(this.posX, this.posY, this.mapObj.getPosX(), this.mapObj.getPosY());
      this.posX = this.mapObj.getPosX();
      this.posY = this.mapObj.getPosY();
      if (this.unTouchCount > 0) {
         this.unTouchCount = Math.max(0, GameTime.advance(this, "unTouchCount", this.unTouchCount, -(1)));
      }

   }

   public boolean objectChkDestroy() {
      boolean var1;
      if (!super.objectChkDestroy() && systemClock - this.appearTime <= 90L && systemClock - this.appearTime >= 0L) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }
}
