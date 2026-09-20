package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class Boss1 extends BossObject {
   private static final int ALERT_RANGE = 6912;
   private static final int BOSS_MOVE_LIMIT_LEFT = 670336;
   private static final int BOSS_MOVE_LIMIT_RIGHT = 702336;
   private static final int BOSS_WAKEN_POINT = 686080;
   private static final int CAMERA_SET_POINT = 678912;
   private static final int CAMERA_SIDE_LEFT = 10544;
   private static final int CAMERA_SIDE_RIGHT = 10904;
   private static final int CAR_HURT = 1;
   private static final int CAR_MOVE = 0;
   private static final int COLLISION_HEIGHT = 3840;
   private static final int COLLISION_WIDTH = 4608;
   private static final int DEGREE_MAX = 23680;
   private static final int DEGREE_MAX_2 = 22400;
   private static final int DEGREE_MIN = 10880;
   private static final int DEGREE_MIN_2 = 12160;
   private static final int FACE_HURT = 2;
   private static final int FACE_NORMAL = 0;
   private static final int FACE_SMILE = 1;
   private static final int STATE_ATTACK_1 = 1;
   private static final int STATE_ATTACK_2 = 3;
   private static final int STATE_ATTACK_3 = 4;
   private static final int STATE_BROKEN = 5;
   private static final int STATE_ESCAPE = 6;
   private static final int STATE_READY = 2;
   private static final int STATE_WAIT = 0;
   private static Animation boatAni;
   private static Animation brokencarAni;
   private static Animation carAni;
   private static final int cnt_max = 8;
   private static Animation escapefaceAni;
   private static Animation faceAni;
   private static final int stop_wait_cnt_max = 32;
   private int[] ArmSharpPos = new int[2];
   private boolean IsBreaking;
   private boolean IsStopWait = false;
   private int WaitCnt;
   private int alert_state;
   private Boss1Arm arm;
   private int ball_size = 1536;
   private AnimationDrawer boatdrawer;
   private BossBroken bossbroken;
   private AnimationDrawer brokencardrawer;
   private int car_cnt;
   private int car_state;
   private AnimationDrawer cardrawer;
   private int con_size = 1152;
   private int degree = 0;
   private int dg_plus = 320;
   private int drop_cnt;
   private int escape_v = 512;
   private AnimationDrawer escapefacedrawer;
   private int face_cnt;
   private int face_state;
   private AnimationDrawer facedrawer;
   private int fly_end;
   private int fly_top;
   private int fly_top_range = 3840;
   private int flywheel_lx;
   private int flywheel_rx;
   private int flywheel_vx;
   private int flywheel_vy;
   private int flywheel_y;
   private int limitLeftX;
   private int limitRightX;
   private int offsetY = 2112;
   private int plus = 1;
   private int range = 44800;
   private int side_left = 0;
   private int side_right = 0;
   private int state;
   private int stop_wait_cnt = 0;
   private int velX = 0;
   private int velY = 0;
   private int velocity = -240;
   private int wait_cnt;
   private int wait_cnt_max = 10;

   protected Boss1(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.limitRightX = 702336;
      this.limitLeftX = 670336;
      this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
      if (carAni == null) {
         carAni = new Animation("/animation/boss1_car");
      }

      this.cardrawer = carAni.getDrawer(0, true, 0);
      if (faceAni == null) {
         faceAni = new Animation("/animation/boss1_egg");
      }

      this.facedrawer = faceAni.getDrawer(0, true, 0);
      if (brokencarAni == null) {
         brokencarAni = new Animation("/animation/boss1_body");
      }

      this.brokencardrawer = brokencarAni.getDrawer(2, true, 0);
      if (boatAni == null) {
         boatAni = new Animation("/animation/pod_boat");
      }

      this.boatdrawer = boatAni.getDrawer(0, true, 0);
      if (escapefaceAni == null) {
         escapefaceAni = new Animation("/animation/pod_face");
      }

      this.escapefacedrawer = escapefaceAni.getDrawer(4, true, 0);
      this.arm = new Boss1Arm(31, var2, var3, var4, var5, var6, var7);
      addGameObject(this.arm, var2, var3);
      this.IsBreaking = false;
      this.WaitCnt = 0;
      this.IsStopWait = false;
      this.setBossHP();
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
      Animation.closeAnimation(carAni);
      Animation.closeAnimation(faceAni);
      Animation.closeAnimation(brokencarAni);
      Animation.closeAnimation(BoomAni);
      Animation.closeAnimation(boatAni);
      Animation.closeAnimation(escapefaceAni);
      carAni = null;
      faceAni = null;
      brokencarAni = null;
      BoomAni = null;
      boatAni = null;
      escapefaceAni = null;
   }

   public void close() {
      this.cardrawer = null;
      this.facedrawer = null;
      this.brokencardrawer = null;
      this.boatdrawer = null;
      this.escapefacedrawer = null;
      super.close();
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (this.face_state == 2 && this.car_cnt == 8) {
         label37: {
            var3 = player.getAnimationId();
            var1 = player;
            if (var3 != 6) {
               var3 = player.getAnimationId();
               var1 = player;
               if (var3 != 7) {
                  break label37;
               }
            }

            player.beHurt();
            this.face_state = 1;
            return;
         }
      }

      if (this.HP > 0 && this.face_state != 2) {
         --this.HP;
         player.doBossAttackPose(this, var2);
         this.face_state = 2;
         this.car_state = 1;
         if (this.HP == 0) {
            SoundSystem.getInstance().playSe(35);
         } else {
            SoundSystem.getInstance().playSe(34);
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && this.state != 5 && this.state != 6 && var1 == player) {
         if (player.isAttackingEnemy()) {
            label48: {
               if (this.face_state == 2 && this.car_cnt == 8) {
                  int var3 = player.getAnimationId();
                  var1 = player;
                  if (var3 == 6) {
                     break label48;
                  }

                  var3 = player.getAnimationId();
                  var1 = player;
                  if (var3 == 7) {
                     break label48;
                  }
               }

               if (this.HP > 0 && this.face_state != 2) {
                  --this.HP;
                  player.doBossAttackPose(this, var2);
                  this.face_state = 2;
                  this.car_state = 1;
                  if (this.HP == 0) {
                     SoundSystem.getInstance().playSe(35);
                  } else {
                     SoundSystem.getInstance().playSe(34);
                  }

                  return;
               }

               return;
            }

            player.beHurt();
            this.face_state = 1;
         } else if (this.state != 5 && this.state != 6 && player.canBeHurt()) {
            player.beHurt();
            this.face_state = 1;
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         System.out.println("draw boss~!");
         if (this.state < 5) {
            this.drawInMap(var1, this.cardrawer);
            this.drawInMap(var1, this.facedrawer, this.posX, this.posY - 3008);
         } else if (this.state == 5) {
            this.changeAniState(this.brokencardrawer, 2);
            this.drawInMap(var1, this.brokencardrawer);
            if (this.drop_cnt < 2) {
               this.changeAniState(this.brokencardrawer, 0);
               this.drawInMap(var1, this.brokencardrawer, this.flywheel_lx, this.flywheel_y);
               this.changeAniState(this.brokencardrawer, 1);
               this.drawInMap(var1, this.brokencardrawer, this.flywheel_rx, this.flywheel_y);
            }

            this.drawInMap(var1, this.facedrawer, this.posX, this.posY - 2496);
         } else if (this.state == 6) {
            this.drawInMap(var1, this.boatdrawer, this.posX, this.posY - 960);
            this.drawInMap(var1, this.escapefacedrawer, this.posX, this.posY - 2624);
         }

         this.arm.drawArm(var1);
         if (this.bossbroken != null) {
            this.bossbroken.draw(var1);
         }

         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var1 = this.posX;
         int var2 = this.posY;
         int var4 = this.posX;
         int var3 = this.posY;
         if (this.HP == 1) {
            if (this.state == 1) {
               this.state = 4;
            } else if (this.arm.getArmState() == 3 && !this.arm.getTurnState()) {
               this.state = 3;
               this.dg_plus = 914;
               this.arm.setTurnState(true);
               this.arm.setDegreeSpeed(this.dg_plus);
            } else if (this.arm.getArmState() == 4 && !this.arm.getTurnState()) {
               this.state = 4;
            }
         } else if (this.HP == 0 && !this.IsBreaking) {
            this.state = 5;
            this.changeAniState(this.facedrawer, 2);
            this.changeAniState(this.brokencardrawer, 2);
            this.posY -= this.con_size;
            this.velY = -600;
            if (player.getVelX() < 0) {
               this.velX = -150;
            } else {
               this.velX = 150;
            }

            if (this.velocity > 0) {
               this.flywheel_lx = this.posX - 147456;
               this.flywheel_rx = this.posX + 147456;
            } else {
               this.flywheel_lx = this.posX + 147456;
               this.flywheel_rx = this.posX - 147456;
            }

            this.flywheel_y = this.posY - this.con_size;
            this.flywheel_vx = 300;
            this.flywheel_vy = -300;
            this.bossbroken = new BossBroken(22, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
            addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
            this.IsBreaking = true;
            this.side_left = MapManager.getCamera().x;
            this.side_right = this.side_left + MapManager.CAMERA_WIDTH;
            MapManager.setCameraLeftLimit(this.side_left);
            MapManager.setCameraRightLimit(this.side_right);
         }

         if (this.state > 0) {
            isBossEnter = true;
         }

         switch(this.state) {
         case 0:
            if (player.getFootPositionX() >= 678912) {
               MapManager.setCameraLeftLimit(10544);
               MapManager.setCameraRightLimit(10904);
            }

            if (player.getFootPositionX() >= 686080) {
               this.IsStopWait = true;
               bossFighting = true;
               bossID = 22;
               SoundSystem.getInstance().playBgm(22, true);
            }

            if (this.IsStopWait) {
               if (this.stop_wait_cnt < 32) {
                  this.stop_wait_cnt = Math.min(32, GameTime.advance(this, "stop_wait_cnt", this.stop_wait_cnt, 1));
               } else {
                  this.state = 1;
               }
            }
            break;
         case 1:
         case 2:
            if (this.face_state != 0) {
               if (this.face_cnt < 8) {
                  this.face_cnt = Math.min(8, GameTime.advance(this, "face_cnt", this.face_cnt, 1));
               } else {
                  this.face_state = 0;
                  this.face_cnt = GameTime.set(this, "face_cnt", 0);
               }
            }

            if (this.car_state == 1) {
               if (this.car_cnt < 8) {
                  this.car_cnt = Math.min(8, GameTime.advance(this, "car_cnt", this.car_cnt, 1));
               } else {
                  this.car_state = 0;
                  this.car_cnt = GameTime.set(this, "car_cnt", 0);
               }
            }

            this.changeAniState(this.cardrawer, this.car_state);
            this.changeAniState(this.facedrawer, this.face_state);
            if (this.state != 4) {
               if (this.velocity > 0) {
                  this.posX = GameTime.advancePosition(this, "posX", this.posX, "velocity", this.velocity);
                  if (this.posX >= this.limitRightX) {
                     this.posX = this.limitRightX;
                     this.velocity = -this.velocity;
                  }
               } else {
                  this.posX = GameTime.advancePosition(this, "posX", this.posX, "velocity", this.velocity);
                  if (this.posX <= this.limitLeftX) {
                     this.posX = this.limitLeftX;
                     this.velocity = -this.velocity;
                  }
               }
            } else if (this.velocity > 0) {
               if (this.posX >= this.limitRightX) {
                  this.posX = this.limitRightX;
                  this.velocity = -this.velocity;
               }
            } else if (this.posX <= this.limitLeftX) {
               this.posX = this.limitLeftX;
               this.velocity = -this.velocity;
            }

            this.arm.logic(this.posX, this.posY - this.offsetY, this.state, this.velocity);
            break;
         case 3:
            if (this.face_state != 0) {
               if (this.face_cnt < 8) {
                  this.face_cnt = Math.min(8, GameTime.advance(this, "face_cnt", this.face_cnt, 1));
               } else {
                  this.face_state = 0;
                  this.face_cnt = GameTime.set(this, "face_cnt", 0);
               }
            }

            if (this.car_state == 1) {
               if (this.car_cnt < 8) {
                  this.car_cnt = Math.min(8, GameTime.advance(this, "car_cnt", this.car_cnt, 1));
               } else {
                  this.car_state = 0;
                  this.car_cnt = GameTime.set(this, "car_cnt", 0);
               }
            }

            this.changeAniState(this.cardrawer, this.car_state);
            this.changeAniState(this.facedrawer, this.face_state);
            this.ArmSharpPos = this.arm.logic(this.posX, this.posY - this.offsetY, this.state, this.velocity);
            this.posX = this.ArmSharpPos[0];
            this.posY = this.ArmSharpPos[1] + this.offsetY;
            break;
         case 4:
            if (this.face_state != 0) {
               if (this.face_cnt < 8) {
                  this.face_cnt = Math.min(8, GameTime.advance(this, "face_cnt", this.face_cnt, 1));
               } else {
                  this.face_state = 0;
                  this.face_cnt = GameTime.set(this, "face_cnt", 0);
               }
            }

            if (this.car_state == 1) {
               if (this.car_cnt < 8) {
                  this.car_cnt = Math.min(8, GameTime.advance(this, "car_cnt", this.car_cnt, 1));
               } else {
                  this.car_state = 0;
                  this.car_cnt = GameTime.set(this, "car_cnt", 0);
               }
            }

            this.changeAniState(this.cardrawer, this.car_state);
            this.changeAniState(this.facedrawer, this.face_state);
            this.posX = var4;
            this.posY = var3;
            if (this.velocity > 0) {
               this.posX = GameTime.advancePosition(this, "posX", this.posX, "velocity", this.velocity);
               if (this.posX >= this.limitRightX) {
                  this.posX = this.limitRightX;
                  this.velocity = -this.velocity;
               }
            } else {
               this.posX = GameTime.advancePosition(this, "posX", this.posX, "velocity", this.velocity);
               if (this.posX <= this.limitLeftX) {
                  this.posX = this.limitLeftX;
                  this.velocity = -this.velocity;
               }
            }

            this.arm.logic(this.posX, this.posY - this.offsetY, this.state, this.velocity);
            break;
         case 5:
            if (this.face_state != 0) {
               if (this.face_cnt < 8) {
                  this.face_cnt = Math.min(8, GameTime.advance(this, "face_cnt", this.face_cnt, 1));
               } else {
                  this.face_state = 0;
                  this.face_cnt = GameTime.set(this, "face_cnt", 0);
               }
            }

            this.changeAniState(this.facedrawer, this.face_state);
            if (this.posY + GameTime.displacement(GameTime.integratedVelocity(this, "velY", this.velY)) >= this.getGroundY(this.posX, this.posY)) {
               this.posY = this.getGroundY(this.posX, this.posY);
               switch(this.drop_cnt) {
               case 0:
                  this.velY = -450;
                  this.drop_cnt = 1;
                  break;
               case 1:
                  this.velY = -300;
                  this.drop_cnt = 2;
               }
            } else {
               this.posX = GameTime.advancePosition(this, "posX", this.posX, "velX", this.velX);
               this.velY = GameTime.advance(this, "velY", this.velY, GRAVITY);
               this.posY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
            }

            if (this.flywheel_y - this.con_size >= this.getGroundY(this.flywheel_lx, this.flywheel_y)) {
               this.flywheel_y = this.getGroundY(this.flywheel_lx, this.flywheel_y) + this.con_size;
            } else {
               if (this.velocity > 0) {
                  this.flywheel_lx = GameTime.advance(this, "flywheel_lx", this.flywheel_lx, -(this.flywheel_vx));
                  this.flywheel_rx = GameTime.advance(this, "flywheel_rx", this.flywheel_rx, this.flywheel_vx);
               } else {
                  this.flywheel_lx = GameTime.advance(this, "flywheel_lx", this.flywheel_lx, this.flywheel_vx);
                  this.flywheel_rx = GameTime.advance(this, "flywheel_rx", this.flywheel_rx, -(this.flywheel_vx));
               }

               this.flywheel_vy = GameTime.advance(this, "flywheel_vy", this.flywheel_vy, GRAVITY >> 1);
               this.flywheel_y = GameTime.advance(this, "flywheel_y", this.flywheel_y, this.flywheel_vy);
            }

            this.arm.logic(this.posX, this.posY, this.state, this.velocity);
            this.bossbroken.logicBoom(this.posX, this.posY);
            if (this.bossbroken.getEndState()) {
               this.state = 6;
               this.fly_top = this.posY;
               this.fly_end = this.side_right;
               bossFighting = false;
               player.getBossScore();
               SoundSystem.getInstance().playBgm(StageManager.getBgmId(), true);
            }
            break;
         case 6:
            this.wait_cnt = GameTime.advance(this, "wait_cnt", this.wait_cnt, 1);
            if (this.wait_cnt >= this.wait_cnt_max && this.posY >= this.fly_top - this.fly_top_range) {
               this.posY = GameTime.advance(this, "posY", this.posY, -(this.escape_v));
            }

            if (this.posY <= this.fly_top - this.fly_top_range && this.WaitCnt == 0) {
               this.posY = this.fly_top - this.fly_top_range;
               this.escapefacedrawer.setActionId(0);
               this.boatdrawer.setActionId(1);
               this.boatdrawer.setLoop(false);
               this.WaitCnt = 1;
            }

            if (this.WaitCnt == 1 && this.boatdrawer.checkEnd()) {
               this.escapefacedrawer.setActionId(0);
               this.escapefacedrawer.setTrans(2);
               this.escapefacedrawer.setLoop(true);
               this.boatdrawer.setActionId(1);
               this.boatdrawer.setTrans(2);
               this.boatdrawer.setLoop(false);
               this.WaitCnt = 2;
            }

            if (this.WaitCnt == 2 && this.boatdrawer.checkEnd()) {
               this.boatdrawer.setActionId(0);
               this.boatdrawer.setTrans(2);
               this.boatdrawer.setLoop(true);
               this.WaitCnt = 3;
            }

            if (this.WaitCnt == 3 || this.WaitCnt == 4) {
               this.posX = GameTime.advance(this, "posX", this.posX, this.escape_v);
            }

            if (this.posX > this.side_right << 6 && this.WaitCnt == 3) {
               int var5 = MapManager.getCamera().x;
               var4 = MapManager.CAMERA_WIDTH;
               var3 = MapManager.getCamera().y;
               Cage var6 = new Cage(var5 + (var4 >> 1) << 6, var3 << 6);
               addGameObject(var6);
               MapManager.lockCamera(true);
               this.WaitCnt = 4;
            }
         }

         this.checkWithPlayer(var1, var2, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 2304, var2 - 3840, 4608, 3840);
   }
}
