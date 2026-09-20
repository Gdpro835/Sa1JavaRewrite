package PlatformStandard;

import GameEngine.Def;
import SonicGBA.MapManager;
import com.sega.mobile.framework.device.MFGraphics;

public class Standard {
    public static final int GAME_STAR_NUM = 3;
    private static final int IMG_BG = 3;
    private static final int IMG_CMCC = 0;
    private static final int IMG_CMCC_DESCREPTION_WORD_0 = 4;
    private static final int IMG_CMCC_DESCREPTION_WORD_1 = 5;
    private static final int IMG_CP = 2;
    private static final int IMG_ROLLING_EFFECT_0 = 6;
    private static final int IMG_ROLLING_EFFECT_1 = 7;
    private static final int IMG_ROLLING_EFFECT_2 = 8;
    private static final int IMG_SP = 1;
    public static final boolean MENU_MORE_GAME = true;
    public static final int MORE_GAME_LOAD = 0;
    public static final int MORE_GAME_SHOW = 1;
    public static final int ROLL_CMCC_DESCREPTION = 0;
    public static final int ROLL_END = 2;
    private static final int ROLL_FAST_DIFF = 800;
    private static final int ROLL_GRID_WIDTH = 52;
    public static final int ROLL_LOGO = 1;
    private static final int ROLL_RANGE = 2;
    private static final int ROLL_RANGE_DES = 5;
    private static final int ROLL_SLOW_DIFF = 200;
    private static final int ROLL_TIME_CMCC = 1000;
    private static final int ROLL_TIME_CMCC_DES = 100;
    private static final int ROLL_TIME_CP = 1500;
    private static final int ROLL_TIME_SP = 2000;
    public static final int R_MOREGAME_EXIT = 0;
    public static final int R_MOREGAME_WAIT = 1;
    public static final int R_SPLASH_END = 3;
    public static final int R_SPLASH_IS_EMU = 4;
    public static final int R_SPLASH_SOUND_DISABLE = 2;
    public static final int R_SPLASH_SOUND_ENABLE = 1;
    public static final int R_SPLASH_WAIT = 0;
    private static final int SHOW_LOGO_TIME = 3000;
    private static final int SHOW_STAR_TIME = 2000;
    protected static final int SOFT_EXIT = 4;
    protected static final int SOFT_KEY_HEIGHT = 17;
    protected static final int SOFT_NO = 2;
    protected static final int SOFT_NULL = 0;
    protected static final int SOFT_OK = 3;
    protected static final int SOFT_YES = 1;
    private static final int SPLASH_EVALUATE = 1;
    public static final int SPLASH_IS_EMU = 4;
    private static final int SPLASH_LOAD = 0;
    private static final int SPLASH_ROLL = 3;
    private static final int SPLASH_SOUND = 2;
    public static final String SRT_MORE_GAME_URL = "http://go.i139.int/gcomm1/portal/spchannel.do?url=http://gamepie.i139.int/wap/s.do?j=3channel";
    private static final int STAR_WIDTH;
    public static final String STR_MORE_GAME = "更多精彩游戏|尽在游戏频道||wap.xjoys.com";
    protected static boolean keyCancel;
    protected static boolean keyConfirm;
    private static long lastTime;
    private static int loadCount;
    public static int moreGameState = 0;
    private static long nowTime;
    private static boolean openMoreGame = false;
    private static boolean pause = false;
    private static boolean playSound;
    private static int rollCmccDesPos;
    private static int[] rollEffectCount = new int[3];
    private static boolean rollEffectFlag = false;
    private static int[] rollLogoImgIndex;
    private static int rollState;
    private static int[] softValue = new int[2];
    private static int splashState = 0;
    private static int timeCount;
    private static long timePeriod;

    static {
        int i;
        if (Def.SCREEN_WIDTH >= 240) {
            i = 25;
        } else {
            i = 20;
        }
        STAR_WIDTH = i;
        int[] iArr = new int[3];
        iArr[0] = 1;
        iArr[2] = 2;
        rollLogoImgIndex = iArr;
    }

    public static void pressConfirm() {
        keyConfirm = true;
        Standard2.pressConfirm();
    }

    public static void pressCancel() {
        keyCancel = true;
        Standard2.pressCancel();
    }

    public static void pause() {
        pause = true;
    }

    public static void resume() {
        pause = false;
        nowTime = System.currentTimeMillis();
        lastTime = nowTime;
        timePeriod = 0;
    }

    public static void menuMoreGameSelected() {
        openMoreGame = true;
    }

    protected static void setSoftValue(int lValue, int rValue) {
        softValue[0] = lValue;
        softValue[1] = rValue;
    }

    private static void drawSoft(MFGraphics g, int SCREEN_W, int SCREEN_H) {
    }

    public static int execSplash() {
        return Standard2.splashLogic();
    }

    public static void drawSplash(MFGraphics g, int SCREEN_W, int SCREEN_H) {
        Standard2.splashDraw(g, SCREEN_W, SCREEN_H);
    }

    public static int execMoreGame(boolean openWapAvailable) {
        return 0;
    }

    public static void drawMoreGame(MFGraphics g, int SCREEN_W, int SCREEN_H) {
    }

    protected static void loadBg() {
    }

    protected static void freeBg() {
    }

    protected static void releaseKey() {
        keyConfirm = false;
        keyCancel = false;
    }

    protected static void drawBg(MFGraphics g, int SCREEN_W, int SCREEN_H) {
        g.setColor(0);
        g.fillRect(0, 0, SCREEN_W, SCREEN_H);
        drawSoft(g, SCREEN_W, SCREEN_H);
    }

    protected static void paintWrapped(MFGraphics g, String content, int SCREEN_W, int SCREEN_H) {
        String showStr;
        g.setFont(-1);
        g.setColor(MapManager.END_COLOR);
        int ry = 0;
        int x = SCREEN_W >> 1;
        int y = (SCREEN_H >> 1) - (g.charHeight() * 3);
        while (content.indexOf("|") != -1) {
            int i = content.indexOf("|");
            if (i == 0) {
                showStr = "";
                content = content.substring(1);
            } else {
                showStr = content.substring(0, i);
                content = content.substring(i + 1);
            }
            g.drawString(showStr, x, (g.charHeight() * ry) + y, 17);
            ry++;
        }
        g.drawString(content, x, (g.charHeight() * ry) + y, 17);
    }
}
