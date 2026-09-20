package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.MyRandom;
import Lib.SoundSystem;
import Lib.crlFP32;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.utility.MFMath;

class BossF3 extends BossObject {
   private static final int ARM_CAUGHT_BACK_V = 1440;
   private static final int ARM_OUT_V = 1320;
   private static final int ARM_UNCAUGHT_BACK_V = 480;
   private static final int BOSS_APPEAR_POSX = 167680;
   private static final int BOSS_APPEAR_POSY = 32128;
   private static final int BOSS_ARMSTATE_V = 720;
   private static final int BOSS_FIRST_STOP_POSY = 24768;
   private static final int BOSS_HANG_BOTTOM = 25024;
   private static final int BOSS_HANG_LEFT_POSX = 152832;
   private static final int BOSS_HANG_RIGHT_POSX = 167680;
   private static final int BOSS_HANG_TOP = 24512;
   private static final int BOSS_LEFT_POSX = 147712;
   private static final int BOSS_RIGHT_POSX = 172800;
   private static final int CABIN_HURT = 1;
   private static final int CABIN_NORMAL = 0;
   private static final int CHAIN_DIAMETER = 1024;
   private static final int CHAIN_HEIGHT = 1024;
   private static final int CHAIN_WIDTH = 1024;
   private static final int CIRCLE_ARM_ATTATCK_1 = 8;
   private static final int CIRCLE_ARM_ATTATCK_2 = 9;
   private static final int CIRCLE_ARM_HURT = 10;
   private static final int CIRCLE_BOMB_ATTATCK_1 = 5;
   private static final int CIRCLE_BOMB_ATTATCK_2 = 6;
   private static final int CIRCLE_BOMB_HURT = 7;
   private static final int CIRCLE_FIRST_OFFSETPOSY = -1536;
   private static final int CIRCLE_HEIGHT = 1536;
   private static final int CIRCLE_NORMAL_OFFSETPOSY = 0;
   private static final int CIRCLE_RAY_ATTATCK_1 = 2;
   private static final int CIRCLE_RAY_ATTATCK_2 = 3;
   private static final int CIRCLE_RAY_HURT = 4;
   private static final int CIRCLE_ROTATE = 0;
   private static final int CIRCLE_ROTATE_HURT = 1;
   private static final int CIRCLE_WIDTH = 4096;
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 2688;
   private static final int DEGREE_OFFSET = 18;
   private static final int FACE_ANGRY = 1;
   private static final int FACE_HURT = 3;
   private static final int FACE_NORMAL = 0;
   private static final int FACE_OFFSETY = -832;
   private static final int FACE_SMILE = 2;
   private static final int INIT_STOP_POSX = 160768;
   private static final int INIT_STOP_POSY1 = 21504;
   private static final int INIT_STOP_POSY2 = 27648;
   private static final int MOVE_SPEED = 240;
   public static final int PRO_ATTACK_ARM = 4;
   public static final int PRO_ATTACK_BOMB = 3;
   public static final int PRO_ATTACK_RAY = 2;
   public static final int PRO_MOVE = 1;
   public static final int PRO_WAIT = 0;
   private static final int SIDE_DOWN = 440;
   private static final int SIDE_LEFT = 2360;
   private static final int SIDE_RIGHT = 2648;
   private static final int SIDE_UP = 248;
   private static final int STATE_BROKEN = 3;
   private static final int STATE_ENTER_SHOW = 1;
   private static final int STATE_ESCAPE = 4;
   private static final int STATE_INIT = 0;
   private static final int STATE_PRO = 2;
   private static Animation boatAni;
   private static Animation cabinAni;
   private static Animation chainAni;
   private static Animation circleAni;
   private static final int cnt_max = 8;
   private static Animation escapefaceAni;
   private static Animation faceAni;
   private int WaitCnt;
   private int aim_max;
   private BossF3Arm arm;
   private boolean armDegreeLock;
   private int armStartPosY;
   private int arm_attack_step;
   private int arm_drop_vely;
   private int arm_posx;
   private int arm_posy;
   private int arm_pre_velx;
   private int arm_pre_vely;
   private int arm_velx;
   private int arm_vely;
   private int attack_state;
   private AnimationDrawer boatdrawer;
   private int boss_hang_posx;
   private BossBroken bossbroken;
   private int brokenFrame;
   private AnimationDrawer cabinDrawer;
   private int cabin_state;
   private AnimationDrawer chainDrawer;
   private BossF3Circle circle;
   private AnimationDrawer circleDrawer;
   private int circleOffsetY = 0;
   private int circleState;
   private int circle_state;
   private BossF3Defence defence;
   private int degree;
   private boolean displayFlag;
   private int drop_vely;
   private int enter_cn;
   private int escape_v = 512;
   private AnimationDrawer escapefacedrawer;
   private AnimationDrawer faceDrawer;
   private int face_cnt;
   private int face_state;
   private int fly_end;
   private int fly_top;
   private int fly_top_range = 4096;
   private boolean isAiming = false;
   private boolean isArmBack = false;
   private boolean isArmCaughtEnd = false;
   private boolean isArmMove = false;
   private boolean isArmUncaughtEnd = false;
   private boolean isCaught = false;
   private boolean isMachineShake = false;
   private boolean isinArmRange = false;
   public int pro_state;
   private int radius;
   private int rayOffsetY = 0;
   private int recoil_offsetx;
   private int shakeFrame = 0;
   private int shakeOffsetY = 0;
   private int shakechainx;
   private int shakechainy;
   private int state;
   private int velocity;
   private int wait_cnt;
   private int wait_cnt_max = 10;
   private int wait_frame_cn;
   private int wait_frame_max;
   private int wait_frame_offset_cn;

   protected BossF3(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX = 167680;
      this.posY = 32128;
      this.enter_cn = 0;
      this.armStartPosY = 0;
      this.armDegreeLock = false;
      if (cabinAni == null) {
         cabinAni = new Animation("/animation/bossf3_cabin");
      }

      this.cabinDrawer = cabinAni.getDrawer(0, true, 0);
      if (circleAni == null) {
         circleAni = new Animation("/animation/bossf3_circle");
      }

      this.circleDrawer = circleAni.getDrawer(0, true, 0);
      if (faceAni == null) {
         faceAni = new Animation("/animation/bossf3_face");
      }

      this.faceDrawer = faceAni.getDrawer(0, true, 0);
      if (chainAni == null) {
         chainAni = new Animation("/animation/bossf3_chain");
      }

      this.chainDrawer = chainAni.getDrawer(0, true, 0);
      if (boatAni == null) {
         boatAni = new Animation("/animation/pod_boat");
      }

      this.boatdrawer = boatAni.getDrawer(0, true, 0);
      if (escapefaceAni == null) {
         escapefaceAni = new Animation("/animation/pod_face");
      }

      this.escapefacedrawer = escapefaceAni.getDrawer(0, false, 0);
      this.circle = new BossF3Circle(var2, var3, this);
      this.defence = new BossF3Defence(var2, var3, this);
      this.displayFlag = false;
      this.state = 0;
      this.setBossHP();
   }

   private void actionInit() {
      int var1 = MyRandom.nextInt(0, 100);
      if (var1 <= 85) {
         this.pro_state = 0;
         this.wait_frame_max = MyRandom.nextInt(0, 10) * 4 + 32;
         this.wait_frame_cn = 0;
      } else {
         this.pro_state = 1;
         this.wait_frame_max = MyRandom.nextInt(0, 10) * 4 + 32;
         this.wait_frame_cn = 0;
      }

   }

   private int calArmDegree(int var1, int var2) {
      var1 = crlFP32.actTanDegree(var1, var2);
      return (var1 + 360) % 360;
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

   private int circleOffsetY() {
      int var1;
      if (this.circleState == 0) {
         this.circleOffsetY = -1536;
         var1 = -1536;
      } else {
         int var2;
         short var3;
         if (this.circleState == 1) {
            var2 = this.circleOffsetY;
            if (this.state == 2) {
               var3 = 480;
            } else {
               var3 = 240;
            }

            this.circleOffsetY = var2 + var3;
            if (this.circleOffsetY >= 0) {
               this.circleOffsetY = 0;
            }

            var1 = this.circleOffsetY;
         } else if (this.circleState == 2) {
            var2 = this.circleOffsetY;
            if (this.state == 2) {
               var3 = 480;
            } else {
               var3 = 240;
            }

            this.circleOffsetY = var2 - var3;
            if (this.circleOffsetY <= -1536) {
               this.circleOffsetY = -1536;
            }

            var1 = this.circleOffsetY;
         } else {
            this.circleOffsetY = 0;
            var1 = 0;
         }
      }

      return var1;
   }

   private void drawChain(MFGraphics var1) {
      int var2;
      int var3;
      int var4;
      int var5;
      int var6;
      int var7;
      int var8;
      int var9;
      AnimationDrawer var10;
      if (this.posX == 152832) {
         var3 = this.arm_posx;
         var4 = this.arm_posx;
         var5 = this.arm_posy;
         var2 = this.arm_posy;
         var5 = (MFMath.sqrt((var3 - 2048) * (var4 - 2048) + var5 * var2) >> 6) / 1024;
         if (var5 > 0) {
            var2 = this.arm_posy / var5;
         } else {
            var2 = 0;
         }

         if (var5 > 0) {
            var3 = (this.arm_posx - 2048) / var5;
         } else {
            var3 = 0;
         }

         for(var4 = 0; var4 < var5; ++var4) {
            var10 = this.chainDrawer;
            var6 = this.posX;
            var7 = var3 / 2;
            var9 = this.posY;
            var8 = var2 / 2;
            this.drawInMap(var1, var10, var6 + 2048 - 1024 + var3 * var4 + var7, var9 + var2 * var4 + var8);
         }
      } else {
         var2 = -this.arm_posx;
         var3 = -this.arm_posx;
         var4 = this.arm_posy;
         var5 = this.arm_posy;
         var5 = (MFMath.sqrt((var2 - 2048) * (var3 - 2048) + var4 * var5) >> 6) / 1024;
         if (var5 > 0) {
            var2 = this.arm_posy / var5;
         } else {
            var2 = 0;
         }

         if (var5 > 0) {
            var3 = (-this.arm_posx - 2048) / var5;
         } else {
            var3 = 0;
         }

         for(var4 = 0; var4 < var5; ++var4) {
            var10 = this.chainDrawer;
            var9 = this.posX;
            var6 = var3 / 2;
            var8 = this.posY;
            var7 = var2 / 2;
            this.drawInMap(var1, var10, var9 - 2048 + 1024 - var3 * var4 - var6, var8 + var2 * var4 + var7);
         }
      }

   }

   private void drawChainShake(MFGraphics var1) {
      if (this.posX == 152832) {
         this.drawInMap(var1, this.chainDrawer, this.posX + 2048 - 512, this.posY);
         this.drawInMap(var1, this.chainDrawer, this.posX + this.shakechainx, this.posY + this.shakechainy);
      } else {
         this.drawInMap(var1, this.chainDrawer, this.posX - 2048 + 512, this.posY);
         this.drawInMap(var1, this.chainDrawer, this.posX + this.shakechainx, this.posY + this.shakechainy);
      }

   }

   private boolean isCircleNoneUpDefenceState() {
      boolean var1;
      if (this.pro_state != 1 && this.pro_state != 2 && this.pro_state != 3 && this.pro_state != 4) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private void playerLoseRings() {
      PlayerObject var1 = player;
      if (PlayerObject.getRingNum() > 5) {
         player.loseRing(5);
         var1 = player;
         var1 = player;
         PlayerObject.setRingNum(PlayerObject.getRingNum() - 5);
      } else {
         var1 = player;
         if (PlayerObject.getRingNum() > 0) {
            PlayerObject var2 = player;
            var1 = player;
            var2.loseRing(PlayerObject.getRingNum());
            var1 = player;
            PlayerObject.setRingNum(0);
         }
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(cabinAni);
      Animation.closeAnimation(circleAni);
      Animation.closeAnimation(faceAni);
      Animation.closeAnimation(chainAni);
      Animation.closeAnimation(boatAni);
      Animation.closeAnimation(escapefaceAni);
      escapefaceAni = null;
      boatAni = null;
      chainAni = null;
      cabinAni = null;
      circleAni = null;
      faceAni = null;
   }

   private void setActionMode() {
      int var1;
      if (player.collisionState == 1) {
         var1 = MyRandom.nextInt(0, 100);
         if (var1 < 15) {
            this.attack_state = 4;
         } else if (var1 < 65) {
            this.attack_state = 2;
         } else {
            this.attack_state = 3;
         }
      } else {
         var1 = MyRandom.nextInt(0, 100);
         if (var1 < 50) {
            this.attack_state = 4;
         } else if (var1 < 75) {
            this.attack_state = 2;
         } else {
            this.attack_state = 3;
         }
      }

   }

   private int shakeOffsetY() {
      int var1;
      if (this.isMachineShake && !IsGamePause) {
         ++this.shakeFrame;
         if (this.shakeFrame >= 0 && this.shakeFrame < 8) {
            this.shakeOffsetY -= 32;
         } else if (this.shakeFrame != 8 && this.shakeFrame != 9) {
            if (this.shakeFrame < 24) {
               this.shakeOffsetY += 32;
            } else if (this.shakeFrame != 24 && this.shakeFrame != 25) {
               if (this.shakeFrame < 34) {
                  this.shakeOffsetY -= 32;
               } else if (this.shakeFrame == 34) {
                  this.shakeOffsetY = 0;
                  this.shakeFrame = 0;
               }
            } else {
               this.shakeOffsetY = 256;
            }
         } else {
            this.shakeOffsetY = -256;
         }

         var1 = this.shakeOffsetY;
      } else {
         this.shakeOffsetY = 0;
         var1 = 0;
      }

      return var1;
   }

   public void close() {
      this.cabinDrawer = null;
      this.circleDrawer = null;
      this.faceDrawer = null;
      this.circle = null;
      this.defence = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (this.state != 3 && this.state != 4 && (this.circle_state != 0 || var1.canBeHurt()) && this.state == 2 && this.pro_state != 0 && this.face_state != 3) {
         --this.HP;
         player.doBossAttackPose(this, var2);
         this.circle.setAvaliable(false);
         this.defence.setAvaliable(false);
         if (this.arm != null) {
            this.arm.setAvaliable(false);
         }

         if (this.HP > 0) {
            this.face_state = 3;
            this.face_cnt = 0;
         } else {
            this.state = 3;
            this.arm_drop_vely = 0;
            this.drop_vely = 0;
            this.shakeOffsetY = 0;
            this.rayOffsetY = 0;
            this.circleOffsetY = 0;
            BossBroken var4 = new BossBroken(30, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
            this.bossbroken = var4;
            addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
            this.bossbroken.setTotalCntMax(60);
            if (StageManager.isGoingToExtraStage()) {
               MapManager.setCameraUpLimit(0);
               player.setTerminal(3);
            } else {
               MapManager.setCameraUpLimit(0);
               MapManager.setMapLoop(33, 37);
               player.setTerminal(2);
            }

            this.face_state = 0;
            player.setMeetingBoss(false);
            if (this.circle_state == 0) {
               this.circle_state = 5;
            }

            this.brokenFrame = 0;
         }

         if (this.HP == 0) {
            SoundSystem.getInstance().playSe(35);
         } else {
            SoundSystem.getInstance().playSe(34);
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && (this.arm == null || !this.arm.getCaughtState()) && (this.circle_state != 0 || var1.canBeHurt()) && this.state != 3 && this.state != 4 && this.state == 2 && var1 == player) {
         if (player.isAttackingEnemy() && this.pro_state != 0) {
            if (this.face_state != 3) {
               --this.HP;
               player.doBossAttackPose(this, var2);
               this.circle.setAvaliable(false);
               this.defence.setAvaliable(false);
               if (this.arm != null) {
                  this.arm.setAvaliable(false);
               }

               if (this.HP > 0) {
                  this.face_state = 3;
                  this.face_cnt = 0;
               } else {
                  this.state = 3;
                  this.arm_drop_vely = 0;
                  this.drop_vely = 0;
                  this.shakeOffsetY = 0;
                  this.rayOffsetY = 0;
                  this.circleOffsetY = 0;
                  BossBroken var3 = new BossBroken(30, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
                  this.bossbroken = var3;
                  addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
                  this.bossbroken.setTotalCntMax(60);
                  if (StageManager.isGoingToExtraStage()) {
                     MapManager.setCameraUpLimit(0);
                     player.setTerminal(3);
                  } else {
                     MapManager.setCameraUpLimit(0);
                     MapManager.setMapLoop(33, 37);
                     player.setTerminal(2);
                  }

                  this.face_state = 0;
                  player.setMeetingBoss(false);
                  if (this.circle_state == 0) {
                     this.circle_state = 5;
                  }

                  this.brokenFrame = 0;
               }

               if (this.HP == 0) {
                  SoundSystem.getInstance().playSe(35);
               } else {
                  SoundSystem.getInstance().playSe(34);
               }
            }
         } else if (this.state != 3 && this.state != 4 && this.face_state != 3 && this.state == 2) {
            player.beHurt();
            this.face_state = 2;
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (this.displayFlag && !this.dead) {
         if (this.state != 4) {
            if (this.state != 3) {
               this.shakeOffsetY();
               this.circleOffsetY();
            }

            if (this.arm != null) {
               if (this.state != 3 && this.arm_attack_step > 0) {
                  if (this.isArmCaughtEnd) {
                     this.drawChainShake(var1);
                  } else {
                     this.drawChain(var1);
                  }
               }

               this.arm.draw(var1);
            }

            this.drawInMap(var1, this.cabinDrawer, this.posX + this.recoil_offsetx, this.posY + this.shakeOffsetY + this.rayOffsetY);
            this.drawInMap(var1, this.faceDrawer, this.posX + this.recoil_offsetx, this.posY - 832 + this.shakeOffsetY + this.rayOffsetY);
            this.drawInMap(var1, this.circleDrawer, this.posX + this.recoil_offsetx, this.posY + this.shakeOffsetY + this.circleOffsetY + this.rayOffsetY);
            if (this.circle != null) {
               this.circle.draw(var1);
            }

            if (this.defence != null) {
               this.defence.draw(var1);
            }
         }

         if (this.state == 4) {
            this.drawInMap(var1, this.boatdrawer, this.posX, this.posY);
            this.drawInMap(var1, this.escapefacedrawer, this.posX, this.posY - 1664);
         }

         if (this.state == 3 && this.bossbroken != null && (!StageManager.isGoingToExtraStage() || this.brokenFrame <= 43)) {
            this.bossbroken.draw(var1);
         }

         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var4 = this.posX;
         int var5 = this.posY;
         if (this.state > 0 && this.state < 4) {
            isBossEnter = true;
         } else if (this.state == 4) {
            isBossEnter = false;
         }

         int var1;
         int var2;
         int var3;
         int var6;
         int var7;
         switch(this.state) {
         case 0:
            if (player.getFootPositionX() >= 160768) {
               MapManager.setCameraLeftLimit(2360);
               MapManager.setCameraRightLimit(2648);
            }

            if (player.getFootPositionX() >= 160768 && player.getFootPositionY() >= 21504 && player.getFootPositionY() <= 27648) {
               this.state = 1;
               MapManager.setCameraUpLimit(248);
               MapManager.setCameraDownLimit(440);
               MapManager.setCameraLeftLimit(2360);
               MapManager.setCameraRightLimit(2648);
               this.enter_cn = 0;
               this.isMachineShake = false;
               this.circleState = 0;
               if (!this.IsPlayBossBattleBGM) {
                  bossFighting = true;
                  bossID = 30;
                  SoundSystem.getInstance().playBgm(19, true);
                  this.IsPlayBossBattleBGM = true;
               }
            }
            break;
         case 1:
            ++this.enter_cn;
            if (this.enter_cn == 11) {
               MapManager.setShake(30);
            }

            if (this.enter_cn == 13) {
               this.displayFlag = true;
            }

            if (this.enter_cn >= 20) {
               this.posY -= 240;
               if (this.posY <= 24768) {
                  this.posY = 24768;
               }
            }

            if (this.enter_cn == 44) {
               this.isMachineShake = true;
            }

            if (this.enter_cn == 56) {
               this.circle_state = 5;
            }

            if (this.enter_cn == 66) {
               this.circleState = 1;
            }

            if (this.enter_cn == 76) {
               this.face_state = 1;
            }

            if (this.enter_cn == 92) {
               this.face_state = 0;
               this.circleState = 2;
            }

            if (this.enter_cn == 97) {
               this.circle_state = 0;
               this.state = 2;
               this.actionInit();
            }
            break;
         case 2:
            if (this.face_state != 0) {
               if (this.face_cnt < 8) {
                  ++this.face_cnt;
                  if (this.circle_state != 0) {
                     if (this.pro_state == 2) {
                        this.circle_state = 2;
                     }

                     if (this.pro_state == 3) {
                        this.circle_state = 5;
                     }

                     if (this.pro_state == 4) {
                        this.circle_state = 8;
                     }
                  }
               } else {
                  this.cabin_state = 0;
                  this.face_state = 0;
                  if (this.pro_state == 0 || this.pro_state == 1) {
                     this.circle_state = 0;
                  }

                  this.circle.setAvaliable(true);
                  this.defence.setAvaliable(true);
                  if (this.arm != null) {
                     this.arm.setAvaliable(false);
                  }

                  this.face_cnt = 0;
               }
            }

            short var10;
            byte var13;
            switch(this.pro_state) {
            case 0:
               this.isMachineShake = true;
               if (this.wait_frame_cn < this.wait_frame_max) {
                  ++this.wait_frame_cn;
                  if (this.wait_frame_cn == 18) {
                     this.setActionMode();
                  }
               } else {
                  this.wait_frame_cn = 0;
                  this.pro_state = this.attack_state;
               }
               break;
            case 1:
               ++this.wait_frame_cn;
               if (this.wait_frame_cn == 25) {
                  this.circleState = 1;
               }

               if (this.wait_frame_cn == 31) {
                  if (this.posX == 152832) {
                     this.velocity = 720;
                  } else if (this.posX == 167680) {
                     this.velocity = -720;
                  }
               }

               if (this.wait_frame_cn > 31 && Math.abs(this.velocity) != 480) {
                  this.posX += this.velocity;
               }

               if (this.wait_frame_cn > 31) {
                  if (this.posX > 172672) {
                     this.velocity = -480;
                     this.circleDrawer.setTrans(0);
                     this.faceDrawer.setTrans(0);
                  } else if (this.posX < 147840) {
                     this.velocity = 480;
                     this.circleDrawer.setTrans(2);
                     this.faceDrawer.setTrans(2);
                  }

                  if (this.velocity == -480) {
                     if (this.posX + this.velocity > 167680) {
                        this.posX += this.velocity;
                     } else {
                        this.posX = 167680;
                        this.pro_state = 0;
                        this.wait_frame_max = MyRandom.nextInt(0, 10) * 4 + 32;
                        this.wait_frame_cn = 0;
                        this.circleState = 2;
                     }
                  }

                  if (this.velocity == 480) {
                     if (this.posX + this.velocity < 152832) {
                        this.posX += this.velocity;
                     } else {
                        this.posX = 152832;
                        this.pro_state = 0;
                        this.wait_frame_max = MyRandom.nextInt(0, 10) * 4 + 32;
                        this.wait_frame_cn = 0;
                        this.circleState = 2;
                     }
                  }
               }
               break;
            case 2:
               ++this.wait_frame_cn;
               if (this.wait_frame_cn == 3) {
                  this.circle_state = 2;
                  this.circleState = 1;
               }

               if (this.wait_frame_cn == 9) {
                  this.circle_state = 3;
                  this.isMachineShake = false;
                  this.rayOffsetY = 256;
                  SoundSystem.getInstance().playSe(61);
                  var3 = this.posX;
                  if (this.posX == 152832) {
                     var10 = 2048;
                  } else {
                     var10 = -2048;
                  }

                  var6 = this.posY;
                  var7 = this.rayOffsetY;
                  if (this.posX == 152832) {
                     var13 = 1;
                  } else {
                     var13 = 0;
                  }

                  BulletObject.addBullet(21, var3 + var10, var6 + 0 + 384 + var7, var13, 0);
               }

               if (this.wait_frame_cn == 17) {
                  this.isMachineShake = true;
                  this.circle_state = 2;
                  this.rayOffsetY = 0;
               }

               if (this.wait_frame_cn == 20) {
                  this.circle_state = 0;
                  this.circleState = 2;
               }

               if (this.wait_frame_cn == 23) {
                  this.actionInit();
               }
               break;
            case 3:
               ++this.wait_frame_cn;
               if (this.wait_frame_cn == 3) {
                  this.circle_state = 5;
                  this.circleState = 1;
                  this.recoil_offsetx = 0;
               }

               if (this.wait_frame_cn == 13) {
                  this.isMachineShake = false;
                  this.circle_state = 6;
               }

               if (this.wait_frame_cn == 14) {
                  var6 = this.posX;
                  if (this.posX == 152832) {
                     var10 = 2048;
                  } else {
                     var10 = -2048;
                  }

                  var7 = this.posY;
                  if (MyRandom.nextInt(0, 10) > 5) {
                     var13 = 1;
                  } else {
                     var13 = 0;
                  }

                  byte var11;
                  if (this.posX == 152832) {
                     var11 = 1;
                  } else {
                     var11 = 0;
                  }

                  BulletObject.addBullet(20, var6 + var10, var7, var13, var11);
                  SoundSystem.getInstance().playSe(36);
               }

               if (this.wait_frame_cn >= 14 && this.wait_frame_cn < 16) {
                  var2 = this.recoil_offsetx;
                  if (this.posX == 152832) {
                     var10 = -128;
                  } else {
                     var10 = 128;
                  }

                  this.recoil_offsetx = var2 + var10;
               }

               if (this.wait_frame_cn >= 16 && this.wait_frame_cn < 18) {
                  var2 = this.recoil_offsetx;
                  if (this.posX == 152832) {
                     var10 = 128;
                  } else {
                     var10 = -128;
                  }

                  this.recoil_offsetx = var2 - var10;
               }

               if (this.wait_frame_cn == 16) {
                  this.isMachineShake = true;
                  this.circle_state = 5;
               }

               if (this.wait_frame_cn == 21) {
                  this.recoil_offsetx = 0;
                  this.circleState = 2;
               }

               if (this.wait_frame_cn == 24) {
                  this.actionInit();
                  this.circle_state = 0;
               }
               break;
            case 4:
               ++this.wait_frame_cn;
               if (player.isDead) {
                  return;
               }

               if (this.wait_frame_cn == 3) {
                  this.circle_state = 8;
                  this.circleState = 1;
                  this.isinArmRange = false;
                  this.isMachineShake = false;
                  this.aim_max = 48;
                  this.isArmUncaughtEnd = false;
                  this.isArmCaughtEnd = false;
                  this.boss_hang_posx = 0;
                  this.armDegreeLock = true;
                  this.isAiming = false;
                  if (this.posX == 152832) {
                     this.degree = 1;
                  }
               }

               byte var9;
               if (this.wait_frame_cn == 9) {
                  this.arm_posx = 0;
                  this.arm_posy = 0;
                  var3 = this.posX;
                  var6 = this.arm_posx;
                  var2 = this.posY;
                  var7 = this.arm_posy;
                  if (this.posX == 152832) {
                     var9 = 1;
                  } else {
                     var9 = 0;
                  }

                  BossF3Arm var12 = new BossF3Arm(var3 + var6, var2 + var7, var9);
                  this.arm = var12;
                  if (this.posX == 152832) {
                     var10 = 640;
                  } else {
                     var10 = -640;
                  }

                  this.arm_velx = var10;
                  this.arm_vely = 0;
                  this.circle_state = 9;
               }

               if (this.wait_frame_cn >= 9 && this.wait_frame_cn < 13) {
                  if (this.posX == 152832) {
                     if (this.arm_posx + this.arm_velx >= this.arm_velx * 4) {
                        this.arm_posx = this.arm_velx * 4;
                        this.armDegreeLock = false;
                     } else {
                        this.arm_posx += this.arm_velx;
                     }
                  } else if (this.arm_posx + this.arm_velx <= this.arm_velx * 4) {
                     this.arm_posx = this.arm_velx * 4;
                     this.armDegreeLock = false;
                  } else {
                     this.arm_posx += this.arm_velx;
                  }
               }

               if (this.wait_frame_cn == 13) {
                  var1 = MyRandom.nextInt(0, 100);
                  if (var1 < 80) {
                     var9 = 48;
                  } else {
                     var9 = 16;
                  }

                  this.aim_max = var9;
                  this.isAiming = true;
               }

               if (this.wait_frame_cn == this.aim_max + 13 - 1) {
                  if (this.arm != null) {
                     this.arm.setAvaliable(true);
                  }

                  this.isAiming = false;
               }

               if (this.wait_frame_cn == this.aim_max + 13) {
                  if (this.posX == 152832) {
                     if (player.getFootPositionX() - this.posX > 0) {
                        var2 = player.getFootPositionX() - this.posX;
                        var3 = player.getFootPositionY() - this.posY;
                        var1 = MFMath.sqrt(var2 * var2 + var3 * var3) >> 6;
                        this.arm_velx = var2 * 1320 / var1;
                        this.arm_vely = var3 * 1320 / var1;
                        this.arm_pre_velx = this.arm_velx;
                        this.arm_pre_vely = this.arm_vely;
                        this.isinArmRange = true;
                     } else {
                        this.arm_pre_velx = 0;
                        this.arm_velx = 0;
                        this.arm_pre_vely = 0;
                        this.arm_vely = 0;
                        this.isinArmRange = false;
                     }
                  } else if (player.getFootPositionX() - this.posX < 0) {
                     var1 = player.getFootPositionX() - this.posX;
                     var3 = player.getFootPositionY() - this.posY;
                     var2 = MFMath.sqrt(var1 * var1 + var3 * var3) >> 6;
                     this.arm_velx = var1 * 1320 / var2;
                     this.arm_vely = var3 * 1320 / var2;
                     this.arm_pre_velx = this.arm_velx;
                     this.arm_pre_vely = this.arm_vely;
                     this.isinArmRange = true;
                  } else {
                     this.arm_pre_velx = 0;
                     this.arm_velx = 0;
                     this.arm_pre_vely = 0;
                     this.arm_vely = 0;
                     this.isinArmRange = false;
                  }

                  this.isCaught = false;
                  this.arm_attack_step = 1;
                  SoundSystem.getInstance().playSe(83);
               }

               if (this.wait_frame_cn > this.aim_max + 13 && !this.isCaught && !this.isinArmRange && this.arm_attack_step == 1) {
                  this.isinArmRange = false;
                  this.arm_attack_step = 3;
               }

               if (this.wait_frame_cn > this.aim_max + 13 && !this.isCaught && this.isinArmRange && this.arm_attack_step == 1) {
                  if (this.posX == 152832) {
                     if (this.arm.getCaughtState()) {
                        this.arm_posx = player.getFootPositionX() - this.posX;
                        this.arm_posy = player.getFootPositionY() - this.posY - (player.getCollisionRect().getHeight() >> 1);
                        this.isCaught = true;
                        this.arm_attack_step = 2;
                     } else {
                        this.arm_posx += this.arm_velx;
                        this.arm_posy += this.arm_vely;
                        if (this.posX + this.arm_posx + this.arm_velx - 2560 >= player.getFootPositionX()) {
                           this.isinArmRange = false;
                           this.arm_attack_step = 2;
                           this.arm.setCatchState(1);
                        }
                     }
                  } else if (this.arm.getCaughtState()) {
                     this.arm_posx = player.getFootPositionX() - this.posX;
                     this.arm_posy = player.getFootPositionY() - this.posY - (player.getCollisionRect().getHeight() >> 1);
                     this.isCaught = true;
                     this.arm_attack_step = 2;
                  } else {
                     this.arm_posx += this.arm_velx;
                     this.arm_posy += this.arm_vely;
                     if (this.posX + this.arm_posx + this.arm_velx + 2560 <= player.getFootPositionX()) {
                        this.isinArmRange = false;
                        this.arm_attack_step = 2;
                        this.arm.setCatchState(1);
                     }
                  }
               }

               if (this.wait_frame_cn > this.aim_max + 13 + 3 && this.arm_attack_step == 2) {
                  this.arm_attack_step = 3;
                  this.arm.setAniState(1);
               }

               if (this.wait_frame_cn > this.aim_max + 13 + 3 && this.arm_attack_step == 3 && !this.isArmUncaughtEnd && !this.isArmCaughtEnd) {
                  if (this.isCaught) {
                     this.arm_velx = -this.arm_pre_velx * 1440 / 1320;
                     this.arm_vely = -this.arm_pre_vely * 1440 / 1320;
                     this.arm_posx += this.arm_velx;
                     this.arm_posy += this.arm_vely;
                     if (this.posX == 152832) {
                        if (this.arm_posx <= 4864) {
                           this.arm_posx = 4864;
                           this.arm_posy = 0;
                           this.isArmCaughtEnd = true;
                           this.isArmBack = false;
                           this.radius = 2304;
                           this.wait_frame_offset_cn = 0;
                           this.isArmMove = false;
                           this.arm.setShakeState(true);
                           this.boss_hang_posx = 152832;
                        }
                     } else if (this.arm_posx >= -4864) {
                        this.arm_posx = -4864;
                        this.arm_posy = 0;
                        this.isArmCaughtEnd = true;
                        this.isArmBack = false;
                        this.radius = 2304;
                        this.wait_frame_offset_cn = 0;
                        this.isArmMove = false;
                        this.arm.setShakeState(true);
                        this.boss_hang_posx = 167680;
                     }
                  } else {
                     if (this.arm_posx != 0 || this.arm_posy != 0) {
                        this.arm_velx = -this.arm_pre_velx * 480 / 1320;
                        this.arm_vely = -this.arm_pre_vely * 480 / 1320;
                        if (this.degree == 0) {
                           if (this.posX == 167680 && this.arm_velx < 256) {
                              this.arm_velx = 256;
                           }

                           if (this.posX == 152832 && this.arm_velx > -256) {
                              this.arm_velx = -256;
                           }
                        }

                        this.arm_posx += this.arm_velx;
                        if (this.arm_vely > 0) {
                           if (this.arm_posy < 0) {
                              this.arm_posy += this.arm_vely;
                           } else {
                              this.arm_posy = 0;
                              this.armDegreeLock = true;
                              if (this.posX == 152832) {
                                 this.degree = 0;
                              } else {
                                 this.degree = 0;
                              }
                           }
                        } else if (this.arm_vely < 0) {
                           if (this.arm_posy > 0) {
                              this.arm_posy += this.arm_vely;
                           } else {
                              this.arm_posy = 0;
                              this.armDegreeLock = true;
                              if (this.posX == 152832) {
                                 this.degree = 0;
                              } else {
                                 this.degree = 0;
                              }
                           }
                        }
                     }

                     if (this.posX == 152832) {
                        if (this.arm_velx == 0 && this.arm_posx > 0) {
                           this.arm_posx -= 480;
                        }

                        if (this.arm_posx <= 0) {
                           this.arm_posx = 0;
                           this.arm_posy = 0;
                           this.isArmUncaughtEnd = true;
                           this.wait_frame_offset_cn = 0;
                           if (this.arm_posy == this.armStartPosY) {
                              this.armDegreeLock = true;
                              this.degree = 0;
                           }
                        }

                        if (player.getFootPositionX() <= 152832) {
                           this.arm_posy = 0;
                           this.wait_frame_offset_cn = 0;
                           if (this.arm_posy == this.armStartPosY) {
                              this.armDegreeLock = true;
                              this.degree = 0;
                           }
                        }
                     } else {
                        if (this.arm_velx == 0 && this.arm_posx < 0) {
                           this.arm_posx += 480;
                        }

                        if (this.arm_posx >= 0) {
                           this.arm_posx = 0;
                           this.arm_posy = 0;
                           this.isArmUncaughtEnd = true;
                           this.wait_frame_offset_cn = 0;
                           if (this.arm_posy == this.armStartPosY) {
                              this.armDegreeLock = true;
                              this.degree = 0;
                           }
                        }

                        if (player.getFootPositionX() >= 167680) {
                           this.arm_posy = 0;
                           this.wait_frame_offset_cn = 0;
                           if (this.arm_posy == this.armStartPosY) {
                              this.armDegreeLock = true;
                              this.degree = 0;
                           }
                        }
                     }
                  }
               }

               if (this.isArmUncaughtEnd) {
                  ++this.wait_frame_offset_cn;
                  if (this.wait_frame_offset_cn == 13) {
                     this.circleState = 2;
                  }

                  if (this.wait_frame_offset_cn == 16) {
                     this.circle_state = 0;
                     this.arm = null;
                     this.arm_attack_step = 0;
                     this.actionInit();
                  }
               }

               if (this.isArmCaughtEnd) {
                  ++this.wait_frame_offset_cn;
                  if (this.boss_hang_posx == 152832) {
                     if (!this.isArmBack) {
                        for(var1 = 0; var1 < 5; ++var1) {
                           if (this.wait_frame_offset_cn > var1 * 10 + 0 && this.wait_frame_offset_cn <= var1 * 10 + 5) {
                              var2 = this.radius * MyAPI.dCos(360 - (this.wait_frame_offset_cn - var1 * 10) * 18) / 100;
                              this.arm_posx = var2 + 512 + 1280;
                              var2 = MyAPI.dCos(360 - (this.wait_frame_offset_cn - var1 * 10) * 18) * 768 / 100;
                              this.shakechainx = var2 + 512 + 1280;
                              this.arm_posy = this.radius * MyAPI.dSin(360 - (this.wait_frame_offset_cn - var1 * 10) * 18) / 100;
                              this.shakechainy = MyAPI.dSin(360 - (this.wait_frame_offset_cn - var1 * 10) * 18) * 768 / 100;
                           } else if (this.wait_frame_offset_cn > var1 * 10 + 5 && this.wait_frame_offset_cn <= var1 * 10 + 10) {
                              var2 = this.radius * MyAPI.dCos(360 - (90 - (this.wait_frame_offset_cn - var1 * 10 - 5) * 18)) / 100;
                              this.arm_posx = var2 + 512 + 1280;
                              var2 = MyAPI.dCos(360 - (90 - (this.wait_frame_offset_cn - var1 * 10 - 5) * 18)) * 768 / 100;
                              this.shakechainx = var2 + 512 + 1280;
                              this.arm_posy = this.radius * MyAPI.dSin(360 - (90 - (this.wait_frame_offset_cn - var1 * 10 - 5) * 18)) / 100;
                              this.shakechainy = MyAPI.dSin(360 - (90 - (this.wait_frame_offset_cn - var1 * 10 - 5) * 18)) * 768 / 100;
                              if (this.wait_frame_offset_cn == var1 * 10 + 9 && this.wait_frame_offset_cn < 45) {
                                 this.playerLoseRings();
                              }
                           }

                           if (this.wait_frame_offset_cn == 35) {
                              this.isArmMove = true;
                           }

                           if (this.wait_frame_offset_cn == 45) {
                              this.arm.setAvaliable(false);
                              this.arm.setCaughtFlag(false);
                              this.arm.releasePlayer();
                           }

                           if (this.isArmMove) {
                              this.posX += 144;
                           }
                        }
                     }

                     if (this.wait_frame_offset_cn == 50) {
                        this.arm_posx = 0;
                        this.arm_posy = 0;
                        this.shakechainx = 0;
                        this.shakechainy = 0;
                        this.circle_state = 8;
                        this.isArmBack = false;
                     }

                     if (this.wait_frame_offset_cn > 50 && !this.isArmBack) {
                        this.posX += 720;
                        if (this.posX >= 172672) {
                           this.posX = 172672;
                           this.isArmBack = true;
                           this.degree = 0;
                        }
                     }

                     if (this.isArmBack) {
                        this.posX -= 480;
                        if (this.posX <= 167680) {
                           this.posX = 167680;
                           this.velocity = -480;
                           this.circle_state = 0;
                           this.circleDrawer.setTrans(0);
                           this.faceDrawer.setTrans(0);
                           this.pro_state = 0;
                           this.wait_frame_max = MyRandom.nextInt(0, 10) * 4 + 32;
                           this.wait_frame_cn = 0;
                           this.circleState = 2;
                           this.arm_attack_step = 0;
                           this.wait_frame_offset_cn = 0;
                           this.isinArmRange = false;
                           this.isMachineShake = false;
                           this.aim_max = 48;
                           this.isArmUncaughtEnd = false;
                           this.isArmCaughtEnd = false;
                           this.arm = null;
                        }
                     }
                  } else if (this.boss_hang_posx == 167680) {
                     if (!this.isArmBack) {
                        for(var1 = 0; var1 < 5; ++var1) {
                           if (this.wait_frame_offset_cn > var1 * 10 + 0 && this.wait_frame_offset_cn <= var1 * 10 + 5) {
                              var2 = -(this.radius * MyAPI.dCos(360 - (this.wait_frame_offset_cn - var1 * 10) * 18) / 100);
                              this.arm_posx = var2 - 512 - 1280;
                              var2 = -(MyAPI.dCos(360 - (this.wait_frame_offset_cn - var1 * 10) * 18) * 768 / 100);
                              this.shakechainx = var2 - 512 - 1280;
                              this.arm_posy = this.radius * MyAPI.dSin(360 - (this.wait_frame_offset_cn - var1 * 10) * 18) / 100;
                              this.shakechainy = MyAPI.dSin(360 - (this.wait_frame_offset_cn - var1 * 10) * 18) * 768 / 100;
                           } else if (this.wait_frame_offset_cn > var1 * 10 + 5 && this.wait_frame_offset_cn <= var1 * 10 + 10) {
                              var2 = -(this.radius * MyAPI.dCos(360 - (90 - (this.wait_frame_offset_cn - var1 * 10 - 5) * 18)) / 100);
                              this.arm_posx = var2 - 512 - 1280;
                              var2 = -(MyAPI.dCos(360 - (90 - (this.wait_frame_offset_cn - var1 * 10 - 5) * 18)) * 768 / 100);
                              this.shakechainx = var2 - 512 - 1280;
                              this.arm_posy = this.radius * MyAPI.dSin(360 - (90 - (this.wait_frame_offset_cn - var1 * 10 - 5) * 18)) / 100;
                              this.shakechainy = MyAPI.dSin(360 - (90 - (this.wait_frame_offset_cn - var1 * 10 - 5) * 18)) * 768 / 100;
                              if (this.wait_frame_offset_cn == var1 * 10 + 9 && this.wait_frame_offset_cn < 45) {
                                 this.playerLoseRings();
                              }
                           }

                           if (this.wait_frame_offset_cn == 35) {
                              this.isArmMove = true;
                           }

                           if (this.wait_frame_offset_cn == 45) {
                              this.arm.setAvaliable(false);
                              this.arm.setCaughtFlag(false);
                              this.arm.releasePlayer();
                           }

                           if (this.isArmMove) {
                              this.posX -= 144;
                           }
                        }
                     }

                     if (this.wait_frame_offset_cn == 50) {
                        this.arm_posx = 0;
                        this.arm_posy = 0;
                        this.shakechainx = 0;
                        this.shakechainy = 0;
                        this.circle_state = 8;
                     }

                     if (this.wait_frame_offset_cn > 50 && !this.isArmBack) {
                        this.posX -= 720;
                        if (this.posX <= 147840) {
                           this.posX = 147840;
                           this.isArmBack = true;
                           this.degree = 0;
                        }
                     }

                     if (this.isArmBack) {
                        this.posX += 480;
                        if (this.posX >= 152832) {
                           this.posX = 152832;
                           this.velocity = 480;
                           this.circle_state = 0;
                           this.circleDrawer.setTrans(2);
                           this.faceDrawer.setTrans(2);
                           this.pro_state = 0;
                           this.wait_frame_max = MyRandom.nextInt(0, 10) * 4 + 32;
                           this.wait_frame_cn = 0;
                           this.circleState = 2;
                           this.arm_attack_step = 0;
                           this.wait_frame_offset_cn = 0;
                           this.isinArmRange = false;
                           this.isMachineShake = false;
                           this.aim_max = 48;
                           this.isArmUncaughtEnd = false;
                           this.isArmCaughtEnd = false;
                           this.arm = null;
                        }
                     }
                  }
               }

               if (this.arm != null) {
                  this.arm.logic(this.posX + this.arm_posx, this.posY + this.arm_posy);
                  if (!this.armDegreeLock) {
                     if (this.isAiming) {
                        if (this.posX == 152832) {
                           var1 = this.posY;
                           var3 = player.getFootPositionY();
                           var2 = this.posX;
                           var6 = player.getFootPositionX();
                           var1 = this.calArmDegree(var1 - var3, var2 - 1280 - var6);
                           this.degree = var1 - 180;
                        } else if (this.posX == 167680) {
                           var6 = this.posY;
                           var1 = player.getFootPositionY();
                           var2 = this.posX;
                           var3 = player.getFootPositionX();
                           this.degree = this.calArmDegree(var6 - var1, var2 + 1280 - var3);
                        }
                     }

                     if (this.isArmCaughtEnd && !this.isArmBack) {
                        if (this.boss_hang_posx == 152832) {
                           this.degree = this.calArmDegree(this.arm_posy, this.arm_posx - 1280);
                        } else if (this.boss_hang_posx == 167680) {
                           this.degree = this.calArmDegree(this.arm_posy, this.arm_posx + 1280) - 180;
                        }
                     } else if (this.boss_hang_posx == 152832) {
                        this.degree = this.calArmDegree(this.arm_posy, this.arm_posx);
                     } else if (this.boss_hang_posx == 167680) {
                        this.degree = this.calArmDegree(this.arm_posy, this.arm_posx) - 180;
                     }
                  }

                  if (this.posX == 167680 && this.degree > 80 && this.degree < 280 && this.arm != null && !this.arm.getCaughtState()) {
                     if (this.degree < 180) {
                        this.degree = 80;
                     } else {
                        this.degree = 280;
                     }
                  }

                  if (this.posX == 152832 && (this.degree <= -80 || this.degree >= 80) && this.arm != null && !this.arm.getCaughtState()) {
                     if (this.degree < 0) {
                        this.degree = -80;
                     } else {
                        this.degree = 80;
                     }
                  }

                  if (this.wait_frame_offset_cn <= 50) {
                     this.arm.setDegree(this.degree);
                  }
               }
            }

            this.circle.logic(this.posX + this.recoil_offsetx, this.posY + this.shakeOffsetY + this.circleOffsetY + this.rayOffsetY);
            this.defence.logic(this.posX + this.recoil_offsetx, this.posY + this.shakeOffsetY + this.rayOffsetY);
            if (this.circle.getHurtState() || this.defence.getHurtState()) {
               this.face_state = 2;
               this.face_cnt = 0;
               this.circle.resetHurtState();
               this.defence.resetHurtState();
            }
            break;
         case 3:
            if (this.arm != null) {
               this.arm.logic(this.posX + this.arm_posx, this.posY + this.arm_posy);
               if (this.arm_posy + this.posY + this.arm_drop_vely >= this.getGroundY(this.posX + this.arm_posx, this.posY + this.arm_posy) - 320) {
                  this.arm_posy = this.getGroundY(this.posX + this.arm_posx, this.posY + this.arm_posy) - this.posY - 320;
                  this.arm_drop_vely = -this.arm_drop_vely / 2;
                  if (this.arm_drop_vely > -GRAVITY / 2) {
                     this.arm_drop_vely = 0;
                     this.arm = null;
                  }
               } else {
                  this.arm_posy += this.arm_drop_vely;
                  this.arm_drop_vely += GRAVITY / 2;
               }
            }

            var3 = this.posY;
            var2 = this.shakeOffsetY;
            var1 = this.rayOffsetY;
            var6 = this.drop_vely;
            if (var3 + var2 + var1 + 1600 + var6 >= this.getGroundY(this.posX, this.posY + this.shakeOffsetY + this.rayOffsetY)) {
               var2 = this.getGroundY(this.posX, this.posY + this.shakeOffsetY + this.rayOffsetY);
               var3 = this.shakeOffsetY;
               var1 = this.rayOffsetY;
               this.posY = var2 - (var3 + var1 + 1600);
               this.drop_vely = -this.drop_vely / 2;
               if (this.drop_vely > -GRAVITY / 2) {
                  this.drop_vely = 0;
               }
            } else {
               this.posY += this.drop_vely;
               this.drop_vely += GRAVITY / 2;
            }

            ++this.brokenFrame;
            var1 = MapManager.getCamera().x;
            var2 = MapManager.getCamera().y;
            if (!StageManager.isGoingToExtraStage()) {
               if (this.brokenFrame % 3 == 0) {
                  var3 = MyRandom.nextInt(0, SCREEN_WIDTH);
                  var6 = MyRandom.nextInt(0, SCREEN_HEIGHT);
                  addGameObject(new Boom(37, var3 + var1 << 6, var6 + var2 << 6, 0, 0, 0, 0));
               }

               if (this.brokenFrame % 3 == 0) {
                  var3 = MyRandom.nextInt(0, SCREEN_WIDTH);
                  var6 = MyRandom.nextInt(0, SCREEN_HEIGHT);
                  addGameObject(new BreakingParts(38, var3 + var1 << 6, var6 + var2 << 6, 0, 0, 0, 0));
               }
            }

            if (StageManager.isGoingToExtraStage()) {
               if (this.brokenFrame < 43) {
                  this.bossbroken.logicBoom(this.posX, this.posY);
               }

               if (this.brokenFrame == 43) {
                  var1 = this.posX;
                  var2 = this.posY;
                  addGameObject(new Boom(37, var1 + 1920, var2, 0, 0, 0, 0));
                  var2 = this.posX;
                  var1 = this.posY;
                  addGameObject(new Boom(37, var2 - 1920, var1, 0, 0, 0, 0));
                  var2 = this.posX;
                  var1 = this.posY;
                  addGameObject(new Boom(37, var2, var1 - 1920, 0, 0, 0, 0));
                  var2 = this.posX;
                  var1 = this.posY;
                  addGameObject(new Boom(37, var2, var1 + 1920, 0, 0, 0, 0));
                  soundInstance.playSe(35);
               }
            } else if (this.brokenFrame < 68) {
               this.bossbroken.logicBoom(this.posX, this.posY);
            } else if (this.brokenFrame % 1 == 0) {
               var6 = player.posX;
               PlayerObject var8 = player;
               var7 = MyRandom.nextInt(0, 60);
               var1 = player.posY;
               var8 = player;
               var2 = 1536 / 2;
               var3 = MyRandom.nextInt(0, 60);
               addGameObject(new Boom(37, var6 - 1024 * 2 + (var7 - 30 << 6), var1 - var2 + (var3 - 30 << 6), 0, 0, 0, 0));
               soundInstance.playSe(35);
            }

            if (this.brokenFrame == 48) {
               if (StageManager.isGoingToExtraStage()) {
                  this.state = 4;
                  this.escapefacedrawer.setActionId(4);
                  this.escapefacedrawer.setLoop(true);
                  this.fly_top = this.posY;
                  this.fly_end = 169472;
                  this.wait_cnt = 0;
               } else {
                  player.setMeetingBoss(true);
                  bossFighting = false;
                  MapManager.setCameraRightLimit(MapManager.getPixelWidth());
               }
            }
            break;
         case 4:
            ++this.wait_cnt;
            if (this.wait_cnt >= this.wait_cnt_max && this.posY >= this.fly_top - this.fly_top_range) {
               this.posY -= this.escape_v;
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
               this.posX += this.escape_v;
               if (this.posX - this.fly_end > this.fly_top_range) {
                  this.posY -= this.fly_top_range;
               }
            }

            if (this.posX - this.fly_end > this.fly_top_range && this.WaitCnt == 3) {
               player.setMeetingBoss(true);
               bossFighting = false;
               MapManager.setCameraRightLimit(MapManager.getPixelWidth() - 1);
            }
         }

         this.changeAniState(this.cabinDrawer, this.cabin_state);
         this.changeAniState(this.circleDrawer, this.circle_state);
         this.changeAniState(this.faceDrawer, this.face_state);
         this.refreshCollisionRect(this.posX + this.recoil_offsetx, this.posY + this.shakeOffsetY + this.rayOffsetY);
         this.checkWithPlayer(var4, var5, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1344, var2 - 1024 - 768, 2688, 1024);
   }
}
