package SonicGBA;

import GameEngine.time.GameTime;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class Cage extends GimmickObject implements MapBehavior {
   private static final int ANIMAL_CREATE_COUNT = 7;
   private static final int BUTTON_OFFSET_Y = -3904;
   private static final int COLLISION_HEIGHT = 3392;
   private static final int COLLISION_WIDTH = 3712;
   private static final int DOOR_SPEED_X = 300;
   private static final int DRAW_HEIGHT = 72;
   private static final int DRAW_WIDTH = 72;
   private static final int STATE_EXPLOSION = 2;
   private static final int STATE_FALL = 0;
   private static final int STATE_STAY = 1;
   private static MFImage cageExplosiveImage = null;
   private static MFImage cageImage = null;
   private int animalCount;
   private boolean attacking;
   private CageButton button;
   private int count;
   private int doorPosX;
   private int doorPosX2;
   private int doorPosY;
   private int doorVelocityY;
   private MapObject mapObj;
   private int state;

   protected Cage(int var1, int var2) {
      super(0, var1, var2, 0, 0, 0, 0);
      if (cageImage == null) {
         try {
            cageImage = MFImage.createImage("/gimmick/cage.png");
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

      if (cageExplosiveImage == null) {
         try {
            cageExplosiveImage = MFImage.createImage("/gimmick/cage_door.png");
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      this.button = new CageButton(this.posX, this.posY - 3904);
      addGameObject(this.button, this.posX, this.posY);
      this.mapObj = new MapObject(this.posX, this.posY, 0, 0, this, 1);
      this.mapObj.setBehavior(this);
      this.state = 0;
      this.attacking = true;
      MapManager.setCameraLeftLimit(MapManager.getCamera().x);
      MapManager.setCameraRightLimit(MapManager.getCamera().x + MapManager.CAMERA_WIDTH);
   }

   public static void releaseAllResource() {
      cageImage = null;
      cageExplosiveImage = null;
   }

   public void close() {
      this.mapObj = null;
      this.button = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.attacking) {
         var1.beHurtByCage();
      } else {
         var1.beStop(0, var2, this);
      }

   }

   public void doWhileToucRoof(int var1, int var2) {
   }

   public void doWhileTouchGround(int var1, int var2) {
      this.state = 1;
      soundInstance.playSe(27);
      MapManager.setShake(8);
   }

   public void draw(MFGraphics var1) {
      int var2;
      int var3;
      MFImage var6;
      switch(this.state) {
      case 2:
         var6 = cageImage;
         var2 = this.posX;
         var3 = this.posY;
         this.drawInMap(var1, var6, 72, 0, 72, 72, 0, var2, var3, 33);
         this.button.drawButton(var1);
         var6 = cageExplosiveImage;
         var2 = this.doorPosX;
         var3 = this.doorPosY;
         this.drawInMap(var1, var6, var2, var3, 40);
         var6 = cageExplosiveImage;
         int var5 = MyAPI.zoomIn(cageExplosiveImage.getWidth());
         int var4 = MyAPI.zoomIn(cageExplosiveImage.getHeight());
         var3 = this.doorPosX2;
         var2 = this.doorPosY;
         this.drawInMap(var1, var6, 0, 0, var5, var4, 2, var3, var2, 36);
         break;
      default:
         var6 = cageImage;
         var3 = this.posX;
         var2 = this.posY;
         this.drawInMap(var1, var6, 0, 0, 72, 72, 0, var3, var2, 33);
         this.button.drawButton(var1);
      }

   }

   public int getGravity() {
      return GRAVITY;
   }

   public int getPaintLayer() {
      return 0;
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
      switch(this.state) {
      case 0:
         this.mapObj.logic();
         this.checkWithPlayer(this.posX, this.posY, this.mapObj.getPosX(), this.mapObj.getPosY());
         this.posX = this.mapObj.getPosX();
         this.posY = this.mapObj.getPosY();
         if (!this.button.used) {
            this.button.posX = this.posX;
            this.button.posY = this.posY - 3904;
         }
         break;
      case 1:
         this.attacking = false;
         if (this.button.used) {
            this.state = 2;
            soundInstance.playSe(28);
            this.doorPosX = this.posX;
            this.doorPosX2 = this.posX;
            this.doorPosY = this.posY;
            this.doorVelocityY = -600;
            this.animalCount = 7;
            this.count = GameTime.set(this, "count", 0);
         }
         break;
      case 2:
         this.doorPosX = GameTime.advance(this, "doorPosX", this.doorPosX, -(300));
         this.doorPosX2 = GameTime.advance(this, "doorPosX2", this.doorPosX2, 300);
         this.doorVelocityY = GameTime.advance(this, "doorVelocityY", this.doorVelocityY, GRAVITY);
         this.doorPosY = GameTime.advancePosition(this, "doorPosY", this.doorPosY, "doorVelocityY", this.doorVelocityY);
         if (this.count > 0) {
            this.count = Math.max(0, GameTime.advance(this, "count", this.count, -(1)));
         }

         if (this.count == 0 && this.animalCount > 0) {
            int var3 = this.posX;
            int var1 = this.posY;
            int var2 = this.posX;
            int var4 = this.posX;
            SmallAnimal.addPatrolAnimal(2, var3, var1 - 640, 1, var2 - 3200, var4 + 3200);
            var4 = this.posX;
            var2 = this.posY;
            var1 = this.posX;
            var3 = this.posX;
            SmallAnimal.addPatrolAnimal(1, var4, var2 - 640, 1, var1 - 3200, var3 + 3200);
            this.count = GameTime.set(this, "count", 4);
            --this.animalCount;
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 1856, var2 - 3392, 3712, 3392);
   }
}
