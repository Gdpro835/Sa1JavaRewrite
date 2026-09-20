//
// Decompiled by Jadx - 1393ms
//
package GameEngine;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGamePad;
import com.sega.mobile.framework.ui.MFSlideSensor;
import com.sega.mobile.framework.ui.MFTouchKey;
import SonicGBA.GlobalResource;

public class Key {
    public static final int B_BACK = 524288;
    public static final int B_HIGH_JUMP = 0x01000000;
    public static final int B_LOOK = 0x02000000;
    public static final int B_NULL = 8388608;
    public static final int B_PAUSE = 4194304;
    public static final int B_S2 = 2;
    public static final int B_SPIN2 = 2097152;
    public static final int PAUSE_HEIGHT = 30;
    public static final int PAUSE_WIDTH = 60;
    public static final int TOUCH_HEIGHT = 20;
    public static final int TOUCH_WIDTH = 20;
    public static final int TYPE_GAME_MODE = 1;
    public static final int TYPE_SP_MODE = 0;
    public static int aXPos;
    public static int aYPos;
    public static int buttonSize;
    public static int bXPos;
    public static int circlePadXPos;
    public static int gDown;
    public static int gLeft;
    public static int gRight;
    public static int gSelect;
    public static int gUp;
    public static MFSlideSensor slidesensorcharacterrecord;
    public static MFSlideSensor slidesensorcharsel;
    public static MFSlideSensor slidesensorhelp;
    public static MFSlideSensor slidesensormenuoption;
    public static MFSlideSensor slidesensorstagesel;
    public static TouchKeyRange touchConfirmNo;
    public static TouchKeyRange touchConfirmYes;
    public static TouchKeyRange touchGameKeyA;
    public static TouchKeyRange touchMainmenuDown;
    public static TouchKeyRange touchMainmenuSel;
    public static TouchKeyRange touchMainmenuUp;
    public static TouchKeyRange touchcharsel;
    public static TouchKeyRange touchcharselleftarrow;
    public static TouchKeyRange touchcharselreturn;
    public static TouchKeyRange touchcharselrightarrow;
    public static TouchKeyRange touchcharselselect;
    public static TouchDirectKey touchdirectgamekey;
    public static TouchKeyRange touchgame;
    private static MFTouchKey touchgamekey_5a;
    private static MFTouchKey touchgamekey_5b;
    private static MFTouchKey touchgamekey_do1;
    private static MFTouchKey touchgamekey_do2;
    private static MFTouchKey touchgamekey_ld;
    private static MFTouchKey touchgamekey_le;
    private static MFTouchKey touchgamekey_lu;
    private static MFTouchKey touchgamekey_rd;
    private static MFTouchKey touchgamekey_ri;
    private static MFTouchKey touchgamekey_ru;
    private static MFTouchKey touchgamekey_up1;
    private static MFTouchKey touchgamekey_up2;
    public static TouchKeyRange touchgameover;
    public static TouchKeyRange touchgameoverno;
    public static TouchKeyRange touchgameoveryres;
    public static TouchKeyRange touchgamepause;
    public static TouchKeyRange[] touchgamepauseitem;
    public static TouchKeyRange touchgamepausereturn;
    public static TouchKeyRange touchhelpdown;
    public static TouchKeyRange touchhelpdownarrow;
    public static TouchKeyRange touchhelpleft;
    public static TouchKeyRange touchhelpleftarrow;
    public static TouchKeyRange touchhelpreturn;
    public static TouchKeyRange touchhelpright;
    public static TouchKeyRange touchhelprightarrow;
    public static TouchKeyRange touchhelpup;
    public static TouchKeyRange touchhelpuparrow;
    public static TouchKeyRange touchintergraderecord;
    public static TouchKeyRange touchintergraderecordleftarrow;
    public static TouchKeyRange touchintergraderecordreturn;
    public static TouchKeyRange touchintergraderecordrightarrow;
    public static TouchKeyRange touchintergraderecordupdate;
    public static TouchKeyRange touchinterruptreturn;
    public static TouchKeyRange touchitemsselect2;
    public static TouchKeyRange touchitemsselect2_1;
    public static TouchKeyRange touchitemsselect2_2;
    public static TouchKeyRange touchitemsselect2_return;
    public static TouchKeyRange touchitemsselect3;
    public static TouchKeyRange touchitemsselect3_1;
    public static TouchKeyRange touchitemsselect3_2;
    public static TouchKeyRange touchitemsselect3_3;
    public static TouchKeyRange touchitemsselect3_return;
    public static TouchKeyRange touchitemsselect4;
    public static TouchKeyRange touchitemsselect4_1;
    public static TouchKeyRange touchitemsselect4_2;
    public static TouchKeyRange touchitemsselect4_3;
    public static TouchKeyRange touchitemsselect4_4;
    public static TouchKeyRange touchitemsselect4_return;
    private static MFTouchKey touchkey_a;
    private static MFTouchKey touchkey_any;
    private static MFTouchKey touchkey_b;
    public static TouchKeyRange touchkey_pause;
    private static MFTouchKey touchkey_pound;
    private static MFTouchKey touchkey_s1;
    private static MFTouchKey touchkey_s2;
    private static MFTouchKey touchkey_star;
    public static TouchKeyRange touchmainmenu;
    public static TouchKeyRange touchmainmenudown;
    public static TouchKeyRange touchmainmenuend;
    public static TouchKeyRange touchmainmenuitem;
    public static TouchKeyRange touchmainmenuoption;
    public static TouchKeyRange touchmainmenurace;
    public static TouchKeyRange touchmainmenureturn;
    public static TouchKeyRange touchmainmenustart;
    public static TouchKeyRange touchmainmenuvs;
    public static TouchKeyRange touchmainmenuup;
    public static TouchKeyRange touchmenucon;
    public static TouchKeyRange touchmenunew;
    public static TouchKeyRange touchmenuoption;
    public static TouchKeyRange touchmenuoptiondownarrow;
    public static TouchKeyRange[] touchmenuoptionitems;
    public static TouchKeyRange touchmenuoptionlanguage;
    public static TouchKeyRange[] touchmenuoptionlanguageitems;
    public static TouchKeyRange touchmenuoptionlanguagereturn;
    public static TouchKeyRange touchmenuoptionleftarrow;
    public static TouchKeyRange touchmenuoptionreturn;
    public static TouchKeyRange touchmenuoptionrightarrow;
    public static TouchKeyRange touchmenuoptionuparrow;
    public static TouchKeyRange touchopening;
    public static TouchKeyRange touchopeningskip;
    public static TouchKeyRange touchoptiondif;
    public static TouchKeyRange touchoptionleft;
    public static TouchKeyRange touchoptionrein;
    public static TouchKeyRange touchoptionreout;
    public static TouchKeyRange touchoptionright;
    public static TouchKeyRange touchoptionsein;
    public static TouchKeyRange touchoptionseout;
    public static TouchKeyRange touchoptionso;
    public static TouchKeyRange touchoptiontimein;
    public static TouchKeyRange touchoptiontimeout;
    public static TouchKeyRange touchoptionvolcut;
    public static TouchKeyRange touchoptionvolplus;
    public static TouchKeyRange touchpadoption;
    public static TouchKeyRange touchpadoptionleft1;
    public static TouchKeyRange touchpadoptionleft2;
    public static TouchKeyRange touchpadoptionminus;
    public static TouchKeyRange touchpadoptionplus;
    public static TouchKeyRange touchpadoptionreturn;
    public static TouchKeyRange touchpadoptionright1;
    public static TouchKeyRange touchpadoptionright2;
    public static TouchKeyRange touchpage;
    public static TouchKeyRange touchpause1;
    public static TouchKeyRange touchpause2;
    public static TouchKeyRange touchpause3;
    public static TouchKeyRange touchpause4;
    public static TouchKeyRange touchpausearrowdown;
    public static TouchKeyRange touchpausearrowdownsingle;
    public static TouchKeyRange touchpausearrowup;
    public static TouchKeyRange touchpausearrowupsingle;
    public static TouchKeyRange touchpauseopsein;
    public static TouchKeyRange touchpauseopseout;
    public static TouchKeyRange touchpauseopsound;
    public static TouchKeyRange touchpauseoption;
    public static TouchKeyRange touchpauseoptionhelp;
    public static TouchKeyRange touchpauseoptionkeyset;
    public static TouchKeyRange touchpauseoptionleft;
    public static TouchKeyRange touchpauseoptionreturn;
    public static TouchKeyRange touchpauseoptionright;
    public static TouchKeyRange touchpauseoptionsound;
    public static TouchKeyRange touchpauseoptionspset;
    public static TouchKeyRange touchpauseoptionvib;
    public static TouchKeyRange touchpauseopvolcut;
    public static TouchKeyRange touchpauseopvolplus;
    public static TouchKeyRange touchproracemode;
    public static TouchKeyRange touchproracemoderecord;
    public static TouchKeyRange touchproracemodereturn;
    public static TouchKeyRange touchproracemodestart;
    public static TouchKeyRange touchscoreupdate;
    public static TouchKeyRange touchscoreupdateno;
    public static TouchKeyRange touchscoreupdatereturn;
    public static TouchKeyRange touchscoreupdateyes;
    public static TouchKeyRange touchsecondensure;
    public static TouchKeyRange touchsecondensureno;
    public static TouchKeyRange touchsecondensurereturn;
    public static TouchKeyRange touchsecondensureyes;
    public static TouchKeyRange touchsel1;
    public static TouchKeyRange touchsel2;
    public static TouchKeyRange touchsel3;
    public static TouchKeyRange touchsel4;
    public static TouchKeyRange touchsel5;
    public static TouchKeyRange touchsel6;
    public static TouchKeyRange touchselarrowdown;
    public static TouchKeyRange touchselarrowup;
    public static TouchKeyRange touchspstage;
    public static TouchKeyRange touchspstagepause;
    public static TouchKeyRange touchstageselect;
    public static TouchKeyRange touchstageselectdownarrow;
    public static TouchKeyRange[] touchstageselectitem;
    public static TouchKeyRange touchstageselectreturn;
    public static TouchKeyRange touchstageselectuparrow;
    public static TouchKeyRange touchstartgame;
    public static TouchKeyRange touchstartgamecontinue;
    public static TouchKeyRange touchstartgamenew;
    public static TouchKeyRange touchstartgamereturn;
    public static int B_UP = 4;
    public static int B_DOWN = 8;
    public static int B_LEFT = 16;
    public static int B_RIGHT = 32;
    public static int B_SEL = 64;
    public static int B_1 = 128;
    public static int B_2 = 256;
    public static int B_3 = 512;
    public static int B_4 = 1024;
    public static int B_5 = 2048;
    public static int B_6 = 4096;
    public static int B_7 = 8192;
    public static int B_8 = 16384;
    public static int B_9 = 32768;
    public static int B_0 = 131072;
    public static int B_ST = 65536;
    public static int B_PO = 262144;
    public static int B_S1 = 1;
    private static int KEY = 0;
    private static int KEY_press = 0;
    private static int KEY_before = 0;
    private static int KEY_get = 0;
    public static int DIR_LEFT = 4;
    public static int DIR_RIGHT = 8;
    public static int DIR_UP = 1;
    public static int DIR_DOWN = 2;
    private static int pause_x = Def.SCREEN_WIDTH - 60;
    private static int pause_y = 0;
    private static boolean keyFunction = true;
    private static int keyState = 0;

    public static void setTouchPosition() {
        aXPos = Def.SCREEN_WIDTH - 22 - GlobalResource.touchKeyBoardRightPosition - (4 * GlobalResource.touchKeyBoardSize);
        aYPos = 113 - (2 * GlobalResource.touchKeyBoardSize);
        bXPos = Def.SCREEN_WIDTH - 47 - GlobalResource.touchKeyBoardRightPosition - (8 * GlobalResource.touchKeyBoardSize);
        buttonSize = 20 + (4 * GlobalResource.touchKeyBoardSize);
        circlePadXPos = 17 + GlobalResource.touchKeyBoardLeftPosition + (3 * GlobalResource.touchKeyBoardSize);
    }

    public static void touchsoftkeyInit() {
        if (touchkey_s1 == null || touchkey_s2 == null) {
            touchkey_s1 = new MFTouchKey(0, MyAPI.zoomOut(Def.SCREEN_HEIGHT - 20), MyAPI.zoomOut(20), MyAPI.zoomOut(20), 1);
            touchkey_s2 = new MFTouchKey(MyAPI.zoomOut(Def.SCREEN_WIDTH - 20), MyAPI.zoomOut(Def.SCREEN_HEIGHT - 20), MyAPI.zoomOut(20), MyAPI.zoomOut(20), 2);
            MFDevice.addComponent(touchkey_s1);
            MFDevice.addComponent(touchkey_s2);
        }
    }

    public static void touchsoftkeyClose() {
        MFDevice.removeComponent(touchkey_s1);
        MFDevice.removeComponent(touchkey_s2);
        touchkey_s1 = null;
        touchkey_s2 = null;
    }

    public static void touchanykeyInit() {
        if (touchkey_any == null) {
            touchkey_any = new MFTouchKey(0, 0, MyAPI.zoomOut(Def.SCREEN_WIDTH), MyAPI.zoomOut(Def.SCREEN_HEIGHT), B_SEL);
            MFDevice.addComponent(touchkey_any);
        }
    }

    public static void touchanykeyClose() {
        if (touchkey_any != null) {
            MFDevice.removeComponent(touchkey_any);
            touchkey_any = null;
        }
    }

    public static void touchactiongamekeyInit() {
        setTouchPosition();
        if (touchkey_a == null || touchkey_b == null) {
            touchkey_a = new MFTouchKey(MyAPI.zoomOut(aXPos), MyAPI.zoomOut(aYPos), MyAPI.zoomOut(buttonSize), MyAPI.zoomOut(buttonSize), B_HIGH_JUMP);
            touchkey_b = new MFTouchKey(MyAPI.zoomOut(bXPos), MyAPI.zoomOut(118), MyAPI.zoomOut(buttonSize), MyAPI.zoomOut(buttonSize), B_SEL);
            MFDevice.addComponent(touchkey_a);
            MFDevice.addComponent(touchkey_b);
        }
        if (touchGameKeyA == null) {
            touchGameKeyA = new TouchKeyRange(aXPos, aYPos, buttonSize, buttonSize);
            MFDevice.addComponent(touchGameKeyA);
        }
        touchstarpoundkeyInit();
    }

    public static void touchstarpoundkeyInit() {
    }

    public static void touchstarpoundkeyClose() {
    }

    public static void touchactionmenukeyInit() {
        setTouchPosition();
        if (touchkey_a == null || touchkey_b == null) {
            touchkey_a = new MFTouchKey(MyAPI.zoomOut(aXPos), MyAPI.zoomOut(aYPos), MyAPI.zoomOut(buttonSize), MyAPI.zoomOut(buttonSize), B_HIGH_JUMP);
            touchkey_b = new MFTouchKey(MyAPI.zoomOut(bXPos), MyAPI.zoomOut(118), MyAPI.zoomOut(buttonSize), MyAPI.zoomOut(buttonSize), B_SEL);
            MFDevice.addComponent(touchkey_a);
            MFDevice.addComponent(touchkey_b);
        }
    }

    public static void touchkeyboardInit() {
        if (touchdirectgamekey == null) {
            touchdirectgamekey = new TouchDirectKey(MyAPI.zoomOut(32), MyAPI.zoomOut(128), MyAPI.zoomOut(0), MyAPI.zoomOut(0), 1);
            MFDevice.addComponent(touchdirectgamekey);
        }
        touchactionmenukeyInit();
        touchstarpoundkeyInit();
    }

    public static void touchkeyboardReset() {
        touchdirectgamekey.reset();
        touchkey_a.reset();
        touchkey_b.reset();
        MFGamePad.resetKeys();
    }

    public static void touchkeyboardClose() {
        if (touchdirectgamekey != null) {
            MFDevice.removeComponent(touchdirectgamekey);
            touchdirectgamekey = null;
        }
        if (touchdirectgamekey != null) {
            MFDevice.removeComponent(touchdirectgamekey);
            touchdirectgamekey = null;
        }
        MFDevice.removeComponent(touchkey_a);
        MFDevice.removeComponent(touchkey_b);
        touchkey_a = null;
        touchkey_b = null;
    }

    public static void touchSpKeyboardInit() {
        setTouchPosition();
        if (touchdirectgamekey == null) {
            touchdirectgamekey = new TouchDirectKey(MyAPI.zoomOut(circlePadXPos), MyAPI.zoomOut(128), MyAPI.zoomOut(40 + (8 * GlobalResource.touchKeyBoardSize)), MyAPI.zoomOut(4), 1);
            MFDevice.addComponent(touchdirectgamekey);
        }
        if (touchkey_a == null || touchkey_b == null) {
            touchkey_a = new MFTouchKey(MyAPI.zoomOut(aXPos), MyAPI.zoomOut(aYPos), MyAPI.zoomOut(buttonSize), MyAPI.zoomOut(buttonSize), B_HIGH_JUMP);
            touchkey_b = new MFTouchKey(MyAPI.zoomOut(bXPos), MyAPI.zoomOut(118), MyAPI.zoomOut(buttonSize), MyAPI.zoomOut(buttonSize), B_SEL);
            MFDevice.addComponent(touchkey_a);
            MFDevice.addComponent(touchkey_b);
        }
    }

    public static void touchkeygameboardInit() {
        setTouchPosition();
        if (touchdirectgamekey == null) {
            touchdirectgamekey = new TouchDirectKey(MyAPI.zoomOut(circlePadXPos), MyAPI.zoomOut(128), MyAPI.zoomOut(40 + (8 * GlobalResource.touchKeyBoardSize)), MyAPI.zoomOut(4), 1);
            MFDevice.addComponent(touchdirectgamekey);
        }
        touchactiongamekeyInit();
    }

    public static void touchkeygameboardClose() {
        if (touchdirectgamekey != null) {
            MFDevice.removeComponent(touchdirectgamekey);
            touchdirectgamekey = null;
        }
        if (touchdirectgamekey != null) {
            MFDevice.removeComponent(touchdirectgamekey);
            touchdirectgamekey = null;
        }
        MFDevice.removeComponent(touchkey_a);
        MFDevice.removeComponent(touchGameKeyA);
        MFDevice.removeComponent(touchkey_b);
        touchkey_a = null;
        touchGameKeyA = null;
        touchkey_b = null;
    }

    public static void touchMainMenuInit() {
        if (touchMainmenuUp == null || touchMainmenuDown == null || touchMainmenuSel == null) {
            touchMainmenuUp = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 32, (Def.SCREEN_HEIGHT >> 1) - 2, 96, 34);
            touchMainmenuDown = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 32, (Def.SCREEN_HEIGHT >> 1) + 56, 96, 34);
            touchMainmenuSel = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 32, (Def.SCREEN_HEIGHT >> 1) + 32, 96, 24);
            MFDevice.addComponent(touchMainmenuUp);
            MFDevice.addComponent(touchMainmenuDown);
            MFDevice.addComponent(touchMainmenuSel);
        }
    }

    public static void touchMainMenuClose() {
        MFDevice.removeComponent(touchMainmenuUp);
        MFDevice.removeComponent(touchMainmenuDown);
        MFDevice.removeComponent(touchMainmenuSel);
        touchMainmenuUp = null;
        touchMainmenuDown = null;
        touchMainmenuSel = null;
    }

    public static void touchConfirmInit() {
        if (touchConfirmYes == null || touchConfirmNo == null) {
            touchConfirmYes = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 40, (Def.SCREEN_HEIGHT >> 1) + 0, 40, 28);
            touchConfirmNo = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 0, (Def.SCREEN_HEIGHT >> 1) + 0, 40, 28);
            MFDevice.addComponent(touchConfirmYes);
            MFDevice.addComponent(touchConfirmNo);
        }
    }

    public static void touchConfirmClose() {
        MFDevice.removeComponent(touchConfirmYes);
        MFDevice.removeComponent(touchConfirmNo);
        touchConfirmYes = null;
        touchConfirmNo = null;
    }

    public static void touchMenuInit() {
        if (touchmenunew == null || touchmenucon == null) {
            touchmenunew = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, (Def.SCREEN_HEIGHT >> 1) - 44, 128, 44);
            touchmenucon = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, (Def.SCREEN_HEIGHT >> 1) + 0, 128, 44);
            MFDevice.addComponent(touchmenunew);
            MFDevice.addComponent(touchmenucon);
        }
    }

    public static void touchMenuClose() {
        MFDevice.removeComponent(touchmenunew);
        MFDevice.removeComponent(touchmenucon);
        touchmenunew = null;
        touchmenucon = null;
    }

    public static void System() {
    }

    public static void touchHelpInit() {
        if (touchhelpleft == null || touchhelpright == null || touchhelpup == null || touchhelpdown == null) {
            touchhelpleft = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 128, (Def.SCREEN_HEIGHT >> 1) - 70, 48, 48);
            touchhelpright = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 80, (Def.SCREEN_HEIGHT >> 1) - 70, 48, 48);
            touchhelpup = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, (Def.SCREEN_HEIGHT >> 1) + 42, 48, 48);
            touchhelpdown = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 0, (Def.SCREEN_HEIGHT >> 1) + 42, 48, 48);
            MFDevice.addComponent(touchhelpleft);
            MFDevice.addComponent(touchhelpright);
            MFDevice.addComponent(touchhelpup);
            MFDevice.addComponent(touchhelpdown);
        }
    }

    public static void touchHelpClose() {
        MFDevice.removeComponent(touchhelpleft);
        MFDevice.removeComponent(touchhelpright);
        MFDevice.removeComponent(touchhelpup);
        MFDevice.removeComponent(touchhelpdown);
        touchhelpleft = null;
        touchhelpright = null;
        touchhelpup = null;
        touchhelpdown = null;
    }

    public static void touchAboutInit() {
        if (touchhelpup == null || touchhelpdown == null) {
            touchhelpup = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, (Def.SCREEN_HEIGHT >> 1) + 42, 48, 48);
            touchhelpdown = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 0, (Def.SCREEN_HEIGHT >> 1) + 42, 48, 48);
            MFDevice.addComponent(touchhelpup);
            MFDevice.addComponent(touchhelpdown);
        }
    }

    public static void touchAboutClose() {
        MFDevice.removeComponent(touchhelpup);
        MFDevice.removeComponent(touchhelpdown);
        touchhelpup = null;
        touchhelpdown = null;
    }

    public static void touchPauseInit(boolean IsRaceMode) {
        if (IsRaceMode && (touchpausearrowup == null || touchpausearrowdown == null || touchpausearrowupsingle == null || touchpausearrowdownsingle == null)) {
            touchpausearrowup = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 44, (Def.SCREEN_HEIGHT >> 1) + 50, 44, 24);
            touchpausearrowdown = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 0, (Def.SCREEN_HEIGHT >> 1) + 50, 44, 24);
            touchpausearrowupsingle = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 44, (Def.SCREEN_HEIGHT >> 1) + 50, 88, 24);
            touchpausearrowdownsingle = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 44, (Def.SCREEN_HEIGHT >> 1) + 50, 88, 24);
            MFDevice.addComponent(touchpausearrowup);
            MFDevice.addComponent(touchpausearrowdown);
            MFDevice.addComponent(touchpausearrowupsingle);
            MFDevice.addComponent(touchpausearrowdownsingle);
        }
        if (touchpause1 == null || touchpause2 == null || touchpause3 == null || touchpause4 == null) {
            touchpause1 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 72, (Def.SCREEN_HEIGHT >> 1) - 30, 144, 20);
            touchpause2 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 72, ((Def.SCREEN_HEIGHT >> 1) - 30) + 20, 144, 20);
            touchpause3 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 72, ((Def.SCREEN_HEIGHT >> 1) - 30) + 40, 144, 20);
            touchpause4 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 72, ((Def.SCREEN_HEIGHT >> 1) - 30) + 60, 144, 20);
            MFDevice.addComponent(touchpause1);
            MFDevice.addComponent(touchpause2);
            MFDevice.addComponent(touchpause3);
            MFDevice.addComponent(touchpause4);
        }
    }

    public static void touchPauseClose(boolean IsRaceMode) {
        if (IsRaceMode) {
            MFDevice.removeComponent(touchpausearrowup);
            MFDevice.removeComponent(touchpausearrowdown);
            MFDevice.removeComponent(touchpausearrowupsingle);
            MFDevice.removeComponent(touchpausearrowdownsingle);
        }
        MFDevice.removeComponent(touchpause1);
        MFDevice.removeComponent(touchpause2);
        MFDevice.removeComponent(touchpause3);
        MFDevice.removeComponent(touchpause4);
        if (IsRaceMode) {
            touchpausearrowup = null;
            touchpausearrowdown = null;
            touchpausearrowupsingle = null;
            touchpausearrowdownsingle = null;
        }
        touchpause1 = null;
        touchpause2 = null;
        touchpause3 = null;
        touchpause4 = null;
    }

    public static void touchSelectStageInit() {
        if (touchselarrowup == null || touchselarrowdown == null || touchsel1 == null || touchsel2 == null || touchsel3 == null || touchsel4 == null || touchsel5 == null || touchsel6 == null) {
            touchselarrowup = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 72, (Def.SCREEN_HEIGHT >> 1) - 64, 24, 32);
            touchselarrowdown = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 72, (Def.SCREEN_HEIGHT >> 1) + 32, 24, 32);
            touchsel1 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, (Def.SCREEN_HEIGHT >> 1) - 72, 108, 24);
            touchsel2 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 72) + 24, 108, 24);
            touchsel3 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 72) + 48, 108, 24);
            touchsel4 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 72) + 72, 108, 24);
            touchsel5 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 72) + 96, 108, 24);
            touchsel6 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 72) + 120, 108, 24);
            MFDevice.addComponent(touchselarrowup);
            MFDevice.addComponent(touchselarrowdown);
            MFDevice.addComponent(touchsel1);
            MFDevice.addComponent(touchsel2);
            MFDevice.addComponent(touchsel3);
            MFDevice.addComponent(touchsel4);
            MFDevice.addComponent(touchsel5);
            MFDevice.addComponent(touchsel6);
        }
    }

    public static void touchSelectStageClose() {
        MFDevice.removeComponent(touchselarrowup);
        MFDevice.removeComponent(touchselarrowdown);
        MFDevice.removeComponent(touchsel1);
        MFDevice.removeComponent(touchsel2);
        MFDevice.removeComponent(touchsel3);
        MFDevice.removeComponent(touchsel4);
        MFDevice.removeComponent(touchsel5);
        MFDevice.removeComponent(touchsel6);
        touchselarrowup = null;
        touchselarrowdown = null;
        touchsel1 = null;
        touchsel2 = null;
        touchsel3 = null;
        touchsel4 = null;
        touchsel5 = null;
        touchsel6 = null;
    }

    public static void touchPauseOptionInit() {
        if (touchpauseopsound == null || touchpauseopvolplus == null || touchpauseopvolcut == null || touchpauseopsein == null || touchpauseopseout == null) {
            touchpauseopsound = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, (Def.SCREEN_HEIGHT >> 1) - 20, 128, 20);
            touchpauseopvolcut = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, ((Def.SCREEN_HEIGHT >> 1) - 20) + 20, 48, 20);
            touchpauseopvolplus = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 16, ((Def.SCREEN_HEIGHT >> 1) - 20) + 20, 48, 20);
            touchpauseopsein = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, ((Def.SCREEN_HEIGHT >> 1) - 20) + 20, 128, 20);
            touchpauseopseout = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, ((Def.SCREEN_HEIGHT >> 1) - 20) + 40, 128, 20);
            MFDevice.addComponent(touchpauseopsound);
            MFDevice.addComponent(touchpauseopvolcut);
            MFDevice.addComponent(touchpauseopvolplus);
            MFDevice.addComponent(touchpauseopsein);
            MFDevice.addComponent(touchpauseopseout);
        }
    }

    public static void touchPauseOptionClose() {
        MFDevice.removeComponent(touchpauseopsound);
        MFDevice.removeComponent(touchpauseopvolcut);
        MFDevice.removeComponent(touchpauseopvolplus);
        MFDevice.removeComponent(touchpauseopsein);
        MFDevice.removeComponent(touchpauseopseout);
        touchpauseopsound = null;
        touchpauseopvolcut = null;
        touchpauseopvolplus = null;
        touchpauseopsein = null;
        touchpauseopseout = null;
    }

    public static void touchOptionInit() {
        if (touchoptionleft == null || touchoptionright == null || touchoptiondif == null || touchoptionso == null || touchoptionvolcut == null || touchoptionvolplus == null || touchoptionsein == null || touchoptionseout == null || touchoptiontimein == null || touchoptiontimeout == null || touchoptionrein == null || touchoptionreout == null) {
            touchoptionleft = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 96, 30, 32, 20);
            touchoptionright = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 76, 30, 32, 20);
            touchoptiondif = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, (Def.SCREEN_HEIGHT >> 1) - 50, 128, 20);
            touchoptionso = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, ((Def.SCREEN_HEIGHT >> 1) - 50) + 20, 128, 20);
            touchoptionvolcut = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, ((Def.SCREEN_HEIGHT >> 1) - 50) + 40, 48, 20);
            touchoptionvolplus = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 16, ((Def.SCREEN_HEIGHT >> 1) - 50) + 40, 48, 20);
            touchoptionsein = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, ((Def.SCREEN_HEIGHT >> 1) - 50) + 40, 128, 20);
            touchoptionseout = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, ((Def.SCREEN_HEIGHT >> 1) - 50) + 60, 128, 20);
            touchoptiontimein = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, ((Def.SCREEN_HEIGHT >> 1) - 50) + 60, 128, 20);
            touchoptiontimeout = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, ((Def.SCREEN_HEIGHT >> 1) - 50) + 80, 128, 20);
            touchoptionrein = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, ((Def.SCREEN_HEIGHT >> 1) - 50) + 80, 128, 20);
            touchoptionreout = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, ((Def.SCREEN_HEIGHT >> 1) - 50) + 100, 128, 20);
            MFDevice.addComponent(touchoptionleft);
            MFDevice.addComponent(touchoptionright);
            MFDevice.addComponent(touchoptiondif);
            MFDevice.addComponent(touchoptionso);
            MFDevice.addComponent(touchoptionvolcut);
            MFDevice.addComponent(touchoptionvolplus);
            MFDevice.addComponent(touchoptionsein);
            MFDevice.addComponent(touchoptionseout);
            MFDevice.addComponent(touchoptiontimein);
            MFDevice.addComponent(touchoptiontimeout);
            MFDevice.addComponent(touchoptionrein);
            MFDevice.addComponent(touchoptionreout);
        }
    }

    public static void touchOptionClose() {
        MFDevice.removeComponent(touchoptionleft);
        MFDevice.removeComponent(touchoptionright);
        MFDevice.removeComponent(touchoptiondif);
        MFDevice.removeComponent(touchoptionso);
        MFDevice.removeComponent(touchoptionvolcut);
        MFDevice.removeComponent(touchoptionvolplus);
        MFDevice.removeComponent(touchoptionsein);
        MFDevice.removeComponent(touchoptionseout);
        MFDevice.removeComponent(touchoptiontimein);
        MFDevice.removeComponent(touchoptiontimeout);
        MFDevice.removeComponent(touchoptionrein);
        MFDevice.removeComponent(touchoptionreout);
        touchoptionleft = null;
        touchoptionright = null;
        touchoptiondif = null;
        touchoptionso = null;
        touchoptionvolcut = null;
        touchoptionvolplus = null;
        touchoptionsein = null;
        touchoptionseout = null;
        touchoptiontimein = null;
        touchoptiontimeout = null;
        touchoptionrein = null;
        touchoptionreout = null;
    }

    public static void touchMainMenuInit2() {
        if (touchmainmenu == null || touchmainmenustart == null || touchmainmenurace == null || touchmainmenuoption == null || touchmainmenuend == null || touchmainmenureturn == null || touchmainmenuup == null || touchmainmenudown == null || touchmainmenuitem == null) {
            touchmainmenu = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchmainmenustart = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, (Def.SCREEN_HEIGHT >> 1) + 2, 128, 20);
            touchmainmenuvs = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, (Def.SCREEN_HEIGHT >> 1) + 2 + 20, 128, 20);
            touchmainmenurace = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, (Def.SCREEN_HEIGHT >> 1) + 2 + 40, 128, 20);
            touchmainmenuoption = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, (Def.SCREEN_HEIGHT >> 1) + 2 + 60, 128, 20);
            touchmainmenuend = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 64, (Def.SCREEN_HEIGHT >> 1) + 2 + 80, 128, 20);
            touchmainmenureturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            touchmainmenuup = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 52, (Def.SCREEN_HEIGHT >> 1) + 0, 104, 28);
            touchmainmenudown = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 52, (Def.SCREEN_HEIGHT >> 1) + 52, 104, 28);
            touchmainmenuitem = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 52, (Def.SCREEN_HEIGHT >> 1) + 28, 104, 24);
            MFDevice.addComponent(touchmainmenu);
            MFDevice.addComponent(touchmainmenustart);
            MFDevice.addComponent(touchmainmenuvs);
            MFDevice.addComponent(touchmainmenurace);
            MFDevice.addComponent(touchmainmenuoption);
            MFDevice.addComponent(touchmainmenuend);
            MFDevice.addComponent(touchmainmenureturn);
            MFDevice.addComponent(touchmainmenuup);
            MFDevice.addComponent(touchmainmenudown);
            MFDevice.addComponent(touchmainmenuitem);
        }
        touchmainmenu.resetKeyState();
        touchmainmenustart.resetKeyState();
        touchmainmenuvs.resetKeyState();
        touchmainmenurace.resetKeyState();
        touchmainmenuoption.resetKeyState();
        touchmainmenuend.resetKeyState();
        touchmainmenureturn.resetKeyState();
        touchmainmenuup.resetKeyState();
        touchmainmenudown.resetKeyState();
        touchmainmenuitem.resetKeyState();
    }

    public static void touchMainMenuReset2() {
        if (touchmainmenu != null) {
            touchmainmenu.resetKeyState();
            touchmainmenustart.resetKeyState();
            touchmainmenuvs.resetKeyState();
            touchmainmenurace.resetKeyState();
            touchmainmenuoption.resetKeyState();
            touchmainmenuend.resetKeyState();
            touchmainmenureturn.resetKeyState();
            touchmainmenuup.resetKeyState();
            touchmainmenudown.resetKeyState();
            touchmainmenuitem.resetKeyState();
        }
    }

    public static void touchMainMenuClose2() {
        MFDevice.removeComponent(touchmainmenu);
        MFDevice.removeComponent(touchmainmenustart);
        MFDevice.removeComponent(touchmainmenuvs);
        MFDevice.removeComponent(touchmainmenurace);
        MFDevice.removeComponent(touchmainmenuoption);
        MFDevice.removeComponent(touchmainmenuend);
        MFDevice.removeComponent(touchmainmenureturn);
        MFDevice.removeComponent(touchmainmenuup);
        MFDevice.removeComponent(touchmainmenudown);
        MFDevice.removeComponent(touchmainmenuitem);
        touchmainmenu = null;
        touchmainmenustart = null;
        touchmainmenuvs = null;
        touchmainmenurace = null;
        touchmainmenuoption = null;
        touchmainmenuend = null;
        touchmainmenureturn = null;
        touchmainmenuup = null;
        touchmainmenudown = null;
        touchmainmenuitem = null;
    }

    public static void touchProRaceModeInit() {
        if (touchproracemode == null || touchproracemodereturn == null || touchproracemodestart == null || touchproracemoderecord == null) {
            touchproracemode = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchproracemodestart = new TouchKeyRange(0, (Def.SCREEN_HEIGHT >> 1) - 28, Def.SCREEN_WIDTH, 24);
            touchproracemoderecord = new TouchKeyRange(0, (Def.SCREEN_HEIGHT >> 1) - 4, Def.SCREEN_WIDTH, 24);
            touchproracemodereturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchproracemode);
            MFDevice.addComponent(touchproracemodestart);
            MFDevice.addComponent(touchproracemoderecord);
            MFDevice.addComponent(touchproracemodereturn);
        }
        touchproracemode.resetKeyState();
        touchproracemodestart.resetKeyState();
        touchproracemoderecord.resetKeyState();
        touchproracemodereturn.resetKeyState();
    }

    public static void touchProRaceModeClose() {
        MFDevice.removeComponent(touchproracemode);
        MFDevice.removeComponent(touchproracemodestart);
        MFDevice.removeComponent(touchproracemoderecord);
        MFDevice.removeComponent(touchproracemodereturn);
        touchproracemodereturn = null;
        touchproracemode = null;
        touchproracemodestart = null;
        touchproracemoderecord = null;
    }

    public static void touchCharacterSelectModeInit() {
        if (touchcharsel == null || touchcharselreturn == null || touchcharselselect == null || slidesensorcharsel == null || touchcharselleftarrow == null || touchcharselrightarrow == null) {
            touchcharsel = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchcharselselect = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 80, (Def.SCREEN_HEIGHT >> 1) - 56, 160, 96);
            slidesensorcharsel = new MFSlideSensor(0, (Def.SCREEN_HEIGHT >> 1) - 56, Def.SCREEN_WIDTH, 96, 64);
            touchcharselreturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            touchcharselleftarrow = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 120, (Def.SCREEN_HEIGHT >> 1) - 44, 48, 44);
            touchcharselrightarrow = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 72, (Def.SCREEN_HEIGHT >> 1) - 44, 48, 44);
            MFDevice.addComponent(touchcharsel);
            MFDevice.addComponent(touchcharselselect);
            MFDevice.addComponent(slidesensorcharsel);
            MFDevice.addComponent(touchcharselreturn);
            MFDevice.addComponent(touchcharselleftarrow);
            MFDevice.addComponent(touchcharselrightarrow);
        }
        touchcharsel.resetKeyState();
        touchcharselselect.resetKeyState();
        touchcharselreturn.resetKeyState();
        touchcharselleftarrow.resetKeyState();
        touchcharselrightarrow.resetKeyState();
    }

    public static void touchCharacterSelectModeClose() {
        MFDevice.removeComponent(touchcharsel);
        MFDevice.removeComponent(touchcharselselect);
        MFDevice.removeComponent(slidesensorcharsel);
        MFDevice.removeComponent(touchcharselreturn);
        MFDevice.removeComponent(touchcharselleftarrow);
        MFDevice.removeComponent(touchcharselrightarrow);
        touchcharsel = null;
        touchcharselselect = null;
        slidesensorcharsel = null;
        touchcharselreturn = null;
        touchcharselleftarrow = null;
        touchcharselrightarrow = null;
    }

    public static void touchStageSelectModeInit() {
        if (slidesensorstagesel == null || touchstageselect == null || touchstageselectreturn == null || touchstageselectuparrow == null || touchstageselectdownarrow == null) {
            slidesensorstagesel = new MFSlideSensor(64, 0, (Def.SCREEN_WIDTH - 64) - 52, Def.SCREEN_HEIGHT, 8);
            touchstageselect = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchstageselectitem = new TouchKeyRange[14];
            for (int i = 0; i < touchstageselectitem.length; i++) {
                touchstageselectitem[i] = new TouchKeyRange(64, ((i + 1) * 24) - 12, (Def.SCREEN_WIDTH - 64) - 52, 24);
            }
            touchstageselectreturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            touchstageselectuparrow = new TouchKeyRange(24, (Def.SCREEN_HEIGHT >> 1) - 80, 40, 48);
            touchstageselectdownarrow = new TouchKeyRange(24, (Def.SCREEN_HEIGHT >> 1) + 32, 40, 48);
            MFDevice.addComponent(slidesensorstagesel);
            MFDevice.addComponent(touchstageselect);
            for (int i2 = 0; i2 < touchstageselectitem.length; i2++) {
                MFDevice.addComponent(touchstageselectitem[i2]);
            }
            MFDevice.addComponent(touchstageselectreturn);
            MFDevice.addComponent(touchstageselectuparrow);
            MFDevice.addComponent(touchstageselectdownarrow);
        }
        touchstageselect.resetKeyState();
        for (int i3 = 0; i3 < touchstageselectitem.length; i3++) {
            touchstageselectitem[i3].resetKeyState();
        }
        touchstageselectreturn.resetKeyState();
        touchstageselectuparrow.resetKeyState();
        touchstageselectdownarrow.resetKeyState();
    }

    public static void touchStageSelectModeClose() {
        MFDevice.removeComponent(slidesensorstagesel);
        MFDevice.removeComponent(touchstageselect);
        for (int i = 0; i < touchstageselectitem.length; i++) {
            MFDevice.removeComponent(touchstageselectitem[i]);
        }
        MFDevice.removeComponent(touchstageselectreturn);
        MFDevice.removeComponent(touchstageselectuparrow);
        MFDevice.removeComponent(touchstageselectdownarrow);
        slidesensorstagesel = null;
        touchstageselect = null;
        for (int i2 = 0; i2 < touchstageselectitem.length; i2++) {
            touchstageselectitem[i2] = null;
        }
        touchstageselectitem = null;
        touchstageselectreturn = null;
        touchstageselectuparrow = null;
        touchstageselectdownarrow = null;
    }

    public static void touchCharacterRecordInit() {
        if (slidesensorcharacterrecord == null || touchintergraderecord == null || touchintergraderecordreturn == null || touchintergraderecordleftarrow == null || touchintergraderecordrightarrow == null || touchintergraderecordupdate == null) {
            slidesensorcharacterrecord = new MFSlideSensor(0, (Def.SCREEN_HEIGHT >> 1) - 48, Def.SCREEN_WIDTH, 112, Def.SCREEN_WIDTH >> 1);
            touchintergraderecord = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchintergraderecordreturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            touchintergraderecordleftarrow = new TouchKeyRange(0, (Def.SCREEN_HEIGHT >> 1) - 12, 60, 60);
            touchintergraderecordrightarrow = new TouchKeyRange(Def.SCREEN_WIDTH - 60, (Def.SCREEN_HEIGHT >> 1) - 12, 60, 60);
            touchintergraderecordupdate = new TouchKeyRange(Def.SCREEN_WIDTH - 50, 0, 50, 32);
            MFDevice.addComponent(slidesensorcharacterrecord);
            MFDevice.addComponent(touchintergraderecord);
            MFDevice.addComponent(touchintergraderecordreturn);
            MFDevice.addComponent(touchintergraderecordleftarrow);
            MFDevice.addComponent(touchintergraderecordrightarrow);
            MFDevice.addComponent(touchintergraderecordupdate);
        }
        touchintergraderecord.resetKeyState();
        touchintergraderecordreturn.resetKeyState();
        touchintergraderecordleftarrow.resetKeyState();
        touchintergraderecordrightarrow.resetKeyState();
        touchintergraderecordupdate.resetKeyState();
    }

    public static void touchCharacterRecordClose() {
        MFDevice.removeComponent(slidesensorcharacterrecord);
        MFDevice.removeComponent(touchintergraderecord);
        MFDevice.removeComponent(touchintergraderecordreturn);
        MFDevice.removeComponent(touchintergraderecordleftarrow);
        MFDevice.removeComponent(touchintergraderecordrightarrow);
        MFDevice.removeComponent(touchintergraderecordupdate);
        slidesensorcharacterrecord = null;
        touchintergraderecord = null;
        touchintergraderecordreturn = null;
        touchintergraderecordleftarrow = null;
        touchintergraderecordrightarrow = null;
        touchintergraderecordupdate = null;
    }

    public static void touchGamePauseInit(int type) {
        if (touchgamepause == null || touchgamepauseitem == null || touchgamepausereturn == null) {
            touchgamepause = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchgamepauseitem = new TouchKeyRange[6];
            int itemsstarty = 0;
            if (type == 0) {
                itemsstarty = ((Def.SCREEN_HEIGHT >> 1) - 36) - 12;
            } else if (type == 1) {
                itemsstarty = ((Def.SCREEN_HEIGHT >> 1) - 60) - 12;
            }
            MFDevice.addComponent(touchgamepause);
            for (int i = 0; i < touchgamepauseitem.length; i++) {
                touchgamepauseitem[i] = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 44, (i * 24) + itemsstarty, 136, 24);
                MFDevice.addComponent(touchgamepauseitem[i]);
            }
            touchgamepausereturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchgamepausereturn);
        }
        touchgamepause.resetKeyState();
        touchgamepausereturn.resetKeyState();
        for (int i2 = 0; i2 < touchgamepauseitem.length; i2++) {
            touchgamepauseitem[i2].resetKeyState();
        }
    }

    public static void touchGamePauseClose() {
        if (touchgamepause != null) {
            MFDevice.removeComponent(touchgamepause);
            touchgamepause = null;
            for (int i = 0; i < touchgamepauseitem.length; i++) {
                MFDevice.removeComponent(touchgamepauseitem[i]);
                touchgamepauseitem[i] = null;
            }
            touchgamepauseitem = null;
            MFDevice.removeComponent(touchgamepausereturn);
            touchgamepausereturn = null;
        }
    }

    public static void touchPadOptionInit() {
        if (touchpadoption == null || touchpadoptionleft1 == null || touchpadoptionleft2 == null || touchpadoptionright1 == null || touchpadoptionright2 == null || touchpadoptionplus == null || touchpadoptionminus == null || touchpadoptionreturn == null) {
            touchpadoption = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchpadoptionleft1 = new TouchKeyRange(8, 66, 52, 28);
            touchpadoptionleft2 = new TouchKeyRange(Def.SCREEN_WIDTH - 116, 66, 52, 28);
            touchpadoptionright1 = new TouchKeyRange(64, 66, 52, 28);
            touchpadoptionright2 = new TouchKeyRange(Def.SCREEN_WIDTH - 60, 66, 52, 28);
            touchpadoptionplus = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 40, Def.SCREEN_HEIGHT - 62, 80, 28);
            touchpadoptionminus = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 40, Def.SCREEN_HEIGHT - 34, 80, 28);
            touchpadoptionreturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchpadoption);
            MFDevice.addComponent(touchpadoptionleft1);
            MFDevice.addComponent(touchpadoptionleft2);
            MFDevice.addComponent(touchpadoptionright1);
            MFDevice.addComponent(touchpadoptionright2);
            MFDevice.addComponent(touchpadoptionplus);
            MFDevice.addComponent(touchpadoptionminus);
            MFDevice.addComponent(touchpadoptionreturn);
        }
        touchpadoption.resetKeyState();
        touchpadoptionleft1.resetKeyState();
        touchpadoptionleft2.resetKeyState();
        touchpadoptionright1.resetKeyState();
        touchpadoptionright2.resetKeyState();
        touchpadoptionplus.resetKeyState();
        touchpadoptionminus.resetKeyState();
        touchpadoptionreturn.resetKeyState();
    }

    public static void touchPadOptionClose() {
        MFDevice.removeComponent(touchpadoption);
        MFDevice.removeComponent(touchpadoptionleft1);
        MFDevice.removeComponent(touchpadoptionleft2);
        MFDevice.removeComponent(touchpadoptionright1);
        MFDevice.removeComponent(touchpadoptionright2);
        MFDevice.removeComponent(touchpadoptionplus);
        MFDevice.removeComponent(touchpadoptionminus);
        MFDevice.removeComponent(touchpadoptionreturn);
        touchpadoption = null;
        touchpadoptionleft1 = null;
        touchpadoptionleft2 = null;
        touchpadoptionright1 = null;
        touchpadoptionright2 = null;
        touchpadoptionplus = null;
        touchpadoptionminus = null;
        touchpadoptionreturn = null;
    }

    public static void touchSecondEnsureInit() {
        if (touchsecondensure == null || touchsecondensureyes == null || touchsecondensureno == null || touchsecondensurereturn == null) {
            touchsecondensure = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchsecondensureyes = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 80, (Def.SCREEN_HEIGHT >> 1) + 24, 80, 32);
            touchsecondensureno = new TouchKeyRange(Def.SCREEN_WIDTH >> 1, (Def.SCREEN_HEIGHT >> 1) + 24, 80, 32);
            touchsecondensurereturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchsecondensure);
            MFDevice.addComponent(touchsecondensureyes);
            MFDevice.addComponent(touchsecondensureno);
            MFDevice.addComponent(touchsecondensurereturn);
        }
        touchsecondensure.resetKeyState();
        touchsecondensureyes.resetKeyState();
        touchsecondensureno.resetKeyState();
        touchsecondensurereturn.resetKeyState();
    }

    public static void touchSecondEnsureClose() {
        MFDevice.removeComponent(touchsecondensure);
        MFDevice.removeComponent(touchsecondensureyes);
        MFDevice.removeComponent(touchsecondensureno);
        MFDevice.removeComponent(touchsecondensurereturn);
        touchsecondensure = null;
        touchsecondensureyes = null;
        touchsecondensureno = null;
        touchsecondensurereturn = null;
    }

    public static void touchStartGameInit() {
        if (touchstartgame == null || touchstartgamecontinue == null || touchstartgamenew == null || touchstartgamereturn == null) {
            touchstartgame = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchstartgamecontinue = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, (Def.SCREEN_HEIGHT >> 1) - 36, 96, 36);
            touchstartgamenew = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, Def.SCREEN_HEIGHT >> 1, 96, 36);
            touchstartgamereturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchstartgame);
            MFDevice.addComponent(touchstartgamecontinue);
            MFDevice.addComponent(touchstartgamenew);
            MFDevice.addComponent(touchstartgamereturn);
        }
        touchstartgame.resetKeyState();
        touchstartgamecontinue.resetKeyState();
        touchstartgamenew.resetKeyState();
        touchstartgamereturn.resetKeyState();
    }

    public static void touchStartGameClose() {
        MFDevice.removeComponent(touchstartgame);
        MFDevice.removeComponent(touchstartgamecontinue);
        MFDevice.removeComponent(touchstartgamenew);
        MFDevice.removeComponent(touchstartgamereturn);
        touchstartgame = null;
        touchstartgamecontinue = null;
        touchstartgamenew = null;
        touchstartgamereturn = null;
    }

    public static void touchGamePauseOptionInit() {
        if (touchpauseoption == null || touchpauseoptionsound == null || touchpauseoptionvib == null || touchpauseoptionkeyset == null || touchpauseoptionspset == null || touchpauseoptionhelp == null || touchpauseoptionreturn == null) {
            touchpauseoptionleft = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 96, 30, 32, 20);
            touchpauseoptionright = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 76, 30, 32, 20);
            touchpauseoption = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchpauseoptionsound = new TouchKeyRange(((Def.SCREEN_WIDTH >> 1) + 56) - 56, 28, 112, 24);
            touchpauseoptionvib = new TouchKeyRange(((Def.SCREEN_WIDTH >> 1) + 56) - 56, 52, 112, 24);
            touchpauseoptionkeyset = new TouchKeyRange(((Def.SCREEN_WIDTH >> 1) - 56) - 112, 76, 224, 24);
            touchpauseoptionspset = new TouchKeyRange(((Def.SCREEN_WIDTH >> 1) + 56) - 56, 100, 112, 24);
            touchpauseoptionhelp = new TouchKeyRange(((Def.SCREEN_WIDTH >> 1) - 56) - 112, 124, 224, 24);
            touchpauseoptionreturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchpauseoptionleft);
            MFDevice.addComponent(touchpauseoptionright);
            MFDevice.addComponent(touchpauseoption);
            MFDevice.addComponent(touchpauseoptionsound);
            MFDevice.addComponent(touchpauseoptionvib);
            MFDevice.addComponent(touchpauseoptionkeyset);
            MFDevice.addComponent(touchpauseoptionspset);
            MFDevice.addComponent(touchpauseoptionhelp);
            MFDevice.addComponent(touchpauseoptionreturn);
        }
        touchpauseoptionleft.resetKeyState();
        touchpauseoptionright.resetKeyState();
        touchpauseoption.resetKeyState();
        touchpauseoptionsound.resetKeyState();
        touchpauseoptionvib.resetKeyState();
        touchpauseoptionkeyset.resetKeyState();
        touchpauseoptionspset.resetKeyState();
        touchpauseoptionhelp.resetKeyState();
        touchpauseoptionreturn.resetKeyState();
    }

    public static void touchGamePauseOptionClose() {
        MFDevice.removeComponent(touchpauseoptionleft);
        MFDevice.removeComponent(touchpauseoptionright);
        MFDevice.removeComponent(touchpauseoption);
        MFDevice.removeComponent(touchpauseoptionsound);
        MFDevice.removeComponent(touchpauseoptionvib);
        MFDevice.removeComponent(touchpauseoptionkeyset);
        MFDevice.removeComponent(touchpauseoptionspset);
        MFDevice.removeComponent(touchpauseoptionhelp);
        MFDevice.removeComponent(touchpauseoptionreturn);
        touchpauseoptionleft = null;
        touchpauseoptionright = null;
        touchpauseoption = null;
        touchpauseoptionsound = null;
        touchpauseoptionvib = null;
        touchpauseoptionkeyset = null;
        touchpauseoptionspset = null;
        touchpauseoptionhelp = null;
        touchpauseoptionreturn = null;
    }

    public static void touchMenuOptionInit() {
        if (touchmenuoption == null || slidesensormenuoption == null || touchmenuoptionitems == null || touchmenuoptionreturn == null || touchmenuoptionuparrow == null || touchmenuoptiondownarrow == null || touchmenuoptionleftarrow == null || touchmenuoptionrightarrow == null) {
            touchmenuoption = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            slidesensormenuoption = new MFSlideSensor(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT, 4);
            touchmenuoptionitems = new TouchKeyRange[22];
            for (int i = 0; i < (touchmenuoptionitems.length >> 1); i++) {
                touchmenuoptionitems[i * 2] = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 100, (i * 24) + 28, 100, 24);
                touchmenuoptionitems[(i * 2) + 1] = new TouchKeyRange(Def.SCREEN_WIDTH >> 1, (i * 24) + 28, 112, 24);
            }
            touchmenuoptionreturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchmenuoption);
            MFDevice.addComponent(slidesensormenuoption);
            MFDevice.addComponent(touchmenuoptionreturn);
            for (int i2 = 0; i2 < touchmenuoptionitems.length; i2++) {
                MFDevice.addComponent(touchmenuoptionitems[i2]);
            }
            touchmenuoptionuparrow = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 124, (Def.SCREEN_HEIGHT >> 1) - 41, 24, 32);
            touchmenuoptiondownarrow = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 124, (Def.SCREEN_HEIGHT >> 1) + 24, 24, 32);
            touchmenuoptionleftarrow = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 96, 30, 32, 20);
            touchmenuoptionrightarrow = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 76, 30, 32, 20);
            MFDevice.addComponent(touchmenuoptionuparrow);
            MFDevice.addComponent(touchmenuoptiondownarrow);
            MFDevice.addComponent(touchmenuoptionleftarrow);
            MFDevice.addComponent(touchmenuoptionrightarrow);
        }
        touchmenuoption.resetKeyState();
        touchmenuoptionreturn.resetKeyState();
        for (int i3 = 0; i3 < touchmenuoptionitems.length; i3++) {
            touchmenuoptionitems[i3].resetKeyState();
        }
    }

    public static void touchMenuOptionClose() {
        MFDevice.removeComponent(touchmenuoption);
        MFDevice.removeComponent(slidesensormenuoption);
        MFDevice.removeComponent(touchmenuoptionreturn);
        for (int i = 0; i < touchmenuoptionitems.length; i++) {
            MFDevice.removeComponent(touchmenuoptionitems[i]);
            touchmenuoptionitems[i] = null;
        }
        touchmenuoptionitems = null;
        touchmenuoption = null;
        slidesensormenuoption = null;
        touchmenuoptionreturn = null;
        MFDevice.removeComponent(touchmenuoptionuparrow);
        MFDevice.removeComponent(touchmenuoptiondownarrow);
        MFDevice.removeComponent(touchmenuoptionleftarrow);
        MFDevice.removeComponent(touchmenuoptionrightarrow);
        touchmenuoptionuparrow = null;
        touchmenuoptiondownarrow = null;
        touchmenuoptionleftarrow = null;
        touchmenuoptionrightarrow = null;
    }

    public static void touchItemsSelect2Init() {
        if (touchitemsselect2 == null || touchitemsselect2_1 == null || touchitemsselect2_2 == null || touchitemsselect2_return == null) {
            touchitemsselect2 = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchitemsselect2_1 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, (Def.SCREEN_HEIGHT >> 1) - 36, 96, 36);
            touchitemsselect2_2 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, Def.SCREEN_HEIGHT >> 1, 96, 36);
            touchitemsselect2_return = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchitemsselect2);
            MFDevice.addComponent(touchitemsselect2_1);
            MFDevice.addComponent(touchitemsselect2_2);
            MFDevice.addComponent(touchitemsselect2_return);
        }
        touchitemsselect2.resetKeyState();
        touchitemsselect2_1.resetKeyState();
        touchitemsselect2_2.resetKeyState();
        touchitemsselect2_return.resetKeyState();
    }

    public static void touchItemsSelect2Close() {
        MFDevice.removeComponent(touchitemsselect2);
        MFDevice.removeComponent(touchitemsselect2_1);
        MFDevice.removeComponent(touchitemsselect2_2);
        MFDevice.removeComponent(touchitemsselect2_return);
        touchitemsselect2 = null;
        touchitemsselect2_1 = null;
        touchitemsselect2_2 = null;
        touchitemsselect2_return = null;
    }

    public static void touchItemsSelect3Init() {
        if (touchitemsselect3 == null || touchitemsselect3_1 == null || touchitemsselect3_2 == null || touchitemsselect3_3 == null || touchitemsselect3_return == null) {
            touchitemsselect3 = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchitemsselect3_1 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 36) - 18, 96, 36);
            touchitemsselect3_2 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 36) + 18, 96, 36);
            touchitemsselect3_3 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 36) + 54, 96, 36);
            touchitemsselect3_return = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchitemsselect3);
            MFDevice.addComponent(touchitemsselect3_1);
            MFDevice.addComponent(touchitemsselect3_2);
            MFDevice.addComponent(touchitemsselect3_3);
            MFDevice.addComponent(touchitemsselect3_return);
        }
        touchitemsselect3.resetKeyState();
        touchitemsselect3_1.resetKeyState();
        touchitemsselect3_2.resetKeyState();
        touchitemsselect3_3.resetKeyState();
        touchitemsselect3_return.resetKeyState();
    }

    public static void touchItemsSelect3Close() {
        MFDevice.removeComponent(touchitemsselect3);
        MFDevice.removeComponent(touchitemsselect3_1);
        MFDevice.removeComponent(touchitemsselect3_2);
        MFDevice.removeComponent(touchitemsselect3_3);
        MFDevice.removeComponent(touchitemsselect3_return);
        touchitemsselect3 = null;
        touchitemsselect3_1 = null;
        touchitemsselect3_2 = null;
        touchitemsselect3_3 = null;
        touchitemsselect3_return = null;
    }

    public static void touchitemsselect4Init() {
        if (touchitemsselect4 == null || touchitemsselect4_1 == null || touchitemsselect4_2 == null || touchitemsselect4_3 == null || touchitemsselect4_4 == null || touchitemsselect4_return == null) {
            touchitemsselect4 = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchitemsselect4_1 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 28) - 31, 96, 28);
            touchitemsselect4_2 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 28) - 1, 96, 28);
            touchitemsselect4_3 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 28) + 29, 96, 28);
            touchitemsselect4_4 = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 28) + 59, 96, 28);
            touchitemsselect4_return = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchitemsselect4);
            MFDevice.addComponent(touchitemsselect4_1);
            MFDevice.addComponent(touchitemsselect4_2);
            MFDevice.addComponent(touchitemsselect4_3);
            MFDevice.addComponent(touchitemsselect4_4);
            MFDevice.addComponent(touchitemsselect4_return);
        }
        touchitemsselect4.resetKeyState();
        touchitemsselect4_1.resetKeyState();
        touchitemsselect4_2.resetKeyState();
        touchitemsselect4_3.resetKeyState();
        touchitemsselect4_4.resetKeyState();
        touchitemsselect4_return.resetKeyState();
    }

    public static void touchitemsselect4Close() {
        MFDevice.removeComponent(touchitemsselect4);
        MFDevice.removeComponent(touchitemsselect4_1);
        MFDevice.removeComponent(touchitemsselect4_2);
        MFDevice.removeComponent(touchitemsselect4_3);
        MFDevice.removeComponent(touchitemsselect4_4);
        MFDevice.removeComponent(touchitemsselect4_return);
        touchitemsselect4 = null;
        touchitemsselect4_1 = null;
        touchitemsselect4_2 = null;
        touchitemsselect4_3 = null;
        touchitemsselect4_4 = null;
        touchitemsselect4_return = null;
    }

    public static void touchInstructionInit() {
        if (slidesensorhelp == null || touchhelpreturn == null || touchhelpuparrow == null || touchhelpdownarrow == null || touchpage == null || touchhelpleftarrow == null || touchhelprightarrow == null) {
            touchpage = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            slidesensorhelp = new MFSlideSensor((Def.SCREEN_WIDTH >> 1) - 104, 0, 208, Def.SCREEN_HEIGHT, 20);
            touchhelpreturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            touchhelpuparrow = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 40, Def.SCREEN_HEIGHT - 24, 32, 24);
            touchhelpdownarrow = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 8, Def.SCREEN_HEIGHT - 24, 32, 24);
            touchhelpleftarrow = new TouchKeyRange(0, (Def.SCREEN_HEIGHT >> 1) - 24, 40, 40);
            touchhelprightarrow = new TouchKeyRange(Def.SCREEN_WIDTH - 40, (Def.SCREEN_HEIGHT >> 1) - 24, 40, 40);
            MFDevice.addComponent(touchpage);
            MFDevice.addComponent(slidesensorhelp);
            MFDevice.addComponent(touchhelpreturn);
            MFDevice.addComponent(touchhelpuparrow);
            MFDevice.addComponent(touchhelpdownarrow);
            MFDevice.addComponent(touchhelpleftarrow);
            MFDevice.addComponent(touchhelprightarrow);
        }
        touchpage.resetKeyState();
        touchhelpreturn.resetKeyState();
        touchhelpuparrow.resetKeyState();
        touchhelpdownarrow.resetKeyState();
        touchhelpleftarrow.resetKeyState();
        touchhelprightarrow.resetKeyState();
    }

    public static void touchInstructionClose() {
        MFDevice.removeComponent(touchpage);
        MFDevice.removeComponent(slidesensorhelp);
        MFDevice.removeComponent(touchhelpreturn);
        MFDevice.removeComponent(touchhelpuparrow);
        MFDevice.removeComponent(touchhelpdownarrow);
        MFDevice.removeComponent(touchhelpleftarrow);
        MFDevice.removeComponent(touchhelprightarrow);
        touchpage = null;
        slidesensorhelp = null;
        touchhelpreturn = null;
        touchhelpuparrow = null;
        touchhelpdownarrow = null;
        touchhelpleftarrow = null;
        touchhelprightarrow = null;
    }

    public static void touchMenuOptionLanguageInit() {
        if (touchmenuoptionlanguage == null || touchmenuoptionlanguageitems == null || touchmenuoptionlanguagereturn == null) {
            touchmenuoptionlanguage = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            MFDevice.addComponent(touchmenuoptionlanguage);
            touchmenuoptionlanguageitems = new TouchKeyRange[5];
            for (int i = 0; i < 5; i++) {
                touchmenuoptionlanguageitems[i] = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, ((Def.SCREEN_HEIGHT >> 1) - 60) + (i * 36), 96, 36);
                MFDevice.addComponent(touchmenuoptionlanguageitems[i]);
            }
            touchmenuoptionlanguagereturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchmenuoptionlanguagereturn);
        }
        touchmenuoptionlanguage.resetKeyState();
        touchmenuoptionlanguagereturn.resetKeyState();
        for (int i2 = 0; i2 < 5; i2++) {
            touchmenuoptionlanguageitems[i2].resetKeyState();
        }
    }

    public static void touchMenuOptionLanguageClose() {
        MFDevice.removeComponent(touchmenuoptionlanguage);
        touchmenuoptionlanguage = null;
        for (int i = 0; i < 5; i++) {
            MFDevice.removeComponent(touchmenuoptionlanguageitems[i]);
            touchmenuoptionlanguageitems[i] = null;
        }
        touchmenuoptionlanguageitems = null;
        MFDevice.removeComponent(touchmenuoptionlanguagereturn);
        touchmenuoptionlanguagereturn = null;
    }

    public static void touchOpeningInit() {
        if (touchopening == null || touchopeningskip == null) {
            touchopening = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchopeningskip = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchopening);
            MFDevice.addComponent(touchopeningskip);
        }
        touchopening.resetKeyState();
        touchopeningskip.resetKeyState();
    }

    public static void touchOpeningClose() {
        MFDevice.removeComponent(touchopening);
        MFDevice.removeComponent(touchopeningskip);
        touchopening = null;
        touchopeningskip = null;
    }

    public static void touchInterruptInit() {
        if (touchinterruptreturn == null) {
            touchinterruptreturn = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 48, (Def.SCREEN_HEIGHT >> 1) - 12, 96, 24);
            MFDevice.addComponent(touchinterruptreturn);
        }
        touchinterruptreturn.resetKeyState();
    }

    public static void touchInterruptClose() {
        MFDevice.removeComponent(touchinterruptreturn);
        touchinterruptreturn = null;
    }

    public static void touchSPstageInit() {
        if (touchspstagepause == null || touchspstage == null) {
            touchspstage = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchspstagepause = new TouchKeyRange(MyAPI.zoomOut(pause_x), MyAPI.zoomOut(pause_y), MyAPI.zoomOut(60), MyAPI.zoomOut(30));
            MFDevice.addComponent(touchspstage);
            MFDevice.addComponent(touchspstagepause);
        }
        touchspstage.resetKeyState();
        touchspstagepause.resetKeyState();
        touchstarpoundkeyInit();
    }

    public static void touchSPstageClose() {
        MFDevice.removeComponent(touchspstage);
        MFDevice.removeComponent(touchspstagepause);
        touchspstage = null;
        touchspstagepause = null;
    }

    public static void touchgameoverensurekeyInit() {
        touchkeyboardClose();
        if (touchgameover == null || touchgameoveryres == null || touchgameoverno == null) {
            touchgameover = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchgameoveryres = new TouchKeyRange(MyAPI.zoomOut(Def.SCREEN_WIDTH - 42), MyAPI.zoomOut(103), MyAPI.zoomOut(40), MyAPI.zoomOut(40));
            touchgameoverno = new TouchKeyRange(MyAPI.zoomOut(Def.SCREEN_WIDTH - 87), MyAPI.zoomOut(118), MyAPI.zoomOut(40), MyAPI.zoomOut(40));
            MFDevice.addComponent(touchgameover);
            MFDevice.addComponent(touchgameoveryres);
            MFDevice.addComponent(touchgameoverno);
        }
        touchgameover.resetKeyState();
        touchgameoveryres.resetKeyState();
        touchgameoverno.resetKeyState();
    }

    public static void touchgameoverensurekeyClose() {
        if (touchgameover != null) {
            MFDevice.removeComponent(touchgameover);
            MFDevice.removeComponent(touchgameoveryres);
            MFDevice.removeComponent(touchgameoverno);
            touchgameover = null;
            touchgameoveryres = null;
            touchgameoverno = null;
        }
    }

    public static void touchscoreupdatekeyInit() {
        if (touchscoreupdate == null || touchscoreupdateyes == null || touchscoreupdateno == null || touchscoreupdatereturn == null) {
            touchscoreupdate = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchscoreupdateyes = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) - 108, (Def.SCREEN_HEIGHT >> 1) + 10, 96, 28);
            touchscoreupdateno = new TouchKeyRange((Def.SCREEN_WIDTH >> 1) + 12, (Def.SCREEN_HEIGHT >> 1) + 10, 96, 28);
            touchscoreupdatereturn = new TouchKeyRange(0, Def.SCREEN_HEIGHT - 24, 24, 24);
            MFDevice.addComponent(touchscoreupdate);
            MFDevice.addComponent(touchscoreupdateyes);
            MFDevice.addComponent(touchscoreupdateno);
            MFDevice.addComponent(touchscoreupdatereturn);
        }
        touchscoreupdate.resetKeyState();
        touchscoreupdateyes.resetKeyState();
        touchscoreupdateno.resetKeyState();
        touchscoreupdatereturn.resetKeyState();
    }

    public static void touchscoreupdatekeyClose() {
        if (touchscoreupdate != null) {
            MFDevice.removeComponent(touchscoreupdate);
            MFDevice.removeComponent(touchscoreupdateyes);
            MFDevice.removeComponent(touchscoreupdateno);
            MFDevice.removeComponent(touchscoreupdatereturn);
            touchscoreupdate = null;
            touchscoreupdateyes = null;
            touchscoreupdateno = null;
            touchscoreupdatereturn = null;
        }
    }

    public static void touchgamekeyInit() {
    }

    public static void touchgamekeyClose() {
    }

    public static void touchkeypauseInit() {
        if (touchkey_pause == null || touchgame == null) {
            touchgame = new TouchKeyRange(0, 0, Def.SCREEN_WIDTH, Def.SCREEN_HEIGHT);
            touchkey_pause = new TouchKeyRange(MyAPI.zoomOut(pause_x), MyAPI.zoomOut(pause_y), MyAPI.zoomOut(60), MyAPI.zoomOut(30));
            MFDevice.addComponent(touchgame);
            MFDevice.addComponent(touchkey_pause);
        }
        touchgame.resetKeyState();
        touchkey_pause.resetKeyState();
    }

    public static void touchkeypauseClose() {
        MFDevice.removeComponent(touchgame);
        MFDevice.removeComponent(touchkey_pause);
        touchgame = null;
        touchkey_pause = null;
    }

    public static void init() {
        gUp = B_UP | B_2;
        gDown = B_DOWN | B_8;
        gLeft = B_LEFT | B_4;
        gRight = B_RIGHT | B_6;
        gSelect = B_SEL | B_5;
    }

    public static void initSonic() {
        gUp = B_UP | B_2 | B_3 | B_1;
        gDown = B_DOWN | B_8;
        gLeft = B_LEFT | B_4 | B_1;
        gRight = B_RIGHT | B_6 | B_3;
        gSelect = B_SEL | B_5;
    }

    public static void getKeypadState() {
        KEY_before = KEY_get;
        KEY_get = KEY;
        KEY_press = (KEY_before ^ (-1)) & KEY_get;
    }

    public static void clear() {
        MFGamePad.resetKeys();
    }

    public static void setPress(int button) {
        KEY |= button;
        getKeypadState();
    }

    public static boolean press(int button) {
        if (keyFunction) {
            return MFGamePad.isKeyPress(button);
        }
        return false;
    }

    public static boolean repeat(int button) {
        if (keyFunction) {
            return MFGamePad.isKeyRepeat(button);
        }
        return false;
    }

    public static boolean release(int button) {
        if (keyFunction) {
            return MFGamePad.isKeyRelease(button);
        }
        return false;
    }

    public static void setKeyFunction(boolean function) {
        keyFunction = function;
    }

    public static boolean pressAnyKey() {
        if (keyFunction) {
            return MFGamePad.isKeyPress(-1);
        }
        return false;
    }

    public static boolean repeatAnyKey() {
        if (keyFunction) {
            return MFGamePad.isKeyRepeat(-1);
        }
        return false;
    }

    public static boolean buttonPress(int button) {
        if (press(button)) {
            if (keyState == 0) {
                keyState = 1;
            }
            return false;
        }
        if (release(button) && keyState == 1) {
            keyState = 0;
            return true;
        }
        return false;
    }

    public static void keyPressed(int keyCode) {
    }

    public static void keyReleased(int keyCode) {
    }
}
