package SonicGBA;

import Lib.Animation;
import Lib.Record;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Locale;

public class GlobalResource {
   public static final int DEFAULT_SOUND_VOL = 9;
   public static int difficultyConfig;
   public static int fixedScreenConfig;
   public static int languageConfig;
   public static int loadingTipsConfig;
   public static int seConfig;
   public static int sensorConfig;
   public static int soundConfig;
   public static int soundSwitchConfig;
   public static int spsetConfig;
   public static Animation statusAnimation;
   public static int timeLimit;
   public static int touchKeyBoardOpacity;
   public static int touchKeyBoardLeftPosition;
   public static int touchKeyBoardRightPosition;
   public static int touchKeyBoardSize;
   public static int vibrationConfig;
   //public static int fps60;

   static {
      String var0 = Locale.getDefault().getLanguage();
      if (var0.equals("fr")) {
         languageConfig = 1;
      } else if (var0.equals("es")) {
         languageConfig = 2;
      } else if (var0.equals("pt")) {
         languageConfig = 3;
      } else if (var0.equals("de")) {
         languageConfig = 4;
      } else if (var0.equals("it")) {
         languageConfig = 5;
      } else if (var0.equals("ru")) {
         languageConfig = 6;
      } else if (var0.equals("ja")) {
         languageConfig = 7;
      } else if (var0.equals("zh")) {
         languageConfig = 8;
      } else {
         languageConfig = 0;
      }

      initSystemConfig();
   }

   public static void initSystemConfig() {
      seConfig = 1;
      soundSwitchConfig = 1;
      difficultyConfig = 1;
      timeLimit = 0;
      vibrationConfig = 1;
      spsetConfig = 0;
      loadingTipsConfig = 0;
      sensorConfig = 1;
      touchKeyBoardOpacity = 0;
      touchKeyBoardLeftPosition = 0;
      touchKeyBoardRightPosition = 0;
      touchKeyBoardSize = 5;
      fixedScreenConfig = 0;
      //fps60 = 0;
   }

   public static boolean isEasyMode() {
      boolean var0;
      if (difficultyConfig == 0) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static void loadSystemConfig() {
      ByteArrayInputStream var0 = Record.loadRecordStream("SONIC_SYSTEM_RECORD");

      try {
         DataInputStream var1 = new DataInputStream(var0);
         languageConfig = var1.readByte();
         difficultyConfig = var1.readByte();
         timeLimit = var1.readByte();
         seConfig = var1.readByte();
         vibrationConfig = var1.readByte();
         spsetConfig = var1.readByte();
         loadingTipsConfig = var1.readByte();
         sensorConfig = var1.readByte();
         touchKeyBoardOpacity = var1.readByte();
         touchKeyBoardLeftPosition = var1.readByte();
         touchKeyBoardRightPosition = var1.readByte();
         touchKeyBoardSize = var1.readByte();
         fixedScreenConfig = var1.readByte();
         //fps60 = var1.readByte();
      } catch (Exception var8) {
         saveSystemConfig();
      } finally {
         if (var0 != null) {
            try {
               var0.close();
            } catch (IOException var7) {
               var7.printStackTrace();
            }
         }

      }

   }

   public static void saveSystemConfig() {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      DataOutputStream var0 = new DataOutputStream(var1);

      try {
         var0.writeByte(languageConfig);
         var0.writeByte(difficultyConfig);
         var0.writeByte(timeLimit);
         var0.writeByte(seConfig);
         var0.writeByte(vibrationConfig);
         var0.writeByte(spsetConfig);
         var0.writeByte(loadingTipsConfig);
         var0.writeByte(sensorConfig);
         var0.writeByte(touchKeyBoardOpacity);
         var0.writeByte(touchKeyBoardLeftPosition);
         var0.writeByte(touchKeyBoardRightPosition);
         var0.writeByte(touchKeyBoardSize);
         var0.writeByte(fixedScreenConfig);
         //var0.writeByte(fps60);
         Record.saveRecordStream("SONIC_SYSTEM_RECORD", var1);
      } catch (Exception var8) {
      } finally {
         try {
            var0.close();
         } catch (IOException var7) {
            var7.printStackTrace();
         }

      }

   }

   public static boolean timeIsLimit() {
      boolean var0;
      if (timeLimit == 0) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }
}
