package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class ProBoss1 extends EnemyObject {
   private static final int COLLISION_HEIGHT = 64;
   private static final int COLLISION_WIDTH = 1792;
   private static final int SIDE = 671232;
   private static final int SIDE_DOWN_MIDDLE = 720;
   private static final int STATE_ANGRY = 2;
   private static final int STATE_FIND = 1;
   private static final int STATE_GO = 5;
   private static final int STATE_TURN1 = 3;
   private static final int STATE_TURN2 = 4;
   private static final int STATE_WAIT = 0;
   private static Animation boatAni;
   private static Animation faceAni;
   private int StartX;
   private AnimationDrawer boatdrawer;
   private AnimationDrawer facedrawer;
   private int state;
   private int velocity = 576;

   protected ProBoss1(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posX -= this.iLeft * 8;
      this.posY -= this.iTop * 8;
      this.posY += 1280;
      this.StartX = this.posX;
      this.refreshCollisionRect(this.posX >> 6, this.posY >> 6);
      if (boatAni == null) {
         boatAni = new Animation("/animation/pod_boat");
      }

      this.boatdrawer = boatAni.getDrawer(0, true, 0);
      if (faceAni == null) {
         faceAni = new Animation("/animation/pod_face");
      }

      this.facedrawer = faceAni.getDrawer(0, true, 0);
   }

   public static void releaseAllResource() {
      Animation.closeAnimation(boatAni);
      Animation.closeAnimation(faceAni);
      boatAni = null;
      faceAni = null;
   }

   public void close() {
      this.boatdrawer = null;
      this.facedrawer = null;
      super.close();
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.dead) {
      }

   }

   public void draw(MFGraphics var1) {
      if (!this.dead) {
         this.drawInMap(var1, this.boatdrawer);
         this.drawInMap(var1, this.facedrawer, this.posX, this.posY - 1664);
      }

   }

   public void logic() {
      if (!this.dead) {
         if (this.state > 0) {
            isBossEnter = true;
         }

         switch(this.state) {
         case 0:
            if (player.getFootPositionX() >= 671232) {
               if (!this.IsPlayBossBattleBGM) {
                  bossFighting = true;
                  bossID = 21;
                  SoundSystem.getInstance().playBgm(23, true);
                  this.IsPlayBossBattleBGM = true;
                  int var2 = MapManager.CAMERA_HEIGHT / 4 + 720;
                  MapManager.setCameraDownLimit(var2);
                  int var1 = SCREEN_HEIGHT;
                  MapManager.setCameraUpLimit(var2 - var1);
                  var1 = MapManager.getCamera().x;
                  MapManager.setCameraLeftLimit(var1);
               }

               this.state = 1;
               player.setMeetingBoss(false);
               this.facedrawer.setActionId(1);
               this.facedrawer.setLoop(false);
            }
            break;
         case 1:
            if (this.facedrawer.checkEnd()) {
               this.state = 2;
               this.facedrawer.setActionId(2);
               this.facedrawer.setLoop(false);
            }
            break;
         case 2:
            if (this.facedrawer.checkEnd()) {
               this.state = 3;
               this.facedrawer.setActionId(0);
               this.facedrawer.setLoop(true);
               this.boatdrawer.setActionId(1);
               this.boatdrawer.setLoop(false);
            }
            break;
         case 3:
            if (this.boatdrawer.checkEnd()) {
               this.state = 4;
               this.facedrawer.setActionId(0);
               this.facedrawer.setTrans(2);
               this.facedrawer.setLoop(true);
               this.boatdrawer.setActionId(1);
               this.boatdrawer.setTrans(2);
               this.boatdrawer.setLoop(false);
            }
            break;
         case 4:
            if (this.boatdrawer.checkEnd()) {
               this.state = 5;
               this.boatdrawer.setActionId(0);
               this.boatdrawer.setTrans(2);
               this.boatdrawer.setLoop(true);
            }
            break;
         case 5:
            this.posX += this.velocity;
            if (this.posX - this.StartX >> 6 >= 200) {
               player.setMeetingBoss(true);
               MapManager.lockCamera(false);
            }
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1, var2, 1792, 64);
   }
}
