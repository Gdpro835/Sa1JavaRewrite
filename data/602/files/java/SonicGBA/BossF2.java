package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class BossF2 extends BossObject {
   private static final int BOSS_ENTER_SPEED = -360;
   private static final int BOSS_ENTER_STOP_POSX = 104448;
   private static final int BOSS_MOVE_SIDE_LEFT = 91520;
   private static final int BOSS_MOVE_SIDE_RIGHT = 117376;
   private static final int BOSS_MOVE_SPEED = 480;
   private static final int BOSS_PLAYER_FIGHT_DISTANCE = 5760;
   private static final int COLLISION_BROKEN_HEIGHT = 2560;
   private static final int COLLISION_HEIGHT = 3072;
   private static final int COLLISION_WIDTH = 4224;
   private static final int DRILL_OFFSET_X = 2944;
   private static final int DRILL_OFFSET_Y = 1280;
   private static final int DRILL_SHOOT_SPEED = 400;
   private static final int END_POSX = 117120;
   private static final int ENTER_SCREEN_FRAME_MAX = 3;
   private static final int FACE_BROKEN = 3;
   private static final int FACE_HURT = 2;
   private static final int FACE_NORMAL = 0;
   private static final int FACE_OFFSET_Y = 2112;
   private static final int FACE_SMILE = 1;
   private static final int INIT_STOP_POSX = 105472;
   private static final int INIT_STOP_POSY = 33792;
   private static final int LAUGH_TIME = 11;
   private static final int MACHINE_BROKEN = 3;
   private static final int MACHINE_HURT = 2;
   private static final int MACHINE_MOVE = 0;
   private static final int MACHINE_NODRILL_MOVE = 1;
   private static final int SHOW_BOSS_END = 2;
   private static final int SHOW_BOSS_ENTER = 0;
   private static final int SHOW_BOSS_LAUGH = 1;
   private static final int SIDE_DOWN = 688;
   private static final int SIDE_LEFT = 1480;
   private static final int SIDE_RIGHT = 1784;
   private static final int SIDE_UP = 528;
   private static final int STATE_BROKEN = 3;
   private static final int STATE_ENTER_SHOW = 1;
   private static final int STATE_ESCAPE = 4;
   private static final int STATE_INIT = 0;
   private static final int STATE_PRO = 2;
   private static Animation boatAni;
   private static final int cnt_max = 8;
   private static Animation escapefaceAni;
   private static Animation faceAni;
   private static Animation machineAni;
   private static Animation wheelAni;
   private int WaitCnt;
   private AnimationDrawer boatdrawer;
   private BossBroken bossbroken;
   private boolean displayFlag;
   private BossF2Drill drill;
   private int drill_offsetx;
   private int drop_cnt;
   private int drop_velY;
   private int enter_screen_frame_cn;
   private int escape_v = 512;
   private AnimationDrawer escapefacedrawer;
   private AnimationDrawer faceDrawer;
   private int face_cnt;
   private int face_state;
   private int fly_end;
   private int fly_top;
   private int fly_top_range = 5760;
   private boolean isDisplayDrill;
   private boolean isFight = false;
   private boolean isShoot = false;
   private int laugh_cn;
   private AnimationDrawer machineDrawer;
   private int machine_state;
   private int show_step;
   private int state;
   private int velocity;
   private int wait_cnt;
   private int wait_cnt_max = 10;
   private AnimationDrawer[] wheelDrawer;
   private int wheel_velx;
   private int wheel_vely;
   private int[][] wheelpos;

   protected BossF2(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.posX = 114176;
      this.posY = this.getGroundY(this.posX, this.posY);
      if (machineAni == null) {
         machineAni = new Animation("/animation/bossf2_machine");
      }

      this.machineDrawer = machineAni.getDrawer(0, true, 0);
      if (faceAni == null) {
         faceAni = new Animation("/animation/bossf2_face");
      }

      this.faceDrawer = faceAni.getDrawer(0, true, 0);
      if (wheelAni == null) {
         wheelAni = new Animation("/animation/bossf2_wheel");
      }

      this.wheelDrawer = new AnimationDrawer[2];
      this.wheelDrawer[0] = wheelAni.getDrawer(0, true, 0);
      this.wheelDrawer[1] = wheelAni.getDrawer(1, true, 0);
      this.wheelpos = new int[4][2];
      if (boatAni == null) {
         boatAni = new Animation("/animation/pod_boat");
      }

      this.boatdrawer = boatAni.getDrawer(0, true, 0);
      if (escapefaceAni == null) {
         escapefaceAni = new Animation("/animation/pod_face");
      }

      this.escapefacedrawer = escapefaceAni.getDrawer(0, false, 0);
      this.machine_state = 0;
      this.face_state = 0;
      this.isDisplayDrill = false;
      this.drill_offsetx = 0;
      this.state = 0;
      this.displayFlag = false;
      this.HP = 4;
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
      Animation.closeAnimation(wheelAni);
      Animation.closeAnimation(boatAni);
      Animation.closeAnimation(escapefaceAni);
      escapefaceAni = null;
      boatAni = null;
      wheelAni = null;
      machineAni = null;
      faceAni = null;
   }

   public void close() {
      this.machineDrawer = null;
      this.faceDrawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (this.state != 4 && this.state != 3 && this.state == 2 && this.face_state != 2) {
         --this.HP;
         player.doBossAttackPose(this, var2);
         if (this.HP > 0) {
            this.machine_state = 2;
            this.face_state = 2;
            this.face_cnt = 0;
            if (this.HP == 1) {
               this.isFight = true;
               this.isShoot = false;
            }
         } else {
            this.state = 3;
            this.face_state = 3;
            this.machine_state = 3;
            BossBroken var4 = new BossBroken(28, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
            this.bossbroken = var4;
            addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
            this.drop_velY = 0;

            for(var2 = 0; var2 < 4; ++var2) {
               this.wheelpos[var2][1] = this.posY;
            }

            if (this.velocity <= 0) {
               this.wheelpos[0][0] = this.posX - 2112 + 256;
               this.wheelpos[1][0] = this.posX - 640;
               this.wheelpos[2][0] = this.posX + 320;
               this.wheelpos[3][0] = this.posX + 2112 - 768;
               this.wheel_velx = -480;
            } else {
               this.wheelDrawer[0].setTrans(2);
               this.wheelDrawer[1].setTrans(2);
               this.wheelpos[3][0] = this.posX - 2112 + 256 + 384;
               this.wheelpos[2][0] = this.posX - 640 + 384;
               this.wheelpos[1][0] = this.posX + 320 + 384;
               this.wheelpos[0][0] = this.posX + 2112 - 768 + 384;
               this.wheel_velx = 480;
            }

            this.wheel_vely = -512;
            this.posY -= 512;
            this.isDisplayDrill = false;
            this.drill.setEnd();
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
      if (!this.dead && this.state != 4 && this.state != 3 && this.state == 2 && var1 == player) {
         if (player.isAttackingEnemy()) {
            if (this.face_state != 2) {
               --this.HP;
               player.doBossAttackPose(this, var2);
               if (this.HP > 0) {
                  this.machine_state = 2;
                  this.face_state = 2;
                  this.face_cnt = 0;
                  if (this.HP == 1) {
                     this.isFight = true;
                     this.isShoot = false;
                  }
               } else {
                  this.state = 3;
                  this.face_state = 3;
                  this.machine_state = 3;
                  BossBroken var3 = new BossBroken(28, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
                  this.bossbroken = var3;
                  addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
                  this.drop_velY = 0;

                  for(var2 = 0; var2 < 4; ++var2) {
                     this.wheelpos[var2][1] = this.posY;
                  }

                  if (this.velocity <= 0) {
                     this.wheelpos[0][0] = this.posX - 2112 + 256;
                     this.wheelpos[1][0] = this.posX - 640;
                     this.wheelpos[2][0] = this.posX + 320;
                     this.wheelpos[3][0] = this.posX + 2112 - 768;
                     this.wheel_velx = -480;
                  } else {
                     this.wheelDrawer[0].setTrans(2);
                     this.wheelDrawer[1].setTrans(2);
                     this.wheelpos[3][0] = this.posX - 2112 + 256 + 384;
                     this.wheelpos[2][0] = this.posX - 640 + 384;
                     this.wheelpos[1][0] = this.posX + 320 + 384;
                     this.wheelpos[0][0] = this.posX + 2112 - 768 + 384;
                     this.wheel_velx = 480;
                  }

                  this.wheel_vely = -512;
                  this.posY -= 512;
                  this.isDisplayDrill = false;
                  this.drill.setEnd();
                  this.drop_cnt = 0;
               }

               if (this.HP == 0) {
                  SoundSystem.getInstance().playSe(35);
               } else {
                  SoundSystem.getInstance().playSe(34);
               }
            }
         } else if (this.state != 3 && this.state != 4) {
            player.beHurt();
            this.face_state = 1;
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (this.displayFlag && !this.dead) {
         if (this.state == 3) {
            this.drawInMap(var1, this.wheelDrawer[1], this.wheelpos[0][0], this.wheelpos[0][1]);
            this.drawInMap(var1, this.wheelDrawer[1], this.wheelpos[2][0], this.wheelpos[2][1]);
         }

         if (this.state != 4) {
            this.drawInMap(var1, this.machineDrawer);
         }

         if (this.state == 3) {
            this.drawInMap(var1, this.wheelDrawer[0], this.wheelpos[1][0], this.wheelpos[1][1]);
            this.drawInMap(var1, this.wheelDrawer[0], this.wheelpos[3][0], this.wheelpos[3][1]);
         }

         if (this.state != 4) {
            AnimationDrawer var5 = this.faceDrawer;
            int var4 = this.posX;
            int var3 = this.posY;
            short var2;
            if (this.state == 3) {
               var2 = 512;
            } else {
               var2 = 0;
            }

            this.drawInMap(var1, var5, var4, var3 - 2112 + var2);
         }

         if (this.state == 4) {
            this.drawInMap(var1, this.boatdrawer, this.posX, this.posY);
            this.drawInMap(var1, this.escapefacedrawer, this.posX, this.posY - 1664);
         }

         if (this.isDisplayDrill && this.drill != null) {
            this.drill.draw(var1);
         }

         if (this.state == 3 && this.bossbroken != null) {
            this.bossbroken.draw(var1);
         }

         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var2 = this.posX;
         int var3 = this.posY;
         if (this.state > 0 && this.state < 4) {
            isBossEnter = true;
         } else if (this.state == 4) {
            isBossEnter = false;
         }

         label168:
         switch(this.state) {
         case 0:
            if (player.getFootPositionX() < 117120) {
               if (player.getFootPositionX() >= 105472) {
                  MapManager.setCameraLeftLimit(1480);
                  MapManager.setCameraRightLimit(1784);
               }

               if (player.getFootPositionX() >= 105472 && player.getFootPositionX() < 116096 && player.getFootPositionY() >= 33792) {
                  this.state = 1;
                  bossFighting = true;
                  bossID = 29;
                  MapManager.setCameraUpLimit(528);
                  MapManager.setCameraDownLimit(688);
                  MapManager.setCameraLeftLimit(1480);
                  MapManager.setCameraRightLimit(1784);
                  this.show_step = 0;
                  SoundSystem.getInstance().playBgm(47);
                  this.displayFlag = true;
               }
            }
            break;
         case 1:
            switch(this.show_step) {
            case 0:
               if (this.posX - 360 > 104448) {
                  this.posX -= 360;
               } else {
                  this.posX = 104448;
                  this.show_step = 1;
                  this.enter_screen_frame_cn = 0;
                  this.laugh_cn = 0;
               }
               break label168;
            case 1:
               if (this.enter_screen_frame_cn < 3) {
                  ++this.enter_screen_frame_cn;
               } else {
                  this.face_state = 1;
               }

               if (this.laugh_cn < 14) {
                  ++this.laugh_cn;
               } else {
                  this.show_step = 2;
                  this.face_state = 0;
               }
               break label168;
            case 2:
               this.state = 2;
               this.velocity = -480;
               this.drill = new BossF2Drill(29, this.posX, this.posY, 0, 0, 0, 0);
               addGameObject(this.drill, this.posX >> 6, this.posY >> 6);
            default:
               break label168;
            }
         case 2:
            if (this.face_state != 0) {
               if (this.face_cnt < 8) {
                  ++this.face_cnt;
               } else {
                  this.machine_state = 0;
                  this.face_state = 0;
                  this.face_cnt = 0;
               }
            }

            this.changeAniState(this.machineDrawer, this.machine_state);
            this.changeAniState(this.faceDrawer, this.face_state);
            int var4 = this.posX;
            short var1;
            if (this.velocity > 0) {
               var1 = 2944;
            } else {
               var1 = -2944;
            }

            var4 = var4 + var1 + this.drill_offsetx;
            if ((var4 > 117376 || var4 < 91520) && this.drill_offsetx != 0) {
               this.isDisplayDrill = false;
               this.drill.setEnd();
            }

            if (this.drill != null) {
               BossF2Drill var8 = this.drill;
               int var7 = this.posY;
               boolean var5;
               if (this.velocity > 0) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               var8.logic(var4, var7 - 1280, var5);
            }

            if (this.velocity > 0) {
               if (this.posX < 117376) {
                  this.posX += this.velocity;
               } else {
                  this.posX = 117376;
                  this.velocity = -this.velocity;
               }
            } else if (this.posX > 91520) {
               this.posX += this.velocity;
            } else {
               this.posX = 91520;
               this.velocity = -this.velocity;
            }

            if (this.isFight) {
               if (this.velocity > 0) {
                  if (this.posX == 91520) {
                     this.isShoot = true;
                     this.isDisplayDrill = true;
                     this.isFight = false;
                  }
               } else if (this.posX == 117376) {
                  this.isShoot = true;
                  this.isDisplayDrill = true;
                  this.isFight = false;
               }
            }

            if (this.isShoot) {
               if (this.velocity > 0) {
                  this.drill_offsetx += 400;
               } else {
                  this.drill_offsetx -= 400;
               }

               this.machine_state = 1;
            }
            break;
         case 3:
            this.bossbroken.logicBoom(this.posX, this.posY - 1280);
            if (this.posY + this.drop_velY > this.getGroundY(this.posX, this.posY)) {
               this.posY = this.getGroundY(this.posX, this.posY);
            } else {
               this.drop_velY += GRAVITY >> 1;
               this.posY += this.drop_velY;
            }

            int[] var6 = this.wheelpos[0];
            var6[0] += this.wheel_velx;
            var6 = this.wheelpos[1];
            var6[0] += this.wheel_velx;
            var6 = this.wheelpos[2];
            var6[0] -= this.wheel_velx;
            var6 = this.wheelpos[3];
            var6[0] -= this.wheel_velx;
            if (this.wheelpos[0][1] + this.wheel_vely > this.getGroundY(this.wheelpos[0][0], this.wheelpos[0][1]) && this.drop_cnt == 0) {
               this.wheelpos[0][1] = this.getGroundY(this.wheelpos[0][0], this.wheelpos[0][1]);
               this.wheel_vely = -384;
               this.drop_cnt = 1;
            } else if (this.wheelpos[0][1] + this.wheel_vely > this.getGroundY(this.wheelpos[0][0], this.wheelpos[0][1]) && this.drop_cnt == 1) {
               this.wheelpos[0][1] = this.getGroundY(this.wheelpos[0][0], this.wheelpos[0][1]);
               this.wheel_vely = -192;
               this.drop_cnt = 2;
            } else if (this.wheelpos[0][1] + this.wheel_vely > this.getGroundY(this.wheelpos[0][0], this.wheelpos[0][1]) && this.drop_cnt == 2) {
               this.wheelpos[0][1] = this.getGroundY(this.wheelpos[0][0], this.wheelpos[0][1]);
            } else {
               this.wheel_vely += GRAVITY;
               var6 = this.wheelpos[0];
               var6[1] += this.wheel_vely;
            }

            this.wheelpos[1][1] = this.wheelpos[0][1];
            this.wheelpos[2][1] = this.wheelpos[0][1];
            this.wheelpos[3][1] = this.wheelpos[0][1];
            if (this.bossbroken.getEndState()) {
               this.escapefacedrawer.setActionId(4);
               this.escapefacedrawer.setLoop(true);
               this.state = 4;
               bossFighting = false;
               this.fly_top = this.posY;
               this.fly_end = 114176;
               player.getBossScore();
               this.wait_cnt = 0;
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
            }

            if (this.posX - this.fly_end > this.fly_top_range && this.WaitCnt == 3) {
               MapManager.setCameraUpLimit(0);
               MapManager.setCameraDownLimit(MapManager.getPixelHeight());
               MapManager.setCameraRightLimit(MapManager.getPixelWidth());
               this.WaitCnt = 4;
               SoundSystem.getInstance().playBgm(18);
            }
         }

         this.changeAniState(this.machineDrawer, this.machine_state);
         this.changeAniState(this.faceDrawer, this.face_state);
         this.refreshCollisionRect(this.posX, this.posY);
         this.checkWithPlayer(var2, var3, this.posX, this.posY);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      if (this.state != 3) {
         this.collisionRect.setRect(var1 - 2112, var2 - 3072, 4224, 3072);
      } else {
         this.collisionRect.setRect(var1 - 2112, var2 - 2560, 4224, 2560);
      }

   }
}
