//
// Decompiled by FernFlower - 2495ms
//
package State;

import GameEngine.Def;
import GameEngine.Key;
import GameEngine.TouchKeyRange;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.MyRandom;
import Lib.SoundSystem;
import Common.NumberDrawer;
import PlatformStandard.Standard2;
import SonicGBA.BackGroundManager;
import SonicGBA.Effect;
import SonicGBA.EnemyObject;
import SonicGBA.GameObject;
import SonicGBA.GlobalResource;
import SonicGBA.MapManager;
import SonicGBA.PlayerObject;
import SonicGBA.RocketSeparateEffect;
import SonicGBA.StageManager;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import com.sega.mobile.platform.ChargePlatform;
import com.sega.mobile.framework.device.MFDevice;

public class GameState extends State {
    private static final byte ACTION = 4;
    private static final int BAR_COLOR = 2;
    private static final int BIRD_NUM = 10;
    private static final int BIRD_OFFSET = 2;
    private static final int BIRD_SPACE_1 = 10;
    private static final int BIRD_SPACE_2 = 14;
    private static final int BIRD_X = 0;
    private static final int BIRD_Y = 1;
    private static final int[] BP_ITEMS_HIGH;
    private static final int[] BP_ITEMS_HIGH_NORMAL_NUM;
    private static final int[] BP_ITEMS_LOW;
    private static final int[] BP_ITEMS_LOW_NORMAL_NUM;
    private static final int CLOUD_NUM = 10;
    private static final int CLOUD_TYPE = 0;
    private static final int[] CLOUD_VELOCITY;
    private static final int CLOUD_X = 1;
    private static final int CLOUD_Y = 2;
    private static final int[] COLOR_SEQ;
    private static final int DEGREE_VELOCITY = 10;
    private static final byte ED_END = 5;
    private static final byte ED_STATE_BIRD_APPEAR = 2;
    private static final byte ED_STATE_CONGRATULATION = 3;
    private static final byte ED_STATE_NO_PLANE = 0;
    private static final byte ED_STATE_PLANE_APPEAR = 1;
    private static final byte ED_STATE_STAFF = 4;
    private static final String ENDING_ANIMATION_PATH = "/ending";
    private static final int FLOAT_RANGE = 10;
    public static boolean IsSingleDown = false;
    public static boolean IsSingleUp = false;
    private static boolean IsSoundVolSet;
    private static final int LOADING_TIME_LIMIT = 10;
    private static final byte NUM = 6;
    private static final int OPTION_ELEMENT_NUM;
    private static final int OPTION_MOVING_INTERVAL = 100;
    private static final int OPTION_MOVING_SPEED = 4;
    private static final int[] OPTION_SE;
    private static final int[][] OPTION_SELECTOR;
    private static final int[][] OPTION_SELECTOR_HAS_SE;
    private static final int[][] OPTION_SELECTOR_NO_SE;
    private static final int[] OPTION_SOUND;
    private static final int[] OPTION_SOUND_NO_VOLUME;
    private static final int[] OPTION_SOUND_VOLUME;
    private static final int[] OPTION_TAG;
    private static final int[] OPTION_TAG_HAS_SE;
    private static final int[] OPTION_TAG_NO_SE;
    private static final int PAUSE_NORMAL_INSTRUCTION = 4;
    private static int[] PAUSE_NORMAL_MODE;
    private static final int[] PAUSE_NORMAL_MODE_NOSHOP;
    private static final int[] PAUSE_NORMAL_MODE_SHOP;
    private static final int PAUSE_NORMAL_OPTION = 3;
    private static final int PAUSE_NORMAL_RESUME = 0;
    private static final int PAUSE_NORMAL_SHOP = 2;
    private static final int PAUSE_NORMAL_TO_TITLE = 1;
    private static final int PAUSE_OPTION_ITEMS_NUM = 6;
    private static final int PAUSE_RACE_INSTRUCTION = 5;
    private static final int PAUSE_RACE_OPTION = 4;
    private static final int PAUSE_RACE_RESUME = 0;
    private static final int PAUSE_RACE_RETRY = 1;
    private static final int PAUSE_RACE_SELECT_STAGE = 2;
    private static final int PAUSE_RACE_TO_TITLE = 3;
    private static final int PLANE_VELOCITY = 2;
    private static final byte POSX = 0;
    private static final byte POSY = 1;
    private static final byte PRESS_DELAY = 5;
    public static boolean PreGame;
    private static final byte SAW = 0;
    private static final byte SONIC = 3;
    private static final int STAFF_CENTER_Y;
    private static final int STAFF_SHOW_TIME = 45;
    private static String[] STAFF_STR;
    private static final byte STAGE_NAME = 2;
    private static final byte STAGE_NAME_S = 5;
    private static final byte STATE_ALL_CLEAR = 14;
    private static final byte STATE_BP_BUY = 23;
    private static final byte STATE_BP_CONTINUE_TRY = 19;
    private static final byte STATE_BP_REVIVE = 21;
    private static final byte STATE_BP_SHOP = 22;
    private static final byte STATE_BP_TOOLS_MAX = 24;
    private static final byte STATE_BP_TOOLS_USE = 25;
    private static final byte STATE_BP_TOOLS_USE_ENSURE = 26;
    private static final byte STATE_BP_TRY_PAYING = 20;
    private static final byte STATE_CONTINUE_0 = 41;
    private static final byte STATE_CONTINUE_1 = 42;
    private static final byte STATE_GAME = 0;
    private static final byte STATE_GAME_OVER_1 = 12;
    private static final byte STATE_GAME_OVER_2 = 13;
    private static final byte STATE_GAME_OVER_PRE = 28;
    private static final byte STATE_INTERRUPT = 18;
    private static final byte STATE_PAUSE = 1;
    private static final byte STATE_PAUSE_INSTRUCTION = 6;
    private static final byte STATE_PAUSE_OPTION = 7;
    private static final byte STATE_PAUSE_OPTION_HELP = 34;
    private static final byte STATE_PAUSE_OPTION_KEY_CONTROL = 32;
    private static final byte STATE_PAUSE_OPTION_SENSOR = 39;
    private static final byte STATE_PAUSE_OPTION_SOUND = 30;
    private static final byte STATE_PAUSE_OPTION_SOUND_VOLUMN = 38;
    private static final byte STATE_PAUSE_OPTION_SP_SET = 33;
    private static final byte STATE_PAUSE_OPTION_VIB = 31;
    private static final byte STATE_PAUSE_RETRY = 8;
    private static final byte STATE_PAUSE_SELECT_CHARACTER = 29;
    private static final byte STATE_PAUSE_SELECT_STAGE = 9;
    private static final byte STATE_PAUSE_TO_TITLE = 10;
    private static final byte STATE_PRE_ALL_CLEAR = 40;
    private static final byte STATE_PRE_GAME_0 = 36;
    private static final byte STATE_PRE_GAME_0_TYPE2 = 37;
    private static final byte STATE_PRE_GAME_1 = 15;
    private static final byte STATE_PRE_GAME_1_TYPE2 = 27;
    private static final byte STATE_PRE_GAME_2 = 16;
    private static final byte STATE_PRE_GAME_3 = 17;
    private static final byte STATE_SET_PARAM = 3;
    private static final byte STATE_STAGE_LOADING = 5;
    private static final byte STATE_STAGE_LOADING_TURN = 35;
    public static final byte STATE_STAGE_PASS = 4;
    private static final byte STATE_STAGE_SELECT = 2;
    private static final byte STATE_TIME_OVER_0 = 11;
    private static final boolean USE_NARRAW_TEXT_TIPS = false;
    private static final int VISIBLE_OPTION_ITEMS_NUM = 9;
    private static final byte VX = 2;
    private static final byte VY = 3;
    private static final byte WHITE_BAR = 1;
    private static int[] currentBPItems;
    private static int[] currentBPItemsNormalNum;
    public static AnimationDrawer guiAniDrawer;
    public static Animation guiAnimation;
    public static boolean isBackFromSpStage;
    public static boolean isThroughGame;
    private static AnimationDrawer numberDrawer;
    private static int spCheckPointID;
    private static int spReserveRingNum;
    private static int spTimeCount;
    private static String[][] staffStringForShow;
    public static AnimationDrawer stageInfoAniDrawer;
    private static String[] tipsForShow;
    private static String[] tipsString;
    private static int tool_x;
    private static int tool_y;
    int BP_CONTINUETRY_MENU_HEIGHT;
    int BP_CONTINUETRY_MENU_START_X;
    int BP_CONTINUETRY_MENU_START_Y;
    int BP_CONTINUETRY_MENU_WIDTH;
    private boolean BP_IsFromContinueTry;
    private boolean IsActNumDrawable;
    private boolean IsPlayerNameDrawable;
    private String[] TIPS;
    private int TIPS_OFFSET_X;
    private int TIPS_TEXT_INTERVAL_Y;
    private int TIPS_TEXT_OFFSET_Y;
    private int TIPS_TITLE_OFFSET_Y;
    private int allclearFrame;
    public int arrowindex = -1;
    private AnimationDrawer[] birdDrawer;
    private int[][] birdInfo;
    private int birdX;
    private boolean changing;
    private int cloudCount;
    private AnimationDrawer cloudDrawer;
    private int[][] cloudInfo;
    private int cnt;
    private int colorCursor;
    private int continueCursor;
    private int continueFrame;
    private int continueMoveBlackBarX;
    private int continueMoveNumberX;
    private int continueNumber;
    private float continueNumberScale;
    private int continueNumberState;
    private float continueScale;
    private int continueStartEndFrame;
    private int count;
    private int degree;
    private int[][] display;
    private byte endingState;
    private MFImage exendBg1Image;
    private MFImage exendBgImage;
    private boolean fadeChangeState;
    private int frameCount;
    private int gameoverCnt;
    private AnimationDrawer interruptDrawer;
    private int interrupt_state;
    private boolean isChanged;
    public static boolean isLoadingSkipped;
    private boolean isOptionChange;
    private boolean isOptionDisFlag;
    private boolean isSelectable;
    private boolean isStateClassSwitch;
    private int itemOffsetX;
    public static AnimationDrawer leftArrowDrawer;
    private AnimationDrawer loadingDrawer;
    public static long loadingStartTime;
    private AnimationDrawer loadingWordsDrawer;
    private int movingTitleSpeedX;
    private int movingTitleX;
    public static AnimationDrawer muiLeftArrowDrawer;
    public static AnimationDrawer muiRightArrowDrawer;
    private int nextState;
    private int offsetOfVolumeInterface;
    private int opengingCursor;
    private int[] optionCursor;
    private int optionDrawOffsetBottomY;
    private int optionDrawOffsetTmpY1;
    private int optionDrawOffsetY;
    private int optionIndex;
    private int optionMenuCursor;
    private int optionOffsetTmp;
    private int optionOffsetX;
    private int optionOffsetY;
    private int optionOffsetYAim;
    private boolean optionReturnFlag;
    private int optionYDirect;
    private int optionslide_getprey;
    private int optionslide_gety;
    private int optionslide_y;
    private int optionslidefirsty;
    private boolean outing;
    public int overcnt;
    private int overtitleID;
    public static boolean pauseAvailable;
    private int pauseOptionCursor;
    private int pause_item_cursor;
    private int pause_item_speed;
    private int pause_item_x;
    private int pause_item_y;
    private boolean pause_optionFlag;
    private int pause_optionframe;
    private boolean pause_returnFlag;
    private int pause_returnframe;
    private int pause_saw_speed;
    private int pause_saw_x;
    private int pause_saw_y;
    private int pausecnt;
    private AnimationDrawer planeDrawer;
    private int planeX;
    private int planeY;
    private int position;
    private int preScore;
    private byte pressDelay;
    private int racemode_cnt;
    public static AnimationDrawer rightArrowDrawer;
    private int showCount;
    private AnimationDrawer skipDrawer;
    private AnimationDrawer stageInfoActNumDrawer;
    private Animation[] stageInfoClearAni;
    private AnimationDrawer stageInfoPlayerNameDrawer;
    public static int state;
    private int stateForSet;
    private int stringCursor;

    static {
        int[] var0 = new int[]{0, 1, 2, 3, 4};
        PAUSE_NORMAL_MODE_SHOP = var0;
        var0 = new int[]{0, 1, 3, 4};
        PAUSE_NORMAL_MODE_NOSHOP = var0;
        isThroughGame = false;
        OPTION_SOUND_VOLUME = new int[]{55, 58, 58, 58, 57, 57, 57, 57, 56, 56, 56};
        OPTION_SOUND_NO_VOLUME = new int[]{55, 144};
        OPTION_SOUND = OPTION_SOUND_VOLUME;
        OPTION_SE = new int[]{55, 144};
        int[] var1 = OPTION_SOUND;
        var0 = OPTION_SE;
        OPTION_SELECTOR_HAS_SE = new int[][]{var1, var0};
        var0 = OPTION_SOUND;
        OPTION_SELECTOR_NO_SE = new int[][]{var0};
        OPTION_SELECTOR = OPTION_SELECTOR_HAS_SE;
        OPTION_TAG_HAS_SE = new int[]{54, 143, 0};
        OPTION_TAG_NO_SE = new int[]{54};
        OPTION_TAG = OPTION_TAG_HAS_SE;
        OPTION_ELEMENT_NUM = OPTION_TAG.length;
        IsSoundVolSet = false;
        CLOUD_VELOCITY = new int[]{5, 4, 3};
        STAFF_STR = null;
        staffStringForShow = null;
        COLOR_SEQ = new int[]{16777024, 16756912, 11599680, 16756800};
        STAFF_CENTER_Y = SCREEN_HEIGHT * 2 / 7;
        var0 = new int[]{0, 1, 3};
        BP_ITEMS_HIGH = var0;
        var0 = new int[]{0, 2, 3};
        BP_ITEMS_LOW = var0;
        currentBPItems = BP_ITEMS_HIGH;
        BP_ITEMS_HIGH_NORMAL_NUM = new int[]{5, 3, 5};
        BP_ITEMS_LOW_NORMAL_NUM = new int[]{5, 5, 5};
        currentBPItemsNormalNum = BP_ITEMS_HIGH_NORMAL_NUM;
        tool_x = 40;
        tool_y = 17;
    }

    public GameState() {
        this.state = 2;
        this.opengingCursor = -1;
        this.overtitleID = 0;
        this.movingTitleX = 0;
        this.movingTitleSpeedX = 0;
        this.display = new int[6][4];
        this.TIPS = null;
        this.loadingStartTime = 0L;
        this.TIPS_OFFSET_X = 16;
        this.TIPS_TITLE_OFFSET_Y = 0;
        this.TIPS_TEXT_OFFSET_Y = FONT_H - 0 - 0;
        this.TIPS_TEXT_INTERVAL_Y = FONT_H - 0 - 0;
        this.racemode_cnt = 60;
        this.optionCursor = new int[OPTION_ELEMENT_NUM];
        this.isOptionDisFlag = false;
        this.optionslide_getprey = -1;
        this.optionslide_gety = -1;
        this.offsetOfVolumeInterface = 0;
        this.pressDelay = 5;
        this.cloudInfo = new int[10][3];
        this.cloudCount = 0;
        this.birdInfo = new int[10][3];
        this.BP_IsFromContinueTry = false;
        this.BP_CONTINUETRY_MENU_START_X = FRAME_X;
        this.BP_CONTINUETRY_MENU_START_Y = this.MORE_GAME_START_Y;
        this.BP_CONTINUETRY_MENU_WIDTH = FRAME_WIDTH;
        this.BP_CONTINUETRY_MENU_HEIGHT = this.MORE_GAME_HEIGHT;
        this.overcnt = 0;
        this.IsPlayerNameDrawable = false;
        this.IsActNumDrawable = false;
        this.state = 5;
        loadingType = 0;
        isLoadingSkipped = false;
        fadeInit(255, 0);
        fading = false;
        isBackFromSpStage = false;
        this.initTips();
        this.pauseAvailable = true;
    }

    public GameState(int var1) {
        this();
        this.opengingCursor = -1;
        switch (var1) {
            case 7:
                isBackFromSpStage = true;
                isThroughGame = true;
            default:
        }
    }

    private boolean BP_IsToolsNumMax() {
        boolean var1;
        if (BP_items_num[currentBPItems[PlayerObject.cursor]] + currentBPItemsNormalNum[PlayerObject.cursor] > 99) {
            this.state = 24;
            var1 = true;
        } else {
            var1 = false;
        }

        return var1;
    }

    public static void BP_toolsAdd() {
        byte[] var1 = BP_items_num;
        int var0 = currentBPItems[tool_id];
        var1[var0] = (byte)(var1[var0] + currentBPItemsNormalNum[tool_id]);
        saveBPRecord();
    }

    private void birdDraw1(MFGraphics var1) {
        for(int var2 = 0; var2 < 5; ++var2) {
            AnimationDrawer var7 = this.birdDrawer[var2];
            int var5 = this.birdX;
            int var3 = this.birdInfo[var2][0];
            int var4 = this.birdInfo[var2][1];
            int var6 = this.getOffsetY(this.birdInfo[var2][2]);
            var7.draw(var1, var5 + var3, var4 + var6);
        }

    }

    private void birdDraw2(MFGraphics var1) {
        for(int var2 = 5; var2 < 10; ++var2) {
            AnimationDrawer var7 = this.birdDrawer[var2];
            int var5 = this.birdX;
            int var6 = this.birdInfo[var2][0];
            int var4 = this.birdInfo[var2][1];
            int var3 = this.getOffsetY(this.birdInfo[var2][2]);
            var7.draw(var1, var5 + var6, var4 + var3);
        }

    }

    private void birdInit() {
        int var1;
        for(var1 = 4; var1 >= 0; --var1) {
            this.birdInfo[var1][1] = this.planeY - (5 - var1) * 10 - 30;
            this.birdInfo[var1][0] = (5 - var1) * 10;
            this.birdInfo[var1][2] = MyRandom.nextInt(360);
        }

        for(var1 = 5; var1 < 10; ++var1) {
            this.birdInfo[var1][1] = this.planeY - (4 - var1) * 14 - 15;
            this.birdInfo[var1][0] = (4 - var1) * -14;
            this.birdInfo[var1][2] = MyRandom.nextInt(360);
        }

        this.birdX = SCREEN_WIDTH + 30;
    }

    private boolean birdLogic() {
        this.birdX -= 2;
        boolean var1;
        if (this.birdX < (SCREEN_WIDTH >> 1) + 30) {
            this.birdX = (SCREEN_WIDTH >> 1) + 30;
            var1 = true;
        } else {
            var1 = false;
        }

        return var1;
    }

    private void cloudDraw(MFGraphics var1) {
        for(int var2 = 0; var2 < 10; ++var2) {
            if (this.cloudInfo[var2][0] != 0) {
                this.cloudDrawer.setActionId(this.cloudInfo[var2][0] - 1);
                this.cloudDrawer.draw(var1, this.cloudInfo[var2][1], this.cloudInfo[var2][2]);
            }
        }

    }

    private void cloudLogic() {
        if (this.cloudCount > 0) {
            --this.cloudCount;
        }

        for(int var1 = 0; var1 < 10; ++var1) {
            if (this.cloudInfo[var1][0] != 0) {
                int[] var2 = this.cloudInfo[var1];
                var2[1] += CLOUD_VELOCITY[this.cloudInfo[var1][0] - 1];
                if (this.cloudInfo[var1][1] >= SCREEN_WIDTH + 75) {
                    this.cloudInfo[var1][0] = 0;
                }
            }

            if (this.cloudInfo[var1][0] == 0 && this.cloudCount == 0) {
                this.cloudInfo[var1][0] = MyRandom.nextInt(1, 3);
                this.cloudInfo[var1][1] = 0;
                this.cloudInfo[var1][2] = MyRandom.nextInt(20, SCREEN_HEIGHT - 40);
                this.cloudCount = MyRandom.nextInt(8, 20);
            }
        }

    }

    private void continueEnd() {
        this.continueNumberState = 3;
        this.continueStartEndFrame = this.continueFrame;
        StageManager.resetStageIdforContinueEnd();
        Key.touchgameoverensurekeyClose();
        isThroughGame = false;
    }

    private void continueInit() {
        this.state = 42;
        this.continueFrame = 0;
        this.continueScale = 1.0F;
        this.continueMoveBlackBarX = -SCREEN_WIDTH;
        this.continueMoveNumberX = -30;
        this.continueNumber = 9;
        this.continueNumberState = 0;
        this.continueNumberScale = 1.0F;
        this.continueCursor = -1;
        GlobalResource.touchKeyBoardSize = 5;
        releaseTouchkeyBoard();
        initTouchkeyBoard();
        Key.touchgameoverensurekeyInit();
        if (guiAnimation == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/gui");
            guiAnimation = new Animation(var1.toString());
        }

        guiAniDrawer = guiAnimation.getDrawer(0, false, 0);
        if (numberDrawer == null) {
            numberDrawer = (new Animation("/animation/number")).getDrawer(0, false, 0);
        }

        isThroughGame = false;
    }

    private void degreeLogic() {
        this.degree += 10;
        this.degree %= 360;
    }

    private void doReturnGameStuff() {
        this.state = 0;
        GameObject.IsGamePause = false;
        fadeInit(102, 0);
        if (!StageManager.isStagePass()) {
            if (PlayerObject.IsInvincibility()) {
                SoundSystem.getInstance().playBgm(44);
            } else if (GameObject.bossFighting) {
                switch (GameObject.bossID) {
                    case 21:
                    case 26:
                        if (GameObject.isBossHalf) {
                            SoundSystem.getInstance().playBgm(24, true);
                        } else {
                            SoundSystem.getInstance().playBgm(23, true);
                        }
                        break;
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                        SoundSystem.getInstance().playBgm(22);
                        break;
                    case 27:
                        SoundSystem.getInstance().playBgm(25);
                        break;
                    case 28:
                        SoundSystem.getInstance().playBgm(46);
                        break;
                    case 29:
                        SoundSystem.getInstance().playBgm(47);
                        break;
                    case 30:
                        SoundSystem.getInstance().playBgm(19, true);
                }
            } else {
                SoundSystem.getInstance().playBgm(StageManager.getBgmId(), true);
            }
        }

        Key.initSonic();
    }

    private void drawAction(MFGraphics var1, int var2, int var3, int var4) {
        if (this.IsActNumDrawable && StageManager.getStageID() < 12) {
            this.stageInfoActNumDrawer.draw(var1, StageManager.getStageID() % 2 + 27, var3, var4, false, 0);
        }

    }

    private void drawGameOver(MFGraphics var1) {
        float var2;
        int var4;
        int var5;
        AnimationDrawer var7;
        if (this.continueFrame <= 5) {
            var7 = guiAniDrawer;
            var4 = SCREEN_WIDTH;
            var5 = SCREEN_HEIGHT;
            var2 = this.continueScale;
            MyAPI.drawScaleAni(var1, var7, 11, var4 >> 1, (var5 >> 1) - 28, var2, 1.0F, 0.0F, 0.0F);
        } else if (this.continueFrame <= 10) {
            var7 = guiAniDrawer;
            var5 = SCREEN_WIDTH;
            var4 = SCREEN_HEIGHT;
            var2 = this.continueScale;
            MyAPI.drawScaleAni(var1, var7, 12, var5 >> 1, (var4 >> 1) - 28, var2, 1.0F, 0.0F, 0.0F);
        }

        if (this.continueFrame > 10) {
            var7 = guiAniDrawer;
            var5 = SCREEN_WIDTH;
            var4 = SCREEN_HEIGHT;
            var2 = this.continueScale;
            float var3 = this.continueScale;
            MyAPI.drawScaleAni(var1, var7, 12, var5 >> 1, (var4 >> 1) - 28, var2, var3, 0.0F, 8.0F);
            var1.setColor(0);
            MyAPI.fillRect(var1, this.continueMoveBlackBarX, (SCREEN_HEIGHT >> 1) + 10, SCREEN_WIDTH, 20);
            if (this.continueMoveBlackBarX == 0) {
                if (this.continueNumberState < 3) {
                    var7 = numberDrawer;
                    var4 = this.continueNumber;
                    int var6 = this.continueMoveNumberX;
                    var5 = SCREEN_HEIGHT;
                    var2 = this.continueNumberScale;
                    var3 = this.continueNumberScale;
                    MyAPI.drawScaleAni(var1, var7, var4 + 32, var6 - 6, (var5 >> 1) + 12, var2, var3, 6.0F, 8.0F);
                } else if (this.continueNumberState > 3) {
                    var7 = guiAniDrawer;
                    var5 = this.continueMoveNumberX;
                    var4 = SCREEN_HEIGHT;
                    var3 = this.continueNumberScale;
                    var2 = this.continueNumberScale;
                    MyAPI.drawScaleAni(var1, var7, 13, var5, (var4 >> 1) + 20, var3, var2, 0.0F, 0.0F);
                }

                if (this.continueNumberState < 3 && Key.touchgameover != null) {
                    this.drawGameOverTouchKey(var1);
                }
            }
        }

    }

    private void drawGameOverSingle(MFGraphics var1) {
        ++this.continueFrame;
        if (this.continueFrame >= 65) {
            if (this.continueScale > 0.0F) {
                this.continueScale -= 0.2F;
            } else {
                this.continueScale = 0.0F;
            }

            AnimationDrawer var5 = guiAniDrawer;
            int var3 = SCREEN_WIDTH;
            int var4 = SCREEN_HEIGHT;
            float var2 = this.continueScale;
            MyAPI.drawScaleAni(var1, var5, 11, var3 >> 1, (var4 >> 1) - 28, var2, 1.0F, 0.0F, 0.0F);
        } else {
            MyAPI.drawScaleAni(var1, guiAniDrawer, 11, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 28, 1.0F, 1.0F, 0.0F, 0.0F);
        }

    }

    private void drawGameOverTouchKey(MFGraphics var1) {
        if (Key.touchgameoveryres.Isin()) {
            drawTouchGameKeyBoardById(var1, 10, SCREEN_WIDTH - 22, 123);
        } else {
            drawTouchGameKeyBoardById(var1, 9, SCREEN_WIDTH - 22, 123);
        }

        if (Key.touchgameoverno.Isin()) {
            drawTouchGameKeyBoardById(var1, 12, SCREEN_WIDTH - 67, 138);
        } else {
            drawTouchGameKeyBoardById(var1, 11, SCREEN_WIDTH - 67, 138);
        }

        guiAniDrawer.draw(var1, 14, SCREEN_WIDTH - 22, 123, false, 0);
        guiAniDrawer.draw(var1, 15, SCREEN_WIDTH - 67, 138, false, 0);
    }

    private void drawGamePause(MFGraphics var1) {
        if (this.pausecnt > 5) {
            muiAniDrawer.setActionId(50);
            muiAniDrawer.draw(var1, this.pause_saw_x, this.pause_saw_y);
        }

        int var2;
        AnimationDrawer var4;
        if (this.pausecnt > 7) {
            var4 = muiAniDrawer;
            if (Key.touchgamepausereturn.Isin() || Key.repeat(524288 | 8388608)) {
                var2 = 5;
            } else {
                var2 = 0;
            }

            var4.setActionId(var2 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

        if (this.pausecnt > 5) {
            if (PlayerObject.stageModeState == 0) {
                var4 = muiAniDrawer;
                if (this.pause_item_cursor == 0 && Key.touchgamepauseitem[0].Isin()) {
                    var2 = 1;
                } else {
                    var2 = 0;
                }

                var4.setActionId(var2 + 2);
                muiAniDrawer.draw(var1, this.pause_item_x, this.pause_item_y);
                var4 = muiAniDrawer;
                if (this.pause_item_cursor == 1 && Key.touchgamepauseitem[1].Isin()) {
                    var2 = 1;
                } else {
                    var2 = 0;
                }

                var4.setActionId(var2 + 10);
                muiAniDrawer.draw(var1, this.pause_item_x, this.pause_item_y + 24);
                var4 = muiAniDrawer;
                if (this.pause_item_cursor == 2 && Key.touchgamepauseitem[2].Isin()) {
                    var2 = 1;
                } else {
                    var2 = 0;
                }

                var4.setActionId(var2 + 12);
                muiAniDrawer.draw(var1, this.pause_item_x, this.pause_item_y + 48);
            } else if (PlayerObject.stageModeState == 1) {
                for(var2 = 0; var2 < 6; ++var2) {
                    var4 = muiAniDrawer;
                    byte var3;
                    if (this.pause_item_cursor == var2 && Key.touchgamepauseitem[var2].Isin()) {
                        var3 = 1;
                    } else {
                        var3 = 0;
                    }

                    var4.setActionId((var2 + 1) * 2 + var3);
                    muiAniDrawer.draw(var1, this.pause_item_x, this.pause_item_y + var2 * 24);
                }
            }
        }

    }

    private void drawHugeStageName(MFGraphics var1, int var2, int var3, int var4) {
        this.selectMenuOffsetX -= 8;
        this.selectMenuOffsetX %= 224;
        if (var2 < 12) {
            var3 = var2 >> 1;
        } else {
            var3 = var2 - 5;
        }

        if (var2 == 11) {
            var3 = 6;
        }

        for(var2 = this.selectMenuOffsetX - 294; var2 < SCREEN_WIDTH * 2; var2 += 224) {
            stageInfoAniDrawer.draw(var1, var3 + 5, var2, var4 - 10 + 2, false, 0);
        }

    }

    private void drawLoading(MFGraphics var1) {
        switch (loadingType) {
            case 0:
                var1.setColor(0);
                MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                break;
            case 1:
                var1.setColor(16777215);
                MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                break;
            case 2:
                var1.setColor(0);
                MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                var1.setColor(16777215);
                MyAPI.fillRect(var1, 0, (SCREEN_HEIGHT >> 1) - 36 - 10, SCREEN_WIDTH, 20);
        }

        if (isLoadingSkipped == false && GlobalResource.loadingTipsConfig == 0) {
            this.loadingDrawer.setActionId(1);
            this.loadingDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
            if (tipsForShow == null || this.skipDrawer == null) {
                String var5 = this.TIPS[MyRandom.nextInt(0, this.TIPS.length - 1)];
                tipsForShow = MyAPI.getStrings(var5, 1200);
                MyAPI.initString();
                this.skipDrawer = (new Animation("/animation/skip")).getDrawer(0, false, 0);
            } else {
                String[] var4 = tipsForShow;
                int var3 = SCREEN_WIDTH;
                int var2 = SCREEN_HEIGHT;
                int var6;
                if (GlobalResource.languageConfig < 8) {
                    var6 = 16;
                } else {
                    var6 = 20;
                }
                MyAPI.drawStrings(var1, var4, (var3 >> 1) - 72, (var2 >> 1) - 50 - 2, 132, 100, var6, 16777215, 4656650, 0);
            }
            AnimationDrawer var8 = this.skipDrawer;
            byte var7;
            if (Key.touchopeningskip.Isin() && this.opengingCursor == 0 || Key.repeat(Key.B_S1 | 16777216 | 8388608)) {
                var7 = 1;
            } else {
                var7 = 0;
            }

            var8.setActionId(var7 + 0);
            this.skipDrawer.draw(var1, 0, SCREEN_HEIGHT);
            this.loadingWordsDrawer.setActionId(0);
        } else {
            this.loadingWordsDrawer.setActionId(2);
        }
        this.loadingWordsDrawer.draw(var1, SCREEN_WIDTH, SCREEN_HEIGHT);

    }

    private void drawLoadingBar(MFGraphics var1, int var2) {
        this.drawTips(var1, var2);
        drawBar(var1, 0, var2);
        this.selectMenuOffsetX += 8;
        this.selectMenuOffsetX %= MENU_TITLE_MOVE_DIRECTION;

        for(var2 = 0; var2 - this.selectMenuOffsetX > 0; var2 -= MENU_TITLE_MOVE_DIRECTION) {
        }

        for(var2 = 0; var2 < MENU_TITLE_DRAW_NUM; ++var2) {
            int var3 = MENU_TITLE_MOVE_DIRECTION;
        }

    }

    private void drawSaw(MFGraphics var1, int var2, int var3) {
        stageInfoAniDrawer.draw(var1, PlayerObject.getCharacterID(), var2, var3, false, 0);
    }

    private void drawScrollString(MFGraphics var1, String var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
        this.itemOffsetX += var4;
        this.itemOffsetX %= var5;

        for(var4 = 0; var4 - this.itemOffsetX > 0; var4 -= var5) {
        }

        int var10 = (SCREEN_WIDTH + var5 - 1) / var5;

        for(var9 = 0; var9 < var10 + 2; ++var9) {
            int var11 = this.itemOffsetX;
            MyAPI.drawBoldString(var1, var2, var4 + var9 * var5 - var11, var3, 17, var6, var7, var8);
        }

    }

    private void drawSonic(MFGraphics var1, int var2, int var3) {
        if (this.IsPlayerNameDrawable) {
            this.stageInfoPlayerNameDrawer.draw(var1, PlayerObject.getCharacterID() + 23, var2, var3, false, 0);
        }

    }

    private void drawTimeOver(MFGraphics var1, int var2, int var3) {
        byte var4 = 11;
        if (this.overtitleID == 78) {
            var4 = 11;
        } else if (this.overtitleID == 136) {
            if (!ChargePlatform.isChargedByIndex(0)) {
                var4 = 11;
            } else {
                var4 = 10;
            }
        }

        if (guiAnimation == null) {
            StringBuilder var5 = new StringBuilder("/lang");
            var5.append(GlobalResource.languageConfig);
            var5.append("/gui");
            guiAnimation = new Animation(var5.toString());
        }

        guiAniDrawer = guiAnimation.getDrawer(0, false, 0);
        guiAniDrawer.setActionId(var4);
        guiAniDrawer.draw(var1, var2, var3);
    }

    private void drawTinyStageName(MFGraphics var1, int var2, int var3, int var4) {
        if (var2 < 11) {
            var2 >>= 1;
        } else {
            var2 -= 5;
        }

        stageInfoAniDrawer.draw(var1, var2 + 14, var3, var4, false, 0);
    }

    private void drawTips(MFGraphics var1, int var2) {
        int var5 = SCREEN_WIDTH;
        int var4 = MENU_RECT_WIDTH;
        int var6 = SCREEN_HEIGHT;
        int var3 = MENU_RECT_WIDTH;
        var2 = SCREEN_HEIGHT;
        State.fillMenuRect(var1, var5 - var4 - 0 >> 1, (var6 >> 2) - 16, var3 + 0, (var2 >> 1) + 16);
        if (tipsForShow == null) {
            String var12 = tipsString[MyRandom.nextInt(0, tipsString.length - 1)];
            var2 = MENU_RECT_WIDTH;
            var3 = this.TIPS_OFFSET_X;
            tipsForShow = MyAPI.getStrings(var12, var2 - var3);
            MyAPI.initString();
        } else {
            var1.setColor(0);
            var6 = SCREEN_WIDTH;
            var3 = MENU_RECT_WIDTH;
            var2 = this.TIPS_OFFSET_X;
            var4 = SCREEN_HEIGHT;
            var5 = this.TIPS_TITLE_OFFSET_Y;
            MyAPI.drawBoldString(var1, "小提示", var6 - var3 + var2 >> 1, var5 + ((var4 >> 2) - 8), 0, 16777215, 4656650, 0);
            String[] var11 = tipsForShow;
            int var10 = SCREEN_WIDTH;
            var3 = MENU_RECT_WIDTH;
            int var7 = this.TIPS_OFFSET_X;
            int var9 = SCREEN_HEIGHT;
            var2 = LINE_SPACE;
            var4 = MENU_RECT_WIDTH;
            var6 = this.TIPS_OFFSET_X;
            var5 = SCREEN_HEIGHT;
            int var8 = LINE_SPACE;
            MyAPI.drawBoldStrings(var1, var11, var10 - var3 + var7 >> 1, var2 + ((var9 >> 2) - 8), var4 - var6, (var5 >> 1) - var8, 16777215, 4656650, 0);
        }

    }

    private static void drawTouchKeyPause(MFGraphics var0) {
    }

    private static void drawTouchKeyPauseOption(MFGraphics var0) {
    }

    private void drawWhiteBar(MFGraphics var1, int var2, int var3) {
        var1.setColor(16777215);
        MyAPI.fillRect(var1, var2, var3 - 10, SCREEN_WIDTH, 20);
    }

    private void endingDraw(MFGraphics var1) {
        var1.setColor(35064);
        MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.cloudDraw(var1);
        this.birdDraw1(var1);
        this.planeDraw(var1);
        this.birdDraw2(var1);
        switch (this.endingState) {
            case 3:
                drawMenuFontById(var1, 79, this.position, STAFF_CENTER_Y);
                break;
            case 4:
                this.staffDraw(var1);
                break;
            case 5:
                this.staffDraw(var1);
                drawSoftKey(var1, true, false);
        }

    }

    private void endingInit() {
        this.planeDrawer = (new Animation("/animation/ending/ending_plane")).getDrawer();
        this.cloudDrawer = (new Animation("/animation/ending/ending_cloud")).getDrawer();
        Animation var2 = new Animation("/animation/ending/ending_bird");
        this.birdDrawer = new AnimationDrawer[10];

        for(int var1 = 0; var1 < 10; ++var1) {
            this.birdDrawer[var1] = var2.getDrawer();
        }

        this.endingState = 0;
        this.cloudCount = 0;
        this.count = 20;
        this.planeInit();
        this.birdInit();
        this.staffInit();
        SoundSystem.getInstance().playBgm(33, false);
    }

    private void endingLogic() {
        if (this.count > 0) {
            --this.count;
        }

        this.degreeLogic();
        this.cloudLogic();
        switch (this.endingState) {
            case 0:
                if (this.count == 0) {
                    this.endingState = 1;
                }
                break;
            case 1:
                this.planeLogic();
                if (this.planeX == SCREEN_WIDTH >> 1) {
                    this.endingState = 2;
                }
                break;
            case 2:
                if (this.birdLogic()) {
                    this.endingState = 3;
                }
                break;
            case 3:
                if (this.showCount > 0) {
                    --this.showCount;
                }

                if (this.showCount == 0) {
                    this.changing = true;
                }

                if (this.changing) {
                    if (this.outing) {
                        int var2 = this.position;
                        int var3 = SCREEN_WIDTH;
                        int var1 = SCREEN_WIDTH;
                        this.position = MyAPI.calNextPositionReverse(var2, var3 >> 1, var1 * 3 >> 1, 1, 3);
                        if (this.position == SCREEN_WIDTH * 3 >> 1) {
                            this.outing = false;
                            this.position = -SCREEN_WIDTH >> 1;
                            this.endingState = 4;
                            Key.touchkeyboardClose();
                            Key.touchkeyboardInit();
                        }
                    } else {
                        this.position = MyAPI.calNextPosition((double)this.position, (double)(SCREEN_WIDTH >> 1), 1, 3);
                        if (this.position == SCREEN_WIDTH >> 1) {
                            this.outing = true;
                            this.showCount = 45;
                            this.changing = false;
                        }
                    }
                }
                break;
            case 4:
                this.staffLogic();
                if (!this.changing && this.showCount == 0 && this.stringCursor >= STAFF_STR.length - 1) {
                    this.endingState = 5;
                }
                break;
            case 5:
                if (Key.press(Key.B_S1 | Key.gSelect)) {
                    SoundSystem.getInstance().stopBgm(true);
                    this.setStateWithFade(4);
                }
        }

    }

    public static void enterSpStage(int var0, int var1, int var2) {
        spReserveRingNum = var0;
        spCheckPointID = var1;
        spTimeCount = var2;
        State.setState(6);
    }

    private void gamePauseInit() {
        this.pausecnt = 0;
        this.pause_saw_x = -50;
        this.pause_saw_y = 0;
        this.pause_saw_speed = 30;
        this.pause_item_x = SCREEN_WIDTH - 26;
        if (PlayerObject.stageModeState == 0) {
            this.pause_item_y = (SCREEN_HEIGHT >> 1) - 36;
            Key.touchGamePauseInit(0);
        } else if (PlayerObject.stageModeState == 1) {
            this.pause_item_y = (SCREEN_HEIGHT >> 1) - 60;
            Key.touchGamePauseInit(1);
        }

        this.pause_item_speed = -((SCREEN_WIDTH >> 1) + 14) / 3;
        if (muiAniDrawer == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/mui");
            muiAniDrawer = (new Animation(var1.toString())).getDrawer(0, false, 0);
        }

        this.pause_returnFlag = false;
    }

    private void gamePauseLogic() {
        ++this.pausecnt;
        if (this.pausecnt >= 5 && this.pausecnt <= 7) {
            if (this.pause_saw_x + this.pause_saw_speed > 0) {
                this.pause_saw_x = 0;
            } else {
                this.pause_saw_x += this.pause_saw_speed;
            }

            if (this.pause_item_x + this.pause_item_speed < (SCREEN_WIDTH >> 1) - 40) {
                this.pause_item_x = (SCREEN_WIDTH >> 1) - 40;
            } else {
                this.pause_item_x += this.pause_item_speed;
            }
        } else if (this.pausecnt > 7) {
            byte var1;
            if (PlayerObject.stageModeState == 0) {
                var1 = 3;
            } else {
                var1 = 6;
            }

            int var2;
            for(var2 = 0; var2 < var1; ++var2) {
                if (Key.touchgamepauseitem[var2].Isin() && Key.touchgamepause.IsClick()) {
                    this.pause_item_cursor = var2;
                }
            }

            if (Key.touchgamepausereturn.Isin() && Key.touchgamepause.IsClick()) {
                this.pause_item_cursor = -2;
            }

            if ((Key.buttonPress(524288 | 8388608) || Key.touchgamepausereturn.IsButtonPress() && this.pause_item_cursor == -2) && !this.pause_returnFlag) {
                this.pause_returnFlag = true;
                this.pause_returnframe = this.pausecnt;
                SoundSystem.getInstance().playSe(2);
            }

            if (this.pause_returnFlag) {
                if (this.pausecnt > this.pause_returnframe && this.pausecnt <= this.pause_returnframe + 3) {
                    this.pause_saw_x -= this.pause_saw_speed;
                    this.pause_item_x -= this.pause_item_speed;
                } else if (this.pausecnt > this.pause_returnframe + 3) {
                    this.BacktoGame();
                    isDrawTouchPad = true;
                    this.pause_returnFlag = false;
                    setFadeOver();
                }
            }

            if (this.pause_optionFlag && this.pausecnt > this.pause_optionframe + 3) {
                this.optionInit();
                this.state = 7;
                this.pause_optionFlag = false;
            }

            if (fadeChangeOver()) {
                if (PlayerObject.stageModeState == 0) {
                    if (Key.touchgamepauseitem[0].IsButtonPress() && this.pause_item_cursor == 0 && !this.pause_returnFlag) {
                        this.pause_returnFlag = true;
                        this.pause_returnframe = this.pausecnt;
                        SoundSystem.getInstance().playSe(1);
                    } else if (Key.touchgamepauseitem[1].IsButtonPress() && this.pause_item_cursor == 1 && fadeChangeOver()) {
                        this.state = 10;
                        fadeInit(102, 220);
                        this.secondEnsureInit();
                        SoundSystem.getInstance().playSe(1);
                    } else if ((Key.press(1073741824 | Integer.MIN_VALUE) || Key.touchgamepauseitem[2].IsButtonPress() && this.pause_item_cursor == 2) && !this.pause_returnFlag) {
                        this.changeStateWithFade(7);
                        this.optionInit();
                        SoundSystem.getInstance().playSe(1);
                    }
                } else if (PlayerObject.stageModeState == 1) {
                    if (Key.touchgamepauseitem[0].IsButtonPress() && this.pause_item_cursor == 0 && !this.pause_returnFlag) {
                        this.pause_returnFlag = true;
                        this.pause_returnframe = this.pausecnt;
                        SoundSystem.getInstance().playSe(1);
                    } else if (Key.touchgamepauseitem[1].IsButtonPress() && this.pause_item_cursor == 1 && fadeChangeOver()) {
                        this.state = 8;
                        fadeInit(102, 220);
                        this.secondEnsureInit();
                        SoundSystem.getInstance().playSe(1);
                    } else if (Key.touchgamepauseitem[2].IsButtonPress() && this.pause_item_cursor == 2 && fadeChangeOver()) {
                        this.state = 29;
                        fadeInit(102, 220);
                        this.secondEnsureInit();
                        SoundSystem.getInstance().playSe(1);
                    } else if (Key.touchgamepauseitem[3].IsButtonPress() && this.pause_item_cursor == 3 && fadeChangeOver()) {
                        this.state = 9;
                        fadeInit(102, 220);
                        this.secondEnsureInit();
                        SoundSystem.getInstance().playSe(1);
                    } else if (Key.touchgamepauseitem[4].IsButtonPress() && this.pause_item_cursor == 4 && fadeChangeOver()) {
                        this.state = 10;
                        fadeInit(102, 220);
                        this.secondEnsureInit();
                        SoundSystem.getInstance().playSe(1);
                    } else if ((Key.press(1073741824 | Integer.MIN_VALUE) || Key.touchgamepauseitem[5].IsButtonPress() && this.pause_item_cursor == 5) && !this.pause_returnFlag) {
                        this.changeStateWithFade(7);
                        this.optionInit();
                        SoundSystem.getInstance().playSe(1);
                    }
                }
            } else {
                for(var2 = 0; var2 < var1; ++var2) {
                    Key.touchgamepauseitem[var2].resetKeyState();
                }
            }
        }

    }

    private int getOffsetY(int var1) {
        int var2 = this.degree;
        return MyAPI.dSin(var2 + var1) * 10 / 100;
    }

    private void initStageInfoClearRes() {
        if (this.stageInfoClearAni == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/utl_res/stage_intro_clear.dat");
            this.stageInfoClearAni = Animation.getInstanceFromQi(var1.toString());
            stageInfoAniDrawer = this.stageInfoClearAni[0].getDrawer(0, false, 0);
            Animation var2 = this.stageInfoClearAni[0];
            AnimationDrawer var3 = var2.getDrawer(PlayerObject.getCharacterID() + 23, false, 0);
            this.stageInfoPlayerNameDrawer = var3;
            this.stageInfoActNumDrawer = this.stageInfoClearAni[0].getDrawer(27, false, 0);
            if (guiAnimation == null) {
                var1 = new StringBuilder("/lang");
                var1.append(GlobalResource.languageConfig);
                var1.append("/gui");
                guiAnimation = new Animation(var1.toString());
            }

            guiAniDrawer = guiAnimation.getDrawer(0, false, 0);
        }

        this.IsPlayerNameDrawable = false;
        this.IsActNumDrawable = false;
    }

    private void initStageIntroType1Conf() {
        this.frameCount = 0;
        this.display[0][0] = -50;
        this.display[0][1] = 0;
        this.display[0][2] = 0;
        this.display[0][3] = 0;
        this.display[1][0] = SCREEN_WIDTH;
        this.display[1][1] = (SCREEN_HEIGHT >> 1) + 48;
        this.display[1][2] = 0;
        this.display[1][3] = 0;
        this.display[2][0] = 48;
        this.display[2][1] = (SCREEN_HEIGHT >> 1) + 48;
        this.display[2][2] = 0;
        this.display[2][3] = 0;
        this.display[3][0] = 0;
        this.display[3][1] = 0;
        this.display[3][2] = 0;
        this.display[3][3] = 0;
        this.display[4][0] = SCREEN_WIDTH;
        this.display[4][1] = SCREEN_HEIGHT;
        this.display[4][2] = 0;
        this.display[4][3] = 0;
        this.display[5][0] = SCREEN_WIDTH;
        this.display[5][1] = (SCREEN_HEIGHT >> 1) - 10;
        this.display[5][2] = 0;
        this.display[5][3] = 0;
    }

    private void initStageIntroType2Conf() {
        this.frameCount = 0;
        this.display[0][0] = -50;
        this.display[0][1] = 0;
        this.display[0][2] = 0;
        this.display[0][3] = 0;
        this.display[1][0] = 0;
        this.display[1][1] = (SCREEN_HEIGHT >> 1) - 36;
        this.display[1][2] = 0;
        this.display[1][3] = 0;
        this.display[2][0] = 48;
        this.display[2][1] = (SCREEN_HEIGHT >> 1) + 48;
        this.display[2][2] = 0;
        this.display[2][3] = 0;
        this.display[3][0] = 0;
        this.display[3][1] = 0;
        this.display[3][2] = 0;
        this.display[3][3] = 0;
        this.display[4][0] = SCREEN_WIDTH;
        this.display[4][1] = SCREEN_HEIGHT;
        this.display[4][2] = 0;
        this.display[4][3] = 0;
        this.display[5][0] = SCREEN_WIDTH;
        this.display[5][1] = (SCREEN_HEIGHT >> 1) - 10;
        this.display[5][2] = 0;
        this.display[5][3] = 0;
    }

    private void initTips() {
        Key.touchOpeningInit();
        MapManager.closeMap();
        if (GameObject.player != null) {
            GameObject.player.close();
        }
        if (GameObject.player2 != null) {
            GameObject.player2.close();
        }

        System.gc();

        try {
            Thread.sleep(100L);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        StringBuilder var1;
        if (this.loadingWordsDrawer == null || this.loadingDrawer == null) {
            var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/loading");
            Animation var4 = new Animation(var1.toString());
            AnimationDrawer var2 = var4.getDrawer(0, true, 0);
            this.loadingWordsDrawer = var2;
            AnimationDrawer var5 = var4.getDrawer(0, false, 0);
            this.loadingDrawer = var5;
        }

        this.TIPS = null;
        switch (PlayerObject.getCharacterID()) {
            case 0:
                if (StageManager.getCurrentZoneId() == 8) {
                    var1 = new StringBuilder("/lang");
                    var1.append(GlobalResource.languageConfig);
                    var1.append("/tip/tips_ssonic");
                    this.TIPS = MyAPI.loadText(var1.toString());
                } else {
                    var1 = new StringBuilder("/lang");
                    var1.append(GlobalResource.languageConfig);
                    var1.append("/tip/tips_sonic");
                    this.TIPS = MyAPI.loadText(var1.toString());
                }
                break;
            case 1:
                var1 = new StringBuilder("/lang");
                var1.append(GlobalResource.languageConfig);
                var1.append("/tip/tips_tails");
                this.TIPS = MyAPI.loadText(var1.toString());
                break;
            case 2:
                var1 = new StringBuilder("/lang");
                var1.append(GlobalResource.languageConfig);
                var1.append("/tip/tips_knuckles");
                this.TIPS = MyAPI.loadText(var1.toString());
                break;
            case 3:
                var1 = new StringBuilder("/lang");
                var1.append(GlobalResource.languageConfig);
                var1.append("/tip/tips_amy");
                this.TIPS = MyAPI.loadText(var1.toString());
        }

        MyAPI.initString();
        if (isLoadingSkipped == false && GlobalResource.loadingTipsConfig == 0) {
            this.loadingStartTime = System.currentTimeMillis();
        }

        tipsForShow = null;
        fadeInit(255, 0);
    }

    private void interruptInit() {
        if (this.interruptDrawer == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/utl_res/suspend_resume.dat");
            String var2 = var1.toString();
            Animation var3 = Animation.getInstanceFromQi(var2)[0];
            AnimationDrawer var4 = var3.getDrawer(0, true, 0);
            this.interruptDrawer = var4;
        }

        isDrawTouchPad = false;
        IsInInterrupt = true;
        lastFading = fading;
        fading = false;
        Key.touchkeygameboardClose();
        Key.touchkeyboardInit();
        Key.touchInterruptInit();
    }

    private int itemsid(int var1) {
        int var2 = this.optionOffsetY / 24 * 2;
        if (var1 + var2 < 0) {
            var1 = 0;
        } else if (var1 + var2 > 9) {
            var1 = 9;
        } else {
            var1 += var2;
        }

        return var1;
    }

    public static boolean loadingEnd() {
        long var2 = System.currentTimeMillis();
        boolean var1;
        if (var2 - loadingStartTime >= 10000L && StageManager.loadStageStep()) {
            var1 = true;
        } else {
            var1 = false;
        }

        return var1;
    }

    private void optionDraw(MFGraphics var1) {
        var1.setColor(0);
        MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        muiAniDrawer.setActionId(52);
        int var2;
        for(var2 = 0; var2 < SCREEN_WIDTH / 48 + 1; ++var2) {
            for(int var3 = 0; var3 < SCREEN_HEIGHT / 48 + 1; ++var3) {
                muiAniDrawer.draw(var1, var2 * 48, var3 * 48);
            }
        }

        if (this.state != 7) {
            this.pauseOptionCursor = -2;
        }

        if (numberDrawer == null) {
            numberDrawer = (new Animation("/animation/number")).getDrawer(0, false, 0);
        }

        muiLeftArrowDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, 44);
        muiRightArrowDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 96, 44);

        AnimationDrawer var4 = muiAniDrawer;
        byte var5;
        if (this.optionIndex == 0) {
            muiAniDrawer.setActionId(74);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1), this.optionDrawOffsetY + 40 + this.optionslide_y + 0);

            // SOUNDTRACK
            muiAniDrawer.setActionId(20);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 24);
            if (GlobalResource.soundSwitchConfig == 0) {
                var2 = 67;
            } else {
                if (Key.touchmenuoptionitems[3].Isin() && this.pauseOptionCursor == 1 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 55;
            }

            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24);
            muiAniDrawer.setActionId(Standard2.soundtrack + 101);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24);

            // VIBRATION
            muiAniDrawer.setActionId(21);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 48);
            var4 = muiAniDrawer;
            if (Key.touchmenuoptionitems[5].Isin() && this.pauseOptionCursor == 2 && this.isSelectable) {
                var5 = 1;
            } else {
                var5 = 0;
            }

            var2 = var5 + 57;
            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48);
            switch (GlobalResource.vibrationConfig) {
                case 0:
                    muiAniDrawer.setActionId(36);
                    break;
                case 1:
                    muiAniDrawer.setActionId(70);
                    break;
                case 2:
                    muiAniDrawer.setActionId(69);
                    break;
                case 3:
                    muiAniDrawer.setActionId(68);
            }

            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48);

            // TIPS SCREEN (LOADING)
            muiAniDrawer.setActionId(23);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 72);
            var4 = muiAniDrawer;
            if (Key.touchmenuoptionitems[7].Isin() && this.pauseOptionCursor == 3 && this.isSelectable) {
                var5 = 1;
            } else {
                var5 = 0;
            }

            var2 = var5 + 55;

            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72);
            var4 = muiAniDrawer;
            if (GlobalResource.loadingTipsConfig == 0) {
                var5 = 0;
            } else {
                var5 = 1;
            }

            var4.setActionId(var5 + 35);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72);
        } else if (this.optionIndex == 1) {
            muiAniDrawer.setActionId(75);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1), this.optionDrawOffsetY + 40 + this.optionslide_y + 0);

            // POSITION
            muiAniDrawer.setActionId(96);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 24);
            var4 = muiAniDrawer;
            if (Def.SCREEN_WIDTH < 260) {
                var2 = 77;
            } else {
                if (Key.touchmenuoptionitems[3].Isin() && this.pauseOptionCursor == 1 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 57;
            }

            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardLeftPosition, (SCREEN_WIDTH >> 1) + 35, this.optionDrawOffsetY + 40 + this.optionslide_y + 18, 2);
            muiAniDrawer.setActionId(79);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 55, this.optionDrawOffsetY + 40 + this.optionslide_y + 24);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardRightPosition, (SCREEN_WIDTH >> 1) + 75, this.optionDrawOffsetY + 40 + this.optionslide_y + 18, 2);

            // SIZE
            muiAniDrawer.setActionId(97);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 48);
            var4 = muiAniDrawer;
            if (Key.touchmenuoptionitems[5].Isin() && this.pauseOptionCursor == 2 && this.isSelectable) {
                var5 = 1;
            } else {
                var5 = 0;
            }

            var2 = var5 + 57;
            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardSize, (SCREEN_WIDTH >> 1) + 46, this.optionDrawOffsetY + 40 + this.optionslide_y + 42, 2);
            muiAniDrawer.setActionId(95);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 51, this.optionDrawOffsetY + 40 + this.optionslide_y + 41);
            NumberDrawer.drawNum(var1, 0, 5, (SCREEN_WIDTH >> 1) + 64, this.optionDrawOffsetY + 40 + this.optionslide_y + 42, 2);

            // OPACITY
            muiAniDrawer.setActionId(98);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 72);
            var4 = muiAniDrawer;
            if (Key.touchmenuoptionitems[7].Isin() && this.pauseOptionCursor == 3 && this.isSelectable) {
                var5 = 1;
            } else {
                var5 = 0;
            }

            var2 = var5 + 57;
            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardOpacity, (SCREEN_WIDTH >> 1) + 46, this.optionDrawOffsetY + 40 + this.optionslide_y + 66, 2);
            muiAniDrawer.setActionId(95);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 51, this.optionDrawOffsetY + 40 + this.optionslide_y + 65);
            NumberDrawer.drawNum(var1, 0, 3, (SCREEN_WIDTH >> 1) + 64, this.optionDrawOffsetY + 40 + this.optionslide_y + 66, 2);
        } else if (this.optionIndex == 2) {
            muiAniDrawer.setActionId(76);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1), this.optionDrawOffsetY + 40 + this.optionslide_y + 0);

            // CONTROLS
            muiAniDrawer.setActionId(99);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 24);
            if (!ChargePlatform.isChargedByIndex(0)) {
                var2 = 67;
            } else {
                if (Key.touchmenuoptionitems[3].Isin() && this.pauseOptionCursor == 1 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 55;
            }

            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24);
            muiAniDrawer.setActionId(GlobalResource.spsetConfig + 37);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24);

            // GYROSCOPE LEVEL
            muiAniDrawer.setActionId(24);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 48);
            var4 = muiAniDrawer;
            if (!ChargePlatform.isChargedByIndex(0) || GlobalResource.spsetConfig == 0) {
                var2 = 77;
            } else {
                if (Key.touchmenuoptionitems[5].Isin() && this.pauseOptionCursor == 2 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 57;
            }

            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48);
            switch (GlobalResource.sensorConfig) {
                case 0:
                    muiAniDrawer.setActionId(70);
                    break;
                case 1:
                    muiAniDrawer.setActionId(69);
                    break;
                case 2:
                    muiAniDrawer.setActionId(68);
            }

            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48);

            // FIXED SCREEN
            muiAniDrawer.setActionId(100);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 72);
            var4 = muiAniDrawer;
            if (!ChargePlatform.isChargedByIndex(0) || Def.SCREEN_WIDTH <= 304) {
                var2 = 67;
            } else {
                if (Key.touchmenuoptionitems[7].Isin() && this.pauseOptionCursor == 3 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 55;
            }

            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72);
            var4 = muiAniDrawer;
            if (GlobalResource.fixedScreenConfig == 0) {
                var5 = 0;
            } else {
                var5 = 1;
            }

            var4.setActionId(var5 + 35);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72);
        }

        // HELP
        var4 = muiAniDrawer;
        if (Key.touchmenuoptionitems[8].Isin() && this.pauseOptionCursor == 4 && this.isSelectable) {
            var5 = 1;
        } else {
            var5 = 0;
        }
        var4.setActionId(var5 + 27);
        muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 96);

        this.optionOffsetX -= 4;
        this.optionOffsetX %= 100;
        muiAniDrawer.setActionId(51);

        for(var2 = this.optionOffsetX; var2 < SCREEN_WIDTH * 2; var2 += 100) {
            muiAniDrawer.draw(var1, var2, 0);
        }

        var4 = muiAniDrawer;
        if (Key.touchmenuoptionreturn.Isin() || Key.repeat(524288 | 8388608)) {
            var5 = 5;
        } else {
            var5 = 0;
        }

        var4.setActionId(var5 + 61);
        muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);

        drawFade(var1);
    }

    private void optionInit() {
        this.optionMenuCursor = 0;
        this.menuInit(OPTION_ELEMENT_NUM);
        this.optionCursor[0] = GlobalResource.soundConfig;
        this.optionCursor[1] = GlobalResource.seConfig;
        this.offsetOfVolumeInterface = MENU_SPACE;
        if (muiAniDrawer == null || muiLeftArrowDrawer == null || muiRightArrowDrawer == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/mui");
            muiAniDrawer = (new Animation(var1.toString())).getDrawer(0, false, 0);
            muiLeftArrowDrawer = (new Animation(var1.toString())).getDrawer(91, true, 0);
            muiRightArrowDrawer = (new Animation(var1.toString())).getDrawer(92, true, 0);
        }

        Key.touchGamePauseOptionInit();
        this.optionIndex = 0;
        this.optionOffsetX = 0;
        this.pauseOptionCursor = 0;
        this.isOptionDisFlag = false;
        fadeInit(102, 102);
        Key.touchMenuOptionInit();
        this.optionOffsetYAim = 0;
        this.optionOffsetY = 0;
        this.isChanged = false;
        this.optionslide_getprey = -1;
        this.optionslide_gety = -1;
        this.optionslide_y = 0;
        this.optionDrawOffsetBottomY = 0;
        this.optionYDirect = 0;
        this.arrowindex = -1;
    }

    private void optionLogic() {
        State.resetTouchPosition();
        if (!this.isOptionDisFlag) {
            SoundSystem.getInstance().playBgm(5);
            this.isOptionDisFlag = true;
        }

        int var1;
        if (fadeChangeOver()) {
            if (Key.press(16) || Key.touchmenuoptionleftarrow.Isin() && Key.touchmenuoption.IsClick()) {
                this.arrowindex = 0;
            } else if (Key.press(32) || Key.touchmenuoptionrightarrow.Isin() && Key.touchmenuoption.IsClick()) {
                this.arrowindex = 1;
            }

            if ((Key.touchmenuoptionleftarrow.IsButtonPress() || Key.press(16)) && this.arrowindex == 0) {
                SoundSystem.getInstance().playSe(3);
                --this.optionIndex;
            } else if ((Key.touchmenuoptionrightarrow.IsButtonPress() || Key.press(32)) && this.arrowindex == 1) {
                SoundSystem.getInstance().playSe(3);
                ++this.optionIndex;
            }

            if (this.optionIndex < 0) {
                this.optionIndex = 2;
            } else if (this.optionIndex > 2) {
                this.optionIndex = 0;
            }

            if (Key.touchmenuoptionreturn.Isin() && Key.touchmenuoption.IsClick()) {
                this.returnCursor = 1;
            }

            this.optionslide_gety = Key.slidesensormenuoption.getPointerY();
            this.optionslide_y = 0;
            this.optionslidefirsty = 0;

            int var2;
            for(var1 = 0; var1 < Key.touchmenuoptionitems.length >> 1; ++var1) {
                TouchKeyRange var4 = Key.touchmenuoptionitems[var1 * 2];
                int var3 = this.optionDrawOffsetY;
                var2 = this.optionslide_y;
                var4.setStartY(var1 * 24 + 28 + var3 + var2);
                var4 = Key.touchmenuoptionitems[var1 * 2 + 1];
                var3 = this.optionDrawOffsetY;
                var2 = this.optionslide_y;
                var4.setStartY(var1 * 24 + 28 + var3 + var2);
            }

            if (this.isSelectable) {
                for(var1 = 0; var1 < Key.touchmenuoptionitems.length; ++var1) {
                    if (Key.touchmenuoptionitems[var1].Isin() && Key.touchmenuoption.IsClick()) {
                        this.pauseOptionCursor = var1 / 2;
                        this.returnCursor = 0;
                        break;
                    }
                }
            }

            if (Key.touchmenuoptionreturn.Isin() && Key.touchmenuoption.IsClick()) {
                this.returnCursor = 1;
            }

            if ((Key.buttonPress(524288 | 8388608) || Key.touchmenuoptionreturn.IsButtonPress() && this.returnCursor == 1) && fadeChangeOver()) {
                this.changeStateWithFade(1);
                SoundSystem.getInstance().stopBgm(false);
                SoundSystem.getInstance().playSe(2);
                GlobalResource.saveSystemConfig();
            }

            if (Key.press(1073741824 | Integer.MIN_VALUE)) {
                this.changeStateWithFade(34);
                this.helpInit();
                SoundSystem.getInstance().playSe(1);
            }

            if (Key.slidesensormenuoption.isSliding()) {
                this.isSelectable = true;
            } else {
                if (this.isOptionChange && this.optionslide_y == 0) {
                    this.optionDrawOffsetY = this.optionDrawOffsetTmpY1;
                    this.isOptionChange = false;
                    this.optionYDirect = 0;
                }

                if (!this.isOptionChange) {
                    if (this.optionDrawOffsetY > 0) {
                        this.optionYDirect = 1;
                        var2 = -this.optionDrawOffsetY >> 1;
                        var1 = var2;
                        if (var2 > -2) {
                            var1 = -2;
                        }

                        if (this.optionDrawOffsetY + var1 <= 0) {
                            this.optionDrawOffsetY = 0;
                            this.optionYDirect = 0;
                        } else {
                            this.optionDrawOffsetY += var1;
                        }
                    } else if (this.optionDrawOffsetY < this.optionDrawOffsetBottomY) {
                        this.optionYDirect = 2;
                        var2 = this.optionDrawOffsetBottomY - this.optionDrawOffsetY >> 1;
                        var1 = var2;
                        if (var2 < 2) {
                            var1 = 2;
                        }

                        if (this.optionDrawOffsetY + var1 >= this.optionDrawOffsetBottomY) {
                            this.optionDrawOffsetY = this.optionDrawOffsetBottomY;
                            this.optionYDirect = 0;
                        } else {
                            this.optionDrawOffsetY += var1;
                        }
                    }
                }
            }

            if (this.isSelectable && this.optionYDirect == 0) {
                if (this.optionIndex == 0) {
                    if (Key.touchmenuoptionitems[3].IsButtonPress() && this.pauseOptionCursor == 1 && GlobalResource.soundSwitchConfig != 0 && fadeChangeOver()) {
                        // this.state = 38;
                        // this.itemsSelect2Init();
                        SoundSystem.getInstance().playSe(1);
                        this.isOptionDisFlag = false;
                        if (Standard2.soundtrack == 1){
                            Standard2.soundtrack = 0;
                        } else {
                            Standard2.soundtrack = 1;
                        }
                    } else if (Key.touchmenuoptionitems[5].IsButtonPress() && this.pauseOptionCursor == 2 && fadeChangeOver()) {
                        this.state = 31;
                        this.itemsSelect4Init();
                        SoundSystem.getInstance().playSe(1);
                    } else if (Key.touchmenuoptionitems[7].IsButtonPress() && this.pauseOptionCursor == 3 && fadeChangeOver()) {
                        // this.state = 30;
                        // this.itemsSelect2Init();
                        SoundSystem.getInstance().playSe(1);
                        if (GlobalResource.loadingTipsConfig == 0){
                            GlobalResource.loadingTipsConfig = 1;
                        } else {
                            GlobalResource.loadingTipsConfig = 0;
                        }
                    }
                } else if (this.optionIndex == 1) {
                    if (Key.touchmenuoptionitems[3].IsButtonPress() && this.pauseOptionCursor == 1 && fadeChangeOver()) {
                        if (Def.SCREEN_WIDTH >= 260) {
                            this.state = 30;
                            this.touchPadInit();
                            SoundSystem.getInstance().playSe(1);
                        } else {
                            SoundSystem.getInstance().playSe(2);
                        }
                    } else if (Key.touchmenuoptionitems[5].IsButtonPress() && this.pauseOptionCursor == 2 && fadeChangeOver()) {
                        this.state = 38;
                        this.touchPadInit();
                        SoundSystem.getInstance().playSe(1);
                    } else if (Key.touchmenuoptionitems[7].IsButtonPress() && this.pauseOptionCursor == 3 && fadeChangeOver()) {
                        this.state = 33;
                        this.touchPadInit();
                        SoundSystem.getInstance().playSe(1);
                    }
                } else if (this.optionIndex == 2) {
                    if (Key.touchmenuoptionitems[3].IsButtonPress() && this.pauseOptionCursor == 1 && fadeChangeOver()) {
                        if (ChargePlatform.isChargedByIndex(0)) {
                            SoundSystem.getInstance().playSe(1);
                            if (GlobalResource.spsetConfig == 0){
                                GlobalResource.spsetConfig = 1;
                            } else {
                                GlobalResource.spsetConfig = 0;
                            }
                        } else {
                            SoundSystem.getInstance().playSe(2);
                        }
                    } else if (Key.touchmenuoptionitems[5].IsButtonPress() && this.pauseOptionCursor == 2 && fadeChangeOver()) {
                        if (ChargePlatform.isChargedByIndex(0) && GlobalResource.spsetConfig != 0) {
                            if (ChargePlatform.isChargedByIndex(0)) {
                                this.state = 39;
                                this.itemsSelect3Init();
                                SoundSystem.getInstance().playSe(1);
                            } else {
                                SoundSystem.getInstance().playSe(2);
                            }
                        } else {
                            SoundSystem.getInstance().playSe(2);
                        }
                    } else if (Key.touchmenuoptionitems[7].IsButtonPress() && this.pauseOptionCursor == 3 && fadeChangeOver()) {
                        if (ChargePlatform.isChargedByIndex(0) && Def.SCREEN_WIDTH > 304) {
                            SoundSystem.getInstance().playSe(1);
                            if (GlobalResource.fixedScreenConfig == 0){
                                GlobalResource.fixedScreenConfig = 1;
                            } else {
                                GlobalResource.fixedScreenConfig = 0;
                            }
                        } else {
                            SoundSystem.getInstance().playSe(2);
                        }
                    }
                }

                if (Key.touchmenuoptionitems[8].IsButtonPress() && this.pauseOptionCursor == 4 && fadeChangeOver()) {
                    this.changeStateWithFade(34);
                    this.helpInit();
                    SoundSystem.getInstance().playSe(1);
                }
                
            }

            this.optionslide_getprey = this.optionslide_gety;
        } else {
            for(var1 = 0; var1 < Key.touchmenuoptionitems.length; ++var1) {
                Key.touchmenuoptionitems[var1].resetKeyState();
            }
        }

    }

    private void pauseOptionLoadingTipsLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                GlobalResource.loadingTipsConfig = 0;
                fadeInit(102, 0);
                this.state = 7;
                this.returnCursor = 0;
                break;
            case 2:
                GlobalResource.loadingTipsConfig = 1;
                fadeInit(102, 0);
                this.state = 7;
                this.returnCursor = 0;
                break;
            case 3:
                fadeInit(102, 0);
                this.state = 7;
                this.returnCursor = 0;
        }

    }

    private void pauseOptionSoundLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                this.isOptionDisFlag = false;
                Standard2.soundtrack = 0;
                fadeInit(102, 0);
                this.state = 7;
                this.returnCursor = 0;
                break;
            case 2:
                this.isOptionDisFlag = false;
                Standard2.soundtrack = 1;
                fadeInit(102, 0);
                this.state = 7;
                this.returnCursor = 0;
                break;
            case 3:
                fadeInit(102, 0);
                this.state = 7;
                this.returnCursor = 0;
        }

    }

    private void pauseOptionSpSetLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                GlobalResource.spsetConfig = 0;
                GlobalResource.sensorConfig = 3;
                fadeInit(102, 0);
                this.state = 7;
                this.returnCursor = 0;
                break;
            case 2:
                this.state = 39;
                this.itemsSelect4Init();
                this.returnCursor = 0;
                break;
            case 3:
                fadeInit(102, 0);
                this.state = 7;
                this.returnCursor = 0;
        }

    }

    private void pauseOptionVibrationLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                GlobalResource.vibrationConfig = 1;
                fadeInit(102, 0);
                this.state = 7;
                this.returnCursor = 0;
                MyAPI.vibrate();
                break;
            case 2:
                GlobalResource.vibrationConfig = 0;
                fadeInit(102, 0);
                this.state = 7;
                this.returnCursor = 0;
                break;
            case 3:
                fadeInit(102, 0);
                this.state = 7;
                this.returnCursor = 0;
        }

    }

    private void pausetoSelectCharacterLogic() {
        switch (this.secondEnsureLogic()) {
            case 1:
                this.setStateWithFade(9);
                break;
            case 2:
                fadeInit(220, 102);
                fadeInit_Modify(192, 102);
                this.state = 1;
                GameObject.IsGamePause = true;
        }

    }

    private void pausetoSelectStageLogic() {
        switch (this.secondEnsureLogic()) {
            case 1:
                this.setStateWithFade(3);
                break;
            case 2:
                fadeInit(220, 102);
                fadeInit_Modify(192, 102);
                this.state = 1;
                GameObject.IsGamePause = true;
        }

    }

    private void pausetoTitleLogic() {
        switch (this.secondEnsureLogic()) {
            case 1:
                if (GameObject.stageModeState == 1) {
                    StageManager.doWhileLeaveRace();
                }

                PlayerObject.doPauseLeaveGame();
                if (GameObject.stageModeState != 1) {
                    StageManager.characterFromGame = PlayerObject.getCharacterID();
                }

                StageManager.stageIDFromGame = StageManager.getStageID();
                this.setStateWithFade(2);
                Key.touchanykeyInit();
                Key.touchMainMenuInit2();
                break;
            case 2:
                fadeInit_Modify(192, 102);
                this.state = 1;
                GameObject.IsGamePause = true;
        }

    }

    private void planeDraw(MFGraphics var1) {
        this.planeDrawer.draw(var1, this.planeX, this.planeY + this.getOffsetY(0));
    }

    private void planeInit() {
        this.planeX = SCREEN_WIDTH + 60;
        this.planeY = SCREEN_HEIGHT * 4 / 5;
    }

    private void planeLogic() {
        this.planeX -= 2;
        if (this.planeX < SCREEN_WIDTH >> 1) {
            this.planeX = SCREEN_WIDTH >> 1;
        }

    }

    private void releaseOptionItemsTouchKey() {
        for(int var1 = 0; var1 < Key.touchmenuoptionitems.length; ++var1) {
            Key.touchmenuoptionitems[var1].resetKeyState();
        }

    }

    private void releaseTips() {
        tipsForShow = null;
    }

    private void retryStageLogic() {
        switch (this.secondEnsureLogic()) {
            case 1:
                this.state = 5;
                loadingType = 0;
                this.initTips();
                break;
            case 2:
                fadeInit_Modify(192, 102);
                this.state = 1;
                GameObject.IsGamePause = true;
        }

    }

    public static void setPauseMenu() {
        int[] var0;
        if (IsToolsCharge()) {
            var0 = PAUSE_NORMAL_MODE_SHOP;
        } else {
            var0 = PAUSE_NORMAL_MODE_NOSHOP;
        }

        PAUSE_NORMAL_MODE = var0;
    }

    private void setStateWithFade(int var1) {
        this.isStateClassSwitch = true;
        if (!fading) {
            fading = true;
            fadeInit(0, 255);
            this.stateForSet = var1;
        }

    }

    private void staffDraw(MFGraphics var1) {
    }

    private void staffInit() {
        this.colorCursor = MyRandom.nextInt(COLOR_SEQ.length);
        this.stringCursor = 0;
        this.position = -SCREEN_WIDTH >> 1;
        this.outing = false;
    }

    private void staffLogic() {
        if (this.showCount > 0) {
            --this.showCount;
        }

        if (this.showCount > 0 && Key.repeatAnyKey()) {
            this.showCount = 0;
        }

        if (this.showCount == 0 && this.stringCursor < STAFF_STR.length - 1) {
            this.changing = true;
        }

        if (this.changing) {
            if (this.outing) {
                int var2 = this.position;
                int var3 = SCREEN_WIDTH;
                int var1 = SCREEN_WIDTH;
                this.position = MyAPI.calNextPositionReverse(var2, var3 >> 1, var1 * 3 >> 1, 1, 3);
                if (this.position == SCREEN_WIDTH * 3 >> 1) {
                    this.outing = false;
                    this.position = -SCREEN_WIDTH >> 1;
                    ++this.stringCursor;
                    this.colorCursor += MyRandom.nextInt(1, COLOR_SEQ.length - 1);
                    this.colorCursor %= COLOR_SEQ.length;
                }
            } else {
                this.position = MyAPI.calNextPosition((double)this.position, (double)(SCREEN_WIDTH >> 1), 1, 3);
                if (this.position == SCREEN_WIDTH >> 1) {
                    this.changing = false;
                    this.outing = true;
                    this.showCount = 45;
                }
            }
        }

    }

    private void stagePassLogic() {
        PlayerObject.isNeedPlayWaterSE = false;
        if (StageManager.isOnlyStagePass) {
            fadeInit(0, 255);
            this.state = 35;
            isThroughGame = true;
            loadingType = 0;
            StageManager.addStageID();
            SoundSystem.getInstance().stopBgm(false);
        } else if (PlayerObject.IsStarttoCnt) {
            ++this.cnt;
            if (this.cnt == 1) {
                if (PlayerObject.stageModeState == 1) {
                    GameObject.ObjectClear();
                }

                PlayerObject.isbarOut = true;
            }

            if (this.cnt == 2) {
                SoundSystem.getInstance().playSe(32, false);
            }

            if (this.cnt > 20) {
                if (this.cnt == 21) {
                    if (PlayerObject.stageModeState == 0) {
                        GameObject.player.setStagePassRunOutofScreen();
                        if (GameObject.player2 != null) GameObject.player2.setStagePassRunOutofScreen();
                    } else if (PlayerObject.stageModeState == 1) {
                        if (PlayerObject.IsDisplayRaceModeNewRecord) {
                            this.racemode_cnt = 128;
                        } else {
                            this.racemode_cnt = 60;
                        }
                    }
                } else if (this.cnt > 21 && PlayerObject.stageModeState == 0 && (GameObject.player.stagePassRunOutofScreenLogic() || GameObject.player2 != null && GameObject.player2.stagePassRunOutofScreenLogic())) {
                    if (StageManager.IsStageEnd()) {
                        StageManager.characterFromGame = -1;
                        StageManager.stageIDFromGame = -1;
                        StageManager.saveStageRecord();
                        setState(11);
                    } else if (StageManager.getStageID() == 12) {
                        if (StageManager.isGoingToExtraStage()) {
                            fadeInit(0, 255);
                            this.state = 35;
                            isThroughGame = true;
                            loadingType = 0;
                            StageManager.addStageID();
                            SoundSystem.getInstance().stopBgm(false);
                        } else {
                            StageManager.characterFromGame = -1;
                            StageManager.stageIDFromGame = -1;
                            StageManager.saveStageRecord();
                            State.setState(10);
                        }
                    } else {
                        fadeInit(0, 255);
                        this.state = 35;
                        isThroughGame = true;
                        loadingType = 0;
                        StageManager.addStageID();
                        StageManager.characterFromGame = PlayerObject.getCharacterID();
                        StageManager.stageIDFromGame = StageManager.getStageID();
                        StageManager.saveStageRecord();
                        SoundSystem.getInstance().stopBgm(false);
                    }
                }

                if (this.cnt == this.racemode_cnt) {
                    if (PlayerObject.stageModeState == 1) {
                        if (!ChargePlatform.isChargedByIndex(0)) {
                            MFDevice.setResponseInterruptFlag(false);
                            ChargePlatform.chargeByIndex(0);
                            MFDevice.setResponseInterruptFlag(true);
                            Standard2.splashinit(true);
                            this.setStateWithFade(0);
                        } else {
                            this.setStateWithFade(3);
                            Key.touchgamekeyClose();
                            Key.touchkeygameboardClose();
                            Key.touchkeyboardInit();
                        }
                    }

                    StageManager.saveHighScoreRecord();
                }
            }
        }

    }

    public void BP_GotoTryPaying() {
        if (StageManager.getStageID() == 1 && !IsPaid) {
            this.BP_continueTryInit();
        }

        if (StageManager.getStageID() == 2 && !IsPaid) {
            this.state = 20;
            BP_enteredPaying = false;
            this.BP_IsFromContinueTry = false;
            this.BP_payingInit(4, 3);
            fadeInit(255, 0);
        }

        if (IsPaid) {
            this.state = 5;
        }

    }

    public void BP_buyLogic() {
        if (BP_chargeLogic(2)) {
            BP_toolsAdd();
            this.shopInit(false);
        } else {
            this.shopInit(false);
        }

    }

    public void BP_continueTryDraw(MFGraphics var1) {
        this.menuBgDraw(var1);
        int var2 = this.BP_CONTINUETRY_MENU_START_X;
        int var5 = this.BP_CONTINUETRY_MENU_START_Y;
        int var4 = this.BP_CONTINUETRY_MENU_WIDTH;
        int var3 = this.BP_CONTINUETRY_MENU_HEIGHT;
        fillMenuRect(var1, var2, var5, var4, var3);
        var1.setColor(0);
        String[] var8 = strForShow;
        var2 = this.BP_CONTINUETRY_MENU_START_X;
        var4 = this.BP_CONTINUETRY_MENU_START_Y;
        var5 = this.BP_CONTINUETRY_MENU_WIDTH;
        var3 = this.BP_CONTINUETRY_MENU_HEIGHT;
        MyAPI.drawBoldStrings(var1, var8, var2 + 20, var4 + 10, var5 - 20, var3 - 20, 16777215, 4656650, 0);
        var4 = SCREEN_WIDTH;
        var3 = this.BP_CONTINUETRY_MENU_START_Y;
        var2 = MENU_SPACE * 3 / 2;
        int var6 = MENU_SPACE;
        int var7 = PlayerObject.cursor;
        var5 = strForShow.length;
        drawMenuFontById(var1, 119, var4 >> 1, var3 + 15 + var2 + var6 * (var7 + var5 - 1));
        var5 = SCREEN_WIDTH;
        var2 = this.BP_CONTINUETRY_MENU_START_Y;
        var3 = MENU_SPACE * 3 / 2;
        var7 = MENU_SPACE;
        var4 = PlayerObject.cursor;
        var6 = strForShow.length;
        drawMenuFontById(var1, 113, (var5 >> 1) - 56, var2 + 15 + var3 + var7 * (var4 + var6 - 1));
        MyAPI.drawBoldString(var1, BPstrings[1], SCREEN_WIDTH >> 1, this.BP_CONTINUETRY_MENU_START_Y + 15 + MENU_SPACE * (strForShow.length - 1 + 1), 17, 16777215, 0);
        MyAPI.drawBoldString(var1, BPstrings[2], SCREEN_WIDTH >> 1, this.BP_CONTINUETRY_MENU_START_Y + 15 + MENU_SPACE * (strForShow.length - 1 + 2), 17, 16777215, 0);
        drawSoftKey(var1, true, false);
    }

    public void BP_continueTryInit() {
        this.state = 19;
        fadeInit(255, 0);
        MyAPI.initString();
        strForShow = MyAPI.getStrings(BPstrings[0], this.BP_CONTINUETRY_MENU_WIDTH - 20);
        this.BP_CONTINUETRY_MENU_HEIGHT = this.MORE_GAME_HEIGHT + (strForShow.length - 1) * LINE_SPACE;
        this.BP_CONTINUETRY_MENU_START_Y = SCREEN_HEIGHT - this.BP_CONTINUETRY_MENU_HEIGHT >> 1;
        PlayerObject.cursor = 0;
    }

    public void BP_continueTryLogic() {
        Key.touchkeyboardInit();
        PlayerObject.cursorMax = 2;
        boolean var1;
        if (Key.press(Key.gUp)) {
            var1 = true;
        } else {
            var1 = false;
        }

        boolean var2;
        if (Key.press(Key.gDown)) {
            var2 = true;
        } else {
            var2 = false;
        }

        if (var1) {
            --PlayerObject.cursor;
            PlayerObject.cursor += PlayerObject.cursorMax;
            PlayerObject.cursor %= PlayerObject.cursorMax;
        } else if (var2) {
            ++PlayerObject.cursor;
            PlayerObject.cursor %= PlayerObject.cursorMax;
        } else if (Key.press(Key.B_S1 | Key.gSelect)) {
            if (PlayerObject.cursor == 0) {
                this.state = 20;
                BP_enteredPaying = false;
                this.BP_payingInit(4, 3);
                this.BP_IsFromContinueTry = true;
                fadeInit(255, 0);
            } else if (PlayerObject.cursor == 1) {
                this.state = 5;
                fadeInit(255, 0);
            }
        }

    }

    public void BP_ensureToolsUseDraw(MFGraphics var1) {
        this.confirmDraw(var1, BPstrings[currentBPItems[PlayerObject.cursor] + 20]);
        drawSoftKey(var1, true, true);
    }

    public void BP_ensureToolsUseLogic() {
        switch (this.comfirmLogic()) {
            case 0:
                switch (currentBPItems[PlayerObject.cursor]) {
                    case 0:
                        GameObject.player.getItem(3);
                        if (GameObject.player2 != null) GameObject.player2.getItem(3);
                        break;
                    case 1:
                        GameObject.player.getItem(2);
                        if (GameObject.player2 != null) GameObject.player2.getItem(2);
                        break;
                    case 2:
                        GameObject.player.getItem(1);
                        if (GameObject.player2 != null) GameObject.player2.getItem(1);
                        break;
                    case 3:
                        GameObject.player.getItem(4);
                        if (GameObject.player2 != null) GameObject.player2.getItem(4);
                }

                byte[] var2 = BP_items_num;
                int var1 = currentBPItems[PlayerObject.cursor];
                --var2[var1];
                saveBPRecord();
                this.BacktoGame();
                break;
            case 1:
            case 400:
                this.state = 25;
        }

    }

    public void BP_gotoRanking() {
        PlayerObject.doPauseLeaveGame();
        this.setStateWithFade(4);
        int var1;
        if (!IsPaid) {
            StageManager.addNewNormalScore(0);
        } else {
            if (IsGameOver) {
                var1 = PlayerObject.getScore();
            } else {
                var1 = this.preScore;
            }

            StageManager.addNewNormalScore(var1);
        }

        if (IsGameOver) {
            var1 = StageManager.getStageID();
        } else {
            var1 = StageManager.getStageID() - 1;
        }

        StageManager.resetOpenedStageIdforTry(var1);
        Key.touchanykeyInit();
    }

    public void BP_gotoRevive() {
        this.state = 21;
        this.BP_payingInit(6, 5);
        fadeInit(255, 0);
    }

    public void BP_payingLogic() {
        if (!BP_enteredPaying) {
            if (BP_chargeLogic(0)) {
                BP_enteredPaying = true;
                if (!this.BP_IsFromContinueTry) {
                    setTry();
                }

                activeGameProcess(true);
                setMenu();
                saveBPRecord();
                if (IsGameOver) {
                    this.BP_gotoRanking();
                } else {
                    this.state = 5;
                    fadeInit(255, 0);
                }
            } else {
                BP_enteredPaying = true;
                if (!this.BP_IsFromContinueTry) {
                    setTry();
                }

                StageManager.resetStageIdforTry();
                saveBPRecord();
                setState(2);
            }
        }

    }

    public void BP_reviveLogic() {
        if (BP_chargeLogic(1)) {
            GameObject.player.resetPlayer();
            if (GameObject.player2 != null) GameObject.player2.resetPlayer();
            this.state = 0;
            fadeInit(255, 0);
        } else {
            this.gotoGameOver();
        }

    }

    public void BP_shopDraw(MFGraphics var1) {
        this.menuBgDraw(var1);
        fillMenuRect(var1, CASE_X, 30, CASE_WIDTH, CASE_HEIGHT);
        MFImage var11 = BP_wordsImg;
        int var2 = BP_wordsWidth;
        int var3 = BP_wordsHeight;
        int var4 = SCREEN_WIDTH;
        MyAPI.drawImage(var1, var11, 0, 0, var2, var3, 0, var4 >> 1, 40, 17);
        String var12 = BPstrings[10];
        var2 = CASE_X;
        var3 = CASE_WIDTH;
        var4 = LINE_SPACE;
        MyAPI.drawBoldString(var1, var12, (var3 >> 2) + var2, var4 + 40, 17, 16777215, 4656650);
        var12 = BPstrings[11];
        var4 = CASE_X;
        var2 = CASE_WIDTH;
        var3 = LINE_SPACE;
        MyAPI.drawBoldString(var1, var12, (var2 * 3 >> 2) + var4, var3 + 40, 17, 16777215, 4656650);

        int var5;
        int var6;
        int var7;
        for(var2 = 0; var2 < currentBPItems.length; ++var2) {
            var11 = BP_itemsImg;
            int var10 = currentBPItems[var2];
            var3 = BP_itemsWidth;
            int var9 = BP_itemsWidth;
            var6 = BP_itemsHeight;
            int var8 = CASE_X;
            var5 = CASE_WIDTH;
            var4 = LINE_SPACE;
            var7 = LINE_SPACE;
            MyAPI.drawImage(var1, var11, var3 * var10, 0, var9, var6, 0, var8 + (var5 >> 2) - (var4 >> 1), var7 * (var2 + 3) + 30, 3);
            var12 = "× " + currentBPItemsNormalNum[var2];
            var8 = CASE_X;
            var6 = CASE_WIDTH;
            var3 = LINE_SPACE;
            var4 = BP_itemsWidth;
            var7 = LINE_SPACE;
            var5 = Def.FONT_H_HALF;
            MyAPI.drawBoldString(var1, var12, var4 + (var8 + (var6 >> 2) - (var3 >> 1)), var7 * (var2 + 3) + 30 - var5, 20, 16777215, 4656650);
            var12 = "" + BP_items_num[currentBPItems[var2]];
            var6 = CASE_X;
            var7 = CASE_WIDTH;
            var4 = Def.FONT_WIDTH_NUM;
            var3 = LINE_SPACE;
            var5 = Def.FONT_H_HALF;
            MyAPI.drawBoldString(var1, var12, var4 * 2 + var6 + (var7 * 3 >> 2), var3 * (var2 + 3) + 30 - var5, 24, 16777215, 4656650);
        }

        var4 = CASE_X;
        var2 = CASE_WIDTH;
        var6 = LINE_SPACE;
        var7 = BP_itemsWidth;
        var5 = LINE_SPACE;
        var3 = PlayerObject.cursor;
        drawMenuFontById(var1, 113, var4 + (var2 >> 2) - (var6 >> 1) - var7 * 2, var5 * (var3 + 3) + 30);
        var12 = BPstrings[12];
        var2 = CASE_X;
        var3 = BP_itemsWidth;
        var4 = LINE_SPACE;
        MyAPI.drawBoldString(var1, var12, var3 + var2, var4 * 7 + 30, 20, 16777215, 4656650);
        var1.setColor(0);
        String[] var13 = BPEffectStrings[PlayerObject.cursor];
        var6 = CASE_X;
        var3 = BP_itemsWidth;
        var4 = LINE_SPACE;
        var5 = MENU_RECT_WIDTH;
        var2 = CASE_HEIGHT;
        MyAPI.drawBoldStrings(var1, var13, var3 + var6, var4 * 8 + 30, var5 - 20, var2 - 20, 16777215, 4656650, 0);
        drawSoftKey(var1, true, true);
    }

    public void BP_shopLogic() {
        this.IsInBP = false;
        Key.touchkeyboardInit();
        PlayerObject.cursorMax = 3;
        boolean var1;
        if (Key.press(Key.gUp)) {
            var1 = true;
        } else {
            var1 = false;
        }

        boolean var2;
        if (Key.press(Key.gDown)) {
            var2 = true;
        } else {
            var2 = false;
        }

        if (var1) {
            --PlayerObject.cursor;
            PlayerObject.cursor += PlayerObject.cursorMax;
            PlayerObject.cursor %= PlayerObject.cursorMax;
        } else if (var2) {
            ++PlayerObject.cursor;
            PlayerObject.cursor %= PlayerObject.cursorMax;
        } else if (Key.press(Key.B_S1 | Key.gSelect)) {
            if (!this.BP_IsToolsNumMax()) {
                this.state = 23;
                tool_id = PlayerObject.cursor;
                this.BP_payingInit(8, 7);
            }
        } else if (Key.press(2)) {
            this.state = 1;
            GameObject.IsGamePause = true;
            PlayerObject.cursor = 2;
            Key.clear();
        }

    }

    public void BP_toolsmaxDraw(MFGraphics var1) {
        this.menuBgDraw(var1);
        int var6 = FRAME_X;
        int var4 = SCREEN_HEIGHT;
        int var3 = MENU_SPACE;
        int var2 = FRAME_WIDTH;
        int var5 = MENU_SPACE;
        fillMenuRect(var1, var6, (var4 >> 1) - var3, var2, var5 << 1);
        var1.setColor(0);
        String var7 = BPstrings[19];
        var3 = SCREEN_WIDTH;
        var2 = SCREEN_HEIGHT;
        var4 = MENU_SPACE;
        MyAPI.drawBoldString(var1, var7, var3 >> 1, (var2 >> 1) - var4 + 10, 17, 16777215, 4656650);
        drawSoftKey(var1, false, true);
    }

    public void BP_toolsmaxLogic() {
        if (Key.press(2)) {
            this.shopInit(false);
        }

    }

    public void BP_toolsuseDraw(MFGraphics var1) {
        int var2 = SCREEN_WIDTH;
        int var6 = PlayerObject.PAUSE_FRAME_OFFSET_X;
        int var3 = SCREEN_HEIGHT;
        int var5 = PlayerObject.PAUSE_FRAME_OFFSET_Y;
        int var7 = PlayerObject.PAUSE_FRAME_WIDTH;
        int var4 = PlayerObject.PAUSE_FRAME_HEIGHT;
        fillMenuRect(var1, (var2 >> 1) + var6, (var3 >> 1) + var5, var7, var4);
        MFImage var15 = BP_wordsImg;
        var5 = BP_wordsHeight;
        var6 = BP_wordsWidth;
        var2 = BP_wordsHeight;
        var7 = SCREEN_WIDTH;
        var3 = SCREEN_HEIGHT;
        var4 = PlayerObject.PAUSE_FRAME_OFFSET_Y;
        int var8 = LINE_SPACE;
        MyAPI.drawImage(var1, var15, 0, var5, var6, var2, 0, var7 >> 1, var8 + (var3 >> 1) + var4, 3);
        var8 = SCREEN_WIDTH;
        var6 = SCREEN_HEIGHT;
        var5 = PlayerObject.PAUSE_FRAME_OFFSET_Y;
        var7 = MENU_SPACE;
        var3 = MENU_SPACE;
        var2 = MENU_SPACE;
        var4 = PlayerObject.cursor;
        drawMenuFontById(var1, 119, var8 >> 1, (var6 >> 1) + var5 + 10 + (var7 >> 1) + var3 + var2 * var4);
        var5 = SCREEN_WIDTH;
        var8 = SCREEN_HEIGHT;
        var7 = PlayerObject.PAUSE_FRAME_OFFSET_Y;
        var2 = MENU_SPACE;
        var6 = MENU_SPACE;
        var4 = MENU_SPACE;
        var3 = PlayerObject.cursor;
        drawMenuFontById(var1, 113, (var5 >> 1) - 56, (var8 >> 1) + var7 + 10 + (var2 >> 1) + var6 + var4 * var3);

        String var16;
        for(var2 = 0; var2 < currentBPItems.length; ++var2) {
            var15 = BP_itemsImg;
            var4 = currentBPItems[var2];
            var5 = BP_itemsWidth;
            if (BP_items_num[currentBPItems[var2]] != 0 && !this.IsToolsUsed(currentBPItems[var2])) {
                var3 = 0;
            } else {
                var3 = BP_itemsHeight;
            }

            int var9 = BP_itemsWidth;
            int var11 = BP_itemsHeight;
            var6 = SCREEN_WIDTH;
            var7 = BP_itemsWidth;
            int var14 = SCREEN_HEIGHT;
            int var10 = PlayerObject.PAUSE_FRAME_OFFSET_Y;
            var8 = MENU_SPACE;
            int var13 = MENU_SPACE;
            int var12 = MENU_SPACE;
            MyAPI.drawImage(var1, var15, var5 * var4, var3, var9, var11, 0, (var6 >> 1) - var7 * 2, var12 * var2 + (var14 >> 1) + var10 + 10 + (var8 >> 1) + var13, 6);
            var1.setColor(0);
            var6 = SCREEN_WIDTH;
            var7 = SCREEN_HEIGHT;
            var3 = PlayerObject.PAUSE_FRAME_OFFSET_Y;
            var8 = MENU_SPACE;
            var4 = MENU_SPACE;
            var9 = MENU_SPACE;
            var5 = Def.FONT_H_HALF;
            MyAPI.drawBoldString(var1, "×", var6 >> 1, (var7 >> 1) + var3 + 10 + (var8 >> 1) + var4 + var9 * var2 - var5, 17, 16777215, 4656650);
            var16 = "" + BP_items_num[currentBPItems[var2]];
            var5 = SCREEN_WIDTH;
            var3 = BP_itemsWidth;
            var7 = SCREEN_HEIGHT;
            var9 = PlayerObject.PAUSE_FRAME_OFFSET_Y;
            var4 = MENU_SPACE;
            var10 = MENU_SPACE;
            var6 = MENU_SPACE;
            var8 = Def.FONT_H_HALF;
            MyAPI.drawBoldString(var1, var16, var3 * 2 + (var5 >> 1), (var7 >> 1) + var9 + 10 + (var4 >> 1) + var10 + var6 * var2 - var8, 24, 16777215, 4656650);
        }

        if (BP_items_num[currentBPItems[PlayerObject.cursor]] == 0) {
            this.tooltipY = MyAPI.calNextPosition((double)this.tooltipY, (double)TOOL_TIP_Y_DES, 1, 3);
        } else {
            this.tooltipY = MyAPI.calNextPositionReverse(this.tooltipY, TOOL_TIP_Y_DES, TOOL_TIP_Y_DES_2, 1, 3);
        }

        fillMenuRect(var1, TOOL_TIP_X, this.tooltipY, TOOL_TIP_WIDTH, TOOL_TIP_HEIGHT);

        for(var2 = 0; var2 < TOOL_TIP_STR.length; ++var2) {
            var16 = TOOL_TIP_STR[var2];
            var3 = SCREEN_WIDTH;
            var5 = this.tooltipY;
            var4 = LINE_SPACE;
            MyAPI.drawBoldString(var1, var16, var3 >> 1, var4 * var2 + var5 + 10, 17, 16777215, 4656650, 0);
        }

        drawSoftKey(var1, true, true);
    }

    public void BP_toolsuseLogic() {
        Key.touchkeyboardInit();
        PlayerObject.cursorMax = 3;
        boolean var1;
        if (Key.press(Key.gUp)) {
            var1 = true;
        } else {
            var1 = false;
        }

        boolean var2;
        if (Key.press(Key.gDown)) {
            var2 = true;
        } else {
            var2 = false;
        }

        if (var1) {
            --PlayerObject.cursor;
            PlayerObject.cursor += PlayerObject.cursorMax;
            PlayerObject.cursor %= PlayerObject.cursorMax;
        } else if (var2) {
            ++PlayerObject.cursor;
            PlayerObject.cursor %= PlayerObject.cursorMax;
        }

        if (Key.press(Key.B_S1 | Key.gSelect) && BP_items_num[currentBPItems[PlayerObject.cursor]] > 0 && !this.IsToolsUsed(currentBPItems[PlayerObject.cursor])) {
            this.state = 26;
            this.cursor = 0;
        }

        if (Key.press(2)) {
            this.BacktoGame();
        }

    }

    public void BacktoGame() {
        this.doReturnGameStuff();
        isDrawTouchPad = true;
        Key.touchgamekeyInit();
        Key.touchkeyboardClose();
        Key.touchkeygameboardInit();
        if (Key.touchkey_pause != null) {
            Key.touchkey_pause.resetKeyState();
        }

        Key.touchkeyboardReset();
        Key.touchanykeyClose();
    }

    public boolean IsToolsUsed(int var1) {
        boolean var2;
        switch (var1) {
            case 0:
                var2 = PlayerObject.IsInvincibility();
                break;
            case 1:
            case 2:
                var2 = PlayerObject.IsUnderSheild();
                break;
            case 3:
                var2 = PlayerObject.IsSpeedUp();
                break;
            default:
                var2 = false;
        }

        return var2;
    }

    public void changeStateWithFade(int var1) {
        this.isStateClassSwitch = false;
        if (!fading) {
            fading = true;
            if (var1 == 7) {
                fadeInit(102, 255);
            } else {
                fadeInit(0, 255);
            }

            this.nextState = var1;
            this.fadeChangeState = true;
        }

    }

    public void close() {
        fadeInit(0, 255);
        MapManager.closeMap();
        releaseTouchkeyBoard();
        Animation.closeAnimationArray(this.stageInfoClearAni);
        this.stageInfoClearAni = null;
        Animation.closeAnimationDrawer(stageInfoAniDrawer);
        stageInfoAniDrawer = null;
        Animation.closeAnimationDrawer(this.stageInfoPlayerNameDrawer);
        this.stageInfoPlayerNameDrawer = null;
        Animation.closeAnimationDrawer(this.stageInfoActNumDrawer);
        this.stageInfoActNumDrawer = null;
        Animation.closeAnimation(guiAnimation);
        guiAnimation = null;
        Animation.closeAnimationDrawer(guiAniDrawer);
        guiAniDrawer = null;
        Animation.closeAnimationDrawer(numberDrawer);
        numberDrawer = null;
        Animation.closeAnimationDrawer(this.planeDrawer);
        this.planeDrawer = null;
        Animation.closeAnimationDrawer(this.cloudDrawer);
        this.cloudDrawer = null;
        Animation.closeAnimationDrawerArray(this.birdDrawer);
        this.birdDrawer = null;
        this.exendBgImage = null;
        this.exendBg1Image = null;
        Animation.closeAnimationDrawer(this.interruptDrawer);
        this.interruptDrawer = null;
        SoundSystem.getInstance().setSoundSpeed(1.0F);
        Key.init();
        GameObject.quitGameState();
        System.gc();

        try {
            Thread.sleep(100L);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void draw(MFGraphics var1) {
        switch (this.state) {
            case 34:
                var1.setFont(11);
                break;
            default:
                if (GlobalResource.languageConfig < 8) {
                    var1.setFont(11);
                } else {
                    var1.setFont(14);
                }
        }

        switch (this.state) {
            case 2:
                StageManager.draw(var1);
            case 3:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                break;
            case 4:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
            case 17:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 35:
            case 36:
            case 37:
            default:
                if (GameObject.IsGamePause) {
                    AnimationDrawer.setAllPause(true);
                }

                if (this.interrupt_state != 5 && this.interrupt_state != 14 && this.interrupt_state != 4) {
                    this.drawGame(var1);
                }

                if (GameObject.IsGamePause) {
                    AnimationDrawer.setAllPause(false);
                }

                if (this.state != 15 && this.state != 16 && this.state != 17 && this.state != 27) {
                    PlayerObject.drawGameUI(var1);
                    this.drawGameSoftKey(var1);
                }

                if (this.state == 1) {
                    drawFade(var1);
                    this.drawGamePause(var1);
                }

                if (this.state == 25) {
                    drawFade(var1);
                    this.BP_toolsuseDraw(var1);
                }

                if (this.state == 26) {
                    drawFade(var1);
                    this.BP_ensureToolsUseDraw(var1);
                }

                if (this.state == 15 || this.state == 27 || this.state == 16 || this.state == 17) {
                    if (this.state != 15 && this.state != 27) {
                        if (this.state == 16) {
                            drawFadeInSpeed(var1, 12);
                        } else {
                            drawFade(var1);
                        }
                    } else {
                        drawFadeInSpeed(var1, 4);
                    }

                    this.drawSaw(var1, this.display[0][0], this.display[0][1]);
                    this.drawWhiteBar(var1, this.display[1][0], this.display[1][1]);
                    this.drawTinyStageName(var1, StageManager.getStageID(), this.display[5][0], this.display[5][1]);
                    var1.setClip(this.display[1][0], this.display[1][1] - 10, SCREEN_WIDTH, 20);
                    this.drawHugeStageName(var1, StageManager.getStageID(), this.display[2][0], this.display[2][1]);
                    var1.setClip(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                    this.drawSonic(var1, this.display[3][0], this.display[3][1]);
                    this.drawAction(var1, StageManager.getStageID(), this.display[4][0], this.display[4][1]);
                }

                if (this.state == 36 || this.state == 37) {
                    var1.setColor(0);
                    MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                    if (loadingType == 2) {
                        var1.setColor(16777215);
                        MyAPI.fillRect(var1, 0, (SCREEN_HEIGHT >> 1) - 36 - 10, SCREEN_WIDTH, 20);
                    }
                }

                if (this.state == 8) {
                    this.drawGamePause(var1);
                    drawFade(var1);
                    this.SecondEnsurePanelDraw(var1, 15);
                }

                if (this.state == 9) {
                    this.drawGamePause(var1);
                    drawFade(var1);
                    this.SecondEnsurePanelDraw(var1, 17);
                }

                if (this.state == 29) {
                    this.drawGamePause(var1);
                    drawFade(var1);
                    this.SecondEnsurePanelDraw(var1, 16);
                }

                if (this.state == 10) {
                    this.drawGamePause(var1);
                    drawFade(var1);
                    this.SecondEnsurePanelDraw(var1, 18);
                }

                if (this.state == 11 || this.state == 41) {
                    this.drawTimeOver(var1, this.movingTitleX, (SCREEN_HEIGHT >> 1) - 28);
                }

                if (this.state == 42) {
                    drawFade(var1);
                    this.drawGameOver(var1);
                }

                if (this.state == 12) {
                    drawFade(var1);
                    this.drawTimeOver(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 28);
                }

                if (this.state == 40) {
                    drawFade(var1);
                }

                if (this.state == 4) {
                    PlayerObject.stagePassDraw(var1);
                }

                if (this.state == 35) {
                    drawFade(var1);
                    if (loadingType == 2) {
                        var1.setColor(16777215);
                        MyAPI.fillRect(var1, 0, (SCREEN_HEIGHT >> 1) - 36 - 10, SCREEN_WIDTH, 20);
                    }
                }
                break;
            case 5:
                drawFadeSlow(var1);
                this.drawLoading(var1);
                break;
            case 6:
                this.helpDraw(var1);
                break;
            case 7:
                this.optionDraw(var1);
                break;
            case 13:
                var1.setColor(0);
                MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                if (!ChargePlatform.isChargedByIndex(0)) {
                    this.drawGameOverSingle(var1);
                } else {
                    this.drawTimeOver(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 28);
                }
                break;
            case 14:
                MyAPI.drawImage(var1, this.exendBgImage, SCREEN_WIDTH >> 1, 0, 17);
                MFImage var3 = this.exendBg1Image;
                int var2 = SCREEN_WIDTH;
                MyAPI.drawImage(var1, var3, var2 >> 1, 80, 17);
                drawFade(var1);
                if (fadeChangeOver() && this.allclearFrame > 20) {
                    PlayerObject.stagePassDraw(var1);
                }
                break;
            case 18:
                this.interruptDraw(var1);
                break;
            case 30:
                this.optionDraw(var1);
                this.touchPadPositionDraw(var1);
                break;
            case 31:
                this.optionDraw(var1);
                this.itemsSelect4Draw(var1);
                break;
            case 32:
                optionDraw(var1);
            case 33:
                this.optionDraw(var1);
                this.touchPadOpacityDraw(var1);
                break;
            case 34:
                this.helpDraw(var1);
                break;
            case 38:
                this.optionDraw(var1);
                this.touchPadSizeDraw(var1);
                break;
            case 39:
                this.optionDraw(var1);
                this.itemsSelect3Draw(var1);
        }

        if (isDrawTouchPad) {
            if (this.state != 5 && this.state != 14) {
                this.drawTouchKeyDirect(var1);
            }

            if (this.state == 14 && this.endingState > 3) {
                this.drawTouchKeyDirect(var1);
            }
        }

    }

    public void drawGame(MFGraphics var1) {
        var1.setColor(16777215);
        MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        if (!GameObject.IsGamePause) {
            ++MapManager.gameFrame;
        }

        BackGroundManager.frame = MapManager.gameFrame;
        MapManager.drawBack(var1);
        GameObject.drawObjectBeforeSonic(var1);
        GameObject.drawPlayer(var1);
        Effect.draw(var1, 1);
        GameObject.drawObjects(var1);
        GameObject.player.drawSheild1(var1);
        if (GameObject.player2 != null) GameObject.player.drawSheild1(var1);
        MapManager.drawFront(var1);
        GameObject.player.draw2(var1);
        GameObject.player.drawSheild2(var1);
        if (GameObject.player2 != null) {
        GameObject.player2.draw2(var1);
        GameObject.player2.drawSheild2(var1);
        }
        GameObject.drawObjectAfterEveryThing(var1);
        MapManager.drawFrontNatural(var1);
        Effect.draw(var1, 0);
        MapManager.drawMapFrame(var1);
        RocketSeparateEffect.getInstance().draw(var1);
    }

    public void drawGameSoftKey(MFGraphics var1) {
        if (this.state != 1 && this.state != 10 && this.state != 8 && this.state != 9 && this.state != 29 && this.state != 25 && this.state != 26 && this.state != 41 && this.state != 42 && this.state != 11 && this.state != 12 && this.state != 13 && !StageManager.isStagePassTimePause()) {
            if (!GameObject.player.isDead) {
                State.drawSoftKeyPause(var1);
            }

            if (this.state != 25 && this.state != 26 && this.state != 1 && this.state != 6 && this.state != 7 && this.state != 8 && this.state != 9 && this.state != 29 && this.state != 10 && IsToolsCharge() && GameObject.stageModeState == 0 && !GameObject.player.isDead) {
                MFImage var7 = BP_wordsImg;
                int var5 = BP_wordsHeight;
                int var6 = BP_wordsWidth;
                int var3 = BP_wordsHeight;
                int var2 = tool_x;
                int var4 = tool_y;
                MyAPI.drawImage(var1, var7, 0, var5, var6, var3, 0, var2, var4, 36);
            }
        }

    }

    public void fadeStateLogic() {
        if (this.isStateClassSwitch) {
            if (fading && fadeChangeOver()) {
                setState(this.stateForSet);
                fadeInit(255, 0);
            }
        } else {
            if (fading && this.fadeChangeState && fadeChangeOver() && this.state != this.nextState) {
                this.state = this.nextState;
                this.fadeChangeState = false;
                if (this.state == 1) {
                    fadeInit_Modify(255, 102);
                } else {
                    fadeInit(255, 0);
                }
            }

            if (this.state == this.nextState && fadeChangeOver()) {
                fading = false;
            }
        }

    }

    public void gamepauseLogic() {
        Key.touchkeyboardInit();
        if (PlayerObject.stageModeState == 0) {
            byte var1;
            if (IsToolsCharge()) {
                var1 = 5;
            } else {
                var1 = 4;
            }

            PlayerObject.cursorMax = var1;
            Key.touchPauseInit(false);
        } else if (PlayerObject.stageModeState == 1) {
            PlayerObject.cursorMax = 6;
            Key.touchPauseInit(true);
        }

        boolean var2;
        boolean var3;
        if (PlayerObject.stageModeState == 1) {
            if ((!Key.touchpausearrowup.Isin() || IsSingleUp || IsSingleDown) && (!Key.touchpausearrowupsingle.Isin() || !IsSingleUp || IsSingleDown) && !Key.press(Key.gUp)) {
                var2 = false;
            } else {
                var2 = true;
            }

            if ((!Key.touchpausearrowdown.Isin() || IsSingleUp || IsSingleDown) && (!Key.touchpausearrowdownsingle.Isin() || IsSingleUp || !IsSingleDown) && !Key.press(Key.gDown)) {
                var3 = false;
            } else {
                var3 = true;
            }
        } else {
            if (Key.press(Key.gUp)) {
                var2 = true;
            } else {
                var2 = false;
            }

            if (Key.press(Key.gDown)) {
                var3 = true;
            } else {
                var3 = false;
            }
        }

        if (PlayerObject.stageModeState == 0) {
            if (Key.touchpause1.IsClick() && PlayerObject.cursor == PlayerObject.cursorIndex + 0 || Key.touchpause2.IsClick() && PlayerObject.cursor == PlayerObject.cursorIndex + 1 || Key.touchpause3.IsClick() && PlayerObject.cursor == PlayerObject.cursorIndex + 2 || Key.touchpause4.IsClick() && PlayerObject.cursor == PlayerObject.cursorIndex + 3) {
                switch (PAUSE_NORMAL_MODE[PlayerObject.cursor]) {
                    case 0:
                        this.BacktoGame();
                        break;
                    case 1:
                        this.state = 10;
                        this.cursor = 1;
                        break;
                    case 2:
                        this.shopInit(true);
                        break;
                    case 3:
                        this.optionInit();
                        this.state = 7;
                        break;
                    case 4:
                        this.helpInit();
                        this.state = 6;
                }

                Key.touchPauseClose(false);
            } else if (Key.touchpause1.IsClick()) {
                PlayerObject.cursor = PlayerObject.cursorIndex + 0;
                Key.touchpause1.reset();
            } else if (Key.touchpause2.IsClick()) {
                PlayerObject.cursor = PlayerObject.cursorIndex + 1;
                Key.touchpause2.reset();
            } else if (Key.touchpause3.IsClick()) {
                PlayerObject.cursor = PlayerObject.cursorIndex + 2;
                Key.touchpause3.reset();
            } else if (Key.touchpause4.IsClick()) {
                PlayerObject.cursor = PlayerObject.cursorIndex + 3;
                Key.touchpause4.reset();
            }
        } else if (PlayerObject.stageModeState == 1) {
            if ((!Key.touchpause1.IsClick() || PlayerObject.cursor != PlayerObject.cursorIndex + 0) && (!Key.touchpause2.IsClick() || PlayerObject.cursor != PlayerObject.cursorIndex + 1) && (!Key.touchpause3.IsClick() || PlayerObject.cursor != PlayerObject.cursorIndex + 2) && (!Key.touchpause4.IsClick() || PlayerObject.cursor != PlayerObject.cursorIndex + 3)) {
                if (Key.touchpause1.IsClick()) {
                    PlayerObject.cursor = PlayerObject.cursorIndex + 0;
                    Key.touchpause1.reset();
                } else if (Key.touchpause2.IsClick()) {
                    PlayerObject.cursor = PlayerObject.cursorIndex + 1;
                    Key.touchpause2.reset();
                } else if (Key.touchpause3.IsClick()) {
                    PlayerObject.cursor = PlayerObject.cursorIndex + 2;
                    Key.touchpause3.reset();
                } else if (Key.touchpause4.IsClick()) {
                    PlayerObject.cursor = PlayerObject.cursorIndex + 3;
                    Key.touchpause4.reset();
                }
            } else {
                switch (PlayerObject.cursor) {
                    case 0:
                        this.BacktoGame();
                        break;
                    case 1:
                        this.state = 8;
                        this.cursor = 1;
                        break;
                    case 2:
                        this.state = 9;
                        this.cursor = 0;
                        break;
                    case 3:
                        this.state = 10;
                        this.cursor = 1;
                        break;
                    case 4:
                        this.optionInit();
                        this.state = 7;
                        this.cursor = 0;
                        break;
                    case 5:
                        this.helpInit();
                        this.state = 6;
                        this.cursor = 0;
                        break;
                    case 29:
                        this.state = 29;
                        this.cursor = 0;
                }

                Key.touchPauseClose(true);
            }
        }

        if (var2) {
            --PlayerObject.cursor;
            PlayerObject.cursor += PlayerObject.cursorMax;
            PlayerObject.cursor %= PlayerObject.cursorMax;
        } else if (var3) {
            ++PlayerObject.cursor;
            PlayerObject.cursor %= PlayerObject.cursorMax;
        } else if (Key.press(Key.gSelect | Key.B_S1)) {
            if (PlayerObject.stageModeState == 0) {
                switch (PAUSE_NORMAL_MODE[PlayerObject.cursor]) {
                    case 0:
                        this.BacktoGame();
                        break;
                    case 1:
                        this.state = 10;
                        this.cursor = 1;
                        break;
                    case 2:
                        this.shopInit(true);
                        break;
                    case 3:
                        this.optionInit();
                        this.state = 7;
                        break;
                    case 4:
                        this.helpInit();
                        this.state = 6;
                }
            } else if (PlayerObject.stageModeState == 1) {
                switch (PlayerObject.cursor) {
                    case 0:
                        this.BacktoGame();
                        break;
                    case 1:
                        this.state = 8;
                        this.cursor = 1;
                        break;
                    case 2:
                        this.state = 9;
                        this.cursor = 0;
                        break;
                    case 3:
                        this.state = 10;
                        this.cursor = 1;
                        break;
                    case 4:
                        this.optionInit();
                        this.state = 7;
                        this.cursor = 0;
                        break;
                    case 5:
                        this.helpInit();
                        this.state = 6;
                        this.cursor = 0;
                        break;
                    case 29:
                        this.state = 29;
                        this.cursor = 0;
                }
            }

            Key.clear();
        }

        if (Key.press(2)) {
        }

        if (Key.press(524288)) {
            this.doReturnGameStuff();
            if (PlayerObject.stageModeState == 0) {
                Key.touchPauseClose(false);
            } else if (PlayerObject.stageModeState == 1) {
                Key.touchPauseClose(true);
            }

            Key.touchgamekeyInit();
            Key.touchkeyboardClose();
            Key.touchkeygameboardInit();
            Key.clear();
        }

    }

    public void gotoGameOver() {
        IsGameOver = true;
        this.overcnt = 0;
        this.state = 28;
        this.overtitleID = 78;
        SoundSystem.getInstance().stopBgm(true);
        SoundSystem.getInstance().playBgm(30, false);
        this.movingTitleX = SCREEN_WIDTH + 30;
        Key.touchgamekeyClose();
        Key.touchkeygameboardClose();
        Key.touchkeyboardInit();
    }

    public void init() {
        initTouchkeyBoard();
        Key.initSonic();
        tipsForShow = null;
    }

    public void interruptDraw(MFGraphics var1) {
        AnimationDrawer var3 = this.interruptDrawer;
        byte var2;
        if (Key.touchinterruptreturn.Isin() || Key.repeat(16777216 | 8388608)) {
            var2 = 1;
        } else {
            var2 = 0;
        }

        var3.setActionId(var2 + 0);
        this.interruptDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
    }

    public void interruptLogic() {
        SoundSystem.getInstance().stopBgm(false);
        if (Key.press(2)) {
        }

        if (Key.buttonPress(16777216 | 8388608) || Key.touchinterruptreturn != null && Key.touchinterruptreturn.IsButtonPress()) {
            fading = lastFading;
            SoundSystem.getInstance().playSe(2);
            Key.touchInterruptClose();
            this.state = this.interrupt_state;
            this.interrupt_state = -1;
            Key.clear();
            if (Key.touchitemsselect2_1 != null) {
                Key.touchitemsselect2_1.reset();
            }

            if (Key.touchitemsselect2_2 != null) {
                Key.touchitemsselect2_2.reset();
            }

            if (Key.touchitemsselect3_1 != null) {
                Key.touchitemsselect3_1.reset();
            }

            if (Key.touchitemsselect3_2 != null) {
                Key.touchitemsselect3_2.reset();
            }

            if (Key.touchitemsselect3_3 != null) {
                Key.touchitemsselect3_3.reset();
            }

            if (Key.touchitemsselect4_1 != null) {
                Key.touchitemsselect4_1.reset();
            }

            if (Key.touchitemsselect4_2 != null) {
                Key.touchitemsselect4_2.reset();
            }

            if (Key.touchitemsselect4_3 != null) {
                Key.touchitemsselect4_3.reset();
            }

            if (Key.touchitemsselect4_4 != null) {
                Key.touchitemsselect4_4.reset();
            }

            isDrawTouchPad = true;
            switch (this.state) {
                case 4:
                case 35:
                    isDrawTouchPad = false;
                case 5:
                case 6:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 36:
                case 37:
                case 40:
                default:
                    break;
                case 7:
                    this.optionInit();
                    fadeInit(0, 0);
                    isDrawTouchPad = false;
                    break;
                case 8:
                case 9:
                case 10:
                case 29:
                    isDrawTouchPad = false;
                    break;
                case 11:
                case 12:
                case 13:
                    isDrawTouchPad = false;
                    break;
                case 14:
                case 41:
                case 42:
                    isDrawTouchPad = false;
                    break;
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 38:
                case 39:
                    isDrawTouchPad = false;
                    SoundSystem.getInstance().playBgm(5);
            }

            IsInInterrupt = false;
        }

    }

    public void logic() {
        int var2 = 0;
        if (this.state != 18) {
            this.fadeStateLogic();
        }

        if (Key.press(536870912) && this.state != 0) {
            pause();
        }

        int var1;
        int[] var3;
        switch (this.state) {
            case 0:
                if (!StageManager.isStagePassTimePause() && !GameObject.IsGamePause) {
                    PlayerObject.timeLogic();
                }

                GameObject.logicObjects();
                StageManager.stageLogic();
                if (Key.buttonPress(2)) {
                }

                if ((Key.touchkey_pause.IsButtonPress() || Key.press(524288 | 536870912)) && fadeChangeOver() && !GameObject.player.isDead && this.pauseAvailable == true) {
                    if (!StageManager.isStagePassTimePause()) {
                        PlayerObject.gamepauseInit();
                        this.state = 1;
                        this.gamePauseInit();
                        isDrawTouchPad = false;
                        SoundSystem.getInstance().playSe(33);
                        Key.init();
                        GameObject.IsGamePause = true;
                        fadeInit_Modify(0, 102);
                        SoundSystem.getInstance().stopBgm(false);
                        SoundSystem.getInstance().stopLongSe();
                        SoundSystem.getInstance().stopLoopSe();
                        this.pauseoptionCursor = GlobalResource.soundConfig;
                        Key.touchgamekeyClose();
                        Key.touchkeygameboardClose();
                        Key.touchkeyboardInit();
                    }
                } else if (!Key.press(Key.B_0) && !Key.press(Key.B_PO) && Key.press(Key.B_S1) && IsToolsCharge() && GameObject.stageModeState == 0 && !GameObject.player.isDead && !StageManager.isStagePassTimePause()) {
                    PlayerObject.gamepauseInit();
                    this.state = 25;
                    this.tooltipY = TOOL_TIP_Y_DES_2;
                    Key.init();
                    GameObject.IsGamePause = true;
                    fadeInit(0, 102);
                    SoundSystem.getInstance().stopBgm(false);
                    this.pauseoptionCursor = GlobalResource.soundConfig;
                    Key.touchgamekeyClose();
                    Key.touchkeygameboardClose();
                    Key.touchkeyboardInit();
                }

                if (!StageManager.isStagePass()) {
                    PlayerObject.initMovingBar();
                    this.cnt = 0;
                    PlayerObject.IsStarttoCnt = false;
                    StageManager.IsCalculateScore = true;
                    PlayerObject.IsDisplayRaceModeNewRecord = false;
                } else {
                    this.state = 4;
                    isDrawTouchPad = false;
                    if (StageManager.IsStageEnd()) {
                        PlayerObject.isbarOut = true;
                        this.state = 40;
                        setFadeColor(16777215);
                        fadeInit(0, 255);
                    }
                }

                if (StageManager.isStageRestart()) {
                    this.state = 5;
                    loadingType = 0;
                    Key.touchgamekeyClose();
                    Key.touchkeygameboardClose();
                    Key.touchkeyboardInit();
                    this.initTips();
                    fadeInit(255, 0);
                /*} else {
                    while(true) {
                        if (StageManager.isStageGameover()) {
                            if (IsToolsCharge() && PlayerObject.stageModeState == 0) {
                                this.BP_gotoRevive();
                            } else {
                                this.gotoGameOver();
                            }
                        }

                        if (!StageManager.isStageTimeover()) {
                            break;
                        }

                        IsTimeOver = true;
                        this.overcnt = 0;
                        this.state = 28;
                        this.movingTitleX = SCREEN_WIDTH + 56;
                        if (!StageManager.isStageGameover()) {
                            this.overtitleID = 136;
                            SoundSystem.getInstance().playSe(40);
                        } else {
                            IsTimeOver = false;
                            IsGameOver = true;
                            SoundSystem.getInstance().stopBgm(true);
                            SoundSystem.getInstance().playBgm(30, false);
                        }

                        if (!ChargePlatform.isChargedByIndex(0)) {
                            if (IsTimeOver) {
                                SoundSystem.getInstance().stopBgm(true);
                                SoundSystem.getInstance().playBgm(30, false);
                            }

                            Key.touchgamekeyClose();
                            Key.touchkeygameboardClose();
                            Key.touchkeyboardInit();
                            break;
                        }
                    }

                    if (IsGameOver) {
                        this.overtitleID = 78;
                    }

                    if (this.overtitleID == 78) {
                        this.movingTitleSpeedX = 24;
                    } else if (this.overtitleID == 136) {
                        this.movingTitleSpeedX = 8;
                    }
                }*/
                } else {
                    if (StageManager.isStageGameover()) {
                        if (IsToolsCharge() && PlayerObject.stageModeState == 0) {
                            BP_gotoRevive();
                        } else {
                            gotoGameOver();
                        }
                    }
                    if (StageManager.isStageTimeover()) {
                        IsTimeOver = true;
                        this.overcnt = 0;
                        this.state = 28;
                        this.movingTitleX = SCREEN_WIDTH + 56;
                        if (!StageManager.isStageGameover()) {
                            this.overtitleID = 136;
                            SoundSystem.getInstance().playSe(40);
                        } else {
                            IsTimeOver = false;
                            IsGameOver = true;
                            SoundSystem.getInstance().stopBgm(true);
                            SoundSystem.getInstance().playBgm(30, false);
                        }
                        if (ChargePlatform.isChargedByIndex(0) && IsTimeOver) {
                            SoundSystem.getInstance().stopBgm(true);
                            SoundSystem.getInstance().playBgm(30, false);
                        }
                        Key.touchgamekeyClose();
                        Key.touchkeygameboardClose();
                        Key.touchkeyboardInit();
                    }
                    if (IsGameOver) {
                        this.overtitleID = 78;
                    }
                    if (this.overtitleID == 78) {
                        this.movingTitleSpeedX = 24;
                        break;
                    } else if (this.overtitleID == 136) {
                        this.movingTitleSpeedX = 8;
                        break;
                    }
                }
                break;
            case 1:
                this.gamePauseLogic();
            case 2:
            case 3:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            default:
                break;
            case 4:
                this.stagePassLogic();
                if (StageManager.getStageID() != 12 || StageManager.isGoingToExtraStage()) {
                    GameObject.logicObjects();
                }

                StageManager.stageLogic();
                if (StageManager.isStagePassTimePause() && StageManager.IsCalculateScore) {
                    StageManager.IsCalculateScore = false;
                    PlayerObject.calculateScore();
                }
                break;
            case 5:
                PlayerObject.isTerminal = false;
                if (SoundSystem.getInstance().bgmPlaying()) {
                    SoundSystem.getInstance().stopBgm(false);
                }

                PreGame = true;
                if (this.loadingEnd()) {
                    Animation.closeAnimationDrawer(this.loadingWordsDrawer);
                    this.loadingWordsDrawer = null;
                    Animation.closeAnimationDrawer(this.loadingDrawer);
                    this.loadingDrawer = null;
                    Animation.closeAnimationDrawer(this.skipDrawer);
                    this.skipDrawer = null;
                    System.gc();
                    if (isThroughGame) {
                        isThroughGame = false;
                    }

                    if (!isBackFromSpStage && GameObject.stageModeState != 1) {
                        StageManager.characterFromGame = PlayerObject.getCharacterID();
                        StageManager.stageIDFromGame = StageManager.getStageID();
                        StageManager.saveStageRecord();
                    }

                    StageManager.isSaveTimeModeScore = false;
                    MapManager.setFocusObj(GameObject.player);
                    GameObject.player.headInit();
                    if (GameObject.player2 != null) GameObject.player2.headInit();
                    GameObject.bossFighting = false;
                    GameObject.isUnlockCage = false;
                    EnemyObject.isBossEnter = false;
                    if (isBackFromSpStage) {
                        PlayerObject.initSpParam(spReserveRingNum, spCheckPointID, spTimeCount);
                        isBackFromSpStage = false;
                    }

                    if (StageManager.getStageID() != 10 || !PlayerObject.isDeadLineEffect) {
                        PlayerObject.isDeadLineEffect = false;
                    }

                    Key.initSonic();
                    IsGameOver = false;
                    isLoadingSkipped = false;
                    if (!StageManager.isNextGameStageDirectedly) {
                        this.state = 36;
                        this.initStageIntroType1Conf();
                    } else {
                        this.state = 36;
                        this.initStageIntroType1Conf();
                    }

                    this.initStageInfoClearRes();
                    var1 = SCREEN_HEIGHT;
                    var2 = SCREEN_WIDTH;
                    PlayerObject.clipMoveInit(0, (var1 >> 1) - 10, 20, var2, 60);
                    Key.touchkeypauseInit();
                    Key.touchkeyboardClose();
                    Key.touchOpeningClose();
                    Key.touchkeygameboardInit();
                    StageManager.stagePassInit();
                } else {
                    if (Key.touchopeningskip.Isin() && Key.touchopening.IsClick()) {
                        this.opengingCursor = 0;
                    }
                    if (Key.touchopeningskip.IsButtonPress() || Key.buttonPress(Key.B_S1 | 16777216 | 8388608)) {
                        isLoadingSkipped = true;
                        loadingStartTime = 10000L;
                    }
                }
                break;
            case 6:
                this.helpLogic();
                if (Key.press(2)) {
                }

                if (Key.press(524288)) {
                    this.state = 1;
                    GameObject.IsGamePause = true;
                    Key.clear();
                    this.isOptionDisFlag = false;
                }
                break;
            case 7:
                this.optionLogic();
                break;
            case 8:
                this.retryStageLogic();
                break;
            case 9:
                this.pausetoSelectStageLogic();
                break;
            case 10:
                this.pausetoTitleLogic();
                break;
            case 11:
                if (this.movingTitleX - this.movingTitleSpeedX < SCREEN_WIDTH >> 1) {
                    this.movingTitleX = SCREEN_WIDTH;
                    fadeInit(0, 255);
                    this.state = 12;
                } else {
                    this.movingTitleX -= this.movingTitleSpeedX;
                }
                break;
            case 12:
                if (fadeChangeOver()) {
                    this.state = 13;
                    if (!ChargePlatform.isChargedByIndex(0)) {
                        MFDevice.setResponseInterruptFlag(false);
                        ChargePlatform.chargeByIndex(0);
                        MFDevice.setResponseInterruptFlag(true);
                        Standard2.splashinit(true);
                        this.setStateWithFade(0);
                    }

                    this.gameoverCnt = 0;
                }
                break;
            case 13:
                if (fadeChangeOver()) {
                    if (IsGameOver) {
                        if (!SoundSystem.getInstance().bgmPlaying() && GlobalResource.soundSwitchConfig != 0 || GlobalResource.soundSwitchConfig == 0 && this.gameoverCnt == 128) {
                            if (PlayerObject.stageModeState == 0) {
                                Standard2.splashinit(true);
                                this.setStateWithFade(0);
                            } else if (PlayerObject.stageModeState == 1) {
                                this.setStateWithFade(3);
                            }

                            Key.touchkeyboardClose();
                            Key.touchkeyboardInit();
                        } else {
                            ++this.gameoverCnt;
                        }
                    }

                    if (IsTimeOver) {
                        if (this.gameoverCnt == 128) {
                            this.state = 5;
                            loadingType = 0;
                            StageManager.setStageRestart();
                            StageManager.checkPointTime = 0;
                            fadeInit(255, 0);
                            this.initTips();
                        } else {
                            ++this.gameoverCnt;
                        }
                    }
                }
                break;
            case 14:
                if (fadeChangeOver()) {
                    ++this.allclearFrame;
                    if (this.allclearFrame > 20) {
                        this.stagePassLogic();
                    }
                }
                break;
            case 15:
                if (this.frameCount < 10) {
                    ++this.frameCount;
                } else {
                    this.releaseTips();
                    this.state = 16;
                    Key.touchanykeyInit();
                    fadeInit(204, 0);
                }

                if (this.frameCount >= 1 && this.frameCount <= 3) {
                    this.display[0][2] = 30;
                } else {
                    this.display[0][2] = 0;
                }

                if (this.frameCount >= 4 && this.frameCount <= 7) {
                    this.display[1][2] = -96;
                } else {
                    this.display[1][2] = 0;
                }

                if (this.frameCount >= 8 && this.frameCount <= 10) {
                    this.display[2][2] = -104;
                } else {
                    this.display[2][2] = 0;
                }

                if (this.frameCount >= 6 && this.frameCount <= 7) {
                    this.display[5][2] = -104;
                } else {
                    this.display[5][2] = 0;
                }

                if (this.frameCount == 7) {
                    this.IsPlayerNameDrawable = true;
                    this.stageInfoPlayerNameDrawer.restart();
                }

                if (this.frameCount == 9) {
                    this.IsActNumDrawable = true;
                    this.stageInfoActNumDrawer.restart();
                }

                if (this.display[0][0] + this.display[0][2] > 0) {
                    this.display[0][0] = 0;
                } else {
                    var3 = this.display[0];
                    var3[0] += this.display[0][2];
                }

                var3 = this.display[0];
                var3[1] += this.display[0][3];
                var3 = this.display[1];
                var3[0] += this.display[1][2];
                if (this.display[1][0] < 0) {
                    this.display[1][0] = 0;
                }

                var3 = this.display[1];
                var3[1] += this.display[1][3];
                var3 = this.display[2];

                for(var3[0] += this.display[2][2]; this.display[2][0] < 0; var3[0] += 224) {
                    var3 = this.display[2];
                }

                var3 = this.display[2];
                var3[0] %= 224;
                var3 = this.display[2];
                var3[1] += this.display[2][3];
                var3 = this.display[4];
                var3[0] += this.display[4][2];
                if (this.display[5][0] + this.display[5][2] < SCREEN_WIDTH - 112) {
                    this.display[5][0] = SCREEN_WIDTH - 112;
                } else {
                    var3 = this.display[5];
                    var3[0] += this.display[5][2];
                }

                if (this.state == 16) {
                    this.frameCount = 0;
                }

                GameObject.setNoInput();
                GameObject.logicObjects();
                break;
            case 16:
                if (this.frameCount < 48) {
                    ++this.frameCount;
                } else {
                    this.state = 17;
                    Key.clear();
                    Key.touchanykeyClose();
                }

                this.display[2][2] = -7;
                this.display[4][3] = 0;
                var3 = this.display[0];
                var3[0] += this.display[0][2];
                var3 = this.display[0];
                var3[1] += this.display[0][3];
                var3 = this.display[1];
                var3[0] += this.display[1][2];
                var3 = this.display[1];
                var3[1] += this.display[1][3];
                var3 = this.display[2];

                for(var3[0] += this.display[2][2]; this.display[2][0] < 0; var3[0] += 224) {
                    var3 = this.display[2];
                }

                var3 = this.display[2];
                var3[0] %= 224;
                var3 = this.display[2];
                var3[1] += this.display[2][3];
                var3 = this.display[3];
                var3[0] += this.display[3][2];
                var3 = this.display[3];
                var3[1] += this.display[3][3];
                var3 = this.display[4];
                var3[0] += this.display[4][2];
                var3 = this.display[4];
                var3[1] += this.display[4][3];
                var3 = this.display[5];
                var3[0] += this.display[5][2];
                if (Key.press(Key.B_SEL | 16777216 | 8388608)) {
                    this.state = 17;
                    Key.clear();
                    Key.touchanykeyClose();
                    fadeInit(getCurrentFade(), 0);
                }

                if (this.state == 17) {
                    this.frameCount = 0;
                }

                GameObject.setNoInput();
                GameObject.logicObjects();
                break;
            case 17:
                if (this.frameCount < 3) {
                    ++this.frameCount;
                } else {
                    PlayerObject.isNeedPlayWaterSE = true;
                    this.state = 0;
                    PreGame = false;
                }

                this.display[0][2] = -30;
                this.display[1][2] = -97;
                this.display[2][2] = -105;
                this.display[5][2] = -105;
                this.display[3][3] = -75;
                this.display[4][3] = 45;
                var3 = this.display[0];
                var3[0] += this.display[0][2];
                var3 = this.display[0];
                var3[1] += this.display[0][3];
                var3 = this.display[1];
                var3[0] += this.display[1][2];
                var3 = this.display[1];
                var3[1] += this.display[1][3];
                var3 = this.display[2];
                var3[0] += this.display[2][2];
                var3 = this.display[5];
                var3[0] += this.display[5][2];
                var3 = this.display[2];
                var3[1] += this.display[2][3];
                var3 = this.display[3];
                var3[0] += this.display[3][2];
                var3 = this.display[3];
                var3[1] += this.display[3][3];
                var3 = this.display[4];
                var3[0] += this.display[4][2];
                var3 = this.display[4];
                var3[1] += this.display[4][3];
                if (this.state == 0) {
                    isDrawTouchPad = true;
                    this.frameCount = 0;
                    fadeInit(102, 0);
                    setFadeOver();
                }

                GameObject.setNoInput();
                GameObject.logicObjects();
                break;
            case 18:
                this.interruptLogic();
                break;
            case 27:
                if (this.frameCount < 18) {
                    ++frameCount;
                } else {
                    this.releaseTips();
                    this.state = 16;
                    Key.touchanykeyInit();
                    fadeInit(204, 0);
                }

                if (this.frameCount >= 9 && this.frameCount <= 11) {
                    this.display[0][2] = 30;
                } else {
                    this.display[0][2] = 0;
                }

                if (this.frameCount >= 12 && this.frameCount <= 15) {
                    this.display[1][3] = 28;
                } else {
                    this.display[1][3] = 0;
                }

                if (this.frameCount >= 16 && this.frameCount <= 18) {
                    this.display[2][2] = -104;
                } else {
                    this.display[2][2] = 0;
                }

                if (this.frameCount >= 14 && this.frameCount <= 15) {
                    this.display[5][2] = -104;
                } else {
                    this.display[5][2] = 0;
                }

                if (this.frameCount == 15) {
                    this.IsPlayerNameDrawable = true;
                    this.stageInfoPlayerNameDrawer.restart();
                }

                if (this.frameCount == 17) {
                    this.IsActNumDrawable = true;
                    this.stageInfoActNumDrawer.restart();
                }

                if (this.display[0][0] + this.display[0][2] > 0) {
                    this.display[0][0] = 0;
                } else {
                    var3 = this.display[0];
                    var3[0] += this.display[0][2];
                }

                var3 = this.display[0];
                var3[1] += this.display[0][3];
                var3 = this.display[1];
                var3[0] += this.display[1][2];
                if (this.display[1][0] < 0) {
                    this.display[1][0] = 0;
                }

                if (this.display[1][1] + this.display[1][3] > (SCREEN_HEIGHT >> 1) + 48) {
                    this.display[1][1] = (SCREEN_HEIGHT >> 1) + 48;
                } else {
                    var3 = this.display[1];
                    var3[1] += this.display[1][3];
                }

                var3 = this.display[2];

                for(var3[0] += this.display[2][2]; this.display[2][0] < 0; var3[0] += 224) {
                    var3 = this.display[2];
                }

                var3 = this.display[2];
                var3[0] %= 224;
                var3 = this.display[2];
                var3[1] += this.display[2][3];
                var3 = this.display[4];
                var3[0] += this.display[4][2];
                if (this.display[5][0] + this.display[5][2] < SCREEN_WIDTH - 112) {
                    this.display[5][0] = SCREEN_WIDTH - 112;
                } else {
                    var3 = this.display[5];
                    var3[0] += this.display[5][2];
                }

                if (this.state == 16) {
                    this.frameCount = 0;
                }

                GameObject.setNoInput();
                GameObject.logicObjects();
                break;
            case 28:
                this.overcnt++;
                if (this.overcnt == 20) {
                    isDrawTouchPad = false;
                }
                if (this.overtitleID == 78) {
                    if (this.overcnt == 36) {
                        initTouchkeyBoard();
                        if (!ChargePlatform.isChargedByIndex(0)) {
                            state = 11;
                            this.continueFrame = 0;
                            this.continueScale = 1.0f;
                            break;
                        } else {
                            state = 41;
                            break;
                        }
                    }
                } else if (this.overtitleID == 136 && this.overcnt == 28) {
                    state = 11;
                    break;
                }
                break;
            case 29:
                this.pausetoSelectCharacterLogic();
                break;
            case 30:
                switch (this.touchPadPositionLogic()) {
                    case 2:
                        GlobalResource.saveSystemConfig();
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    default:
                        return;
                }
            case 31:
                switch (this.itemsSelect4Logic()) {
                    case 1:
                        GlobalResource.vibrationConfig = 0;
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    case 2:
                        GlobalResource.vibrationConfig = 1;
                        MyAPI.vibrate();
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    case 3:
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    case 4:
                        GlobalResource.vibrationConfig = 2;
                        MyAPI.vibrate();
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    case 5:
                        GlobalResource.vibrationConfig = 3;
                        MyAPI.vibrate();
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    default:
                        return;
                }
            case 33:
                switch (this.touchPadOpacityLogic()) {
                    case 2:
                        GlobalResource.saveSystemConfig();
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    default:
                        return;
                }
            case 34:
                this.helpLogic();
                if ((Key.buttonPress(524288 | 8388608) || Key.touchhelpreturn.IsButtonPress() && this.returnPageCursor == 1) && fadeChangeOver()) {
                    this.changeStateWithFade(7);
                    GameObject.IsGamePause = true;
                    this.isOptionDisFlag = false;
                    SoundSystem.getInstance().playSe(2);
                    this.returnCursor = 0;
                }
                break;
            case 35:
                if (fadeChangeOver()) {
                    this.state = 5;
                    this.initTips();
                }
                break;
            case 36:
                if (fadeChangeOver()) {
                    this.state = 15;
                    fadeInit(255, 204);
                }
                break;
            case 37:
                if (fadeChangeOver()) {
                    this.state = 27;
                    fadeInit(255, 204);
                }
                break;
            case 38:
                switch (this.touchPadSizeLogic()) {
                    case 2:
                        GlobalResource.saveSystemConfig();
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    default:
                        return;
                }
            case 39:
                switch (this.itemsSelect3Logic()) {
                    case 1:
                        GlobalResource.sensorConfig = 0;
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    case 2:
                        GlobalResource.sensorConfig = 1;
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    case 3:
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    case 4:
                        GlobalResource.sensorConfig = 2;
                        fadeInit(220, 0);
                        state = 7;
                        return;
                    default:
                        return;
                }
            case 40:
                if (fadeChangeOver()) {
                    PlayerObject.calculateScore();
                    this.state = 14;
                    fadeInit(255, 0);
                    this.exendBgImage = null;
                    this.exendBg1Image = null;
                    this.exendBgImage = MFImage.createImage("/animation/ending/ed_ex_moon_bg.png");
                    this.exendBg1Image = MFImage.createImage("/animation/ending/ed_ex_forest.png");
                    SoundSystem.getInstance().stopBgm(false);
                    this.allclearFrame = 0;
                }
                break;
            case 41:
                if (this.movingTitleX - this.movingTitleSpeedX < SCREEN_WIDTH >> 1) {
                    this.movingTitleX = SCREEN_WIDTH;
                    fadeInit(0, 102);
                    this.continueInit();
                } else {
                    this.movingTitleX -= this.movingTitleSpeedX;
                }
                break;
            case 42:
                if (this.continueFrame == 0) {
                }
                
                ++this.continueFrame;
                if (this.continueFrame <= 5) {
                    this.continueScale -= 0.2F;
                } else if (this.continueFrame <= 10) {
                    this.continueScale += 0.2F;
                    if (this.continueScale > 1.0F) {
                        this.continueScale = 1.0F;
                    }
                } else if (this.continueFrame > 10) {
                    if (this.continueMoveBlackBarX + SCREEN_WIDTH / 6 > 0) {
                        this.continueMoveBlackBarX = 0;
                    } else {
                        this.continueMoveBlackBarX += SCREEN_WIDTH / 6;
                    }

                    if (this.continueMoveBlackBarX == 0) {
                        if (this.continueNumberState == 0) {
                            this.continueMoveNumberX += SCREEN_WIDTH / 12;
                            var1 = var2;
                            if (this.continueMoveNumberX >= SCREEN_WIDTH >> 1) {
                                this.continueMoveNumberX = SCREEN_WIDTH >> 1;
                                this.continueNumberState = 1;
                                this.continueNumberScale = 2.0F;
                                var1 = var2;
                            }
                        } else if (this.continueNumberState == 1) {
                            this.continueNumberScale -= 0.125F;
                            var1 = var2;
                            if (this.continueNumberScale <= 1.0F) {
                                this.continueNumberScale = 1.0F;
                                this.continueNumberState = 2;
                                var1 = var2;
                            }
                        } else if (this.continueNumberState == 2) {
                            this.continueMoveNumberX += SCREEN_WIDTH / 12;
                            var1 = var2;
                            if (this.continueMoveNumberX >= SCREEN_WIDTH + 30) {
                                this.continueMoveNumberX = -30;
                                this.continueNumberState = 0;
                                --this.continueNumber;
                                var1 = var2;
                                if (this.continueNumber == -1) {
                                    this.continueEnd();
                                    var1 = var2;
                                }
                            }
                        } else if (this.continueNumberState == 3) {
                            if (this.continueFrame == this.continueStartEndFrame + 3) {
                                fadeInit(102, 255);
                                var1 = var2;
                            } else {
                                var1 = var2;
                                if (this.continueFrame > this.continueStartEndFrame + 3) {
                                    var1 = var2;
                                    if (fadeChangeOver()) {
                                        if (this.continueScale - 0.2F > 0.0F) {
                                            this.continueScale -= 0.2F;
                                        } else {
                                            this.continueScale = 0.0F;
                                        }

                                        var1 = var2;
                                        if (this.continueScale == 0.0F) {
                                            Standard2.splashinit(true);
                                            State.setState(0);
                                            var1 = var2;
                                        }
                                    }
                                }
                            }
                        } else if (this.continueNumberState == 4) {
                            this.continueMoveNumberX += SCREEN_WIDTH / 12;
                            var1 = var2;
                            if (this.continueMoveNumberX >= SCREEN_WIDTH >> 1) {
                                this.continueMoveNumberX = SCREEN_WIDTH >> 1;
                                this.continueNumberState = 5;
                                this.continueNumberScale = 2.0F;
                                var1 = var2;
                            }
                        } else if (this.continueNumberState == 5) {
                            this.continueNumberScale -= 0.125F;
                            var1 = var2;
                            if (this.continueNumberScale <= 1.0F) {
                                this.continueNumberScale = 1.0F;
                                this.continueNumberState = 6;
                                var1 = var2;
                            }
                        } else {
                            var1 = var2;
                            if (this.continueNumberState == 6) {
                                this.continueMoveNumberX += SCREEN_WIDTH / 12;
                                var1 = var2;
                                if (this.continueMoveNumberX >= SCREEN_WIDTH + 30) {
                                    this.state = 5;
                                    loadingType = 0;
                                    this.initTips();
                                    PlayerObject.resetGameParam();
                                }
                            }
                        }

                        if (this.continueNumberState < 3) {
                            if (Key.touchgameoveryres.Isin() && Key.touchgameover.IsClick()) {
                                this.continueCursor = var1;
                            }

                            if (Key.touchgameoverno.Isin() && Key.touchgameover.IsClick()) {
                                this.continueCursor = 1;
                            }

                            if (Key.touchgameoveryres.IsButtonPress() && this.continueCursor == 0 || Key.press(16777216)) {
                                this.continueNumberState = 4;
                                this.continueMoveNumberX = -30;
                                this.continueNumberScale = 1.0F;
                                SoundSystem.getInstance().playSe(1);
                            }

                            if (Key.touchgameoverno.IsButtonPress() && this.continueCursor == 1 || Key.press(8388608)) {
                                this.continueEnd();
                                SoundSystem.getInstance().playSe(2);
                            }
                        }
                    }
                }
        }

    }

    public void pause() {
        if (this.state != 18 && this.state != 1 && this.state != 20 && this.state != 23 && this.state != 21) {
            if (this.state == 0 && GameObject.player.isDead) {
                this.interrupt_state = this.state;
                this.state = 18;
                this.interruptInit();
            } else if (this.state != 5 && this.state != 35 && this.state != 14 && this.state != 4 && this.state != 7 && this.state != 8 && this.state != 9 && this.state != 29 && this.state != 10 && this.state != 6 && this.state != 29 && this.state != 41 && this.state != 42 && this.state != 28 && this.state != 11 && this.state != 12 && this.state != 13 && this.state != 30 && this.state != 31 && this.state != 32 && this.state != 33 && this.state != 34 && this.state != 38 && this.state != 39) {
                PlayerObject.gamepauseInit();
                this.state = 1;
                isDrawTouchPad = false;
                this.gamePauseInit();
                Key.init();
                GameObject.IsGamePause = true;
                fadeInit_Modify(0, 102);
                SoundSystem.getInstance().stopBgm(false);
                SoundSystem.getInstance().stopLongSe();
                SoundSystem.getInstance().stopLoopSe();
                this.pauseoptionCursor = GlobalResource.soundConfig;
                Key.touchgamekeyClose();
            } else {
                this.interrupt_state = this.state;
                this.state = 18;
                this.interruptInit();
                System.out.println("interrupt");
            }
        }

    }

    public void shopInit(boolean var1) {
        if (var1) {
            PlayerObject.cursor = 0;
        }

        this.state = 22;
        MyAPI.initString();
        Key.clear();
    }
}

