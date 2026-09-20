package SonicGBA;

import GameEngine.time.GameTime;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import com.sega.engine.lib.CrlFP32;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import State.TitleState;

public class PlayerTails extends PlayerObject {
   private static final int[] ANIMATION_CONVERT;
   private static final int FLY_GRAVITY = 30;
   private static final int FLY_POWER = -90;
   private static final int FLY_POWER_COUNT = 8;
   private static final int FLY_TIME = 128;
   private static final int LOOP = -1;
   private static final int[] LOOP_INDEX;
   private static final int MAX_FLY_VEL_Y = -176;
   private static final int NO_ANIMATION = -1;
   private static final int NO_LOOP = -2;
   private static final int NO_LOOP_DEPAND = -2;
   public static final int TAILS_ANI_ATTACK = 11;
   public static final int TAILS_ANI_BANK_1 = 31;
   public static final int TAILS_ANI_BANK_2 = 32;
   public static final int TAILS_ANI_BANK_3 = 33;
   public static final int TAILS_ANI_BAR_MOVE = 42;
   public static final int TAILS_ANI_BAR_STAY = 41;
   public static final int TAILS_ANI_BRAKE = 46;
   public static final int TAILS_ANI_BREATHE = 52;
   public static final int TAILS_ANI_CAUGHT = 47;
   public static final int TAILS_ANI_CELEBRATE_1 = 27;
   public static final int TAILS_ANI_CELEBRATE_2 = 28;
   public static final int TAILS_ANI_CELEBRATE_3 = 29;
   public static final int TAILS_ANI_CELEBRATE_4 = 30;
   public static final int TAILS_ANI_CLIFF_1 = 25;
   public static final int TAILS_ANI_CLIFF_2 = 26;
   public static final int TAILS_ANI_DEAD_1 = 17;
   public static final int TAILS_ANI_DEAD_2 = 18;
   public static final int TAILS_ANI_ENTER_SP = 51;
   public static final int TAILS_ANI_FLY_1 = 12;
   public static final int TAILS_ANI_FLY_2 = 13;
   public static final int TAILS_ANI_FLY_3 = 14;
   public static final int TAILS_ANI_HURT_1 = 15;
   public static final int TAILS_ANI_HURT_2 = 16;
   public static final int TAILS_ANI_JUMP_BODY = 5;
   public static final int TAILS_ANI_JUMP_TAIL = 6;
   public static final int TAILS_ANI_LOOK_UP_1 = 7;
   public static final int TAILS_ANI_LOOK_UP_2 = 8;
   public static final int TAILS_ANI_POLE_H = 36;
   public static final int TAILS_ANI_POLE_V = 35;
   public static final int TAILS_ANI_PUSH_WALL = 19;
   public static final int TAILS_ANI_RAIL_BODY = 44;
   public static final int TAILS_ANI_RAIL_TAIL = 45;
   public static final int TAILS_ANI_ROLL_H_1 = 39;
   public static final int TAILS_ANI_ROLL_H_2 = 40;
   public static final int TAILS_ANI_ROLL_V_1 = 37;
   public static final int TAILS_ANI_ROLL_V_2 = 38;
   public static final int TAILS_ANI_RUN = 3;
   public static final int TAILS_ANI_SPIN = 4;
   public static final int TAILS_ANI_SPRING_1 = 20;
   public static final int TAILS_ANI_SPRING_2 = 21;
   public static final int TAILS_ANI_SPRING_3 = 22;
   public static final int TAILS_ANI_SPRING_4 = 23;
   public static final int TAILS_ANI_SPRING_5 = 24;
   public static final int TAILS_ANI_SQUAT_1 = 9;
   public static final int TAILS_ANI_SQUAT_2 = 10;
   public static final int TAILS_ANI_STAND = 0;
   public static final int TAILS_ANI_SWIM_1 = 48;
   public static final int TAILS_ANI_SWIM_2 = 49;
   public static final int TAILS_ANI_UP_ARM = 34;
   public static final int TAILS_ANI_VS_KNUCKLE = 50;
   public static final int TAILS_ANI_WAITING_1 = 53;
   public static final int TAILS_ANI_WAITING_2 = 54;
   public static final int TAILS_ANI_WALK_1 = 1;
   public static final int TAILS_ANI_WALK_2 = 2;
   public static final int TAILS_ANI_WIND = 43;
   public static boolean isInWind;
   private PlayerAnimationCollisionRect attackRect;
   public int flyCount = 0;
   private int flyUpCoolCount;
   private AnimationDrawer tailDrawer;
   private AnimationDrawer tailsDrawer1;
   private AnimationDrawer tailsDrawer2;

   static {
      int[] var0 = new int[]{0, 1, 2, 3, 5, 10, 4, 4, 19, 20, 24, -1, 16, 35, 21, -1, -1, 46, -1, -1, -1, 44, 39, 40, 34, 37, 38, 41, 42, 43, 16, 36, 31, 32, 33, 27, 28, 29, 7, 8, 7, 18, 22, 23, 16, 17, 9, 25, 26, 52, 53, 54, 47, 50};
      ANIMATION_CONVERT = var0;
      var0 = new int[]{-1, -1, -1, -1, -1, -1, -1, 8, -1, -2, -1, 0, -1, 14, -1, 16, -1, 18, -1, -1, 23, -1, 23, 24, -1, -1, -1, 28, -1, 30, -1, -1, -1, -1, -1, 21, 3, 38, 37, 40, 39, -1, -1, -1, -1, -1, -1, -1, 49, -1, -1, -2, -2, 54, -1, 0};
      LOOP_INDEX = var0;
      isInWind = false;
   }

   public PlayerTails() {
      MFImage var1 = MFImage.createImage("/animation/player/chr_tails" + (TitleState.characterslots > 2 ? "_slot" + TitleState.characterslots : "") + ".png");
      Animation var2 = new Animation(var1, "/animation/player/chr_tails_01");
      this.tailsDrawer1 = var2.getDrawer();
      this.tailDrawer = var2.getDrawer();
      this.drawer = this.tailsDrawer1;
      Animation var3 = new Animation(var1, "/animation/player/chr_tails_02");
      this.tailsDrawer2 = var3.getDrawer();
      this.attackRect = new PlayerAnimationCollisionRect(this);
   }

   private void drawTail(MFGraphics var1) {
      byte var2 = -1;
      int var5 = this.getNewPointX(this.footPointX, 0, -512, this.faceDegree);
      int var4 = this.getNewPointY(this.footPointY, 0, -512, this.faceDegree);
      if (this.myAnimationID == 5) {
         var2 = 6;
      } else if (this.myAnimationID == 44) {
         var2 = 45;
      }

      int var3 = this.faceDegree;
      this.getTrans(var3);
      boolean var8;
      if (this.faceDirection) {
         var8 = false;
      } else {
         var8 = true;
      }

      boolean var9 = this.faceDirection;
      if (this.animationID == 4) {
         if (this.collisionState == 0) {
            if (this.faceDirection) {
               if (this.totalVelocity < 0) {
                  var8 = true;
               } else {
                  var8 = false;
               }
            } else if (this.totalVelocity <= 0) {
               var8 = true;
            } else {
               var8 = false;
            }
         } else if (this.faceDirection) {
            if (this.velX < 0) {
               var8 = true;
            } else {
               var8 = false;
            }
         } else if (this.velX <= 0) {
            var8 = true;
         } else {
            var8 = false;
         }
      }

      int var6;
      if (this.collisionState == 1 || this.collisionState == 3 || this.piping && this.pipeState == 1 || this.myAnimationID == 44) {
         var3 = CrlFP32.actTanDegree(this.velY, this.velX);
         var6 = TRANS[this.getTransId(var3)];
         var8 = false;
         this.faceDirection = true;
      }

      if (var2 != -1) {
         AnimationDrawer var10 = this.tailDrawer;
         int var7 = camera.x;
         var6 = camera.y;
         this.drawDrawerByDegree(var1, var10, var2, (var5 >> 6) - var7, (var4 >> 6) - var6, true, var3, var8);
      }

      this.faceDirection = var9;
   }

   public boolean canDoJump() {
      boolean var1;
      if (this.myAnimationID == 11) {
         var1 = false;
      } else {
         var1 = super.canDoJump();
      }

      return var1;
   }

   public void closeImpl() {
      Animation.closeAnimationDrawer(this.tailsDrawer1);
      this.tailsDrawer1 = null;
      Animation.closeAnimationDrawer(this.tailDrawer);
      this.tailDrawer = null;
      Animation.closeAnimationDrawer(this.tailsDrawer2);
      this.tailsDrawer2 = null;
   }

   public boolean doPoalMotion(int var1, int var2, boolean var3) {
      if (this.myAnimationID != 12 && this.myAnimationID != 13 && this.myAnimationID != 14) {
         var3 = super.doPoalMotion(var1, var2, var3);
      } else {
         var3 = false;
      }

      return var3;
   }

   public void drawCharacter(MFGraphics var1) {
      Coordinate var12 = MapManager.getCamera();
      int var2 = this.myAnimationID;
      if (this.animationID != -1) {
         this.myAnimationID = ANIMATION_CONVERT[this.animationID];
      }

      if (var2 == 30 && this.myAnimationID == 29) {
         this.myAnimationID = 30;
      }

      if (this.myAnimationID != -1) {
         boolean var10;
         if (LOOP_INDEX[this.myAnimationID] == -1) {
            var10 = true;
         } else {
            var10 = false;
         }

         if (this.myAnimationID == 50) {
            var10 = false;
         }

         this.drawer = this.tailsDrawer1;
         int var4 = this.myAnimationID;
         if (this.myAnimationID >= 48) {
            this.drawer = this.tailsDrawer2;
            var2 = this.myAnimationID - 48;
            var4 = var2;
            if (this.myAnimationID == 53) {
               var4 = var2;
               if (this.isResetWaitAni) {
                  this.drawer.restart();
                  this.isResetWaitAni = false;
                  var4 = var2;
               }
            }
         }

         if (this.isInWater) {
            this.drawer.setSpeed(1, 2);
            this.tailDrawer.setSpeed(1, 2);
         } else {
            this.drawer.setSpeed(1, 1);
            this.tailDrawer.setSpeed(1, 1);
         }

         int var3;
         int var5;
         int var6;
         int var7;
         if (this.hurtCount % 2 == 0) {
            this.drawTail(var1);
            AnimationDrawer var13;
            byte var16;
            if (this.animationID == 4) {
               var3 = this.getNewPointX(this.footPointX, 0, -512, this.faceDegree);
               var2 = this.getNewPointY(this.footPointY, 0, -512, this.faceDegree);
               var5 = this.getNewPointX(var3, 0, 512, 0);
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

               var13 = this.drawer;
               var6 = var12.x;
               var7 = var12.y;
               var13.draw(var1, var4, (var5 >> 6) - var6, (var3 >> 6) - var7, var10, var16);
            } else {
               boolean var11;
               if (this.animationID != 6 && this.animationID != 7) {
                  if (this.myAnimationID == 25 || this.myAnimationID == 26 || this.myAnimationID == 7 || this.myAnimationID == 8) {
                     this.degreeForDraw = this.degreeStable;
                     this.faceDegree = this.degreeStable;
                  }

                  if (this.myAnimationID == 46) {
                     this.degreeForDraw = this.degreeStable;
                  }

                  if (this.myAnimationID != 1 && this.myAnimationID != 2 && this.myAnimationID != 3 && this.myAnimationID != 47) {
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
                     var5 = this.footPointX;
                     var3 = -this.collisionRect.getHeight();
                     var6 = this.faceDegree;
                     var3 = this.getNewPointX(var5, 0, var3 >> 1, var6);
                     var5 = this.footPointY;
                     var7 = -this.collisionRect.getHeight();
                     var6 = this.faceDegree;
                     var5 = this.getNewPointY(var5, 0, var7 >> 1, var6);
                     var1.saveCanvas();
                     var1.translateCanvas((var3 >> 6) - var12.x, (var5 >> 6) - var12.y);
                     var1.rotateCanvas((float)this.degreeForDraw);
                     this.drawer.draw(var1, var4, 0, this.collisionRect.getHeight() >> 1 >> 6, var10, var16);
                     var1.restoreCanvas();
                  } else {
                     var13 = this.drawer;
                     var5 = this.footPointX;
                     var3 = var12.x;
                     var7 = this.footPointY;
                     var2 = var12.y;
                     var6 = this.degreeForDraw;
                     if (this.faceDirection) {
                        var11 = false;
                     } else {
                        var11 = true;
                     }

                     this.drawDrawerByDegree(var1, var13, var4, (var5 >> 6) - var3, (var7 >> 6) - var2, var10, var6, var11);
                  }
               } else {
                  var13 = this.drawer;
                  var6 = this.footPointX;
                  var2 = var12.x;
                  var7 = this.footPointY;
                  var3 = var12.y;
                  var5 = this.degreeForDraw;
                  if (this.faceDirection) {
                     var11 = false;
                  } else {
                     var11 = true;
                  }

                  this.drawDrawerByDegree(var1, var13, var4, (var6 >> 6) - var2, (var7 >> 6) - var3, var10, var5, var11);
               }
            }
         } else {
            if (var4 != this.drawer.getActionId()) {
               this.drawer.setActionId(var4);
            }

            if (!AnimationDrawer.isAllPause()) {
               this.drawer.moveOn();
               this.tailDrawer.moveOn();
            }
         }

         this.attackRectVec.removeAllElements();
         byte[] var22 = this.drawer.getARect();
         if (this.isAntiGravity) {
            byte[] var14 = this.drawer.getARect();
            if (var14 != null) {
               var22[0] = (byte)(-var14[0] - var14[2]);
            }
         }

         if (var22 != null) {
            if (SonicDebug.showCollisionRect) {
               var1.setColor(65280);
               var3 = this.footPointX;
               byte var17 = var22[0];
               var5 = var12.x;
               var6 = this.footPointY;
               if (this.isAntiGravity) {
                  var2 = -var22[1] - var22[3];
               } else {
                  var2 = var22[1];
               }

               var7 = var12.y;
               byte var8 = var22[2];
               byte var9 = var22[3];
               var1.drawRect((var3 >> 6) + var17 - var5, (var6 >> 6) + var2 - var7, var8, var9);
            }

            PlayerAnimationCollisionRect var15 = this.attackRect;
            byte var19 = var22[0];
            byte var18 = var22[1];
            byte var21 = var22[2];
            byte var20 = var22[3];
            var4 = this.myAnimationID;
            var15.initCollision(var19 << 6, var18 << 6, var21 << 6, var20 << 6, var4);
            this.attackRectVec.addElement(this.attackRect);
         } else {
            this.attackRect.reset();
         }

         if (this.myAnimationID == 50 && this.drawer.checkEnd()) {
            this.animationID = 0;
         } else if (this.drawer.checkEnd() && LOOP_INDEX[this.myAnimationID] >= 0) {
            if (this.animationID == -1) {
               switch(this.myAnimationID) {
               case 11:
                  this.animationID = 0;
                  this.isAttacking = false;
               }
            }

            this.myAnimationID = LOOP_INDEX[this.myAnimationID];
         }
      }

   }

   protected void extraLogicJump() {
      byte var1;
      int var2;
      if (this.myAnimationID == 13 || this.myAnimationID == 14) {
         var2 = this.velY;
         if (this.isAntiGravity) {
            var1 = -1;
         } else {
            var1 = 1;
         }

         this.velY = GameTime.advance(this, "velY", var2, var1 * 30);
         var2 = this.velY;
         if (this.isAntiGravity) {
            var1 = -1;
         } else {
            var1 = 1;
         }

         this.velY = GameTime.advance(this, "velY", var2, -(var1 * this.getGravity()));
      }

      if (this.myAnimationID != 12 && this.myAnimationID != 48 && this.myAnimationID != 49) {
         if (this.animationID == 4 && (this.doJumpForwardly || this.isCrashFallingSand) && Key.press(16777216)) {
            this.animationID = -1;
            this.myAnimationID = 12;
            if (this.isInWater) {
               this.myAnimationID = 48;
            } else {
               soundInstance.playLoopSe(15);
            }

            this.flyCount = GameTime.set(this, "flyCount", 128);
            this.flyUpCoolCount = GameTime.set(this, "flyUpCoolCount", 1);
            var2 = this.velY;
            if (this.isAntiGravity) {
               var1 = -1;
            } else {
               var1 = 1;
            }

            this.velY = var2 + var1 * this.getGravity2();
            this.velY = this.velY * 13 / 16;
            var2 = this.velY;
            if (this.isAntiGravity) {
               var1 = -1;
            } else {
               var1 = 1;
            }

            this.velY = GameTime.advance(this, "velY", var2, -var1 * this.getGravity());
            var2 = this.velY;
            if (this.isAntiGravity) {
               var1 = -1;
            } else {
               var1 = 1;
            }

            this.velY = var2 + (var1 * 30);
         }
      } else {
         this.flyCount = Math.max(0, GameTime.advance(this, "flyCount", this.flyCount, -(1)));
         if (this.flyUpCoolCount != 1) {
            if ((this.isAntiGravity || this.velY < -176) && (!this.isAntiGravity || this.velY > 176)) {
               this.flyUpCoolCount = GameTime.set(this, "flyUpCoolCount", 1);
            } else {
               if (this.myAnimationID != 48 && this.myAnimationID != 49) {
                  var2 = this.velY;
                  if (this.isAntiGravity) {
                     var1 = -1;
                  } else {
                     var1 = 1;
                  }

                  this.velY = GameTime.advance(this, "velY", var2, var1 * -90);
               } else {
                  var2 = this.velY;
                  if (this.isAntiGravity) {
                     var1 = -1;
                  } else {
                     var1 = 1;
                  }

                  this.velY = GameTime.advance(this, "velY", var2, var1 * -450);
               }

               this.flyUpCoolCount = GameTime.advance(this, "flyUpCoolCount", this.flyUpCoolCount, 1);
               if (GameTime.event(this, "flyUpCoolCount", "extraLogicJump:556", GameTime.crosses(this, "flyUpCoolCount", this.flyUpCoolCount, 8))) {
                  this.flyUpCoolCount = GameTime.set(this, "flyUpCoolCount", 1);
               }
            }
         } else {
            if (Key.press(16777216) && (!this.isAntiGravity && this.velY >= -176 || this.isAntiGravity && this.velY <= 176) && this.flyCount > 0) {
               this.flyUpCoolCount = GameTime.set(this, "flyUpCoolCount", 2);
            } else if (Key.press(Key.gSelect | 8388608)) {
               this.animationID = 4;
               this.doJumpForwardly = false;
               if (PlayerObject.soundInstance.getPlayingLoopSeIndex() == 15) {
                  PlayerObject.soundInstance.stopLoopSe();
               }
            }

            var2 = this.velY;
            if (this.isAntiGravity) {
               var1 = -1;
            } else {
               var1 = 1;
            }

            this.velY = GameTime.advance(this, "velY", var2, var1 * 30);
         }

         var2 = this.velY;
         if (this.isAntiGravity) {
            var1 = -1;
         } else {
            var1 = 1;
         }

         this.velY = GameTime.advance(this, "velY", var2, -(var1 * this.getGravity()));
         if (this.isInWater && this.myAnimationID == 12) {
            this.myAnimationID = 48;
         } else if (!this.isInWater) {
            this.myAnimationID = 12;
         }

         if (this.flyCount == 0) {
            this.myAnimationID = 13;
            soundInstance.stopLoopSe();
         } else if (!this.isInWater && !isInWind && !this.isCrashFallingSand && !IsGamePause) {
            soundInstance.playLoopSe(15);
         }
      }

   }

   protected void extraLogicOnObject() {
      this.extraLogicWalk();
   }

   protected void extraLogicWalk() {
      if (this.flyCount > 0) {
         soundInstance.stopLoopSe();
         this.flyCount = GameTime.set(this, "flyCount", 0);
      }

      if (Key.press(Key.gSelect | 8388608) && this.myAnimationID != 11 && this.myAnimationID != 19 && this.collisionState != 1 && this.animationID != 4) {
         this.animationID = -1;
         this.myAnimationID = 11;
         this.drawer.restart();
         soundInstance.playSe(16);
         this.isAttacking = true;
      }

   }

   public boolean flyState() {
      boolean var1;
      if (this.flyCount > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int getGravity() {
      int var1;
      if (this.flyCount == 0) {
         var1 = super.getGravity();
      } else {
         var1 = 0;
      }

      return var1;
   }

   public int getGravity2() {
      return super.getGravity();
   }

   public int getRetPower() {
      int var1;
      if (this.myAnimationID == 11) {
         var1 = MOVE_POWER_REVERSE >> 1;
      } else {
         var1 = super.getRetPower();
      }

      return var1;
   }

   public boolean needRetPower() {
      boolean var1;
      if (this.myAnimationID == 11) {
         var1 = true;
      } else {
         var1 = super.needRetPower();
      }

      return var1;
   }

   public boolean noRotateDraw() {
      boolean var1;
      if (this.myAnimationID == 11) {
         var1 = true;
      } else {
         var1 = super.noRotateDraw();
      }

      return var1;
   }

   public void resetFlyCount() {
      this.flyCount = GameTime.set(this, "flyCount", 0);
   }

   public void stopFly() {
      soundInstance.stopLoopSe();
      this.flyCount = GameTime.set(this, "flyCount", 0);
   }
}
