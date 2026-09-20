package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import Lib.MyAPI;
import Lib.SoundSystem;
import com.sega.engine.action.ACBlock;
import com.sega.engine.action.ACObject;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Vector;
import State.TitleState;
import android.widget.Toast;
import android.os.Handler;
import android.os.Looper;
import Lib.CopyFields;

public abstract class GameObject extends ACObject implements SonicDef {
   private static final int AVAILABLE_RANGE = 1;
   public static final int CHECK_OFFSET = 192;
   private static final int CLOSE_NUM_IN_ONE_LOOP = 10;
   private static final int DESTORY_RANGE = 2;
   public static final int DIRECTION_DOWN = 1;
   public static final int DIRECTION_LEFT = 2;
   public static final int DIRECTION_NONE = 4;
   public static final int DIRECTION_RIGHT = 3;
   public static final int DIRECTION_UP = 0;
   public static final int DRAW_AFTER_MAP = 2;
   public static final int DRAW_AFTER_SONIC = 1;
   public static final int DRAW_BEFORE_BEFORE_SONIC = 3;
   public static final int DRAW_BEFORE_SONIC = 0;
   protected static int GRAVITY;
   public static final int INIT_DISTANCE = 14720;
   public static boolean IsGamePause;
   public static final int LOAD_CONTENT = 1;
   public static final int LOAD_END = 2;
   public static final int LOAD_INDEX_ENEMY = 2;
   public static final int LOAD_INDEX_GIMMICK = 0;
   public static final int LOAD_INDEX_ITEM = 3;
   public static final int LOAD_INDEX_RING = 1;
   public static final int LOAD_NUM_IN_ONE_LOOP = 20;
   public static final int LOAD_OPEN_FILE = 0;
   private static final int PAINT_LAYER_NUM = 4;
   public static final int REACTION_ATTACK = 1;
   public static final int REACTION_STOP = 0;
   public static final int ROOM_HEIGHT = 256;
   public static final int ROOM_WIDTH = 256;
   private static final int SEARCH_COUNT = 3;
   private static final int SEARCH_RANGE = 10;
   public static final int STATE_NORMAL_MODE = 0;
   public static final int STATE_RACE_MODE = 1;
   public static final int VELOCITY_DIVIDE = 512;
   public static Vector[][] allGameObject;
   public static boolean bossFighting;
   public static int bossID;
   public static Vector bossObjVec = new Vector();
   public static Coordinate camera;
   private static int closeStep;
   public static int currentLoadIndex;
   private static int currentX;
   private static int currentY;
   private static int cursorX;
   private static int cursorY;
   public static Animation destroyEffectAnimation;
   public static DataInputStream ds;
   private static int endX;
   private static int endY;
   private static boolean gettingObject;
   private static ACBlock groundblock;
   public static Animation iceBreakAnimation;
   public static boolean isBossHalf;
   public static boolean isDamageSandActive;
   public static boolean isFirstTouchedSandSlip;
   public static boolean isFirstTouchedWind;
   public static boolean isGotRings;
   public static boolean isUnlockCage;
   public static int loadNum;
   public static int loadStep;
   public static Vector mainObjectLogicVec = new Vector();
   public static int objVecHeight;
   public static int objVecWidth;
   private static int objectCursor;
   public static Vector[] paintVec = new Vector[4];
   public static Animation platformBreakAnimation;
   public static PlayerObject player;
   public static PlayerObject player2;
   public static Vector playerCheckVec = new Vector();
   private static int preCenterX;
   private static int preCenterY;
   public static CollisionRect rectH;
   public static CollisionRect rectV;
   private static CollisionRect resetRect;
   public static AnimationDrawer ringDrawer;
   protected static Animation rockBreakAnimation;
   public static CollisionRect screenRect;
   protected static SoundSystem soundInstance;
   public static int stageModeState;
   private static int startX;
   private static int startY;
   public static long systemClock;
   public CollisionRect collisionRect = new CollisionRect();
   protected int currentLayer;
   protected boolean firstTouch = true;
   protected int mHeight;
   protected int mWidth;
   public Coordinate moveDistance = new Coordinate();
   private boolean needInit;
   protected int objId;
   public CollisionRect preCollisionRect = new CollisionRect();
   public static boolean newth = false;
   
   static {
      for(int var0 = 0; var0 < 4; ++var0) {
         paintVec[var0] = new Vector();
      }

      GRAVITY = 172;
      destroyEffectAnimation = null;
      iceBreakAnimation = null;
      platformBreakAnimation = null;
      soundInstance = null;
      bossFighting = false;
      isGotRings = false;
      isFirstTouchedWind = false;
      isFirstTouchedSandSlip = false;
      loadStep = 0;
      ds = null;
      closeStep = 0;
      preCenterX = -1;
      preCenterY = -1;
      screenRect = new CollisionRect();
      rectH = new CollisionRect();
      rectV = new CollisionRect();
      groundblock = CollisionMap.getInstance().getNewCollisionBlock();
      resetRect = new CollisionRect();
   }

   public GameObject() {
      super(CollisionMap.getInstance());
   }

   public static void ObjectClear() {
      GimmickObject.gimmickInit();
      bossObjVec.removeAllElements();
   }

   public static void addGameObject(GameObject var0) {
      addGameObject(var0, var0.posX, var0.posY);
   }

   public static void addGameObject(GameObject var0, int var1, int var2) {
      if (var0 != null) {
         int var3 = (var1 >> 6) / 256;
         var1 = var3;
         if (var3 >= objVecWidth) {
            var1 = objVecWidth - 1;
         }

         var3 = (var2 >> 6) / 256;
         var2 = var3;
         if (var3 >= objVecHeight) {
            var2 = objVecHeight - 1;
         }

         Exception var10000;
         label32: {
            boolean var10001;
            if (var1 > -1) {
               Vector var4;
               try {
                  var4 = allGameObject[var1][var2];
               } catch (Exception var7) {
                  var10000 = var7;
                  var10001 = false;
                  break label32;
               }

               try {
                  var4.addElement(var0);
               } catch (Exception var6) {
                  var10000 = var6;
                  var10001 = false;
                  break label32;
               }
            }

            try {
               var0.refreshCollisionRect(var0.posX, var0.posY);
               return;
            } catch (Exception var5) {
               var10000 = var5;
               var10001 = false;
            }
         }

         Exception var8 = var10000;
         var8.printStackTrace();
      }

   }

   public static void checkObjWhileMoving(GameObject var0) {
      int var5 = (MapManager.getCamera().x + (MapManager.CAMERA_WIDTH >> 1)) / 256;
      int var4 = (MapManager.getCamera().y + (MapManager.CAMERA_HEIGHT >> 1)) / 256;
      int var2;
      int var3;
      int var11;
      if (preCenterX == -1 && preCenterY == -1) {
         for(var11 = var5 - 1; var11 <= var5 + 1; ++var11) {
            if (var11 >= 0 && var11 < objVecWidth) {
               for(var2 = var4 - 1; var2 <= var4 + 1; ++var2) {
                  if (var2 >= 0 && var2 < objVecHeight) {
                     for(var3 = 0; var3 < allGameObject[var11][var2].size(); ++var3) {
                        var0 = (GameObject)allGameObject[var11][var2].elementAt(var3);
                        var0.doInitWhileInCamera();
                     }
                  }
               }
            }
         }

         preCenterX = var5;
         preCenterY = var4;
      } else if (preCenterX != var5 || preCenterY != var4) {
         int var7 = var5 - preCenterX;
         int var6 = var4 - preCenterY;
         byte var1;
         int var8;
         int var9;
         if (var7 != 0) {
            if (var7 > 0) {
               var1 = -2;
            } else {
               var1 = 2;
            }

            var8 = var1 + var5;
            if (var8 >= 0 && var8 < objVecWidth) {
               for(var2 = -2; var2 <= 2; ++var2) {
                  if (var4 + var2 >= 0 && var4 + var2 < objVecHeight) {
                     for(var11 = 0; var11 < allGameObject[var8][var4 + var2].size(); var11 = var3 + 1) {
                        var0 = (GameObject)allGameObject[var8][var4 + var2].elementAt(var11);
                        var9 = (var0.getCheckPositionX() >> 6) / 256;
                        int var10 = (var0.getCheckPositionY() >> 6) / 256;
                        var3 = var11;
                        if (var9 >= 0) {
                           if (var9 >= objVecWidth) {
                              var3 = var11;
                           } else {
                              var3 = var11;
                              if (var10 >= 0) {
                                 var3 = var11;
                                 if (var10 < objVecHeight) {
                                    if (var9 == var8) {
                                       var3 = var11;
                                       if (var10 == var4 + var2) {
                                          continue;
                                       }
                                    }

                                    allGameObject[var8][var4 + var2].removeElementAt(var11);
                                    var3 = var11 - 1;
                                    allGameObject[var9][var10].addElement(var0);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            if (var7 > 0) {
               var1 = 1;
            } else {
               var1 = -1;
            }

            var7 = var5 + var1;
            if (var7 >= 0 && var7 < objVecWidth) {
               for(var2 = var4 - 1; var2 <= var4 + 1; ++var2) {
                  if (var2 >= 0 && var2 < objVecHeight) {
                     for(var11 = 0; var11 < allGameObject[var7][var2].size(); var11 = var3 + 1) {
                        var0 = (GameObject)allGameObject[var7][var2].elementAt(var11);
                        var8 = (var0.getCheckPositionX() >> 6) / 256;
                        var9 = (var0.getCheckPositionY() >> 6) / 256;
                        var3 = var11;
                        if (var8 >= 0) {
                           if (var8 >= objVecWidth) {
                              var3 = var11;
                           } else {
                              var3 = var11;
                              if (var9 >= 0) {
                                 var3 = var11;
                                 if (var9 < objVecHeight) {
                                    if (var8 == var7) {
                                       var3 = var11;
                                       if (var9 == var2) {
                                          continue;
                                       }
                                    }

                                    allGameObject[var7][var2].removeElementAt(var11);
                                    var3 = var11 - 1;
                                    allGameObject[var8][var9].addElement(var0);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         if (var6 != 0) {
            if (var6 > 0) {
               var1 = -2;
            } else {
               var1 = 2;
            }

            var7 = var1 + var4;
            if (var7 >= 0 && var7 < objVecHeight) {
               for(var2 = -2; var2 <= 2; ++var2) {
                  if (var5 + var2 >= 0 && var5 + var2 < objVecWidth) {
                     for(var11 = 0; var11 < allGameObject[var5 + var2][var7].size(); var11 = var3 + 1) {
                        var0 = (GameObject)allGameObject[var5 + var2][var7].elementAt(var11);
                        var9 = (var0.getCheckPositionX() >> 6) / 256;
                        var8 = (var0.getCheckPositionY() >> 6) / 256;
                        var3 = var11;
                        if (var9 >= 0) {
                           if (var9 >= objVecWidth) {
                              var3 = var11;
                           } else {
                              var3 = var11;
                              if (var8 >= 0) {
                                 var3 = var11;
                                 if (var8 < objVecHeight) {
                                    if (var9 == var5 + var2) {
                                       var3 = var11;
                                       if (var8 == var7) {
                                          continue;
                                       }
                                    }

                                    allGameObject[var5 + var2][var7].removeElementAt(var11);
                                    var3 = var11 - 1;
                                    allGameObject[var9][var8].addElement(var0);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            if (var6 > 0) {
               var1 = 1;
            } else {
               var1 = -1;
            }

            var6 = var4 + var1;
            if (var6 >= 0 && var6 < objVecHeight) {
               for(var2 = var5 - 1; var2 <= var5 + 1; ++var2) {
                  if (var2 >= 0 && var2 < objVecWidth) {
                     for(var11 = 0; var11 < allGameObject[var2][var6].size(); var11 = var3 + 1) {
                        var0 = (GameObject)allGameObject[var2][var6].elementAt(var11);
                        var8 = (var0.getCheckPositionX() >> 6) / 256;
                        var7 = (var0.getCheckPositionY() >> 6) / 256;
                        var3 = var11;
                        if (var8 >= 0) {
                           if (var8 >= objVecWidth) {
                              var3 = var11;
                           } else {
                              var3 = var11;
                              if (var7 >= 0) {
                                 var3 = var11;
                                 if (var7 < objVecHeight) {
                                    if (var8 == var2) {
                                       var3 = var11;
                                       if (var7 == var6) {
                                          continue;
                                       }
                                    }

                                    allGameObject[var2][var6].removeElementAt(var11);
                                    var3 = var11 - 1;
                                    allGameObject[var8][var7].addElement(var0);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         preCenterX = var5;
         preCenterY = var4;
      }

   }

   public static boolean checkPaintNecessary(GameObject var0) {
      return var0.isInCamera();
   }

   public static void closeObject() {
      if (player != null) {
         player.close();
      }
      if (player2 != null) {
         player2.close();
      }

      int var0;
      if (allGameObject != null) {
         for(var0 = 0; var0 < objVecWidth; ++var0) {
            for(int var1 = 0; var1 < objVecHeight; ++var1) {
               for(int var2 = 0; var2 < allGameObject[var0][var1].size(); ++var2) {
                  ((GameObject)allGameObject[var0][var1].elementAt(var2)).close();
               }

               allGameObject[var0][var1].removeAllElements();
            }
         }

         allGameObject = null;
      }

      for(var0 = 0; var0 < 4; ++var0) {
         paintVec[var0].removeAllElements();
      }

      SmallAnimal.animalClose();
      BulletObject.bulletClose();
      System.gc();
   }

   public static void closeObject(boolean var0) {
      closeObject();
      if (!var0) {
         EnemyObject.releaseAllEnemyResource();
         GimmickObject.releaseGimmickResource();
      }

      System.gc();
   }

   public static boolean closeObjectStep(boolean var0) {
      boolean var1;
      boolean var3 = true;
      int var2;
      label57:
      switch(closeStep) {
      case 0:
         if (player != null) {
            player.close();
         }
         if (player2 != null) {
            player2.close();
         }

         currentX = 0;
         currentY = 0;
         var1 = var3;
         break;
      case 1:
         var1 = var3;
         if (allGameObject != null) {
            var3 = false;
            var2 = 0;

            while(true) {
               var1 = var3;
               if (var2 >= 10) {
                  break;
               }

               for(int var4 = 0; var4 < allGameObject[currentX][currentY].size(); ++var4) {
                  ((GameObject)allGameObject[currentX][currentY].elementAt(var4)).close();
               }

               allGameObject[currentX][currentY].removeAllElements();
               ++currentY;
               if (currentY >= objVecHeight) {
                  currentY = 0;
                  ++currentX;
                  if (currentX >= objVecWidth) {
                     var1 = true;
                     allGameObject = null;
                     break;
                  }
               }

               ++var2;
            }
         }
         break;
      case 2:
         var2 = 0;

         while(true) {
            var1 = var3;
            if (var2 >= 4) {
               break label57;
            }

            paintVec[var2].removeAllElements();
            ++var2;
         }
      case 3:
         SmallAnimal.animalClose();
         BulletObject.bulletClose();
         var1 = var3;
         break;
      case 4:
         var1 = var3;
         if (!var0) {
            EnemyObject.releaseAllEnemyResource();
            GimmickObject.releaseGimmickResource();
            var1 = var3;
         }
         break;
      case 5:
         System.gc();
         var1 = var3;
         break;
      case 6:
         closeStep = 0;
         var0 = true;
         return var0;
      default:
         var1 = var3;
      }

      if (var1) {
         ++closeStep;
      }

      var0 = false;
      return var0;
   }

   public static void collisionChkWithAllGameObject(PlayerObject var0) {
      boolean var2 = false;
      if (var0.attackRectVec.size() > 0) {
         var2 = true;
      }

      int var1 = 0;
      int var3 = 0;
      getGameObjectVecArray(var0, mainObjectLogicVec);
      
      while(true) {
         PlayerAnimationCollisionRect var6;
         CollisionRect var7;
         GameObject var8;
         RingObject var9;
         while(var3 < mainObjectLogicVec.size()) {
            Vector var5 = (Vector)mainObjectLogicVec.elementAt(var3);
            if (var1 < var5.size()) {
               var8 = (GameObject)var5.elementAt(var1);
               if (var2) {
                  for(int var4 = 0; var4 < var0.attackRectVec.size(); ++var4) {
                     var6 = (PlayerAnimationCollisionRect)var0.attackRectVec.elementAt(var4);
                     var6.collisionChkWithObject(var8);
                  }
               }

               if (var0.isAttracting() && var8 instanceof RingObject) {
                  var9 = (RingObject)var8;
                  var7 = var0.attractRect;
                  if (var7.collisionChk(var9.getCollisionRect())) {
                     var9.beAttract();
                  }
               }

               if (var8.collisionChkWithObject(var0)) {
                  var8.doWhileCollisionWrap(var0);
                  var8.firstTouch = false;
               } else {
                  var8.doWhileNoCollision();
                  var8.firstTouch = true;
               }

               ++var1;
            } else {
               var1 = 0;
               ++var3;
            }
         }

         if (bossObjVec != null) {
            for(var1 = 0; var1 < bossObjVec.size(); ++var1) {
               var8 = (GameObject)bossObjVec.elementAt(var1);
               if (var2) {
                  for(var3 = 0; var3 < var0.attackRectVec.size(); ++var3) {
                     var6 = (PlayerAnimationCollisionRect)var0.attackRectVec.elementAt(var3);
                     var6.collisionChkWithObject(var8);
                  }
               }

               if (var0.isAttracting() && var8 instanceof RingObject) {
                  var9 = (RingObject)var8;
                  var7 = var0.attractRect;
                  if (var7.collisionChk(var9.getCollisionRect())) {
                     var9.beAttract();
                  }
               }

               if (var8.collisionChkWithObject(var0)) {
                  var8.doWhileCollisionWrap(var0);
                  var8.firstTouch = false;
               } else {
                  var8.doWhileNoCollision();
                  var8.firstTouch = true;
               }
            }
         }

         BulletObject.checkWithAllBullet(var0);
         return;
      }
   }

   private int downSideCollisionChk(int var1, int var2) {
      return -1;
   }

   public static void drawObjectAfterEveryThing(MFGraphics var0) {
      camera = MapManager.getCamera();

      for(int var1 = 0; var1 < paintVec[2].size(); ++var1) {
         ((GameObject)paintVec[2].elementAt(var1)).draw(var0);
      }

      RingObject.ringDraw(var0);
   }

   public static void drawObjectBeforeSonic(MFGraphics var0) {
      camera = MapManager.getCamera();

      int var1;
      for(var1 = 0; var1 < paintVec[3].size(); ++var1) {
         ((GameObject)paintVec[3].elementAt(var1)).draw(var0);
      }

      for(var1 = 0; var1 < paintVec[0].size(); ++var1) {
         ((GameObject)paintVec[0].elementAt(var1)).draw(var0);
      }

      if (!isUnlockCage) {
         SmallAnimal.animalDraw(var0);
      }

   }

   public static void drawObjects(MFGraphics var0) {
      if (ringDrawer != null && !IsGamePause) {
         ringDrawer.moveOn();
      }

      camera = MapManager.getCamera();

      for(int var1 = 0; var1 < paintVec[1].size(); ++var1) {
         ((GameObject)paintVec[1].elementAt(var1)).draw(var0);
      }

      if (isUnlockCage) {
         SmallAnimal.animalDraw(var0);
      }

   }

   public static void drawPlayer(MFGraphics var0) {
      camera = MapManager.getCamera();
      player.draw(var0);
      if (player2 != null) {
          player2.draw(var0);
      }
   }

   private static GameObject getAvailableObject() {
      GameObject var0;
      if (!gettingObject) {
         var0 = null;
      } else {
         Vector var1 = allGameObject[cursorX][cursorY];
         var0 = (GameObject)var1.elementAt(objectCursor);
         ++objectCursor;
         nextCursor();
      }

      return var0;
   }

   private int getDownCheckPointY(int var1, int var2) {
      this.refreshCollisionRect(var1, var2);
      return this.collisionRect.y1;
   }
   
   private static void getGameObjectVecArray(GameObject currentObject, Vector objVec) {
        objVec.removeAllElements();
        int centerX = (MapManager.getCamera().x + (MapManager.CAMERA_WIDTH >> 1)) / 256;
        int centerY = (MapManager.getCamera().y + (MapManager.CAMERA_HEIGHT >> 1)) / 256;
        if (centerX >= 0 && centerX < objVecWidth && centerY >= 0 && centerY < objVecHeight) {
            int startX2 = centerX - 1;
            int startY2 = centerY - 1;
            int endX2 = centerX + 1;
            int endY2 = centerY + 1;
            if (startX2 < 0) {
                startX2 = 0;
            }
            if (startY2 < 0) {
                startY2 = 0;
            }
            if (endX2 >= objVecWidth) {
                endX2 = objVecWidth - 1;
            }
            if (endY2 >= objVecHeight) {
                endY2 = objVecHeight - 1;
            }
            if (allGameObject != null) {
                for (int x = startX2; x <= endX2; x++) {
                    for (int y = startY2; y <= endY2; y++) {
                        objVec.addElement(allGameObject[x][y]);
                    }
                }
            }
        }
    }

   private int getUpCheckPointY(int var1, int var2) {
      this.refreshCollisionRect(var1, var2);
      return this.collisionRect.y0;
   }

   private static void initGetAvailableObject(GameObject var0) {
      objectCursor = 0;
      int var5 = (var0.getCheckPositionX() >> 6) / 256;
      int var4 = (var0.getCheckPositionY() >> 6) / 256;
      if (var5 >= 0 && var5 < objVecWidth && var4 >= 0 && var4 < objVecHeight) {
         startX = var5 - 1;
         startY = var4 - 1;
         endX = var5 + 1;
         endY = var4 + 1;
         if (startX < 0) {
            startX = 0;
         }

         if (startY < 0) {
            startY = 0;
         }

         if (endX >= objVecWidth) {
            endX = objVecWidth - 1;
         }

         if (endY >= objVecHeight) {
            endY = objVecHeight - 1;
         }

         cursorX = startX;
         cursorY = startY;
         gettingObject = true;
         if (preCenterX == -1 && preCenterY == -1) {
            preCenterX = var5;
            preCenterY = var4;
         } else if (preCenterX != var5 || preCenterY != var4) {
            int var1 = var5 - preCenterX;
            int var6 = var4 - preCenterY;
            int var2;
            int var3;
            int var7;
            int var8;
            byte var10;
            if (var1 != 0) {
               if (var1 > 0) {
                  var10 = -2;
               } else {
                  var10 = 2;
               }

               var7 = var10 + var5;
               if (var7 >= 0 && var7 < objVecWidth) {
                  for(var2 = -2; var2 <= 2; ++var2) {
                     if (var4 + var2 >= 0 && var4 + var2 < objVecHeight) {
                        for(var1 = 0; var1 < allGameObject[var7][var4 + var2].size(); var1 = var3 + 1) {
                           var0 = (GameObject)allGameObject[var7][var4 + var2].elementAt(var1);
                           int var9 = (var0.getCheckPositionX() >> 6) / 256;
                           var8 = (var0.getCheckPositionY() >> 6) / 256;
                           var3 = var1;
                           if (var9 >= 0) {
                              if (var9 >= objVecWidth) {
                                 var3 = var1;
                              } else {
                                 var3 = var1;
                                 if (var8 >= 0) {
                                    var3 = var1;
                                    if (var8 < objVecHeight) {
                                       if (var9 == var7) {
                                          var3 = var1;
                                          if (var8 == var4 + var2) {
                                             continue;
                                          }
                                       }

                                       allGameObject[var7][var4 + var2].removeElementAt(var1);
                                       var3 = var1 - 1;
                                       allGameObject[var9][var8].addElement(var0);
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            if (var6 != 0) {
               if (var6 > 0) {
                  var10 = -2;
               } else {
                  var10 = 2;
               }

               var6 = var10 + var4;
               if (var6 >= 0 && var6 < objVecHeight) {
                  for(var2 = -2; var2 <= 2; ++var2) {
                     if (var5 + var2 >= 0 && var5 + var2 < objVecWidth) {
                        for(var1 = 0; var1 < allGameObject[var5 + var2][var6].size(); var1 = var3 + 1) {
                           var0 = (GameObject)allGameObject[var5 + var2][var6].elementAt(var1);
                           var8 = (var0.getCheckPositionX() >> 6) / 256;
                           var7 = (var0.getCheckPositionY() >> 6) / 256;
                           var3 = var1;
                           if (var8 >= 0) {
                              if (var8 >= objVecWidth) {
                                 var3 = var1;
                              } else {
                                 var3 = var1;
                                 if (var7 >= 0) {
                                    var3 = var1;
                                    if (var7 < objVecHeight) {
                                       if (var8 == var5 + var2) {
                                          var3 = var1;
                                          if (var7 == var6) {
                                             continue;
                                          }
                                       }

                                       allGameObject[var5 + var2][var6].removeElementAt(var1);
                                       var3 = var1 - 1;
                                       allGameObject[var8][var7].addElement(var0);
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            preCenterX = var5;
            preCenterY = var4;
         }

         nextCursor();
      }

   }
   
   public static void setPlayer2() {
   if (!MFMain.multiplayer && player2 == null && TitleState.characterslots == 2 && MFMain.tails >= 7) {
          int id = player.getCharacterID();
          player2 = PlayerObject.getPlayer(id == 0 ? 1 : id == 1 ? 0 : id == 2 ? 3 : id == 3 ? 2 : 1);
          //player2.invincibleCount = 9999;
          player2.posX = player.posX - 5;
          player2.posY = player.posY;
          player2.setPlayer(player2);
          player2.Player2Hurt = true;
      } else {
      if (MFMain.multiplayer) {
      while (player2 == null) {
      player2 = PlayerObject.getPlayer(MFMain.getPlayerMulti().getCharacterIDMulti());
      player2.setPlayer(player2);
      }
      final Handler mainHandler = new Handler(Looper.getMainLooper());
Thread t = new Thread(new Runnable() {
    @Override
    public void run() {
        while (MFMain.multiplayer) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        CopyFields.copyFields(MFMain.getPlayerMulti(), player2);
                        player2.setPlayer(player2);
                    }
                });
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
});
t.start();
      if (player2 != null) {
      player2.posX = player.posX - 5;
      player2.posY = player.posY;
      player2.Player2Hurt = true;
      }
      }
      }
      }

   public static void initObject(int var0, int var1, boolean var2) {
      closeObject(var2);
      player = PlayerObject.getPlayer();
      if (MFMain.multiplayer) player.characterIDMulti = player.getCharacterID();
      player.setPlayer(player);
      player.Player2Hurt = false;
      setPlayer2();
      bossObjVec.removeAllElements();
      allGameObject = null;
      objVecWidth = (var0 + 256 - 1) / 256;
      objVecHeight = (var1 + 256 - 1) / 256;
      allGameObject = new Vector[objVecWidth][objVecHeight];

      for(var0 = 0; var0 < objVecWidth; ++var0) {
         for(var1 = 0; var1 < objVecHeight; ++var1) {
            allGameObject[var0][var1] = new Vector();
         }
      }

      if (destroyEffectAnimation == null) {
         destroyEffectAnimation = new Animation("/animation/destroy_effect");
      }

      if (rockBreakAnimation == null) {
         rockBreakAnimation = new Animation("/animation/iwa_patch");
      }

      if (iceBreakAnimation == null) {
         iceBreakAnimation = new Animation("/animation/ice_patch");
      }

      if (platformBreakAnimation == null) {
         platformBreakAnimation = new Animation("/animation/subehahen_5");
      }

      preCenterX = -1;
      preCenterY = -1;
      IsGamePause = false;
      if (soundInstance == null) {
         soundInstance = SoundSystem.getInstance();
      }

      RingObject.ringInit();
      GimmickObject.gimmickInit();
      EnemyObject.enemyinit();
      bossFighting = false;
   }

   public static void loadEnemyByStream(DataInputStream var0) {
      short var2;
      boolean var10001;
      try {
         var2 = var0.readShort();
      } catch (Exception var18) {
         var10001 = false;
         return;
      }

      short var3;
      try {
         var3 = var0.readShort();
      } catch (Exception var17) {
         var10001 = false;
         return;
      }

      byte var6;
      try {
         var6 = var0.readByte();
      } catch (Exception var16) {
         var10001 = false;
         return;
      }

      byte var4;
      try {
         var4 = var0.readByte();
      } catch (Exception var15) {
         var10001 = false;
         return;
      }

      byte var5;
      try {
         var5 = var0.readByte();
      } catch (Exception var14) {
         var10001 = false;
         return;
      }

      byte var8;
      try {
         var8 = var0.readByte();
      } catch (Exception var13) {
         var10001 = false;
         return;
      }

      byte var7;
      try {
         var7 = var0.readByte();
      } catch (Exception var12) {
         var10001 = false;
         return;
      }

      int var1 = var2;
      if (var2 < 0) {
         var1 = var2 + 256;
      }

      int var20 = var3;
      if (var3 < 0) {
         var20 = var3 + 256;
      }

      int var21 = var4;
      if (var4 < 0) {
         var21 = var4 + 256;
      }

      int var22 = var5;
      if (var5 < 0) {
         var22 = var5 + 256;
      }

      EnemyObject var19;
      try {
         var19 = EnemyObject.getNewInstance(var6, var1, var20, var8, var7, var21, var22);
      } catch (Exception var11) {
         var10001 = false;
         return;
      }

      if (var19 != null) {
         try {
            if (EnemyObject.IsBoss) {
               return;
            }
         } catch (Exception var10) {
            var10001 = false;
            return;
         }

         try {
            addGameObject(var19);
         } catch (Exception var9) {
            var10001 = false;
         }
      }

   }

   public static void loadGimmickByStream(DataInputStream var0) {
      short var2;
      boolean var10001;
      try {
         var2 = var0.readShort();
      } catch (Exception var17) {
         var10001 = false;
         return;
      }

      short var3;
      try {
         var3 = var0.readShort();
      } catch (Exception var16) {
         var10001 = false;
         return;
      }

      byte var7;
      try {
         var7 = var0.readByte();
      } catch (Exception var15) {
         var10001 = false;
         return;
      }

      byte var4;
      try {
         var4 = var0.readByte();
      } catch (Exception var14) {
         var10001 = false;
         return;
      }

      byte var5;
      try {
         var5 = var0.readByte();
      } catch (Exception var13) {
         var10001 = false;
         return;
      }

      byte var6;
      try {
         var6 = var0.readByte();
      } catch (Exception var12) {
         var10001 = false;
         return;
      }

      byte var8;
      try {
         var8 = var0.readByte();
      } catch (Exception var11) {
         var10001 = false;
         return;
      }

      int var1 = var2;
      if (var2 < 0) {
         var1 = var2 + 256;
      }

      int var19 = var3;
      if (var3 < 0) {
         var19 = var3 + 256;
      }

      int var20 = var4;
      if (var4 < 0) {
         var20 = var4 + 256;
      }

      int var21 = var5;
      if (var5 < 0) {
         var21 = var5 + 256;
      }

      GameObject var18;
      try {
         var18 = GimmickObject.getNewInstance(var7, var1, var19, var6, var8, var20, var21);
      } catch (Exception var10) {
         var10001 = false;
         return;
      }

      if (var18 != null) {
         try {
            addGameObject(var18);
         } catch (Exception var9) {
            var10001 = false;
         }
      }

   }

   public static void loadItemByStream(DataInputStream var0) {
      try {
         short var2 = var0.readShort();
         short var1 = var0.readShort();
         byte var3 = var0.readByte();
         ItemObject var5 = ItemObject.getNewInstance(var3, var2, var1);
         addGameObject(var5);
      } catch (Exception var4) {
      }

   }

   public static boolean loadObjectStep(String var0, int var1) {
      boolean var3 = true;
      boolean var10001;
      boolean var2;
      boolean var4;
      switch(loadStep) {
      case 0:
         label134: {
            label143: {
               InputStream var21;
               try {
                  var21 = MFDevice.getResourceAsStream(var0);
               } catch (Exception var20) {
                  var10001 = false;
                  break label143;
               }

               try {
                  DataInputStream var5 = new DataInputStream(var21);
                  ds = var5;
               } catch (Exception var19) {
                  var10001 = false;
                  break label143;
               }

               try {
                  loadNum = ds.readShort();
               } catch (Exception var18) {
                  var10001 = false;
                  break label143;
               }

               try {
                  currentLoadIndex = 0;
                  break label134;
               } catch (Exception var17) {
                  var10001 = false;
               }
            }

            ds = null;
            var2 = var3;
            break;
         }

         var2 = var3;
         break;
      case 1:
         var2 = var3;

         label118: {
            label141: {
               try {
                  if (ds == null) {
                     break;
                  }
               } catch (Exception var16) {
                  var10001 = false;
                  break label141;
               }

               int var22 = 0;

               while(var22 < 20) {
                  try {
                     if (currentLoadIndex >= loadNum) {
                        break;
                     }
                  } catch (Exception var15) {
                     var10001 = false;
                     break label141;
                  }

                  switch(var1) {
                  case 0:
                     try {
                        loadGimmickByStream(ds);
                        break;
                     } catch (Exception var14) {
                        var10001 = false;
                        break label141;
                     }
                  case 1:
                     try {
                        loadRingByStream(ds);
                        break;
                     } catch (Exception var13) {
                        var10001 = false;
                        break label141;
                     }
                  case 2:
                     try {
                        loadEnemyByStream(ds);
                        break;
                     } catch (Exception var12) {
                        var10001 = false;
                        break label141;
                     }
                  case 3:
                     try {
                        loadItemByStream(ds);
                     } catch (Exception var11) {
                        var10001 = false;
                        break label141;
                     }
                  }

                  ++var22;

                  try {
                     ++currentLoadIndex;
                  } catch (Exception var10) {
                     var10001 = false;
                     break label141;
                  }
               }

               var2 = var3;

               try {
                  if (currentLoadIndex >= loadNum) {
                     break;
                  }
                  break label118;
               } catch (Exception var9) {
                  var10001 = false;
               }
            }

            var2 = var3;
            break;
         }

         var2 = false;
         break;
      case 2:
         label84: {
            label83: {
               try {
                  if (ds == null) {
                     break label83;
                  }
               } catch (Exception var8) {
                  var10001 = false;
                  break label84;
               }

               try {
                  ds.close();
               } catch (Exception var7) {
                  var10001 = false;
                  break label84;
               }
            }

            try {
               loadStep = 0;
            } catch (Exception var6) {
               var10001 = false;
            }
         }

         var4 = true;
         return var4;
      default:
         var2 = var3;
      }

      if (var2) {
         ++loadStep;
      }

      var4 = false;
      return var4;
   }

   public static void loadRingByStream(DataInputStream var0) {
      try {
         short var1 = var0.readShort();
         short var2 = var0.readShort();
         RingObject var4 = RingObject.getNewInstance(var1, var2);
         addGameObject(var4);
      } catch (Exception var3) {
      }

   }

   public static void logicObjects() {
      if (!IsGamePause) {
         if (systemClock < Long.MAX_VALUE) {
            ++systemClock;
         } else {
            systemClock = 0L;
         }

         int var0;
         for(var0 = 0; var0 < 4; ++var0) {
            paintVec[var0].removeAllElements();
         }

         GimmickObject.gimmickStaticLogic();
         EnemyObject.enemyStaticLogic();
         RingObject.ringLogic();
         RocketSeparateEffect.getInstance().logic();
         player.logic();
         if (player.Jumped() && player2 != null && player.collisionChkWithObject(player2)) {
            player.doWhileCollision(player2, 0);
         }
         /*if (player2 != null) {
    int followSpeed = 256;

    if (player2.posX < player.posX - 44) {
        player2.posX += followSpeed;
    } else if (player2.posX > player.posX + 44) {
        player2.posX -= followSpeed;
    }

    if (player2.posY < player.posY - 44) {
        player2.posY += followSpeed;
    } else if (player2.posY > player.posY + 44) {
        player2.posY -= followSpeed;
    }

    player2.logic();
         }*/
         if (player2 != null) {
         if (!newth && !MFMain.multiplayer) {
         newth = true;
    Thread t = new Thread(new Runnable() {
    @Override
    public void run() {
        while (player2 != null) {
            int dx = player2.posX - player.posX;
            int dy = player2.posY - player.posY;
            int dx2 = player2.footPointX - player.footPointX;
            int dy2 = player2.footPointY - player.footPointY;
            if (Math.abs(dx) > 10 || Math.abs(dy) > 10 || Math.abs(dx2) > 10 || Math.abs(dy2) > 10) {
                player2.posX = player.posX -2;
                player2.posY = player.posY;
            }
        try {
              Thread.sleep(5);
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              break;
            }
        }
    }
});
t.start();
}
player2.logic();
if (!MFMain.multiplayer) player2.runAI();
player2.collisionCheckWithGameObject();
if (MFMain.multiplayer && player2.Jumped() && player != null && player2.collisionChkWithObject(player)) {
            player2.doWhileCollision(player, 0);
         }
}

         if (!player.outOfControl) {
            MapManager.cameraLogic();
         }

         checkObjWhileMoving(player);
         if (player2 != null) {
            //checkObjWhileMoving(player2);
            collisionChkWithAllGameObject(player2);
         }
         var0 = 0;
         int var2 = 0;
         getGameObjectVecArray(player, mainObjectLogicVec);

         GameObject var3;
         while(var2 < mainObjectLogicVec.size()) {
            Vector var4 = (Vector)mainObjectLogicVec.elementAt(var2);
            if (var0 < var4.size()) {
               var3 = (GameObject)var4.elementAt(var0);
               int var1 = var0;
               if (!player.isControlObject(var3) || player2 != null && !player2.isControlObject(var3)) {
                  var3.logic();
                  if (var3.objectChkDestroy()) {
                     var3.close();
                     var4.removeElementAt(var0);
                     var1 = var0 - 1;
                  } else {
                     var1 = var0;
                     if (var3.checkInit()) {
                        var4.removeElementAt(var0);
                        var1 = var0 - 1;
                     }
                  }
               }

               if (checkPaintNecessary(var3)) {
                  paintVec[var3.getPaintLayer()].addElement(var3);
               }

               var0 = var1 + 1;
            } else {
               var0 = 0;
               ++var2;
            }
         }

         if (bossObjVec != null) {
            for(var0 = 0; var0 < bossObjVec.size(); ++var0) {
               var3 = (GameObject)bossObjVec.elementAt(var0);
               var3.logic();
               if (!var3.isFarAwayCamera()) {
                  paintVec[var3.getPaintLayer()].addElement(var3);
               }
            }
         }

         BulletObject.bulletLogicAll();
         SmallAnimal.animalLogic();
         if (player.outOfControl) {
            MapManager.cameraLogic();
         }
      }

   }

   private static void nextCursor() {
      while(true) {
         if (gettingObject && objectCursor >= allGameObject[cursorX][cursorY].size()) {
            objectCursor = 0;
            ++cursorX;
            if (cursorX <= endX) {
               continue;
            }

            cursorX = startX;
            ++cursorY;
            if (cursorY <= endY) {
               continue;
            }

            gettingObject = false;
         }

         return;
      }
   }

   public static void quitGameState() {
      closeObject(false);
      ItemObject.closeItem();
      SmallAnimal.releaseAllResource();
      PlayerObject.doWhileQuitGame();
      System.gc();
   }

   public static void setNewParam(int[] var0) {
      PlayerObject.setNewParam(var0);
      GRAVITY = var0[10];
   }

   public static void setNoInput() {
      if (player != null) {
         player.setNoKey();
      }
      if (player2 != null) {
          player2.setNoKey();
      }

   }

   public static void setPlayerPosition(int var0, int var1) {
      if (player != null) {
         PlayerObject var3 = player;
         PlayerObject var2 = player;
         var0 <<= 6;
         var2.footPointX = var0;
         var3.posX = var0;
         var2 = player;
         var3 = player;
         var0 = var1 << 6;
         var3.footPointY = var0;
         var2.posY = var0;
      }
   }

   public boolean canBeInit() {
      return true;
   }

   public boolean checkInit() {
      CollisionRect var6 = resetRect;
      int var4 = MapManager.getCamera().x;
      int var3 = MapManager.CAMERA_WIDTH;
      int var1 = MapManager.getCamera().y;
      int var2 = MapManager.CAMERA_HEIGHT;
      var6.setRect((var4 + (var3 >> 1) << 6) - 14720, (var1 + (var2 >> 1) << 6) - 14720, 29440, 29440);
      boolean var5;
      if (this.needInit) {
         if (!resetRect.collisionChk(this.collisionRect) && this.canBeInit()) {
            this.doInitWhileInCamera();
            this.refreshCollisionRect(this.posX, this.posY);
            addGameObject(this);
            this.needInit = false;
            var5 = true;
            return var5;
         }
      } else if (resetRect.collisionChk(this.collisionRect)) {
         this.needInit = true;
      }

      var5 = false;
      return var5;
   }

   public void checkWithMap(int var1, int var2, int var3, int var4) {
      int var10 = var3 - var1;
      int var11 = var4 - var2;
      if (var11 < 0) {
      }

      if (var10 < 0) {
      }

      int var8 = (Math.abs(var10) + 512 - 1) / 512;
      int var7 = (Math.abs(var11) + 512 - 1) / 512;
      boolean var5;
      if (var8 > var7) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var11 > 0) {
         int var6 = 0;

         while(true) {
            if (var5) {
               var3 = var8;
            } else {
               var3 = var7;
            }

            if (var6 > var3) {
               break;
            }

            int var9;
            byte var13;
            if (var5) {
               if (var10 >= 0) {
                  var13 = 1;
               } else {
                  var13 = -1;
               }

               var9 = var1 + var13 * 512 * var6;
               var4 = (var2 * var8 + var11 * var6) / var8;
               if (var10 > 0) {
                  var3 = var9;
                  if (var9 > var1 + var10) {
                     var3 = var1 + var10;
                     var4 = var2 + var11;
                  }
               } else if (var10 < 0) {
                  var3 = var9;
                  if (var9 < var1 + var10) {
                     var3 = var1 + var10;
                     var4 = var2 + var11;
                  }
               } else {
                  var3 = var1;
               }
            } else {
               if (var11 >= 0) {
                  var13 = 1;
               } else {
                  var13 = -1;
               }

               var9 = var2 + var13 * 512 * var6;
               var3 = (var1 * var7 + var10 * var6) / var7;
               var4 = var9;
               if (var9 > var2 + var11) {
                  var3 = var1 + var10;
                  var4 = var2 + var11;
               }
            }

            var9 = this.getDownCheckPointY(var3, var4);
            int var12 = this.downSideCollisionChk(var3, var9);
            if (var12 >= 0) {
               this.posY = var12 - (var9 - var4);
               this.posX = var3;
               break;
            }

            ++var6;
         }
      }

   }

   public void checkWithPlayer(int var1, int var2, int var3, int var4) {
      int var7 = var3 - var1;
      int var6 = var4 - var2;
      if (var7 == 0 && var6 == 0) {
         this.refreshCollisionRect(var3, var4);
         this.doWhileCollisionWrapWithPlayer();
      } else {
         boolean var9;
         if (Math.abs(var7) >= Math.abs(var6)) {
            var9 = true;
         } else {
            var9 = false;
         }

         if (var9) {
            var3 = Math.abs(var7);
         } else {
            var3 = Math.abs(var6);
         }

         var4 = 0;

         while(var4 <= var3 && var4 < var3) {
            int var5 = var4 + 512;
            var4 = var5;
            if (var5 >= var3) {
               var4 = var3;
            }

            var5 = var7 * var4 / var3;
            int var8 = var6 * var4 / var3;
            this.refreshCollisionRect(var1 + var5, var2 + var8);
            this.doWhileCollisionWrapWithPlayer();
         }
      }

   }

   public abstract void close();

   public boolean collisionChkWithObject(PlayerObject var1) {
      CollisionRect var7 = var1.getCollisionRect();
      CollisionRect var9 = this.getCollisionRect();
      CollisionRect var8 = rectH;
      int var5 = var7.x0;
      int var2 = var7.y0;
      int var4 = var7.getWidth();
      int var3 = var7.getHeight();
      var8.setRect(var5, var2 + 192, var4, var3 - 384);
      var8 = rectV;
      var3 = var7.x0;
      var2 = var7.y0;
      var4 = var7.getWidth();
      var5 = var7.getHeight();
      var8.setRect(var3 + 192, var2, var4 - 384, var5);
      boolean var6;
      if (!var9.collisionChk(rectH) && !var9.collisionChk(rectV)) {
         var6 = false;
      } else {
         var6 = true;
      }

      return var6;
   }

   public void doInitWhileInCamera() {
   }

   public void doWhileBeAttack(PlayerObject var1, int var2, int var3) {
   }

   public abstract void doWhileCollision(PlayerObject var1, int var2);

   public void doWhileCollisionWrap(PlayerObject var1) {
      byte var4 = 4;
      int var2 = var1.getMoveDistance().x;
      var2 = var1.getMoveDistance().y;
      CollisionRect var9 = var1.getCollisionRect();
      CollisionRect var8 = var1.preCollisionRect;
      var2 = var9.x0;
      int var6 = var8.x0;
      int var5 = var9.y0;
      int var3 = var8.y0;
      boolean var11;
      if (Math.abs(var2 - var6) >= Math.abs(var5 - var3)) {
         var11 = true;
      } else {
         var11 = false;
      }

      CollisionRect var10 = rectH;
      var2 = var9.x0;
      var5 = var9.y0;
      var6 = var9.getWidth();
      int var7 = var9.getHeight();
      var10.setRect(var2, var5 + 192, var6, var7 - 192 * 2);
      var10 = rectV;
      var7 = var9.x0;
      var5 = var9.y0;
      var2 = var9.getWidth();
      var6 = var9.getHeight();
      var10.setRect(var7 + 192, var5, var2 - 192 * 2, var6);
      byte var12 = var4;
      if (var11) {
         var12 = var4;
         if (rectH.collisionChk(this.getCollisionRect())) {
            if ((var9.x1 - var8.x1 <= 0 || !var8.isLeftOf(this.collisionRect, 192)) && (rectV.collisionChk(this.getCollisionRect()) || var9.x0 >= this.collisionRect.x0 || var1.getVelX() < -192)) {
               label120: {
                  if (var9.x0 - var8.x0 >= 0 || !var8.isRightOf(this.collisionRect, 192)) {
                     var12 = var4;
                     if (rectV.collisionChk(this.getCollisionRect())) {
                        break label120;
                     }

                     var12 = var4;
                     if (var9.x1 <= this.collisionRect.x1) {
                        break label120;
                     }

                     var12 = var4;
                     if (var1.getVelX() > 192) {
                        break label120;
                     }
                  }

                  var12 = 2;
               }
            } else {
               var12 = 3;
            }
         }
      }

      byte var13 = var12;
      if (var12 == 4) {
         var13 = var12;
         if (rectV.collisionChk(this.getCollisionRect())) {
            if (var9.y1 - var8.y1 > 0 && var8.isUpOf(this.collisionRect, 192 + 5)) {
               var13 = 1;
            } else {
               var13 = var12;
               if (var9.y0 - var8.y0 < 0) {
                  var13 = var12;
                  if (var8.isDownOf(this.collisionRect, 192)) {
                     var13 = 0;
                  }
               }
            }
         }
      }

      var12 = var13;
      if (var13 == 4) {
         var12 = var13;
         if (rectH.collisionChk(this.getCollisionRect())) {
            if ((var9.x1 - var8.x1 <= 0 || !var8.isLeftOf(this.collisionRect, 192)) && (rectV.collisionChk(this.getCollisionRect()) || var9.x0 >= this.collisionRect.x0 || var1.getVelX() < -192)) {
               label122: {
                  if (var9.x0 - var8.x0 >= 0 || !var8.isRightOf(this.collisionRect, 192)) {
                     var12 = var13;
                     if (rectV.collisionChk(this.getCollisionRect())) {
                        break label122;
                     }

                     var12 = var13;
                     if (var9.x1 <= this.collisionRect.x1) {
                        break label122;
                     }

                     var12 = var13;
                     if (var1.getVelX() > 192) {
                        break label122;
                     }
                  }

                  var12 = 2;
               }
            } else {
               var12 = 3;
            }
         }
      }

      if (player.inRailState() || player2 != null && player2.inRailState()) {
         this.doWhileRail(var1, var12);
      } else {
         this.doWhileCollision(var1, var12);
      }

   }

   public void doWhileCollisionWrapWithPlayer() {
      if (player != null && !player.isDead) {
         byte var3 = 4;
         int var4 = this.collisionRect.x0 - this.preCollisionRect.x0;
         int var2 = this.collisionRect.y0;
         int var1 = this.preCollisionRect.y0;
         boolean var10;
         if (Math.abs(var4) >= Math.abs(var2 - var1)) {
            var10 = true;
         } else {
            var10 = false;
         }

         player.refreshCollisionRectWrap();
         CollisionRect var8 = rectH;
         int var6 = player.collisionRect.x0;
         int var7 = player.collisionRect.y0;
         int var5 = player.collisionRect.getWidth();
         var1 = player.collisionRect.getHeight();
         var8.setRect(var6, var7 + 192, var5, var1 - 192 * 2);
         var8 = rectV;
         var6 = player.collisionRect.x0;
         var5 = player.collisionRect.y0;
         var1 = player.collisionRect.getWidth();
         var7 = player.collisionRect.getHeight();
         var8.setRect(var6 + 192, var5, var1 - 192 * 2, var7);
         byte var9 = var3;
         if (var10) {
            var9 = var3;
            if (rectH.collisionChk(this.getCollisionRect())) {
               if (this.collisionRect.x1 > this.preCollisionRect.x1 && this.preCollisionRect.isLeftOf(player.collisionRect, 192)) {
                  var9 = 2;
               } else {
                  var9 = var3;
                  if (this.collisionRect.x0 < this.preCollisionRect.x0) {
                     var9 = var3;
                     if (this.preCollisionRect.isRightOf(player.collisionRect, 192)) {
                        var9 = 3;
                     }
                  }
               }
            }
         }

         byte var11 = var9;
         if (var9 == 4) {
            var11 = var9;
            if (rectV.collisionChk(this.getCollisionRect())) {
               if (this.collisionRect.y1 > this.preCollisionRect.y1 && this.preCollisionRect.isUpOf(player.collisionRect, 192)) {
                  var11 = 0;
               } else {
                  var11 = var9;
                  if (this.collisionRect.y0 < this.preCollisionRect.y0) {
                     var11 = var9;
                     if (this.preCollisionRect.isDownOf(player.collisionRect, 192)) {
                        var11 = 1;
                     }
                  }
               }
            }
         }

         var9 = var11;
         if (var11 == 4) {
            var9 = var11;
            if (rectH.collisionChk(this.getCollisionRect())) {
               if (this.collisionRect.x1 > this.preCollisionRect.x1 && this.preCollisionRect.isLeftOf(player.collisionRect, 192)) {
                  var9 = 2;
               } else {
                  var9 = var11;
                  if (this.collisionRect.x0 < this.preCollisionRect.x0) {
                     var9 = var11;
                     if (this.preCollisionRect.isRightOf(player.collisionRect, 192)) {
                        var9 = 3;
                     }
                  }
               }
            }
         }

         if (!player.isFootOnObject(this)) {
            if (this.collisionChkWithObject(player)) {
               if (player.railing) {
                  this.doWhileRail(player, var9);
               } else {
                  this.doWhileCollision(player, var9);
               }
            }
         } else {
            PlayerObject var13 = player;
            var2 = player.footPointX;
            if (player.isAntiGravity) {
               var1 = this.collisionRect.y1;
            } else {
               var1 = this.collisionRect.y0;
            }

            var13.moveOnObject(var2 + var4, var1);
         }

         var8 = this.preCollisionRect;
         var4 = this.collisionRect.x0;
         var2 = this.collisionRect.y0;
         var1 = this.collisionRect.x1;
         int var12 = this.collisionRect.y1;
         var8.setTwoPosition(var4, var2, var1, var12);
      }
      if (player2 != null && !player2.isDead) {
         byte var3 = 4;
         int var4 = this.collisionRect.x0 - this.preCollisionRect.x0;
         int var2 = this.collisionRect.y0;
         int var1 = this.preCollisionRect.y0;
         boolean var10;
         if (Math.abs(var4) >= Math.abs(var2 - var1)) {
            var10 = true;
         } else {
            var10 = false;
         }

         player2.refreshCollisionRectWrap();
         CollisionRect var8 = rectH;
         int var6 = player2.collisionRect.x0;
         int var7 = player2.collisionRect.y0;
         int var5 = player2.collisionRect.getWidth();
         var1 = player2.collisionRect.getHeight();
         var8.setRect(var6, var7 + 192, var5, var1 - 192 * 2);
         var8 = rectV;
         var6 = player2.collisionRect.x0;
         var5 = player2.collisionRect.y0;
         var1 = player2.collisionRect.getWidth();
         var7 = player2.collisionRect.getHeight();
         var8.setRect(var6 + 192, var5, var1 - 192 * 2, var7);
         byte var9 = var3;
         if (var10) {
            var9 = var3;
            if (rectH.collisionChk(this.getCollisionRect())) {
               if (this.collisionRect.x1 > this.preCollisionRect.x1 && this.preCollisionRect.isLeftOf(player2.collisionRect, 192)) {
                  var9 = 2;
               } else {
                  var9 = var3;
                  if (this.collisionRect.x0 < this.preCollisionRect.x0) {
                     var9 = var3;
                     if (this.preCollisionRect.isRightOf(player2.collisionRect, 192)) {
                        var9 = 3;
                     }
                  }
               }
            }
         }

         byte var11 = var9;
         if (var9 == 4) {
            var11 = var9;
            if (rectV.collisionChk(this.getCollisionRect())) {
               if (this.collisionRect.y1 > this.preCollisionRect.y1 && this.preCollisionRect.isUpOf(player2.collisionRect, 192)) {
                  var11 = 0;
               } else {
                  var11 = var9;
                  if (this.collisionRect.y0 < this.preCollisionRect.y0) {
                     var11 = var9;
                     if (this.preCollisionRect.isDownOf(player2.collisionRect, 192)) {
                        var11 = 1;
                     }
                  }
               }
            }
         }

         var9 = var11;
         if (var11 == 4) {
            var9 = var11;
            if (rectH.collisionChk(this.getCollisionRect())) {
               if (this.collisionRect.x1 > this.preCollisionRect.x1 && this.preCollisionRect.isLeftOf(player2.collisionRect, 192)) {
                  var9 = 2;
               } else {
                  var9 = var11;
                  if (this.collisionRect.x0 < this.preCollisionRect.x0) {
                     var9 = var11;
                     if (this.preCollisionRect.isRightOf(player2.collisionRect, 192)) {
                        var9 = 3;
                     }
                  }
               }
            }
         }

         if (!player2.isFootOnObject(this)) {
            if (this.collisionChkWithObject(player2)) {
               if (player2.railing) {
                  this.doWhileRail(player2, var9);
               } else {
                  this.doWhileCollision(player2, var9);
               }
            }
         } else {
            PlayerObject var13 = player2;
            var2 = player2.footPointX;
            if (player2.isAntiGravity) {
               var1 = this.collisionRect.y1;
            } else {
               var1 = this.collisionRect.y0;
            }

            var13.moveOnObject(var2 + var4, var1);
         }

         var8 = this.preCollisionRect;
         var4 = this.collisionRect.x0;
         var2 = this.collisionRect.y0;
         var1 = this.collisionRect.x1;
         int var12 = this.collisionRect.y1;
         var8.setTwoPosition(var4, var2, var1, var12);
      }

   }

   public void doWhileNoCollision() {
   }

   public void doWhileRail(PlayerObject var1, int var2) {
   }

   public abstract void draw(MFGraphics var1);

   public void drawCollisionRect(MFGraphics var1) {
      if (SonicDebug.showCollisionRect) {
         var1.setColor(16711680);
         int var2 = this.collisionRect.x0;
         int var4 = camera.x;
         int var6 = this.collisionRect.y0;
         int var3 = camera.y;
         int var5 = this.collisionRect.getWidth();
         int var7 = this.collisionRect.getHeight();
         var1.drawRect((var2 >> 6) - var4, (var6 >> 6) - var3, var5 >> 6, var7 >> 6);
         var3 = this.collisionRect.x0;
         var7 = camera.x;
         var2 = this.collisionRect.y0;
         var4 = camera.y;
         var5 = this.collisionRect.getWidth();
         var6 = this.collisionRect.getHeight();
         var1.drawRect((var3 >> 6) - var7 + 1, (var2 >> 6) - var4 + 1, (var5 >> 6) - 2, (var6 >> 6) - 2);
      }

   }

   public void drawInMap(MFGraphics var1, AnimationDrawer var2) {
      this.drawInMap(var1, var2, this.posX, this.posY);
   }

   public void drawInMap(MFGraphics var1, AnimationDrawer var2, int var3, int var4) {
      var2.draw(var1, (var3 >> 6) - camera.x, (var4 >> 6) - camera.y);
   }

   public void drawInMap(MFGraphics var1, MFImage var2, int var3) {
      this.drawInMap(var1, var2, this.posX, this.posY, var3);
   }

   public void drawInMap(MFGraphics var1, MFImage var2, int var3, int var4, int var5) {
      int var7 = camera.x;
      int var6 = camera.y;
      MyAPI.drawImage(var1, var2, (var3 >> 6) - var7, (var4 >> 6) - var6, var5);
   }

   public void drawInMap(MFGraphics var1, MFImage var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      this.drawInMap(var1, var2, var3, var4, var5, var6, var7, this.posX, this.posY, var8);
   }

   public void drawInMap(MFGraphics var1, MFImage var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      int var11 = camera.x;
      int var12 = camera.y;
      MyAPI.drawRegion(var1, var2, var3, var4, var5, var6, var7, (var8 >> 6) - var11, (var9 >> 6) - var12, var10);
   }

   public int getBlockDownSide(int var1, int var2) {
      return (var2 + 1 << 3) - 1 << 6;
   }

   public int getBlockLeftSide(int var1, int var2) {
      return var1 + 0 << 3 << 6;
   }

   public int getBlockRightSide(int var1, int var2) {
      return (var1 + 1 << 3) - 1 << 6;
   }

   public int getBlockUpSide(int var1, int var2) {
      return var2 + 0 << 3 << 6;
   }

   public int getCheckPositionX() {
      return this.posX;
   }

   public int getCheckPositionY() {
      return this.posY;
   }

   public CollisionRect getCollisionRect() {
      return this.collisionRect;
   }

   public int getGroundY(int var1, int var2) {
      int var3 = this.getGroundY(var1, var2, 0);
      var1 = this.getGroundY(var1, var2, 1);
      if (var3 == -1000 && var1 == -1000) {
         var1 = var2;
      } else if (var3 != -1000) {
         if (var1 == -1000) {
            var1 = var3;
         } else if (var3 < var1) {
            var1 = var3;
         }
      }

      return var1;
   }

   public int getGroundY(int var1, int var2, int var3) {
      int var4 = 0;

      while(true) {
         if (var4 >= 10) {
            var1 = var2;
            break;
         }

         var2 += this.worldInstance.getTileHeight() * 1;
         int var5 = this.worldInstance.getWorldY(var1, var2, var3, 0);
         if (var5 != -1000) {
            var1 = var5;
            break;
         }

         ++var4;
      }

      return var1;
   }

   public Coordinate getMoveDistance() {
      return this.moveDistance;
   }

   public int getObjectID() {
      return this.objId;
   }

   public int getPaintLayer() {
      return 1;
   }

   public int getQuaParam(int var1, int var2) {
      if (var1 > 0) {
         var1 /= var2;
      } else {
         var1 = (var1 - (var2 - 1)) / var2;
      }

      return var1;
   }

   public boolean isAwayFromCameraInWidth() {
      boolean var1;
      if (this.isInCameraOnlyWidth(MapManager.CAMERA_WIDTH >> 1)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isFarAwayCamera() {
      boolean var1;
      if (this.isInCamera(256)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isInCamera() {
      return this.isInCamera(80);
   }

   public boolean isInCamera(int var1) {
      Coordinate var9 = MapManager.getCamera();
      CollisionRect var8 = screenRect;
      int var4 = var9.x;
      int var7 = MapManager.CAMERA_OFFSET_X;
      int var6 = var9.y;
      int var3 = MapManager.CAMERA_OFFSET_Y;
      int var2 = MapManager.CAMERA_WIDTH;
      int var5 = MapManager.CAMERA_HEIGHT;
      var8.setRect(var4 + var7 << 6, var6 + var3 << 6, var2 << 6, var5 << 6);
      var8 = screenRect;
      var8.x0 -= var1 << 6;
      var8 = screenRect;
      var8.x1 += var1 << 6;
      var8 = screenRect;
      var8.y0 -= var1 << 6;
      var8 = screenRect;
      var8.y1 += var1 << 6;
      return this.collisionRect.collisionChk(screenRect);
   }

   public boolean isInCamera(int var1, int var2) {
      Coordinate var9 = MapManager.getCamera();
      CollisionRect var10 = screenRect;
      int var8 = var9.x;
      int var5 = MapManager.CAMERA_OFFSET_X;
      int var3 = var9.y;
      int var6 = MapManager.CAMERA_OFFSET_Y;
      int var4 = MapManager.CAMERA_WIDTH;
      int var7 = MapManager.CAMERA_HEIGHT;
      var10.setRect(var8 + var5 << 6, var3 + var6 << 6, var4 << 6, var7 << 6);
      CollisionRect var11 = screenRect;
      var11.x0 -= var1 >> 1 << 6;
      var11 = screenRect;
      var11.x1 += var1 >> 1 << 6;
      var11 = screenRect;
      var11.y0 -= var2 >> 1 << 6;
      var11 = screenRect;
      var11.y1 += var2 >> 1 << 6;
      return this.collisionRect.collisionChk(screenRect);
   }

   public boolean isInCameraOnlyHeight(int var1) {
      this.isInCamera(0, var1);
      return this.collisionRect.collisionChkWidth(screenRect);
   }

   public boolean isInCameraOnlyWidth(int var1) {
      this.isInCamera(var1, 0);
      return this.collisionRect.collisionChkWidth(screenRect);
   }

   public boolean isInCameraSmaller() {
      return this.isInCamera(0);
   }

   public abstract void logic();

   public boolean objectChkDestroy() {
      return false;
   }

   public boolean onObjectChk(PlayerObject var1) {
      CollisionRect var8 = var1.getCollisionRect();
      CollisionRect var6 = this.getCollisionRect();
      CollisionRect var7 = rectH;
      int var2 = var8.x0;
      int var5 = var8.y0;
      int var4 = var8.getWidth();
      int var3 = var8.getHeight();
      var7.setRect(var2, var5 + 192, var4, var3 - 384);
      var7 = rectV;
      var4 = var8.x0;
      var5 = var8.y0;
      var2 = var8.getWidth();
      var3 = var8.getHeight();
      var7.setRect(var4 + 192, var5, var2 - 384, var3);
      return var6.collisionChk(rectV);
   }

   public abstract void refreshCollisionRect(int var1, int var2);

   public void refreshCollisionRectWrap() {
      this.refreshCollisionRect(this.posX, this.posY);
      CollisionRect var5 = this.preCollisionRect;
      int var2 = this.collisionRect.x0;
      int var3 = this.collisionRect.y0;
      int var4 = this.collisionRect.x1;
      int var1 = this.collisionRect.y1;
      var5.setTwoPosition(var2, var3, var4, var1);
   }

   public boolean releaseWhileBeHurt() {
      return false;
   }

   public void transportTo(int var1, int var2) {
      this.posX = var1;
      this.posY = var2;
      this.refreshCollisionRect(this.posX, this.posY);
      CollisionRect var5 = this.preCollisionRect;
      int var3 = this.collisionRect.x0;
      var1 = this.collisionRect.y0;
      int var4 = this.collisionRect.x1;
      var2 = this.collisionRect.y1;
      var5.setTwoPosition(var3, var1, var4, var2);
   }
}
