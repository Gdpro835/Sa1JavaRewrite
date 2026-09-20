package SonicGBA;

import com.sega.mobile.framework.device.MFGraphics;

public abstract class BackGroundManager implements SonicDef {
   public static final int DRAW_HEIGHT;
   public static final int DRAW_WIDTH;
   public static final int DRAW_X;
   public static final int DRAW_Y;
   public static final String MAP_FILE_PATH = "/map";
   public static int frame;
   private static BackGroundManager frontNatural;
   private static BackGroundManager instance;
   public static int stageId;

   static {
      DRAW_X = MapManager.CAMERA_OFFSET_X;
      DRAW_Y = MapManager.CAMERA_OFFSET_Y;
      DRAW_WIDTH = MapManager.CAMERA_WIDTH;
      DRAW_HEIGHT = MapManager.CAMERA_HEIGHT;
   }

   public static void drawBackGround(MFGraphics var0) {
      if (instance != null) {
         instance.draw(var0);
      }

   }

   public static void drawFrontNatural(MFGraphics var0) {
      if (frontNatural != null) {
         frontNatural.draw(var0);
      }

   }

   public static void init(int var0) {
      if (stageId != var0) {
         if (instance != null) {
            instance.close();
            instance = null;
         }

         if (frontNatural != null) {
            frontNatural.close();
            frontNatural = null;
         }
      }

      stageId = var0;
      switch(stageId) {
      case 0:
      case 1:
         instance = new BackManagerStage1();
         break;
      case 2:
      case 3:
         instance = new BackManagerStage2();
         if (stageId == 2) {
            frontNatural = new FrontManagerStage2_1();
         }
         break;
      case 4:
      case 5:
         instance = new BackManagerStage3();
         break;
      case 6:
      case 7:
         frontNatural = new SnowStage4();
         instance = new BackManagerStage4(stageId - 6);
         break;
      case 8:
      case 9:
         instance = new BackManagerStage5();
         break;
      case 10:
         instance = new BackManagerStage61();
         break;
      case 11:
         instance = new BackManagerStage62();
         break;
      case 12:
         instance = new BackManagerStageFinal();
         break;
      case 13:
         instance = new BackManagerStageExtra();
      }

   }

   public static void next() {
      if (instance != null) {
         instance.nextState();
      }

      if (frontNatural != null) {
         frontNatural.nextState();
      }

   }

   public abstract void close();

   public abstract void draw(MFGraphics var1);

   public void nextState() {
   }
}
