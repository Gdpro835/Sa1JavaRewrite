package Special;

import GameEngine.time.GameTime;

import Common.BarWord;
import Common.NumberDrawer;
import Common.WhiteBarDrawer;
import GameEngine.Def;
import GameEngine.Key;
import GameEngine.TouchDirectKey;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import Lib.MyRandom;
import Lib.SoundSystem;
import SonicGBA.CollisionRect;
import SonicGBA.GlobalResource;
import SonicGBA.SonicDebug;
import State.State;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFSensor;
import com.sega.mobile.framework.ui.MFTouchKey;
import java.lang.reflect.Array;

public class SpecialPlayer extends SpecialObject implements BarWord {
    private static final int ACC_FAST = 6;
    private static final int ACC_NORMAL = 4;
    private static final int ACC_SLOW = 2;
    private static final String[] ANIMATION_NAME = {"/chr_sonic_sp", "/chr_tails_sp", "/chr_knuckles_sp", "/chr_amy_sp"};
    private static final int ANI_DAMAGE = 25;
    private static final int ANI_DASH_1 = 18;
    private static final int ANI_DASH_2 = 19;
    private static final int ANI_DIE_BOARD = 22;
    private static final int ANI_DIE_BODY = 21;
    private static final int ANI_FONT_WELCOME = 0;
    private static final int ANI_INTRO = 0;
    private static final boolean[] ANI_LOOP;
    private static final int ANI_MOVE_DOWN = 11;
    private static final int ANI_MOVE_LEFT = 12;
    private static final int ANI_MOVE_LEFT_DOWN = 16;
    private static final int ANI_MOVE_LEFT_UP = 14;
    private static final int ANI_MOVE_RIGHT = 13;
    private static final int ANI_MOVE_RIGHT_DOWN = 17;
    private static final int ANI_MOVE_RIGHT_UP = 15;
    private static final int ANI_MOVE_UP = 10;
    private static final int ANI_NICE_SKILL = 26;
    private static final int ANI_SKILL = 20;
    private static final int ANI_STAND = 1;
    private static final int ANI_STAND_DOWN = 3;
    private static final int ANI_STAND_LEFT = 4;
    private static final int ANI_STAND_LEFT_DOWN = 8;
    private static final int ANI_STAND_LEFT_UP = 6;
    private static final int ANI_STAND_RIGHT = 5;
    private static final int ANI_STAND_RIGHT_DOWN = 9;
    private static final int ANI_STAND_RIGHT_UP = 7;
    private static final int ANI_STAND_UP = 2;
    private static final int ANI_VICTORY_1 = 23;
    private static final int ANI_VICTORY_2 = 24;
    private static final int ANI_WELCOME_1 = 27;
    private static final int ANI_WELCOME_2 = 28;
    private static final int ANI_WELCOME_3 = 29;
    private static final int ANI_WELCOME_4 = 30;
    private static final int ANI_WELCOME_5 = 31;
    private static final int ANI_WELCOME_6 = 32;
    private static final int ANI_WELCOME_7 = 33;
    private static final int CENTER_ACC_X = 5;
    private static final int GRAVITY = 172;
    private static final int MAX_VELOCITY = 3600;
    private static final int MOVE_DOWN = 4;
    private static final int MOVE_LEFT = 2;
    private static final int MOVE_RIGHT = 8;
    private static final int MOVE_UP = 1;
    private static final int MOVE_VELOCITY = 960;
    private static final float RATIO = 0.16f;
    private static final int RING_HUD_DESTINY = 5;
    private static final int RING_HUD_ORIGINAL = -19;
    private static final int RING_NUM_DESTINY = 15;
    private static final int RING_NUM_ORIGINAL = -24;
    private static final float SCALE_ZOOM_0 = -0.08f;
    private static final int SCALE_ZOOM_0_FRAME_COUNT = 5;
    private static final float SCALE_ZOOM_1 = -0.048f;
    private static final int SCALE_ZOOM_1_FRAME_COUNT = 1;
    private static final float SCALE_ZOOM_2 = -0.016f;
    private static final int SCALE_ZOOM_2_FRAME_COUNT = 1;
    private static final float SCALE_ZOOM_3 = 0.016f;
    private static final int SCALE_ZOOM_3_FRAME_COUNT = 1;
    private static final float SCALE_ZOOM_4 = 0.048f;
    private static final int SCALE_ZOOM_4_FRAME_COUNT = 1;
    private static final float SCALE_ZOOM_5 = 0.08f;
    private static final int SCALE_ZOOM_5_FRAME_COUNT = 5;
    private static final float SCALE_ZOOM_BASE = 0.8f;
    private static final int SCALE_ZOOM_IN = 9;
    private static final float SCALE_ZOOM_OFFSET = 0.0f;
    private static final int SCALE_ZOOM_OUT = 9;
    private static final int SSSpringCount = 15;
    private static final int START_POS_X = ((SCREEN_WIDTH << 6) >> 1);
    private static final int START_POS_Y = ((SCREEN_HEIGHT << 6) >> 1);
    private static final int STATE_CHECK_POINT = 5;
    private static final int STATE_DASH = 2;
    private static final int STATE_DEAD = 4;
    private static final int STATE_GOAL = 6;
    private static final int STATE_INIT = 8;
    private static final int STATE_NORMAL = 0;
    private static final int STATE_OVER = 9;
    private static final int STATE_READY = 7;
    private static final int STATE_SKILLING = 1;
    private static final int STATE_SPRING = 10;
    private static final int STATE_TUTORIAL = 11;
    private static final int STATE_VICTORY = 3;
    private static final int STAY_TIME = 60;
    private static final int TOTAL_COUNT_SCALE_ZOOM = 18;
    private static final int[][] TUTORIAL_ANIMATION_ID;
    private static final int WELCOME_ANI_CHANGE_RANGE = 500;
    private static final int WELCOMT_VEL_X = -240;
    private static final int WHITE_BAR_HEIGHT = 20;
    private static final int WHITE_BAR_VEL_X = -96;
    private static final int WHITE_BAR_VEL_Y = -36;
    private static final int WHITE_BAR_WIDTH = SCREEN_WIDTH;
    private static final int WHITE_BAR_Y_DES = ((SCREEN_HEIGHT >> 1) - 60);
    private static final int WORDS_INIT_X = 14;
    private static final int WORDS_VEL_X = -8;
    private static final int WORD_DISTANCE = 224;
    public static int[] ringDirection = new int[10];
    public static final int[][] ringVel = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{10, 2}));
    private static boolean showTutorial = true;
    private int acc_value;
    private int actionID;
    protected CollisionRect attackCollisionRect;
    private int boardOffsetX;
    private int boardOffsetY;
    private int boardOffsetZ;
    private boolean changingState;
    private AnimationDrawer characterBoardDrawer;
    private int checkCount;
    public boolean checkSuccess;
    private int count;
    private boolean debugPassStage;
    private AnimationDrawer drawer;
    private AnimationDrawer fontAnimationDrawer;
    public CollisionRect hurtRect;
    private boolean isGoal;
    private boolean isNeedTouchPad;
    private boolean isPause = false;
    private int moveDistance = 0;
    private boolean niceTriking;
    public boolean noMoving;
    private float pauseKeepScale = 1.0f;
    private int preState;
    byte[] rect1 = null;
    byte[] rect2 = null;
    private int ringHudY = -19;
    private int ringNum;
    private int ringNumY = RING_NUM_ORIGINAL;
    private int scaleCount;
    private int skipOffsetY;
    private AnimationDrawer spObjDrawer;
    private boolean startFlag;
    private int startX;
    private int startY;
    private int state;
    public int targetRingNum;
    private int trickCount2;
    private int trikCount;
    private boolean triking;
    private int tutorID;
    private boolean tutorMoving;
    private MFTouchKey tutorSkip;
    private int tutorX;
    private AnimationDrawer[] tutorialDrawer;
    private AnimationDrawer tutorialSkipDrawer;
    public int velX;
    public int velY;
    public int velZ = 4;
    private int welcomeVelY;
    private int welcomeX;
    private int welcomeY;
    private WhiteBarDrawer whiteBar;
    private int whiteBarX;
    private int whiteBarY;
    private int wordX;

    static {
        boolean[] zArr = new boolean[27];
        zArr[1] = true;
        zArr[2] = true;
        zArr[3] = true;
        zArr[4] = true;
        zArr[5] = true;
        zArr[6] = true;
        zArr[7] = true;
        zArr[8] = true;
        zArr[9] = true;
        zArr[10] = true;
        zArr[11] = true;
        zArr[12] = true;
        zArr[13] = true;
        zArr[14] = true;
        zArr[15] = true;
        zArr[16] = true;
        zArr[17] = true;
        zArr[19] = true;
        zArr[21] = true;
        zArr[22] = true;
        zArr[24] = true;
        ANI_LOOP = zArr;
        int[] iArr = new int[4];
        iArr[1] = 6;
        iArr[2] = 4;
        iArr[3] = 1;
        TUTORIAL_ANIMATION_ID = new int[][]{iArr, new int[]{2, 6, 5, 3}};
    }

    public SpecialPlayer(int characterID) {
        super(-1, 0, 0, 0);
        Animation animation = new Animation(SSDef.SPECIAL_ANIMATION_PATH + ANIMATION_NAME[characterID]);
        this.drawer = animation.getDrawer();
        this.characterBoardDrawer = animation.getDrawer(22, true, 0);
        this.actionID = 8;
        this.hurtRect = new CollisionRect();
        this.noMoving = true;
        this.fontAnimationDrawer = Animation.getInstanceFromQi("/lang" + GlobalResource.languageConfig + "/special_res/sp_font.dat")[0].getDrawer();
        this.spObjDrawer = objAnimation.getDrawer();
        this.state = 8;
        this.preState = this.state;
        this.isNeedTouchPad = false;
        this.whiteBar = WhiteBarDrawer.getInstance();
        Animation tutorAnimation = Animation.getInstanceFromQi("/animation/special/sp_control_change_hint.dat")[0];
        this.tutorialDrawer = new AnimationDrawer[TUTORIAL_ANIMATION_ID[0].length];
        for (int i = 0; i < this.tutorialDrawer.length; i++) {
            this.tutorialDrawer[i] = tutorAnimation.getDrawer();
        }
        this.tutorialSkipDrawer = new Animation("/animation/special/skip").getDrawer();
    }

    public void logic() {
        if (this.trikCount > 0) {
            this.trikCount = Math.max(0, GameTime.advance(this, "trikCount", this.trikCount, -(1)));
            if (this.trikCount == 0 && this.actionID != 26) {
                devcideRingFollow(false);
            }
        }
        if (!this.noMoving) {
            this.posZ = GameTime.advancePosition(this, "posZ", this.posZ, "velZ", this.velZ);
            this.ringHudY = MyAPI.calNextPosition(this, "ringHudY", (double) this.ringHudY, 5.0d, 1, 3);
            this.ringNumY = MyAPI.calNextPosition(this, "ringNumY", (double) this.ringNumY, 15.0d, 1, 3);
        }
        this.moveDistance = 0;
        this.count = GameTime.advance(this, "count", this.count, 1);
        int preX = this.posX;
        int preY = this.posY;
        if (this.state != 10) {
            this.velX = 0;
            this.velY = 0;
        } else if (this.count >= 15) {
            this.velX = 0;
            this.velY = 0;
        }
        if (GlobalResource.spsetConfig == 1) {
            calSensor();
        } else {
            if (this.velX == 0 && this.velY == 0 && this.velZ >= 0) {
                if (Key.repeat(4)) {
                    this.velY = -960;
                } else if (Key.repeat(Key.gDown)) {
                    this.velY = 960;
                }

                if (Key.repeat(Key.gLeft)) {
                    this.velX = -960;
                } else if (Key.repeat(Key.gRight)) {
                    this.velX = 960;
                }
            }

            TouchDirectKey touchDirectKey = Key.touchdirectgamekey;
            if (TouchDirectKey.IsKeyPressed()) {
                int degree = Key.touchdirectgamekey.getDegree();
                if (this.state == 2) {
                    if (this.state != 10) {
                        this.velX = (MyAPI.dCos(degree) * 960) / 100;
                    } else if (this.count >= 15) {
                        this.velX = (MyAPI.dCos(degree) * 960) / 100;
                    }
                } else if (this.state != 10) {
                    this.velX = (MyAPI.dCos(degree) * 960) / 100;
                    this.velY = (MyAPI.dSin(degree) * 960) / 100;
                } else if (this.count >= 15) {
                    this.velX = (MyAPI.dCos(degree) * 960) / 100;
                    this.velY = (MyAPI.dSin(degree) * 960) / 100;
                }

            }
        }
        if (this.state != 10) {
            this.posX = GameTime.advancePosition(this, "posX", this.posX, "velX", this.velX);
            this.posY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
        } else if (this.count >= 15) {
            this.posX = GameTime.advancePosition(this, "posX", this.posX, "velX", this.velX);
            this.posY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
        }
        switch (this.state) {
            case 0:
            case 7:
                if (this.actionID != 25) {
                    this.actionID = 1;
                    if (TouchDirectKey.IsKeyPressed()) {
                        int actDegree = Key.touchdirectgamekey.getDegree();
                        if (actDegree > 337 || actDegree < 23) {
                            this.actionID = 13;
                        } else if (actDegree >= 23 && actDegree < 68) {
                            this.actionID = 17;
                        } else if (actDegree >= 68 && actDegree < 113) {
                            this.actionID = 11;
                        } else if (actDegree >= 113 && actDegree < 158) {
                            this.actionID = 16;
                        } else if (actDegree >= 158 && actDegree < 203) {
                            this.actionID = 12;
                        } else if (actDegree >= 203 && actDegree < 248) {
                            this.actionID = 14;
                        } else if (actDegree >= 248 && actDegree < 293) {
                            this.actionID = 10;
                        } else if (actDegree >= 293 && actDegree < 338) {
                            this.actionID = 15;
                        }
                    }
                    if (this.state == 0) {
                        if (Key.press(Key.gSelect | 8388608)) {
                            this.preState = this.state;
                            this.state = 1;
                            if (this.trikCount > 0) {
                                this.actionID = 26;
                                SoundSystem.getInstance().playBgmSequence(36, 35);
                                devcideRingFollow(true);
                            } else {
                                this.actionID = 20;
                                SoundSystem.getInstance().playSe(38);
                            }
                        }
                        if (Key.repeat(16777216)) {
                            this.state = 2;
                            this.actionID = 18;
                            this.velZ = 6;
                        }
                    }
                    setStayAnimation();
                    break;
                }
                break;
            case 1:
                this.posX = preX;
                this.posY = preY;
                if (this.drawer.checkEnd()) {
                    this.state = this.preState;
                    break;
                }
                break;
            case 2:
                if (Key.repeat(16777216)) {
                    if (GlobalResource.spsetConfig == 1) {
                        switch (this.acc_value) {
                            case 2:
                                this.posX = MyAPI.calNextPosition(this, "posX", (double) this.posX, 0.0d, 3, 16);
                                break;
                            case 4:
                                this.posX = MyAPI.calNextPosition(this, "posX", (double) this.posX, 0.0d, 3, 8);
                                break;
                            case 6:
                                this.posX = MyAPI.calNextPosition(this, "posX", (double) this.posX, 0.0d, 9, 16);
                                break;
                        }
                    } else {
                        this.posX = MyAPI.calNextPosition(this, "posX", (double) this.posX, 0.0d, 1, 4);
                    }
                    this.posY = MyAPI.calNextPosition(this, "posY", (double) this.posY, 0.0d, 1, 4);
                    if (this.drawer.checkEnd()) {
                        this.actionID = 19;
                        break;
                    }
                } else {
                    this.state = 0;
                    this.velZ = 4;
                    break;
                }
                break;
            case 5:
                this.posX = preX;
                this.posY = preY;
                if (this.velZ > 4) {
                    this.velZ = MyAPI.calNextPosition(this, "velZ", (double) this.velZ, 4.0d, 1, 4);
                }
                moveToCenter();
                this.checkCount = GameTime.advance(this, "checkCount", this.checkCount, 1);
                if (!isInCenter() || this.checkCount <= 20) {
                    this.count = GameTime.set(this, "count", 0);
                    this.actionID = 1;
                    this.checkSuccess = (this.ringNum >= this.targetRingNum) | this.debugPassStage;
                } else if (this.checkSuccess) {
                    if (!(this.actionID == 23 || this.actionID == 24)) {
                        this.actionID = 23;
                        if (this.isGoal) {
                            SoundSystem.getInstance().playBgmSequence(37, 35);
                        }
                    }
                    if (!this.isGoal) {
                        if (GameTime.event(this, "count", "logic:406", GameTime.crosses(this, "count", this.count, 16))) {
                            initScoreBase();
                        }
                        if (GameTime.event(this, "count", "logic:409", GameTime.crosses(this, "count", this.count, 20))) {
                            this.targetRingNum = RING_TARGET[SpecialMap.specialStageID][1];
                            WhiteBarDrawer.getInstance().initBar(this, 0);
                        }
                        if (this.whiteBar.getState() == 2 && this.whiteBar.getCount() > 30) {
                            this.state = 7;
                        }
                    } else if (this.count > 48) {
                        this.state = 9;
                        this.count = GameTime.set(this, "count", 0);
                    }
                } else {
                    this.boardOffsetZ = GameTime.advance(this, "boardOffsetZ", this.boardOffsetZ, -(30));
                    this.boardOffsetX = GameTime.advance(this, "boardOffsetX", this.boardOffsetX, -(30));
                    this.actionID = 21;
                    if (this.count > 48) {
                        this.state = 9;
                        this.count = GameTime.set(this, "count", 0);
                    }
                }
                setStayAnimation();
                break;
            case 8:
                this.preState = this.state;
                this.ringNum = 0;
                this.targetRingNum = RING_TARGET[SpecialMap.specialStageID][0];
                this.state = 7;
                this.whiteBar.initBar(this, 0);
                this.startFlag = false;
                this.startX = SCREEN_WIDTH >> 1;
                this.startY = SCREEN_HEIGHT >> 1;
                this.noMoving = true;
                initScoreBase();
                if (showTutorial && GlobalResource.spsetConfig == 1) {
                    this.state = 11;
                    this.actionID = 1;
                    this.count = GameTime.advance(this, "count", this.count, 1);
                    this.tutorX = SCREEN_WIDTH << 1;
                    this.tutorSkip = new MFTouchKey(0, MyAPI.zoomOut(Def.SCREEN_HEIGHT) - 20, 30, 20, 1);
                    MFDevice.addComponent(this.tutorSkip);
                    this.skipOffsetY = 20;
                    showTutorial = false;
                    break;
                } else {
                    this.isNeedTouchPad = true;
                    break;
                }
            case 9:
                this.posX = preX;
                this.posY = preY;
                this.isNeedTouchPad = false;
                break;
            case 10:
                if (this.count >= 15) {
                    this.velX = MyAPI.calNextPosition(this, "velX", (double) this.velX, 0.0d, 1, 4, 3.0d);
                    this.velY = MyAPI.calNextPosition(this, "velY", (double) this.velY, 0.0d, 1, 4, 3.0d);
                    if (this.velZ > 4) {
                        this.velZ = MyAPI.calNextPosition(this, "velZ", (double) this.velZ, 4.0d, 1, 4);
                    }
                }
                if (this.velZ < 4) {
                    this.velZ = GameTime.advance(this, "velZ", this.velZ, 1);
                }
                this.posX = GameTime.advancePosition(this, "posX", this.posX, "velX", this.velX);
                this.posY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
                this.posZ = GameTime.advancePosition(this, "posZ", this.posZ, "velZ", this.velZ);
                if (this.velX == 0 && this.velY == 0 && this.velZ == 4) {
                    this.state = 0;
                }
                if (this.count >= 15 && this.velZ == 4) {
                    this.state = 0;
                }
                this.actionID = 1;
                setStayAnimation();
                break;
            case 11:
                this.posX = preX;
                this.posY = preY;
                setStayAnimation();
                if (GameTime.event(this, "count", "logic:488", GameTime.crosses(this, "count", this.count, 10))) {
                    State.fadeInit(0, 200);
                }
                if (Key.press(Key.B_S1 | 16777216) && !this.tutorMoving && this.skipOffsetY == 0) {
                    switch (this.tutorID) {
                        case 0:
                            this.tutorID = 1;
                            this.tutorMoving = true;
                            break;
                        case 1:
                            this.tutorID = 2;
                            this.tutorMoving = true;
                            break;
                    }
                }
                if (this.tutorID == 2 && !this.tutorMoving && State.fadeChangeOver()) {
                    if (this.changingState) {
                        this.preState = this.state;
                        this.state = 7;
                        this.isNeedTouchPad = true;
                        this.changingState = false;
                        MFDevice.removeComponent(this.tutorSkip);
                        break;
                    } else {
                        State.fadeInit(200, 0);
                        this.changingState = true;
                        break;
                    }
                }
        }
        if (this.posX < -9600) {
            this.posX = -9600;
        }
        if (this.posX > 9600) {
            this.posX = 9600;
        }
        if (this.posY < -7680) {
            this.posY = -7680;
        }
        if (this.posY > 7680) {
            this.posY = 7680;
        }
    }

    public void draw(MFGraphics g) {
        int i;
        calDrawPosition(this.posX >> 6, (-this.posY) >> 6, this.posZ - (this.boardOffsetX >> 6));
        if (!this.isPause) {
            if (this.state != 10 && this.state != 4 && ((this.state != 5 || !isInCenter() || this.checkCount <= 20) && this.state != 9)) {
                int i2 = this.scaleCount;
                this.scaleCount = GameTime.advance(this, "scaleCount", i2, 1);
                if (i2 >= 17) {
                    this.scaleCount = 0;
                }
                if (this.scaleCount < 5) {
                    scale = 0.92f;
                } else if (this.scaleCount < 6) {
                    scale = 0.952f;
                } else if (this.scaleCount < 7) {
                    scale = 0.984f;
                } else if (this.scaleCount < 8) {
                    scale = 1.016f;
                } else if (this.scaleCount < 9) {
                    scale = 1.048f;
                } else if (this.scaleCount < 14) {
                    scale = 1.08f;
                } else if (this.scaleCount < 15) {
                    scale = 1.048f;
                } else if (this.scaleCount < 16) {
                    scale = 1.016f;
                } else if (this.scaleCount < 17) {
                    scale = 0.984f;
                } else if (this.scaleCount < 18) {
                    scale = 0.952f;
                }
            } else if (this.checkSuccess) {
                int i3 = this.scaleCount;
                this.scaleCount = GameTime.advance(this, "scaleCount", i3, 1);
                if (i3 >= 17) {
                    this.scaleCount = 0;
                }
                if (this.scaleCount < 5) {
                    scale = 0.92f;
                } else if (this.scaleCount < 6) {
                    scale = 0.952f;
                } else if (this.scaleCount < 7) {
                    scale = 0.984f;
                } else if (this.scaleCount < 8) {
                    scale = 1.016f;
                } else if (this.scaleCount < 9) {
                    scale = 1.048f;
                } else if (this.scaleCount < 14) {
                    scale = 1.08f;
                } else if (this.scaleCount < 15) {
                    scale = 1.048f;
                } else if (this.scaleCount < 16) {
                    scale = 1.016f;
                } else if (this.scaleCount < 17) {
                    scale = 0.984f;
                } else if (this.scaleCount < 18) {
                    scale = 0.952f;
                }
            } else {
                scale *= SCALE_ZOOM_BASE;
            }
            this.pauseKeepScale = scale;
        } else if (this.state == 10 || this.state == 4 || ((this.state == 5 && isInCenter() && this.checkCount > 20) || this.state == 9)) {
            scale *= SCALE_ZOOM_BASE;
        } else {
            scale = this.pauseKeepScale;
        }
        this.drawer.setActionId(this.actionID);
        this.drawer.setLoop(ANI_LOOP[this.actionID]);
        drawObj(g, this.drawer, ((((int) ((((float) this.posX) * scale) - ((float) this.posX))) * 1) / 4) >> 6, ((((int) ((((float) this.posY) * scale) - ((float) this.posY))) * 1) / 4) >> 6);
        if (this.drawer.checkEnd()) {
            switch (this.actionID) {
                case 23:
                    this.actionID = 24;
                    break;
                case 25:
                    this.actionID = 1;
                    break;
            }
        }
        switch (this.state) {
            case 5:
                if (this.count > 1) {
                    if (this.count < ((this.isGoal || !this.checkSuccess) ? 48 : 26)) {
                        AnimationDrawer animationDrawer = this.spObjDrawer;
                        if (this.checkSuccess) {
                            i = 10;
                        } else {
                            i = 11;
                        }
                        animationDrawer.draw(g, i, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, false, 0);
                        if (!this.checkSuccess) {
                            calDrawPosition((this.posX + this.boardOffsetX) >> 6, ((-this.posY) - this.boardOffsetY) >> 6, this.posZ + (this.boardOffsetZ >> 6));
                            drawObj(g, this.characterBoardDrawer, 0, 0);
                        }
                    }
                }
                if (this.count == 0 && !this.checkSuccess) {
                    this.fontAnimationDrawer.draw(g, 7, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, false, 0);
                    break;
                }
                break;
            case 11:
                State.drawFade(g);
                if (this.count > 10 && State.fadeChangeOver()) {
                    int tutorDesX = (SCREEN_WIDTH >> 1) - (SCREEN_WIDTH * this.tutorID);
                    this.tutorX = MyAPI.calNextPosition(this, "tutorX", (double) this.tutorX, (double) tutorDesX, 1, 3);
                    for (int i4 = 0; i4 < this.tutorialDrawer.length; i4++) {
                        this.tutorialDrawer[i4].setPause(true);
                        if (this.tutorX == tutorDesX) {
                            this.tutorialDrawer[i4].setPause(false);
                            this.tutorMoving = false;
                        } else if (this.tutorID >= 1) {
                            this.tutorialDrawer[i4].draw(g, TUTORIAL_ANIMATION_ID[this.tutorID - 1][i4], ((this.tutorID - 1) * SCREEN_WIDTH) + this.tutorX, SCREEN_HEIGHT >> 1, true, 0);
                        }
                        if (this.tutorID <= 1) {
                            this.tutorialDrawer[i4].draw(g, TUTORIAL_ANIMATION_ID[this.tutorID][i4], (this.tutorID * SCREEN_WIDTH) + this.tutorX, SCREEN_HEIGHT >> 1, true, 0);
                        }
                    }
                    this.skipOffsetY = MyAPI.calNextPosition(this, "skipOffsetY", (double) this.skipOffsetY, (double) (this.tutorID > 1 ? 20 : 0), 1, 3);
                    this.tutorialSkipDrawer.draw(g, Key.repeat(Key.B_S1) ? 1 : 0, 0, this.skipOffsetY + (SCREEN_HEIGHT - 1), true, 0);
                    break;
                }
        }
        drawcollisionRect(g);
    }

    public void drawInfo(MFGraphics g) {
        this.whiteBar.setPauseCount(this.isPause);
        switch (this.state) {
            case 5:
                WhiteBarDrawer.getInstance().drawBar(g);
                break;
            case 7:
                if (this.whiteBar.getState() == 2 || this.whiteBar.getState() == 3 || this.whiteBar.getState() == 4) {
                    if (!this.isPause) {
                        State.staticDrawFadeSlow(g);
                    }
                } else if (this.preState == 8) {
                    g.setColor(0);
                    MyAPI.fillRect(g, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                }
                if (this.startFlag) {
                    this.fontAnimationDrawer.draw(g, 4, this.startX, this.startY, false, 0);
                    if (this.whiteBar.getState() == 3) {
                        this.startX = GameTime.advance(this, "startX", this.startX, -(96));
                    }
                }
                WhiteBarDrawer.getInstance().drawBar(g);
                if (this.whiteBar.getState() == 1) {
                    this.startFlag = true;
                }
                if (this.whiteBar.getState() == 4) {
                    this.state = 0;
                    this.noMoving = false;
                    break;
                }
                break;
            case 8:
                g.setColor(0);
                MyAPI.fillRect(g, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                break;
        }
        if (!(this.state == 8 || this.state == 7)) {
            this.spObjDrawer.draw(g, 12, SCREEN_WIDTH >> 1, this.ringHudY, false, 0);
            NumberDrawer.drawNum(g, 0, this.targetRingNum, (SCREEN_WIDTH >> 1) + 12, this.ringNumY, 2);
            NumberDrawer.drawNum(g, 0, this.ringNum, (SCREEN_WIDTH >> 1) - 12, this.ringNumY - 12, 2);
        }
        if (this.triking && this.trikCount == 0 && this.trickCount2 < 16) {
            this.trickCount2 = GameTime.advance(this, "trickCount2", this.trickCount2, 1);
            if (this.actionID == 26 || this.niceTriking) {
                this.fontAnimationDrawer.draw(g, 5, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, false, 0);
                this.niceTriking = true;
            } else {
                this.fontAnimationDrawer.draw(g, 6, SCREEN_WIDTH >> 1, SCREEN_HEIGHT >> 1, false, 0);
            }
            if (this.trikCount >= 16) {
                this.triking = false;
            }
        }
    }

    public void initWelcome() {
        this.welcomeX = ((SCREEN_WIDTH >> 1) + 50) << 6;
        this.welcomeY = (SCREEN_HEIGHT + 160) << 6;
        this.welcomeVelY = -2500;
    }

    public void logicWelcome() {
        this.welcomeVelY = GameTime.advance(this, "welcomeVelY", this.welcomeVelY, GRAVITY);
        this.welcomeY = GameTime.advance(this, "welcomeY", this.welcomeY, this.welcomeVelY);
        this.welcomeX = GameTime.advance(this, "welcomeX", this.welcomeX, WELCOMT_VEL_X);
    }

    public void drawWelcome(MFGraphics g) {
        int actioID;
        if (this.welcomeVelY < -500) {
            actioID = 27;
        } else if (this.welcomeVelY < -333) {
            actioID = 28;
        } else if (this.welcomeVelY < -166) {
            actioID = 29;
        } else if (this.welcomeVelY < 166) {
            actioID = 30;
        } else if (this.welcomeVelY < 333) {
            actioID = 31;
        } else if (this.welcomeVelY < 500) {
            actioID = 32;
        } else {
            actioID = 33;
        }
        this.drawer.draw(g, actioID, this.welcomeX >> 6, this.welcomeY >> 6, true, 0);
        this.fontAnimationDrawer.draw(g, 0, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 40, true, 0);
    }

    public boolean isWelcomeOver() {
        return this.welcomeY > ((SCREEN_HEIGHT + 60) << 6);
    }

    public void close() {
        Animation.closeAnimationDrawerArray(this.tutorialDrawer);
        this.tutorialDrawer = null;
        Animation.closeAnimationDrawer(this.drawer);
        this.drawer = null;
    }

    public void doWhileCollision(SpecialObject collisionObj) {
    }

    public void refreshCollision(int x, int y) {
        this.rect1 = this.drawer.getCRect();
        this.rect2 = this.drawer.getARect();
        if (this.rect1 != null) {
            this.collisionRect.setTwoPosition((x >> 6) + this.rect1[0], ((-y) >> 6) - this.rect1[1], (x >> 6) + this.rect1[0] + this.rect1[2], (((-y) >> 6) - this.rect1[1]) - this.rect1[3]);
        }
        if (this.rect2 != null) {
            if (this.attackCollisionRect == null) {
                this.attackCollisionRect = new CollisionRect();
            }
            this.attackCollisionRect.setTwoPosition((x >> 6) + this.rect2[0], ((-y) >> 6) - this.rect2[1], (x >> 6) + this.rect2[0] + this.rect2[2], (((-y) >> 6) - this.rect2[1]) - this.rect2[3]);
        }
    }

    private void drawcollisionRect(MFGraphics g) {
        if (this.rect1 != null && SonicDebug.showCollisionRect) {
            g.setColor(16711680);
            g.drawRect(((drawX + (SCREEN_WIDTH >> 1)) - SpecialMap.getCameraOffsetX()) + this.rect1[0], ((drawY + (SCREEN_HEIGHT >> 1)) - SpecialMap.getCameraOffsetY()) + this.rect1[1], this.rect1[2], this.rect1[3]);
        }
        if (this.rect2 != null && SonicDebug.showCollisionRect) {
            g.setColor(65280);
            g.drawRect(((drawX + (SCREEN_WIDTH >> 1)) - SpecialMap.getCameraOffsetX()) + this.rect2[0], ((drawY + (SCREEN_HEIGHT >> 1)) - SpecialMap.getCameraOffsetY()) + this.rect2[1], this.rect2[2], this.rect2[3]);
        }
    }

    public void beSpringX(int velX2) {
        this.velX = velX2 << 6;
        this.state = 10;
        this.count = GameTime.set(this, "count", 0);
    }

    public void beSpringY(int velY2) {
        this.velY = velY2 << 6;
        this.state = 10;
        this.count = GameTime.set(this, "count", 0);
    }

    public void beSpringZ(int velZ2) {
        this.velZ = velZ2;
        this.state = 10;
        this.count = GameTime.set(this, "count", 0);
    }

    public void beHurt() {
        if (this.actionID != 25) {
            this.actionID = 25;
            this.state = 0;
            if (this.ringNum == 0) {
                SoundSystem.getInstance().playSe(35);
            } else {
                SoundSystem.getInstance().playSe(13);
            }
            int lostnum = 10;
            this.ringNum -= 10;
            if (this.ringNum < 0) {
                lostnum = 10 + this.ringNum;
                this.ringNum = 0;
            }
            for (int i = 0; i < 10; i++) {
                ringDirection[i] = i;
                ringVel[i][0] = (MyAPI.dCos(i * 36) * 800) / 100;
                ringVel[i][1] = (MyAPI.dSin(i * 36) * 800) / 100;
            }
            for (int i2 = 0; i2 < 10; i2++) {
                int pos = MyRandom.nextInt(0, 9);
                int tmp = ringDirection[pos];
                ringDirection[pos] = ringDirection[i2];
                ringDirection[i2] = tmp;
            }
            for (int i3 = 0; i3 < lostnum; i3++) {
                addExtraObject(new SSLostRing(this.posX, -this.posY, this.posZ, ringVel[ringDirection[i3]][0], ringVel[ringDirection[i3]][1], this.velZ));
            }
        }
    }

    public void getRing(int ringNum2) {
        this.ringNum += ringNum2;
    }

    public void setCheckPoint() {
        this.checkSuccess = (this.ringNum >= this.targetRingNum) | this.debugPassStage;
        this.noMoving = true;
        this.state = 5;
        this.count = GameTime.set(this, "count", 0);
        this.checkCount = GameTime.set(this, "checkCount", 0);
        this.isGoal = false;
        this.velZ = 4;
    }

    public void setGoal() {
        setCheckPoint();
        this.isGoal = true;
    }

    public boolean isTricking() {
        return this.state == 1;
    }

    private void calSensor() {
        float accX = MFSensor.getAccX();
        float accY = MFSensor.getAccY();
        switch (GlobalResource.sensorConfig) {
            case 0:
                this.acc_value = 2;
                break;
            case 1:
                this.acc_value = 4;
                break;
            case 2:
                this.acc_value = 6;
                break;
        }
        float accX2 = Math.max(Math.min(accX, (float) (this.acc_value + 5)), (float) (5 - this.acc_value));
        float accY2 = Math.max(Math.min(accY, (float) this.acc_value), (float) (-this.acc_value));
        if (this.state == 2) {
            if (this.state != 10) {
                this.velX = (int) ((accY2 * 3600.0f) / 10.0f);
            } else if (this.count >= 15) {
                this.velX = (int) ((accY2 * 3600.0f) / 10.0f);
            }
        } else if (this.state != 10) {
            this.velX = (int) ((accY2 * 3600.0f) / 10.0f);
            this.velY = (int) (((accX2 - 5.0f) * 3600.0f) / 10.0f);
        } else if (this.count >= 15) {
            this.velX = (int) ((accY2 * 3600.0f) / 10.0f);
            this.velY = (int) (((accX2 - 5.0f) * 3600.0f) / 10.0f);
        }
    }

    public void moveToCenter() {
        this.posX = MyAPI.calNextPosition(this, "posX", (double) this.posX, 0.0d, 2, 24);
        this.posY = MyAPI.calNextPosition(this, "posY", (double) this.posY, 0.0d, 2, 24);
    }

    public boolean isInCenter() {
        return this.posY == 0 && this.posX == 0;
    }

    public void setStayAnimation() {
        if (this.actionID == 1) {
            this.moveDistance = 0;
            if (this.posX < -2560) {
                this.moveDistance |= 2;
            } else if (this.posX > 2560) {
                this.moveDistance |= 8;
            }
            if (this.posY < -2560) {
                this.moveDistance |= 1;
            } else if (this.posY > 2560) {
                this.moveDistance |= 4;
            }
            if ((this.moveDistance & 1) != 0) {
                this.actionID = 2;
                if ((this.moveDistance & 2) != 0) {
                    this.actionID = 6;
                } else if ((this.moveDistance & 8) != 0) {
                    this.actionID = 7;
                }
            } else if ((this.moveDistance & 4) != 0) {
                this.actionID = 3;
                if ((this.moveDistance & 2) != 0) {
                    this.actionID = 8;
                } else if ((this.moveDistance & 8) != 0) {
                    this.actionID = 9;
                }
            } else if ((this.moveDistance & 2) != 0) {
                this.actionID = 4;
            } else if ((this.moveDistance & 8) != 0) {
                this.actionID = 5;
            }
        }
    }

    private void initScoreBase() {
        this.ringHudY = -19;
        this.ringNumY = RING_NUM_ORIGINAL;
    }

    public boolean isOver() {
        return this.state == 9 && this.count > 20;
    }

    public void drawWord(MFGraphics g, int wordID, int x, int y) {
        this.fontAnimationDrawer.draw(g, 2, x, y, false, 0);
        this.fontAnimationDrawer.draw(g, 3, NumberDrawer.drawNum(g, 3, this.targetRingNum, x + 50, y, 1) + 8, y, false, 0);
    }

    public int getWordLength(int wordID) {
        return 224;
    }

    public int getRingNum() {
        return this.ringNum;
    }

    public void setTrikCount() {
        this.triking = true;
        this.niceTriking = false;
        this.trikCount = GameTime.set(this, "trikCount", 6);
        this.trickCount2 = GameTime.set(this, "trickCount2", 0);
    }

    public void devcideRingFollow(boolean flag) {
        for (int i = 0; i < decideObjects.size(); i++) {
            ((SSFollowRing) decideObjects.elementAt(i)).setFollowOrNot(flag);
        }
        decideObjects.removeAllElements();
    }

    public boolean isNeedTouchPad() {
        return this.isNeedTouchPad;
    }

    public void setPause(boolean statePause) {
        this.isPause = statePause;
    }
}
