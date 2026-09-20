package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class Leaf extends GimmickObject {
   private static final int COLLISION_HEIGHT = 4608;
   private static final int COLLISION_WIDTH = 1024;
   private static Animation leafAnimation;
   private boolean isStart;
   private AnimationDrawer leafdrawer;

   protected Leaf(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (leafAnimation == null) {
         leafAnimation = new Animation("/animation/bush");
      }

      this.leafdrawer = leafAnimation.getDrawer(0, false, 0);
      this.isStart = false;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      CollisionRect var4 = this.collisionRect;
      int var3 = player.getCheckPositionX();
      var2 = player.getCheckPositionY();
      if (var4.collisionChk(var3, var2) && !this.isStart) {
         this.isStart = true;
         SoundSystem.getInstance().playSe(49);
      }

   }

   public void doWhileNoCollision() {
      this.isStart = false;
      this.leafdrawer.restart();
   }

   public void draw(MFGraphics var1) {
      if (this.isStart) {
         this.drawInMap(var1, this.leafdrawer, this.posX, this.posY + 1024);
      }

      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 2;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 512, var2, 1024, 4608);
   }
}
