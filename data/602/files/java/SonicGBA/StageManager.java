package SonicGBA;

import GameEngine.time.GameTime;

import GameEngine.Key;
import Lib.MyAPI;
import Lib.Record;
import Lib.SoundSystem;
import State.GameState;
import State.SpecialStageState;
import State.State;
import State.TitleState;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.platform.ChargePlatform;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import com.sega.mobile.framework.MFMain;
import State.SpecialStageState;

public class StageManager implements SonicDef {
   private static final int CHARACTER_NUM = 4;
   private static final int HIGH_SCORE_NUM = 5;
   private static final int HIGH_SCORE_OFFSET_X = 0;
   private static final int HIGH_SCORE_Y;
   private static final int HIGH_SCORE_Y_TMP;
   public static boolean IsCalculateScore;
   private static final int LOAD_ANIMAL = 10;
   private static final int LOAD_BACKGROUND = 4;
   private static final int LOAD_COLLISION = 3;
   private static final int LOAD_ENEMY = 8;
   private static final int LOAD_GAME_INIT = 13;
   private static final int LOAD_GAME_LOGIC = 11;
   private static final int LOAD_GIMMICK = 6;
   private static final int LOAD_ITEM = 9;
   private static final int LOAD_MAP = 2;
   private static final int LOAD_OBJ_INIT = 5;
   private static final int LOAD_RELEASE_MEMORY = 0;
   private static final int LOAD_RELEASE_MEMORY_2 = 1;
   private static final int LOAD_RING = 7;
   private static final int LOAD_SE = 12;
   private static final int MOVING_SPACE = 2;
   public static final int[] MUSIC_ID;
   public static final int[] MUSIC_ID_HIGH;
   public static final int[] MUSIC_ID_LOW;
   public static final int[][] PLAYER_FAST_PASS_START;
   public static final int[][] PLAYER_START;
   private static final String[] RANK_STR_FOR_EN;
   private static final int RECORD_NUM = 3;
   private static final int STAGE_4_1_WATER_LEVEL = 1548;
   private static final int STAGE_4_2_WATER_LEVEL = 1667;
   private static final int STAGE_GAMEOVER_FRAME = 10;
   public static final String[] STAGE_NAME;
   public static final String[] STAGE_NAME_HIGH;
   public static final int[] STAGE_NAME_ID;
   private static final String[] STAGE_NAME_LOW;
   public static final String[] STAGE_NAME_LOW_FOUR = new String[]{"1_1", "1_2", "2_1", "2_2"};
   public static final String[] STAGE_NAME_LOW_SIX = new String[]{"1_1", "1_2", "2_1", "2_2", "5_1", "5_2"};
   public static final int STAGE_NUM;
   private static final int STAGE_PASS_FRAME = 0;
   private static final int STAGE_RESTART_FRAME = 10;
   private static final int STAGE_TIMEOVER_FRAME = 0;
   public static final int[] ZOME_ID;
   public static int characterFromGame;
   public static int checkCameraDownX;
   public static boolean checkCameraEnable;
   public static int checkCameraLeftX;
   public static int checkCameraRightX;
   public static int checkCameraUpX;
   public static boolean checkPointEnable;
   public static int checkPointTime;
   public static int checkPointX;
   public static int checkPointY;
   private static int drawNewScore;
   private static int[] highScore;
   public static boolean isContinueGame;
   public static boolean isNextGameStageDirectedly;
   public static boolean isOnlyScoreCal;
   public static boolean isOnlyStagePass;
   private static boolean isRacing;
   public static boolean isSaveTimeModeScore;
   public static boolean isScoreBarOutOfScreen;
   public static int loadStep;
   private static int movingCount;
   private static int movingRow;
   private static int[] normalStageIDArray;
   private static int normalStageId;
   private static int[] openedStageIDArray;
   private static int openedStageId;
   private static int[] preStageIDArray;
   private static int preStageId = -1;
   private static int[] rankingOffsetX;
   public static int specialStagePointX;
   public static int specialStagePointY;
   private static int stageGameoverCount;
   private static boolean stageGameoverFlag;
   private static int[] stageIDArray;
   public static int stageIDFromGame;
   private static int stageId = 0;
   private static int stagePassCount;
   private static boolean stagePassFlag;
   private static int stageRestartCount;
   private static boolean stageRestartFlag;
   private static int stageTimeoverCount;
   private static boolean stageTimeoverFlag;
   private static int startStageID;
   private static int[] startStageIDArray;
   private static int[] timeModeScore;
   private static int waterLevel;
   public static volatile boolean unlckemrld = false;

   static {
      STAGE_NAME_LOW = STAGE_NAME_LOW_SIX;
      STAGE_NAME_HIGH = new String[]{"1_1", "1_2", "2_1", "2_2", "3_1", "3_2", "4_1", "4_2", "5_1", "5_2", "6_1", "6_2", "final", "EX"};
      STAGE_NAME = STAGE_NAME_HIGH;
      int[] var0 = new int[]{96, 146};
      int[] var1 = new int[]{106, 6988};
      int[] var2 = new int[]{56, 230};
      PLAYER_START = new int[][]{{96, 448}, {96, 690}, var0, {96, 1600}, {96, 160}, {96, 640}, {100, 200}, {80, 868}, {96, 832}, {96, 928}, var1, {100, 1732}, {100, 771}, var2};
      var0 = new int[]{8416, 1347};
      PLAYER_FAST_PASS_START = new int[][]{{11976, 997}, {9635, 706}, {9613, 584}, var0, {6153, 2406}, {7128, 2220}, {9730, 1399}, {7797, 1205}, {8247, 2264}, {8393, 1107}, {2094, 125}, {9609, 890}, {1845, 385}, {56, 230}};
      MUSIC_ID_LOW = new int[]{6, 7, 8, 9, 10, 11, 12, 13};
      MUSIC_ID_HIGH = new int[]{6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 20};
      MUSIC_ID = MUSIC_ID_HIGH;
      var0 = new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 7};
      ZOME_ID = var0;
      STAGE_NAME_ID = new int[]{86, 87, 88, 89, 89, 89, 89, 89};
      STAGE_NUM = STAGE_NAME.length;
      loadStep = 0;
      isNextGameStageDirectedly = false;
      highScore = new int[5];
      openedStageId = 0;
      timeModeScore = new int[STAGE_NUM * 3 * 4];
      stageIDArray = new int[4];
      openedStageIDArray = new int[4];
      normalStageIDArray = new int[4];
      startStageIDArray = new int[4];
      preStageIDArray = new int[4];
      characterFromGame = -1;
      stageIDFromGame = -1;
      drawNewScore = -1;
      isSaveTimeModeScore = false;
      rankingOffsetX = new int[5];
      movingRow = 0;
      movingCount = GameTime.set(StageManager.class, "movingCount", 0);
      HIGH_SCORE_Y_TMP = SCREEN_HEIGHT - MENU_SPACE * 5 >> 1;
      HIGH_SCORE_Y = FONT_H_HALF + 44;
      RANK_STR_FOR_EN = new String[]{"1st", "2nd", "3rd", "4th", "5th"};
      isRacing = false;
      waterLevel = 1548;
      isOnlyScoreCal = false;
      isOnlyStagePass = false;
      isScoreBarOutOfScreen = false;
   }

   public static boolean IsStageEnd() {
      boolean var0;
      if (stageIDArray[PlayerObject.getCharacterID()] + 1 == STAGE_NAME.length) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static void addNewNormalScore(int var0) {
      boolean var3 = false;
      int var1 = 0;

      for(int var2 = 0; var2 < 5; ++var2) {
         if (!var3) {
            if (var0 > highScore[var2]) {
               var3 = true;
               var1 = highScore[var2];
               highScore[var2] = var0;
               drawNewScore = var2;
            }
         } else {
            int var4 = highScore[var2];
            highScore[var2] = var1;
            var1 = var4;
         }
      }

   }

   public static void addStageID() {
      int[] var1 = stageIDArray;
      int var0 = PlayerObject.getCharacterID();
      int var10002 = var1[var0]++;
      if (openedStageIDArray[PlayerObject.getCharacterID()] < stageIDArray[PlayerObject.getCharacterID()]) {
         openedStageIDArray[PlayerObject.getCharacterID()] = stageIDArray[PlayerObject.getCharacterID()];
      }

   }

   public static void doWhileEnterRace() {
      if (!isRacing) {
         normalStageIDArray[PlayerObject.getCharacterID()] = stageIDArray[PlayerObject.getCharacterID()];
         isRacing = true;
      }

   }

   public static void doWhileLeaveRace() {
      if (isRacing) {
         stageIDArray[PlayerObject.getCharacterID()] = normalStageIDArray[PlayerObject.getCharacterID()];
         isRacing = false;
      }

   }

   public static void draw(MFGraphics var0) {
      var0.setColor(16777215);
      MyAPI.fillRect(var0, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

      for(int var1 = 0; var1 < STAGE_NAME.length; ++var1) {
         var0.setColor(0);
         if (var1 == stageId) {
            var0.setColor(16711680);
         }

         String var3 = "stage" + STAGE_NAME[var1];
         int var2 = SCREEN_WIDTH;
         MyAPI.drawString(var0, var3, var2 >> 1, var1 * 20 + 20, 17);
      }

   }

   public static void drawHighScoreEnd() {
      drawNewScore = -1;
   }

   public static void drawNormalHighScore(MFGraphics var0) {
      int var3;
      for(var3 = 0; var3 < 5; ++var3) {
         int var4;
         int var5;
         int var6;
         int var7;
         int var8;
         if (drawNewScore == var3 && GameTime.milliseconds() / 300L % 2L == 0L) {
            var5 = SCREEN_WIDTH;
            var6 = rankingOffsetX[var3];
            var7 = HIGH_SCORE_Y;
            var4 = MENU_SPACE;
            State.drawMenuFontById(var0, 49, (var5 >> 1) - 45 + var6 + 0, var7 + var4 * var3);
            var5 = SCREEN_WIDTH;
            var7 = rankingOffsetX[var3];
            var4 = HIGH_SCORE_Y;
            var6 = MENU_SPACE;
            State.drawMenuFontById(var0, var3 + 38, (var5 >> 1) - 45 + var7 + 0, var4 + var6 * var3);
            var4 = highScore[var3];
            var8 = SCREEN_WIDTH;
            var5 = rankingOffsetX[var3];
            var6 = HIGH_SCORE_Y;
            var7 = MENU_SPACE;
            PlayerObject.drawNum(var0, var4, (var8 >> 1) + 20 + 48 + var5 + 0, var7 * var3 + var6, 2, 4);
         } else {
            var7 = SCREEN_WIDTH;
            var6 = rankingOffsetX[var3];
            var4 = HIGH_SCORE_Y;
            var5 = MENU_SPACE;
            State.drawMenuFontById(var0, 48, (var7 >> 1) - 45 + var6 + 0, var4 + var5 * var3);
            var5 = SCREEN_WIDTH;
            var4 = rankingOffsetX[var3];
            var6 = HIGH_SCORE_Y;
            var7 = MENU_SPACE;
            State.drawMenuFontById(var0, var3 + 28, (var5 >> 1) - 45 + var4 + 0, var6 + var7 * var3);
            var7 = highScore[var3];
            var4 = SCREEN_WIDTH;
            var6 = rankingOffsetX[var3];
            var8 = HIGH_SCORE_Y;
            var5 = MENU_SPACE;
            PlayerObject.drawNum(var0, var7, (var4 >> 1) + 20 + 48 + var6 + 0, var5 * var3 + var8, 2, 0);
         }
      }

      if (movingRow < rankingOffsetX.length && GameTime.periodic(StageManager.class, "movingCount", "drawNormalHighScore:282", movingCount, 2, 0)) {
         ++movingRow;
      }

      movingCount = GameTime.advance(StageManager.class, "movingCount", movingCount, 1);

      for(var3 = 0; var3 < movingRow; ++var3) {
         int[] var9 = rankingOffsetX;
         double var1 = (double)rankingOffsetX[var3];
         var9[var3] = MyAPI.calNextPosition(var9, String.valueOf(var3), var1, 0.0D, 1, 3);
      }

   }

   public static int getBgmId() {
      return MUSIC_ID[stageIDArray[PlayerObject.getCharacterID()]];
   }

   public static int getCurrentZoneId() {
      int var0;
      if (stageIDArray[PlayerObject.getCharacterID()] >= ZOME_ID.length) {
         var0 = ZOME_ID[ZOME_ID.length - 1] + 1;
      } else {
         var0 = ZOME_ID[stageIDArray[PlayerObject.getCharacterID()]] + 1;
      }

      return var0;
   }

   public static int getMaxStageID() {
      int var0;
      if (PlayerObject.getCharacterID() == 0) {
      if (MFMain.cheat) return STAGE_NUM;
         if (GameObject.stageModeState == 0) {
            if (openedStageIDArray[PlayerObject.getCharacterID()] >= STAGE_NUM - 1) {
               var0 = STAGE_NUM - 1;
            } else {
               var0 = STAGE_NUM - 2;
            }

            return var0;
         }

         if (GameObject.stageModeState == 1) {
            var0 = STAGE_NUM - 3;
            return var0;
         }
      } else {
         if (GameObject.stageModeState == 0) {
            var0 = STAGE_NUM - 2;
            return var0;
         }

         if (GameObject.stageModeState == 1) {
            var0 = STAGE_NUM - 3;
            return var0;
         }
      }

      var0 = 0;
      return var0;
   }

   public static int getOpenedStageId() {
      int var0;
      if (MFMain.cheat) {
      if (!unlckemrld) for (int i = 0; i < SpecialStageState.emeraldStatus.length; i++) SpecialStageState.emeraldStatus[i] = 1;
      unlckemrld = true;
      var0 = getMaxStageID();
      } else if (!ChargePlatform.isChargedByIndex(0)) {
         var0 = 0;
      } else if (TitleState.preStageSelectState == 24) {
         var0 = getMaxStageID();
      } else {
         int var1 = openedStageIDArray[PlayerObject.getCharacterID()];
         if (PlayerObject.getCharacterID() == 0) {
            if (GameObject.stageModeState == 0) {
               var0 = var1;
               if (var1 >= STAGE_NUM) {
                  var0 = STAGE_NUM;
               }
            } else {
               var0 = var1;
               if (GameObject.stageModeState == 1) {
                  var0 = var1;
                  if (var1 >= STAGE_NUM - 3) {
                     var0 = STAGE_NUM - 3;
                  }
               }
            }
         } else if (GameObject.stageModeState == 0) {
            var0 = var1;
            if (var1 >= STAGE_NUM - 2) {
               var0 = STAGE_NUM - 2;
            }
         } else {
            var0 = var1;
            if (GameObject.stageModeState == 1) {
               var0 = var1;
               if (var1 >= STAGE_NUM - 3) {
                  var0 = STAGE_NUM - 3;
               }
            }
         }
      }

      return var0;
   }

   public static int getStageID() {
      return stageIDArray[PlayerObject.getCharacterID()];
   }

   public static int getStageNameID(int var0) {
      return STAGE_NAME_ID[var0 / 2];
   }

   public static int getStartStageID() {
      return startStageIDArray[PlayerObject.getCharacterID()];
   }

   public static int getTimeModeScore(int var0) {
      return getTimeModeScore(var0, stageIDArray[var0]);
   }

   public static int getTimeModeScore(int var0, int var1) {
      return timeModeScore[STAGE_NUM * 3 * var0 + var1 * 3];
   }

   public static int getTimeModeScore(int var0, int var1, int var2) {
      return timeModeScore[STAGE_NUM * 3 * var0 + var1 * 3 + var2];
   }

   public static int[] getTimeModeScore() {
      return timeModeScore;
   }

   public static int getWaterLevel() {
      int var0;
      if (getCurrentZoneId() == 4) {
         var0 = waterLevel;
      } else {
         var0 = -1;
      }

      return var0;
   }

   public static boolean hasContinueGame() {
      boolean var0;
      if (stageIDArray[PlayerObject.getCharacterID()] == 0) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean isGoingToExtraStage() {
      boolean var0;
      if (PlayerObject.getCharacterID() == 0 && !SpecialStageState.emeraldMissed() && getStartStageID() != 12 && getStageID() == 12) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean isScoreBarOut() {
      return isScoreBarOutOfScreen;
   }

   public static boolean isStageGameover() {
      boolean var0;
      if (stageGameoverFlag && stageGameoverCount == 0) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean isStagePass() {
      boolean var0;
      if (stagePassFlag && stagePassCount == 0) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean isStagePassTimePause() {
      return stagePassFlag;
   }

   public static boolean isStageRestart() {
      boolean var0;
      if (stageRestartFlag && stageRestartCount == 0) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean isStageTimeover() {
      boolean var0;
      if (stageTimeoverFlag && stageTimeoverCount == 0) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static void loadHighScoreRecord() {
      ByteArrayInputStream bs = Record.loadRecordStream("SONIC_HIGHSCORE_RECORD");
      try {
         try {
            DataInputStream ds = new DataInputStream(bs);
            for (int i = 0; i < timeModeScore.length; i += LOAD_RELEASE_MEMORY_2) {
               timeModeScore[i] = ds.readInt();
            }
            if (bs != null) {
               try {
                  bs.close();
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
         } catch (Throwable th) {
            if (bs != null) {
               try {
                  bs.close();
               } catch (IOException e2) {
                  e2.printStackTrace();
               }
            }
            throw th;
         }
      } catch (Exception e3) {
         e3.printStackTrace();
         for (int i2 = 0; i2 < timeModeScore.length; i2 += LOAD_RELEASE_MEMORY_2) {
            timeModeScore[i2] = 599999;
         }
         saveHighScoreRecord();
         if (bs != null) {
            try {
               bs.close();
            } catch (IOException e4) {
               e4.printStackTrace();
            }
         }
      }
   }

   public static void loadStageRecord() {
        ByteArrayInputStream bs = Record.loadRecordStream("SONIC_STAGE_RECORD");
        try {
            try {
                DataInputStream ds = new DataInputStream(bs);
                stageId = ds.readByte();
                for (int i = 0; i < 4; i += LOAD_RELEASE_MEMORY_2) {
                    stageIDArray[i] = ds.readByte();
                }
                openedStageId = ds.readByte();
                if (openedStageId >= STAGE_NUM) {
                    openedStageId = STAGE_NUM - LOAD_RELEASE_MEMORY_2;
                }
                PlayerObject.getCharacterID();
                for (int i2 = 0; i2 < 4; i2 += LOAD_RELEASE_MEMORY_2) {
                    openedStageIDArray[i2] = ds.readByte();
                    if (openedStageIDArray[i2] >= STAGE_NUM) {
                        openedStageIDArray[i2] = STAGE_NUM - LOAD_RELEASE_MEMORY_2;
                    }
                }
                for (int i3 = 0; i3 < timeModeScore.length; i3 += LOAD_RELEASE_MEMORY_2) {
                    ds.readInt();
                }
                normalStageId = ds.readByte();
                if (normalStageId != stageId) {
                    stageId = normalStageId;
                }
                for (int i4 = 0; i4 < 4; i4 += LOAD_RELEASE_MEMORY_2) {
                    normalStageIDArray[i4] = ds.readByte();
                    if (normalStageIDArray[i4] != stageIDArray[i4]) {
                        stageIDArray[i4] = normalStageIDArray[i4];
                    }
                }
                startStageID = ds.readByte();
                for (int i5 = 0; i5 < 4; i5 += LOAD_RELEASE_MEMORY_2) {
                    startStageIDArray[i5] = ds.readByte();
                }
                characterFromGame = ds.readByte();
                stageIDFromGame = ds.readByte();
                PlayerObject.setLife(ds.readByte());
                PlayerObject.setScore(ds.readInt());
                TitleState.characterslots = ds.readInt();
                if (TitleState.characterslots == 2) GameObject.setPlayer2();
                if (bs != null) {
                    try {
                        bs.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Throwable th) {
                if (bs != null) {
                    try {
                        bs.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            stageId = 0;
            for (int i6 = 0; i6 < 4; i6 += LOAD_RELEASE_MEMORY_2) {
                stageIDArray[i6] = 0;
            }
            normalStageId = 0;
            for (int i7 = 0; i7 < 4; i7 += LOAD_RELEASE_MEMORY_2) {
                normalStageIDArray[i7] = 0;
            }
            PlayerObject.setScore(0);
            PlayerObject.setLife(2);
            openedStageId = 0;
            for (int i8 = 0; i8 < 4; i8 += LOAD_RELEASE_MEMORY_2) {
                openedStageIDArray[i8] = 0;
            }
            PlayerObject.resetGameParam();
            for (int i9 = 0; i9 < timeModeScore.length; i9 += LOAD_RELEASE_MEMORY_2) {
                timeModeScore[i9] = 599999;
            }
            startStageID = 0;
            for (int i10 = 0; i10 < 4; i10 += LOAD_RELEASE_MEMORY_2) {
                startStageIDArray[i10] = 0;
            }
            characterFromGame = -1;
            stageIDFromGame = -1;
            PlayerObject.setScore(0);
            PlayerObject.setLife(2);
            saveStageRecord();
            if (bs != null) {
                try {
                    bs.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        }
    }

    public static void saveStageRecord() {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        DataOutputStream ds = new DataOutputStream(bs);
        try {
            ds.writeByte(stageId);
            for (int i = 0; i < 4; i += LOAD_RELEASE_MEMORY_2) {
                ds.writeByte(stageIDArray[i]);
            }
            if (openedStageId < stageId) {
                openedStageId = stageId;
            }
            if (openedStageId >= STAGE_NUM) {
                openedStageId = STAGE_NUM - LOAD_RELEASE_MEMORY_2;
            }
            ds.writeByte(openedStageId);
            int characterID = PlayerObject.getCharacterID();
            if (openedStageIDArray[characterID] < stageIDArray[characterID]) {
                openedStageIDArray[characterID] = stageIDArray[characterID];
            }
            if (openedStageIDArray[characterID] >= STAGE_NUM) {
                openedStageIDArray[characterID] = STAGE_NUM - LOAD_RELEASE_MEMORY_2;
            }
            for (int i2 = 0; i2 < 4; i2 += LOAD_RELEASE_MEMORY_2) {
                ds.writeByte(openedStageIDArray[i2]);
            }
            for (int i3 = 0; i3 < timeModeScore.length; i3 += LOAD_RELEASE_MEMORY_2) {
                ds.writeInt(timeModeScore[i3]);
            }
            if (!isRacing && normalStageId != stageId) {
                normalStageId = stageId;
            }
            if (!isRacing && normalStageIDArray[characterID] != stageIDArray[characterID]) {
                normalStageIDArray[characterID] = stageIDArray[characterID];
            }
            ds.writeByte(normalStageId);
            for (int i4 = 0; i4 < 4; i4 += LOAD_RELEASE_MEMORY_2) {
                ds.writeByte(normalStageIDArray[i4]);
            }
            ds.writeByte(startStageID);
            for (int i5 = 0; i5 < 4; i5 += LOAD_RELEASE_MEMORY_2) {
                ds.writeByte(startStageIDArray[i5]);
            }
            ds.writeByte(characterFromGame);
            ds.writeByte(stageIDFromGame);
            ds.writeByte(PlayerObject.getLife());
            ds.writeInt(PlayerObject.getScore());
            ds.writeInt(TitleState.characterslots);
            Record.saveRecordStream("SONIC_STAGE_RECORD", bs);
            try {
                ds.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            try {
                ds.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } catch (Throwable th) {
            try {
                ds.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            throw th;
        }
    }

   public static boolean loadStageStep() {
      boolean var5 = true;
      int var0;
      int var1;
      boolean var4;
      String var6;
      switch(loadStep) {
      case 0:
         SoundSystem.getInstance().stopBgm(true);
         MapManager.closeMap();
         CollisionMap.getInstance().closeMap();
         Key.touchkeygameboardClose();
         RocketSeparateEffect.getInstance().close();
         Key.touchGamePauseClose();
         State.isDrawTouchPad = false;
         PlayerObject.isNeedPlayWaterSE = false;
         SoundSystem.getInstance().setSoundSpeed(1.0F);
         var4 = var5;
         State.initTouchkeyBoard();
         break;
      case 1:
         var1 = preStageIDArray[PlayerObject.getCharacterID()];
         var0 = stageIDArray[PlayerObject.getCharacterID()];
         if (var1 == var0) {
            var4 = true;
         } else {
            var4 = false;
         }

         var4 = GameObject.closeObjectStep(var4);
         break;
      case 2:
         PlayerObject.initStageParam();
         if (getCurrentZoneId() == 4) {
            if (stageIDArray[PlayerObject.getCharacterID()] % 2 == 0) {
               setWaterLevel(1548);
            } else {
               setWaterLevel(1667);
            }
         }

         var0 = stageIDArray[PlayerObject.getCharacterID()];
         var6 = STAGE_NAME[stageIDArray[PlayerObject.getCharacterID()]];
         var4 = MapManager.loadMapStep(var0, var6);
         break;
      case 3:
         CollisionMap var9 = CollisionMap.getInstance();
         var4 = var9.loadCollisionInfoStep(STAGE_NAME[stageIDArray[PlayerObject.getCharacterID()]]);
         break;
      case 4:
         BackGroundManager.init(stageIDArray[PlayerObject.getCharacterID()]);
         var4 = var5;
         break;
      case 5:
         int var3 = MapManager.getPixelWidth();
         var0 = MapManager.getPixelHeight();
         int var2 = preStageIDArray[PlayerObject.getCharacterID()];
         var1 = stageIDArray[PlayerObject.getCharacterID()];
         if (var2 == var1) {
            var4 = true;
         } else {
            var4 = false;
         }

         GameObject.initObject(var3, var0, var4);
         int[] var8 = preStageIDArray;
         var1 = PlayerObject.getCharacterID();
         var0 = stageIDArray[PlayerObject.getCharacterID()];
         var8[var1] = var0;
         if (GameState.isBackFromSpStage) {
            GameObject.setPlayerPosition(specialStagePointX, specialStagePointY);
            var4 = var5;
         } else {
            if (!stageRestartFlag) {
               checkPointEnable = false;
               checkCameraEnable = false;
            }

            if (stageRestartFlag && checkPointEnable) {
               GameObject.setPlayerPosition(checkPointX, checkPointY);
               PlayerObject.timeCount = checkPointTime;
            } else {
               var1 = PLAYER_START[stageIDArray[PlayerObject.getCharacterID()]][0];
               var0 = PLAYER_START[stageIDArray[PlayerObject.getCharacterID()]][1];
               GameObject.setPlayerPosition(var1, var0);
               PlayerObject.doInitInNewStage();
            }

            var4 = var5;
            if (checkCameraEnable) {
               var4 = var5;
               if (stageRestartFlag) {
                  var4 = var5;
                  if (PlayerObject.stageModeState != 1) {
                     MapManager.setCameraUpLimit(checkCameraUpX);
                     MapManager.setCameraDownLimit(checkCameraDownX);
                     MapManager.setCameraLeftLimit(checkCameraLeftX);
                     MapManager.setCameraRightLimit(checkCameraRightX);
                     MapManager.calCameraImmidiately();
                     var4 = var5;
                  }
               }
            }
         }
         break;
      case 6:
         var6 = "/map/" + STAGE_NAME[stageIDArray[PlayerObject.getCharacterID()]] + ".gi";
         var4 = GameObject.loadObjectStep(var6, 0);
         break;
      case 7:
         var6 = "/map/" + STAGE_NAME[stageIDArray[PlayerObject.getCharacterID()]] + ".ri";
         var4 = GameObject.loadObjectStep(var6, 1);
         break;
      case 8:
         var6 = "/map/" + STAGE_NAME[stageIDArray[PlayerObject.getCharacterID()]] + ".en";
         var4 = GameObject.loadObjectStep(var6, 2);
         if (getCurrentZoneId() == 8) {
            EnemyObject var7 = EnemyObject.getNewInstance(36, 0, 0, 0, 0, 0, 0);
            if (var7 != null && !EnemyObject.IsBoss) {
               GameObject.addGameObject(var7);
            }

            var4 = true;
         }
         break;
      case 9:
         var6 = "/map/" + STAGE_NAME[stageIDArray[PlayerObject.getCharacterID()]] + ".it";
         var4 = GameObject.loadObjectStep(var6, 3);
         Key.clear();
         stagePassFlag = false;
         stageRestartFlag = false;
         stageGameoverFlag = false;
         stageTimeoverFlag = false;
         break;
      case 10:
         SmallAnimal.animalInit();
         MapManager.focusQuickLocation();
         var4 = var5;
         break;
      case 11:
         var4 = true;
         Key.clear();
         GameObject.logicObjects();
         break;
      case 12:
         GameObject.isDamageSandActive = false;
         SoundSystem.getInstance().preLoadAllSe();
         var4 = var5;
         break;
      case 13:
         SoundSystem.getInstance().playBgm(getBgmId(), true);
         loadStep = 0;
         if (State.loadingType == 2) {
            isNextGameStageDirectedly = true;
         } else {
            isNextGameStageDirectedly = false;
         }

         var4 = true;
         return var4;
      default:
         var4 = var5;
      }

      if (var4) {
         ++loadStep;
      }

      var4 = false;
      return var4;
   }

   public static void normalHighScoreInit() {
      movingRow = 0;
      movingCount = GameTime.set(StageManager.class, "movingCount", 0);

      for(int var0 = 0; var0 < rankingOffsetX.length; ++var0) {
         rankingOffsetX[var0] = SCREEN_WIDTH;
      }

   }

   public static void resetGameRecord() {
      stageId = 0;

      int var0;
      for(var0 = 0; var0 < 4; ++var0) {
         stageIDArray[var0] = 0;
      }

      normalStageId = 0;

      for(var0 = 0; var0 < 4; ++var0) {
         normalStageIDArray[var0] = 0;
      }

      PlayerObject.setScore(0);
      PlayerObject.setLife(2);
      openedStageId = 0;

      for(var0 = 0; var0 < 4; ++var0) {
         openedStageIDArray[var0] = 0;
      }

      PlayerObject.resetGameParam();

      for(var0 = 0; var0 < timeModeScore.length; ++var0) {
         timeModeScore[var0] = 599999;
      }

      startStageID = 0;

      for(var0 = 0; var0 < 4; ++var0) {
         startStageIDArray[var0] = 0;
      }

      characterFromGame = -1;
      stageIDFromGame = -1;
      PlayerObject.setScore(0);
      PlayerObject.setLife(2);
      SpecialStageState.emptyEmeraldArray();
      GlobalResource.initSystemConfig();
      saveStageRecord();
   }

   public static void resetOpenedStageIdforTry(int var0) {
      openedStageIDArray[PlayerObject.getCharacterID()] = var0;
      stageIDArray[PlayerObject.getCharacterID()] = 0;
      normalStageIDArray[PlayerObject.getCharacterID()] = 0;
      saveStageRecord();
   }

   public static void resetStageGameover() {
      stageGameoverFlag = false;
   }

   public static void resetStageId() {
      if (openedStageIDArray[PlayerObject.getCharacterID()] < stageIDArray[PlayerObject.getCharacterID()]) {
         openedStageIDArray[PlayerObject.getCharacterID()] = stageIDArray[PlayerObject.getCharacterID()];
      }

      stageIDArray[PlayerObject.getCharacterID()] = 0;
      normalStageIDArray[PlayerObject.getCharacterID()] = 0;
      saveStageRecord();
   }

   public static void resetStageIdforContinueEnd() {
      characterFromGame = -1;
      stageIDFromGame = -1;
      PlayerObject.setScore(0);
      PlayerObject.setLife(2);
      saveStageRecord();
   }

   public static void resetStageIdforTry() {
      openedStageIDArray[PlayerObject.getCharacterID()] = 0;
      stageIDArray[PlayerObject.getCharacterID()] = 0;
      normalStageIDArray[PlayerObject.getCharacterID()] = 0;
      saveStageRecord();
   }

   public static void saveCheckPoint(int var0, int var1) {
      checkPointX = var0 >> 6;
      checkPointY = var1 >> 6;
      checkPointEnable = true;
      checkPointTime = PlayerObject.timeCount;
   }

   public static void saveCheckPointCamera(int var0, int var1, int var2, int var3) {
      checkCameraUpX = var0;
      checkCameraDownX = var1;
      checkCameraLeftX = var2;
      checkCameraRightX = var3;
      checkCameraEnable = true;
   }

   public static void saveHighScoreRecord() {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();
      DataOutputStream var1 = new DataOutputStream(var2);
      int var0 = 0;

      while(true) {
         boolean var8 = false;

         label87: {
            label86: {
               try {
                  var8 = true;
                  if (var0 < timeModeScore.length) {
                     var1.writeInt(timeModeScore[var0]);
                     var8 = false;
                     break label87;
                  }

                  Record.saveRecordStream("SONIC_HIGHSCORE_RECORD", var2);
                  var8 = false;
               } catch (Exception var12) {
                  var8 = false;
                  break label86;
               } finally {
                  if (var8) {
                     try {
                        var1.close();
                     } catch (IOException var9) {
                        var9.printStackTrace();
                     }

                  }
               }

               try {
                  var1.close();
               } catch (IOException var11) {
                  var11.printStackTrace();
               }
               break;
            }

            try {
               var1.close();
            } catch (IOException var10) {
               var10.printStackTrace();
            }
            break;
         }

         ++var0;
      }

   }

   public static void saveSpecialStagePoint(int var0, int var1) {
      specialStagePointX = var0 >> 6;
      specialStagePointY = var1 >> 6;
   }

   public static void setOnlyScoreCal() {
      isOnlyScoreCal = true;
      isOnlyStagePass = false;
      PlayerObject.isbarOut = true;
   }

   public static void setStageGameover() {
      if (!stageGameoverFlag) {
         stageGameoverFlag = true;
         stageGameoverCount = GameTime.set(StageManager.class, "stageGameoverCount", 10);
      }

   }

   public static void setStageID(int var0) {
      stageIDArray[PlayerObject.getCharacterID()] = var0;
   }

   public static void setStagePass() {
      if (!stagePassFlag) {
         stagePassFlag = true;
         stagePassCount = GameTime.set(StageManager.class, "stagePassCount", 0);
      }

   }

   public static void setStageRestart() {
      if (!stageRestartFlag) {
         stageRestartFlag = true;
         stageRestartCount = GameTime.set(StageManager.class, "stageRestartCount", 10);
      }

   }

   public static void setStageTimeover() {
      if (!stageTimeoverFlag) {
         stageTimeoverFlag = true;
         stageTimeoverCount = GameTime.set(StageManager.class, "stageTimeoverCount", 0);
      }

   }

   public static void setStartStageID(int var0) {
      startStageIDArray[PlayerObject.getCharacterID()] = var0;
   }

   public static void setStraightlyPass() {
      isOnlyStagePass = true;
   }

   public static void setTimeModeScore(int var0, int var1) {
      int var2;
      int var3;
      int var4;
      int[] var5;
      if (var1 <= timeModeScore[STAGE_NUM * 3 * var0 + stageIDArray[var0] * 3]) {
         var5 = timeModeScore;
         var3 = STAGE_NUM;
         var2 = stageIDArray[var0];
         var4 = timeModeScore[STAGE_NUM * 3 * var0 + stageIDArray[var0] * 3 + 1];
         var5[var3 * 3 * var0 + var2 * 3 + 2] = var4;
         var5 = timeModeScore;
         var2 = STAGE_NUM;
         var4 = stageIDArray[var0];
         var3 = timeModeScore[STAGE_NUM * 3 * var0 + stageIDArray[var0] * 3];
         var5[var2 * 3 * var0 + var4 * 3 + 1] = var3;
         timeModeScore[STAGE_NUM * 3 * var0 + stageIDArray[var0] * 3] = var1;
      } else if (var1 <= timeModeScore[STAGE_NUM * 3 * var0 + stageIDArray[var0] * 3 + 1]) {
         var5 = timeModeScore;
         var2 = STAGE_NUM;
         var3 = stageIDArray[var0];
         var4 = timeModeScore[STAGE_NUM * 3 * var0 + stageIDArray[var0] * 3 + 1];
         var5[var2 * 3 * var0 + var3 * 3 + 2] = var4;
         timeModeScore[STAGE_NUM * 3 * var0 + stageIDArray[var0] * 3 + 1] = var1;
      } else if (var1 <= timeModeScore[STAGE_NUM * 3 * var0 + stageIDArray[var0] * 3 + 2]) {
         timeModeScore[STAGE_NUM * 3 * var0 + stageIDArray[var0] * 3 + 2] = var1;
      }

   }

   public static void setTimeModeScore(int[] var0) {
      for(int var1 = 0; var1 < timeModeScore.length; ++var1) {
         timeModeScore[var1] = var0[var1];
      }

   }

   public static void setWaterLevel(int var0) {
      waterLevel = var0;
   }

   public static void stageLogic() {
      if (stagePassFlag && stagePassCount > 0) {
         stagePassCount = GameTime.advance(StageManager.class, "stagePassCount", stagePassCount, -(1));
      }

      if (stageRestartFlag && stageRestartCount > 0) {
         stageRestartCount = GameTime.advance(StageManager.class, "stageRestartCount", stageRestartCount, -(1));
      }

      if (stageGameoverFlag && stageGameoverCount > 0) {
         stageGameoverCount = GameTime.advance(StageManager.class, "stageGameoverCount", stageGameoverCount, -(1));
      }

      if (stageTimeoverFlag && stageTimeoverCount > 0) {
         stageTimeoverCount = GameTime.advance(StageManager.class, "stageTimeoverCount", stageTimeoverCount, -(1));
      }

   }

   public static void stagePassInit() {
      isOnlyScoreCal = false;
      isOnlyStagePass = false;
      isScoreBarOutOfScreen = false;
      PlayerObject.isbarOut = false;
   }
}
