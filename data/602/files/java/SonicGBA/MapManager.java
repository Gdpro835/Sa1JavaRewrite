package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import java.io.DataInputStream;
import java.io.InputStream;
import com.sega.MFLib.Main;

public class MapManager implements SonicDef {
   public static final int CAMERA_HEIGHT;
   private static final int CAMERA_MAX_SPEED_X = 48;
   private static final int CAMERA_MAX_SPEED_Y = 48;
   public static final int CAMERA_OFFSET_X;
   public static final int CAMERA_OFFSET_Y;
   private static final int CAMERA_SPEED = 5;
   public static final int CAMERA_WIDTH;
   private static final int CAM_OFF = 5;
   public static final int COLOR_SPACE = 20;
   public static final int END_COLOR = 16777215;
   private static int IMAGE_TILE_WIDTH;
   private static final int LINE_HEIGHT = 2;
   private static final int LOAD_BACK = 5;
   private static final int LOAD_CAMERA_RESET = 6;
   private static final int LOAD_FRONT = 4;
   private static final int LOAD_MAP_IMAGE = 0;
   private static final int LOAD_MODEL = 3;
   private static final int LOAD_OPEN_FILE = 1;
   private static final int LOAD_OVERALL = 2;
   private static final int LOOP_COUNT;
   public static final String MAP_EXTEND_NAME = ".pm";
   private static final boolean MODEL_CACHE_DRAW = false;
   private static final int MODEL_HEIGHT = 6;
   private static final int MODEL_WIDTH = 6;
   private static final boolean NO_LINE = true;
   public static final String PNG_NAME = "/stage";
   private static final int RECT_FRAME_WIDTH = 40;
   private static final int SHAKE_RANGE = 6;
   public static final int START_COLOR = 6067452;
   public static final int START_COLOR_2 = 15964672;
   private static final int TILE_HEIGHT = 16;
   private static final int TILE_WIDTH = 16;
   private static final int WIND_LOOP_WIDTH = 480;
   private static final int[][] WIND_POSITION;
   public static int actualDownCameraLimit;
   public static int actualLeftCameraLimit;
   public static int actualRightCameraLimit;
   public static int actualUpCameraLimit;
   private static int brokeOffsetY;
   private static int brokePointY;
   private static Coordinate camera;
   private static int cameraActionX;
   private static int cameraActionY;
   private static boolean cameraLocked;
   private static boolean cameraUpDownLocked;
   private static DataInputStream ds;
   public static Focusable focusObj;
   public static int gameFrame;
   public static MFImage image;
   private static InputStream is;
   private static int loadStep;
   public static short[][] mapBack;
   public static short[][] mapFront;
   public static int mapHeight;
   private static int mapLoopLeft;
   private static int mapLoopRight;
   public static short[][][] mapModel;
   public static int mapOffsetX;
   public static int mapVelX;
   public static int mapWidth;
   private static int mappaintframe;
   private static MFImage[] modelImageArray;
   private static int[] modelRGB = new int[9216];
   public static int proposeDownCameraLimit;
   public static int proposeLeftCameraLimit;
   public static int proposeRightCameraLimit;
   public static int proposeUpCameraLimit;
   public static Coordinate reCamera;
   private static int shakeCount;
   private static int shakeMaxCount;
   private static int shakePowerX;
   private static int shakePowerY;
   private static boolean shakingUp;
   private static boolean stageFlag;
   private static int stage_id;
   public static MFImage[] tileimage;
   private static AnimationDrawer windDrawer;
   private static MFImage windImage;
   private static int[] zone4TileLoopID;
   private static int[] zone4TileLoopID_Low;

   static {
      for(int var0 = 0; var0 < modelRGB.length; ++var0) {
         modelRGB[var0] |= -16777216;
      }

      camera = new Coordinate();
      cameraActionX = 2;
      cameraActionY = 2;
      CAMERA_WIDTH = SCREEN_WIDTH;
      CAMERA_HEIGHT = SCREEN_HEIGHT;
      CAMERA_OFFSET_X = (int)((SCREEN_WIDTH - CAMERA_WIDTH) / 2.0f);
      CAMERA_OFFSET_Y = (int)((SCREEN_HEIGHT - CAMERA_HEIGHT) / 2.0f);
      IMAGE_TILE_WIDTH = 16;
      mapVelX = -30;
      gameFrame = 0;
      reCamera = new Coordinate();
      loadStep = 0;
      stageFlag = false;
      int[] var6 = new int[]{0, 24, 0};
      int[] var1 = new int[]{60, 80, 1};
      int[] var3 = new int[]{100, 0, 1};
      int[] var8 = new int[]{120, 44, 0};
      int[] var7 = new int[]{216, 58, 1};
      int[] var2 = new int[]{296, 12, 0};
      int[] var4 = new int[]{352, 72, 0};
      int[] var5 = new int[]{386, 36, 1};
      WIND_POSITION = new int[][]{var6, var1, var3, var8, var7, var2, var4, var5};
      LOOP_COUNT = (SCREEN_WIDTH + 960 - 1) / 480;
      mappaintframe = 0;
      var1 = new int[]{0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 7, 8, 9, 9, 9, 9, 9, 9, 9, 9, 10, 11, 12, 13, 14, 14, 14, 14, 14, 14, 14, 14, 15, 16, 17, 18, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19};
      zone4TileLoopID = var1;
      var1 = new int[]{0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
      zone4TileLoopID_Low = var1;
   }

   public static void calCameraImmidiately() {
      actualUpCameraLimit = proposeUpCameraLimit;
      actualDownCameraLimit = proposeDownCameraLimit;
      actualLeftCameraLimit = proposeLeftCameraLimit;
      actualRightCameraLimit = proposeRightCameraLimit;
   }

   private static void cameraActionX() {
      int var5 = focusObj.getFocusX() - (CAMERA_WIDTH >> 1) - CAMERA_OFFSET_X;
      Coordinate var9;
      switch(cameraActionX) {
      case 0:
         int var10 = camera.x;
         var9 = camera;
         double var2 = (double)camera.x;
         double var0 = (double)var5;
         var9.x = MyAPI.calNextPosition(var2, var0, 3, 6, 4.0D);
         if (Math.abs(var10 - camera.x) > 48) {
            if (camera.x > var10) {
               camera.x = var10 + 48;
            }

            if (camera.x < var10) {
               camera.x = var10 - 48;
            }
         }
         break;
      case 1:
         if (var5 < 0) {
            var5 = 0;
         }

         int var6 = (var5 - camera.x) * 100 / 5;
         var9 = camera;
         int var8 = var9.x;
         int var7 = var6 / 100;
         byte var4;
         if (var6 == 0) {
            var4 = 0;
         } else if (var6 > 0) {
            var4 = 5;
         } else {
            var4 = -5;
         }

         var9.x = var8 + var7 + var4;
         if ((var5 * 100 - camera.x * 100) * var6 <= 0) {
            cameraActionX = 0;
         }
         break;
      case 2:
         camera.x = var5;
         cameraActionX = 0;
      }

   }

   private static void cameraActionY() {
      int var5 = focusObj.getFocusY() - (CAMERA_HEIGHT >> 1) - CAMERA_OFFSET_Y;
      Coordinate var9;
      switch(cameraActionY) {
      case 0:
         int var10 = camera.y;
         var9 = camera;
         double var2 = (double)camera.y;
         double var0 = (double)var5;
         var9.y = MyAPI.calNextPosition(var2, var0, 3, 6, 4.0D);
         if (Math.abs(var10 - camera.y) > 48) {
            if (camera.y > var10) {
               camera.y = var10 + 48;
            }

            if (camera.y < var10) {
               camera.y = var10 - 48;
            }
         }
         break;
      case 1:
         if (focusObj.getFocusY() - (CAMERA_HEIGHT >> 1) < 0) {
            var5 = 0;
         } else {
            var5 = focusObj.getFocusY() - (CAMERA_HEIGHT >> 1);
         }

         int var6 = (var5 - camera.y) * 100 / 5;
         var9 = camera;
         int var7 = var9.y;
         int var8 = var6 / 100;
         byte var4;
         if (var6 == 0) {
            var4 = 0;
         } else if (var6 > 0) {
            var4 = 5;
         } else {
            var4 = -5;
         }

         var9.y = var7 + var8 + var4;
         if ((var5 * 100 - camera.y * 100) * var6 <= 0) {
            cameraActionY = 0;
         }
         break;
      case 2:
         camera.y = var5;
         cameraActionY = 0;
      }

   }

   public static void cameraLogic() {
      if (focusObj != null && !cameraLocked) {
         if (getPixelWidth() < CAMERA_WIDTH) {
            camera.x = (CAMERA_WIDTH - getPixelWidth()) / 2;
         } else {
            cameraActionX();
            if (actualLeftCameraLimit > proposeLeftCameraLimit) {
               actualLeftCameraLimit -= 5;
               if (actualLeftCameraLimit < proposeLeftCameraLimit) {
                  actualLeftCameraLimit = proposeLeftCameraLimit;
               }
            } else if (actualLeftCameraLimit < proposeLeftCameraLimit) {
               actualLeftCameraLimit += 5;
               if (actualLeftCameraLimit > proposeLeftCameraLimit) {
                  actualLeftCameraLimit = proposeLeftCameraLimit;
               }
            }

            if (actualRightCameraLimit < proposeRightCameraLimit) {
               actualRightCameraLimit += 5;
               if (actualRightCameraLimit > proposeRightCameraLimit) {
                  actualRightCameraLimit = proposeRightCameraLimit;
               }
            } else if (actualRightCameraLimit > proposeRightCameraLimit) {
               actualRightCameraLimit -= 5;
               if (actualRightCameraLimit < proposeRightCameraLimit) {
                  actualRightCameraLimit = proposeRightCameraLimit;
               }
            }

            if (camera.x < actualLeftCameraLimit - CAMERA_OFFSET_X) {
               camera.x = actualLeftCameraLimit - CAMERA_OFFSET_X;
               cameraActionX = 0;
            }

            if (actualRightCameraLimit != getPixelWidth() && camera.x > actualRightCameraLimit - CAMERA_WIDTH - CAMERA_OFFSET_X) {
               camera.x = actualRightCameraLimit - CAMERA_WIDTH - CAMERA_OFFSET_X;
               cameraActionX = 0;
            }

            if (actualRightCameraLimit != getPixelWidth() && Math.abs(actualRightCameraLimit - actualLeftCameraLimit) < CAMERA_WIDTH) {
               camera.x = actualRightCameraLimit + actualLeftCameraLimit - CAMERA_WIDTH >> 1;
               cameraActionX = 0;
            }
         }

         if (!cameraUpDownLocked) {
            if (getPixelHeight() < CAMERA_HEIGHT) {
               camera.y = (CAMERA_HEIGHT - getPixelHeight()) / 2;
            } else {
               cameraActionY();
               if (actualDownCameraLimit > proposeDownCameraLimit) {
                  actualDownCameraLimit -= 5;
                  if (actualDownCameraLimit < proposeDownCameraLimit) {
                     actualDownCameraLimit = proposeDownCameraLimit;
                  }
               } else if (actualDownCameraLimit < proposeDownCameraLimit) {
                  actualDownCameraLimit += 5;
                  if (actualDownCameraLimit > proposeDownCameraLimit) {
                     actualDownCameraLimit = proposeDownCameraLimit;
                  }
               }

               if (actualUpCameraLimit < proposeUpCameraLimit) {
                  actualUpCameraLimit += 5;
                  if (actualUpCameraLimit > proposeUpCameraLimit) {
                     actualUpCameraLimit = proposeUpCameraLimit;
                  }
               } else if (actualUpCameraLimit > proposeUpCameraLimit) {
                  actualUpCameraLimit -= 5;
                  if (actualUpCameraLimit < proposeUpCameraLimit) {
                     actualUpCameraLimit = proposeUpCameraLimit;
                  }
               }

               if (camera.y < actualUpCameraLimit - CAMERA_OFFSET_Y) {
                  camera.y = actualUpCameraLimit - CAMERA_OFFSET_Y;
                  cameraActionY = 0;
               } else if (camera.y > actualDownCameraLimit - CAMERA_HEIGHT - CAMERA_OFFSET_Y) {
                  camera.y = actualDownCameraLimit - CAMERA_HEIGHT - CAMERA_OFFSET_Y;
                  cameraActionY = 0;
               }
            }
         }

         Coordinate var3 = camera;
         var3.x += shakePowerX;
         shakePowerX = 0;
         if (shakeCount > 0) {
            --shakeCount;
            int var0 = shakeCount * shakePowerY / shakeMaxCount;
            var3 = camera;
            int var1 = var3.y;
            if (!shakingUp) {
               var0 = -var0;
            }

            var3.y = var1 + var0;
            boolean var2;
            if (shakingUp) {
               var2 = false;
            } else {
               var2 = true;
            }

            shakingUp = var2;
            if (camera.x < 0) {
               camera.x = 0;
            }

            if (camera.x + CAMERA_WIDTH > getPixelWidth()) {
               camera.x = getPixelWidth() - CAMERA_WIDTH;
            }

            if (camera.y < 0) {
               camera.y = 0;
            }

            if (camera.y + CAMERA_HEIGHT > getPixelHeight()) {
               camera.y = getPixelHeight() - CAMERA_HEIGHT;
            }
         }

         if (StageManager.getCurrentZoneId() == 8) {
            mapOffsetX += mapVelX;
            if (-mapOffsetX + camera.x > getPixelWidth() - CAMERA_WIDTH && Math.abs(mapOffsetX) < 0) {
               mapOffsetX += 448;
            }
         }
      }

   }

   public static void closeMap() {
      image = null;
      int var0;
      if (tileimage != null) {
         for(var0 = 0; var0 < tileimage.length; ++var0) {
            tileimage[var0] = null;
         }
      }

      tileimage = null;
      mapModel = null;
      mapFront = null;
      mapBack = null;
      windImage = null;
      Animation.closeAnimationDrawer(windDrawer);
      windDrawer = null;
      if (modelImageArray != null) {
         for(var0 = 0; var0 < modelImageArray.length; ++var0) {
            modelImageArray[var0] = null;
         }
      }

      modelImageArray = null;
   }

   public static void drawBack(MFGraphics var0) {
      BackGroundManager.drawBackGround(var0);
      drawMap(var0, mapBack);
   }

   public static void drawFront(MFGraphics var0) {
      drawMap(var0, mapFront);
   }

   public static void drawFrontNatural(MFGraphics var0) {
      BackGroundManager.drawFrontNatural(var0);
   }

   private static void drawMap(MFGraphics var0, short[][] var1) {
      int var2 = (camera.x + CAMERA_OFFSET_X - mapOffsetX) / 16;
      int var7 = (camera.y + CAMERA_OFFSET_Y) / 16;
      int var9 = (camera.x + CAMERA_WIDTH + 16 - 1 + CAMERA_OFFSET_X - mapOffsetX) / 16;
      int var8 = (camera.y + CAMERA_HEIGHT + 16 - 1 + CAMERA_OFFSET_Y) / 16;

      int var4;
      for(int var3 = -1; var2 < var9; var2 = var4) {
         label69: {
            int var6 = var2 / 6;
            boolean var5;
            if (var6 != var3) {
               var5 = true;

               for(var3 = var7 / 6; var3 < (var8 + 6 - 1) / 6; ++var3) {
                  if (getModelIdByIndex(var1, var6, var3) != 0) {
                     var5 = false;
                     break;
                  }
               }

               var4 = var2 / 6;
               var3 = var4;
               if (var5) {
                  var3 = (var6 + 1) * 6 - 1;
                  var2 = var4;
                  var4 = var3;
                  break label69;
               }
            }

            for(var4 = var7; var4 < var8; ++var4) {
               int var11 = getModelId(var1, var2, var4);
               if (var11 == 0) {
                  var4 /= 6;
                  var4 = (var4 + 1) * 6 - 1;
               } else {
                  int var10 = getTileId(var1, var2, var4);
                  boolean var12;
                  if (('耀' & var10) != 0) {
                     var12 = true;
                  } else {
                     var12 = false;
                  }

                  if ((var10 & 16384) != 0) {
                     var5 = true;
                  } else {
                     var5 = false;
                  }

                  if (var5) {
                     var11 = 0 | 2;
                  } else {
                     var11 = 0;
                  }

                  if (var12) {
                     var11 |= 1;
                  }

                  drawTile(var0, var10 & 16383, var2, var4, var11);
               }
            }

            var4 = var2;
            var2 = var3;
         }

         ++var4;
         var3 = var2;
      }

   }

   public static void drawMapFrame(MFGraphics var0) {
      if (CAMERA_OFFSET_X > 0 || CAMERA_OFFSET_Y > 0) {
         int var1;
         int var2;
         int var3;
         int var4;
         if (CAMERA_OFFSET_Y > 0) {
            var0.setColor(255);
            MyAPI.fillRect(var0, 0, 0, SCREEN_WIDTH, CAMERA_OFFSET_Y);
            var4 = SCREEN_HEIGHT;
            var3 = CAMERA_OFFSET_Y;
            var1 = SCREEN_WIDTH;
            var2 = CAMERA_OFFSET_Y;
            MyAPI.fillRect(var0, 0, var4 - var3, var1, var2);
         }

         if (CAMERA_OFFSET_X > 0) {
            var0.setColor(255);
            var4 = CAMERA_OFFSET_Y;
            var1 = CAMERA_OFFSET_X;
            var2 = SCREEN_HEIGHT;
            var3 = CAMERA_OFFSET_Y;
            MyAPI.fillRect(var0, 0, var4 + 0, var1, var2 - (var3 << 1));
            int var6 = SCREEN_WIDTH;
            var2 = CAMERA_OFFSET_X;
            var3 = CAMERA_OFFSET_Y;
            var4 = CAMERA_OFFSET_X;
            int var5 = SCREEN_HEIGHT;
            var1 = CAMERA_OFFSET_Y;
            MyAPI.fillRect(var0, var6 - var2, var3 + 0, var4, var5 - (var1 << 1));
         }
      }

   }

   private static void drawTile(MFGraphics var0, int var1, int var2, int var3, int var4) {
      if (var1 != 0) {
         int var5 = var1 % IMAGE_TILE_WIDTH;
         int var6 = var1 / IMAGE_TILE_WIDTH;
         int var7;
         int var8;
         int var9;
         MFImage var10;
         if (!stageFlag) {
            var10 = image;
            var9 = camera.x;
            var7 = mapOffsetX;
            var8 = camera.y;
            if (var3 >= brokePointY) {
               var1 = brokeOffsetY;
            } else {
               var1 = 0;
            }

            MyAPI.drawImage(var0, var10, var5 * 16, var6 * 16, 16, 16, var4, var2 * 16 - var9 + var7, var3 * 16 - var8 + var1, 20);
         } else {
            mappaintframe = gameFrame;
            switch(stage_id) {
            case 0:
            case 1:
            case 8:
            case 9:
            case 10:
               mappaintframe %= tileimage.length;
            case 2:
            case 3:
            case 6:
            case 7:
            default:
               break;
            case 4:
            case 5:
            case 11:
               mappaintframe = gameFrame % (tileimage.length * 2) / 2;
            }

            if (stage_id == 6 || stage_id == 7) {
               if (MFDevice.getDeviceHeight() > 320 && MFDevice.getDeviceWidth() > 480) {
                  var1 = gameFrame;
                  var7 = zone4TileLoopID.length;
                  mappaintframe = zone4TileLoopID[var1 % var7];
               } else {
                  var7 = gameFrame;
                  var1 = zone4TileLoopID_Low.length;
                  mappaintframe = zone4TileLoopID_Low[var7 % var1];
               }
            }

            var10 = tileimage[mappaintframe];
            var8 = camera.x;
            var9 = mapOffsetX;
            var7 = camera.y;
            if (var3 >= brokePointY) {
               var1 = brokeOffsetY;
            } else {
               var1 = 0;
            }

            MyAPI.drawImage(var0, var10, var5 * 16, var6 * 16, 16, 16, var4, var2 * 16 - var8 + var9, var3 * 16 - var7 + var1, 20);
         }
      }

   }

   private static void drawWind(MFGraphics var0) {
      if (windImage == null) {
         windImage = MFImage.createImage("/animation/bg_cloud_" + StageManager.getCurrentZoneId() + ".png");
         windDrawer = (new Animation(windImage, "/animation/bg_cloud")).getDrawer();
      }

      if (windImage != null && windDrawer != null) {
         int var3 = camera.x / 8 % 480;

         for(int var1 = -1; var1 < LOOP_COUNT; ++var1) {
            for(int var2 = 0; var2 < WIND_POSITION.length; ++var2) {
               if (WIND_POSITION[var2][0] - var3 + var1 * 480 >= -(SCREEN_WIDTH >> 1)) {
                  if (WIND_POSITION[var2][0] - var3 + var1 * 480 > SCREEN_WIDTH) {
                     break;
                  }

                  windDrawer.setActionId(WIND_POSITION[var2][2]);
                  AnimationDrawer var6 = windDrawer;
                  int var5 = WIND_POSITION[var2][0];
                  int var4 = WIND_POSITION[var2][1];
                  var6.draw(var0, var5 - var3 + var1 * 480, var4);
               }
            }
         }
      }

   }

   private static void fillChangeColorRect(MFGraphics var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      var2 = (var4 + var7 - 1) / var7;

      for(var1 = 0; var1 < var2; ++var1) {
         int var8 = (((16711680 & var6) >> 16) * var1 + ((16711680 & var5) >> 16) * (var2 - var1)) / var2;
         var4 = ((('\uff00' & var6) >> 8) * var1 + (('\uff00' & var5) >> 8) * (var2 - var1)) / var2;
         int var9 = (((var6 & 255) >> 0) * var1 + ((var5 & 255) >> 0) * (var2 - var1)) / var2;
         var0.setColor(var9 << 0 & 255 | var4 << 8 & '\uff00' | var8 << 16 & 16711680);
         MyAPI.fillRect(var0, 0, var7 * var1, var3, var7);
      }

   }

   public static void focusQuickLocation() {
      cameraActionX = 0;
      cameraActionY = 0;
      if (focusObj != null) {
         camera.x = focusObj.getFocusX() - (CAMERA_WIDTH >> 1);
         camera.y = focusObj.getFocusY() - (CAMERA_HEIGHT >> 1);
      }

      cameraLogic();
   }
   
   public static void focusQuickLocation2(PlayerObject obj) {
      cameraActionX = 0;
      cameraActionY = 0;
      if (obj != null) {
         camera.x = obj.getFocusX() - (CAMERA_WIDTH >> 1);
         camera.y = obj.getFocusY() - (CAMERA_HEIGHT >> 1);
      }

      cameraLogic();
   }

   public static Coordinate getCamera() {
      return camera;
   }

   public static int getCameraRightLimit() {
      return proposeRightCameraLimit;
   }

   public static int getConvertX(int var0) {
      int var1 = var0;
      if (var0 >= mapLoopRight) {
         var1 = mapLoopRight - mapLoopLeft;
         switch(StageManager.getCurrentZoneId()) {
         case 8:
            var1 = mapLoopLeft + (var0 - mapLoopRight) % var1;
            break;
         default:
            var1 = mapLoopLeft + (var0 - mapLoopRight) % var1;
         }
      }

      return var1;
   }

   public static int getMapHeight() {
      return mapHeight;
   }

   public static int getMapWidth() {
      return mapWidth;
   }

   private static int getModelId(short[][] var0, int var1, int var2) {
      var1 /= 6;
      var2 /= 6;
      return getModelIdByIndex(var0, var1, var2);
   }

   private static int getModelIdByIndex(short[][] var0, int var1, int var2) {
      var1 = getConvertX(var1);
      short var3;
      if (var2 >= var0[0].length) {
         var3 = 0;
      } else {
         var3 = var0[var1][var2];
      }

      return var3;
   }

   public static int getPixelHeight() {
      return mapHeight * 16 * 6;
   }

   public static int getPixelWidth() {
      return mapWidth * 16 * 6;
   }

   private static int getTileId(short[][] var0, int var1, int var2) {
      var0 = mapModel[getModelId(var0, var1, var2)];
      return var0[var1 % 6][var2 % 6];
   }

   public static boolean isCameraStop() {
      boolean var0;
      if (focusObj != null && !cameraLocked) {
         if (cameraActionX == 0 && cameraActionY == 0) {
            var0 = true;
         } else {
            var0 = false;
         }
      } else {
         var0 = true;
      }

      return var0;
   }

   public static boolean loadMapStep(int var0, String var1) {
      boolean var5;
      int var2;
      Exception var10000;
      boolean var10001;
      Exception var40;
      label343:
      switch(loadStep) {
      case 0:
         label367: {
            try {
               stage_id = var0;
            } catch (Exception var39) {
               var10000 = var39;
               var10001 = false;
               break label367;
            }

            StringBuilder var6;
            if (var0 != 0 && var0 != 1 && var0 != 4 && var0 != 5 && var0 != 6 && var0 != 7 && var0 != 8 && var0 != 9 && var0 != 10 && var0 != 11) {
               try {
                  stageFlag = false;
               } catch (Exception var37) {
                  var10000 = var37;
                  var10001 = false;
                  break label367;
               }

               try {
                  var6 = new StringBuilder("/map/stage");
                  image = MFImage.createImage(var6.append(var1).append(".png").toString());
               } catch (Exception var36) {
                  var10000 = var36;
                  var10001 = false;
                  break label367;
               }
            } else {
               try {
                  stageFlag = true;
               } catch (Exception var38) {
                  var10000 = var38;
                  var10001 = false;
                  break label367;
               }
            }

            label318: {
               try {
                  if (stageFlag) {
                     break label318;
                  }
               } catch (Exception var35) {
                  var10000 = var35;
                  var10001 = false;
                  break label367;
               }

               try {
                  IMAGE_TILE_WIDTH = MyAPI.zoomIn(image.getWidth() / 16);
                  break;
               } catch (Exception var34) {
                  var10000 = var34;
                  var10001 = false;
                  break label367;
               }
            }

            byte var41 = 0;
            byte var42 = var41;
            switch(var0) {
            case 0:
            case 1:
            case 4:
            case 5:
            case 10:
               var42 = 8;
            case 2:
            case 3:
               break;
            case 6:
            case 7:
               label306: {
                  try {
                     if (MFDevice.getDeviceHeight() <= 320 || MFDevice.getDeviceWidth() <= 480) {
                        break label306;
                     }
                  } catch (Exception var33) {
                     var10000 = var33;
                     var10001 = false;
                     break label367;
                  }

                  var42 = 20;
                  break;
               }

               var42 = 5;
               break;
            case 8:
            case 9:
               var42 = 4;
               break;
            case 11:
               var42 = 16;
               break;
            default:
               var42 = var41;
            }

            try {
               tileimage = new MFImage[var42];
            } catch (Exception var32) {
               var10000 = var32;
               var10001 = false;
               break label367;
            }

            MFImage[] var7;
            if (var0 != 0 && var0 != 1) {
               try {
                  var7 = tileimage;
                  var6 = new StringBuilder("/map/stage");
                  var7[0] = MFImage.createImage(var6.append(var1).append("/#1.png").toString());
               } catch (Exception var30) {
                  var10000 = var30;
                  var10001 = false;
                  break label367;
               }

               for(var0 = 1; var0 < var42; ++var0) {
                  try {
                     MFImage[] var43 = tileimage;
                     StringBuilder var44 = new StringBuilder("/map/stage");
                     var43[var0] = MFImage.createPaletteImage(var44.append(var1).append("/#").append(var0 + 1).append(".pal").toString());
                  } catch (Exception var29) {
                     var10000 = var29;
                     var10001 = false;
                     break label367;
                  }
               }
            } else {
               for(var0 = 0; var0 < var42; ++var0) {
                  try {
                     var7 = tileimage;
                     var6 = new StringBuilder("/map/stage");
                     var7[var0] = MFImage.createImage(var6.append(var1).append("/#").append(var0 + 1).append(".png").toString());
                  } catch (Exception var31) {
                     var10000 = var31;
                     var10001 = false;
                     break label367;
                  }
               }
            }

            try {
               IMAGE_TILE_WIDTH = MyAPI.zoomIn(tileimage[0].getWidth() / 16);
               break;
            } catch (Exception var28) {
               var10000 = var28;
               var10001 = false;
            }
         }

         var40 = var10000;
         var40.printStackTrace();
         break;
      case 1:
         is = MFDevice.getResourceAsStream("/map/" + var1 + ".pm");
         ds = new DataInputStream(is);
         break;
      case 2:
         try {
            mapWidth = ds.readByte();
         } catch (Exception var27) {
            var10001 = false;
            break;
         }

         try {
            mapHeight = ds.readByte();
         } catch (Exception var26) {
            var10001 = false;
            break;
         }

         label262: {
            try {
               if (mapWidth >= 0) {
                  break label262;
               }
            } catch (Exception var25) {
               var10001 = false;
               break;
            }

            try {
               mapWidth += 256;
            } catch (Exception var24) {
               var10001 = false;
               break;
            }
         }

         label255: {
            try {
               if (mapHeight >= 0) {
                  break label255;
               }
            } catch (Exception var23) {
               var10001 = false;
               break;
            }

            try {
               mapHeight += 256;
            } catch (Exception var22) {
               var10001 = false;
               break;
            }
         }

         try {
            var0 = mapWidth;
            var2 = mapHeight;
            mapFront = new short[var0][var2];
         } catch (Exception var21) {
            var10001 = false;
            break;
         }

         try {
            var2 = mapWidth;
            var0 = mapHeight;
            mapBack = new short[var2][var0];
         } catch (Exception var20) {
            var10001 = false;
         }
         break;
      case 3:
         label356: {
            short var4;
            try {
               var4 = ds.readShort();
            } catch (Exception var19) {
               var10000 = var19;
               var10001 = false;
               break label356;
            }

            try {
               ds.readShort();
            } catch (Exception var18) {
               var10000 = var18;
               var10001 = false;
               break label356;
            }

            try {
               mapModel = new short[var4][6][6];
            } catch (Exception var17) {
               var10000 = var17;
               var10001 = false;
               break label356;
            }

            var0 = 0;

            label232:
            while(true) {
               if (var0 >= var4) {
                  break label343;
               }

               for(var2 = 0; var2 < 6; ++var2) {
                  for(int var3 = 0; var3 < 6; ++var3) {
                     try {
                        mapModel[var0][var2][var3] = ds.readShort();
                     } catch (Exception var16) {
                        var10000 = var16;
                        var10001 = false;
                        break label232;
                     }
                  }
               }

               ++var0;
            }
         }

         var40 = var10000;
         var40.printStackTrace();
         break;
      case 4:
         var0 = 0;

         while(true) {
            try {
               if (var0 >= mapWidth) {
                  break label343;
               }
            } catch (Exception var15) {
               var10001 = false;
               break label343;
            }

            var2 = 0;

            while(true) {
               try {
                  if (var2 >= mapHeight) {
                     break;
                  }
               } catch (Exception var14) {
                  var10001 = false;
                  break label343;
               }

               try {
                  mapFront[var0][var2] = ds.readShort();
               } catch (Exception var13) {
                  var10001 = false;
                  break label343;
               }

               ++var2;
            }

            ++var0;
         }
      case 5:
         var0 = 0;

         while(true) {
            try {
               if (var0 >= mapWidth) {
                  break;
               }
            } catch (Exception var12) {
               var10001 = false;
               break label343;
            }

            var2 = 0;

            while(true) {
               label185: {
                  try {
                     if (var2 < mapHeight) {
                        break label185;
                     }
                  } catch (Exception var11) {
                     var10001 = false;
                     break label343;
                  }

                  ++var0;
                  break;
               }

               try {
                  mapBack[var0][var2] = ds.readShort();
               } catch (Exception var10) {
                  var10001 = false;
                  break label343;
               }

               ++var2;
            }
         }

         try {
            if (ds == null) {
               break;
            }
         } catch (Exception var9) {
            var10001 = false;
            break;
         }

         try {
            ds.close();
         } catch (Exception var8) {
            var10001 = false;
         }
         break;
      case 6:
         proposeLeftCameraLimit = 0;
         actualLeftCameraLimit = 0;
         proposeUpCameraLimit = 0;
         actualUpCameraLimit = 0;
         proposeRightCameraLimit = getPixelWidth();
         actualRightCameraLimit = getPixelWidth();
         proposeDownCameraLimit = getPixelHeight();
         actualDownCameraLimit = getPixelHeight();
         loadStep = 0;
         mapOffsetX = 0;
         shakeCount = 0;
         brokePointY = 0;
         brokeOffsetY = 0;
         setMapLoop(mapWidth - 4, mapWidth);
         switch(StageManager.getCurrentZoneId()) {
         case 8:
            setCameraRightLimit(480);
            setMapLoop(mapWidth - 28, mapWidth);
            calCameraImmidiately();
         default:
            switch(StageManager.getStageID()) {
            case 10:
               setCameraRightLimit(getPixelWidth() - 1);
            default:
               var5 = true;
               return var5;
            }
         }
      }

      if (true) {
         ++loadStep;
      }

      var5 = false;
      return var5;
   }

   public static void lockCamera(boolean var0) {
      cameraLocked = var0;
      if (!var0) {
         cameraUpDownLocked = false;
      }

   }

   public static void lockUpDownCamera(boolean var0) {
      cameraUpDownLocked = var0;
   }

   public static void releaseCamera() {
      proposeLeftCameraLimit = 0;
      proposeRightCameraLimit = getPixelWidth();
      proposeUpCameraLimit = 0;
      proposeDownCameraLimit = getPixelHeight();
      calCameraImmidiately();
   }

   public static void releaseCamera2() {
      proposeLeftCameraLimit = 0;
      proposeRightCameraLimit = getPixelWidth();
      proposeUpCameraLimit = 0;
      proposeDownCameraLimit = 2372;
      calCameraImmidiately();
   }

   public static void releaseCameraLeftLimit(int var0) {
      proposeLeftCameraLimit = 0;
   }

   public static void releaseCameraRightLimit(int var0) {
      proposeRightCameraLimit = getPixelWidth();
   }

   public static void releaseCameraUpLimit() {
      proposeUpCameraLimit = 0;
   }

   public static void setCameraDownLimit(int var0) {
      proposeDownCameraLimit = var0;
      actualDownCameraLimit = camera.y + CAMERA_HEIGHT;
   }

   public static void setCameraLeftLimit(int var0) {
      proposeLeftCameraLimit = var0;
      if (proposeLeftCameraLimit <= camera.x) {
         actualLeftCameraLimit = proposeLeftCameraLimit;
      } else {
         actualLeftCameraLimit = camera.x;
      }

   }

   public static void setCameraMoving() {
      cameraActionX = 1;
      cameraActionY = 1;
      cameraLogic();
   }

   public static void setCameraRightLimit(int var0) {
      proposeRightCameraLimit = var0;
      if (proposeRightCameraLimit >= camera.x + CAMERA_WIDTH) {
         actualRightCameraLimit = proposeRightCameraLimit;
      } else {
         actualRightCameraLimit = camera.x + CAMERA_WIDTH;
      }

   }

   public static void setCameraUpLimit(int var0) {
      proposeUpCameraLimit = var0;
      if (camera.y < proposeUpCameraLimit) {
         actualUpCameraLimit = camera.y;
      } else {
         actualUpCameraLimit = proposeUpCameraLimit;
      }

   }

   public static void setFocusObj(Focusable var0) {
      if (focusObj != var0) {
         cameraActionX = 1;
         cameraActionY = 1;
      }

      focusObj = var0;
      lockCamera(false);
   }
   
   public static Focusable getFocusObj() {
      return focusObj;
   }

   public static void setMapBrokeParam(int var0, int var1) {
      brokePointY = var0 / 2;
      brokeOffsetY = var1;
   }

   public static void setMapLoop(int var0, int var1) {
      mapLoopLeft = var0;
      mapLoopRight = var1;
   }

   public static void setShake(int var0) {
      setShake(var0, 6);
   }

   public static void setShake(int var0, int var1) {
      if (var0 > 0) {
         shakeCount = var0;
         shakeMaxCount = var0;
         shakePowerY = var1;
      }

   }

   public static void setShakeX(int var0) {
      shakePowerX = var0;
   }
}
