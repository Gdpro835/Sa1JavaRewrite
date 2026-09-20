package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;
import com.sega.mobile.framework.device.MFGraphics;

public abstract class EnemyObject extends GameObject {
   protected static Animation BoomAni;
   public static final int CANNOT_BE_SEEN = 2;
   protected static final int ENEMY_100PTS = 39;
   protected static final int ENEMY_ASPIRATE_BUBBLE = 40;
   protected static final int ENEMY_BAT = 9;
   protected static final int ENEMY_BEE = 1;
   protected static final int ENEMY_BOOM = 37;
   public static final int ENEMY_BOSS1 = 22;
   protected static final int ENEMY_BOSS1_ARM = 31;
   public static final int ENEMY_BOSS2 = 23;
   protected static final int ENEMY_BOSS2_SPRING = 32;
   public static final int ENEMY_BOSS3 = 24;
   protected static final int ENEMY_BOSS3_HARDBASE = 35;
   protected static final int ENEMY_BOSS3_SHADOW = 34;
   public static final int ENEMY_BOSS4 = 25;
   public static final int ENEMY_BOSS5 = 26;
   protected static final int ENEMY_BOSS5_FLYDEFENCE = 33;
   public static final int ENEMY_BOSS6 = 27;
   public static final int ENEMY_BOSSF1 = 28;
   public static final int ENEMY_BOSSF2 = 29;
   public static final int ENEMY_BOSSF3 = 30;
   public static final int ENEMY_BOSS_EXTRA = 36;
   protected static final int ENEMY_BREAKING_PARTS = 38;
   protected static final int ENEMY_CATERPILLAR = 13;
   protected static final int ENEMY_CHAMELEON = 11;
   protected static final int ENEMY_CLOWN = 10;
   protected static final int ENEMY_CRAB = 2;
   protected static final int ENEMY_DORISAME = 18;
   protected static final int ENEMY_DROWN_BUBBLE = 41;
   protected static final int ENEMY_FROG = 4;
   protected static final int ENEMY_HERIKO = 14;
   protected static final int ENEMY_KORA = 19;
   protected static final int ENEMY_LADYBUG = 6;
   protected static final int ENEMY_LIZARD = 8;
   protected static final int ENEMY_MAGMA = 7;
   protected static final int ENEMY_MIRA = 12;
   protected static final int ENEMY_MOLE = 15;
   protected static final int ENEMY_MONKEY = 0;
   protected static final int ENEMY_MOTOR = 3;
   protected static final int ENEMY_PEN = 20;
   public static final int ENEMY_PROBOSS1 = 21;
   protected static final int ENEMY_RABBIT_FISH = 5;
   protected static final String ENEMY_RES_PATH = "/enemy";
   protected static final int ENEMY_YOKOYUKIMAL = 17;
   protected static final int ENEMY_YUKIMAL = 16;
   public static final int IN_ALERT_RANGE = 0;
   public static final int IN_AVAILABLE_RANGE = 1;
   public static boolean IsBoss = false;
   protected static final int POS_BOTTOM = 1;
   protected static final int POS_LEFT = 2;
   protected static final int POS_RIGHT = 3;
   protected static final int POS_TOP = 0;
   public static boolean isBossEnter = false;
   private static boolean magmaEnable = false;
   protected static Animation snowrobotAnimation;
   protected boolean IsPlayBossBattleBGM;
   private CollisionBlock currentBlock;
   protected boolean dead;
   protected AnimationDrawer drawer;
   private int faceDegree;
   protected int iLeft;
   protected int iTop;
   public int layer;
   private int objId;

   protected EnemyObject(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.objId = var1;
      this.posX = var2 << 6;
      this.posY = var3 << 6;
      this.iLeft = var4 << 6;
      this.iTop = var5 << 6;
      this.mWidth = var6 * 8 << 6;
      this.mHeight = var7 * 8 << 6;
      this.posX += this.iLeft * 8;
      this.posY += this.iTop * 8;
      if (var6 < var7) {
         if (var6 == 0) {
            this.layer = 0;
         } else {
            this.layer = 1;
         }
      } else if (var7 == 0) {
         this.layer = 0;
      } else {
         this.layer = 1;
      }

      this.IsPlayBossBattleBGM = false;
      this.refreshCollisionRect(this.posX, this.posY);
   }

   public static void enemyStaticLogic() {
      if (magmaEnable) {
         Magma.staticlogic();
      }

   }

   public static void enemyinit() {
      magmaEnable = false;
   }

   public static EnemyObject getNewInstance(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      IsBoss = false;
      Object var7;
      switch(var0) {
      case 1:
         if (GlobalResource.isEasyMode() && PlayerObject.stageModeState == 0) {
            var7 = null;
         } else {
            var7 = new Bee(var0, var1, var2, var3, var4, var5, var6);
         }
         break;
      case 4:
         var7 = new Frog(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 5:
         if (GlobalResource.isEasyMode() && PlayerObject.stageModeState == 0) {
            var7 = null;
         } else {
            var7 = new RabbitFish(var0, var1, var2, var3, var4, var5, var6);
         }
         break;
      case 6:
         var7 = new LadyBug(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 7:
         magmaEnable = true;
         var7 = new Magma(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 15:
         var7 = new Mole(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 23:
         IsBoss = true;
         var7 = new Boss2(var0, var1, var2, var3, var4, var5, var6);
         break;
      default:
         switch(var0) {
         case 0:
            var7 = new Monkey(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 2:
            var7 = new Crab(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 3:
            var7 = new Motor(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 8:
            var7 = new Lizard(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 9:
            if (GlobalResource.isEasyMode() && PlayerObject.stageModeState == 0) {
               var7 = null;
            } else {
               var7 = new Bat(var0, var1, var2, var3, var4, var5, var6);
            }
            break;
         case 10:
            var7 = new Clown(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 13:
            var7 = new Caterpillar(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 21:
            var7 = new ProBoss1(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 22:
            IsBoss = true;
            var7 = new Boss1(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 24:
            IsBoss = true;
            var7 = new Boss3(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 26:
            IsBoss = true;
            var7 = new Boss5(var0, var1, var2, var3, var4, var5, var6);
            break;
         default:
            switch(var0) {
            case 11:
               if (GlobalResource.isEasyMode() && PlayerObject.stageModeState == 0) {
                  var7 = null;
               } else {
                  var7 = new Chameleon(var0, var1, var2, var3, var4, var5, var6);
               }
               break;
            case 12:
               if (GlobalResource.isEasyMode() && PlayerObject.stageModeState == 0) {
                  var7 = null;
               } else {
                  var7 = new Mira(var0, var1, var2, var3, var4, var5, var6);
               }
               break;
            case 13:
            case 15:
            case 21:
            case 22:
            case 23:
            case 24:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            default:
               var7 = null;
               break;
            case 14:
               var7 = new Heriko(var0, var1, var2, var3, var4, var5, var6);
               break;
            case 16:
               var7 = new SnowRobotH(var0, var1, var2, var3, var4, var5, var6);
               break;
            case 17:
               var7 = new SnowRobotV(var0, var1, var2, var3, var4, var5, var6);
               break;
            case 18:
               var7 = new Fish(var0, var1, var2, var3, var4, var5, var6);
               break;
            case 19:
               var7 = new Cement(var0, var1, var2, var3, var4, var5, var6);
               break;
            case 20:
               if (GlobalResource.isEasyMode() && PlayerObject.stageModeState == 0) {
                  var7 = null;
               } else {
                  var7 = new Penguin(var0, var1, var2, var3, var4, var5, var6);
               }
               break;
            case 25:
               IsBoss = true;
               var7 = new Boss4(var0, var1, var2, var3, var4, var5, var6);
               break;
            case 26:
               IsBoss = true;
               var7 = new Boss2(var0, var1, var2, var3, var4, var5, var6);
               break;
            case 27:
               IsBoss = true;
               var7 = new Boss6(var0, var1, var2, var3, var4, var5, var6);
               break;
            case 28:
               IsBoss = true;
               var7 = new BossF1(var0, var1, var2, var3, var4, var5, var6);
               break;
            case 29:
               IsBoss = true;
               var7 = new BossF2(var0, var1, var2, var3, var4, var5, var6);
               break;
            case 30:
               IsBoss = true;
               var7 = new BossF3(var0, var1, var2, var3, var4, var5, var6);
               break;
            case 36:
               IsBoss = true;
               var7 = new BossExtra(var0, var1, var2, var3, var4, var5, var6);
            }
         }
      }

      return (EnemyObject)var7;
   }

   public static void releaseAllEnemyResource() {
      Bee.releaseAllResource();
      Frog.releaseAllResource();
      RabbitFish.releaseAllResource();
      LadyBug.releaseAllResource();
      Magma.releaseAllResource();
      Mole.releaseAllResource();
      BossBroken.releaseAllResource();
      Boss2.releaseAllResource();
      Boss2Spring.releaseAllResource();
      Motor.releaseAllResource();
      Monkey.releaseAllResource();
      Crab.releaseAllResource();
      Clown.releaseAllResource();
      Caterpillar.releaseAllResource();
      Bat.releaseAllResource();
      Boss1.releaseAllResource();
      Boss1Arm.releaseAllResource();
      Boss3.releaseAllResource();
      Boss3Pipe.releaseAllResource();
      Boss5.releaseAllResource();
      ProBoss1.releaseAllResource();
      Lizard.releaseAllResource();
      Chameleon.releaseAllResource();
      Mira.releaseAllResource();
      Heriko.releaseAllResource();
      SnowRobotH.releaseAllResource();
      SnowRobotV.releaseAllResource();
      Fish.releaseAllResource();
      Cement.releaseAllResource();
      Penguin.releaseAllResource();
      Boss4.releaseAllResource();
      Boss6.releaseAllResource();
      BossF1.releaseAllResource();
      BossF2.releaseAllResource();
      BossF3.releaseAllResource();
      BossF3Arm.releaseAllResource();
      BossExtra.releaseAllResource();
      Boom.releaseAllResource();
      BreakingParts.releaseAllResource();
      Bonus100pts.releaseAllResource();
      AspirateBubble.releaseAllResource();
   }

   public void beAttack() {
      if (!this.dead) {
         this.dead = true;
         Effect.showEffect(destroyEffectAnimation, 0, this.posX >> 6, (this.posY >> 6) - 10, 0);
         if (this.objId != 6 && this.objId != 14) {
            SmallAnimal.addAnimal(this.posX, this.posY, this.getLayer());
         } else {
            SmallAnimal.addAnimal(this.posX, this.posY, this.getLayer(), true);
         }

         player.getEnemyScore();
         soundInstance.playSe(29);
         addGameObject(new Bonus100pts(39, this.posX, this.posY - 640, 0, 0, 0, 0));
      }

   }

   public int checkPlayerInEnemyAlertRange(int var1, int var2, int var3) {
      var3 >>= 6;
      int var4 = player.getCheckPositionX();
      int var7 = player.getCheckPositionX();
      int var5 = player.getCheckPositionY();
      int var6 = player.getCheckPositionY();
      var1 = (var1 - (var4 >> 6)) * (var1 - (var7 >> 6)) + (var2 - (var5 >> 6)) * (var2 - (var6 >> 6));
      var2 = var3 * var3;
      byte var8;
      if (var1 <= var2) {
         var8 = 0;
      } else if (var1 > var2 && var1 <= var2 * 6) {
         var8 = 1;
      } else {
         var8 = 2;
      }

      return var8;
   }

   public int checkPlayerInEnemyAlertRange(int var1, int var2, int var3, int var4) {
      var1 = Math.abs(var1 - (player.getFootPositionX() >> 6));
      var2 = Math.abs(var2 - (player.getFootPositionY() >> 6));
      byte var5;
      if (var1 <= var3 && var2 <= var4) {
         var5 = 0;
      } else if (var1 <= var3 * 2 && var2 <= var4 * 2) {
         var5 = 1;
      } else {
         var5 = 2;
      }

      return var5;
   }

   public int checkPlayerInEnemyAlertRange(int var1, int var2, int var3, int var4, int var5, int var6) {
      int var9 = player.getFootPositionX();
      int var8 = player.getFootPositionX();
      int var10 = player.getFootPositionY();
      int var7 = player.getFootPositionY();
      byte var11;
      if (player.getFootPositionX() >> 6 > var4 - (var6 >> 1) - 8 && player.getFootPositionX() >> 6 < (var6 >> 1) + var5 + 8 && player.getFootPositionY() >> 6 <= var2 + 11 && player.getFootPositionY() >> 6 >= var2 - 44 && (var1 - (var9 >> 6)) * (var1 - (var8 >> 6)) + (var2 - (var10 >> 6)) * (var2 - (var7 >> 6)) <= var3 * var3) {
         var11 = 0;
      } else if ((player.getFootPositionX() >> 6 > var4 - (var6 >> 1) - 8 || player.getFootPositionX() >> 6 < var4 - var6 * 2) && (player.getFootPositionX() >> 6 < (var6 >> 1) + var5 + 8 || player.getFootPositionX() >> 6 > var6 * 2 + var4)) {
         var11 = 2;
      } else {
         var11 = 1;
      }

      return var11;
   }

   public int checkPlayerInEnemyAlertRange(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      var1 = Math.abs(var1 - (player.getFootPositionX() >> 6));
      var2 = Math.abs(var2 - (player.getFootPositionY() >> 6));
      byte var8;
      if (player.getFootPositionX() >> 6 > var5 - (var7 >> 1) - 8 && player.getFootPositionX() >> 6 < (var7 >> 1) + var6 + 8 && var1 <= var3 && var2 <= var4) {
         var8 = 0;
      } else if (var1 <= var3 * 2 && var2 <= var4 * 2) {
         var8 = 1;
      } else {
         var8 = 2;
      }

      return var8;
   }

   public boolean checkPlayerInEnemyAlertRangeScale(int var1, int var2, int var3, int var4) {
      var1 = Math.abs(var1 - (player.getFootPositionX() >> 6));
      var2 = Math.abs(var2 - (player.getFootPositionY() >> 6));
      boolean var5;
      if (var1 == 0) {
         var5 = false;
      } else {
         var1 = var2 * 100 / var1;
         if (var1 >= var3 && var1 <= var4) {
            var5 = true;
         } else {
            var5 = false;
         }
      }

      return var5;
   }

   public void close() {
      this.drawer = null;
   }

   public void doBeforeCollisionCheck() {
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
      if (!this.dead) {
         if (var1 instanceof PlayerAmy && (var1.getCharacterAnimationID() < 6 || var1.getCharacterAnimationID() > 8)) {
            byte var4;
            if (var1.isAntiGravity) {
               var4 = -1;
            } else {
               var4 = 1;
            }

            var1.setVelY(var4 * -900);
         }

         if (player instanceof PlayerTails && player.myAnimationID == 12 && var2 == 1) {
            player.velY = -600;
         }
      }

      this.beAttack();
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.dead && var1 == player) {
         if (player.isAttackingEnemy()) {
            player.doAttackPose(this, var2);
            this.beAttack();
         } else {
            player.beHurt();
         }
      }

   }

   public void doWhileCollision(ACObject var1, ACCollision var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void draw(MFGraphics var1) {
      Coordinate var2 = MapManager.getCamera();
      var1.setColor(65280);
      var1.drawRect(this.posX - var2.x, this.posY - var2.y, this.mWidth, this.mHeight);
      var1.drawRect(this.posX + 1 - var2.x, this.posY + 1 - var2.y, this.mWidth - 2, this.mHeight - 2);
      var1.setColor(0);
      var1.drawString("ID:" + this.objId + ";iLeft:" + this.iLeft + ";iTop:" + this.iTop, this.posX - var2.x - 1, this.posY - var2.y - 1, 36);
      var1.drawString("ID:" + this.objId + ";iLeft:" + this.iLeft + ";iTop:" + this.iTop, this.posX - var2.x + 1, this.posY - var2.y - 1, 36);
      var1.drawString("ID:" + this.objId + ";iLeft:" + this.iLeft + ";iTop:" + this.iTop, this.posX - var2.x, this.posY - var2.y - 1 - 1, 36);
      var1.drawString("ID:" + this.objId + ";iLeft:" + this.iLeft + ";iTop:" + this.iTop, this.posX - var2.x, this.posY - var2.y - 1 + 1, 36);
      var1.setColor(16776960);
      var1.drawString("ID:" + this.objId + ";iLeft:" + this.iLeft + ";iTop:" + this.iTop, this.posX - var2.x, this.posY - var2.y - 1, 36);
   }

   public void drawAlertRangeLine(MFGraphics var1, int var2, int var3, int var4, Coordinate var5) {
      int var6;
      int var7;
      int var8;
      int var9;
      int var10;
      switch(var2) {
      case 0:
         var1.setColor(16711680);
         var6 = player.getFootPositionX();
         var10 = var5.x;
         var9 = player.getFootPositionY();
         var8 = var5.y;
         var7 = var5.x;
         var2 = var5.y;
         var1.drawLine((var6 >> 6) - var10, (var9 >> 6) - var8, var3 - var7, var4 - var2);
         break;
      case 1:
         var1.setColor(65280);
         var7 = player.getFootPositionX();
         var10 = var5.x;
         var9 = player.getFootPositionY();
         var8 = var5.y;
         var2 = var5.x;
         var6 = var5.y;
         var1.drawLine((var7 >> 6) - var10, (var9 >> 6) - var8, var3 - var2, var4 - var6);
      }

   }

   public void drawPatrolRect(MFGraphics var1, int var2, int var3, int var4, int var5) {
      var1.setColor(16711680);
      var1.drawRect((var2 >> 6) - camera.x, (var3 >> 6) - camera.y, var4 >> 6, var5 >> 6);
   }

   public int getLayer() {
      return player.currentLayer;
   }

   public int getPaintLayer() {
      return 0;
   }
}
