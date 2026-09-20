package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;
import java.util.Vector;

public abstract class BulletObject extends MoveObject {
   protected static final int BULLET_ANIMAL = 0;
   protected static final int BULLET_BAT = 9;
   protected static final int BULLET_BEE = 1;
   protected static final int BULLET_BOSS5 = 16;
   protected static final int BULLET_BOSS6 = 19;
   protected static final int BULLET_BOSSF3BOMB = 20;
   protected static final int BULLET_BOSSF3RAY = 21;
   protected static final int BULLET_BOSS_EXTRA_LASER = 23;
   protected static final int BULLET_BOSS_EXTRA_PACMAN = 22;
   protected static final int BULLET_BOSS_STONE = 24;
   protected static final int BULLET_BOSS_STONE_SMALL = 25;
   protected static final int BULLET_CATERPILLAR = 13;
   protected static final int BULLET_CHAMELEON = 11;
   protected static final int BULLET_CLOWN = 10;
   protected static final int BULLET_CRAB = 2;
   protected static final int BULLET_FROG = 4;
   protected static final int BULLET_HERIKO = 14;
   protected static final int BULLET_LADYBUG = 6;
   protected static final int BULLET_LIZARD = 8;
   protected static final int BULLET_MAGMA = 7;
   protected static final int BULLET_MIRA = 12;
   protected static final int BULLET_MOLE = 15;
   protected static final int BULLET_MONKEY = 0;
   protected static final int BULLET_MOTOR = 3;
   protected static final int BULLET_NATURE = 1;
   protected static final int BULLET_PENGUIN = 18;
   protected static final int BULLET_RABBIT_FISH = 5;
   protected static final int BULLET_ROBOT = 17;
   protected static Animation batbulletAnimation;
   protected static Animation beebulletAnimation;
   protected static Animation boomAnimation;
   protected static Animation boss6bulletAnimation;
   protected static Animation bossf3bombAnimation;
   private static Vector bulletVec = new Vector();
   protected static Animation doublegravityflashbulletAnimation;
   protected static Animation laserAnimation;
   protected static Animation lizardbulletAnimation;
   protected static Animation mirabulletAnimation;
   protected static Animation missileAnimation;
   protected static Animation monkeybulletAnimation;
   protected static Animation pacmanAnimation;
   protected static Animation penguinbulletAnimation;
   protected static Animation robotbulletAnimation;
   protected static Animation stoneAnimation;
   protected AnimationDrawer drawer;
   private final boolean hitAndDestroy;
   private boolean hitted;
   private boolean isInCamera;

   protected BulletObject(int var1, int var2, int var3, int var4, boolean var5) {
      this.posX = var1;
      this.posY = var2;
      this.velX = var3;
      this.velY = var4;
      this.hitAndDestroy = var5;
      this.refreshCollisionRect(this.posX, this.posY);
   }

   public static void addBullet(int var0, int var1, int var2, int var3, int var4) {
      Object var5;
      Object var6 = null;
      label31:
      switch(var0) {
      case 1:
         var5 = new BeeBullet(var1, var2, var3, var4);
         break;
      case 2:
      case 6:
         var5 = new DoubleGravityFlashBullet(var1, var2, var3, var4, 0);
         break;
      case 3:
      case 4:
      case 5:
      default:
         var5 = var6;
         switch(var0) {
         case 0:
            var5 = new MonkeyBullet(var1, var2, var3, var4);
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 10:
         case 11:
         case 13:
         case 14:
         case 15:
            break label31;
         case 8:
            var5 = new LizardBullet(var1, var2, var3, var4);
            break label31;
         case 9:
            var5 = new BatBullet(var1, var2, var3, var4);
            break label31;
         case 12:
            var5 = new MiraBullet(var1, var2, var3, var4);
            break label31;
         case 16:
            var5 = new MissileBullet(var1, var2, var3, var4);
            break label31;
         case 17:
            var5 = new RobotBullet(var1, var2, var3, var4);
            break label31;
         case 18:
            var5 = new PenguinBullet(var1, var2, var3, var4);
            break label31;
         case 19:
            var5 = new Boss6Bullet(var1, var2, var3, var4);
            break label31;
         case 20:
            var5 = new BossF3Bomb(var1, var2, var3, var4);
            break label31;
         case 21:
            var5 = new BossF3Ray(var1, var2, var3);
            break label31;
         case 22:
            var5 = new BossExtraPacman(var1, var2);
            break label31;
         case 23:
            var5 = new LaserDamage(var1, var2);
            break label31;
         case 24:
            var5 = new BossExtraStone(var1, var2);
            break label31;
         default:
            var5 = var6;
            break label31;
         }
      case 7:
         var5 = new DoubleGravityFlashBullet(var1, var2, var3, var4, 1);
      }

      if (var5 != null) {
         bulletVec.addElement(var5);
      }

   }

   public static void bulletClose() {
      beebulletAnimation = null;
      monkeybulletAnimation = null;
      doublegravityflashbulletAnimation = null;
      lizardbulletAnimation = null;
      batbulletAnimation = null;
      missileAnimation = null;
      boomAnimation = null;
      mirabulletAnimation = null;
      robotbulletAnimation = null;
      penguinbulletAnimation = null;
      boss6bulletAnimation = null;
      bossf3bombAnimation = null;
      stoneAnimation = null;
      laserAnimation = null;
      pacmanAnimation = null;

      for(int var0 = 0; var0 < bulletVec.size(); ++var0) {
         BulletObject var1 = (BulletObject)bulletVec.elementAt(var0);
         var1.close();
      }

      bulletVec.removeAllElements();
   }

   public static void bulletLogicAll() {
      int var1;
      for(int var0 = 0; var0 < bulletVec.size(); var0 = var1 + 1) {
         BulletObject var2 = (BulletObject)bulletVec.elementAt(var0);
         var2.bulletLogic();
         if (var2.chkDestroy()) {
            bulletVec.removeElementAt(var0);
            var1 = var0 - 1;
         } else {
            var1 = var0;
            if (checkPaintNecessary(var2)) {
               paintVec[var2.getPaintLayer()].addElement(var2);
               var1 = var0;
            }
         }
      }

   }

   public static void checkWithAllBullet(PlayerObject var0) {
      int var2;
      for(int var1 = 0; var1 < bulletVec.size(); var1 = var2 + 1) {
         BulletObject var3 = (BulletObject)bulletVec.elementAt(var1);
         if (var3.collisionChkWithObject(var0)) {
            var3.doWhileCollisionWrap(var0);
         } else {
            var3.doWhileNoCollision();
         }

         var2 = var1;
         if (var3.chkDestroy()) {
            bulletVec.removeElementAt(var1);
            var2 = var1 - 1;
         }
      }

   }

   public boolean IsHitted() {
      return this.hitted;
   }

   public abstract void bulletLogic();

   public boolean chkDestroy() {
      boolean var1;
      if (this.isInCamera && !this.isInCamera() || this.isFarAwayCamera() || this.hitAndDestroy && this.hitted) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void close() {
      this.drawer = null;
   }

   public void doBeforeCollisionCheck() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1 == player && player.canBeHurt()) {
         player.beHurt();
         this.hitted = true;
      }

   }

   public void doWhileCollision(ACObject var1, ACCollision var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void logic() {
      if (!this.isInCamera && this.isInCamera()) {
         this.isInCamera = true;
      }

   }
}
