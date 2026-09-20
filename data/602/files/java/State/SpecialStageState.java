//
// Decompiled by FernFlower - 1351ms
//
package State;

import GameEngine.time.GameTime;

import Common.BarWord;
import Common.NumberDrawer;
import Common.WhiteBarDrawer;
import Ending.SpecialEnding;
import GameEngine.Def;
import GameEngine.Key;
import GameEngine.TouchKeyRange;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.MyRandom;
import Lib.Record;
import Lib.SoundSystem;
import PlatformStandard.Standard2;
import SonicGBA.GlobalResource;
import SonicGBA.PlayerObject;
import SonicGBA.StageManager;
import Special.SSDef;
import Special.SpecialMap;
import Special.SpecialObject;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import com.sega.mobile.framework.ui.MFTouchKey;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Vector;
import com.sega.mobile.framework.device.MFDevice;

public class SpecialStageState extends State implements SSDef, BarWord {
    private static final int[] BONUS_ID;
    private static final int BONUS_NUM_X;
    private static final int BONUS_X;
    private static final int BONUS_Y_ORIGINAL;
    private static final int BONUS_Y_SPACE = 18;
    private static final int BONUS_Y_START;
    private static final int EMERALD_SPACE = 32;
    private static final int EMERALD_X_ORIGINAL;
    private static final int EMERALD_X_START;
    private static final int EMERALD_Y;
    private static final boolean IS_EMERALD_DEBUG_FULL = false;
    private static final int OPTION_MOVING_INTERVAL = 100;
    private static final int OPTION_MOVING_SPEED = 4;
    private static final int PAUSE_OPTION_ITEMS_NUM = 6;
    private static final int SPEED_LIGHT_NUM_PER_FRAME = 2;
    private static final int SPEED_LIGHT_VELOCITY = 60;
    private static final int STATE_END = 3;
    private static final int STATE_GAMING = 2;
    private static final int STATE_GET_EMERALD = 4;
    private static final int STATE_INTERRUPT = 14;
    private static final int STATE_INTRO = 1;
    private static final int STATE_PAUSE = 6;
    private static final int STATE_PAUSE_OPTION = 8;
    private static final int STATE_PAUSE_OPTION_HELP = 13;
    private static final int STATE_PAUSE_OPTION_KEY_CONTROL = 11;
    private static final int STATE_PAUSE_OPTION_SENSOR = 16;
    private static final int STATE_PAUSE_OPTION_SOUND = 9;
    private static final int STATE_PAUSE_OPTION_SOUND_VOLUMN = 15;
    private static final int STATE_PAUSE_OPTION_SP_SET = 12;
    private static final int STATE_PAUSE_OPTION_VIB = 10;
    private static final int STATE_PAUSE_TO_TITLE = 7;
    private static final int STATE_READY = 0;
    private static final int STATE_WAIT_FOR_OVER = 5;
    private static final int VISIBLE_OPTION_ITEMS_NUM = 9;
    private static final int WORD_FINISH_STAGE = 1;
    private static final int WORD_GET_EMERALD = 0;
    public static MFTouchKey aButton;
    public int arrowindex = -1;
    public static MFTouchKey bButton;
    public static volatile int[] emeraldStatus = new int[7];
    private WhiteBarDrawer barDrawer;
    private int[] bonusY;
    private boolean changingState;
    private AnimationDrawer characterUpDrawer;
    private int characterY;
    private int clearScore;
    private int count;
    private int[] emeraldX;
    private SpecialEnding endingInstance;
    private boolean fadeChangeState;
    private AnimationDrawer fontDrawer;
    private AnimationDrawer interruptDrawer;
    private int interrupt_state;
    private boolean isChanged;
    private boolean isIntroBGMplay;
    private boolean isOptionChange;
    private boolean isOptionDisFlag;
    private boolean isSelectable;
    public static AnimationDrawer muiLeftArrowDrawer;
    public static AnimationDrawer muiRightArrowDrawer;
    private int nextState;
    private static AnimationDrawer numberDrawer;
    private int optionDrawOffsetBottomY;
    private int optionDrawOffsetTmpY1;
    private int optionDrawOffsetY;
    private int optionIndex;
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
    private int preVelZ;
    private MFImage readyBgImage;
    private int ringScore;
    private MFImage speedLight;
    private Vector speedLightVec;
    private int state;
    private int totalScore;
    private MFImage welcomeBgImage;

    static {
        BONUS_X = (SCREEN_WIDTH >> 1) - 79;
        BONUS_Y_START = (SCREEN_HEIGHT >> 1) - 7;
        BONUS_Y_ORIGINAL = SCREEN_HEIGHT + 40;
        BONUS_NUM_X = (SCREEN_WIDTH >> 1) + 75;
        EMERALD_Y = (SCREEN_HEIGHT >> 1) - 32;
        EMERALD_X_START = (SCREEN_WIDTH >> 1) - 96;
        EMERALD_X_ORIGINAL = SCREEN_WIDTH + 30;
        BONUS_ID = new int[]{22, 23, 24};
    }

    public SpecialStageState() {
        this.characterY = SCREEN_HEIGHT;
        this.bonusY = new int[3];
        this.emeraldX = new int[7];
        this.isOptionDisFlag = false;
        this.optionslide_getprey = -1;
        this.optionslide_gety = -1;
        this.state = 0;
        this.isIntroBGMplay = false;
        fading = false;
        this.barDrawer = WhiteBarDrawer.getInstance();
        if (GlobalResource.spsetConfig == 1) {
            int var1 = MyAPI.zoomOut(Def.SCREEN_WIDTH);
            int var2 = MyAPI.zoomOut(Def.SCREEN_HEIGHT);
            int var3 = Key.B_SEL;
            bButton = new MFTouchKey(0, 0, var1 >> 1, var2, var3);
            MFDevice.addComponent(bButton);
            var1 = MyAPI.zoomOut(Def.SCREEN_WIDTH);
            var2 = MyAPI.zoomOut(Def.SCREEN_WIDTH);
            var3 = MyAPI.zoomOut(Def.SCREEN_HEIGHT);
            aButton = new MFTouchKey(var1 >> 1, 0, var2 >> 1, var3, 0x01000000);
            MFDevice.addComponent(aButton);
        } else if (GlobalResource.spsetConfig == 0) {
            if (bButton != null) {
                MFDevice.removeComponent(bButton);
            }

            bButton = null;
            if (aButton != null) {
                MFDevice.removeComponent(aButton);
            }

            aButton = null;
            Key.touchkeyboardClose();
            Key.touchSpKeyboardInit();
        }

        Key.touchSPstageInit();
        loadData();
        if (emeraldState(StageManager.getStageID()) != 1) {
            setEmeraldState(StageManager.getStageID(), 2);
            saveData();
        }

    }

    private void BacktoGame() {
        this.state = 2;
        fadeInit(102, 0);
        SoundSystem var1 = SoundSystem.getInstance();
        SoundSystem.getInstance();
        var1.playBgm(35);
        Key.initSonic();
        SpecialObject.player.velZ = this.preVelZ;
        AnimationDrawer.setAllPause(false);
        if (Key.touchspstagepause != null) {
            Key.touchspstagepause.resetKeyState();
        }
        if (GlobalResource.spsetConfig == 0) {
            Key.touchSpKeyboardInit();
            if (bButton != null) {
                MFDevice.removeComponent(bButton);
                bButton = null;
            }

            if (aButton != null) {
                MFDevice.removeComponent(aButton);
                aButton = null;
            }
        } else {
            Key.touchkeyboardClose();
            int var2;
            int var3;
            int var4;
            if (bButton == null) {
                var4 = MyAPI.zoomOut(Def.SCREEN_WIDTH);
                var3 = MyAPI.zoomOut(Def.SCREEN_HEIGHT);
                var2 = Key.B_SEL;
                bButton = new MFTouchKey(0, 0, var4 >> 1, var3, var2);
                MFDevice.addComponent(bButton);
            }

            if (aButton == null) {
                var3 = MyAPI.zoomOut(Def.SCREEN_WIDTH);
                var4 = MyAPI.zoomOut(Def.SCREEN_WIDTH);
                var2 = MyAPI.zoomOut(Def.SCREEN_HEIGHT);
                aButton = new MFTouchKey(var3 >> 1, 0, var4 >> 1, var2, 0x01000000);
                MFDevice.addComponent(aButton);
            }
        }

    }

    private void backToGameStage() {
        State.setState(7);
    }

    public static int emeraldID(int var0) {
        return STAGE_ID_TO_SPECIAL_ID[var0];
    }

    public static boolean emeraldMissed() {
        int var0 = 0;

        boolean var1;
        while(true) {
            if (var0 >= emeraldStatus.length) {
                var1 = false;
                break;
            }

            if (emeraldStatus[var0] != 1) {
                var1 = true;
                break;
            }

            ++var0;
        }

        return var1;
    }

    public static int emeraldState(int var0) {
        return emeraldStatus[emeraldID(var0)];
    }

    public static void emptyEmeraldArray() {
        for(int var0 = 0; var0 < emeraldStatus.length; ++var0) {
            emeraldStatus[var0] = 0;
        }

        saveData();
    }

    private void interruptDraw(MFGraphics var1) {
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

    private void interruptInit() {
        fadeInitAndStart(0, 0);
        if (SpecialObject.player.velZ != 0) {
            this.preVelZ = SpecialObject.player.velZ;
        }

        SpecialObject.player.velZ = 0;
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
        Key.touchInterruptInit();
    }

    private void interruptLogic() {
        if (Key.buttonPress(16777216 | 8388608) || Key.touchinterruptreturn != null && Key.touchinterruptreturn.IsButtonPress()) {
            SoundSystem.getInstance().playSe(2);
            Key.touchInterruptClose();
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

            this.state = this.interrupt_state;
            this.interrupt_state = -1;
            Key.clear();
            isDrawTouchPad = true;
            fading = false;
            switch (this.state) {
                case 0:
                case 1:
                    fadeInit(0, 0);
                    SpecialObject.player.velZ = this.preVelZ;
                    break;
                case 2:
                    fadeInit(192, 192);
                    SpecialObject.player.velZ = this.preVelZ;
                    SoundSystem.getInstance().playBgm(35);
                case 3:
                case 5:
                case 6:
                case 13:
                case 14:
                default:
                    break;
                case 4:
                    this.endingInstance.setOverFromInterrupt();
                    break;
                case 7:
                    fadeInit(192, 192);
                    isDrawTouchPad = false;
                    break;
                case 8:
                    this.optionInit();
                    isDrawTouchPad = false;
                    break;
                case 9:
                case 10:
                case 11:
                case 12:
                case 15:
                case 16:
                    fadeInit(192, 192);
            }
        }

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

    public static void loadData() {
        byte[] var1 = Record.loadRecord("EMERALD_RECORD");
        int var0;
        if (var1 != null) {
            DataInputStream var3 = new DataInputStream(new ByteArrayInputStream(var1));
            var0 = 0;

            while(true) {
                try {
                    if (var0 >= emeraldStatus.length) {
                        break;
                    }

                    emeraldStatus[var0] = var3.readByte();
                } catch (Exception var2) {
                    for(var0 = 0; var0 < emeraldStatus.length; ++var0) {
                        emeraldStatus[var0] = 0;
                    }

                    return;
                }

                ++var0;
            }
        } else {
            for(var0 = 0; var0 < emeraldStatus.length; ++var0) {
                emeraldStatus[var0] = 0;
            }
        }

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

        if (this.state != 8) {
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
            if (Key.touchmenuoptionitems[3].Isin() && this.pauseOptionCursor == 1 && this.isSelectable) {
                var5 = 1;
            } else {
                var5 = 0;
            }

            var2 = var5 + 55;

            var4.setActionId(var2);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24);
            muiAniDrawer.setActionId(GlobalResource.spsetConfig + 37);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) + 56, this.optionDrawOffsetY + 40 + this.optionslide_y + 24);

            // GYROSCOPE LEVEL
            muiAniDrawer.setActionId(24);
            muiAniDrawer.draw(var1, (SCREEN_WIDTH >> 1) - 96, this.optionDrawOffsetY + 40 + this.optionslide_y + 48);
            var4 = muiAniDrawer;
            if (GlobalResource.spsetConfig == 0) {
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
            if (Def.SCREEN_WIDTH <= 304) {
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

        this.optionOffsetX = GameTime.advance(this, "optionOffsetX", this.optionOffsetX, -(4));
        this.optionOffsetX = GameTime.wrap(this, "optionOffsetX", this.optionOffsetX, 100);
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
        if (muiAniDrawer == null || muiLeftArrowDrawer == null || muiRightArrowDrawer == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/mui");
            muiAniDrawer = (new Animation(var1.toString())).getDrawer(0, false, 0);
            muiLeftArrowDrawer = (new Animation(var1.toString())).getDrawer(91, true, 0);
            muiRightArrowDrawer = (new Animation(var1.toString())).getDrawer(92, true, 0);
        }

        Key.touchGamePauseOptionInit();
        this.optionOffsetX = 0;
        this.optionIndex = 0;
        this.pauseOptionCursor = 0;
        this.isOptionDisFlag = false;
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

        if (Key.touchmenuoptionreturn.Isin() && Key.touchmenuoption.IsClick()) {
            this.returnCursor = 1;
        }

        this.optionslide_gety = Key.slidesensormenuoption.getPointerY();
        this.optionslide_y = 0;
        this.optionslidefirsty = 0;

        int var1;
        int var2;
        for(var1 = 0; var1 < Key.touchmenuoptionitems.length >> 1; ++var1) {
            TouchKeyRange var4 = Key.touchmenuoptionitems[var1 * 2];
            var2 = this.optionDrawOffsetY;
            int var3 = this.optionslide_y;
            var4.setStartY(var1 * 24 + 28 + var2 + var3);
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

        if ((Key.buttonPress(524288 | 8388608) || Key.touchmenuoptionreturn.IsButtonPress() && this.returnCursor == 1) && fadeChangeOver()) {
            this.changeStateWithFade(6);
            SoundSystem.getInstance().stopBgm(false);
            SoundSystem.getInstance().playSe(2);
            GlobalResource.saveSystemConfig();
        }

        if (Key.press(1073741824 | Integer.MIN_VALUE)) {
            this.changeStateWithFade(13);
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
                        this.optionDrawOffsetY = GameTime.advance(this, "optionDrawOffsetY", this.optionDrawOffsetY, var1);
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
                        this.optionDrawOffsetY = GameTime.advance(this, "optionDrawOffsetY", this.optionDrawOffsetY, var1);
                    }
                }
            }
        }

        if (this.isSelectable && this.optionYDirect == 0) {
            if (this.optionIndex == 0) {
                if (Key.touchmenuoptionitems[3].IsButtonPress() && this.pauseOptionCursor == 1 && GlobalResource.soundSwitchConfig != 0 && fadeChangeOver()) {
                    SoundSystem.getInstance().playSe(1);
                    this.isOptionDisFlag = false;
                    if (Standard2.soundtrack == 1){
                        Standard2.soundtrack = 0;
                    } else {
                        Standard2.soundtrack = 1;
                    }
                } else if (Key.touchmenuoptionitems[5].IsButtonPress() && this.pauseOptionCursor == 2 && fadeChangeOver()) {
                    this.state = 10;
                    this.itemsSelect4Init();
                    SoundSystem.getInstance().playSe(1);
                } else if (Key.touchmenuoptionitems[7].IsButtonPress() && this.pauseOptionCursor == 3 && fadeChangeOver()) {
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
                        this.state = 9;
                        this.touchPadInit();
                        SoundSystem.getInstance().playSe(1);
                    } else {
                        SoundSystem.getInstance().playSe(2);
                    }
                } else if (Key.touchmenuoptionitems[5].IsButtonPress() && this.pauseOptionCursor == 2 && fadeChangeOver()) {
                    this.state = 12;
                    this.touchPadInit();
                    SoundSystem.getInstance().playSe(1);
                } else if (Key.touchmenuoptionitems[7].IsButtonPress() && this.pauseOptionCursor == 3 && fadeChangeOver()) {
                    this.state = 15;
                    this.touchPadInit();
                    SoundSystem.getInstance().playSe(1);
                }
            } else if (this.optionIndex == 2) {
                if (Key.touchmenuoptionitems[3].IsButtonPress() && this.pauseOptionCursor == 1 && fadeChangeOver()) {
                    SoundSystem.getInstance().playSe(1);
                    if (GlobalResource.spsetConfig == 0){
                        GlobalResource.spsetConfig = 1;
                    } else {
                        GlobalResource.spsetConfig = 0;
                    }
                } else if (Key.touchmenuoptionitems[5].IsButtonPress() && this.pauseOptionCursor == 2 && fadeChangeOver()) {
                    if (GlobalResource.spsetConfig != 0) {
                        this.state = 16;
                        this.itemsSelect3Init();
                        SoundSystem.getInstance().playSe(1);
                    } else {
                        SoundSystem.getInstance().playSe(2);
                    }
                } else if (Key.touchmenuoptionitems[7].IsButtonPress() && this.pauseOptionCursor == 3 && fadeChangeOver()) {
                    if (Def.SCREEN_WIDTH > 304) {
                        SoundSystem.getInstance().playSe(1);
                        SpecialMap.starImage = null;
                        if (GlobalResource.fixedScreenConfig == 0){
                            GlobalResource.fixedScreenConfig = 1;
                            SpecialMap.starImage = MFImage.createImage("/special_res/sp_star_bg.png");
                        } else {
                            GlobalResource.fixedScreenConfig = 0;
                            SpecialMap.starImage = MFImage.createImage("/special_res/sp_star_bg1.png");
                        }
                    } else {
                        SoundSystem.getInstance().playSe(2);
                    }
                }
            }

            if (Key.touchmenuoptionitems[8].IsButtonPress() && this.pauseOptionCursor == 4 && fadeChangeOver()) {
                this.changeStateWithFade(13);
                this.helpInit();
                SoundSystem.getInstance().playSe(1);
            }    
        }
        this.optionslide_getprey = this.optionslide_gety;
    }

    private void pauseDraw(MFGraphics var1) {
        drawFade(var1);
        if (this.pausecnt > 5) {
            muiAniDrawer.setActionId(50);
            muiAniDrawer.draw(var1, this.pause_saw_x, this.pause_saw_y);
        }

        byte var2;
        AnimationDrawer var3;
        if (this.pausecnt > 7) {
            var3 = muiAniDrawer;
            if (Key.touchgamepausereturn.Isin() || Key.repeat(524288 | 8388608)) {
                var2 = 5;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 61);
            muiAniDrawer.draw(var1, 0, SCREEN_HEIGHT);
        }

        if (this.pausecnt > 5) {
            var3 = muiAniDrawer;
            if (this.pause_item_cursor == 0 && Key.touchgamepauseitem[0].Isin()) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 2);
            muiAniDrawer.draw(var1, this.pause_item_x, this.pause_item_y);
            var3 = muiAniDrawer;
            if (this.pause_item_cursor == 1 && Key.touchgamepauseitem[1].Isin()) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 10);
            muiAniDrawer.draw(var1, this.pause_item_x, this.pause_item_y + 24);
            var3 = muiAniDrawer;
            if (this.pause_item_cursor == 2 && Key.touchgamepauseitem[2].Isin()) {
                var2 = 1;
            } else {
                var2 = 0;
            }

            var3.setActionId(var2 + 12);
            muiAniDrawer.draw(var1, this.pause_item_x, this.pause_item_y + 48);
        }

    }

    private void pauseInit() {
        if (SpecialObject.player.velZ != 0) {
            this.preVelZ = SpecialObject.player.velZ;
        }

        SpecialObject.player.velZ = 0;
        this.state = 6;
        Key.touchGamePauseInit(0);
        this.pausecnt = GameTime.set(this, "pausecnt", 0);
        this.pause_saw_x = -50;
        this.pause_saw_y = 0;
        this.pause_saw_speed = 30;
        this.pause_item_x = SCREEN_WIDTH - 26;
        this.pause_item_speed = -((SCREEN_WIDTH >> 1) + 14) / 3;
        if (muiAniDrawer == null) {
            StringBuilder var1 = new StringBuilder("/lang");
            var1.append(GlobalResource.languageConfig);
            var1.append("/mui");
            muiAniDrawer = (new Animation(var1.toString())).getDrawer(0, false, 0);
        }

        this.pause_returnFlag = false;
        this.pause_item_y = (SCREEN_HEIGHT >> 1) - 36;
        fadeInit_Modify(0, 102);
        SoundSystem.getInstance().stopBgm(false);
        Key.touchgamekeyClose();
        AnimationDrawer.setAllPause(true);
    }

    private void pauseInitFromItems() {
        if (SpecialObject.player.velZ != 0) {
            this.preVelZ = SpecialObject.player.velZ;
        }

        SpecialObject.player.velZ = 0;
        this.state = 6;
        Key.touchGamePauseInit(0);
        Key.touchgamekeyClose();
        fadeInit_Modify(192, 102);
    }

    private void pauseLogic() {
        this.pausecnt = GameTime.advance(this, "pausecnt", this.pausecnt, 1);
        if (this.pausecnt >= 5 && this.pausecnt <= 7) {
            if (this.pause_saw_x + this.pause_saw_speed > 0) {
                this.pause_saw_x = 0;
            } else {
                this.pause_saw_x = GameTime.advance(this, "pause_saw_x", this.pause_saw_x, this.pause_saw_speed);
            }

            if (this.pause_item_x + this.pause_item_speed < (SCREEN_WIDTH >> 1) - 40) {
                this.pause_item_x = (SCREEN_WIDTH >> 1) - 40;
            } else {
                this.pause_item_x = GameTime.advance(this, "pause_item_x", this.pause_item_x, this.pause_item_speed);
            }
        } else if (this.pausecnt > 7) {
            int var1;
            for(var1 = 0; var1 < 3; ++var1) {
                if (Key.touchgamepauseitem[var1].Isin() && Key.touchgamepause.IsClick()) {
                    this.pause_item_cursor = var1;
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
                    this.pause_saw_x = GameTime.advance(this, "pause_saw_x", this.pause_saw_x, -(this.pause_saw_speed));
                    this.pause_item_x = GameTime.advance(this, "pause_item_x", this.pause_item_x, -(this.pause_item_speed));
                } else if (this.pausecnt > this.pause_returnframe + 3) {
                    this.BacktoGame();
                    isDrawTouchPad = true;
                    this.pause_returnFlag = false;
                }
            }

            if (this.pause_optionFlag && this.pausecnt > this.pause_optionframe + 3) {
                this.optionInit();
                this.state = 8;
                this.pause_optionFlag = false;
            }

            if (fadeChangeOver()) {
                if (Key.touchgamepauseitem[0].IsButtonPress() && this.pause_item_cursor == 0 && !this.pause_optionFlag) {
                    this.pause_returnFlag = true;
                    this.pause_returnframe = this.pausecnt;
                    SoundSystem.getInstance().playSe(1);
                } else if (Key.touchgamepauseitem[1].IsButtonPress() && this.pause_item_cursor == 1 && fadeChangeOver()) {
                    this.state = 7;
                    fadeInit(102, 220);
                    this.secondEnsureInit();
                    SoundSystem.getInstance().playSe(1);
                } else if ((Key.press(1073741824 | Integer.MIN_VALUE) || Key.touchgamepauseitem[2].IsButtonPress() && this.pause_item_cursor == 2) && !this.pause_optionFlag) {
                    this.changeStateWithFade(8);
                    this.optionInit();
                    SoundSystem.getInstance().playSe(1);
                }
            } else {
                for(var1 = 0; var1 < 3; ++var1) {
                    Key.touchgamepauseitem[var1].resetKeyState();
                }
            }
        }

    }

    private void pauseOptionLoadingTipsLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                GlobalResource.loadingTipsConfig = 0;
                fadeInit(102, 8);
                this.state = 8;
                this.returnCursor = 8;
                break;
            case 2:
                GlobalResource.loadingTipsConfig = 1;
                fadeInit(102, 8);
                this.state = 8;
                this.returnCursor = 8;
                break;
            case 3:
                fadeInit(102, 8);
                this.state = 0;
                this.returnCursor = 8;
        }

    }

    private void pauseOptionSoundLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                this.isOptionDisFlag = false;
                Standard2.soundtrack = 0;
                fadeInit(102, 8);
                this.state = 8;
                this.returnCursor = 8;
                break;
            case 2:
                this.isOptionDisFlag = false;
                Standard2.soundtrack = 1;
                fadeInit(102, 8);
                this.state = 8;
                this.returnCursor = 8;
                break;
            case 3:
                fadeInit(102, 8);
                this.state = 0;
                this.returnCursor = 8;
        }

    }

    private void pauseOptionSpSetLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                GlobalResource.spsetConfig = 0;
                GlobalResource.sensorConfig = 3;
                fadeInit(102, 0);
                this.state = 8;
                Key.touchkeyboardClose();
                Key.touchSpKeyboardInit();
                if (bButton != null) {
                    MFDevice.removeComponent(bButton);
                    bButton = null;
                }

                if (aButton != null) {
                    MFDevice.removeComponent(aButton);
                    aButton = null;
                }
                break;
            case 2:
                this.state = 16;
                this.itemsSelect4Init();
                Key.touchkeyboardClose();
                int var1;
                int var2;
                int var3;
                if (bButton == null) {
                    var3 = MyAPI.zoomOut(Def.SCREEN_WIDTH);
                    var2 = MyAPI.zoomOut(Def.SCREEN_HEIGHT);
                    var1 = Key.B_SEL;
                    bButton = new MFTouchKey(0, 0, var3 >> 1, var2, var1);
                    MFDevice.addComponent(bButton);
                }

                if (aButton == null) {
                    var2 = MyAPI.zoomOut(Def.SCREEN_WIDTH);
                    var3 = MyAPI.zoomOut(Def.SCREEN_WIDTH);
                    var1 = MyAPI.zoomOut(Def.SCREEN_HEIGHT);
                    aButton = new MFTouchKey(var2 >> 1, 0, var3 >> 1, var1, 0x01000000);
                    MFDevice.addComponent(aButton);
                }
                break;
            case 3:
                GlobalResource.spsetConfig = 0;
                GlobalResource.sensorConfig = 3;
                fadeInit(102, 0);
                this.state = 8;
                Key.touchkeyboardClose();
                Key.touchSpKeyboardInit();
                if (bButton != null) {
                    MFDevice.removeComponent(bButton);
                    bButton = null;
                }

                if (aButton != null) {
                    MFDevice.removeComponent(aButton);
                    aButton = null;
                }
        }

    }

    private void pauseOptionVibrationLogic() {
        switch (this.itemsSelect2Logic()) {
            case 1:
                GlobalResource.vibrationConfig = 1;
                fadeInit(102, 0);
                this.state = 8;
                MyAPI.vibrate();
                break;
            case 2:
                GlobalResource.vibrationConfig = 0;
                fadeInit(102, 0);
                this.state = 8;
                break;
            case 3:
                fadeInit(102, 0);
                this.state = 8;
        }

    }

    private void pausetoTitleLogic() {
        switch (this.secondEnsureLogic()) {
            case 1:
                Key.touchanykeyInit();
                Key.touchMainMenuInit2();
                AnimationDrawer.setAllPause(false);
                StageManager.characterFromGame = PlayerObject.getCharacterID();
                StageManager.stageIDFromGame = StageManager.getStageID();
                State.setState(2);
                break;
            case 2:
                this.pauseInitFromItems();
        }

    }

    private void releaseOptionItemsTouchKey() {
        for(int var1 = 0; var1 < Key.touchmenuoptionitems.length; ++var1) {
            Key.touchmenuoptionitems[var1].resetKeyState();
        }

    }

    public static void saveData() {
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        DataOutputStream var1 = new DataOutputStream(var2);
        int var0 = 0;

        while(true) {
            try {
                if (var0 >= emeraldStatus.length) {
                    byte[] var4 = var2.toByteArray();
                    Record.saveRecord("EMERALD_RECORD", var4);
                    break;
                }

                var1.writeByte(emeraldStatus[var0]);
            } catch (Exception var3) {
                break;
            }

            ++var0;
        }

    }

    public static void setEmeraldState(int var0, int var1) {
        emeraldStatus[emeraldID(var0)] = var1;
    }

    public void changeStateWithFade(int var1) {
        if (!fading) {
            fading = true;
            if (var1 == 8) {
                fadeInit(102, 255);
            } else {
                fadeInit(0, 255);
            }

            this.nextState = var1;
            this.fadeChangeState = true;
        }

    }

    public void close() {
        Animation.closeAnimation(SpecialObject.objAnimation);
        SpecialObject.objAnimation = null;
        if (this.endingInstance != null) {
            this.endingInstance.close();
            this.endingInstance = null;
        }

        GameState.releaseTouchkeyBoard();
        Animation.closeAnimationDrawer(this.characterUpDrawer);
        this.characterUpDrawer = null;
        this.speedLight = null;
        this.readyBgImage = null;
        this.welcomeBgImage = null;
        Animation.closeAnimationDrawer(this.fontDrawer);
        this.fontDrawer = null;
        Animation.closeAnimationDrawer(this.interruptDrawer);
        this.interruptDrawer = null;
        Animation.closeAnimationDrawer(numberDrawer);
        numberDrawer = null;
        SpecialMap.releaseMap();
        SpecialObject.closeObjects();
        if (bButton != null) {
            MFDevice.removeComponent(bButton);
        }

        if (aButton != null) {
            MFDevice.removeComponent(aButton);
        }

        if (GlobalResource.spsetConfig == 0) {
            Key.touchkeyboardClose();
        }

        Key.touchSPstageClose();
        System.gc();

        try {
            Thread.sleep(100L);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public void draw(MFGraphics var1) {
        switch (this.state) {
            case 13:
                var1.setFont(11);
                break;
            default:
                if (GlobalResource.languageConfig < 8) {
                    var1.setFont(11);
                } else {
                    var1.setFont(14);
                }
        }

        int var2;
        int var3;
        int var4;
        switch (this.state) {
            case 0:
                MyAPI.drawImage(var1, this.readyBgImage, 0, 0, 0);

                for(var2 = 0; var2 < this.speedLightVec.size(); ++var2) {
                    int[] var9 = (int[])this.speedLightVec.elementAt(var2);
                    MFImage var8 = this.speedLight;
                    var4 = var9[0];
                    var3 = var9[1];
                    MyAPI.drawImage(var1, var8, var4, var3, 33);
                }

                this.characterUpDrawer.draw(var1, SCREEN_WIDTH >> 1, this.characterY);
                break;
            case 1:
                var1.setColor(0);
                MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                MyAPI.drawImage(var1, this.welcomeBgImage, 0, 0, 0);
                SpecialObject.player.setPause(false);
                SpecialObject.player.drawWelcome(var1);
                break;
            case 2:
                SpecialMap.drawMap(var1);
                SpecialObject.drawObjects(var1);
                SpecialObject.player.setPause(false);
                SpecialObject.player.drawInfo(var1);
                if (GlobalResource.spsetConfig == 0 && SpecialObject.player.isNeedTouchPad()) {
                    this.drawTouchKeyDirect(var1);
                }

                if (SpecialObject.player.isNeedTouchPad()) {
                    drawSoftKeyPause(var1);
                }
                break;
            case 3:
            case 5:
                var1.setColor(16777215);
                MyAPI.fillRect(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                this.barDrawer.drawBar(var1);

                int var5;
                int var6;
                AnimationDrawer var7;
                for(var3 = 0; var3 < this.bonusY.length; ++var3) {
                    var7 = this.fontDrawer;
                    var6 = BONUS_ID[var3];
                    var2 = BONUS_X;
                    var5 = this.barDrawer.getBarX();
                    var4 = this.bonusY[var3];
                    var7.draw(var1, var6, var5 + var2, var4, false, 0);
                    var2 = 0;
                    switch (var3) {
                        case 0:
                            var2 = this.ringScore;
                            break;
                        case 1:
                            var2 = this.clearScore;
                            break;
                        case 2:
                            var2 = this.totalScore;
                    }

                    var4 = SCREEN_WIDTH;
                    var5 = this.barDrawer.getBarX();
                    var6 = this.bonusY[var3];
                    NumberDrawer.drawNum(var1, 0, var2, var5 + (var4 >> 1) + 75, var6, 4);
                }

                for(var2 = 0; var2 < 7; ++var2) {
                    var7 = this.fontDrawer;
                    if (emeraldStatus[var2] == 1) {
                        var3 = var2 + 15;
                    } else {
                        var3 = 14;
                    }

                    var5 = this.emeraldX[var2];
                    var4 = this.barDrawer.getBarX();
                    var6 = EMERALD_Y;
                    var7.draw(var1, var3, var5 + var4, var6, false, 0);
                }

                return;
            case 4:
                this.endingInstance.draw(var1);
                break;
            case 6:
                SpecialMap.drawMap(var1);
                SpecialObject.drawObjects(var1);
                SpecialObject.player.setPause(true);
                SpecialObject.player.drawInfo(var1);
                this.pauseDraw(var1);
                break;
            case 7:
                SpecialMap.drawMap(var1);
                SpecialObject.drawObjects(var1);
                SpecialObject.player.setPause(true);
                SpecialObject.player.drawInfo(var1);
                muiAniDrawer.setActionId(50);
                muiAniDrawer.draw(var1, this.pause_saw_x, this.pause_saw_y);
                drawFade(var1);
                this.SecondEnsurePanelDraw(var1, 18);
                break;
            case 8:
                this.optionDraw(var1);
                break;
            case 9:
                this.optionDraw(var1);
                this.touchPadPositionDraw(var1);
                break;
            case 10:
                this.optionDraw(var1);
                this.itemsSelect4Draw(var1);
                break;
            case 11:
                this.optionDraw(var1);
                break;
            case 12:
                this.optionDraw(var1);
                this.touchPadSizeDraw(var1);
                break;
            case 13:
                this.helpDraw(var1);
                break;
            case 14:
                this.interruptDraw(var1);
                break;
            case 15:
                this.optionDraw(var1);
                this.touchPadOpacityDraw(var1);
                break;
            case 16:
                this.optionDraw(var1);
                this.itemsSelect3Draw(var1);
        }

    }

    public void drawWord(MFGraphics var1, int var2, int var3, int var4) {
        switch (var2) {
            case 0:
                this.fontDrawer.draw(var1, PlayerObject.getCharacterID() + 8, var3, var4, false, 0);
                this.fontDrawer.draw(var1, 12, var3, var4, false, 0);
                break;
            case 1:
                this.fontDrawer.draw(var1, 13, var3, var4, false, 0);
        }

    }

    public void fadeStateLogic() {
        if (fading && this.fadeChangeState && fadeChangeOver() && this.state != this.nextState) {
            this.state = this.nextState;
            this.fadeChangeState = false;
            if (this.state == 6) {
                fadeInit_Modify(255, 102);
            } else {
                fadeInit(255, 0);
            }
        }

        if (this.state == this.nextState && fadeChangeOver()) {
            fading = false;
        }

    }

    public int getWordLength(int var1) {
        return 320;
    }

    public void init() {
        GameState.initTouchkeyBoard();
        SpecialMap.loadMap();
        SpecialObject.initObjects();
        Animation var1 = new Animation("/animation/special/sp_up_chr");
        AnimationDrawer var2 = var1.getDrawer(PlayerObject.getCharacterID(), true, 0);
        this.characterUpDrawer = var2;
        this.characterY = SCREEN_HEIGHT + 40;
        fadeInitAndStart(255, 0);
        this.speedLight = MFImage.createImage("/special_res/speed_light.png");
        this.speedLightVec = new Vector();
        this.changingState = false;
        this.readyBgImage = MFImage.createImage("/special_res/sp_up_bg.png");
        this.welcomeBgImage = MFImage.createImage("/special_res/sp_welcome_bg.png");
        StringBuilder var3 = new StringBuilder("/lang");
        var3.append(GlobalResource.languageConfig);
        var3.append("/special_res/sp_font.dat");
        this.fontDrawer = Animation.getInstanceFromQi(var3.toString())[0].getDrawer();
    }

    public void logic() {
        this.fadeStateLogic();
        if (Key.press(Key.B_PO)) {
        }

        if (Key.press(536870912) && this.state != 2) {
            pause();
        }

        int var5;
        int[] var7;
        switch (this.state) {
            case 0:
                if (fadeChangeOver()) {
                    if (!this.isIntroBGMplay) {
                        SoundSystem.getInstance().playBgm(34, false);
                        this.isIntroBGMplay = true;
                    }

                    if (!this.changingState) {
                        this.characterY = GameTime.advance(this, "characterY", this.characterY, -(15));
                        if (this.characterY < -40) {
                            fadeInitAndStart(0, 255);
                            this.changingState = true;
                        }
                    } else {
                        this.state = 1;
                        fadeInitAndStart(255, 0);
                        SpecialObject.player.initWelcome();
                        this.changingState = false;
                    }
                }

                int var6;
                for(var5 = 0; var5 < this.speedLightVec.size(); var5 = var6 + 1) {
                    var7 = (int[])this.speedLightVec.elementAt(var5);
                    var7[1] = GameTime.advance(var7, String.valueOf(1), var7[1], 60);
                    var6 = var5;
                    if (var7[1] > SCREEN_HEIGHT + this.speedLight.getHeight()) {
                        this.speedLightVec.removeElementAt(var5);
                        var6 = var5 - 1;
                    }
                }

                for(var5 = 0; var5 < 2; ++var5) {
                    var6 = MyRandom.nextInt(0, SCREEN_WIDTH);
                    this.speedLightVec.addElement(new int[]{var6, 0 - var5 * 30});
                }

                return;
            case 1:
                SpecialObject.player.logicWelcome();
                if (fadeChangeOver()) {
                    if (!this.changingState) {
                        if (SpecialObject.player.isWelcomeOver()) {
                            fadeInitAndStart(0, 255);
                            this.changingState = true;
                        }
                    } else {
                        this.state = 2;
                        SoundSystem.getInstance().playBgm(35);
                        fading = false;
                        fadeInit(255, 0);
                        this.changingState = false;
                    }
                }
                break;
            case 2:
                AnimationDrawer.setAllPause(false);
                SpecialObject.player.logic();
                SpecialObject.objectLogic();
                if (!this.changingState && SpecialObject.player.isOver()) {
                    setFadeColor(16777215);
                    fadeInitAndStart(0, 255);
                    this.changingState = true;
                }

                if (fadeChangeOver() && this.changingState) {
                    this.state = 3;
                    setFadeColor(0);
                    fading = false;
                    WhiteBarDrawer var9 = this.barDrawer;
                    short var8;
                    if (SpecialObject.player.checkSuccess) {
                        var8 = 0;
                    } else {
                        var8 = 1;
                    }

                    var9.initBar(this, var8);
                    this.barDrawer.setPause(true);
                    this.changingState = false;
                    this.count = GameTime.set(this, "count", 0);

                    for(var5 = 0; var5 < this.bonusY.length; ++var5) {
                        this.bonusY[var5] = BONUS_Y_ORIGINAL;
                    }

                    for(var5 = 0; var5 < this.emeraldX.length; ++var5) {
                        this.emeraldX[var5] = EMERALD_X_ORIGINAL;
                    }

                    this.ringScore = SpecialObject.player.getRingNum() * 100;
                    if (SpecialObject.player.checkSuccess) {
                        var8 = 10000;
                    } else {
                        var8 = 0;
                    }

                    this.clearScore = var8;
                    this.totalScore = 0;
                    if (SpecialObject.player.checkSuccess) {
                        setEmeraldState(StageManager.getStageID(), 1);
                        saveData();
                        var5 = PlayerObject.getCharacterID();
                        SpecialEnding var10 = new SpecialEnding(var5, STAGE_ID_TO_SPECIAL_ID[StageManager.getStageID()]);
                        this.endingInstance = var10;
                        this.state = 4;
                        setFadeColor(16777215);
                        fadeInitAndStart(255, 0);
                        SoundSystem.getInstance().stopBgm(false);
                    } else {
                        if (emeraldState(StageManager.getStageID()) != 1) {
                            setEmeraldState(StageManager.getStageID(), 2);
                            saveData();
                        }

                        SoundSystem.getInstance().playBgm(39);
                    }
                }

                if (SpecialObject.player.isNeedTouchPad() && (Key.touchspstagepause.IsButtonPress() || Key.press(524288 | 536870912))) {
                    SoundSystem.getInstance().playSe(33);
                    this.pauseInit();
                }
                break;
            case 3:
                this.count = GameTime.advance(this, "count", this.count, 1);
                double var1;
                double var3;
                if (this.count >= 13) {
                    for(var5 = 0; var5 < 3; ++var5) {
                        if (var5 <= this.count - 13) {
                            var7 = this.bonusY;
                            var1 = (double)this.bonusY[var5];
                            var3 = (double)(BONUS_Y_START + var5 * 18);
                            var7[var5] = MyAPI.calNextPosition(var7, String.valueOf(var5), var1, var3, 1, 4);
                        }
                    }
                }

                if (this.count >= 16) {
                    for(var5 = 0; var5 < 7; ++var5) {
                        if (var5 <= this.count - 16) {
                            var7 = this.emeraldX;
                            var3 = (double)this.emeraldX[var5];
                            var1 = (double)(EMERALD_X_START + var5 * 32);
                            var7[var5] = MyAPI.calNextPosition(var7, String.valueOf(var5), var3, var1, 1, 4);
                        }
                    }
                }

                if (this.count > 46) {
                    if (this.ringScore > 0) {
                        var5 = Math.min(GameTime.distance(this, "ringScoreTransfer", 400), this.ringScore);
                        this.ringScore -= var5;
                        this.totalScore += var5;
                    }

                    if (this.clearScore > 0) {
                        var5 = Math.min(GameTime.distance(this, "clearScoreTransfer", 400), this.clearScore);
                        this.clearScore -= var5;
                        this.totalScore += var5;
                    }

                    if (this.ringScore == 0) {
                        SoundSystem.getInstance().playSe(32);
                    } else {
                        SoundSystem.getInstance().playSe(31);
                    }

                    if (this.ringScore == 0 && this.clearScore == 0) {
                        PlayerObject.setScore(PlayerObject.getScore() + this.totalScore);
                        this.state = 5;
                        this.count = GameTime.set(this, "count", 0);
                    }
                }
                break;
            case 4:
                this.endingInstance.logic();
                if (this.endingInstance.isOver() && !this.changingState) {
                    setFadeColor(16777215);
                    fadeInitAndStart(0, 255);
                    this.changingState = true;
                }

                if (fadeChangeOver()) {
                    if (this.changingState) {
                        this.state = 3;
                        SoundSystem.getInstance().playBgm(40);
                        this.changingState = false;
                        setFadeColor(0);
                        fading = false;
                    } else {
                        setFadeColor(0);
                    }
                }
                break;
            case 5:
                this.count = GameTime.advance(this, "count", this.count, 1);
                if (GameTime.event(this, "count", "logic:1756", GameTime.crosses(this, "count", this.count, 128))) {
                    this.barDrawer.setPause(false);
                }

                if (this.barDrawer.getState() == 4) {
                    this.backToGameStage();
                }
                break;
            case 6:
                this.pauseLogic();
                AnimationDrawer.setAllPause(true);
                break;
            case 7:
                this.pausetoTitleLogic();
                break;
            case 8:
                this.optionLogic();
                AnimationDrawer.setAllPause(false);
                break;
            case 9:
                switch (this.touchPadPositionLogic()) {
                    case 2:
                        GlobalResource.saveSystemConfig();
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    default:
                        return;
                }
            case 10:
                switch (this.itemsSelect4Logic()) {
                    case 1:
                        GlobalResource.vibrationConfig = 0;
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    case 2:
                        GlobalResource.vibrationConfig = 1;
                        MyAPI.vibrate();
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    case 3:
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    case 4:
                        GlobalResource.vibrationConfig = 2;
                        MyAPI.vibrate();
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    case 5:
                        GlobalResource.vibrationConfig = 3;
                        MyAPI.vibrate();
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    default:
                        return;
                }
            case 11:
            default:
                break;
            case 12:
                switch (this.touchPadSizeLogic()) {
                    case 2:
                        GlobalResource.saveSystemConfig();
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    default:
                        return;
                }
            case 13:
                this.helpLogic();
                if ((Key.buttonPress(524288 | 8388608) || Key.touchhelpreturn.IsButtonPress() && this.returnPageCursor == 1) && fadeChangeOver()) {
                    this.changeStateWithFade(8);
                    SoundSystem.getInstance().playSe(2);
                }
                break;
            case 14:
                this.interruptLogic();
                break;
            case 15:
                switch (this.touchPadOpacityLogic()) {
                    case 2:
                        GlobalResource.saveSystemConfig();
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    default:
                        return;
                }
            case 16:
                switch (this.itemsSelect3Logic()) {
                    case 1:
                        GlobalResource.sensorConfig = 0;
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    case 2:
                        GlobalResource.sensorConfig = 1;
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    case 3:
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    case 4:
                        GlobalResource.sensorConfig = 2;
                        fadeInit(220, 0);
                        state = 8;
                        return;
                    default:
                        return;
                }
        }

    }

    public void pause() {
        if (this.state != 14 && this.state != 6) {
            if (this.state == 2 && SpecialObject.player.isNeedTouchPad()) {
                this.pauseInit();
            } else {
                this.interrupt_state = this.state;
                this.state = 14;
                this.interruptInit();
            }

            if (this.interrupt_state == 4) {
                this.endingInstance.pause();
            }
        }

    }
}

