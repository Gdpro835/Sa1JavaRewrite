package SonicGBA;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import Lib.SoundSystem;
import com.sega.engine.action.ACMoveCalculator;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.framework.device.MFGraphics;
import java.util.Vector;

public class PlayerSuperSonic extends PlayerObject {
   private static final int ATTACK_VELOCITY = 1200;
   private static final int HEIGHT = 1024;
   private static final int HURT_COUNT = 20;
   private static final int INIT_RING_NUM = 50;
   private static final int MOVE_MAX_VELOCITY = 960;
   private static final int MOVE_POWER = 240;
   private static final int ROLLING_COUNT = 7;
   private static final int SMALL_JUMP_COUNT = 9;
   private static final int START_JUMP_VELOCITY;
   private static final int SUPER_ANI_ATTACK = 4;
   private static final int SUPER_ANI_ATTACK_EFFECT = 5;
   private static final int SUPER_ANI_DAMAGE = 6;
   private static final int SUPER_ANI_DIE_1 = 7;
   private static final int SUPER_ANI_DIE_2 = 8;
   private static final int SUPER_ANI_STAND = 3;
   private static final int SUPER_ANI_STAR_1 = 9;
   private static final int SUPER_ANI_STAR_2 = 10;
   private static final int VELOCITY_STAY = 400;
   private static final int WIDTH = 1536;
   private int attackEffectCount;
   private AnimationDrawer attackEffectDrawer;
   private boolean attackEffectShow;
   private boolean bossDie;
   private int frameCount;
   private int jumpframe;
   private boolean jumplocked;
   private ACMoveCalculator moveCal;
   private int myAnimationID;
   private int nextStarCount;
   private boolean noRingLose = false;
   private BossExtraPacman pacman;
   private AnimationDrawer shadowDrawer;
   private int[][] shadowPosition;
   private int smallJumpCount;
   private int starCount;
   private Vector starVec;
   private Animation superSonicAcnimation = new Animation("/animation/player/chr_super_sonic");
   private AnimationDrawer superSonicDrawer;

   static {
      START_JUMP_VELOCITY = GRAVITY - 1800;
   }

   public PlayerSuperSonic() {
      this.superSonicDrawer = this.superSonicAcnimation.getDrawer();
      this.shadowDrawer = this.superSonicAcnimation.getDrawer(0, true, 0);
      this.attackEffectDrawer = this.superSonicAcnimation.getDrawer(5, false, 0);
      this.moveCal = new ACMoveCalculator(this, this);
      this.myAnimationID = 3;
      this.drawer = this.superSonicDrawer;
      this.starVec = new Vector();
      this.shadowPosition = new int[3][2];

      for(int var1 = 0; var1 < 3; ++var1) {
         this.shadowPosition[var1][0] = this.posX;
         this.shadowPosition[var1][1] = this.posY;
      }

      ringNum = 50;
   }

   private void addStar() {
      ++this.starCount;
      if (this.starCount >= this.nextStarCount) {
         int var1 = MyRandom.nextInt(2);
         int var2 = MyRandom.nextInt(-400, 0);
         Animation var5 = this.superSonicAcnimation;
         byte var6;
         if (var1 == 0) {
            var6 = 9;
         } else {
            var6 = 10;
         }

         int var4 = this.posX;
         int var3 = this.posY;
         StarEffect var7 = new StarEffect(var5, var6, var4 + 1536, var3 + var2);
         this.starVec.addElement(var7);
         this.starCount = 0;
         this.nextStarCount = MyRandom.nextInt(2, 6);
      }

   }

   private void drawDamage(MFGraphics var1, AnimationDrawer var2, int var3, int var4) {
      int var5 = (this.hurtCount - 20) * 360 / 7;
      Graphics var6 = (Graphics)var1.getSystemGraphics();
      var6.save();
      var6.translate((float)((var3 >> 6) - camera.x), (float)((var4 >> 6) - camera.y));
      var6.rotate((float)var5);
      var2.draw(var1, 0, 0);
      var6.restore();
   }

   private void drawShadow(MFGraphics var1) {
      ++this.frameCount;
      this.frameCount %= 3;
      this.shadowDrawer.setActionId(this.myAnimationID);

      for(int var2 = 0; var2 < this.shadowPosition.length; ++var2) {
         if (this.myAnimationID == 6) {
            this.drawDamage(var1, this.shadowDrawer, this.shadowPosition[var2][0], this.shadowPosition[var2][1]);
         } else {
            if (IsGamePause) {
               this.frameCount = 0;
            }

            if (this.frameCount % 2 == 0) {
               AnimationDrawer var7 = this.shadowDrawer;
               int var6 = this.shadowPosition[var2][0];
               int var3 = camera.x;
               int var4 = this.shadowPosition[var2][1];
               int var5 = camera.y;
               var7.draw(var1, (var6 >> 6) - var3, (var4 >> 6) - var5);
            } else {
               this.shadowDrawer.moveOn();
            }
         }
      }

   }

   private void drawStar(MFGraphics var1) {
      int var3;
      for(int var2 = 0; var2 < this.starVec.size(); var2 = var3 + 1) {
         StarEffect var4 = (StarEffect)this.starVec.elementAt(var2);
         var3 = var2;
         if (var4.draw(var1)) {
            var4.close();
            this.starVec.removeElementAt(var2);
            var3 = var2 - 1;
         }
      }

   }

   private void inputJumpLogic() {
      if (Key.repeat(Key.gLeft)) {
         if (this.velX > -960) {
            this.velX -= 240;
            if (this.velX < -960) {
               this.velX = -960;
            }
         }
      } else if (Key.repeat(Key.gRight)) {
         if (this.velX < 960) {
            this.velX += 240;
            if (this.velX > 960) {
               this.velX = 960;
            }
         }
      } else if (this.velX > 0) {
         this.velX -= 240;
         if (this.velX < 0) {
            this.velX = 0;
         }
      } else if (this.velX < 0) {
         this.velX += 240;
         if (this.velX > 0) {
            this.velX = 0;
         }
      }

      if (this.smallJumpCount > 0) {
         --this.smallJumpCount;
         if (!Key.repeat(16777216)) {
            this.velY += GRAVITY >> 1;
            this.velY += GRAVITY >> 2;
         }
      }

      if (this.attackEffectShow) {
         this.velY = 0;
      } else {
         this.velY += GRAVITY;
      }

      if (Key.press(Key.gSelect | 8388608) && !this.attackEffectShow) {
         this.velX = 1200;
         this.attackEffectShow = true;
         this.attackEffectDrawer.restart();
         this.myAnimationID = 4;
         this.attackEffectCount = 0;
         SoundSystem.getInstance().playSe(7);
      }

   }

   private void inputWalkLogic() {
      if (this.hurtCount < 13) {
         if (Key.repeat(Key.gLeft)) {
            if (this.velX > -960) {
               this.velX -= 240;
               if (this.velX < -960) {
                  this.velX = -960;
               }
            }
         } else if (Key.repeat(Key.gRight)) {
            if (this.velX < 960) {
               this.velX += 240;
               if (this.velX > 960) {
                  this.velX = 960;
               }
            }
         } else if (this.velX > 0) {
            this.velX -= 240;
            if (this.velX < 0) {
               this.velX = 0;
            }
         } else if (this.velX < 0) {
            this.velX += 240;
            if (this.velX > 0) {
               this.velX = 0;
            }
         }

         if (Key.press(16777216)) {
            this.jumplocked = false;
            this.velY = START_JUMP_VELOCITY;
            this.collisionState = 1;
            SoundSystem.getInstance().playSe(11);
            this.smallJumpCount = 9;
         }

         if (Key.press(Key.gSelect | 8388608) && !this.attackEffectShow) {
            this.velX = 1200;
            this.attackEffectShow = true;
            this.attackEffectDrawer.restart();
            this.myAnimationID = 4;
            this.attackEffectCount = 0;
            SoundSystem.getInstance().playSe(7);
         }
      }

   }

   private void jumpRelock() {
      this.jumpframe = 0;
      this.jumplocked = true;
   }

   public void beHurt() {
      if (player.canBeHurt()) {
         this.myAnimationID = 6;
         this.hurtCount = 20;
         SoundSystem.getInstance().playSe(14);
      }

      if (this.pacman != null) {
         this.pacman.setDie();
         this.pacman = null;
      }

   }

   public void closeImpl() {
      Animation.closeAnimationDrawer(this.superSonicDrawer);
      this.superSonicDrawer = null;
      Animation.closeAnimationDrawer(this.shadowDrawer);
      this.shadowDrawer = null;
      Animation.closeAnimationDrawer(this.attackEffectDrawer);
      this.attackEffectDrawer = null;
      Animation.closeAnimation(this.superSonicAcnimation);
      this.superSonicAcnimation = null;
   }

   public void didAfterEveryMove(int var1, int var2) {
      if (this.posX - 768 < MapManager.actualLeftCameraLimit << 6) {
         this.posX = (MapManager.actualLeftCameraLimit << 6) + 768;
         if (this.getVelX() < 0) {
            this.setVelX(0);
         }
      }

      if (MapManager.actualRightCameraLimit != MapManager.getPixelWidth() && this.posX + 768 > MapManager.actualRightCameraLimit << 6) {
         this.posX = (MapManager.actualRightCameraLimit << 6) - 768;
         if (this.getVelX() > 0) {
            this.setVelX(0);
         }
      }

      if (!this.isDead && this.footPointY > MapManager.actualDownCameraLimit << 6) {
         this.posY = MapManager.actualDownCameraLimit << 6;
         this.setDieWithoutSE();
      }

      if (this.leftStopped && this.rightStopped) {
         this.setDieWithoutSE();
      }

      int var3 = this.getGroundY(this.posX, this.posY) - 400;
      switch(this.collisionState) {
      case 0:
         this.posY = var3;
         break;
      case 1:
         if (this.velY > 0 && var3 < this.posY) {
            this.collisionState = 0;
            this.posY = var3;
         }
      }

      super.didAfterEveryMove(var1, var2);
   }

   public void doBossAttackPose(GameObject var1, int var2) {
      if (this.collisionState == 1) {
         this.setVelX(-1243);
         this.attackEffectShow = false;
         this.myAnimationID = 3;
      }

   }

   public void doWhileCollision() {
   }

   public void drawCharacter(MFGraphics var1) {
      if (this.pacman == null) {
         this.drawer.setLoop(true);
         this.drawer.setTrans(0);
         this.drawer.setActionId(this.myAnimationID);
         if (this.myAnimationID == 7) {
            this.drawer.setLoop(false);
         }

         if (!this.isDead) {
            if (this.attackEffectShow && this.attackEffectCount % 2 == 1) {
               this.drawInMap(var1, this.attackEffectDrawer, this.posX, this.posY);
            }

            this.drawShadow(var1);
         }

         if (this.myAnimationID == 6) {
            this.drawDamage(var1, this.drawer, this.posX, this.posY);
         } else if (this.hurtCount % 2 == 0) {
            this.drawInMap(var1, this.drawer, this.posX, this.posY);
         } else {
            this.drawer.moveOn();
            if (this.drawer.checkEnd() && this.myAnimationID == 7) {
               this.myAnimationID = 8;
            }
         }

         if (!this.isDead && this.attackEffectShow) {
            if (this.attackEffectCount % 2 == 0) {
               this.drawInMap(var1, this.attackEffectDrawer, this.posX, this.posY);
            }

            if (!IsGamePause) {
               ++this.attackEffectCount;
            }

            if (this.attackEffectDrawer.checkEnd()) {
               this.attackEffectShow = false;
               if (this.myAnimationID == 4) {
                  this.myAnimationID = 3;
               }
            }
         }

         if (this.isDead && StageManager.isStageTimeover()) {
            this.myAnimationID = 7;
            this.drawer.setLoop(false);
            if (!IsGamePause) {
               this.velY += this.getGravity();
               this.posX += this.velX;
               this.posY += this.velY;
               if (this.posY > (MapManager.getCamera().y + MapManager.CAMERA_HEIGHT << 6) + 4096) {
                  this.posY = (MapManager.getCamera().y + MapManager.CAMERA_HEIGHT << 6) + 4096;
               }
            }
         }

         this.drawStar(var1);
         this.drawCollisionRect(var1);
      }

   }

   public boolean getBossDieFlag() {
      return this.bossDie;
   }

   public void getBossScore() {
      super.getBossScore();
      this.noRingLose = true;
   }

   public int getFocusX() {
      int var2 = super.getFocusX();
      int var1 = SCREEN_WIDTH;
      return var2 + (var1 >> 2);
   }

   public boolean isAttackingEnemy() {
      boolean var1;
      if (!super.isAttackingEnemy() && !this.attackEffectShow) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void logic() {
      if (!MFMain.SUPERINNORMALSTAGE && StageManager.getCurrentZoneId() == 8) MapManager.setCameraUpLimit(MapManager.getPixelHeight() - 200);
      if (this.getBossDieFlag()) {
         timeStopped = true;
      }

      if (PlayerObject.getTimeCount() > 0 && !this.noRingLose && timeCount - lastTimeCount >= 1000) {
         lastTimeCount += 1000;
         if (ringNum > 0) {
            --ringNum;
            if (ringNum <= 10) {
               SoundSystem.getInstance().playSe(30);
            }
         }

         if (ringNum == 0) {
            timeCount -= timeCount % 1000;
            this.setDieWithoutSE();
            if (this.pacman != null) {
               this.pacman.setDie();
            }

            this.pacman = null;
            this.myAnimationID = 7;
         }
      }

      if (this.isDead) {
         timeCount -= timeCount % 1000;
         ringNum = 0;
         if (this.pacman != null) {
            this.pacman.setDie();
            this.pacman = null;
         }

         this.velY += this.getGravity();
         this.posX += this.velX;
         this.posY += this.velY;
         if (this.posY > (MapManager.getCamera().y + MapManager.CAMERA_HEIGHT << 6) + 4096) {
            this.posY = (MapManager.getCamera().y + MapManager.CAMERA_HEIGHT << 6) + 4096;
            if (!this.finishDeadStuff) {
               if (stageModeState == 1) {
                  StageManager.setStageRestart();
               } else if (lifeNum > 0) {
                  --lifeNum;
                  StageManager.setStageRestart();
               } else {
                  StageManager.setStageGameover();
               }

               this.finishDeadStuff = true;
            }
         }
      } else if (this.pacman != null) {
         this.moveCal.actionLogic(0, 0);
      } else {
         this.shadowPosition[0][1] = this.shadowPosition[1][1];
         this.shadowPosition[1][1] = this.shadowPosition[2][1];
         this.shadowPosition[2][1] = this.posY;
         --this.hurtCount;
         if (this.hurtCount == 12) {
            this.myAnimationID = 3;
         }

         if (this.hurtCount < 0) {
            this.hurtCount = 0;
         }

         if (this.hurtCount < 13) {
            switch(this.collisionState) {
            case 0:
               this.inputWalkLogic();
               break;
            case 1:
               this.inputJumpLogic();
            }
         } else {
            this.velY = 0;
            this.velX = -1243;
         }

         this.moveCal.actionLogic(this.velX, this.velY);
         int var1 = (this.velX + 960) * 2 / 3;
         this.shadowPosition[0][0] = this.posX - var1 * 3;
         this.shadowPosition[1][0] = this.posX - var1 * 2;
         this.shadowPosition[2][0] = this.posX - var1;
         this.addStar();
      }

   }

   public void refreshCollisionRectWrap() {
      CollisionRect var3 = this.collisionRect;
      int var2 = this.posX;
      int var1 = this.posY;
      var3.setRect(var2 - 768, var1 - 1024, 1536, 1024);
   }

   public void setBossDieFlag(boolean var1) {
      this.bossDie = var1;
   }

   public void setPackageObj(BossExtraPacman var1) {
      if (this.pacman != null) {
         this.pacman.setDie();
      }

      this.pacman = var1;
   }
}
