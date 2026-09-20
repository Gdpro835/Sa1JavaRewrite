package Ending;

import GameEngine.time.GameTime;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.MyRandom;
import Lib.SoundSystem;
import PlatformStandard.Standard2;
import SonicGBA.GlobalResource;
import SonicGBA.MapManager;
import SonicGBA.SonicDef;
import State.SpecialStageState;
import State.State;
import com.sega.mobile.define.MDPhone;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import java.lang.reflect.Array;
import java.util.Vector;

public class NormalEnding extends State implements SonicDef {
    private static final boolean[] ANIMATION_LOOP;
    private static final int BACK_GROUND_STOP_Y = -135;
    private static final int BIRD_NUM = 10;
    private static final int BIRD_OFFSET = 2;
    private static final int BIRD_SPACE_1 = 10;
    private static final int BIRD_SPACE_2 = 14;
    private static final int BIRD_VELOCITY = 2;
    private static final int BIRD_X = 0;
    private static final int BIRD_Y = 1;
    private static final String[] CHARACTER_ANIMATION_NAME = {"/sonic_ed", "/tails_ed", "/knuckles_ed", "/amy_ed"};
    private static final int CLOUD_APPEAR_Y = -50;
    private static final int CLOUD_END_Y = -100;
    private static final int CLOUD_GROUP_COUNT = 3;
    private static int[] CLOUD_GROUP_OFFSET = null;
    private static int CLOUD_GROUP_START_X = -50;
    private static int CLOUD_GROUP_START_Y = -30;
    private static int CLOUD_GROUP_VEL_X = 20;
    private static int CLOUD_GROUP_VEL_Y = 20;
    private static final int CLOUD_NUM = 4;
    private static final int[] CLOUD_RIGHT_SPEED = {1, 2, 2, 1};
    private static final int[] CLOUD_UP_SPEED = {16, 10, 14, 8};
    private static final int DEGREE_VELOCITY = 10;
    private static final float EMERALD_DISPLAY_COUNT = 80.0f;
    private static final String ENDING_ANIMATION_PATH = "/animation/ending";
    private static final int ENDING_NORMAL = 0;
    private static final float END_DISPLAY_COUNT = 45.0f;
    private static final int FADE_FILL_HEIGHT = 40;
    private static final int FADE_FILL_WIDTH = 40;
    private static final int FALL_COUNT = 60;
    private static final int FLOAT_RANGE = 10;
    private static final String[] LOOK_UP_IMAGE_NAME = {"/look_up_sonic.png", "/look_up_tails.png", "/look_up_knuckles.png", "/look_up_amy.png"};
    private static final int OFFSET_RANGE = 12;
    private static final int OFFSET_SPEED = 10;
    private static final int PLANE_DES_X = ((-SCREEN_WIDTH) << 1);
    private static final int PLANE_FLY_IN_FRAME = 2;
    private static final int PLANE_FLY_OUT_VEL_Y = -10;
    private static final int PLANE_START_X = (SCREEN_WIDTH + 50);
    private static final int PLANE_START_Y = ((SCREEN_HEIGHT >> 1) + 34);
    private static final int PLANE_TOUCH_DOWN_DES_X = ((SCREEN_WIDTH >> 1) + 10);
    private static final int PLANE_VEL_X = ((PLANE_TOUCH_DOWN_DES_X - PLANE_START_X) / 5);
    private static final int PLAYER_OFFSET_TO_PLANE_X = 10;
    private static final int PLAYER_OFFSET_TO_PLANE_Y = 34;
    private static final float SCALE_VELOCITY = 0.03f;
    private static final int SPEED_LIGHT_NUM_PER_FRAME = 2;
    private static final int SPEED_LIGHT_VELOCITY = 60;
    private static final int STATE_CLOUD_MOVE_RIGHT = 3;
    private static final int STATE_CREDIT = 12;
    private static final int STATE_FALLING = 1;
    private static final int STATE_INIT = 0;
    private static final int STATE_INTERRUPT = 14;
    private static final int STATE_NEED_EMERALD = 13;
    private static final int STATE_PLANE_BIRD = 6;
    private static final int STATE_PLANE_INIT = 4;
    private static final int STATE_PLANE_JUMPING = 10;
    private static final int STATE_PLANE_LAST_WAIT = 9;
    private static final int STATE_PLANE_LOOK_UP_1 = 7;
    private static final int STATE_PLANE_LOOK_UP_2 = 8;
    private static final int STATE_PLANE_SMILE = 5;
    private static final int STATE_SHOW_BIG_IMAGE = 11;
    private static final int STATE_TOUCH_DOWN = 2;
    private static final int WORD_DESTINY_1 = ((SCREEN_HEIGHT >> 1) - 76);
    private static final int WORD_DESTINY_2 = (SCREEN_HEIGHT - 56);
    private static final int[][] WORD_PARAM;
    private static final int WORD_START = (SCREEN_HEIGHT + 68);
    private static final int WORD_VELOCITY = -8;
    private static AnimationDrawer[] birdDrawer;
    private static AnimationDrawer cloudDrawer;
    private static int[][] cloudInfoArray = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{4, 2}));
    private static MFImage creditImage;
    private static MFImage endingBackGround;
    private static MFImage endingWordImage;
    private static int fadeAlpha = 40;
    private static int fadeFromValue;
    private static int[] fadeRGB = new int[1600];
    private static int fadeToValue;
    private static boolean fading;
    private static AnimationDrawer needEmeraldDrawer;
    private static AnimationDrawer planeDrawer;
    private static AnimationDrawer planeHeadDrawer;
    private static int preFadeAlpha;
    private static AnimationDrawer skipDrawer;
    private static MFImage speedLight;
    private static Vector speedLightVec;
    private int backGroundY;
    private MFImage bigPoseImage;
    private int[][] birdInfo = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{10, 3}));
    private int birdX;
    private AnimationDrawer characterDrawer;
    private int characterID;
    private int characterX;
    private int characterY;
    private boolean cloudAppearFlag;
    private int cloudGroupCount;
    private int cloudGroupX;
    private int cloudGroupY;
    private int count;
    private int degree;
    private int endcount;
    private AnimationDrawer interruptDrawer;
    private int interrupt_state;
    private boolean isSkipPressed;
    private MFImage lookUpImage;
    private int needEmeraldCount;
    private int pilotHeadID;
    private boolean pilotSmile;
    private int planeOffsetDegree;
    private int planeOffsetY;
    private int planeX;
    private int planeY;
    private int playerAccX;
    private int playerAccY;
    private int playerActionID;
    private float playerScale;
    private int playerVelX;
    private int playerVelY;
    private int preCharacterX;
    private int preCharacterY;
    private int state;
    private int word1Y;
    private int word2Y;

    static {
        int[] iArr = new int[4];
        iArr[2] = 184;
        iArr[3] = 24;
        int[] iArr2 = new int[4];
        iArr2[1] = 24;
        iArr2[2] = 184;
        iArr2[3] = 48;
        WORD_PARAM = new int[][]{iArr, iArr2};
        boolean[] zArr = new boolean[8];
        zArr[0] = true;
        zArr[2] = true;
        zArr[4] = true;
        zArr[5] = true;
        zArr[7] = true;
        ANIMATION_LOOP = zArr;
        int[] iArr3 = new int[4];
        iArr3[1] = -40;
        iArr3[2] = -80;
        iArr3[3] = -120;
        CLOUD_GROUP_OFFSET = iArr3;
    }

    public NormalEnding() {
        if (planeDrawer == null) {
            planeDrawer = new Animation("/animation/ending/ending_plane").getDrawer();
        }
        if (cloudDrawer == null) {
            cloudDrawer = new Animation("/animation/ending/ending_cloud").getDrawer();
        }
        if (planeHeadDrawer == null) {
            planeHeadDrawer = new Animation("/animation/ending/plane_head").getDrawer();
        }
        if (birdDrawer == null) {
            birdDrawer = new AnimationDrawer[10];
            Animation animation = new Animation("/animation/ending/ending_bird");
            for (int i = 0; i < 10; i++) {
                birdDrawer[i] = animation.getDrawer();
            }
        }
        if (speedLight == null) {
            speedLight = MFImage.createImage("/animation/ending/speed_light.png");
            speedLightVec = new Vector();
        }
        if (endingBackGround == null) {
            endingBackGround = MFImage.createImage("/animation/ending/ending_bg.png");
        }
        if (endingWordImage == null) {
            endingWordImage = MFImage.createImage("/lang" + GlobalResource.languageConfig + "/ending/ending_word.png");
        }
        fading = false;
        if (needEmeraldDrawer == null) {
            needEmeraldDrawer = new Animation("/lang" + GlobalResource.languageConfig + "/ending/ed_emerald_hint").getDrawer(0, false, 0);
        }
        if (creditImage == null) {
            creditImage = MFImage.createImage("/animation/ending/sega_logo_ed.png");
        }
        if (skipDrawer == null) {
            skipDrawer = new Animation("/animation/skip").getDrawer(0, false, 0);
        }
    }

    public void init(int type, int characterID2) {
        int i;
        this.characterDrawer = new Animation(ENDING_ANIMATION_PATH + CHARACTER_ANIMATION_NAME[characterID2]).getDrawer();
        this.lookUpImage = MFImage.createImage(ENDING_ANIMATION_PATH + LOOK_UP_IMAGE_NAME[characterID2]);
        this.bigPoseImage = MFImage.createImage("/animation/ending/big_show.png");
        this.state = 0;
        this.characterID = characterID2;
        if (characterID2 == 0) {
            i = 2;
        } else {
            i = 0;
        }
        this.pilotHeadID = i;
    }

    public void logic() {
        if (this.state != 14) {
            this.count = GameTime.advance(this, "count", this.count, 1);
        }
        if (Key.press(536870912)) {
            pause();
        }

        degreeLogic();
        switch (this.state) {
            case 0:
                this.characterX = SCREEN_WIDTH >> 1;
                this.characterY = SCREEN_HEIGHT >> 1;
                this.state = 1;
                cloudInfoArray[0][0] = (SCREEN_WIDTH * 4) / 4;
                cloudInfoArray[1][0] = (SCREEN_WIDTH * 3) / 4;
                cloudInfoArray[2][0] = (SCREEN_WIDTH * 1) / 4;
                cloudInfoArray[3][0] = (SCREEN_WIDTH * 2) / 4;
                this.cloudAppearFlag = true;
                this.planeX = PLANE_START_X;
                this.planeY = PLANE_START_Y;
                this.count = GameTime.set(this, "count", 0);
                SoundSystem.getInstance().playBgm(31, false);
                return;
            case 1:
                this.backGroundY = ((this.count * BACK_GROUND_STOP_Y) / 60) + 0;
                for (int i = 0; i < 2; i++) {
                    speedLightVec.addElement(new int[]{MyRandom.nextInt(0, SCREEN_WIDTH), 0 - (i * 30)});
                }
                if (this.count > 36) {
                    this.cloudAppearFlag = false;
                }
                if (this.count >= 60) {
                    this.state = 2;
                }
                if (this.count >= 58) {
                    this.planeX = GameTime.advance(this, "planeX", this.planeX, PLANE_VEL_X);
                    return;
                }
                return;
            case 2:
                this.planeX = GameTime.advance(this, "planeX", this.planeX, PLANE_VEL_X);
                if (this.characterDrawer.checkEnd()) {
                    this.planeY = GameTime.advance(this, "planeY", this.planeY, -(10));
                    this.characterX = this.planeX - 10;
                    this.characterY = this.planeY - 34;
                }
                if (this.planeX < PLANE_DES_X) {
                    this.state = 3;
                    this.cloudGroupX = CLOUD_GROUP_START_X;
                    this.cloudGroupY = CLOUD_GROUP_START_Y;
                    for (int i2 = 0; i2 < cloudInfoArray.length; i2++) {
                        cloudInfoArray[i2][0] = 0;
                        cloudInfoArray[i2][1] = CLOUD_GROUP_OFFSET[i2];
                    }
                    this.cloudGroupCount = 0;
                    return;
                }
                return;
            case 3:
                this.cloudGroupX = GameTime.advance(this, "cloudGroupX", this.cloudGroupX, CLOUD_GROUP_VEL_X);
                this.cloudGroupY = GameTime.advance(this, "cloudGroupY", this.cloudGroupY, CLOUD_GROUP_VEL_Y);
                if (this.cloudGroupCount == 2) {
                    boolean nextState = false;
                    if (this.cloudGroupY >= (SCREEN_HEIGHT * 2) / 3) {
                        this.cloudGroupY = (SCREEN_HEIGHT * 2) / 3;
                    }
                    if (this.cloudGroupX >= (SCREEN_WIDTH >> 1) + 10) {
                        this.cloudGroupX = (SCREEN_WIDTH >> 1) + 10;
                        nextState = true;
                    }
                    if (nextState) {
                        this.state = 4;
                        this.planeX = this.cloudGroupX - 10;
                        this.planeY = this.cloudGroupY + 20;
                        this.count = GameTime.set(this, "count", 0);
                        this.playerActionID = 2;
                        return;
                    }
                    this.planeX = this.cloudGroupX - 10;
                    this.planeY = this.cloudGroupY + 10;
                }
                if (this.cloudGroupX > SCREEN_WIDTH && this.cloudGroupCount != 2) {
                    this.cloudGroupX = CLOUD_GROUP_START_X;
                    this.cloudGroupY = CLOUD_GROUP_START_Y;
                    this.cloudGroupCount++;
                    return;
                }
                return;
            case 4:
                if (GameTime.event(this, "count", "logic:312", GameTime.crosses(this, "count", this.count, 80))) {
                    this.playerActionID = 3;
                    this.state = 5;
                    this.count = GameTime.set(this, "count", 0);
                    this.pilotSmile = true;
                    return;
                }
                return;
            case 5:
                if (GameTime.event(this, "count", "logic:321", GameTime.crosses(this, "count", this.count, 29))) {
                    this.state = 6;
                    birdInit();
                    this.count = GameTime.set(this, "count", 0);
                    return;
                }
                return;
            case 6:
                if (birdLogic() && this.count >= 72) {
                    this.state = 7;
                    this.playerActionID = 5;
                    this.count = GameTime.set(this, "count", 0);
                    this.pilotSmile = false;
                    return;
                }
                return;
            case 7:
                if (this.count >= 64) {
                    this.count = GameTime.set(this, "count", 0);
                    this.state = 8;
                    return;
                }
                return;
            case 8:
                if (this.count >= 64) {
                    this.playerActionID = 6;
                    this.count = GameTime.set(this, "count", 0);
                    this.state = 9;
                    return;
                }
                return;
            case 9:
                if (this.count >= 16) {
                    this.playerActionID = 7;
                    this.playerVelX = 18;
                    this.playerVelY = -18;
                    this.playerAccX = -2;
                    this.playerAccY = 2;
                    this.preCharacterX = this.characterX + this.playerVelX;
                    this.preCharacterY = this.characterY + this.playerVelY;
                    this.state = 10;
                    this.word1Y = WORD_START;
                    this.word2Y = WORD_START;
                    this.count = GameTime.set(this, "count", 0);
                    this.playerScale = 1.0f;
                    return;
                }
                return;
            case 10:
                this.playerVelX = GameTime.advance(this, "playerVelX", this.playerVelX, this.playerAccX);
                this.playerVelY = GameTime.advance(this, "playerVelY", this.playerVelY, this.playerAccY);
                this.characterX = GameTime.advancePosition(this, "characterX", this.characterX, "playerVelX", this.playerVelX);
                this.characterY = GameTime.advancePosition(this, "characterY", this.characterY, "playerVelY", this.playerVelY);
                this.playerScale += (float) ((SCALE_VELOCITY) * GameTime.assetUnits());
                if (this.characterX < this.preCharacterX && this.playerVelX < 0) {
                    this.state = 11;
                    this.characterX = GameTime.advance(this, "characterX", this.characterX, -(this.playerVelX));
                    this.characterY = GameTime.advance(this, "characterY", this.characterY, -(this.playerVelY));
                    fadeInitAndStart(0, 0);
                }
                this.word1Y = GameTime.advance(this, "word1Y", this.word1Y, WORD_VELOCITY);
                if (this.word1Y <= WORD_DESTINY_1) {
                    this.word1Y = WORD_DESTINY_1;
                }
                if (this.count > 70) {
                    this.word2Y = GameTime.advance(this, "word2Y", this.word2Y, WORD_VELOCITY);
                    if (this.word2Y <= WORD_DESTINY_2) {
                        this.word2Y = WORD_DESTINY_2;
                        return;
                    }
                    return;
                }
                return;
            case 11:
                this.word1Y = GameTime.advance(this, "word1Y", this.word1Y, WORD_VELOCITY);
                if (this.word1Y <= WORD_DESTINY_1) {
                    this.word1Y = WORD_DESTINY_1;
                }
                if (this.count > 70) {
                    if (this.word2Y <= WORD_DESTINY_2) {
                        this.word2Y = WORD_DESTINY_2;
                    } else {
                        this.word2Y = GameTime.advance(this, "word2Y", this.word2Y, WORD_VELOCITY);
                        this.endcount = GameTime.set(this, "endcount", 0);
                    }
                    if (this.word2Y == WORD_DESTINY_2) {
                        this.endcount = GameTime.advance(this, "endcount", this.endcount, 1);
                        if (((float) this.endcount) == END_DISPLAY_COUNT) {
                            fadeInitAndStart(0, 255);
                            return;
                        } else if (((float) this.endcount) > 61.0f && fadeChangeOver()) {
                            this.state = 12;
                            creditInit();
                            return;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            case 12:
                if (Key.touchopeningskip.IsButtonPress() && !this.isSkipPressed || Key.buttonPress(Key.B_S1 | 16777216 | 8388608)) {
                    this.isSkipPressed = true;
                    SoundSystem.getInstance().stopBgm(false);
                    if (SpecialStageState.emeraldMissed()) {
                        fadeInitAndStart(0, 255);
                        this.endcount = GameTime.set(this, "endcount", 30);
                    } else {
                        fadeInitAndStart(0, 255);
                        this.endcount = GameTime.set(this, "endcount", 10);
                    }
                }
                if (this.endcount >= 10 && this.endcount < 26) {
                    this.endcount = GameTime.advance(this, "endcount", this.endcount, 1);
                    if (this.endcount >= 26 && fadeChangeOver()) {
                        Standard2.splashinit(true);
                        State.setState(0);
                    }
                }
                if (this.endcount >= 30) {
                    this.endcount = GameTime.advance(this, "endcount", this.endcount, 1);
                    if (this.endcount >= 46 && fadeChangeOver()) {
                        this.state = 13;
                        fadeInitAndStart(255, 0);
                        this.endcount = GameTime.set(this, "endcount", 0);
                        return;
                    }
                    return;
                }
                return;
            case 13:
                if (fadeChangeOver()) {
                    this.endcount = GameTime.advance(this, "endcount", this.endcount, 1);
                    if (((float) this.endcount) == EMERALD_DISPLAY_COUNT) {
                        fadeInitAndStart(0, 255);
                        Key.touchOpeningClose();
                        return;
                    } else if (((float) this.endcount) > EMERALD_DISPLAY_COUNT && fadeChangeOver()) {
                        SoundSystem.getInstance().stopBgm(false);
                        Standard2.splashinit(true);
                        State.setState(0);
                        return;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            case 14:
                interruptLogic();
                return;
            default:
                return;
        }
    }

    public void draw(MFGraphics g) {
        MyAPI.drawImage(g, endingBackGround, SCREEN_WIDTH >> 1, this.backGroundY, 17);
        switch (this.state) {
            case 1:
                this.characterDrawer.draw(g, 0, this.characterX, this.characterY, true, 0);
                break;
            case 2:
                drawPlane(g);
                this.characterDrawer.draw(g, 1, this.characterX, this.characterY, false, 0);
                break;
            case 3:
                for (int i = 0; i < cloudInfoArray.length; i++) {
                    int[] iArr = cloudInfoArray[i];
                    iArr[0] = iArr[0] + CLOUD_RIGHT_SPEED[i];
                    cloudDrawer.draw(g, i, cloudInfoArray[i][0] + this.cloudGroupX, cloudInfoArray[i][1] + this.cloudGroupY, false, 0);
                }
                drawPlane(g);
                this.characterX = this.planeX - 10;
                this.characterY = this.planeY - 34;
                this.characterDrawer.draw(g, 2, this.characterX, this.characterY, true, 0);
                break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                cloudRightLogic(g);
                if (this.state >= 6) {
                    birdDraw1(g);
                }
                drawPlane(g);
                if (this.state != 10) {
                    this.characterX = this.planeX - 10;
                    this.characterY = (this.planeY - 34) + this.planeOffsetY;
                }
                if (this.state == 10) {
                    g.saveCanvas();
                    g.translateCanvas(this.characterX, this.characterY);
                    g.scaleCanvas(this.playerScale, this.playerScale);
                    this.characterDrawer.draw(g, this.playerActionID, 0, 0, ANIMATION_LOOP[this.playerActionID], 0);
                    g.restoreCanvas();
                } else {
                    this.characterDrawer.draw(g, this.playerActionID, this.characterX, this.characterY, ANIMATION_LOOP[this.playerActionID], 0);
                }
                if (this.characterDrawer.checkEnd()) {
                    switch (this.playerActionID) {
                        case 3:
                            this.playerActionID = 4;
                            break;
                    }
                }
                if (this.state >= 6) {
                    birdDraw2(g);
                }
                if (this.state == 7) {
                    MyAPI.drawImage(g, this.lookUpImage, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, 3);
                }
                if (this.state == 10) {
                    MyAPI.drawImage(g, endingWordImage, WORD_PARAM[0][0], WORD_PARAM[0][1], WORD_PARAM[0][2], WORD_PARAM[0][3], 0, SCREEN_WIDTH >> 1, this.word1Y, 17);
                    MyAPI.drawImage(g, endingWordImage, WORD_PARAM[1][0], WORD_PARAM[1][1], WORD_PARAM[1][2], WORD_PARAM[1][3], 0, SCREEN_WIDTH >> 1, this.word2Y, 17);
                    break;
                }
                break;
            case 11:
                cloudRightLogic(g);
                if (this.state >= 6) {
                    birdDraw1(g);
                }
                drawPlane(g);
                if (this.state >= 6) {
                    birdDraw2(g);
                }
                MyAPI.drawImage(g, this.bigPoseImage, (this.characterID % 2) * 128, (this.characterID / 2) * 128, 128, 128, 0, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, 3);
                MyAPI.drawImage(g, endingWordImage, WORD_PARAM[0][0], WORD_PARAM[0][1], WORD_PARAM[0][2], WORD_PARAM[0][3], 0, SCREEN_WIDTH >> 1, this.word1Y, 17);
                MyAPI.drawImage(g, endingWordImage, WORD_PARAM[1][0], WORD_PARAM[1][1], WORD_PARAM[1][2], WORD_PARAM[1][3], 0, SCREEN_WIDTH >> 1, this.word2Y, 17);
                drawFade(g);
                break;
            case 12:
                g.setColor(0);
                MyAPI.fillRect(g, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                MyAPI.drawImage(g, creditImage, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, 3);
                skipDrawer.setActionId((Key.touchopeningskip.Isin() || Key.repeat(Key.B_S1 | 16777216 | 8388608) ? 1 : 0) + 0);
                skipDrawer.draw(g, 0, SCREEN_HEIGHT);
                drawFade(g);
                break;
            case 13:
                g.setColor(0);
                MyAPI.fillRect(g, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                needEmeraldDrawer.setActionId(0);
                needEmeraldDrawer.draw(g, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
                needEmeraldDrawer.setActionId(1);
                needEmeraldDrawer.draw(g, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
                drawFade(g);
                break;
            case 14:
                interruptDraw(g);
                break;
        }
        if ((this.state == 1 || this.state == 2) && this.backGroundY < -50) {
            cloudUpLogic(g);
        }
        speedLightLogic(g);
    }

    private void speedLightLogic(MFGraphics g) {
        int i = 0;
        while (i < speedLightVec.size()) {
            int[] position = (int[]) speedLightVec.elementAt(i);
            position[1] = position[1] + 60;
            if (position[1] > SCREEN_HEIGHT + speedLight.getHeight()) {
                speedLightVec.removeElementAt(i);
                i--;
            } else {
                MyAPI.drawImage(g, speedLight, position[0], SCREEN_HEIGHT - position[1], 17);
            }
            i++;
        }
    }

    private void cloudUpLogic(MFGraphics g) {
        for (int i = 0; i < cloudInfoArray.length; i++) {
            int[] position = cloudInfoArray[i];
            position[1] = position[1] + CLOUD_UP_SPEED[i];
            if (position[1] > SCREEN_HEIGHT + 40 && this.cloudAppearFlag) {
                position[1] = -20;
                position[0] = MyRandom.nextInt(0, SCREEN_WIDTH);
            }
            cloudDrawer.draw(g, i, position[0], SCREEN_HEIGHT - position[1], false, 0);
        }
    }

    private void cloudRightLogic(MFGraphics g) {
        for (int i = 0; i < cloudInfoArray.length; i++) {
            int[] iArr = cloudInfoArray[i];
            iArr[0] = iArr[0] + CLOUD_RIGHT_SPEED[i];
            cloudDrawer.setActionId(i);
            if (this.cloudGroupX + cloudInfoArray[i][0] > SCREEN_WIDTH + cloudDrawer.getCurrentFrameWidth()) {
                cloudInfoArray[i][0] = ((-cloudDrawer.getCurrentFrameWidth()) >> 1) - this.cloudGroupX;
            }
            cloudDrawer.draw(g, i, cloudInfoArray[i][0] + this.cloudGroupX, cloudInfoArray[i][1] + this.cloudGroupY, false, 0);
        }
    }

    private void drawPlane(MFGraphics g) {
        int i;
        if (this.state >= 4) {
            this.planeOffsetY = getPlaneOffset();
        } else {
            this.planeOffsetY = 0;
        }
        planeDrawer.draw(g, this.planeX, this.planeY + this.planeOffsetY);
        AnimationDrawer animationDrawer = planeHeadDrawer;
        if (this.pilotSmile) {
            i = 1;
        } else {
            i = 0;
        }
        animationDrawer.draw(g, this.pilotHeadID + i, this.planeX + 3, this.planeOffsetY + (this.planeY - 22), true, 0);
    }

    private int getPlaneOffset() {
        this.planeOffsetDegree = GameTime.advance(this, "planeOffsetDegree", this.planeOffsetDegree, 10);
        return ((MyAPI.dSin(this.planeOffsetDegree) * 12) / 100) + 12;
    }

    private void birdInit() {
        for (int i = 4; i >= 0; i--) {
            this.birdInfo[i][1] = ((this.planeY - ((5 - i) * 10)) - 30) - 20;
            this.birdInfo[i][0] = (5 - i) * 10;
            this.birdInfo[i][2] = MyRandom.nextInt(MDPhone.SCREEN_WIDTH);
        }
        for (int i2 = 5; i2 < 10; i2++) {
            this.birdInfo[i2][1] = ((this.planeY - ((4 - i2) * 14)) - 15) - 20;
            this.birdInfo[i2][0] = (4 - i2) * -14;
            this.birdInfo[i2][2] = MyRandom.nextInt(MDPhone.SCREEN_WIDTH);
        }
        this.birdX = SCREEN_WIDTH + 30;
    }

    private boolean birdLogic() {
        this.birdX = GameTime.advance(this, "birdX", this.birdX, -(2));
        if (this.birdX >= (SCREEN_WIDTH >> 1) + 40) {
            return false;
        }
        this.birdX = (SCREEN_WIDTH >> 1) + 40;
        return true;
    }

    private void birdDraw1(MFGraphics g) {
        for (int i = 0; i < 5; i++) {
            birdDrawer[i].draw(g, this.birdX + this.birdInfo[i][0], this.birdInfo[i][1] + getOffsetY(this.birdInfo[i][2]));
        }
    }

    private void birdDraw2(MFGraphics g) {
        for (int i = 5; i < 10; i++) {
            birdDrawer[i].draw(g, this.birdX + this.birdInfo[i][0], this.birdInfo[i][1] + getOffsetY(this.birdInfo[i][2]));
        }
    }

    private void degreeLogic() {
        this.degree = GameTime.advance(this, "degree", this.degree, 10);
        this.degree = GameTime.wrap(this, "degree", this.degree, MDPhone.SCREEN_WIDTH);
    }

    private int getOffsetY(int degreeOffset) {
        return (MyAPI.dSin(this.degree + degreeOffset) * 10) / 100;
    }

    public static void fadeInitAndStart(int from, int to) {
        fadeFromValue = from;
        fadeToValue = to;
        fadeAlpha = fadeFromValue;
        preFadeAlpha = -1;
        fading = true;
    }

    public static void drawFadeBase(MFGraphics g, int vel2) {
        fadeAlpha = MyAPI.calNextPosition(NormalEnding.class, "fadeAlpha", (double) fadeAlpha, (double) fadeToValue, 1, vel2, 3.0d);
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

    public static void drawFade(MFGraphics g) {
        drawFadeBase(g, 3);
    }

    public static boolean fadeChangeOver() {
        return fadeAlpha == fadeToValue;
    }

    private void creditInit() {
        fadeInitAndStart(0, 0);
        SoundSystem.getInstance().stopBgm(false);
        SoundSystem.getInstance().playBgm(33);
        Key.touchOpeningInit();
        this.endcount = GameTime.set(this, "endcount", 0);
        this.isSkipPressed = false;
    }

    public void close() {
        Animation.closeAnimationDrawer(this.characterDrawer);
        this.characterDrawer = null;
        Animation.closeAnimationDrawer(this.interruptDrawer);
        this.interruptDrawer = null;
        Animation.closeAnimationDrawer(planeDrawer);
        planeDrawer = null;
        Animation.closeAnimationDrawer(cloudDrawer);
        cloudDrawer = null;
        Animation.closeAnimationDrawer(planeHeadDrawer);
        planeHeadDrawer = null;
        Animation.closeAnimationDrawerArray(birdDrawer);
        birdDrawer = null;
        endingBackGround = null;
        endingWordImage = null;
        Animation.closeAnimationDrawer(needEmeraldDrawer);
        needEmeraldDrawer = null;
        creditImage = null;
        Animation.closeAnimationDrawer(skipDrawer);
        skipDrawer = null;
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
        if (this.state == 14) {
            this.state = 14;
            interruptInit();
            return;
        }
        this.interrupt_state = this.state;
        this.state = 14;
        if (this.interrupt_state == 13) {
            this.needEmeraldCount = this.endcount;
        }
        interruptInit();
        Key.touchInterruptInit();
        Key.touchkeyboardInit();
        SoundSystem.getInstance().stopBgm(false);
    }

    private void interruptInit() {
        if (this.interruptDrawer == null) {
            Key.touchInterruptInit();
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
            if (this.interrupt_state <= 12) {
                this.state = 12;
                creditInit();
            } else if (this.interrupt_state == 13) {
                fadeInitAndStart(0, 0);
                this.state = 13;
                this.endcount = GameTime.set(this, "endcount", this.needEmeraldCount);
            }
            Key.clear();
        }
    }

    private void interruptDraw(MFGraphics g) {
        this.interruptDrawer.setActionId((Key.touchinterruptreturn.Isin() || Key.repeat(16777216 | 8388608) ? 1 : 0) + 0);
        this.interruptDrawer.draw(g, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1);
    }
}
