package Special;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import SonicGBA.GlobalResource;
import SonicGBA.StageManager;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

public class SpecialMap implements SSDef {
    public static final int MAP_WIDTH = 304;
    public static final int CAMERA_MAX_X = Math.abs(SCREEN_WIDTH - MAP_WIDTH);
    public static final int CAMERA_MAX_Y = Math.abs(SCREEN_HEIGHT - 224);
    public static final int MAP_HEIGHT = 224;
    public static final int MAP_LENGTH = 1024;
    public static final int MAP_VELOCITY_STANDARD = 1;
    public static int cameraX;
    public static int cameraY;
    public static AnimationDrawer mapBg1Drawer;
    public static AnimationDrawer mapBg2Drawer;
    public static AnimationDrawer mapDrawer;
    public static int mapProgress;
    public static boolean showingInfo;
    public static int specialStageID;
    public static MFImage starImage;
    private static int targetRingNum;

    public static void loadMap() {
        specialStageID = STAGE_ID_TO_SPECIAL_ID[StageManager.getStageID()];
        MFImage image2 = null;
        if (specialStageID > 0) {
            image2 = MFImage.createPaletteImage("/special_res/sp_bg_" + (specialStageID + 1) + ".pal");
        }
        Animation animation = Animation.getInstanceFromQi("/special_res/sp_bg.dat")[0];
        if (image2 != null) {
            animation.setImage(image2, 0);
        }
        mapDrawer = animation.getDrawer();
        mapBg1Drawer = animation.getDrawer(16, true, 0);
        mapBg2Drawer = animation.getDrawer(17, true, 0);
        if (GlobalResource.fixedScreenConfig == 1 || SCREEN_WIDTH <= 304) {
            starImage = MFImage.createImage("/special_res/sp_star_bg.png");
        } else {
            starImage = MFImage.createImage("/special_res/sp_star_bg1.png");
        }
    }

    public static void releaseMap() {
        Animation.closeAnimationDrawer(mapDrawer);
        Animation.closeAnimationDrawer(mapBg1Drawer);
        Animation.closeAnimationDrawer(mapBg2Drawer);
        starImage = null;
    }

    public static void cameraLogic() {
        if (GlobalResource.fixedScreenConfig == 1 || SCREEN_WIDTH <= 304) {
            cameraX = (CAMERA_MAX_X * ((SpecialObject.player.posX >> 6) + 150)) / 300;
        } else {
            cameraX = CAMERA_MAX_X / 2;
        }
        cameraY = (CAMERA_MAX_Y * ((SpecialObject.player.posY >> 6) + 120)) / SSDef.PLAYER_MOVE_HEIGHT;
        mapProgress = GameTime.advance(SpecialMap.class, "mapProgress", mapProgress, (SpecialObject.player.velZ << 6) / 1);
        while (mapProgress < 0) {
            mapProgress += 1024;
        }
        mapProgress = GameTime.wrap(SpecialMap.class, "mapProgress", mapProgress, 1024);
        mapDrawer.setActionId(mapProgress >> 6);
    }

    public static void drawMap(MFGraphics g) {
        cameraLogic();
        g.setColor(0);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        int var1;
        int var2;
        if (GlobalResource.fixedScreenConfig == 1 || SCREEN_WIDTH <= 304) {
            var1 = 0;
            var2 = SCREEN_WIDTH;
        } else {
            var1 = ((SCREEN_WIDTH >> 1) - (MAP_WIDTH >> 1)) + 1;
            var2 = 304;
        }
        for (int x = var1; x < var2; x += starImage.getWidth()) {
            for (int y = 0; y < SCREEN_HEIGHT; y += starImage.getHeight()) {
                MyAPI.drawImage(g, starImage, x, y, 0);
            }
        }
        mapBg1Drawer.draw(g, ((SCREEN_WIDTH + CAMERA_MAX_X) >> 1) - cameraX, ((SCREEN_HEIGHT + CAMERA_MAX_Y) >> 1) - cameraY);
        mapBg2Drawer.draw(g, ((SCREEN_WIDTH + CAMERA_MAX_X) >> 1) - cameraX, ((SCREEN_HEIGHT + CAMERA_MAX_Y) >> 1) - cameraY);
        mapDrawer.draw(g, ((SCREEN_WIDTH + CAMERA_MAX_X) >> 1) - cameraX, ((SCREEN_HEIGHT + CAMERA_MAX_Y) >> 1) - cameraY);
    }

    public static int getCameraOffsetX() {
        return cameraX - (CAMERA_MAX_X >> 1);
    }

    public static int getCameraOffsetY() {
        return cameraY - (CAMERA_MAX_Y >> 1);
    }

    public static void drawInfomation(MFGraphics g) {
    }
}
