//
// Decompiled by FernFlower - 2699ms
//
package State;

import GameEngine.Key;
import GameEngine.TouchKeyRange;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.SoundSystem;
import Common.NumberDrawer;
import PlatformStandard.Standard;
import PlatformStandard.Standard2;
import SonicGBA.GameObject;
import SonicGBA.GlobalResource;
import SonicGBA.PlayerObject;
import SonicGBA.StageManager;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGamePad;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import com.sega.mobile.framework.ui.MFSlideSensor;
import com.sega.mobile.platform.ChargePlatform;
import com.sega.mobile.framework.MFMain;

public class TitleState extends State {
    private static final int ACTION_NUM_OFFSET = 49;
    private static final int ACTION_OFFSET = 26;
    private static final int ARRAW_OFFSET_Y;
    private static final int BACK_LINE_SPACE_NORMAL = 96;
    private static final int BACK_LINE_SPACE_TIME = 96;
    private static final int BALL_INIT_Y;
    private static final int BALL_POSITION_X = 0;
    private static final int BALL_RUN_POSITION_X = -460;
    private static final int CHARACTER_MOVE_OFFSET = 8;
    private static final int CHARACTER_MOVE_OFFSET_SPEED_MAX = 32;
    private static final int CHARACTER_OFFSET_LEFT = 1;
    private static final int CHARACTER_OFFSET_NONE = 0;
    private static final int CHARACTER_OFFSET_RIGHT = 2;
    public static final int CHARACTER_RECORD_BG_HEIGHT = 48;
    public static final int CHARACTER_RECORD_BG_OFFSET = 128;
    public static final int CHARACTER_RECORD_BG_SPEED = 4;
    private static final int CHARACTER_SELECT_ARROW_LEFT = 1;
    private static final int CHARACTER_SELECT_ARROW_NONE = 0;
    private static final int CHARACTER_SELECT_ARROW_RIGHT = 2;
    private static final String[] CHARACTER_STR;
    private static final int COPY_RIGHT_X = -2;
    private static final int COPY_RIGHT_Y = 4;
    private static final int CREDIT_PAGE_BACKGROUND_WIDTH = 112;
    private static final int DEGREE_GAP = 18;
    private static final int DEGREE_START = -18;
    private static final int ELEMENT_OFFSET = -1;
    private static final int ELEMENT_V_OFFSET = 0;
    private static final int EN_TITLE_SPACE_FOR_STAGE_SELECT = 0;
    private static final int GESTURE_SLIDE_STATE_DOWN = 2;
    private static final int GESTURE_SLIDE_STATE_NONE = 0;
    private static final int GESTURE_SLIDE_STATE_UP = 1;
    private static final int INTERGRADE_RECORD_STAGE_NAME_SPEED = -8;
    private static final int INTERGRADE_RECORD_STAGE_NAME_WIDTH = 200;
    private static final int INTERVAL_ABOVE_RECORD_BAR;
    private static final int INTERVAL_FOR_RECORD_BAR;
    private static final int ITEMS_INTERVALS = 20;
    private static final int ITEM_SPACE;
    public static final int ITEM_X;
    private static boolean IsSoundVolSet;
    private static final int LINE_START_X;
    private static final int LINE_START_Y;
    private static final int LOGO_POSITION_X;
    private static final int LOGO_POSITION_Y;
    private static final int LOGO_POSITION_Y_2;
    private static int[] MAIN_MENU;
    private static final int MAIN_MENU_CENTER_X = 51;
    private static final int MAIN_MENU_CENTER_Y = 159;
    private static int[] MAIN_MENU_FUNCTION;
    private static final int[] MAIN_MENU_FUNCTION_MOREGAME = new int[]{4, 6, 7, 8, 9, 10, 11, 12};
    private static final int[] MAIN_MENU_FUNCTION_NO_MOREGAME = new int[]{4, 6, 8, 9, 10, 11, 12};
    private static final int[] MAIN_MENU_FUNCTION_UNACTIVIATE = new int[]{4, 7, 8, 9, 10, 11, 12};
    private static final int[] MAIN_MENU_MOREGAME = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
    private static final int[] MAIN_MENU_NO_MOREGAME = new int[]{1, 2, 4, 5, 6, 7, 8};
    private static final int[] MAIN_MENU_UNACTIVIATE = new int[]{1, 3, 4, 5, 6, 7, 8};
    private static final int MAIN_MENU_V_CENTER_X;
    private static final int MAIN_MENU_V_CENTER_Y;
    private static int MENU_INTERVAL;
    private static int MENU_OFFSET_X;
    private static final int MENU_SPACE_INTERVAL;
    private static final int N5500_OFFSET = -15;
    private static final int N5500_OFFSET_2 = -7;
    private static final int N5500_OFFSET_3 = -12;
    private static final int[] OFFSET_ARRAY;
    private static final byte OPENING_STATE_AMY = 5;
    private static final byte OPENING_STATE_EMERALD = 0;
    private static final byte OPENING_STATE_EMERALD_SHINING = 1;
    private static final byte OPENING_STATE_END = 6;
    private static final byte OPENING_STATE_KNUCKLES = 4;
    private static final byte OPENING_STATE_SONIC = 2;
    private static final byte OPENING_STATE_TAILS = 3;
    private static final int[] OPTION_DIFFICULTY;
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
    private static final int[] OPTION_TIME;
    private static final int ORIGINAL_LOGO_X;
    private static final int ORIGINAL_LOGO_Y_NO_ROTATE;
    private static final int ORIGINAL_LOGO_Y_ROTATE;
    private static final int PATCH_OFFSET_X = 66;
    private static final int PATCH_OFFSET_Y = 224;
    private static final int PIC_OFFSET_X = 92;
    private static final int PIC_OFFSET_Y = 12;
    private static final byte PRESS_DELAY = 5;
    private static final int PRESS_START_Y;
    private static final int RADIUS = 137;
    public static final int RETURN_PRESSED = 400;
    private static final int SHOW_ELEMENT_NUM = 5;
    private static final int SHOW_ELEMENT_V_NUM = 3;
    private static final int SONIC_BALL_SPACE = 120;
    private static final int SONIC_BIG_Y;
    private static final int SONIC_RUN_POSITION_X = -580;
    private static final int STAGE_SELECT_ARROW_STATE_DOWN = 2;
    private static final int STAGE_SELECT_ARROW_STATE_NONE = 0;
    private static final int STAGE_SELECT_ARROW_STATE_UP = 1;
    public static final int STAGE_SELECT_KEY_DIRECT_PLAY = 2;
    public static final int STAGE_SELECT_KEY_RECORD_1 = 0;
    public static final int STAGE_SELECT_KEY_RECORD_2 = 1;
    private static final int STAGE_SELECT_PRESS_STATE_ARROW = 2;
    private static final int STAGE_SELECT_PRESS_STATE_NONE = 0;
    private static final int STAGE_SELECT_PRESS_STATE_SLIDING = 1;
    private static final int STAGE_SELECT_SIDE_BAR_WIDTH = 60;
    private static final int STAGE_TYPE_CANT_CHOOSE = 137;
    private static final int STAGE_TYPE_CHOOSE = 19;
    private static final int STAGE_TYPE_UNCHOOSE = 13;
    private static final int[] START_GAME_MENU = new int[]{145, 12};
    private static final int STATE_ABOUT = 11;
    private static final int STATE_BP_TRY_PAYING = 22;
    private static final int STATE_CHARACTER_RECORD = 25;
    private static final int STATE_CHARACTER_SELECT = 23;
    private static final int STATE_EXIT = 12;
    private static final int STATE_GAMEOVER_RANKING = 15;
    private static final int STATE_GOTO_GAME = 5;
    private static final int STATE_HELP = 10;
    private static final int STATE_INTERGRADE_RECORD = 26;
    private static final int STATE_INTERRUPT = 16;
    private static final int STATE_MAIN_MENU = 2;
    private static final int STATE_MORE_GAME = 7;
    private static final int STATE_MOVING = 2;
    private static final int STATE_OPENING = 3;
    private static final int STATE_OPTION = 9;
    private static final int STATE_OPTION_CREDIT = 35;
    private static final int STATE_OPTION_DIFF = 27;
    private static final int STATE_OPTION_HELP = 34;
    private static final int STATE_OPTION_KEY_SET = 31;
    private static final int STATE_OPTION_LANGUAGE = 33;
    private static final int STATE_OPTION_RESET_RECORD = 36;
    private static final int STATE_OPTION_RESET_RECORD_ENSURE = 37;
    private static final int STATE_OPTION_SENSOR_SET = 40;
    private static final int STATE_OPTION_SOUND = 28;
    private static final int STATE_OPTION_SOUND_VOLUMN = 39;
    private static final int STATE_OPTION_SP_SET = 32;
    private static final int STATE_OPTION_TIME_LIMIT = 30;
    private static final int STATE_OPTION_VIBRATION = 29;
    private static final int STATE_PRESS_START = 1;
    private static final int STATE_PRE_PRESS_START = 38;
    private static final int STATE_PRO_RACE_MODE = 24;
    private static final int STATE_QUIT = 13;
    private static final int STATE_RACE_MODE = 6;
    private static final int STATE_RANKING = 8;
    private static final int STATE_RESET_RECORD_ASK = 17;
    private static final int STATE_RETURN_TO_LOGO_1 = 20;
    private static final int STATE_RETURN_TO_LOGO_2 = 21;
    private static final int STATE_SEGA_LOGO = 0;
    private static final int STATE_SEGA_MORE = 41;
    private static final int STATE_SELECT = 1;
    private static final int STATE_STAGE_SELECT = 14;
    private static final int STATE_START_GAME = 4;
    private static final int STATE_START_TO_MENU_1 = 18;
    private static final int STATE_START_TO_MENU_2 = 19;
    private static final int STATE_UP = 0;
    private static final int TIME_ATTACK_SPEED_X = -2;
    private static final int TIME_ATTACK_WIDTH = 128;
    private static final int TITLE_BG_COLOR_1 = 15530750;
    private static final int TITLE_BG_COLOR_2 = 9886462;
    private static final int TITLE_BG_OFFSET = 31;
    private static final int TITLE_BG_SPEED = 1;
    private static final int TITLE_BG_WIDTH = 62;
    private static final int TITLE_FRAME_HEIGHT;
    private static final int TOTAL_OPTION_ITEMS_NUM = 10;
    private static final int VISIBLE_OPTION_ITEMS_NUM = 9;
    private static final int ZONE_NUM_OFFSET = 1;
    private static final int ZONE_OFFSET = -22;
    private static AnimationDrawer downCursorDrawer;
    private static final int intergradeRecordtoGamecnt_max = 56;
    public static int preStageSelectState;
    private static String[] stageName;
    public static int state;
    public static MFImage titleLeftImage;
    public static MFImage titleRightImage;
    public static MFImage titleSegaImage;
    private static AnimationDrawer upCursorDrawer;
    public boolean IsFromOptionItems;
    public boolean IsFromStageSelect;
    private int RESET_INFO_COUNT = 30;
    private int RecordtimeScrollPosY;
    private int STAGE_SEL_ARROW_DOWN_X;
    private int STAGE_SEL_ARROW_DOWN_Y;
    private int STAGE_SEL_ARROW_UP_X;
    private int STAGE_SEL_ARROW_UP_Y;
    private int STAGE_TOTAL_NUM = 14;
    private int arrowPressState;
    private int ballVx;
    private int ballY;
    private int cameraX;
    private Animation[] charSelAni;
    private AnimationDrawer charSelAniDrawer;
    private AnimationDrawer charSelArrowDrawer;
    private AnimationDrawer charSelCaseDrawer;
    private Animation[] charSelFilAni;
    private AnimationDrawer charSelFilAniDrawer;
    private AnimationDrawer charSelRoleDrawer;
    private AnimationDrawer charSelTitleDrawer;
    private int characterRecordBGOffsetX_1;
    private int characterRecordBGOffsetX_2;
    private boolean characterRecordDisFlag;
    private int characterRecordID;
    private int characterRecordScoreUpdateCursor;
    private int characterRecordScoreUpdateIconY;
    private boolean character_arrow_display;
    private boolean character_circleturnright;
    private int character_id;
    private boolean character_idchangeFlag;
    private boolean character_move;
    private int character_move_frame;
    private int character_offset_state;
    private boolean character_outer;
    private int character_preid;
    private boolean character_reback;
    private int character_sel_frame_cnt;
    private int character_sel_offset_x;
    private int copyOffsetX;
    private MFImage copyrightImage;
    private int count = 0;
    private int creditOffsetY;
    private int creditStringLineNum;
    private int currentStageSelectSlidePointY;
    private int debug_bgm_id;
    private int debug_bgm_state;
    private int degree;
    private int degreeDes;
    private boolean fadeChangeState;
    private int firstStageSelectSlidePointY;
    private int gestureSlideSpeed;
    private int gestureSlideState;
    private int intergradeRecordStageNameOffsetX;
    private int intergradeRecordtoGamecnt;
    private AnimationDrawer interruptDrawer;
    public int interrupt_state;
    private boolean isAtMainMenu;
    private boolean isChanged;
    private boolean isDrawDownArrow;
    private boolean isDrawUpArrow;
    private boolean isFromStartGame;
    private boolean isLinkMoreGame;
    private boolean isOptionChange;
    private boolean isOptionDisFlag;
    private boolean isRaceModeItemsSelected;
    private boolean isSelectable;
    private boolean isStageSelectChange;
    private boolean isTitleBGMPlay;
    private int itemOffsetX;
    public static AnimationDrawer langAniDrawer;
    private int logoGravity;
    private MFImage logoImage;
    private int logoVx;
    private int logoVy;
    private int logoX;
    private int logoY;
    private boolean mainMenuBackFlag = false;
    private int mainMenuCursor;
    private boolean mainMenuEnsureFlag;
    private int menuOptionCursor;
    public static AnimationDrawer muiLeftArrowDrawer;
    public static AnimationDrawer muiRightArrowDrawer;
    private int[] multiMainItems;
    private int[] multiMainNoMoreGameItems = new int[]{3, /*19,*/ 5, 7, 17, 9};
    public static int nextState;
    private static AnimationDrawer numberDrawer;
    private int offsetOfVolumeInterface;
    private int[] offsetY;
    int offset_flag;
    private int opengingCursor;
    private Animation[] openingAnimation;
    private int openingCount;
    private AnimationDrawer[] openingDrawer;
    private boolean openingEnding;
    private int openingFrame;
    private int openingOffsetX;
    private int openingOffsetY;
    private byte openingState;
    private boolean openingStateChanging;
    private AnimationDrawer optionArrowDownDrawer;
    private int optionArrowDriveOffsetY;
    private int optionArrowDriveY;
    private boolean optionArrowMoveable;
    private AnimationDrawer optionArrowUpDrawer;
    private int[] optionCursor;
    private boolean optionDownArrowAvailable;
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
    private boolean optionUpArrowAvailable;
    private int optionYDirect;
    private int optionslide_getprey;
    private int optionslide_gety;
    private int optionslide_y;
    private int optionslidefirsty;
    public static int preCharaterSelectState;
    private int preStageSelectSlidePointY;
    private byte pressDelay;
    private byte pressDelay2;
    private int quitFlag;
    private int[] rankingScore;
    private Animation[] recordAni;
    private AnimationDrawer recordAniDrawer;
    private int[] recordArrowOffsetXArray;
    private int recordArrowOffsetXID;
    private int resetInfoCount = 0;
    private int returnCursor;
    private int shakeCount;
    private AnimationDrawer skipDrawer;
    private MFImage sonicBigImage;
    private MFImage sonicBigPatchImage;
    private int sonicBigX;
    private int sonicVx;
    private int sonicX;
    private int sonicY;
    private int stageDrawEndY;
    private int stageDrawKeyAimY;
    private int stageDrawKeyOffsetY;
    private int stageDrawOffsetBottomY;
    private int stageDrawOffsetTmpY1;
    private int stageDrawOffsetTmpY2;
    private int stageDrawOffsetY;
    private int stageDrawStartY;
    private int stageItemNumForShow;
    private Animation[] stageSelAni;
    private AnimationDrawer stageSelAniDrawer;
    private AnimationDrawer stageSelArrowDownDrawer;
    private AnimationDrawer stageSelArrowUpDrawer;
    private AnimationDrawer stageSelEmeraldDrawer;
    private int stageSelectArrowDriveOffsetY;
    private int stageSelectArrowDriveY;
    private boolean stageSelectArrowMoveable;
    private boolean stageSelectDownArrowAvailable;
    private boolean stageSelectReturnFlag;
    private int stageSelectSlideFrame;
    private boolean stageSelectUpArrowAvailable;
    private int stageStartIndex;
    private int stageYDirect;
    private int stage_characterRecord_ID;
    public static int stage_sel_key;
    private int stage_select_arrow_state;
    private int stage_select_press_state;
    private int stage_select_state;
    private int stageselectslide_getprey;
    private int stageselectslide_gety;
    private int stageselectslide_y;
    private int stageselectslidefirsty;
    private int startgamecursor;
    private boolean startgameensureFlag;
    private int startgameframe;
    private Animation[] timeAttAni;
    private AnimationDrawer timeAttAniDrawer;
    private int timeAttackOffsetX;
    private int timecount_ranking;
    private Animation[] titleAni;
    private AnimationDrawer titleAniDrawer;
    private int titleDegree;
    private int titleFrame = 0;
    private MFImage titleFrameImage;
    public float titleScale = 1.0F;
    private AnimationDrawer titleSonicDrawer;
    private int title_name_center_x = 77;
    private int title_name_center_y = -53;
    private int[] vY;
    public static int characterslots = 1;
    public static int maxcharacterslots = 2;

    static {
        ITEM_X = SCREEN_WIDTH * 5 / 6;
        MENU_INTERVAL = MENU_SPACE;
        MAIN_MENU_V_CENTER_X = (SCREEN_WIDTH >> 1) + 56;
        MAIN_MENU_V_CENTER_Y = (SCREEN_HEIGHT >> 1) + 40;
        MENU_SPACE_INTERVAL = 0;
        ARRAW_OFFSET_Y = 16;
        int var1 = SCREEN_HEIGHT;
        int var2 = MENU_SPACE;
        byte var0;
        if (SCREEN_HEIGHT == 240) {
            var0 = 24;
        } else {
            var0 = 0;
        }

        LINE_START_Y = (var1 >> 1) + var2 + 4 + var0 + 0;
        OPTION_TAG_HAS_SE = new int[]{50, 54, 143, 59, 146};
        OPTION_TAG_NO_SE = new int[]{50, 54, 59, 146};
        OPTION_TAG = OPTION_TAG_HAS_SE;
        OPTION_ELEMENT_NUM = OPTION_TAG.length;
        OPTION_DIFFICULTY = new int[]{51, 53};
        OPTION_SOUND_VOLUME = new int[]{55, 58, 58, 58, 57, 57, 57, 57, 56, 56, 56};
        OPTION_SOUND_NO_VOLUME = new int[]{55, 144};
        OPTION_SOUND = OPTION_SOUND_VOLUME;
        OPTION_SE = new int[]{55, 144};
        OPTION_TIME = new int[]{60, 61};
        int[] var5 = OPTION_DIFFICULTY;
        int[] var4 = OPTION_SOUND;
        int[] var3 = OPTION_SE;
        int[] var6 = OPTION_TIME;
        OPTION_SELECTOR_HAS_SE = new int[][]{var5, var4, var3, var6};
        var4 = OPTION_DIFFICULTY;
        var3 = OPTION_SOUND;
        var5 = OPTION_TIME;
        OPTION_SELECTOR_NO_SE = new int[][]{var4, var3, var5};
        OPTION_SELECTOR = OPTION_SELECTOR_HAS_SE;
        IsSoundVolSet = false;
        ITEM_SPACE = 24;
        INTERVAL_ABOVE_RECORD_BAR = MENU_SPACE >> 1;
        INTERVAL_FOR_RECORD_BAR = MENU_SPACE;
        stageName = null;
        TITLE_FRAME_HEIGHT = SCREEN_HEIGHT * 44 / 320;
        BALL_INIT_Y = SCREEN_HEIGHT >> 1;
        ORIGINAL_LOGO_X = SCREEN_WIDTH * 152 / 240;
        ORIGINAL_LOGO_Y_ROTATE = SCREEN_HEIGHT * 168 / 320;
        ORIGINAL_LOGO_Y_NO_ROTATE = SCREEN_HEIGHT * 61 / 320;
        LOGO_POSITION_X = ORIGINAL_LOGO_X;
        LINE_START_X = LOGO_POSITION_X + 0;
        LOGO_POSITION_Y = ORIGINAL_LOGO_Y_ROTATE;
        LOGO_POSITION_Y_2 = ORIGINAL_LOGO_Y_NO_ROTATE;
        SONIC_BIG_Y = SCREEN_HEIGHT * 28 / 320;
        PRESS_START_Y = SCREEN_HEIGHT * 236 / 320;
        var3 = new int[]{0, -1, -1, 0, 1, 1};
        OFFSET_ARRAY = var3;
        CHARACTER_STR = new String[]{"索尼克", "塔尔斯", "那克鲁兹", "艾米"};
    }

    public TitleState() {
        this.optionCursor = new int[OPTION_ELEMENT_NUM];
        this.isOptionDisFlag = false;
        this.optionslide_getprey = -1;
        this.optionslide_gety = -1;
        this.offsetOfVolumeInterface = 0;
        this.pressDelay = 5;
        this.pressDelay2 = 1;
        this.rankingScore = new int[5];
        this.timecount_ranking = 0;
        this.offset_flag = 0;
        this.RecordtimeScrollPosY = 0;
        this.logoX = LOGO_POSITION_X;
        this.logoGravity = 12;
        this.copyOffsetX = 0;
        this.opengingCursor = -1;
        this.shakeCount = 0;
        this.multiMainItems = new int[]{3, 5, 7, 12, 17, 9};
        //this.multiMainNoMoreGameItems = new int[]{3, 19, 5, 7, 17, 9};
        this.isAtMainMenu = true;
        this.arrowPressState = 0;
        this.timeAttackOffsetX = 0;
        this.stageselectslide_getprey = -1;
        this.stageselectslide_gety = -1;
        this.stageSelectReturnFlag = false;
        this.characterRecordDisFlag = false;
        int[] var1 = new int[]{1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0, 0};
        this.recordArrowOffsetXArray = var1;
        this.isTitleBGMPlay = false;
        this.debug_bgm_state = 0;
        this.debug_bgm_id = 0;
        state = 0;
        Key.touchsoftkeyInit();
        this.logoX = LOGO_POSITION_X;
        this.logoY = LOGO_POSITION_Y;
        this.sonicBigX = 0;
        this.initTitleRes();
    }

    public TitleState(int var1) {
        this.optionCursor = new int[OPTION_ELEMENT_NUM];
        this.isOptionDisFlag = false;
        this.optionslide_getprey = -1;
        this.optionslide_gety = -1;
        this.offsetOfVolumeInterface = 0;
        this.pressDelay = 5;
        this.pressDelay2 = 1;
        this.rankingScore = new int[5];
        this.timecount_ranking = 0;
        this.offset_flag = 0;
        this.RecordtimeScrollPosY = 0;
        this.logoX = LOGO_POSITION_X;
        this.logoGravity = 12;
        this.copyOffsetX = 0;
        this.opengingCursor = -1;
        this.shakeCount = 0;
        this.multiMainItems = new int[]{3, 5, 7, 12, 17, 9};
        //this.multiMainNoMoreGameItems = new int[]{3, 19, 5, 7, 17, 9};
        this.isAtMainMenu = true;
        this.arrowPressState = 0;
        this.timeAttackOffsetX = 0;
        this.stageselectslide_getprey = -1;
        this.stageselectslide_gety = -1;
        this.stageSelectReturnFlag = false;
        this.characterRecordDisFlag = false;
        int[] var2 = new int[]{1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0, 0};
        this.recordArrowOffsetXArray = var2;
        this.isTitleBGMPlay = false;
        this.debug_bgm_state = 0;
        this.debug_bgm_id = 0;
        state = 0;
        this.logoX = LOGO_POSITION_X;
        this.logoY = LOGO_POSITION_Y_2;
        this.sonicBigX = 0;
        this.initTitleRes();
        switch (var1) {
            case 2:
                state = 1;
                this.nextState = 1;
                this.logoY = LOGO_POSITION_Y;
                SoundSystem.getInstance().playBgm(1, false);
                break;
            case 3:
                state = 14;
                this.nextState = 14;
                preStageSelectState = 23;
                this.preCharaterSelectState = 24;
                this.menuInit(this.STAGE_TOTAL_NUM);
                this.initStageSelectRes();
                PlayerObject.stageModeState = 1;
                SoundSystem.getInstance().playBgm(3);
                this.stage_sel_key = 1;
                break;
            case 4:
                this.rankingInit();
                state = 15;
                this.nextState = 15;
                SoundSystem.getInstance().playBgm(4);
                break;
            case 5:
                state = 14;
                this.nextState = 14;
                preStageSelectState = 2;
                this.menuInit(this.STAGE_TOTAL_NUM);
                this.initStageSelet();
                PlayerObject.stageModeState = 0;
                SoundSystem.getInstance().playBgm(3);
            case 6:
            case 7:
            case 8:
            default:
                break;
            case 9:
                state = 23;
                this.nextState = 23;
                this.preCharaterSelectState = 24;
                this.stage_sel_key = 1;
                characterslots = 1;
                this.initCharacterSelectRes();
        }

        Key.touchkeypauseClose();
    }

    private void characterRecordLogic() {
        if (!this.characterRecordDisFlag) {
            SoundSystem.getInstance().playBgm(4);
            this.characterRecordDisFlag = true;
        }

        if (Key.slidesensorcharacterrecord.isSliding()) {
            if (Key.slidesensorcharacterrecord.isSlide(Key.DIR_LEFT)) {
                --this.characterRecordID;
                this.characterRecordID += PlayerObject.CHARACTER_LIST.length;
                this.characterRecordID %= PlayerObject.CHARACTER_LIST.length;
                SoundSystem.getInstance().playSe(3);
            } else if (Key.slidesensorcharacterrecord.isSlide(Key.DIR_RIGHT)) {
                ++this.characterRecordID;
                this.characterRecordID += PlayerObject.CHARACTER_LIST.length;
                this.characterRecordID %= PlayerObject.CHARACTER_LIST.length;
                SoundSystem.getInstance().playSe(3);
            }
        }

        if (Key.touchintergraderecordleftarrow.IsButtonPress() && fadeChangeOver() || Key.press(16)) {
            --this.characterRecordID;
            this.characterRecordID += PlayerObject.CHARACTER_LIST.length;
            this.characterRecordID %= PlayerObject.CHARACTER_LIST.length;
            SoundSystem.getInstance().playSe(3);
        } else if (Key.touchintergraderecordrightarrow.IsButtonPress() && fadeChangeOver() || Key.press(32)) {
            ++this.characterRecordID;
            this.characterRecordID += PlayerObject.CHARACTER_LIST.length;
            this.characterRecordID %= PlayerObject.CHARACTER_LIST.length;
            SoundSystem.getInstance().playSe(3);
        }

        if ((Key.buttonPress(524288 | 8388608) || Key.touchintergraderecordreturn.IsButtonPress()) && fadeChangeOver()) {
            this.stage_sel_key = 0;
            this.changeStateWithFade(14);
            preStageSelectState = 24;
            this.initStageSelectRes();
            SoundSystem.getInstance().playSe(2);
        }

    }

    private void characterSelectLogic() {
        ++this.character_sel_frame_cnt;
        if (this.character_sel_frame_cnt == 1) {
            this.charSelAniDrawer.setActionId(0);
            this.arrowPressState = 0;
            SoundSystem.getInstance().playBgm(2);
        }

        if (this.character_sel_frame_cnt == 4) {
            this.charSelCaseDrawer.setLoop(false);
        }

        if (this.character_sel_frame_cnt == 9) {
            this.charSelRoleDrawer.setLoop(false);
        }

        if (this.character_sel_frame_cnt == 10) {
            this.charSelFilAniDrawer.setLoop(false);
        }

        if (this.character_sel_frame_cnt == 16) {
            this.charSelCaseDrawer.setActionId(15);
            this.charSelRoleDrawer.setActionId(28);
        }

        if (this.character_sel_frame_cnt > 16) {
            if (!this.character_move) {
                if (Key.touchcharsel != null && Key.touchcharselselect.Isin() && Key.touchcharsel.IsClick()) {
                    this.cursor = 2;
                    this.returnCursor = 0;
                }

                if (Key.touchcharsel != null && Key.touchcharselleftarrow.Isin() && Key.touchcharsel.IsClick()) {
                    this.cursor = 3;
                    this.returnCursor = 0;
                }

                if (Key.touchcharsel != null && Key.touchcharselrightarrow.Isin() && Key.touchcharsel.IsClick()) {
                    this.cursor = 4;
                    this.returnCursor = 0;
                }

                if (Key.touchcharselreturn != null && Key.touchcharsel.IsClick() && Key.touchcharselreturn.Isin()) {
                    this.returnCursor = 1;
                }

                if (!this.character_outer) {
                    if (Key.touchcharselleftarrow.IsButtonPress() && this.cursor == 3 || Key.press(16)) {
                        this.arrowPressState = 1;
                        this.character_offset_state = 1;
                        Key.touchcharselleftarrow.resetKeyState();
                    }

                    if (Key.touchcharselrightarrow.IsButtonPress() && this.cursor == 4 || Key.press(32)) {
                        this.arrowPressState = 2;
                        this.character_offset_state = 2;
                        Key.touchcharselrightarrow.resetKeyState();
                    }

                    if (Key.slidesensorcharsel.isSliding()) {
                        boolean var2 = false;
                        if (this.arrowPressState == 0) {
                            boolean var1;
                            MFSlideSensor var3;
                            if (Key.slidesensorcharsel.isSlide(Key.DIR_LEFT)) {
                                switch (this.character_offset_state) {
                                    case 0:
                                        ++this.character_id;
                                        this.character_circleturnright = true;
                                        var1 = true;
                                        this.character_offset_state = 1;
                                        break;
                                    case 1:
                                        this.character_offset_state = 0;
                                        var1 = var2;
                                        break;
                                    case 2:
                                        ++this.character_id;
                                        this.character_circleturnright = true;
                                        var1 = true;
                                        this.character_offset_state = 0;
                                        break;
                                    default:
                                        var1 = var2;
                                }
                            } else {
                                var3 = Key.slidesensorcharsel;
                                var1 = var2;
                                if (var3.isSlide(Key.DIR_RIGHT)) {
                                    switch (this.character_offset_state) {
                                        case 0:
                                            --this.character_id;
                                            this.character_circleturnright = false;
                                            var1 = true;
                                            this.character_offset_state = 2;
                                            break;
                                        case 1:
                                            --this.character_id;
                                            this.character_circleturnright = false;
                                            var1 = true;
                                            this.character_offset_state = 0;
                                            break;
                                        case 2:
                                            this.character_offset_state = 0;
                                            var1 = var2;
                                            break;
                                        default:
                                            var1 = var2;
                                    }
                                }
                            }

                            int var4;
                            switch (this.character_offset_state) {
                                case 0:
                                    var3 = Key.slidesensorcharsel;
                                    var4 = var3.getOffsetX();
                                    this.character_sel_offset_x = var4;
                                    break;
                                case 1:
                                    var3 = Key.slidesensorcharsel;
                                    var4 = var3.getOffsetX();
                                    this.character_sel_offset_x = var4 + 64;
                                    break;
                                case 2:
                                    var3 = Key.slidesensorcharsel;
                                    var4 = var3.getOffsetX();
                                    this.character_sel_offset_x = var4 - 64;
                            }

                            if (var1) {
                                this.character_id += PlayerObject.CHARACTER_LIST.length;
                                this.character_id %= PlayerObject.CHARACTER_LIST.length;
                                this.charSelRoleDrawer.setLoop(false);
                                this.charSelRoleDrawer.setActionId(this.character_id + 28);
                                this.character_idchangeFlag = true;
                                if (this.character_id != this.character_preid) {
                                    this.character_reback = true;
                                } else {
                                    this.character_reback = false;
                                }
                            }
                        }
                    } else {
                        this.character_offset_state = 0;
                        int var5;
                        if (this.arrowPressState == 0) {
                            var5 = MyAPI.calNextPosition((double)this.character_sel_offset_x, 0.0, 1, 2);
                            this.character_sel_offset_x = var5;
                        } else {
                            if (this.arrowPressState == 1) {
                                var5 = MyAPI.calNextPosition((double)this.character_sel_offset_x, 128.0, 1, 2);
                                this.character_sel_offset_x = var5;
                                if (this.character_sel_offset_x == 128) {
                                    --this.character_id;
                                    this.character_circleturnright = false;
                                    this.character_id += PlayerObject.CHARACTER_LIST.length;
                                    this.character_id %= PlayerObject.CHARACTER_LIST.length;
                                    this.charSelRoleDrawer.setLoop(false);
                                    this.charSelRoleDrawer.setActionId(this.character_id + 28);
                                    this.character_idchangeFlag = true;
                                    this.arrowPressState = 0;
                                    this.character_reback = true;
                                    this.character_sel_offset_x = 0;                  
                                }
                            }

                            if (this.arrowPressState == 2) {
                                var5 = MyAPI.calNextPosition((double)this.character_sel_offset_x, -128.0, 1, 2);
                                this.character_sel_offset_x = var5;
                                if (this.character_sel_offset_x == -128) {
                                    ++this.character_id;
                                    this.character_circleturnright = true;
                                    this.character_id += PlayerObject.CHARACTER_LIST.length;
                                    this.character_id %= PlayerObject.CHARACTER_LIST.length;
                                    this.charSelRoleDrawer.setLoop(false);
                                    this.charSelRoleDrawer.setActionId(this.character_id + 28);
                                    this.character_idchangeFlag = true;
                                    this.arrowPressState = 0;
                                    this.character_reback = true;
                                    this.character_sel_offset_x = 0;

                                    if (this.character_id == 0) {
                                       if (MFMain.tails >= 7 || MFMain.tails != 7 && characterslots != 1 && maxcharacterslots >= 3) characterslots++;
                                       else if (MFMain.tails != 7 && characterslots == 1 && maxcharacterslots >= 3) characterslots += 2;
                                       if (characterslots > maxcharacterslots) characterslots = 1;
                                       if (maxcharacterslots >= 3 || maxcharacterslots == 2 && MFMain.tails >= 7) initCharacterSelectResPrefix(characterslots != 1 ? "_slot" + characterslots : "");
                                    }
                                }
                            }
                        }

                        if (this.character_idchangeFlag && this.character_sel_offset_x == 0) {
                            if (this.character_reback) {
                                this.character_move = true;
                                AnimationDrawer var6 = this.charSelAniDrawer;
                                byte var7;
                                if (this.character_circleturnright) {
                                    var7 = 1;
                                } else {
                                    var7 = 2;
                                }

                                var6.setActionId(var7);
                                this.charSelAniDrawer.restart();
                            }

                            this.charSelFilAniDrawer.setActionId(this.character_id + 1);
                            this.character_idchangeFlag = false;
                            Key.touchcharselselect.reset();
                            //if (characterslots == 2 && MFMain.tails < 7) MFMain.tails = 7;
                            if (characterslots == 2 && MFMain.tails < 7) characterslots = 1;
                            SoundSystem.getInstance().playSe(0);
                        }

                        if (!this.character_idchangeFlag && this.character_sel_offset_x < 8 && this.character_sel_offset_x > -8 && (Key.press(16777216) || Key.touchcharselselect.IsButtonPress() && this.cursor == 2)) {
                            if (ChargePlatform.isChargedByIndex(0)) {
                                SoundSystem.getInstance().playSe(1);
                            } else if (this.character_id == 0) {
                                SoundSystem.getInstance().playSe(1);
                            } else {
                                SoundSystem.getInstance().playSe(2);
                            }

                            if (!ChargePlatform.isChargedByIndex(0) && this.character_id == 0 || ChargePlatform.isChargedByIndex(0)) {
                                this.charSelTitleDrawer.setActionId(27);
                                this.character_arrow_display = false;
                                this.charSelRoleDrawer.setActionId(this.character_id + 9);
                                this.character_outer = true;
                                this.character_sel_offset_x = 0;
                            }
                        }

                        this.character_preid = this.character_id;
                    }

                    if (this.character_sel_offset_x == 0 && (Key.buttonPress(524288 | 8388608) || Key.touchcharselreturn.IsButtonPress() && this.returnCursor == 1) && fadeChangeOver()) {
                        this.changeStateWithFade(this.preCharaterSelectState);
                        switch (this.preCharaterSelectState) {
                            case 2:
                                this.isTitleBGMPlay = false;
                                Key.touchMainMenuInit2();
                                this.initTitleRes2();
                                SoundSystem.getInstance().stopBgm(false);
                                break;
                            case 24:
                                Key.touchProRaceModeInit();
                                this.initTimeStageRes();
                        }

                        SoundSystem.getInstance().playSe(2);
                    }
                }

                if (this.character_outer && this.charSelRoleDrawer.checkEnd()) {
                    PlayerObject.setCharacter(this.character_id);
                    if (MFMain.multiplayer) {
                        StageManager.setStageID(0);
                        StageManager.setStartStageID(0);
                        setState(1);
                        PlayerObject.resetGameParam();
                    } else {
                    if (StageManager.getStageID() == 0 && StageManager.getOpenedStageId() == 0 && GameObject.stageModeState == 0) {
                        StageManager.setStageID(0);
                        StageManager.setStartStageID(0);
                        setState(1);
                        PlayerObject.resetGameParam();
                        StageManager.saveStageRecord();
                    } else {
                        this.changeStateWithFade(14);
                        preStageSelectState = 23;
                        this.menuInit(this.STAGE_TOTAL_NUM);
                        this.initStageSelectRes();
                    }
                    }

                    Key.touchCharacterSelectModeClose();
                }
            }
        } else {
            this.returnCursor = 0;
        }

    }

    private void creditDraw(MFGraphics var1) {
        if (muiAniDrawer == null) {
            StringBuilder var5 = new StringBuilder("/lang");
            var5.append(GlobalResource.languageConfig);
            var5.append("/mui");
            muiAniDrawer = (new Animation(var5.toString())).getDrawer(0, false, 0);
        } else {
            muiAniDrawer.setActionId(63);
            ++PageFrameCnt;
            PageFrameCnt %= 11;
            if (PageFrameCnt % 2 == 0) {
                ++PageBackGroundOffsetX;
                PageBackGroundOffsetX %= 112;
                --PageBackGroundOffsetY;
                PageBackGroundOffsetY %= 56;
            }

            int var2;
            int var3;
            for(var2 = PageBackGroundOffsetX - 112; var2 < SCREEN_WIDTH * 3 / 2; var2 += 112) {
                for(var3 = PageBackGroundOffsetY - 56; var3 < SCREEN_HEIGHT * 3 / 2; var3 += 56) {
                    muiAniDrawer.draw(var1, var2, var3);
                }
            }

            for(var2 = 0; var2 < 8; ++var2) {
                MFImage var7 = this.textBGImage;
                int var4 = SCREEN_WIDTH;
                var3 = SCREEN_HEIGHT;
                MyAPI.drawImage(var1, var7, (var4 >> 1) - 104 + var2 * 26, (var3 >> 1) - 72, 0);
            }

            if (fadeAlpha < 64) {
                String[] var8 = strForShow;
                var3 = SCREEN_WIDTH;
                var2 = SCREEN_HEIGHT;
                MyAPI.drawStrings(var1, var8, (var3 >> 1) - 104, (var2 >> 1) - 72 + 5, 208, 131, 0, true, 16777215, 4656650, 0, 11);
            }

            if (MyAPI.upPermit) {
                muiUpArrowDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 25, SCREEN_HEIGHT);
            }

            if (MyAPI.downPermit) {
                muiDownArrowDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 24, SCREEN_HEIGHT);
            }

            AnimationDrawer var9 = muiAniDrawer;
            byte var6;
            if (Key.touchhelpreturn.Isin() || Key.repeat(524288 | 8388608)) {
                var6 = 5;
            } else {
                var6 = 0;
            }

            var9.setActionId(var6 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

        drawFade(var1);
    }

    private void creditInit() {
        MyAPI.initString();
        strForShow = MyAPI.getStrings(aboutStrings[0], 11, SCREEN_WIDTH);
        this.creditStringLineNum = strForShow.length;
        this.creditOffsetY = 0;
        StringBuilder var1 = new StringBuilder("/lang");
        var1.append(GlobalResource.languageConfig);
        var1.append("/mui");
        muiUpArrowDrawer = (new Animation(var1.toString())).getDrawer(93, true, 0);
        var1 = new StringBuilder("/lang");
        var1.append(GlobalResource.languageConfig);
        var1.append("/mui");
        muiDownArrowDrawer = (new Animation(var1.toString())).getDrawer(94, true, 0);
        Key.touchInstructionInit();
        SoundSystem.getInstance().playBgm(33);
        this.arrowframecnt = 0;
        PageFrameCnt = 0;
        if (this.textBGImage == null) {
            this.textBGImage = MFImage.createImage("/animation/text_bg.png");
        }

        this.returnPageCursor = 0;
    }

    private void creditLogic() {
        if (Key.slidesensorhelp.isSliding()) {
            if (Key.slidesensorhelp.isSlide(Key.DIR_UP)) {
                MyAPI.logicString(true, false);
            } else if (Key.slidesensorhelp.isSlide(Key.DIR_DOWN)) {
                MyAPI.logicString(false, true);
            }
        }

        this.creditOffsetY %= this.creditStringLineNum * LINE_SPACE + SCREEN_HEIGHT;
        if (Key.touchhelpreturn.Isin() && Key.touchpage.IsClick()) {
            this.returnPageCursor = 1;
        }

        if ((Key.buttonPress(524288 | 8388608) || Key.touchhelpreturn.IsButtonPress() && this.returnPageCursor == 1) && fadeChangeOver()) {
            this.changeStateWithFade(9);
            this.isOptionDisFlag = false;
            SoundSystem.getInstance().playSe(2);
        }

        if (Key.repeat(4) || Key.touchhelpuparrow.Isin()) {
            ++this.arrowframecnt;
            if (this.arrowframecnt <= 4 && !this.isArrowClicked) {
                MyAPI.logicString(false, true);
                this.isArrowClicked = true;
            } else if (this.arrowframecnt > 4 && this.arrowframecnt % 2 == 0) {
                MyAPI.logicString(false, true);
            }
        } else if (Key.repeat(8) || Key.touchhelpdownarrow.Isin()) {
            ++this.arrowframecnt;
            if (this.arrowframecnt <= 4 && !this.isArrowClicked) {
                MyAPI.logicString(true, false);
                this.isArrowClicked = true;
            } else if (this.arrowframecnt > 4 && this.arrowframecnt % 2 == 0) {
                MyAPI.logicString(true, false);
            }
        } else {
            this.arrowframecnt = 0;
            this.isArrowClicked = false;
        }

    }

    private void degreeLogic() {
        ++this.titleDegree;
        this.titleDegree %= OFFSET_ARRAY.length;
    }

    private void drawCharacterMovingBG(MFGraphics var1, int var2) {
        this.recordAniDrawer.setActionId(var2);
        this.characterRecordBGOffsetX_1 += 4;
        this.characterRecordBGOffsetX_1 %= 128;

        int var3;
        for(var2 = this.characterRecordBGOffsetX_1; var2 < SCREEN_WIDTH * 2; var2 += 128) {
            for(var3 = 0; var3 < SCREEN_HEIGHT; var3 += 48) {
                this.recordAniDrawer.draw(var1, var2 - 128, var3);
            }
        }

        this.characterRecordBGOffsetX_2 -= 4;
        this.characterRecordBGOffsetX_2 %= 128;

        for(var2 = this.characterRecordBGOffsetX_2 - 64; var2 < SCREEN_WIDTH * 3 / 2; var2 += 128) {
            for(var3 = 0; var3 < SCREEN_HEIGHT; var3 += 48) {
                this.recordAniDrawer.draw(var1, var2, var3 + 24);
            }
        }

    }

    private void drawCharacterRecord(MFGraphics var1) {
        this.drawCharacterMovingBG(var1, this.characterRecordID);
        this.recordAniDrawer.draw(var1, this.characterRecordID + 4, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, false, 0);
        this.recordAniDrawer.draw(var1, this.stage_characterRecord_ID + 14, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, false, 0);
        this.drawRecordArrow(var1);
        this.drawRecordTime(var1, StageManager.getTimeModeScore(this.characterRecordID, this.stage_characterRecord_ID, 0), (SCREEN_HEIGHT >> 1) + 12);
        this.drawRecordTime(var1, StageManager.getTimeModeScore(this.characterRecordID, this.stage_characterRecord_ID, 1), (SCREEN_HEIGHT >> 1) + 12 + 16);
        this.drawRecordTime(var1, StageManager.getTimeModeScore(this.characterRecordID, this.stage_characterRecord_ID, 2), (SCREEN_HEIGHT >> 1) + 12 + 32);
        if (muiAniDrawer == null) {
            StringBuilder var4 = new StringBuilder("/lang");
            var4.append(GlobalResource.languageConfig);
            var4.append("/mui");
            muiAniDrawer = (new Animation(var4.toString())).getDrawer(0, false, 0);
        } else {
            AnimationDrawer var3 = muiAniDrawer;
            byte var2;
            if (Key.touchintergraderecordreturn.Isin() || Key.repeat(524288 | 8388608)) {
                var2 = 5;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

    }

    private void drawCharacterSelect(MFGraphics var1) {
        var1.setColor(16777215);
        MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.charSelAniDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
        if (this.character_sel_frame_cnt > 4 && this.character_sel_frame_cnt <= 16) {
            this.charSelCaseDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
            if (this.character_sel_frame_cnt == 16) {
                this.charSelCaseDrawer.setLoop(true);
            }
        }

        AnimationDrawer var5;
        if (this.character_sel_frame_cnt > 9) {
            var5 = this.charSelRoleDrawer;
            int var4 = SCREEN_WIDTH;
            int var3 = this.character_sel_offset_x;
            int var2 = SCREEN_HEIGHT;
            var5.draw(var1, (var4 >> 1) + var3, var2 >> 1);
        }

        if (this.character_sel_frame_cnt > 16) {
            if (this.character_arrow_display) {
                this.charSelArrowDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
            }

            this.charSelTitleDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 256, SCREEN_HEIGHT >> 1);
            this.charSelCaseDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
            if (this.character_move && this.charSelCaseDrawer.checkEnd()) {
                this.charSelCaseDrawer.setLoop(false);
                this.charSelCaseDrawer.setActionId(this.character_id * 3 + 17);
                this.character_move = false;
            }
        }

        if (muiAniDrawer == null) {
            StringBuilder var7 = new StringBuilder("/lang");
            var7.append(GlobalResource.languageConfig);
            var7.append("/mui");
            muiAniDrawer = (new Animation(var7.toString())).getDrawer(0, false, 0);
        } else {
            if (Key.touchcharselreturn != null) {
                var5 = muiAniDrawer;
                byte var6;
                if (Key.touchcharselreturn.Isin() || Key.repeat(524288 | 8388608)) {
                    var6 = 5;
                } else {
                    var6 = 0;
                }

                var5.setActionId(var6 + 61);
            }

            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

        drawFade(var1);
    }

    private void drawIntergradeRecord(MFGraphics var1) {
        this.drawCharacterMovingBG(var1, this.characterRecordID);
        this.recordAniDrawer.draw(var1, this.characterRecordID + 8, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, false, 0);
        this.recordAniDrawer.draw(var1, this.stage_characterRecord_ID + 14, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, false, 0);
        this.drawStageNameinIntergradeRecord(var1);
        this.drawRecordTime(var1, StageManager.getTimeModeScore(this.characterRecordID, this.stage_characterRecord_ID, 0), (SCREEN_HEIGHT >> 1) + 12);
        this.drawRecordTime(var1, StageManager.getTimeModeScore(this.characterRecordID, this.stage_characterRecord_ID, 1), (SCREEN_HEIGHT >> 1) + 12 + 16);
        this.drawRecordTime(var1, StageManager.getTimeModeScore(this.characterRecordID, this.stage_characterRecord_ID, 2), (SCREEN_HEIGHT >> 1) + 12 + 32);
    }

    private void drawMainMenu(MFGraphics var1) {
        this.drawMainMenuMultiItems(var1);
    }

    private void drawMainMenuMultiItems(MFGraphics var1) {
        if (state == 2) {
            this.isAtMainMenu = true;
        } else {
            this.isAtMainMenu = false;
        }

        this.degree = MyAPI.calNextPosition((double)this.degree, 0.0, 1, 3);
        if (this.degree == 0) {
            this.menuMoving = false;
        }

        this.titleAniDrawer.setActionId(14);
        this.titleAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 40);
        int var4 = this.mainMenuItemCursor;
        int var5 = this.currentElement.length;
        int var6 = this.currentElement.length;

        AnimationDrawer var9;
        for(int var2 = 0; var2 < 4; ++var2) {
            int var3 = ((var4 - 1 - 1 + var5) % var6 + var2) % this.currentElement.length;
            int var7 = this.degree;
            var7 = var7 + 20 + (var2 - 1) * 20;
            if (var7 >= 10 && var7 <= 70) {
                var9 = this.titleAniDrawer;
                int var8 = this.currentElement[var3];
                byte var12;
                if (this.mainMenuEnsureFlag && var4 == var3) {
                    var12 = 1;
                } else {
                    var12 = 0;
                }

                var9.setActionId(var8 + var12);
                this.titleAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + var7);
            }
        }

        this.titleAniDrawer.setActionId(15);
        this.titleAniDrawer.draw(var1, 0, SCREEN_HEIGHT >> 1);
        this.drawSegaLogo(var1);
        this.drawTitleName(var1);
        this.titleAniDrawer.setActionId(16);
        this.titleAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 40);
        if (muiAniDrawer == null) {
            StringBuilder var10 = new StringBuilder("/lang");
            var10.append(GlobalResource.languageConfig);
            var10.append("/mui");
            muiAniDrawer = (new Animation(var10.toString())).getDrawer(0, false, 0);
        } else {
            var9 = muiAniDrawer;
            byte var11;
            if (Key.touchmainmenureturn.Isin() || Key.repeat(524288 | 8388608)) {
                var11 = 5;
            } else {
                var11 = 0;
            }

            var9.setActionId(var11 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

    }

    private void drawMainMenuNormal(MFGraphics var1) {
        if (state == 2) {
            this.isAtMainMenu = true;
        } else {
            this.isAtMainMenu = false;
        }

        AnimationDrawer var3 = this.titleAniDrawer;
        byte var2;
        if (this.isAtMainMenu) {
            if (Key.touchmainmenustart.Isin() && this.cursor == 0) {
                var2 = 1;
            } else {
                var2 = 0;
            }
        } else {
            var2 = 0;
        }

        var3.setActionId(var2 + 3);
        this.titleAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 12);
        var3 = this.titleAniDrawer;
        if (this.isAtMainMenu) {
            if (Key.touchmainmenurace.Isin() && this.cursor == 1) {
                var2 = 1;
            } else {
                var2 = 0;
            }
        } else {
            var2 = 0;
        }

        var3.setActionId(var2 + 5);
        this.titleAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 32);
        var3 = this.titleAniDrawer;
        if (this.isAtMainMenu) {
            if (Key.touchmainmenuoption.Isin() && this.cursor == 2) {
                var2 = 1;
            } else {
                var2 = 0;
            }
        } else {
            var2 = 0;
        }

        var3.setActionId(var2 + 7);
        this.titleAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 52);
        var3 = this.titleAniDrawer;
        if (this.isAtMainMenu) {
            if (Key.touchmainmenuend.Isin() && this.cursor == 3) {
                var2 = 1;
            } else {
                var2 = 0;
            }
        } else {
            var2 = 0;
        }

        var3.setActionId(var2 + 9);
        this.titleAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 72);
        if (muiAniDrawer == null) {
            StringBuilder var4 = new StringBuilder("/lang");
            var4.append(GlobalResource.languageConfig);
            var4.append("/mui");
            muiAniDrawer = (new Animation(var4.toString())).getDrawer(0, false, 0);
        } else {
            var3 = muiAniDrawer;
            if (Key.touchmainmenureturn.Isin() || Key.repeat(524288 | 8388608)) {
                var2 = 5;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

    }

    private void drawMenuSelection1(MFGraphics var1, int var2, int var3, int var4) {
        drawMenuBar(var1, 1, 0, var4);
        this.selectMenuOffsetX += 8;

        for(this.selectMenuOffsetX %= MOVE_DIRECTION; var3 - this.selectMenuOffsetX > 0; var3 -= MOVE_DIRECTION) {
        }

        for(int var5 = 0; var5 < 2; ++var5) {
            int var6 = MOVE_DIRECTION;
            drawMenuFontById(var1, var2, var3 + var6 * var5 - this.selectMenuOffsetX, var4);
        }

    }

    private void drawProTimeAttack(MFGraphics var1) {
        var1.setColor(16777215);
        MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        this.timeAttAniDrawer.setActionId(0);
        this.timeAttAniDrawer.draw(var1, 0, 0);
        this.timeAttAniDrawer.setActionId(1);
        this.timeAttAniDrawer.draw(var1, SCREEN_WIDTH, SCREEN_HEIGHT >> 1);
        int var2;
        if ((Key.touchproracemodestart.Isin() || this.isRaceModeItemsSelected) && this.cursor == 0) {
            this.timeAttackOffsetX -= 2;
            this.timeAttackOffsetX %= 128;
            this.timeAttAniDrawer.setActionId(2);

            for(var2 = this.timeAttackOffsetX; var2 < SCREEN_WIDTH * 3 / 2; var2 += 128) {
                this.timeAttAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + var2 - 8, SCREEN_HEIGHT >> 1);
            }

            this.timeAttAniDrawer.draw(var1, this.timeAttackOffsetX - 128 + (SCREEN_WIDTH >> 1) - 8, SCREEN_HEIGHT >> 1);
        } else if ((Key.touchproracemoderecord.Isin() || this.isRaceModeItemsSelected) && this.cursor == 1) {
            this.timeAttackOffsetX -= 2;
            this.timeAttackOffsetX %= 128;
            this.timeAttAniDrawer.setActionId(3);

            for(var2 = this.timeAttackOffsetX; var2 < SCREEN_WIDTH * 3 / 2; var2 += 128) {
                this.timeAttAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + var2 - 8, SCREEN_HEIGHT >> 1);
            }

            this.timeAttAniDrawer.draw(var1, this.timeAttackOffsetX - 128 + (SCREEN_WIDTH >> 1) - 8, SCREEN_HEIGHT >> 1);
        } else {
            this.timeAttackOffsetX = 0;
        }

        if (muiAniDrawer == null) {
            StringBuilder var4 = new StringBuilder("/lang");
            var4.append(GlobalResource.languageConfig);
            var4.append("/mui");
            muiAniDrawer = (new Animation(var4.toString())).getDrawer(0, false, 0);
        } else {
            AnimationDrawer var3 = muiAniDrawer;
            byte var5;
            if (Key.touchproracemodereturn.Isin()) {
                var5 = 5;
            } else {
                var5 = 0;
            }

            var3.setActionId(var5 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

    }

    private void drawRecordArrow(MFGraphics var1) {
        ++this.recordArrowOffsetXID;
        this.recordArrowOffsetXID %= this.recordArrowOffsetXArray.length;
        AnimationDrawer var3 = this.recordAniDrawer;
        int var2 = SCREEN_WIDTH;
        var3.draw(var1, 12, this.recordArrowOffsetXArray[this.recordArrowOffsetXID] + var2, SCREEN_HEIGHT >> 1, false, 0);
        this.recordAniDrawer.draw(var1, 13, 0 - this.recordArrowOffsetXArray[this.recordArrowOffsetXID], SCREEN_HEIGHT >> 1, false, 0);
    }

    private void drawRecordTime(MFGraphics var1, int var2, int var3) {
        if (var2 == 0) {
            var2 = 599999;
        }

        int var6 = var2 / '\uea60';
        int var5 = var2 % '\uea60' / 1000;
        int var4 = var5 / 10;
        int var7 = var2 % '\uea60' % 1000 / 10;
        var2 = var7 / 10;
        this.recordAniDrawer.draw(var1, var6 + 34, (SCREEN_WIDTH >> 1) - 38, var3, false, 0);
        this.recordAniDrawer.draw(var1, var4 + 34, (SCREEN_WIDTH >> 1) - 6, var3, false, 0);
        this.recordAniDrawer.draw(var1, var5 % 10 + 34, (SCREEN_WIDTH >> 1) + 10, var3, false, 0);
        this.recordAniDrawer.draw(var1, var2 + 34, (SCREEN_WIDTH >> 1) + 42, var3, false, 0);
        this.recordAniDrawer.draw(var1, var7 % 10 + 34, (SCREEN_WIDTH >> 1) + 58, var3, false, 0);
    }

    private void drawRecordtime(MFGraphics var1, int var2, int var3, int var4) {
        PlayerObject.drawRecordTimeLeft(var1, var2, var3, var4);
    }

    private void drawRecordtimeScroll(MFGraphics var1, int var2, int var3, int var4, int var5, int var6) {
        drawBar(var1, 0, var3);
        this.itemOffsetX += var5;
        this.itemOffsetX %= var6;

        for(var5 = 0; var5 - this.itemOffsetX > 0; var5 -= var6) {
        }

        int var8 = (SCREEN_WIDTH + var6 - 1) / var6;

        for(int var7 = 0; var7 < var8 + 2; ++var7) {
            int var9 = var5 + var7 * var6;
            drawMenuFontById(var1, var2, var9 - this.itemOffsetX, var3);
            this.drawRecordtime(var1, var4, var9 - this.itemOffsetX + FONT_WIDTH, var3);
        }

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

    private void drawSegaLogo(MFGraphics var1) {
    }

    private void drawStageEmerald(MFGraphics var1, int var2) {
        int var3;
        int var4;
        int var5;
        int var6;
        int var7;
        int var8;
        AnimationDrawer var10;
        if (var2 == 7) {
            if (SpecialStageState.emeraldState(var2 - 1) == 0) {
                switch (SpecialStageState.emeraldState(var2)) {
                    case 1:
                        var10 = this.stageSelEmeraldDrawer;
                        var4 = SCREEN_WIDTH;
                        var8 = this.stageDrawStartY;
                        var6 = this.offsetY[(var2 >> 1) * 2 + 1];
                        var5 = this.stageDrawOffsetY;
                        var3 = this.stageselectslide_y;
                        var7 = this.stageSelectArrowDriveY;
                        var10.draw(var1, 6, var4, var7 + var8 + var6 + ((var2 >> 1) + 1) * 48 + var5 + var3, false, 0);
                        break;
                    case 2:
                        var10 = this.stageSelEmeraldDrawer;
                        var8 = SCREEN_WIDTH;
                        var7 = this.stageDrawStartY;
                        var5 = this.offsetY[(var2 >> 1) * 2 + 1];
                        var6 = this.stageDrawOffsetY;
                        var3 = this.stageselectslide_y;
                        var4 = this.stageSelectArrowDriveY;
                        var10.draw(var1, 0, var8, var7 + var5 + ((var2 >> 1) + 1) * 48 + var6 + var3 + var4, false, 0);
                }
            } else {
                switch (SpecialStageState.emeraldState(var2)) {
                    case 1:
                        var10 = this.stageSelEmeraldDrawer;
                        var6 = SCREEN_WIDTH;
                        var4 = this.stageDrawStartY;
                        var3 = this.offsetY[(var2 >> 1) * 2 + 1];
                        var8 = this.stageDrawOffsetY;
                        var5 = this.stageselectslide_y;
                        var7 = this.stageSelectArrowDriveY;
                        var10.draw(var1, 7, var6, var7 + var4 + var3 + ((var2 >> 1) + 1) * 48 + var8 + var5, false, 0);
                        break;
                    case 2:
                        var10 = this.stageSelEmeraldDrawer;
                        var7 = SCREEN_WIDTH;
                        var8 = this.stageDrawStartY;
                        var4 = this.offsetY[(var2 >> 1) * 2 + 1];
                        var3 = this.stageDrawOffsetY;
                        var6 = this.stageselectslide_y;
                        var5 = this.stageSelectArrowDriveY;
                        var10.draw(var1, 1, var7, var8 + var4 + ((var2 >> 1) + 1) * 48 + var3 + var6 + var5, false, 0);
                }
            }
        } else {
            switch (SpecialStageState.emeraldState(var2)) {
                case 1:
                    var10 = this.stageSelEmeraldDrawer;
                    if (var2 < 7) {
                        var3 = (var2 >> 1) + 2;
                    } else {
                        var3 = (var2 >> 1) + 2 + 2;
                    }

                    int var9 = SCREEN_WIDTH;
                    var5 = this.stageDrawStartY;
                    var4 = this.offsetY[(var2 >> 1) * 2 + 1];
                    var8 = this.stageDrawOffsetY;
                    var6 = this.stageselectslide_y;
                    var7 = this.stageSelectArrowDriveY;
                    var10.draw(var1, var3, var9, var7 + var5 + var4 + ((var2 >> 1) + 1) * 48 + var8 + var6, false, 0);
                    break;
                case 2:
                    var10 = this.stageSelEmeraldDrawer;
                    var6 = SCREEN_WIDTH;
                    var8 = this.stageDrawStartY;
                    var3 = this.offsetY[(var2 >> 1) * 2 + 1];
                    var7 = this.stageDrawOffsetY;
                    var5 = this.stageselectslide_y;
                    var4 = this.stageSelectArrowDriveY;
                    var10.draw(var1, 0, var6, var8 + var3 + ((var2 >> 1) + 1) * 48 + var7 + var5 + var4, false, 0);
            }
        }

    }

    private void drawStageName(MFGraphics var1, int var2, int var3, int var4) {
        AnimationDrawer var8 = this.stageSelAniDrawer;
        int var6 = SCREEN_WIDTH;
        int var5 = this.stageDrawStartY;
        int var7 = this.offsetY[var3];
        var8.draw(var1, var2 + var3, var6 >> 1, var5 + (var3 + 1) * 24 + var7 + var4, false, 0);
    }

    private void drawStageName(MFGraphics var1, int var2, int var3, int var4, int var5) {
        drawMenuFontById(var1, 109, COMFIRM_X, this.stageDrawStartY + ITEM_SPACE * var2 + this.offsetY[var2] + var5);
        drawMenuFontById(var1, var3 + 2 + var2 % 2, COMFIRM_X + 49 + var4, this.stageDrawStartY + ITEM_SPACE * var2 + this.offsetY[var2] + var5);
        drawMenuFontById(var1, var3 + 1, COMFIRM_X + 26 + var4, this.stageDrawStartY + ITEM_SPACE * var2 + this.offsetY[var2] + var5);
        drawMenuFontById(var1, (var2 >> 1) + var3 + 2, COMFIRM_X + 1 + var4, this.stageDrawStartY + ITEM_SPACE * var2 + this.offsetY[var2] + var5);
        drawMenuFontById(var1, var3, COMFIRM_X - 22 + var4, this.stageDrawStartY + ITEM_SPACE * var2 + this.offsetY[var2] + var5);
    }

    private void drawStageNameinIntergradeRecord(MFGraphics var1) {
        this.recordAniDrawer.draw(var1, 26, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, false, 0);
        this.intergradeRecordStageNameOffsetX -= 8;
        this.intergradeRecordStageNameOffsetX %= 200;
        int var2;
        if (this.stage_characterRecord_ID < 10) {
            var2 = (this.stage_characterRecord_ID >> 1) + 27;
        } else {
            var2 = this.stage_characterRecord_ID - 5 + 27;
        }

        for(int var3 = this.intergradeRecordStageNameOffsetX - 100; var3 < SCREEN_WIDTH * 3 / 2; var3 += 200) {
            this.recordAniDrawer.draw(var1, var2, var3, SCREEN_HEIGHT >> 1, false, 0);
        }

    }

    private void drawStageSelect(MFGraphics var1) {
        var1.setColor(16777215);
        MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        int var2;
        int var3;
        if (this.stageItemNumForShow != StageManager.STAGE_NUM) {
            var3 = this.stageDrawStartY;
            var2 = SCREEN_WIDTH;
            MyAPI.setClip(var1, 0, var3 - 12, var2, 200);
        }

        for(var2 = 0; var2 < StageManager.STAGE_NAME.length; ++var2) {
            if (var2 == this.optionMenuCursor && this.stage_select_state == 1 && this.isSelectable && this.stageYDirect == 0) {
                this.drawStageName(var1, 1, var2, this.stageDrawOffsetY + this.stageselectslide_y + this.stageSelectArrowDriveY);
                this.drawStageName(var1, 29, var2, this.stageDrawOffsetY + this.stageselectslide_y + this.stageSelectArrowDriveY);
            } else if (var2 <= StageManager.getOpenedStageId()) {
                this.drawStageName(var1, 15, var2, this.stageDrawOffsetY + this.stageselectslide_y + this.stageSelectArrowDriveY);
            } else if (GameObject.stageModeState == 0 && var2 < 13 || GameObject.stageModeState == 1 && (MFMain.cheat || var2 < 12)) {
                this.drawStageName(var1, 1, var2, this.stageDrawOffsetY + this.stageselectslide_y + this.stageSelectArrowDriveY);
            }
        }

        int var4;
        int var5;
        int var6;
        int var7;
        AnimationDrawer var9;
        for(var2 = 0; var2 < (StageManager.STAGE_NAME.length >> 1) - 1 && var2 * 2 <= StageManager.getOpenedStageId(); ++var2) {
            var9 = this.stageSelAniDrawer;
            var4 = SCREEN_WIDTH;
            var6 = this.stageDrawStartY;
            int var8 = this.offsetY[var2 * 2 + 1];
            var3 = this.stageDrawOffsetY;
            var7 = this.stageselectslide_y;
            var5 = this.stageSelectArrowDriveY;
            var9.draw(var1, var2 + 43, var4, var5 + var6 + var8 + (var2 + 1) * 48 + var3 + var7, false, 0);
        }

        if (StageManager.getOpenedStageId() / 2 * 2 + 1 > 12) {
            var2 = 12;
        } else {
            var2 = StageManager.getOpenedStageId() / 2 * 2 + 1;
        }

        var3 = var2;
        if (var2 == 7) {
            var3 = 8;
        }

        if (GameObject.stageModeState == 0) {
            for(var2 = 0; var2 < var3; ++var2) {
                this.drawStageEmerald(var1, var2);
            }
        }

        if (12 <= StageManager.getOpenedStageId()) {
            var9 = this.stageSelAniDrawer;
            var6 = SCREEN_WIDTH;
            var3 = this.stageDrawStartY;
            var2 = this.offsetY[12];
            var4 = this.stageDrawOffsetY;
            var7 = this.stageselectslide_y;
            var5 = this.stageSelectArrowDriveY;
            var9.draw(var1, 49, var6, var5 + var3 + var2 + 336 - 24 + var4 + var7, false, 0);
        }

        if (13 <= StageManager.getOpenedStageId()) {
            var9 = this.stageSelAniDrawer;
            var5 = SCREEN_WIDTH;
            var6 = this.stageDrawStartY;
            var2 = this.offsetY[13];
            var3 = this.stageDrawOffsetY;
            var4 = this.stageselectslide_y;
            var7 = this.stageSelectArrowDriveY;
            var9.draw(var1, 50, var5, var7 + var6 + var2 + 336 + var3 + var4, false, 0);
        }

        this.stageSelAniDrawer.setActionId(0);
        this.stageSelAniDrawer.draw(var1, 0, 0);
        if (this.isDrawUpArrow) {
            this.stageSelArrowUpDrawer.draw(var1, 44, (SCREEN_HEIGHT >> 1) - 49);
        }

        if (this.isDrawDownArrow) {
            this.stageSelArrowDownDrawer.draw(var1, 44, (SCREEN_HEIGHT >> 1) + 49);
        }

        if (muiAniDrawer == null) {
            StringBuilder var10 = new StringBuilder("/lang");
            var10.append(GlobalResource.languageConfig);
            var10.append("/mui");
            muiAniDrawer = (new Animation(var10.toString())).getDrawer(0, false, 0);
        } else {
            var9 = muiAniDrawer;
            byte var11;
            if (Key.touchstageselectreturn.Isin() || Key.repeat(524288 | 8388608)) {
                var11 = 5;
            } else {
                var11 = 0;
            }

            var9.setActionId(var11 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

    }

    private void drawTimeNum(MFGraphics var1, int var2, int var3, int var4, int var5) {
        int var6 = 1;

        int var7;
        for(var7 = 1; var7 < var5; ++var7) {
            var6 *= 10;
        }

        for(var7 = 0; var7 < var5; ++var7) {
            int var8 = Math.abs(var2 / var6);
            var6 /= 10;
            drawMenuFontById(var1, var8 % 10 + 37, var7 * 8 + var3, var4);
        }

    }

    public static void drawTitle(MFGraphics var0, int var1) {
        if (state == 38 || state == 1 || state == 2 || state == 12 || state == 4 || state == 41) {
            float var2 = (float)MFDevice.getDeviceHeight();
            float var3 = (float)titleLeftImage.getHeight();
            var2 /= var3;
            var0.saveCanvas();
            var0.scaleCanvas(var2, var2, 0, 0);
            var0.drawImage(titleLeftImage, 0, 0, 20);
            var0.restoreCanvas();
            var0.saveCanvas();
            var0.scaleCanvas(var2, var2, MFDevice.getDeviceWidth(), 0);
            MFImage var5 = titleRightImage;
            var1 = MFDevice.getDeviceWidth();
            var0.drawImage(var5, var1, 0, 24);
            var0.restoreCanvas();
            var0.saveCanvas();
            var1 = MFDevice.getDeviceWidth();
            int var4 = MFDevice.getDeviceHeight();
            var0.scaleCanvas(var2, var2, var1, var4);
            var5 = titleSegaImage;
            var4 = MFDevice.getDeviceWidth();
            var1 = MFDevice.getDeviceHeight();
            var0.drawImage(var5, var4, var1, 40);
            var0.restoreCanvas();
        }

    }

    private void drawTitle1(MFGraphics var1) {
        this.copyOffsetX = MyAPI.calNextPosition((double)this.copyOffsetX, 0.0, 1, 2);
        MFImage var5 = this.sonicBigImage;
        int var3 = this.sonicBigX;
        int var2 = this.cameraX;
        int var4 = SONIC_BIG_Y;
        MyAPI.drawImage(var1, var5, var3 - var2, var4, 20);
    }

    private void drawTitle2(MFGraphics var1) {
        if (this.logoX == LOGO_POSITION_X) {
            this.degreeLogic();
        }

    }

    private void drawTitleBg(MFGraphics var1) {
        var1.setColor(0);
        this.titleAniDrawer.setActionId(0);
        if (state == 1) {
            if (System.currentTimeMillis() / 500L % 2L == 0L) {
                this.titleAniDrawer.setActionId(2);
                this.titleAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 33);
            }
        } else if (state == 12 && this.quitFlag == 1) {
            this.titleAniDrawer.setActionId(2);
            this.titleAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 33);
        }

        this.drawSegaLogo(var1);
        this.drawTitleName(var1);
    }

    private void drawTitleName(MFGraphics var1) {
        if (this.titleFrame == 0) {
            this.titleScale = 4.0F;
            this.title_name_center_x = 216;
            this.title_name_center_y = 152;
        } else if (this.titleFrame == 1) {
            this.titleScale = 3.25F;
            this.title_name_center_x = 162;
            this.title_name_center_y = 114;
        } else if (this.titleFrame == 2) {
            this.titleScale = 2.5F;
            this.title_name_center_x = 108;
            this.title_name_center_y = 76;
        } else if (this.titleFrame == 3) {
            this.titleScale = 1.75F;
            this.title_name_center_x = 54;
            this.title_name_center_y = 38;
        } else if (this.titleFrame >= 4) {
            this.titleScale = 1.0F;
            this.title_name_center_x = 0;
            this.title_name_center_y = 0;
        }

        var1.saveCanvas();
        int var5 = SCREEN_WIDTH;
        int var4 = this.title_name_center_x;
        int var2 = SCREEN_HEIGHT;
        int var3 = this.title_name_center_y;
        var1.translateCanvas(var5 + var4, (var2 >> 1) + var3);
        var1.scaleCanvas(this.titleScale, this.titleScale);
        this.titleAniDrawer.setActionId(1);
        var1.restoreCanvas();
        if (this.titleFrame >= 4) {
            this.titleAniDrawer.setActionId(11);
        }

    }

    private static void drawTouchKeyAbout(MFGraphics var0) {
    }

    private static void drawTouchKeyConfirm(MFGraphics var0) {
    }

    private static void drawTouchKeyHelp(MFGraphics var0) {
    }

    private static void drawTouchKeyMainMenu(MFGraphics var0) {
    }

    private static void drawTouchKeyMenu(MFGraphics var0) {
    }

    private static void drawTouchKeyOption(MFGraphics var0) {
    }

    private static void drawTouchKeySelectStage(MFGraphics var0) {
    }

    private void gameover_rankingDraw(MFGraphics var1) {
        this.menuBgDraw(var1);

        for(int var2 = 0; var2 < SCREEN_WIDTH / 32 + 1; ++var2) {
            drawMenuFontById(var1, 111, var2 * 32, 0);
            drawMenuFontById(var1, 112, var2 * 32, SCREEN_HEIGHT);
        }

        this.drawMenuTitle(var1, 4, 0);
        StageManager.drawNormalHighScore(var1);
    }

    private void gameover_rankingLogic() {
        ++this.timecount_ranking;
        switch (this.comfirmLogic()) {
            case 400:
                this.changeStateWithFade(2);
                this.menuInit(MAIN_MENU);
                this.mainMenuInit();
                this.returnCursor = 0;
                StageManager.drawHighScoreEnd();
            default:
        }
    }

    private int getAvailableItemNum() {
        int var2 = (SCREEN_HEIGHT - INTERVAL_FOR_RECORD_BAR - MENU_TITLE_DRAW_OFFSET_Y - INTERVAL_ABOVE_RECORD_BAR) / ITEM_SPACE;
        int var1 = var2;
        if (var2 % 2 != 0) {
            if (var2 < 2) {
                var1 = var2 + 1;
            } else {
                var1 = var2 - 1;
            }
        }

        int var3;
        int var4;
        if (var1 > StageManager.STAGE_NUM) {
            if (StageManager.STAGE_NUM < 8) {
                int var5 = MENU_TITLE_DRAW_OFFSET_Y;
                int var7 = MENU_SPACE;
                var1 = SCREEN_HEIGHT;
                int var8 = INTERVAL_FOR_RECORD_BAR;
                var4 = MENU_TITLE_DRAW_OFFSET_Y;
                var2 = INTERVAL_ABOVE_RECORD_BAR;
                var3 = StageManager.STAGE_NUM;
                int var6 = ITEM_SPACE;
                this.stageDrawStartY = var5 + (var7 >> 1) + (var1 - var8 - var4 - var2 - var3 * var6 >> 1);
            } else {
                this.stageDrawStartY = 72;
            }

            var1 = StageManager.STAGE_NUM;
        } else {
            var3 = MENU_TITLE_DRAW_OFFSET_Y;
            var4 = MENU_SPACE;
            var2 = ITEM_SPACE;
            this.stageDrawStartY = var3 + (var4 >> 1) + (var2 >> 1);
        }

        return var1;
    }

    private int getOffsetY(int var1) {
        return OFFSET_ARRAY[this.titleDegree];
    }
    
    public void initCharacterSelectResPrefix(String prefix) {
            StringBuilder var1;
            try {
            if (ChargePlatform.isChargedByIndex(0)) {
                var1 = new StringBuilder("/lang");
                var1.append(GlobalResource.languageConfig);
                var1.append("/utl_res/character_select" + prefix + ".dat");
                this.charSelAni = Animation.getInstanceFromQi(var1.toString());
            } else {
                var1 = new StringBuilder("/lang");
                var1.append(GlobalResource.languageConfig);
                var1.append("/utl_res/character_select_locked" + prefix + ".dat");
                this.charSelAni = Animation.getInstanceFromQi(var1.toString());
            }

            this.charSelAniDrawer = this.charSelAni[0].getDrawer(0, false, 0);
            this.charSelCaseDrawer = this.charSelAni[0].getDrawer(3, false, 0);
            this.charSelRoleDrawer = this.charSelAni[0].getDrawer(4, false, 0);
            this.charSelArrowDrawer = this.charSelAni[0].getDrawer(13, true, 0);
            this.charSelTitleDrawer = this.charSelAni[0].getDrawer(14, true, 0);
            var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/utl_res/character_select_filter" + prefix + ".dat");
            this.charSelFilAni = Animation.getInstanceFromQi(var1.toString());
            this.charSelFilAniDrawer = this.charSelFilAni[0].getDrawer(0, false, 0);
            } catch (Exception e) {
            if (!prefix.isEmpty()) initCharacterSelectResPrefix("");
            }
        }

    public void initCharacterSelectRes() {
        if (characterslots != 1 || this.charSelAni == null || this.charSelFilAni == null) {
            StringBuilder var1;
            if (ChargePlatform.isChargedByIndex(0)) {
                var1 = new StringBuilder("/lang");
                var1.append(GlobalResource.languageConfig);
                var1.append("/utl_res/character_select.dat");
                this.charSelAni = Animation.getInstanceFromQi(var1.toString());
            } else {
                var1 = new StringBuilder("/lang");
                var1.append(GlobalResource.languageConfig);
                var1.append("/utl_res/character_select_locked.dat");
                this.charSelAni = Animation.getInstanceFromQi(var1.toString());
            }

            this.charSelAniDrawer = this.charSelAni[0].getDrawer(0, false, 0);
            this.charSelCaseDrawer = this.charSelAni[0].getDrawer(3, false, 0);
            this.charSelRoleDrawer = this.charSelAni[0].getDrawer(4, false, 0);
            this.charSelArrowDrawer = this.charSelAni[0].getDrawer(13, true, 0);
            this.charSelTitleDrawer = this.charSelAni[0].getDrawer(14, true, 0);
            var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/utl_res/character_select_filter.dat");
            this.charSelFilAni = Animation.getInstanceFromQi(var1.toString());
            this.charSelFilAniDrawer = this.charSelFilAni[0].getDrawer(0, false, 0);
        }

        this.charSelAniDrawer.setActionId(0);
        this.charSelCaseDrawer.setActionId(3);
        this.charSelRoleDrawer.setActionId(4);
        this.charSelArrowDrawer.setActionId(13);
        this.charSelTitleDrawer.setActionId(14);
        this.charSelFilAniDrawer.setActionId(0);
        this.charSelAniDrawer.restart();
        this.charSelCaseDrawer.restart();
        this.charSelRoleDrawer.restart();
        this.charSelArrowDrawer.restart();
        this.charSelTitleDrawer.restart();
        this.charSelFilAniDrawer.restart();
        this.character_sel_frame_cnt = 0;
        this.character_id = 0;
        this.character_move = false;
        this.character_arrow_display = true;
        this.character_outer = false;
        this.cursor = 0;
        this.character_sel_offset_x = 0;
        this.character_idchangeFlag = false;
        this.character_offset_state = 0;
        Key.touchCharacterSelectModeInit();
        this.returnCursor = 0;
    }

    private void initIntergradeRecordRes() {
        StringBuilder var1;
        if (ChargePlatform.isChargedByIndex(0)) {
            var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/utl_res/record.dat");
            this.recordAni = Animation.getInstanceFromQi(var1.toString());
            this.recordAniDrawer = this.recordAni[0].getDrawer(0, true, 0);
        } else {
            var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/utl_res/record_locked.dat");
            this.recordAni = Animation.getInstanceFromQi(var1.toString());
            this.recordAniDrawer = this.recordAni[0].getDrawer(0, true, 0);
        }

        this.characterRecordID = PlayerObject.getCharacterID();
        Key.touchCharacterRecordInit();
        Key.touchanykeyInit();
        this.intergradeRecordtoGamecnt = 0;
    }

    private void initRecordRes() {
        StringBuilder var1;
        if (ChargePlatform.isChargedByIndex(0)) {
            var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/utl_res/record.dat");
            this.recordAni = Animation.getInstanceFromQi(var1.toString());
            this.recordAniDrawer = this.recordAni[0].getDrawer(0, true, 0);
        } else {
            var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/utl_res/record_locked.dat");
            this.recordAni = Animation.getInstanceFromQi(var1.toString());
            this.recordAniDrawer = this.recordAni[0].getDrawer(0, true, 0);
        }

        Key.touchCharacterRecordInit();
        this.characterRecordID = 0;
        this.characterRecordDisFlag = false;
        this.characterRecordScoreUpdateIconY = -32;
        this.characterRecordScoreUpdateCursor = 0;
    }

    private void initStageSelectRes() {
        if (this.stageSelAni == null) {
            StringBuilder var2 = new StringBuilder("/lang");
            var2.append(GlobalResource.languageConfig);
            var2.append("/utl_res/stage_select.dat");
            this.stageSelAni = Animation.getInstanceFromQi(var2.toString());
            this.stageSelAniDrawer = this.stageSelAni[0].getDrawer(0, true, 0);
            this.stageSelArrowUpDrawer = this.stageSelAni[0].getDrawer(51, true, 0);
            this.stageSelArrowDownDrawer = this.stageSelAni[0].getDrawer(52, true, 0);
        }

        if (this.stageSelEmeraldDrawer == null) {
            this.stageSelEmeraldDrawer = (new Animation("/animation/stage_select_emerald")).getDrawer(0, false, 0);
        }

        Key.touchStageSelectModeInit();
        this.stageItemNumForShow = 6;
        this.stageDrawEndY = this.stageDrawStartY + this.stageItemNumForShow * ITEM_SPACE - (ITEM_SPACE >> 1);
        this.stageStartIndex = 0;
        this.stageDrawOffsetY = 0;
        this.offsetY = new int[this.STAGE_TOTAL_NUM];
        this.vY = new int[this.STAGE_TOTAL_NUM];
        this.offsetY[0] = SCREEN_HEIGHT >> 1;
        this.vY[0] = 6;

        for(int var1 = 1; var1 < this.STAGE_TOTAL_NUM; ++var1) {
            this.offsetY[var1] = this.offsetY[var1 - 1] * 2;
            this.vY[var1] = this.vY[var1 - 1] * 2;
        }

        this.stage_select_state = 0;
        this.stageSelectReturnFlag = false;
        this.optionMenuCursor = -1;
    }

    private void initStageSelet() {
        this.stageItemNumForShow = this.getAvailableItemNum();
        this.stageDrawEndY = this.stageDrawStartY + this.stageItemNumForShow * ITEM_SPACE - (ITEM_SPACE >> 1);
        this.stageStartIndex = 0;
        this.stageDrawOffsetY = 0;
        this.offsetY = new int[this.STAGE_TOTAL_NUM];
        this.vY = new int[this.STAGE_TOTAL_NUM];
        this.offsetY[0] = (SCREEN_HEIGHT >> 1) - 72;
        this.vY[0] = 4;

        for(int var1 = 1; var1 < this.STAGE_TOTAL_NUM; ++var1) {
            this.offsetY[var1] = this.offsetY[var1 - 1] * 2;
            this.vY[var1] = this.vY[var1 - 1] * 2;
        }

        this.stage_select_state = 0;
        this.optionMenuCursor = 0;
    }

    private void initTimeStageRes() {
        if (this.timeAttAni == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/utl_res/time_attack.dat");
            this.timeAttAni = Animation.getInstanceFromQi(var1.toString());
            this.timeAttAniDrawer = this.timeAttAni[0].getDrawer(0, true, 0);
        }

        Key.touchProRaceModeInit();
        SoundSystem.getInstance().playBgm(2);
        this.isRaceModeItemsSelected = false;
    }

    private void initTitleRes() {
        if (titleLeftImage == null) {
            MFDevice.enableLayer(-1);
            titleLeftImage = MFImage.createImage("/title/title_left.png");
            StringBuilder var1;
            if (GlobalResource.languageConfig >= 6) {
                var1 = new StringBuilder("/lang");
                var1.append(GlobalResource.languageConfig);
                var1.append("/title_right.png");
            } else {
                var1 = new StringBuilder("/title/title_right.png");
            }

            titleRightImage = MFImage.createImage(var1.toString());
            titleSegaImage = MFImage.createImage("/title/title_sega.png");
            if (!ChargePlatform.isChargedByIndex(0) && ChargePlatform.getEnterTimesByIndex(0) < 3) {
                var1 = new StringBuilder("/lang");
                var1.append(GlobalResource.languageConfig);
                var1.append("/utl_res/title_locked.dat");
                this.titleAni = Animation.getInstanceFromQi(var1.toString());
                this.titleAniDrawer = this.titleAni[0].getDrawer(0, true, 0);
            } else {
                var1 = new StringBuilder("/lang");
                var1.append(GlobalResource.languageConfig);
                var1.append("/utl_res/title.dat");
                this.titleAni = Animation.getInstanceFromQi(var1.toString());
                this.titleAniDrawer = this.titleAni[0].getDrawer(0, true, 0);
            }

            this.titleFrame = 0;
            Key.touchMainMenuInit2();
        }

    }

    private void initTitleRes2() {
        StringBuilder var1;
        if (!ChargePlatform.isChargedByIndex(0) && ChargePlatform.getEnterTimesByIndex(0) < 3) {
            var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/utl_res/title_locked.dat");
            this.titleAni = Animation.getInstanceFromQi(var1.toString());
            this.titleAniDrawer = this.titleAni[0].getDrawer(0, true, 0);
        } else {
            var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/utl_res/title.dat");
            this.titleAni = Animation.getInstanceFromQi(var1.toString());
            this.titleAniDrawer = this.titleAni[0].getDrawer(0, true, 0);
        }

        this.titleFrame = 5;
        Key.touchMainMenuInit2();
    }

    private void intergradeRecordLogic() {
        ++this.intergradeRecordtoGamecnt;
        if (Key.press(Key.B_SEL | 16777216) && this.intergradeRecordtoGamecnt != 56) {
            this.intergradeRecordtoGamecnt = 56;
            SoundSystem.getInstance().playSe(1);
        }

        if (this.intergradeRecordtoGamecnt == 56) {
            StageManager.setStageID(this.stage_characterRecord_ID);
            SoundSystem.getInstance().stopBgm(false);
            setState(1);
            Key.touchCharacterRecordClose();
            Key.touchanykeyClose();
        }

        if (Key.press(524288) && fadeChangeOver()) {
            StageManager.setStageID(this.stage_characterRecord_ID);
            SoundSystem.getInstance().stopBgm(false);
            setState(1);
            Key.touchCharacterRecordClose();
            SoundSystem.getInstance().playSe(1);
        }

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

        IsInInterrupt = true;
        lastFading = fading;
        fading = false;
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

    private void mainMenuBackInit() {
        this.menuMoving = true;
        this.mainMenuBackFlag = true;
        this.degree = 0;
        this.degreeDes = (this.currentElement.length - 1) * 18;
    }

    private void mainMenuDraw(MFGraphics var1) {
        if (this.mainMenuBackFlag) {
            this.degree = MyAPI.calNextPositionReverse(this.degree, 0, this.degreeDes, 1, 3);
        } else {
            this.degree = MyAPI.calNextPosition((double)this.degree, (double)this.degreeDes, 1, 3);
        }

        if (this.degree == this.degreeDes) {
            this.menuMoving = false;
        }

        if (this.currentElement != null) {
            int var3 = this.cursor;
            int var5 = this.currentElement.length;
            int var4 = this.currentElement.length;

            for(int var2 = 0; var2 < 6; ++var2) {
                int var6 = this.currentElement.length;
                int var8 = this.degree - 18 + (var2 - 1) * 18;
                if (var8 >= -27 && var8 <= 63) {
                    int var7 = (MyAPI.dCos(var8) * 137 + 5100) / 100;
                    var8 = (MyAPI.dSin(var8) * 137 + 15900) / 100;
                    drawMenuFontById(var1, this.currentElement[((var3 - 1 - 1 + var5) % var4 + var2) % var6], var7, var8);
                }
            }
        }

    }

    private void mainMenuDraw2(MFGraphics var1) {
        MENU_OFFSET_X = (SCREEN_WIDTH >> 1) + 16;
        this.degree = MyAPI.calNextPosition((double)this.degree, 0.0, 1, 3);
        if (this.degree == 0) {
            this.menuMoving = false;
        }

        int var5 = this.cursor;
        int var3 = this.currentElement.length;
        int var4 = this.currentElement.length;

        for(int var2 = 0; var2 < 4; ++var2) {
            int var6 = this.currentElement.length;
            int var7 = MENU_OFFSET_X;
            int var8 = this.degree + LINE_START_Y + (MENU_SPACE + MENU_SPACE_INTERVAL) * (var2 - 1);
            if (var8 >= LINE_START_Y - (MENU_SPACE + MENU_SPACE_INTERVAL >> 1) && var8 <= LINE_START_Y + MENU_OFFSET_X * 2 + (MENU_SPACE + MENU_SPACE_INTERVAL >> 1)) {
                drawMenuFontById(var1, this.currentElement[((var5 - 1 - 1 + var3) % var4 + var2) % var6], var7, var8);
            }
        }

    }

    private void mainMenuDrawV(MFGraphics var1) {
        this.menuMoving = false;
        this.mainMenuBackFlag = false;
        int var4 = this.cursor;
        int var3 = this.elementNum;
        int var5 = this.elementNum;
        if (this.currentElement != null) {
            for(int var2 = 0; var2 < 3; ++var2) {
                int var7 = this.currentElement.length;
                int var6 = MAIN_MENU_V_CENTER_Y;
                int var8 = this.currentElement[((var4 + 0 - 1 + var3) % var5 + var2) % var7];
                var7 = MAIN_MENU_V_CENTER_X;
                drawMenuFontById(var1, var8, var7, var6 + (var2 - 1) * 20);
            }

            drawMenuFontById(var1, 95, MAIN_MENU_V_CENTER_X, (SCREEN_HEIGHT >> 1) + 10);
            drawMenuFontById(var1, 96, MAIN_MENU_V_CENTER_X, (SCREEN_HEIGHT >> 1) + 70);
        }

    }

    private void mainMenuInit() {
        this.menuMoving = true;
        this.degree = (this.currentElement.length - 1) * 18;
        this.degreeDes = 0;
    }

    private void mainMenuLogic() {
        this.mainMenuMultiItemsLogic();
    }

    private void mainMenuMultiItemsLogic() {
        if (!this.isTitleBGMPlay) {
            this.isTitleBGMPlay = true;
        }

        Key.touchanykeyClose();
        if (this.menuMoving) {
            this.mainMenuCursor = 0;
            this.returnCursor = 0;
            this.mainMenuEnsureFlag = false;
        } else {
            if (Key.touchmainmenuup.Isin() && Key.touchmainmenu.IsClick()) {
                this.mainMenuCursor = 1;
            } else if (Key.touchmainmenudown.Isin() && Key.touchmainmenu.IsClick()) {
                this.mainMenuCursor = 2;
            } else if (Key.touchmainmenuitem.Isin() && Key.touchmainmenu.IsClick()) {
                this.mainMenuCursor = 3;
                this.mainMenuEnsureFlag = true;
            }

            if (!Key.touchmainmenuitem.Isin()) {
                this.mainMenuEnsureFlag = false;
            }

            if (Key.touchmainmenureturn.Isin() && Key.touchmainmenu.IsClick()) {
                this.returnCursor = 1;
            }

            if (Key.touchmainmenuup.IsButtonPress() && this.mainMenuCursor == 1 || Key.press(4)) {
                --this.mainMenuItemCursor;
                this.mainMenuItemCursor = (this.mainMenuItemCursor + this.elementNum) % this.elementNum;
                this.changeUpSelect();
                SoundSystem.getInstance().playSe(3);
            } else if (Key.touchmainmenudown.IsButtonPress() && this.mainMenuCursor == 2 || Key.press(8)) {
                ++this.mainMenuItemCursor;
                this.mainMenuItemCursor = (this.mainMenuItemCursor + this.elementNum) % this.elementNum;
                this.changeDownSelect();
                SoundSystem.getInstance().playSe(3);
            } else if (Key.touchmainmenuitem.IsButtonPress() && this.mainMenuCursor == 3 || Key.press(16777216)) {
                boolean var1;
                int multActiv = multiMainNoMoreGameItems[1] == 19 || this.mainMenuItemCursor == 0 ? this.mainMenuItemCursor : this.mainMenuItemCursor + 1;
                switch (multActiv) {
                    case 0:
                        if (ChargePlatform.isChargedByIndex(0)) {
                            PlayerObject.stageModeState = 0;
                            if (!this.BP_gotoPaying()) {
                                this.gotoStageSelect();
                            }

                            if (this.isFromStartGame) {
                                this.isFromStartGame = false;
                            }
                        } else if (ChargePlatform.getEnterTimesByIndex(0) >= 3) {
                            MFDevice.setResponseInterruptFlag(false);
                            var1 = ChargePlatform.chargeByIndex(0);
                            MFDevice.setResponseInterruptFlag(true);
                            if (var1) {
                                PlayerObject.stageModeState = 0;
                                if (!this.BP_gotoPaying()) {
                                    this.gotoStageSelect();
                                }

                                if (this.isFromStartGame) {
                                    this.isFromStartGame = false;
                                }
                            }
                        }

                        this.mainMenuEnsureFlag = false;
                        break;
                    case 1:
                        MFMain.getInstance().runOnUiThread(new Runnable() {
                        @Override
                            public void run() {
                        MFMain.getInstance().initMultiplayer();
                        }
                        });
                        break;
                    case 2:
                        if (!ChargePlatform.isChargedByIndex(0) && ChargePlatform.getEnterTimesByIndex(0) >= 3) {
                            MFDevice.setResponseInterruptFlag(false);
                            var1 = ChargePlatform.chargeByIndex(0);
                            MFDevice.setResponseInterruptFlag(true);
                            this.mainMenuEnsureFlag = false;
                            if (var1) {
                                PlayerObject.stageModeState = 1;
                                this.changeStateWithFade(24);
                                this.initTimeStageRes();
                                this.cursor = 0;
                                if (this.isFromStartGame) {
                                    this.isFromStartGame = false;
                                }

                                this.isRaceModeItemsSelected = false;
                            }
                        } else {
                            PlayerObject.stageModeState = 1;
                            this.changeStateWithFade(24);
                            this.initTimeStageRes();
                            this.cursor = 0;
                            if (this.isFromStartGame) {
                                this.isFromStartGame = false;
                            }

                            this.isRaceModeItemsSelected = false;
                        }

                        if (!ChargePlatform.isChargedByIndex(0) && ChargePlatform.getEnterTimesByIndex(0) < 3) {
                            this.mainMenuItemCursor = 1;
                        }
                        break;
                    case 3:
                        this.optionInit();
                        this.changeStateWithFade(9);
                        if (this.isFromStartGame) {
                            this.isFromStartGame = false;
                        }
                        break;
                    case 4:
                        state = 12;
                        this.secondEnsureInit();
                        fadeInit(0, 220);
                        if (this.isFromStartGame) {
                            this.isFromStartGame = false;
                        }

                        this.isLinkMoreGame = true;
                        this.quitFlag = 0;
                        break;
                    case 5:
                        state = 12;
                        this.secondEnsureInit();
                        fadeInit(0, 220);
                        if (this.isFromStartGame) {
                            this.isFromStartGame = false;
                        }

                        this.isLinkMoreGame = false;
                        this.quitFlag = 0;
                        break;
                    case 6:
                        state = 12;
                        this.secondEnsureInit();
                        fadeInit(0, 220);
                        if (this.isFromStartGame) {
                            this.isFromStartGame = false;
                        }

                        this.isLinkMoreGame = false;
                        this.quitFlag = 0;
                }

                if (ChargePlatform.isChargedByIndex(0)) {
                    SoundSystem.getInstance().playSe(1);
                } else if (this.mainMenuItemCursor == 0) {
                    SoundSystem.getInstance().playSe(2);
                } else {
                    SoundSystem.getInstance().playSe(1);
                }
            }

            if ((Key.buttonPress(524288 | 8388608) || Key.touchmainmenureturn.IsButtonPress() && this.returnCursor == 1) && (fadeChangeOver() || this.isFromStartGame)) {
                state = 1;
                this.initTitleRes2();
                SoundSystem.getInstance().playSe(2);
                if (this.isFromStartGame) {
                    this.isFromStartGame = false;
                }

                Key.touchanykeyInit();
                fading = false;
            }
        }

    }

    private void mainMenuNormalLogic() {
        if (!this.isTitleBGMPlay) {
            this.isTitleBGMPlay = true;
        }

        Key.touchanykeyClose();
        if (Key.touchmainmenustart.Isin() && Key.touchmainmenu.IsClick()) {
            this.cursor = 0;
        } else if (Key.touchmainmenurace.Isin() && Key.touchmainmenu.IsClick()) {
            this.cursor = 1;
        } else if (Key.touchmainmenuoption.Isin() && Key.touchmainmenu.IsClick()) {
            this.cursor = 2;
        } else if (Key.touchmainmenuend.Isin() && Key.touchmainmenu.IsClick()) {
            this.cursor = 3;
        }

        if (Key.touchmainmenureturn.Isin() && Key.touchmainmenu.IsClick()) {
            this.returnCursor = 1;
        }

        if (!Key.touchmainmenustart.IsButtonPress() || this.cursor != 0 || !fadeChangeOver() && !this.isFromStartGame) {
            if (!Key.touchmainmenurace.IsButtonPress() || this.cursor != 1 || !fadeChangeOver() && !this.isFromStartGame) {
                if (Key.touchmainmenuoption.IsButtonPress() && this.cursor == 2 && (fadeChangeOver() || this.isFromStartGame)) {
                    this.optionInit();
                    this.changeStateWithFade(9);
                    SoundSystem.getInstance().playSe(1);
                    if (this.isFromStartGame) {
                        this.isFromStartGame = false;
                    }
                } else if (Key.touchmainmenuend.IsButtonPress() && this.cursor == 3 && (fadeChangeOver() || this.isFromStartGame)) {
                    state = 12;
                    this.secondEnsureInit();
                    fadeInit(0, 220);
                    SoundSystem.getInstance().playSe(1);
                    if (this.isFromStartGame) {
                        this.isFromStartGame = false;
                    }

                    this.quitFlag = 0;
                }
            } else {
                PlayerObject.stageModeState = 1;
                this.changeStateWithFade(24);
                this.initTimeStageRes();
                this.cursor = 0;
                SoundSystem.getInstance().playSe(1);
                if (this.isFromStartGame) {
                    this.isFromStartGame = false;
                }

                this.isRaceModeItemsSelected = false;
            }
        } else {
            PlayerObject.stageModeState = 0;
            if (!this.BP_gotoPaying()) {
                this.gotoStageSelect();
            }

            SoundSystem.getInstance().playSe(1);
            if (this.isFromStartGame) {
                this.isFromStartGame = false;
            }
        }

        if ((Key.buttonPress(524288 | 8388608) || Key.touchmainmenureturn.IsButtonPress() && this.returnCursor == 1) && (fadeChangeOver() || this.isFromStartGame)) {
            state = 1;
            this.initTitleRes2();
            SoundSystem.getInstance().playSe(2);
            if (this.isFromStartGame) {
                this.isFromStartGame = false;
            }

            Key.touchanykeyInit();
            fading = false;
        }

    }

    private void menuDraw(MFGraphics var1) {
        this.menuMoving = false;
        if (this.currentElement != null) {
            int var2 = SCREEN_HEIGHT;
            var2 = this.currentElement.length;
            var2 = MENU_INTERVAL;

            /*for(var2 = 0; var2 < this.currentElement.length; ++var2) {
            }*/
        }

    }

    private void menuOptionDiffLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                GlobalResource.difficultyConfig = 1;
                fadeInit(220, 0);
                state = 9;
                break;
            case 2:
                GlobalResource.difficultyConfig = 0;
                fadeInit(220, 0);
                state = 9;
                break;
            case 3:
                fadeInit(220, 0);
                state = 9;
        }

    }

    private int menuOptionLanguageCheck() {
        int var1;
        for(var1 = 0; var1 < 5; ++var1) {
            if (Key.touchmenuoptionlanguageitems[var1].Isin() && Key.touchmenuoptionlanguage.IsClick()) {
                this.itemsselectcursor = var1;
            }
        }

        if (Key.touchmenuoptionlanguagereturn.Isin() && Key.touchmenuoptionlanguage.IsClick()) {
            this.itemsselectcursor = -2;
        }

        if (this.isItemsSelect) {
            ++this.itemsselectframe;
            if (this.itemsselectframe > 8) {
                fadeInit(220, 102);
                var1 = this.finalitemsselectcursor + 2;
                return var1;
            }
        }

        var1 = 0;

        while(true) {
            if (var1 >= 5) {
                if ((Key.press(524288 | 8388608) || Key.touchmenuoptionlanguagereturn.IsButtonPress() && this.itemsselectcursor == -2) && fadeChangeOver()) {
                    SoundSystem.getInstance().playSe(2);
                    var1 = 1;
                } else {
                    var1 = 0;
                }
                break;
            }

            if (this.itemsselectcursor == var1 && Key.touchmenuoptionlanguageitems[var1].IsClick()) {
                this.isItemsSelect = true;
                this.itemsselectframe = 0;
                this.finalitemsselectcursor = var1;
                var1 = 0;
                break;
            }

            ++var1;
        }

        return var1;
    }

    private void menuOptionLanguageDraw(MFGraphics var1) {
        if (muiAniDrawer == null) {
            StringBuilder var5 = new StringBuilder("/lang");
            var5.append(GlobalResource.languageConfig);
            var5.append("/mui");
            muiAniDrawer = (new Animation(var5.toString())).getDrawer(0, false, 0);
        } else {
            AnimationDrawer var4;
            for(int var2 = 0; var2 < 5; ++var2) {
                var4 = muiAniDrawer;
                byte var3;
                if (Key.touchmenuoptionlanguageitems[var2].Isin() && this.itemsselectcursor == var2) {
                    var3 = 1;
                } else {
                    var3 = 0;
                }

                var4.setActionId(var3 + 55);
                muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 48 + var2 * 24);
                muiAniDrawer.setActionId(var2 + 39);
                muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 48 + var2 * 24);
            }

            var4 = muiAniDrawer;
            byte var6;
            if (Key.touchmenuoptionlanguagereturn.Isin()) {
                var6 = 5;
            } else {
                var6 = 0;
            }

            var4.setActionId(var6 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

    }

    private void menuOptionLanguageInit() {
        this.itemsselectcursor = 0;
        this.isItemsSelect = false;
        fadeInit(102, 220);
        Key.touchMenuOptionLanguageInit();
    }

    private void menuOptionLanguageLogic() {
        int var1 = this.menuOptionLanguageCheck();
        if (var1 == 1) {
            fadeInit(220, 102);
            state = 9;
        } else if (var1 >= 2) {
            GlobalResource.languageConfig = var1 - 2;
            fadeInit(220, 102);
            state = 9;
        }

    }

    private void menuOptionLoadingTipsLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                GlobalResource.loadingTipsConfig = 0;
                fadeInit(220, 0);
                state = 9;
                break;
            case 2:
                GlobalResource.loadingTipsConfig = 1;
                fadeInit(220, 0);
                state = 9;
                break;
            case 3:
                fadeInit(220, 0);
                state = 9;
        }

    }

    private void menuOptionResetRecordEnsureLogic() {
        switch (this.secondEnsureLogic()) {
            case 1:
                StageManager.resetGameRecord();
                SoundSystem.getInstance().setSoundState(GlobalResource.soundConfig);
                SoundSystem.getInstance().setSeState(GlobalResource.seConfig);
                this.resetInfoCount = this.RESET_INFO_COUNT;
                state = 9;
                fadeInit(220, 0);
                break;
            case 2:
                state = 36;
                this.secondEnsureInit();
                fadeInit(220, 220);
        }

    }

    private void menuOptionResetRecordLogic() {
        switch (this.secondEnsureDirectLogic()) {
            case 1:
                state = 37;
                this.secondEnsureInit();
                break;
            case 2:
                fadeInit(220, 0);
                state = 9;
        }

    }

    private void menuOptionSoundLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                this.isOptionDisFlag = false;
                Standard2.soundtrack = 0;
                fadeInit(102, 0);
                this.state = 9;
                this.returnCursor = 0;
                break;
            case 2:
                this.isOptionDisFlag = false;
                Standard2.soundtrack = 1;
                fadeInit(102, 0);
                this.state = 9;
                this.returnCursor = 0;
                break;
            case 3:
                fadeInit(102, 0);
                this.state = 9;
                this.returnCursor = 0;
        }

    }

    private void menuOptionSpSetLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                GlobalResource.spsetConfig = 0;
                GlobalResource.sensorConfig = 3;
                fadeInit(220, 0);
                state = 9;
                break;
            case 2:
                state = 40;
                this.itemsSelect4Init();
                break;
            case 3:
                fadeInit(220, 0);
                state = 9;
        }

    }

    private void menuOptionTimeLimitLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                GlobalResource.timeLimit = 0;
                fadeInit(220, 0);
                state = 9;
                break;
            case 2:
                GlobalResource.timeLimit = 1;
                fadeInit(220, 0);
                state = 9;
                break;
            case 3:
                fadeInit(220, 0);
                state = 9;
        }

    }

    private void menuOptionVibLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                GlobalResource.vibrationConfig = 1;
                fadeInit(220, 0);
                state = 9;
                MyAPI.vibrate();
                break;
            case 2:
                GlobalResource.vibrationConfig = 0;
                fadeInit(220, 0);
                state = 9;
                break;
            case 3:
                fadeInit(220, 0);
                state = 9;
        }

    }

    private void openingClose() {
        Animation.closeAnimationArray(this.openingAnimation);
        this.openingAnimation = null;
        Animation.closeAnimationDrawerArray(this.openingDrawer);
        this.openingDrawer = null;
        Animation.closeAnimationDrawer(this.skipDrawer);
        this.skipDrawer = null;
        System.gc();

        try {
            Thread.sleep(100L);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    private void openingDraw(MFGraphics var1) {
        switch (this.openingState) {
            case 0:
            case 1:
                this.openingDrawer[0].draw(var1, this.openingOffsetX, this.openingOffsetY);
                break;
            case 2:
                this.openingDrawer[1].draw(var1, this.openingOffsetX, this.openingOffsetY);
                break;
            case 3:
                this.openingDrawer[2].draw(var1, this.openingOffsetX, this.openingOffsetY);
                break;
            case 4:
                this.openingDrawer[3].draw(var1, this.openingOffsetX, this.openingOffsetY);
                break;
            case 5:
                this.openingDrawer[4].draw(var1, this.openingOffsetX, this.openingOffsetY);
                if (!this.openingEnding && this.openingDrawer[4].checkEnd()) {
                    this.openingEnding = true;
                    setFadeColor(16777215);
                    fadeInit(0, 255);
                }

                if (this.openingEnding) {
                    drawFadeBase(var1, 2);
                }
        }

        if (this.openingStateChanging) {
            drawFadeBase(var1, 2);
        }

        if (!this.openingDrawer[4].checkEnd()) {
            AnimationDrawer var3 = this.skipDrawer;
            byte var2;
            if (Key.touchopeningskip.Isin() && this.opengingCursor == 0 || Key.repeat(Key.B_S1 | 16777216 | 8388608)) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 0);
            this.skipDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

    }

    private void openingInit() {
        fading = false;
        this.close();
        this.openingFrame = 0;
        this.openingState = 0;
        int var1;
        if (this.openingAnimation == null) {
            StringBuilder var2;
            if (GlobalResource.languageConfig != 6 && GlobalResource.languageConfig != 8) {
                var2 = new StringBuilder("/animation/opening/opening.dat");
            } else {
                var2 = new StringBuilder("/lang");
                var2.append(GlobalResource.languageConfig);
                var2.append("/opening/opening.dat");
            }
            this.openingAnimation = Animation.getInstanceFromQi(var2.toString());
            this.openingDrawer = new AnimationDrawer[this.openingAnimation.length];

            for(var1 = 0; var1 < this.openingDrawer.length; ++var1) {
                this.openingDrawer[var1] = this.openingAnimation[var1].getDrawer(0, false, 0);
                this.openingDrawer[var1].mustKeepFrameTime(MFLib.MainState.OpeningFPS());
            }
        } else {
            for(var1 = 0; var1 < this.openingDrawer.length; ++var1) {
                this.openingDrawer[var1].setActionId(0);
                this.openingDrawer[var1].restart();
            }
        }

        if (this.skipDrawer == null) {
            this.skipDrawer = (new Animation("/animation/skip")).getDrawer(0, false, 0);
        }

        this.openingStateChanging = false;
    }

    private boolean openingLogic() {
        ++this.openingFrame;
        boolean var1;
        if (Key.touchopeningskip.Isin() && Key.touchopening.IsClick()) {
            this.opengingCursor = 0;
        }

        if ((Key.touchopeningskip.IsButtonPress() || Key.buttonPress(Key.B_S1 | 16777216 | 8388608)) && !this.openingDrawer[4].checkEnd()) {
            SoundSystem.getInstance().playSe(1);
            SoundSystem.getInstance().stopBgm(false);
            SoundSystem.getInstance().playBgm(1, false);
            var1 = true;
            return var1;
        }

        if (this.openingCount != 0) {
            --this.openingCount;
        }

        switch (this.openingState) {
            case 0:
                if (this.openingDrawer[0].checkEnd()) {
                    this.openingDrawer[0].setActionId(1);
                    this.openingState = 1;
                    SoundSystem.getInstance().playSequenceSe(80);
                }
                break;
            case 1:
                if (this.openingDrawer[0].checkEnd()) {
                    this.openingDrawer[1].setActionId(0);
                    this.openingState = 2;
                }
                break;
            case 2:
                if (this.openingDrawer[1].checkEnd()) {
                    this.openingDrawer[2].setActionId(0);
                    if (this.openingStateChanging) {
                        if (fadeChangeOver()) {
                            this.openingState = 3;
                            this.openingStateChanging = false;
                        }
                    } else {
                        this.openingStateChanging = true;
                        setFadeColor(16777215);
                        fadeInit(0, 255);
                    }
                }
                break;
            case 3:
                if (this.openingDrawer[2].checkEnd()) {
                    this.openingDrawer[3].setActionId(0);
                    if (this.openingStateChanging) {
                        if (fadeChangeOver()) {
                            this.openingState = 4;
                            this.openingStateChanging = false;
                        }
                    } else {
                        this.openingStateChanging = true;
                        setFadeColor(16777215);
                        fadeInit(0, 255);
                    }
                }
                break;
            case 4:
                if (this.openingDrawer[3].checkEnd()) {
                    this.openingDrawer[4].setActionId(0);
                    this.openingDrawer[4].restart();
                    this.openingEnding = false;
                    if (this.openingStateChanging) {
                        if (fadeChangeOver()) {
                            this.openingState = 5;
                            this.openingStateChanging = false;
                        }
                    } else {
                        this.openingStateChanging = true;
                        setFadeColor(16777215);
                        fadeInit(0, 255);
                    }
                }
                break;
            case 5:
                if (this.openingDrawer[4].checkEnd() && this.openingEnding && fadeChangeOver()) {
                    this.openingState = 6;
                }
                break;
            case 6:
                this.openingFrame = 0;
                var1 = true;
                return var1;
        }

        var1 = false;
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

        if (state != 9) {
            this.menuOptionCursor = -2;
        }

        if (numberDrawer == null) {
            numberDrawer = (new Animation("/animation/number")).getDrawer(0, false, 0);
        }

        if (langAniDrawer == null) {
            langAniDrawer = new Animation("/animation/language").getDrawer();
        }

        muiLeftArrowDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 4 + this.optionArrowDriveY);
        muiRightArrowDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 4 + this.optionArrowDriveY);

        AnimationDrawer var4 = muiAniDrawer;
        byte var5;
        if (this.optionIndex == 0) {
            muiAniDrawer.setActionId(73);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1), this.optionDrawOffsetY + 40 + this.optionslide_y + this.optionArrowDriveY);

            // DIFFICULTY LEVEL
            muiAniDrawer.setActionId(19);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);
            if (!ChargePlatform.isChargedByIndex(0)) {
                var2 = 67;
            } else {
                if (Key.touchmenuoptionitems[3].Isin() && this.menuOptionCursor == 1 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 55;
            }
            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (GlobalResource.difficultyConfig == 0) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            var4.setActionId(var5 + 33);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);

            // TIME LIMIT
            muiAniDrawer.setActionId(22);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 48 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (!ChargePlatform.isChargedByIndex(0)) {
                var2 = 67;
            } else {
                if (Key.touchmenuoptionitems[5].Isin() && this.menuOptionCursor == 2 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 55;
            }
            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48 + this.optionArrowDriveY);
            muiAniDrawer.setActionId(GlobalResource.timeLimit + 35);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48 + this.optionArrowDriveY);

            // LANGUAGE
            muiAniDrawer.setActionId(25);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 72 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (Key.touchmenuoptionitems[7].Isin() && this.menuOptionCursor == 3 && this.isSelectable) {
                var5 = 1;
            } else {
                var5 = 0;
            }

            var2 = var5 + 57;
            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72 + this.optionArrowDriveY);
            langAniDrawer.setActionId(GlobalResource.languageConfig);
            langAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72 + this.optionArrowDriveY);
        } else if (this.optionIndex == 1) {
            muiAniDrawer.setActionId(74);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1), this.optionDrawOffsetY + 40 + this.optionslide_y + this.optionArrowDriveY);

            // SOUNDTRACK
            muiAniDrawer.setActionId(20);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);
            if (GlobalResource.soundSwitchConfig == 0) {
                var2 = 67;
            } else {
                if (Key.touchmenuoptionitems[3].Isin() && this.menuOptionCursor == 1 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }
                var2 = var5 + 55;
            }
            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);
            muiAniDrawer.setActionId(Standard2.soundtrack + 101);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);

            // VIBRATION
            muiAniDrawer.setActionId(21);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 48 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (Key.touchmenuoptionitems[5].Isin() && this.menuOptionCursor == 2 && this.isSelectable) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            var2 = var5 + 57;
            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48 + this.optionArrowDriveY);
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
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48 + this.optionArrowDriveY);
            var4 = muiAniDrawer;

            // TIPS SCREEN (LOADING)
            muiAniDrawer.setActionId(23);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 72 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (!ChargePlatform.isChargedByIndex(0)) {
                var2 = 67;
            } else {
                if (Key.touchmenuoptionitems[7].Isin() && this.menuOptionCursor == 3 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 55;
            }
            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (GlobalResource.loadingTipsConfig == 0) {
                var5 = 0;
            } else {
                var5 = 1;
            }
            var4.setActionId(var5 + 35);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72 + this.optionArrowDriveY);
        } else if (this.optionIndex == 2) {
            muiAniDrawer.setActionId(75);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1), this.optionDrawOffsetY + 40 + this.optionslide_y + this.optionArrowDriveY);

            // POSITION
            muiAniDrawer.setActionId(96);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (SCREEN_WIDTH < 260) {
                var2 = 77;
            } else {
                if (Key.touchmenuoptionitems[3].Isin() && this.menuOptionCursor == 1 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 57;
            }

            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardLeftPosition, (SCREEN_WIDTH >> 1) + 35, this.optionDrawOffsetY + 40 + this.optionslide_y + 18 + this.optionArrowDriveY, 2);
            muiAniDrawer.setActionId(79);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 55, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardRightPosition, (SCREEN_WIDTH >> 1) + 75, this.optionDrawOffsetY + 40 + this.optionslide_y + 18 + this.optionArrowDriveY, 2);

            // SIZE
            muiAniDrawer.setActionId(97);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 48 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (Key.touchmenuoptionitems[5].Isin() && this.menuOptionCursor == 2 && this.isSelectable) {
                var5 = 1;
            } else {
                var5 = 0;
            }

            var2 = var5 + 57;
            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48 + this.optionArrowDriveY);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardSize, (SCREEN_WIDTH >> 1) + 46, this.optionDrawOffsetY + 40 + this.optionslide_y + 42 + this.optionArrowDriveY, 2);
            muiAniDrawer.setActionId(95);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 51, this.optionDrawOffsetY + 40 + this.optionslide_y + 41 + this.optionArrowDriveY);
            NumberDrawer.drawNum(var1, 0, 5, (SCREEN_WIDTH >> 1) + 64, this.optionDrawOffsetY + 40 + this.optionslide_y + 42 + this.optionArrowDriveY, 2);

            // OPACITY
            muiAniDrawer.setActionId(98);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 72 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (Key.touchmenuoptionitems[7].Isin() && this.menuOptionCursor == 3 && this.isSelectable) {
                var5 = 1;
            } else {
                var5 = 0;
            }

            var2 = var5 + 57;
            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72 + this.optionArrowDriveY);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardOpacity, (SCREEN_WIDTH >> 1) + 46, this.optionDrawOffsetY + 40 + this.optionslide_y + 66 + this.optionArrowDriveY, 2);
            muiAniDrawer.setActionId(95);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 51, this.optionDrawOffsetY + 40 + this.optionslide_y + 65 + this.optionArrowDriveY);
            NumberDrawer.drawNum(var1, 0, 3, (SCREEN_WIDTH >> 1) + 64, this.optionDrawOffsetY + 40 + this.optionslide_y + 66 + this.optionArrowDriveY, 2);
        } else if (this.optionIndex == 3) {
            muiAniDrawer.setActionId(76);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1), this.optionDrawOffsetY + 40 + this.optionslide_y + this.optionArrowDriveY);

            // CONTROLS
            muiAniDrawer.setActionId(99);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);
            if (!ChargePlatform.isChargedByIndex(0)) {
                var2 = 67;
            } else {
                if (Key.touchmenuoptionitems[3].Isin() && this.menuOptionCursor == 1 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 55;
            }

            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);
            muiAniDrawer.setActionId(GlobalResource.spsetConfig + 37);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24 + this.optionArrowDriveY);

            // GYROSCOPE LEVEL
            muiAniDrawer.setActionId(24);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 48 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (!ChargePlatform.isChargedByIndex(0) || GlobalResource.spsetConfig == 0) {
                var2 = 77;
            } else {
                if (Key.touchmenuoptionitems[5].Isin() && this.menuOptionCursor == 2 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 57;
            }

            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48 + this.optionArrowDriveY);
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

            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 48 + this.optionArrowDriveY);

            // FIXED SCREEN
            muiAniDrawer.setActionId(100);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 72 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (!ChargePlatform.isChargedByIndex(0) || SCREEN_WIDTH <= 304) {
                var2 = 67;
            } else {
                if (Key.touchmenuoptionitems[7].Isin() && this.menuOptionCursor == 3 && this.isSelectable) {
                    var5 = 1;
                } else {
                    var5 = 0;
                }

                var2 = var5 + 55;
            }

            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72 + this.optionArrowDriveY);
            var4 = muiAniDrawer;
            if (GlobalResource.fixedScreenConfig == 0) {
                var5 = 0;
            } else {
                var5 = 1;
            }

            var4.setActionId(var5 + 35);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 72 + this.optionArrowDriveY);
        }

        // HELP
        if (Key.touchmenuoptionitems[8].Isin() && this.menuOptionCursor == 4 && this.isSelectable) {
            var5 = 1;
        } else {
            var5 = 0;
        }
        var4.setActionId(var5 + 27);
        muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 96 + this.optionArrowDriveY);

        // CREDITS
        var4 = muiAniDrawer;
        if (Key.touchmenuoptionitems[10].Isin() && this.menuOptionCursor == 5 && this.isSelectable) {
            var5 = 1;
        } else {
            var5 = 0;
        }
        var4.setActionId(var5 + 29);
        muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 120 + this.optionArrowDriveY);

        // DELETE DATA
        var4 = muiAniDrawer;
        if (!ChargePlatform.isChargedByIndex(0)) {
            var2 = 102;
        } else {
            if (Key.touchmenuoptionitems[12].Isin() && this.menuOptionCursor == 6 && this.isSelectable) {
                var5 = 1;
            } else {
                var5 = 0;
            }
            var2 = var5 + 31;
        }
        var4.setActionId(var2);
        muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 144 + this.optionArrowDriveY);

        if (this.optionUpArrowAvailable) {
            this.optionArrowUpDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 115, (SCREEN_HEIGHT >> 1) - 19);
        }

        if (this.optionDownArrowAvailable) {
            this.optionArrowDownDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 115, (SCREEN_HEIGHT >> 1) + 25);
        }

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
        int var2 = -1;
        boolean z = true;
        this.optionMenuCursor = 0;
        this.optionCursor[0] = GlobalResource.difficultyConfig;
        this.optionCursor[1] = GlobalResource.languageConfig;
        this.optionCursor[2] = GlobalResource.seConfig;
        this.optionCursor[3] = GlobalResource.timeLimit;
        this.resetInfoCount = 0;
        warningY = WARNING_Y_DES_2;
        this.offsetOfVolumeInterface = 0;
        this.optionOffsetX = 0;
        StringBuilder var3;
        if (muiAniDrawer == null || muiLeftArrowDrawer == null || muiRightArrowDrawer == null) {
            var3 = new StringBuilder("/lang");
            int var1 = GlobalResource.languageConfig;
            var3.append(var1);
            var3.append("/mui");
            muiAniDrawer = (new Animation(var3.toString())).getDrawer(0, false, 0);
            muiLeftArrowDrawer = (new Animation(var3.toString())).getDrawer(91, true, 0);
            muiRightArrowDrawer = (new Animation(var3.toString())).getDrawer(92, true, 0);
        }

        if (this.optionArrowUpDrawer == null) {
            var3 = new StringBuilder("/lang");
            var3.append(GlobalResource.languageConfig);
            var3.append("/mui");
            this.optionArrowUpDrawer = (new Animation(var3.toString())).getDrawer(64, z, 0);
            var3 = new StringBuilder("/lang");
            var2 = GlobalResource.languageConfig;
            var3.append(var2);
            var3.append("/mui");
            this.optionArrowDownDrawer = (new Animation(var3.toString())).getDrawer(65, z, 0);
        }

        Key.touchMenuOptionInit();
        this.optionIndex = 0;
        this.menuOptionCursor = 0;
        this.optionOffsetYAim = 0;
        this.optionOffsetY = 0;
        this.isChanged = false;
        this.isOptionDisFlag = false;
        this.optionslide_getprey = var2;
        this.optionslide_gety = var2;
        this.optionslide_y = 0;
        this.optionDrawOffsetBottomY = -48;
        this.optionYDirect = 0;
    }

    private void optionLogic() {
        State.resetTouchPosition();
        if (!this.isOptionDisFlag) {
            SoundSystem.getInstance().playBgm(5);
            this.isOptionDisFlag = true;
        }

        this.optionslide_gety = Key.slidesensormenuoption.getPointerY();
        if (this.optionslide_gety == -1 && this.optionslide_getprey == -1) {
            this.optionslide_y = 0;
            this.optionslidefirsty = 0;
        } else if (this.optionslide_gety != -1 && this.optionslide_getprey == -1) {
            this.optionslidefirsty = this.optionslide_gety;
        } else if (this.optionslide_gety != -1 && this.optionslide_getprey != -1) {
            this.optionslide_y = this.optionslide_gety - this.optionslidefirsty;
        } else if (this.optionslide_gety == -1 && this.optionslide_getprey != -1) {
            this.optionDrawOffsetTmpY1 = this.optionslide_y + this.optionDrawOffsetY;
        }

        int var1;
        int var2;
        int var3 = this.optionDrawOffsetY;
        TouchKeyRange var4;
        var2 = this.optionslide_y;
        for(var1 = 0; var1 < Key.touchmenuoptionitems.length >> 1; ++var1) {
            var4 = Key.touchmenuoptionitems[var1 * 2];
            var2 = this.optionslide_y;
            var4.setStartY(var1 * 24 + 28 + var3 + var2);
            var4 = Key.touchmenuoptionitems[var1 * 2 + 1];
            var3 = this.optionDrawOffsetY;
            var2 = this.optionslide_y;
            var4.setStartY(var1 * 24 + 28 + var3 + var2);
        }

        var4 = Key.touchmenuoptionleftarrow;
        var4.setStartY(var3 + 30 + var2);
        var4 = Key.touchmenuoptionrightarrow;
        var4.setStartY(var3 + 30 + var2);

        if (this.isSelectable) {
            for(var1 = 0; var1 < Key.touchmenuoptionitems.length; ++var1) {
                if (Key.touchmenuoptionitems[var1].Isin() && Key.touchmenuoption.IsClick()) {
                    this.menuOptionCursor = var1 / 2;
                    this.returnCursor = 0;
                    break;
                }
            }
        }

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
            this.optionIndex = 3;
        } else if (this.optionIndex > 3) {
            this.optionIndex = 0;
        }

        if (Key.touchmenuoptionreturn.Isin() && Key.touchmenuoption.IsClick()) {
            this.returnCursor = 1;
        }

        if ((Key.buttonPress(524288 | 8388608) || Key.touchmenuoptionreturn.IsButtonPress() && this.returnCursor == 1) && fadeChangeOver()) {
            this.changeStateWithFade(2);
            this.isTitleBGMPlay = false;
            Key.touchMainMenuInit2();
            SoundSystem.getInstance().stopBgm(false);
            SoundSystem.getInstance().playSe(2);
            GlobalResource.saveSystemConfig();
            this.returnCursor = 0;
            this.menuInit(this.multiMainNoMoreGameItems);
            if (!ChargePlatform.isChargedByIndex(0) && ChargePlatform.getEnterTimesByIndex(0) < 3) {
                this.mainMenuItemCursor = 1;
            }
        }

        if (Key.press(Integer.MIN_VALUE)) {
            this.changeStateWithFade(34);
            this.helpInit();
            SoundSystem.getInstance().playSe(1);
        } else if (Key.press(1073741824)) {
            this.changeStateWithFade(35);
            this.creditInit();
            SoundSystem.getInstance().playSe(1);
        }

        if (this.optionDrawOffsetY + this.optionslide_y < 0) {
            this.optionUpArrowAvailable = true;
        } else {
            this.optionUpArrowAvailable = false;
        }

        if (this.optionDrawOffsetY + this.optionslide_y > this.optionDrawOffsetBottomY) {
            this.optionDownArrowAvailable = true;
        } else {
            this.optionDownArrowAvailable = false;
        }

        if ((Key.repeat(4) || Key.touchmenuoptionuparrow.Isin()) && this.optionUpArrowAvailable) {
            this.optionArrowDriveOffsetY = 12;
            this.optionArrowMoveable = true;
        }

        if ((Key.repeat(8) || Key.touchmenuoptiondownarrow.Isin()) && this.optionDownArrowAvailable) {
            this.optionArrowDriveOffsetY = -12;
            this.optionArrowMoveable = true;
        }

        if (this.optionArrowMoveable) {
            this.optionArrowDriveOffsetY /= 2;
            if (this.optionArrowDriveOffsetY > 0 && this.optionArrowDriveOffsetY < 2) {
                this.optionArrowDriveOffsetY = 2;
            }

            if (this.optionArrowDriveOffsetY < 0 && this.optionArrowDriveOffsetY > -2) {
                this.optionArrowDriveOffsetY = -2;
            }

            this.optionArrowDriveY += this.optionArrowDriveOffsetY;
            if (this.optionArrowDriveOffsetY > 0) {
                if (this.optionArrowDriveY >= 24) {
                    this.optionArrowDriveY = 24;
                    this.optionDrawOffsetY += this.optionArrowDriveY;
                    this.optionArrowDriveY = 0;
                    this.optionArrowMoveable = false;
                }
            } else if (this.optionArrowDriveOffsetY < 0 && this.optionArrowDriveY <= -24) {
                this.optionArrowDriveY = -24;
                this.optionDrawOffsetY += this.optionArrowDriveY;
                this.optionArrowDriveY = 0;
                this.optionArrowMoveable = false;
            }
        }

        if (Key.slidesensormenuoption.isSliding()) {
            if (this.optionslide_y <= 4 && this.optionslide_y >= -4) {
                this.isSelectable = true;
            } else {
                this.isOptionChange = true;
                this.isSelectable = false;
                this.releaseOptionItemsTouchKey();
                this.optionReturnFlag = false;
            }

            if (Key.slidesensormenuoption.isSlide(Key.DIR_UP)) {
                this.isOptionChange = true;
                this.isSelectable = false;
            } else if (Key.slidesensormenuoption.isSlide(Key.DIR_DOWN)) {
                this.isOptionChange = true;
                this.isSelectable = false;
            }
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

        if (this.optionIndex == 0) {
            if (Key.touchmenuoptionitems[3].IsButtonPress() && this.menuOptionCursor == 1 && fadeChangeOver()) {
                if (ChargePlatform.isChargedByIndex(0)) {
                    SoundSystem.getInstance().playSe(1);
                    if (GlobalResource.difficultyConfig == 0){
                        GlobalResource.difficultyConfig = 1;
                    } else {
                        GlobalResource.difficultyConfig = 0;
                    }
                } else {
                    SoundSystem.getInstance().playSe(2);
                }
            } else if (Key.touchmenuoptionitems[5].IsButtonPress() && this.menuOptionCursor == 2 && fadeChangeOver()) {
                if (ChargePlatform.isChargedByIndex(0)) {
                    SoundSystem.getInstance().playSe(1);
                    if (GlobalResource.timeLimit == 0) {
                        GlobalResource.timeLimit = 1;
                    } else {
                        GlobalResource.timeLimit = 0;
                    }
                } else {
                    SoundSystem.getInstance().playSe(2);
                }
            } else if (Key.touchmenuoptionitems[7].IsButtonPress() && this.menuOptionCursor == 3 && fadeChangeOver()) {
                state = 39;
                this.newLanguageInit();
                SoundSystem.getInstance().playSe(1);
            }
        } else if (this.optionIndex == 1) {
            if (Key.touchmenuoptionitems[3].IsButtonPress() && this.menuOptionCursor == 1 && fadeChangeOver()) {
                SoundSystem.getInstance().playSe(1);
                this.isOptionDisFlag = false;
                if (Standard2.soundtrack == 1) {
                    Standard2.soundtrack = 0;
                } else {
                    Standard2.soundtrack = 1;
                }
            } else if (Key.touchmenuoptionitems[5].IsButtonPress() && this.menuOptionCursor == 2 && fadeChangeOver()) {
                state = 29;
                this.itemsSelect4Init();
                SoundSystem.getInstance().playSe(1);
            } else if (Key.touchmenuoptionitems[7].IsButtonPress() && this.menuOptionCursor == 3 && fadeChangeOver()) {
                SoundSystem.getInstance().playSe(1);
                if (GlobalResource.loadingTipsConfig == 0) {
                    GlobalResource.loadingTipsConfig = 1;
                } else {
                    GlobalResource.loadingTipsConfig = 0;
                }
            }
        } else if (this.optionIndex == 2) {
            if (Key.touchmenuoptionitems[3].IsButtonPress() && this.menuOptionCursor == 1 && fadeChangeOver()) {
                if (SCREEN_WIDTH >= 260) {
                    state = 27;
                    this.touchPadInit();
                    SoundSystem.getInstance().playSe(1);
                } else {
                    SoundSystem.getInstance().playSe(2);
                }
            } else if (Key.touchmenuoptionitems[5].IsButtonPress() && this.menuOptionCursor == 2 && fadeChangeOver()) {
                state = 28;
                this.touchPadInit();
                SoundSystem.getInstance().playSe(1);
            } else if (Key.touchmenuoptionitems[7].IsButtonPress() && this.menuOptionCursor == 3 && fadeChangeOver()) {
                state = 30;
                this.touchPadInit();
                SoundSystem.getInstance().playSe(1);
            }
        } else if (this.optionIndex == 3) {
            if (Key.touchmenuoptionitems[3].IsButtonPress() && this.menuOptionCursor == 1 && fadeChangeOver()) {
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
            } else if (Key.touchmenuoptionitems[5].IsButtonPress() && this.menuOptionCursor == 2 && fadeChangeOver()) {
                if (ChargePlatform.isChargedByIndex(0) && GlobalResource.spsetConfig != 0) {
                    if (ChargePlatform.isChargedByIndex(0)) {
                        state = 40;
                        this.itemsSelect3Init();
                        SoundSystem.getInstance().playSe(1);
                    } else {
                        SoundSystem.getInstance().playSe(2);
                    }
                } else {
                    SoundSystem.getInstance().playSe(2);
                }
            } else if (Key.touchmenuoptionitems[7].IsButtonPress() && this.menuOptionCursor == 3 && fadeChangeOver()) {
                if (ChargePlatform.isChargedByIndex(0) && SCREEN_WIDTH > 304) {
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

        if (Key.touchmenuoptionitems[8].IsButtonPress() && this.menuOptionCursor == 4 && fadeChangeOver()) {
            this.changeStateWithFade(34);
                this.helpInit();
            SoundSystem.getInstance().playSe(1);
        } else if (Key.touchmenuoptionitems[10].IsButtonPress() && this.menuOptionCursor == 5 && fadeChangeOver()) {
            this.changeStateWithFade(35);
            this.creditInit();
            SoundSystem.getInstance().playSe(1);
        } else if (Key.touchmenuoptionitems[12].IsButtonPress() && this.menuOptionCursor == 6 && fadeChangeOver()) {
            if (ChargePlatform.isChargedByIndex(0)) {
                state = 36;
                this.secondEnsureInit();
                fadeInit(102, 220);
                SoundSystem.getInstance().playSe(1);
            } else {
                SoundSystem.getInstance().playSe(2);
            }
        }

        this.optionslide_getprey = this.optionslide_gety;
    }

    private void proRaceModeLogic() {
        if (fadeChangeOver()) {
            if (Key.touchproracemodestart.Isin() && Key.touchproracemode.IsClick()) {
                this.cursor = 0;
            } else if (Key.touchproracemoderecord.Isin() && Key.touchproracemode.IsClick()) {
                this.cursor = 1;
            } else if (Key.touchproracemodereturn.Isin() && Key.touchproracemode.IsClick()) {
                this.cursor = 2;
            }

            if (Key.touchproracemodestart.IsButtonPress() && this.cursor == 0 && !this.isRaceModeItemsSelected) {
                this.isRaceModeItemsSelected = true;
                this.stage_sel_key = 1;
                this.changeStateWithFade(23);
                this.preCharaterSelectState = 24;
                characterslots = 1;
                this.initCharacterSelectRes();
                SoundSystem.getInstance().playSe(1);
            } else if (Key.touchproracemoderecord.IsButtonPress() && this.cursor == 1 && !this.isRaceModeItemsSelected) {
                this.isRaceModeItemsSelected = true;
                this.stage_sel_key = 0;
                this.changeStateWithFade(14);
                preStageSelectState = 24;
                this.initStageSelectRes();
                SoundSystem.getInstance().playSe(1);
            }

            if ((Key.press(524288 | 8388608) || Key.touchproracemodereturn.IsButtonPress() && this.cursor == 2) && fadeChangeOver()) {
                this.changeStateWithFade(2);
                this.isTitleBGMPlay = false;
                Key.touchMainMenuInit2();
                this.initTitleRes2();
                this.returnCursor = 0;
                SoundSystem.getInstance().playSe(2);
                SoundSystem.getInstance().stopBgm(false);
                this.menuInit(this.multiMainNoMoreGameItems);
            }
        } else {
            Key.touchproracemodestart.reset();
            Key.touchproracemoderecord.reset();
        }

    }

    private void quitLogic() {
        Key.touchMainMenuReset2();
        switch (this.secondEnsureLogic()) {
            case 1:
                SoundSystem.getInstance().stopBgm(true);
                Key.touchkeyboardClose();
                Key.touchsoftkeyInit();
                if (this.isLinkMoreGame) {
                    MFDevice.openUrl("https://sonicmobirev.github.io/sonicadvance/", false);
                } else {
                    this.close();
                    System.gc();
                    state = 13;
                }
                break;
            case 2:
                if (this.quitFlag == 1) {
                    state = 1;
                    Key.touchanykeyInit();
                } else if (this.quitFlag == 0) {
                    this.gotoMainmenu();
                    Key.clear();
                    setFadeOver();
                }

                this.isLinkMoreGame = false;
        }

    }

    private void rankingDraw(MFGraphics var1) {
        this.menuBgDraw(var1);

        for(int var2 = 0; var2 < SCREEN_WIDTH / 32 + 1; ++var2) {
            drawMenuFontById(var1, 111, var2 * 32, 0);
            drawMenuFontById(var1, 112, var2 * 32 - 1, SCREEN_HEIGHT);
        }

        this.drawMenuTitle(var1, 4, 0);
        StageManager.drawNormalHighScore(var1);
    }

    private void rankingInit() {
        StageManager.normalHighScoreInit();
    }

    private void rankingLogic() {
        switch (this.comfirmLogic()) {
            case 400:
                this.changeStateWithFade(2);
                this.menuInit(MAIN_MENU);
                this.mainMenuInit();
                StageManager.drawHighScoreEnd();
            default:
        }
    }

    private void releaseAllStageSelectItemsTouchKey() {
        for(int var1 = 0; var1 < Key.touchstageselectitem.length; ++var1) {
            Key.touchstageselectitem[var1].resetKeyState();
        }

    }

    private void releaseOptionItemsTouchKey() {
        for(int var1 = 0; var1 < Key.touchmenuoptionitems.length; ++var1) {
            Key.touchmenuoptionitems[var1].resetKeyState();
        }

    }

    private void segaMoreLogic() {
        Key.touchMainMenuReset2();
        switch (this.secondEnsureLogic()) {
            case 1:
                //exitGame();
                break;
            case 2:
                this.gotoMainmenu();
                Key.clear();
                setFadeOver();
        }

    }

    public static void setMainMenu() {
        int[] var0 = MAIN_MENU_MOREGAME;
        MAIN_MENU = var0;
        var0 = MAIN_MENU_FUNCTION_MOREGAME;
        MAIN_MENU_FUNCTION = var0;
    }

    private void stageSelectLogic() {
        int var1;
        if (this.stage_select_state == 0) {
            Key.touchstageselectreturn.resetKeyState();
            Key.touchCharacterSelectModeClose();
            Key.setKeyFunction(true);
            if (this.offsetY[0] == SCREEN_HEIGHT >> 1) {
                SoundSystem.getInstance().playBgm(3);
            }

            if (this.offsetY[0] - this.vY[0] > 0) {
                for(var1 = 0; var1 < this.STAGE_TOTAL_NUM; ++var1) {
                    int[] var4 = this.offsetY;
                    var4[var1] -= this.vY[var1];
                }
            } else {
                for(var1 = 0; var1 < this.STAGE_TOTAL_NUM; ++var1) {
                    this.offsetY[var1] = 0;
                }

                this.stage_select_state = 1;
            }

            this.isStageSelectChange = false;
            this.stageselectslide_getprey = -1;
            this.stageselectslide_gety = -1;
            this.stageselectslide_y = 0;
            this.stageDrawOffsetBottomY = -(StageManager.getMaxStageID() - 5) * 24;
            this.stageYDirect = 0;
            this.isSelectable = false;
            this.stageSelectReturnFlag = false;
            this.stage_select_press_state = 0;
            this.stage_select_arrow_state = 0;
        } else if (this.stage_select_state == 1) {
            if (this.stageStartIndex > 0) {
                this.stageSelectUpArrowAvailable = true;
                this.isDrawUpArrow = true;
            } else {
                this.stageSelectUpArrowAvailable = false;
                this.isDrawUpArrow = false;
            }

            if (this.stageStartIndex < StageManager.getMaxStageID() - 5) {
                this.stageSelectDownArrowAvailable = true;
                this.isDrawDownArrow = true;
            } else {
                this.stageSelectDownArrowAvailable = false;
                this.isDrawDownArrow = false;
            }

            if ((Key.touchstageselectuparrow.Isin() || Key.repeat(4)) && this.stageSelectUpArrowAvailable) {
                this.stageSelectArrowDriveOffsetY = 12;
                this.stageSelectArrowMoveable = true;
            }

            if ((Key.touchstageselectdownarrow.Isin() || Key.repeat(8)) && this.stageSelectDownArrowAvailable) {
                this.stageSelectArrowDriveOffsetY = -12;
                this.stageSelectArrowMoveable = true;
            }

            if (this.stageSelectArrowMoveable) {
                this.stageSelectArrowDriveOffsetY /= 2;
                if (this.stageSelectArrowDriveOffsetY > 0 && this.stageSelectArrowDriveOffsetY < 2) {
                    this.stageSelectArrowDriveOffsetY = 2;
                }

                if (this.stageSelectArrowDriveOffsetY < 0 && this.stageSelectArrowDriveOffsetY > -2) {
                    this.stageSelectArrowDriveOffsetY = -2;
                }

                this.stageSelectArrowDriveY += this.stageSelectArrowDriveOffsetY;
                if (this.stageSelectArrowDriveOffsetY > 0) {
                    if (this.stageSelectArrowDriveY >= 24) {
                        this.stageSelectArrowDriveY = 24;
                        this.stageDrawOffsetY += this.stageSelectArrowDriveY;
                        this.stageSelectArrowDriveY = 0;
                        this.stageSelectArrowMoveable = false;
                    }
                } else if (this.stageSelectArrowDriveOffsetY < 0 && this.stageSelectArrowDriveY <= -24) {
                    this.stageSelectArrowDriveY = -24;
                    this.stageDrawOffsetY += this.stageSelectArrowDriveY;
                    this.stageSelectArrowDriveY = 0;
                    this.stageSelectArrowMoveable = false;
                }
            }

            this.stageselectslide_gety = Key.slidesensorstagesel.getPointerY();
            if (this.stageselectslide_gety == -1 && this.stageselectslide_getprey == -1) {
                this.stageselectslide_y = 0;
                this.stageselectslidefirsty = 0;
            } else if (this.stageselectslide_gety != -1 && this.stageselectslide_getprey == -1) {
                this.stageselectslidefirsty = this.stageselectslide_gety;
                this.firstStageSelectSlidePointY = this.stageselectslidefirsty;
                this.stageSelectSlideFrame = 0;
            } else if (this.stageselectslide_gety != -1 && this.stageselectslide_getprey != -1) {
                this.stageselectslide_y = this.stageselectslide_gety - this.stageselectslidefirsty;
            } else if (this.stageselectslide_gety == -1 && this.stageselectslide_getprey != -1) {
                this.stageDrawOffsetTmpY1 = this.stageselectslide_y + this.stageDrawOffsetY;
            }

            int var2;
            if (!Key.slidesensorstagesel.isSliding()) {
                if (this.stageYDirect == 0) {
                    if (this.gestureSlideSpeed > 0) {
                        this.gestureSlideSpeed -= 3;
                        if (this.gestureSlideSpeed < 2) {
                            this.gestureSlideSpeed = 0;
                        }
                    }

                    if (this.gestureSlideSpeed < 0) {
                        this.gestureSlideSpeed += 3;
                        if (this.gestureSlideSpeed > -2) {
                            this.gestureSlideSpeed = 0;
                        }
                    }

                    this.stageDrawOffsetY += this.gestureSlideSpeed;
                    if (this.stageDrawOffsetY + this.gestureSlideSpeed > 0) {
                        this.stageDrawOffsetY = 0;
                        this.gestureSlideSpeed = 0;
                    } else if (this.stageDrawOffsetY + this.gestureSlideSpeed < this.stageDrawOffsetBottomY) {
                        this.stageDrawOffsetY = this.stageDrawOffsetBottomY;
                        this.gestureSlideSpeed = 0;
                    }
                }
            } else {
                this.currentStageSelectSlidePointY = this.stageselectslide_y;
                if (this.currentStageSelectSlidePointY - this.preStageSelectSlidePointY < 2 && this.currentStageSelectSlidePointY - this.preStageSelectSlidePointY > -2) {
                    this.stageSelectSlideFrame = 0;
                    this.firstStageSelectSlidePointY = this.currentStageSelectSlidePointY;
                } else {
                    ++this.stageSelectSlideFrame;
                }

                this.preStageSelectSlidePointY = this.currentStageSelectSlidePointY;
                if (this.stageSelectSlideFrame == 0) {
                    this.gestureSlideSpeed = 0;
                } else {
                    var2 = this.currentStageSelectSlidePointY;
                    var1 = this.firstStageSelectSlidePointY;
                    int var3 = this.stageSelectSlideFrame;
                    var1 = (var2 - var1) / var3;
                    this.gestureSlideSpeed = var1 * 2;
                }
            }

            for(var1 = 0; var1 < Key.touchstageselectitem.length; ++var1) {
                Key.touchstageselectitem[var1].setStartY(this.stageDrawStartY + (var1 + 1) * 24 + this.offsetY[var1] + this.stageDrawOffsetY + this.stageselectslide_y);
            }

            if (this.isSelectable && this.stageYDirect == 0) {
                for(var1 = 0; var1 < Key.touchstageselectitem.length; ++var1) {
                    if (Key.touchstageselectitem[var1].IsButtonPress() && this.optionMenuCursor == var1 && fadeChangeOver() && !Key.touchstageselect.Isin()) {
                        switch (this.stage_sel_key) {
                            case 0:
                                this.changeStateWithFade(25);
                                this.stage_characterRecord_ID = this.optionMenuCursor;
                                this.initRecordRes();
                                break;
                            case 1:
                                this.changeStateWithFade(26);
                                this.stage_characterRecord_ID = this.optionMenuCursor;
                                this.initIntergradeRecordRes();
                                break;
                            case 2:
                                StageManager.setStageID(this.optionMenuCursor);
                                StageManager.setStartStageID(this.optionMenuCursor);
                                setState(1);
                        }

                        SoundSystem.getInstance().playSe(1);
                        break;
                    }
                }
            }

            if (!Key.slidesensorstagesel.isSliding()) {
                if (this.isStageSelectChange && this.stageselectslide_y == 0) {
                    this.stageDrawOffsetY = this.stageDrawOffsetTmpY1;
                    this.isStageSelectChange = false;
                    this.stageYDirect = 0;
                }

                if (!this.isStageSelectChange) {
                    if (this.stageDrawOffsetY > 0) {
                        this.stageYDirect = 1;
                        var2 = -this.stageDrawOffsetY >> 1;
                        var1 = var2;
                        if (var2 > -2) {
                            var1 = -2;
                        }

                        if (this.stageDrawOffsetY + var1 <= 0) {
                            this.stageDrawOffsetY = 0;
                            this.stage_select_press_state = 0;
                            this.stageYDirect = 0;
                        } else {
                            this.stageDrawOffsetY += var1;
                        }
                    } else if (this.stageDrawOffsetY < this.stageDrawOffsetBottomY) {
                        this.stageYDirect = 2;
                        var2 = this.stageDrawOffsetBottomY - this.stageDrawOffsetY >> 1;
                        var1 = var2;
                        if (var2 < 2) {
                            var1 = 2;
                        }

                        if (this.stageDrawOffsetY + var1 >= this.stageDrawOffsetBottomY) {
                            this.stageDrawOffsetY = this.stageDrawOffsetBottomY;
                            this.stage_select_press_state = 0;
                            this.stageYDirect = 0;
                        } else {
                            this.stageDrawOffsetY += var1;
                        }
                    }
                }
            } else {
                this.stage_select_press_state = 1;
                if (this.stageselectslide_y <= 4 && this.stageselectslide_y >= -4) {
                    this.isSelectable = true;
                } else {
                    this.isStageSelectChange = true;
                    this.isSelectable = false;
                    this.releaseAllStageSelectItemsTouchKey();
                    this.optionMenuCursor = -1;
                    this.stageSelectReturnFlag = false;
                }

                if (Key.slidesensorstagesel.isSlide(Key.DIR_UP)) {
                    this.isStageSelectChange = true;
                    this.isSelectable = false;
                } else if (Key.slidesensorstagesel.isSlide(Key.DIR_DOWN)) {
                    this.isStageSelectChange = true;
                    this.isSelectable = false;
                }
            }

            this.stageStartIndex = -(this.stageDrawOffsetY + this.stageselectslide_y) / 24;
            if (this.stageYDirect == 0) {
                if (Key.touchstageselectreturn.Isin() && Key.touchstageselect.IsClick()) {
                    this.returnCursor = 1;
                }

                if (this.isSelectable) {
                    for(var1 = 0; var1 < Key.touchstageselectitem.length; ++var1) {
                        if (Key.touchstageselectitem[var1].Isin() && Key.touchstageselect.IsClick() && var1 <= StageManager.getOpenedStageId()) {
                            this.optionMenuCursor = var1;
                            this.returnCursor = 0;
                            break;
                        }
                    }
                }
            }

            if ((Key.buttonPress(524288 | 8388608) || Key.touchstageselectreturn.IsButtonPress() && this.returnCursor == 1) && fadeChangeOver()) {
                SoundSystem.getInstance().stopBgm(false);
                this.changeStateWithFade(preStageSelectState);
                switch (preStageSelectState) {
                    case 2:
                        this.isTitleBGMPlay = false;
                        Key.touchMainMenuInit2();
                        break;
                    case 23:
                        Key.touchCharacterSelectModeInit();
                        characterslots = 1;
                        this.initCharacterSelectRes();
                        break;
                    case 24:
                        Key.touchProRaceModeInit();
                        this.initTimeStageRes();
                }

                SoundSystem.getInstance().playSe(2);
            }

            this.stageselectslide_getprey = this.stageselectslide_gety;
        }

    }

    private void startGameDraw(MFGraphics var1) {
        AnimationDrawer var3 = muiAniDrawer;
        byte var2;
        if (Key.touchstartgamecontinue.Isin() && this.startgamecursor == 0) {
            var2 = 1;
        } else {
            var2 = 0;
        }

        var3.setActionId(var2 + 55);
        muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 18);
        var3 = muiAniDrawer;
        if (Key.touchstartgamenew.Isin() && this.startgamecursor == 1) {
            var2 = 1;
        } else {
            var2 = 0;
        }

        var3.setActionId(var2 + 55);
        muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 18);
        muiAniDrawer.setActionId(0);
        muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 18);
        muiAniDrawer.setActionId(1);
        muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 18);
        var3 = muiAniDrawer;
        if (Key.touchstartgamereturn.Isin() || Key.repeat(524288 | 8388608)) {
            var2 = 5;
        } else {
            var2 = 0;
        }

        var3.setActionId(var2 + 61);
        muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        if (this.startgameensureFlag) {
            drawFadeSlow(var1);
            if (this.startgameframe >= 12) {
                var1.setColor(0);
                MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            }
        }

    }

    private void startGameInit() {
        if (muiAniDrawer == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/mui");
            muiAniDrawer = (new Animation(var1.toString())).getDrawer(0, false, 0);
        }

        Key.touchStartGameInit();
        this.startgamecursor = -1;
        this.startgameframe = 0;
        this.startgameensureFlag = false;
        fadeInit(102, 220);
        StageManager.isContinueGame = false;
    }

    private void startGameLogic() {
        Key.touchMainMenuReset2();
        if (Key.touchstartgamecontinue.Isin() && Key.touchstartgame.IsClick()) {
            this.startgamecursor = 0;
        } else if (Key.touchstartgamenew.Isin() && Key.touchstartgame.IsClick()) {
            this.startgamecursor = 1;
        } else if (Key.touchstartgamereturn.Isin() && Key.touchstartgame.IsClick() && !this.startgameensureFlag) {
            this.startgamecursor = 2;
        }

        if (!this.startgameensureFlag) {
            // || Key.buttonPress(4)
            // || Key.buttonPress(8)
            if (Key.touchstartgamecontinue.IsButtonPress() && this.startgamecursor == 0) {
                this.startgameensureFlag = true;
                this.startgameframe = 0;
                StageManager.loadStageRecord();
                PlayerObject.setCharacter(StageManager.characterFromGame);
                StageManager.setStageID(StageManager.stageIDFromGame);
                fadeInit(102, 255);
                SoundSystem.getInstance().playSe(1);
            } else if (Key.touchstartgamenew.IsButtonPress() && this.startgamecursor == 1) {
                this.startgameensureFlag = true;
                this.startgameframe = 0;
                PlayerObject.setScore(0);
                PlayerObject.setLife(2);
                fadeInit(102, 255);
                SoundSystem.getInstance().playSe(1);
                GameState.isThroughGame = false;
                StageManager.isContinueGame = false;
            }

            if ((Key.buttonPress(524288 | 8388608) || Key.touchstartgamereturn.IsButtonPress() && this.startgamecursor == 2) && fadeChangeOver()) {
                fadeInit(102, 0);
                state = 2;
                this.nextState = 2;
                setFadeOver();
                this.isTitleBGMPlay = false;
                Key.touchMainMenuInit2();
                Key.touchanykeyClose();
                Key.touchkeyboardInit();
                Key.clear();
                SoundSystem.getInstance().playSe(2);
                this.initTitleRes2();
                this.returnCursor = 0;
            }
        } else {
            ++this.startgameframe;
            if (this.startgameframe == 12) {
                if (this.startgamecursor == 0) {
                    StageManager.isContinueGame = true;
                    fadeInit(255, 0);
                    setState(1);
                    System.out.println("continue");
                } else if (this.startgamecursor == 1) {
                    StageManager.isContinueGame = false;
                    this.changeStateWithFade(23);
                    characterslots = 1;
                    this.initCharacterSelectRes();
                    this.preCharaterSelectState = 2;
                    this.stage_sel_key = 2;
                    PlayerObject.stageModeState = 0;
                    System.out.println("new game");
                }
            }
        }

    }

    private void titleBgDraw0(MFGraphics var1) {
    }

    public boolean BP_gotoPaying() {
        return false;
    }

    public void BP_payingLogic() {
        if (!BP_enteredPaying) {
            if (BP_chargeLogic(0)) {
                BP_enteredPaying = true;
                activeGameProcess(true);
                setMenu();
                setTry();
                saveBPRecord();
                this.gotoStageSelect();
            } else {
                BP_enteredPaying = true;
                setTry();
                StageManager.resetStageIdforTry();
                saveBPRecord();
                this.gotoMainmenu();
            }
        }

    }

    public void aboutDraw(MFGraphics var1) {
        this.menuBgDraw(var1);
        this.drawMenuTitle(var1, 7, 0);
        fillMenuRect(var1, FRAME_X, 30, FRAME_WIDTH, FRAME_HEIGHT);
        var1.setColor(0);
        MyAPI.drawBoldStrings(var1, strForShow, FRAME_X + 10, 38, MENU_RECT_WIDTH - 20, FRAME_HEIGHT - 16, 16777215, 4656650, 0);
    }

    public void aboutInit() {
        MyAPI.initString();
        strForShow = MyAPI.getStrings(aboutStrings[0], MENU_RECT_WIDTH - 20);
    }

    public void aboutLogic() {
        Key.touchAboutInit();
        boolean var1;
        if (!Key.touchhelpup.Isin() && !Key.repeat(4)) {
            var1 = false;
        } else {
            var1 = true;
        }

        boolean var2;
        if (!Key.touchhelpdown.Isin() && !Key.repeat(8)) {
            var2 = false;
        } else {
            var2 = true;
        }

        MyAPI.logicString(var2, var1);
        if (Key.press(2)) {
        }

        if (Key.press(524288)) {
            this.changeStateWithFade(2);
            this.menuInit(MAIN_MENU);
            this.mainMenuInit();
            Key.touchAboutClose();
        }

    }

    public void changeDownSelect() {
        this.degree = 18;
        this.menuMoving = true;
    }

    public void changeStateWithFade(int var1) {
        if (!fading) {
            fading = true;
            fadeInit(0, 255);
            this.nextState = var1;
            this.fadeChangeState = true;
        }

    }

    public void changeUpSelect() {
        this.degree = -18;
        this.menuMoving = true;
    }

    public void characterSelectDraw(MFGraphics var1) {
        this.menuBgDraw(var1);

        for(int var2 = 0; var2 < CHARACTER_STR.length; ++var2) {
            String var5 = CHARACTER_STR[var2];
            int var3 = SCREEN_WIDTH;
            MyAPI.drawBoldString(var1, var5, var3 >> 1, var2 * 30 + 20, 17, 16776960, 0);
            if (var2 == PlayerObject.getCharacterID()) {
                int var4 = SCREEN_WIDTH;
                var3 = MFGraphics.stringWidth(14, CHARACTER_STR[var2]);
                MyAPI.drawBoldString(var1, "*", (var4 - var3 >> 1) - 10, var2 * 30 + 20, 24, 16776960, 0);
            }
        }

    }

    public void characterSelectInit() {
    }

    public void close() {
        MFDevice.disableLayer(-1);
        titleLeftImage = null;
        titleRightImage = null;
        titleSegaImage = null;
        Animation.closeAnimationArray(this.titleAni);
        this.titleAni = null;
        Animation.closeAnimationDrawer(this.titleAniDrawer);
        this.titleAniDrawer = null;
        Animation.closeAnimationArray(this.stageSelAni);
        this.stageSelAni = null;
        Animation.closeAnimationDrawer(this.stageSelAniDrawer);
        this.stageSelAniDrawer = null;
        Animation.closeAnimationDrawer(this.stageSelArrowUpDrawer);
        this.stageSelArrowUpDrawer = null;
        Animation.closeAnimationDrawer(this.stageSelArrowDownDrawer);
        this.stageSelArrowDownDrawer = null;
        Animation.closeAnimationDrawer(this.stageSelEmeraldDrawer);
        this.stageSelEmeraldDrawer = null;
        Animation.closeAnimationDrawer(this.optionArrowUpDrawer);
        this.optionArrowUpDrawer = null;
        Animation.closeAnimationDrawer(this.optionArrowDownDrawer);
        this.optionArrowDownDrawer = null;
        Animation.closeAnimationArray(this.timeAttAni);
        this.timeAttAni = null;
        Animation.closeAnimationDrawer(this.timeAttAniDrawer);
        this.timeAttAniDrawer = null;
        Animation.closeAnimationArray(this.recordAni);
        this.recordAni = null;
        Animation.closeAnimationDrawer(this.recordAniDrawer);
        this.recordAniDrawer = null;
        Animation.closeAnimationArray(this.charSelAni);
        this.charSelAni = null;
        Animation.closeAnimationDrawer(this.charSelAniDrawer);
        this.charSelAniDrawer = null;
        Animation.closeAnimationDrawer(this.charSelCaseDrawer);
        this.charSelCaseDrawer = null;
        Animation.closeAnimationDrawer(this.charSelRoleDrawer);
        this.charSelRoleDrawer = null;
        Animation.closeAnimationDrawer(this.charSelArrowDrawer);
        this.charSelArrowDrawer = null;
        Animation.closeAnimationDrawer(this.charSelTitleDrawer);
        this.charSelTitleDrawer = null;
        Animation.closeAnimationArray(this.charSelFilAni);
        this.charSelFilAni = null;
        Animation.closeAnimationDrawer(this.charSelFilAniDrawer);
        this.charSelFilAniDrawer = null;
        Animation.closeAnimationDrawer(this.titleSonicDrawer);
        this.titleSonicDrawer = null;
        this.logoImage = null;
        this.sonicBigImage = null;
        this.titleFrameImage = null;
        this.copyrightImage = null;
        this.openingClose();
        Animation.closeAnimationDrawer(this.interruptDrawer);
        this.interruptDrawer = null;
        System.gc();

        try {
            Thread.sleep(100L);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void draw(MFGraphics var1) {
        switch (state) {
            case 34:
            case 35:
                var1.setFont(11);
                break;
            default:
                if (GlobalResource.languageConfig < 8) {
                    var1.setFont(11);
                } else {
                    var1.setFont(14);
                }
        }

        switch (state) {
            case 0:
                Standard.drawSplash(var1, MyAPI.zoomOut(SCREEN_WIDTH), MyAPI.zoomOut(SCREEN_HEIGHT));
                break;
            case 1:
                this.drawTitleBg(var1);
                break;
            case 2:
                this.drawTitleBg(var1);
                this.drawMainMenu(var1);
                break;
            case 3:
                this.openingDraw(var1);
                break;
            case 4:
                this.drawTitleBg(var1);
                this.drawMainMenu(var1);
                drawFade(var1);
                this.startGameDraw(var1);
            case 5:
            case 22:
            default:
                break;
            case 6:
                this.stageSelectDraw(var1, 1);
                drawTouchKeySelectStage(var1);
                drawSoftKey(var1, true, true);
                break;
            case 7:
                this.moregameDraw(var1);
                drawSoftKey(var1, true, true);
                break;
            case 8:
                this.rankingDraw(var1);
                drawSoftKey(var1, false, true);
                break;
            case 9:
                this.optionDraw(var1);
                break;
            case 10:
                this.helpDraw(var1);
                drawTouchKeyHelp(var1);
                drawSoftKey(var1, false, true);
                break;
            case 11:
                this.aboutDraw(var1);
                drawTouchKeyAbout(var1);
                drawSoftKey(var1, false, true);
                break;
            case 12:
                this.drawTitleBg(var1);
                if (this.quitFlag == 0) {
                    this.drawMainMenu(var1);
                }

                drawFade(var1);
                if (this.isLinkMoreGame) {
                    this.SecondEnsurePanelDraw(var1, 26);
                } else {
                    this.SecondEnsurePanelDraw(var1, 14);
                }
                break;
            case 13:
                Standard.drawMoreGame(var1, MyAPI.zoomOut(SCREEN_WIDTH), MyAPI.zoomOut(SCREEN_HEIGHT));
                break;
            case 14:
                this.drawStageSelect(var1);
                break;
            case 15:
                this.rankingDraw(var1);
                drawSoftKey(var1, false, true);
                break;
            case 16:
                this.interruptDraw(var1);
                break;
            case 17:
                this.menuBgDraw(var1);
                this.optionDraw(var1);
                drawFade(var1);
                this.comfirmDraw(var1, 147);
                drawSoftKey(var1, true, true);
                break;
            case 18:
            case 19:
            case 20:
            case 21:
                this.titleBgDraw0(var1);
                this.drawTitle1(var1);
                if (state == 1 && System.currentTimeMillis() / 500L % 2L == 0L) {
                }

                this.drawTitle2(var1);
                break;
            case 23:
                this.drawCharacterSelect(var1);
                break;
            case 24:
                this.drawProTimeAttack(var1);
                break;
            case 25:
                this.drawCharacterRecord(var1);
                break;
            case 26:
                this.drawIntergradeRecord(var1);
                break;
            case 27:
                this.optionDraw(var1);
                this.touchPadPositionDraw(var1);
                break;
            case 28:
                this.optionDraw(var1);
                this.touchPadSizeDraw(var1);
                break;
            case 29:
                this.optionDraw(var1);
                this.itemsSelect4Draw(var1);
                break;
            case 30:
                this.optionDraw(var1);
                this.touchPadOpacityDraw(var1);
                break;
            case 31:
                this.optionDraw(var1);
                drawFade(var1);
                break;
            case 32:
                this.optionDraw(var1);
                this.itemsSelect2Draw(var1, 36, 35);
                break;
            case 33:
                this.optionDraw(var1);
                this.menuOptionLanguageDraw(var1);
                break;
            case 34:
                this.optionDraw(var1);
                this.helpDraw(var1);
                break;
            case 35:
                this.optionDraw(var1);
                this.creditDraw(var1);
                break;
            case 36:
                this.optionDraw(var1);
                this.SecondEnsurePanelDraw(var1, 44);
                break;
            case 37:
                this.optionDraw(var1);
                this.SecondEnsurePanelDraw(var1, 45);
                break;
            case 38:
                var1.setColor(0);
                this.titleAniDrawer.setActionId(0);
                drawFade(var1);
                break;
            case 39:
                this.optionDraw(var1);
                this.newLanguageDraw(var1);
                break;
            case 40:
                this.optionDraw(var1);
                this.itemsSelect3Draw(var1);
                break;
            case 41:
                this.drawTitleBg(var1);
                this.drawMainMenu(var1);
                drawFade(var1);
                this.SecondEnsurePanelDraw(var1, 105);
        }

        if (isDrawTouchPad && state != 0 && state != 1 && state != 13) {
            this.drawTouchKeyDirect(var1);
        }

    }

    public void fadeStateLogic() {
        if (fading && this.fadeChangeState && fadeChangeOver() && state != this.nextState) {
            state = this.nextState;
            this.fadeChangeState = false;
            if (this.IsFromStageSelect) {
                fadeInit(255, 102);
                this.IsFromStageSelect = false;
            } else if (this.IsFromOptionItems) {
                fadeInit(255, 220);
                this.IsFromOptionItems = false;
            } else {
                fadeInit(255, 0);
            }
        }

        if (state == this.nextState && fadeChangeOver()) {
            fading = false;
        }

    }

    public void gotoMainmenu() {
        state = 2;
        this.nextState = 2;
        this.menuInit(this.multiMainNoMoreGameItems);
        this.mainMenuInit();
        Key.touchMainMenuInit2();
        this.returnCursor = 0;
        Key.touchanykeyClose();
        Key.touchkeyboardInit();
        Key.clear();
        if (!ChargePlatform.isChargedByIndex(0) && ChargePlatform.getEnterTimesByIndex(0) < 3) {
            this.mainMenuItemCursor = 1;
        }

    }

    public void gotoStageSelect() {
        if (StageManager.characterFromGame != -1 && StageManager.stageIDFromGame != -1) {
            this.startGameInit();
            fadeInit(0, 102);
            state = 4;
        } else {
            this.changeStateWithFade(23);
            this.preCharaterSelectState = 2;
            this.stage_sel_key = 2;
            characterslots = 1;
            this.initCharacterSelectRes();
            GameState.isThroughGame = false;
            StageManager.isContinueGame = false;
        }

    }

    public void init() {
        try {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/help");
            helpStrings = MyAPI.loadText(var1.toString());
            var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/about");
            aboutStrings = MyAPI.loadText(var1.toString());
            this.openingOffsetX = SCREEN_WIDTH - 284 >> 1;
            this.openingOffsetY = SCREEN_HEIGHT - 160 >> 1;
        } catch (Exception var2) {
        }

        this.count = 50;
        initMenuFont();
        if (state == 0) {
            this.openingInit();
        }

        this.titleScale = 4.0F;
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
            SoundSystem.getInstance().playSe(2);
            Key.touchInterruptClose();
            Key.touchkeyboardClose();
            MFGamePad.resetKeys();
            if (Key.touchitemsselect2_1 != null) {
                Key.touchitemsselect2_1.reset();
            }

            if (Key.touchitemsselect2_2 != null) {
                Key.touchitemsselect2_2.reset();
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

            Standard.resume();
            state = this.interrupt_state;
            switch (this.interrupt_state) {
                case 0:
                case 1:
                case 4:
                case 5:
                case 7:
                case 10:
                case 11:
                case 12:
                case 13:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 26:
                case 38:
                default:
                    break;
                case 2:
                    fadeInit(0, 0);
                    break;
                case 3:
                    this.interrupt_state = 38;
                    state = 38;
                    this.isTitleBGMPlay = true;
                    setFadeColor(16777215);
                    fadeInit(255, 0);
                    this.initTitleRes();
                    SoundSystem.getInstance().playBgm(1, false);
                    break;
                case 6:
                case 8:
                    SoundSystem.getInstance().playBgm(4);
                    break;
                case 9:
                    SoundSystem.getInstance().playBgm(5);
                    break;
                case 14:
                    fadeInit(0, 0);
                    SoundSystem.getInstance().playBgm(3);
                    break;
                case 23:
                    SoundSystem.getInstance().playBgm(2);
                    break;
                case 24:
                    fadeInit(0, 0);
                    this.initTimeStageRes();
                    break;
                case 25:
                    fadeInit(0, 0);
                    this.characterRecordDisFlag = false;
                    break;
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 36:
                case 37:
                case 39:
                case 40:
                    SoundSystem.getInstance().playBgm(5);
                    this.IsFromOptionItems = true;
                    break;
                case 34:
                    SoundSystem.getInstance().playBgm(5);
                    break;
                case 35:
                    SoundSystem.getInstance().playBgm(33);
            }

            switch (this.interrupt_state) {
                case 0:
                    Key.touchsoftkeyInit();
                    break;
                case 1:
                    Key.touchanykeyInit();
                    break;
                default:
                    Key.touchkeyboardInit();
            }

            IsInInterrupt = false;
            this.IsFromStageSelect = false;
            this.IsFromOptionItems = false;
            Key.clear();
        }

    }

    public void logic() {
        if (this.count > 0) {
            --this.count;
        }

        if (Key.press(536870912)) {
            pause();
        }

        this.fadeStateLogic();
        double var1;
        double var3;
        int var5;
        int var6;
        int var7;
        int var8;
        switch (state) {
            case 0:
                if (Key.press(Key.B_S1 | Key.gSelect | 524288)) {
                    Standard.pressConfirm();
                } else if (Key.press(2)) {
                    Standard.pressCancel();
                }

                switch (Standard.execSplash()) {
                    case 1:
                        GlobalResource.soundSwitchConfig = 1;
                        if (GlobalResource.soundConfig == 0) {
                            GlobalResource.soundConfig = 9;
                        }

                        GlobalResource.seConfig = 1;
                        SoundSystem.getInstance().setSoundState(GlobalResource.soundConfig);
                        SoundSystem.getInstance().setSeState(GlobalResource.seConfig);
                        return;
                    case 2:
                        GlobalResource.soundSwitchConfig = 1;
                        GlobalResource.soundConfig = 0;
                        GlobalResource.seConfig = 0;
                        SoundSystem.getInstance().setSoundState(0);
                        SoundSystem.getInstance().setSeState(0);
                        return;
                    case 3:
                        this.changeStateWithFade(3);
                        Key.touchOpeningInit();
                        if (!SoundSystem.getInstance().bgmPlaying()) {
                            SoundSystem.getInstance().playBgm(0, false);
                        }

                        Key.touchsoftkeyClose();
                        Key.touchanykeyInit();
                        load_bp_string();
                        return;
                    default:
                        return;
                }
            case 1:
                ++this.titleFrame;
                if (this.titleFrame > 212) {
                    state = 0;
                    this.openingInit();
                    Key.touchOpeningInit();
                    SoundSystem.getInstance().playBgm(0, false);
                }

                if (this.titleFrame > 4) {
                    if (Key.buttonPress(Key.B_SEL | 16777216 | 8388608)) {
                        SoundSystem.getInstance().playSe(1);
                        this.gotoMainmenu();
                        setFadeOver();
                        Key.clear();
                    }

                    /*if (Key.press(524288) && fadeChangeOver()) {
                        state = 12;
                        this.secondEnsureInit();
                        fadeInit(0, 220);
                        SoundSystem.getInstance().playSe(1);
                        this.quitFlag = 1;
                    }*/
                    if (Key.press(524288) && fadeChangeOver()) {
                        if (this.isLinkMoreGame) {
                            state = 12;
                            this.secondEnsureInit();
                            fadeInit(0, 220);
                            SoundSystem.getInstance().playSe(1);
                            this.quitFlag = 1;
                        } else {
                            if (!MFMain.cheat && MFMain.tapCount < 7) {
                                MFMain.tapCount++;
                                break;
                            } else {
                                if (!MFMain.cheat) {
                                    SoundSystem.getInstance().playSe(12);
                                }
                                MFMain.cheat = true;
                                MFMain.tapCount = 0;
                                break;
                            }
                        }
                    }
                }
                break;
            case 2:
                this.mainMenuLogic();
                break;
            case 3:
                if (this.openingLogic()) {
                    state = 38;
                    this.isTitleBGMPlay = true;
                    setFadeColor(16777215);
                    fadeInit(255, 0);
                    this.openingClose();
                    this.initTitleRes();
                }
                break;
            case 4:
                this.startGameLogic();
                break;
            case 5:
                setState(1);
            case 6:
            case 22:
            case 31:
            default:
                break;
            case 7:
                switch (this.comfirmLogic()) {
                    case 0:
                        SoundSystem.getInstance().stopBgm(true);
                        Standard.menuMoreGameSelected();
                        exitGame();
                        return;
                    case 1:
                    case 400:
                        state = 2;
                        this.menuInit(MAIN_MENU);
                        this.mainMenuInit();
                        return;
                    default:
                        return;
                }
            case 8:
                this.rankingLogic();
                break;
            case 9:
                this.optionLogic();
                break;
            case 10:
                this.helpLogic();
                if (Key.press(2)) {
                }

                if (Key.press(524288)) {
                    this.changeStateWithFade(2);
                    this.menuInit(MAIN_MENU);
                    this.mainMenuInit();
                    Key.touchHelpClose();
                }
                break;
            case 11:
                this.aboutLogic();
                break;
            case 12:
                this.quitLogic();
                break;
            case 13:
                if (Key.press(Key.B_S1 | Key.gSelect | 524288)) {
                    Standard.pressConfirm();
                } else if (Key.press(2)) {
                    Standard.pressCancel();
                }

                switch (Standard.execMoreGame(true)) {
                    case 0:
                        Key.touchsoftkeyClose();
                        GlobalResource.saveSystemConfig();
                        exitGame();
                        return;
                    default:
                        return;
                }
            case 14:
                this.stageSelectLogic();
                break;
            case 15:
                this.gameover_rankingLogic();
                break;
            case 16:
                this.interruptLogic();
                break;
            case 17:
                switch (this.comfirmLogic()) {
                    case 0:
                        StageManager.resetGameRecord();
                        this.resetInfoCount = this.RESET_INFO_COUNT;
                        state = 9;
                        return;
                    case 1:
                    case 400:
                        state = 9;
                        return;
                    default:
                        return;
                }
            case 18:
                var5 = this.logoX;
                var6 = LOGO_POSITION_X;
                var8 = SCREEN_WIDTH;
                var7 = SCREEN_WIDTH;
                this.logoX = MyAPI.calNextPositionReverse(var5, var6, var8 + (var7 >> 1), 1, 2);
                if (this.logoX == SCREEN_WIDTH + (SCREEN_WIDTH >> 1)) {
                    this.logoX = -(SCREEN_WIDTH >> 1);
                    state = 2;
                    this.nextState = 2;
                    this.logoY = LOGO_POSITION_Y_2;
                }
                break;
            case 19:
                var1 = (double)this.logoX;
                var3 = (double)LOGO_POSITION_X;
                this.logoX = MyAPI.calNextPosition(var1, var3, 1, 2);
                if (this.logoX == LOGO_POSITION_X) {
                    state = 2;
                    this.nextState = 2;
                }
                break;
            case 20:
                var5 = this.logoX;
                var6 = LOGO_POSITION_X;
                var8 = SCREEN_WIDTH;
                var7 = SCREEN_WIDTH;
                this.logoX = MyAPI.calNextPositionReverse(var5, var6, var8 + (var7 >> 1), 1, 2);
                if (this.logoX == SCREEN_WIDTH + (SCREEN_WIDTH >> 1)) {
                    this.logoX = -(SCREEN_WIDTH >> 1);
                    state = 21;
                    this.nextState = 21;
                    this.logoY = LOGO_POSITION_Y;
                }

                if (this.mainMenuBackFlag && !this.menuMoving) {
                    this.mainMenuBackFlag = false;
                }
                break;
            case 21:
                var1 = (double)this.logoX;
                var3 = (double)LOGO_POSITION_X;
                this.logoX = MyAPI.calNextPosition(var1, var3, 1, 2);
                if (this.logoX == LOGO_POSITION_X) {
                    state = 1;
                    this.nextState = 1;
                }

                if (this.mainMenuBackFlag && !this.menuMoving) {
                    this.mainMenuBackFlag = false;
                }
                break;
            case 23:
                this.characterSelectLogic();
                break;
            case 24:
                this.proRaceModeLogic();
                break;
            case 25:
                this.characterRecordLogic();
                break;
            case 26:
                this.intergradeRecordLogic();
                break;
            case 27:
                switch (this.touchPadPositionLogic()) {
                    case 2:
                        GlobalResource.saveSystemConfig();
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    default:
                        return;
                }
            case 28:
                switch (this.touchPadSizeLogic()) {
                    case 2:
                        GlobalResource.saveSystemConfig();
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    default:
                        return;
                }
            case 29:
                switch (this.itemsSelect4Logic()) {
                    case 1:
                        GlobalResource.vibrationConfig = 0;
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    case 2:
                        GlobalResource.vibrationConfig = 1;
                        MyAPI.vibrate();
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    case 3:
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    case 4:
                        GlobalResource.vibrationConfig = 2;
                        MyAPI.vibrate();
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    case 5:
                        GlobalResource.vibrationConfig = 3;
                        MyAPI.vibrate();
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    default:
                        return;
                }
            case 30:
                switch (this.touchPadOpacityLogic()) {
                    case 2:
                        GlobalResource.saveSystemConfig();
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    default:
                        return;
                }
            case 32:
                this.menuOptionSpSetLogic();
                break;
            case 33:
                this.menuOptionLanguageLogic();
                break;
            case 34:
                this.helpLogic();
                if ((Key.buttonPress(524288 | 8388608) || Key.touchhelpreturn.IsButtonPress() && this.returnPageCursor == 1) && fadeChangeOver()) {
                    this.changeStateWithFade(9);
                    this.isOptionDisFlag = false;
                    SoundSystem.getInstance().playSe(2);
                }
                break;
            case 35:
                this.creditLogic();
                break;
            case 36:
                this.menuOptionResetRecordLogic();
                break;
            case 37:
                this.menuOptionResetRecordEnsureLogic();
                break;
            case 38:
                if (fadeChangeOver()) {
                    state = 1;
                    setFadeColor(0);
                    Key.touchOpeningClose();
                }
                break;
            case 39:
                switch (this.newLanguageLogic()) {
                    case 2:
                        GlobalResource.saveSystemConfig();
                        StringBuilder var9 = new StringBuilder("/lang");
                        var9.append(GlobalResource.languageConfig);
                        var9.append("/mui");
                        muiAniDrawer = (new Animation(var9.toString())).getDrawer(0, false, 0);
                        var9 = new StringBuilder("/lang");
                        var9.append(GlobalResource.languageConfig);
                        var9.append("/about");
                        aboutStrings = MyAPI.loadText(var9.toString());
                        if (GlobalResource.languageConfig >= 6) {
                            var9 = new StringBuilder("/lang");
                            var9.append(GlobalResource.languageConfig);
                            var9.append("/title_right.png");
                        } else {
                            var9 = new StringBuilder("/title/title_right.png");
                        }
                        titleRightImage = MFImage.createImage(var9.toString());
                        var9 = new StringBuilder("/lang");
                        var9.append(GlobalResource.languageConfig);
                        var9.append("/utl_res/title.dat");
                        this.titleAni = Animation.getInstanceFromQi(var9.toString());
                        this.titleAniDrawer = this.titleAni[0].getDrawer(0, true, 0);
                        this.interruptDrawer = null;
                        this.timeAttAni = null;
                        this.charSelAni = null;
                        this.stageSelAni = null;
                        this.recordAni = null;
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    default:
                        return;
                }
            case 40:
                switch (this.itemsSelect3Logic()) {
                    case 1:
                        GlobalResource.sensorConfig = 0;
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    case 2:
                        GlobalResource.sensorConfig = 1;
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    case 3:
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    case 4:
                        GlobalResource.sensorConfig = 2;
                        fadeInit(220, 0);
                        state = 9;
                        return;
                    default:
                        return;
                }
            case 41:
                this.segaMoreLogic();
        }

    }

    public void pause() {
        if (state == 16) {
            state = 16;
            this.nextState = 16;
            this.interruptInit();
        } else {
            if (this.fadeChangeState && this.nextState != state) {
                state = this.nextState;
                this.fadeChangeState = false;
                if (this.IsFromStageSelect) {
                    fadeInit(255, 102);
                    this.IsFromStageSelect = false;
                } else if (this.IsFromOptionItems) {
                    fadeInit(255, 220);
                    this.IsFromOptionItems = false;
                } else {
                    fadeInit(255, 0);
                }
            }

            this.interrupt_state = state;
            state = 16;
            this.nextState = 16;
            this.interruptInit();
            Key.touchInterruptInit();
            Standard.pause();
            Key.touchkeyboardInit();
        }

    }

    public void stageSelectDraw(MFGraphics var1, int var2) {
        this.menuBgDraw(var1);
        double var3 = (double)this.stageDrawOffsetY;
        double var5 = (double)(-this.stageStartIndex * ITEM_SPACE);
        this.stageDrawOffsetY = MyAPI.calNextPosition(var3, var5, 1, 2);
        int var7;
        int var8;
        int var9;
        if (this.stageItemNumForShow != StageManager.STAGE_NUM) {
            var8 = this.stageDrawStartY;
            int var10 = ITEM_SPACE;
            int var11 = SCREEN_WIDTH;
            var7 = this.stageItemNumForShow;
            var9 = ITEM_SPACE;
            MyAPI.setClip(var1, 0, var8 - (var10 >> 1), var11, var7 * var9);
        }

        for(var7 = 0; var7 < StageManager.STAGE_NAME.length; ++var7) {
            if (var7 == this.optionMenuCursor && this.stage_select_state == 1) {
                var1.setColor(16711680);
            } else {
                var1.setColor(0);
            }

            String var12 = "stage" + StageManager.STAGE_NAME[var7];
            var9 = SCREEN_WIDTH;
            var8 = this.stageDrawOffsetY;
            MyAPI.drawString(var1, var12, var9 >> 1, var8 + var7 * 20 + 20, 17);
        }

        var8 = SCREEN_WIDTH;
        var7 = SCREEN_HEIGHT;
        MyAPI.setClip(var1, 0, 0, var8, var7);
        if (var2 == 0) {
            for(var2 = 0; var2 < SCREEN_HEIGHT / 96 + 1; ++var2) {
                drawMenuFontById(var1, 104, 0, var2 * 96);
            }

            this.drawMenuTitle(var1, 1, 5, 0);
        } else if (var2 == 1) {
            for(var2 = 0; var2 < SCREEN_HEIGHT / 96 + 2; ++var2) {
                drawMenuFontById(var1, 110, 0, var2 * 96);
            }

            this.drawMenuTitle(var1, 2, 5, 0);
        }

        this.STAGE_SEL_ARROW_UP_X = (SCREEN_WIDTH >> 1) - 64;
        this.STAGE_SEL_ARROW_UP_Y = (SCREEN_HEIGHT >> 1) - 48;
        this.STAGE_SEL_ARROW_DOWN_X = (SCREEN_WIDTH >> 1) - 64;
        this.STAGE_SEL_ARROW_DOWN_Y = (SCREEN_HEIGHT >> 1) + 48;
    }
}

