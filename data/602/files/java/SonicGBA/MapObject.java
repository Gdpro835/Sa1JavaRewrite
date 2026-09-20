package SonicGBA;

import GameEngine.time.GameTime;

import Lib.MyAPI;
import com.sega.engine.action.ACBlock;
import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;
import com.sega.engine.action.ACUtilities;
import com.sega.engine.action.ACWorldCalUser;
import com.sega.engine.action.ACWorldCollisionCalculator;
import com.sega.engine.action.ACWorldCollisionLimit;
import com.sega.mobile.framework.device.MFGraphics;

public class MapObject extends GameObject implements ACWorldCalUser, ACWorldCollisionLimit {
   private static final int STATE_GROUND = 0;
   private static final int STATE_SKY = 1;
   private static ACBlock groundBlock = CollisionMap.getInstance().getNewCollisionBlock();
   private static ACBlock skyBlock = CollisionMap.getInstance().getNewCollisionBlock();
   private int LEFT_WALK_COLLISION_CHECK_OFFSET_X;
   private int LEFT_WALK_COLLISION_CHECK_OFFSET_Y;
   private int RIGHT_WALK_COLLISION_CHECK_OFFSET_X;
   private int RIGHT_WALK_COLLISION_CHECK_OFFSET_Y = -512;
   private int centerOffsetX;
   private int centerOffsetY;
   private MapBehavior collisionBehavior;
   private boolean collisionChkBreak;
   private int crashCount;
   private int footX;
   private int footY;
   private int gravity;
   private boolean isAntiGravity;
   private int moveDegree;
   private int moveDistanceX;
   private int moveDistanceY;
   private GameObject object;
   private int state;
   private int totalVelocity;
   private ACWorldCollisionCalculator worldCal;

   public MapObject(int var1, int var2, int var3, int var4, GameObject var5, int var6) {
      this.LEFT_WALK_COLLISION_CHECK_OFFSET_Y = this.RIGHT_WALK_COLLISION_CHECK_OFFSET_Y;
      this.crashCount = 1;
      this.object = var5;
      this.currentLayer = var6;
      this.setPosition(var1, var2, var3, var4, var5);
      this.worldCal = new ACWorldCollisionCalculator(this, this);
      this.worldCal.setLimit(this);
      this.gravity = GameObject.GRAVITY;
   }

   public MapObject(int var1, int var2, int var3, int var4, GameObject var5, int var6, int var7) {
      this.LEFT_WALK_COLLISION_CHECK_OFFSET_Y = this.RIGHT_WALK_COLLISION_CHECK_OFFSET_Y;
      this.crashCount = 1;
      this.object = var5;
      this.currentLayer = var6;
      this.setPosition(var1, var2, var3, var4, var5);
      this.worldCal = new ACWorldCollisionCalculator(this, this);
      this.worldCal.setLimit(this);
      this.gravity = GameObject.GRAVITY;
   }

   public MapObject(GameObject var1, int var2) {
      this.LEFT_WALK_COLLISION_CHECK_OFFSET_Y = this.RIGHT_WALK_COLLISION_CHECK_OFFSET_Y;
      this.crashCount = 1;
      this.object = var1;
      this.currentLayer = var2;
      this.worldCal = new ACWorldCollisionCalculator(this, this);
      this.worldCal.setLimit(this);
      this.gravity = GameObject.GRAVITY;
   }

   private int getNewPointX(int var1, int var2, int var3, int var4) {
      return var1 + var2;
   }

   private int getNewPointY(int var1, int var2, int var3, int var4) {
      return var1 + var3;
   }

   public void calDivideVelocity() {
      this.calDivideVelocity(this.moveDegree);
   }

   public void calDivideVelocity(int var1) {
      this.velX = this.totalVelocity * MyAPI.dCos(var1) / 100;
      this.velY = this.totalVelocity * MyAPI.dSin(var1) / 100;
   }

   public void calTotalVelocity() {
      this.calTotalVelocity(this.moveDegree);
   }

   public void calTotalVelocity(int var1) {
      this.totalVelocity = (this.velX * MyAPI.dCos(var1) + this.velY * MyAPI.dSin(var1)) / 100;
   }

   public void checkWithMap() {
      switch(this.state) {
      case 0:
         int var1 = this.totalVelocity * MyAPI.dCos(this.moveDegree) / 100;
         this.moveDistanceX = var1;
         this.velX = var1;
         var1 = this.totalVelocity * MyAPI.dSin(this.moveDegree) / 100;
         this.moveDistanceY = var1;
         this.velY = var1;
         break;
      case 1:
         this.moveDistanceX = this.velX;
         this.moveDistanceY = this.velY;
         this.moveDegree = 0;
      }

      this.posZ = this.currentLayer;
      this.worldCal.moveVelocity(this.moveDistanceX, this.moveDistanceY);
   }

   public boolean chkCrash() {
      boolean var1;
      if (this.crashCount <= 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void close() {
   }

   public void didAfterEveryMove(int var1, int var2) {
      switch(this.worldCal.actionState) {
      case 0:
         this.state = 0;
         this.moveDegree = this.worldCal.footDegree;
         break;
      case 1:
         this.state = 1;
      }

   }

   public void doBeforeCollisionCheck() {
   }

   public void doJump(int var1, int var2) {
      this.doJump(var1, var2, false);
   }

   public void doJump(int var1, int var2, boolean var3) {
      System.out.println("do jump:" + this.state);
      if (this.state == 0 || var3) {
         this.state = 1;
         this.velX = var1;
         this.velY = var2;
         this.worldCal.stopMove();
         this.worldCal.actionState = 1;
      }

   }

   public void doStop() {
      this.totalVelocity = 0;
      this.velX = 0;
      this.velY = 0;
      this.worldCal.stopMove();
      this.collisionChkBreak = true;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
   }

   public void doWhileCollision(ACObject var1, ACCollision var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void doWhileLand(int var1) {
      --this.crashCount;
      this.state = 0;
      this.totalVelocity = ACUtilities.getTotalFromDegree(this.velX, this.velY, var1);
      if (this.collisionBehavior != null) {
         this.collisionBehavior.doWhileTouchGround(this.velX, this.velY);
      }

   }

   public void doWhileLeaveGround() {
   }

   public void doWhileTouchWorld(int var1, int var2) {
      boolean var3 = false;
      boolean var4 = var3;
      switch(var1) {
      case 0:
         var4 = var3;
         if (this.collisionBehavior != null) {
            this.collisionBehavior.doWhileToucRoof(this.velX, this.velY);
            var4 = var3;
         }
         break;
      case 1:
         var1 = ACUtilities.getTotalFromDegree(this.velX, this.velY, 0);
         var4 = var3;
         if (var1 > 0) {
            var4 = true;
         }
      case 2:
         break;
      case 3:
         var1 = ACUtilities.getTotalFromDegree(this.velX, this.velY, 180);
         var4 = var3;
         if (var1 > 0) {
            var4 = true;
         }
         break;
      default:
         var4 = var3;
      }

      if (var4) {
         --this.crashCount;
      }

   }

   public void draw(MFGraphics var1) {
   }

   public int getBodyDegree() {
      return this.worldCal.footDegree;
   }

   public int getBodyOffset() {
      return this.height >> 1;
   }

   public int getCurrentCrashCount() {
      return this.crashCount;
   }

   public int getFootOffset() {
      return this.width >> 1;
   }

   public int getFootX() {
      return this.posX + this.centerOffsetX;
   }

   public int getFootY() {
      return this.posY + this.centerOffsetY;
   }

   public int getMinDegreeToLeaveGround() {
      return 30;
   }

   public int getPosX() {
      return this.posX;
   }

   public int getPosY() {
      return this.posY;
   }

   public int getPressToGround() {
      return GRAVITY;
   }

   public int getQuaParam(int var1, int var2) {
      if (var1 > 0) {
         var1 /= var2;
      } else {
         var1 = (var1 - (var2 - 1)) / var2;
      }

      return var1;
   }

   public int getVelX() {
      return this.velX;
   }

   public int getVelY() {
      return this.velX;
   }

   public void logic() {
      this.gravity = GameObject.GRAVITY;
      if (this.collisionBehavior != null) {
         this.gravity = this.collisionBehavior.getGravity();
      }

      switch(this.state) {
      case 0:
         if (this.totalVelocity != 0) {
            this.totalVelocity = GameTime.advance(this, "totalVelocity", this.totalVelocity, this.gravity * MyAPI.dSin(this.moveDegree) / 100);
         }
         break;
      case 1:
         int var2 = this.velY;
         int var1;
         if (this.isAntiGravity) {
            var1 = -this.gravity;
         } else {
            var1 = this.gravity;
         }

         this.velY = GameTime.advance(this, "velY", var2, var1);
      }

      this.checkWithMap();
   }

   public void logic2() {
      this.gravity = GameObject.GRAVITY;
      if (this.collisionBehavior != null) {
         this.gravity = this.collisionBehavior.getGravity();
      }

      switch(this.state) {
      case 0:
         this.totalVelocity = 0;
         break;
      case 1:
         int var2 = this.velY;
         int var1;
         if (this.isAntiGravity) {
            var1 = -this.gravity;
         } else {
            var1 = this.gravity;
         }

         this.velY = GameTime.advance(this, "velY", var2, var1);
      }

      this.checkWithMap();
   }

   public boolean noDownCollision() {
      boolean var1;
      if (this.collisionBehavior != null) {
         if (this.collisionBehavior.hasDownCollision()) {
            var1 = false;
         } else {
            var1 = true;
         }
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean noSideCollision() {
      boolean var1;
      if (this.collisionBehavior != null) {
         if (this.collisionBehavior.hasSideCollision()) {
            var1 = false;
         } else {
            var1 = true;
         }
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean noTopCollision() {
      boolean var1;
      if (this.collisionBehavior != null) {
         if (this.collisionBehavior.hasTopCollision()) {
            var1 = false;
         } else {
            var1 = true;
         }
      } else {
         var1 = false;
      }

      return var1;
   }

   public void refreshCollisionRect(int var1, int var2) {
   }

   public void reset() {
      this.worldCal.setLimit((ACWorldCollisionLimit)null);
      this.worldCal = null;
   }

   public void setAntiGravity(boolean var1) {
      this.isAntiGravity = var1;
   }

   public void setBehavior(MapBehavior var1) {
      this.collisionBehavior = var1;
   }

   public void setCrashCount(int var1) {
      this.crashCount = var1;
   }

   public void setLayer(int var1) {
      this.currentLayer = var1;
   }

   public void setPosition(int var1, int var2, int var3, int var4, GameObject var5) {
      this.state = 1;
      this.posX = var1;
      this.posY = var2;
      this.velX = var3;
      this.velY = var4;
      this.object = var5;
      this.object.refreshCollisionRect(this.posX, this.posY);
      CollisionRect var6 = this.object.getCollisionRect();
      this.footX = var6.x0 + var6.x1 >> 1;
      this.footY = var6.y1;
      this.width = var6.x1 - var6.x0;
      this.height = var6.y1 - var6.y0;
      this.centerOffsetX = this.footX - this.posX;
      this.centerOffsetY = this.footY - this.posY;
      this.RIGHT_WALK_COLLISION_CHECK_OFFSET_X = var6.getWidth() >> 1;
      this.LEFT_WALK_COLLISION_CHECK_OFFSET_X = -this.RIGHT_WALK_COLLISION_CHECK_OFFSET_X;
   }

   public void setVel(int var1, int var2) {
      this.velX = var1;
      this.velY = var2;
      this.calTotalVelocity();
   }
}
