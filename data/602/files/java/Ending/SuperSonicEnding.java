package Ending;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.MyRandom;
import Lib.SoundSystem;
import PlatformStandard.Standard2;
import SonicGBA.GlobalResource;
import SonicGBA.SonicDef;
import State.State;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import java.lang.reflect.Array;

public class SuperSonicEnding extends State implements SonicDef {
    private static final int BGM_ENDING_COUNT = 205;
    private static final int CLOUD_NUM = 10;
    private static final int CLOUD_TYPE = 0;
    private static final int[] CLOUD_VELOCITY = {5, 4, 3};
    private static final int CLOUD_X = 1;
    private static final int CLOUD_Y = 2;
    private static final String ENDING_ANIMATION_PATH = "/animation/ending";
    private static final int MOON_BG_MOVE_FRAME = 56;
    private static int[] MOON_BG_OFFSET_END = {-69, 11, 25};
    private static int[] MOON_BG_OFFSET_START = null;
    private static final String[] MOON_PNG_NAME = {"/ed_ex_moon_bg.png", "/ed_ex_forest.png", "/ed_ex_character.png"};
    private static final int OFFSET_RANGE = 8;
    private static final int OFFSET_SPEED = 5;
    private static final int PLANE_CATCH_UP_FRAME = 30;
    private static final int PLANE_PULL_DOWN_VEL = 10;
    private static final int SHINING_VEL_H = 4;
    private static final int SHINING_VEL_V = 7;
    private static final int[][] STAR_POSITION = {new int[]{-16, 18}, new int[]{-73, 96}, new int[]{93, 70}, new int[]{47, 95}};
    private static final int STATE_AFTER_THAT = 5;
    private static final int STATE_CATCH = 11;
    private static final int STATE_CHANGING_FACE = 12;
    private static final int STATE_CONGRATULATION = 13;
    private static final int STATE_CREDIT = 15;
    private static final int STATE_INIT = 0;
    private static final int STATE_INTERRUPT = 16;
    private static final int STATE_MOON_END = 4;
    private static final int STATE_MOON_MOVE = 3;
    private static final int STATE_MOON_START = 1;
    private static final int STATE_MOON_STAR_SHINING = 2;
    private static final int STATE_PRE_INIT = 14;
    private static final int STATE_TAILS_CATCH_UP = 10;
    private static final int STATE_TAILS_FIND = 8;
    private static final int STATE_TAILS_FLYING = 6;
    private static final int STATE_TAILS_PULL_DOWN = 9;
    private static final int STATE_TAILS_SEARCHING = 7;
    private static final int WORD_DESTINY_2 = (SCREEN_HEIGHT - 44);
    private static final int[][] WORD_PARAM;
    private static MFImage creditImage;
    private static AnimationDrawer skipDrawer;
    private int catchID;
    private MFImage[] catchImage;
    private boolean changeState;
    private int cloudCount = 0;
    private AnimationDrawer cloudDrawer;
    private int[][] cloudInfo = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{10, 3}));
    private int congratulationY;
    private int count;
    private int count2;
    private MFImage daysLaterImage;
    private MFImage endingBgImage;
    private MFImage endingWordImage;
    private AnimationDrawer faceChangeDrawer;
    private AnimationDrawer interruptDrawer;
    private int interrupt_state;
    private boolean isSkipPressed;
    private MFImage[] moonImage = new MFImage[MOON_PNG_NAME.length];
    private int[] moonOffset = new int[3];
    private int pilotHeadID;
    private int planeDegree;
    private AnimationDrawer planeDrawer;
    private AnimationDrawer planeHeadDrawer;
    private int planeOffsetDegree;
    private int planeOffsetY;
    private double planeScale;
    private int planeX;
    private int planeY;
    private int shiningX;
    private int shiningY;
    private int starCount;
    private AnimationDrawer[] starDrawer;
    private int state = 14;
    private AnimationDrawer superSonicShiningDrawer;
    private int thankY;

    static {
        int[] iArr = new int[3];
        iArr[1] = 80;
        iArr[2] = 180;
        MOON_BG_OFFSET_START = iArr;
        int[] iArr2 = new int[4];
        iArr2[2] = 184;
        iArr2[3] = 24;
        int[] iArr3 = new int[4];
        iArr3[1] = 24;
        iArr3[2] = 184;
        iArr3[3] = 48;
        WORD_PARAM = new int[][]{iArr2, iArr3};
    }

    public SuperSonicEnding() {
        for (int i = 0; i < this.moonImage.length; i++) {
            this.moonImage[i] = MFImage.createImage(ENDING_ANIMATION_PATH + MOON_PNG_NAME[i]);
        }
        for (int i2 = 0; i2 < 3; i2++) {
            this.moonOffset[i2] = MOON_BG_OFFSET_START[i2];
        }
    }

    public void logic() {
        if (this.state != 16) {
            this.count++;
        }
        if (Key.press(536870912)) {
            pause();
        }

        switch (this.state) {
            case 0:
                this.state = 1;
                this.count = 0;
                this.starCount = 0;
                SoundSystem.getInstance().playBgmSequenceNoLoop(32, 45);
                return;
            case 1:
                if (this.count > 5) {
                    this.state = 2;
                    return;
                }
                return;
            case 2:
                if (this.starDrawer[3].checkEnd() && this.count > 4) {
                    this.state = 3;
                    return;
                }
                return;
            case 3:
                for (int i = 0; i < 3; i++) {
                    this.moonOffset[i] = MOON_BG_OFFSET_START[i] + (((MOON_BG_OFFSET_END[i] - MOON_BG_OFFSET_START[i]) * this.count) / 56);
                }
                if (this.count >= 56) {
                    this.state = 4;
                    this.count = 0;
                    return;
                }
                return;
            case 4:
                if (this.count == 30) {
                    State.fadeInitAndStart(0, 255);
                }
                if (this.count > 30 && State.fadeChangeOver()) {
                    this.state = 5;
                    State.fading = false;
                    this.count = 0;
                    return;
                }
                return;
            case 5:
                if (this.count == 160) {
                    State.fadeInitAndStart(255, 0);
                    this.state = 6;
                    this.count = 0;
                    return;
                }
                return;
            case 6:
                if (this.count > 16) {
                    this.state = 7;
                    this.count = 0;
                    this.pilotHeadID = 4;
                    return;
                }
                return;
            case 7:
                if (this.count > 48) {
                    this.shiningX -= 7;
                    this.shiningY += 4;
                    if (this.shiningY > 184) {
                        this.shiningY = 184;
                    }
                    if (this.count > 90) {
                        this.pilotHeadID = 5;
                    }
                    if (this.shiningX < -10) {
                        this.state = 9;
                        this.planeY += this.planeOffsetY;
                        return;
                    }
                    return;
                }
                return;
            case 9:
                this.planeY += 10;
                if (this.planeY > SCREEN_HEIGHT + 80) {
                    this.state = 10;
                    this.planeX = (SCREEN_WIDTH * 3) / 4;
                    this.planeY = SCREEN_HEIGHT + 40;
                    this.count = 0;
                    this.planeDegree = 0;
                    return;
                }
                return;
            case 10:
                this.planeDegree = (this.count * -55) / 30;
                this.planeX = ((SCREEN_WIDTH * 3) / 4) + (((-40 - ((SCREEN_WIDTH * 3) / 4)) * this.count) / 30);
                this.planeY = SCREEN_HEIGHT + 40 + (((((SCREEN_HEIGHT >> 1) - 10) - (SCREEN_HEIGHT + 40)) * this.count) / 30);
                this.planeScale = 0.9d + ((-0.6000000000000001d * ((double) this.count)) / 30.0d);
                if (!this.changeState && this.planeX < -80) {
                    State.fadeInitAndStart(0, 255);
                    this.changeState = true;
                }
                if (this.changeState && State.fadeChangeOver()) {
                    State.fadeInitAndStart(255, 0);
                    this.state = 11;
                    this.changeState = false;
                    this.count = 0;
                    this.catchID = 0;
                    this.congratulationY = SCREEN_HEIGHT + 16;
                    this.thankY = SCREEN_HEIGHT + 16;
                    return;
                }
                return;
            case 11:
                if (this.count == 72) {
                    State.fadeInitAndStart(0, 255);
                    this.changeState = true;
                }
                if (this.changeState && State.fadeChangeOver()) {
                    this.changeState = false;
                    State.fadeInitAndStart(255, 0);
                    this.count = 0;
                    if (this.catchID == 1) {
                        this.state = 12;
                        return;
                    } else {
                        this.catchID++;
                        return;
                    }
                } else {
                    return;
                }
            case 12:
                if (this.faceChangeDrawer.checkEnd() && this.faceChangeDrawer.getActionId() == 1) {
                    this.state = 13;
                    this.count = 0;
                    return;
                }
                return;
            case 13:
                this.congratulationY = SCREEN_HEIGHT + 16 + ((((((SCREEN_HEIGHT >> 1) - 48) - 24) - (SCREEN_HEIGHT + 16)) * this.count) / 13);
                if (this.congratulationY < ((SCREEN_HEIGHT >> 1) - 52) - 24) {
                    this.congratulationY = ((SCREEN_HEIGHT >> 1) - 52) - 24;
                }
                if (this.count > 27) {
                    this.thankY = SCREEN_HEIGHT + 16 + ((((((SCREEN_HEIGHT >> 1) + 48) - 6) - (SCREEN_HEIGHT + 16)) * (this.count - 27)) / 13);
                    if (this.thankY < WORD_DESTINY_2) {
                        this.thankY = WORD_DESTINY_2;
                    }
                }
                if (this.count == BGM_ENDING_COUNT) {
                    State.fadeInitAndStart(0, 255);
                    return;
                } else if (isOver() && State.fadeChangeOver()) {
                    this.state = 15;
                    creditInit();
                    return;
                } else {
                    return;
                }
            case 14:
                this.daysLaterImage = MFImage.createImage("/lang" + GlobalResource.languageConfig + "/ending/ed_fewdays.png");
                this.planeDrawer = new Animation("/animation/ending/ending_plane").getDrawer();
                this.planeHeadDrawer = new Animation("/animation/ending/plane_head").getDrawer();
                this.cloudDrawer = new Animation("/animation/ending/ending_cloud").getDrawer();
                this.endingBgImage = MFImage.createImage("/animation/ending/ending_bg.png");
                this.superSonicShiningDrawer = new Animation("/animation/ending/super_sonic_shining").getDrawer();
                this.planeX = SCREEN_WIDTH >> 1;
                this.planeY = (SCREEN_HEIGHT >> 1) + 40;
                this.pilotHeadID = 2;
                this.shiningX = 480;
                this.shiningY = -40;
                this.catchImage = new MFImage[4];
                for (int i2 = 0; i2 < 4; i2++) {
                    this.catchImage[i2] = MFImage.createImage("/animation/ending/ending_catch_" + (i2 + 1) + ".png");
                }
                this.faceChangeDrawer = new Animation("/animation/ending/ed_supersonic_b").getDrawer(0, false, 0);
                this.endingWordImage = MFImage.createImage("/lang" + GlobalResource.languageConfig + "/ending/ending_word.png");
                Animation starAnimation = new Animation("/animation/ending/ending_star");
                this.starDrawer = new AnimationDrawer[STAR_POSITION.length];
                for (int i3 = 0; i3 < STAR_POSITION.length; i3++) {
                    this.starDrawer[i3] = starAnimation.getDrawer(0, false, 0);
                }
                if (creditImage == null) {
                    creditImage = MFImage.createImage("/animation/ending/sega_logo_ed.png");
                }
                if (skipDrawer == null) {
                    skipDrawer = new Animation("/animation/skip").getDrawer(0, false, 0);
                }
                this.state = 0;
                return;
            case 15:
                if (Key.touchopeningskip.IsButtonPress() && !this.isSkipPressed || Key.buttonPress(Key.B_S1 | 16777216 | 8388608)) {
                    this.isSkipPressed = true;
                    SoundSystem.getInstance().stopBgm(false);
                    State.fadeInitAndStart(0, 255);
                    this.count2 = 10;
                }
                if (this.count2 >= 10) {
                    this.count2++;
                    if (this.count2 >= 26 && State.fadeChangeOver()) {
                        State.fadeInitAndStart(0, 0);
                        Standard2.splashinit(true);
                        State.setState(0);
                        return;
                    }
                    return;
                }
                return;
            case 16:
                interruptLogic();
                return;
            default:
                return;
        }
    }

    public void draw(MFGraphics g) {
        int i;
        switch (this.state) {
            case 0:
            case 14:
                for (int i2 = 0; i2 < 3; i2++) {
                    MyAPI.drawImage(g, this.moonImage[i2], SCREEN_WIDTH >> 1, this.moonOffset[i2], 17);
                }
                return;
            case 1:
            case 2:
            case 3:
            case 4:
                for (int i3 = 0; i3 < 3; i3++) {
                    MyAPI.drawImage(g, this.moonImage[i3], SCREEN_WIDTH >> 1, this.moonOffset[i3], 17);
                }
                if (this.state == 2) {
                    switch (this.starCount) {
                        case 0:
                            this.starDrawer[0].draw(g, (SCREEN_WIDTH >> 1) + STAR_POSITION[0][0], STAR_POSITION[0][1]);
                            if (this.starDrawer[0].checkEnd()) {
                                this.starCount = 1;
                                return;
                            }
                            return;
                        case 1:
                            this.starDrawer[1].draw(g, (SCREEN_WIDTH >> 1) + STAR_POSITION[1][0], STAR_POSITION[1][1]);
                            this.starDrawer[2].draw(g, (SCREEN_WIDTH >> 1) + STAR_POSITION[2][0], STAR_POSITION[2][1]);
                            if (this.starDrawer[1].checkEnd()) {
                                this.starCount = 2;
                                return;
                            }
                            return;
                        case 2:
                            this.starDrawer[3].draw(g, (SCREEN_WIDTH >> 1) + STAR_POSITION[3][0], STAR_POSITION[3][1]);
                            if (this.starDrawer[3].checkEndTrigger()) {
                                this.count = 0;
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                } else {
                    return;
                }
            case 5:
                g.setColor(0);
                MyAPI.fillRect(g, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                MyAPI.drawImage(g, this.daysLaterImage, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, 3);
                return;
            case 6:
            case 7:
            case 8:
            case 9:
                drawBG(g);
                this.superSonicShiningDrawer.draw(g, this.shiningX >> 2, this.shiningY >> 2);
                cloudLogic();
                cloudDraw(g);
                drawPlane(g);
                return;
            case 10:
                drawBG(g);
                cloudLogic();
                cloudDraw(g);
                drawCatchPlane(g);
                return;
            case 11:
                g.setColor(0);
                MyAPI.fillRect(g, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                MyAPI.drawImage(g, this.catchImage[this.catchID], SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, 3);
                return;
            case 12:
                g.setColor(0);
                MyAPI.fillRect(g, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                this.faceChangeDrawer.draw(g, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
                if (this.faceChangeDrawer.checkEnd()) {
                    this.faceChangeDrawer.setActionId(1);
                    return;
                }
                return;
            case 13:
                g.setColor(0);
                MyAPI.fillRect(g, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                this.faceChangeDrawer.draw(g, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
                MyAPI.drawImage(g, this.endingWordImage, WORD_PARAM[0][0], WORD_PARAM[0][1], WORD_PARAM[0][2], WORD_PARAM[0][3], 0, SCREEN_WIDTH >> 1, this.congratulationY, 17);
                MyAPI.drawImage(g, this.endingWordImage, WORD_PARAM[1][0], WORD_PARAM[1][1], WORD_PARAM[1][2], WORD_PARAM[1][3], 0, SCREEN_WIDTH >> 1, this.thankY, 17);
                return;
            case 15:
                g.setColor(0);
                MyAPI.fillRect(g, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                MyAPI.drawImage(g, creditImage, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, 3);
                AnimationDrawer animationDrawer = skipDrawer;
                if (Key.touchopeningskip.Isin() || Key.repeat(Key.B_S1 | 16777216 | 8388608)) {
                    i = 1;
                } else {
                    i = 0;
                }
                animationDrawer.setActionId(i + 0);
                skipDrawer.draw(g, 0, SCREEN_HEIGHT);
                return;
            case 16:
                interruptDraw(g);
                return;
            default:
                return;
        }
    }

    public boolean isOver() {
        return this.state == 13 && this.count > BGM_ENDING_COUNT;
    }

    private void drawPlane(MFGraphics g) {
        if (this.state < 9) {
            this.planeOffsetY = getPlaneOffset();
        } else {
            this.planeOffsetY = 0;
        }
        this.planeDrawer.draw(g, this.planeX, this.planeY + this.planeOffsetY);
        this.planeHeadDrawer.draw(g, this.pilotHeadID, this.planeX + 3, this.planeOffsetY + (this.planeY - 22), true, 0);
    }

    private int getPlaneOffset() {
        this.planeOffsetDegree += 5;
        return ((MyAPI.dSin(this.planeOffsetDegree) * 8) / 100) + 8;
    }

    private void drawCatchPlane(MFGraphics g) {
        g.saveCanvas();
        g.translateCanvas(this.planeX, this.planeY);
        g.scaleCanvas((float) this.planeScale, (float) this.planeScale);
        g.rotateCanvas((float) this.planeDegree);
        this.planeDrawer.draw(g, 1, 0, 0, true, 0);
        g.restoreCanvas();
    }

    private void cloudLogic() {
        if (this.cloudCount > 0) {
            this.cloudCount--;
        }
        for (int i = 0; i < 10; i++) {
            if (this.cloudInfo[i][0] != 0) {
                int[] iArr = this.cloudInfo[i];
                iArr[1] = iArr[1] + CLOUD_VELOCITY[this.cloudInfo[i][0] - 1];
                if (this.cloudInfo[i][1] >= SCREEN_WIDTH + 75) {
                    this.cloudInfo[i][0] = 0;
                }
            }
            if (this.cloudInfo[i][0] == 0 && this.cloudCount == 0) {
                this.cloudInfo[i][0] = MyRandom.nextInt(1, 3);
                this.cloudInfo[i][1] = -60;
                this.cloudInfo[i][2] = MyRandom.nextInt(20, SCREEN_HEIGHT - 40);
                this.cloudCount = MyRandom.nextInt(8, 20);
            }
        }
    }

    private void cloudDraw(MFGraphics g) {
        for (int i = 0; i < 10; i++) {
            if (this.cloudInfo[i][0] != 0) {
                this.cloudDrawer.setActionId(this.cloudInfo[i][0] - 1);
                this.cloudDrawer.draw(g, this.cloudInfo[i][1], this.cloudInfo[i][2]);
            }
        }
    }

    private void drawBG(MFGraphics g) {
        MyAPI.drawImage(g, this.endingBgImage, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - 30, 3);
    }

    public void close() {
        Animation.closeAnimationDrawer(this.interruptDrawer);
        this.interruptDrawer = null;
        if (this.moonImage != null) {
            for (int i = 0; i < this.moonImage.length; i++) {
                this.moonImage[i] = null;
            }
        }
        this.moonImage = null;
        if (this.catchImage != null) {
            for (int i2 = 0; i2 < this.catchImage.length; i2++) {
                this.catchImage[i2] = null;
            }
        }
        this.catchImage = null;
        this.daysLaterImage = null;
        this.endingWordImage = null;
        Animation.closeAnimationDrawer(this.planeDrawer);
        this.planeDrawer = null;
        Animation.closeAnimationDrawer(this.planeHeadDrawer);
        this.planeHeadDrawer = null;
        Animation.closeAnimationDrawer(this.cloudDrawer);
        this.cloudDrawer = null;
        this.endingBgImage = null;
        Animation.closeAnimationDrawer(this.superSonicShiningDrawer);
        this.superSonicShiningDrawer = null;
        Animation.closeAnimationDrawer(this.faceChangeDrawer);
        this.faceChangeDrawer = null;
        creditImage = null;
        Animation.closeAnimationDrawer(skipDrawer);
        skipDrawer = null;
        Animation.closeAnimationDrawerArray(this.starDrawer);
        this.starDrawer = null;
        System.gc();
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
    }

    public void pause() {
        if (this.state == 16) {
            this.state = 16;
            interruptInit();
            return;
        }
        this.interrupt_state = this.state;
        this.state = 16;
        interruptInit();
        Key.touchInterruptInit();
        Key.touchkeyboardInit();
        SoundSystem.getInstance().stopBgm(false);
    }

    private void creditInit() {
        this.isSkipPressed = false;
        State.fadeInitAndStart(0, 0);
        SoundSystem.getInstance().stopBgm(false);
        SoundSystem.getInstance().playBgm(33);
        Key.touchOpeningInit();
        this.count = 0;
        this.count2 = 0;
    }

    private void interruptInit() {
        if (this.interruptDrawer == null) {
            this.interruptDrawer = Animation.getInstanceFromQi("/lang" + GlobalResource.languageConfig + "/utl_res/suspend_resume.dat")[0].getDrawer(0, true, 0);
        }
    }

    private void interruptLogic() {
        State.fadeInitAndStart(0, 0);
        SoundSystem.getInstance().stopBgm(false);
        if (Key.press(2)) {
        }
        if (Key.buttonPress(16777216 | 8388608) || (Key.touchinterruptreturn != null && Key.touchinterruptreturn.IsButtonPress())) {
            SoundSystem.getInstance().playSe(2);
            Key.touchInterruptClose();
            Key.touchkeyboardClose();
            if (this.interrupt_state <= 15) {
                this.state = 15;
                creditInit();
            }
            Key.clear();
        }
    }

    private void interruptDraw(MFGraphics g) {
        this.interruptDrawer.setActionId((Key.touchinterruptreturn.Isin() || Key.repeat(16777216 | 8388608) ? 1 : 0) + 0);
        this.interruptDrawer.draw(g, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
    }
}
