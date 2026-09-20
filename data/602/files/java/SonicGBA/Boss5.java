package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class Boss5 extends BossObject {
   private static final int BEFORE_MEET_LINE;
   private static final int CAGE_DRIP_Y = 1020;
   private static final int EGG_POS_X = 609792;
   private static final int EGG_POS_Y = 69120;
   private static final int EGG_SIDE_LEFT = 9360;
   private static final int EGG_SIDE_RIGHT;
   private static final int F_BALL_A = 3;
   private static final int F_BALL_A_HURT = 8;
   private static final int F_BALL_B = 4;
   private static final int F_BALL_B_HURT = 9;
   private static final int F_DEFENCE_BACK = 15;
   private static final int F_DEFENCE_READY = 13;
   private static final int F_DEFENCING = 14;
   private static final int F_DRIP_AIR = 11;
   private static final int F_DRIP_LAND = 12;
   private static final int F_DRIP_READY = 10;
   private static final int F_FIGHT = 18;
   private static final int F_FLY_A = 16;
   private static final int F_FLY_B = 17;
   private static final int F_HURT_AIR = 6;
   private static final int F_HURT_KNOCK = 5;
   private static final int F_HURT_LAND = 7;
   private static final int F_READY = 0;
   private static final int F_WAIT = 1;
   private static final int F_WAIT_HURT = 2;
   private static final int MEET_SONIC_LINE;
   private static final int MIDDLE_SCREEN;
   private static final int M_BALL_A = 21;
   private static final int M_BALL_A_HURT = 26;
   private static final int M_BALL_B = 22;
   private static final int M_BALL_B_HURT = 27;
   private static final int M_DEFENCE_BACK = 33;
   private static final int M_DEFENCE_READY = 31;
   private static final int M_DEFENCING = 32;
   private static final int M_DRIP_AIR = 29;
   private static final int M_DRIP_LAND = 30;
   private static final int M_DRIP_READY = 28;
   private static final int M_FLY_A = 34;
   private static final int M_FLY_B = 35;
   private static final int M_HURT_AIR = 24;
   private static final int M_HURT_KNOCK = 23;
   private static final int M_HURT_LAND = 25;
   private static final int M_KO_AIR = 39;
   private static final int M_KO_LAND = 40;
   private static final int M_MISSILE_LAUNCH = 37;
   private static final int M_MISSILE_READY = 36;
   private static final int M_MISSILE_READY_HURT = 38;
   private static final int M_PARTS_BODY = 42;
   private static final int M_PARTS_HAND_L = 44;
   private static final int M_PARTS_HAND_R = 43;
   private static final int M_PARTS_HEAD = 41;
   private static final int M_PARTS_LEG_L = 46;
   private static final int M_PARTS_LEG_R = 45;
   private static final int M_WAIT = 19;
   private static final int M_WAIT_HURT = 20;
   private static final int SIDE_DOWN_MIDDLE = 1128;
   private static final int SIDE_LEFT;
   private static final int SIDE_RIGHT = 9496;
   private static final int START_POS_X = 605696;
   private static final int STATE_ESCAPE = 40;
   private static final int STATE_FRESH_ATTACK_TRANS = 3;
   private static final int STATE_FRESH_BALL_HORIZON_ATTACK_FIGHT = 6;
   private static final int STATE_FRESH_BALL_HORIZON_ATTACK_READY = 5;
   private static final int STATE_FRESH_BALL_UP = 7;
   private static final int STATE_FRESH_DEFENCE_BACK = 18;
   private static final int STATE_FRESH_DEFENCE_READY = 16;
   private static final int STATE_FRESH_DEFENCING = 17;
   private static final int STATE_FRESH_FIGHT = 12;
   private static final int STATE_FRESH_FLY = 8;
   private static final int STATE_FRESH_FLY_DRIPPING = 10;
   private static final int STATE_FRESH_FLY_DRIP_LAND = 11;
   private static final int STATE_FRESH_FLY_DRIP_READY = 9;
   private static final int STATE_FRESH_HURT_AIR = 14;
   private static final int STATE_FRESH_HURT_KNOCK = 13;
   private static final int STATE_FRESH_HURT_LAND = 15;
   private static final int STATE_FRESH_READY = 4;
   private static final int STATE_FRESH_WAIT_0 = 0;
   private static final int STATE_FRESH_WAIT_1 = 1;
   private static final int STATE_FRESH_WAKE = 2;
   private static final int STATE_MACHINE_ATTACK_TRANS = 20;
   private static final int STATE_MACHINE_BALL_HORIZON_ATTACK_FIGHT = 22;
   private static final int STATE_MACHINE_BALL_HORIZON_ATTACK_READY = 21;
   private static final int STATE_MACHINE_BALL_UP = 23;
   private static final int STATE_MACHINE_BROKEN = 38;
   private static final int STATE_MACHINE_DEFENCE_BACK = 35;
   private static final int STATE_MACHINE_DEFENCE_READY = 33;
   private static final int STATE_MACHINE_DEFENCING = 34;
   private static final int STATE_MACHINE_FLY = 24;
   private static final int STATE_MACHINE_FLY_DRIPPING = 26;
   private static final int STATE_MACHINE_FLY_DRIP_LAND = 27;
   private static final int STATE_MACHINE_FLY_DRIP_READY = 25;
   private static final int STATE_MACHINE_HURT_AIR = 31;
   private static final int STATE_MACHINE_HURT_KNOCK = 30;
   private static final int STATE_MACHINE_HURT_LAND = 32;
   private static final int STATE_MACHINE_KO_AIR = 36;
   private static final int STATE_MACHINE_KO_LAND = 37;
   private static final int STATE_MACHINE_MISSILE_ATTACK = 29;
   private static final int STATE_MACHINE_MISSILE_READY = 28;
   private static final int STATE_MACHINE_PIECES = 39;
   private static final int STATE_MACHINE_READY = 19;
   private static Animation boatAni;
   private static int damageframe;
   private static final int defence_cnt_max = 15;
   private static Animation escapefaceAni;
   private static final int first_jump_cnt_max = 3;
   private static Animation knucklesAni;
   private static final int ready_cnt_max = 24;
   private static final int talk_cnt_max = 9;
   private int ALERT_RANGE = 11520;
   private int AttackStartDirection;
   private int BOSS5_WIDTH = 1536;
   private int COLLISION_HEIGHT = 1664;
   private int COLLISION_WIDTH = 1024;
   private boolean IsConner = false;
   private boolean IsHurt = false;
   private boolean IsPlayerRunaway = false;
   private int KOWaitCnt;
   private int KOWaitCntMax = 10;
   private int[] Vix = new int[]{750, -300, -150, 150, 450, -750, 600, 300, -450};
   private int[] Viy = new int[]{-1200, -1050, -900, -750, -600};
   private int WaitCnt;
   private int alert_state;
   private AnimationDrawer boatdrawer;
   private int boomX;
   private int boomY;
   private int boom_offset = 640;
   private AnimationDrawer boomdrawer;
   private BossBroken bossbroken;
   private int defence_cnt = 0;
   private int enemyDirct;
   private int escape_cnt = 0;
   private int escape_cnt_max = 60;
   private int escape_v = 512;
   private AnimationDrawer escapefacedrawer;
   private int fight_alert_range = 2048;
   private int first_jump_cnt = 0;
   private int fly_attack_site;
   private int fly_drip_offset = 2304;
   private int fly_end;
   private int fly_move_x_speed1 = -772;
   private int fly_move_y_speed1 = 140;
   private int fly_range = 4096;
   private int fly_top;
   private int fly_top_offset = 5632;
   private int fly_up_speed1 = 704;
   private Boss5FlyDefence flydefence;
   private int horizonAttackReady_cnt;
   private int horizonAttackReady_cnt_max = 16;
   private int horizon_move_speed = 1080;
   private AnimationDrawer knuckdrawer;
   private int limitLeftX;
   private int limitRightX;
   private int missile_alert_range = 5120;
   private int missile_alert_state;
   private int pieces_drip_cnt;
   private int[][] pos;
   private int prestate;
   private int randomAttackState;
   private int ready_cnt = 0;
   private int state;
   private int talk_cnt = 0;
   private int velX;
   private int velY;
   private int velocity = -768;

   static {
      SIDE_LEFT = 9496 - SCREEN_WIDTH;
      MEET_SONIC_LINE = SIDE_LEFT + 24 << 6;
      BEFORE_MEET_LINE = SIDE_LEFT - 232 << 6;
      MIDDLE_SCREEN = SIDE_LEFT + 9496 >> 1 << 6;
      EGG_SIDE_RIGHT = SCREEN_WIDTH + 9360;
   }

   protected Boss5(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.posY -= 960;
      this.posX = 605696;
      this.posY = this.getGroundY(this.posX, this.posY);
      this.limitRightX = 606720;
      this.limitLeftX = SIDE_LEFT + 16 << 6;
      this.fly_top = this.posY - this.fly_top_offset;
      this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
      if (knucklesAni == null) {
         knucklesAni = new Animation("/animation/boss5");
      }

      this.knuckdrawer = knucklesAni.getDrawer(0, true, 0);
      if (BoomAni == null) {
         BoomAni = new Animation("/animation/boom");
      }

      this.boomdrawer = BoomAni.getDrawer(0, true, 0);
      if (boatAni == null) {
         boatAni = new Animation("/animation/pod_boat");
      }

      this.boatdrawer = boatAni.getDrawer(0, true, 0);
      if (escapefaceAni == null) {
         escapefaceAni = new Animation("/animation/pod_face");
      }

      this.escapefacedrawer = escapefaceAni.getDrawer(2, false, 0);
      this.flydefence = new Boss5FlyDefence(33, var2, var3, 0, 0, 0, 0);
      addGameObject(this.flydefence, var2, var3);
      this.setBossHP();
   }

   private boolean CanFreshFight() {
      boolean var1;
      if (Math.abs(this.posX - player.getFootPositionX()) <= this.fight_alert_range && player.isOnGound()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private int halfLife() {
      byte var1;
      if (GlobalResource.isEasyMode() && stageModeState == 0) {
         var1 = 3;
      } else {
         var1 = 4;
      }

      return var1;
   }

   private int halfLifeNum() {
      byte var1;
      if (GlobalResource.isEasyMode() && stageModeState == 0) {
         var1 = 3;
      } else {
         var1 = 4;
      }

      return var1;
   }

   private void hurt_air_control() {
      this.hurt_side_cotrol();
      this.velY += GRAVITY;
      this.posY += this.velY;
   }

   private void hurt_side_cotrol() {
      int var3 = this.posX;
      int var2 = this.velX;
      int var1 = this.BOSS5_WIDTH;
      if (var3 + var2 + var1 >= MapManager.getCamera().x + MapManager.CAMERA_WIDTH << 6) {
         this.posX += 0;
      } else if (this.posX + this.velX - this.BOSS5_WIDTH <= MapManager.getCamera().x << 6) {
         this.posX += 0;
      } else {
         this.posX += this.velX;
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(knucklesAni);
      Animation.closeAnimation(BoomAni);
      Animation.closeAnimation(boatAni);
      Animation.closeAnimation(escapefaceAni);
      knucklesAni = null;
      BoomAni = null;
      boatAni = null;
      escapefaceAni = null;
   }

   public void HurtLogic() {
      if (!this.IsHurt) {
         player.doAttackPose(this, this.enemyDirct);
         --this.HP;
         this.velY = -Math.abs(this.velocity);
         if (player.getVelX() > 0) {
            this.velX = Math.abs(this.velocity) >> 1;
         } else {
            this.velX = -Math.abs(this.velocity) >> 1;
         }

         this.hurt_side_cotrol();
         this.IsHurt = true;
         if (this.HP >= this.halfLifeNum()) {
            this.changeAniState(this.knuckdrawer, 5, false);
            this.state = 13;
            if (this.HP == this.halfLifeNum()) {
               isBossHalf = true;
               SoundSystem.getInstance().playBgm(24, true);
            } else {
               isBossHalf = false;
               SoundSystem.getInstance().playSe(34, false);
            }
         } else if (this.HP == 0) {
            this.changeAniState(this.knuckdrawer, 39, true);
            this.state = 36;
            SoundSystem.getInstance().playSe(35, false);
         } else {
            this.changeAniState(this.knuckdrawer, 23, false);
            this.state = 30;
            SoundSystem.getInstance().playSe(34, false);
         }

         this.COLLISION_WIDTH = 64;
         this.COLLISION_HEIGHT = 64;
      }

   }

   public boolean IsNeedforDefence(int var1) {
      boolean var2;
      if (var1 == 0 && player.isAttackingEnemy() && player.isOnGound()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void PlayerHurtBall(PlayerObject var1, int var2) {
      int var3 = Math.abs(player.getVelX());
      int var4 = player.getAnimationId();
      switch(var2) {
      case 0:
         var1.beStop(this.collisionRect.y1, var2, this);
         break;
      case 1:
      case 4:
         var1.beStop(this.collisionRect.y0, var2, this);
         break;
      case 2:
         var1.beStop(this.collisionRect.x1, var2, this);
         player.setVelX(var3);
         break;
      case 3:
         var1.beStop(this.collisionRect.x0, var2, this);
         player.setVelX(-var3);
      }

      player.setAnimationId(var4);
      if (this.defence_cnt < 15) {
         ++this.defence_cnt;
      } else {
         this.IsPlayerRunaway = true;
      }

      var1 = player;
      var2 = PlayerObject.getCharacterID();
      var1 = player;
      if (var2 == 3) {
         soundInstance.playSe(24);
      }

   }

   public void changeAniState(AnimationDrawer var1, int var2, boolean var3) {
      if (player.getFootPositionX() > this.posX) {
         var1.setActionId(var2);
         var1.setTrans(2);
         var1.setLoop(var3);
         if (this.velocity < 0) {
            this.velocity = -this.velocity;
         }

         if (this.fly_move_x_speed1 < 0) {
            this.fly_move_x_speed1 = -this.fly_move_x_speed1;
         }
      } else {
         var1.setActionId(var2);
         var1.setTrans(0);
         var1.setLoop(var3);
         if (this.velocity > 0) {
            this.velocity = -this.velocity;
         }

         if (this.fly_move_x_speed1 > 0) {
            this.fly_move_x_speed1 = -this.fly_move_x_speed1;
         }
      }

   }

   public void changeAniState(AnimationDrawer var1, int var2, boolean var3, int var4) {
      if (var4 < 0) {
         var1.setActionId(var2);
         var1.setTrans(2);
         var1.setLoop(var3);
         if (this.velocity < 0) {
            this.velocity = -this.velocity;
         }

         if (this.fly_move_x_speed1 < 0) {
            this.fly_move_x_speed1 = -this.fly_move_x_speed1;
         }
      } else {
         var1.setActionId(var2);
         var1.setTrans(0);
         var1.setLoop(var3);
         if (this.velocity > 0) {
            this.velocity = -this.velocity;
         }

         if (this.fly_move_x_speed1 > 0) {
            this.fly_move_x_speed1 = -this.fly_move_x_speed1;
         }
      }

   }

   public void changeAniStateNoTrans(AnimationDrawer var1, int var2, boolean var3) {
      var1.setActionId(var2);
      var1.setLoop(var3);
      if (player.getFootPositionX() > this.posX) {
         if (this.velocity < 0) {
            this.velocity = -this.velocity;
         }

         if (this.fly_move_x_speed1 < 0) {
            this.fly_move_x_speed1 = -this.fly_move_x_speed1;
         }
      } else {
         if (this.velocity > 0) {
            this.velocity = -this.velocity;
         }

         if (this.fly_move_x_speed1 > 0) {
            this.fly_move_x_speed1 = -this.fly_move_x_speed1;
         }
      }

   }

   public void close() {
      this.knuckdrawer = null;
      this.boomdrawer = null;
      this.boatdrawer = null;
      this.escapefacedrawer = null;
      this.bossbroken = null;
      this.flydefence = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      switch(this.state) {
      case 3:
      case 13:
      case 14:
      case 15:
      case 20:
      case 30:
      case 31:
      case 32:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
         break;
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 19:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      default:
         this.enemyDirct = var2;
         this.HurtLogic();
         break;
      case 16:
      case 17:
      case 18:
      case 33:
      case 34:
      case 35:
         this.PlayerHurtBall(var1, var2);
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && var1 == player) {
         if (player.isAttackingEnemy()) {
            switch(this.state) {
            case 3:
            case 13:
            case 14:
            case 15:
            case 20:
            case 30:
            case 31:
            case 32:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
               break;
            case 4:
            case 9:
            case 10:
            case 11:
            case 12:
            case 19:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            default:
               this.enemyDirct = var2;
               this.HurtLogic();
               break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 21:
            case 22:
            case 23:
            case 24:
               player.beHurt();
               break;
            case 16:
            case 17:
            case 18:
            case 33:
            case 34:
            case 35:
               this.PlayerHurtBall(var1, var2);
            }
         } else {
            switch(this.state) {
            case 4:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 19:
            case 20:
            case 25:
            case 26:
            case 27:
            case 30:
            case 31:
            case 32:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
               break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 12:
            case 16:
            case 17:
            case 18:
            case 21:
            case 22:
            case 23:
            case 24:
            case 28:
            case 29:
            case 33:
            case 34:
            case 35:
            default:
               player.beHurt();
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         if (this.state < 39) {
            this.drawInMap(var1, this.knuckdrawer, this.posX, this.posY);
         }

         if (this.HP == this.halfLifeNum() && this.state >= 13 && this.state <= 15 || this.HP == 0 && this.state >= 36 && this.state <= 37) {
            this.drawInMap(var1, this.boomdrawer, this.boomX, this.boomY);
         }

         if (this.bossbroken != null) {
            this.bossbroken.draw(var1);
         }

         if (this.state == 39) {
            for(int var2 = 0; var2 < this.pos.length; ++var2) {
               this.knuckdrawer.setActionId(var2 + 41);
               this.drawInMap(var1, this.knuckdrawer, this.pos[var2][0], this.pos[var2][1]);
            }
         }

         if (this.state == 40) {
            this.drawInMap(var1, this.boatdrawer, this.posX, this.posY);
            this.drawInMap(var1, this.escapefacedrawer, this.posX, this.posY - 1664);
         }

         this.flydefence.draw(var1);
         this.drawCollisionRect(var1);
      }

   }

   public void logic() {
      if (!this.dead) {
         int var3 = this.posX;
         int var2 = this.posY;
         this.boomX = var3;
         this.boomY = var2;
         if (this.boomY + 1024 + this.velY >= this.getGroundY(this.boomX, this.boomY)) {
            this.boomY = this.getGroundY(this.boomX, this.boomY) - 1024;
         }

         if (this.state > 0) {
            isBossEnter = true;
         }

         int var1;
         int var4;
         int var5;
         byte var8;
         label363:
         switch(this.state) {
         case 0:
            if (player.getFootPositionX() >= BEFORE_MEET_LINE) {
               this.state = 1;
               var1 = MapManager.CAMERA_HEIGHT * 3 / 4;
               MapManager.setCameraUpLimit(1128 - var1);
               var1 = MapManager.CAMERA_HEIGHT * 1 / 4;
               MapManager.setCameraDownLimit(var1 + 1128);
            }
            break;
         case 1:
            if (player.getFootPositionX() >= MEET_SONIC_LINE) {
               player.setMeetingBoss(false);
               MapManager.setCameraLeftLimit(SIDE_LEFT);
               MapManager.setCameraRightLimit(9496);
            } else {
               var1 = MapManager.getCamera().x;
               MapManager.setCameraLeftLimit(var1);
            }

            var4 = this.posX;
            var1 = MapManager.getCamera().x;
            if (var4 < var1 + MapManager.CAMERA_WIDTH - 30 << 6) {
               this.changeAniState(this.knuckdrawer, 0, false);
               this.COLLISION_WIDTH = 1024;
               this.COLLISION_HEIGHT = 1664;
               this.state = 2;
               bossFighting = true;
               bossID = 26;
               SoundSystem.getInstance().playBgm(23);
            }
            break;
         case 2:
            if (this.knuckdrawer.checkEnd()) {
               this.changeAniState(this.knuckdrawer, 1, true);
               this.COLLISION_WIDTH = 1024;
               this.COLLISION_HEIGHT = 1664;
               this.talk_cnt = 1;
            }

            if (this.talk_cnt >= 1) {
               ++this.talk_cnt;
               if (this.talk_cnt == 9) {
                  player.setMeetingBoss(true);
                  player.setOutOfControl(this);
                  PlayerObject var10 = player;
                  PlayerObject var7 = player;
                  var10.setAnimationId(53);
               } else if (this.talk_cnt >= 19 && player.getAnimationId() == 0) {
                  this.state = 4;
                  player.releaseOutOfControl();
               }
            }
            break;
         case 3:
            this.state = this.randomSetState();
            break;
         case 4:
            var1 = this.posX;
            var5 = this.posY;
            var4 = this.fight_alert_range;
            this.alert_state = this.checkPlayerInEnemyAlertRange(var1 >> 6, var5 >> 6, var4 * 2);
            if (this.IsNeedforDefence(this.alert_state)) {
               this.changeAniState(this.knuckdrawer, 13, false);
               this.COLLISION_WIDTH = 1024;
               this.COLLISION_HEIGHT = 1024;
               this.state = 16;
               soundInstance.playSe(24);
               this.IsPlayerRunaway = false;
               this.defence_cnt = 0;
            } else if (this.ready_cnt < 24) {
               ++this.ready_cnt;
            } else {
               this.ready_cnt = 0;
               this.state = 3;
               this.prestate = 4;
            }
            break;
         case 5:
            if (this.horizonAttackReady_cnt < this.horizonAttackReady_cnt_max) {
               ++this.horizonAttackReady_cnt;
            } else {
               if (this.IsHurt) {
                  var8 = 8;
               } else {
                  var8 = 3;
               }

               this.changeAniState(this.knuckdrawer, var8, true);
               this.COLLISION_WIDTH = 1536;
               this.COLLISION_HEIGHT = 1536;
               this.horizonAttackReady_cnt = 0;
               this.state = 6;
               soundInstance.playSe(5);
            }
            break;
         case 6:
            this.IsHurt = false;
            if (this.AttackStartDirection > 0) {
               if (this.posX <= this.limitLeftX) {
                  this.posX = this.limitLeftX;
                  this.IsConner = true;
               } else {
                  this.posX += this.horizon_move_speed;
               }
            } else if (this.posX >= this.limitRightX) {
               this.posX = this.limitRightX;
               this.IsConner = true;
            } else {
               this.posX += this.horizon_move_speed;
            }

            if (this.IsConner) {
               this.horizonAttackReady_cnt = 0;
               this.IsConner = false;
               this.state = 3;
               this.prestate = 6;
            }
            break;
         case 7:
            if (this.posY <= this.fly_top) {
               this.posY = this.fly_top;
               this.changeAniState(this.knuckdrawer, 16, true);
               this.AttackStartDirection = this.posX - player.getFootPositionX();
               this.COLLISION_WIDTH = 960;
               this.COLLISION_HEIGHT = 1472;
               this.state = 8;
            } else {
               this.posY -= this.fly_up_speed1;
            }
            break;
         case 8:
            this.IsHurt = false;
            if (this.AttackStartDirection > 0) {
               if (player.getFootPositionX() - this.fly_drip_offset > this.limitLeftX) {
                  this.fly_attack_site = player.getFootPositionX() - this.fly_drip_offset;
               } else {
                  this.fly_attack_site = this.limitLeftX;
               }

               if (this.posX + this.fly_move_x_speed1 <= this.fly_attack_site) {
                  this.posX = this.fly_attack_site;
                  this.changeAniStateNoTrans(this.knuckdrawer, 10, false);
                  this.COLLISION_WIDTH = 1920;
                  this.COLLISION_HEIGHT = 2432;
                  this.state = 9;
               } else {
                  this.posX += this.fly_move_x_speed1;
                  this.posY += this.fly_move_y_speed1;
               }
            } else {
               if (player.getFootPositionX() + this.fly_drip_offset < this.limitRightX) {
                  this.fly_attack_site = player.getFootPositionX() + this.fly_drip_offset;
               } else {
                  this.fly_attack_site = this.limitRightX;
               }

               if (this.posX + this.fly_move_x_speed1 >= this.fly_attack_site) {
                  this.posX = this.fly_attack_site;
                  this.changeAniStateNoTrans(this.knuckdrawer, 10, false);
                  this.COLLISION_WIDTH = 1408;
                  this.COLLISION_HEIGHT = 2432;
                  this.state = 9;
               } else {
                  this.posX += this.fly_move_x_speed1;
                  this.posY += this.fly_move_y_speed1;
               }
            }
            break;
         case 9:
            if (this.knuckdrawer.checkEnd()) {
               this.changeAniStateNoTrans(this.knuckdrawer, 11, true);
               this.COLLISION_WIDTH = 1536;
               this.COLLISION_HEIGHT = 2432;
               this.state = 10;
               this.velY = 0;
            }
            break;
         case 10:
            if (this.posY + this.velY >= this.getGroundY(this.posX, this.posY)) {
               this.posY = this.getGroundY(this.posX, this.posY);
               this.changeAniStateNoTrans(this.knuckdrawer, 12, false);
               this.COLLISION_WIDTH = 1408;
               this.COLLISION_HEIGHT = 1920;
               this.state = 11;
            } else {
               this.velY += GRAVITY;
               this.posY += this.velY;
            }
            break;
         case 11:
            if (this.knuckdrawer.checkEnd()) {
               this.state = 3;
               this.prestate = 11;
            }
            break;
         case 12:
            if (this.knuckdrawer.checkEnd()) {
               this.state = 3;
               this.prestate = 12;
            } else {
               if (this.knuckdrawer.getCurrentFrame() != 3 && this.knuckdrawer.getCurrentFrame() != 4 && this.knuckdrawer.getCurrentFrame() != 5 && this.knuckdrawer.getCurrentFrame() != 6 && this.knuckdrawer.getCurrentFrame() != 11 && this.knuckdrawer.getCurrentFrame() != 12) {
                  this.COLLISION_WIDTH = 1536;
                  this.COLLISION_HEIGHT = 1920;
               } else {
                  this.COLLISION_WIDTH = 3584;
                  this.COLLISION_HEIGHT = 2176;
               }

               if (this.knuckdrawer.getCurrentFrame() == 1 || this.knuckdrawer.getCurrentFrame() == 5) {
                  soundInstance.playSe(19);
               }
            }
            break;
         case 13:
            this.flydefence.setHurtState(false);
            if (this.knuckdrawer.checkEnd()) {
               this.changeAniStateNoTrans(this.knuckdrawer, 6, true);
               this.state = 14;
            } else {
               this.hurt_air_control();
               if (isBossHalf) {
                  ++damageframe;
                  damageframe %= 11;
                  if (damageframe % 2 == 0) {
                     SoundSystem.getInstance().playSe(35);
                  }
               }
            }
            break;
         case 14:
            this.flydefence.setHurtState(false);
            if (this.posY + this.velY >= this.getGroundY(this.posX, this.posY)) {
               this.posY = this.getGroundY(this.posX, this.posY);
               this.changeAniStateNoTrans(this.knuckdrawer, 7, false);
               this.state = 15;
            } else {
               this.hurt_air_control();
               if (isBossHalf) {
                  ++damageframe;
                  damageframe %= 11;
                  if (damageframe % 2 == 0) {
                     SoundSystem.getInstance().playSe(35);
                  }
               }
            }
            break;
         case 15:
            this.flydefence.setHurtState(false);
            if (this.knuckdrawer.checkEnd()) {
               if (this.HP == this.halfLifeNum()) {
                  this.state = 20;
                  this.prestate = 32;
               } else {
                  this.state = 3;
                  this.prestate = 15;
               }
            }
            break;
         case 16:
            if (this.knuckdrawer.checkEnd()) {
               this.changeAniState(this.knuckdrawer, 14, true);
               this.COLLISION_WIDTH = 1024;
               this.COLLISION_HEIGHT = 1024;
               this.state = 17;
            }
            break;
         case 17:
            if (this.IsPlayerRunaway || !player.isAttackingEnemy() || !player.isOnGound()) {
               this.changeAniState(this.knuckdrawer, 15, false);
               this.COLLISION_WIDTH = 1024;
               this.COLLISION_HEIGHT = 1024;
               this.state = 18;
            }
            break;
         case 18:
            if (this.knuckdrawer.checkEnd()) {
               this.state = 3;
               this.prestate = 18;
               this.IsPlayerRunaway = false;
            }
            break;
         case 19:
            var5 = this.posX;
            var1 = this.posY;
            var4 = this.fight_alert_range;
            this.alert_state = this.checkPlayerInEnemyAlertRange(var5 >> 6, var1 >> 6, var4 * 2);
            if (this.IsNeedforDefence(this.alert_state)) {
               this.changeAniState(this.knuckdrawer, 31, false);
               this.COLLISION_WIDTH = 1024;
               this.COLLISION_HEIGHT = 1024;
               this.state = 33;
               this.IsPlayerRunaway = false;
               this.defence_cnt = 0;
            } else if (this.ready_cnt < 24) {
               ++this.ready_cnt;
            } else {
               this.ready_cnt = 0;
               this.state = 20;
               this.prestate = 19;
            }
            break;
         case 20:
            this.state = this.randomSetState();
            break;
         case 21:
            if (this.horizonAttackReady_cnt < this.horizonAttackReady_cnt_max) {
               ++this.horizonAttackReady_cnt;
            } else {
               if (this.IsHurt) {
                  var8 = 26;
               } else {
                  var8 = 21;
               }

               this.changeAniState(this.knuckdrawer, var8, true);
               this.COLLISION_WIDTH = 1536;
               this.COLLISION_HEIGHT = 1536;
               this.horizonAttackReady_cnt = 0;
               this.state = 22;
               soundInstance.playSe(5);
            }
            break;
         case 22:
            this.IsHurt = false;
            if (this.AttackStartDirection > 0) {
               if (this.posX <= this.limitLeftX) {
                  this.posX = this.limitLeftX;
                  this.IsConner = true;
               } else {
                  this.posX += this.horizon_move_speed;
               }
            } else if (this.posX >= this.limitRightX) {
               this.posX = this.limitRightX;
               this.IsConner = true;
            } else {
               this.posX += this.horizon_move_speed;
            }

            if (this.IsConner) {
               this.horizonAttackReady_cnt = 0;
               this.IsConner = false;
               this.state = 20;
               this.prestate = 22;
            }
            break;
         case 23:
            if (this.posY <= this.fly_top) {
               this.posY = this.fly_top;
               this.changeAniState(this.knuckdrawer, 34, true);
               this.AttackStartDirection = this.posX - player.getFootPositionX();
               this.COLLISION_WIDTH = 960;
               this.COLLISION_HEIGHT = 1472;
               this.state = 24;
            } else {
               this.posY -= Math.abs(this.velocity);
            }
            break;
         case 24:
            this.IsHurt = false;
            if (this.AttackStartDirection > 0) {
               if (player.getFootPositionX() - this.fly_drip_offset > this.limitLeftX) {
                  this.fly_attack_site = player.getFootPositionX() - this.fly_drip_offset;
               } else {
                  this.fly_attack_site = this.limitLeftX;
               }

               if (this.posX + this.fly_move_x_speed1 <= this.fly_attack_site) {
                  this.posX = this.fly_attack_site;
                  this.changeAniStateNoTrans(this.knuckdrawer, 28, false);
                  this.COLLISION_WIDTH = 1920;
                  this.COLLISION_HEIGHT = 2432;
                  this.state = 25;
               } else {
                  this.posX += this.fly_move_x_speed1;
                  this.posY += this.fly_move_y_speed1;
               }
            } else {
               if (player.getFootPositionX() + this.fly_drip_offset < this.limitRightX) {
                  this.fly_attack_site = player.getFootPositionX() + this.fly_drip_offset;
               } else {
                  this.fly_attack_site = this.limitRightX;
               }

               if (this.posX + this.fly_move_x_speed1 >= this.fly_attack_site) {
                  this.posX = this.fly_attack_site;
                  this.changeAniStateNoTrans(this.knuckdrawer, 28, false);
                  this.COLLISION_WIDTH = 1408;
                  this.COLLISION_HEIGHT = 2432;
                  this.state = 25;
               } else {
                  this.posX += this.fly_move_x_speed1;
                  this.posY += this.fly_move_y_speed1;
               }
            }
            break;
         case 25:
            if (this.knuckdrawer.checkEnd()) {
               this.changeAniStateNoTrans(this.knuckdrawer, 29, true);
               this.COLLISION_WIDTH = 1536;
               this.COLLISION_HEIGHT = 2432;
               this.state = 26;
               this.velY = 0;
            }
            break;
         case 26:
            if (this.posY + this.velY >= this.getGroundY(this.posX, this.posY)) {
               this.posY = this.getGroundY(this.posX, this.posY);
               this.changeAniStateNoTrans(this.knuckdrawer, 30, false);
               this.COLLISION_WIDTH = 1408;
               this.COLLISION_HEIGHT = 1920;
               this.state = 27;
            } else {
               this.velY += GRAVITY;
               this.posY += this.velY;
            }
            break;
         case 27:
            if (this.knuckdrawer.checkEnd()) {
               this.state = 20;
               this.prestate = 27;
            }
            break;
         case 28:
            if (this.knuckdrawer.checkEnd()) {
               this.changeAniState(this.knuckdrawer, 37, false);
               this.COLLISION_WIDTH = 1408;
               this.COLLISION_HEIGHT = 2176;
               this.state = 29;
               if (this.posX - player.getFootPositionX() > 0) {
                  var1 = this.posX;
                  var4 = this.posY;
                  BulletObject.addBullet(16, var1 - 1280, var4 - 1280, -320, 0);
               } else {
                  var4 = this.posX;
                  var1 = this.posY;
                  BulletObject.addBullet(16, var4 + 1280, var1 - 1280, 320, 0);
               }

               MapManager.setShake(10);
            }
            break;
         case 29:
            this.IsHurt = false;
            if (this.knuckdrawer.checkEnd()) {
               this.state = 20;
               this.prestate = 29;
            }
            break;
         case 30:
            this.flydefence.setHurtState(false);
            if (this.knuckdrawer.checkEnd()) {
               this.changeAniStateNoTrans(this.knuckdrawer, 24, true);
               this.state = 31;
            } else {
               this.hurt_air_control();
            }
            break;
         case 31:
            this.flydefence.setHurtState(false);
            if (this.posY + this.velY >= this.getGroundY(this.posX, this.posY)) {
               this.posY = this.getGroundY(this.posX, this.posY);
               this.changeAniStateNoTrans(this.knuckdrawer, 25, false);
               this.state = 32;
            } else {
               this.hurt_air_control();
            }
            break;
         case 32:
            this.flydefence.setHurtState(false);
            if (this.knuckdrawer.checkEnd()) {
               this.state = 20;
               this.prestate = 32;
            }
            break;
         case 33:
            if (this.knuckdrawer.checkEnd()) {
               this.changeAniState(this.knuckdrawer, 32, true);
               this.COLLISION_WIDTH = 1024;
               this.COLLISION_HEIGHT = 1024;
               this.state = 34;
            }
            break;
         case 34:
            if (this.IsPlayerRunaway || !player.isAttackingEnemy() || !player.isOnGound()) {
               this.changeAniState(this.knuckdrawer, 33, false);
               this.COLLISION_WIDTH = 1024;
               this.COLLISION_HEIGHT = 1024;
               this.state = 35;
            }
            break;
         case 35:
            if (this.knuckdrawer.checkEnd()) {
               this.changeAniState(this.knuckdrawer, 19, false);
               this.COLLISION_WIDTH = 1024;
               this.COLLISION_HEIGHT = 1664;
               this.state = 20;
               this.prestate = 35;
               this.IsPlayerRunaway = false;
            }
            break;
         case 36:
            if (this.posY + this.velY >= this.getGroundY(this.posX, this.posY)) {
               this.posY = this.getGroundY(this.posX, this.posY);
               this.changeAniState(this.knuckdrawer, 40, true);
               this.state = 37;
            } else {
               this.hurt_air_control();
            }
            break;
         case 37:
            if (this.KOWaitCnt < this.KOWaitCntMax) {
               ++this.KOWaitCnt;
            } else {
               this.state = 38;
               this.bossbroken = new BossBroken(26, this.posX >> 6, this.posY >> 6, 0, 0, 0, 0);
               addGameObject(this.bossbroken, this.posX >> 6, this.posY >> 6);
               this.bossbroken.setTotalCntMax(6);
               this.bossbroken.setJumpTime(9);
            }
            break;
         case 38:
            this.bossbroken.logicBoom(this.posX, this.posY);
            if (!this.bossbroken.getEndState()) {
               break;
            }

            this.state = 39;
            this.pos = new int[6][4];
            var1 = 0;

            while(true) {
               if (var1 >= this.pos.length) {
                  break label363;
               }

               this.pos[var1][0] = this.posX;
               this.pos[var1][1] = this.posY - this.boom_offset;
               this.pos[var1][2] = this.Vix[MyRandom.nextInt(this.Vix.length)];
               this.pos[var1][3] = this.Viy[MyRandom.nextInt(this.Viy.length)];
               ++var1;
            }
         case 39:
            for(var1 = 0; var1 < this.pos.length; ++var1) {
               int[] var9 = this.pos[var1];
               var9[0] += this.pos[var1][2];
               var9 = this.pos[var1];
               var9[3] += GRAVITY >> 1;
               var9 = this.pos[var1];
               var9[1] += this.pos[var1][3];
               if (this.pos[var1][1] >= this.posY) {
                  ++this.pieces_drip_cnt;
               }
            }

            if (this.pieces_drip_cnt >= this.pos.length) {
               this.posX = 609792;
               this.posY = 69120;
               this.fly_end = 607744;
               this.escapefacedrawer.setActionId(0);
               this.escapefacedrawer.setLoop(true);
               this.WaitCnt = 0;
               this.state = 40;
               MapManager.setCameraLeftLimit(9360);
               MapManager.setCameraRightLimit(EGG_SIDE_RIGHT);
               bossFighting = false;
               player.getBossScore();
               SoundSystem.getInstance().playBgm(StageManager.getBgmId(), true);
            }
            break;
         case 40:
            var4 = this.posX;
            var1 = MapManager.getCamera().x;
            if (var4 <= var1 + MapManager.CAMERA_WIDTH - 30 << 6 && this.WaitCnt == 0) {
               this.escapefacedrawer.setActionId(2);
               this.escapefacedrawer.setLoop(false);
               this.WaitCnt = 1;
               var1 = MapManager.getCamera().x;
               var4 = MapManager.CAMERA_WIDTH;
               MapManager.setCameraLeftLimit(var1);
               MapManager.setCameraRightLimit(var1 + var4);
            }

            if (this.escapefacedrawer.checkEnd() && this.WaitCnt == 1) {
               this.escapefacedrawer.setActionId(0);
               this.boatdrawer.setActionId(1);
               this.boatdrawer.setLoop(false);
               this.WaitCnt = 2;
            }

            if (this.WaitCnt == 2 && this.boatdrawer.checkEnd()) {
               this.escapefacedrawer.setActionId(0);
               this.escapefacedrawer.setTrans(2);
               this.escapefacedrawer.setLoop(true);
               this.boatdrawer.setActionId(1);
               this.boatdrawer.setTrans(2);
               this.boatdrawer.setLoop(false);
               this.WaitCnt = 3;
            }

            if (this.WaitCnt == 3 && this.boatdrawer.checkEnd()) {
               this.boatdrawer.setActionId(0);
               this.boatdrawer.setTrans(2);
               this.boatdrawer.setLoop(true);
               this.WaitCnt = 4;
            }

            if (this.WaitCnt == 4 || this.WaitCnt == 5) {
               this.posX += this.escape_v;
            }

            if (this.posX - this.fly_end > this.fly_range && this.WaitCnt == 4) {
               this.WaitCnt = 5;
            }

            if (this.WaitCnt == 5) {
               if (this.escape_cnt < this.escape_cnt_max) {
                  ++this.escape_cnt;
               } else {
                  this.WaitCnt = 6;
               }
            }

            if (this.WaitCnt == 6) {
               var1 = MapManager.getCamera().x;
               var4 = MapManager.CAMERA_WIDTH;
               Cage var6 = new Cage(var1 + (var4 >> 1) << 6, 65280);
               addGameObject(var6);
               this.WaitCnt = 7;
            }

            this.checkWithPlayer(var3, var2, this.posX, this.posY);
         }

         this.flydefence.logic(this.posX, this.posY, this.AttackStartDirection);
         if (this.state != 8 && this.state != 24) {
            this.flydefence.setCollAvailable(false);
         } else {
            this.flydefence.setCollAvailable(true);
            if (this.flydefence.getHurtState()) {
               this.HurtLogic();
               this.flydefence.setHurtState(false);
            }
         }

         this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
         this.checkWithPlayer(var3, var2, this.posX, this.posY);
      }

   }

   public int randomSetState() {
      byte var1 = 0;
      this.missile_alert_state = this.checkPlayerInEnemyAlertRange(this.posX >> 6, this.posY >> 6, this.missile_alert_range);
      if (this.first_jump_cnt < 3) {
         ++this.first_jump_cnt;
         if (this.CanFreshFight()) {
            var1 = 12;
         } else {
            var1 = 7;
            soundInstance.playSe(11);
         }
      } else {
         int var2;
         switch(this.prestate) {
         case 4:
            if (this.CanFreshFight()) {
               var1 = 12;
            } else {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 45) {
                  var1 = 7;
                  soundInstance.playSe(11);
               } else if (var2 >= 45 && var2 < 55) {
                  var1 = 4;
               } else if (var2 >= 55) {
                  var1 = 5;
                  soundInstance.playSe(4);
               }
            }
            break;
         case 6:
            if (this.CanFreshFight()) {
               var1 = 12;
            } else {
               var1 = 4;
            }
            break;
         case 11:
         case 12:
            if (this.CanFreshFight()) {
               var1 = 12;
            } else {
               var2 = MyRandom.nextInt(0, 15);
               if (var2 >= 0 && var2 < 5) {
                  var1 = 7;
                  soundInstance.playSe(11);
               } else if (var2 >= 5 && var2 < 10) {
                  var1 = 4;
               } else if (var2 >= 10) {
                  var1 = 5;
                  soundInstance.playSe(4);
               }
            }
            break;
         case 15:
            if (this.CanFreshFight()) {
               var1 = 12;
            } else {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 50) {
                  var1 = 7;
                  soundInstance.playSe(11);
               } else if (var2 >= 50) {
                  var1 = 5;
                  soundInstance.playSe(4);
               }
            }
            break;
         case 18:
            if (this.CanFreshFight()) {
               var1 = 12;
            } else {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 25) {
                  var1 = 7;
                  soundInstance.playSe(11);
               } else if (var2 >= 25 && var2 < 30) {
                  var1 = 4;
               } else if (var2 >= 30) {
                  var1 = 5;
                  soundInstance.playSe(4);
               }
            }
            break;
         case 19:
            if (this.missile_alert_state == 0) {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 45) {
                  var1 = 23;
                  soundInstance.playSe(11);
               } else if (var2 >= 45 && var2 < 55) {
                  var1 = 19;
               } else if (var2 >= 55) {
                  var1 = 21;
                  soundInstance.playSe(4);
               }
            } else {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 12) {
                  var1 = 23;
                  soundInstance.playSe(11);
               } else if (var2 >= 12 && var2 < 25) {
                  var1 = 21;
                  soundInstance.playSe(4);
               } else if (var2 >= 25) {
                  var1 = 28;
               }
            }
            break;
         case 22:
            var1 = 19;
            break;
         case 27:
            if (this.missile_alert_state == 0) {
               var2 = MyRandom.nextInt(0, 15);
               if (var2 >= 0 && var2 < 5) {
                  var1 = 23;
                  soundInstance.playSe(11);
               } else if (var2 >= 5 && var2 < 10) {
                  var1 = 19;
               } else if (var2 >= 10) {
                  var1 = 21;
                  soundInstance.playSe(4);
               }
            } else {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 15) {
                  var1 = 23;
                  soundInstance.playSe(11);
               } else if (var2 >= 15 && var2 < 17) {
                  var1 = 19;
               } else if (var2 >= 17 && var2 < 25) {
                  var1 = 21;
                  soundInstance.playSe(4);
               } else if (var2 >= 25) {
                  var1 = 28;
               }
            }
            break;
         case 29:
            if (this.missile_alert_state == 0) {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 45) {
                  var1 = 23;
                  soundInstance.playSe(11);
               } else if (var2 >= 45 && var2 < 55) {
                  var1 = 19;
               } else if (var2 >= 55) {
                  var1 = 21;
                  soundInstance.playSe(4);
               }
            } else {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 10) {
                  var1 = 23;
                  soundInstance.playSe(11);
               } else if (var2 >= 10 && var2 < 20) {
                  var1 = 21;
                  soundInstance.playSe(4);
               } else if (var2 >= 20) {
                  var1 = 28;
               }
            }
            break;
         case 32:
            if (this.missile_alert_state == 0) {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 50) {
                  var1 = 23;
                  soundInstance.playSe(11);
               } else if (var2 >= 50) {
                  var1 = 21;
                  soundInstance.playSe(4);
               }
            } else {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 25) {
                  var1 = 23;
                  soundInstance.playSe(11);
               } else if (var2 >= 25 && var2 < 50) {
                  var1 = 21;
                  soundInstance.playSe(4);
               } else if (var2 >= 50) {
                  var1 = 28;
               }
            }
            break;
         case 35:
            if (this.missile_alert_state == 0) {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 25) {
                  var1 = 23;
                  soundInstance.playSe(11);
               } else if (var2 >= 25 && var2 < 30) {
                  var1 = 19;
               } else if (var2 >= 30) {
                  var1 = 21;
                  soundInstance.playSe(4);
               }
            } else {
               var2 = MyRandom.nextInt(0, 100);
               if (var2 >= 0 && var2 < 12) {
                  var1 = 23;
                  soundInstance.playSe(11);
               } else if (var2 >= 12 && var2 < 25) {
                  var1 = 21;
                  soundInstance.playSe(4);
               } else if (var2 >= 25) {
                  var1 = 28;
               }
            }
         }
      }

      byte var3;
      switch(var1) {
      case 4:
         if (this.IsHurt) {
            var3 = 2;
         } else {
            var3 = 1;
         }

         this.changeAniState(this.knuckdrawer, var3, true);
         this.COLLISION_WIDTH = 1024;
         this.COLLISION_HEIGHT = 1664;
         break;
      case 5:
         this.AttackStartDirection = this.posX - MIDDLE_SCREEN;
         if (MIDDLE_SCREEN > this.posX) {
            if (this.horizon_move_speed < 0) {
               this.horizon_move_speed = -this.horizon_move_speed;
            }
         } else if (this.horizon_move_speed > 0) {
            this.horizon_move_speed = -this.horizon_move_speed;
         }

         if (this.IsHurt) {
            var3 = 9;
         } else {
            var3 = 4;
         }

         this.changeAniState(this.knuckdrawer, var3, true, this.AttackStartDirection);
         this.COLLISION_WIDTH = 1536;
         this.COLLISION_HEIGHT = 1536;
         break;
      case 7:
         if (this.IsHurt) {
            var3 = 8;
         } else {
            var3 = 3;
         }

         this.changeAniState(this.knuckdrawer, var3, true);
         this.COLLISION_WIDTH = 1536;
         this.COLLISION_HEIGHT = 1536;
         this.flydefence.setHurtState(false);
         break;
      case 12:
         this.changeAniState(this.knuckdrawer, 18, false);
         this.COLLISION_WIDTH = 1536;
         this.COLLISION_HEIGHT = 1920;
         break;
      case 19:
         if (this.IsHurt) {
            var3 = 20;
         } else {
            var3 = 19;
         }

         this.changeAniState(this.knuckdrawer, var3, true);
         this.COLLISION_WIDTH = 1024;
         this.COLLISION_HEIGHT = 1664;
         break;
      case 21:
         this.AttackStartDirection = this.posX - MIDDLE_SCREEN;
         if (MIDDLE_SCREEN > this.posX) {
            if (this.horizon_move_speed < 0) {
               this.horizon_move_speed = -this.horizon_move_speed;
            }
         } else if (this.horizon_move_speed > 0) {
            this.horizon_move_speed = -this.horizon_move_speed;
         }

         if (this.IsHurt) {
            var3 = 27;
         } else {
            var3 = 22;
         }

         this.changeAniState(this.knuckdrawer, var3, true, this.AttackStartDirection);
         this.COLLISION_WIDTH = 1536;
         this.COLLISION_HEIGHT = 1536;
         break;
      case 23:
         if (this.IsHurt) {
            var3 = 26;
         } else {
            var3 = 21;
         }

         this.changeAniState(this.knuckdrawer, var3, true);
         this.COLLISION_WIDTH = 1536;
         this.COLLISION_HEIGHT = 1536;
         this.flydefence.setHurtState(false);
         break;
      case 28:
         if (this.IsHurt) {
            var3 = 38;
         } else {
            var3 = 36;
         }

         this.changeAniState(this.knuckdrawer, var3, false);
         this.COLLISION_WIDTH = 1024;
         this.COLLISION_HEIGHT = 1664;
      }

      return var1;
   }

   public void refreshCollisionRect(int var1, int var2) {
      int var3;
      int var4;
      int var5;
      CollisionRect var7;
      if (this.state != 8 && this.state != 24) {
         var7 = this.collisionRect;
         var5 = this.COLLISION_WIDTH;
         var4 = this.COLLISION_HEIGHT;
         int var6 = this.COLLISION_WIDTH;
         var3 = this.COLLISION_HEIGHT;
         var7.setRect(var1 - (var5 >> 1), var2 - var4, var6, var3);
      } else if (this.AttackStartDirection > 0) {
         var7 = this.collisionRect;
         var4 = this.COLLISION_HEIGHT;
         var3 = this.COLLISION_WIDTH;
         var5 = this.COLLISION_HEIGHT;
         var7.setRect(var1 - 1408, var2 - var4, var3, var5);
      } else {
         var7 = this.collisionRect;
         var4 = this.COLLISION_HEIGHT;
         var5 = this.COLLISION_WIDTH;
         var3 = this.COLLISION_HEIGHT;
         var7.setRect(var1 + 448, var2 - var4, var5, var3);
      }

   }
}
