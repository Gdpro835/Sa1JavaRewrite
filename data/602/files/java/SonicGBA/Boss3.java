package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class Boss3 extends BossObject {
   private static final int BOAT_HURT = 1;
   private static final int BOAT_NORMAL = 0;
   private static final int COLLISION_HEIGHT = 3200;
   private static final int COLLISION_WIDTH = 3200;
   private static final int FACE_HURT = 2;
   private static final int FACE_NORMAL = 0;
   private static final int FACE_SMILE = 1;
   private static final int PLATFORM_ID = 21;
   private static final int PLATFORM_LEFT = 5312;
   private static final int PLATFORM_TOP = -960;
   private static final int PLATFORM_X = 481792;
   private static final int PLATFORM_Y = 138752;
   private static final int PRO_BOSS_MOVING = 1;
   private static final int PRO_INIT = 0;
   private static final int SHOW_BOSS_ENTER = 1;
   private static final int SHOW_BOSS_INTO_PIPE = 3;
   private static final int SHOW_BOSS_LAUGH = 2;
   private static final int SHOW_PIPE_ENTER = 0;
   private static final int SIDE_CHECK_UP = 129024;
   private static final int SIDE_DOWN_MIDDLE = 2232;
   private static final int SIDE_LEFT = 7384;
   private static final int SIDE_RIGHT = 7664;
   private static final int SIDE_UP = 2062;
   private static final int STATE_BROKEN = 3;
   private static final int STATE_ENTER_SHOW = 1;
   private static final int STATE_ESCAPE = 4;
   private static final int STATE_INIT = 0;
   private static final int STATE_PRO = 2;
   private static Animation boatAni;
   private static final int boss_wait_cnt_max = 16;
   private static final int cnt_max = 8;
   private static Animation escapefaceAni;
   private static Animation faceAni;
   private static Animation partAni;
   private static Animation pipeAni;
   private static final int pipe_offset_max = 16;
   private static Animation realAni;
   private static final int release_cnt_max = 25;
   private static Animation shadowAni;
   private static final int show_pipe_cnt_max = 19;
   private int BossVX;
   private int BossVY;
   private int BossV_x;
   private int BossV_y;
   private int FACE_OFFSET_X = 576;
   private int FACE_OFFSET_Y = -768;
   private boolean IsInPipeCollision = false;
   private boolean IsPipeOut = false;
   private boolean IsStartAttack = false;
   private int ShadowPosX;
   private int ShadowPosY;
   private int ShadowVX;
   private int ShadowVY;
   private boolean StartEscape;
   private int StartPosX;
   private int WaitCnt;
   private int boat_cnt;
   private int boat_state;
   private AnimationDrawer boatdrawer;
   private int boss_drip_cnt;
   private int boss_show_top;
   private int boss_v = 320;
   private int boss_wait_cnt;
   private BossBroken bossbroken;
   private int compartPosX;
   private int escape_v = 512;
   private AnimationDrawer escapefacedrawer;
   private int face_cnt;
   private int face_state;
   private AnimationDrawer facedrawer;
   private int fly_end;
   private int fly_top;
   private int fly_top_range = 4096;
   private AnimationDrawer partdrawer;
   private int partvx;
   private int partvy;
   private int partx;
   private int party;
   private Boss3Pipe[] pipe;
   private int pipe_offset;
   private int pipe_vel = 128;
   private int pipe_vel_h = 256;
   private int pipe_vel_v = 512;
   private AnimationDrawer pipedrawer;
   private int[][] pipepos;
   private int[] pipeposend;
   private Platform platform;
   private int pro_step;
   private AnimationDrawer realdrawer;
   private int release_cnt = 0;
   private Boss3Shadow shadow;
   private int shadow_drip_cnt;
   private AnimationDrawer shadowdrawer;
   private int show_pipe_cnt = 0;
   private int show_step;
   private int side_left = 0;
   private int side_right = 0;
   private int state;
   private int wait_cnt;
   private int wait_cnt_max = 10;

   protected Boss3(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.StartPosX = 475136;
      this.compartPosX = 481536;
      this.pipepos = new int[8][2];
      this.pipepos[0][0] = 494592;
      this.pipepos[0][1] = 136384;
      this.pipepos[1][0] = 494592;
      this.pipepos[1][1] = 141632;
      this.pipepos[2][0] = 468480;
      this.pipepos[2][1] = 136384;
      this.pipepos[3][0] = 468480;
      this.pipepos[3][1] = 141632;
      this.pipepos[4][0] = 478016;
      this.pipepos[4][1] = 124544;
      this.pipepos[5][0] = 485056;
      this.pipepos[5][1] = 124544;
      this.pipepos[6][0] = 478016;
      this.pipepos[6][1] = 153600;
      this.pipepos[7][0] = 485056;
      this.pipepos[7][1] = 153600;
      this.boss_show_top = 139264;
      this.pipeposend = new int[4];
      this.pipeposend[0] = 491264;
      this.pipeposend[1] = 471808;
      this.pipeposend[2] = 131200;
      this.pipeposend[3] = 148224;
      this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
      if (realAni == null) {
         realAni = new Animation("/animation/boss3_real");
      }

      this.realdrawer = realAni.getDrawer(0, true, 0);
      if (shadowAni == null) {
         shadowAni = new Animation("/animation/boss3_shadow");
      }

      this.shadowdrawer = shadowAni.getDrawer(0, true, 0);
      if (pipeAni == null) {
         pipeAni = new Animation("/animation/boss3_pipe");
      }

      this.pipedrawer = pipeAni.getDrawer();
      this.pipedrawer.setPause(true);
      if (faceAni == null) {
         faceAni = new Animation("/animation/boss3_face");
      }

      this.facedrawer = faceAni.getDrawer(0, true, 0);
      this.state = 0;
      this.pipe = new Boss3Pipe[8];
      this.pipe[0] = new Boss3Pipe(24, this.pipepos[0][0] >> 6, this.pipepos[0][1] >> 6, false, 3, 0);
      this.pipe[1] = new Boss3Pipe(24, this.pipepos[1][0] >> 6, this.pipepos[1][1] >> 6, false, 3, 0);
      this.pipe[2] = new Boss3Pipe(24, this.pipepos[2][0] >> 6, this.pipepos[2][1] >> 6, false, 2, 0);
      this.pipe[3] = new Boss3Pipe(24, this.pipepos[3][0] >> 6, this.pipepos[3][1] >> 6, false, 2, 0);
      this.pipe[4] = new Boss3Pipe(24, this.pipepos[4][0] >> 6, this.pipepos[4][1] >> 6, false, 0, 1);
      this.pipe[5] = new Boss3Pipe(24, this.pipepos[5][0] >> 6, this.pipepos[5][1] >> 6, false, 0, 1);
      this.pipe[6] = new Boss3Pipe(24, this.pipepos[6][0] >> 6, this.pipepos[6][1] >> 6, true, 1, 0);
      this.pipe[7] = new Boss3Pipe(24, this.pipepos[7][0] >> 6, this.pipepos[7][1] >> 6, true, 1, 0);

      for(var1 = 0; var1 < this.pipe.length; ++var1) {
         addGameObject(this.pipe[var1]);
         this.pipe[var1].setisDraw(false);
      }

      Platform var8 = new Platform(21, 481792, 138752, 5312, -960, 0, 0);
      this.platform = var8;
      addGameObject(this.platform);
      this.IsStartAttack = false;
      this.IsPipeOut = false;
      this.IsInPipeCollision = false;
      this.setBossHP();
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(realAni);
      Animation.closeAnimation(shadowAni);
      Animation.closeAnimation(faceAni);
      Animation.closeAnimation(pipeAni);
      Animation.closeAnimation(partAni);
      Animation.closeAnimation(boatAni);
      Animation.closeAnimation(escapefaceAni);
      realAni = null;
      shadowAni = null;
      faceAni = null;
      pipeAni = null;
      partAni = null;
      boatAni = null;
      escapefaceAni = null;
   }

   public void close() {
      this.realdrawer = null;
      this.shadowdrawer = null;
      this.facedrawer = null;
      this.pipedrawer = null;
      this.partdrawer = null;
      this.boatdrawer = null;
      this.escapefacedrawer = null;
      this.shadow = null;
      this.bossbroken = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (this.state > 1 && !this.IsInPipeCollision && this.IsStartAttack && this.HP > 0 && this.state == 2 && this.pro_step == 1 && this.face_state != 2) {
         --this.HP;
         player.doBossAttackPose(this, var2);
         this.face_state = 2;
         this.boat_state = 1;
         if (this.HP == 0) {
            this.state = 3;
            this.side_left = MapManager.getCamera().x;
            this.side_right = this.side_left + MapManager.CAMERA_WIDTH;
            MapManager.setCameraLeftLimit(this.side_left);
            MapManager.setCameraRightLimit(this.side_right);
            BossBroken var4 = new BossBroken(24, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
            this.bossbroken = var4;
            addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
            this.pipe_offset = 0;
            if (this.compartPosX < this.posX) {
               this.BossVX = -256;
            } else {
               this.BossVX = 256;
            }

            this.BossVY = 0;
            if (this.compartPosX < this.ShadowPosX) {
               this.ShadowVX = -1024;
            } else {
               this.ShadowVX = 1024;
            }

            this.ShadowVY = -512;
            this.boss_drip_cnt = 0;
            this.shadow_drip_cnt = 0;
            this.facedrawer.setActionId(0);
            SoundSystem.getInstance().playSe(35, false);
         } else {
            SoundSystem.getInstance().playSe(34, false);
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && this.state > 1 && !this.IsInPipeCollision && var1 == player) {
         if (player.isAttackingEnemy()) {
            if (this.IsStartAttack && this.HP > 0 && this.state == 2 && this.pro_step == 1 && this.face_state != 2) {
               --this.HP;
               player.doBossAttackPose(this, var2);
               this.face_state = 2;
               this.boat_state = 1;
               if (this.HP == 0) {
                  this.state = 3;
                  this.side_left = MapManager.getCamera().x;
                  this.side_right = this.side_left + MapManager.CAMERA_WIDTH;
                  MapManager.setCameraLeftLimit(this.side_left);
                  MapManager.setCameraRightLimit(this.side_right);
                  BossBroken var3 = new BossBroken(24, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
                  this.bossbroken = var3;
                  addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
                  this.pipe_offset = 0;
                  if (this.compartPosX < this.posX) {
                     this.BossVX = -256;
                  } else {
                     this.BossVX = 256;
                  }

                  this.BossVY = 0;
                  if (this.compartPosX < this.ShadowPosX) {
                     this.ShadowVX = -1024;
                  } else {
                     this.ShadowVX = 1024;
                  }

                  this.ShadowVY = -512;
                  this.boss_drip_cnt = 0;
                  this.shadow_drip_cnt = 0;
                  this.facedrawer.setActionId(0);
                  SoundSystem.getInstance().playSe(35, false);
               } else {
                  SoundSystem.getInstance().playSe(34, false);
               }
            }
         } else if (this.state == 2 && this.pro_step == 1 && this.boat_state != 1 && this.IsStartAttack && player.canBeHurt()) {
            player.beHurt();
            this.face_state = 1;
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (this.state >= 2) {
         this.drawInMap(var1, this.shadowdrawer, this.ShadowPosX, this.ShadowPosY);
      }

      if (this.state >= 1 && this.show_step > 0 && !this.StartEscape) {
         this.drawInMap(var1, this.realdrawer);
         this.drawInMap(var1, this.facedrawer, this.posX + this.FACE_OFFSET_X, this.posY + this.FACE_OFFSET_Y);
      }

      if (this.StartEscape) {
         this.drawInMap(var1, this.boatdrawer, this.posX, this.posY + 640);
         this.drawInMap(var1, this.escapefacedrawer, this.posX, this.posY - 1024);
      }

      if (this.bossbroken != null) {
         this.bossbroken.draw(var1);
      }

      if (this.partdrawer != null && this.state == 4 && !this.StartEscape) {
         this.drawInMap(var1, this.partdrawer, this.partx, this.party);
      }

      this.drawCollisionRect(var1);
      int var2;
      if (this.state != 4 && this.show_pipe_cnt == 19 && !this.IsPipeOut) {
         for(var2 = 0; var2 < this.pipe.length; ++var2) {
            this.pipe[var2].setisDraw(true);
         }
      } else {
         for(var2 = 0; var2 < this.pipe.length; ++var2) {
            this.pipe[var2].setisDraw(false);
         }
      }

   }

   public void drawpipes(MFGraphics var1) {
      for(int var2 = 0; var2 < this.pipe.length; ++var2) {
         this.pipe[var2].draw(var1);
      }

   }

   public int getPaintLayer() {
      return 3;
   }

   public void logic() {
      if (!this.dead) {
         int var3 = this.posX;
         int var4 = this.posY;
         if (this.state > 0) {
            isBossEnter = true;
         }

         int var1;
         int var2;
         int var5;
         int[] var7;
         label338:
         switch(this.state) {
         case 0:
            if (player.getFootPositionX() >= this.StartPosX) {
               MapManager.setCameraRightLimit(7664);
            }

            if (player.getFootPositionX() >= this.StartPosX && player.getFootPositionY() >= 129024) {
               this.state = 1;
               this.show_step = 0;
               this.pipe_offset = 0;
               MapManager.setCameraLeftLimit(7384);
               MapManager.setCameraRightLimit(7664);
               MapManager.setCameraUpLimit(2062);
               var1 = MapManager.CAMERA_HEIGHT * 1 / 4;
               MapManager.setCameraDownLimit(var1 + 2232);
               if (!this.IsPlayBossBattleBGM) {
                  bossFighting = true;
                  bossID = 24;
                  SoundSystem.getInstance().playBgm(22, true);
                  this.IsPlayBossBattleBGM = true;
               }
            }
            break;
         case 1:
            switch(this.show_step) {
            case 0:
               if (this.show_pipe_cnt < 19) {
                  ++this.show_pipe_cnt;
               } else if (this.pipe_offset < 16) {
                  ++this.pipe_offset;
                  var7 = this.pipepos[0];
                  var7[0] -= this.pipe_vel_h;
                  var7 = this.pipepos[1];
                  var7[0] -= this.pipe_vel_h;
                  var7 = this.pipepos[2];
                  var7[0] += this.pipe_vel_h;
                  var7 = this.pipepos[3];
                  var7[0] += this.pipe_vel_h;
                  var7 = this.pipepos[4];
                  var7[1] += this.pipe_vel_v;
                  var7 = this.pipepos[5];
                  var7[1] += this.pipe_vel_v;
                  var7 = this.pipepos[6];
                  var7[1] -= this.pipe_vel_v;
                  var7 = this.pipepos[7];
                  var7[1] -= this.pipe_vel_v;
               } else {
                  this.show_step = 1;
                  this.pipepos[0][0] = 490496;
                  this.pipepos[0][1] = 136384;
                  this.pipepos[1][0] = 490496;
                  this.pipepos[1][1] = 141632;
                  this.pipepos[2][0] = 472576;
                  this.pipepos[2][1] = 136384;
                  this.pipepos[3][0] = 472576;
                  this.pipepos[3][1] = 141632;
                  this.pipepos[4][0] = 478016;
                  this.pipepos[4][1] = 132736;
                  this.pipepos[5][0] = 485056;
                  this.pipepos[5][1] = 132736;
                  this.pipepos[6][0] = 478016;
                  this.pipepos[6][1] = 145408;
                  this.pipepos[7][0] = 485056;
                  this.pipepos[7][1] = 145408;
                  this.posX = this.pipepos[7][0];
                  this.posY = this.pipepos[7][1];
               }
               break label338;
            case 1:
               if (this.posY > this.boss_show_top) {
                  this.posY -= this.boss_v;
               } else {
                  this.posY = this.boss_show_top;
                  this.show_step = 2;
               }
               break label338;
            case 2:
               if (this.boss_wait_cnt < 16) {
                  ++this.boss_wait_cnt;
               } else if (this.boss_wait_cnt == 16) {
                  ++this.boss_wait_cnt;
                  this.facedrawer.setActionId(1);
                  this.facedrawer.setLoop(true);
               } else if (this.boss_wait_cnt > 16 && this.boss_wait_cnt < 26) {
                  ++this.boss_wait_cnt;
               } else if (this.boss_wait_cnt == 26) {
                  ++this.boss_wait_cnt;
                  this.facedrawer.setActionId(0);
                  this.facedrawer.setLoop(true);
               } else if (this.boss_wait_cnt > 26 && this.boss_wait_cnt < 42) {
                  ++this.boss_wait_cnt;
               } else if (this.boss_wait_cnt == 42) {
                  this.show_step = 3;
               }
               break label338;
            case 3:
               if (this.posY > this.pipepos[5][1]) {
                  this.posY -= this.boss_v;
               } else {
                  this.posY = this.pipepos[5][1];
                  this.state = 2;
                  this.pro_step = 0;
                  this.shadow = new Boss3Shadow(34, this.posX, this.posY, 0, 0, 0, 0);
                  this.boss_drip_cnt = 0;
               }
            default:
               break label338;
            }
         case 2:
            if (this.face_state == 0) {
               this.face_state = this.shadow.getShadowHurt();
            }

            if (this.face_state != 0) {
               if (this.face_cnt < 8) {
                  ++this.face_cnt;
               } else {
                  this.face_state = 0;
                  this.shadow.setShadowHurt(0);
                  this.face_cnt = 0;
               }
            }

            if (this.boat_state == 1) {
               if (this.boat_cnt < 8) {
                  ++this.boat_cnt;
               } else {
                  this.boat_state = 0;
                  this.boat_cnt = 0;
               }
            }

            switch(this.pro_step) {
            case 0:
               this.IsStartAttack = false;
               if (this.release_cnt < 25) {
                  ++this.release_cnt;
               } else {
                  if (this.HP >= 6) {
                     this.BossV_x = 458;
                     this.BossV_y = 330;
                  } else if (this.HP == 5) {
                     this.BossV_x = 549;
                     this.BossV_y = 396;
                  } else if (this.HP == 4) {
                     this.BossV_x = 549;
                     this.BossV_y = 400;
                  } else if (this.HP != 3 && this.HP != 2) {
                     if (this.HP == 1) {
                        this.BossV_x = 733;
                        this.BossV_y = 528;
                     }
                  } else {
                     this.BossV_x = 640;
                     this.BossV_y = 462;
                  }

                  var5 = MyRandom.nextInt(0, 7);
                  var2 = MyRandom.nextInt(0, 7);
                  switch(var5) {
                  case 0:
                  case 2:
                     if (var2 != 0) {
                        var1 = var2;
                        if (var2 != 2) {
                           break;
                        }
                     }

                     var1 = MyRandom.nextInt(3, 7);
                     break;
                  case 1:
                  case 3:
                     if (var2 != 1) {
                        var1 = var2;
                        if (var2 != 3) {
                           break;
                        }
                     }

                     var1 = MyRandom.nextInt(4, 7);
                     break;
                  case 4:
                  case 6:
                     if (var2 != 4) {
                        var1 = var2;
                        if (var2 != 6) {
                           break;
                        }
                     }

                     var1 = MyRandom.nextInt(0, 3);
                     break;
                  case 5:
                  case 7:
                     if (var2 != 5) {
                        var1 = var2;
                        if (var2 != 7) {
                           break;
                        }
                     }

                     var1 = MyRandom.nextInt(0, 4);
                     break;
                  default:
                     var1 = var2;
                  }

                  this.posX = this.pipepos[var5][0];
                  this.posY = this.pipepos[var5][1];
                  this.ShadowPosX = this.pipepos[var1][0];
                  this.ShadowPosY = this.pipepos[var1][1];
                  switch(var5) {
                  case 0:
                  case 1:
                     this.BossVX = -Math.abs(this.BossV_x);
                     this.BossVY = 0;
                     this.realdrawer.setTrans(0);
                     this.facedrawer.setTrans(0);
                     this.FACE_OFFSET_X = 576;
                     break;
                  case 2:
                  case 3:
                     this.BossVX = Math.abs(this.BossV_x);
                     this.BossVY = 0;
                     this.realdrawer.setTrans(2);
                     this.facedrawer.setTrans(2);
                     this.FACE_OFFSET_X = -448;
                     break;
                  case 4:
                  case 5:
                     this.BossVX = 0;
                     this.BossVY = Math.abs(this.BossV_y);
                     if (var5 == 4) {
                        this.realdrawer.setTrans(2);
                        this.facedrawer.setTrans(2);
                        this.FACE_OFFSET_X = -448;
                     } else {
                        this.realdrawer.setTrans(0);
                        this.facedrawer.setTrans(0);
                        this.FACE_OFFSET_X = 576;
                     }
                     break;
                  case 6:
                  case 7:
                     this.BossVX = 0;
                     this.BossVY = -Math.abs(this.BossV_y);
                     if (var5 == 6) {
                        this.realdrawer.setTrans(2);
                        this.facedrawer.setTrans(2);
                        this.FACE_OFFSET_X = -448;
                     } else {
                        this.realdrawer.setTrans(0);
                        this.facedrawer.setTrans(0);
                        this.FACE_OFFSET_X = 576;
                     }
                  }

                  switch(var1) {
                  case 0:
                  case 1:
                     this.ShadowVX = -Math.abs(this.BossV_x);
                     this.ShadowVY = 0;
                     this.shadowdrawer.setTrans(0);
                     break;
                  case 2:
                  case 3:
                     this.ShadowVX = Math.abs(this.BossV_x);
                     this.ShadowVY = 0;
                     this.shadowdrawer.setTrans(2);
                     break;
                  case 4:
                  case 5:
                     this.ShadowVX = 0;
                     this.ShadowVY = Math.abs(this.BossV_y);
                     if (var1 == 4) {
                        this.shadowdrawer.setTrans(2);
                     } else {
                        this.shadowdrawer.setTrans(0);
                     }
                     break;
                  case 6:
                  case 7:
                     this.ShadowVX = 0;
                     this.ShadowVY = -Math.abs(this.BossV_y);
                     if (var1 == 6) {
                        this.shadowdrawer.setTrans(2);
                     } else {
                        this.shadowdrawer.setTrans(0);
                     }
                  }

                  this.pro_step = 1;
                  this.release_cnt = 0;
               }
               break;
            case 1:
               this.IsStartAttack = true;
               if (this.posX + this.BossVX >= this.pipeposend[1] + 1280 && this.posX + this.BossVX <= this.pipeposend[0] - 1280) {
                  this.IsInPipeCollision = false;
               } else {
                  this.IsInPipeCollision = true;
               }

               if (this.ShadowPosX + this.ShadowVX >= this.pipeposend[1] + 1280 && this.ShadowPosX + this.ShadowVX <= this.pipeposend[0] - 1280) {
                  this.shadow.IsInPipeCollision = false;
               } else {
                  this.shadow.IsInPipeCollision = true;
               }

               if (this.posX + this.BossVX >= this.pipeposend[1] && this.posX + this.BossVX <= this.pipeposend[0] && this.ShadowPosX + this.ShadowVX >= this.pipeposend[1] && this.ShadowPosX + this.ShadowVX <= this.pipeposend[0] && this.posY + this.BossVY >= this.pipeposend[2] && this.posY + this.BossVY <= this.pipeposend[3] && this.ShadowPosY + this.ShadowVY >= this.pipeposend[2] && this.ShadowPosY + this.ShadowVY <= this.pipeposend[3]) {
                  this.posX += this.BossVX;
                  this.posY += this.BossVY;
                  this.ShadowPosX += this.ShadowVX;
                  this.ShadowPosY += this.ShadowVY;
               } else {
                  this.pro_step = 0;
               }
            }

            this.shadow.logic(this.ShadowPosX, this.ShadowPosY);
            this.facedrawer.setActionId(this.face_state);
            this.realdrawer.setActionId(this.boat_state);
            break;
         case 3:
            this.platform.setDisplay(false);
            this.shadow.IsOver = true;
            this.bossbroken.logicBoom(this.posX, this.posY);
            if (this.pipe_offset < 16) {
               ++this.pipe_offset;
               var7 = this.pipepos[0];
               var7[0] += this.pipe_vel_h;
               var7 = this.pipepos[1];
               var7[0] += this.pipe_vel_h;
               var7 = this.pipepos[2];
               var7[0] -= this.pipe_vel_h;
               var7 = this.pipepos[3];
               var7[0] -= this.pipe_vel_h;
               var7 = this.pipepos[4];
               var7[1] -= this.pipe_vel_v;
               var7 = this.pipepos[5];
               var7[1] -= this.pipe_vel_v;
               var7 = this.pipepos[6];
               var7[1] += this.pipe_vel_v;
               var7 = this.pipepos[7];
               var7[1] += this.pipe_vel_v;
            } else {
               this.IsPipeOut = true;
            }

            if (this.posY + this.BossVY + 1600 > this.getGroundY(this.posX, this.posY) && this.boss_drip_cnt == 0) {
               this.posY = this.getGroundY(this.posX, this.posY) - 1600;
               this.BossVY = -1280;
               this.boss_drip_cnt = 1;
            } else if (this.posY + this.BossVY + 1600 > this.getGroundY(this.posX, this.posY) && this.boss_drip_cnt == 1) {
               this.posY = this.getGroundY(this.posX, this.posY) - 1600;
               this.BossVY = -640;
               this.boss_drip_cnt = 2;
            } else if (this.posY + this.BossVY + 1600 > this.getGroundY(this.posX, this.posY) && this.boss_drip_cnt == 2) {
               this.posY = this.getGroundY(this.posX, this.posY) - 1600;
               if (partAni == null) {
                  partAni = new Animation("/animation/boss3_part");
               }

               this.partdrawer = partAni.getDrawer(0, true, 0);
               if (boatAni == null) {
                  boatAni = new Animation("/animation/pod_boat");
               }

               this.boatdrawer = boatAni.getDrawer(0, true, 0);
               if (escapefaceAni == null) {
                  escapefaceAni = new Animation("/animation/pod_face");
               }

               this.escapefacedrawer = escapefaceAni.getDrawer(4, true, 0);
               this.partx = this.posX;
               this.party = this.posY;
               this.partvx = -1600;
               this.partvy = -320;
            } else {
               this.posX += this.BossVX;
               this.BossVY += GRAVITY;
               this.posY += this.BossVY;
            }

            var1 = this.ShadowPosY;
            var2 = this.ShadowVY;
            if (var1 + var2 + 1600 > this.getGroundY(this.ShadowPosX, this.ShadowPosY) && this.shadow_drip_cnt == 0) {
               this.ShadowPosY = this.getGroundY(this.ShadowPosX, this.ShadowPosY) - 1600;
               this.ShadowVY = -1280;
               this.shadow_drip_cnt = 1;
            } else {
               var2 = this.ShadowPosY;
               var1 = this.ShadowVY;
               if (var2 + var1 + 1600 > this.getGroundY(this.ShadowPosX, this.ShadowPosY) && this.shadow_drip_cnt == 1) {
                  this.ShadowPosY = this.getGroundY(this.ShadowPosX, this.ShadowPosY) - 1600;
                  this.ShadowVY = -640;
                  this.shadow_drip_cnt = 2;
               } else {
                  var1 = this.ShadowPosY;
                  var2 = this.ShadowVY;
                  if (var1 + var2 + 1600 > this.getGroundY(this.ShadowPosX, this.ShadowPosY) && this.shadow_drip_cnt == 2) {
                     this.ShadowPosY = this.getGroundY(this.ShadowPosX, this.ShadowPosY) - 1600;
                  } else {
                     this.ShadowPosX += this.BossVX * 3;
                     this.ShadowVY += GRAVITY;
                     this.ShadowPosY += this.ShadowVY;
                  }
               }
            }

            if (this.bossbroken.getEndState()) {
               this.state = 4;
               this.StartEscape = false;
               this.WaitCnt = 0;
               this.wait_cnt = 0;
               this.fly_top = this.posY - 3072;
               this.fly_end = this.side_right;
               bossFighting = false;
               player.getBossScore();
               SoundSystem.getInstance().playBgm(StageManager.getBgmId(), true);
            }
            break;
         case 4:
            if (this.party + this.partvy > this.getGroundY(this.partx, this.party)) {
               this.party = this.getGroundY(this.partx, this.party);
               this.StartEscape = true;
            } else {
               this.partx += this.partvx;
               this.partvy += GRAVITY;
               this.party += this.partvy;
            }

            if (this.StartEscape) {
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
                  var2 = MapManager.getCamera().x;
                  var1 = MapManager.CAMERA_WIDTH;
                  var5 = MapManager.getCamera().y;
                  Cage var6 = new Cage(var2 + (var1 >> 1) << 6, var5 << 6);
                  addGameObject(var6);
                  var1 = MapManager.CAMERA_HEIGHT * 3 / 4;
                  MapManager.setCameraUpLimit(2232 - var1);
                  var1 = MapManager.CAMERA_HEIGHT * 1 / 4;
                  MapManager.setCameraDownLimit(var1 + 2232);
                  this.WaitCnt = 4;
               }
            }
         }

         for(var1 = 0; var1 < this.pipe.length; ++var1) {
            this.pipe[var1].logic(this.pipepos[var1][0], this.pipepos[var1][1]);
         }

         this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
         this.checkWithPlayer(var3, var4, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1600, var2 - 1600, 3200, 3200);
   }
}
