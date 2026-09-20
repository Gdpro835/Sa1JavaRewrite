package SonicGBA;

import GameEngine.time.GameTime;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import Lib.MyAPI;
import com.sega.engine.action.ACWorldCollisionCalculator;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import State.GameState;
import State.TitleState;

public class PlayerAmy extends PlayerObject {
   public static final int AMY_ANI_ATTACK_1 = 18;
   public static final int AMY_ANI_ATTACK_2 = 19;
   public static final int AMY_ANI_BANK_1 = 46;
   public static final int AMY_ANI_BANK_2 = 47;
   public static final int AMY_ANI_BANK_3 = 48;
   public static final int AMY_ANI_BAR_MOVE = 44;
   public static final int AMY_ANI_BAR_STAY = 43;
   public static final int AMY_ANI_BIG_JUMP = 13;
   public static final int AMY_ANI_BRAKE = 54;
   public static final int AMY_ANI_BREATHE = 53;
   public static final int AMY_ANI_CAUGHT = 58;
   public static final int AMY_ANI_CELEBRATE_1 = 49;
   public static final int AMY_ANI_CELEBRATE_2 = 50;
   public static final int AMY_ANI_CELEBRATE_3 = 51;
   public static final int AMY_ANI_CLIFF_1 = 33;
   public static final int AMY_ANI_CLIFF_2 = 34;
   public static final int AMY_ANI_CLIFF_3 = 35;
   public static final int AMY_ANI_CLIFF_4 = 36;
   public static final int AMY_ANI_DASH_1 = 4;
   public static final int AMY_ANI_DASH_2 = 5;
   public static final int AMY_ANI_DASH_3 = 6;
   public static final int AMY_ANI_DASH_4 = 7;
   public static final int AMY_ANI_DASH_5 = 8;
   public static final int AMY_ANI_DEAD_1 = 26;
   public static final int AMY_ANI_DEAD_2 = 27;
   public static final int AMY_ANI_ENTER_SP = 56;
   public static final int AMY_ANI_HEART_SYMBOL = 57;
   public static final int AMY_ANI_HURT_1 = 24;
   public static final int AMY_ANI_HURT_2 = 25;
   public static final int AMY_ANI_JUMP_ATTACK_1 = 20;
   public static final int AMY_ANI_JUMP_ATTACK_2 = 21;
   public static final int AMY_ANI_JUMP_ATTACK_3 = 22;
   public static final int AMY_ANI_LOOK_UP_1 = 9;
   public static final int AMY_ANI_LOOK_UP_2 = 10;
   public static final int AMY_ANI_POLE_H = 42;
   public static final int AMY_ANI_POLE_V = 41;
   public static final int AMY_ANI_PUSH_WALL = 28;
   public static final int AMY_ANI_RAIL_BODY = 55;
   public static final int AMY_ANI_ROLL = 39;
   public static final int AMY_ANI_ROLL_H_1 = 31;
   public static final int AMY_ANI_ROLL_H_2 = 32;
   public static final int AMY_ANI_ROLL_V_1 = 29;
   public static final int AMY_ANI_ROLL_V_2 = 30;
   public static final int AMY_ANI_RUN = 3;
   public static final int AMY_ANI_SLIP_D0 = 37;
   public static final int AMY_ANI_SLIP_D45 = 38;
   public static final int AMY_ANI_SPRING_1 = 23;
   public static final int AMY_ANI_SPRING_2 = 14;
   public static final int AMY_ANI_SPRING_3 = 15;
   public static final int AMY_ANI_SPRING_4 = 16;
   public static final int AMY_ANI_SPRING_5 = 17;
   public static final int AMY_ANI_SQUAT_1 = 11;
   public static final int AMY_ANI_SQUAT_2 = 12;
   public static final int AMY_ANI_STAND = 0;
   public static final int AMY_ANI_UP_ARM = 40;
   public static final int AMY_ANI_VS_KNUCKLE = 52;
   public static final int AMY_ANI_WAITING_1 = 59;
   public static final int AMY_ANI_WAITING_2 = 60;
   public static final int AMY_ANI_WALK_1 = 1;
   public static final int AMY_ANI_WALK_2 = 2;
   public static final int AMY_ANI_WIND = 45;
   private static final int[] ANIMATION_CONVERT;
   private static final int ATTACK_COUNT_MAX = 10;
   private static final int BIG_JUMP_INWATER_POWER = 1728;
   private static final int BIG_JUMP_POWER = 1536;
   private static final int[][][] HEART_SYMBOL_PARAM;
   private static final int LOOP = -1;
   private static final int[] LOOP_INDEX;
   private static final int NO_ANIMATION = -1;
   private static final int NO_LOOP = -2;
   private static final int NO_LOOP_DEPAND = -2;
   private static final int SLIDING_BRAKE = 64;
   private static final int STEP_JUMP_INWATER_X = 1504;
   private static final int STEP_JUMP_INWATER_Y = 388;
   private static final int STEP_JUMP_LIMIT_Y = -820;
   private static final int STEP_JUMP_X = 1024;
   private static final int STEP_JUMP_X_V0 = 912;
   private static final int STEP_JUMP_Y = 320;
   public static boolean isCanJump;
   private Animation amyAnimation;
   private AnimationDrawer amyDrawer1;
   private AnimationDrawer amyDrawer2;
   private boolean attack1Flag;
   private boolean attack2Flag;
   private int attackCount;
   private int attackLevel;
   private PlayerAnimationCollisionRect attackRect;
   private boolean cannotAttack = false;
   private boolean isinBigJumpAttack = false;
   private boolean jumpAttackUsed;
   public boolean skipBeStop;
   int slideframe;

   static {
      int[] var0 = new int[]{0, 1, 2, 3, 39, 12, -1, -1, 28, 23, 17, -1, 25, 41, 14, -1, -1, 54, -1, -1, -1, 55, 31, 32, 40, 29, 30, 43, 44, 45, 25, 42, 46, 47, 48, 49, 50, 51, 9, 10, 9, 27, 15, 16, 25, 26, 11, 34, 36, 53, 59, 60, 58, 52};
      ANIMATION_CONVERT = var0;
      var0 = new int[]{-1, -1, -1, -1, 5, -1, 7, -1, 0, 10, -1, -2, -1, 17, -1, 16, 17, -1, 0, 0, 17, 22, -1, 16, 25, -1, 27, -1, -1, 30, 29, 32, 31, 34, -1, 36, -1, -1, -1, -1, -1, 14, 3, -1, -1, -1, -1, -1, -1, 50, -1, -2, 0, -2, -1, -1, -1, -2, 60, 60, -1, 0};
      LOOP_INDEX = var0;
      int[] var3 = new int[1];
      int[] var4 = new int[1];
      var0 = new int[1];
      int[] var1 = new int[]{2, 0, -41, 13, -36};
      int[] var2 = new int[]{1, 26, -14};
      int[] var7 = new int[1];
      int[] var8 = new int[1];
      int[] var9 = new int[1];
      int[] var5 = new int[]{1, 20, -36};
      int[] var6 = new int[]{1, 30, -27};
      int[][] var13 = new int[][]{var7, var8, var9, {1, 7, -41}, var5, var6, {1, 33, -14}};
      var6 = new int[]{1, 22, -32};
      int[][] var14 = new int[][]{{1, -10, -41}, {1, 8, -43}, var6, {1, 28, -16}, {1, 23, 1}, {1, 10, 11}};
      var7 = new int[1];
      var8 = new int[]{1, 0, -1};
      var9 = new int[]{1, 19, -9};
      int[] var10 = new int[]{1, 28, -11};
      int[] var12 = new int[]{1, -28, -11};
      int[] var11 = new int[]{1, 0, -9};
      int[][] var15 = new int[][]{var10, var12, var11};
      HEART_SYMBOL_PARAM = new int[][][]{{var3, var4, var0, var1, {1, 23, -27}, var2}, var13, var14, {var7, var8, var9}, var15};
      isCanJump = false;
   }

   public PlayerAmy() {
      MFImage var1 = MFImage.createImage("/animation/player/chr_amy" + (TitleState.characterslots > 2 ? "_slot" + TitleState.characterslots : "") + ".png");
      this.amyAnimation = new Animation(var1, "/animation/player/chr_amy_01");
      this.amyDrawer1 = this.amyAnimation.getDrawer();
      this.drawer = this.amyDrawer1;
      Animation var2 = new Animation(var1, "/animation/player/chr_amy_02");
      this.amyDrawer2 = var2.getDrawer();
      this.attackRect = new PlayerAnimationCollisionRect(this);
      this.cannotAttack = false;
   }

   private void setMinSlipSpeed() {
      if (this.getVelX() < SPEED_LIMIT_LEVEL_1) {
         this.setVelX(SPEED_LIMIT_LEVEL_1);
      }

   }

   public boolean beAccelerate(int var1, boolean var2, GameObject var3) {
      if (this.myAnimationID != 6 && this.myAnimationID != 7) {
         var2 = super.beAccelerate(var1, var2, var3);
         if (this.animationID != 0 || this.animationID != 1 || this.animationID != 2 || this.animationID != 3) {
            this.animationID = 0;
         }

         if (Key.repeat(Key.gDown)) {
            this.animationID = 5;
         }
      } else {
         var2 = false;
      }

      return var2;
   }

   public void beSpring(int var1, int var2) {
      this.attackLevel = 0;
      this.attackCount = GameTime.set(this, "attackCount", 0);
      this.jumpAttackUsed = false;
      super.beSpring(var1, var2);
      if (this.myAnimationID == 6 || this.myAnimationID == 7) {
         if (this.animationID != 0 || this.animationID != 1 || this.animationID != 2 || this.animationID != 3) {
            this.animationID = 0;
         }

         if (Key.repeat(Key.gDown)) {
            this.animationID = 5;
         }
      }

   }

   public void closeImpl() {
      Animation.closeAnimationDrawer(this.amyDrawer1);
      this.amyDrawer1 = null;
      Animation.closeAnimation(this.amyAnimation);
      this.amyAnimation = null;
      Animation.closeAnimationDrawer(this.amyDrawer2);
      this.amyDrawer2 = null;
   }

   public void doHurt() {
      super.doHurt();
      if (this.slipping) {
         this.currentLayer = 1;
         this.slipping = false;
      }

   }

   public void doJump() {
      if (this.myAnimationID != 13 && this.myAnimationID != 7 && this.myAnimationID != 8) {
         this.jumpAttackUsed = false;
         if (this.slipping) {
            boolean var1 = this.isHeadCollision();
            if (var1) {
               return;
            }

            this.currentLayer = 1;
         }

         if (this.slipping && this.totalVelocity == 192) {
            super.doJumpV();
         } else {
            super.doJump();
         }

         if (this.slipping) {
            this.currentLayer = 1;
            this.slipping = false;
         }

         this.animationID = 14;
      }

   }

   public void doWhileLand(int var1) {
      super.doWhileLand(var1);
      this.jumpAttackUsed = false;
      if (this.myAnimationID == 7 || this.myAnimationID == 6) {
         this.myAnimationID = 7;
         this.animationID = -1;
         soundInstance.playSe(6);
      }

      this.isinBigJumpAttack = false;
      if (this.myAnimationID == 21 || this.myAnimationID == 22) {
         this.isAttacking = false;
      }

   }

   public void drawCharacter(MFGraphics var1) {
      Coordinate var13 = MapManager.getCamera();
      if (this.animationID != -1) {
         this.myAnimationID = ANIMATION_CONVERT[this.animationID];
      }

      if (this.myAnimationID != -1) {
         boolean var11;
         if (LOOP_INDEX[this.myAnimationID] == -1) {
            var11 = true;
         } else {
            var11 = false;
         }

         byte var2 = -1;
         switch(this.myAnimationID) {
         case 18:
            var2 = 0;
            this.isAttacking = true;
            break;
         case 19:
            var2 = 1;
            this.isAttacking = true;
            break;
         case 20:
            var2 = 2;
            this.checkBreatheReset();
            this.isAttacking = true;
            break;
         case 21:
            var2 = 3;
            this.isAttacking = true;
            break;
         case 22:
            var2 = 4;
            this.isAttacking = true;
         }

         int var3;
         byte var4;
         byte var5;
         int var6;
         int var7;
         if (var2 >= 0) {
            GameState.pauseAvailable = false;
            var6 = this.drawer.getCurrentFrame();
            if (var6 >= 0 && var6 < HEART_SYMBOL_PARAM[var2].length) {
               for(var3 = 0; var3 < HEART_SYMBOL_PARAM[var2][var6][0]; ++var3) {
                  Animation var14 = this.amyAnimation;
                  var7 = this.footPointX;
                  if (this.isAntiGravity ^ this.faceDirection) {
                     var4 = 1;
                  } else {
                     var4 = -1;
                  }

                  int var8 = HEART_SYMBOL_PARAM[var2][var6][var3 * 2 + 1];
                  int var9 = this.footPointY;
                  if (this.isAntiGravity) {
                     var5 = -1;
                  } else {
                     var5 = 1;
                  }

                  int var10 = HEART_SYMBOL_PARAM[var2][var6][var3 * 2 + 2];
                  Effect.showEffect(var14, 57, (var7 >> 6) + var4 * var8, (var9 >> 6) + var5 * var10, 0, 1);
               }
            }
         } else {
            GameState.pauseAvailable = true;
         }

         this.drawer = this.amyDrawer1;
         int var19 = this.myAnimationID;
         if (this.myAnimationID >= 59) {
            this.drawer = this.amyDrawer2;
            var3 = this.myAnimationID - 59;
            var19 = var3;
            if (this.myAnimationID == 59) {
               var19 = var3;
               if (this.isResetWaitAni) {
                  this.drawer.restart();
                  this.isResetWaitAni = false;
                  var19 = var3;
               }
            }
         }

         if (this.isInWater) {
            this.drawer.setSpeed(1, 2);
         } else {
            this.drawer.setSpeed(1, 1);
         }

         byte var22;
         if (this.hurtCount % 2 != 0) {
            if (var19 != this.drawer.getActionId()) {
               this.drawer.setActionId(var19);
            }

            if (!AnimationDrawer.isAllPause()) {
               this.drawer.moveOn();
            }
         } else {
            if (this.fallinSandSlipState != 0) {
               if (this.fallinSandSlipState == 1) {
                  this.faceDirection = true;
               } else if (this.fallinSandSlipState == 2) {
                  this.faceDirection = false;
               }
            }

            byte var18;
            if (this.faceDirection) {
               var18 = 0;
            } else {
               var18 = 2;
            }

            int var20;
            int var21;
            AnimationDrawer var26;
            if (this.animationID == 4) {
               var3 = this.getNewPointX(this.footPointX, 0, -512, this.faceDegree);
               var20 = this.getNewPointY(this.footPointY, 0, -512, this.faceDegree);
               var3 = this.getNewPointX(var3, 0, 512, 0);
               var6 = this.getNewPointY(var20, 0, 512, 0);
               var26 = this.drawer;
               var20 = var13.x;
               var21 = var13.y;
               var26.draw(var1, var19, (var3 >> 6) - var20, (var6 >> 6) - var21, var11, 0);
            } else if (this.animationID != 6 && this.animationID != 7) {
               switch(this.myAnimationID) {
               case 37:
                  var26 = this.drawer;
                  var3 = this.footPointX;
                  var6 = var13.x;
                  var21 = this.footPointY;
                  var20 = var13.y;
                  var26.draw(var1, var19, (var3 >> 6) - var6 + 0, (var21 >> 6) - var20 + 0, var11, 0);
                  break;
               case 38:
                  var26 = this.drawer;
                  var20 = this.footPointX;
                  var3 = var13.x;
                  var21 = this.footPointY;
                  var6 = var13.y;
                  var26.draw(var1, var19, (var20 >> 6) - var3 + 8, (var21 >> 6) - var6 + 0, var11, 0);
                  break;
               default:
                  if (this.myAnimationID == 22 && this.drawer.checkEnd()) {
                     soundInstance.playSe(23);
                  }

                  if (this.myAnimationID == 33 || this.myAnimationID == 34 || this.myAnimationID == 9 || this.myAnimationID == 10) {
                     this.degreeForDraw = this.degreeStable;
                     this.faceDegree = this.degreeStable;
                  }

                  if (this.myAnimationID >= 4 && this.myAnimationID <= 8) {
                     this.degreeForDraw = this.degreeStable;
                  }

                  if (this.myAnimationID == 54) {
                     this.degreeForDraw = this.degreeStable;
                  }

                  if (this.myAnimationID != 1 && this.myAnimationID != 2 && this.myAnimationID != 3 && this.myAnimationID != 58) {
                     this.degreeForDraw = this.degreeStable;
                  }

                  if (this.degreeForDraw != this.faceDegree) {
                     var6 = this.footPointX;
                     var20 = -this.collisionRect.getHeight();
                     var21 = this.faceDegree;
                     var20 = this.getNewPointX(var6, 0, var20 >> 1, var21);
                     var7 = this.footPointY;
                     var21 = -this.collisionRect.getHeight();
                     var6 = this.faceDegree;
                     var21 = this.getNewPointY(var7, 0, var21 >> 1, var6);
                     var1.saveCanvas();
                     var1.translateCanvas((var20 >> 6) - var13.x, (var21 >> 6) - var13.y);
                     var1.rotateCanvas((float)this.degreeForDraw);
                     this.drawer.draw(var1, var19, 0, this.collisionRect.getHeight() >> 1 >> 6, var11, var18);
                     var1.restoreCanvas();
                  } else {
                     var26 = this.drawer;
                     var21 = this.footPointX;
                     var3 = var13.x;
                     var20 = this.footPointY;
                     var6 = var13.y;
                     var7 = this.degreeForDraw;
                     boolean var12;
                     if (this.faceDirection) {
                        var12 = false;
                     } else {
                        var12 = true;
                     }

                     this.drawDrawerByDegree(var1, var26, var19, (var21 >> 6) - var3, (var20 >> 6) - var6, var11, var7, var12);
                  }
               }
            } else {
               var26 = this.drawer;
               var20 = this.footPointX;
               var21 = var13.x;
               var7 = this.footPointY;
               var6 = var13.y;
               var26.draw(var1, var19, (var20 >> 6) - var21, (var7 >> 6) - var6, var11, var18);
            }

            this.attackRectVec.removeAllElements();
            byte[] var27 = this.drawer.getARect();
            if (this.isAntiGravity) {
               byte[] var15 = this.drawer.getARect();
               if (var15 != null) {
                  var27[0] = (byte)(-var15[0] - var15[2]);
               }
            }

            if (var27 != null) {
               if (SonicDebug.showCollisionRect) {
                  var1.setColor(65280);
                  var21 = this.footPointX;
                  var4 = var27[0];
                  var3 = var13.x;
                  var6 = this.footPointY;
                  if (this.isAntiGravity) {
                     var19 = -var27[1] - var27[3];
                  } else {
                     var19 = var27[1];
                  }

                  var7 = var13.y;
                  byte var23 = var27[2];
                  byte var25 = var27[3];
                  var1.drawRect((var21 >> 6) + var4 - var3, (var6 >> 6) + var19 - var7, var23, var25);
               }

               PlayerAnimationCollisionRect var16 = this.attackRect;
               var5 = var27[0];
               var22 = var27[1];
               byte var24 = var27[2];
               var4 = var27[3];
               var19 = this.myAnimationID;
               var16.initCollision(var5 << 6, var22 << 6, var24 << 6, var4 << 6, var19);
               this.attackRectVec.addElement(this.attackRect);
            } else {
               this.attackRect.reset();
            }
         }

         if (this.animationID == -1 && this.drawer.checkEnd()) {
            switch(this.myAnimationID) {
            case 8:
               this.animationID = 0;
               break;
            case 18:
               if (this.attackLevel == 2) {
                  this.myAnimationID = 19;
                  this.attack2Flag = true;
                  ACWorldCollisionCalculator var17 = this.worldCal;
                  if (this.isAntiGravity ^ this.faceDirection) {
                     var2 = 1;
                  } else {
                     var2 = -1;
                  }

                  if (this.faceDirection) {
                     var22 = 1;
                  } else {
                     var22 = -1;
                  }

                  var17.moveVelocity(var2 * 768, 0, var22 * 768);
                  return;
               }

               this.animationID = 0;
               this.attackLevel = 0;
               this.isAttacking = false;
               break;
            case 19:
               this.animationID = 0;
               this.attackLevel = 0;
               this.isAttacking = false;
               break;
            case 20:
               this.animationID = 10;
               this.isAttacking = false;
            }

            if (LOOP_INDEX[this.myAnimationID] >= 0) {
               this.myAnimationID = LOOP_INDEX[this.myAnimationID];
            }
         }
      }

   }

   protected void extraLogicJump() {
      switch(this.myAnimationID) {
      case 4:
      case 5:
         if (Key.press(Key.gSelect | 8388608)) {
            this.myAnimationID = 6;
         } else if (this.velX != 0) {
            if (this.velX < 0) {
               this.velX = Math.min(this.velX, -912);
            } else {
               this.velX = Math.max(this.velX, 912);
            }
         }
         break;
      case 14:
      case 15:
      case 16:
      case 17:
      case 23:
         if (Key.press(Key.gSelect | 8388608) && !this.isinBigJumpAttack && !this.jumpAttackUsed) {
            this.jumpAttackUsed = true;
            if (Key.repeat(Key.gDown)) {
               this.animationID = -1;
               this.myAnimationID = 21;
            } else {
               this.animationID = -1;
               this.myAnimationID = 20;
               soundInstance.playSe(22);
            }
         }
      }

      if (this.animationID >= 1 && this.animationID <= 3 && Key.press(Key.gSelect | 8388608) && !this.jumpAttackUsed) {
         this.jumpAttackUsed = true;
         if (Key.repeat(Key.gDown)) {
            this.animationID = -1;
            this.myAnimationID = 21;
         } else {
            this.animationID = -1;
            this.myAnimationID = 20;
            soundInstance.playSe(22);
         }
      }

      if (this.myAnimationID == 23 || this.myAnimationID == 17) {
         this.skipBeStop = false;
      }

   }

   protected void extraLogicOnObject() {
      this.isinBigJumpAttack = false;
      if (this.myAnimationID == 18 && this.drawer.getCurrentFrame() == 4) {
         if (this.attack1Flag) {
            soundInstance.playSe(21);
            this.attack1Flag = false;
         }
      } else if (this.myAnimationID == 19 && this.drawer.getCurrentFrame() == 5 && this.attack2Flag) {
         soundInstance.playSe(21);
         this.attack2Flag = false;
      }

      if (this.attackCount > 0) {
         this.attackCount = Math.max(0, GameTime.advance(this, "attackCount", this.attackCount, -(1)));
         if (this.attackCount == 0) {
            this.attackLevel = 0;
         }
      }

      if (this.attackLevel > 0) {
         this.totalVelocity = 0;
      }

      if (this.attackLevel == 0) {
         isCanJump = true;
      } else {
         isCanJump = false;
      }

      if (this.myAnimationID == 7 && this.velX == 0 && this.velY == 0) {
         this.myAnimationID = 8;
      }

      if (Key.press(Key.gSelect | 8388608)) {
         if (this.animationID != -1 && !Key.repeat(Key.gDown) && this.collisionState != 1) {
            this.totalVelocity = 0;
            this.animationID = -1;
            this.myAnimationID = 18;
            this.attack1Flag = true;
            byte var1;
            if (this.isInWater) {
               var1 = 20;
            } else {
               var1 = 10;
            }

            this.attackCount = GameTime.set(this, "attackCount", var1);
            this.attackLevel = 1;
         } else if (this.attackCount > 0 && this.attackLevel < 2) {
            ++this.attackLevel;
         }
      }

   }

   protected void extraLogicWalk() {
      if (this.myAnimationID == 18 && this.drawer.getCurrentFrame() == 4) {
         if (this.attack1Flag) {
            soundInstance.playSe(21);
            this.attack1Flag = false;
         }
      } else if (this.myAnimationID == 19 && this.drawer.getCurrentFrame() == 5 && this.attack2Flag) {
         soundInstance.playSe(21);
         this.attack2Flag = false;
      }

      if (this.attackCount > 0) {
         this.attackCount = Math.max(0, GameTime.advance(this, "attackCount", this.attackCount, -(1)));
         if (this.attackCount == 0) {
            this.attackLevel = 0;
         }
      }

      if (this.attackLevel > 0) {
         this.totalVelocity = 0;
      }

      if (this.attackLevel == 0) {
         isCanJump = true;
      } else {
         isCanJump = false;
      }

      if (!this.cannotAttack && Key.press(Key.gSelect | 8388608) && this.myAnimationID != 28 && this.collisionState != 1) {
         if (this.animationID != -1 && !Key.repeat(Key.gDown)) {
            this.totalVelocity = 0;
            this.animationID = -1;
            this.myAnimationID = 18;
            this.attack1Flag = true;
            byte var1;
            if (this.isInWater) {
               var1 = 20;
            } else {
               var1 = 10;
            }

            this.attackCount = GameTime.set(this, "attackCount", var1);
            this.attackLevel = 1;
         } else if (this.attackCount > 0 && this.attackLevel < 2) {
            ++this.attackLevel;
         }
      }

      if (this.myAnimationID == 7 && this.totalVelocity == 0) {
         this.myAnimationID = 8;
      }

      if (this.slipping) {
         if (Key.repeat(Key.gLeft) && this.myAnimationID == 38) {
            this.totalVelocity = GameTime.advance(this, "totalVelocity", this.totalVelocity, -(30));
         } else if (Key.repeat(Key.gDown | Key.gRight) && this.faceDegree < 135) {
            this.totalVelocity = GameTime.advance(this, "totalVelocity", this.totalVelocity, MyAPI.dSin(this.faceDegree) * 150 / 100);
         }

         this.totalVelocity = GameTime.advance(this, "totalVelocity", this.totalVelocity, -(30));
         this.totalVelocity = Math.max(this.totalVelocity, 192);
         this.animationID = -1;
         this.faceDirection = true;
         if (this.faceDegree == 45) {
            this.myAnimationID = 38;
            if (this.slideSoundStart) {
               soundInstance.playSe(8);
               this.slideSoundStart = false;
            }
         } else {
            this.setMinSlipSpeed();
            this.myAnimationID = 37;
            if (this.slideSoundStart) {
               soundInstance.playSe(8);
               this.slideSoundStart = false;
            }
         }

         slidingFrame = GameTime.advanceOnce(PlayerObject.class, "slidingFrame", slidingFrame, 1);
         if (slidingFrame == 4) {
            soundInstance.playLoopSe(9);
         }
      }

   }

   public int getRetPower() {
      int var1;
      if (this.animationID == 5 && this.fallTime == 0) {
         var1 = MOVE_POWER_REVERSE;
      } else if (this.myAnimationID != 6 && this.myAnimationID != 7) {
         var1 = super.getRetPower();
      } else {
         Animation var3 = this.dustEffectAnimation;
         var1 = this.posX;
         int var2 = this.posY;
         Effect.showEffectPlayer(var3, 2, var1 >> 6, var2 >> 6, 0);
         var1 = 64;
      }

      return var1;
   }

   public int getSlopeGravity() {
      int var1;
      if (this.myAnimationID != 6 && this.myAnimationID != 7) {
         var1 = super.getSlopeGravity();
      } else {
         var1 = 0;
      }

      return var1;
   }

   public boolean isOnSlip0() {
      boolean var1;
      if (this.myAnimationID == 37) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean needRetPower() {
      boolean var1;
      if (this.myAnimationID != 6 && this.myAnimationID != 7) {
         var1 = false;
      } else {
         var1 = true;
      }

      boolean var2 = super.needRetPower();
      return var1 | var2;
   }

   public boolean noRotateDraw() {
      boolean var1;
      if (this.myAnimationID != 18 && this.myAnimationID != 19 && this.myAnimationID != 6 && this.myAnimationID != 7 && this.myAnimationID != 8 && !super.noRotateDraw()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void resetAttackLevel() {
      this.attackLevel = 0;
      this.attackCount = GameTime.set(this, "attackCount", 0);
   }

   public void setCannotAttack(boolean var1) {
      this.cannotAttack = var1;
   }

   public void setSlideAni() {
      this.animationID = -1;
      this.myAnimationID = 37;
   }

   public void setSlip0() {
      if (this.collisionState == 0) {
         this.animationID = -1;
         this.myAnimationID = 37;
      }

      this.setMinSlipSpeed();
   }

   public void slipEnd() {
      if (this.slipping) {
         this.currentLayer = 1;
         this.slipping = false;
         this.calDivideVelocity();
         this.collisionState = 1;
         this.worldCal.actionState = 1;
         this.velY = -1540;
         this.animationID = 9;
         this.collisionChkBreak = true;
         this.worldCal.stopMove();
         soundInstance.stopLoopSe();
         soundInstance.playSequenceSe(11);
      }

   }

   public void slipJumpOut() {
      if (this.slipping) {
         this.currentLayer = 1;
         this.slipping = false;
         this.calDivideVelocity();
         int var1;
         if (this.isInWater) {
            var1 = JUMP_INWATER_START_VELOCITY;
         } else {
            var1 = JUMP_START_VELOCITY;
         }

         this.setVelY(var1);
         this.collisionState = 1;
         this.worldCal.actionState = 1;
         this.collisionChkBreak = true;
         this.worldCal.stopMove();
      }

   }

   public void slipStart() {
      this.currentLayer = 0;
      this.slipping = true;
      this.slideSoundStart = true;
      this.collisionState = 1;
      this.worldCal.actionState = 1;
      this.setMinSlipSpeed();
   }

   protected boolean spinLogic() {
      boolean var4;
      if (!Key.repeat(Key.gLeft) && !Key.repeat(Key.gRight) && !isTerminal && this.animationID != -1) {
         if (Key.repeat(Key.gDown)) {
            if (this.animationID != 5 && this.animationID != 47 && this.animationID != 48) {
               this.animationID = 46;
            }

            short var1;
            if (Key.press(16777216) && (!this.isAntiGravity && (this.faceDegree < 90 || this.faceDegree > 270) || this.isAntiGravity && this.faceDegree > 90 && this.faceDegree < 270)) {
               this.animationID = -1;
               this.myAnimationID = 4;
               this.collisionState = 1;
               this.worldCal.actionState = 1;
               short var2;
               if (this.isInWater) {
                  var2 = 1504;
               } else {
                  var2 = 1024;
               }

               if (this.isInWater) {
                  var1 = 388;
               } else {
                  var1 = 320;
               }

               byte var3;
               if (this.faceDirection) {
                  var3 = 1;
               } else {
                  var3 = -1;
               }

               this.velX = var3 * var2 * MyAPI.dCos(this.faceDegree) / 100;
               if (this.faceDirection) {
                  var3 = 1;
               } else {
                  var3 = -1;
               }

               this.velY = var3 * var2 * MyAPI.dSin(this.faceDegree) / 100;
               if (this.velY < -820) {
                  this.velY = -820;
               }

               int var6 = this.velY;
               byte var5;
               if (this.isAntiGravity) {
                  var5 = -1;
               } else {
                  var5 = 1;
               }

               this.velY = var6 + var5 * (-var1 - this.getGravity());
               soundInstance.playSe(11);
               var4 = true;
               return var4;
            }

            if (Key.press(Key.gSelect | 8388608)) {
               this.animationID = -1;
               this.myAnimationID = 13;
               this.isinBigJumpAttack = true;
               this.collisionState = 1;
               this.worldCal.actionState = 1;
               if (this.isInWater) {
                  var1 = 1728;
               } else {
                  var1 = 1536;
               }

               this.velY -= var1 * MyAPI.dCos(this.faceDegree) / 100;
                  this.velY = GameTime.advance(this, "velY", this.velY, -this.getGravity() * MyAPI.dCos(this.faceDegree) / 100);
               this.velX += var1 * MyAPI.dSin(this.faceDegree) / 100;
                  this.velX = GameTime.advance(this, "velX", this.velX, this.getGravity() * MyAPI.dSin(this.faceDegree) / 100);
               soundInstance.playSe(11);
            } else if (Math.abs(this.getVelX()) <= 64 && this.getDegreeDiff(this.faceDegree, this.degreeStable) <= 45) {
               this.focusMovingState = 2;
            }
         } else if (this.animationID == 5) {
            this.animationID = 46;
         }
      }

      var4 = false;
      return var4;
   }
}
