package SonicGBA;

import Lib.AnimationDrawer;
import com.sega.mobile.framework.device.MFGraphics;

class Fountain extends GimmickObject {
   private static final int CRASH_WAIT_FRAME = 8;
   private static final int DRAWER_NUM = 5;
   private static final int DRAW_OFFSET_X = 448;
   private static final int DRAW_OFFSET_Y = -384;
   private static final int LINE_HEIGHT = 80;
   private static final int LINE_WIDTH = 188;
   private static final int PLAYER_CROSS_END_X = 152704;
   private static final int PLAYER_CROSS_START_X = 147200;
   private static final int PLAYER_ON_WATER_MAX_SPEED = 1920;
   private static final int[][] POSITION_OFFSET;
   private static AnimationDrawer[] drawer;
   private int crashCnt;
   private boolean isActived;
   private boolean nonecrashable;
   private boolean touching = false;

   static {
      int[] var0 = new int[2];
      int[] var3 = new int[]{1024, -1024};
      int[] var2 = new int[]{7168, -3072};
      int[] var1 = new int[]{18432, -5120};
      POSITION_OFFSET = new int[][]{var0, var3, var2, {12288, -6144}, var1};
   }

   protected Fountain(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.touching = false;
      this.isActived = false;
      this.crashCnt = 0;
      this.nonecrashable = false;
   }

   public void close() {
      drawer = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (!this.touching && (player.collisionState == 0 || player.collisionState == 2) && player.getFootPositionX() > 147200 && player.getFootPositionX() < 152704) {
         this.nonecrashable = true;
         player.setFootPositionX(player.getFootPositionX() - 3840);
         player.setAnimationId(0);
         player.collisionState = 1;
         player.setVelX(0);
         player.faceDirection = false;
      } else {
         if (!this.touching && this.collisionRect.collisionChk(var1.getFootPositionX(), var1.getFootPositionY())) {
            int var3 = var1.getFootPositionY();
            int var4 = this.getLineY(var1.getFootPositionX());
            byte var5 = player.collisionState;
            if (var5 == 2) {
               this.nonecrashable = true;
            } else if (var3 >= var4 && !this.nonecrashable) {
               this.touching = true;
               player.setOutOfControl(this);
               player.setBodyPositionY(var4);
               player.setAnimationId(30);
               player.setCollisionState((byte)1);
               player.setNoKey();
               player.showWaterFlush = true;
               this.crashCnt = 0;
            }
         }

         if (player.showWaterFlush) {
            this.crashCnt = 0;
            if (this.crashCnt > 8) {
               player.showWaterFlush = false;
            }

            if (!this.isActived) {
               soundInstance.playSe(82);
               this.isActived = true;
            }
         }
      }

   }

   public void doWhileNoCollision() {
      if (this.touching) {
         this.touching = false;
         player.outOfControl = false;
         player.outOfControlObject = null;
         soundInstance.stopLoopSe();
         if (player.collisionState == 1) {
            player.doWhileLand(0);
         }

         this.isActived = false;
         this.crashCnt = 0;
      }

      this.nonecrashable = false;
   }

   public void draw(MFGraphics var1) {
      this.drawCollisionRect(var1);
   }

   public int getLineY(int var1) {
      var1 -= this.posX;
      var1 = (var1 >> 6) * (var1 >> 6) * 80 / '訐';
      int var2 = this.collisionRect.y0;
      return (var1 << 6) + var2;
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      if (this.touching && player.outOfControl) {
         if (player.getVelX() < 0) {
            player.setVelX(player.getVelX() + 300);
         } else {
            player.setVelX(player.getVelX() + 100);
         }

         if (player.getVelX() > 1920) {
            player.setVelX(1920);
         }

         player.setVelY(0);
         player.setFaceDegree(0);
         int var2 = player.getFootPositionX();
         player.getFootPositionY();
         player.setAnimationId(30);
         player.setNoKey();
         player.showWaterFlush = true;
         player.justLeaveLand = false;
         PlayerObject var4 = player;
         int var3 = player.getVelX();
         int var1 = this.getLineY(player.getVelX() + var2);
         var4.moveOnObject(var3 + var2, var1 + 768, true);
         if (player.collisionState == 0) {
            this.touching = false;
            player.outOfControl = false;
            player.outOfControlObject = null;
            var4 = player;
            if (PlayerObject.getCharacterID() == 2 && player.getCharacterAnimationID() == 22) {
               player.setAnimationId(1);
            }
         }
      }

      if (player.getFootPositionX() >= (this.posX >> 6) + 188 - 10 << 6) {
         this.nonecrashable = false;
         this.isActived = false;
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var5 = this.collisionRect;
      int var3 = this.iTop;
      int var4 = this.mHeight;
      var5.setRect(var1 - 13312, var3 * 512 + var2, 24064, var4);
   }
}
