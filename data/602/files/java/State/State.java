//
// Decompiled by FernFlower - 1456ms
//
package State;

import GameEngine.time.GameTime;

import GameEngine.Key;
import GameEngine.TouchDirectKey;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Common.NumberDrawer;
import Lib.Record;
import Lib.SoundSystem;
import SonicGBA.GameObject;
import SonicGBA.GlobalResource;
import SonicGBA.SonicDef;
import SonicGBA.StageManager;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import com.sega.mobile.framework.device.MFDevice;

public abstract class State implements SonicDef, StringIndex {
    public static double delta = 0;
    private static int touchKeyboardAssetSize = -1;
    private int previewSize = -1;
    private int previewLeft = Integer.MIN_VALUE, previewRight = Integer.MIN_VALUE;
    public static final int ARROW_WAIT_FRAME = 4;
    public static final int BACKGROUND_WIDTH = 80;
    private static final int[] BAR_ANIMATION;
    private static final int[] BAR_COLOR;
    public static final int BAR_HEIGHT = 20;
    public static final int BAR_ID_BLACK = 1;
    public static final int BAR_ID_BLUE = 0;
    public static final int BAR_ID_WHITE = 2;
    public static final int BG_NUM;
    public static String[][] BPEffectStrings;
    public static String BPPayingTitle;
    public static final byte BP_ITEM_BLUE_SHELTER = 1;
    public static final byte BP_ITEM_GREEN_SHELTER = 2;
    public static final byte BP_ITEM_INVINCIBILITY = 0;
    public static final byte BP_ITEM_SPEED = 3;
    public static boolean BP_enteredPaying;
    public static int BP_itemsHeight;
    public static MFImage BP_itemsImg;
    public static int BP_itemsWidth;
    public static byte[] BP_items_num;
    public static int BP_wordsHeight;
    public static MFImage BP_wordsImg;
    public static int BP_wordsWidth;
    public static String[] BPstrings;
    public static final int CASE_HEIGHT;
    public static final int CASE_WIDTH;
    public static final int CASE_X;
    public static final int CASE_Y = 30;
    public static final int COMFIRM_FRAME_ID = 100;
    public static final int COMFIRM_ID = 10;
    public static final int COMFIRM_X;
    public static final int COMFIRM_Y;
    private static final int CONFIRM_FRAME_OFFSET_X;
    private static final int CONFIRM_FRAME_OFFSET_Y;
    private static final int CONFIRM_OFFSET_X;
    private static final int CONFIRM_STR_OFFSET_X = 44;
    public static final int DRAW_NUM = 2;
    public static final int EMERALD_STATE_FAILD = 2;
    public static final int EMERALD_STATE_NONENTER = 0;
    public static final int EMERALD_STATE_SUCCESS = 1;
    private static final int FADE_FILL_HEIGHT = 40;
    private static final int FADE_FILL_WIDTH = 40;
    public static final int FONT_MOVE_SPEED = 8;
    private static final int FRAME_DOWN = 117;
    public static final int FRAME_HEIGHT;
    private static final int FRAME_MIDDLE = 116;
    private static final int FRAME_OFFSET_Y = 24;
    private static final int FRAME_UP = 115;
    public static final int FRAME_WIDTH;
    public static final int FRAME_X;
    public static final int FRAME_Y = 30;
    private static final int HELP_PAGE_BACKGROUND_WIDTH = 64;
    public static boolean IsAllClear;
    public static boolean IsGameOver;
    public static boolean IsInInterrupt;
    public static boolean IsPaid;
    public static boolean IsTimeOver;
    public static final int LOADING_TYPE_BAR = 2;
    public static final int LOADING_TYPE_BLACK = 0;
    public static final int LOADING_TYPE_WHITE = 1;
    private static final int MENU_BG_COLOR_1 = 16777215;
    private static final int MENU_BG_COLOR_2 = 15658734;
    private static final int MENU_BG_OFFSET = 24;
    private static final int MENU_BG_SPEED;
    private static final int MENU_BG_WIDTH = 48;
    private static final int MENU_FRAME_BACK_COLOR = 16763904;
    private static final int MENU_FRAME_BAR_COLOR = 16750916;
    public static final int MENU_TITLE_DRAW_NUM;
    public static final int MENU_TITLE_DRAW_OFFSET_Y;
    public static final int MENU_TITLE_MOVE_DIRECTION;
    public static final int MORE_GAME_COMFIRM = 3;
    public static final int MOVE_DIRECTION;
    public static final int MOVE_DIRECTION_FONT;
    private static final int[] OPTION_SOUND;
    public static final int PAGE_BACKGROUND_HEIGHT = 56;
    public static final int PAGE_BACKGROUND_SPEED = 1;
    private static final int PAUSE_SOUND_ITEMS = 4;
    public static int PageBackGroundOffsetX;
    public static int PageBackGroundOffsetY;
    public static int PageFrameCnt;
    public static final int QUIT_COMFIRM = 9;
    public static final int RESET_HEIGHT;
    public static final String[] RESET_STR;
    public static final int RESET_Y_DES;
    public static final int RETURN_PRESSED = 400;
    public static int SELECT_BAR_WIDTH;
    private static final int SELECT_BOX_COLOR = 16773039;
    private static final int SELECT_BOX_WIDTH;
    private static final int SOFT_KEY_OFFSET = 2;
    private static final int SOUND_VOLUMN_WAIT_FRAME = 4;
    public static final int STAGE_MOVE_DIRECTION = 224;
    public static final int STATE_ENDING = 8;
    public static final int STATE_EXTRA_ENDING = 11;
    public static final int STATE_GAME = 1;
    public static final int STATE_NORMAL_ENDING = 10;
    public static final int STATE_RETURN_FROM_GAME = 2;
    public static final int STATE_SCORE_RANKING = 4;
    public static final int STATE_SELECT_CHARACTER = 9;
    public static final int STATE_SELECT_NORMAL_STAGE = 5;
    public static final int STATE_SELECT_RACE_STAGE = 3;
    public static final int STATE_SPECIAL = 6;
    public static final int STATE_SPECIAL_ENDING = 12;
    public static final int STATE_SPECIAL_TO_GMAE = 7;
    public static final int STATE_TITLE = 0;
    private static final int STR_NO = 149;
    private static final int STR_YES = 148;
    public static final int TEXT_DISPLAY_WIDTH = 188;
    public static final int TOOL_TIP_FONT_WIDTH;
    public static int TOOL_TIP_HEIGHT;
    public static String[] TOOL_TIP_STR;
    public static String TOOL_TIP_STR_ORIGNAL;
    public static final int TOOL_TIP_WIDTH;
    public static int TOOL_TIP_X;
    public static int TOOL_TIP_Y_DES;
    public static int TOOL_TIP_Y_DES_2;
    public static final byte TXT_BLUE_SHELTER_EFFECT = 14;
    public static final byte TXT_BUY_TOOL = 18;
    public static final byte TXT_BUY_TOOLS_TEXT = 8;
    public static final byte TXT_BUY_TOOLS_TITLE = 7;
    public static final byte TXT_CONTINUE_TRY = 2;
    public static final byte TXT_EFFECT = 12;
    public static final byte TXT_GREEN_SHELTER_EFFECT = 15;
    public static final byte TXT_HOLD_NUM = 11;
    public static final byte TXT_INVINCIBILITY_EFFECT = 16;
    public static final byte TXT_MENU = 10;
    public static final byte TXT_PAY = 1;
    public static final byte TXT_PAY_TEXT = 4;
    public static final byte TXT_PAY_TITLE = 3;
    public static final byte TXT_REVIVE_TEXT = 6;
    public static final byte TXT_REVIVE_TITLE = 5;
    public static final byte TXT_SHOP = 9;
    public static final byte TXT_SPEED_EFFECT = 13;
    public static final byte TXT_TOOL = 17;
    public static final byte TXT_TOOL_MAX = 19;
    public static final byte TXT_TRY_TITLE = 0;
    public static final byte TXT_USE_BLUE_SHELTER = 21;
    public static final byte TXT_USE_GREEN_SHELTER = 22;
    public static final byte TXT_USE_INVINCIBILITY = 20;
    public static final byte TXT_USE_SPEED = 23;
    public static int VOLUME_X;
    protected static final int VOL_IMAGE_WIDTH = 8;
    protected static final int VOL_SIGN_IMAGE_WIDTH = 10;
    public static final int WARNING_FONT_WIDTH;
    public static final int WARNING_HEIGHT;
    public static final String[] WARNING_STR;
    public static final String WARNING_STR_ORIGNAL = "警告：开始新游戏将覆盖您当前的游戏进度";
    public static final int WARNING_WIDTH;
    public static final int WARNING_X;
    public static final int WARNING_Y_DES;
    public static final int WARNING_Y_DES_2;
    public static String[] aboutStrings;
    public static String[] allStrings;
    public static Animation arrowAnimation;
    public static AnimationDrawer downArrowDrawer;
    protected static int fadeAlpha;
    private static int fadeFromValue;
    private static int[] fadeRGB;
    private static int fadeToValue;
    public static boolean fading;
    public static AnimationDrawer guiAniDrawer;
    public static Animation guiAnimation;
    public static String[] helpStrings;
    private static String helpTitleString;
    public static boolean isDrawTouchPad;
    public static boolean isInSPStage;
    public static AnimationDrawer langAniDrawer;
    public static boolean lastFading;
    public static AnimationDrawer leftArrowDrawer;
    public static int loadingType;
    public static MFImage menuBg;
    public static AnimationDrawer menuFontDrawer;
    public static AnimationDrawer muiAniDrawer;
    public static AnimationDrawer muiDownArrowDrawer;
    public static AnimationDrawer muiLeftArrowDrawer;
    public static AnimationDrawer muiRightArrowDrawer;
    public static AnimationDrawer muiUpArrowDrawer;
    private static AnimationDrawer numberDrawer;
    private static int pause_x;
    private static int pause_y;
    private static int preFadeAlpha;
    public static AnimationDrawer rightArrowDrawer;
    protected static MFImage skipImage;
    private static int softKeyHeight;
    private static MFImage softKeyImage;
    private static int softKeyWidth;
    public static State state;
    private static int stateId = -1;
    public static String[] strForShow;
    public static int tool_id;
    public static MFImage touchgamekeyImage;
    public static Animation touchgamekeyboardAnimation;
    public static AnimationDrawer touchgamekeyboardDrawer;
    public static Animation touchkeyboardAnimation;
    public static AnimationDrawer touchkeyboardDrawer;
    public static int trytimes;
    public static AnimationDrawer upArrowDrawer;
    protected static MFImage volumeImage;
    public static int warningY;
    public boolean IsInBP;
    public int MORE_GAME_HEIGHT;
    public int MORE_GAME_START_X;
    public int MORE_GAME_START_Y;
    public int MORE_GAME_WIDTH;
    public int arrowframecnt;
    public int arrowindex = -1;
    public int confirmcursor;
    public int confirmframe;
    public int[] currentElement;
    public int cursor;
    public int elementNum;
    public int finalitemsselectcursor;
    private int helpIndex;
    public boolean isArrowClicked = false;
    public boolean isConfirm;
    public boolean isItemsSelect;
    private boolean isNewLanguageClick;
    private boolean isTouchPadClick;
    public int itemsselectcursor;
    public int itemsselectframe;
    public int mainMenuItemCursor;
    public boolean menuMoving;
    public int pauseoptionCursor;
    public int returnCursor;
    public int returnPageCursor = 0;
    public int selectMenuOffsetX = 0;
    public MFImage textBGImage;
    private int timeCnt;
    public int titleBgOffsetX;
    public int titleBgOffsetY;
    public int tooltipY;

    static {
        VOLUME_X = SCREEN_WIDTH - ((VOL_IMAGE_WIDTH - 1) * 10 + 1) >> 1;
        softKeyWidth = 0;
        softKeyHeight = 0;
        SELECT_BAR_WIDTH = 40;
        fadeAlpha = 40;
        fadeRGB = new int[1600];
        pause_x = SCREEN_WIDTH - 40;
        pause_y = 17;
        isInSPStage = false;
        int[] var1 = new int[]{255, 0, 16777215};
        BAR_COLOR = var1;
        BAR_ANIMATION = new int[]{103, 102, 118};
        COMFIRM_X = SCREEN_WIDTH >> 1;
        COMFIRM_Y = SCREEN_HEIGHT >> 1;
        int var0 = Math.max(FONT_WIDTH * 6, 72);
        CONFIRM_FRAME_OFFSET_X = var0;
        if (MENU_SPACE > 32) {
            var0 = MENU_SPACE;
        } else {
            var0 = 32;
        }

        CONFIRM_FRAME_OFFSET_Y = var0 + 20;
        CONFIRM_OFFSET_X = 0;
        SELECT_BOX_WIDTH = FONT_H + 4;
        MENU_BG_SPEED = MyAPI.zoomIn(1);
        MOVE_DIRECTION_FONT = 80;
        if (MOVE_DIRECTION_FONT > 120) {
            var0 = MOVE_DIRECTION_FONT;
        } else {
            var0 = 120;
        }

        MOVE_DIRECTION = var0;
        BG_NUM = (SCREEN_WIDTH + 80 - 1) / 80;
        if (MOVE_DIRECTION_FONT > 80) {
            var0 = MOVE_DIRECTION_FONT;
        } else {
            var0 = 80;
        }

        MENU_TITLE_MOVE_DIRECTION = var0;
        MENU_TITLE_DRAW_NUM = (SCREEN_WIDTH + MENU_TITLE_MOVE_DIRECTION - 1) / MENU_TITLE_MOVE_DIRECTION + 2;
        byte var2;
        if (MOVING_TITLE_TOP) {
            var2 = 10;
        } else if (SCREEN_HEIGHT < 220) {
            var2 = 10;
        } else {
            var2 = 30;
        }

        MENU_TITLE_DRAW_OFFSET_Y = var2;
        FRAME_X = SCREEN_WIDTH - MENU_RECT_WIDTH >> 1;
        FRAME_WIDTH = MENU_RECT_WIDTH;
        FRAME_HEIGHT = SCREEN_HEIGHT - 30 - 30;
        OPTION_SOUND = new int[]{55, 58, 57, 56};
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            var2 = 50;
        } else {
            var2 = 26;
        }

        WARNING_X = var2;
        WARNING_Y_DES_2 = SCREEN_HEIGHT + 40;
        warningY = WARNING_Y_DES_2;
        WARNING_WIDTH = SCREEN_WIDTH - WARNING_X * 2;
        WARNING_FONT_WIDTH = SCREEN_WIDTH - (WARNING_X + 5) * 2;
        var0 = WARNING_FONT_WIDTH;
        WARNING_STR = MyAPI.getStrings("警告：开始新游戏将覆盖您当前的游戏进度", var0);
        WARNING_HEIGHT = WARNING_STR.length * LINE_SPACE + 20;
        WARNING_Y_DES = SCREEN_HEIGHT - WARNING_HEIGHT;
        var0 = WARNING_FONT_WIDTH;
        RESET_STR = MyAPI.getStrings("记录已重置！", var0);
        RESET_HEIGHT = RESET_STR.length * LINE_SPACE + 20;
        RESET_Y_DES = SCREEN_HEIGHT - RESET_HEIGHT;
        trytimes = 3;
        IsPaid = false;
        TOOL_TIP_X = WARNING_X;
        TOOL_TIP_Y_DES_2 = WARNING_Y_DES_2;
        TOOL_TIP_WIDTH = WARNING_WIDTH;
        TOOL_TIP_FONT_WIDTH = WARNING_FONT_WIDTH;
        BP_enteredPaying = false;
        CASE_X = SCREEN_WIDTH - MENU_RECT_WIDTH >> 1;
        CASE_WIDTH = MENU_RECT_WIDTH;
        CASE_HEIGHT = SCREEN_HEIGHT - 60;
        BP_items_num = new byte[4];
        loadingType = 0;
    }

    public State() {
        this.MORE_GAME_WIDTH = FRAME_WIDTH;
        this.MORE_GAME_HEIGHT = MENU_SPACE * 4 + 20;
        this.MORE_GAME_START_X = FRAME_X;
        this.MORE_GAME_START_Y = SCREEN_HEIGHT - this.MORE_GAME_HEIGHT >> 1;
        this.menuMoving = true;
        this.returnCursor = 0;
        this.tooltipY = TOOL_TIP_Y_DES_2;
        this.IsInBP = false;
        this.confirmcursor = 0;
    }

    public static boolean BP_IsChargeByIndex(int var0) {
        return false;
    }

    public static boolean BP_chargeLogic(int var0) {
        return false;
    }

    public static void BP_loadImage() {
    }

    public static boolean IsActiveGame() {
        return false;
    }

    public static boolean IsToolsCharge() {
        return false;
    }

    public static void activeGameProcess(boolean var0) {
        IsPaid = var0;
    }

    public static void arrowLogic() {
    }

    public static void drawBar(MFGraphics var0, int var1, int var2) {
        drawBar(var0, var1, 0, var2);
    }

    public static void drawBar(MFGraphics var0, int var1, int var2, int var3) {
        var0.setColor(BAR_COLOR[var1]);
        MyAPI.fillRect(var0, var2, var3 - 10, SCREEN_WIDTH, 20);
    }

    public static void drawFade(MFGraphics var0) {
        drawFadeBase(var0, 3);
    }

    public static void drawFadeBase(MFGraphics var0, int var1) {
        fadeAlpha = GameTime.approachOnce(State.class, "fadeAlpha", fadeAlpha, fadeToValue, 1, var1, 3.0);
        drawFadeCore(var0);
    }

    private static void drawFadeCore(MFGraphics var0) {
        if (var0.getg() instanceof com.sega.mobile.framework.opengl.GLGraphics) {
            int oldColor = var0.getColor(), oldAlpha = var0.getAlpha();
            var0.setColor(fadeRGB.length == 0 ? 0 : fadeRGB[0]);
            var0.setAlpha(fadeAlpha);
            var0.fillRect(0, 0, MyAPI.zoomOut(SCREEN_WIDTH), MyAPI.zoomOut(SCREEN_HEIGHT));
            var0.setColor(oldColor);
            var0.setAlpha(oldAlpha);
            return;
        }

        if (fadeAlpha != 0) {
            int var1;
            int var2;
            int[] var5;
            if (preFadeAlpha != fadeAlpha) {
                var1 = 0;

                while(true) {
                    if (var1 >= 40) {
                        preFadeAlpha = fadeAlpha;
                        break;
                    }

                    for(var2 = 0; var2 < 40; ++var2) {
                        var5 = fadeRGB;
                        int var3 = fadeAlpha;
                        int var4 = fadeRGB[var2 * 40 + var1];
                        var5[var2 * 40 + var1] = var3 << 24 & -16777216 | var4 & 16777215;
                    }

                    ++var1;
                }
            }

            for(var1 = 0; var1 < MyAPI.zoomOut(SCREEN_WIDTH); var1 += 40) {
                for(var2 = 0; var2 < MyAPI.zoomOut(SCREEN_HEIGHT); var2 += 40) {
                    var5 = fadeRGB;
                    var0.drawRGB(var5, 0, 40, var1, var2, 40, 40, true);
                }
            }
        }

    }

    public static void drawFadeInSpeed(final MFGraphics mfGraphics, final int n) {
        if (State.fadeFromValue > State.fadeToValue) {
            State.fadeAlpha = GameTime.advanceOnce(State.class, "fadeAlpha", State.fadeAlpha, -(n));
            if (State.fadeAlpha <= State.fadeToValue) {
                State.fadeAlpha = State.fadeToValue;
            }
        }
        else if (State.fadeFromValue < State.fadeToValue) {
            State.fadeAlpha = GameTime.advanceOnce(State.class, "fadeAlpha", State.fadeAlpha, n);
            if (State.fadeAlpha >= State.fadeToValue) {
                State.fadeAlpha = State.fadeToValue;
            }
        }
        drawFadeCore(mfGraphics);
    }

    public static void drawFadeSlow(MFGraphics var0) {
        drawFadeBase(var0, 6);
    }

    private static void drawLeftSoftKey(MFGraphics var0) {
    }

    public static void drawMenuBar(MFGraphics var0, int var1, int var2, int var3) {
        for(int var4 = 0; var4 < BG_NUM; ++var4) {
            drawMenuFontById(var0, BAR_ANIMATION[var1], var4 * 80 + var2, var3);
        }

    }

    public static void drawMenuFontById(MFGraphics var0, int var1, int var2, int var3) {
        drawMenuFontById(var0, var1, var2, var3, 0);
    }

    public static void drawMenuFontById(MFGraphics var0, int var1, int var2, int var3, int var4) {
    }

    public static void drawMenuFontByString(MFGraphics var0, int var1, int var2, int var3) {
        drawMenuFontByString(var0, var1, var2, var3, 17);
    }

    public static void drawMenuFontByString(MFGraphics var0, int var1, int var2, int var3, int var4) {
    }

    public static void drawMenuFontByString(MFGraphics var0, String var1, int var2, int var3, int var4) {
        drawMenuFontByString(var0, var1, var2, var3, var4, 16777215, 0);
    }

    public static void drawMenuFontByString(MFGraphics var0, String var1, int var2, int var3, int var4, int var5, int var6) {
        MyAPI.drawBoldString(var0, var1, var2, var3 - FONT_H_HALF, var4, var5, var6);
    }

    public static void drawMenuStringById(MFGraphics var0, int var1, int var2, int var3, int var4) {
    }

    private static void drawRightSoftKey(MFGraphics var0) {
    }

    public static void drawSkipSoftKey(MFGraphics var0) {
    }

    public static void drawSoftKey(MFGraphics var0, boolean var1, boolean var2) {
        if (var1) {
            drawLeftSoftKey(var0);
        }

        if (var2) {
            drawRightSoftKey(var0);
        }

    }

    public static void drawSoftKeyPause(MFGraphics var0) {
        if (!isInSPStage) {
            byte var1;
            if (GameObject.stageModeState == 0) {
                var1 = 0;
            } else {
                var1 = 32;
            }

            if (Key.touchkey_pause != null) {
                if (Key.touchkey_pause.Isin()) {
                    drawTouchKeyBoardById(var0, 14, SCREEN_WIDTH + var1, 0);
                } else {
                    drawTouchKeyBoardById(var0, 13, SCREEN_WIDTH + var1, 0);
                }
            }
        } else if (Key.touchspstagepause != null) {
            if (Key.touchspstagepause.Isin()) {
                drawTouchKeyBoardById(var0, 14, SCREEN_WIDTH + 32, 0);
            } else {
                drawTouchKeyBoardById(var0, 13, SCREEN_WIDTH + 32, 0);
            }
        }

    }

    public static void drawTouchGameKey(MFGraphics var0) {
    }

    public static void drawTouchGameKeyBoardById(MFGraphics var0, int var1, int var2, int var3) {
        touchgamekeyboardDrawer.setActionId(var1);
        touchgamekeyboardDrawer.draw(var0, var2, var3);
    }

    public static void drawTouchKeyBoardById(MFGraphics var0, int var1, int var2, int var3) {
        touchkeyboardDrawer.setActionId(var1);
        touchkeyboardDrawer.draw(var0, var2, var3);
    }

    private void drawTouchKeyDirectPad(MFGraphics var1) {
        int var3 = 0;
        if (Key.touchdirectgamekey != null) {
            var3 = Key.touchdirectgamekey.getDegree();
        }

        byte var4 = 0;
        byte var2;
        if (var3 >= 248 && var3 < 292) {
            var2 = 1;
        } else if (var3 >= 292 && var3 < 337) {
            var2 = 2;
        } else if (var3 >= 0 && var3 < 22 || var3 >= 337 && var3 <= 360) {
            var2 = 3;
        } else if (var3 >= 22 && var3 < 67) {
            var2 = 4;
        } else if (var3 >= 67 && var3 < 113) {
            var2 = 5;
        } else if (var3 >= 113 && var3 < 157) {
            var2 = 6;
        } else if (var3 >= 157 && var3 < 202) {
            var2 = 7;
        } else {
            var2 = var4;
            if (var3 >= 202) {
                var2 = var4;
                if (var3 < 248) {
                    var2 = 8;
                }
            }
        }

        drawTouchGameKeyBoardById(var1, var2, Key.circlePadXPos, 128);
    }

    public static void shutdown() {
        if (state != null) state.close();
        state = null;
        stateId = -1;
        Lib.AnimationDrawer.setAllPause(false);
        Lib.AnimationDrawer.setWorldPaused(false);
        Lib.AnimationDrawer.setWorldContext(false);
    }

    public static void exitGame() {
        if (state != null) {
            state.close();
            state = null;
        }

        MFDevice.notifyExit();
    }

    public static boolean fadeChangeOver() {
        boolean var0;
        if (fadeAlpha == fadeToValue) {
            var0 = true;
        } else {
            var0 = false;
        }

        return var0;
    }

    public static void fadeInit(final int fadeFromValue, final int n) {
        int fadeToValue;
        if (n == 220 || (fadeToValue = n) == 102) {
            fadeToValue = 192;
        }
        State.fadeFromValue = fadeFromValue;
        State.fadeToValue = fadeToValue;
        State.fadeAlpha = GameTime.set(State.class, "fadeAlpha", State.fadeFromValue);
        State.preFadeAlpha = -1;
    }
    
    public static void fadeInitAndStart(final int fadeFromValue, final int fadeToValue) {
        State.fadeFromValue = fadeFromValue;
        State.fadeToValue = fadeToValue;
        State.fadeAlpha = GameTime.set(State.class, "fadeAlpha", State.fadeFromValue);
        State.preFadeAlpha = -1;
        State.fading = true;
    }
    
    public static void fadeInit_Modify(final int fadeFromValue, final int fadeToValue) {
        State.fadeFromValue = fadeFromValue;
        State.fadeToValue = fadeToValue;
        State.fadeAlpha = GameTime.set(State.class, "fadeAlpha", State.fadeFromValue);
        State.preFadeAlpha = -1;
    }

    public static void fillMenuRect(MFGraphics var0, int var1, int var2, int var3, int var4) {
        drawMenuFontById(var0, 117, var1, var2 + var4);
        drawMenuFontById(var0, 117, var1 + var3, var2 + var4, 2);
        if (var4 - 48 > 0) {
            MyAPI.setClip(var0, var1, var2 + 24, var3, var4 - 48);

            for(int var5 = 0; var5 < var4 - 48; var5 += 24) {
                drawMenuFontById(var0, 116, var1, var2 + 24 + var5);
                drawMenuFontById(var0, 116, var1 + var3, var2 + 24 + var5, 2);
            }
        }

        MyAPI.setClip(var0, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        drawMenuFontById(var0, 115, var1, var2);
        drawMenuFontById(var0, 115, var1 + var3, var2, 2);
    }

    public static int getCurrentFade() {
        return fadeAlpha;
    }

    public static int getFade() {
        return fadeAlpha;
    }

    public static int getMenuFontWidth(int var0) {
        if (menuFontDrawer == null) {
            initMenuFont();
        }

        menuFontDrawer.setActionId(var0);
        return menuFontDrawer.getCurrentFrameWidth();
    }

    private static void initArrowDrawer() {
    }

    public static void initMenuFont() {
    }

    public static void initTouchkeyBoard() {
        if (touchkeyboardAnimation != null && touchKeyboardAssetSize != GlobalResource.touchKeyBoardSize) {
            releaseTouchkeyBoard();
        }
        if (touchkeyboardAnimation == null) {
            StringBuilder var1 = new StringBuilder("/tuch/control_panel");
            var1.append(GlobalResource.touchKeyBoardSize);
            touchkeyboardAnimation = new Animation(var1.toString());
            touchKeyboardAssetSize = GlobalResource.touchKeyBoardSize;
        }

        if (touchkeyboardDrawer == null) touchkeyboardDrawer = touchkeyboardAnimation.getDrawer(0, true, 0);
        if (touchgamekeyboardDrawer == null) touchgamekeyboardDrawer = touchkeyboardAnimation.getDrawer(0, true, 0);
    }

    public static void init_bp() {
    }

    public static void loadBPRecord() {
    }

    public static void load_bp_string() {
        setMenu();
    }

    public static void pauseTrigger() {
        if (SoundSystem.getInstance().bgmPlaying2()) {
            SoundSystem.getInstance().stopBgm(true);
            SoundSystem.getInstance().stopLongSe();
            SoundSystem.getInstance().stopLoopSe();
        }

    }

    public static void releaseTouchkeyBoard() {
        touchKeyboardAssetSize = -1;
        Animation.closeAnimation(touchkeyboardAnimation);
        touchkeyboardAnimation = null;
        Animation.closeAnimationDrawer(touchkeyboardDrawer);
        touchkeyboardDrawer = null;
        Animation.closeAnimation(touchgamekeyboardAnimation);
        touchgamekeyboardAnimation = null;
        Animation.closeAnimationDrawer(touchgamekeyboardDrawer);
        touchgamekeyboardDrawer = null;
        touchgamekeyImage = null;
    }

    public static void saveBPRecord() {
    }

    public static void setFadeColor(int var0) {
        for(int var1 = 0; var1 < fadeRGB.length; ++var1) {
            fadeRGB[var1] = var0;
        }

    }

    public static void setFadeOver() {
        fadeAlpha = fadeToValue;
    }

    public static void setMenu() {
        TitleState.setMainMenu();
        GameState.setPauseMenu();
    }

    public static void setSoundVolumnDown() {
        if (GlobalResource.soundConfig <= 0) {
            GlobalResource.soundConfig = 0;
        } else {
            --GlobalResource.soundConfig;
        }

        if (GlobalResource.soundConfig == 0) {
            GlobalResource.seConfig = 0;
        }

        SoundSystem.getInstance().setVolumnState(GlobalResource.soundConfig);
        SoundSystem.getInstance().setSeState(GlobalResource.seConfig);
    }

    public static void setSoundVolumnUp() {
        if (GlobalResource.soundConfig >= 15) {
            GlobalResource.soundConfig = 15;
        } else {
            ++GlobalResource.soundConfig;
        }

        if (GlobalResource.soundConfig == 1) {
            SoundSystem.getInstance().resumeBgm();
        }

        if (GlobalResource.soundConfig > 0) {
            GlobalResource.seConfig = 1;
        }

        SoundSystem.getInstance().setVolumnState(GlobalResource.soundConfig);
        SoundSystem.getInstance().setSeState(GlobalResource.seConfig);
    }

    public static void setState(int var0) {
        if (stateId != var0) {
            if (state != null) {
                state.close();
                state = null;
                System.gc();
            }

            stateId = var0;
            switch (stateId) {
                case 0:
                    state = new TitleState();
                    break;
                case 1:
                    Key.touchCharacterSelectModeClose();
                    state = new GameState();
                    break;
                case 2:
                    Key.touchkeyboardClose();
                case 3:
                case 4:
                case 5:
                case 9:
                    state = new TitleState(stateId);
                    break;
                case 6:
                    state = new SpecialStageState();
                    break;
                case 7:
                    state = new GameState(stateId);
                case 8:
                default:
                    break;
                case 10:
                    state = new EndingState(0);
                    break;
                case 11:
                    state = new EndingState(1);
                    break;
                case 12:
                    state = new EndingState(2);
            }

            state.init();
            isDrawTouchPad = false;
            if (stateId == 6) {
                isInSPStage = true;
            } else {
                isInSPStage = false;
            }
        }

    }

    public static void setTry() {
        if (trytimes > 0) {
            --trytimes;
        } else {
            trytimes = 0;
        }

    }

    public static void stateDraw(MFGraphics var0) {
        if (state != null) {
            try {
                MyAPI.setClip(var0, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                state.draw(var0);
            } catch (Exception var2) {
                var2.printStackTrace();
            }

            if (fading) {
                drawFade(var0);
            }
        }

    }

    public static void stateInit() {
        Key.init();
        Record.initRecord();
        initArrowDrawer();
        StageManager.loadStageRecord();
        GlobalResource.loadSystemConfig();
        SpecialStageState.loadData();
        StageManager.loadHighScoreRecord();
        State.resetTouchPosition();
    }

    /** Whether the scene's gameplay animation clock is stopped; menu/UI time stays live. */
    public boolean isWorldAnimationPaused() { return false; }

    public static boolean worldAnimationsPaused() {
        return state != null && state.isWorldAnimationPaused();
    }

    public static void stateLogic() {
        SoundSystem.getInstance().exec();
        if (state != null) {
            try {
                arrowLogic();
                state.logic();
            } catch (Exception var1) {
                var1.printStackTrace();
            }
        }
    }

    public static void statePause() {
        if (state != null) {
            state.pause();
        }

        SoundSystem.getInstance().stopBgm(true);
    }

    public static void staticDrawFadeSlow(MFGraphics var0) {
        if (state != null) {
            drawFadeSlow(var0);
        }

    }

    private int transTouchPointX(TouchDirectKey var1, int var2, int var3) {
        int var4 = var2 - 32;
        var3 -= 128;
        if (var4 * var4 + var3 * var3 > 256) {
            var2 = MyAPI.dCos(var1.getDegree()) * 16 / 100 + 32;
        }

        return var2;
    }

    private int transTouchPointY(TouchDirectKey var1, int var2, int var3) {
        var2 -= 32;
        int var4 = var3 - 128;
        if (var2 * var2 + var4 * var4 > 256) {
            var3 = MyAPI.dSin(var1.getDegree()) * 16 / 100 + 128;
        }

        return var3;
    }

    public void BP_payingDraw(MFGraphics var1) {
        this.menuBgDraw(var1);
    }

    public void BP_payingInit(int var1, int var2) {
        MyAPI.initString();
        strForShow = MyAPI.getStrings(BPstrings[var1], MENU_RECT_WIDTH - 20);
        BPPayingTitle = BPstrings[var2];
        this.IsInBP = true;
    }

    public void SecondEnsurePanelDraw(MFGraphics var1, int var2) {
        if (muiAniDrawer == null) {
            StringBuilder var5 = new StringBuilder("/lang");
            var5.append(GlobalResource.languageConfig);
            var5.append("/mui");
            muiAniDrawer = (new Animation(var5.toString())).getDrawer(0, false, 0);
        } else {
            muiAniDrawer.setActionId(54);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 20);
            AnimationDrawer var4 = muiAniDrawer;
            byte var3;
            if (Key.touchsecondensureyes.Isin() && this.confirmcursor == 0) {
                var3 = 1;
            } else {
                var3 = 0;
            }

            var4.setActionId(var3 + 59);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 40, (SCREEN_HEIGHT >> 1) + 40);
            var4 = muiAniDrawer;
            if (Key.touchsecondensureno.Isin() && this.confirmcursor == 1) {
                var3 = 1;
            } else {
                var3 = 0;
            }

            var4.setActionId(var3 + 59);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 40, (SCREEN_HEIGHT >> 1) + 40);
            muiAniDrawer.setActionId(46);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 40, (SCREEN_HEIGHT >> 1) + 40);
            muiAniDrawer.setActionId(47);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 40, (SCREEN_HEIGHT >> 1) + 40);
            muiAniDrawer.setActionId(var2);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 20);
            var4 = muiAniDrawer;
            byte var6;
            if (Key.touchsecondensurereturn.Isin() || Key.repeat((524288 | 8388608))) {
                var6 = 5;
            } else {
                var6 = 0;
            }

            var4.setActionId(var6 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
            if (this.isConfirm && this.confirmframe > 8) {
                var1.setColor(0);
                MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            }
        }

    }

    public void changeDownSelect() {
    }

    public void changeUpSelect() {
    }

    public abstract void close();

    public void comfirmDraw(MFGraphics var1, int var2) {
        drawMenuFontById(var1, 100, COMFIRM_X, COMFIRM_Y);
        drawMenuFontById(var1, var2, COMFIRM_X, COMFIRM_Y - (MENU_SPACE >> 1));
        drawMenuFontById(var1, 101, this.cursor * 40 + COMFIRM_X - 20, COMFIRM_Y + (MENU_SPACE >> 1));
        drawMenuFontById(var1, 10, COMFIRM_X, COMFIRM_Y + (MENU_SPACE >> 1));
    }

    public int comfirmLogic() {
        Key.touchConfirmInit();
        int var1;
        if (Key.press(Key.gSelect | Key.B_S1)) {
            Key.touchConfirmClose();
            var1 = this.cursor;
        } else if (Key.touchConfirmYes.Isin() && this.cursor == 1) {
            this.cursor = 0;
            Key.touchConfirmYes.reset();
            var1 = -1;
        } else if (Key.touchConfirmNo.Isin() && this.cursor == 0) {
            this.cursor = 1;
            Key.touchConfirmNo.reset();
            var1 = -1;
        } else if ((!Key.touchConfirmYes.Isin() || this.cursor != 0) && (!Key.touchConfirmNo.Isin() || this.cursor != 1)) {
            if (Key.press(2)) {
            }

            if (Key.press(524288)) {
                Key.touchConfirmClose();
                var1 = 400;
            } else {
                if (Key.press(16)) {
                    --this.cursor;
                    this.cursor += 2;
                    this.cursor %= 2;
                } else if (Key.press(32)) {
                    ++this.cursor;
                    this.cursor += 2;
                    this.cursor %= 2;
                }

                var1 = -1;
            }
        } else {
            Key.touchConfirmClose();
            var1 = this.cursor;
        }

        return var1;
    }

    public void confirmDraw(MFGraphics var1, String var2) {
        drawMenuFontById(var1, 100, COMFIRM_X, COMFIRM_Y);
        int var3 = COMFIRM_X;
        int var5 = COMFIRM_Y;
        int var6 = CONFIRM_FRAME_OFFSET_Y;
        int var4 = LINE_SPACE;
        MyAPI.drawBoldString(var1, var2, var3, var5 - var6 + 10 + var4, 17, 16777215, 4656650);
        drawMenuFontById(var1, 101, this.cursor * 40 + COMFIRM_X - 20, COMFIRM_Y + (MENU_SPACE >> 1));
        drawMenuFontById(var1, 10, COMFIRM_X, COMFIRM_Y + (MENU_SPACE >> 1));
    }

    public abstract void draw(MFGraphics var1);

    public void drawArcLine(MFGraphics var1, int var2, int var3, int var4, int var5) {
        var1.setColor(16711680);
        MyAPI.drawLine(var1, var2, var3, var4, var5);
    }

    public void drawLowQualifyBackGround(MFGraphics var1, int var2, int var3, int var4) {
        var1.setColor(var2);
        MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        var1.setColor(var3);
        this.titleBgOffsetX = GameTime.advance(this, "titleBgOffsetX", this.titleBgOffsetX, MENU_BG_SPEED);
        this.titleBgOffsetY = GameTime.advance(this, "titleBgOffsetY", this.titleBgOffsetY, MENU_BG_SPEED);
        this.titleBgOffsetX = GameTime.wrap(this, "titleBgOffsetX", this.titleBgOffsetX, var4);
        this.titleBgOffsetY = GameTime.wrap(this, "titleBgOffsetY", this.titleBgOffsetY, var4);

        for(var2 = -var4 + this.titleBgOffsetX; var2 < SCREEN_WIDTH; var2 += var4) {
            for(var3 = -var4 - this.titleBgOffsetY; var3 < SCREEN_HEIGHT; var3 += var4) {
                MyAPI.fillRect(var1, var2, var3, var4 >> 1, var4 >> 1);
                MyAPI.fillRect(var1, (var4 >> 1) + var2, (var4 >> 1) + var3, var4 >> 1, var4 >> 1);
            }
        }

    }

    public void drawMenuTitle(MFGraphics var1, int var2, int var3) {
        this.drawScrollFont(var1, var2, MENU_TITLE_DRAW_OFFSET_Y, MENU_TITLE_MOVE_DIRECTION);
    }

    public void drawMenuTitle(MFGraphics var1, int var2, int var3, int var4) {
        var3 = MENU_TITLE_DRAW_OFFSET_Y;
        if (var4 <= 0) {
            var4 = MENU_TITLE_MOVE_DIRECTION;
        }

        this.drawScrollFont(var1, var2, var3, var4);
    }

    public void drawScrollFont(MFGraphics var1, int var2, int var3, int var4) {
        drawBar(var1, 0, var3);
        this.selectMenuOffsetX = GameTime.advance(this, "selectMenuOffsetX", this.selectMenuOffsetX, 8);
        this.selectMenuOffsetX = GameTime.wrap(this, "selectMenuOffsetX", this.selectMenuOffsetX, var4);

        int var5;
        for(var5 = 0; var5 - this.selectMenuOffsetX > 0; var5 -= var4) {
        }

        for(int var6 = 0; var6 < MENU_TITLE_DRAW_NUM; ++var6) {
            drawMenuFontById(var1, var2, var5 + var6 * var4 - this.selectMenuOffsetX, var3);
        }

    }

    public void drawTouchKeyDirect(MFGraphics var1) {
        TouchDirectKey var2 = Key.touchdirectgamekey;
        int var3 = 10 + (2 * GlobalResource.touchKeyBoardSize);
        for (int var4 = 0; var4 < GlobalResource.touchKeyBoardOpacity + 1; var4++) {
            if (TouchDirectKey.IsKeyReleased()) {
                drawTouchGameKeyBoardById(var1, 0, Key.circlePadXPos, 128);
            } else {
                label31: {
                    var2 = Key.touchdirectgamekey;
                    if (!TouchDirectKey.IsKeyDragged()) {
                        var2 = Key.touchdirectgamekey;
                        if (!TouchDirectKey.IsKeyPressed()) {
                            break label31;
                        }
                    }

                    this.drawTouchKeyDirectPad(var1);
                }
            }

            if (!Key.press(16777216) && !Key.repeat(16777216)) {
                drawTouchGameKeyBoardById(var1, 9, Key.aXPos + var3, Key.aYPos + var3);
            } else {
                drawTouchGameKeyBoardById(var1, 10, Key.aXPos + var3, Key.aYPos + var3);
            }

            if (!Key.press(Key.B_SEL) && !Key.repeat(Key.B_SEL)) {
                drawTouchGameKeyBoardById(var1, 11, Key.bXPos + var3, 118 + var3);
            } else {
                drawTouchGameKeyBoardById(var1, 12, Key.bXPos + var3, 118 + var3);
            }
        }

    }

    public int getMenuPosY(int var1) {
        return (SCREEN_HEIGHT - (this.elementNum - 1) * MENU_SPACE >> 1) + MENU_SPACE * var1;
    }

    public int getMenuPosY(int var1, int var2) {
        return (SCREEN_HEIGHT - (var2 - 1) * MENU_SPACE >> 1) + MENU_SPACE * var1;
    }

    public void helpDraw(MFGraphics var1) {
        drawFade(var1);
        if (muiAniDrawer == null) {
            StringBuilder var6 = new StringBuilder("/lang");
            var6.append(GlobalResource.languageConfig);
            var6.append("/mui");
            muiAniDrawer = (new Animation(var6.toString())).getDrawer(0, false, 0);
        } else {
            muiAniDrawer.setActionId(62);

            int var2;
            int var3;
            for(var2 = PageBackGroundOffsetX - 64; var2 < SCREEN_WIDTH * 3 / 2; var2 += 64) {
                for(var3 = PageBackGroundOffsetY - 56; var3 < SCREEN_HEIGHT * 3 / 2; var3 += 56) {
                    muiAniDrawer.draw(var1, var2, var3);
                }
            }

            int var4;
            for(var2 = 0; var2 < 8; ++var2) {
                MFImage var5 = this.textBGImage;
                var4 = SCREEN_WIDTH;
                var3 = SCREEN_HEIGHT;
                MyAPI.drawImage(var1, var5, (var4 >> 1) - 104 + var2 * 26, (var3 >> 1) - 72, 0);
            }

            if (fadeAlpha < 128) {
                String[] var8 = strForShow;
                var3 = SCREEN_WIDTH;
                var4 = SCREEN_HEIGHT;
                var2 = SCREEN_WIDTH;
                MyAPI.drawStrings(var1, var8, (var3 >> 1) - 104 + 10, (var4 >> 1) - 72 + 5, var2, 131, 0, true, 16777215, 4656650, 0, 11);
            }

            muiLeftArrowDrawer.draw(var1, 0, (SCREEN_HEIGHT >> 1) - 4);
            muiRightArrowDrawer.draw(var1, SCREEN_WIDTH, (SCREEN_HEIGHT >> 1) - 4);
            if (MyAPI.upPermit) {
                muiUpArrowDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 25, SCREEN_HEIGHT);
            }

            if (MyAPI.downPermit) {
                muiDownArrowDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 24, SCREEN_HEIGHT);
            }

            // muiAniDrawer.setActionId(this.helpIndex + 95);
            // muiAniDrawer.draw(var1, SCREEN_WIDTH, SCREEN_HEIGHT);
            AnimationDrawer var9 = muiAniDrawer;
            byte var7;
            if (Key.touchhelpreturn.Isin() || Key.repeat(524288 | 8388608)) {
                var7 = 5;
            } else {
                var7 = 0;
            }

            var9.setActionId(var7 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

        if (numberDrawer == null) {
            numberDrawer = (new Animation("/animation/number")).getDrawer(0, false, 0);
        } else {
            NumberDrawer.drawNum(var1, 0, this.helpIndex + 1, SCREEN_WIDTH - 26, 145, 2);
            muiAniDrawer.setActionId(95);
            muiAniDrawer.draw(var1, SCREEN_WIDTH - 21, 144);
            NumberDrawer.drawNum(var1, 0, 7, SCREEN_WIDTH - 8, 145, 2);
        }

    }

    public void helpInit() {
        StringBuilder var1 = new StringBuilder("/lang");
        var1.append(GlobalResource.languageConfig);
        var1.append("/help");
        helpStrings = MyAPI.loadText(var1.toString());
        this.helpIndex = 0;
        MyAPI.initString();
        strForShow = MyAPI.getStrings(helpStrings[this.helpIndex], 11, SCREEN_WIDTH);
        helpTitleString = MyAPI.getStringToDraw(strForShow[0]);
        var1 = new StringBuilder("/lang");
        var1.append(GlobalResource.languageConfig);
        var1.append("/mui");
        muiLeftArrowDrawer = (new Animation(var1.toString())).getDrawer(91, true, 0);
        muiRightArrowDrawer = (new Animation(var1.toString())).getDrawer(92, true, 0);
        muiUpArrowDrawer = (new Animation(var1.toString())).getDrawer(93, true, 0);
        muiDownArrowDrawer = (new Animation(var1.toString())).getDrawer(94, true, 0);
        Key.touchInstructionInit();
        this.arrowframecnt = GameTime.set(this, "arrowframecnt", 0);
        this.isArrowClicked = false;
        PageFrameCnt = GameTime.set(State.class, "PageFrameCnt", 0);
        if (this.textBGImage == null) {
            this.textBGImage = MFImage.createImage("/animation/text_bg.png");
        }

        this.arrowindex = -1;
        this.returnPageCursor = 0;
    }

    protected void updatePageBackground(int tileWidth) {
        PageFrameCnt = GameTime.advanceOnce(State.class, "PageFrameCnt", PageFrameCnt, 1);
        PageFrameCnt = GameTime.wrap(State.class, "PageFrameCnt", PageFrameCnt, 11);
        int steps = GameTime.periods(State.class, "PageFrameCnt", "pageBackground", PageFrameCnt, 2, 0);
        PageBackGroundOffsetX = (PageBackGroundOffsetX + steps) % tileWidth;
        PageBackGroundOffsetY = ((PageBackGroundOffsetY - steps) % 56 + 56) % 56;
    }

    public void helpLogic() {
        updatePageBackground(64);
        if (Key.press(16) || Key.touchhelpleftarrow.Isin() && Key.touchpage.IsClick()) {
            this.arrowindex = 0;
        } else if (Key.press(32) || Key.touchhelprightarrow.Isin() && Key.touchpage.IsClick()) {
            this.arrowindex = 1;
        }

        if (Key.touchhelpreturn.Isin() && Key.touchpage.IsClick()) {
            this.returnPageCursor = 1;
        }

        if (Key.slidesensorhelp.isSliding()) {
            if (Key.slidesensorhelp.isSlide(Key.DIR_UP)) {
                MyAPI.logicString(true, false);
            } else if (Key.slidesensorhelp.isSlide(Key.DIR_DOWN)) {
                MyAPI.logicString(false, true);
            }
        }

        if (Key.repeat(4) || Key.touchhelpuparrow.Isin()) {
            this.arrowframecnt = GameTime.advance(this, "arrowframecnt", this.arrowframecnt, 1);
            if (this.arrowframecnt <= 4 && !this.isArrowClicked) {
                MyAPI.logicString(false, true);
                this.isArrowClicked = true;
            } else if (this.arrowframecnt > 4 && GameTime.periodic(this, "arrowframecnt", "helpLogic:1267", this.arrowframecnt, 2, 0)) {
                MyAPI.logicString(false, true);
            }
        } else if (Key.repeat(8) || Key.touchhelpdownarrow.Isin()) {
            this.arrowframecnt = GameTime.advance(this, "arrowframecnt", this.arrowframecnt, 1);
            if (this.arrowframecnt <= 4 && !this.isArrowClicked) {
                MyAPI.logicString(true, false);
                this.isArrowClicked = true;
            } else if (this.arrowframecnt > 4 && GameTime.periodic(this, "arrowframecnt", "helpLogic:1275", this.arrowframecnt, 2, 0)) {
                MyAPI.logicString(true, false);
            }
        } else {
            this.arrowframecnt = GameTime.set(this, "arrowframecnt", 0);
            this.isArrowClicked = false;
        }

        if ((Key.touchhelpleftarrow.IsButtonPress() || Key.press(16)) && this.arrowindex == 0) {
            SoundSystem.getInstance().playSe(3);
            --this.helpIndex;
            this.helpIndex += helpStrings.length;
            this.helpIndex %= helpStrings.length;
            MyAPI.initString();
            strForShow = MyAPI.getStrings(helpStrings[this.helpIndex], 11, SCREEN_WIDTH);
        } else if ((Key.touchhelprightarrow.IsButtonPress() || Key.press(32)) && this.arrowindex == 1) {
            SoundSystem.getInstance().playSe(3);
            ++this.helpIndex;
            this.helpIndex %= helpStrings.length;
            MyAPI.initString();
            strForShow = MyAPI.getStrings(helpStrings[this.helpIndex], 11, SCREEN_WIDTH);
        }

    }

    public abstract void init();

    public void itemsSelect2Draw(MFGraphics var1, int var2, int var3) {
        if (muiAniDrawer == null) {
            StringBuilder var6 = new StringBuilder("/lang");
            var6.append(GlobalResource.languageConfig);
            var6.append("/mui");
            muiAniDrawer = (new Animation(var6.toString())).getDrawer(0, false, 0);
        } else {
            AnimationDrawer var5 = muiAniDrawer;
            byte var4;
            if (Key.touchitemsselect2_1.Isin() && this.itemsselectcursor == 0) {
                var4 = 1;
            } else {
                var4 = 0;
            }

            var5.setActionId(var4 + 55);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 18);
            var5 = muiAniDrawer;
            if (Key.touchitemsselect2_2.Isin() && this.itemsselectcursor == 1) {
                var4 = 1;
            } else {
                var4 = 0;
            }

            var5.setActionId(var4 + 55);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 18);
            muiAniDrawer.setActionId(var2);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 18);
            muiAniDrawer.setActionId(var3);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 18);
            var5 = muiAniDrawer;
            byte var7;
            if (Key.touchitemsselect2_return.Isin()) {
                var7 = 5;
            } else {
                var7 = 0;
            }

            var5.setActionId(var7 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

    }

    public void itemsSelect2Init() {
        Key.touchItemsSelect2Init();
        this.itemsselectcursor = 0;
        this.isItemsSelect = false;
        fadeInit(0, 192);
    }

    public int itemsSelect2Logic() {
        if (Key.touchitemsselect2_return.Isin() && Key.touchitemsselect2.IsClick()) {
            this.itemsselectcursor = 2;
        }

        if (Key.touchitemsselect2_1.Isin() && Key.touchitemsselect2.IsClick()) {
            this.itemsselectcursor = 0;
        }

        if (Key.touchitemsselect2_2.Isin() && Key.touchitemsselect2.IsClick()) {
            this.itemsselectcursor = 1;
        }

        byte var1;
        if (this.isItemsSelect) {
            this.itemsselectframe = GameTime.advance(this, "itemsselectframe", this.itemsselectframe, 1);
            if (this.itemsselectframe > 8) {
                fadeInit(220, 102);
                if (this.finalitemsselectcursor == 0) {
                    var1 = 1;
                } else {
                    var1 = 2;
                }

                return var1;
            }
        }

        if (Key.touchitemsselect2_1.IsButtonPress() && this.itemsselectcursor == 0 && !this.isItemsSelect) {
            this.isItemsSelect = true;
            this.itemsselectframe = GameTime.set(this, "itemsselectframe", 0);
            this.finalitemsselectcursor = 0;
            SoundSystem.getInstance().playSe(1);
            var1 = 0;
        } else if (Key.touchitemsselect2_2.IsButtonPress() && this.itemsselectcursor == 1 && !this.isItemsSelect) {
            this.isItemsSelect = true;
            this.itemsselectframe = GameTime.set(this, "itemsselectframe", 0);
            this.finalitemsselectcursor = 1;
            SoundSystem.getInstance().playSe(1);
            var1 = 0;
        } else if ((Key.press(524288 | 8388608) || Key.touchitemsselect2_return.IsButtonPress()) && fadeChangeOver()) {
            SoundSystem.getInstance().playSe(2);
            var1 = 3;
        } else {
            var1 = 0;
        }

        return var1;
    }

    public void itemsSelect3Draw(MFGraphics var1) {
        if (muiAniDrawer == null) {
            StringBuilder var4 = new StringBuilder("/lang");
            var4.append(GlobalResource.languageConfig);
            var4.append("/mui");
            muiAniDrawer = (new Animation(var4.toString())).getDrawer(0, false, 0);
        } else {
            AnimationDrawer var3 = muiAniDrawer;
            byte var2;
            if (Key.touchitemsselect3_1.Isin() && this.itemsselectcursor == 0) {
                var2 = 1;
            } else {
                var2 = 0;
            }
            var3.setActionId(var2 + 55);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 36);

            var3 = muiAniDrawer;
            if (Key.touchitemsselect3_2.Isin() && this.itemsselectcursor == 1) {
                var2 = 1;
            } else {
                var2 = 0;
            }
            var3.setActionId(var2 + 55);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, ((SCREEN_HEIGHT >> 1) - 36) + 36);

            var3 = muiAniDrawer;
            if (Key.touchitemsselect3_3.Isin() && this.itemsselectcursor == 4) {
                var2 = 1;
            } else {
                var2 = 0;
            }
            var3.setActionId(var2 + 55);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, ((SCREEN_HEIGHT >> 1) - 36) + 72);
            muiAniDrawer.setActionId(70);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 36);
            muiAniDrawer.setActionId(69);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, ((SCREEN_HEIGHT >> 1) - 36) + 36);
            muiAniDrawer.setActionId(68);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, ((SCREEN_HEIGHT >> 1) - 36) + 72);

            var3 = muiAniDrawer;
            if (Key.touchitemsselect3_return.Isin() || Key.repeat(524288 | 8388608)) {
                var2 = 5;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

    }

    public void itemsSelect3Init() {
        Key.touchItemsSelect3Init();
        this.itemsselectcursor = 0;
        this.isItemsSelect = false;
        fadeInit(0, 102);
        if (muiAniDrawer == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/mui");
            muiAniDrawer = (new Animation(var1.toString())).getDrawer(0, false, 0);
        }

    }

    public int itemsSelect3Logic() {
        if (Key.touchitemsselect3_return.Isin() && Key.touchitemsselect3.IsClick()) {
            this.itemsselectcursor = 2;
        }

        if (Key.touchitemsselect3_1.Isin() && Key.touchitemsselect3.IsClick()) {
            this.itemsselectcursor = 0;
        }

        if (Key.touchitemsselect3_2.Isin() && Key.touchitemsselect3.IsClick()) {
            this.itemsselectcursor = 1;
        }

        if (Key.touchitemsselect3_3.Isin() && Key.touchitemsselect3.IsClick()) {
            this.itemsselectcursor = 4;
        }

        byte var1;
        if (this.isItemsSelect) {
            this.itemsselectframe = GameTime.advance(this, "itemsselectframe", this.itemsselectframe, 1);
            if (this.itemsselectframe > 8) {
                fadeInit(220, 102);
                if (this.finalitemsselectcursor == 0) {
                    var1 = 1;
                    return var1;
                } else {
                    if (this.finalitemsselectcursor == 1) {
                        var1 = 2;
                    } else {
                        var1 = 4;
                    }

                    return var1;
                }
            }
        }

        if (Key.touchitemsselect3_1.IsButtonPress() && this.itemsselectcursor == 0 && !this.isItemsSelect) {
            this.isItemsSelect = true;
            this.itemsselectframe = GameTime.set(this, "itemsselectframe", 0);
            this.finalitemsselectcursor = 0;
            SoundSystem.getInstance().playSe(1);
            var1 = 0;
        } else if (Key.touchitemsselect3_2.IsButtonPress() && this.itemsselectcursor == 1 && !this.isItemsSelect) {
            this.isItemsSelect = true;
            this.itemsselectframe = GameTime.set(this, "itemsselectframe", 0);
            this.finalitemsselectcursor = 1;
            SoundSystem.getInstance().playSe(1);
            var1 = 0;
        } else if (Key.touchitemsselect3_3.IsButtonPress() && this.itemsselectcursor == 4 && !this.isItemsSelect) {
            this.isItemsSelect = true;
            this.itemsselectframe = GameTime.set(this, "itemsselectframe", 0);
            this.finalitemsselectcursor = 2;
            SoundSystem.getInstance().playSe(1);
            var1 = 0;
        } else if (Key.buttonPress(524288 | 8388608) && fadeChangeOver() || Key.touchitemsselect3_return.IsButtonPress() && this.itemsselectcursor == 2  && fadeChangeOver()) {
            SoundSystem.getInstance().playSe(2);
            var1 = 3;
        } else {
            var1 = 0;
        }

        return var1;
    }

    public void itemsSelect4Draw(MFGraphics var1) {
        if (muiAniDrawer == null) {
            StringBuilder var4 = new StringBuilder("/lang");
            var4.append(GlobalResource.languageConfig);
            var4.append("/mui");
            muiAniDrawer = (new Animation(var4.toString())).getDrawer(0, false, 0);
        } else {
            AnimationDrawer var3 = muiAniDrawer;
            byte var2;
            if (Key.touchitemsselect4_1.Isin() && this.itemsselectcursor == 0) {
                var2 = 1;
            } else {
                var2 = 0;
            }
            var3.setActionId(var2 + 55);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 28 - 17);

            var3 = muiAniDrawer;
            if (Key.touchitemsselect4_2.Isin() && this.itemsselectcursor == 1) {
                var2 = 1;
            } else {
                var2 = 0;
            }
            var3.setActionId(var2 + 55);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 28 + 13);

            var3 = muiAniDrawer;
            if (Key.touchitemsselect4_3.Isin() && this.itemsselectcursor == 3) {
                var2 = 1;
            } else {
                var2 = 0;
            }
            var3.setActionId(var2 + 55);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 28 + 43);

            var3 = muiAniDrawer;
            if (Key.touchitemsselect4_4.Isin() && this.itemsselectcursor == 4) {
                var2 = 1;
            } else {
                var2 = 0;
            }
            var3.setActionId(var2 + 55);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 28 + 73);

            muiAniDrawer.setActionId(36);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 28 - 17);
            muiAniDrawer.setActionId(70);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 28 + 13);
            muiAniDrawer.setActionId(69);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 28 + 43);
            muiAniDrawer.setActionId(68);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 28 + 73);
            var3 = muiAniDrawer;
            if (Key.touchitemsselect4_return.Isin() || Key.repeat(524288 | 8388608)) {
                var2 = 5;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

    }

    public void itemsSelect4Init() {
        Key.touchitemsselect4Init();
        this.itemsselectcursor = 0;
        this.isItemsSelect = false;
        fadeInit(0, 102);
        if (muiAniDrawer == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/mui");
            muiAniDrawer = (new Animation(var1.toString())).getDrawer(0, false, 0);
        }

    }

    public int itemsSelect4Logic() {
        if (Key.touchitemsselect4_return.Isin() && Key.touchitemsselect4.IsClick()) {
            this.itemsselectcursor = 2;
        }

        if (Key.touchitemsselect4_1.Isin() && Key.touchitemsselect4.IsClick()) {
            this.itemsselectcursor = 0;
        }

        if (Key.touchitemsselect4_2.Isin() && Key.touchitemsselect4.IsClick()) {
            this.itemsselectcursor = 1;
        }

        if (Key.touchitemsselect4_3.Isin() && Key.touchitemsselect4.IsClick()) {
            this.itemsselectcursor = 3;
        }
        
        if (Key.touchitemsselect4_4.Isin() && Key.touchitemsselect4.IsClick()) {
            this.itemsselectcursor = 4;
        }

        byte var1;
        if (this.isItemsSelect) {
            this.itemsselectframe = GameTime.advance(this, "itemsselectframe", this.itemsselectframe, 1);
            if (this.itemsselectframe > 8) {
                fadeInit(220, 102);
                if (this.finalitemsselectcursor == 0) {
                    var1 = 1;
                    return var1;
                } else {
                    if (this.finalitemsselectcursor == 1) {
                        var1 = 2;
                        return var1;
                    } else {
                        if (this.finalitemsselectcursor == 2) {
                            var1 = 4;
                        } else {
                            var1 = 5;
                        }

                        return var1;
                    }
                }
            }
        }

        if (Key.touchitemsselect4_1.IsButtonPress() && this.itemsselectcursor == 0 && !this.isItemsSelect) {
            this.isItemsSelect = true;
            this.itemsselectframe = GameTime.set(this, "itemsselectframe", 0);
            this.finalitemsselectcursor = 0;
            SoundSystem.getInstance().playSe(1);
            var1 = 0;
        } else if (Key.touchitemsselect4_2.IsButtonPress() && this.itemsselectcursor == 1 && !this.isItemsSelect) {
            this.isItemsSelect = true;
            this.itemsselectframe = GameTime.set(this, "itemsselectframe", 0);
            this.finalitemsselectcursor = 1;
            SoundSystem.getInstance().playSe(1);
            var1 = 0;
        } else if (Key.touchitemsselect4_3.IsButtonPress() && this.itemsselectcursor == 3 && !this.isItemsSelect) {
            this.isItemsSelect = true;
            this.itemsselectframe = GameTime.set(this, "itemsselectframe", 0);
            this.finalitemsselectcursor = 2;
            SoundSystem.getInstance().playSe(1);
            var1 = 0;
        } else if (Key.touchitemsselect4_4.IsButtonPress() && this.itemsselectcursor == 4 && !this.isItemsSelect) {
            this.isItemsSelect = true;
            this.itemsselectframe = GameTime.set(this, "itemsselectframe", 0);
            this.finalitemsselectcursor = 3;
            SoundSystem.getInstance().playSe(1);
            var1 = 0;
        } else if (Key.buttonPress(524288 | 8388608) && fadeChangeOver() || Key.touchitemsselect4_return.IsButtonPress() && this.itemsselectcursor == 2  && fadeChangeOver()) {
            SoundSystem.getInstance().playSe(2);
            var1 = 3;
        } else {
            var1 = 0;
        }

        return var1;
    }

    public abstract void logic();

    public void menuBgDraw(MFGraphics var1) {
    }

    public void menuInit(int var1) {
        this.cursor = 0;
        this.mainMenuItemCursor = 0;
        this.menuMoving = false;
        this.elementNum = var1;
        this.selectMenuOffsetX = 0;
        this.cursor = this.returnCursor;
        this.mainMenuItemCursor = this.returnCursor;
    }

    public void menuInit(int var1, int var2) {
        this.menuInit(var1);
        this.cursor = var2;
        this.mainMenuItemCursor = var2;
    }

    public void menuInit(int[] var1) {
        this.currentElement = var1;
        this.cursor = 0;
        this.mainMenuItemCursor = 0;
        this.menuMoving = true;
        this.elementNum = this.currentElement.length;
        this.selectMenuOffsetX = 0;
        this.cursor = this.returnCursor;
        this.mainMenuItemCursor = this.returnCursor;
    }

    public int menuLogic() {
        Key.touchMenuInit();
        int var1;
        if (Key.touchmenunew.Isin() && this.cursor == 1) {
            this.cursor = 0;
            Key.touchmenunew.reset();
            var1 = -1;
        } else if (Key.touchmenucon.Isin() && this.cursor == 0) {
            this.cursor = 1;
            Key.touchmenucon.reset();
            var1 = -1;
        } else if (Key.touchmenunew.Isin() && this.cursor == 0 || Key.touchmenucon.Isin() && this.cursor == 1) {
            var1 = this.cursor;
        } else if (this.menuMoving) {
            var1 = -1;
        } else {
            if (Key.press(4)) {
                --this.cursor;
                this.cursor = (this.cursor + this.elementNum) % this.elementNum;
                this.selectMenuOffsetX = 0;
                this.changeUpSelect();
            } else if (Key.press(8)) {
                ++this.cursor;
                this.cursor = (this.cursor + this.elementNum) % this.elementNum;
                this.selectMenuOffsetX = 0;
                this.changeDownSelect();
            } else {
                if (Key.press(Key.gSelect | Key.B_S1)) {
                    this.cursor = (this.cursor + this.elementNum) % this.elementNum;
                    Key.touchMenuClose();
                    var1 = this.cursor;
                    return var1;
                }

                if (Key.press(2)) {
                }

                if (Key.press(524288)) {
                    Key.touchMenuClose();
                    var1 = 400;
                    return var1;
                }
            }

            var1 = -1;
        }

        return var1;
    }

    public void moregameDraw(MFGraphics var1) {
        this.menuBgDraw(var1);
        this.drawMenuTitle(var1, 3, 0);
        int var2 = this.MORE_GAME_START_X;
        int var3 = this.MORE_GAME_START_Y;
        int var5 = this.MORE_GAME_WIDTH;
        int var4 = this.MORE_GAME_HEIGHT;
        fillMenuRect(var1, var2, var3, var5, var4);
        MyAPI.drawBoldString(var1, "是否退出游戏", SCREEN_WIDTH >> 1, this.MORE_GAME_START_Y + 10, 17, 16777215, 0);
        var2 = SCREEN_WIDTH;
        var3 = this.MORE_GAME_START_Y;
        MyAPI.drawBoldString(var1, "并连接网络", var2 >> 1, MENU_SPACE + var3 + 10, 17, 16777215, 0);
        drawMenuFontById(var1, 101, this.cursor * 40 + COMFIRM_X - 20, this.MORE_GAME_START_Y + 10 + MENU_SPACE * 3 + FONT_H_HALF);
        drawMenuFontById(var1, 10, COMFIRM_X, this.MORE_GAME_START_Y + 10 + MENU_SPACE * 3 + FONT_H_HALF);
    }

    public void newLanguageDraw(MFGraphics var1) {
        if (muiAniDrawer == null || langAniDrawer == null) {
            StringBuilder var4 = new StringBuilder("/lang");
            var4.append(GlobalResource.languageConfig);
            var4.append("/mui");
            muiAniDrawer = (new Animation(var4.toString())).getDrawer(0, false, 0);
            langAniDrawer = new Animation("/animation/language").getDrawer();
        } else {
            muiAniDrawer.setActionId(54);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 20);
            AnimationDrawer var3 = muiAniDrawer;
            int var2;
            if (Key.touchsecondensureyes.Isin() && this.confirmcursor == 0) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 59);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 40, (SCREEN_HEIGHT >> 1) + 40);
            var3 = muiAniDrawer;
            if (Key.touchsecondensureno.Isin() && this.confirmcursor == 1) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 59);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 40, (SCREEN_HEIGHT >> 1) + 40);
            muiAniDrawer.setActionId(89);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 40, (SCREEN_HEIGHT >> 1) + 40);
            muiAniDrawer.setActionId(90);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 40, (SCREEN_HEIGHT >> 1) + 40);
            muiAniDrawer.setActionId(71);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 20);
            muiAniDrawer.setActionId(72);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 33 + (GlobalResource.languageConfig) * 8, (SCREEN_HEIGHT >> 1) - 3);
            langAniDrawer.setActionId(GlobalResource.languageConfig);
            langAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 20);
            var3 = muiAniDrawer;
            byte var5;
            if (Key.touchsecondensurereturn.Isin() || Key.repeat(524288 | 8388608)) {
                var5 = 5;
            } else {
                var5 = 0;
            }

            var3.setActionId(var5 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

    }

    public void newLanguageInit() {
        Key.touchSecondEnsureClose();
        Key.touchSecondEnsureInit();
        this.timeCnt = GameTime.set(this, "timeCnt", 0);
        this.isNewLanguageClick = false;
        fadeInit(0, 192);
        if (muiAniDrawer == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/mui");
            muiAniDrawer = (new Animation(var1.toString())).getDrawer(0, false, 0);
        }

    }

    public int newLanguageLogic() {
        if (Key.touchsecondensurereturn.Isin() && Key.touchsecondensure.IsClick()) {
            this.confirmcursor = 2;
        }

        if (Key.touchsecondensureyes.Isin() && Key.touchsecondensure.IsClick()) {
            this.confirmcursor = 0;
        }

        if (Key.touchsecondensureno.Isin() && Key.touchsecondensure.IsClick()) {
            this.confirmcursor = 1;
        }

        if (Key.touchsecondensureyes.Isin()) {
            this.timeCnt = GameTime.advance(this, "timeCnt", this.timeCnt, 1);
            if (this.timeCnt <= 4 && !this.isNewLanguageClick) {
                if (GlobalResource.languageConfig < 0) {
                    GlobalResource.languageConfig = 8;
                } else {
                    --GlobalResource.languageConfig;
                }

                if (GlobalResource.soundConfig == 0) {
                    GlobalResource.seConfig = 0;
                }

                SoundSystem.getInstance().setSoundState(GlobalResource.soundConfig);
                SoundSystem.getInstance().setSeState(GlobalResource.seConfig);
                this.isNewLanguageClick = true;
            } else if (this.timeCnt > 4 && GameTime.periodic(this, "timeCnt", "newLanguageLogic:1890", this.timeCnt, 2, 0)) {
                if (GlobalResource.languageConfig < 0) {
                    GlobalResource.languageConfig = 8;
                } else {
                    --GlobalResource.languageConfig;
                }

                if (GlobalResource.soundConfig == 0) {
                    GlobalResource.seConfig = 0;
                }

                SoundSystem.getInstance().setSoundState(GlobalResource.soundConfig);
                SoundSystem.getInstance().setSeState(GlobalResource.seConfig);
            }
        } else if (Key.touchsecondensureno.Isin()) {
            this.timeCnt = GameTime.advance(this, "timeCnt", this.timeCnt, 1);
            if (this.timeCnt <= 4 && !this.isNewLanguageClick) {
                if (GlobalResource.languageConfig > 8) {
                    GlobalResource.languageConfig = 0;
                } else {
                    ++GlobalResource.languageConfig;
                }

                if (GlobalResource.soundConfig > 0) {
                    GlobalResource.seConfig = 1;
                }

                this.isNewLanguageClick = true;
                SoundSystem.getInstance().setSoundState(GlobalResource.soundConfig);
                SoundSystem.getInstance().setSeState(GlobalResource.seConfig);
                if (GlobalResource.languageConfig == 1) {
                    SoundSystem.getInstance().resumeBgm();
                }
            } else if (this.timeCnt > 4 && GameTime.periodic(this, "timeCnt", "newLanguageLogic:1923", this.timeCnt, 2, 0)) {
                if (GlobalResource.languageConfig > 8) {
                    GlobalResource.languageConfig = 0;
                } else {
                    ++GlobalResource.languageConfig;
                }

                if (GlobalResource.soundConfig > 0) {
                    GlobalResource.seConfig = 1;
                }

                SoundSystem.getInstance().setSoundState(GlobalResource.soundConfig);
                SoundSystem.getInstance().setSeState(GlobalResource.seConfig);
                if (GlobalResource.soundConfig == 1) {
                    SoundSystem.getInstance().resumeBgm();
                }
            }
        } else {
            this.timeCnt = GameTime.set(this, "timeCnt", 0);
            this.isNewLanguageClick = false;
        }

        if (GlobalResource.languageConfig > 8) {
            GlobalResource.languageConfig = 0;
        } else if (GlobalResource.languageConfig < 0) {
            GlobalResource.languageConfig = 8;
        }

        if (GlobalResource.soundConfig > 0) {
            GlobalResource.seConfig = 1;
        }

        byte var1;
        if ((Key.buttonPress(524288 | 8388608) || Key.touchsecondensurereturn.IsButtonPress()) && fadeChangeOver()) {
            SoundSystem.getInstance().playSe(1);
            var1 = 2;
        } else {
            var1 = 0;
        }

        return var1;
    }

    public abstract void pause();

    public void pauseoptionDraw(MFGraphics var1) {
        this.drawMenuTitle(var1, 5, 0);
        drawMenuFontById(var1, 54, (SCREEN_WIDTH >> 1) - 32, SCREEN_HEIGHT >> 1);
        drawMenuFontById(var1, 113, (SCREEN_WIDTH >> 1) - 76, SCREEN_HEIGHT >> 1);
        drawMenuFontById(var1, OPTION_SOUND[this.pauseoptionCursor] + 55, (SCREEN_WIDTH >> 1) + 32, SCREEN_HEIGHT >> 1);
    }

    public void pauseoptionLogic() {
        if (Key.press(Key.gSelect)) {
            ++this.pauseoptionCursor;
            this.pauseoptionCursor += 4;
            this.pauseoptionCursor %= 4;
        }

    }

    public static void resetTouchPosition() {
        if (GlobalResource.touchKeyBoardLeftPosition > (SCREEN_WIDTH >> 1) - 120 + (50 - (GlobalResource.touchKeyBoardSize * 10))) {
            GlobalResource.touchKeyBoardLeftPosition = (SCREEN_WIDTH >> 1) - 120 + (50 - (GlobalResource.touchKeyBoardSize * 10));
        }

        if (GlobalResource.touchKeyBoardRightPosition > (SCREEN_WIDTH >> 1) - 120 + (40 - (GlobalResource.touchKeyBoardSize * 8))) {
            GlobalResource.touchKeyBoardRightPosition = (SCREEN_WIDTH >> 1) - 120 + (40 - (GlobalResource.touchKeyBoardSize * 8));
        }

    }

    public int secondEnsureDirectLogic() {
        if (Key.touchsecondensurereturn.Isin() && Key.touchsecondensure.IsClick()) {
            this.confirmcursor = 2;
        }

        if (Key.touchsecondensureyes.Isin() && Key.touchsecondensure.IsClick()) {
            this.confirmcursor = 0;
        }

        if (Key.touchsecondensureno.Isin() && Key.touchsecondensure.IsClick()) {
            this.confirmcursor = 1;
        }

        byte var1;
        if (Key.touchsecondensureyes.IsButtonPress() && this.confirmcursor == 0) {
            this.isConfirm = true;
            this.confirmframe = GameTime.set(this, "confirmframe", 0);
            SoundSystem.getInstance().playSe(1);
            var1 = 1;
        } else if (Key.touchsecondensureno.IsButtonPress() && this.confirmcursor == 1) {
            SoundSystem.getInstance().playSe(2);
            var1 = 2;
        } else if ((Key.buttonPress(524288 | 8388608) || Key.touchsecondensurereturn.IsButtonPress()) && fadeChangeOver()) {
            SoundSystem.getInstance().playSe(2);
            var1 = 2;
        } else {
            var1 = 0;
        }

        return var1;
    }

    public void secondEnsureInit() {
        this.isConfirm = false;
        this.confirmframe = GameTime.set(this, "confirmframe", 0);
        if (muiAniDrawer == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/mui");
            muiAniDrawer = (new Animation(var1.toString())).getDrawer(0, false, 0);
        }

        Key.touchSecondEnsureClose();
        Key.touchSecondEnsureInit();
    }

    public void secondEnsureInit2() {
        this.isConfirm = false;
        this.confirmframe = GameTime.set(this, "confirmframe", 0);
        if (muiAniDrawer == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/mui");
            muiAniDrawer = (new Animation(var1.toString())).getDrawer(0, false, 0);
        }

        Key.touchSecondEnsureClose();
        Key.touchSecondEnsureInit();
    }

    public int secondEnsureLogic() {
        if (Key.touchsecondensurereturn.Isin() && Key.touchsecondensure.IsClick()) {
            this.confirmcursor = 2;
        }

        if (Key.touchsecondensureyes.Isin() && Key.touchsecondensure.IsClick()) {
            this.confirmcursor = 0;
        }

        if (Key.touchsecondensureno.Isin() && Key.touchsecondensure.IsClick()) {
            this.confirmcursor = 1;
        }

        byte var1;
        if (this.isConfirm) {
            this.confirmframe = GameTime.advance(this, "confirmframe", this.confirmframe, 1);
            if (this.confirmframe > 8) {
                var1 = 1;
                return var1;
            }
        }

        if (Key.touchsecondensureyes.IsButtonPress() && this.confirmcursor == 0 && !this.isConfirm) {
            this.isConfirm = true;
            this.confirmframe = GameTime.set(this, "confirmframe", 0);
            SoundSystem.getInstance().playSe(1);
            var1 = 0;
        } else if (Key.touchsecondensureno.IsButtonPress() && this.confirmcursor == 1 && !this.isConfirm) {
            SoundSystem.getInstance().playSe(2);
            var1 = 2;
        } else if ((Key.buttonPress(524288 | 8388608) || Key.touchsecondensurereturn.IsButtonPress()) && fadeChangeOver() && !this.isConfirm) {
            SoundSystem.getInstance().playSe(2);
            var1 = 2;
        } else {
            var1 = 0;
        }

        return var1;
    }

    private void updateTouchPadPreview() {
        resetTouchPosition();
        initTouchkeyBoard();
        if (previewSize != GlobalResource.touchKeyBoardSize || previewLeft != GlobalResource.touchKeyBoardLeftPosition
                || previewRight != GlobalResource.touchKeyBoardRightPosition || Key.touchdirectgamekey == null) {
            Key.touchkeyboardClose();
            Key.touchkeyboardInit();
            previewSize = GlobalResource.touchKeyBoardSize;
            previewLeft = GlobalResource.touchKeyBoardLeftPosition;
            previewRight = GlobalResource.touchKeyBoardRightPosition;
        }
    }

    public void touchPadInit() {
        Key.touchPadOptionClose();
        Key.touchPadOptionInit();
        previewSize = -1;
        updateTouchPadPreview();
        this.timeCnt = GameTime.set(this, "timeCnt", 0);
        this.isTouchPadClick = false;
        fadeInit(0, 192);
    }

    public void touchPadOpacityDraw(MFGraphics var1) {
        if (muiAniDrawer == null) {
            StringBuilder var4 = new StringBuilder("/lang");
            var4.append(GlobalResource.languageConfig);
            var4.append("/mui");
            muiAniDrawer = (new Animation(var4.toString())).getDrawer(0, false, 0);
        } else {
            muiAniDrawer.setActionId(54);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 20);
            muiAniDrawer.setActionId(98);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 60, (SCREEN_HEIGHT >> 1) - 30);
            AnimationDrawer var3 = muiAniDrawer;
            int var2;
            if (Key.touchpadoptionplus.Isin() && this.confirmcursor == 0) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 59);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT - 48);
            var3 = muiAniDrawer;
            if (Key.touchpadoptionminus.Isin() && this.confirmcursor == 1) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 59);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT - 20);
            muiAniDrawer.setActionId(78);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT - 48);
            muiAniDrawer.setActionId(79);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT - 20);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardOpacity, (SCREEN_WIDTH >> 1) - 10, (SCREEN_HEIGHT >> 1) - 12, 2);
            muiAniDrawer.setActionId(95);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 5, (SCREEN_HEIGHT >> 1) - 13);
            NumberDrawer.drawNum(var1, 0, 3, (SCREEN_WIDTH >> 1) + 9, (SCREEN_HEIGHT >> 1) - 12, 2);
            var3 = muiAniDrawer;
            byte var5;
            if (Key.touchpadoptionreturn.Isin() || Key.repeat(524288 | 8388608)) {
                var5 = 5;
            } else {
                var5 = 0;
            }

            var3.setActionId(var5 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
            this.drawTouchKeyDirect(var1);
        }

    }

    public int touchPadOpacityLogic() {
        if (Key.touchpadoptionreturn.Isin() && Key.touchpadoption.IsClick()) {
            this.confirmcursor = 2;
        }

        if (Key.touchpadoptionplus.Isin() && Key.touchpadoption.IsClick()) {
            this.confirmcursor = 0;
        }

        if (Key.touchpadoptionminus.Isin() && Key.touchpadoption.IsClick()) {
            this.confirmcursor = 1;
        }

        if (Key.touchpadoptionplus.Isin()) {
            this.timeCnt = GameTime.advance(this, "timeCnt", this.timeCnt, 1);
            if (this.timeCnt <= 4 && !this.isTouchPadClick) {
                if (GlobalResource.touchKeyBoardOpacity > 3) {
                    GlobalResource.touchKeyBoardOpacity = 0;
                } else {
                    ++GlobalResource.touchKeyBoardOpacity;
                }

                this.isTouchPadClick = true;
            } else if (this.timeCnt > 4 && GameTime.periodic(this, "timeCnt", "touchPadOpacityLogic:2182", this.timeCnt, 2, 0)) {
                if (GlobalResource.touchKeyBoardOpacity > 3) {
                    GlobalResource.touchKeyBoardOpacity = 0;
                } else {
                    ++GlobalResource.touchKeyBoardOpacity;
                }
            }
        } else if (Key.touchpadoptionminus.Isin()) {
            this.timeCnt = GameTime.advance(this, "timeCnt", this.timeCnt, 1);
            if (this.timeCnt <= 4 && !this.isTouchPadClick) {
                if (GlobalResource.touchKeyBoardOpacity < 0) {
                    GlobalResource.touchKeyBoardOpacity = 3;
                } else {
                    --GlobalResource.touchKeyBoardOpacity;
                }

                this.isTouchPadClick = true;
            } else if (this.timeCnt > 4 && GameTime.periodic(this, "timeCnt", "touchPadOpacityLogic:2199", this.timeCnt, 2, 0)) {
                if (GlobalResource.touchKeyBoardOpacity < 0) {
                    GlobalResource.touchKeyBoardOpacity = 3;
                } else {
                    --GlobalResource.touchKeyBoardOpacity;
                }
            }
        } else {
            this.timeCnt = GameTime.set(this, "timeCnt", 0);
            this.isTouchPadClick = false;
        }

        if (GlobalResource.touchKeyBoardOpacity > 3) {
            GlobalResource.touchKeyBoardOpacity = 0;
        } else if (GlobalResource.touchKeyBoardOpacity < 0) {
            GlobalResource.touchKeyBoardOpacity = 3;
        }

        updateTouchPadPreview();

        byte var1;
        if ((Key.buttonPress(524288 | 8388608) || Key.touchpadoptionreturn.IsButtonPress()) && fadeChangeOver()) {
            SoundSystem.getInstance().playSe(2);
            var1 = 2;
        } else {
            var1 = 0;
        }

        return var1;
    }

    public void touchPadPositionDraw(MFGraphics var1) {
        if (muiAniDrawer == null) {
            StringBuilder var4 = new StringBuilder("/lang");
            var4.append(GlobalResource.languageConfig);
            var4.append("/mui");
            muiAniDrawer = (new Animation(var4.toString())).getDrawer(0, false, 0);
        } else {
            muiAniDrawer.setActionId(59);
            muiAniDrawer.draw(var1, 62, 48);
            muiAniDrawer.draw(var1, SCREEN_WIDTH - 62, 48);
            AnimationDrawer var3 = muiAniDrawer;
            int var2;
            if (Key.touchpadoptionleft1.Isin() && this.confirmcursor == 0) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 80);
            muiAniDrawer.draw(var1, 34, SCREEN_HEIGHT - 78);
            var3 = muiAniDrawer;
            if (Key.touchpadoptionright1.Isin() && this.confirmcursor == 1) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 80);
            muiAniDrawer.draw(var1, 90, SCREEN_HEIGHT - 78);
            if (Key.touchpadoptionleft2.Isin() && this.confirmcursor == 3) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 80);
            muiAniDrawer.draw(var1, SCREEN_WIDTH - 90, SCREEN_HEIGHT - 78);
            var3 = muiAniDrawer;
            if (Key.touchpadoptionright2.Isin() && this.confirmcursor == 4) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 80);
            muiAniDrawer.draw(var1, SCREEN_WIDTH - 34, SCREEN_HEIGHT - 78);
            muiAniDrawer.setActionId(89);
            muiAniDrawer.draw(var1, 34, SCREEN_HEIGHT - 80);
            muiAniDrawer.draw(var1, SCREEN_WIDTH - 90, SCREEN_HEIGHT - 80);
            muiAniDrawer.setActionId(90);
            muiAniDrawer.draw(var1, 90, SCREEN_HEIGHT - 80);
            muiAniDrawer.draw(var1, SCREEN_WIDTH - 34, SCREEN_HEIGHT - 80);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardLeftPosition, 62, 42, 2);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardRightPosition, SCREEN_WIDTH - 62, 42, 2);
            var3 = muiAniDrawer;
            byte var5;
            if (Key.touchpadoptionreturn.Isin() || Key.repeat(524288 | 8388608)) {
                var5 = 5;
            } else {
                var5 = 0;
            }

            var3.setActionId(var5 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
            this.drawTouchKeyDirect(var1);
        }

    }

    public int touchPadPositionLogic() {
        if (Key.touchpadoptionreturn.Isin() && Key.touchpadoption.IsClick()) {
            this.confirmcursor = 2;
        }

        if (Key.touchpadoptionleft1.Isin() && Key.touchpadoption.IsClick()) {
            this.confirmcursor = 0;
        }

        if (Key.touchpadoptionright1.Isin() && Key.touchpadoption.IsClick()) {
            this.confirmcursor = 1;
        }

        if (Key.touchpadoptionleft2.Isin() && Key.touchpadoption.IsClick()) {
            this.confirmcursor = 3;
        }

        if (Key.touchpadoptionright2.Isin() && Key.touchpadoption.IsClick()) {
            this.confirmcursor = 4;
        }

        if (Key.touchpadoptionleft1.Isin()) {
            this.timeCnt = GameTime.advance(this, "timeCnt", this.timeCnt, 1);
            if (this.timeCnt <= 4 && !this.isTouchPadClick) {
                if (GlobalResource.touchKeyBoardLeftPosition < 0) {
                    GlobalResource.touchKeyBoardLeftPosition = (SCREEN_WIDTH >> 1) - 120 + (50 - (GlobalResource.touchKeyBoardSize * 10));
                } else {
                    --GlobalResource.touchKeyBoardLeftPosition;
                }

                this.isTouchPadClick = true;
            } else if (this.timeCnt > 4 && GameTime.periodic(this, "timeCnt", "touchPadPositionLogic:2332", this.timeCnt, 2, 0)) {
                if (GlobalResource.touchKeyBoardLeftPosition < 0) {
                    GlobalResource.touchKeyBoardLeftPosition = (SCREEN_WIDTH >> 1) - 120 + (50 - (GlobalResource.touchKeyBoardSize * 10));
                } else {
                    --GlobalResource.touchKeyBoardLeftPosition;
                }
            }

        } else if (Key.touchpadoptionright1.Isin()) {
            this.timeCnt = GameTime.advance(this, "timeCnt", this.timeCnt, 1);
            if (this.timeCnt <= 4 && !this.isTouchPadClick) {
                if (GlobalResource.touchKeyBoardLeftPosition > (SCREEN_WIDTH >> 1) - 120 + (50 - (GlobalResource.touchKeyBoardSize * 10))) {
                    GlobalResource.touchKeyBoardLeftPosition = 0;
                } else {
                    ++GlobalResource.touchKeyBoardLeftPosition;
                }

                this.isTouchPadClick = true;
            } else if (this.timeCnt > 4 && GameTime.periodic(this, "timeCnt", "touchPadPositionLogic:2350", this.timeCnt, 2, 0)) {
                if (GlobalResource.touchKeyBoardLeftPosition > (SCREEN_WIDTH >> 1) - 120 + (50 - (GlobalResource.touchKeyBoardSize * 10))) {
                    GlobalResource.touchKeyBoardLeftPosition = 0;
                } else {
                    ++GlobalResource.touchKeyBoardLeftPosition;
                }
            }
        } else if (Key.touchpadoptionleft2.Isin()) {
            this.timeCnt = GameTime.advance(this, "timeCnt", this.timeCnt, 1);
            if (this.timeCnt <= 4 && !this.isTouchPadClick) {
                if (GlobalResource.touchKeyBoardRightPosition > (SCREEN_WIDTH >> 1) - 120 + (40 - (GlobalResource.touchKeyBoardSize * 8))) {
                    GlobalResource.touchKeyBoardRightPosition = 0;
                } else {
                    ++GlobalResource.touchKeyBoardRightPosition;
                }

                this.isTouchPadClick = true;
            } else if (this.timeCnt > 4 && GameTime.periodic(this, "timeCnt", "touchPadPositionLogic:2367", this.timeCnt, 2, 0)) {
                if (GlobalResource.touchKeyBoardRightPosition > (SCREEN_WIDTH >> 1) - 120 + (40 - (GlobalResource.touchKeyBoardSize * 8))) {
                    GlobalResource.touchKeyBoardRightPosition = 0;
                } else {
                    ++GlobalResource.touchKeyBoardRightPosition;
                }
            }
        } else if (Key.touchpadoptionright2.Isin()) {
            this.timeCnt = GameTime.advance(this, "timeCnt", this.timeCnt, 1);
            if (this.timeCnt <= 4 && !this.isTouchPadClick) {
                if (GlobalResource.touchKeyBoardRightPosition < 0) {
                    GlobalResource.touchKeyBoardRightPosition = (SCREEN_WIDTH >> 1) - 120 + (40 - (GlobalResource.touchKeyBoardSize * 8));
                } else {
                    --GlobalResource.touchKeyBoardRightPosition;
                }

                this.isTouchPadClick = true;
            } else if (this.timeCnt > 4 && GameTime.periodic(this, "timeCnt", "touchPadPositionLogic:2384", this.timeCnt, 2, 0)) {
                if (GlobalResource.touchKeyBoardRightPosition < 0) {
                    GlobalResource.touchKeyBoardRightPosition = (SCREEN_WIDTH >> 1) - 120 + (40 - (GlobalResource.touchKeyBoardSize * 8));
                } else {
                    --GlobalResource.touchKeyBoardRightPosition;
                }
            }
        } else {
            this.timeCnt = GameTime.set(this, "timeCnt", 0);
            this.isTouchPadClick = false;
        }

        if (GlobalResource.touchKeyBoardLeftPosition > (SCREEN_WIDTH >> 1) - 120 + (50 - (GlobalResource.touchKeyBoardSize * 10))) {
            GlobalResource.touchKeyBoardLeftPosition = 0;
        } else if (GlobalResource.touchKeyBoardLeftPosition < 0) {
            GlobalResource.touchKeyBoardLeftPosition = (SCREEN_WIDTH >> 1) - 120 + (50 - (GlobalResource.touchKeyBoardSize * 10));
        }

        if (GlobalResource.touchKeyBoardRightPosition > (SCREEN_WIDTH >> 1) - 120 + (40 - (GlobalResource.touchKeyBoardSize * 8))) {
            GlobalResource.touchKeyBoardRightPosition = 0;
        } else if (GlobalResource.touchKeyBoardRightPosition < 0) {
            GlobalResource.touchKeyBoardRightPosition = (SCREEN_WIDTH >> 1) - 120 + (40 - (GlobalResource.touchKeyBoardSize * 8));
        }

        updateTouchPadPreview();

        byte var1;
        if ((Key.buttonPress(524288 | 8388608) || Key.touchpadoptionreturn.IsButtonPress()) && fadeChangeOver()) {
            SoundSystem.getInstance().playSe(2);
            var1 = 2;
        } else {
            var1 = 0;
        }

        return var1;
    }

    public void touchPadSizeDraw(MFGraphics var1) {
        if (muiAniDrawer == null) {
            StringBuilder var4 = new StringBuilder("/lang");
            var4.append(GlobalResource.languageConfig);
            var4.append("/mui");
            muiAniDrawer = (new Animation(var4.toString())).getDrawer(0, false, 0);
        } else {
            muiAniDrawer.setActionId(54);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 20);
            muiAniDrawer.setActionId(97);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 60, (SCREEN_HEIGHT >> 1) - 30);
            AnimationDrawer var3 = muiAniDrawer;
            int var2;
            if (Key.touchpadoptionplus.Isin() && this.confirmcursor == 0) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 59);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT - 48);
            var3 = muiAniDrawer;
            if (Key.touchpadoptionminus.Isin() && this.confirmcursor == 1) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 59);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT - 20);
            muiAniDrawer.setActionId(78);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT - 48);
            muiAniDrawer.setActionId(79);
            muiAniDrawer.draw(var1, SCREEN_WIDTH >> 1, SCREEN_HEIGHT - 20);
            NumberDrawer.drawNum(var1, 0, GlobalResource.touchKeyBoardSize, (SCREEN_WIDTH >> 1) - 10, (SCREEN_HEIGHT >> 1) - 12, 2);
            muiAniDrawer.setActionId(95);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 5, (SCREEN_HEIGHT >> 1) - 13);
            NumberDrawer.drawNum(var1, 0, 5, (SCREEN_WIDTH >> 1) + 9, (SCREEN_HEIGHT >> 1) - 12, 2);
            var3 = muiAniDrawer;
            byte var5;
            if (Key.touchpadoptionreturn.Isin() || Key.repeat(524288 | 8388608)) {
                var5 = 5;
            } else {
                var5 = 0;
            }

            var3.setActionId(var5 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
            this.drawTouchKeyDirect(var1);
        }

    }

    public int touchPadSizeLogic() {
        if (Key.touchpadoptionreturn.Isin() && Key.touchpadoption.IsClick()) {
            this.confirmcursor = 2;
        }

        if (Key.touchpadoptionplus.Isin() && Key.touchpadoption.IsClick()) {
            this.confirmcursor = 0;
        }

        if (Key.touchpadoptionminus.Isin() && Key.touchpadoption.IsClick()) {
            this.confirmcursor = 1;
        }

        if (Key.touchpadoptionplus.Isin()) {
            this.timeCnt = GameTime.advance(this, "timeCnt", this.timeCnt, 1);
            if (this.timeCnt <= 4 && !this.isTouchPadClick) {
                if (GlobalResource.touchKeyBoardSize > 5) {
                    GlobalResource.touchKeyBoardSize = 0;
                } else {
                    ++GlobalResource.touchKeyBoardSize;
                }

                this.isTouchPadClick = true;
            } else if (this.timeCnt > 4 && GameTime.periodic(this, "timeCnt", "touchPadSizeLogic:2500", this.timeCnt, 2, 0)) {
                if (GlobalResource.touchKeyBoardSize > 5) {
                    GlobalResource.touchKeyBoardSize = 0;
                } else {
                    ++GlobalResource.touchKeyBoardSize;
                }
            }
        } else if (Key.touchpadoptionminus.Isin()) {
            this.timeCnt = GameTime.advance(this, "timeCnt", this.timeCnt, 1);
            if (this.timeCnt <= 4 && !this.isTouchPadClick) {
                if (GlobalResource.touchKeyBoardSize < 0) {
                    GlobalResource.touchKeyBoardSize = 5;
                } else {
                    --GlobalResource.touchKeyBoardSize;
                }

                this.isTouchPadClick = true;
            } else if (this.timeCnt > 4 && GameTime.periodic(this, "timeCnt", "touchPadSizeLogic:2517", this.timeCnt, 2, 0)) {
                if (GlobalResource.touchKeyBoardSize < 0) {
                    GlobalResource.touchKeyBoardSize = 5;
                } else {
                    --GlobalResource.touchKeyBoardSize;
                }
            }
        } else {
            this.timeCnt = GameTime.set(this, "timeCnt", 0);
            this.isTouchPadClick = false;
        }

        if (GlobalResource.touchKeyBoardSize > 5) {
            GlobalResource.touchKeyBoardSize = 0;
        } else if (GlobalResource.touchKeyBoardSize < 0) {
            GlobalResource.touchKeyBoardSize = 5;
        }

        updateTouchPadPreview();

        byte var1;
        if ((Key.buttonPress(524288 | 8388608) || Key.touchpadoptionreturn.IsButtonPress()) && fadeChangeOver()) {
            SoundSystem.getInstance().playSe(2);
            var1 = 2;
        } else {
            var1 = 0;
        }

        return var1;
    }
}

