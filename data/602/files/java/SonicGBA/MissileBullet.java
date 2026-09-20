package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class MissileBullet extends BulletObject {
   private static final int COLLISION_HEIGHT = 768;
   private static final int COLLISION_WIDTH = 1920;
   private static final int SIDE_LEFT;
   private static final int SIDE_RIGHT = 607744;
   private AnimationDrawer boomdrawer;
   private int flyCounter = 0;
   private int frame;
   boolean isBoom = false;
   private boolean isboomed;
   private boolean isbooming;
   private int mvel;
   private int velA = 64;
   private int velA2 = 256;

   static {
      SIDE_LEFT = 607744 - (SCREEN_WIDTH << 6);
   }

   protected MissileBullet(int var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4, true);
      if (missileAnimation == null) {
         missileAnimation = new Animation("/animation/missile");
      }

      if (var3 > 0) {
         this.velA = Math.abs(this.velA);
         this.velA2 = Math.abs(this.velA2);
         this.drawer = missileAnimation.getDrawer(0, true, 2);
      } else if (var3 < 0) {
         this.velA = -Math.abs(this.velA);
         this.velA2 = -Math.abs(this.velA2);
         this.drawer = missileAnimation.getDrawer(0, true, 0);
      }

      this.mvel = var3;
      if (boomAnimation == null) {
         boomAnimation = new Animation("/animation/boom");
      }

      this.boomdrawer = boomAnimation.getDrawer(0, true, 0);
      this.isboomed = false;
      SoundSystem.getInstance().playSequenceSe(68);
   }

   private void missleNoiseControl() {
      if (this.IsHitted() || !this.isInCamera()) {
         int var1 = SoundSystem.getInstance().getPlayingLoopSeIndex();
         if (var1 == 68) {
            SoundSystem.getInstance().stopLoopSe();
         }
      }

      if (this.IsHitted() && !this.isboomed) {
         this.isboomed = true;
      }

   }

   public void bulletLogic() {
      int var2 = this.posX;
      int var1 = this.posY;
      this.missleNoiseControl();
      if (!this.IsHitted()) {
         this.flyCounter = GameTime.advance(this, "flyCounter", this.flyCounter, 1);
         if (this.flyCounter < 8) {
            this.velX = GameTime.advance(this, "velX", this.velX, this.velA);
         } else {
            this.velX = GameTime.advance(this, "velX", this.velX, this.velA2);
         }

         this.posX = GameTime.advancePosition(this, "posX", this.posX, "velX", this.velX);
         int var3 = this.posY;
         int var4 = player.getCheckPositionY();
         this.velY = -(var3 - var4 >> 3);
         this.posY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
      } else {
         this.boomdrawer.setActionId(0);
         this.boomdrawer.setLoop(false);
         this.isbooming = true;
      }

      this.checkWithPlayer(var2, var1, this.posX, this.posY);
   }

   public boolean chkDestroy() {
      boolean var1;
      if (!super.chkDestroy() && !this.boomdrawer.checkEnd() && !this.screenOutChek() && this.isInCamera()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void close() {
      super.close();
      this.boomdrawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      super.doWhileCollision(var1, var2);
      if (!this.isBoom) {
         SoundSystem.getInstance().playSe(35);
         this.isBoom = true;
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.isbooming) {
         this.drawInMap(var1, this.drawer);
      }

      if (this.isbooming && !this.boomdrawer.checkEnd()) {
         this.drawInMap(var1, this.boomdrawer);
      }

      this.collisionRect.draw(var1, camera);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3;
      if (this.posX < player.getFootPositionX()) {
         var3 = this.collisionRect;
         var3.setRect(var1 - 1920, var2 - 384, 1920, 768);
      } else {
         var3 = this.collisionRect;
         var3.setRect(var1, var2 - 384, 1920, 768);
      }

   }

   public boolean screenOutChek() {
      boolean var1;
      if (this.mvel > 0) {
         if (this.posX > 607744) {
            var1 = true;
         } else {
            var1 = false;
         }
      } else if (this.posX < SIDE_LEFT) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
