package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Spring extends GimmickObject {
   public static final int[][] COLLISION_PARAM;
   public static final int[] SPRING_INWATER_POWER;
   public static final int[] SPRING_POWER;
   public static final int[] SPRING_POWER_ORIGINAL;
   private static final int[] VELOCITY_MINUS;
   private static final int[] VELOCITY_MINUS_2;
   public static Animation springAnimation = null;
   public int centerPointX;
   private AnimationDrawer drawer;
   private int firstCollisionDirection = 4;
   public boolean springAttacked;
   public int springPower;

   static {
      int[] var3 = new int[]{-15, 0, 30, 19};
      int[] var4 = new int[]{0, -28, 16, 26};
      COLLISION_PARAM = new int[][]{{-15, -15, 30, 15}, var3, {-16, -28, 16, 26}, var4, {-22, -29, 26, 26}, {-4, -29, 26, 26}, {-22, -21, 26, 13}, {-4, -21, 26, 13}};
      int var0 = GRAVITY;
      int var2 = GRAVITY;
      int var1 = GRAVITY;
      SPRING_POWER = new int[]{(var0 >> 1) + 1968, (var2 >> 1) + 2351, (var1 >> 1) + 2744, 3234};
      var0 = GRAVITY;
      var2 = GRAVITY;
      var1 = GRAVITY;
      SPRING_INWATER_POWER = new int[]{(var0 >> 1) + 2064, (var2 >> 1) + 2256, (var1 >> 1) + 2440, 2624};
      SPRING_POWER_ORIGINAL = new int[]{1920, 2304, 2688, 3072};
      VELOCITY_MINUS = new int[]{20, 85, 20, 20};
      VELOCITY_MINUS_2 = new int[]{30, 100, 30, 30};
   }

   public Spring(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      if (springAnimation == null) {
         springAnimation = new Animation("/animation/se_bane_kiro");
      }

      if (this.iLeft < 0) {
         this.iLeft = 0;
      } else if (this.iLeft > 3) {
         this.iLeft = 3;
      }

      this.springPower = SPRING_POWER[this.iLeft];
      this.drawer = springAnimation.getDrawer((this.objId - 8) * 2, false, 0);
      if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
         this.drawer.setActionId(this.objId * 2);
      }

      this.centerPointX = this.collisionRect.x0 + this.collisionRect.x1 >> 1;
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(springAnimation);
      springAnimation = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (PlayerObject.getCharacterID() == 3 && var1.myAnimationID != 7 && var1.myAnimationID != 8) {
         if (this.objId != 10 && this.objId != 11) {
            this.springAttacked = true;
         }

         switch(this.objId) {
         case 8:
            if (var3 != 6 && var3 != 7) {
               player.beSpring(this.springPower * 13 / 10, 1);
               if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
                  this.drawer.setActionId(this.objId * 2 + 1);
               } else {
                  this.drawer.setActionId((this.objId - 8) * 2 + 1);
               }

               if (this.iLeft == 1) {
                  player.setAnimationId(14);
               }

               soundInstance.playSe(37);
            }
            break;
         case 9:
            if (var3 != 6 && var3 != 7) {
               player.beSpring(this.springPower * 13 / 10, 0);
               if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
                  this.drawer.setActionId(this.objId * 2 + 1);
               } else {
                  this.drawer.setActionId((this.objId - 8) * 2 + 1);
               }

               if (player.isAntiGravity && this.iLeft == 1) {
                  player.setAnimationId(14);
               }

               soundInstance.playSe(37);
            }
            break;
         case 10:
         case 11:
         default:
            if (var2 == 1) {
               player.setAnimationId(0);
               player.restartAniDrawer();
            }
            break;
         case 12:
         case 14:
            if (var3 != 6 && var3 != 7) {
               player.beSpring(this.springPower * 13 / 10 * VELOCITY_MINUS[this.iLeft] / VELOCITY_MINUS_2[this.iLeft], 3);
               player.beSpring(this.springPower * 13 / 10, 1);
               if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
                  this.drawer.setActionId(this.objId * 2 + 1);
               } else {
                  this.drawer.setActionId((this.objId - 8) * 2 + 1);
               }

               if (this.iLeft == 1) {
                  player.setAnimationId(14);
               }

               soundInstance.playSe(37);
            }
            break;
         case 13:
         case 15:
            if (var3 != 6 && var3 != 7) {
               player.beSpring(this.springPower * 13 / 10 * VELOCITY_MINUS[this.iLeft] / VELOCITY_MINUS_2[this.iLeft], 2);
               player.beSpring(this.springPower * 13 / 10, 1);
               if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
                  this.drawer.setActionId(this.objId * 2 + 1);
               } else {
                  this.drawer.setActionId((this.objId - 8) * 2 + 1);
               }

               if (this.iLeft == 1) {
                  player.setAnimationId(14);
               }

               soundInstance.playSe(37);
            }
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.used && (PlayerObject.getCharacterID() != 3 || !this.springAttacked)) {
         int var3 = var2;
         if (this.firstCollisionDirection != 4) {
            var3 = var2;
            if (var2 == 4) {
               var3 = this.firstCollisionDirection;
            }
         }

         if (this.firstCollisionDirection == 4 && var3 != 4) {
            this.firstCollisionDirection = var3;
         }

         var1.beStop(this.collisionRect.x0, var3, this);
         if (var1 == player) {
            boolean var4;
            PlayerObject var5;
            switch(var3) {
            case 0:
               switch(this.objId) {
               case 9:
                  if (var1 instanceof PlayerKnuckles && player.getCharacterAnimationID() >= 29 && player.getCharacterAnimationID() <= 33) {
                     player.collisionState = 1;
                     player.restartAniDrawer();
                  }

                  player.beSpring(this.springPower, var3);
                  if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
                     this.drawer.setActionId(this.objId * 2 + 1);
                  } else {
                     this.drawer.setActionId((this.objId - 8) * 2 + 1);
                  }

                  if (player.isAntiGravity && this.iLeft == 1) {
                     player.setAnimationId(14);
                  }

                  soundInstance.playSe(37);
                  return;
               case 10:
                  if (player.isAntiGravity && this.firstTouch) {
                     player.setAnimationId(0);
                  }

                  return;
               case 11:
                  if (player.isAntiGravity && this.firstTouch) {
                     player.setAnimationId(0);
                  }

                  return;
               default:
                  return;
               }
            case 1:
               switch(this.objId) {
               case 8:
                  player.beSpring(this.springPower, var3);
                  if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
                     this.drawer.setActionId(this.objId * 2 + 1);
                  } else {
                     this.drawer.setActionId((this.objId - 8) * 2 + 1);
                  }

                  if (this.iLeft == 1) {
                     player.setAnimationId(14);
                  }

                  if (this.firstTouch) {
                     soundInstance.playSe(37);
                  }

                  return;
               case 9:
               default:
                  if (var1 instanceof PlayerAmy && (player.getCharacterAnimationID() >= 5 && player.getCharacterAnimationID() <= 7 || player.getCharacterAnimationID() >= 20 && player.getCharacterAnimationID() <= 22 || player.getCharacterAnimationID() >= 14 && player.getCharacterAnimationID() <= 17)) {
                     player.setAnimationId(0);
                     player.restartAniDrawer();
                  }

                  if (var1 instanceof PlayerKnuckles && player.getCharacterAnimationID() >= 29 && player.getCharacterAnimationID() <= 33) {
                     player.collisionState = 2;
                     player.setAnimationId(0);
                     player.restartAniDrawer();
                  }

                  return;
               case 10:
                  if (this.firstTouch) {
                     player.setAnimationId(0);
                  }

                  return;
               case 11:
                  if (this.firstTouch) {
                     player.setAnimationId(0);
                  }

                  if (StageManager.getStageID() == 4 && player.degreeForDraw != 0) {
                     player.faceDirection = true;
                     player.beSpring(this.springPower, 2);
                     this.drawer.setActionId((this.objId - 8) * 2 + 1);
                     soundInstance.playSe(37);
                     if (var1 instanceof PlayerAmy) {
                        player.setAnimationId(3);
                        player.restartAniDrawer();
                        return;
                     }
                  }

                  return;
               case 12:
               case 14:
                  if (player.getCheckPositionX() >= this.collisionRect.x0 + (this.collisionRect.x1 - this.collisionRect.x0) / 4) {
                     if (this.firstTouch) {
                        player.setAnimationId(0);
                        return;
                     }
                  } else {
                     player.beSpring(this.springPower * VELOCITY_MINUS[this.iLeft] / VELOCITY_MINUS_2[this.iLeft], 3);
                     player.beSpring(this.springPower, var3);
                     if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
                        this.drawer.setActionId(this.objId * 2 + 1);
                     } else {
                        this.drawer.setActionId((this.objId - 8) * 2 + 1);
                     }

                     if (this.iLeft == 1) {
                        player.setAnimationId(14);
                     }

                     player.setFootPositionY(this.collisionRect.y0);
                     soundInstance.playSe(37);
                  }

                  return;
               case 13:
               case 15:
                  if (player.getCheckPositionX() <= this.collisionRect.x0 + (this.collisionRect.x1 - this.collisionRect.x0) * 3 / 4) {
                     if (this.firstTouch) {
                        player.setAnimationId(0);
                        return;
                     }
                  } else {
                     player.beSpring(this.springPower * VELOCITY_MINUS[this.iLeft] / VELOCITY_MINUS_2[this.iLeft], 2);
                     player.beSpring(this.springPower, var3);
                     if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
                        this.drawer.setActionId(this.objId * 2 + 1);
                     } else {
                        this.drawer.setActionId((this.objId - 8) * 2 + 1);
                     }

                     if (this.iLeft == 1) {
                        player.setAnimationId(14);
                     }

                     player.setFootPositionY(this.collisionRect.y0);
                     soundInstance.playSe(37);
                  }

                  return;
               }
            case 2:
               switch(this.objId) {
               case 11:
                  var5 = player;
                  if (player.isAntiGravity) {
                     var4 = false;
                  } else {
                     var4 = true;
                  }

                  var5.faceDirection = var4;
                  player.beSpring(this.springPower, var3);
                  this.drawer.setActionId((this.objId - 8) * 2 + 1);
                  soundInstance.playSe(37);
                  if (var1 instanceof PlayerAmy) {
                     player.setAnimationId(3);
                     player.restartAniDrawer();
                  }

                  return;
               case 12:
               case 14:
               default:
                  return;
               case 13:
               case 15:
                  if (player.getCheckPositionX() > this.centerPointX) {
                     player.beSpring(this.springPower * VELOCITY_MINUS[this.iLeft] / VELOCITY_MINUS_2[this.iLeft], var3);
                     player.beSpring(this.springPower, 1);
                     this.drawer.setActionId((this.objId - 8) * 2 + 1);
                     if (this.iLeft == 1) {
                        player.setAnimationId(14);
                     }

                     player.setFootPositionY(this.collisionRect.y0);
                     player.setFootPositionX(this.collisionRect.x1);
                     soundInstance.playSe(37);
                  }

                  return;
               }
            case 3:
               switch(this.objId) {
               case 10:
                  var5 = player;
                  if (player.isAntiGravity) {
                     var4 = true;
                  } else {
                     var4 = false;
                  }

                  var5.faceDirection = var4;
                  player.beSpring(this.springPower, var3);
                  this.drawer.setActionId((this.objId - 8) * 2 + 1);
                  soundInstance.playSe(37);
                  if (var1 instanceof PlayerAmy) {
                     player.setAnimationId(3);
                     player.restartAniDrawer();
                  }

                  return;
               case 11:
               case 13:
               default:
                  return;
               case 12:
               case 14:
                  if (player.getCheckPositionX() < this.centerPointX) {
                     player.beSpring(this.springPower * VELOCITY_MINUS[this.iLeft] / VELOCITY_MINUS_2[this.iLeft], var3);
                     player.beSpring(this.springPower, 1);
                     this.drawer.setActionId((this.objId - 8) * 2 + 1);
                     if (this.iLeft == 1) {
                        player.setAnimationId(14);
                     }

                     player.setFootPositionY(this.collisionRect.y0);
                     player.setFootPositionX(this.collisionRect.x0);
                     soundInstance.playSe(37);
                  }

                  return;
               }
            case 4:
               if (var1 instanceof PlayerKnuckles) {
                  switch(this.objId) {
                  case 8:
                     player.beSpring(this.springPower, 1);
                     if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
                        this.drawer.setActionId(this.objId * 2 + 1);
                     } else {
                        this.drawer.setActionId((this.objId - 8) * 2 + 1);
                     }

                     if (this.iLeft == 1) {
                        player.setAnimationId(14);
                     }

                     soundInstance.playSe(37);
                     break;
                  case 9:
                     player.beSpring(this.springPower, 0);
                     if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
                        this.drawer.setActionId(this.objId * 2 + 1);
                     } else {
                        this.drawer.setActionId((this.objId - 8) * 2 + 1);
                     }

                     if (player.isAntiGravity && this.iLeft == 1) {
                        player.setAnimationId(14);
                     }

                     soundInstance.playSe(37);
                  }
               }

               if (this.objId == 14 && this.firstTouch) {
                  player.beSpring(this.springPower * VELOCITY_MINUS[this.iLeft] / VELOCITY_MINUS_2[this.iLeft], 3);
                  player.beSpring(this.springPower, 1);
                  this.drawer.setActionId((this.objId - 8) * 2 + 1);
                  if (this.iLeft == 1) {
                     player.setAnimationId(14);
                  }

                  player.setFootPositionY(this.collisionRect.y0);
                  player.setFootPositionX(this.collisionRect.x0);
                  soundInstance.playSe(37);
               }
            }
         }
      }

   }

   public void doWhileNoCollision() {
      this.firstCollisionDirection = 4;
      this.springAttacked = false;
   }

   public void draw(MFGraphics var1) {
      int var2;
      if (player.isInWater) {
         var2 = SPRING_INWATER_POWER[this.iLeft];
      } else {
         var2 = SPRING_POWER[this.iLeft];
      }

      this.springPower = var2;
      this.drawInMap(var1, this.drawer, this.posX, this.posY);
      if (this.drawer.checkEnd()) {
         if ((this.objId == 8 || this.objId == 9) && this.iLeft == 2) {
            this.drawer.setActionId(this.objId * 2);
         } else {
            this.drawer.setActionId((this.objId - 8) * 2);
         }
      }

      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var7 = this.collisionRect;
      int var6 = COLLISION_PARAM[this.objId - 8][0];
      int var5 = COLLISION_PARAM[this.objId - 8][1];
      int var3 = COLLISION_PARAM[this.objId - 8][2];
      int var4 = COLLISION_PARAM[this.objId - 8][3];
      var7.setRect((var6 << 6) + var1, (var5 << 6) + var2, var3 << 6, var4 << 6);
   }
}
