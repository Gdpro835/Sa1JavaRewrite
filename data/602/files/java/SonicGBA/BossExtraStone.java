package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.MyRandom;
import com.sega.engine.action.ACMoveCalUser;
import com.sega.engine.action.ACMoveCalculator;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.device.MFGraphics;

class BossExtraStone extends BulletObject implements ACMoveCalUser {
   private static final int[][] VELOCITY_LIST;
   private boolean dead = false;
   private int degree;
   private int degreeSpeed;
   private ACMoveCalculator moveCal;
   private byte[] rect;
   private int type;

   static {
      int[] var1 = new int[]{24, 5};
      int[] var0 = new int[]{168, 5};
      int[] var4 = new int[]{240, 15};
      int[] var2 = new int[]{312, 15};
      int[] var5 = new int[]{456, 15};
      int[] var3 = new int[]{528, 15};
      int[] var6 = new int[]{600, 10};
      VELOCITY_LIST = new int[][]{var1, {96, 5}, var0, var4, var2, {384, 15}, var5, var3, var6};
   }

   protected BossExtraStone(int var1, int var2) {
      super(var1, var2, 0, 0, false);
      int var4 = MyRandom.nextInt(100);
      var2 = 0;
      byte var3 = 0;
      var1 = 0;

      while(true) {
         if (var1 >= VELOCITY_LIST.length) {
            var1 = var3;
            break;
         }

         var2 += VELOCITY_LIST[var1][1];
         if (var4 < var2) {
            var1 = VELOCITY_LIST[var1][0];
            break;
         }

         ++var1;
      }

      var2 = MyRandom.nextInt(100);
      if (var2 < 85) {
         var2 = MyRandom.nextInt(225, 270);
      } else {
         var2 = MyRandom.nextInt(270, 315);
      }

      this.velX = MyAPI.dCos(var2) * var1 / 100;
      this.velY = MyAPI.dSin(var2) * var1 / 100;
      var1 = MyRandom.nextInt(5);
      this.type = var1;
      this.moveCal = new ACMoveCalculator(this, this);
      if (stoneAnimation == null) {
         stoneAnimation = new Animation("/animation/boss_extra_stone");
      }

      var1 = MyRandom.nextInt(2);
      Animation var5 = stoneAnimation;
      var2 = this.type;
      byte var6;
      if (var1 == 0) {
         var6 = 0;
      } else {
         var6 = 2;
      }

      this.drawer = var5.getDrawer(var2, false, var6);
      this.rect = this.drawer.getARect();
      this.degreeSpeed = MyRandom.nextInt(-20, 20);
   }

   private void explode() {
      int var1 = 0;

      while(true) {
         byte var2;
         if (this.type == 4) {
            var2 = 2;
         } else {
            var2 = 3;
         }

         if (var1 >= var2) {
            return;
         }

         AnimationDrawer var6 = stoneAnimation.getDrawer(5, true, 0);
         int var4 = this.posX;
         int var7 = this.posY;
         int var3 = MyRandom.nextInt(-200, 200);
         int var5 = MyRandom.nextInt(-GRAVITY * 2, -GRAVITY);
         BossExtraStone.StonePiece var8 = new BossExtraStone.StonePiece(this, var6, var4, var7, var3, var5);
         addGameObject(var8);
         ++var1;
      }
   }

   public void bulletLogic() {
      this.velY += GRAVITY >> 4;
      this.moveCal.actionLogic(this.velX, this.velY);

      for(this.degree += this.degreeSpeed; this.degree < 0; this.degree += 360) {
      }

      this.degree %= 360;
      if (this.posY > 19200) {
         this.dead = true;
      }

   }

   public boolean chkDestroy() {
      return this.dead;
   }

   public void didAfterEveryMove(int var1, int var2) {
      this.refreshCollisionRect(this.posX, this.posY);
      this.doWhileCollisionWrapWithPlayer();
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (player.isAttackingEnemy()) {
         this.dead = true;
         this.explode();
      } else {
         player.beHurt();
      }

   }

   public void draw(MFGraphics var1) {
      Graphics var4 = (Graphics)var1.getSystemGraphics();
      var4.save();
      float var3 = (float)((this.posX >> 6) - camera.x);
      float var2 = (float)((this.posY >> 6) - camera.y);
      var4.translate(var3, var2);
      var4.rotate((float)this.degree);
      this.drawer.draw(var1, 0, 0);
      var4.restore();
   }

   public void refreshCollisionRect(int var1, int var2) {
      if (this.rect != null) {
         CollisionRect var7 = this.collisionRect;
         byte var5 = this.rect[0];
         byte var4 = this.rect[1];
         byte var3 = this.rect[2];
         byte var6 = this.rect[3];
         var7.setRect((var5 << 6) + var1, (var4 << 6) + var2, var3 << 6, var6 << 6);
      }

   }

   class StonePiece extends GimmickObject {
      private int degree;
      private AnimationDrawer drawer;
      final BossExtraStone this$0;

      protected StonePiece(BossExtraStone var1, AnimationDrawer var2, int var3, int var4, int var5, int var6) {
         super(0, var3, var4, 0, 0, 0, 0);
         this.this$0 = var1;
         this.drawer = var2;
         this.velX = var5;
         this.velY = var6;
         this.degree = 0;
      }

      public boolean chkDestroy() {
         boolean var1;
         if (this.posY >> 6 > camera.y + SCREEN_HEIGHT + 40) {
            var1 = true;
         } else {
            var1 = false;
         }

         return var1;
      }

      public void close() {
         this.drawer = null;
      }

      public void draw(MFGraphics var1) {
         var1.saveCanvas();
         var1.translateCanvas((this.posX >> 6) - camera.x, (this.posY >> 6) - camera.y);
         var1.rotateCanvas((float)this.degree);
         this.drawer.draw(var1, 0, 0);
         var1.restoreCanvas();
      }

      public void logic() {
         this.velY += GRAVITY >> 2;
         this.posX += this.velX;
         this.posY += this.velY;
         this.degree += 20;
         this.degree %= 360;
      }
   }
}
