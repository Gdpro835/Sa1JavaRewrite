package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;
import java.util.Vector;

public class RocketSeparateEffect implements SonicDef {
   private static final int ANICOUNT_TIME = 5;
   private static final String PARTS_ANIMATION_PATH = "/animation/parts";
   private static final int PARTS_INFO_TYPE = 0;
   private static final int PARTS_INFO_VX = 3;
   private static final int PARTS_INFO_VY = 4;
   private static final int PARTS_INFO_X = 1;
   private static final int PARTS_INFO_Y = 2;
   private static final int PARTS_NUM = 6;
   private static final int SHAKING_COUNT = 80;
   private static final int STATE_INIT = 1;
   private static final int STATE_MAP_BROKE_1 = 6;
   private static final int STATE_MAP_BROKE_2 = 7;
   private static final int STATE_MAP_BROKE_3 = 8;
   private static final int STATE_MAP_BROKE_4 = 9;
   private static final int STATE_NONE = 0;
   private static final int STATE_OVER = 3;
   private static final int STATE_SHAKING = 2;
   private static final int STATE_WAITING = 4;
   private static final int STATE_WAITING_CAMERA = 5;
   private static RocketSeparateEffect instance;
   private int brokeOffset;
   private int brokeVel;
   private int count;
   private int effectID;
   private int markY;
   private AnimationDrawer partsDrawer = (new Animation("/animation/parts")).getDrawer();
   private Vector partsInfoVec = new Vector();
   private int state = 0;

   private RocketSeparateEffect() {
   }

   public static void clearInstance() {
      instance = null;
   }

   public static RocketSeparateEffect getInstance() {
      if (instance == null) {
         instance = new RocketSeparateEffect();
      }

      return instance;
   }

   public void close() {
      this.state = 0;
   }

   public void createParts(int var1) {
      for(int var2 = 0; var2 < var1; ++var2) {
         int[] var3 = new int[]{MyRandom.nextInt(6), MyRandom.nextInt(SCREEN_WIDTH), MyRandom.nextInt(-20, -10), MyRandom.nextInt(-5, 5), 0};
         this.partsInfoVec.addElement(var3);
      }

   }

   public void draw(MFGraphics var1) {
      this.partsDraw(var1);
   }

   public void functionSecond(int var1) {
      this.effectID = var1;
      if (PlayerObject.stageModeState == 0) {
         PlayerObject.setTimeCount(5, 0, 0);
         PlayerObject.setOverCount(0, 0, 0);
      }

      switch(var1) {
      case 2:
         BackGroundManager.next();
      case 0:
      case 1:
      default:
      }
   }

   public void init(int var1) {
      this.effectID = var1;
      switch(var1) {
      case 0:
         MapManager.setCameraLeftLimit(1056);
         break;
      case 1:
         this.markY = 696;
         break;
      case 2:
         this.markY = 408;
      }

      GameObject.player.setMeetingBoss(false);
      if (GameObject.player2 != null) GameObject.player2.setMeetingBoss(false);
      this.state = 1;
   }

   public void logic() {
      if (!GameObject.IsGamePause) {
         ++this.count;
         int var1;
         int var2;
         switch(this.state) {
         case 0:
         default:
            break;
         case 1:
            this.count = 0;
            this.state = 4;
            if (this.effectID != 0) {
               PlayerObject.timeStopped = true;
            }
            break;
         case 2:
            MapManager.setShake(20, MyRandom.nextInt(5, 10));
            if (this.count == 80) {
               this.state = 3;
            }

            if (this.count % 4 == 0) {
               SoundSystem.getInstance().playSe(35);
            }
            break;
         case 3:
            PlayerObject.timeStopped = false;
            GameObject.player.setMeetingBoss(true);
            if (GameObject.player2 != null) GameObject.player2.setMeetingBoss(true);
            var2 = MapManager.proposeUpCameraLimit;
            var1 = MapManager.proposeDownCameraLimit;
            int var4 = MapManager.proposeLeftCameraLimit;
            int var3 = MapManager.proposeRightCameraLimit;
            StageManager.saveCheckPointCamera(var2, var1, var4, var3);
            MapManager.actualUpCameraLimit = MapManager.proposeUpCameraLimit;
            this.state = 0;
            if (PlayerObject.stageModeState == 0) {
               PlayerObject.setTimeCount(5, 0, 0);
               PlayerObject.setOverCount(0, 0, 0);
            }
            break;
         case 4:
            if (((GameObject.player.isOnGound() || GameObject.player2 != null && GameObject.player2.isOnGound())) && this.count > 10) {
               switch(this.effectID) {
               case 0:
                  this.state = 2;
                  break;
               case 1:
               case 2:
                  MapManager.setCameraUpLimit((this.markY - 2) * 8);
                  this.state = 5;
               }

               this.count = 0;
            }
            break;
         case 5:
            if (MapManager.proposeUpCameraLimit == MapManager.actualUpCameraLimit) {
               this.state = 9;
               this.brokeOffset = 4;
               this.brokeVel = 2;
               this.createParts(2);
               MapManager.setMapBrokeParam(this.markY, this.brokeOffset);
               this.count = 0;
               if (this.effectID == 2) {
                  BackGroundManager.next();
               }
            }
            break;
         case 6:
            if (this.count % 4 == 0) {
               SoundSystem.getInstance().playSe(35);
            }

            this.brokeOffset += this.brokeVel;
            if (this.brokeOffset > this.brokeVel * 10) {
               this.state = 7;
            }

            MapManager.setMapBrokeParam(this.markY, this.brokeOffset);
            break;
         case 7:
            if (this.count % 4 == 0) {
               SoundSystem.getInstance().playSe(35);
            }

            ++this.brokeVel;
            this.brokeOffset += this.brokeVel;
            this.createParts(2);
            MapManager.setShakeX(MyRandom.nextInt(5, 10));
            MapManager.setShake(20, MyRandom.nextInt(5, 10));
            MapManager.setMapBrokeParam(this.markY, this.brokeOffset);
            if (this.brokeOffset > SCREEN_HEIGHT) {
               MapManager.releaseCameraUpLimit();
               MapManager.setCameraDownLimit(this.markY * 8);
               PlayerObject.isDeadLineEffect = true;
               this.state = 8;
            }
            break;
         case 8:
            if (this.count % 4 == 0) {
               SoundSystem.getInstance().playSe(35);
            }

            this.createParts(2);
            MapManager.setShakeX(MyRandom.nextInt(5, 10));
            MapManager.setShake(20, MyRandom.nextInt(5, 10));
            var1 = MapManager.proposeDownCameraLimit;
            var2 = MapManager.actualDownCameraLimit;
            if (var1 == var2) {
               this.state = 3;
            }
            break;
         case 9:
            if (this.count == 2) {
               SoundSystem.getInstance().playSequenceSe(45);
            }

            if (this.count > 8) {
               this.state = 6;
            }
         }
      }

   }

   public void partsDraw(MFGraphics var1) {
      for(int var2 = 0; var2 < this.partsInfoVec.size(); ++var2) {
         int[] var6 = (int[])this.partsInfoVec.elementAt(var2);
         if (!GameObject.IsGamePause) {
            var6[4] += 2;
            var6[1] += var6[3];
            var6[2] += var6[4];
            if (var6[2] > SCREEN_HEIGHT + 20) {
               this.partsInfoVec.removeElementAt(var2);
               --var2;
               continue;
            }
         }

         AnimationDrawer var7 = this.partsDrawer;
         int var5 = var6[0];
         int var3 = var6[1];
         int var4 = var6[2];
         var7.draw(var1, var5, var3, var4, false, 0);
      }

   }
}
