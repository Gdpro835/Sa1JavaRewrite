package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class BossF1 extends BossObject {
   // 256 world units at the authored 15 units/frame. This is a duration, not a tick.
   private static final double SWING_PHASE_SECONDS = 256.0 / 15.0 * GameTime.ASSET_TIME_UNIT_SECONDS;
   private static final double DEPLOY_SECONDS = 17.0 * GameTime.ASSET_TIME_UNIT_SECONDS;
   private static final int BALL_RANGE = 2048;
   private static final int BALL_RADIUS = 4992;
   private static final int BALL_START_Y = 42240;
   private static final int BOSS_1_STOP_POSY = 39936;
   private static final int BOSS_2_STOP_POSY = 43264;
   private static final int BOSS_3_STOP_POSX = 55296;
   private static final int BOSS_3_STOP_POSY = 43008;
   private static final int BOSS_LEFT_POSX = 53760;
   private static final int BOSS_MOVE_1_POSX = 54528;
   private static final int BOSS_MOVE_1_POSY = 42880;
   private static final int BOSS_MOVE_2_POSY = 43136;
   private static final int BOSS_RIGHT_POSX = 56832;
   private static final int BOSS_START_POSX = 59392;
   private static final int BOSS_START_POSY = 33920;
   private static final int BROKEN_OFFSET_Y = 1280;
   private static final int BROKEN_STOP_POSY = 46336;
   private static final int COLLISION_HEIGHT = 2560;
   private static final int COLLISION_HEIGHT_OFFSET = -384;
   private static final int COLLISION_WIDTH = 3008;
   private static final int END_POSX = 117120;
   private static final int ENTER_SCREEN_FRAME_MAX = 18;
   private static final int ENTER_SPEED_1_Y = 270;
   private static final int ENTER_SPEED_3_X = -240;
   private static final int ESCAPE_OUT_POSX = 65536;
   private static final int ESCAPE_SPEED_X = 480;
   private static final int ESCAPE_STOP_POSY = 45056;
   private static final int FACE_BROKEN = 3;
   private static final int FACE_HURT = 2;
   private static final int FACE_NORMAL = 0;
   private static final int FACE_OFFSET_X = 192;
   private static final int FACE_OFFSET_Y = -1920;
   private static final int FACE_SMILE = 1;
   private static final int INIT_STOP_POSX = 55296;
   private static final int INIT_STOP_POSY = 43008;
   private static final int LAUGH_TIME = 10;
   private static final int MACHINE_ESCAPE_MOVE = 3;
   private static final int MACHINE_ESCAPE_WAIT = 2;
   private static final int MACHINE_MOVE = 1;
   private static final int MACHINE_MOVE_HURT = 5;
   private static final int MACHINE_WAIT = 0;
   private static final int MACHINE_WAIT_HURT = 4;
   private static final int PRO_STEP_BOTTOM_2_LEFT = 0;
   private static final int PRO_STEP_BOTTOM_2_RIGHT = 2;
   private static final int PRO_STEP_LEFT_2_BOTTOM = 1;
   private static final int PRO_STEP_RIGHT_2_BOTTOM = 3;
   private static final int RING_RANGE = 896;
   private static final int SHOW_BOSS_END = 4;
   private static final int SHOW_BOSS_ENTER_1 = 0;
   private static final int SHOW_BOSS_ENTER_2 = 1;
   private static final int SHOW_BOSS_ENTER_3 = 2;
   private static final int SHOW_BOSS_GOTO_PRO = 5;
   private static final int SHOW_BOSS_LAUGH = 3;
   private static final int SIDE_DOWN1 = 784;
   private static final int SIDE_DOWN2 = 784;
   private static final int SIDE_LEFT1 = 864;
   private static final int SIDE_RIGHT1 = 864;
   private static final int SIDE_UP0 = 544;
   private static final int SIDE_UP1 = 624;
   private static final int SIDE_UP2 = 544;
   private static final int STATE_BROKEN = 3;
   private static final int STATE_ENTER_SHOW = 1;
   private static final int STATE_ESCAPE = 4;
   private static final int STATE_INIT = 0;
   private static final int STATE_PRO = 2;
   private static Animation ballAni;
   private static final int cnt_max = 8;
   private static Animation faceAni;
   private static Animation machineAni;
   private BossF1Ball ball;
   private AnimationDrawer[] ballDrawer;
   private int[][] ballPos;
   private double deploySeconds;
   private double swingSeconds;
   private BossBroken bossbroken;
   private boolean directTrans = false;
   private boolean displayFlag;
   private int drop_cnt;
   private int drop_vely;
   private int enter_screen_frame_cn;
   private AnimationDrawer faceDrawer;
   private int face_cnt;
   private int face_state;
   private boolean isDisplayBall = false;
   private int laugh_cn;
   private AnimationDrawer machineDrawer;
   private int machine_state;
   private int pro_step;
   private int show_step;
   private int state;
   private int velocity;

   protected BossF1(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.posX = 59392;
      this.posY = 33920;
      if (machineAni == null) {
         machineAni = new Animation("/animation/bossf1_machine");
      }

      this.machineDrawer = machineAni.getDrawer(0, true, 0);
      if (faceAni == null) {
         faceAni = new Animation("/animation/bossf2_face");
      }

      this.faceDrawer = faceAni.getDrawer(0, true, 0);
      if (ballAni == null) {
         ballAni = new Animation("/animation/bossf1_ring_ball");
      }

      this.ballDrawer = new AnimationDrawer[6];

      for(var4 = 0; var4 < 4; ++var4) {
         this.ballDrawer[var4] = ballAni.getDrawer(0, true, 0);
      }

      this.ballDrawer[4] = ballAni.getDrawer(1, true, 0);
      this.ballDrawer[5] = ballAni.getDrawer(2, true, 0);
      this.ballPos = new int[6][2];

      for(var4 = 0; var4 < 6; ++var4) {
         this.ballPos[var4][0] = 55296;
         this.ballPos[var4][1] = 42240;
      }

      this.isDisplayBall = false;
      this.directTrans = false;
      this.ball = new BossF1Ball(var1, var2, var3, 0, 0, 0, 0);
      addGameObject(this.ball, this.posX >> 6, this.posY >> 6);
      this.displayFlag = false;
      this.state = 0;
      this.HP = 4;
   }

   private void bossStateChange() {
      if (this.ball != null && this.ball.getPlayerHurt()) {
         this.face_state = 1;
         this.ball.resetPlayerHurt();
         this.face_cnt = GameTime.set(this, "face_cnt", 0);
      }

      if (this.face_state != 0) {
         if (this.face_cnt < 8) {
            this.face_cnt = Math.min(8, GameTime.advance(this, "face_cnt", this.face_cnt, 1));
         } else {
            if (this.machine_state == 4) {
               this.machine_state = 0;
            }

            if (this.machine_state == 5) {
               this.machine_state = 1;
            }

            this.face_state = 0;
            this.face_cnt = GameTime.set(this, "face_cnt", 0);
         }
      }

      this.changeAniState(this.machineDrawer, this.machine_state);
      this.changeAniState(this.faceDrawer, this.face_state);
   }

   private void changeAniState(AnimationDrawer var1, int var2) {
      if (this.velocity > 0) {
         var1.setActionId(var2);
         var1.setTrans(2);
         var1.setLoop(true);
      } else {
         var1.setActionId(var2);
         var1.setTrans(0);
         var1.setLoop(true);
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(machineAni);
      Animation.closeAnimation(faceAni);
      Animation.closeAnimation(ballAni);
      machineAni = null;
      faceAni = null;
      ballAni = null;
   }

   private static double smooth(double t) {
      return t * t * (3.0 - 2.0 * t);
   }

   private static int between(int from, int to, double t) {
      return (int) Math.round(from + (to - from) * t);
   }

   /** One authoritative fixed-point pose for chain rendering and the damaging ball. */
   private void positionBall(double offsetX, double offsetY) {
      for (int i = 0; i < 4; i++) {
         double fraction = (i * 14 + 13) / 78.0;
         this.ballPos[i][0] = (int) Math.round(this.posX + offsetX * fraction);
         this.ballPos[i][1] = (int) Math.round(this.posY + offsetY * fraction);
      }
      this.ballPos[4][0] = (int) Math.round(this.posX + offsetX);
      this.ballPos[4][1] = (int) Math.round(this.posY + offsetY);
      this.ballPos[5][0] = this.posX;
      this.ballPos[5][1] = this.posY + 256;
      this.ball.logic(this.ballPos[4][0], this.ballPos[4][1]);
   }

   private double deployBall(double seconds) {
      double remaining = Math.max(0.0, this.deploySeconds + seconds - DEPLOY_SECONDS);
      this.deploySeconds = Math.min(DEPLOY_SECONDS, this.deploySeconds + seconds);
      double progress = smooth(this.deploySeconds / DEPLOY_SECONDS);
      for (int i = 0; i < this.ballPos.length; i++) {
         int target = i == 5 ? this.posY + 256
               : this.posY + (i == 4 ? BALL_RADIUS : (i * 14 + 13) * 64);
         this.ballPos[i][0] = this.posX;
         this.ballPos[i][1] = between(BALL_START_Y, target, progress);
      }
      return remaining;
   }

   private void openingSwing(double seconds) {
      this.swingSeconds += seconds;
      if (this.swingSeconds + 1e-10 >= SWING_PHASE_SECONDS * 2.0) {
         // Carry the remainder into combat, never discard a long display interval.
         this.swingSeconds = Math.max(0.0, this.swingSeconds - SWING_PHASE_SECONDS * 2.0) % (SWING_PHASE_SECONDS * 4.0);
         this.state = STATE_PRO;
         this.pro_step = PRO_STEP_BOTTOM_2_LEFT;
         this.machine_state = MACHINE_WAIT;
         this.directTrans = false;
         battlePose();
         return;
      }
      boolean returning = this.swingSeconds + 1e-10 >= SWING_PHASE_SECONDS;
      this.show_step = returning ? SHOW_BOSS_GOTO_PRO : SHOW_BOSS_END;
      double t = Math.max(0.0, Math.min(1.0, this.swingSeconds / SWING_PHASE_SECONDS - (returning ? 1.0 : 0.0)));
      double travel = smooth(t);
      this.posX = between(returning ? BOSS_MOVE_1_POSX : INIT_STOP_POSX,
            returning ? BOSS_LEFT_POSX : BOSS_MOVE_1_POSX, travel);
      this.posY = between(returning ? BOSS_MOVE_1_POSY : INIT_STOP_POSY,
            returning ? BOSS_MOVE_2_POSY : BOSS_MOVE_1_POSY, travel);
      // Ease out of the deployment; join the periodic swing with matching angular velocity.
      double angle = Math.PI * 0.5 * (returning ? Math.cos(t * Math.PI * 0.5) : smooth(t));
      positionBall(BALL_RADIUS * Math.sin(angle), BALL_RADIUS * Math.cos(angle));
   }

   private void battlePose() {
      double phaseTime = this.swingSeconds / SWING_PHASE_SECONDS;
      double boundary = Math.rint(phaseTime);
      if (Math.abs(phaseTime - boundary) < 1e-10) phaseTime = boundary;
      int phase = (int) phaseTime % 4;
      double t = smooth(phaseTime - Math.floor(phaseTime));
      if (phase != this.pro_step) {
         this.pro_step = phase;
         this.machine_state = (phase & 1) == 0 ? MACHINE_WAIT : MACHINE_MOVE;
         if ((phase & 1) != 0) this.face_state = FACE_NORMAL;
      }
      this.directTrans = phase == 1 || phase == 2;
      this.velocity = this.directTrans ? 180 : -180;
      switch (phase) {
         case 0:
            this.posX = BOSS_LEFT_POSX;
            this.posY = between(BOSS_MOVE_2_POSY, BOSS_MOVE_1_POSY, t);
            break;
         case 1:
            this.posX = between(BOSS_LEFT_POSX, BOSS_RIGHT_POSX, t);
            this.posY = between(BOSS_MOVE_1_POSY, BOSS_MOVE_2_POSY, t);
            break;
         case 2:
            this.posX = BOSS_RIGHT_POSX;
            this.posY = between(BOSS_MOVE_2_POSY, BOSS_MOVE_1_POSY, t);
            break;
         default:
            this.posX = between(BOSS_RIGHT_POSX, BOSS_LEFT_POSX, t);
            this.posY = between(BOSS_MOVE_1_POSY, BOSS_MOVE_2_POSY, t);
      }
      // Sample a continuous angle, rather than integrating Y then snapping it to 0/radius.
      double angle = -Math.PI * 0.5 * Math.sin(phaseTime * Math.PI * 0.5);
      positionBall(BALL_RADIUS * Math.sin(angle), BALL_RADIUS * Math.cos(angle));
   }

   private void drawPart(MFGraphics g, AnimationDrawer drawer, int x, int y) {
      g.saveCanvas();
      try {
         // drawInMap truncates to game pixels; retain its 1/64-pixel remainder on the GPU.
         g.translateCanvas((x & 63) / 64f, (y & 63) / 64f);
         this.drawInMap(g, drawer, x, y);
      } finally {
         g.restoreCanvas();
      }
   }

   public void close() {
      this.machineDrawer = null;
      this.faceDrawer = null;
      this.bossbroken = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (this.state != 3 && this.state != 4 && this.state != 0 && (this.state != 1 || this.show_step >= 4) && this.face_state != 2) {
         --this.HP;
         player.doBossAttackPose(this, var2);
         if (this.HP > 0) {
            if (this.machine_state == 0) {
               this.machine_state = 4;
            }

            if (this.machine_state == 1) {
               this.machine_state = 5;
            }

            this.face_state = 2;
            this.face_cnt = GameTime.set(this, "face_cnt", 0);
         } else {
            this.state = 3;
            this.face_state = 3;
            this.machine_state = 0;
            BossBroken var4 = new BossBroken(28, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
            this.bossbroken = var4;
            addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
            this.directTrans = true;
            this.drop_cnt = 0;
         }

         if (this.HP == 0) {
            SoundSystem.getInstance().playSe(35);
         } else {
            SoundSystem.getInstance().playSe(34);
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && this.state != 3 && this.state != 4 && (this.state != 1 || this.show_step >= 4) && var1 == player) {
         if (player.isAttackingEnemy()) {
            if (this.face_state != 2) {
               --this.HP;
               player.doBossAttackPose(this, var2);
               if (this.HP > 0) {
                  if (this.machine_state == 0) {
                     this.machine_state = 4;
                  }

                  if (this.machine_state == 1) {
                     this.machine_state = 5;
                  }

                  this.face_state = 2;
                  this.face_cnt = GameTime.set(this, "face_cnt", 0);
               } else {
                  this.state = 3;
                  this.face_state = 3;
                  this.machine_state = 0;
                  BossBroken var3 = new BossBroken(28, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
                  this.bossbroken = var3;
                  addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
                  this.directTrans = true;
                  this.drop_cnt = 0;
               }

               if (this.HP == 0) {
                  SoundSystem.getInstance().playSe(35);
               } else {
                  SoundSystem.getInstance().playSe(34);
               }
            }
         } else if (this.state != 3 && this.state != 4 && this.machine_state != 4 && this.machine_state != 5 && this.face_state != 2) {
            player.beHurt();
            this.face_state = 1;
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (this.displayFlag && !this.dead) {
         if (this.state >= 1) {
            if (this.isDisplayBall) {
               for(int var2 = 0; var2 < 6; ++var2) {
                  this.drawPart(var1, this.ballDrawer[var2], this.ballPos[var2][0], this.ballPos[var2][1]);
               }
            }

            this.drawPart(var1, this.machineDrawer, this.posX, this.posY);
            if (!this.directTrans) {
               this.drawPart(var1, this.faceDrawer, this.posX + 192, this.posY - 1920);
            } else {
               this.drawPart(var1, this.faceDrawer, this.posX - 192, this.posY - 1920);
            }

            if (this.ball != null) {
               this.ball.draw(var1);
            }

            if (this.state == 3) {
               this.bossbroken.draw(var1);
            }
         }

         this.drawCollisionRect(var1);
      }

   }

   public int getPaintLayer() {
      return 3;
   }

   public void logic() {
      if (IsGamePause || GameTime.deltaSeconds() <= 0.0) return;
      if (!this.dead) {
         int var3 = this.posX;
         int var4 = this.posY;
         if (this.state > 0 && this.state < 4) {
            isBossEnter = true;
         } else if (this.state == 4) {
            isBossEnter = false;
         }

         int[] var5;
         label202:
         switch(this.state) {
         case 0:
            if (player.getFootPositionX() < 117120) {
               if (player.getFootPositionX() >= 55296) {
                  MapManager.setCameraLeftLimit(864 - SCREEN_WIDTH / 2);
                  MapManager.setCameraRightLimit(SCREEN_WIDTH / 2 + 864);
               }

               if (player.getFootPositionX() >= 55296 && player.getFootPositionX() < SCREEN_WIDTH / 2 + 864 + 20 << 6 && player.getFootPositionY() >= 43008) {
                  this.state = 1;
                  bossFighting = true;
                  bossID = 28;
                  MapManager.setCameraUpLimit(624);
                  MapManager.setCameraDownLimit(784);
                  MapManager.setCameraLeftLimit(864 - SCREEN_WIDTH / 2);
                  MapManager.setCameraRightLimit(SCREEN_WIDTH / 2 + 864);
                  this.show_step = 0;
                  this.enter_screen_frame_cn = GameTime.set(this, "enter_screen_frame_cn", 0);
                  SoundSystem.getInstance().playBgm(46);
                  this.displayFlag = true;
               }
            }
            break;
         case 1:
            switch(this.show_step) {
            case 0:
               if (this.enter_screen_frame_cn < 18) {
                  this.enter_screen_frame_cn = Math.min(18, GameTime.advance(this, "enter_screen_frame_cn", this.enter_screen_frame_cn, 1));
               } else {
                  this.posY = GameTime.advance(this, "posY", this.posY, 270);
                  if (this.posY >= 39936) {
                     this.posY = 39936;
                     this.show_step = 1;
                  }
               }
               break label202;
            case 1:
               this.posY = GameTime.advance(this, "posY", this.posY, 270);
               if (this.posY >= 43264) {
                  this.posY = 43264;
                  this.show_step = 2;
                  this.changeAniState(this.machineDrawer, 1);
               }
               break label202;
            case 2:
               this.posX = GameTime.advance(this, "posX", this.posX, -(240));
               if (this.posX > 55296) {
                  break label202;
               }

               this.posX = 55296;
               this.posY = 43008;
               this.show_step = 3;
               this.changeAniState(this.faceDrawer, 1);
               this.changeAniState(this.machineDrawer, 0);
               this.isDisplayBall = true;

               this.deploySeconds = 0.0;
               break label202;
            case 3:
               if (this.laugh_cn < 10) {
                  this.laugh_cn = Math.min(10, GameTime.advance(this, "laugh_cn", this.laugh_cn, 1));
               } else {
                  this.changeAniState(this.faceDrawer, 0);
               }

               double remainder = deployBall(GameTime.deltaSeconds());
               if (this.deploySeconds < DEPLOY_SECONDS) break label202;

               this.show_step = 4;
               this.machine_state = 1;
               MapManager.setCameraUpLimit(544);
               MapManager.setCameraDownLimit(784);
               this.velocity = -45;
               this.changeAniState(this.machineDrawer, 1);
               this.swingSeconds = 0.0;
               openingSwing(remainder);
               break label202;
            case 4:
            case 5:
               openingSwing(GameTime.deltaSeconds());
               this.bossStateChange();
               break label202;
            default:
               break label202;
            }
         case 2:
            this.swingSeconds = (this.swingSeconds + GameTime.deltaSeconds()) % (SWING_PHASE_SECONDS * 4.0);
            battlePose();
            this.bossStateChange();
            break;
         case 3:
            if (this.posY >= 46336) {
               this.posY = 46336;
            } else {
               this.posY = GameTime.advance(this, "posY", this.posY, 270);
            }

            this.bossbroken.logicBoom(this.posX, this.posY - 1280);
            if (this.ballPos[4][1] + this.drop_vely > this.getGroundY(this.ballPos[4][0], this.ballPos[4][1]) && this.drop_cnt == 0) {
               this.ballPos[4][1] = this.getGroundY(this.ballPos[4][0], this.ballPos[4][1]);
               this.drop_vely = -640;
               this.drop_cnt = 1;
            } else if (this.ballPos[4][1] + this.drop_vely > this.getGroundY(this.ballPos[4][0], this.ballPos[4][1]) && this.drop_cnt == 1) {
               this.ballPos[4][1] = this.getGroundY(this.ballPos[4][0], this.ballPos[4][1]);
               this.drop_vely = -320;
               this.drop_cnt = 2;
            } else if (this.ballPos[4][1] + this.drop_vely > this.getGroundY(this.ballPos[4][0], this.ballPos[4][1]) && this.drop_cnt == 2) {
               this.ballPos[4][1] = this.getGroundY(this.ballPos[4][0], this.ballPos[4][1]);
               this.drop_cnt = 3;
            } else if (this.drop_cnt != 3) {
               this.drop_vely = GameTime.advance(this, "drop_vely", this.drop_vely, GRAVITY);
               var5 = this.ballPos[4];
               var5[1] = GameTime.advance(var5, String.valueOf(1), var5[1], this.drop_vely);
               this.ball.setEnd();
               this.isDisplayBall = false;
            }

            if (this.bossbroken.getEndState()) {
               this.state = 4;
               bossFighting = false;
               player.getBossScore();
               this.velocity = 270;
               this.machine_state = 1;
               this.face_state = 0;
               SoundSystem.getInstance().playBgm(18);
            }

            this.changeAniState(this.machineDrawer, this.machine_state);
            this.changeAniState(this.faceDrawer, this.face_state);
            break;
         case 4:
            if (this.posX >= 65536) {
               this.posX = 65536;
               MapManager.releaseCamera();
               MapManager.setCameraLeftLimit(864 - SCREEN_WIDTH / 2);
               MapManager.setCameraDownLimit(MapManager.getPixelHeight());
               MapManager.setCameraRightLimit(MapManager.getPixelWidth());
               this.dead = true;
            } else {
               this.posX = GameTime.advance(this, "posX", this.posX, 480);
            }

            if (this.posY >= 45056) {
               this.posY = 45056;
            } else {
               this.posY = GameTime.advance(this, "posY", this.posY, 270);
            }
         }

         this.refreshCollisionRect(this.posX, this.posY);
         this.checkWithPlayer(var3, var4, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1504, var2 - 2560, 3008, 2176);
   }
}
