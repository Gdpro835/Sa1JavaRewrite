package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Boss2Spring extends EnemyObject {
   private static final int SPRING_DAMPING = 2;
   private static final int SPRING_FLYING = 0;
   private static final int SPRING_WAITING = 1;
   private static Animation springAni;
   private int COLLISION_HEIGHT = 2240;
   private int COLLISION_WIDTH = 2048;
   private boolean IsAttack = false;
   private boolean IsBossBroken;
   private boolean IsEnd;
   private boolean IsHurt;
   private AnimationDrawer springdrawer;
   private int state;
   private int velocity;

   protected Boss2Spring(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
      if (springAni == null) {
         springAni = new Animation("/animation/boss2_spring");
      }

      this.springdrawer = springAni.getDrawer(0, true, 0);
      this.IsEnd = false;
      this.IsBossBroken = false;
      this.IsAttack = false;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(springAni);
      springAni = null;
   }

   public void changeAniState(AnimationDrawer var1, int var2, boolean var3) {
      if (this.velocity > 0) {
         var1.setActionId(var2);
         var1.setTrans(2);
         var1.setLoop(var3);
      } else {
         var1.setActionId(var2);
         var1.setTrans(0);
         var1.setLoop(var3);
      }

   }

   public void close() {
      this.springdrawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && var1 == player && var2 != 1 && !this.IsHurt && this.IsAttack && !this.IsBossBroken) {
         player.beHurt();
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.springdrawer);
         this.drawCollisionRect(var1);
      }

   }

   public boolean getEndState() {
      return this.IsEnd;
   }

   public void getIsHurt(boolean var1) {
      this.IsHurt = var1;
   }

   public int getSpringHeight() {
      int var1;
      if (springAni != null) {
         var1 = this.springdrawer.getCurrentFrameHeight() << 6;
      } else {
         var1 = 0;
      }

      return var1;
   }

   public void logic() {
   }

   public void logic(int var1, int var2, int var3, int var4) {
      this.posX = var1;
      this.posY = var2;
      if (!this.dead) {
         this.COLLISION_HEIGHT = this.getSpringHeight();
         var1 = this.posX;
         var2 = this.posY;
         switch(var3) {
         case 2:
            if (this.springdrawer.checkEnd()) {
               this.IsEnd = true;
            }
         case 0:
         case 1:
         default:
            this.checkWithPlayer(var1, var2, this.posX, this.posY);
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var7 = this.collisionRect;
      int var5 = this.COLLISION_WIDTH;
      int var6 = this.COLLISION_HEIGHT;
      int var4 = this.COLLISION_WIDTH;
      int var3 = this.COLLISION_HEIGHT;
      var7.setRect(var1 - (var5 >> 1), var2 - var6, var4, var3);
   }

   public void setAttackable(boolean var1) {
      this.IsAttack = var1;
   }

   public void setBossBrokenState(boolean var1) {
      this.IsBossBroken = var1;
   }

   public void setSpringAni(int var1, boolean var2) {
      this.changeAniState(this.springdrawer, var1, var2);
      this.IsEnd = false;
   }
}
