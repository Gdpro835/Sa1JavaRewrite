package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.SoundSystem;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.device.MFGraphics;

class Boss1Arm extends EnemyObject {
   private static Animation ArmAni;
   private static final int COLLISION_HEIGHT = 2176;
   private static final int COLLISION_WIDTH = 2176;
   private static final int DEGREE_MAX = 23680;
   private static final int DEGREE_MAX_2 = 10880;
   private static final int DEGREE_MIN = 10880;
   private static final int DEGREE_MIN_2 = 23680;
   private static final int STATE_ATTACK_1 = 1;
   private static final int STATE_ATTACK_2 = 3;
   private static final int STATE_ATTACK_3 = 4;
   private static final int STATE_BROKEN = 5;
   private static final int STATE_READY = 2;
   private static final int STATE_WAIT = 0;
   private boolean IsBreaking;
   private boolean IsTurn;
   private int Step2CenterX;
   private int Step2CenterY;
   private AnimationDrawer armdrawer;
   private int ball_size = 1536;
   private AnimationDrawer boomdrawer;
   private int con_size = 1152;
   private int degree = 0;
   private int dg_plus = 320;
   private int drop_cnt;
   private AnimationDrawer hammerdrawer;
   private int offsetY = 2112;
   private int plus = 1;
   private int[][] pos;
   private int[][] prepos;
   private int state;
   private int[] velX;
   private int velY;

   protected Boss1Arm(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.degree = 10560;
      if (ArmAni == null) {
         ArmAni = new Animation("/animation/boss1_arm");
      }

      this.armdrawer = ArmAni.getDrawer(0, true, 0);
      if (BoomAni == null) {
         BoomAni = new Animation("/animation/boom");
      }

      this.boomdrawer = BoomAni.getDrawer(0, true, 0);
      this.hammerdrawer = (new Animation("/animation/boss1_hammer")).getDrawer(0, false, 0);
      this.pos = new int[6][2];
      this.prepos = new int[6][2];
      this.IsBreaking = false;
      this.velX = new int[6];
      this.velX[0] = -450;
      this.velX[1] = -300;
      this.velX[2] = -150;
      this.velX[3] = 150;
      this.velX[4] = 300;
      this.velX[5] = 450;
      this.velY = -1200;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(ArmAni);
      Animation.closeAnimation(BoomAni);
      ArmAni = null;
      BoomAni = null;
   }

   public void close() {
      this.armdrawer = null;
      this.boomdrawer = null;
      this.pos = null;
      this.prepos = null;
      this.velX = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && var1 == player && this.state != 5) {
         player.beHurt();
      }

   }

   public void draw(MFGraphics var1) {
      if (this.dead) {
      }

   }

   public void drawArm(MFGraphics var1) {
      if (!this.dead) {
         for(int var2 = 0; var2 < this.pos.length; ++var2) {
            if (this.state == 5 && this.drop_cnt < 2) {
               this.drawInMap(var1, this.boomdrawer, this.prepos[var2][0], this.prepos[var2][1]);
            }

            if (var2 < this.pos.length - 1 && this.drop_cnt < 2) {
               this.drawInMap(var1, this.armdrawer, this.pos[var2][0], this.pos[var2][1]);
            }
         }

         if (this.drop_cnt < 2) {
            Graphics var3 = (Graphics)var1.getSystemGraphics();
            var3.save();
            var3.translate((float)((this.pos[5][0] >> 6) - camera.x), (float)((this.pos[5][1] >> 6) - camera.y));
            var3.rotate((float)(-this.degree >> 6));
            this.hammerdrawer.draw(var1, 0, 0);
            var3.restore();
         }

         this.drawCollisionRect(var1);
      }

   }

   public int getArmAngel() {
      return this.degree;
   }

   public int getArmState() {
      return this.state;
   }

   public boolean getTurnState() {
      return this.IsTurn;
   }

   public void logic() {
      if (this.dead) {
      }

   }

   public int[] logic(int var1, int var2, int var3, int var4) {
      int[] var7;
      if (this.dead) {
         var7 = this.pos[0];
      } else {
         int var5 = this.pos[5][0];
         int var6 = this.pos[5][1];
         switch(var3) {
         case 1:
            if (this.plus > 0) {
               this.degree += this.plus * this.dg_plus;
               if (this.degree >= 23680) {
                  this.degree = 23680;
                  this.plus = -this.plus;
                  MapManager.setShake(8);
                  SoundSystem.getInstance().playSe(36);
               }
            } else {
               this.degree += this.plus * this.dg_plus;
               if (this.degree <= 10880) {
                  this.degree = 10880;
                  this.plus = -this.plus;
                  MapManager.setShake(8);
                  SoundSystem.getInstance().playSe(36);
               }
            }

            for(var4 = 0; var4 < this.pos.length - 1; ++var4) {
               this.pos[var4][0] = var1 - this.con_size * var4 * MyAPI.dCos(this.degree >> 6) / 100;
               this.pos[var4][1] = this.con_size * var4 * MyAPI.dSin(this.degree >> 6) / 100 + var2;
            }

            this.pos[5][0] = var1 - (this.con_size * 4 + this.ball_size) * MyAPI.dCos(this.degree >> 6) / 100;
            this.pos[5][1] = (this.con_size * 4 + this.ball_size) * MyAPI.dSin(this.degree >> 6) / 100 + var2;
            this.checkWithPlayer(var5, var6, this.pos[5][0], this.pos[5][1]);
         case 2:
         default:
            break;
         case 3:
            this.dg_plus = 1137;
            if (var4 > 0) {
               if (this.plus > 0) {
                  if (this.degree <= 10880) {
                     this.degree = 10880;
                     this.plus = -this.plus;
                  } else {
                     this.degree += this.plus * this.dg_plus;
                  }
               } else if (this.degree + this.plus * this.dg_plus <= 23680) {
                  this.degree = 23680;
                  this.state = 4;
                  this.IsTurn = false;
               } else {
                  this.degree += this.plus * this.dg_plus;
               }
            } else if (this.plus > 0) {
               if (this.degree + this.plus * this.dg_plus >= 10880) {
                  this.degree = 10880;
                  this.state = 4;
                  this.IsTurn = false;
               } else {
                  this.degree += this.plus * this.dg_plus;
               }
            } else if (this.degree >= 23680) {
               this.degree = 23680;
               this.plus = -this.plus;
            } else {
               this.degree += this.plus * this.dg_plus;
            }

            this.pos[5][0] = this.Step2CenterX;
            this.pos[5][1] = this.Step2CenterY;

            for(var1 = 1; var1 < this.pos.length; ++var1) {
               this.pos[5 - var1][0] = this.Step2CenterX + (this.con_size * (var1 - 1) + this.ball_size) * MyAPI.dCos(this.degree >> 6) / 100;
               this.pos[5 - var1][1] = this.Step2CenterY - (this.con_size * (var1 - 1) + this.ball_size) * MyAPI.dSin(this.degree >> 6) / 100;
            }

            this.checkWithPlayer(var5, var6, this.pos[5][0], this.pos[5][1]);
            break;
         case 4:
            this.dg_plus = 914;
            if (var4 > 0) {
               if (this.plus > 0) {
                  if (this.degree >= 23680) {
                     this.degree = 23680;
                     this.plus = -this.plus;
                  } else {
                     this.degree += this.plus * this.dg_plus;
                  }
               } else if (this.degree + this.plus * this.dg_plus <= 10880) {
                  this.degree = 10880;
                  this.state = 3;
                  this.IsTurn = false;
               } else {
                  this.degree += this.plus * this.dg_plus;
               }
            } else if (this.plus > 0) {
               if (this.degree + this.plus * this.dg_plus >= 23680) {
                  this.degree = 23680;
                  this.state = 3;
                  this.IsTurn = false;
               } else {
                  this.degree += this.plus * this.dg_plus;
               }
            } else if (this.degree <= 10880) {
               this.degree = 10880;
               this.plus = -this.plus;
            } else {
               this.degree += this.plus * this.dg_plus;
            }

            for(var4 = 0; var4 < this.pos.length - 1; ++var4) {
               this.pos[var4][0] = var1 - this.con_size * var4 * MyAPI.dCos(this.degree >> 6) / 100;
               this.pos[var4][1] = this.con_size * var4 * MyAPI.dSin(this.degree >> 6) / 100 + var2;
            }

            this.pos[5][0] = var1 - (this.con_size * 4 + this.ball_size) * MyAPI.dCos(this.degree >> 6) / 100;
            this.pos[5][1] = (this.con_size * 4 + this.ball_size) * MyAPI.dSin(this.degree >> 6) / 100 + var2;
            if (this.state == 3) {
               this.Step2CenterX = this.pos[5][0];
               this.Step2CenterY = this.pos[5][1];
               if (this.degree == 23680) {
                  this.degree -= 23040;
               }

               if (this.degree == 10880) {
                  this.degree += 23040;
               }

               MapManager.setShake(8);
               SoundSystem.getInstance().playSe(36);
            }

            this.checkWithPlayer(var5, var6, this.pos[5][0], this.pos[5][1]);
            break;
         case 5:
            this.state = 5;
            if (!this.IsBreaking) {
               if (this.pos[5][0] < this.pos[0][0]) {
                  for(var1 = 0; var1 < this.velX.length; ++var1) {
                     this.velX[var1] = -this.velX[var1];
                  }
               }

               this.IsBreaking = true;
            }

            for(var1 = 0; var1 < this.velX.length; ++var1) {
               var2 = this.con_size;
               if (var1 == 5) {
                  var2 = this.offsetY >> 1;
               } else {
                  var2 = this.con_size >> 1;
               }

               if (this.pos[var1][1] + var2 >= this.getGroundY(this.pos[var1][0], this.pos[var1][1])) {
                  this.pos[var1][1] = this.getGroundY(this.pos[var1][0], this.pos[var1][1]) - var2;
                  switch(this.drop_cnt) {
                  case 0:
                     this.velY = -900;
                     this.drop_cnt = 1;
                     break;
                  case 1:
                     this.velY = -600;
                     this.drop_cnt = 2;
                  }
               } else {
                  this.prepos[var1][0] = this.pos[var1][0];
                  this.prepos[var1][1] = this.pos[var1][1];
                  var7 = this.pos[var1];
                  var7[0] += this.velX[var1];
                  this.velY += GRAVITY >> 3;
                  var7 = this.pos[var1];
                  var7[1] += this.velY;
               }
            }

            this.checkWithPlayer(var5, var6, this.pos[5][0], this.pos[5][1]);
         }

         if (var3 == 3) {
            var7 = this.pos[0];
         } else {
            var7 = this.pos[5];
         }
      }

      return var7;
   }

   public void refreshCollisionRect(int var1, int var2) {
      if (this.pos == null) {
         this.pos = new int[6][2];
      }

      if (this.state != 5) {
         CollisionRect var3 = this.collisionRect;
         var1 = this.pos[5][0];
         var2 = this.pos[5][1];
         var3.setRect(var1 - 1088, var2 - 1088, 2176, 2176);
      }

   }

   public void setArmAngel(int var1) {
      this.degree = var1;
   }

   public void setArmState(int var1) {
      this.state = var1;
   }

   public void setDegreeSpeed(int var1) {
      this.dg_plus = var1;
   }

   public void setTurnState(boolean var1) {
      this.IsTurn = var1;
   }
}
