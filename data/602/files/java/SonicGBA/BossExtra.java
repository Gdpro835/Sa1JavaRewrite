package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import Lib.MyRandom;
import Lib.SoundSystem;
import PyxEditor.PyxAnimation;
import PyxEditor.PyxAnimation.NodeInfo;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.device.MFGraphics;

class BossExtra extends BossObject {
   private static final int ANI_APPEAR_1 = 0;
   private static final int ANI_APPEAR_2 = 8;
   private static final int ANI_ATTACK_1 = 2;
   private static final int ANI_ATTACK_2 = 3;
   private static final int ANI_DEAD = 9;
   private static final int ANI_DEFENCE = 7;
   private static final int ANI_FIRST_DEFENCE = 1;
   private static final int ANI_GROUND = 5;
   private static final int ANI_GROUND_READY = 4;
   private static final int ANI_LASER = 6;
   private static final int[] ANI_SEQUENCE_1 = new int[]{2, 3, 4};
   private static final int APPEAR_VELOCITY_1 = 1920;
   private static final int APPEAR_VELOCITY_2 = -960;
   private static final int APPEAR_X = -3584;
   private static final int APPEAR_Y = 8192;
   private static final String[] COLLISION_BODY_NAME = new String[]{"body", "head", "hand_f", "hand_b", "leg_front", "leg_back"};
   private static final int COLLISION_HEIGHT = 18432;
   private static final int COLLISION_WIDTH = 30720;
   private static final int DEAD_DISTANCE_TO_GROUND = 1200;
   private static final int GROUND_COUNT = 60;
   private static final int LASER_COUNT = 18;
   private static final int LASER_DEGREE_END = 10880;
   private static final int LASER_DEGREE_START = 8320;
   private static final int LASER_DIVIDE_COUNT = 110;
   private static final int LASER_NUM = 4;
   private static final int LASER_VELOCITY_ACCELERATE = 80;
   private static final int PYX_ANI_SPEED = 240;
   private static final int PYX_ANI_SPEED_GROUND = 64;
   private static final int STATE_APPEAR_1 = 1;
   private static final int STATE_APPEAR_2 = 2;
   private static final int STATE_DEAD = 8;
   private static final int STATE_DEFENCE = 7;
   private static final int STATE_FIRST_DEFENCE = 3;
   private static final int STATE_GROUND = 5;
   private static final int STATE_LASER = 6;
   private static final int STATE_NONE = 0;
   private static final int STATE_SEQUENCE_1 = 4;
   private static final int WARNING_COUNT = 48;
   private static CollisionRect bodyRect = new CollisionRect();
   private static Animation[] bossAnimation;
   private static int damageframe;
   private static NodeInfo nodeInfo = new NodeInfo();
   private int actionID;
   private int afterLaserCount;
   private boolean bulletShowing;
   private int count;
   private int damageCount;
   private int distanceToGround;
   private AnimationDrawer headFlashDrawer;
   private boolean isOnLand;
   private boolean isShotting;
   private int laserCount = 0;
   private int laserDegree;
   private int laserDegreeVelocity;
   private int laserHeight;
   private int laserNumCount = 0;
   private int pacmanCount;
   private boolean pacmanFlag;
   private PyxAnimation pyxAnimation;
   private int state = 0;
   private AnimationDrawer warningDrawer;

   protected BossExtra(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      bossAnimation = new Animation[1];
      bossAnimation[0] = new Animation("/animation/boss_extra");
      this.pyxAnimation = new PyxAnimation("/animation/aaa.pyx", bossAnimation);
      StringBuilder var8 = new StringBuilder("/lang");
      var8.append(GlobalResource.languageConfig);
      var8.append("/utl_res/warning.dat");
      this.warningDrawer = Animation.getInstanceFromQi(var8.toString())[0].getDrawer(0, true, 0);
      this.headFlashDrawer = bossAnimation[0].getDrawer(15, false, 0);
      this.posX = -3584;
      this.posY = 8192;
      this.velY = 0;
      this.distanceToGround = 2800;
      this.pyxAnimation.setSpeed(240);
      if (GlobalResource.isEasyMode() && stageModeState == 0) {
         this.HP = 6;
      } else {
         this.HP = 8;
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimationArray(bossAnimation);
      bossAnimation = null;
   }

   public void close() {
      this.headFlashDrawer = null;
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (this.state != 8) {
         for(var2 = 0; var2 < COLLISION_BODY_NAME.length; ++var2) {
            this.pyxAnimation.getNodeInfo(nodeInfo, COLLISION_BODY_NAME[var2]);
            if (nodeInfo.hasNode()) {
               byte[] var12 = nodeInfo.drawer.getCRect();
               if (var12 != null) {
                  CollisionRect var11 = bodyRect;
                  int var5 = this.posX;
                  int var10 = nodeInfo.animationX;
                  byte var7 = var12[0];
                  int var6 = this.posY;
                  var3 = nodeInfo.animationY;
                  byte var4 = var12[1];
                  byte var9 = var12[2];
                  byte var8 = var12[3];
                  var11.setRect(var5 + (var10 + var7 << 6), var6 + (var3 + var4 << 6), var9 << 6, var8 << 6);
                  int var14 = nodeInfo.rotateX;
                  var5 = nodeInfo.animationX;
                  var6 = nodeInfo.rotateY;
                  int var15 = nodeInfo.animationY;
                  var11 = bodyRect;
                  var3 = nodeInfo.degree;
                  int var16 = -var12[0];
                  int var13 = -var12[1];
                  var11.setRotate(var3, var16 + (var14 - var5) << 6, var13 + (var6 - var15) << 6);
                  if (bodyRect.collisionChk(player.getCollisionRect())) {
                     if (var2 == 1 && this.damageCount == 0) {
                        --this.HP;
                        if (this.HP == 0) {
                           this.state = 8;
                           this.pyxAnimation.changeToAction(9, 10);
                           this.pyxAnimation.setLoop(false);
                           player.getBossScore();
                           if (player instanceof PlayerSuperSonic) {
                              ((PlayerSuperSonic)player).setBossDieFlag(true);
                           }
                        } else {
                           this.damageCount = 10;
                        }

                        player.doBossAttackPose(this, 2);
                     }
                     break;
                  }
               }
            }
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.state != 8) {
         for(var2 = 0; var2 < COLLISION_BODY_NAME.length; ++var2) {
            this.pyxAnimation.getNodeInfo(nodeInfo, COLLISION_BODY_NAME[var2]);
            if (nodeInfo.hasNode()) {
               byte[] var12 = nodeInfo.drawer.getCRect();
               if (var12 != null) {
                  CollisionRect var11 = bodyRect;
                  int var9 = this.posX;
                  int var5 = nodeInfo.animationX;
                  byte var7 = var12[0];
                  int var3 = this.posY;
                  int var10 = nodeInfo.animationY;
                  byte var8 = var12[1];
                  byte var4 = var12[2];
                  byte var6 = var12[3];
                  var11.setRect(var9 + (var5 + var7 << 6), var3 + (var10 + var8 << 6), var4 << 6, var6 << 6);
                  var3 = nodeInfo.rotateX;
                  var9 = nodeInfo.animationX;
                  var5 = nodeInfo.rotateY;
                  int var15 = nodeInfo.animationY;
                  var11 = bodyRect;
                  int var13 = nodeInfo.degree;
                  int var14 = -var12[0];
                  int var16 = -var12[1];
                  var11.setRotate(var13, var14 + (var3 - var9) << 6, var16 + (var5 - var15) << 6);
                  if (bodyRect.collisionChk(player.getCollisionRect())) {
                     if (var2 == 1) {
                        if (player.isAttackingEnemy() && this.damageCount == 0) {
                           --this.HP;
                           if (this.HP == 0) {
                              this.state = 8;
                              this.pyxAnimation.changeToAction(9, 10);
                              this.pyxAnimation.setLoop(false);
                              player.getBossScore();
                              if (player instanceof PlayerSuperSonic) {
                                 ((PlayerSuperSonic)player).setBossDieFlag(true);
                              }
                           } else {
                              this.damageCount = 10;
                           }

                           player.doBossAttackPose(this, 2);
                        } else if (this.damageCount == 0) {
                           player.beHurt();
                        }
                     } else {
                        player.beHurt();
                     }
                     break;
                  }
               }
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      if (PlayerObject.getTimeCount() != 0) {
         Coordinate var8 = MapManager.getCamera();
         PyxAnimation var9 = this.pyxAnimation;
         int var7 = this.posX;
         int var5 = var8.x;
         int var6 = this.posY;
         int var4 = var8.y;
         var9.drawAction(var1, (var7 >> 6) - var5, (var6 >> 6) - var4);
         switch(this.state) {
         case 0:
            this.warningDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
         default:
            float var2;
            float var3;
            Graphics var10;
            if (this.isShotting) {
               var4 = this.pyxAnimation.getNodeXByAnimationNamed("head", -20, -12);
               var5 = this.pyxAnimation.getNodeYByAnimationNamed("head", -20, -12);
               var10 = (Graphics)var1.getSystemGraphics();
               var10.save();
               var3 = (float)((this.posX >> 6) + var4 - var8.x);
               var2 = (float)((this.posY >> 6) + var5 - var8.y);
               var10.translate(var3, var2);
               var10.rotate((float)(this.laserDegree >> 6));
               var1.setColor(16777215);
               var1.fillRect(0, -this.laserHeight >> 1, 500, this.laserHeight);
               ++this.laserHeight;
               if (this.laserHeight > 4) {
                  this.laserHeight = 4;
               }

               var10.restore();
            }

            if (!IsGamePause) {
               --this.damageCount;
            }

            if (this.damageCount < 0) {
               this.damageCount = 0;
            }

            if (this.damageCount / 1 % 2 == 1) {
               this.pyxAnimation.getNodeInfo(nodeInfo, "head");
               if (nodeInfo.hasNode()) {
                  var10 = (Graphics)var1.getSystemGraphics();
                  var10.save();
                  var3 = (float)(nodeInfo.animationX + (this.posX >> 6) - var8.x);
                  var2 = (float)(nodeInfo.animationY + (this.posY >> 6) - var8.y);
                  var10.translate(var3, var2);
                  var10.rotate((float)nodeInfo.degree);
                  this.headFlashDrawer.draw(var1, 0, 0);
                  var10.restore();
               }
            }
         }
      }

   }

   public void logic() {
      int var1;
      int var2;
      int var3;
      int var4;
      switch(this.state) {
      case 0:
         this.posX = -3584;
         this.posY = 8192;
         this.isOnLand = false;
         if (PlayerObject.getTimeCount() > 0) {
            ++this.count;
            if (this.count == 48) {
               this.state = 1;
               this.count = 0;
               this.pyxAnimation.setAction(8);
               this.velX = 1920;
               this.velY = 0;
            }
         }
         break;
      case 1:
         this.posX += this.velX;
         this.posY += this.velY;
         ++this.count;
         if (this.count == 32) {
            this.pyxAnimation.changeToAction(0, 14);
            this.velY = 128;
            this.state = 2;
            this.count = 0;
         }
         break;
      case 2:
         this.posX += this.velX;
         this.posY += this.velY;
         ++this.count;
         if (this.count > 14) {
            this.velX = -960;
            var1 = this.getGroundY(this.posX, this.posY + this.distanceToGround);
            this.posY = var1 - this.distanceToGround;
            if (this.posX < 26368) {
               this.posX = 26368;
               this.state = 3;
               this.pyxAnimation.setAction(1);
               this.actionID = 0;
               this.isOnLand = true;
               this.pacmanFlag = true;
               this.pacmanCount = 0;
            }
         }
         break;
      case 3:
         if (this.pyxAnimation.chkEnd()) {
            this.state = 4;
            this.pyxAnimation.setAction(ANI_SEQUENCE_1[this.actionID]);
            ++this.actionID;
         }
         break;
      case 4:
         if (this.pyxAnimation.chkEnd()) {
            if (this.actionID < ANI_SEQUENCE_1.length) {
               this.pyxAnimation.setAction(ANI_SEQUENCE_1[this.actionID]);
               ++this.actionID;
            } else {
               this.state = 5;
               this.pyxAnimation.setAction(5);
               this.pyxAnimation.setLoop(true);
               this.count = 0;
            }
         }
         break;
      case 5:
         ++this.count;
         if (this.count % 15 == 0) {
            var3 = this.posX;
            var2 = MyRandom.nextInt(-256, 256);
            var1 = this.posY;
            var4 = MyRandom.nextInt(-128, 128);
            BulletObject.addBullet(24, var3 + var2, var1 + 3072 + var4, 0, 0);
            var3 = this.posX;
            var2 = MyRandom.nextInt(-256, 256);
            var4 = this.posY;
            var1 = MyRandom.nextInt(-128, 128);
            BulletObject.addBullet(24, var3 + var2, var4 + 3072 + var1, 0, 0);
         }

         if (this.count > 60) {
            this.state = 6;
            this.pyxAnimation.setAction(6);
            this.pyxAnimation.setLoop(false);
            this.count = 0;
            this.laserNumCount = 0;
         }
         break;
      case 6:
         this.pyxAnimation.chkEnd();
         if (this.count % 110 == 0 && this.laserNumCount < 4) {
            this.laserDegree = 8320;
            this.laserDegreeVelocity = 0;
            this.laserCount = 0;
            this.isShotting = true;
            this.afterLaserCount = 0;
            this.laserHeight = 1;
            ++this.laserNumCount;
            this.pyxAnimation.changeAnimation("head", 0, 3);
         }

         if (!this.isShotting && this.laserNumCount == 4) {
            this.state = 7;
            this.pyxAnimation.setAction(7);
         }

         ++this.count;
         break;
      case 7:
         if (this.pyxAnimation.chkEnd()) {
            this.state = 4;
            this.actionID = 0;
            this.pyxAnimation.setAction(ANI_SEQUENCE_1[this.actionID]);
            ++this.actionID;
         }
         break;
      case 8:
         ++damageframe;
         damageframe %= 11;
         if (damageframe % 2 == 0) {
            SoundSystem.getInstance().playSe(35);
         }

         var1 = MapManager.getCamera().x;
         var1 = MapManager.getCamera().y;
         if (damageframe % 3 == 0) {
            var2 = this.posX;
            var3 = MyRandom.nextInt(0, 100);
            var4 = this.posY;
            var1 = MyRandom.nextInt(0, 100);
            addGameObject(new Boom(37, (var2 >> 6) - 50 + var3 << 6, (var4 >> 6) - 50 + var1 << 6, 0, 0, 0, 0));
         }

         this.distanceToGround -= 120;
         if (this.distanceToGround < 1200) {
            this.distanceToGround = 1200;
         }

         if (this.distanceToGround == 1200) {
            this.posX -= 640;
         }

         if (this.posX < -12800) {
            StageManager.setStagePass();
         }
      }

      if (this.isOnLand) {
         var1 = this.getGroundY(this.posX, this.posY + this.distanceToGround);
         this.posY = var1 - this.distanceToGround;
      }

      if (this.isShotting) {
         if (this.laserDegree > 10880) {
            this.isShotting = false;
            this.bulletShowing = true;
            this.pyxAnimation.changeAnimation("head", 0, 2);
         } else {
            this.laserDegree += this.laserDegreeVelocity;
            this.laserDegreeVelocity += 80;
         }
      }

      if (this.state != 8) {
         if (this.pacmanFlag) {
            ++this.pacmanCount;
            if (this.pacmanCount >= 64) {
               this.pacmanCount = 0;
               var2 = this.posX;
               var1 = this.posY;
               BulletObject.addBullet(22, var2 - 1152, var1 - 896, 0, 0);
            }
         }

         if (this.bulletShowing) {
            ++this.afterLaserCount;
            if (this.afterLaserCount >= 5 && (this.afterLaserCount - 5) % 4 == 0) {
               var1 = 320 - (this.afterLaserCount - 5) * 32 / 4;
               if (var1 > 0) {
                  var2 = this.posY;
                  var3 = this.distanceToGround;
                  BulletObject.addBullet(23, var1 << 6, var2 + var3, 0, 0);
                  SoundSystem.getInstance().playSe(81);
               } else {
                  this.bulletShowing = false;
               }
            }
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(15360, 0, 15360, 18432);
   }
}
