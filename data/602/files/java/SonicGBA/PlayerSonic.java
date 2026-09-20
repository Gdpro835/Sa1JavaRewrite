package SonicGBA;

import GameEngine.time.GameTime;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import com.sega.engine.lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import State.TitleState;

public class PlayerSonic extends PlayerObject {
   private static final int AIR_DASH_TIME_COUNT = 5;
   private static final int[] ANIMATION_CONVERT;
   private static final int ATTACK4_ISINWATER_JUMP_START_V;
   private static final int ATTACK4_JUMP_START_V;
   public static final int ATTACK_COUNT_LEVEL_1 = 6;
   public static final int ATTACK_COUNT_LEVEL_2 = 12;
   public static final int BACK_JUMP_SPEED_X = 384;
   private static final int EFFECT_JUMP = 1;
   private static final int EFFECT_NONE = 0;
   private static final int EFFECT_SLIP = 2;
   private static final int LOOP = -1;
   private static final int[] LOOP_INDEX;
   private static final int NO_ANIMATION = -1;
   private static final int NO_LOOP = -2;
   private static final int NO_LOOP_DEPAND = -3;
   private static final int SNOW_DIVIDE_COUNT = 70;
   public static final int SONIC_ANI_ATTACK_1 = 13;
   public static final int SONIC_ANI_ATTACK_2 = 14;
   public static final int SONIC_ANI_ATTACK_3 = 15;
   public static final int SONIC_ANI_ATTACK_4 = 16;
   public static final int SONIC_ANI_BANK_1 = 44;
   public static final int SONIC_ANI_BANK_2 = 45;
   public static final int SONIC_ANI_BANK_3 = 46;
   public static final int SONIC_ANI_BAR_MOVE = 42;
   public static final int SONIC_ANI_BAR_STAY = 41;
   public static final int SONIC_ANI_BRAKE = 54;
   public static final int SONIC_ANI_BREATHE = 51;
   public static final int SONIC_ANI_CAUGHT = 57;
   public static final int SONIC_ANI_CELEBRATE_1 = 47;
   public static final int SONIC_ANI_CELEBRATE_2 = 48;
   public static final int SONIC_ANI_CELEBRATE_3 = 49;
   public static final int SONIC_ANI_CLIFF_1 = 36;
   public static final int SONIC_ANI_CLIFF_2 = 37;
   public static final int SONIC_ANI_DEAD_1 = 29;
   public static final int SONIC_ANI_DEAD_2 = 30;
   public static final int SONIC_ANI_ENTER_SP = 53;
   public static final int SONIC_ANI_HURT_1 = 27;
   public static final int SONIC_ANI_HURT_2 = 28;
   public static final int SONIC_ANI_JUMP = 4;
   public static final int SONIC_ANI_JUMP_ATTACK_BODY = 11;
   public static final int SONIC_ANI_JUMP_ATTACK_EFFECT = 12;
   public static final int SONIC_ANI_JUMP_DASH_1 = 17;
   public static final int SONIC_ANI_JUMP_DASH_2 = 18;
   public static final int SONIC_ANI_LOOK_UP_1 = 7;
   public static final int SONIC_ANI_LOOK_UP_2 = 8;
   public static final int SONIC_ANI_POLE_H = 40;
   public static final int SONIC_ANI_POLE_V = 39;
   public static final int SONIC_ANI_PUSH_WALL = 31;
   public static final int SONIC_ANI_RAIL_BODY = 52;
   public static final int SONIC_ANI_ROLL_H_1 = 34;
   public static final int SONIC_ANI_ROLL_H_2 = 35;
   public static final int SONIC_ANI_ROLL_V_1 = 32;
   public static final int SONIC_ANI_ROLL_V_2 = 33;
   public static final int SONIC_ANI_RUN = 3;
   public static final int SONIC_ANI_SLIDE_D0 = 24;
   public static final int SONIC_ANI_SLIDE_D45 = 25;
   public static final int SONIC_ANI_SLIDE_D45_EFFECT = 26;
   public static final int SONIC_ANI_SPIN_1 = 5;
   public static final int SONIC_ANI_SPIN_2 = 6;
   public static final int SONIC_ANI_SPRING_1 = 19;
   public static final int SONIC_ANI_SPRING_2 = 20;
   public static final int SONIC_ANI_SPRING_3 = 21;
   public static final int SONIC_ANI_SPRING_4 = 22;
   public static final int SONIC_ANI_SPRING_5 = 23;
   public static final int SONIC_ANI_SQUAT_1 = 9;
   public static final int SONIC_ANI_SQUAT_2 = 10;
   public static final int SONIC_ANI_STAND = 0;
   public static final int SONIC_ANI_UP_ARM = 38;
   public static final int SONIC_ANI_VS_KNUCKLE = 50;
   public static final int SONIC_ANI_WAITING_1 = 55;
   public static final int SONIC_ANI_WAITING_2 = 56;
   public static final int SONIC_ANI_WALK_1 = 1;
   public static final int SONIC_ANI_WALK_2 = 2;
   public static final int SONIC_ANI_WIND = 43;
   public static final int SONIC_ATTACK_LEVEL_1_V0_IN_WATER = 650;
   public static final int SONIC_ATTACK_LEVEL_2_V0_IN_WATER = 1036;
   public static final int SONIC_ATTACK_LEVEL_3_V0_IN_WATER = 1620;
   private static final int SUPER_SONIC_ANI_CHANGE_1 = 1;
   private static final int SUPER_SONIC_ANI_CHANGE_2 = 2;
   private static final int SUPER_SONIC_ANI_GO = 3;
   private static final int SUPER_SONIC_ANI_LOOK_MOON = 0;
   private static final boolean[] SUPER_SONIC_LOOP;
   private int attackCount;
   private int attackLevel;
   private PlayerAnimationCollisionRect attackRect;
   private AnimationDrawer effectDrawer;
   private int effectID;
   private boolean firstJump = false;
   private boolean isFirstAttack;
   private boolean jumpRollEnable;
   private int leftCount;
   private int rightCount;
   private int superSonicAnimationID;
   private AnimationDrawer superSonicDrawer;

   static {
      ATTACK4_JUMP_START_V = -1188 - GRAVITY;
      ATTACK4_ISINWATER_JUMP_START_V = -1354 - GRAVITY;
      int[] var0 = new int[]{0, 1, 2, 3, 4, 10, 5, 6, 31, 19, 23, -1, 28, 39, 20, -1, -1, 54, -1, -1, -1, 52, 34, 35, 38, 32, 33, 41, 42, 43, 28, 40, 44, 45, 46, 47, 48, 49, 7, 8, 7, 30, 21, 22, 28, 29, 9, 36, 37, 51, 55, 56, 57, 50};
      ANIMATION_CONVERT = var0;
      var0 = new int[]{-1, -1, -1, -1, -1, -1, -1, 8, -1, -3, -1, 4, -2, -1, -2, -1, -1, 18, -1, 22, -1, 22, 23, -1, -1, -1, -1, 28, -1, 30, -1, -1, 33, 32, 35, 34, -1, -1, -1, 20, 3, -1, -1, -1, -1, -1, -1, 48, -1, -2, 0, -2, -1, -1, -1, 56, -1, -1, 0};
      LOOP_INDEX = var0;
      boolean[] var1 = new boolean[]{false, false, true, true};
      SUPER_SONIC_LOOP = var1;
   }

   public PlayerSonic() {
      Animation var1 = new Animation("/animation/player/chr_sonic" + (TitleState.characterslots > 2 ? "_slot" + TitleState.characterslots : ""));
      this.drawer = var1.getDrawer();
      this.effectDrawer = var1.getDrawer();
      this.attackRect = new PlayerAnimationCollisionRect(this);
      if (StageManager.getStageID() == 12) {
         this.superSonicDrawer = (new Animation("/animation/player/chr_super_sonic" + (TitleState.characterslots > 2 ? "_slot" + TitleState.characterslots : ""))).getDrawer();
      }

   }

   private void setMinSlipSpeed() {
      if (this.getVelX() < SPEED_LIMIT_LEVEL_1) {
         this.setVelX(SPEED_LIMIT_LEVEL_1);
      }

   }

   private int startSpeedSet(boolean var1, int var2, int var3) {
      if (var1) {
         var2 = var2 * var3 / 100;
      }

      return var2;
   }

   public boolean beAccelerate(int var1, boolean var2, GameObject var3) {
      var2 = super.beAccelerate(var1, var2, var3);
      if (this.attackLevel != 0) {
         this.attackLevel = 0;
         this.attackCount = GameTime.set(this, "attackCount", 0);
         this.animationID = 3;
         if (Key.repeat(Key.gDown)) {
            this.animationID = 4;
         }
      }

      return var2;
   }

   public void beSpring(int var1, int var2) {
      super.beSpring(var1, var2);
      if (this.attackLevel != 0) {
         this.attackLevel = 0;
         this.attackCount = GameTime.set(this, "attackCount", 0);
         switch(var2) {
         case 2:
         case 3:
            this.animationID = 3;
            if (Key.repeat(Key.gDown)) {
               this.animationID = 4;
            }
         }
      }

   }

   public void closeImpl() {
      Animation.closeAnimationDrawer(this.superSonicDrawer);
      this.superSonicDrawer = null;
      Animation.closeAnimationDrawer(this.effectDrawer);
      this.effectDrawer = null;
   }

   public void doHurt() {
      super.doHurt();
      if (this.slipping) {
         this.currentLayer = 1;
         this.slipping = false;
      }

   }

   public void doJump() {
      if (this.myAnimationID != 13 && this.myAnimationID != 14 && this.myAnimationID != 15) {
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

         this.leftCount = GameTime.set(this, "leftCount", 0);
         this.rightCount = GameTime.set(this, "rightCount", 0);
         this.jumpRollEnable = false;
         if (this.slipping) {
            this.currentLayer = 1;
            this.slipping = false;
         }

         this.firstJump = true;
         if (this.bankwalking) {
            this.firstJump = false;
         }
      }

   }

   public void doWhileLand(int var1) {
      super.doWhileLand(var1);
      this.firstJump = false;
   }

   public void drawCharacter(MFGraphics var1) {
      Coordinate var13 = MapManager.getCamera();
      if (isTerminal && terminalType == 3 && terminalState >= 2) {
         switch(terminalState) {
         case 2:
         case 3:
            this.superSonicAnimationID = 0;
            break;
         case 4:
         case 5:
            if (this.superSonicAnimationID != 1 && this.superSonicAnimationID != 2) {
               this.superSonicAnimationID = 1;
            }
            break;
         case 6:
            this.superSonicAnimationID = 3;
         }

         this.superSonicDrawer.setActionId(this.superSonicAnimationID);
         this.superSonicDrawer.setLoop(SUPER_SONIC_LOOP[this.superSonicAnimationID]);
         this.drawInMap(var1, this.superSonicDrawer, this.posX + this.terminalOffset, this.posY);
         if (this.superSonicDrawer.checkEnd()) {
            switch(this.superSonicAnimationID) {
            case 1:
               this.superSonicAnimationID = 2;
            }
         }
      } else {
         if (this.animationID != -1) {
            this.myAnimationID = ANIMATION_CONVERT[this.animationID];
         }

         if (this.myAnimationID != -1) {
            boolean var10;
            if (LOOP_INDEX[this.myAnimationID] == -1) {
               var10 = true;
            } else {
               var10 = false;
            }

            int var2;
            int var3;
            int var4;
            int var5;
            int var6;
            int var7;
            AnimationDrawer var12;
            byte var16;
            if (this.hurtCount % 2 == 0) {
               if (this.animationID == 4) {
                  var3 = this.getNewPointX(this.footPointX, 0, -512, this.faceDegree);
                  var2 = this.getNewPointY(this.footPointY, 0, -512, this.faceDegree);
                  var4 = this.getNewPointX(var3, 0, 512, 0);
                  var3 = this.getNewPointY(var2, 0, 512, 0);
                  if (this.collisionState == 0) {
                     if (this.isAntiGravity) {
                        if (this.faceDirection) {
                           if (this.totalVelocity >= 0) {
                              var16 = 3;
                           } else {
                              var16 = 1;
                           }

                           var3 -= 1024;
                        } else {
                           if (this.totalVelocity > 0) {
                              var16 = 3;
                           } else {
                              var16 = 1;
                           }

                           var3 -= 1024;
                        }
                     } else if (this.faceDirection) {
                        if (this.totalVelocity >= 0) {
                           var16 = 0;
                        } else {
                           var16 = 2;
                        }
                     } else if (this.totalVelocity > 0) {
                        var16 = 0;
                     } else {
                        var16 = 2;
                     }
                  } else if (this.isAntiGravity) {
                     if (this.faceDirection) {
                        if (this.velX <= 0) {
                           var16 = 3;
                        } else {
                           var16 = 1;
                        }

                        var3 -= 1024;
                     } else {
                        if (this.velX < 0) {
                           var16 = 3;
                        } else {
                           var16 = 1;
                        }

                        var3 -= 1024;
                     }
                  } else if (this.faceDirection) {
                     if (this.velX >= 0) {
                        var16 = 0;
                     } else {
                        var16 = 2;
                     }
                  } else if (this.velX > 0) {
                     var16 = 0;
                  } else {
                     var16 = 2;
                  }

                  var12 = this.drawer;
                  var7 = this.myAnimationID;
                  var5 = var13.x;
                  var6 = var13.y;
                  var12.draw(var1, var7, (var4 >> 6) - var5, (var3 >> 6) - var6, var10, var16);
               } else {
                  boolean var11;
                  if (this.animationID != 6 && this.animationID != 7) {
                     switch(this.myAnimationID) {
                     case 24:
                        var12 = this.drawer;
                        var3 = this.myAnimationID;
                        var4 = this.footPointX;
                        var5 = var13.x;
                        var2 = this.footPointY;
                        var6 = var13.y;
                        var12.draw(var1, var3, (var4 >> 6) - var5 + 0, (var2 >> 6) - var6 + 0, var10, 0);
                        if (this.isInWater) {
                           this.effectDrawer.setSpeed(1, 2);
                        } else {
                           this.effectDrawer.setSpeed(1, 1);
                        }
                        break;
                     case 25:
                        var12 = this.effectDrawer;
                        var3 = this.footPointX;
                        var5 = var13.x;
                        var2 = this.footPointY;
                        var4 = var13.y;
                        var12.draw(var1, 26, (var3 >> 6) - var5 + 8, (var2 >> 6) - var4 + 0, var10, 0);
                        var12 = this.drawer;
                        var2 = this.myAnimationID;
                        var5 = this.footPointX;
                        var4 = var13.x;
                        var6 = this.footPointY;
                        var3 = var13.y;
                        var12.draw(var1, var2, (var5 >> 6) - var4 + 8, (var6 >> 6) - var3 + 0, var10, 0);
                        if (this.isInWater) {
                           this.effectDrawer.setSpeed(1, 2);
                        } else {
                           this.effectDrawer.setSpeed(1, 1);
                        }
                        break;
                     default:
                        if (this.isInWater) {
                           this.drawer.setSpeed(1, 2);
                        } else {
                           this.drawer.setSpeed(1, 1);
                        }

                        if (this.myAnimationID == 36 || this.myAnimationID == 37 || this.myAnimationID == 7 || this.myAnimationID == 8) {
                           this.degreeForDraw = this.degreeStable;
                           this.faceDegree = this.degreeStable;
                        }

                        if (this.myAnimationID == 54) {
                           this.degreeForDraw = this.degreeStable;
                        }

                        if (this.myAnimationID != 1 && this.myAnimationID != 2 && this.myAnimationID != 3 && this.myAnimationID != 57) {
                           this.degreeForDraw = this.degreeStable;
                        }

                        if (this.fallinSandSlipState != 0) {
                           if (this.fallinSandSlipState == 1) {
                              this.faceDirection = true;
                           } else if (this.fallinSandSlipState == 2) {
                              this.faceDirection = false;
                           }
                        }

                        if (this.faceDirection) {
                           var16 = 0;
                        } else {
                           var16 = 2;
                        }

                        if (this.degreeForDraw != this.faceDegree) {
                           var3 = this.footPointX;
                           var5 = -this.collisionRect.getHeight();
                           var4 = this.faceDegree;
                           var3 = this.getNewPointX(var3, 0, var5 >> 1, var4);
                           var4 = this.footPointY;
                           var6 = -this.collisionRect.getHeight();
                           var5 = this.faceDegree;
                           var4 = this.getNewPointY(var4, 0, var6 >> 1, var5);
                           var1.saveCanvas();
                           var1.translateCanvas((var3 >> 6) - var13.x, (var4 >> 6) - var13.y);
                           var1.rotateCanvas((float)this.degreeForDraw);
                           this.drawer.draw(var1, this.myAnimationID, 0, this.collisionRect.getHeight() >> 1 >> 6, var10, var16);
                           var1.restoreCanvas();
                        } else {
                           var12 = this.drawer;
                           var5 = this.myAnimationID;
                           var6 = this.footPointX;
                           var4 = var13.x;
                           var7 = this.footPointY;
                           var2 = var13.y;
                           var3 = this.degreeForDraw;
                           if (this.faceDirection) {
                              var11 = false;
                           } else {
                              var11 = true;
                           }

                           this.drawDrawerByDegree(var1, var12, var5, (var6 >> 6) - var4, (var7 >> 6) - var2, var10, var3, var11);
                        }
                     }
                  } else {
                     var12 = this.drawer;
                     var4 = this.myAnimationID;
                     var6 = this.footPointX;
                     var2 = var13.x;
                     var3 = this.footPointY;
                     var5 = var13.y;
                     var7 = this.degreeForDraw;
                     if (this.faceDirection) {
                        var11 = false;
                     } else {
                        var11 = true;
                     }

                     this.drawDrawerByDegree(var1, var12, var4, (var6 >> 6) - var2, (var3 >> 6) - var5, var10, var7, var11);
                  }
               }
            } else {
               if (this.myAnimationID != this.drawer.getActionId()) {
                  this.drawer.setActionId(this.myAnimationID);
               }

               if (!AnimationDrawer.isAllPause()) {
                  this.drawer.moveOn();
               }
            }

            switch(this.effectID) {
            case 1:
               var3 = 0;
               if (this.collisionState == 0) {
                  if (this.faceDirection) {
                     if (this.totalVelocity >= 0) {
                        var16 = 0;
                     } else {
                        var16 = 2;
                     }
                  } else if (this.totalVelocity > 0) {
                     var16 = 0;
                  } else {
                     var16 = 2;
                  }
               } else if (this.isAntiGravity) {
                  if (this.faceDirection) {
                     if (this.velX <= 0) {
                        var16 = 3;
                     } else {
                        var16 = 1;
                     }

                     var3 = 0 - 1024;
                  } else {
                     if (this.velX < 0) {
                        var16 = 3;
                     } else {
                        var16 = 1;
                     }

                     var3 = 0 - 1024;
                  }
               } else if (this.faceDirection) {
                  if (this.velX >= 0) {
                     var16 = 0;
                  } else {
                     var16 = 2;
                  }
               } else if (this.velX > 0) {
                  var16 = 0;
               } else {
                  var16 = 2;
               }

               var12 = this.effectDrawer;
               var7 = this.posX;
               var6 = var13.x;
               var5 = this.posY;
               short var17;
               if (this.isAntiGravity) {
                  var17 = 1024;
               } else {
                  var17 = 0;
               }

               int var8 = var13.y;
               var12.draw(var1, 12, (var7 >> 6) - var6, (var5 + var17 >> 6) + (var3 >> 6) - var8, false, var16);
               if (this.isInWater) {
                  this.effectDrawer.setSpeed(1, 2);
               } else {
                  this.effectDrawer.setSpeed(1, 1);
               }
            default:
               if (this.effectDrawer.checkEnd()) {
                  this.effectID = 0;
               }

               this.attackRectVec.removeAllElements();
               byte[] var23;
               if (this.effectID == 1) {
                  var23 = this.effectDrawer.getARect();
               } else {
                  var23 = this.drawer.getARect();
               }

               if (this.isAntiGravity) {
                  byte[] var14 = this.drawer.getARect();
                  if (var14 != null) {
                     var23[0] = (byte)(-var14[0] - var14[2]);
                     var23[1] = (byte)(-var14[1] - var14[3]);
                  }
               }

               if (var23 != null) {
                  byte var18;
                  byte var20;
                  if (SonicDebug.showCollisionRect) {
                     var1.setColor(65280);
                     var3 = this.footPointX;
                     byte var19 = var23[0];
                     int var9 = var13.x;
                     var4 = this.footPointY;
                     var20 = var23[1];
                     var7 = var13.y;
                     byte var24 = var23[2];
                     var18 = var23[3];
                     var1.drawRect((var3 >> 6) + var19 - var9, (var4 >> 6) + var20 - var7, var24, var18);
                  }

                  PlayerAnimationCollisionRect var15 = this.attackRect;
                  var20 = var23[0];
                  var18 = var23[1];
                  byte var21 = var23[2];
                  byte var22 = var23[3];
                  if (this.effectID == 1) {
                     var2 = 12;
                  } else {
                     var2 = this.myAnimationID;
                  }

                  var15.initCollision(var20 << 6, var18 << 6, var21 << 6, var22 << 6, var2);
                  this.attackRectVec.addElement(this.attackRect);
               } else {
                  this.attackRect.reset();
               }

               if (this.animationID == -1 && this.drawer.checkEnd() && LOOP_INDEX[this.myAnimationID] >= 0) {
                  this.myAnimationID = LOOP_INDEX[this.myAnimationID];
               }

               if (this.isFirstAttack) {
                  if (this.myAnimationID == 13 && !this.isCrashFallingSand) {
                     soundInstance.playSe(4);
                  }

                  this.isFirstAttack = false;
               }
            }
         }
      }

   }

   public void extraInputLogic() {
      if (isTerminal && terminalState >= 2) {
         switch(terminalState) {
         case 4:
            this.velX = 0;
         }
      }

   }

   protected void extraLogicJump() {
      if (!this.hurtNoControl) {
         if (!this.slipping && Key.press(Key.gLeft)) {
            if (!this.jumpRollEnable) {
               this.leftCount = GameTime.set(this, "leftCount", 5);
            }

            this.rightCount = GameTime.set(this, "rightCount", 0);
         }

         if (Key.press(Key.gRight)) {
            this.leftCount = GameTime.set(this, "leftCount", 0);
            if (!this.jumpRollEnable) {
               this.rightCount = GameTime.set(this, "rightCount", 5);
            }
         }
      }

      if (this.animationID == 4 && this.firstJump) {
         if (this.leftCount > 0) {
            this.leftCount = Math.max(0, GameTime.advance(this, "leftCount", this.leftCount, -(1)));
            if (!Key.repeat(Key.gLeft)) {
               this.jumpRollEnable = true;
            }

            if (this.jumpRollEnable && (Key.repeat(Key.gLeft))) {
               this.animationID = -1;
               this.myAnimationID = 17;
               this.leftCount = GameTime.set(this, "leftCount", 0);
               this.velY = 0;
               this.velX = GameTime.advance(this, "velX", this.velX, -(this.maxVelocity >> 2));
               soundInstance.playSe(7);
               this.firstJump = false;
            }
         }

         if (Key.press(1073741824)) {
            this.animationID = -1;
            this.myAnimationID = 17;
            this.leftCount = GameTime.set(this, "leftCount", 0);
            this.velY = 0;
            this.velX = this.velX + (-(this.maxVelocity >> 2));
            soundInstance.playSe(7);
            this.firstJump = false;
         }

         if (this.rightCount > 0) {
            this.rightCount = Math.max(0, GameTime.advance(this, "rightCount", this.rightCount, -(1)));
            if (!Key.repeat(Key.gRight)) {
               this.jumpRollEnable = true;
            }

            if (this.jumpRollEnable && (Key.repeat(Key.gRight))) {
               this.animationID = -1;
               this.myAnimationID = 17;
               this.rightCount = GameTime.set(this, "rightCount", 0);
               this.velY = 0;
               this.velX = GameTime.advance(this, "velX", this.velX, this.maxVelocity >> 2);
               soundInstance.playSe(7);
               this.firstJump = false;
            }
         }

         if (Key.press(Integer.MIN_VALUE)) {
            this.animationID = -1;
            this.myAnimationID = 17;
            this.leftCount = GameTime.set(this, "leftCount", 0);
            this.velY = 0;
            this.velX = this.velX + (this.maxVelocity >> 2);
            soundInstance.playSe(7);
            this.firstJump = false;
         }

         if (this.firstJump && Key.press(16777216)) {
            this.effectID = 1;
            this.firstJump = false;
            soundInstance.playSe(79);
            this.effectDrawer.restart();
            this.effectDrawer.setActionId(12);
            byte[] var5 = this.effectDrawer.getARect();
            if (var5 != null) {
               PlayerAnimationCollisionRect var6 = this.attackRect;
               byte var1 = var5[0];
               byte var3 = var5[1];
               byte var2 = var5[2];
               byte var4 = var5[3];
               var6.initCollision(var1 << 6, var3 << 6, var2 << 6, var4 << 6, 12);
               this.attackRectVec.addElement(this.attackRect);
            }
         } else if (this.firstJump && Key.press(Key.gSelect | 8388608) && this.velY < 1400){
            short var9;
            if (this.isInWater) {
               var9 = 832;
            } else {
               var9 = 719;
            }

            int var10;
            if (this.isAntiGravity) {
               var10 = 2;
            } else {
               var10 = -2;
            }

            this.velY = var10 * -var9;
            this.firstJump = false;
            soundInstance.playSe(7);
         }
      }

      if (this.myAnimationID == 14 && this.drawer.checkEnd()) {
         this.animationID = 0;
      }

   }

   protected void extraLogicOnObject() {
      this.firstJump = false;
      if (this.attackCount > 0) {
         this.attackCount = Math.max(0, GameTime.advance(this, "attackCount", this.attackCount, -(1)));
      }

      if ((this.myAnimationID == 13 || this.myAnimationID == 14 || this.myAnimationID == 15) && this.attackLevel == 0) {
         this.animationID = 0;
         this.myAnimationID = ANIMATION_CONVERT[0];
      }

      short var1;
      int var8;
      switch(this.myAnimationID) {
      case 13:
         if ((this.attackCount == 0 || this.getVelX() == 0) && !this.isStopByObject) {
            this.attackLevel = 0;
         } else {
            this.myAnimationID = 13;
         }

         if (this.isStopByObject && this.attackCount == 0) {
            this.attackLevel = 0;
         }

         if (Key.press(Key.gSelect | 8388608) && !this.isCrashPipe) {
            this.attackLevel = 2;
            this.myAnimationID = 14;
            soundInstance.playSe(17);
            if (this.isInWater) {
               var1 = 1036;
            } else {
               var1 = 672;
            }

            if (this.collisionState == 0) {
               if (this.isAntiGravity ^ this.faceDirection) {
                  var8 = this.startSpeedSet(this.isInSnow, var1, 70);
               } else {
                  var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
               }

               this.totalVelocity = var8;
            } else {
               if (this.isAntiGravity ^ this.faceDirection) {
                  var8 = this.startSpeedSet(this.isInSnow, var1, 70);
               } else {
                  var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
               }

               this.setVelX(var8);
            }

            soundInstance.playSe(17);
         }
         break;
      case 14:
         if (!this.drawer.checkEnd()) {
            this.myAnimationID = 14;
         } else if (this.attackLevel >= 3 && (this.getVelX() != 0 || this.isStopByObject)) {
            this.myAnimationID = 15;
            if (this.isInWater) {
               var1 = 1620;
            } else {
               var1 = 1200;
            }

            if (this.collisionState == 0) {
               if (this.isAntiGravity ^ this.faceDirection) {
                  var8 = this.startSpeedSet(this.isInSnow, var1, 70);
               } else {
                  var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
               }

               this.totalVelocity = var8;
            } else {
               if (this.isAntiGravity ^ this.faceDirection) {
                  var8 = this.startSpeedSet(this.isInSnow, var1, 70);
               } else {
                  var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
               }

               this.setVelX(var8);
            }

            this.attackCount = GameTime.set(this, "attackCount", 12);
            soundInstance.playSe(6);
         } else {
            this.animationID = 0;
            this.attackLevel = 0;
            this.setVelX(0);
            this.totalVelocity = 0;
         }

         if (Key.press(Key.gSelect | 8388608)) {
            this.attackLevel = 3;
         }
         break;
      case 15:
         if (this.getVelX() == 0 && !this.isStopByObject) {
            this.attackLevel = 0;
         } else {
            this.myAnimationID = 15;
         }

         if (this.isStopByObject && this.attackCount == 0) {
            this.attackLevel = 0;
         }
         break;
      default:
         if (this.animationID != 4) {
            boolean var5 = Key.press(Key.gSelect | 8388608);
            if (var5 && this.animationID != 8 && !this.isCrashFallingSand) {
               this.attackLevel = 1;
               byte var9;
               if (this.isInWater) {
                  var9 = 2;
               } else {
                  var9 = 1;
               }

               this.attackCount = GameTime.set(this, "attackCount", var9 * 6);
               this.animationID = -1;
               this.myAnimationID = 13;
               this.isFirstAttack = true;
               if (this.isInWater) {
                  var1 = 650;
               } else {
                  var1 = 488;
               }

               if (this.collisionState == 0) {
                  if (this.isAntiGravity ^ this.faceDirection) {
                     var8 = this.startSpeedSet(this.isInSnow, var1, 70);
                  } else {
                     var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
                  }

                  this.totalVelocity = var8;
               } else {
                  if (this.isAntiGravity ^ this.faceDirection) {
                     var8 = this.startSpeedSet(this.isInSnow, var1, 70);
                  } else {
                     var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
                  }

                  this.setVelX(var8);
               }

               this.drawer.setActionId(13);
               byte[] var6 = this.drawer.getARect();
               if (var6 != null) {
                  PlayerAnimationCollisionRect var7 = this.attackRect;
                  byte var2 = var6[0];
                  byte var3 = var6[1];
                  byte var4 = var6[2];
                  byte var10 = var6[3];
                  var7.initCollision(var2 << 6, var3 << 6, var4 << 6, var10 << 6, 13);
                  this.attackRectVec.addElement(this.attackRect);
               }
            }
         }
      }

      this.isStopByObject = false;
      if (this.attackLevel == 0) {
         this.isAttacking = false;
      } else {
         this.isAttacking = true;
      }

   }

   protected void extraLogicWalk() {
      if (this.slipping) {
         if (Key.repeat(Key.gLeft) && this.myAnimationID == 25) {
            this.totalVelocity = GameTime.advance(this, "totalVelocity", this.totalVelocity, -(30));
         } else if (Key.repeat(Key.gDown | Key.gRight) && this.faceDegree < 135) {
            this.totalVelocity = GameTime.advance(this, "totalVelocity", this.totalVelocity, MyAPI.dSin(this.faceDegree) * 150 / 100);
         }

         this.totalVelocity = GameTime.advance(this, "totalVelocity", this.totalVelocity, -(30));
         this.totalVelocity = Math.max(this.totalVelocity, 192);
         this.animationID = -1;
         this.faceDirection = true;
         if (this.faceDegree == 45) {
            this.myAnimationID = 25;
            this.effectID = 2;
         } else {
            this.setMinSlipSpeed();
            this.myAnimationID = 24;
         }

         slidingFrame = GameTime.advanceOnce(PlayerObject.class, "slidingFrame", slidingFrame, 1);
         System.out.println("~~slidingFrame:" + slidingFrame);
         if (slidingFrame == 2) {
            soundInstance.playLoopSe(9);
         }
      }

      if (this.attackCount > 0) {
         this.attackCount = Math.max(0, GameTime.advance(this, "attackCount", this.attackCount, -(1)));
      }

      if ((this.myAnimationID == 13 || this.myAnimationID == 14 || this.myAnimationID == 15) && this.faceDegree != 90 && this.faceDegree != 270 && this.attackLevel == 0) {
         this.animationID = 0;
         this.myAnimationID = ANIMATION_CONVERT[0];
         if (this.collisionState == 1) {
            this.animationID = 4;
            this.myAnimationID = ANIMATION_CONVERT[4];
         }
      }

      short var1;
      int var8;
      switch(this.myAnimationID) {
      case 13:
         if ((this.attackCount == 0 || this.getVelX() == 0) && !this.isStopByObject) {
            this.attackLevel = 0;
         } else {
            this.myAnimationID = 13;
         }

         if (this.isStopByObject && this.attackCount == 0) {
            this.attackLevel = 0;
         }

         if (Key.press(Key.gSelect | 8388608) && !this.isCrashPipe) {
            this.attackLevel = 2;
            this.myAnimationID = 14;
            soundInstance.playSe(17);
            if (this.isInWater) {
               var1 = 1036;
            } else {
               var1 = 672;
            }

            if (this.collisionState == 0) {
               if (this.faceDirection) {
                  var8 = this.startSpeedSet(this.isInSnow, var1, 70);
               } else {
                  var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
               }

               this.totalVelocity = var8;
            } else {
               if (this.faceDirection) {
                  var8 = this.startSpeedSet(this.isInSnow, var1, 70);
               } else {
                  var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
               }

               this.setVelX(var8);
            }

            soundInstance.playSe(17);
         }
         break;
      case 14:
         if (!this.drawer.checkEnd()) {
            this.myAnimationID = 14;
         } else if (this.attackLevel != 3 || this.getVelX() == 0 && !this.isStopByObject) {
            if (this.attackLevel == 4) {
               if (this.isStopByObject) {
                  if (this.faceDirection ^ this.isAntiGravity) {
                     var8 = 384;
                  } else {
                     var8 = -384;
                  }
               } else {
                  var8 = this.getVelX();
               }

               int var2 = var8;
               if (this.isInSnow) {
                  var2 = var8 >> 1;
               }

               if (this.isInWater) {
                  var8 = ATTACK4_ISINWATER_JUMP_START_V;
               } else {
                  var8 = ATTACK4_JUMP_START_V;
               }

               if (this.isAntiGravity) {
                  var8 = -var8;
               }

               super.doJumpV(var8);
               this.attackLevel = 0;
               this.setVelX(-var2);
               this.animationID = -1;
               this.myAnimationID = 16;
               this.noVelMinus = true;
            } else {
               this.animationID = 0;
               this.attackLevel = 0;
               this.setVelX(0);
               this.totalVelocity = 0;
            }
         } else {
            this.myAnimationID = 15;
            if (this.isInWater) {
               var1 = 1620;
            } else {
               var1 = 1200;
            }

            if (this.collisionState == 0) {
               if (this.faceDirection) {
                  var8 = this.startSpeedSet(this.isInSnow, var1, 70);
               } else {
                  var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
               }

               this.totalVelocity = var8;
            } else {
               if (this.faceDirection) {
                  var8 = this.startSpeedSet(this.isInSnow, var1, 70);
               } else {
                  var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
               }

               this.setVelX(var8);
            }

            this.attackCount = GameTime.set(this, "attackCount", 12);
            soundInstance.playSe(6);
         }

         if (this.attackLevel != 0) {
            if (Key.press(Key.gSelect | 8388608)) {
               this.attackLevel = 3;
            } else if (Key.press(16777216)) {
               this.attackLevel = 4;
            }
         }
         break;
      case 15:
         if (this.getVelX() == 0 && !this.isStopByObject) {
            this.attackLevel = 0;
         } else {
            this.myAnimationID = 15;
         }

         if (this.isStopByObject && this.attackCount == 0) {
            this.attackLevel = 0;
         }
         break;
      default:
         if (this.animationID != 4) {
            boolean var5 = Key.press(Key.gSelect | 8388608);
            if (var5 && !this.slipping && this.animationID != 8 && !this.isCrashFallingSand) {
               if (this.onBank) {
                  this.onBank = false;
               }

               this.attackLevel = 1;
               byte var10;
               if (this.isInWater) {
                  var10 = 2;
               } else {
                  var10 = 1;
               }

               this.attackCount = GameTime.set(this, "attackCount", var10 * 6);
               this.animationID = -1;
               this.myAnimationID = 13;
               this.isFirstAttack = true;
               if (this.isInWater) {
                  var1 = 650;
               } else {
                  var1 = 488;
               }

               if (this.collisionState == 0) {
                  if (this.faceDirection) {
                     var8 = this.startSpeedSet(this.isInSnow, var1, 70);
                  } else {
                     var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
                  }

                  this.totalVelocity = var8;
               } else {
                  if (this.faceDirection) {
                     var8 = this.startSpeedSet(this.isInSnow, var1, 70);
                  } else {
                     var8 = this.startSpeedSet(this.isInSnow, -var1, 70);
                  }

                  this.setVelX(var8);
               }

               this.drawer.setActionId(13);
               byte[] var7 = this.drawer.getARect();
               if (var7 != null) {
                  PlayerAnimationCollisionRect var6 = this.attackRect;
                  byte var11 = var7[0];
                  byte var4 = var7[1];
                  byte var9 = var7[2];
                  byte var3 = var7[3];
                  var6.initCollision(var11 << 6, var4 << 6, var9 << 6, var3 << 6, 13);
                  this.attackRectVec.addElement(this.attackRect);
               }
            }
         }
      }

      this.isStopByObject = false;
      if (this.attackLevel == 0) {
         this.isAttacking = false;
      } else {
         this.isAttacking = true;
      }

   }

   public int getRetPower() {
      int var2 = super.getRetPower();
      int var1 = var2;
      if (speedCount > 0) {
         var1 = var2;
         if (this.myAnimationID >= 13) {
            var1 = var2;
            if (this.myAnimationID <= 14) {
               var1 = var2 / 2;
            }
         }
      }

      if (this.myAnimationID == 15) {
         var1 = 150;
      }

      return var1;
   }

   public int getSlopeGravity() {
      int var1;
      if (this.slipping) {
         var1 = 0;
      } else {
         var1 = super.getSlopeGravity();
      }

      return var1;
   }

   public boolean isOnSlip0() {
      boolean var1;
      if (this.myAnimationID == 24) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean needRetPower() {
      boolean var1;
      if (this.slipping) {
         var1 = false;
      } else if (this.myAnimationID != 13 && this.myAnimationID != 14 && this.myAnimationID != 15) {
         var1 = super.needRetPower();
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean noRotateDraw() {
      boolean var1;
      if (this.myAnimationID != 13 && this.myAnimationID != 14 && this.myAnimationID != 15) {
         var1 = super.noRotateDraw();
      } else {
         var1 = true;
      }

      return var1;
   }

   public void setSlideAni() {
      this.animationID = -1;
      this.myAnimationID = 24;
   }

   public void setSlip0() {
      if (this.collisionState == 0) {
         this.animationID = -1;
         this.myAnimationID = 24;
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
         this.worldCal.setMovedState(false);
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
         this.worldCal.setMovedState(false);
      }

   }

   public void slipStart() {
      this.currentLayer = 0;
      this.slipping = true;
      this.slideSoundStart = true;
      soundInstance.playSe(8);
      slidingFrame = 0;
      this.collisionState = 1;
      this.worldCal.actionState = 1;
      this.setMinSlipSpeed();
      this.worldCal.setMovedState(true);
   }
}
