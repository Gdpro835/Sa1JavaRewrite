package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Boss4 extends BossObject {
   private static final int ATTACK_FIRE = 3;
   private static final int ATTACK_INIT = 0;
   private static final int ATTACK_MOVE = 1;
   private static final int ATTACK_TIME_MAX = 32;
   private static final int ATTACK_WAIT = 2;
   private static final int BOSS_LAUGH_MAX = 16;
   private static final int BOSS_SHOW_END_POSX = 556544;
   private static final int FACE_HURT = 2;
   private static final int FACE_NORMAL = 0;
   private static final int FACE_SMILE = 1;
   private static final int FLY_TOP_OFFSETY = 9600;
   private static final int ICE_INTERVAL = 1280;
   private static final int MACHINE_ATTACK = 2;
   private static final int MACHINE_ATTACK_HURT = 3;
   private static final int MACHINE_BASE_HALF_HEIGHT = 1280;
   private static final int MACHINE_MOVE = 0;
   private static final int MACHINE_MOVE_HURT = 1;
   private static final int MACHINE_WAIT = 4;
   private static final int MACHINE_WAIT_HURT = 5;
   private static final int MOVE_INTERVAL = 2048;
   private static final int MOVE_SPEED = 256;
   private static final int SHOW_BOSS_END = 2;
   private static final int SHOW_BOSS_ENTER = 0;
   private static final int SHOW_BOSS_LAUGH = 1;
   private static final int SIDE_DOWN1 = 1758;
   private static final int SIDE_DOWN2 = 1808;
   private static final int SIDE_LEFT = 8496;
   private static final int SIDE_MIDDLE = 553984;
   private static final int SIDE_RIGHT = 8816;
   private static final int SIDE_UP = 1616;
   private static final int STATE_BROKEN = 3;
   private static final int STATE_ENTER_SHOW = 1;
   private static final int STATE_ESCAPE = 4;
   private static final int STATE_INIT = 0;
   private static final int STATE_PRO = 2;
   private static final int StartPosX = 548992;
   private static final int TOUCH_BOTTOM_CNT_MAX = 20;
   private static final int WAIT_TIME_MAX = 8;
   private static final int WATER_LEVEL_DROP_SPEED = 2;
   private static Animation boatAni;
   private static final int cnt_max = 30;
   private static Animation escapefaceAni;
   private static Animation faceAni;
   private static final int limitLeftIceX = 544384;
   private static final int limitLeftX = 545792;
   private static final int limitRightX = 562176;
   private static Animation machineAni;
   private static MFImage machineBase;
   private static Animation machinePartsAni;
   private int COLLISION_HEIGHT = 3648;
   private int COLLISION_WIDTH = 2560;
   private int WaitCnt;
   private int attack_cn;
   private int attack_distance;
   private int attack_step;
   private AnimationDrawer boatdrawer;
   private BossBroken bossbroken;
   private boolean direct = false;
   private int escape_v = 512;
   private AnimationDrawer escapefacedrawer;
   private AnimationDrawer faceDrawer;
   private int face_cnt;
   private int face_state;
   private int fly_end;
   private int fly_top;
   private int fly_top_range = 3840;
   public boolean isNoneIce = false;
   private int laugh_cn;
   private AnimationDrawer machineDrawer;
   private AnimationDrawer machinePartsdrawer;
   private int machine_cnt;
   private int machine_state;
   private int move_distance;
   private int move_velX;
   private int[][] parts_pos;
   private int[][] parts_v;
   private int posStartX;
   private int pro_machine_state;
   private int pro_machine_state2;
   private int show_step;
   private int state;
   private int touch_bottom_cnt;
   private int wait_cn;
   private int wait_cnt;
   private int wait_cnt_max = 10;
   private int water_level;

   protected Boss4(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.posY += 1024;
      if (machineAni == null) {
         machineAni = new Animation("/animation/boss4_machine");
      }

      this.machineDrawer = machineAni.getDrawer(0, true, 0);
      if (faceAni == null) {
         faceAni = new Animation("/animation/boss4_face");
      }

      this.faceDrawer = faceAni.getDrawer(0, true, 0);
      machineBase = MFImage.createImage("/animation/boss4_base.png");
      if (machinePartsAni == null) {
         machinePartsAni = new Animation("/animation/boss4_parts");
      }

      this.machinePartsdrawer = machinePartsAni.getDrawer(0, true, 0);
      this.parts_pos = new int[3][2];
      this.parts_v = new int[3][2];
      if (boatAni == null) {
         boatAni = new Animation("/animation/pod_boat");
      }

      this.boatdrawer = boatAni.getDrawer(0, true, 0);
      if (escapefaceAni == null) {
         escapefaceAni = new Animation("/animation/pod_face");
      }

      this.escapefacedrawer = escapefaceAni.getDrawer(4, true, 0);
      this.state = 0;
      this.wait_cn = 0;
      this.attack_cn = 0;
      this.isNoneIce = false;
      this.setBossHP();
   }

   private int facePosY() {
      short var1;
      switch(this.machineDrawer.getCurrentFrame()) {
      case 0:
      case 2:
         var1 = 2752;
         break;
      case 1:
      case 3:
      case 4:
         var1 = 2688;
         break;
      case 5:
         var1 = 2816;
         break;
      default:
         var1 = 2688;
      }

      return var1;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(machineAni);
      Animation.closeAnimation(faceAni);
      Animation.closeAnimation(machinePartsAni);
      Animation.closeAnimation(boatAni);
      Animation.closeAnimation(escapefaceAni);
      escapefaceAni = null;
      boatAni = null;
      machinePartsAni = null;
      machineBase = null;
      machineAni = null;
      faceAni = null;
   }

   private void setAniState(int var1, int var2) {
      this.machineDrawer.setActionId(var1);
      this.machineDrawer.setLoop(true);
      this.faceDrawer.setActionId(var2);
      this.faceDrawer.setLoop(true);
      this.face_state = var2;
   }

   private void setAniState(AnimationDrawer var1, int var2) {
      if (var1 == this.faceDrawer) {
         this.face_state = var2;
      }

      var1.setActionId(var2);
      var1.setLoop(true);
   }

   private void setMoveConf() {
      if (this.posX > 553984) {
         if (this.direct) {
            this.move_velX = -256;
         } else {
            this.move_velX = 256;
         }
      } else if (this.direct) {
         this.move_velX = 256;
      } else {
         this.move_velX = -256;
      }

      int var1 = MyRandom.nextInt(0, 100);
      if (var1 < 5) {
         this.move_distance = 1;
      } else if (var1 < 20) {
         this.move_distance = 2;
      } else if (var1 < 35) {
         this.move_distance = 3;
      } else if (var1 < 55) {
         this.move_distance = 4;
      } else if (var1 < 75) {
         this.move_distance = 5;
      } else if (var1 < 90) {
         this.move_distance = 6;
      } else {
         this.move_distance = 7;
      }

      this.setAniState(this.machineDrawer, 0);
      if (this.move_velX > 0) {
         this.machineDrawer.setTrans(2);
      } else {
         this.machineDrawer.setTrans(0);
      }

      this.posStartX = this.posX;
      this.attack_step = 1;
      this.wait_cn = 0;
   }

   public void close() {
      this.machineDrawer = null;
      this.faceDrawer = null;
      this.bossbroken = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (this.state == 2 && this.HP > 0 && (!(player instanceof PlayerTails) || player.getCharacterAnimationID() != 12 && player.getCharacterAnimationID() != 13 || player.getVelY() <= 0) && this.face_state != 2) {
         --this.HP;
         player.doBossAttackPose(this, var2);
         this.setAniState(this.faceDrawer, 2);
         this.pro_machine_state = this.machine_state;
         this.machine_state = this.pro_machine_state + 1;
         this.setAniState(this.machineDrawer, this.machine_state);
         if (this.HP == 0) {
            this.state = 3;
            this.isNoneIce = true;
            this.fly_top = this.posY + 9600;
            this.fly_end = 564224;

            for(var2 = 0; var2 < 3; ++var2) {
               this.parts_pos[var2][0] = this.posX;
               this.parts_pos[var2][1] = this.posY;
               int[] var4 = this.parts_v[var2];
               short var5;
               if (MyRandom.nextInt(0, 10) > 5) {
                  var5 = -640;
               } else {
                  var5 = 640;
               }

               var4[0] = var5;
               var4 = this.parts_v[var2];
               if (MyRandom.nextInt(0, 10) > 5) {
                  var5 = -320;
               } else {
                  var5 = -512;
               }

               var4[1] = var5;
            }

            this.setAniState(this.faceDrawer, 1);
            this.bossbroken = new BossBroken(25, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
            addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
            SoundSystem.getInstance().playSe(35, false);
         } else {
            SoundSystem.getInstance().playSe(34, false);
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && this.state == 2 && var1 == player && (!(player instanceof PlayerTails) || player.getCharacterAnimationID() != 12 && player.getCharacterAnimationID() != 13)) {
         if (player.isAttackingEnemy()) {
            if (this.HP > 0 && this.face_state != 2) {
               --this.HP;
               player.doBossAttackPose(this, var2);
               this.setAniState(this.faceDrawer, 2);
               this.pro_machine_state = this.machine_state;
               this.machine_state = this.pro_machine_state + 1;
               this.setAniState(this.machineDrawer, this.machine_state);
               if (this.HP == 0) {
                  this.state = 3;
                  this.isNoneIce = true;
                  this.fly_top = this.posY + 9600;
                  this.fly_end = 564224;

                  for(var2 = 0; var2 < 3; ++var2) {
                     this.parts_pos[var2][0] = this.posX;
                     this.parts_pos[var2][1] = this.posY;
                     int[] var4 = this.parts_v[var2];
                     short var3;
                     if (MyRandom.nextInt(0, 10) > 5) {
                        var3 = -640;
                     } else {
                        var3 = 640;
                     }

                     var4[0] = var3;
                     var4 = this.parts_v[var2];
                     if (MyRandom.nextInt(0, 10) > 5) {
                        var3 = -320;
                     } else {
                        var3 = -512;
                     }

                     var4[1] = var3;
                  }

                  this.setAniState(this.faceDrawer, 1);
                  this.bossbroken = new BossBroken(25, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
                  addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
                  SoundSystem.getInstance().playSe(35, false);
               } else {
                  SoundSystem.getInstance().playSe(34, false);
               }
            }
         } else if (this.machine_state != 1 && this.machine_state != 3 && this.machine_state != 5 && player.canBeHurt()) {
            player.beHurt();
            this.setAniState(this.faceDrawer, 1);
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         if (this.state != 3 && this.state != 4) {
            this.drawInMap(var1, this.machineDrawer);
            if (this.face_state != 0) {
               this.drawInMap(var1, this.faceDrawer, this.posX, this.posY + this.facePosY());
            }
         } else if (this.state == 3) {
            if (this.bossbroken != null) {
               this.bossbroken.draw(var1);
            }

            this.machinePartsdrawer.setActionId(0);
            this.drawInMap(var1, this.machinePartsdrawer, this.parts_pos[0][0], this.parts_pos[0][1]);
            this.machinePartsdrawer.setActionId(1);
            this.drawInMap(var1, this.machinePartsdrawer, this.parts_pos[1][0], this.parts_pos[1][1]);
            this.drawInMap(var1, this.machinePartsdrawer, this.parts_pos[2][0], this.parts_pos[2][1]);
            this.drawInMap(var1, machineBase, this.posX, this.posY, 3);
            this.drawInMap(var1, this.faceDrawer, this.posX, this.posY);
         } else if (this.state == 4) {
            this.drawInMap(var1, this.boatdrawer, this.posX, this.posY - 960);
            this.drawInMap(var1, this.escapefacedrawer, this.posX, this.posY - 2624);
         }

         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var2 = this.posX;
         int var3 = this.posY;
         if (this.state > 0) {
            isBossEnter = true;
         }

         int var1;
         int var4;
         label229:
         switch(this.state) {
         case 0:
            if (player.getFootPositionX() >= 548992) {
               this.state = 1;
               MapManager.setCameraLeftLimit(8496);
               MapManager.setCameraRightLimit(8816);
               MapManager.setCameraUpLimit(1616);
               MapManager.setCameraDownLimit(1758);
               if (!this.IsPlayBossBattleBGM) {
                  bossFighting = true;
                  bossID = 25;
                  SoundSystem.getInstance().playBgm(22, true);
                  this.IsPlayBossBattleBGM = true;
               }

               this.show_step = 0;
               this.setAniState(0, 0);
               player.setMeetingBoss(false);
               this.water_level = StageManager.getWaterLevel();
               if (player instanceof PlayerKnuckles) {
                  player.dripDownUnderWater();
               }

               player.isAttackBoss4 = true;
            }
            break;
         case 1:
            if (player instanceof PlayerKnuckles) {
               player.dripDownUnderWater();
            }

            switch(this.show_step) {
            case 0:
               if (this.posX > 556544) {
                  this.posX -= 256;
               } else {
                  this.show_step = 1;
                  this.setAniState(4, 1);
                  this.laugh_cn = 0;
               }
               break label229;
            case 1:
               if (this.laugh_cn < 16) {
                  ++this.laugh_cn;
               } else {
                  this.show_step = 2;
                  this.setAniState(this.faceDrawer, 0);
               }
               break label229;
            case 2:
               this.state = 2;
               this.attack_step = 0;
               this.wait_cn = 0;
               MapManager.setCameraDownLimit(1808);
            default:
               break label229;
            }
         case 2:
            if (MapManager.actualDownCameraLimit == MapManager.proposeDownCameraLimit) {
               player.setMeetingBoss(true);
            }

            if (this.face_state != 0) {
               if (this.face_cnt < 30) {
                  ++this.face_cnt;
               } else {
                  this.setAniState(this.faceDrawer, 0);
                  this.face_cnt = 0;
               }
            }

            if (this.machine_state != 0 && this.machine_state != 2 && this.machine_state != 4) {
               this.pro_machine_state2 = this.machine_state;
               if (this.machine_cnt < 30) {
                  ++this.machine_cnt;
               } else {
                  this.machine_state = this.pro_machine_state2 - 1;
                  this.setAniState(this.machineDrawer, this.machine_state);
                  this.machine_cnt = 0;
               }
            }

            switch(this.attack_step) {
            case 0:
               if (this.wait_cn < 8) {
                  ++this.wait_cn;
               } else {
                  this.direct = false;
                  if (MyRandom.nextInt(0, 100) <= 95) {
                     this.direct = true;
                  } else {
                     this.direct = false;
                  }

                  this.setMoveConf();
               }
               break label229;
            case 1:
               if (this.posStartX > 553984 && this.direct && this.posX >= this.posStartX - this.move_distance * 2048 || this.posStartX > 553984 && !this.direct && this.posX <= this.posStartX + this.move_distance * 2048 || this.posStartX <= 553984 && this.direct && this.posX <= this.posStartX + this.move_distance * 2048 || this.posStartX <= 553984 && !this.direct && this.posX >= this.posStartX - this.move_distance * 2048) {
                  if (this.posX > 560128) {
                     this.posX = 560128;
                     this.attack_step = 2;
                     this.setAniState(this.machineDrawer, 4);
                  } else if (this.posX < 545792) {
                     this.posX = 545792;
                     this.attack_step = 2;
                     this.setAniState(this.machineDrawer, 4);
                  } else {
                     this.posX += this.move_velX;
                  }
               } else {
                  if (this.posStartX > 553984) {
                     if (this.direct) {
                        this.posX = this.posStartX - this.move_distance * 2048;
                     } else {
                        this.posX = this.posStartX + this.move_distance * 2048;
                     }
                  } else if (this.direct) {
                     this.posX = this.posStartX + this.move_distance * 2048;
                  } else {
                     this.posX = this.posStartX - this.move_distance * 2048;
                  }

                  this.attack_step = 2;
                  this.setAniState(this.machineDrawer, 4);
               }

               this.wait_cn = 0;
               break label229;
            case 2:
               if (this.move_distance != 1) {
                  if (this.wait_cn < 8) {
                     ++this.wait_cn;
                  } else {
                     this.attack_step = 3;
                     this.setAniState(this.machineDrawer, 2);
                     this.attack_cn = 0;
                     this.wait_cn = 0;
                     MapManager.setShake(16);
                  }
               } else if (this.wait_cn < 16) {
                  ++this.wait_cn;
               } else {
                  this.direct = false;
                  if (MyRandom.nextInt(0, 100) >= 50) {
                     this.direct = true;
                  } else {
                     this.direct = false;
                  }

                  this.setMoveConf();
               }
               break label229;
            case 3:
               if (this.attack_cn < 32) {
                  ++this.attack_cn;
                  Boss4Ice var7;
                  if (this.HP > 4) {
                     switch(this.attack_cn) {
                     case 6:
                     case 22:
                        var1 = MyRandom.nextInt(16);
                        var4 = MyRandom.nextInt(92, 148);
                        var7 = new Boss4Ice(544384 + var1 * 1280, 103424, var4, 116352, this);
                        GameObject.addGameObject(var7);
                        SoundSystem.getInstance().playSe(36);
                     }
                  } else if (this.HP > 2) {
                     switch(this.attack_cn) {
                     case 6:
                     case 16:
                     case 25:
                        var4 = MyRandom.nextInt(16);
                        var1 = MyRandom.nextInt(92, 148);
                        var7 = new Boss4Ice(544384 + var4 * 1280, 103424, var1, 116352, this);
                        GameObject.addGameObject(var7);
                        SoundSystem.getInstance().playSe(36);
                     }
                  } else {
                     switch(this.attack_cn) {
                     case 6:
                     case 13:
                     case 22:
                     case 29:
                        var4 = MyRandom.nextInt(16);
                        var1 = MyRandom.nextInt(92, 148);
                        var7 = new Boss4Ice(544384 + var4 * 1280, 103424, var1, 116352, this);
                        GameObject.addGameObject(var7);
                        SoundSystem.getInstance().playSe(36);
                     }
                  }
               } else {
                  this.attack_step = 0;
                  this.setAniState(this.machineDrawer, 4);
                  this.attack_cn = 0;
                  this.wait_cn = 0;
               }
            default:
               break label229;
            }
         case 3:
            this.water_level += 2;
            StageManager.setWaterLevel(this.water_level);

            for(var1 = 0; var1 < 3; ++var1) {
               int[] var6 = this.parts_pos[var1];
               var6[0] += this.parts_v[var1][0];
               var6 = this.parts_v[var1];
               var6[1] += GRAVITY;
               var6 = this.parts_pos[var1];
               var6[1] += this.parts_v[var1][1];
            }

            if (this.posY >= this.getGroundY(this.posX, this.posY) - 1280) {
               this.posY = this.getGroundY(this.posX, this.posY) - 1280;
               ++this.touch_bottom_cnt;
               if (this.touch_bottom_cnt > 20) {
                  this.state = 4;
                  bossFighting = false;
                  SoundSystem.getInstance().playBgm(StageManager.getBgmId(), true);
               }
            } else {
               this.posY = this.water_level << 6;
            }

            this.bossbroken.logicBoom(this.posX, this.posY);
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
            }

            if (this.posX - this.fly_end > this.fly_top_range && this.WaitCnt == 3) {
               var1 = MapManager.getCamera().x;
               int var5 = MapManager.CAMERA_WIDTH;
               var4 = MapManager.getCamera().y;
               addGameObject(new Cage(var1 + (var5 >> 1) << 6, var4 + 40 << 6));
               this.WaitCnt = 4;
            }
         }

         this.checkWithPlayer(var2, var3, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - (this.COLLISION_WIDTH >> 1), var2, this.COLLISION_WIDTH, this.COLLISION_HEIGHT);
   }
}
