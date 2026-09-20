package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class SeabedVolcanoAsynBase extends GimmickObject {
   private static final int COLLISION_HEIGHT = 768;
   private static final int COLLISION_WIDTH = 4096;
   private static final int FIRE_OFFSET_Y = 192;
   private static byte count;
   private AnimationDrawer drawer;
   public SeabedVolcanoAsynHurt sh;
   public SeabedVolcanoAsynPlatform sp;

   protected SeabedVolcanoAsynBase(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (firemtAnimation == null) {
         firemtAnimation = new Animation("/animation/firemt");
      }

      if (firemtAnimation != null) {
         this.drawer = firemtAnimation.getDrawer(0, false, 0);
      }

      this.sh = new SeabedVolcanoAsynHurt(this.posX, this.posY, this);
      this.sp = new SeabedVolcanoAsynPlatform(this.posX, this.posY, this);
      GameObject.addGameObject(this.sh, this.posX, this.posY);
      GameObject.addGameObject(this.sp, this.posX, this.posY);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(firemtAnimation);
      firemtAnimation = null;
   }

   public static void staticLogic() {
      ++count;
      count = (byte)(count % 50);
      if (count == 25) {
         SeabedVolcanoAsynPlatform.shot();
      }

      SeabedVolcanoAsynPlatform.staticLogic();
   }

   public void close() {
      this.drawer = null;
      this.sp = null;
      this.sh = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
   }

   public void draw(MFGraphics var1) {
      if (count == 22) {
         this.drawer.setActionId(1);
         this.drawer.restart();
         SoundSystem var3 = soundInstance;
         SoundSystem var2 = soundInstance;
         var3.playSe(53);
         count = 23;
      }

      this.drawInMap(var1, this.drawer, this.posX, this.posY + 192);
      this.sp.drawPlatform(var1);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 2048, var2 - 768, 4096, 768);
   }
}
