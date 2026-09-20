package SonicGBA;

import GameEngine.time.GameTime;

import GameEngine.Key;
import Lib.Coordinate;
import Lib.SoundSystem;

class FreeFallSystem extends GimmickObject {
   private static final int MOVE_DISTANCE = 30720;
   private static final int MOVE_VELOCITY = 320;
   private static final int SHOOT_TIME = 80;
   private FreeFallBar bar;
   private int barOriginalPosX;
   private int barOriginalPosY;
   private int frame;
   public boolean initFlag;
   private boolean isActive;
   public boolean moving;
   private int plaOriginalPosX;
   private int plaOriginalPosY;
   private FreeFallPlatform platform;
   private int posYOriginal = 0;
   private Coordinate position;
   public boolean releaseAble;
   private int shootCnt;
   public boolean shootDirection;

   protected FreeFallSystem(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posYOriginal = this.posY;
      this.position = new Coordinate();
      this.position.x = this.posX;
      this.position.y = this.posY;
      this.moving = false;
      this.releaseAble = false;
      this.bar = new FreeFallBar(this, this.posX, this.posY);
      this.platform = new FreeFallPlatform(this, this.posX, this.posY);
      addGameObject(this.bar, this.posX, this.posY);
      addGameObject(this.platform, this.posX, this.posY);
      this.plaOriginalPosX = this.posX;
      this.plaOriginalPosY = this.posY;
      this.barOriginalPosX = this.posX;
      this.barOriginalPosY = this.posY;
      this.initFlag = false;
      this.frame = GameTime.set(this, "frame", 0);
      this.isActive = false;
      this.shootCnt = GameTime.set(this, "shootCnt", 0);
      this.shootDirection = false;
   }

   public static void releaseAllResource() {
   }

   public void close() {
      this.bar = null;
      this.platform = null;
      this.position = null;
   }

   public void doInitInCamera() {
      this.posX = this.plaOriginalPosX;
      this.posY = this.plaOriginalPosY;
      this.position.x = this.posX;
      this.position.y = this.posY;
      this.initFlag = false;
      this.bar.init();
      this.platform.init();
   }

   public void doWhileNoCollision() {
      if (this.isActive) {
         this.shootCnt = GameTime.set(this, "shootCnt", 0);
         this.isActive = false;
      }

   }

   public Coordinate getBarPosition() {
      return this.position;
   }

   public Coordinate getPlatformPosition() {
      return this.position;
   }

   public boolean isSystemReady() {
      boolean var1;
      if (this.posY == this.posYOriginal) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void logic() {
      if (this.initFlag) {
         this.refreshCollisionRect(this.posX, this.posY);
         if (!screenRect.collisionChk(this.bar.collisionRect) && !screenRect.collisionChk(this.platform.collisionRect)) {
            this.initFlag = false;
         }
      } else {
         if (this.moving) {
            if (!IsGamePause) {
               this.isActive = true;
            }

            this.posY = GameTime.advance(this, "posY", this.posY, 320);
            if (this.posY >= this.posYOriginal + 30720) {
               this.posY = this.posYOriginal + 30720;
               this.releaseAble = true;
            }

            this.position.y = this.posY;
            this.refreshCollisionRect(this.posX, this.posY);
         }

         this.bar.barLogic();
         this.platform.platformLogic();
         if (this.releaseAble) {
            this.shootCnt = GameTime.advance(this, "shootCnt", this.shootCnt, 1);
            if (this.shootCnt < 80) {
               if (Key.press(Key.gLeft)) {
                  player.changeVisible(true);
                  player.outOfControl = false;
                  player.setVelX(-2000);
                  player.faceDirection = false;
                  this.moving = false;
                  this.frame = GameTime.set(this, "frame", 0);
                  this.releaseAble = false;
                  player.setAnimationId(3);
                  player.restartAniDrawer();
               } else if (Key.press(Key.gRight)) {
                  player.changeVisible(true);
                  player.outOfControl = false;
                  player.setVelX(2000);
                  this.moving = false;
                  this.frame = GameTime.set(this, "frame", 0);
                  this.releaseAble = false;
                  player.faceDirection = true;
                  player.setAnimationId(3);
                  player.restartAniDrawer();
               }
            } else if (!this.shootDirection) {
               player.changeVisible(true);
               player.outOfControl = false;
               player.setVelX(-2000);
               player.faceDirection = false;
               this.moving = false;
               this.frame = GameTime.set(this, "frame", 0);
               this.releaseAble = false;
               player.setAnimationId(3);
               player.restartAniDrawer();
            } else {
               player.changeVisible(true);
               player.outOfControl = false;
               player.setVelX(2000);
               this.moving = false;
               this.frame = GameTime.set(this, "frame", 0);
               this.releaseAble = false;
               player.faceDirection = true;
               player.setAnimationId(3);
               player.restartAniDrawer();
            }
         }

         if (this.moving && !IsGamePause) {
            this.frame = GameTime.advance(this, "frame", this.frame, 1);
            if (this.frame <= 32) {
               if (GameTime.periodic(this, "frame", "logic:172", this.frame, 7, 0)) {
                  SoundSystem.getInstance().playSe(73);
               }
            } else if (this.frame <= 64) {
               if (GameTime.periodic(this, "frame", "logic:176", this.frame, 4, 0)) {
                  SoundSystem.getInstance().playSe(74);
               }
            } else if (GameTime.periodic(this, "frame", "logic:179", this.frame, 2, 0)) {
               SoundSystem.getInstance().playSe(75);
            }
         }

         if (this.posY == this.posYOriginal + 30720 && (player.posX - this.posX > 16384 || this.posX - player.posX > 16384 || player.posY - this.posY > 9216 || this.posY - player.posY > 9216)) {
            this.doInitInCamera();
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
   }
}
