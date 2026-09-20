package SonicGBA;

import GameEngine.Key;

class IronBar extends GimmickObject {
   private static final int BAR_VELOCITY = 500;
   private boolean touching = false;

   protected IronBar(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.touching && !player.leavingBar && !player.outOfControl && player.getVelY() >= 0) {
         this.touching = true;
         player.setOutOfControl(this);
         player.doPullBarMotion(this.posY);
         player.degreeForDraw = player.faceDegree;
      }

   }

   public void logic() {
      if (this.touching) {
         int var1 = player.getFootPositionX();
         int var3 = player.getFootPositionY();
         player.doPullBarMotion(this.posY);
         if (Key.repeat(Key.gLeft)) {
            player.setFootPositionX(player.getFootPositionX() - 500);
            player.setAnimationId(28);
            player.faceDirection = false;
         } else if (Key.repeat(Key.gRight)) {
            player.setFootPositionX(player.getFootPositionX() + 500);
            player.setAnimationId(28);
            player.faceDirection = true;
         }

         if (player.getCollisionRect().x1 < this.collisionRect.x0 || player.getCollisionRect().x0 > this.collisionRect.x1 || Key.press(Key.gUp | 16777216)) {
            player.outOfControl = false;
            player.setAnimationId(10);
            this.touching = false;
            player.leavingBar = true;
            System.out.println("do leave bar");
         }

         PlayerObject var5 = player;
         int var4 = player.getFootPositionX();
         int var2 = player.getFootPositionY();
         var5.checkWithObject(var1, var3, var4, var2);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(this.posX + (this.iLeft * 8 << 6), this.posY, this.mWidth, 2);
   }
}
