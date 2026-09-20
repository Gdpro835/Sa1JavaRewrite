package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class Boss6 extends BossObject {
   private static final int ATTACK_FIRE = 1;
   private static final int ATTACK_HURT = 2;
   private static final int ATTACK_MOVE = 0;
   private static final int BROKEN_X = 652032;
   private static final int BROKEN_Y = 145920;
   private static final int COLLISION_HEIGHT = 4096;
   private static final int COLLISION_WIDTH = 3072;
   private static final boolean DEBUG_BOSS6_1BLOOD = false;
   private static final boolean DEBUG_BOSS6_WUDI = false;
   private static final int ESCAPE_END_X = 656000;
   private static final int ESCAPE_END_Y = 150400;
   private static final int FACE_HURT = 2;
   private static final int FACE_NORMAL = 0;
   private static final int FACE_OFFSET = 1024;
   private static final int FACE_SMILE = 1;
   private static final int FIRE_CN_MAX = 3;
   private static final int FIRE_INTERVAL = 19;
   private static final int HURT_CN_MAX = 6;
   private static final int INIT_LAUGH_MAX = 60;
   private static final int INIT_STOP_POSX = 648192;
   private static final int MACHINE_FIRE_DOWN = 6;
   private static final int MACHINE_FIRE_UP = 5;
   private static final int MACHINE_HURT = 2;
   private static final int MACHINE_NORMAL_DOWN = 1;
   private static final int MACHINE_NORMAL_UP = 0;
   private static final int MACHINE_TURN_DOWN = 4;
   private static final int MACHINE_TURN_UP = 3;
   private static final int MOVE_LIMIT_LEFT = 640512;
   private static final int MOVE_LIMIT_RIGHT = 655872;
   private static final int MOVE_SPEED = 256;
   private static final int NORMAL_SPEED_ACCELE = 12;
   private static final int OFFSET_Y = 256;
   private static final int RASH_DISTANCE_MAX_1 = 20480;
   private static final int RASH_DISTANCE_MAX_2 = 30720;
   private static final int SHOW_BOSS_END = 2;
   private static final int SHOW_BOSS_ENTER = 0;
   private static final int SHOW_BOSS_GOTO_PRO = 3;
   private static final int SHOW_BOSS_LAUGH = 1;
   private static final int SIDE_DOWN = 1952;
   private static final int SIDE_END = 2490;
   private static final int SIDE_LEFT = 9968;
   private static final int SIDE_RIGHT = 10288;
   private static final int SIDE_UP = 1792;
   private static final int STATE_BROKEN = 3;
   private static final int STATE_ENTER_SHOW = 1;
   private static final int STATE_ESCAPE = 5;
   private static final int STATE_FALL = 4;
   private static final int STATE_INIT = 0;
   private static final int STATE_PRO = 2;
   private static final int TOP_SPEED = 384;
   private static final int TURN_OFFFSET_Y = 512;
   private static Animation boatAni;
   private static Animation escapefaceAni;
   private static Animation faceAni;
   private static Animation machineAni;
   private int WaitCnt;
   private int attack_cn;
   private int attack_cn_max;
   private int attack_step;
   private Boss6BlockArray blockArray;
   private AnimationDrawer boatdrawer;
   private BossBroken bossbroken;
   private boolean direct;
   private int escape_v = 512;
   private AnimationDrawer escapefacedrawer;
   private AnimationDrawer faceDrawer;
   private int face_state;
   private int fire_cn;
   private int fire_time;
   private int fly_end;
   private int fly_top;
   private int fly_top_range = 4096;
   private int holdheadup_cn;
   private int holdheadup_cn_max;
   private int hurt_cn;
   private int initPosY;
   private boolean isRash;
   private int last_attack_step;
   private int laugh_cn;
   private AnimationDrawer machineDrawer;
   private boolean machineUp = false;
   private int machine_state;
   private int rash_distance;
   private int show_step;
   private int speed;
   private int speedCount;
   private boolean startFlag;
   private int state;
   private int wait_cnt;
   private int wait_cnt_max = 10;

   protected Boss6(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.initPosY = this.posY - 2048;
      this.blockArray = new Boss6BlockArray(this.posX, this.posY);
      this.posX += this.iLeft * 8;
      this.machineUp = false;
      this.isRash = false;
      if (machineAni == null) {
         machineAni = new Animation("/animation/boss6_machine");
      }

      this.machineDrawer = machineAni.getDrawer(0, true, 0);
      if (faceAni == null) {
         faceAni = new Animation("/animation/boss4_face");
      }

      this.faceDrawer = faceAni.getDrawer(0, true, 0);
      if (boatAni == null) {
         boatAni = new Animation("/animation/pod_boat");
      }

      this.boatdrawer = boatAni.getDrawer(0, true, 0);
      if (escapefaceAni == null) {
         escapefaceAni = new Animation("/animation/pod_face");
      }

      this.escapefacedrawer = escapefaceAni.getDrawer(2, false, 0);
      this.state = 0;
      this.setBossHP();
   }

   private void attackSet() {
      switch(this.HP) {
      case 1:
         this.attack_cn_max = 120;
         break;
      case 2:
         this.attack_cn_max = 140;
         break;
      case 3:
         this.attack_cn_max = 154;
         break;
      case 4:
         this.attack_cn_max = 173;
         break;
      default:
         this.attack_cn_max = 200;
      }

      if (this.attack_cn < this.attack_cn_max) {
         ++this.attack_cn;
      } else {
         this.attack_cn = 0;
         this.attack_step = 1;
         this.fire_time = 0;
         this.fire_cn = 0;
      }

   }

   private int machineSpeed() {
      if (this.startFlag) {
         if (this.speedCount > 17) {
            this.speed -= 25;
         } else {
            this.speed += 25;
         }

         --this.speedCount;
         if (this.speedCount == 0) {
            this.speed = 0;
            this.startFlag = false;
            this.direct = true;
         }
      } else {
         if (this.speedCount > 35) {
            this.speed -= 12;
         } else {
            this.speed += 12;
         }

         if (this.direct) {
            ++this.speedCount;
            if (this.speedCount == 70) {
               this.speed = 0;
               this.direct = false;
            }
         } else {
            --this.speedCount;
            if (this.speedCount == 0) {
               this.speed = 0;
               this.direct = true;
            }
         }
      }

      return this.speed;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(machineAni);
      Animation.closeAnimation(faceAni);
      Animation.closeAnimation(boatAni);
      Animation.closeAnimation(escapefaceAni);
      machineAni = null;
      faceAni = null;
      boatAni = null;
      escapefaceAni = null;
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

      if (var1 == this.machineDrawer) {
         this.machine_state = var2;
      }

      var1.setActionId(var2);
   }

   private void turnHead() {
      if (this.machine_state != 1 && this.machine_state != 4 && this.machine_state != 6) {
         if (this.machine_state == 3 && this.machineDrawer.checkEnd()) {
            this.setAniState(this.machineDrawer, 0);
            this.machineDrawer.setLoop(true);
         }
      } else if (this.posY - this.initPosY - 2048 >= 512) {
         this.setAniState(this.machineDrawer, 3);
         this.machineDrawer.setLoop(false);
         this.machineUp = true;
         this.holdheadup_cn = 0;
         int var1 = MyRandom.nextInt(0, 100);
         if (var1 < 10) {
            this.holdheadup_cn_max = 24;
         } else if (var1 < 50) {
            this.holdheadup_cn_max = 48;
         } else if (var1 < 80) {
            this.holdheadup_cn_max = 80;
         } else {
            this.holdheadup_cn_max = 128;
         }
      }

   }

   public void close() {
      this.machineDrawer = null;
      this.faceDrawer = null;
      this.blockArray = null;
      this.bossbroken = null;
      this.boatdrawer = null;
      this.escapefacedrawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      switch(var2) {
      case 1:
         if (this.state == 2 && this.attack_step != 2 && this.machineUp) {
            --this.HP;
            player.doBossAttackPose(this, var2);
            if (this.HP > 0) {
               this.last_attack_step = this.attack_step;
               this.attack_step = 2;
               this.setAniState(this.machineDrawer, 2);
               this.machineDrawer.setLoop(true);
               this.setAniState(this.faceDrawer, 2);
               this.hurt_cn = 0;
            }

            if (this.HP <= 2) {
               this.isRash = true;
               this.rash_distance = 0;
            }

            if (this.HP == 0) {
               this.state = 3;
               BossBroken var4 = new BossBroken(27, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
               this.bossbroken = var4;
               MapManager.setCameraLeftLimit(MapManager.getCamera().x);
               MapManager.setCameraRightLimit(MapManager.getCamera().x + MapManager.CAMERA_WIDTH);
               MapManager.setCameraDownLimit(2490);
               addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
            }

            if (this.HP == 0) {
               SoundSystem.getInstance().playSe(35);
            } else {
               SoundSystem.getInstance().playSe(34);
            }
         }
      default:
      }
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && var1 == player) {
         if (player.isAttackingEnemy()) {
            switch(var2) {
            case 1:
               if (this.state == 2 && this.attack_step != 2 && this.machineUp && player.getVelY() >= 0) {
                  --this.HP;
                  player.doBossAttackPose(this, var2);
                  if (this.HP > 0) {
                     this.last_attack_step = this.attack_step;
                     this.attack_step = 2;
                     this.setAniState(this.machineDrawer, 2);
                     this.machineDrawer.setLoop(true);
                     this.setAniState(this.faceDrawer, 2);
                     this.hurt_cn = 0;
                  }

                  if (this.HP <= 2) {
                     this.isRash = true;
                     this.rash_distance = 0;
                  }

                  if (this.HP == 0) {
                     this.state = 3;
                     BossBroken var3 = new BossBroken(27, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
                     this.bossbroken = var3;
                     MapManager.setCameraLeftLimit(MapManager.getCamera().x);
                     MapManager.setCameraRightLimit(MapManager.getCamera().x + MapManager.CAMERA_WIDTH);
                     MapManager.setCameraDownLimit(2490);
                     addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
                  }

                  if (this.HP == 0) {
                     SoundSystem.getInstance().playSe(35);
                  } else {
                     SoundSystem.getInstance().playSe(34);
                  }
               }
            }
         } else if (this.state != 5 && this.state != 3 && this.state != 4 && this.attack_step != 2 && this.state == 2) {
            player.beHurt();
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         if (this.state != 5 && this.state != 4) {
            this.blockArray.draw(var1);
            this.drawInMap(var1, this.machineDrawer, this.posX, this.posY);
            if (this.face_state != 0) {
               if (this.machine_state == 1) {
                  this.faceDrawer.setTrans(1);
                  this.drawInMap(var1, this.faceDrawer, this.posX, this.posY + 1024 + 128);
               } else {
                  if (this.machineUp) {
                     this.faceDrawer.setTrans(0);
                  } else {
                     this.faceDrawer.setTrans(1);
                  }

                  if (this.machine_state != 4 && this.machine_state != 3 && this.machine_state != 1) {
                     this.drawInMap(var1, this.faceDrawer, this.posX, this.posY - 1024);
                  }
               }
            }
         }

         if (this.state == 5 || this.state == 4) {
            this.drawInMap(var1, this.boatdrawer, this.posX, this.posY + 640);
            this.drawInMap(var1, this.escapefacedrawer, this.posX, this.posY - 1024);
         }

         if (this.bossbroken != null && this.state == 3) {
            this.bossbroken.draw(var1);
         }

         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         this.blockArray.logic();
         int var4 = this.posX;
         int var5 = this.posY;
         if (this.state != 5 && this.state != 3 && this.state != 4) {
            this.posY = this.blockArray.getBossY(this.posX);
         }

         if (this.state > 0) {
            isBossEnter = true;
         }

         label181:
         switch(this.state) {
         case 0:
            if (player.getFootPositionX() >= 637952) {
               MapManager.setCameraRightLimit(10288);
            }

            if (player.getFootPositionY() >= this.initPosY && player.getFootPositionX() >= 637952) {
               this.state = 1;
               player.setFootPositionY(this.initPosY);
               player.setVelY(0);
               MapManager.setCameraUpLimit(1792);
               MapManager.setCameraDownLimit(1952);
               MapManager.setCameraLeftLimit(9968);
               MapManager.setCameraRightLimit(10288);
               if (!this.IsPlayBossBattleBGM) {
                  bossFighting = true;
                  bossID = 27;
                  SoundSystem.getInstance().playBgm(25, true);
                  this.IsPlayBossBattleBGM = true;
               }

               this.show_step = 0;
               this.setAniState(this.machineDrawer, 1);
            }
            break;
         case 1:
            switch(this.show_step) {
            case 0:
               if (this.posX > 648192) {
                  this.posX -= 256;
               } else {
                  this.posX = 648192;
                  this.show_step = 1;
                  this.setAniState(this.faceDrawer, 1);
                  this.laugh_cn = 0;
               }
               break label181;
            case 1:
               if (this.laugh_cn < 60) {
                  ++this.laugh_cn;
               } else {
                  this.show_step = 2;
                  this.laugh_cn = 0;
                  this.setAniState(this.faceDrawer, 0);
               }
               break label181;
            case 2:
               this.show_step = 3;
               break label181;
            case 3:
               this.state = 2;
               this.direct = false;
               this.speedCount = 35;
               this.speed = 0;
               this.startFlag = true;
            default:
               break label181;
            }
         case 2:
            this.attackSet();
            int var2;
            int var3;
            int var8;
            switch(this.attack_step) {
            case 0:
               this.turnHead();
               break;
            case 1:
               this.turnHead();
               if (this.machine_state != 3) {
                  byte var1;
                  AnimationDrawer var7;
                  if (this.fire_time < 19) {
                     ++this.fire_time;
                  } else {
                     this.fire_time = 0;
                     var7 = this.machineDrawer;
                     if (this.machineUp) {
                        var1 = 5;
                     } else {
                        var1 = 6;
                     }

                     this.setAniState(var7, var1);
                     this.machineDrawer.setLoop(false);
                     var8 = this.posX;
                     var2 = this.posY;
                     var3 = player.getFootPositionX();
                     int var6 = player.getFootPositionY();
                     BulletObject.addBullet(19, var8, var2, var3, var6);
                     ++this.fire_cn;
                  }

                  if (this.machineDrawer.checkEnd()) {
                     var7 = this.machineDrawer;
                     if (this.machineUp) {
                        var1 = 0;
                     } else {
                        var1 = 1;
                     }

                     this.setAniState(var7, var1);
                     this.machineDrawer.setLoop(true);
                  }

                  if (this.fire_cn >= 3) {
                     this.fire_cn = 0;
                     this.attack_step = 0;
                  }
               }
            }

            if (this.face_state == 2) {
               if (this.hurt_cn < 6) {
                  ++this.hurt_cn;
               } else {
                  this.hurt_cn = 0;
                  this.setAniState(this.machineDrawer, 4);
                  this.machineDrawer.setLoop(false);
                  this.face_state = 0;
                  this.attack_step = this.last_attack_step;
                  this.holdheadup_cn = this.holdheadup_cn_max - 2;
               }
            }

            if (this.machineUp) {
               if (this.holdheadup_cn < this.holdheadup_cn_max) {
                  ++this.holdheadup_cn;
               } else {
                  this.holdheadup_cn = 0;
                  this.setAniState(this.machineDrawer, 4);
                  this.machineDrawer.setLoop(false);
                  this.machineUp = false;
               }
            }

            if (this.machine_state == 4 && this.machineDrawer.checkEnd()) {
               this.setAniState(this.machineDrawer, 1);
               this.machineDrawer.setLoop(true);
            }

            if (this.attack_step == 2) {
               break;
            }

            var8 = 0;
            var3 = 0;

            while(true) {
               byte var9;
               if (!this.isRash) {
                  var9 = 1;
               } else if (this.HP == 2) {
                  var9 = 3;
               } else {
                  var9 = 4;
               }

               if (var3 >= var9) {
                  this.posX += var8;
                  if (this.posX <= 640512) {
                     this.posX = 640512;
                  }

                  if (this.posX >= 655872) {
                     this.posX = 655872;
                  }

                  if (this.isRash) {
                     var2 = this.rash_distance;
                     if (var8 <= 0) {
                        var8 = -var8;
                     }

                     this.rash_distance = var2 + var8;
                     var2 = this.rash_distance;
                     short var10;
                     if (this.HP == 2) {
                        var10 = 20480;
                     } else {
                        var10 = 30720;
                     }

                     if (var2 >= var10) {
                        if (this.HP == 2) {
                           var10 = 20480;
                        } else {
                           var10 = 30720;
                        }

                        this.rash_distance = var10;
                        this.isRash = false;
                     }
                  }
                  break label181;
               }

               var8 += this.machineSpeed();
               ++var3;
            }
         case 3:
            this.bossbroken.logicBoom(this.posX, this.posY);
            if (this.posY + this.velY >= this.getGroundY(this.posX, this.posY) - 1280) {
               this.state = 4;
               this.escapefacedrawer.setActionId(4);
               this.escapefacedrawer.setLoop(true);
               this.boatdrawer.setActionId(1);
               this.boatdrawer.setLoop(true);
               this.posY = this.getGroundY(this.posX, this.posY) - 1280;
               bossFighting = false;
               MapManager.setCameraDownLimit(2372);
               MapManager.calCameraImmidiately();
               this.blockArray.setDisplayState();
            } else {
               this.velY += 10;
               this.posY += this.velY;
            }
            break;
         case 4:
            player.velY = 1280;
            if (player.getFootPositionY() == this.getGroundY(player.footPointX, player.footPointY)) {
               if (player.posX < this.posX) {
                  player.faceDirection = true;
               } else {
                  player.faceDirection = false;
               }

               this.state = 5;
               this.WaitCnt = 0;
               this.wait_cnt = 0;
               this.fly_top = 150400;
               this.fly_end = 656000;
               player.getBossScore();
               SoundSystem.getInstance().playBgm(StageManager.getBgmId(), true);
               player.setAnimationId(0);
               player.setOutOfControl((GameObject)null);
               player.totalVelocity = 0;
            }
            break;
         case 5:
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

            if (this.posX > this.fly_end && this.WaitCnt == 3) {
               player.releaseOutOfControl();
               player.setTerminal(2);
               player.collisionState = 0;
               MapManager.releaseCamera();
               MapManager.setFocusObj((Focusable)null);
               this.WaitCnt = 4;
            }
         }

         this.refreshCollisionRect(this.posX, this.posY);
         this.checkWithPlayer(var4, var5, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1536, var2 - 2048, 3072, 4096);
   }
}
