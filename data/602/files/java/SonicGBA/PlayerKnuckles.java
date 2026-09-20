package SonicGBA;

import GameEngine.time.GameTime;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import Lib.MyAPI;
import Lib.SoundSystem;
import com.sega.engine.action.ACWorld;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import State.TitleState;

public class PlayerKnuckles extends PlayerObject {
   private static final int[] ANIMATION_CONVERT;
   private static final int ATTACK_LEVEL_1 = 1;
   private static final int ATTACK_LEVEL_2 = 2;
   private static final int ATTACK_LEVEL_3 = 3;
   private static final int ATTACK_LEVEL_NONE = 0;
   public static final int COLLISION_STATE_CLIMB = 4;
   private static final int DRIP_SPEED_Y_LIMIT = 128;
   private static final int FALL_ADD_SPEED = 337;
   private static final int FLY_DEGREE_VELOCITY = 20;
   private static final int FLY_DOWN_SPEED = 90;
   private static final int FLY_GROUND_SPEED = 64;
   private static final int FLY_MAX_SPEED = 4608;
   private static final int FLY_MIN_SPEED = 768;
   private static final int FLY_SPEED_PLUS = 22;
   private static final int FLY_START_ADD_Y_SPEED = 384;
   private static final int FLY_START_X_SPEED = 768;
   private static final int FLY_VELOCITY = 600;
   public static final int KNUCKLES_ANI_ATTACK_1 = 11;
   public static final int KNUCKLES_ANI_ATTACK_2 = 12;
   public static final int KNUCKLES_ANI_ATTACK_3 = 13;
   public static final int KNUCKLES_ANI_BANK_1 = 49;
   public static final int KNUCKLES_ANI_BANK_2 = 50;
   public static final int KNUCKLES_ANI_BANK_3 = 51;
   public static final int KNUCKLES_ANI_BAR_MOVE = 47;
   public static final int KNUCKLES_ANI_BAR_STAY = 46;
   public static final int KNUCKLES_ANI_BRAKE = 56;
   public static final int KNUCKLES_ANI_BREATHE = 36;
   public static final int KNUCKLES_ANI_CAUGHT = 60;
   public static final int KNUCKLES_ANI_CELEBRATE_1 = 52;
   public static final int KNUCKLES_ANI_CELEBRATE_2 = 53;
   public static final int KNUCKLES_ANI_CELEBRATE_3 = 54;
   public static final int KNUCKLES_ANI_CLIFF_1 = 37;
   public static final int KNUCKLES_ANI_CLIFF_2 = 38;
   public static final int KNUCKLES_ANI_CLIMB_1 = 29;
   public static final int KNUCKLES_ANI_CLIMB_2 = 30;
   public static final int KNUCKLES_ANI_CLIMB_3 = 31;
   public static final int KNUCKLES_ANI_CLIMB_4 = 32;
   public static final int KNUCKLES_ANI_CLIMB_5 = 33;
   public static final int KNUCKLES_ANI_DEAD_1 = 26;
   public static final int KNUCKLES_ANI_DEAD_2 = 27;
   public static final int KNUCKLES_ANI_ENTER_SP = 58;
   public static final int KNUCKLES_ANI_FLY_1 = 19;
   public static final int KNUCKLES_ANI_FLY_2 = 20;
   public static final int KNUCKLES_ANI_FLY_3 = 21;
   public static final int KNUCKLES_ANI_FLY_4 = 22;
   public static final int KNUCKLES_ANI_HURT_1 = 24;
   public static final int KNUCKLES_ANI_HURT_2 = 25;
   public static final int KNUCKLES_ANI_JUMP = 4;
   public static final int KNUCKLES_ANI_LOOK_UP_1 = 7;
   public static final int KNUCKLES_ANI_LOOK_UP_2 = 8;
   public static final int KNUCKLES_ANI_POLE_H = 45;
   public static final int KNUCKLES_ANI_POLE_V = 44;
   public static final int KNUCKLES_ANI_PUSH_WALL = 23;
   public static final int KNUCKLES_ANI_RAIL_BODY = 57;
   public static final int KNUCKLES_ANI_ROLL_H_1 = 41;
   public static final int KNUCKLES_ANI_ROLL_H_2 = 42;
   public static final int KNUCKLES_ANI_ROLL_V_1 = 39;
   public static final int KNUCKLES_ANI_ROLL_V_2 = 40;
   public static final int KNUCKLES_ANI_RUN = 3;
   public static final int KNUCKLES_ANI_SPIN_1 = 5;
   public static final int KNUCKLES_ANI_SPIN_2 = 6;
   public static final int KNUCKLES_ANI_SPRING_1 = 14;
   public static final int KNUCKLES_ANI_SPRING_2 = 15;
   public static final int KNUCKLES_ANI_SPRING_3 = 16;
   public static final int KNUCKLES_ANI_SPRING_4 = 17;
   public static final int KNUCKLES_ANI_SPRING_5 = 18;
   public static final int KNUCKLES_ANI_SQUAT_1 = 9;
   public static final int KNUCKLES_ANI_SQUAT_2 = 10;
   public static final int KNUCKLES_ANI_STAND = 0;
   public static final int KNUCKLES_ANI_SWIM_1 = 28;
   public static final int KNUCKLES_ANI_SWIM_2 = 34;
   public static final int KNUCKLES_ANI_SWIM_3 = 35;
   public static final int KNUCKLES_ANI_SWIM_EFFECT = 59;
   public static final int KNUCKLES_ANI_UP_ARM = 43;
   public static final int KNUCKLES_ANI_VS_KNUCKLE = 55;
   public static final int KNUCKLES_ANI_WAITING_1 = 61;
   public static final int KNUCKLES_ANI_WAITING_2 = 62;
   public static final int KNUCKLES_ANI_WALK_1 = 1;
   public static final int KNUCKLES_ANI_WALK_2 = 2;
   public static final int KNUCKLES_ANI_WIND = 48;
   private static final int KNUCKLES_ATTACK_1_COUNT = 4;
   private static final int KNUCKLES_ATTACK_2_COUNT = 4;
   private static final int KNUCKLES_ATTACK_3_COUNT = 6;
   private static final int LOOP = -1;
   private static final int[] LOOP_INDEX;
   private static final int NORMAL_FRAME_INTERVAL = 3;
   private static final int NO_ANIMATION = -1;
   private static final int NO_LOOP = -2;
   private static final int NO_LOOP_DEPAND = -2;
   private static final int PUNCH_MOVE01 = 768;
   private static final int UPPER_INWATER_SPEED = 832;
   private static final int UPPER_SPEED = 719;
   private static final int WALL_CLIMB_SPEED = 192;
   private static final int WALL_CLIMB_SPEED_W = 256;
   private static final int WATER_FRAME_INTERVAL = 6;
   private static int slipbrakeFrame;
   private int attackCount;
   private int attackLevel;
   private int attackLevelNext;
   private PlayerAnimationCollisionRect attackRect;
   private AnimationDrawer effectDrawer;
   private boolean floating = false;
   private int flyDegree;
   private int flyDegreeStable;
   private int flySpeed;
   public boolean flying;
   private boolean isSlipActived;
   private AnimationDrawer knucklesDrawer1;
   private AnimationDrawer knucklesDrawer2;
   private boolean preWaterFlag;
   private boolean swimWaterEffectFlag = false;
   private int waterframe;

   static {
      int[] var0 = new int[]{0, 1, 2, 3, 4, 10, 5, 6, 23, 14, 18, -1, 25, 44, 15, -1, -1, 56, -1, -1, -1, 57, 41, 42, 43, 39, 40, 46, 47, 48, 25, 45, 49, 50, 51, 52, 53, 54, 7, 8, 7, 27, 16, 17, 25, 26, 9, 37, 38, 36, 61, 62, 60, 55};
      ANIMATION_CONVERT = var0;
      var0 = new int[]{-1, -1, -1, -1, -1, -1, -1, 8, -1, -2, -1, 0, 0, 18, 17, -1, 17, 18, -1, -1, -1, -1, -1, -1, 25, -1, 27, -1, -1, 30, -1, -1, -1, 0, -1, -1, -2, -1, -1, 40, 39, 42, 41, -1, 15, 3, -1, -1, -1, -1, -1, -1, 53, -1, -2, 0, -1, -1, -1, -1, 62, 62, -1, 0};
      LOOP_INDEX = var0;
   }

   public PlayerKnuckles() {
      MFImage var1 = MFImage.createImage("/animation/player/chr_knuckles" + (TitleState.characterslots > 2 ? "_slot" + TitleState.characterslots : "") + ".png");
      Animation var2 = new Animation(var1, "/animation/player/chr_knuckles_01");
      this.knucklesDrawer1 = var2.getDrawer();
      this.effectDrawer = var2.getDrawer();
      Animation var3 = new Animation(var1, "/animation/player/chr_knuckles_02");
      this.knucklesDrawer2 = var3.getDrawer();
      this.drawer = this.knucklesDrawer1;
      this.attackRect = new PlayerAnimationCollisionRect(this);
   }

   private void inputLogicClimb() {
      this.animationID = -1;
      this.velX = 0;
      this.velY = 0;
      if (this.myAnimationID != 33) {
         this.myAnimationID = 30;
         short var1;
         SoundSystem var7;
         SoundSystem var8;
         byte var9;
         if ((Key.repeat(4 | Key.gUp | 33554432)) && !this.isAntiGravity || Key.repeat(Key.gDown) && this.isAntiGravity) {
            if (!this.isInWater) {
               this.waterframe = GameTime.advance(this, "waterframe", this.waterframe, 1);
               this.waterframe = GameTime.wrap(this, "waterframe", this.waterframe, 3);
               if (GameTime.event(this, "waterframe", "inputLogicClimb:162", GameTime.crosses(this, "waterframe", this.waterframe, 1))) {
                  var8 = soundInstance;
                  var7 = soundInstance;
                  var8.playSequenceSe(18);
               }
            } else {
               this.waterframe = GameTime.advance(this, "waterframe", this.waterframe, 1);
               this.waterframe = GameTime.wrap(this, "waterframe", this.waterframe, 6);
               if (GameTime.event(this, "waterframe", "inputLogicClimb:170", GameTime.crosses(this, "waterframe", this.waterframe, 1))) {
                  var8 = soundInstance;
                  var7 = soundInstance;
                  var8.playSequenceSe(18);
               }
            }

            if (this.isInWater) {
               var1 = -256;
            } else {
               var1 = -192;
            }

            this.velY = var1;
            if (this.isAntiGravity) {
               var9 = 32;
            } else {
               var9 = 31;
            }

            this.myAnimationID = var9;
         }

         if (Key.repeat(Key.gDown) && !this.isAntiGravity || (Key.repeat(4 | Key.gUp | 33554432)) && this.isAntiGravity) {
            if (!this.isInWater) {
               this.waterframe = GameTime.advance(this, "waterframe", this.waterframe, 1);
               this.waterframe = GameTime.wrap(this, "waterframe", this.waterframe, 3);
               if (GameTime.event(this, "waterframe", "inputLogicClimb:197", GameTime.crosses(this, "waterframe", this.waterframe, 1))) {
                  var7 = soundInstance;
                  var8 = soundInstance;
                  var7.playSequenceSe(18);
               }
            } else {
               this.waterframe = GameTime.advance(this, "waterframe", this.waterframe, 1);
               this.waterframe = GameTime.wrap(this, "waterframe", this.waterframe, 6);
               if (GameTime.event(this, "waterframe", "inputLogicClimb:205", GameTime.crosses(this, "waterframe", this.waterframe, 1))) {
                  var7 = soundInstance;
                  var8 = soundInstance;
                  var7.playSequenceSe(18);
               }
            }

            short var10;
            if (this.isInWater) {
               var10 = 256;
            } else {
               var10 = 192;
            }

            this.velY = var10;
            if (this.isAntiGravity) {
               var9 = 31;
            } else {
               var9 = 32;
            }

            this.myAnimationID = var9;
         }

         byte var12;
         if (Key.press(16777216)) {
            soundInstance.stopLoopSe();
            this.collisionState = 1;
            if (this.isAntiGravity) {
               var1 = 672;
            } else {
               var1 = -672;
            }

            this.velY = var1;
            if (this.faceDirection ^ this.isAntiGravity) {
               var12 = 1;
            } else {
               var12 = -1;
            }

            this.velX = var12 * -768;
            boolean var6;
            if (this.faceDirection) {
               var6 = false;
            } else {
               var6 = true;
            }

            this.faceDirection = var6;
            this.animationID = 4;
         } else {
            int var13;
            if (this.myAnimationID != 31 && this.myAnimationID != 32) {
               var13 = soundInstance.getPlayingLoopSeIndex();
               var7 = soundInstance;
               if (var13 == 18) {
                  soundInstance.stopLoopSe();
               }
            }

            int var2 = this.posX;
            if (this.faceDirection ^ this.isAntiGravity) {
               var12 = 1;
            } else {
               var12 = -1;
            }

            int var3 = var2 + var12 * 1024;
            var2 = this.posY;
            if (this.isAntiGravity) {
               var12 = 1;
            } else {
               var12 = -1;
            }

            int var4 = var2 + var12 * (this.getCollisionRectHeight() >> 1) + this.velY;
            if (var4 >= (MapManager.getPixelHeight() << 6) - 1536 && this.isAntiGravity) {
               this.velY = 0;
            } else {
               ACWorld var14 = this.worldInstance;
               var2 = this.currentLayer;
               if (this.faceDirection ^ this.isAntiGravity) {
                  var9 = 3;
               } else {
                  var9 = 1;
               }

               var13 = var14.getWorldX(var3, var4, var2, var9);
               if (var13 == -1000) {
                  var2 = this.posX;
                  if (this.faceDirection ^ this.isAntiGravity) {
                     var12 = 1;
                  } else {
                     var12 = -1;
                  }

                  var2 += var12 * 1024;
                  var4 = this.posY;
                  var14 = this.worldInstance;
                  var3 = this.currentLayer;
                  if (this.isAntiGravity) {
                     var9 = 2;
                  } else {
                     var9 = 0;
                  }

                  var3 = var14.getWorldY(var2, var4, var3, var9);
                  if (var3 != -1000) {
                     if (this.faceDirection ^ this.isAntiGravity) {
                        var12 = 1;
                     } else {
                        var12 = -1;
                     }

                     var13 = var2 - var12 * (this.worldInstance.getTileWidth() >> 1);
                     this.posX = var13;
                     this.footPointX = var13;
                     this.posY = var3;
                     this.footPointY = var3;
                     this.myAnimationID = 33;
                     soundInstance.stopLoopSe();
                     this.velX = 0;
                     this.velY = 0;
                  } else {
                     this.collisionState = 1;
                     this.animationID = 1;
                     soundInstance.stopLoopSe();
                  }
               } else {
                  var14 = this.worldInstance;
                  if (this.isAntiGravity) {
                     var1 = 1536;
                  } else {
                     var1 = -1536;
                  }

                  int var5 = this.currentLayer;
                  byte var11;
                  if (this.faceDirection ^ this.isAntiGravity) {
                     var11 = 3;
                  } else {
                     var11 = 1;
                  }

                  var2 = var14.getWorldX(var3, var1 + var4, var5, var11);
                  if (this.faceDirection ^ this.isAntiGravity) {
                     var12 = 1;
                  } else {
                     var12 = -1;
                  }

                  var13 = var2 - var12 * 512;
                  if (var13 >= 0 && (var13 - this.posX > 0 && !(this.faceDirection ^ this.isAntiGravity) || var13 - this.posX < 0 && this.faceDirection ^ this.isAntiGravity) && (this.velY < 0 && !this.isAntiGravity || this.velY > 0 && this.isAntiGravity)) {
                     this.velY = 0;
                  }

                  var14 = this.worldInstance;
                  if (this.isAntiGravity) {
                     var1 = 1200;
                  } else {
                     var1 = -1200;
                  }

                  var5 = this.currentLayer;
                  if (this.faceDirection ^ this.isAntiGravity) {
                     var11 = 3;
                  } else {
                     var11 = 1;
                  }

                  var2 = var14.getWorldX(var3, var4 - var1, var5, var11);
                  if (this.faceDirection ^ this.isAntiGravity) {
                     var12 = 1;
                  } else {
                     var12 = -1;
                  }

                  if (var2 - var12 * 512 != this.posX && (this.velY > 0 && !this.isAntiGravity || this.velY < 0 && this.isAntiGravity)) {
                     this.collisionState = 1;
                     this.animationID = 1;
                     soundInstance.stopLoopSe();
                  }
               }
            }
         }
      }

   }

   private void slipBrakeNoise() {
      if (!this.isSlipActived) {
         SoundSystem var1 = soundInstance;
         SoundSystem var2 = soundInstance;
         var1.playSequenceSe(6);
         this.isSlipActived = true;
      }

   }

   public boolean beAccelerate(int var1, boolean var2, GameObject var3) {
      if (this.myAnimationID == 22) {
         var2 = false;
      } else {
         var2 = super.beAccelerate(var1, var2, var3);
      }

      return var2;
   }

   public void beSpring(int var1, int var2) {
      if ((this.myAnimationID == 21 || this.myAnimationID == 22) && (var2 == 3 || var2 == 2)) {
         this.flying = false;
         this.animationID = 0;
      }

      super.beSpring(var1, var2);
   }

   public void beStop(int var1, int var2, GameObject var3) {
      super.beStop(var1, var2, var3);
      var1 = var2;
      if (this.isAntiGravity) {
         if (var2 == 1) {
            var1 = 0;
         } else {
            var1 = var2;
            if (var2 == 0) {
               var1 = 1;
            }
         }
      }

      if (var1 == 1 && this.flying && this.hurtCount == 0) {
         this.animationID = -1;
         this.myAnimationID = 22;
      }

   }

   public boolean canDoJump() {
      boolean var1;
      if (this.myAnimationID != 11 && this.myAnimationID != 12) {
         var1 = super.canDoJump();
      } else {
         var1 = false;
      }

      return var1;
   }

   public void closeImpl() {
      Animation.closeAnimationDrawer(this.knucklesDrawer1);
      this.knucklesDrawer1 = null;
      Animation.closeAnimationDrawer(this.knucklesDrawer2);
      this.knucklesDrawer2 = null;
      Animation.closeAnimationDrawer(this.effectDrawer);
      this.effectDrawer = null;
   }

   public void doHurt() {
      if (this.floating) {
         this.floating = false;
      }

      super.doHurt();
   }

   public void doJump() {
      this.attackLevel = 0;
      this.attackLevelNext = 0;
      this.attackCount = GameTime.set(this, "attackCount", 0);
      super.doJump();
   }

   public void doWhileLand(int var1) {
      super.doWhileLand(var1);
      if (this.flying && this.hurtCount == 0) {
         this.animationID = -1;
         this.myAnimationID = 22;
      }

   }

   public void doWhileTouchWorld(int var1, int var2) {
      super.doWhileTouchWorld(var1, var2);
      if (this.flying && (var2 == 90 || var2 == 270) && (var1 == 1 && this.faceDirection || var1 == 3 && !this.faceDirection)) {
         this.collisionState = 4;
         this.flying = false;
         this.animationID = -1;
         this.myAnimationID = 29;
         this.worldCal.stopMove();
         var1 = this.posY + 256;
         this.footPointY = var1;
         this.posX = var1;
      }

   }

   public void drawCharacter(MFGraphics var1) {
      Coordinate var12 = MapManager.getCamera();
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

         this.drawer = this.knucklesDrawer1;
         int var4 = this.myAnimationID;
         int var2;
         if (this.myAnimationID >= 61) {
            this.drawer = this.knucklesDrawer2;
            var2 = this.myAnimationID - 61;
            var4 = var2;
            if (this.myAnimationID == 61) {
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
         } else {
            this.drawer.setSpeed(1, 1);
         }

         int var3;
         int var5;
         if (this.hurtCount % 2 == 0) {
            int var6;
            int var7;
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
               var7 = var12.x;
               var6 = var12.y;
               var13.draw(var1, var4, (var5 >> 6) - var7, (var3 >> 6) - var6, var10, var16);
            } else {
               boolean var11;
               if (this.animationID != 6 && this.animationID != 7) {
                  if (this.myAnimationID != 19 && this.myAnimationID != 20 && this.myAnimationID != 21 && this.myAnimationID != 28) {
                     if (this.myAnimationID == 37 || this.myAnimationID == 38 || this.myAnimationID == 7 || this.myAnimationID == 8) {
                        this.degreeForDraw = this.degreeStable;
                        this.faceDegree = this.degreeStable;
                     }

                     if (this.myAnimationID == 56) {
                        this.degreeForDraw = this.degreeStable;
                     }

                     if (this.myAnimationID != 1 && this.myAnimationID != 2 && this.myAnimationID != 3 && this.myAnimationID != 60) {
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
                        var6 = this.footPointX;
                        var3 = -this.collisionRect.getHeight();
                        var5 = this.faceDegree;
                        var3 = this.getNewPointX(var6, 0, var3 >> 1, var5);
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
                        var7 = this.footPointX;
                        var5 = var12.x;
                        var2 = this.footPointY;
                        var3 = var12.y;
                        var6 = this.degreeForDraw;
                        if (this.faceDirection) {
                           var11 = false;
                        } else {
                           var11 = true;
                        }

                        this.drawDrawerByDegree(var1, var13, var4, (var7 >> 6) - var5, (var2 >> 6) - var3, var10, var6, var11);
                     }
                  } else {
                     if (this.flyDegree >= 0) {
                        if (this.isAntiGravity) {
                           var16 = 1;
                        } else {
                           var16 = 0;
                        }
                     } else if (this.isAntiGravity) {
                        var16 = 3;
                     } else {
                        var16 = 2;
                     }

                     var13 = this.drawer;
                     var7 = this.footPointX;
                     var5 = var12.x;
                     var6 = this.footPointY;
                     var3 = var12.y;
                     var13.draw(var1, var4, (var7 >> 6) - var5, (var6 >> 6) - var3, var10, var16);
                  }

                  if (this.myAnimationID == 34 && this.swimWaterEffectFlag) {
                     var3 = StageManager.getWaterLevel();
                     var13 = this.effectDrawer;
                     var5 = this.footPointX;
                     var2 = var12.x;
                     var6 = var12.y;
                     var4 = this.getTrans();
                     var13.draw(var1, 59, (var5 >> 6) - var2, (var3 << 6 >> 6) + 14 - var6, true, var4);
                  }
               } else {
                  var13 = this.drawer;
                  var2 = this.footPointX;
                  var3 = var12.x;
                  var7 = this.footPointY;
                  var5 = var12.y;
                  var6 = this.degreeForDraw;
                  if (this.faceDirection) {
                     var11 = false;
                  } else {
                     var11 = true;
                  }

                  this.drawDrawerByDegree(var1, var13, var4, (var2 >> 6) - var3, (var7 >> 6) - var5, var10, var6, var11);
               }
            }
         } else {
            if (var4 != this.drawer.getActionId()) {
               this.drawer.setActionId(var4);
            }

            if (!AnimationDrawer.isAllPause()) {
               this.drawer.moveOn();
            }
         }

         this.attackRectVec.removeAllElements();
         byte[] var22 = this.drawer.getARect();
         if (this.isAntiGravity) {
            byte[] var14 = this.drawer.getARect();
            if (var14 != null) {
               if (this.attackLevel != 0) {
                  var22[0] = (byte)(-var14[0] - var14[2]);
               } else {
                  if (this.flying && this.faceDirection) {
                     var22[0] = (byte)(-var14[0] - var14[2]);
                  }

                  var22[1] = (byte)(-var14[1] - var14[3]);
               }
            }
         }

         if (var22 != null) {
            byte var19;
            if (SonicDebug.showCollisionRect) {
               var1.setColor(65280);
               var4 = this.footPointX;
               var19 = var22[0];
               var3 = var12.x;
               var5 = this.footPointY;
               if (this.isAntiGravity) {
                  var2 = -var22[1] - var22[3];
               } else {
                  var2 = var22[1];
               }

               int var9 = var12.y;
               byte var20 = var22[2];
               byte var8 = var22[3];
               var1.drawRect((var4 >> 6) + var19 - var3, (var5 >> 6) + var2 - var9, var20, var8);
            }

            PlayerAnimationCollisionRect var15 = this.attackRect;
            var19 = var22[0];
            byte var18 = var22[1];
            byte var17 = var22[2];
            byte var21 = var22[3];
            var5 = this.myAnimationID;
            var15.initCollision(var19 << 6, var18 << 6, var17 << 6, var21 << 6, var5);
            this.attackRectVec.addElement(this.attackRect);
         } else {
            this.attackRect.reset();
         }

         if (this.animationID == -1 && this.drawer.checkEnd() && LOOP_INDEX[this.myAnimationID] >= 0) {
            switch(this.myAnimationID) {
            case 33:
               this.canAttackByHari = true;
               this.collisionState = 1;
               this.worldCal.actionState = 1;
            default:
               this.myAnimationID = LOOP_INDEX[this.myAnimationID];
            }
         }
      }

   }

   public void dripDownUnderWater() {
      if (this.floating) {
         this.floating = false;
         this.animationID = 10;
      }

   }

   protected void extraInputLogic() {
      this.swimWaterEffectFlag = false;
      switch(this.collisionState) {
      case 4:
         this.inputLogicClimb();
      default:
      }
   }

   protected void extraLogicJump() {
      boolean var9;
      if (this.myAnimationID != 19 && this.myAnimationID != 20 && this.myAnimationID != 21 && this.myAnimationID != 28) {
         var9 = false;
      } else {
         var9 = true;
      }

      this.flying = var9;
      byte var5;
      int var10;
      SoundSystem var11;
      SoundSystem var12;
      if (this.animationID == 4 && this.doJumpForwardly && Key.press(Key.gSelect | 8388608)) {
         this.animationID = -1;
         this.myAnimationID = 13;
         this.worldCal.actionState = 1;
         this.collisionState = 1;
         short var13;
         if (this.isInWater) {
            var13 = 832;
         } else {
            var13 = 719;
         }

         byte var14;
         if (this.isAntiGravity) {
            var14 = -1;
         } else {
            var14 = 1;
         }

         this.velY = var14 * -var13;

         if (this.isInWater) {
            var5 = 2;
         } else {
            var5 = 1;
         }

         this.attackCount = GameTime.set(this, "attackCount", var5 * 6);
         var11 = soundInstance;
         var12 = soundInstance;
         var11.playSe(20);
      }
      if (this.flying) {
         if (!Key.repeat(16777216)) {
            this.flying = false;
            this.myAnimationID = 17;
            this.velX >>= 2;
         } else {
            if (this.flySpeed < 768) {
               this.flySpeed = GameTime.advance(this, "flySpeed", this.flySpeed, 22);
            } else if (this.flySpeed < 4608 && (this.flyDegree == 90 || this.flyDegree == -90)) {
               this.flySpeed = GameTime.advance(this, "flySpeed", this.flySpeed, 11);
            }

            if (this.isAntiGravity ^ this.faceDirection) {
               var5 = 90;
            } else {
               var5 = -90;
            }

            this.flyDegreeStable = var5;
            double var1 = (double)this.flyDegree;
            double var3 = (double)this.flyDegreeStable;
            this.flyDegree = MyAPI.calNextPosition(this, "flyDegree", var1, var3, 1, 100, 20.0D);
            this.velX = this.flySpeed * MyAPI.dSin(this.flyDegree) / 100;
            if (this.isAntiGravity) {
               if (this.velY > -128) {
                  this.velY = GameTime.advance(this, "velY", this.velY, -(90));
               } else {
                  this.velY = GameTime.advance(this, "velY", this.velY, 90);
               }

               this.velY = GameTime.advance(this, "velY", this.velY, this.getGravity());
            } else {
               if (this.velY < 128) {
                  this.velY = GameTime.advance(this, "velY", this.velY, 90);
               } else {
                  this.velY = GameTime.advance(this, "velY", this.velY, -(90));
               }

               this.velY = GameTime.advance(this, "velY", this.velY, -(this.getGravity()));
            }

            if (Math.abs(this.flyDegree) >= 75) {
               this.myAnimationID = 19;
               if (this.isInWater) {
                  this.myAnimationID = 28;
               }
            } else if (Math.abs(this.flyDegree) >= 25) {
               this.myAnimationID = 20;
            } else {
               this.myAnimationID = 21;
            }
         }
      } else if (this.animationID == 4 && this.doJumpForwardly && Key.press(16777216)) {
         this.animationID = -1;
         this.myAnimationID = 19;
         if (this.isInWater) {
            this.myAnimationID = 28;
         }

         if (this.isAntiGravity ^ this.faceDirection) {
            var5 = 90;
         } else {
            var5 = -90;
         }

         this.flyDegreeStable = var5;
         this.velY = 0;
         this.flyDegree = this.flyDegreeStable;
         this.flying = true;
         this.velY = this.velY + (384);
         if (this.velY < 0) {
            this.velY = 0;
         }

         this.velY = GameTime.advance(this, "velY", this.velY, -this.getGravity());
         this.flySpeed = 768;
         var10 = this.footPointY - 512;
         this.footPointY = var10;
         this.posY = var10;
      }

      this.floatchk();
      var10 = StageManager.getWaterLevel() << 6;
      if (this.floating) {
         this.breatheCount = 0;
      }

      if (this.floating && this.isInWater) {
         if (Key.press(Key.gDown)) {
            this.floating = false;
            this.animationID = 10;
         } else if (Key.press(16777216)) {
            this.doJump();
            this.velY = this.velY * 3 / 5;
            this.floating = false;
         } else {
            int var8 = this.posY;
            int var6 = -this.collisionRect.getHeight();
            int var7 = this.faceDegree;
            var6 = this.getNewPointY(var8, 0, var6 >> 1, var7);
            this.animationID = -1;
            if (Key.repeat(4)) {
               this.myAnimationID = 35;
               this.focusMovingState = 1;
            } else if (var6 - 320 <= var10) {
               this.myAnimationID = 34;
            }

            if (this.floating) {
               this.velY = GameTime.advance(this, "velY", this.velY, -(this.getGravity()));
               this.velY = GameTime.advance(this, "velY", this.velY, -(337));
               this.velY = Math.max(-1920, this.velY);
               this.velY = Math.max(var10 - var6, this.velY);
            }

            this.swimWaterEffectFlag = false;
            if (Math.abs(var6 - var10) < 640) {
               this.swimWaterEffectFlag = true;
            } else {
               this.resetBreatheCount();
            }
         }
      }

   }

   protected void extraLogicOnObject() {
      this.flying = false;
      byte var1;
      int var3;
      if (this.myAnimationID == 22) {
         if (this.velX == 0) {
            this.animationID = 0;
            this.isSlipActived = false;
         } else if (Key.repeat(16777216) && (this.faceDegree < 45 || this.faceDegree > 315 || this.isAntiGravity) && (this.faceDegree >= 45 || this.faceDegree <= 315 || !this.isAntiGravity)) {
            int var2 = this.velX;
            var3 = this.velX;
            if (this.faceDirection) {
               var1 = 1;
            } else {
               var1 = -1;
            }

            this.velX = GameTime.advance(this, "velX", var3, -(var1 * 64));
            if (this.velX * var2 <= 0) {
               this.velX = 0;
            }

            Effect.showEffect(this.dustEffectAnimation, 2, this.posX >> 6, this.posY >> 6, 0);
            this.slipBrakeNoise();
         } else {
            this.animationID = 0;
            this.velX = 0;
            this.isSlipActived = false;
         }
      }

      if (this.attackCount > 0) {
         this.attackCount = Math.max(0, GameTime.advance(this, "attackCount", this.attackCount, -(1)));
      }

      SoundSystem var4;
      SoundSystem var5;
      byte var8;
      switch(this.attackLevel) {
      case 0:
         if ((this.animationID == 0 || this.animationID == 1 || this.animationID == 2 || this.animationID == 3 || this.animationID == 47 || this.animationID == 48 || this.animationID == 5) && Key.press(Key.gSelect | 8388608) && this.myAnimationID != 23) {
            this.attackLevel = 1;
            this.animationID = -1;
            this.myAnimationID = 11;
            if (this.isAntiGravity ^ this.faceDirection) {
               var1 = 1;
            } else {
               var1 = -1;
            }

            this.velX = var1 * 768;
            if (this.isInWater) {
               var8 = 2;
            } else {
               var8 = 1;
            }

            this.attackCount = GameTime.set(this, "attackCount", var8 * 4);
            this.attackLevelNext = 0;
            var5 = soundInstance;
            var4 = soundInstance;
            var5.playSe(19);
            this.drawer.setActionId(11);
         }
         break;
      case 1:
         if (this.attackCount == 0) {
            this.attackLevel = this.attackLevelNext;
            switch(this.attackLevelNext) {
            case 0:
               this.animationID = 0;
            case 1:
            default:
               break;
            case 2:
               this.animationID = -1;
               this.myAnimationID = 12;
               if (this.isAntiGravity ^ this.faceDirection) {
                  var1 = 1;
               } else {
                  var1 = -1;
               }

               this.velX = var1 * 768;
               if (this.isInWater) {
                  var8 = 2;
               } else {
                  var8 = 1;
               }

               this.attackCount = GameTime.set(this, "attackCount", var8 * 4);
               var4 = soundInstance;
               var5 = soundInstance;
               var4.playSe(19);
            }

            this.attackLevelNext = 0;
         } else if (Key.press(Key.gSelect | 8388608)) {
            this.attackLevelNext = 2;
         }
         break;
      case 2:
         if (this.attackCount == 0) {
            this.attackLevel = this.attackLevelNext;
            switch(this.attackLevelNext) {
            case 0:
               this.animationID = 0;
            case 1:
            case 2:
            default:
               break;
            case 3:
               this.animationID = -1;
               this.myAnimationID = 13;
               this.worldCal.actionState = 1;
               this.collisionState = 1;
               short var6;
               if (this.isInWater) {
                  var6 = 832;
               } else {
                  var6 = 719;
               }

               var3 = this.velY;
               byte var7;
               if (this.isAntiGravity) {
                  var7 = -1;
               } else {
                  var7 = 1;
               }

               this.velY = var3 + var7 * -var6;
               if (this.isAntiGravity ^ this.faceDirection) {
                  var7 = 1;
               } else {
                  var7 = -1;
               }

               this.velX = var7 * var6;
               if (this.isInWater) {
                  var8 = 2;
               } else {
                  var8 = 1;
               }

               this.attackCount = GameTime.set(this, "attackCount", var8 * 6);
               var4 = soundInstance;
               var5 = soundInstance;
               var4.playSe(20);
            }

            this.attackLevelNext = 0;
         } else if (Key.press(Key.gSelect | 8388608)) {
            this.attackLevelNext = 3;
         }
         break;
      case 3:
         if (this.animationID != -1) {
            this.attackLevel = 0;
         }
      }

      if (this.attackLevel == 0) {
         this.isAttacking = false;
      } else {
         this.isAttacking = true;
      }

   }

   protected void extraLogicWalk() {
      this.flying = false;
      this.floating = false;
      byte var1;
      int var3;
      if (this.myAnimationID == 22) {
         if (this.totalVelocity == 0) {
            this.animationID = 0;
            this.isSlipActived = false;
         } else if (Key.repeat(16777216) && (this.faceDegree < 45 || this.faceDegree > 315 || this.isAntiGravity) && (this.faceDegree >= 45 || this.faceDegree <= 315 || !this.isAntiGravity)) {
            if (this.faceDirection) {
               this.totalVelocity = Math.abs(this.totalVelocity);
            } else {
               this.totalVelocity = -Math.abs(this.totalVelocity);
            }

            var3 = this.totalVelocity;
            int var2 = this.totalVelocity;
            if (this.faceDirection) {
               var1 = 1;
            } else {
               var1 = -1;
            }

            this.totalVelocity = GameTime.advance(this, "totalVelocity", var2, -(var1 * 64));
            if (this.totalVelocity * var3 <= 0) {
               this.totalVelocity = 0;
            }

            Effect.showEffect(this.dustEffectAnimation, 2, this.posX >> 6, this.posY >> 6, 0);
            this.slipBrakeNoise();
         } else {
            this.animationID = 0;
            this.velX = 0;
            this.isSlipActived = false;
         }
      }

      if (this.attackCount > 0) {
         this.attackCount = Math.max(0, GameTime.advance(this, "attackCount", this.attackCount, -(1)));
      }

      SoundSystem var4;
      SoundSystem var5;
      byte var8;
      switch(this.attackLevel) {
      case 0:
         if ((this.animationID == 0 || this.animationID == 1 || this.animationID == 2 || this.animationID == 3 || this.animationID == 47 || this.animationID == 48 || this.animationID == 5) && Key.press(Key.gSelect | 8388608) && this.myAnimationID != 23) {
            this.attackLevel = 1;
            this.animationID = -1;
            this.myAnimationID = 11;
            this.calDivideVelocity();
            if (this.isAntiGravity ^ this.faceDirection) {
               var1 = 1;
            } else {
               var1 = -1;
            }

            this.velX = var1 * 768;
            this.calTotalVelocity();
            if (this.isInWater) {
               var8 = 2;
            } else {
               var8 = 1;
            }

            this.attackCount = GameTime.set(this, "attackCount", var8 * 4);
            this.attackLevelNext = 0;
            var5 = soundInstance;
            var4 = soundInstance;
            var5.playSe(19);
            this.drawer.setActionId(11);
         }
         break;
      case 1:
         if (this.attackCount == 0) {
            this.attackLevel = this.attackLevelNext;
            switch(this.attackLevelNext) {
            case 0:
               this.animationID = 0;
            case 1:
            default:
               break;
            case 2:
               this.animationID = -1;
               this.myAnimationID = 12;
               this.calDivideVelocity();
               if (this.isAntiGravity ^ this.faceDirection) {
                  var1 = 1;
               } else {
                  var1 = -1;
               }

               this.velX = var1 * 768;
               this.calTotalVelocity();
               if (this.isInWater) {
                  var8 = 2;
               } else {
                  var8 = 1;
               }

               this.attackCount = GameTime.set(this, "attackCount", var8 * 4);
               var5 = soundInstance;
               var4 = soundInstance;
               var5.playSe(19);
            }

            this.attackLevelNext = 0;
         } else if (Key.press(Key.gSelect | 8388608)) {
            this.attackLevelNext = 2;
         }
         break;
      case 2:
         if (this.attackCount == 0) {
            this.attackLevel = this.attackLevelNext;
            switch(this.attackLevelNext) {
            case 0:
               this.animationID = 0;
            case 1:
            case 2:
            default:
               break;
            case 3:
               this.animationID = -1;
               this.myAnimationID = 13;
               this.worldCal.actionState = 1;
               this.collisionState = 1;
               short var6;
               if (this.isInWater) {
                  var6 = 832;
               } else {
                  var6 = 719;
               }

               var3 = this.velY;
               byte var7;
               if (this.isAntiGravity) {
                  var7 = -1;
               } else {
                  var7 = 1;
               }

               this.velY = var3 + var7 * -var6;
               if (this.isAntiGravity ^ this.faceDirection) {
                  var7 = 1;
               } else {
                  var7 = -1;
               }

               this.velX = var7 * var6;
               if (this.isInWater) {
                  var8 = 2;
               } else {
                  var8 = 1;
               }

               this.attackCount = GameTime.set(this, "attackCount", var8 * 6);
               var5 = soundInstance;
               var4 = soundInstance;
               var5.playSe(20);
            }

            this.attackLevelNext = 0;
         } else if (Key.press(Key.gSelect | 8388608)) {
            this.attackLevelNext = 3;
         }
         break;
      case 3:
         if (this.animationID != -1) {
            this.attackLevel = 0;
         }
      }

      this.preWaterFlag = this.isInWater;
      if (this.isBodyCenterOutOfWater()) {
         this.floating = false;
      }

      if (this.attackLevel == 0) {
         this.isAttacking = false;
      } else {
         this.isAttacking = true;
      }

   }

   public void floatchk() {
      if (!this.preWaterFlag && this.isInWater && !this.flying) {
         this.floating = true;
      }

      this.preWaterFlag = this.isInWater;
   }

   public int getCollisionRectHeight() {
      int var1;
      if (this.animationID == -1 && (this.myAnimationID == 19 || this.myAnimationID == 20 || this.myAnimationID == 21 || this.myAnimationID == 28)) {
         var1 = 1024;
      } else if (this.collisionState == 4 && this.myAnimationID != 33) {
         var1 = 1280;
      } else {
         var1 = super.getCollisionRectHeight();
      }

      return var1;
   }

   public int getCollisionRectWidth() {
      int var1;
      if (this.animationID != -1 || this.myAnimationID != 19 && this.myAnimationID != 20 && this.myAnimationID != 21 && this.myAnimationID != 28) {
         var1 = super.getCollisionRectWidth();
      } else {
         var1 = 1280;
      }

      return var1;
   }

   public int getRetPower() {
      int var1;
      if (this.attackLevel != 0 && this.attackLevel != 3) {
         var1 = 288;
      } else {
         var1 = super.getRetPower();
      }

      return var1;
   }

   public int getSlopeGravity() {
      int var1;
      if (this.myAnimationID == 22) {
         var1 = 0;
      } else {
         var1 = super.getSlopeGravity();
      }

      return var1;
   }

   public boolean needRetPower() {
      boolean var1;
      if ((this.attackLevel == 0 || this.attackLevel == 3) && this.myAnimationID != 22) {
         var1 = super.needRetPower();
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean noRotateDraw() {
      boolean var1;
      if (this.myAnimationID != 11 && this.myAnimationID != 12 && this.myAnimationID != 13) {
         var1 = super.noRotateDraw();
      } else {
         var1 = true;
      }

      return var1;
   }

   public void refreshCollisionRectWrap() {
      super.refreshCollisionRectWrap();
      if (this.animationID == -1 && (this.myAnimationID == 19 || this.myAnimationID == 20 || this.myAnimationID == 21 || this.myAnimationID == 28)) {
         this.checkPositionX = this.getNewPointX(this.footPointX, 0, -1024 >> 1, 0) + 0;
         int var2 = this.footPointY;
         int var1;
         if (this.isAntiGravity) {
            var1 = 1024 >> 1;
         } else {
            var1 = -1024 >> 1;
         }

         this.checkPositionY = this.getNewPointY(var2, 0, var1, 0) + 0;
         var1 = this.checkPositionX;
         var2 = this.checkPositionY;
         int var3 = this.checkPositionX;
         int var4 = this.checkPositionY;
         this.collisionRect.setTwoPosition(var1 - (1280 >> 1), var2 - (1024 >> 1), var3 + 640, var4 + 512);
      }

   }

   public void setFloating(boolean var1) {
      this.floating = var1;
   }

   public void setPreWaterFlag(boolean var1) {
      this.preWaterFlag = var1;
   }
}
