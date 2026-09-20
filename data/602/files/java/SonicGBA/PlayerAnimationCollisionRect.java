package SonicGBA;

public class PlayerAnimationCollisionRect implements SonicDef {
   public static final int CHECK_OFFSET = 192;
   private static CollisionRect rectH = new CollisionRect();
   private static CollisionRect rectV = new CollisionRect();
   private int animationID;
   public CollisionRect collisionRect;
   private boolean initFlag;
   private PlayerObject player;
   public CollisionRect preCollisionRect;

   public PlayerAnimationCollisionRect(PlayerObject var1) {
      this.player = var1;
      this.collisionRect = new CollisionRect();
      this.preCollisionRect = new CollisionRect();
      this.initFlag = true;
   }

   public void calPreCollision() {
      CollisionRect var5 = this.preCollisionRect;
      int var3 = this.collisionRect.x0;
      int var1 = this.collisionRect.y0;
      int var4 = this.collisionRect.x1;
      int var2 = this.collisionRect.y1;
      var5.setTwoPosition(var3, var1, var4, var2);
   }

   public void collisionChkWithObject(GameObject var1) {
      CollisionRect var6 = var1.getCollisionRect();
      CollisionRect var7 = this.collisionRect;
      CollisionRect var8 = rectH;
      int var2 = var7.x0;
      int var3 = var7.y0;
      int var5 = var7.getWidth();
      int var4 = var7.getHeight();
      var8.setRect(var2, var3 + 192, var5, var4 - 384);
      var8 = rectV;
      var5 = var7.x0;
      var3 = var7.y0;
      var4 = var7.getWidth();
      var2 = var7.getHeight();
      var8.setRect(var5 + 192, var3, var4 - 384, var2);
      if (var6.collisionChk(rectH) || var6.collisionChk(rectV)) {
         this.doWhileCollisionWrap(var1);
      }

   }

   public void doWhileCollisionWrap(GameObject var1) {
      byte var4 = 4;
      int var2 = var1.getMoveDistance().x;
      var2 = var1.getMoveDistance().y;
      CollisionRect var10 = this.collisionRect;
      CollisionRect var8 = this.preCollisionRect;
      CollisionRect var9 = var1.getCollisionRect();
      var2 = var10.x0;
      int var6 = var8.x0;
      int var5 = var10.y0;
      int var3 = var8.y0;
      boolean var12;
      if (Math.abs(var2 - var6) >= Math.abs(var5 - var3)) {
         var12 = true;
      } else {
         var12 = false;
      }

      CollisionRect var11 = rectH;
      var5 = var10.x0;
      var6 = var10.y0;
      var2 = var10.getWidth();
      int var7 = var10.getHeight();
      var11.setRect(var5, var6 + 192, var2, var7 - 192 * 2);
      var11 = rectV;
      var2 = var10.x0;
      var6 = var10.y0;
      var7 = var10.getWidth();
      var5 = var10.getHeight();
      var11.setRect(var2 + 192, var6, var7 - 192 * 2, var5);
      byte var13 = var4;
      if (var12) {
         var13 = var4;
         if (rectH.collisionChk(var9)) {
            if (var10.x1 - var8.x1 > 0 && var8.isLeftOf(var9, 192) || !rectV.collisionChk(var9) && var10.x0 < var9.x0 && this.player.getVelX() >= -192) {
               var13 = 3;
            } else {
               label122: {
                  if (var10.x0 - var8.x0 >= 0 || !var8.isRightOf(var9, 192)) {
                     var13 = var4;
                     if (rectV.collisionChk(var9)) {
                        break label122;
                     }

                     var13 = var4;
                     if (var10.x1 <= var9.x1) {
                        break label122;
                     }

                     var13 = var4;
                     if (this.player.getVelX() > 192) {
                        break label122;
                     }
                  }

                  var13 = 2;
               }
            }
         }
      }

      byte var14 = var13;
      if (var13 == 4) {
         var14 = var13;
         if (rectV.collisionChk(var9)) {
            if (var10.y1 - var8.y1 > 0 && var8.isUpOf(var9, 192 + 5)) {
               var14 = 1;
            } else {
               var14 = var13;
               if (var10.y0 - var8.y0 < 0) {
                  var14 = var13;
                  if (var8.isDownOf(var9, 192)) {
                     var14 = 0;
                  }
               }
            }
         }
      }

      var13 = var14;
      if (var14 == 4) {
         var13 = var14;
         if (rectH.collisionChk(var9)) {
            if ((var10.x1 - var8.x1 <= 0 || !var8.isLeftOf(var9, 192)) && (rectV.collisionChk(var9) || var10.x0 >= var9.x0 || this.player.getVelX() < -192)) {
               label120: {
                  if (var10.x0 - var8.x0 >= 0 || !var8.isRightOf(this.collisionRect, 192)) {
                     var13 = var14;
                     if (rectV.collisionChk(var9)) {
                        break label120;
                     }

                     var13 = var14;
                     if (var10.x1 <= this.collisionRect.x1) {
                        break label120;
                     }

                     var13 = var14;
                     if (this.player.getVelX() > 192) {
                        break label120;
                     }
                  }

                  var13 = 2;
               }
            } else {
               var13 = 3;
            }
         }
      }

      var1.doWhileBeAttack(this.player, var13, this.animationID);
      this.calPreCollision();
   }

   public void initCollision(int var1, int var2, int var3, int var4, int var5) {
      CollisionRect var9 = this.collisionRect;
      int var8 = this.player.posX;
      int var7 = this.player.posY;
      int var6;
      if (this.player.isAntiGravity) {
         var6 = -var2 - var4;
      } else {
         var6 = var2;
      }

      var9.setRect(var8 + var1, var7 + var6, var3, var4);
      if (this.initFlag) {
         var9 = this.preCollisionRect;
         var6 = this.player.posX;
         var7 = this.player.posY;
         if (this.player.isAntiGravity) {
            var2 = -var2 - var4;
         }

         var9.setRect(var6 + var1, var7 + var2, var3, var4);
         this.initFlag = false;
      }

      this.animationID = var5;
   }

   public void reset() {
      this.initFlag = true;
   }
}
