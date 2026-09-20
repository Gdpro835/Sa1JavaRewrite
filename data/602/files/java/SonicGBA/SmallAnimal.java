package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;
import com.sega.mobile.framework.device.MFGraphics;
import java.util.Vector;

public class SmallAnimal extends GameObject implements MapBehavior {
   private static final int[][] ANIMAL_ID;
   private static final int ANIMAL_INIT_VEL_Y = -1000;
   private static final int ANIMAL_NUM = 12;
   private static final int[] ANIMAL_TYPE_INFO;
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 1024;
   public static final int FLY_VELOCITY_X = -1000;
   public static final int FLY_VELOCITY_Y = -300;
   private static final int[][] STAGE_TO_ANIMAL_ID;
   private static final int[][] STAGE_TO_CAGE_ANIMAL_ID;
   public static final int TYPE_FLY = 2;
   public static final int TYPE_MOVE = 1;
   public static final int TYPE_STAY = 0;
   private static Animation animalAnimation;
   private static Vector animalVec;
   private static int animationIndex;
   private static boolean changeLayer;
   private static int initalVelY;
   protected AnimationDrawer drawer;
   protected MapObject mObj;
   private int sourceLayer;
   private int startPosX;
   private int startPosY;
   protected int type;

   static {
      int[] var0 = new int[]{0, 2, 7, 8, 10};
      int[] var1 = new int[]{5, 9};
      ANIMAL_ID = new int[][]{{1, 3, 4, 6, 11}, var0, var1};
      var0 = new int[15];
      var0[0] = 1;
      var0[2] = 1;
      var0[5] = 2;
      var0[7] = 1;
      var0[8] = 1;
      var0[9] = 2;
      var0[10] = 1;
      var0[12] = 1;
      var0[13] = 1;
      ANIMAL_TYPE_INFO = var0;
      int[] var2 = new int[]{6, 1, 10};
      var1 = new int[]{11, 3, 0};
      var0 = new int[]{4, 7, 12};
      STAGE_TO_ANIMAL_ID = new int[][]{var2, {4, 8, 9}, {3, 2, 5}, {6, 14, 13}, var1, var0};
      var1 = new int[]{0, 10};
      var0 = new int[]{0, 8};
      STAGE_TO_CAGE_ANIMAL_ID = new int[][]{var1, var0, {2, 7}, {12, 13}, {8, 10}};
      animalVec = new Vector();
      initalVelY = -1000;
   }

   public SmallAnimal(int var1, int var2, int var3, int var4, int var5) {
      this.posX = var3;
      this.posY = var4;
      this.startPosX = var3;
      this.startPosY = var4;
      this.sourceLayer = var5;
      this.type = var1;
      this.drawer = animalAnimation.getDrawer(var2, true, 0);
      if (var1 != 2) {
         this.mObj = new MapObject(var3, var4, 0, initalVelY, this, var5);
         this.mObj.setBehavior(this);
      }

   }

   public static void addAnimal(int var0, int var1, int var2) {
      addAnimal(var0, var1, var2, false);
   }

   public static void addAnimal(int var0, int var1, int var2, int var3) {
      if (animalAnimation == null) {
         animalAnimation = new Animation("/animation/animal");
      }

      int[] var5 = ANIMAL_ID[var0];
      int var4 = var5[MyRandom.nextInt(var5.length)];
      SmallAnimal var6 = new SmallAnimal(var0, var4, var1, var2, var3);
      var6.refreshCollisionRect(var1, var2);
      animalVec.addElement(var6);
   }

   public static void addAnimal(int var0, int var1, int var2, boolean var3) {
      int var5 = StageManager.getCurrentZoneId() - 1;
      changeLayer = var3;
      int var4 = STAGE_TO_ANIMAL_ID[var5][animationIndex];
      if (changeLayer) {
         var2 = -var2 + 1;
      }

      addAnimalByID(var4, var0, var1, var2);
      ++animationIndex;
      animationIndex %= STAGE_TO_ANIMAL_ID[var5].length;
      initalVelY = -1000;
   }

   public static void addAnimalByID(int var0, int var1, int var2, int var3) {
      if (animalAnimation == null) {
         animalAnimation = new Animation("/animation/animal");
      }

      SmallAnimal var4 = new SmallAnimal(ANIMAL_TYPE_INFO[var0], var0, var1, var2, var3);
      var4.refreshCollisionRect(var1, var2);
      animalVec.addElement(var4);
   }

   public static void addPatrolAnimal(int var0, int var1, int var2, int var3, int var4) {
      int var5 = MyRandom.nextInt(1, 2);
      addPatrolAnimal(var5, var0, var1, var2, var3, var4);
   }

   public static void addPatrolAnimal(int var0, int var1, int var2, int var3, int var4, int var5) {
      if (animalAnimation == null) {
         animalAnimation = new Animation("/animation/animal");
      }

      int[] var7;
      switch(var0) {
      case 2:
         var7 = ANIMAL_ID[var0];
         break;
      default:
         var7 = STAGE_TO_CAGE_ANIMAL_ID[StageManager.getCurrentZoneId() - 1];
      }

      int var6 = var7[MyRandom.nextInt(var7.length)];
      PatrolAnimal var8 = new PatrolAnimal(var0, var6, var1, var2, var3, var4, var5);
      var8.refreshCollisionRect(var1, var2);
      animalVec.addElement(var8);
   }

   public static void animalClose() {
      for(int var0 = 0; var0 < animalVec.size(); ++var0) {
         SmallAnimal var1 = (SmallAnimal)animalVec.elementAt(var0);
         var1.close();
      }

      animalVec.removeAllElements();
   }

   public static void animalDraw(MFGraphics var0) {
      for(int var1 = 0; var1 < animalVec.size(); ++var1) {
         SmallAnimal var2 = (SmallAnimal)animalVec.elementAt(var1);
         var2.draw(var0);
      }

   }

   public static void animalInit() {
      animalClose();
      if (animalAnimation == null) {
         animalAnimation = new Animation("/animation/animal");
      }

      animationIndex = 0;
   }

   public static void animalLogic() {
      int var1;
      for(int var0 = 0; var0 < animalVec.size(); var0 = var1 + 1) {
         SmallAnimal var2 = (SmallAnimal)animalVec.elementAt(var0);
         var2.logic();
         var1 = var0;
         if (var2.checkDestroy()) {
            var2.close();
            animalVec.removeElementAt(var0);
            var1 = var0 - 1;
         }
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(animalAnimation);
      animalAnimation = null;
   }

   public static void setInitVelY(int var0) {
      initalVelY = var0;
   }

   public boolean checkDestroy() {
      boolean var1;
      if (this.isInCamera()) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void close() {
      this.mObj = null;
      this.drawer = null;
   }

   public void doBeforeCollisionCheck() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
   }

   public void doWhileCollision(ACObject var1, ACCollision var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void doWhileToucRoof(int var1, int var2) {
   }

   public void doWhileTouchGround(int var1, int var2) {
      if (this.type == 0) {
         this.mObj.doStop();
      } else if (this.type == 1) {
         this.mObj.doJump(-300, -1000);
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.drawer);
   }

   public int getGravity() {
      return GRAVITY;
   }

   public boolean hasDownCollision() {
      return true;
   }

   public boolean hasSideCollision() {
      return false;
   }

   public boolean hasTopCollision() {
      return false;
   }

   public void logic() {
      if (this.type != 2) {
         if (this.mObj != null) {
            this.mObj.logic();
            this.posX = this.mObj.getPosX();
            this.posY = this.mObj.getPosY();
            if (changeLayer && this.posY > this.startPosY && this.posY < this.startPosY + 512) {
               this.sourceLayer = -this.sourceLayer + 1;
               this.mObj.setLayer(this.sourceLayer);
               changeLayer = false;
            }
         }
      } else {
         this.posX = GameTime.advance(this, "posX", this.posX, -(1000));
         this.posY = GameTime.advance(this, "posY", this.posY, -(300));
      }

      this.refreshCollisionRect(this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 512, var2 - 1024, 1024, 1024);
   }
}
