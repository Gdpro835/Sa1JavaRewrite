package SonicGBA;

import GameEngine.time.GameTime;

import com.sega.mobile.framework.device.MFGraphics;

class FallingPlatform extends Platform {
   private static final int FALLING_COUNT = 20;
   private int fallingCount = 20;
   private int posOriginalX;
   private int posOriginalY;
   private int velocity = 0;

   protected FallingPlatform(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posOriginalX = this.posX;
      this.posOriginalY = this.posY;
      this.velocity = 0;
   }

   public boolean checkInit() {
      if (Math.abs(player.getFootPositionX() - this.posOriginalX) >> 6 >= MapManager.CAMERA_WIDTH >> 1 && Math.abs(player.getFootPositionY() - this.posOriginalY) >> 6 >= MapManager.CAMERA_HEIGHT >> 1) {
         this.posY = this.posOriginalY;
         this.used = false;
         this.fallingCount = GameTime.set(this, "fallingCount", 20);
         this.velocity = 0;
         this.refreshCollisionRect(this.posX, this.posY);
      }

      return false;
   }

   public boolean collisionChkWithObject(PlayerObject var1) {
      CollisionRect var9 = var1.getCollisionRect();
      CollisionRect var8 = this.getCollisionRect();
      CollisionRect var7 = rectV;
      int var2 = var9.x0;
      int var5 = var9.y0;
      int var6 = this.offsetY2;
      int var4 = var9.getWidth();
      int var3 = var9.getHeight();
      var7.setRect(var2 + 192, var5 + var6, var4 - 384, var3);
      return var8.collisionChk(rectV);
   }

   public void doInitWhileInCamera() {
   }

   public void draw(MFGraphics var1) {
      super.draw(var1);
   }

   public void logic() {
      this.refreshCollisionRect(this.posX, this.posY);
      if (this.fallingCount > 0 && this.used) {
         this.fallingCount = GameTime.advance(this, "fallingCount", this.fallingCount, -(1));
      }

      if (this.fallingCount == 0) {
         this.velocity = GameTime.advance(this, "velocity", this.velocity, GRAVITY);
      }

      if (player.isFootOnObject(this)) {
         this.offsetY = 192;
      } else {
         this.offsetY = 0;
      }

      if (StageManager.getCurrentZoneId() >= 3 && StageManager.getCurrentZoneId() <= 6) {
         this.offsetY2 = -256;
      }

      this.checkWithPlayer(this.posX, this.posY, this.posX, this.posY + this.velocity);
      this.posY = GameTime.advancePosition(this, "posY", this.posY, "velocity", this.velocity);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var7 = this.collisionRect;
      int var5 = COLLISION_OFFSET_Y;
      int var6 = this.offsetY;
      int var4 = this.offsetY2;
      int var3 = COLLISION_HEIGHT;
      var7.setRect(var1 - 1536, var5 + var2 - 768 + var6 + var4, 3072, var3);
   }
}
