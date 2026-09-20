package SonicGBA;

class AntiGravity extends GimmickObject {
   private boolean activeAfterNoCollison;
   private int enterPlayerX;
   private int iLeft;

   protected AntiGravity(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.iLeft = var4;
      if (this.iLeft == 0 && this.iTop == 0) {
         this.activeAfterNoCollison = true;
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1 == player) {
         if (this.activeAfterNoCollison) {
            if (this.firstTouch) {
               this.enterPlayerX = player.getCheckPositionX();
               this.used = true;
            }
         } else if (this.collisionRect.collisionChk(player.getCheckPositionX(), player.getCheckPositionY())) {
            if (!this.used) {
               switch(this.iLeft) {
               case 0:
                  if (this.iTop == 0) {
                     var1 = player;
                     boolean var3;
                     if (player.isAntiGravity) {
                        var3 = false;
                     } else {
                        var3 = true;
                     }

                     var1.setAntiGravity(var3);
                  } else {
                     player.setAntiGravity(false);
                  }
                  break;
               case 1:
                  player.setAntiGravity(false);
                  break;
               case 2:
                  player.setAntiGravity(true);
               }

               this.used = true;
            }
         } else {
            this.used = false;
         }
      }

   }

   public void doWhileNoCollision() {
      if (this.activeAfterNoCollison && this.used && (player.getCheckPositionX() > this.enterPlayerX + 1000 || player.getCheckPositionX() < this.enterPlayerX - 1000)) {
         PlayerObject var2 = player;
         boolean var1;
         if (player.isAntiGravity) {
            var1 = false;
         } else {
            var1 = true;
         }

         var2.setAntiGravity(var1);
      }

      this.used = false;
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1, var2, this.mWidth, this.mHeight);
   }
}
