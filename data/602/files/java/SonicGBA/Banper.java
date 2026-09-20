package SonicGBA;

import Lib.SoundSystem;

class Banper extends GimmickObject {
   private boolean isActived = false;

   protected Banper(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      super.doWhileBeAttack(var1, var2, var3);
      if (player instanceof PlayerAmy && !this.isActived) {
         this.isActived = true;
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (player.getCollisionRect().y0 >= this.collisionRect.y0 && player.getCollisionRect().y1 <= this.collisionRect.y1 + 512 || this.iHeight > this.iWidth) {
         player.setCollisionState((byte)1);
         if (player instanceof PlayerKnuckles && ((PlayerKnuckles)player).flying) {
            player.setAnimationId(4);
            ((PlayerKnuckles)player).flying = false;
         } else {
            player.setAnimationId(4);
         }

         int var4 = player.getVelX();
         int var3 = player.getVelY();
         var1 = player;
         short var5;
         if (this.iLeft != 2 && this.iLeft != 4) {
            var5 = 1200;
         } else {
            var5 = -1200;
         }

         var1.setVelX(var5);
         var1 = player;
         if (this.iLeft != 1 && this.iLeft != 2) {
            var5 = 1536;
         } else {
            var5 = -1536;
         }

         var1.setVelY(var5);
         boolean var6 = false;
         if (player.getVelX() * var4 <= 0) {
            player.getCal().stopMoveX();
            SoundSystem.getInstance().playSe(55);
            var6 = true;
         }

         if (player.getVelY() * var3 <= 0) {
            player.getCal().stopMoveY();
            if (!var6) {
               SoundSystem.getInstance().playSe(55);
            }
         }

         if (!this.isActived) {
            this.isActived = true;
         }
      }

   }

   public void doWhileNoCollision() {
      if (this.isActived) {
         this.isActived = false;
      }

   }
}
