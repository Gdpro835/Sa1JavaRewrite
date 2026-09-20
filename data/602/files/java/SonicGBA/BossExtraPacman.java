package SonicGBA;

import GameEngine.time.GameTime;

import GameEngine.Key;
import Lib.Animation;
import Lib.MyRandom;
import Lib.SoundSystem;
import com.sega.engine.action.ACMoveCalUser;
import com.sega.engine.action.ACMoveCalculator;
import com.sega.mobile.framework.device.MFGraphics;

class BossExtraPacman extends BulletObject implements ACMoveCalUser {
   private static final int COLLISION_HEIGHT = 1536;
   private static final int COLLISION_OFFSET_Y = -960;
   private static final int COLLISION_WIDTH = 1024;
   private static final int PACMAN_LIFE = 64;
   private static final int RING_NUM = 7;
   private static final int STATE_BEGIN = 0;
   private static final int STATE_BROKE = 6;
   private static final int STATE_DASH = 2;
   private static final int STATE_DEAD = 3;
   private static final int STATE_PACKAGED = 5;
   private static final int STATE_PACKAGING = 4;
   private static final int STATE_SEARCH = 1;
   private int count;
   private boolean isHigher;
   private ACMoveCalculator moveCal;
   private int packageOffset;
   private int[][] pieceInfo = new int[2][4];
   private int state;
   private PlayerSuperSonic superSonic;

   protected BossExtraPacman(int var1, int var2) {
      super(var1, var2, 0, 0, false);
      if (pacmanAnimation == null) {
         pacmanAnimation = new Animation("/animation/boss_extra_pacman");
      }

      this.drawer = pacmanAnimation.getDrawer(0, true, 0);
      this.state = 0;
      this.count = GameTime.set(this, "count", 0);
      this.moveCal = new ACMoveCalculator(this, this);
   }

   private void doBroke() {
      SoundSystem.getInstance().playSe(29);
      if (this.pieceInfo != null) {
         this.pieceInfo[0][0] = this.posX - 640;
         this.pieceInfo[1][0] = this.posX + 640;
         this.pieceInfo[0][1] = this.posY;
         this.pieceInfo[1][1] = this.posY;
         this.pieceInfo[0][2] = -200;
         this.pieceInfo[1][2] = 200;
         this.pieceInfo[0][3] = -200;
         this.pieceInfo[1][3] = -200;
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(pacmanAnimation);
      pacmanAnimation = null;
   }

   private void ringBroke() {
      for(int var1 = 0; var1 < 7; ++var1) {
         int var3 = this.posX;
         int var4 = this.posY;
         int var5 = MyRandom.nextInt(-400, 400);
         int var2 = MyRandom.nextInt(-800 - GRAVITY * 2, -300 - GRAVITY * 1);
         long var6 = systemClock;
         MoveRingObject var8 = new MoveRingObject(var3, var4, var5, var2, 1, var6, 14);
         addGameObject(var8);
      }

   }

   public void bulletLogic() {
      if (player instanceof PlayerSuperSonic && ((PlayerSuperSonic)player).getBossDieFlag() && this.state != 6) {
         this.setDie();
      }

      this.count = GameTime.advance(this, "count", this.count, 1);
      switch(this.state) {
      case 0:
         this.moveCal.moveVelocity(-240, 0);
         if (this.count > 12) {
            this.state = 1;
            boolean var2;
            if (this.posY < player.posY) {
               var2 = true;
            } else {
               var2 = false;
            }

            this.isHigher = var2;
         }
         break;
      case 1:
         if (this.posY < player.posY) {
            if (!this.isHigher) {
               this.state = 2;
               break;
            }

            this.moveCal.moveVelocity(0, Math.min(player.posY - this.posY, 240));
         } else if (this.posY > player.posY) {
            if (this.isHigher) {
               this.state = 2;
               break;
            }

            this.moveCal.moveVelocity(0, Math.max(player.posY - this.posY, -240));
         }

         if (player.posY == this.posY) {
            this.state = 2;
         }
         break;
      case 2:
         this.moveCal.moveVelocity(-720, 0);
      case 3:
      default:
         break;
      case 4:
         this.posX = player.posX;
         this.posY = player.posY;
         if (this.drawer.checkEnd()) {
            this.state = 5;
            this.count = GameTime.set(this, "count", 0);
            if (this.superSonic != null) {
               this.superSonic.setPackageObj(this);
            }
         }
         break;
      case 5:
         this.posX = player.posX;
         this.posY = player.posY;
         if (Key.press(Key.gLeft) && this.packageOffset != -256) {
            this.packageOffset = -256;
            this.count += 5;
         } else if (Key.press(Key.gRight) && this.packageOffset != 256) {
            this.packageOffset = 256;
            this.count += 5;
         } else {
            this.packageOffset = 0;
         }

         if (this.count > 64) {
            this.superSonic.setPackageObj((BossExtraPacman)null);
            this.state = 6;
            this.doBroke();
         }
         break;
      case 6:
         for(int var1 = 0; var1 < this.pieceInfo.length; ++var1) {
            int[] var3 = this.pieceInfo[var1];
            var3[3] = GameTime.advance(var3, String.valueOf(3), var3[3], GRAVITY >> 2);
            var3 = this.pieceInfo[var1];
            var3[0] = GameTime.advance(var3, String.valueOf(0), var3[0], this.pieceInfo[var1][2]);
            var3 = this.pieceInfo[var1];
            var3[1] = GameTime.advance(var3, String.valueOf(1), var3[1], this.pieceInfo[var1][3]);
         }
      }

   }

   public boolean chkDestroy() {
      boolean var1;
      if (this.state == 3 || this.posX < 0 || this.state == 6 && this.pieceInfo[0][1] >> 6 > camera.y + SCREEN_HEIGHT + 40) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void didAfterEveryMove(int var1, int var2) {
      this.refreshCollisionRect(this.posX, this.posY);
      this.doWhileCollisionWrapWithPlayer();
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1 instanceof PlayerSuperSonic) {
         this.superSonic = (PlayerSuperSonic)var1;
         if (this.state == 1 || this.state == 2) {
            if (var1.isAttackingEnemy()) {
               this.state = 6;
               this.doBroke();
               this.ringBroke();
            } else {
               this.state = 4;
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      switch(this.state) {
      case 0:
         this.drawer.setActionId(0);
         this.drawer.setLoop(false);
         this.drawInMap(var1, this.drawer);
         break;
      case 1:
      case 2:
         this.drawer.setActionId(1);
         this.drawer.setLoop(true);
         this.drawInMap(var1, this.drawer);
         break;
      case 3:
         this.drawer.setActionId(4);
         this.drawInMap(var1, this.drawer);
         break;
      case 4:
         this.drawer.setActionId(2);
         this.drawer.setLoop(false);
         this.drawInMap(var1, this.drawer);
         break;
      case 5:
         this.drawer.setActionId(3);
         this.drawInMap(var1, this.drawer, this.posX + this.packageOffset, this.posY);
         break;
      case 6:
         for(int var2 = 0; var2 < this.pieceInfo.length; ++var2) {
            this.drawer.setActionId(var2 + 4);
            this.drawInMap(var1, this.drawer, this.pieceInfo[var2][0], this.pieceInfo[var2][1]);
         }
      }

      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 2;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1, var2 - 960, 1024, 1536);
   }

   public void setDie() {
      this.state = 6;
      this.doBroke();
   }
}
