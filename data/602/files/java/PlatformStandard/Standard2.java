package PlatformStandard;

import GameEngine.Def;
import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.SoundSystem;
import SonicGBA.GlobalResource;
import SonicGBA.MapManager;
import android.os.Message;
import com.sega.MFLib.Main;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import com.sega.mobile.framework.ui.MFButton;

public class Standard2 implements Def {
    public static final String ANIMATION_PATH = "/animation";
    private static final int CLIP_NUM = 25;
    private static final int FADE_FILL_HEIGHT = 40;
    private static final int FADE_FILL_WIDTH = 40;
    private static final int FRAME_DIFF = 8;
    public static final int MESSAGE_EXIT = 5;
    public static final int MESSAGE_GAME_TOP = 2;
    public static final int MESSAGE_INIT_SDK = 0;
    public static final int MESSAGE_PAGE = 1;
    public static final int MESSAGE_RANKING = 3;
    public static final int MESSAGE_SAVE = 6;
    public static final int MESSAGE_USER = 4;
    private static final int SPLASH_2_COME_IN_COUNT = 30;
    private static final int SPLASH_EXIT = 5;
    private static final int SPLASH_INIT = 0;
    private static final int SPLASH_OVER = 4;
    private static final int SPLASH_QUIT = 6;
    private static final int SPLASH_SEGA = 2;
    private static final int SPLASH_SONIC_TEAM = 3;
    private static final int SPLASH_SOUND = 1;
    private static final String STANDARD_RES = "/standard";
    private static final int STATE_INIT = 0;
    private static final int STATE_LOGO_IN = 1;
    private static final int STATE_LOGO_OUT = 3;
    private static final int STATE_OVER = 4;
    private static final int STATE_SHOW = 2;
    public static Main activity;
    public static int confirmcursor = 0;
    public static int confirmframe;
    private static int count;
    private static int fadeAlpha = 40;
    private static int fadeFromValue;
    private static int[] fadeRGB = new int[1600];
    private static int fadeToValue;
    public static boolean isConfirm;
    private static boolean isFromEnding = false;
    protected static boolean keyCancel;
    protected static boolean keyConfirm;
    public static AnimationDrawer muiAniDrawer;
    private static MFButton noButton;
    private static int preFadeAlpha;
    public static int returnValue;
    private static AnimationDrawer soundUIDrawer;
    public static int soundtrack;
    private static MFImage splashImage1;
    private static MFImage splashImage2;
    private static int splashState;
    private static int state;
    private static MFButton yesButton;

    public static void splashinit(boolean isNeedSoundControl) {
        isFromEnding = isNeedSoundControl;
        splashState = 0;
    }

    public static int splashLogic() {
        returnValue = 0;
        switch (splashState) {
            case 0:
                soundInit();
                if (isFromEnding) {
                    state = 0;
                    splashState = 2;
                    if (yesButton != null) {
                        MFDevice.removeComponent(yesButton);
                    }
                    if (noButton != null) {
                        MFDevice.removeComponent(noButton);
                        break;
                    }
                } else {
                    splashState = 1;
                    setFadeOver();
                    break;
                }
                break;
            case 1:
                if (!soundLogic()) {
                    if (Key.press(524288) && fadeChangeOver()) {
                        splashState = 5;
                        secondEnsureInit();
                        fadeInit(0, 220);
                        SoundSystem.getInstance().playSe(1);
                        break;
                    }
                } else {
                    state = 0;
                    splashState = 2;
                    if (yesButton != null) {
                        MFDevice.removeComponent(yesButton);
                    }
                    if (noButton != null) {
                        MFDevice.removeComponent(noButton);
                        break;
                    }
                }
                break;
            case 2:
                splashLogic1();
                if (splashIsOver1()) {
                    state = 0;
                    splashState = 3;
                    break;
                }
                break;
            case 3:
                splashLogic2();
                if (splash2IsOver()) {
                    splashState = 4;
                    break;
                }
                break;
            case 4:
                returnValue = 3;
                close();
                isFromEnding = false;
                break;
            case 5:
                switch (secondEnsureLogic()) {
                    case 1:
                        close();
                        System.gc();
                        splashState = 6;
                        SoundSystem.getInstance().stopBgm(true);
                        Key.touchkeyboardClose();
                        Key.touchsoftkeyInit();
                        break;
                    case 2:
                        soundInit();
                        splashState = 1;
                        break;
                }
            case 6:
                if (Key.press(Key.B_S1 | Key.gSelect)) {
                    Standard.pressConfirm();
                } else if (Key.press(2)) {
                    Standard.pressCancel();
                }
                switch (Standard.execMoreGame(true)) {
                    case 0:
                        Key.touchsoftkeyClose();
                        GlobalResource.saveSystemConfig();
                        MFDevice.notifyExit();
                        sendMessage(new Message(), 5);
                        break;
                }
        }
        return returnValue;
    }

    public static void splashDraw(MFGraphics g, int SCREEN_WIDTH, int SCREEN_HEIGHT) {
        switch (splashState) {
            case 1:
                soundDraw(g, SCREEN_WIDTH, SCREEN_HEIGHT);
                return;
            case 2:
                splashDraw1(g, SCREEN_WIDTH, SCREEN_HEIGHT);
                return;
            case 3:
                splashDraw2(g, SCREEN_WIDTH, SCREEN_HEIGHT);
                return;
            case 4:
                splashDraw2(g, SCREEN_WIDTH, SCREEN_HEIGHT);
                return;
            case 5:
                soundDraw(g, SCREEN_WIDTH, SCREEN_HEIGHT);
                drawFade(g);
                SecondEnsurePanelDraw(g, 14);
                return;
            default:
                return;
        }
    }

    public static boolean splashOver() {
        return splashState == 4;
    }

    public static void close() {
        splashImage1 = null;
        splashImage2 = null;
        Animation.closeAnimationDrawer(soundUIDrawer);
        soundUIDrawer = null;
    }

    private static void splashInit1() {
        splashImage1 = MFImage.createImage("/standard/sega_logo.png");
        count = 0;
    }

    private static void splashLogic1() {
        count++;
        switch (state) {
            case 0:
                splashInit1();
                state = 1;
                return;
            case 1:
                if (count == 16) {
                    state = 2;
                    count = 0;
                    return;
                }
                return;
            case 2:
                if (count == 60) {
                    state = 3;
                    count = 0;
                    return;
                }
                return;
            case 3:
                if (count == 16) {
                    state = 4;
                    count = 0;
                    return;
                }
                return;
            default:
                return;
        }
    }

    private static void splashDraw1(MFGraphics g, int screenWidth, int screenHeight) {
        g.setColor(16777215);
        g.fillRect(0, 0, screenWidth, screenHeight);
        switch (state) {
            case 1:
                drawSplashEffect1(g, splashImage1, screenWidth >> 1, (screenHeight >> 1) - (splashImage1.getHeight() >> 1), count);
                return;
            case 2:
                MyAPI.drawImage(g, splashImage1, screenWidth >> 1, (screenHeight >> 1) - (splashImage1.getHeight() >> 1), 17);
                return;
            case 3:
                drawSplashEffect2(g, splashImage1, screenWidth >> 1, (screenHeight >> 1) - (splashImage1.getHeight() >> 1), count, screenHeight);
                return;
            default:
                return;
        }
    }

    private static boolean splashIsOver1() {
        return state == 4 && count >= 20;
    }

    private static void drawSplashEffect1(MFGraphics g, MFImage image, int x, int y, int count2) {
        for (int i = 0; i < (image.getHeight() + y) - ((image.getHeight() * count2) / 16); i++) {
            MyAPI.drawImage(g, image, 0, (image.getHeight() - ((image.getHeight() * count2) / 16)) - 1, image.getWidth(), 1, 0, x, i, 17);
        }
        MyAPI.drawImage(g, image, 0, image.getHeight() - ((image.getHeight() * count2) / 16), image.getWidth(), (image.getHeight() * count2) / 16, 0, x, (image.getHeight() + y) - ((image.getHeight() * count2) / 16), 17);
    }

    private static void drawSplashEffect2(MFGraphics g, MFImage image, int x, int y, int count2, int screenHeight) {
        for (int i = (image.getHeight() + y) - ((image.getHeight() * count2) / 16); i < screenHeight; i++) {
            MyAPI.drawImage(g, image, 0, (image.getHeight() - ((image.getHeight() * count2) / 16)) - 1, image.getWidth(), 1, 0, x, i, 17);
        }
        MyAPI.drawImage(g, image, 0, 0, image.getWidth(), image.getHeight() - ((image.getHeight() * count2) / 16), 0, x, y, 17);
    }

    private static void splashInit2() {
        splashImage2 = MFImage.createImage("/standard/sonic_team.png");
        count = 0;
    }

    private static void splashLogic2() {
        count++;
        switch (state) {
            case 0:
                splashInit2();
                state = 1;
                SoundSystem.getInstance().preLoadSequenceSe(12);
                return;
            case 1:
                if (count == 30) {
                    state = 2;
                    // SoundSystem.getInstance().playSequenceSeSingle();
                    SoundSystem.getInstance().playSe(12);
                    count = 0;
                    return;
                }
                return;
            case 2:
                if (count == 36) {
                    state = 4;
                    count = 0;
                    return;
                }
                return;
            default:
                return;
        }
    }

    private static void splashDraw2(MFGraphics g, int screenWidth, int screenHeight) {
        g.setColor(16777215);
        g.fillRect(0, 0, screenWidth, screenHeight);
        switch (state) {
            case 1:
                drawSplash2Effect(g, splashImage2, screenWidth >> 1, (screenHeight >> 1) - (splashImage2.getHeight() >> 1), count, screenWidth);
                return;
            case 2:
            case 4:
                if (splashImage2 != null) {
                    MyAPI.drawImage(g, splashImage2, screenWidth >> 1, (screenHeight >> 1) - (splashImage2.getHeight() >> 1), 17);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private static boolean splash2IsOver() {
        return state == 4;
    }

    private static void drawSplash2Effect(MFGraphics g, MFImage image, int x, int y, int count2, int screenWidth) {
        int space = image.getHeight() / 25;
        int clipHeight = (image.getHeight() / 25) / 2;
        for (int i = 0; i < 25; i++) {
            int countBase = (30 - ((i * 8) / 25)) - 1;
            int countDiff = countBase - count2;
            if (countDiff < 0) {
                countDiff = 0;
            }
            MyAPI.drawImage(g, image, 0, i * space, image.getWidth(), clipHeight + 1, 0, x + ((((-screenWidth) - ((24 - i) * 4)) * countDiff) / countBase), y + (i * space), 17);
            MyAPI.drawImage(g, image, 0, (i * space) + clipHeight, image.getWidth(), clipHeight + 1, 0, x + (((((24 - i) * 4) + screenWidth) * countDiff) / countBase), (i * space) + y + clipHeight, 17);
        }
    }

    private static void soundInit() {
        soundUIDrawer = Animation.getInstanceFromQi("/lang" + GlobalResource.languageConfig + "/standard/enable_sound.dat")[0].getDrawer();
        yesButton = new MFButton((SCREEN_WIDTH >> 1) + -72, (SCREEN_HEIGHT >> 1) + 28, 64, 24);
        noButton = new MFButton((SCREEN_WIDTH >> 1) + 8, (SCREEN_HEIGHT >> 1) + 28, 64, 24);
        MFDevice.addComponent(yesButton);
        MFDevice.addComponent(noButton);
    }

    private static void soundDraw(MFGraphics g, int screenWidth, int screenHeight) {
        g.setColor(0);
        g.fillRect(0, 0, screenWidth, screenHeight);
        int actionID = 0;
        if (!yesButton.isRepeat() || !noButton.isRepeat()) {
            if (yesButton.isRepeat() || Key.repeat(16)) {
                actionID = 1;
            }
            if (noButton.isRepeat() || Key.repeat(32)) {
                actionID = 2;
            }
        }
        soundUIDrawer.draw(g, actionID, screenWidth >> 1, screenHeight >> 1, false, 0);
    }

    private static boolean soundLogic() {
        if (yesButton.isRelease() && !noButton.isRepeat() || Key.buttonPress(16)) {
            pressConfirm();
            returnValue = 1;
            soundtrack = 0;
            SoundSystem.getInstance().playSe(1);
            return true;
        } else if (!yesButton.isRepeat() && noButton.isRelease() || Key.buttonPress(32)) {
            pressCancel();
            returnValue = 1;
            soundtrack = 1;
            SoundSystem.getInstance().playSe(1);
            return true;
        } else {
            return false;
        }
    }

    public static void pressConfirm() {
        keyConfirm = true;
    }

    public static void pressCancel() {
        keyCancel = true;
    }

    public static void fadeInit(int from, int to) {
        fadeFromValue = from;
        fadeToValue = to;
        fadeAlpha = fadeFromValue;
        preFadeAlpha = -1;
    }

    public static void drawFade(MFGraphics g) {
        fadeAlpha = MyAPI.calNextPosition((double) fadeAlpha, (double) fadeToValue, 1, 3, 3.0d);
        if (fadeAlpha != 0) {
            if (preFadeAlpha != fadeAlpha) {
                for (int w = 0; w < 40; w++) {
                    for (int h = 0; h < 40; h++) {
                        fadeRGB[(h * 40) + w] = ((fadeAlpha << 24) & -16777216) | (fadeRGB[(h * 40) + w] & MapManager.END_COLOR);
                    }
                }
                preFadeAlpha = fadeAlpha;
            }
            for (int w2 = 0; w2 < MyAPI.zoomOut(SCREEN_WIDTH); w2 += 40) {
                for (int h2 = 0; h2 < MyAPI.zoomOut(SCREEN_HEIGHT); h2 += 40) {
                    g.drawRGB(fadeRGB, 0, 40, w2, h2, 40, 40, true);
                }
            }
        }
    }

    public static void setFadeOver() {
        fadeAlpha = fadeToValue;
    }

    public static boolean fadeChangeOver() {
        return fadeAlpha == fadeToValue;
    }

    public static void secondEnsureInit() {
        isConfirm = false;
        confirmframe = 0;
        confirmcursor = 0;
        if (muiAniDrawer == null) {
            muiAniDrawer = new Animation("/lang" + GlobalResource.languageConfig + "/mui").getDrawer(0, false, 0);
        }
        Key.touchSecondEnsureClose();
        Key.touchSecondEnsureInit();
    }

    public static int secondEnsureLogic() {
        if (Key.touchsecondensurereturn.Isin() && Key.touchsecondensure.IsClick()) {
            confirmcursor = 2;
        }
        if (Key.touchsecondensureyes.Isin() && Key.touchsecondensure.IsClick()) {
            confirmcursor = 0;
        }
        if (Key.touchsecondensureno.Isin() && Key.touchsecondensure.IsClick()) {
            confirmcursor = 1;
        }
        if (isConfirm) {
            confirmframe++;
            if (confirmframe > 8) {
                return 1;
            }
        }
        if (Key.touchsecondensureyes.IsButtonPress() && confirmcursor == 0 && !isConfirm) {
            isConfirm = true;
            confirmframe = 0;
            SoundSystem.getInstance().playSe(1);
            return 0;
        } else if (Key.touchsecondensureno.IsButtonPress() && confirmcursor == 1 && !isConfirm) {
            SoundSystem.getInstance().playSe(2);
            return 2;
        } else if ((!Key.press(524288) && !Key.touchsecondensurereturn.IsButtonPress()) || !fadeChangeOver() || isConfirm) {
            return 0;
        } else {
            SoundSystem.getInstance().playSe(2);
            return 2;
        }
    }

    public static void SecondEnsurePanelDraw(MFGraphics g, int ani_id) {
        int i;
        int i2;
        if (muiAniDrawer == null) {
            muiAniDrawer = new Animation("/lang" + GlobalResource.languageConfig + "/mui").getDrawer(0, false, 0);
            return;
        }
        muiAniDrawer.setActionId(54);
        muiAniDrawer.draw(g, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 20);
        muiAniDrawer.setActionId(((!Key.touchsecondensureyes.Isin() || confirmcursor != 0) ? 0 : 1) + 59);
        muiAniDrawer.draw(g, (SCREEN_WIDTH >> 1) - 40, (SCREEN_HEIGHT >> 1) + 40);
        AnimationDrawer animationDrawer = muiAniDrawer;
        if (!Key.touchsecondensureno.Isin() || confirmcursor != 1) {
            i = 0;
        } else {
            i = 1;
        }
        animationDrawer.setActionId(i + 59);
        muiAniDrawer.draw(g, (SCREEN_WIDTH >> 1) + 40, (SCREEN_HEIGHT >> 1) + 40);
        muiAniDrawer.setActionId(46);
        muiAniDrawer.draw(g, (SCREEN_WIDTH >> 1) - 40, (SCREEN_HEIGHT >> 1) + 40);
        muiAniDrawer.setActionId(47);
        muiAniDrawer.draw(g, (SCREEN_WIDTH >> 1) + 40, (SCREEN_HEIGHT >> 1) + 40);
        muiAniDrawer.setActionId(ani_id);
        muiAniDrawer.draw(g, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 20);
        AnimationDrawer animationDrawer2 = muiAniDrawer;
        if (Key.touchsecondensurereturn.Isin()) {
            i2 = 5;
        } else {
            i2 = 0;
        }
        animationDrawer2.setActionId(i2 + 61);
        muiAniDrawer.draw(g, 0, SCREEN_HEIGHT);
        if (isConfirm) {
            if (confirmframe == 1) {
                fadeInit(220, 255);
            }
            drawFade(g);
            if (confirmframe > 8) {
                g.setColor(0);
                MyAPI.fillRect(g, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            }
        }
    }

    public static void getMain(Main main) {
        activity = main;
    }

    public static void sendMessage(Message msg, int id) {
        msg.what = id;
        //activity.handler.handleMessage(msg);
    }
}
