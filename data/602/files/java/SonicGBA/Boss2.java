package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class Boss2 extends BossObject {
   private static final int BOAT_HURT = 1;
   private static final int BOAT_NORMAL = 0;
   private static int BOSS_DRIP_X;
   private static final int BOSS_DRIP_X_ST2 = 572672;
   private static final int BOSS_DRIP_X_ST5 = 603136;
   private static final int COLLISION_HEIGHT = 1280;
   private static final int COLLISION_WIDTH = 3072;
   private static final int FACE_HURT = 2;
   private static final int FACE_NORMAL = 0;
   private static final int FACE_SMILE = 1;
   private static int SIDE;
   private static int SIDE_DOWN_MIDDLE;
   private static final int SIDE_DOWN_MIDDLE_ST2 = 1416;
   private static final int SIDE_DOWN_MIDDLE_ST5 = 1136;
   private static int SIDE_LEFT;
   private static final int SIDE_LEFT_ST2 = 8708;
   private static final int SIDE_LEFT_ST5 = 9184;
   private static int SIDE_RIGHT;
   private static final int SIDE_RIGHT_ST2 = 9008;
   private static final int SIDE_RIGHT_ST5 = 9484;
   private static final int SIDE_ST2 = 561152;
   private static final int SIDE_ST5 = 591616;
   private static int SIDE_UP = 78848;
   private static final int SPRING_DAMPING = 2;
   private static final int SPRING_FLYING = 0;
   private static final int SPRING_WAITING = 1;
   private static final int STATE_ATTACK_JUMPING = 7;
   private static final int STATE_ATTACK_SPRING_DAMPING = 6;
   private static final int STATE_ATTACK_WAITING = 5;
   private static final int STATE_BROKEN = 9;
   private static final int STATE_ESCAPE = 10;
   private static final int STATE_RELASE_SPRING_DAMPING = 8;
   private static final int STATE_SHOW_DROP = 0;
   private static final int STATE_SHOW_LAUGH = 3;
   private static final int STATE_SHOW_SPRING_DAMPING = 1;
   private static final int STATE_SHOW_WAITING = 2;
   private static final int STATE_SHOW_WAITING_2 = 4;
   private static final int STATE_WAIT = -1;
   private static Animation boatAni;
   private static final int cnt_max = 8;
   private static final int display_cnt_max = 35;
   private static final int display_wait_cnt_max = 16;
   private static Animation escapeboatAni;
   private static Animation escapefaceAni;
   private static Animation faceAni;
   private static final int high_jump_wait_cnt_max = 34;
   private static final int show_laugh_cnt_max = 10;
   private boolean IsBroken;
   private boolean IsinHighJump;
   private int WaitCnt;
   private int boat_cnt;
   private int boat_state;
   private AnimationDrawer boatdrawer;
   private int boom_offset;
   private BossBroken bossbroken;
   private int display_cnt;
   private int display_wait_cnt;
   private int drop_cnt;
   private int escape_v;
   private AnimationDrawer escapeboatdrawer;
   private AnimationDrawer escapefacedrawer;
   private int face_cnt;
   private int face_state;
   private AnimationDrawer facedrawer;
   private int fly_end;
   private int fly_top;
   private int fly_top_range;
   private int high_jump_cnt;
   private int high_jump_cnt_max;
   private int high_jump_wait_cnt;
   private int limitLeftX;
   private int limitRightX;
   private int lowerHP_jump_startVerlY;
   private int min_range_x;
   private int normal_jump_startVerlY;
   private int offset_y = 1088;
   private int range = 28800;
   private int show_laugh_cnt;
   private int side_left;
   private int side_right;
   private Boss2Spring spring;
   private int springH;
   private int springHmax;
   private int spring_state;
   private boolean start_cnt;
   private int start_pos = 16640;
   private int state;
   private int v_x_frame = 19;
   private int velX = 0;
   private int velY = 0;
   private int velocity = -384;
   private int wait_cnt;
   private int wait_cnt_max;

   protected Boss2(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.normal_jump_startVerlY = -GRAVITY / 2 * 19;
      this.lowerHP_jump_startVerlY = -2600;
      this.wait_cnt_max = 5;
      this.min_range_x = 0;
      this.boom_offset = 1088;
      this.escape_v = 512;
      this.fly_top_range = 4096;
      this.side_left = 0;
      this.side_right = 0;
      this.start_cnt = false;
      this.display_cnt = 0;
      this.show_laugh_cnt = 0;
      this.high_jump_wait_cnt = 0;
      this.IsinHighJump = false;
      this.high_jump_cnt = -1;
      this.high_jump_cnt_max = 3;
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      if (StageManager.getCurrentZoneId() == 5) {
         SIDE = 591616;
         SIDE_LEFT = 9184;
         SIDE_RIGHT = 9484;
         SIDE_DOWN_MIDDLE = 1136;
         BOSS_DRIP_X = 603136;
      } else {
         SIDE = 561152;
         SIDE_LEFT = 8708;
         SIDE_RIGHT = 9008;
         SIDE_DOWN_MIDDLE = 1416;
         BOSS_DRIP_X = 572672;
      }

      this.limitRightX = SIDE_RIGHT << 6;
      this.limitLeftX = SIDE_LEFT << 6;
      this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
      if (boatAni == null) {
         boatAni = new Animation("/animation/boss2_boat");
      }

      this.boatdrawer = boatAni.getDrawer(0, true, 0);
      if (faceAni == null) {
         faceAni = new Animation("/animation/boss2_face");
      }

      this.facedrawer = faceAni.getDrawer(0, true, 0);
      if (escapeboatAni == null) {
         escapeboatAni = new Animation("/animation/pod_boat");
      }

      this.escapeboatdrawer = escapeboatAni.getDrawer(0, true, 0);
      if (escapefaceAni == null) {
         escapefaceAni = new Animation("/animation/pod_face");
      }

      this.escapefacedrawer = escapefaceAni.getDrawer(4, true, 0);
      this.posY -= this.start_pos;
      this.spring = new Boss2Spring(32, var2, var3, var4, var5, var6, var7);
      addGameObject(this.spring, var2, var3);
      this.IsBroken = false;
      this.state = -1;
      this.high_jump_cnt = -1;
      this.setBossHP();
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(boatAni);
      Animation.closeAnimation(faceAni);
      Animation.closeAnimation(escapeboatAni);
      Animation.closeAnimation(escapefaceAni);
      boatAni = null;
      faceAni = null;
      escapeboatAni = null;
      escapefaceAni = null;
   }

   public boolean HighJump() {
      boolean var1;
      if (this.HP < 5 && this.high_jump_cnt == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean IsJumpOutScreen() {
      boolean var1;
      if (this.HighJump() && this.posY >> 6 < MapManager.getCamera().y) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void changeAniState(AnimationDrawer var1, int var2, boolean var3) {
      if (player.getCheckPositionX() > this.posX) {
         var1.setActionId(var2);
         var1.setTrans(2);
         var1.setLoop(var3);
      } else {
         var1.setActionId(var2);
         var1.setTrans(0);
         var1.setLoop(var3);
      }

   }

   public void changeAniStateNoTran(AnimationDrawer var1, int var2, boolean var3) {
      var1.setActionId(var2);
      var1.setLoop(var3);
   }

   public void close() {
      this.boatdrawer = null;
      this.facedrawer = null;
      this.escapeboatdrawer = null;
      this.escapefacedrawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (this.state > 4 && this.HP > 0 && this.face_state != 2) {
         --this.HP;
         if (this.HP == 4) {
            this.high_jump_cnt = 0;
         }

         player.doBossAttackPose(this, var2);
         this.face_state = 2;
         this.boat_state = 1;
         if (this.HP == 0) {
            SoundSystem.getInstance().playSe(35);
         } else {
            SoundSystem.getInstance().playSe(34);
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && var1 == player && this.state > 4) {
         if (player.isAttackingEnemy()) {
            if (this.HP > 0 && this.face_state != 2) {
               --this.HP;
               if (this.HP == 4) {
                  this.high_jump_cnt = 0;
               }

               player.doBossAttackPose(this, var2);
               this.face_state = 2;
               this.boat_state = 1;
               if (this.HP == 0) {
                  SoundSystem.getInstance().playSe(35);
               } else {
                  SoundSystem.getInstance().playSe(34);
               }
            }
         } else if (this.state != 9 && this.state != 10 && this.boat_state != 1 && player.canBeHurt()) {
            player.beHurt();
            this.face_state = 1;
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         if (this.state == 0) {
            if (this.posY > SIDE_DOWN_MIDDLE - MapManager.CAMERA_HEIGHT << 6) {
               this.drawInMap(var1, this.boatdrawer, this.posX, this.posY - this.springH);
               this.drawInMap(var1, this.facedrawer, this.posX, this.posY - 1664 - this.springH);
            }
         } else if (this.state != 10 && this.state != -1) {
            this.drawInMap(var1, this.boatdrawer, this.posX, this.posY - this.springH);
            this.drawInMap(var1, this.facedrawer, this.posX, this.posY - 1664 - this.springH);
         } else if (this.state != -1) {
            this.drawInMap(var1, this.escapeboatdrawer, this.posX, this.posY);
            this.drawInMap(var1, this.escapefacedrawer, this.posX, this.posY - 1664);
         }

         this.drawCollisionRect(var1);
         if (this.state != 9 && this.state != 10 && this.state != -1) {
            this.spring.draw(var1);
         }

         if (this.bossbroken != null) {
            this.bossbroken.draw(var1);
         }
      }

   }

   public void logic() {
      if (!this.dead) {
         this.springH = this.spring.getSpringHeight();
         int var2 = this.posX;
         int var1 = this.posY;
         int var3 = this.posX;
         var3 = this.posY;
         if (this.HP == 0 && !this.IsBroken) {
            this.state = 9;
            this.spring.setBossBrokenState(true);
            this.posY = this.getGroundY(this.posX, this.posY) - this.springHmax;
            this.bossbroken = new BossBroken(23, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
            addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
            this.IsBroken = true;
            this.velY = 0;
            this.side_left = MapManager.getCamera().x;
            this.side_right = this.side_left + MapManager.CAMERA_WIDTH;
            MapManager.setCameraLeftLimit(this.side_left);
            MapManager.setCameraRightLimit(this.side_right);
         }

         if (this.state > 4) {
            this.spring.setAttackable(true);
         }

         if (this.HP >= 5) {
            this.wait_cnt_max = 8;
            this.v_x_frame = 19;
         } else if (this.HP != 4 && this.HP != 3) {
            if (this.HP == 2) {
               this.wait_cnt_max = 6;
               this.v_x_frame = 14;
            } else if (this.HP == 1) {
               this.wait_cnt_max = 5;
               this.v_x_frame = 12;
            }
         } else {
            this.wait_cnt_max = 7;
            this.v_x_frame = 16;
         }

         this.normal_jump_startVerlY = -GRAVITY / 2 * this.v_x_frame;
         if (this.state > -1) {
            isBossEnter = true;
         }

         int var4;
         switch(this.state) {
         case -1:
            if (player.getFootPositionX() >= SIDE) {
               MapManager.setCameraRightLimit(SIDE_RIGHT);
            }

            if (player.getFootPositionX() >= SIDE && !this.start_cnt && player.getFootPositionY() >= SIDE_UP) {
               if (!this.IsPlayBossBattleBGM) {
                  bossFighting = true;
                  bossID = 23;
                  SoundSystem.getInstance().playBgm(22, true);
                  MapManager.setCameraLeftLimit(SIDE_LEFT);
                  MapManager.setCameraRightLimit(SIDE_RIGHT);
                  var3 = SIDE_DOWN_MIDDLE;
                  var4 = MapManager.CAMERA_HEIGHT * 1 / 4;
                  MapManager.setCameraDownLimit(var3 + var4);
                  var4 = SIDE_DOWN_MIDDLE;
                  var3 = MapManager.CAMERA_HEIGHT * 3 / 4;
                  MapManager.setCameraUpLimit(var4 - var3);
                  this.IsPlayBossBattleBGM = true;
               }

               this.start_cnt = true;
            }

            if (this.start_cnt) {
               if (this.display_cnt >= 35) {
                  this.state = 0;
                  this.posX = BOSS_DRIP_X;
               } else {
                  ++this.display_cnt;
               }
            }
            break;
         case 0:
            this.velY += GRAVITY;
            this.posY += this.velY;
            if (this.posY + this.velY >= this.getGroundY(this.posX, this.posY)) {
               this.posY = this.getGroundY(this.posX, this.posY);
               this.state = 1;
               this.spring_state = 2;
               this.spring.setSpringAni(2, false);
               SoundSystem.getInstance().playSe(36);
            }
            break;
         case 1:
            if (this.spring.getEndState()) {
               this.state = 2;
               this.spring_state = 1;
               this.spring.setSpringAni(1, true);
            }
            break;
         case 2:
            if (this.display_wait_cnt < 16) {
               ++this.display_wait_cnt;
            } else {
               this.state = 3;
               this.facedrawer.setActionId(1);
               this.facedrawer.setTrans(0);
               this.facedrawer.setLoop(true);
               this.display_wait_cnt = 0;
            }
            break;
         case 3:
            if (this.show_laugh_cnt < 10) {
               ++this.show_laugh_cnt;
            } else {
               this.state = 4;
               this.changeAniState(this.facedrawer, 0, true);
            }
            break;
         case 4:
            if (this.display_wait_cnt < 16) {
               ++this.display_wait_cnt;
            } else {
               this.state = 6;
               this.spring_state = 2;
               this.spring.setSpringAni(2, false);
               this.display_wait_cnt = 0;
            }

            this.changeAniState(this.boatdrawer, this.boat_state, true);
            this.changeAniState(this.facedrawer, this.face_state, true);
            break;
         case 5:
            this.resetBossDisplayState();
            if (this.wait_cnt < this.wait_cnt_max) {
               ++this.wait_cnt;
            } else {
               this.state = 6;
               this.spring_state = 2;
               this.spring.setSpringAni(2, false);
               this.wait_cnt = 0;
            }

            this.changeAniStateNoTran(this.boatdrawer, this.boat_state, true);
            this.changeAniStateNoTran(this.facedrawer, this.face_state, true);
            break;
         case 6:
            this.resetBossDisplayState();
            if (this.spring.getEndState()) {
               this.state = 7;
               this.spring_state = 0;
               this.spring.setSpringAni(0, true);
               if (!this.HighJump()) {
                  if (player.getFootPositionX() - this.posX <= this.min_range_x && player.getFootPositionX() - this.posX >= -this.min_range_x) {
                     if (player.getFootPositionX() - this.posX <= this.min_range_x && player.getFootPositionX() - this.posX > 0) {
                        this.velX = this.min_range_x / this.v_x_frame;
                     } else if (player.getFootPositionX() - this.posX >= -this.min_range_x && player.getFootPositionX() - this.posX < 0) {
                        this.velX = -this.min_range_x / this.v_x_frame;
                     }
                  } else {
                     this.velX = (player.getFootPositionX() - this.posX) / this.v_x_frame;
                  }

                  this.velY = this.normal_jump_startVerlY;
               } else {
                  this.velX = 0;
                  this.velY = this.lowerHP_jump_startVerlY;
                  this.IsinHighJump = false;
               }
            }

            this.changeAniState(this.boatdrawer, this.boat_state, true);
            this.changeAniState(this.facedrawer, this.face_state, true);
            break;
         case 7:
            this.resetBossDisplayState();
            if (this.posY + this.velY > this.getGroundY(this.posX, this.posY)) {
               this.posY = this.getGroundY(this.posX, this.posY);
               this.state = 8;
               this.spring_state = 2;
               this.spring.setSpringAni(2, false);
               if (this.HighJump()) {
                  MapManager.setShake(8);
               }

               if (this.HighJump()) {
                  var3 = player.getFootPositionY();
                  if (var3 == this.getGroundY(player.getFootPositionX(), player.getFootPositionY())) {
                     player.beHurt();
                     this.face_state = 1;
                  }
               }

               if (this.HP < 5) {
                  if (this.high_jump_cnt < this.high_jump_cnt_max) {
                     ++this.high_jump_cnt;
                  } else {
                     this.high_jump_cnt = 0;
                  }
               }

               SoundSystem.getInstance().playSe(36);
            } else {
               if (this.posX + this.velX >= this.limitRightX) {
                  this.posX = this.limitRightX;
               } else if (this.posX + this.velX <= this.limitLeftX) {
                  this.posX = this.limitLeftX;
               } else {
                  this.posX += this.velX;
               }

               if (this.IsJumpOutScreen() && !this.IsinHighJump) {
                  if (this.high_jump_wait_cnt < 34) {
                     ++this.high_jump_wait_cnt;
                  } else {
                     this.posX = player.getFootPositionX();
                     this.velY = 0;
                     this.velY += GRAVITY * 2;
                     this.IsinHighJump = true;
                  }
               } else {
                  this.velY += GRAVITY;
                  this.posY += this.velY;
                  this.high_jump_wait_cnt = 0;
               }

               this.springHmax = this.spring.getSpringHeight();
            }

            this.changeAniStateNoTran(this.boatdrawer, this.boat_state, true);
            this.changeAniStateNoTran(this.facedrawer, this.face_state, true);
            break;
         case 8:
            this.resetBossDisplayState();
            if (this.spring.getEndState()) {
               this.state = 5;
               this.spring_state = 1;
               this.spring.setSpringAni(1, true);
            }

            this.changeAniStateNoTran(this.boatdrawer, this.boat_state, true);
            this.changeAniStateNoTran(this.facedrawer, this.face_state, true);
            break;
         case 9:
            this.resetBossDisplayState();
            this.bossbroken.logicBoom(this.posX, this.posY - this.boom_offset);
            this.springH = 0;
            if (this.posY + this.velY > this.getGroundY(this.posX, this.posY) && this.drop_cnt == 0) {
               this.posY = this.getGroundY(this.posX, this.posY);
               this.velY = -640;
               this.drop_cnt = 1;
            } else if (this.posY + this.velY > this.getGroundY(this.posX, this.posY) && this.drop_cnt == 1) {
               this.posY = this.getGroundY(this.posX, this.posY);
               this.velY = -320;
               this.drop_cnt = 2;
            } else if (this.posY + this.velY > this.getGroundY(this.posX, this.posY) && this.drop_cnt == 2) {
               this.posY = this.getGroundY(this.posX, this.posY);
               this.drop_cnt = 3;
            } else if (this.drop_cnt != 3) {
               this.velY += GRAVITY;
               this.posY += this.velY;
            }

            if (this.bossbroken.getEndState()) {
               this.state = 10;
               this.fly_top = this.posY;
               this.fly_end = this.side_right;
               this.wait_cnt = 0;
               bossFighting = false;
               player.getBossScore();
               SoundSystem.getInstance().playBgm(StageManager.getBgmId(), true);
            }

            this.changeAniStateNoTran(this.facedrawer, this.face_state, true);
            break;
         case 10:
            this.springH = 0;
            ++this.wait_cnt;
            if (this.wait_cnt >= this.wait_cnt_max && this.posY >= this.fly_top - this.fly_top_range) {
               this.posY -= this.escape_v;
            }

            if (this.posY <= this.fly_top - this.fly_top_range && this.WaitCnt == 0) {
               this.posY = this.fly_top - this.fly_top_range;
               this.escapefacedrawer.setActionId(0);
               this.escapeboatdrawer.setActionId(1);
               this.escapeboatdrawer.setLoop(false);
               this.WaitCnt = 1;
            }

            if (this.WaitCnt == 1 && this.escapeboatdrawer.checkEnd()) {
               this.escapefacedrawer.setActionId(0);
               this.escapefacedrawer.setTrans(2);
               this.escapefacedrawer.setLoop(true);
               this.escapeboatdrawer.setActionId(1);
               this.escapeboatdrawer.setTrans(2);
               this.escapeboatdrawer.setLoop(false);
               this.WaitCnt = 2;
            }

            if (this.WaitCnt == 2 && this.escapeboatdrawer.checkEnd()) {
               this.escapeboatdrawer.setActionId(0);
               this.escapeboatdrawer.setTrans(2);
               this.escapeboatdrawer.setLoop(true);
               this.WaitCnt = 3;
            }

            if (this.WaitCnt == 3 || this.WaitCnt == 4) {
               this.posX += this.escape_v;
            }

            if (this.posX > this.side_right << 6 && this.WaitCnt == 3) {
               var3 = MapManager.getCamera().x;
               int var5 = MapManager.CAMERA_WIDTH;
               var4 = MapManager.getCamera().y;
               Cage var7 = new Cage(var3 + (var5 >> 1) << 6, var4 << 6);
               addGameObject(var7);
               MapManager.lockCamera(true);
               this.WaitCnt = 4;
            }
         }

         this.spring.logic(this.posX, this.posY, this.spring_state, this.velocity);
         Boss2Spring var8 = this.spring;
         boolean var6;
         if (this.boat_state == 1) {
            var6 = true;
         } else {
            var6 = false;
         }

         var8.getIsHurt(var6);
         this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
         this.checkWithPlayer(var2, var1, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var5 = this.collisionRect;
      int var3 = this.offset_y;
      int var4 = this.springH;
      var5.setRect(var1 - 1536, var2 - (var3 + 1280) - var4, 3072, 1280);
   }

   public void resetBossDisplayState() {
      if (this.face_state != 0) {
         if (this.face_cnt < 8) {
            ++this.face_cnt;
         } else {
            this.face_state = 0;
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

   }
}
