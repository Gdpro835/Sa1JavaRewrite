package SonicGBA;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class MonkeyBullet extends BulletObject {
   private static final int COLLISION_BOOM_HEIGHT = 1600;
   private static final int COLLISION_BOOM_WIDTH = 1600;
   private static final int COLLISION_HEIGHT = 832;
   private static final int COLLISION_WIDTH = 640;
   private static final int STATE_BOOM = 1;
   private static final int STATE_DROP = 0;
   private static final int boom_cnt_max = 10;
   private static final int drop_cnt_max = 2;
   private int boom_cnt;
   private int drop_cnt;
   private boolean isboom;
   private boolean isbooming;
   private int state;

   protected MonkeyBullet(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4, true);
      if (monkeybulletAnimation == null) {
         monkeybulletAnimation = new Animation("/animation/monkey_bullet");
      }

      this.drawer = monkeybulletAnimation.getDrawer(0, true, 0);
      this.isboom = false;
   }

   public void bulletLogic() {
      int var1 = this.posX;
      int var2 = this.posY;
      switch(this.state) {
      case 0:
         this.isbooming = false;
         this.drawer.setActionId(0);
         this.drawer.setLoop(true);
         this.boom_cnt = 0;
         this.posX += this.velX;
         this.velY += GRAVITY;
         this.posY += this.velY;
         if (this.posY + 416 >= this.getGroundY(this.posX, this.posY)) {
            this.posY = this.getGroundY(this.posX, this.posY) - 416;
            switch(this.drop_cnt) {
            case 0:
               this.velY = -450;
               this.drop_cnt = 1;
               break;
            case 1:
               this.velY = -300;
               this.drop_cnt = 2;
               break;
            case 2:
               this.state = 1;
            }
         }

         this.checkWithPlayer(var1, var2, this.posX, this.posY);
         break;
      case 1:
         if (this.boom_cnt < 10) {
            ++this.boom_cnt;
         } else {
            this.drawer.setActionId(1);
            this.drawer.setLoop(false);
            this.isbooming = true;
         }

         if (this.drawer.checkEnd() && this.isbooming) {
            this.isboom = true;
         }

         this.checkWithPlayer(var1, var2, this.posX, this.posY);
      }

   }

   public boolean chkDestroy() {
      boolean var1;
      if (this.isbooming) {
         var1 = this.isboom;
      } else {
         var1 = super.chkDestroy();
      }

      return var1;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1 == player && player.canBeHurt()) {
         player.beHurt();
         this.state = 1;
         this.boom_cnt = 10;
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.isboom) {
         this.drawInMap(var1, this.drawer);
      }

      this.collisionRect.draw(var1, camera);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3;
      if (!this.isbooming) {
         var3 = this.collisionRect;
         var3.setRect(var1 - 320, var2 - 416, 640, 832);
      } else {
         var3 = this.collisionRect;
         var3.setRect(var1 - 800, var2 - 800, 1600, 1600);
      }

   }
}
