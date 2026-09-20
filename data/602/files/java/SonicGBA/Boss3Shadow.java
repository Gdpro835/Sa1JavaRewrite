package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;

class Boss3Shadow extends EnemyObject {
   private static final int COLLISION_HEIGHT = 3200;
   private static final int COLLISION_WIDTH = 3200;
   private static final int FACE_HURT = 2;
   private static final int FACE_NORMAL = 0;
   private static final int FACE_SMILE = 1;
   public boolean IsInPipeCollision = false;
   public boolean IsOver;
   private int face_state;

   protected Boss3Shadow(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.IsInPipeCollision = false;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.IsOver && !this.IsInPipeCollision && var1 == player) {
         player.beHurt();
         this.face_state = 1;
      }

   }

   public void draw(MFGraphics var1) {
   }

   public int getShadowHurt() {
      return this.face_state;
   }

   public void logic() {
   }

   public void logic(int var1, int var2) {
      this.posX = var1;
      this.posY = var2;
      var1 = this.posX;
      var2 = this.posY;
      this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
      this.checkWithPlayer(var1, var2, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1600, var2 - 1600, 3200, 3200);
   }

   public void setShadowHurt(int var1) {
      this.face_state = var1;
   }
}
