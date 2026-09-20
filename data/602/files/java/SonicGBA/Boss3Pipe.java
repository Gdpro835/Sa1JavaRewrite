package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Boss3Pipe extends EnemyObject {
   private static final int COLLISION_HEIGHT = 2048;
   private static final int COLLISION_WIDTH = 3584;
   private static Animation pipeAni;
   private boolean IsNeedCollision = false;
   private boolean IsNeedDraw = true;
   private int drawLayer = 0;
   private AnimationDrawer pipedrawer;

   protected Boss3Pipe(int var1, int var2, int var3, boolean var4, int var5, int var6) {
      super(var1, var2, var3, 0, 0, 0, 0);
      this.IsNeedCollision = var4;
      this.drawLayer = var6;
      if (pipeAni == null) {
         pipeAni = new Animation("/animation/boss3_pipe");
      }

      this.pipedrawer = pipeAni.getDrawer();
      switch(var5) {
      case 0:
         this.pipedrawer.setTrans(1);
         break;
      case 1:
         this.pipedrawer.setTrans(0);
         break;
      case 2:
         this.pipedrawer.setTrans(7);
         break;
      case 3:
         this.pipedrawer.setTrans(6);
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(pipeAni);
      pipeAni = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      switch(var2) {
      case 2:
      case 3:
         var1.isCrashPipe = true;
         break;
      default:
         var1.isCrashPipe = false;
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.IsNeedCollision && var1 == player) {
         var1.beStop(0, var2, this);
      }

   }

   public void draw(MFGraphics var1) {
      this.refreshCollisionRect(this.posX, this.posY);
      if (this.IsNeedDraw) {
         this.drawInMap(var1, this.pipedrawer, this.posX, this.posY);
      }

      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return this.drawLayer;
   }

   public void logic() {
   }

   public void logic(int var1, int var2) {
      int var3 = this.posX;
      int var4 = this.posY;
      this.posX = var1;
      this.posY = var2;
      this.refreshCollisionRect(this.posX, this.posY);
      this.checkWithPlayer(var3, var4, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1792, var2 - 2048, 3584, 2048);
   }

   public void setisDraw(boolean var1) {
      this.IsNeedDraw = var1;
   }
}
