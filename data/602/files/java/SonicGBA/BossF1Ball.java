package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;

class BossF1Ball extends EnemyObject {
   private static final int COLLISION_HEIGHT = 2048;
   private static final int COLLISION_WIDTH = 2048;
   private boolean isPlayerHurt = false;

   protected BossF1Ball(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX = 0;
      this.posY = 0;
      this.isPlayerHurt = false;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && var1 == player) {
         player.beHurt();
         this.isPlayerHurt = true;
      }

   }

   public void doWhileNoCollision() {
      this.isPlayerHurt = false;
   }

   public void draw(MFGraphics var1) {
      this.drawCollisionRect(var1);
   }

   public boolean getPlayerHurt() {
      return this.isPlayerHurt;
   }

   public void logic() {
   }

   public void logic(int var1, int var2) {
      this.posX = var1;
      this.posY = var2;
      var1 = this.posX;
      var2 = this.posY;
      this.refreshCollisionRect(this.posX, this.posY);
      this.checkWithPlayer(var1, var2, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 1024, var2 - 1024, 2048, 2048);
   }

   public void resetPlayerHurt() {
      this.isPlayerHurt = false;
   }

   public void setEnd() {
      this.dead = true;
   }
}
