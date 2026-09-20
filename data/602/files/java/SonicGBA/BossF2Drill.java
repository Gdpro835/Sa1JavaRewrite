package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class BossF2Drill extends EnemyObject {
   private static final int COLLISION_HEIGHT = 1344;
   private static final int COLLISION_WIDTH = 1728;
   private static Animation drillAni;
   private AnimationDrawer drillDrawer;
   private boolean isPlayerHurt = false;

   protected BossF2Drill(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (drillAni == null) {
         drillAni = new Animation("/animation/bossf2_drill");
      }

      this.drillDrawer = drillAni.getDrawer(0, true, 0);
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
      if (!this.dead) {
         this.drawInMap(var1, this.drillDrawer);
         this.drawCollisionRect(var1);
      }

   }

   public boolean getPlayerHurt() {
      return this.isPlayerHurt;
   }

   public void logic() {
   }

   public void logic(int var1, int var2, boolean var3) {
      if (!this.dead) {
         this.posX = var1;
         this.posY = var2;
         var1 = this.posX;
         var2 = this.posY;
         if (var3) {
            this.drillDrawer.setTrans(2);
         } else {
            this.drillDrawer.setTrans(0);
         }

         this.refreshCollisionRect(this.posX, this.posY);
         this.checkWithPlayer(var1, var2, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 864, var2 - 672, 1728, 1344);
   }

   public void resetPlayerHurt() {
      this.isPlayerHurt = false;
   }

   public void setEnd() {
      this.dead = true;
   }
}
