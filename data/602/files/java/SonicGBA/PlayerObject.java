package SonicGBA;

import Common.NumberDrawer;
import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import Lib.Direction;
import Lib.Line;
import Lib.MyAPI;
import Lib.MyRandom;
import Lib.SoundSystem;
import Lib.crlFP32;
import State.GameState;
import State.State;
import com.sega.engine.action.ACBlock;
import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;
import com.sega.engine.action.ACUtilities;
import com.sega.engine.action.ACWorld;
import com.sega.engine.action.ACWorldCalUser;
import com.sega.engine.action.ACWorldCollisionCalculator;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.platform.ChargePlatform;
import java.util.Vector;
import State.TitleState;
import com.sega.mobile.framework.device.MFDevice;

public abstract class PlayerObject extends MoveObject implements Focusable, ACWorldCalUser {
   public boolean Player2Hurt = false;
   public PlayerObject myPlayer = null;
   public static PlayerObject[] myPlayerStatic = new PlayerObject[2];
   private static final int A = 3072;
   public boolean AI = false;
   private static final String ANIMATION_PATH = "/animation";
   public static final int ANI_ATTACK_1 = 18;
   public static final int ANI_ATTACK_2 = 19;
   public static final int ANI_ATTACK_3 = 20;
   public static final int ANI_BANK_1 = 32;
   public static final int ANI_BANK_2 = 33;
   public static final int ANI_BANK_3 = 34;
   public static final int ANI_BAR_ROLL_1 = 22;
   public static final int ANI_BAR_ROLL_2 = 23;
   private static final int ANI_BIG_ZERO = 67;
   public static final int ANI_BRAKE = 17;
   public static final int ANI_BREATHE = 49;
   public static final int ANI_CAUGHT = 52;
   public static final int ANI_CELEBRATE_1 = 35;
   public static final int ANI_CELEBRATE_2 = 36;
   public static final int ANI_CELEBRATE_3 = 37;
   public static final int ANI_CLIFF_1 = 47;
   public static final int ANI_CLIFF_2 = 48;
   public static final int ANI_DEAD = 41;
   public static final int ANI_DEAD_PRE = 45;
   public static final int ANI_FALLING = 10;
   public static final int ANI_HURT = 12;
   public static final int ANI_HURT_PRE = 44;
   public static final int ANI_JUMP = 4;
   public static final int ANI_JUMP_ROLL = 15;
   public static final int ANI_JUMP_RUSH = 16;
   public static final int ANI_LOOK_UP_1 = 38;
   public static final int ANI_LOOK_UP_2 = 39;
   public static final int ANI_LOOK_UP_OVER = 40;
   public static final int ANI_NONE = -1;
   public static final int ANI_POAL_PULL = 13;
   public static final int ANI_POAL_PULL_2 = 31;
   public static final int ANI_POP_JUMP_DOWN_SLOW = 43;
   public static final int ANI_POP_JUMP_UP = 14;
   public static final int ANI_POP_JUMP_UP_SLOW = 42;
   public static final int ANI_PULL = 24;
   public static final int ANI_PULL_BAR_MOVE = 28;
   public static final int ANI_PULL_BAR_STAY = 27;
   public static final int ANI_PUSH_WALL = 8;
   public static final int ANI_RAIL_ROLL = 21;
   public static final int ANI_ROPE_ROLL_1 = 25;
   public static final int ANI_ROPE_ROLL_2 = 26;
   public static final int ANI_ROTATE_JUMP = 9;
   public static final int ANI_RUN_1 = 1;
   public static final int ANI_RUN_2 = 2;
   public static final int ANI_RUN_3 = 3;
   public static final int ANI_SLIP = 11;
   private static final int ANI_SMALL_ZERO = 27;
   private static final int ANI_SMALL_ZERO_Y = 37;
   public static final int ANI_SPIN_LV1 = 6;
   public static final int ANI_SPIN_LV2 = 7;
   public static final int ANI_SQUAT = 5;
   public static final int ANI_SQUAT_PROCESS = 46;
   public static final int ANI_STAND = 0;
   public static final int ANI_VS_FAKE_KNUCKLE = 53;
   public static final int ANI_WAITING_1 = 50;
   public static final int ANI_WAITING_2 = 51;
   public static final int ANI_WIND_JUMP = 29;
   public static final int ANI_YELL = 30;
   private static final int ASPIRATE_INTERVAL = 3;
   public static final int ATTACK_POP_POWER;
   private static final int ATTRACT_EFFECT_HEIGHT = 9600;
   private static final int ATTRACT_EFFECT_WIDTH = 9600;
   private static final int BACKGROUND_WIDTH = 80;
   public static final int BALL_HEIGHT_OFFSET = 1024;
   public static final int BANKING_MIN_SPEED = 500;
   public static int BANK_BRAKE_SPEED_LIMIT;
   private static final int BAR_COLOR = 2;
   private static final int BG_NUM;
   public static final int BIG_NUM = 2;
   private static final int BODY_OFFSET = 768;
   private static final int BREATHE_IMAGE_HEIGHT = 16;
   private static final int BREATHE_IMAGE_WIDTH = 16;
   private static final int BREATHE_TIME_COUNT = 21000;
   private static final int BREATHE_TO_DIE_PER_COUNT = 1760;
   private static final int B_1 = 5760;
   private static final int B_2 = 11264;
   private static final int C = 3072;
   private static final int CAMERA_MAX_DISTANCE = 20;
   public static final boolean CAN_BE_SQUEEZE = true;
   private static final int CENTER_X = 660480;
   private static final int CENTER_Y = 63488;
   public static final int CHARACTER_AMY = 3;
   public static final int CHARACTER_KNUCKLES = 2;
   public static final int[] CHARACTER_LIST;
   public static final int CHARACTER_SONIC = 0;
   public static final int CHARACTER_TAILS = 1;
   public static final byte COLLISION_STATE_IN_SAND = 3;
   public static final byte COLLISION_STATE_JUMP = 1;
   public static final byte COLLISION_STATE_NONE = 4;
   public static final byte COLLISION_STATE_NUM = 4;
   public static final byte COLLISION_STATE_ON_OBJECT = 2;
   public static final byte COLLISION_STATE_WALK = 0;
   private static final int COUNT_INDEX = 1;
   private static final boolean DEBUG_WUDI = false;
   private static final int[] DEGREE_DIVIDE;
   public static final int DETECT_HEIGHT = 2048;
   private static final int DIE_DRIP_STATE_JUMP_V0 = -800;
   private static final int DO_POAL_MOTION_SPEED = 600;
   private static final boolean[] EFFECT_LOOP;
   protected static final int EFFECT_NONE = -1;
   protected static final int EFFECT_SAND_1 = 0;
   protected static final int EFFECT_SAND_2 = 1;
   private static final int ENLARGE_NUM = 1920;
   private static final int FADE_FILL_HEIGHT = 40;
   private static final int FADE_FILL_WIDTH = 40;
   public static int FAKE_GRAVITY_ON_BALL;
   public static int FAKE_GRAVITY_ON_WALK;
   public static final int FALL_IN_SAND_SLIP_LEFT = 2;
   public static final int FALL_IN_SAND_SLIP_NONE = 0;
   public static final int FALL_IN_SAND_SLIP_RIGHT = 1;
   private static final int FOCUS_MAX_OFFSET;
   private static final int FOCUS_MOVE_SPEED = 15;
   protected static final int FOCUS_MOVING_DOWN = 2;
   private static final int FOCUS_MOVING_NONE = 0;
   protected static final int FOCUS_MOVING_UP = 1;
   private static final int FONT_NUM = 7;
   private static final int FOOT_OFFSET = 256;
   private static final int[] FOOT_OFFSET_X;
   public static final int HEIGHT = 1536;
   private static final int HINER_JUMP_LIMIT = 1024;
   private static final int HINER_JUMP_MAX = 4352;
   private static final int HINER_JUMP_X_ADD = 1024;
   private static final int HINER_JUMP_Y = 2048;
   public static final int HUGE_POWER_SPEED = 1900;
   public static final int HURT_COUNT = 48;
   public static int HURT_POWER_X;
   public static int HURT_POWER_Y;
   private static final int ICE_SLIP_FLUSH_OFFSET_Y = 512;
   private static final int INVINCIBLE_COUNT = 320;
   public static final boolean IN_BLOCK_CHECK = false;
   private static final float IN_WATER_WALK_SPEED_SCALE1 = 5.0F;
   private static final float IN_WATER_WALK_SPEED_SCALE2 = 9.0F;
   private static final int ITEM_INDEX = 0;
   public static final int ITEM_INVINCIBLE = 3;
   public static final int ITEM_LIFE = 0;
   public static final int ITEM_RING_10 = 7;
   public static final int ITEM_RING_5 = 6;
   public static final int ITEM_RING_RANDOM = 5;
   public static final int ITEM_SHIELD = 1;
   public static final int ITEM_SHIELD_2 = 2;
   public static final int ITEM_SPEED = 4;
   public static boolean IsDisplayRaceModeNewRecord;
   public static boolean IsStarttoCnt;
   private static final int JUMP_EFFECT_HEIGHT = 1920;
   private static final int JUMP_EFFECT_OFFSET_Y = 256;
   private static final int JUMP_EFFECT_WIDTH = 1920;
   public static int JUMP_INWATER_START_VELOCITY;
   public static int JUMP_PROTECT;
   public static int JUMP_REVERSE_POWER;
   public static int JUMP_RUSH_SPEED_PLUS;
   public static int JUMP_START_VELOCITY;
   private static final int LEFT_FOOT_OFFSET_X = -256;
   private static final int LEFT_WALK_COLLISION_CHECK_OFFSET_X = -512;
   private static final int LEFT_WALK_COLLISION_CHECK_OFFSET_Y = -512;
   public static final int LIFE_NUM_RESET = 2;
   private static final int LOOK_COUNT = 32;
   private static final int MAX_ITEM = 5;
   private static final int MAX_ITEM_SHOW_NUM = 4;
   public static int MAX_VELOCITY;
   public static final int MIN_ATTACK_JUMP = -900;
   private static final int MOON_STAR_DES_X_1;
   private static final int MOON_STAR_DES_Y_1 = 26;
   private static final int MOON_STAR_FRAMES_1 = 207;
   private static final int MOON_STAR_FRAMES_2 = 120;
   private static final int MOON_STAR_ORI_X_1;
   private static final int MOON_STAR_ORI_Y_1 = 18;
   public static int MOVE_POWER;
   public static int MOVE_POWER_IN_AIR;
   public static int MOVE_POWER_REVERSE;
   public static int MOVE_POWER_REVERSE_BALL;
   public static final boolean NEED_RESET_DEDREE = false;
   private static final int[] NUM_ANI_ID;
   public static final int NUM_CENTER = 0;
   private static final int NUM_DISTANCE;
   public static final int NUM_DISTANCE_BIG = 72;
   public static final int NUM_LEFT = 1;
   private static final int NUM_PIC_HEIGHT;
   private static final int NUM_PIC_WIDTH;
   public static final int NUM_RIGHT = 2;
   private static final int[] NUM_SPACE;
   private static final int[] NUM_SPACE_ANIMATION;
   private static final int[] NUM_SPACE_FONT;
   private static final int[] NUM_SPACE_IMAGE;
   public static final int NumberSideX;
   public static final int PAUSE_FRAME_HEIGHT;
   public static final int PAUSE_FRAME_OFFSET_X;
   public static final int PAUSE_FRAME_OFFSET_Y;
   public static int PAUSE_FRAME_WIDTH;
   private static int[] PAUSE_MENU_NORMAL_ITEM;
   private static final int[] PAUSE_MENU_NORMAL_NOSHOP;
   private static final int[] PAUSE_MENU_NORMAL_SHOP;
   private static final int[] PAUSE_MENU_RACE_ITEM;
   private static final int PIPE_SET_POWER = 2880;
   protected static final String PLAYER_ANIMATION_PATH = "/animation/player";
   private static final int RAIL_FLIPPER_V0 = -3380;
   private static final int RAIL_OUT_SPEED_VY0 = -1200;
   private static final int[] RANDOM_RING_NUM;
   public static final int RED_NUM = 3;
   private static final int RIGHT_FOOT_OFFSET_X = 256;
   private static final int RIGHT_WALK_COLLISION_CHECK_OFFSET_X = 512;
   private static final int RIGHT_WALK_COLLISION_CHECK_OFFSET_Y = -512;
   protected static final int ROTATE_MODE_NEGATIVE = 2;
   protected static final int ROTATE_MODE_NEVER_MIND = 0;
   protected static final int ROTATE_MODE_POSITIVE = 1;
   public static int RUN_BRAKE_SPEED_LIMIT;
   public static int RingBonus;
   public static final int SHOOT_POWER = -1800;
   private static final int SIDE_COLLISION_NUM = -2;
   private static final int SIDE_FOOT_FROM_CENTER = 256;
   private static final int SMALL_JUMP_COUNT = 4;
   public static final int SMALL_NUM = 0;
   public static final int SMALL_NUM_Y = 1;
   public static final int SONIC_ATTACK_LEVEL_1_V0 = 488;
   public static final int SONIC_ATTACK_LEVEL_2_V0 = 672;
   public static final int SONIC_ATTACK_LEVEL_3_V0 = 1200;
   private static final int SONIC_DRAW_HEIGHT = 1920;
   public static int SPEED_FLOAT_DEVICE;
   public static int SPEED_LIMIT_LEVEL_1;
   public static int SPEED_LIMIT_LEVEL_2;
   public static int SPIN_INWATER_START_SPEED_1;
   public static int SPIN_INWATER_START_SPEED_2;
   private static final int SPIN_KEY_COUNT = 20;
   private static final int SPIN_LV2_COUNT = 12;
   private static final int SPIN_LV2_COUNT_CONF = 36;
   public static int SPIN_START_SPEED_1;
   public static int SPIN_START_SPEED_2;
   private static final int STAGE_PASS_STR_SPACE;
   private static final int STAGE_PASS_STR_SPACE_FONT;
   protected static final byte STATE_PIPE_IN = 0;
   protected static final byte STATE_PIPE_OVER = 2;
   protected static final byte STATE_PIPING = 1;
   private static final int SUPER_SONIC_CHANGING_CENTER_Y = 25280;
   private static final int SUPER_SONIC_STAND_POS_X = 235136;
   private static final int TERMINAL_COUNT = 10;
   public static final int TERMINAL_NO_MOVE = 1;
   public static final int TERMINAL_RUN_TO_RIGHT = 0;
   public static final int TERMINAL_RUN_TO_RIGHT_2 = 2;
   public static final int TERMINAL_SUPER_SONIC = 3;
   protected static final byte TER_STATE_BRAKE = 1;
   protected static final byte TER_STATE_CHANGE_1 = 4;
   protected static final byte TER_STATE_CHANGE_2 = 5;
   protected static final byte TER_STATE_GO_AWAY = 6;
   protected static final byte TER_STATE_LOOK_MOON = 2;
   protected static final byte TER_STATE_LOOK_MOON_WAIT = 3;
   protected static final byte TER_STATE_RUN = 0;
   protected static final byte TER_STATE_SHINING_2 = 7;
   protected static final int[] TRANS;
   public static int TimeBonus;
   private static final int WALK_COLLISION_CHECK_OFFSET_X = 0;
   private static final int WALK_COLLISION_CHECK_OFFSET_Y = 0;
   private static final int WHITE_BACKGROUND_ID = 118;
   protected static final int WIDTH = 1024;
   public static final int YELLOW_NUM = 4;
   private static AnimationDrawer bariaDrawer;
   private static MFImage breatheCountImage;
   public static int characterID;
   public int characterIDMulti;
   public static int characterID2;
   private static int clipendw;
   private static int cliph;
   private static int clipspeed;
   private static int clipstartw;
   private static int clipx;
   private static int clipy;
   private static ACBlock collisionBlockGround;
   private static ACBlock collisionBlockGroundTmp;
   private static ACBlock collisionBlockSky;
   public static int currentMarkId;
   private static int[] currentPauseMenuItem;
   public static int cursor;
   public static int cursorIndex;
   public static int cursorMax;
   private static int fadeAlpha;
   private static int fadeFromValue;
   private static int[] fadeRGB;
   private static int fadeToValue;
   private static AnimationDrawer fastRunDrawer;
   private static AnimationDrawer gBariaDrawer;
   private static AnimationDrawer getLifeDrawer;
   private static AnimationDrawer headDrawer;
   private static Animation invincibleAnimation;
   public static int invincibleCount;
   private static AnimationDrawer invincibleDrawer;
   public static boolean isDeadLineEffect;
   public static boolean isNeedPlayWaterSE;
   public static boolean isOnlyBarOut;
   private static boolean isStartStageEndFlag;
   public static boolean isTerminal;
   public static boolean isbarOut;
   private static int itemOffsetX;
   private static int[][] itemVec;
   public static int lastTimeCount;
   private static int lifeDrawerX;
   public static int lifeNum;
   private static AnimationDrawer moonStarDrawer;
   private static int movespeedx;
   private static int movespeedy;
   private static int newRecordCount;
   private static AnimationDrawer numDrawer;
   public static MFImage numImage;
   private static int offsetx;
   private static int offsety;
   public static int onlyBarOutCnt;
   public static int onlyBarOutCntMax;
   public static int overTime;
   private static int passStageActionID;
   private static int preFadeAlpha;
   private static int preLifeNum;
   private static int preScoreNum;
   private static int preTimeCount;
   public static int raceScoreNum;
   protected static int ringNum;
   private static int ringRandomNum;
   protected static int ringTmpNum;
   private static int score1;
   private static int score2;
   public static int scoreNum;
   private static int shieldType;
   public static int slidingFrame;
   protected static int speedCount;
   private static int stageEndFrameCnt;
   private static int stagePassResultOutOffsetX;
   protected static byte terminalState;
   protected static int terminalType;
   public static int timeCount;
   protected static boolean timeStopped;
   private static int totalPlusscore;
   private static AnimationDrawer uiDrawer;
   public static int uiOffsetX;
   private static MFImage uiRingImage;
   private static MFImage uiSonicHeadImage;
   private static AnimationDrawer waterSprayDrawer;
   public boolean IsStandOnItems;
   private CollisionRect aaaAttackRect;
   protected int animationID;
   private int attackAnimationID;
   private int attackCount;
   private int attackLevel;
   public Vector attackRectVec;
   public CollisionRect attractRect;
   public boolean bankwalking;
   public boolean beAttackByHari;
   public int bePushedFootX;
   protected int breatheCount;
   protected int breatheFrame;
   private int breatheNumCount;
   private int breatheNumY;
   public boolean canAttackByHari;
   public boolean changeRectHeight;
   protected int checkPositionX;
   protected int checkPositionY;
   private boolean checkedObject;
   public boolean collisionChkBreak;
   private int collisionLayer;
   public byte collisionState;
   public boolean controlObjectLogic;
   private long count;
   protected boolean dashRolling;
   private int deadPosX;
   private int deadPosY;
   protected int degreeForDraw;
   protected int degreeRotateMode;
   public int degreeStable = 0;
   protected boolean doJumpForwardly;
   protected AnimationDrawer drawer;
   private int drownCnt;
   private boolean ducting;
   private int ductingCount;
   protected Animation dustEffectAnimation;
   private AnimationDrawer effectDrawer;
   protected int effectID;
   private boolean enteringSP;
   public boolean extraAttackFlag = false;
   protected int faceDegree = 0;
   public boolean faceDirection = true;
   protected boolean fading;
   public int fallTime;
   public int fallinSandSlipState;
   public boolean finishDeadStuff;
   protected int focusMovingState;
   private int focusOffsetY;
   public boolean footObjectLogic;
   int footOffsetX;
   public GameObject footOnObject;
   public int footPointX = 0;
   public int footPointY;
   private int frame;
   private int frameCnt;
   private boolean freeMoveDebug;
   public int hurtCount;
   public boolean hurtNoControl;
   public boolean ignoreFirstTouch;
   public boolean isAfterSpinDash;
   public boolean isAntiGravity;
   public boolean isAttackBoss4;
   public boolean isAttacking;
   public boolean isCelebrate;
   public boolean isCrashFallingSand;
   public boolean isCrashPipe;
   public boolean isDead;
   public boolean isDirectioninSkyChange;
   public boolean isInGravityCircle;
   public boolean isInSnow;
   protected boolean isInWater;
   public boolean isOnBlock;
   public boolean isOnlyJump;
   public boolean isPowerShoot;
   public boolean isResetWaitAni;
   public boolean isSharked;
   public int isSidePushed;
   public boolean isStopByObject;
   private boolean isTouchSandSlip;
   public boolean isUpPipeIn;
   private CollisionRect jumpAttackRect;
   private int justLeaveCount;
   private int justLeaveDegree;
   public boolean justLeaveLand;
   public boolean leavingBar;
   public boolean leftStopped;
   private int lookCount;
   protected int maxVelocity;
   private int moonStarFrame1;
   private int moonStarFrame2;
   public int moveLimit;
   private int movePower;
   private int movePowerInAir;
   private int movePowerReserseBall;
   private int movePowerReserseBallInSand;
   private int movePowerReverse;
   private int movePowerReverseInSand;
   public int movedSpeedX;
   public int movedSpeedY;
   protected int myAnimationID;
   private int nextVelX;
   private int nextVelY;
   private boolean noKeyFlag;
   public boolean noMoving;
   private int noMovingPosition;
   public boolean noVelMinus;
   public boolean onBank;
   private boolean onGround = false;
   private boolean onObjectContinue;
   private boolean orgGravity;
   public boolean outOfControl;
   public GameObject outOfControlObject;
   private int pipeDesX;
   private int pipeDesY;
   protected byte pipeState;
   public boolean piping;
   private int preBreatheNumCount;
   public CollisionRect preCollisionRect;
   private int preFocusX;
   private int preFocusY;
   public boolean prefaceDirection = true;
   private int preposY;
   private boolean pushOnce;
   private boolean railFlipping;
   protected Line railLine;
   public boolean railOut;
   public boolean railing;
   public boolean rightStopped;
   private int sBlockX;
   private int sBlockY;
   private int sXPosition;
   private int sYPosition;
   private int sandFrame;
   private boolean sandStanding;
   private boolean setNoMoving;
   public boolean showWaterFlush;
   public boolean slideSoundStart;
   private boolean slipFlag;
   public boolean slipping;
   private int smallJumpCount;
   public boolean speedLock;
   private int spinCount = 0;
   private int spinDownWaitCount;
   private int spinKeyCount;
   private boolean squeezeFlag;
   public int terminalCount;
   public int terminalOffset;
   private boolean transing;
   private boolean visible;
   private int waitingCount;
   private int waitingLevel;
   private AnimationDrawer waterFallDrawer;
   private boolean waterFalling;
   private AnimationDrawer waterFlushDrawer;
   private boolean waterSprayFlag;
   private int waterSprayX;
   protected ACWorldCollisionCalculator worldCal;
   private boolean xFirst;

   static {
      int[] var5 = new int[]{0, 1, 2, 3};
      CHARACTER_LIST = var5;
      characterID = 0;
      EFFECT_LOOP = new boolean[]{true, true};
      MOVE_POWER = 28;
      MOVE_POWER_IN_AIR = 92;
      MAX_VELOCITY = 1280;
      MOVE_POWER_REVERSE = 336;
      MOVE_POWER_REVERSE_BALL = 96;
      FAKE_GRAVITY_ON_WALK = 72;
      FAKE_GRAVITY_ON_BALL = 224;
      JUMP_START_VELOCITY = -1208 - GRAVITY;
      JUMP_INWATER_START_VELOCITY = -1304 - GRAVITY;
      JUMP_PROTECT = -GRAVITY - GRAVITY;
      SPIN_START_SPEED_1 = 1440;
      SPIN_START_SPEED_2 = 2400;
      SPIN_INWATER_START_SPEED_1 = 2160;
      SPIN_INWATER_START_SPEED_2 = 3600;
      HURT_POWER_X = 384;
      HURT_POWER_Y = -992;
      JUMP_RUSH_SPEED_PLUS = 480;
      JUMP_REVERSE_POWER = 32;
      SPEED_FLOAT_DEVICE = 40;
      SPEED_LIMIT_LEVEL_1 = 500;
      SPEED_LIMIT_LEVEL_2 = 1120;
      BANK_BRAKE_SPEED_LIMIT = 1100;
      RUN_BRAKE_SPEED_LIMIT = 480;
      lifeDrawerX = 0;
      passStageActionID = 0;
      isNeedPlayWaterSE = false;
      MOON_STAR_ORI_X_1 = SCREEN_WIDTH;
      MOON_STAR_DES_X_1 = SCREEN_WIDTH - 22;
      DEGREE_DIVIDE = new int[]{44, 75, 105, 136, 224, 255, 285, 316, 360};
      var5 = new int[]{0, 5, 3, 6};
      TRANS = var5;
      FOCUS_MAX_OFFSET = (MapManager.CAMERA_HEIGHT >> 1) - 16;
      FOOT_OFFSET_X = new int[]{-256, 256};
      collisionBlockGround = CollisionMap.getInstance().getNewCollisionBlock();
      collisionBlockGroundTmp = CollisionMap.getInstance().getNewCollisionBlock();
      collisionBlockSky = CollisionMap.getInstance().getNewCollisionBlock();
      ATTACK_POP_POWER = GRAVITY + 774;
      RANDOM_RING_NUM = new int[]{1, 5, 5, 5, 5, 10, 20, 30, 40};
      itemVec = new int[5][2];
      lifeNum = 2;
      timeStopped = false;
      uiOffsetX = 0;
      NumberSideX = 81 - MyAPI.zoomIn(0);
      NUM_PIC_WIDTH = 7;
      NUM_PIC_HEIGHT = 13;
      NUM_SPACE_ANIMATION = new int[]{8, 8, 16, 8, 8};
      int var0 = FONT_WIDTH_NUM;
      int var1 = FONT_WIDTH_NUM;
      int var2 = FONT_WIDTH_NUM;
      int var4 = FONT_WIDTH_NUM;
      int var3 = FONT_WIDTH_NUM;
      NUM_SPACE_FONT = new int[]{var0, var1, var2, var4, var3};
      var3 = NUM_PIC_WIDTH;
      var0 = NUM_PIC_WIDTH;
      var1 = NUM_PIC_WIDTH;
      var2 = NUM_PIC_WIDTH;
      var4 = NUM_PIC_WIDTH;
      NUM_SPACE_IMAGE = new int[]{var3 + 1, var0 + 1, var1 + 1, var2 + 1, var4 + 1};
      NUM_SPACE = NUM_SPACE_ANIMATION;
      NUM_ANI_ID = new int[]{27, 37, 67, 125, 37};
      preTimeCount = 0;
      BG_NUM = (SCREEN_WIDTH + 80 - 1) / 80;
      isbarOut = false;
      movespeedx = 96;
      movespeedy = 28;
      clipspeed = 5;
      score1 = 49700;
      score2 = 12700;
      STAGE_PASS_STR_SPACE_FONT = MyAPI.zoomIn(MFGraphics.stringWidth(14, "索尼克完成行动1")) + 20;
      STAGE_PASS_STR_SPACE = 182;
      if (NUM_SPACE[0] * 8 > 60) {
         var0 = NUM_SPACE[0] * 7;
      } else {
         var0 = 60;
      }

      NUM_DISTANCE = var0;
      isStartStageEndFlag = false;
      isOnlyBarOut = false;
      onlyBarOutCnt = 0;
      onlyBarOutCntMax = 80;
      cursorMax = 5;
      var0 = FONT_WIDTH;
      PAUSE_FRAME_WIDTH = var0 * 7;
      PAUSE_FRAME_WIDTH = PAUSE_FRAME_WIDTH;
      PAUSE_FRAME_WIDTH += 4;
      PAUSE_FRAME_HEIGHT = MENU_SPACE * 5 + 20;
      PAUSE_FRAME_OFFSET_X = -PAUSE_FRAME_WIDTH >> 1;
      PAUSE_FRAME_OFFSET_Y = -PAUSE_FRAME_HEIGHT >> 1;
      PAUSE_MENU_NORMAL_NOSHOP = new int[]{12, 81, 5, 6};
      PAUSE_MENU_NORMAL_SHOP = new int[]{12, 81, 52, 5, 6};
      PAUSE_MENU_RACE_ITEM = new int[]{12, 82, 11, 81, 5, 6};
      fadeAlpha = 40;
      fadeRGB = new int[1600];
   }

   public PlayerObject() {
      this.movePower = MOVE_POWER;
      this.movePowerInAir = MOVE_POWER_IN_AIR;
      this.movePowerReverse = MOVE_POWER_REVERSE;
      this.movePowerReserseBall = MOVE_POWER_REVERSE_BALL;
      this.movePowerReverseInSand = MOVE_POWER_REVERSE << 1;
      this.movePowerReserseBallInSand = MOVE_POWER_REVERSE << 1;
      this.maxVelocity = MAX_VELOCITY;
      this.effectID = -1;
      this.collisionLayer = 0;
      this.dashRolling = false;
      this.hurtCount = 0;
      this.hurtNoControl = false;
      this.visible = true;
      this.outOfControl = false;
      this.controlObjectLogic = false;
      this.leavingBar = false;
      this.footObjectLogic = false;
      this.outOfControlObject = null;
      this.attackRectVec = new Vector();
      this.jumpAttackRect = new CollisionRect();
      this.attractRect = new CollisionRect();
      this.aaaAttackRect = new CollisionRect();
      this.fallinSandSlipState = 0;
      this.isAttacking = false;
      this.canAttackByHari = false;
      this.beAttackByHari = false;
      this.setNoMoving = false;
      this.leftStopped = false;
      this.rightStopped = false;
      this.focusMovingState = 0;
      this.lookCount = 32;
      this.footOffsetX = 0;
      this.justLeaveLand = false;
      this.justLeaveCount = 2;
      this.IsStandOnItems = false;
      this.degreeRotateMode = 0;
      this.slipping = false;
      this.doJumpForwardly = false;
      this.preCollisionRect = new CollisionRect();
      this.ignoreFirstTouch = false;
      this.waterFallDrawer = null;
      this.waterFlushDrawer = null;
      this.railFlipping = false;
      this.isPowerShoot = false;
      this.isDead = false;
      this.isSharked = false;
      this.finishDeadStuff = false;
      this.deadPosX = 0;
      this.deadPosY = 0;
      this.noKeyFlag = false;
      this.bankwalking = false;
      this.transing = false;
      this.ducting = false;
      this.ductingCount = 0;
      this.pushOnce = false;
      this.squeezeFlag = true;
      this.orgGravity = false;
      this.footPointX = 512;
      this.footPointY = 0;
      MapManager.setFocusObj(this);
      MapManager.focusQuickLocation();
      this.dustEffectAnimation = new Animation("/animation/effect_dust");
      this.effectDrawer = this.dustEffectAnimation.getDrawer();
      this.animationID = 1;
      this.collisionState = 1;
      this.currentLayer = 1;
      if (bariaDrawer == null) {
         bariaDrawer = (new Animation("/animation/baria")).getDrawer(0, true, 0);
      }

      if (gBariaDrawer == null) {
         gBariaDrawer = (new Animation("/animation/g_baria")).getDrawer(0, true, 0);
      }

      if (invincibleAnimation == null) {
         invincibleAnimation = new Animation("/animation/muteki");
      }

      if (invincibleDrawer == null) {
         invincibleDrawer = invincibleAnimation.getDrawer(0, true, 0);
      }

      if (breatheCountImage == null) {
         breatheCountImage = MFImage.createImage("/animation/player/breathe_count.png");
      }

      if (waterSprayDrawer == null && StageManager.getCurrentZoneId() == 4) {
         waterSprayDrawer = (new Animation("/animation/stage6_water_spray")).getDrawer();
      }

      if (moonStarDrawer == null && StageManager.isGoingToExtraStage()) {
         moonStarDrawer = (new Animation("/animation/moon_star")).getDrawer();
      }

      this.width = 1024;
      this.height = 1536;
      this.worldCal = new ACWorldCollisionCalculator(this, this);
      this.initUIResource();
   }
   
   public PlayerObject getPlayerObj() {
      return myPlayer;
   }

   public static boolean IsInvincibility() {
      boolean var0;
      if (invincibleCount > 0) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean IsSpeedUp() {
      boolean var0;
      if (speedCount > 0) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean IsUnderSheild() {
      boolean var0;
      if (shieldType == 2) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static void addLife() {
      ++lifeNum;
   }

   private void aspirating() {
      int var1 = this.breatheCount;
   }

   public static void calculateScore() {
      if (StageManager.getStageID() == 10) {
         System.out.println("timeCount=" + timeCount);
         if (timeCount > 192000) {
            score1 = 1000;
         } else if (timeCount <= 192000 && timeCount > 132000) {
            score1 = 500;
         } else {
            score1 = 0;
         }

         score2 = ringNum * 100;
      } else {
         if (timeCount < 50000) {
            score1 = 50000;
         } else if (timeCount >= 50000 && timeCount < 60000) {
            score1 = 10000;
         } else if (timeCount >= 60000 && timeCount < 90000) {
            score1 = 5000;
         } else if (timeCount >= 90000 && timeCount < 120000) {
            score1 = 4000;
         } else if (timeCount >= 120000 && timeCount < 180000) {
            score1 = 3000;
         } else if (timeCount >= 180000 && timeCount < 240000) {
            score1 = 2000;
         } else if (timeCount >= 240000 && timeCount < 300000) {
            score1 = 1000;
         } else if (timeCount >= 300000 && timeCount < 360000) {
            score1 = 500;
         } else {
            score1 = 0;
         }

         score2 = ringNum * 100;
      }

   }

   public static boolean characterSelectLogic() {
      boolean var0;
      if (Key.press(Key.gSelect | 8388608)) {
         var0 = true;
      } else {
         if (Key.press(Key.gLeft)) {
            --characterID;
            characterID += CHARACTER_LIST.length;
            characterID %= CHARACTER_LIST.length;
         } else if (Key.press(Key.gRight)) {
            ++characterID;
            characterID += CHARACTER_LIST.length;
            characterID %= CHARACTER_LIST.length;
         }

         var0 = false;
      }

      return var0;
   }

   private void checkCliffAnimation() {
      int var3 = ACUtilities.getRelativePointX(this.posX, -256, 0, this.faceDegree);
      int var4 = ACUtilities.getRelativePointY(this.posY, -256, this.worldInstance.getTileHeight(), this.faceDegree);
      int var6 = ACUtilities.getRelativePointX(this.posX, 0, 0, this.faceDegree);
      int var5 = ACUtilities.getRelativePointY(this.posY, 0, this.worldInstance.getTileHeight(), this.faceDegree);
      int var2 = ACUtilities.getRelativePointX(this.posX, 256, 0, this.faceDegree);
      int var1 = ACUtilities.getRelativePointY(this.posY, 256, this.worldInstance.getTileHeight(), this.faceDegree);
      switch(this.collisionState) {
      case 0:
         var5 = this.worldInstance.getWorldY(var6, var5, this.currentLayer, this.worldCal.getDirectionByDegree(this.faceDegree));
         if (var5 == -1000) {
            var3 = this.worldInstance.getWorldY(var3, var4, this.currentLayer, this.worldCal.getDirectionByDegree(this.faceDegree));
            if (var3 != -1000) {
               if (this.faceDirection) {
                  this.animationID = 47;
               } else {
                  this.animationID = 48;
               }
            } else {
               var1 = this.worldInstance.getWorldY(var2, var1, this.currentLayer, this.worldCal.getDirectionByDegree(this.faceDegree));
               if (var1 != -1000) {
                  if (this.faceDirection) {
                     this.animationID = 48;
                  } else {
                     this.animationID = 47;
                  }
               }
            }
         }
      case 1:
      default:
         break;
      case 2:
         if (this.footOnObject != null) {
            if (var6 < this.footOnObject.collisionRect.x0) {
               if (this.faceDirection) {
                  this.animationID = 48;
               } else {
                  this.animationID = 47;
               }
            } else if (var6 > this.footOnObject.collisionRect.x1) {
               if (this.faceDirection) {
                  this.animationID = 47;
               } else {
                  this.animationID = 48;
               }
            }
         }
      }

   }

   public static void clipMoveInit(int var0, int var1, int var2, int var3, int var4) {
      clipx = var0;
      clipy = var1;
      clipstartw = var2;
      clipendw = var3;
      cliph = var4;
   }

   public static boolean clipMoveLogic() {
      boolean var0;
      if (clipstartw < clipendw) {
         clipstartw += clipspeed;
         var0 = false;
      } else {
         clipstartw = clipendw;
         var0 = true;
      }

      return var0;
   }

   public static void clipMoveShadow(MFGraphics var0) {
      MyAPI.setClip(var0, clipx, 0, clipstartw, SCREEN_HEIGHT);
   }

   private void decelerate() {
      int var2 = this.totalVelocity;
      int var1 = this.getRetPower();
      if (this.totalVelocity > 0) {
         this.totalVelocity -= var1;
         if (this.totalVelocity < 0) {
            this.totalVelocity = 0;
         }
      } else if (this.totalVelocity < 0) {
         this.totalVelocity += var1;
         if (this.totalVelocity > 0) {
            this.totalVelocity = 0;
         }
      }

      if (this.totalVelocity * var2 <= 0 && this.animationID == 4) {
         this.animationID = 0;
      }

   }

   public static void doInitInNewStage() {
      currentMarkId = 0;
   }

   public static void doPauseLeaveGame() {
      scoreNum = preScoreNum;
      lifeNum = preLifeNum;
   }

   public static void doWhileQuitGame() {
      bariaDrawer = null;
      gBariaDrawer = null;
      invincibleAnimation = null;
      invincibleDrawer = null;
   }

   public static void drawFadeBase(MFGraphics var0, int var1) {
      fadeAlpha = MyAPI.calNextPosition((double)fadeAlpha, (double)fadeToValue, 1, var1, 3.0D);
      if (fadeAlpha != 0) {
         int var2;
         if (preFadeAlpha != fadeAlpha) {
            var1 = 0;

            while(true) {
               if (var1 >= 40) {
                  preFadeAlpha = fadeAlpha;
                  break;
               }

               for(var2 = 0; var2 < 40; ++var2) {
                  int[] var5 = fadeRGB;
                  int var3 = fadeAlpha;
                  int var4 = fadeRGB[var2 * 40 + var1];
                  var5[var2 * 40 + var1] = var3 << 24 & -16777216 | var4 & 16777215;
               }

               ++var1;
            }
         }

         for(var1 = 0; var1 < MyAPI.zoomOut(SCREEN_WIDTH); var1 += 40) {
            for(var2 = 0; var2 < MyAPI.zoomOut(SCREEN_HEIGHT); var2 += 40) {
               var0.drawRGB(fadeRGB, 0, 40, var1, var2, 40, 40, true);
            }
         }
      }

   }

   public static void drawGameUI(MFGraphics var0) {
      if (!isTerminal || terminalType != 3 || terminalState <= 3) {
         GameState.guiAniDrawer.draw(var0, 5, uiOffsetX + 0, 0, false, 0);
         int var3 = ringNum;
         int var2 = uiOffsetX;
         byte var1;
         if (ringNum == 0 && timeCount / 240 % 2 == 0) {
            var1 = 3;
         } else {
            var1 = 0;
         }

         drawNum(var0, var3, var2 + 12, 15, 0, var1);
         int var4;
         if (stageModeState == 1) {
            var4 = raceScoreNum;
         } else {
            var4 = scoreNum;
         }

         var2 = NumberSideX;
         var3 = uiOffsetX;
         drawNum(var0, var4, var2 + var3, 8, 2, 0);
         var4 = NumberSideX;
         var2 = uiOffsetX;
         timeDraw(var0, var4 + var2, 22);
         if (stageModeState != 1) {
            if (MFMain.SUPERINNORMALSTAGE || StageManager.getCurrentZoneId() == 8) {
               if (GameObject.player.isDead) {
                  headDrawer.setActionId(0);
               } else {
                  headDrawer.setActionId(4);
               }
            }

            headDrawer.draw(var0, SCREEN_WIDTH, 0);
            if (lifeNum >= 9) {
               var4 = 9;
            } else {
               var4 = lifeNum;
            }

            var2 = SCREEN_WIDTH;
            drawNum(var0, var4, var2 - 9, 4, 12, 0);
         }
      }

   }

   private static void drawMovingbar(MFGraphics var0, int var1) {
      State.drawBar(var0, 2, offsetx - 80, offsety);
      int var4 = offsetx;
      int var3 = SCREEN_WIDTH;
      int var2 = offsety;
      State.drawBar(var0, 2, var4 - 80 + var3, var2);
      var3 = (SCREEN_WIDTH + var1 - 1) / var1;

      for(var2 = 0; var2 < var3 + 2; ++var2) {
         var4 = offsetx + var2 * var1;
         GameState.stageInfoAniDrawer.draw(var0, getCharacterID() + 29, var4, offsety - 10 + 2, false, 0);
         GameState.stageInfoAniDrawer.draw(var0, 33, var4, offsety - 10 + 2, false, 0);
         GameState.stageInfoAniDrawer.draw(var0, passStageActionID, var4, offsety - 10 + 2, false, 0);
      }

   }

   public static void drawNum(MFGraphics var0, int var1, int var2, int var3, int var4, int var5) {
      int var7 = 10;
      int var8 = 1;

      int var6;
      for(var6 = 0; var1 / var7 != 0; ++var6) {
         ++var8;
         var7 *= 10;
      }

      var6 = var7 / 10;
      byte var10 = 0;
      switch(var4) {
      case 0:
         var10 = 34;
         break;
      case 1:
         var10 = 33;
         break;
      case 2:
         var10 = 36;
      }

      byte var9 = 0;
      switch(var5) {
      case 0:
         var9 = 0;
         break;
      case 1:
         var9 = 1;
         break;
      case 2:
         var9 = 3;
         break;
      case 3:
         var9 = 2;
         break;
      case 4:
         var9 = 1;
      }

      NumberDrawer.drawNum(var0, var9, var1, var2, var3, var10);
   }

   public static void drawNum(MFGraphics var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      if (numDrawer == null) {
         numDrawer = GlobalResource.statusAnimation.getDrawer(0, false, 0);
      }

      int var7 = 1;

      int var8;
      for(var8 = 1; var8 < var6; ++var8) {
         var7 *= 10;
      }

      byte var9 = 0;
      switch(var4) {
      case 0:
         var2 -= NUM_SPACE[var5] * (var6 - 1) >> 1;
      case 1:
         break;
      case 2:
         var2 -= NUM_SPACE[var5] * (var6 - 1);
         break;
      default:
         var2 = var9;
      }

      for(var4 = 0; var4 < var6; ++var4) {
         var8 = Math.abs(var1 / var7) % 10;
         var7 /= 10;
         if (var5 == 3 && var8 == 0) {
            numDrawer.setActionId(26);
         } else {
            numDrawer.setActionId(NUM_ANI_ID[var5] + var8);
         }

         numDrawer.draw(var0, NUM_SPACE[var5] * var4 + var2, var3);
      }

   }

   public static void drawRecordTime(MFGraphics var0, int var1, int var2, int var3, int var4, int var5) {
      int var6 = var1 / '\uea60';
      int var7 = var1 % '\uea60' / 1000;
      int var8 = var1 % '\uea60' % 1000 / 10;
      var1 = var2;
      switch(var5) {
      case 0:
         var1 = var2 + (NUM_SPACE[var4] * 7 >> 1);
         break;
      case 1:
         var1 = var2 + NUM_SPACE[var4] * 7;
      case 2:
         break;
      default:
         var1 = var2;
      }

      if (var8 < 10) {
         drawNum(var0, 0, var1 - NUM_SPACE[var4], var3, 2, var4);
      }

      drawNum(var0, var8, var1, var3, 2, var4);
      var5 = NUM_SPACE[var4];
      var2 = NUM_SPACE[var4];
      NumberDrawer.drawColon(var0, 3, var1 - var5 * 2 - (var2 >> 1), var3, 34);
      if (var7 < 10) {
         drawNum(var0, 0, var1 - NUM_SPACE[var4] * 4, var3, 2, var4);
      }

      drawNum(var0, var7, var1 - NUM_SPACE[var4] * 3, var3, 2, var4);
      var2 = NUM_SPACE[var4];
      var5 = NUM_SPACE[var4];
      NumberDrawer.drawColon(var0, 3, var1 - var2 * 5 - (var5 >> 1), var3, 34);
      drawNum(var0, var6, var1 - NUM_SPACE[var4] * 6, var3, 2, var4);
   }

   public static void drawRecordTimeLeft(MFGraphics var0, int var1, int var2, int var3) {
      drawRecordTimeTotalYellow(var0, var1, var2, var3, 0, 1);
      MyAPI.setBmfColor(0);
   }

   public static void drawRecordTimeTotalYellow(MFGraphics var0, int var1, int var2, int var3, int var4, int var5) {
      int var6 = var1 / '\uea60';
      int var7 = var1 % '\uea60' / 1000;
      int var8 = var1 % '\uea60' % 1000 / 10;
      var1 = var2;
      switch(var5) {
      case 0:
         var1 = var2 + (NUM_SPACE[var4] * 7 >> 1);
         break;
      case 1:
         var1 = var2 + NUM_SPACE[var4] * 7;
      case 2:
         break;
      default:
         var1 = var2;
      }

      if (var8 < 10) {
         drawNum(var0, 0, var1 - NUM_SPACE[var4], var3, 2, var4);
      }

      drawNum(var0, var8, var1, var3, 2, var4);
      var2 = NUM_SPACE[var4];
      var5 = NUM_SPACE[var4];
      NumberDrawer.drawColon(var0, 0, var1 - var2 * 2 - (var5 >> 1), var3, 34);
      if (var7 < 10) {
         drawNum(var0, 0, var1 - NUM_SPACE[var4] * 4, var3, 2, var4);
      }

      drawNum(var0, var7, var1 - NUM_SPACE[var4] * 3, var3, 2, var4);
      var2 = NUM_SPACE[var4];
      var5 = NUM_SPACE[var4];
      NumberDrawer.drawColon(var0, 0, var1 - var2 * 5 - (var5 >> 1), var3, 34);
      drawNum(var0, var6, var1 - NUM_SPACE[var4] * 6, var3, 2, var4);
   }

   private void drawSheildPrivate(MFGraphics var1) {
      int var2 = this.faceDegree;
      int var4 = -(this.getCollisionRectHeight() + 384) >> 1;
      int var5;
      if (characterID == 2 && this.myAnimationID >= 19 && this.myAnimationID <= 22) {
         var4 = -384;
         var5 = var2;
      } else if (this.animationID == 11 && this.getAnimationOffset() == 1) {
         var5 = 0;
         var4 = -1408;
      } else if (this.animationID != 4 && this.animationID != 5 && this.animationID != 46 && this.animationID != 6 && this.animationID != 7 && this.animationID != 18 && this.animationID != 19 && this.animationID != 20) {
         label86: {
            if (this.animationID != 25) {
               var5 = var2;
               if (this.animationID != 26) {
                  break label86;
               }
            }

            var4 = 0;
            var5 = var2;
         }
      } else {
         var4 = -640;
         var5 = var2;
      }

      short var3;
      short var9;
      if (characterID == 0 && this.myAnimationID == 25) {
         var9 = -128;
         var3 = 0;
      } else if (characterID == 3 && this.myAnimationID == 38) {
         var9 = -128;
         var3 = 128;
      } else if (characterID == 3 && this.myAnimationID == 37) {
         var9 = -128;
         var3 = 256;
      } else if (characterID == 2 && this.myAnimationID >= 29 && this.myAnimationID <= 32) {
         if (getPlayerObj().isAntiGravity) {
            if (this.faceDirection) {
               var9 = 256;
               var3 = -256;
            } else {
               var9 = -256;
               var3 = -256;
            }
         } else if (this.faceDirection) {
            var9 = -256;
            var3 = 256;
         } else {
            var9 = 256;
            var3 = 256;
         }
      } else {
         var9 = 0;
         var3 = 0;
      }

      int var6 = this.getNewPointX(this.footPointX, 0, var4, var5);
      var4 = this.getNewPointY(this.footPointY, 0, var4, var5);
      AnimationDrawer var7;
      if (invincibleCount > 0) {
         if (invincibleDrawer != null) {
            var7 = invincibleDrawer;
            this.drawInMap(var1, var7, var6 + var9, var4 + var3);
         }

         if (systemClock % 2L == 0L) {
            Animation var8 = invincibleAnimation;
            var2 = MyRandom.nextInt(-3, 3);
            int var10 = MyRandom.nextInt(-3, 3);
            Effect.showEffect(var8, 1, (var6 >> 6) + var2, (var4 >> 6) + var10, 0);
         }
      } else if (shieldType > 0) {
         if (shieldType == 1) {
            var7 = bariaDrawer;
            this.drawInMap(var1, var7, var6 + var9, var4 + var3);
         } else if (this.isAttracting()) {
            var7 = gBariaDrawer;
            this.drawInMap(var1, var7, var6 + var9, var4 + var3);
         }
      }

   }

   private static void drawStagePassInfoScroll(MFGraphics var0, int var1, int var2, int var3) {
      State.drawBar(var0, 2, var1);
      itemOffsetX -= var2;
      itemOffsetX %= var3;

      for(var2 = itemOffsetX - 294; var2 < SCREEN_WIDTH * 2; var2 += var3) {
         GameState.stageInfoAniDrawer.draw(var0, getCharacterID() + 29, var2, var1 - 10 + 2, false, 0);
         GameState.stageInfoAniDrawer.draw(var0, 33, var2, var1 - 10 + 2, false, 0);
         GameState.stageInfoAniDrawer.draw(var0, passStageActionID, var2, var1 - 10 + 2, false, 0);
      }

   }

   private static void drawStagePassInfoScroll(MFGraphics var0, int var1, int var2, int var3, int var4) {
      if (!isbarOut) {
         State.drawBar(var0, 2, var2);
      } else {
         State.drawBar(var0, 2, var1, var2);
         State.drawBar(var0, 2, SCREEN_WIDTH + var1, var2);
         State.drawBar(var0, 2, SCREEN_WIDTH * 2 + var1, var2);
      }

      if (var1 == 0) {
         itemOffsetX -= var3;
         itemOffsetX %= var4;
      }

      for(var3 = itemOffsetX - 294; var3 < SCREEN_WIDTH * 2; var3 += var4) {
         GameState.stageInfoAniDrawer.draw(var0, getCharacterID() + 29, var3 + var1, var2 - 10 + 2, false, 0);
         GameState.stageInfoAniDrawer.draw(var0, 33, var3 + var1, var2 - 10 + 2, false, 0);
         GameState.stageInfoAniDrawer.draw(var0, passStageActionID, var3 + var1, var2 - 10 + 2, false, 0);
      }

   }

   private static void drawStaticAni(MFGraphics var0, int var1, int var2, int var3) {
      numDrawer.setActionId(var1);
      numDrawer.draw(var0, var2, var3);
   }

   private int faceDegreeChk() {
      return this.faceDegree;
   }

   private boolean faceDirectionChk() {
      boolean var1;
      if (this.totalVelocity > 0) {
         var1 = true;
      } else if (this.totalVelocity < 0) {
         var1 = false;
      } else if (!Key.press(Key.gLeft) && !Key.repeat(Key.gLeft)) {
         if (!Key.press(Key.gRight) && !Key.repeat(Key.gRight)) {
            var1 = true;
         } else {
            var1 = true;
         }
      } else {
         var1 = false;
      }

      return var1;
   }

   private void faceSlopeChk() {
      int var2 = this.getGravity();
      byte var1;
      if (this.isAntiGravity) {
         var1 = -1;
      } else {
         var1 = 1;
      }

      int var3 = MyAPI.dSin(this.faceDegree) * var2 * var1 / 100;
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

   public static void fadeInit(int var0, int var1) {
      fadeFromValue = var0;
      fadeToValue = var1;
      fadeAlpha = fadeFromValue;
      preFadeAlpha = -1;
   }

   public static void gamepauseDraw(MFGraphics var0) {
      int[] var10 = PAUSE_MENU_NORMAL_NOSHOP;
      PAUSE_MENU_NORMAL_ITEM = var10;
      int var2 = SCREEN_WIDTH;
      int var6 = PAUSE_FRAME_OFFSET_X;
      int var5 = SCREEN_HEIGHT;
      int var4 = PAUSE_FRAME_OFFSET_Y;
      int var1 = PAUSE_FRAME_WIDTH;
      int var3 = PAUSE_FRAME_HEIGHT;
      State.fillMenuRect(var0, (var2 >> 1) + var6, (var5 >> 1) + var4, var1, var3);
      State.drawMenuFontById(var0, 80, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + PAUSE_FRAME_OFFSET_Y + (MENU_SPACE >> 1) + 10);
      if (stageModeState == 0) {
         currentPauseMenuItem = PAUSE_MENU_NORMAL_ITEM;
      } else {
         currentPauseMenuItem = PAUSE_MENU_RACE_ITEM;
      }

      if (currentPauseMenuItem.length > 4) {
         if (cursorIndex > cursor) {
            cursorIndex = cursor;
         } else if (cursorIndex + 4 - 1 < cursor) {
            cursorIndex = cursor - 4 + 1;
         }
      } else {
         cursorIndex = 0;
      }

      int var8 = SCREEN_WIDTH;
      int var7 = SCREEN_HEIGHT;
      var1 = PAUSE_FRAME_OFFSET_Y;
      var2 = MENU_SPACE;
      var4 = MENU_SPACE;
      var3 = MENU_SPACE;
      var5 = cursor;
      var6 = cursorIndex;
      State.drawMenuFontById(var0, 119, var8 >> 1, (var7 >> 1) + var1 + 10 + (var2 >> 1) + var4 + var3 * (var5 - var6));
      var3 = SCREEN_WIDTH;
      var2 = SCREEN_HEIGHT;
      var7 = PAUSE_FRAME_OFFSET_Y;
      var5 = MENU_SPACE;
      var6 = MENU_SPACE;
      var8 = MENU_SPACE;
      var1 = cursor;
      var4 = cursorIndex;
      State.drawMenuFontById(var0, 113, (var3 >> 1) - 56 - 0, (var2 >> 1) + var7 + 10 + (var5 >> 1) + var6 + var8 * (var1 - var4));

      for(var1 = cursorIndex; var1 < cursorIndex + 4; ++var1) {
         var7 = currentPauseMenuItem[var1];
         var8 = SCREEN_WIDTH;
         int var9 = SCREEN_HEIGHT;
         var5 = PAUSE_FRAME_OFFSET_Y;
         var4 = MENU_SPACE;
         var6 = MENU_SPACE;
         var2 = MENU_SPACE;
         var3 = cursorIndex;
         State.drawMenuFontById(var0, var7, var8 >> 1, (var9 >> 1) + var5 + 10 + (var4 >> 1) + var6 + var2 * (var1 - var3));
      }

      if (currentPauseMenuItem.length > 4) {
         if (cursorIndex == 0) {
            State.drawMenuFontById(var0, 96, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - PAUSE_FRAME_OFFSET_Y + (MENU_SPACE >> 1));
            GameState.IsSingleUp = false;
            GameState.IsSingleDown = true;
         } else if (cursorIndex == currentPauseMenuItem.length - 4) {
            State.drawMenuFontById(var0, 95, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) - PAUSE_FRAME_OFFSET_Y + (MENU_SPACE >> 1));
            GameState.IsSingleUp = true;
            GameState.IsSingleDown = false;
         } else {
            State.drawMenuFontById(var0, 95, (SCREEN_WIDTH >> 1) - 23, (SCREEN_HEIGHT >> 1) - PAUSE_FRAME_OFFSET_Y + (MENU_SPACE >> 1));
            State.drawMenuFontById(var0, 96, (SCREEN_WIDTH >> 1) + 22, (SCREEN_HEIGHT >> 1) - PAUSE_FRAME_OFFSET_Y + (MENU_SPACE >> 1));
            GameState.IsSingleUp = false;
            GameState.IsSingleDown = false;
         }
      }

      State.drawSoftKey(var0, true, true);
   }

   public static void gamepauseInit() {
      cursor = 0;
      cursorIndex = 0;
      Key.touchkeygameboardClose();
   }
   
   public int getCharacterIDMulti() {
      return characterIDMulti;
   }

   public static int getCharacterID() {
      return characterID;
   }
   
   public static int getCharacterID2() {
      return characterID2;
   }

   public static int getLife() {
      return lifeNum;
   }
   
   public static PlayerObject getPlayer(int i) {
      Object var0;
      switch(i) {
      case 0:
         if (StageManager.getCurrentZoneId() == 8) {
            var0 = new PlayerSuperSonic();
         } else {
            var0 = new PlayerSonic();
         }
         break;
      case 1:
         var0 = new PlayerTails();
         break;
      case 2:
         var0 = new PlayerKnuckles();
         break;
      case 3:
         var0 = new PlayerAmy();
         break;
      case 4:
         var0 = new PlayerSuperSonic();
         break;
      default:
         var0 = new PlayerSonic();
      }

      terminalState = 0;
      terminalType = 0;
      characterID2 = i;
      return (PlayerObject)var0;
   }

   public static PlayerObject getPlayer() {
      Object var0;
      switch(characterID) {
      case 0:
         if (StageManager.getCurrentZoneId() == 8) {
            var0 = new PlayerSuperSonic();
         } else {
            var0 = new PlayerSonic();
         }
         break;
      case 1:
         var0 = new PlayerTails();
         break;
      case 2:
         var0 = new PlayerKnuckles();
         break;
      case 3:
         var0 = new PlayerAmy();
         break;
      case 4:
         var0 = new PlayerSuperSonic();
         break;
      default:
         var0 = new PlayerSonic();
      }

      terminalState = 0;
      terminalType = 0;
      return (PlayerObject)var0;
   }

   public void setPlayer(PlayerObject var0) {
   myPlayer = var0;
   if (myPlayerStatic != null) {
   for (int i = 0; i < myPlayerStatic.length; i++) {
   if (myPlayerStatic[i] == null) {
   myPlayerStatic[i] = var0;
   break;
   }
   }
   }
   }
   
   public void runAI() {
    if (GameObject.player == null) return;

    int diffX = GameObject.player.posX - this.posX;
    boolean enemyAhead = false;

    if (this.isOnGound()) {
for (int xx = 0; xx < GameObject.objVecWidth; xx++) {
    for (int yy = 0; yy < GameObject.objVecHeight; yy++) {
        for (int i = 0; i < GameObject.allGameObject[xx][yy].size(); i++) {
            GameObject obj = (GameObject) GameObject.allGameObject[xx][yy].elementAt(i);
            if (obj instanceof EnemyObject) {
                EnemyObject enemy = (EnemyObject) obj;
                int enemyDiff = enemy.posX - this.posX;

                if (Math.abs(enemyDiff) < (32 << 6)) {
                    if ((enemyDiff > 0 && diffX > 0) || (enemyDiff < 0 && diffX < 0)) {
                        enemyAhead = true;
                        break;
                    }
                }
            }
        }
    }
}

        if (enemyAhead) {
            doJump();
        }
    }
}

   public static void getRing(int var0) {
      int var1 = ringNum;
      ringNum += var0;
      if (stageModeState != 1 && StageManager.getCurrentZoneId() != 8) {
         if (var1 / 100 != ringNum / 100) {
            addLife();
            playerLifeUpBGM();
         }

         if (ringTmpNum != 0) {
            ringTmpNum = 0;
         }
      }

   }

   public static int getRingNum() {
      return ringNum;
   }

   public static int getScore() {
      return scoreNum;
   }

   public static int getTimeCount() {
      return timeCount;
   }

   public static void getTmpRing(int var0) {
      switch(var0) {
      case 5:
         ringTmpNum = RANDOM_RING_NUM[MyRandom.nextInt(RANDOM_RING_NUM.length)];
         ringRandomNum = ringTmpNum;
         break;
      case 6:
         ringTmpNum = 5;
         break;
      case 7:
         ringTmpNum = 10;
      }

   }

   public static void initMovingBar() {
      offsetx = SCREEN_WIDTH;
      offsety = (SCREEN_HEIGHT >> 1) + 48;
      if (StageManager.getStageID() < 12) {
         if (StageManager.getStageID() % 2 == 0) {
            passStageActionID = 34;
         } else if (StageManager.getStageID() % 2 == 1) {
            passStageActionID = 35;
         }
      } else {
         passStageActionID = StageManager.getStageID() - 12 + 36;
      }

   }

   public static void initSpParam(int var0, int var1, int var2) {
      if (player != null) {
         PlayerObject var3 = player;
         currentMarkId = var1;
      }

      ringNum = var0;
      timeCount = var2;
      lastTimeCount = timeCount;
   }

   public static void initStageParam() {
      ringNum = 0;
      invincibleCount = 0;
      speedCount = 0;
      SoundSystem.getInstance().setSoundSpeed(1.0F);
      shieldType = 0;
      timeCount = 0;
      lastTimeCount = timeCount;
      timeStopped = false;
      raceScoreNum = 0;
      preScoreNum = scoreNum;
      preLifeNum = lifeNum;

      for(int var0 = 0; var0 < 5; ++var0) {
         itemVec[var0][0] = -1;
      }

      setOverCount(599999);
   }

   private void initUIResource() {
   }

   private void inputLogicJump() {
      int var1;
      int var2;
      int var3;
      int var4;
      if (this.faceDegree != this.degreeStable) {
         var2 = this.posX;
         var3 = -this.collisionRect.getHeight();
         var1 = this.faceDegree;
         var2 = this.getNewPointX(var2, 0, var3 >> 1, var1);
         var4 = this.posY;
         var1 = -this.collisionRect.getHeight();
         var3 = this.faceDegree;
         var1 = this.getNewPointY(var4, 0, var1 >> 1, var3);
         this.faceDegree = this.degreeStable;
         var4 = this.collisionRect.getHeight();
         var3 = this.faceDegree;
         var2 = this.getNewPointX(var2, 0, var4 >> 1, var3);
         this.footPointX = var2;
         this.posX = var2;
         var3 = this.collisionRect.getHeight();
         var2 = this.faceDegree;
         var1 = this.getNewPointY(var1, 0, var3 >> 1, var2);
         this.footPointY = var1;
         this.posY = var1;
      }

      if (this.degreeForDraw != this.faceDegree) {
         var3 = this.faceDegree;
         var4 = this.degreeForDraw;
         var2 = this.faceDegree;
         switch(this.degreeRotateMode) {
         case 0:
            var1 = var2;
            if (Math.abs(var3 - var4) > 180) {
               if (var2 > this.degreeForDraw) {
                  var1 = var2 - 360;
               } else {
                  var1 = var2 + 360;
               }
            }

            this.degreeForDraw = MyAPI.calNextPosition((double)this.degreeForDraw, (double)var1, 1, 3);
            break;
         case 1:
            this.degreeForDraw += 24;
            break;
         case 2:
            this.degreeForDraw -= 24;
         }

         while(this.degreeForDraw < 0) {
            this.degreeForDraw += 360;
         }

         this.degreeForDraw %= 360;
      }

      if (this.animationID == 8) {
         this.doWalkPoseInAir();
      }

      if (!this.hurtNoControl && this.animationID != 30 && (characterID != 3 || this.myAnimationID < 5 || this.myAnimationID > 7)) {
         boolean var5;
         if ((Key.repeat(Key.gLeft) || this.isCelebrate && !this.faceDirection) && !this.ducting) {
            if (this.velX > -this.maxVelocity) {
               this.velX -= this.movePowerInAir;
               if (this.velX < -this.maxVelocity) {
                  this.velX = -this.maxVelocity;
               }
            }

            if (this.degreeRotateMode == 0) {
               if (this.isAntiGravity) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               this.faceDirection = var5;
            }
         } else if ((Key.repeat(Key.gRight) || isTerminal || this.isCelebrate && this.faceDirection) && !this.ducting) {
            if (this.velX < this.maxVelocity) {
               this.velX += this.movePowerInAir;
               if (this.velX > this.maxVelocity) {
                  this.velX = this.maxVelocity;
               }
            }

            if (this.degreeRotateMode == 0) {
               if (this.isAntiGravity) {
                  var5 = false;
               } else {
                  var5 = true;
               }

               this.faceDirection = var5;
            }
         }
      }

      if (!this.isOnlyJump) {
         this.extraLogicJump();
      }

      if (this.velY >= -768 - this.getGravity()) {
         var2 = this.velX << 5;
         var3 = var2 * 3 / JUMP_REVERSE_POWER;
         if (var2 > 0) {
            var2 -= var3;
            var1 = var2;
            if (var2 < 0) {
               var1 = 0;
            }
         } else {
            var1 = var2;
            if (var2 < 0) {
               var2 -= var3;
               var1 = var2;
               if (var2 > 0) {
                  var1 = 0;
               }
            }
         }

         this.velX = var1 >> 5;
      }

      byte var6;
      if (this.smallJumpCount > 0) {
         --this.smallJumpCount;
         if (!this.noVelMinus && !Key.repeat(16777216)) {
            var2 = this.velY;
            if (this.isAntiGravity) {
               var6 = -1;
            } else {
               var6 = 1;
            }

            this.velY = var2 + var6 * (this.getGravity() >> 1);
            var2 = this.velY;
            if (this.isAntiGravity) {
               var6 = -1;
            } else {
               var6 = 1;
            }

            this.velY = var2 + var6 * (this.getGravity() >> 2);
         }
      }

      var2 = this.velY;
      if (this.isAntiGravity) {
         var6 = -1;
      } else {
         var6 = 1;
      }

      this.velY = var2 + var6 * this.getGravity();
      if (this.animationID == 14 && (this.velY > -200 && !this.isAntiGravity || this.velY < 200 && this.isAntiGravity)) {
         this.animationID = 42;
      }

   }

   private void inputLogicOnObject() {
      this.leavingBar = false;
      this.doJumpForwardly = false;
      this.degreeRotateMode = 0;
      int var1 = this.movePower;
      int var2 = this.maxVelocity;
      if (this.animationID != 5) {
         boolean var3;
         if ((Key.repeat(Key.gLeft) && (this.animationID == 0 || this.animationID == 47 || this.animationID == 48 || this.animationID == 1 || this.animationID == 2 || this.animationID == 3) || this.isCelebrate && !this.faceDirection) && !this.isOnSlip0()) {
            if (this.animationID == 5) {
               this.animationID = 0;
            }

            if (this.isAntiGravity) {
               var3 = true;
            } else {
               var3 = false;
            }

            this.faceDirection = var3;
            if (this.velX <= 0) {
               if (this.animationID != 4) {
                  this.velX -= var1;
                  if (this.velX < -var2) {
                     this.velX += var1;
                     if (this.velX > -var2) {
                        this.velX = -var2;
                     }
                  }
               }
            } else {
               if (this.animationID == 4) {
                  var1 = this.movePowerReserseBall;
               } else {
                  var1 = this.movePowerReverse;
               }

               this.velX -= var1;
               if (this.velX < 0) {
                  this.velX = 0 - var1 >> 2;
               } else {
                  this.faceDirection = true;
               }
            }
         } else if (Key.repeat(Key.gRight) && (this.animationID == 0 || this.animationID == 47 || this.animationID == 48 || this.animationID == 1 || this.animationID == 2 || this.animationID == 3) || this.isCelebrate && this.faceDirection) {
            if (this.animationID == 5) {
               this.animationID = 0;
            }

            if (this.isAntiGravity) {
               var3 = false;
            } else {
               var3 = true;
            }

            this.faceDirection = var3;
            if (this.velX >= 0) {
               if (this.animationID != 4) {
                  this.velX += var1;
                  if (this.velX > var2) {
                     this.velX -= var1;
                     if (this.velX < var2) {
                        this.velX = var2;
                     }
                  }
               }
            } else {
               if (this.animationID == 4) {
                  var1 = this.movePowerReserseBall;
               } else {
                  var1 = this.movePowerReverse;
               }

               this.velX += var1;
               if (this.velX > -1) {
                  this.velX = var1 >> 2;
               } else {
                  this.faceDirection = false;
               }
            }
         }
      }

      if (this.animationID != -1) {
         if (Math.abs(this.velX) <= 0) {
            if (this.animationID != 38 && this.animationID != 39 && this.animationID != 40 && this.animationID != 5) {
               this.animationID = 0;
               this.checkCliffAnimation();
            }
         } else if (this.animationID != 4) {
            if (Math.abs(this.velX) < SPEED_LIMIT_LEVEL_1) {
               this.animationID = 1;
            } else if (Math.abs(this.velX) < SPEED_LIMIT_LEVEL_2) {
               this.animationID = 2;
            } else {
               this.animationID = 3;
            }
         }
      }

      this.extraLogicOnObject();
      this.attackAnimationID = this.animationID;
      if (!this.spinLogic()) {
         if (this.canDoJump() && !this.dashRolling && Key.press(16777216)) {
            if (characterID != 3 || PlayerAmy.isCanJump) {
               this.doJump();
            }
         } else if (Key.repeat(4 | Key.gUp | 33554432)) {
            if (this.animationID == 38 && this.drawer.checkEnd()) {
               this.animationID = 39;
            }

            if (this.animationID != 38 && this.animationID != 39 && this.animationID == 0) {
               this.animationID = 38;
            }

            if (this.animationID == 39) {
               this.focusMovingState = 1;
            }
         } else {
            if (this.animationID == 40 && this.drawer.checkEnd()) {
               this.animationID = 0;
            }

            if (this.animationID == 38 || this.animationID == 39) {
               this.animationID = 40;
            }
         }
      }

      if (this.needRetPower() && this.collisionState == 2) {
         var1 = this.getRetPower();
         if (this.velX > 0) {
            this.velX -= var1;
            if (this.velX < 0) {
               this.velX = 0;
            }
         } else if (this.velX < 0) {
            this.velX += var1;
            if (this.velX > 0) {
               this.velX = 0;
            }
         }
      }

      var2 = this.velY;
      byte var4;
      if (this.isAntiGravity) {
         var4 = -1;
      } else {
         var4 = 1;
      }

      this.velY = var2 + var4 * this.getGravity();
      this.waitingChk();
   }

   private void inputLogicSand() {
      this.leavingBar = false;
      this.doJumpForwardly = false;
      this.degreeRotateMode = 0;
      if (this.velY > 0 && !this.sandStanding) {
         this.sandStanding = true;
      }
      ++this.sandFrame;
      if (this.velX == 0) {
         this.sandFrame = 0;
      } else if (this.sandFrame == 1) {
         soundInstance.playSe(70);
      } else if (this.sandFrame > 2) {
         soundInstance.playSequenceSe(71);
      }
      if (this.sandStanding) {
         int var2 = this.movePower >> 1;
         int var3 = this.maxVelocity >> 1;
         int var1;
         if (Key.repeat(Key.gLeft)) {
            this.faceDirection = false;
            if (this.velX <= 0) {
               if (this.animationID != 4) {
                  this.velX -= var2;
                  if (this.velX < -var3) {
                     this.velX += var2;
                     if (this.velX > -var3) {
                        this.velX = -var3;
                     }
                  }
               }
            } else {
               if (this.animationID == 4) {
                  var1 = this.movePowerReserseBallInSand;
               } else {
                  var1 = this.movePowerReverseInSand;
               }

               this.velX -= var1;
               if (this.velX < 0) {
                  this.velX = 0 - var1 >> 2;
               } else {
                  this.faceDirection = true;
               }
            }
         } else if (Key.repeat(Key.gRight)) {
            this.faceDirection = true;
            if (this.velX >= 0) {
               if (this.animationID != 4) {
                  this.velX += var2;
                  if (this.velX > var3) {
                     this.velX -= var2;
                     if (this.velX < var3) {
                        this.velX = var3;
                     }
                  }
               }
            } else {
               if (this.animationID == 4) {
                  var1 = this.movePowerReserseBallInSand;
               } else {
                  var1 = this.movePowerReverseInSand;
               }

               this.velX += var1;
               if (this.velX > -1) {
                  this.velX = var1 >> 2;
               } else {
                  this.faceDirection = false;
               }
            }
         } else {
            this.velX = 0;
         }

         if (Math.abs(this.velX) <= 64) {
            if ((!(this instanceof PlayerAmy) || this.getCharacterAnimationID() != 13 || this.getVelY() >= 0) && this.animationID != 38 && this.animationID != 39 && this.animationID != 40) {
               this.animationID = 0;
            }
         } else if (characterID == 1 && ((PlayerTails)player).flyCount > 0) {
            if (this.myAnimationID != 12 && this.myAnimationID != 48 && this.myAnimationID != 49) {
               ((PlayerTails)player).flyCount = 0;
            }
         } else if ((!(this instanceof PlayerAmy) || this.getCharacterAnimationID() != 4 && (this.getCharacterAnimationID() != 5 || this.drawer.getCurrentFrame() >= 2)) && (!(this instanceof PlayerAmy) || this.getCharacterAnimationID() != 13 || this.getVelY() >= 0)) {
            if (Math.abs(this.velX) < SPEED_LIMIT_LEVEL_1) {
               this.animationID = 1;
            } else if (Math.abs(this.velX) < SPEED_LIMIT_LEVEL_2) {
               this.animationID = 2;
            } else {
               this.animationID = 3;
            }
         }

         if (!(this instanceof PlayerAmy) || this.getCharacterAnimationID() != 13 || this.getVelY() >= 0) {
            this.velY = 100;
         }

         if (characterID == 0) {
            var1 = SPEED_LIMIT_LEVEL_1 >> 2;
            if (Key.press(Key.gSelect | 8388608)) {
               soundInstance.playSe(4);
               if (this.faceDirection) {
                  if (this.velX >= 0) {
                     if (this.animationID != 4) {
                        this.velX += var1;
                        if (this.velX > var3) {
                           this.velX -= var1;
                           if (this.velX < var3) {
                              this.velX = var3;
                           }
                        }
                     }
                  } else {
                     if (this.animationID == 4) {
                        var1 = this.movePowerReserseBallInSand;
                     } else {
                        var1 = this.movePowerReverseInSand;
                     }

                     this.velX += var1;
                     if (this.velX > -1) {
                        this.velX = var1 >> 2;
                     } else {
                        this.faceDirection = false;
                     }
                  }
               } else if (this.velX <= 0) {
                  if (this.animationID != 4) {
                     this.velX -= var1;
                     if (this.velX < -var3) {
                        this.velX += var1;
                        if (this.velX > -var3) {
                           this.velX = -var3;
                        }
                     }
                  }
               } else {
                  if (this.animationID == 4) {
                     var1 = this.movePowerReserseBallInSand;
                  } else {
                     var1 = this.movePowerReverseInSand;
                  }

                  this.velX -= var1;
                  if (this.velX < 0) {
                     this.velX = 0 - var1 >> 2;
                  } else {
                     this.faceDirection = true;
                  }
               }
            }
         }

         if (!this.spinLogic()) {
            if (!Key.repeat(Key.gLeft) && !Key.repeat(Key.gRight) && !this.isTerminalRunRight() && this.animationID != -1) {
               if (Key.repeat(Key.gDown)) {
                  if (Math.abs(this.velX) <= 64) {
                     if (this.animationID != 5) {
                        this.animationID = 46;
                     }
                  } else {
                     this.velX = 0;
                  }
               } else if (this.animationID == 5) {
                  this.animationID = 46;
               }
            }

            if (this.animationID != 5 && Key.press(16777216)) {
               if (this instanceof PlayerTails && ((PlayerTails)player).flyCount > 0) {
                  ((PlayerTails)player).flyCount = 0;
               }

               this.doJump();
               this.velY -= this.getGravity();
               this.sandStanding = false;
            }
         }

         if (!Key.repeat(Key.gLeft | Key.gRight) && this.sandStanding) {
            if (this.animationID != 4) {
               var1 = var2;
            } else {
               var1 = var2 >> 1;
            }

            if (this.velX > 0) {
               this.velX -= var1;
               if (this.velX < 0) {
                  this.velX = 0;
               }
            } else if (this.velX < 0) {
               this.velX += var1;
               if (this.velX > 0) {
                  this.velX = 0;
               }
            }
         }
      } else {
         this.inputLogicJump();
      }

      this.collisionState = 1;
   }

   private void inputLogicWalk() {
      this.leavingBar = false;
      this.doJumpForwardly = false;
      this.degreeRotateMode = 0;
      byte var1;
      int var2;
      int var3;
      boolean var5;
      int var6;
      if (this.slipFlag || this.totalVelocity != 0) {
         var2 = this.getSlopeGravity();
         if (this.isAntiGravity) {
            var1 = -1;
         } else {
            var1 = 1;
         }

         var2 *= var1;
         var6 = var2;
         if (this.slipFlag) {
            var6 = var2 * 3;
         }

         var2 = MyAPI.dSin(this.faceDegree) * var6 / 100;
         var3 = this.totalVelocity;
         var6 = var2;
         if (this.slipFlag) {
            var6 = var2;
            if (Math.abs(var2) < 100) {
               if (var2 < 0) {
                  var6 = -100;
               } else {
                  var6 = 100;
               }
            }
         }

         var2 = var6;
         if (this.animationID == 4) {
            if (this.totalVelocity >= 0) {
               var2 = var6;
               if (var6 < 0) {
                  var2 = var6 >> 2;
               }
            } else {
               var2 = var6;
               if (var6 > 0) {
                  var2 = var6 >> 2;
               }
            }
         }

         this.totalVelocity += var2;
         if (this.totalVelocity * var3 <= 0 && this.animationID == 4) {
            this.animationID = 0;
            if (var3 > 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            this.faceDirection = var5;
         }
      }

      if (this.attackLevel == 0 && !Key.repeat(Key.gDown) && this.animationID != -1 && this.animationID != 30) {
         if ((this.isAntiGravity || !Key.repeat(Key.gLeft)) && (!this.isAntiGravity || !Key.repeat(Key.gRight)) && !this.doBrake()) {
            if (!this.isAntiGravity && Key.repeat(Key.gRight) || this.isAntiGravity && Key.repeat(Key.gLeft) || this.isTerminalRunRight()) {
               if (this.animationID == 5) {
                  this.animationID = 0;
               }

               if (this.animationID != 4 || this.collisionState != 0) {
                  this.faceDirection = true;
               }

               if (this.fallTime == 0) {
                  if (this.totalVelocity >= 0 && !this.doBrake()) {
                     if (this.animationID != 4) {
                        this.totalVelocity += this.movePower;
                        if (this.totalVelocity > this.maxVelocity) {
                           this.totalVelocity -= this.movePower;
                           if (this.totalVelocity < this.maxVelocity) {
                              this.totalVelocity = this.maxVelocity;
                           }
                        }
                     }
                  } else {
                     if (this.animationID == 4) {
                        var6 = this.movePowerReserseBall;
                     } else {
                        var6 = this.movePowerReverse;
                     }

                     this.totalVelocity += var6;
                     if (this.totalVelocity > -1) {
                        if (this.onBank) {
                           this.totalVelocity = 0;
                           this.onBank = false;
                           this.bankwalking = false;
                        } else {
                           this.totalVelocity = var6 >> 2;
                        }
                     }

                     if (Math.abs(this.totalVelocity) > BANK_BRAKE_SPEED_LIMIT && this.animationID != 4 && this.animationID != 17) {
                        soundInstance.playSe(10);
                        if (this.onBank) {
                           this.onBank = false;
                           this.bankwalking = false;
                        }
                     }
                  }
               }
            }
         } else {
            if (this.animationID == 5) {
               this.animationID = 0;
            }

            if ((this.animationID != 4 || this.collisionState != 0) && !this.doBrake()) {
               this.faceDirection = false;
            }

            if (this.fallTime == 0) {
               if (this.totalVelocity <= 0 && !this.doBrake()) {
                  if (this.animationID != 4) {
                     this.totalVelocity -= this.movePower;
                     if (this.totalVelocity < -this.maxVelocity) {
                        this.totalVelocity += this.movePower;
                        if (this.totalVelocity > -this.maxVelocity) {
                           this.totalVelocity = -this.maxVelocity;
                        }
                     }
                  }
               } else {
                  if (this.animationID == 4) {
                     var6 = this.movePowerReserseBall;
                  } else {
                     var6 = this.movePowerReverse;
                  }

                  this.totalVelocity -= var6;
                  if (this.totalVelocity < 0) {
                     if (this.onBank) {
                        this.totalVelocity = 0;
                        this.onBank = false;
                        this.bankwalking = false;
                     } else {
                        this.totalVelocity = 0 - var6 >> 2;
                     }
                  }

                  if (Math.abs(this.totalVelocity) > BANK_BRAKE_SPEED_LIMIT && this.animationID != 4 && this.animationID != 17) {
                     soundInstance.playSe(10);
                     if (this.onBank) {
                        this.onBank = false;
                        this.bankwalking = false;
                     }
                  }
               }
            }
         }
      }

      if (this.animationID != -1) {
         if (Math.abs(this.totalVelocity) <= 0) {
            if (this.animationID != 38 && this.animationID != 39 && this.animationID != 40 && this.animationID != 5 && this.collisionState != 1) {
               this.animationID = 0;
               this.bankwalking = false;
               this.checkCliffAnimation();
            }
         } else if (this.animationID != 4 && this.animationID != 35 && this.animationID != 36 && this.animationID != 5 && this.animationID != 31) {
            if (Math.abs(this.totalVelocity) < SPEED_LIMIT_LEVEL_1) {
               this.animationID = 1;
            } else if (Math.abs(this.totalVelocity) < SPEED_LIMIT_LEVEL_2) {
               this.animationID = 2;
            } else if (!this.slipping) {
               this.animationID = 3;
            }
         }
      }

      this.waitingChk();
      var2 = this.getGravity();
      if (this.isAntiGravity) {
         var1 = -1;
      } else {
         var1 = 1;
      }

      var6 = MyAPI.dSin(this.faceDegree) * var2 * var1 / 100;
      this.faceSlopeChk();
      if (this.animationID != -1 && this.attackLevel == 0 && this.animationID != 4 && Math.abs(this.totalVelocity) > Math.abs(var6) && this.fallTime == 0) {
         if ((!Key.repeat(Key.gLeft) || !Key.repeat(Key.gRight)) && ((!this.isAntiGravity && Key.repeat(Key.gLeft) || this.isAntiGravity && Key.repeat(Key.gRight)) && this.totalVelocity > RUN_BRAKE_SPEED_LIMIT || (!this.isAntiGravity && Key.repeat(Key.gRight) || this.isAntiGravity && Key.repeat(Key.gLeft)) && this.totalVelocity < -RUN_BRAKE_SPEED_LIMIT)) {
            this.animationID = 17;
            soundInstance.playSe(10);
            if (this.totalVelocity > 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            this.faceDirection = var5;
         } else if (this.totalVelocity != 0 && this.doBrake()) {
            this.animationID = 17;
            soundInstance.playSe(10);
            if (this.totalVelocity > 0) {
               var5 = true;
            } else {
               var5 = false;
            }

            this.faceDirection = var5;
         }
      }

      if (this.ducting && Math.abs(this.totalVelocity) < 640) {
         if (this.totalVelocity > 0 && this.pushOnce) {
            this.totalVelocity += 640;
            this.pushOnce = false;
         }

         if (this.totalVelocity < 0 && this.pushOnce) {
            this.totalVelocity -= 640;
            this.pushOnce = false;
         }
      }

      if (!this.spinLogic()) {
         if (this.canDoJump() && Key.press(16777216)) {
            if ((characterID != 3 || PlayerAmy.isCanJump) && (characterID != 3 || this.getCharacterAnimationID() != 18 && this.getCharacterAnimationID() != 19)) {
               this.doJump();
            }
         } else if (Key.repeat(4 | Key.gUp | 33554432)) {
            if (this.animationID == 38 && this.drawer.checkEnd()) {
               this.animationID = 39;
            }

            if (this.animationID != 38 && this.animationID != 39 && (this.animationID == 0 || this.animationID == 50 || this.animationID == 51)) {
               this.animationID = 38;
            }

            if (this.animationID == 39) {
               this.focusMovingState = 1;
            }
         } else {
            if (this.animationID == 40 && this.drawer.checkEnd()) {
               this.animationID = 0;
            }

            if (this.animationID == 38 || this.animationID == 39) {
               this.animationID = 40;
            }
         }
      }

      this.extraLogicWalk();
      if (!this.isAntiGravity && this.faceDegree >= 90 && this.faceDegree <= 270 || this.isAntiGravity && (this.faceDegree <= 90 || this.faceDegree >= 270)) {
         var6 = Math.abs(FAKE_GRAVITY_ON_WALK * MyAPI.dCos(this.faceDegree) / 100);
         var2 = this.totalVelocity * this.totalVelocity / 4864;
         if (var6 >= var2 && !this.ducting || this.animationID == 17) {
            this.calDivideVelocity();
            var2 = this.posX;
            var6 = -this.collisionRect.getHeight();
            var3 = this.faceDegree;
            var2 = this.getNewPointX(var2, 0, var6 >> 1, var3);
            var3 = this.posY;
            int var4 = -this.collisionRect.getHeight();
            var6 = this.faceDegree;
            var6 = this.getNewPointY(var3, 0, var4 >> 1, var6);
            var3 = this.collisionRect.getHeight();
            var4 = this.faceDegree;
            var2 = this.getNewPointX(var2, 0, var3 >> 1, var4);
            this.footPointX = var2;
            this.posX = var2;
            var3 = this.collisionRect.getHeight();
            var2 = this.faceDegree;
            var6 = this.getNewPointY(var6, 0, var3 >> 1, var2);
            this.footPointY = var6;
            this.posY = var6;
            this.collisionState = 1;
            this.worldCal.actionState = 1;
            return;
         }
      }

      if (!this.ducting) {
         if (this.needRetPower() && this.collisionState == 0) {
            var6 = this.totalVelocity;
            var2 = this.getRetPower();
            if (this.totalVelocity > 0) {
               this.totalVelocity -= var2;
               if (this.totalVelocity < 0) {
                  this.totalVelocity = 0;
               }
            } else if (this.totalVelocity < 0) {
               this.totalVelocity += var2;
               if (this.totalVelocity > 0) {
                  this.totalVelocity = 0;
               }
            }

            if (this.totalVelocity * var6 <= 0 && this.animationID == 4) {
               this.animationID = 0;
               if (var6 > 0) {
                  var5 = true;
               } else {
                  var5 = false;
               }

               this.faceDirection = var5;
            }
         }

         System.out.println("");
         if (this.collisionState == 1) {
            var2 = this.velY;
            if (this.isAntiGravity) {
               var1 = -1;
            } else {
               var1 = 1;
            }

            this.velY = var2 + var1 * this.getGravity();
         }
      }

   }

   public static boolean isHadRaceRecord() {
      boolean var0;
      if (StageManager.getTimeModeScore(characterID) < 599999) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   private static boolean isRaceModeNewRecord() {
      boolean var0;
      if (timeCount < StageManager.getTimeModeScore(characterID)) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   private int jumpDirectionX() {
      byte var1;
      if (this.faceDegree > 90 && this.faceDegree < 270) {
         var1 = 1;
      } else {
         var1 = -1;
      }

      return var1;
   }

   private void land() {
      this.calTotalVelocity();
      int var1 = soundInstance.getPlayingLoopSeIndex();
      SoundSystem var2 = soundInstance;
      if (var1 == 18) {
         soundInstance.stopLoopSe();
      }

      if (this.animationID != 45) {
         if (Math.abs(this.totalVelocity) == 0) {
            this.animationID = 0;
         } else if (Math.abs(this.totalVelocity) < SPEED_LIMIT_LEVEL_1) {
            this.animationID = 1;
         } else if (Math.abs(this.totalVelocity) < SPEED_LIMIT_LEVEL_2) {
            this.animationID = 2;
         } else if (!this.slipping) {
            this.animationID = 3;
         }
      }

      if (this.ducting) {
         if (this.totalVelocity > 0 && this.totalVelocity < 640 && this.pushOnce) {
            this.totalVelocity += 640;
            this.pushOnce = false;
         }

         if (this.totalVelocity < 0 && this.totalVelocity > -640 && this.pushOnce) {
            this.totalVelocity -= 640;
            this.pushOnce = false;
         }
      }

   }

   public static void minusLife() {
      --lifeNum;
   }

   public static boolean movingBar() {
      if (offsetx <= 0) {
         offsetx = 0;
      } else {
         offsetx -= movespeedx;
         if (offsetx == SCREEN_WIDTH - movespeedx) {
            if (stageModeState == 1) {
               if (isRaceModeNewRecord()) {
                  SoundSystem.getInstance().playBgm(41, false);
               } else {
                  SoundSystem.getInstance().playBgm(42, false);
               }
            } else if (StageManager.getStageID() == 12) {
               SoundSystem.getInstance().playBgm(28, false);
            } else if (StageManager.getStageID() == 13) {
               SoundSystem.getInstance().playBgm(29, false);
            } else {
               if (StageManager.getStageID() % 2 == 0) {
                  SoundSystem.getInstance().playBgm(26, false);
               }

               if (StageManager.getStageID() % 2 == 1) {
                  SoundSystem.getInstance().playBgm(27, false);
               }
            }
         }
      }

      boolean var0;
      if (offsetx == 0) {
         if (offsety <= (SCREEN_HEIGHT >> 1) - 36) {
            offsety = (SCREEN_HEIGHT >> 1) - 36;
            var0 = true;
         } else {
            offsety -= movespeedy;
            var0 = false;
         }
      } else {
         var0 = false;
      }

      return var0;
   }

   private static void playerLifeUpBGM() {
      SoundSystem.getInstance().stopBgm(false);
      SoundSystem var1;
      if (invincibleCount > 0) {
         var1 = SoundSystem.getInstance();
         var1.playBgmSequence(43, 44);
      } else {
         var1 = SoundSystem.getInstance();
         int var0 = StageManager.getBgmId();
         var1.playBgmSequence(43, var0);
      }

   }

   public static void resetGameParam() {
      scoreNum = 0;
      lifeNum = 2;
   }

   public static void setCharacter(int var0) {
      characterID = var0;
   }

   public static void setFadeColor(int var0) {
      for(int var1 = 0; var1 < fadeRGB.length; ++var1) {
         fadeRGB[var1] = var0;
      }

   }

   public static void setLife(int var0) {
      lifeNum = var0;
   }

   public static void setNewParam(int[] var0) {
      MOVE_POWER = var0[0];
      MOVE_POWER_IN_AIR = MOVE_POWER << 1;
      MOVE_POWER_REVERSE = var0[1];
      MAX_VELOCITY = var0[2];
      MOVE_POWER_REVERSE_BALL = var0[3];
      SPIN_START_SPEED_1 = var0[4];
      SPIN_START_SPEED_2 = var0[5];
      JUMP_START_VELOCITY = var0[6];
      HURT_POWER_X = var0[7];
      HURT_POWER_Y = var0[8];
      JUMP_RUSH_SPEED_PLUS = var0[10];
      JUMP_REVERSE_POWER = var0[11];
      FAKE_GRAVITY_ON_WALK = var0[12];
      FAKE_GRAVITY_ON_BALL = var0[13];
   }

   public static void setOverCount(int var0) {
      overTime = var0;
   }

   public static void setOverCount(int var0, int var1, int var2) {
      overTime = var0 * 60 * 1000 + var1 * 1000 + var2;
   }

   public static void setRingNum(int var0) {
      ringNum = var0;
   }

   public static void setScore(int var0) {
      scoreNum = var0;
   }

   public static void setTimeCount(int var0) {
      timeCount = var0;
      lastTimeCount = timeCount;
   }

   public static void setTimeCount(int var0, int var1, int var2) {
      timeCount = var0 * 60 * 1000 + var1 * 1000 + var2;
      lastTimeCount = timeCount;
   }

   private int spinLv2Calc() {
      int var1;
      if (this.isInWater) {
         var1 = SPIN_INWATER_START_SPEED_2;
      } else {
         var1 = SPIN_START_SPEED_2;
      }

      int var2 = this.spinDownWaitCount;
      return var1 * (1200 - var2 * 36) / 12 / 100;
   }

   public static void stagePassDraw(MFGraphics var0) {
      if (!StageManager.isOnlyStagePass) {
         switch(stageModeState) {
         case 0:
            if (!movingBar()) {
               drawMovingbar(var0, STAGE_PASS_STR_SPACE);
               stagePassResultOutOffsetX = 0;
               isStartStageEndFlag = false;
               stageEndFrameCnt = 0;
               isOnlyBarOut = false;
            } else {
               drawStagePassInfoScroll(var0, stagePassResultOutOffsetX, (SCREEN_HEIGHT >> 1) - 36, 8, 256);
               if (!clipMoveLogic()) {
                  clipMoveShadow(var0);
                  GameState.guiAniDrawer.draw(var0, 6, (SCREEN_WIDTH >> 1) - 70, (SCREEN_HEIGHT >> 1) - 6, false, 0);
                  GameState.guiAniDrawer.draw(var0, 7, (SCREEN_WIDTH >> 1) - 70, (SCREEN_HEIGHT >> 1) + MENU_SPACE - 6, false, 0);
                  drawNum(var0, score1, (SCREEN_WIDTH >> 1) + NUM_DISTANCE, SCREEN_HEIGHT >> 1, 2, 0);
                  drawNum(var0, score2, (SCREEN_WIDTH >> 1) + NUM_DISTANCE, (SCREEN_HEIGHT >> 1) + MENU_SPACE, 2, 0);
                  MyAPI.setClip(var0, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                  totalPlusscore = score1 + score2 + scoreNum;
               }
            }

            if (clipMoveLogic()) {
               AnimationDrawer var2 = GameState.guiAniDrawer;
               int var1 = SCREEN_WIDTH;
               var2.draw(var0, 6, stagePassResultOutOffsetX + ((var1 >> 1) - 70), (SCREEN_HEIGHT >> 1) - 6, false, 0);
               var2 = GameState.guiAniDrawer;
               var1 = SCREEN_WIDTH;
               var2.draw(var0, 7, stagePassResultOutOffsetX + ((var1 >> 1) - 70), (SCREEN_HEIGHT >> 1) + MENU_SPACE - 6, false, 0);
               if (stageModeState == 1) {
                  raceScoreNum = MyAPI.calNextPosition((double)raceScoreNum, (double)totalPlusscore, 1, 5);
               } else {
                  scoreNum = MyAPI.calNextPosition((double)scoreNum, (double)totalPlusscore, 1, 5);
               }

               score1 = MyAPI.calNextPosition((double)score1, 0.0D, 1, 5);
               score2 = MyAPI.calNextPosition((double)score2, 0.0D, 1, 5);
               drawNum(var0, score1, (SCREEN_WIDTH >> 1) + NUM_DISTANCE + stagePassResultOutOffsetX, SCREEN_HEIGHT >> 1, 2, 0);
               drawNum(var0, score2, (SCREEN_WIDTH >> 1) + NUM_DISTANCE + stagePassResultOutOffsetX, (SCREEN_HEIGHT >> 1) + MENU_SPACE, 2, 0);
               if (scoreNum == totalPlusscore) {
                  IsStarttoCnt = true;
                  if (!StageManager.isOnlyScoreCal) {
                     isStartStageEndFlag = true;
                  } else {
                     isOnlyBarOut = true;
                  }
               } else {
                  SoundSystem.getInstance().playSe(31);
               }
            }

            if (isStartStageEndFlag) {
               ++stageEndFrameCnt;
               if (stageEndFrameCnt == 2) {
                  SoundSystem.getInstance().playSe(32);
               }
            }

            if (isOnlyBarOut) {
               ++onlyBarOutCnt;
               if (onlyBarOutCnt == 2) {
                  SoundSystem.getInstance().playSe(32);
               }

               if (onlyBarOutCnt > onlyBarOutCntMax) {
                  stagePassResultOutOffsetX -= 96;
               }

               if (stagePassResultOutOffsetX < -1000) {
                  StageManager.isScoreBarOutOfScreen = true;
               }
            }
            break;
         case 1:
            if (!movingBar()) {
               drawMovingbar(var0, STAGE_PASS_STR_SPACE);
            } else {
               drawStagePassInfoScroll(var0, (SCREEN_HEIGHT >> 1) - 36, 8, 256);
               if (clipMoveLogic()) {
                  IsStarttoCnt = true;
               }

               clipMoveShadow(var0);
               GameState.guiAniDrawer.draw(var0, 8, (SCREEN_WIDTH >> 1) - 80, SCREEN_HEIGHT >> 1, false, 0);
               drawRecordTime(var0, timeCount, (SCREEN_WIDTH >> 1) + 72, (SCREEN_HEIGHT >> 1) + 7, 2, 2);
               MyAPI.setClip(var0, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
               if (isRaceModeNewRecord() && IsStarttoCnt && !StageManager.isSaveTimeModeScore) {
                  IsDisplayRaceModeNewRecord = true;
               }

               if (IsDisplayRaceModeNewRecord) {
                  GameState.guiAniDrawer.draw(var0, 9, SCREEN_WIDTH >> 1, (SCREEN_HEIGHT >> 1) + 33, false, 0);
               }

               if (!StageManager.isSaveTimeModeScore && IsStarttoCnt) {
                  StageManager.setTimeModeScore(characterID, timeCount);
                  StageManager.isSaveTimeModeScore = true;
               }
            }
         }
      }

   }

   public static void stagePassLogic() {
      switch(stageModeState) {
      case 0:
      default:
      }
   }

   public static void timeDraw(MFGraphics var0, int var1, int var2) {
      byte var3;
      byte var4;
      int var5;
      int var6;
      int var7;
      label50: {
         var5 = timeCount / '\uea60';
         var6 = timeCount % '\uea60' / 1000;
         var7 = timeCount % '\uea60' % 1000 / 10;
         var4 = 0;
         if (!GlobalResource.timeIsLimit()) {
            var3 = var4;
            if (stageModeState != 1) {
               break label50;
            }
         }

         if (overTime <= timeCount || timeCount <= 540000) {
            var3 = var4;
            if (overTime >= timeCount) {
               break label50;
            }

            var3 = var4;
            if (timeCount >= 60000) {
               break label50;
            }
         }

         var3 = var4;
         if (timeCount / 240 % 2 == 0) {
            var3 = 3;
         }
      }

      if (var7 < 10) {
         drawNum(var0, 0, var1 - NUM_SPACE[var3], var2, 2, var3);
      }

      drawNum(var0, var7, var1, var2, 2, var3);
      if (var3 == 3) {
         var4 = 2;
      } else {
         var4 = 0;
      }

      var7 = NUM_SPACE[var3];
      int var8 = NUM_SPACE[var3];
      NumberDrawer.drawColon(var0, var4, var1 - var7 * 2 - (var8 >> 1), var2, 34);
      if (var6 < 10) {
         drawNum(var0, 0, var1 - NUM_SPACE[var3] * 4, var2, 2, var3);
      }

      drawNum(var0, var6, var1 - NUM_SPACE[var3] * 3, var2, 2, var3);
      if (var3 == 3) {
         var4 = 2;
      } else {
         var4 = 0;
      }

      var7 = NUM_SPACE[var3];
      var6 = NUM_SPACE[var3];
      NumberDrawer.drawColon(var0, var4, var1 - var7 * 5 - (var6 >> 1), var2, 34);
      drawNum(var0, var5, var1 - NUM_SPACE[var3] * 6, var2, 2, var3);
   }
   
   public static void timeLogic() {
      if (!timeStopped) {
         if (overTime > timeCount) {
            timeCount += 60;
            if (timeCount > overTime) {
               timeCount = overTime;
            }

            if (GlobalResource.timeIsLimit()) {
               if (overTime - timeCount <= 21000) {
                  if (timeCount / 1000 != preTimeCount) {
                     SoundSystem.getInstance().playSe(30);
                  }

                  preTimeCount = timeCount / 1000;
               }

               if (timeCount == overTime && player != null) {
                  if (stageModeState == 1) {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageTimeover();
                     StageManager.checkPointTime = 0;
                  } else if (lifeNum > 0) {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageTimeover();
                     StageManager.checkPointTime = 0;
                     minusLife();
                  } else {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageGameover();
                  }
               }
            } else if (stageModeState == 1) {
               if (overTime - timeCount <= 21000) {
                  if (timeCount / 1000 != preTimeCount) {
                     SoundSystem.getInstance().playSe(30);
                  }

                  preTimeCount = timeCount / 1000;
               }

               if (timeCount == overTime && player != null) {
                  if (stageModeState == 1) {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageTimeover();
                     StageManager.checkPointTime = 0;
                  } else if (lifeNum > 0) {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageTimeover();
                     StageManager.checkPointTime = 0;
                     minusLife();
                  } else {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageGameover();
                  }
               }
            }
         } else if (overTime < timeCount) {
            timeCount -= 60;
            if (timeCount < overTime) {
               timeCount = overTime;
            }

            if (GlobalResource.timeIsLimit()) {
               if (timeCount <= 21000) {
                  if (timeCount / 1000 != preTimeCount) {
                     SoundSystem.getInstance().playSe(30);
                  }

                  preTimeCount = timeCount / 1000;
               }

               if (timeCount == overTime && player != null) {
                  if (stageModeState == 1) {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageTimeover();
                     StageManager.checkPointTime = 0;
                  } else if (lifeNum > 0) {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageTimeover();
                     StageManager.checkPointTime = 0;
                     minusLife();
                  } else {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageGameover();
                  }
               }
            } else if (stageModeState == 1) {
               if (timeCount <= 21000) {
                  if (timeCount / 1000 != preTimeCount) {
                     SoundSystem.getInstance().playSe(30);
                  }

                  preTimeCount = timeCount / 1000;
               }

               if (timeCount == overTime && player != null) {
                  if (stageModeState == 1) {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageTimeover();
                     StageManager.checkPointTime = 0;
                  } else if (lifeNum > 0) {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageTimeover();
                     StageManager.checkPointTime = 0;
                     minusLife();
                  } else {
                     for (int i = 0; i < myPlayerStatic.length; i++) {
                     if (myPlayerStatic[i] != null) myPlayerStatic[i].setDie(false);
                     }
                     StageManager.setStageGameover();
                  }
               }
            }
         }
      }

   }

   private void waterFallDraw(MFGraphics var1, Coordinate var2) {
      if (this.waterFalling) {
         short var3;
         if (characterID == 2 && this.myAnimationID == 22) {
            var3 = 320;
         } else {
            var3 = -320;
         }

         AnimationDrawer var7 = this.waterFallDrawer;
         int var6 = this.collisionRect.x0;
         int var5 = this.collisionRect.x1;
         int var4 = this.collisionRect.y0;
         this.drawInMap(var1, var7, var6 + var5 >> 1, var4 + var3);
         this.waterFalling = false;
      }

   }

   private void waterFlushDraw(MFGraphics var1) {
      if (this.showWaterFlush) {
         this.initWaterFlush();
         AnimationDrawer var4 = this.waterFlushDrawer;
         int var3 = this.footPointX;
         int var2;
         if (StageManager.getCurrentZoneId() != 4 && StageManager.getCurrentZoneId() != 5) {
            var2 = this.collisionRect.y1;
         } else {
            var2 = this.collisionRect.y1 - 512;
         }

         this.drawInMap(var1, var4, var3, var2);
         this.showWaterFlush = false;
      }

   }

   public void bankLogic() {
      if (this.onBank) {
         this.faceDegree = 0;
         this.inputLogicWalk();
         if (this.onBank) {
            this.calDivideVelocity();
            this.velY = 0;
            int var1 = getPlayerObj().getFootPositionX();
            int var2 = getPlayerObj().getFootPositionY();
            this.footPointX += this.velX;
            int var3 = this.footPointX;
            var3 = MyAPI.dCos((var3 - 660480) * 5760 / 11264 >> 6) * 3072 / 100;
            this.decelerate();
            int var4 = getPlayerObj().getVelX();
            if (Math.abs(var4) > 500) {
               getPlayerObj().setFootPositionY(Math.max('\uf800' - (var3 + 3072), getPlayerObj().getFootPositionY() - Math.abs(var4) * 400 / 500));
            } else {
               getPlayerObj().setFootPositionY(Math.min(63488, getPlayerObj().getFootPositionY() + 200));
               if (this.footPointY >= 63488) {
                  this.onBank = false;
                  this.collisionState = 1;
                  this.worldCal.actionState = 1;
                  this.bankwalking = false;
               }
            }

            if (this.animationID != 4) {
               if (Math.abs(var4) > 500) {
                  if (this.footPointY < 61184) {
                     this.animationID = 34;
                  } else if (this.footPointY < 61952) {
                     this.animationID = 33;
                  } else if (this.footPointY < 62720) {
                     this.animationID = 32;
                  }
               } else {
                  this.onBank = false;
                  this.collisionState = 1;
                  this.worldCal.actionState = 1;
                  this.doDripInAir();
               }
            }

            this.checkWithObject(var1, var2, this.footPointX, this.footPointY);
         }
      }

   }

   public boolean beAccelerate(int var1, boolean var2, GameObject var3) {
      if (this.collisionState == 0) {
         this.totalVelocity = var1;
         if (this.totalVelocity > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         this.faceDirection = var2;
         var2 = true;
      } else if (this.collisionState == 2 && !(var3 instanceof Accelerate)) {
         if (var2) {
            this.velX = var1;
         } else {
            this.velY = var1;
         }

         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }
   
   public void beHurt() {
      if (Player2Hurt && getPlayerObj().canBeHurt()) {
         beHurtNoRingLose();
         if (!this.beAttackByHari) soundInstance.playSe(14);
         if (this.beAttackByHari) this.beAttackByHari = false;
         return;
      } else if (getPlayerObj().canBeHurt()) {
         this.doHurt();
         int var1 = this.footPointX;
         int var2 = this.faceDegree;
         var1 = this.getNewPointX(var1, 0, -768, var2);
         int var3 = this.footPointY;
         var2 = this.faceDegree;
         var2 = this.getNewPointY(var3, 0, -768, var2);
         this.faceDegree = this.degreeStable;
         var3 = this.faceDegree;
         this.footPointX = this.getNewPointX(var1, 0, 768, var3);
         var1 = this.faceDegree;
         this.footPointY = this.getNewPointY(var2, 0, 768, var1);
         if (shieldType == 0) {
            if (ringNum + ringTmpNum > 0) {
               var2 = ringNum;
               var3 = ringTmpNum;
               var1 = this.getBodyPositionX();
               int var4 = this.getBodyPositionY();
               int var5 = this.currentLayer;
               boolean var6 = this.isAntiGravity;
               RingObject.hurtRingExplosion(var2 + var3, var1, var4, var5, var6);
               ringNum = 0;
               ringTmpNum = 0;
            } else if (ringNum == 0 && ringTmpNum == 0) {
               this.setDie(false);
            }
         } else {
            shieldType = 0;
            if (!this.beAttackByHari) {
               soundInstance.playSe(14);
            }

            if (this.beAttackByHari) {
               this.beAttackByHari = false;
            }
         }
      }
   }
   
   public void beHurtByCage() {
      if (this.hurtCount == 0) {
         this.doHurt();
         this.velX = this.velX * 3 / 2;
         this.velY = this.velY * 3 / 2;
      }

   }

   public void beHurtNoRingLose() {
      if (getPlayerObj().canBeHurt()) {
         this.doHurt();
         int var1 = this.footPointX;
         int var2 = this.faceDegree;
         var1 = this.getNewPointX(var1, 0, -768, var2);
         int var3 = this.footPointY;
         var2 = this.faceDegree;
         var2 = this.getNewPointY(var3, 0, -768, var2);
         this.faceDegree = this.degreeStable;
         var3 = this.faceDegree;
         this.footPointX = this.getNewPointX(var1, 0, 768, var3);
         var1 = this.faceDegree;
         this.footPointY = this.getNewPointY(var2, 0, 768, var1);
         if (!Player2Hurt && shieldType != 0) {
            shieldType = 0;
         }
      }

   }

   public void bePop(int var1, int var2) {
      this.beSpring(var1, var2);
      if (!this.isAntiGravity && var2 == 1 || this.isAntiGravity && var2 == 0) {
         this.animationID = 14;
         this.collisionState = 1;
         this.worldCal.actionState = 1;
      }

   }

   public void beSlide0(GameObject var1) {
      if (this.collisionState == 0) {
         this.calDivideVelocity();
      }

      this.degreeRotateMode = 0;
      if (this.hurtNoControl && this.collisionState == 1 && (this.velY < 0 && !this.isAntiGravity || this.velY > 0 && this.isAntiGravity)) {
         if (this.isAntiGravity) {
            this.footPointY = var1.getCollisionRect().y1;
         } else {
            this.footPointY = var1.getCollisionRect().y0;
         }
      } else {
         if (this.collisionState != 2) {
            this.calTotalVelocity();
         }

         this.setSlideAni();
         if (this.isAntiGravity) {
            this.footPointY = var1.getCollisionRect().y1;
         } else {
            this.footPointY = var1.getCollisionRect().y0;
         }

         if (this.isFootOnObject(var1)) {
            this.checkedObject = true;
         }

         this.setVelY(0);
         this.worldCal.stopMoveY();
         if (this.collisionState != 2 || !this.isFootOnObject(var1)) {
            this.footOnObject = var1;
            this.collisionState = 2;
            this.collisionChkBreak = true;
         }
      }

      this.onObjectContinue = true;
      this.posX = this.footPointX;
      this.posY = this.footPointY;
      this.setPosition(this.posX, this.posY);
   }

   public void beSpSpring(int var1, int var2) {
      if (this.collisionState == 0) {
         this.calDivideVelocity();
      }

      this.velY = -var1;
      this.worldCal.stopMoveY();
      if (this.collisionState == 0) {
         this.calTotalVelocity();
      }

      var1 = this.degreeStable;
      this.faceDegree = var1;
      this.degreeForDraw = var1;
      this.animationID = 9;
      this.collisionState = 1;
      this.worldCal.actionState = 1;
      this.collisionChkBreak = true;
      this.drawer.restart();
      MapManager.setFocusObj((Focusable)null);
      this.setMeetingBoss(false);
      this.animationID = 14;
      this.enteringSP = true;
      soundInstance.playSe(37);
   }

   public void beSpring(int var1, int var2) {
      if (this.collisionState == 0) {
         this.calDivideVelocity();
      }

      int var3 = var1;
      if (this.isInWater) {
         var3 = var1 * 185 / 100;
      }

      switch(var2) {
      case 0:
         this.velY = var3;
         this.worldCal.stopMoveY();
         break;
      case 1:
         this.velY = -var3;
         this.worldCal.stopMoveY();
         break;
      case 2:
         this.velX = var3;
         this.worldCal.stopMoveX();
         break;
      case 3:
         this.velX = -var3;
         this.worldCal.stopMoveX();
      }

      if (this.collisionState == 0) {
         this.calTotalVelocity();
      }

      if (!this.isAntiGravity && var2 == 1 || this.isAntiGravity && var2 == 0) {
         var1 = this.degreeStable;
         this.faceDegree = var1;
         this.degreeForDraw = var1;
         this.animationID = 9;
         this.collisionState = 1;
         this.worldCal.actionState = 1;
         this.collisionChkBreak = true;
         this.drawer.restart();
      }

      if (player instanceof PlayerTails) {
         ((PlayerTails)player).resetFlyCount();
      }

   }

   public void beStop(int var1, int var2, GameObject var3) {
      var1 = var2;
      if (this.isAntiGravity) {
         if (var2 == 1) {
            var1 = 0;
         } else {
            var1 = var2;
            if (var2 == 0) {
               var1 = 1;
            }
         }
      }

      switch(var1) {
      case 0:
         if (!this.isAntiGravity && this.velY < 0) {
            this.setVelY(0);
            this.worldCal.stopMoveY();
         }

         if (this.isAntiGravity && this.velY > 0) {
            this.setVelY(0);
            this.worldCal.stopMoveY();
         }

         if (this.isAntiGravity) {
            this.footPointY = var3.getCollisionRect().y0 - this.collisionRect.getHeight();
         } else {
            this.footPointY = var3.getCollisionRect().y1 + this.collisionRect.getHeight();
         }

         if ((this.collisionState == 0 || this.collisionState == 2) && this.faceDegree == 0 && !(var3 instanceof Spring) && !(var3 instanceof ItemObject)) {
            this.setDie(false);
         }
         break;
      case 1:
         if (this.collisionState == 0) {
            this.calDivideVelocity();
         }

         this.degreeRotateMode = 0;
         if (this.hurtNoControl && this.collisionState == 1 && (this.velY < 0 && !this.isAntiGravity || this.velY > 0 && this.isAntiGravity)) {
            if (this.isAntiGravity) {
               this.footPointY = var3.getCollisionRect().y1;
            } else {
               this.footPointY = var3.getCollisionRect().y0;
            }
         } else {
            if (this.collisionState != 2 && !(var3 instanceof Spring)) {
               this.land();
            }

            if (this.isAntiGravity) {
               this.footPointY = var3.getCollisionRect().y1;
            } else {
               this.footPointY = var3.getCollisionRect().y0;
            }

            if (this.isFootOnObject(var3)) {
               this.checkedObject = true;
            }

            this.setVelY(0);
            this.worldCal.stopMoveY();
            if (this.collisionState != 2 || !this.isFootOnObject(var3)) {
               this.footOnObject = var3;
               this.collisionState = 2;
               this.collisionChkBreak = true;
            }
         }

         this.onObjectContinue = true;
         break;
      case 2:
      case 3:
         if (var1 == 3) {
            var2 = this.footPointX;
            this.footPointX = var3.getCollisionRect().x0 - (this.collisionRect.getWidth() >> 1) + 1;
            this.footPointX = this.getNewPointX(this.footPointX, 0, this.getCurrentHeight() >> 1, this.faceDegree);
            var1 = this.footPointX;
            this.movedSpeedX = var1 - var2;
            if (!(var3 instanceof DekaPlatform)) {
               this.movedSpeedX = 0;
            }

            if (Key.repeat(Key.gRight) && (this.animationID == 0 || this.animationID == 47 || this.animationID == 48 || this.animationID == 1 || this.animationID == 2 || this.animationID == 3) && (!(var3 instanceof Hari) || var3.objId != 3 || !this.canBeHurt())) {
               this.animationID = 8;
            }

            if ((!(var3 instanceof Hari) || var3.objId != 3 || !this.canBeHurt()) && this.getVelX() > 0) {
               this.setVelX(0);
               this.worldCal.stopMoveX();
            }

            this.rightStopped = true;
         } else {
            var1 = this.footPointX;
            this.footPointX = var3.getCollisionRect().x1 + (this.collisionRect.getWidth() >> 1) - 1;
            this.footPointX = this.getNewPointX(this.footPointX, 0, this.getCurrentHeight() >> 1, this.faceDegree);
            var2 = this.footPointX;
            this.movedSpeedX = var2 - var1;
            if (!(var3 instanceof DekaPlatform)) {
               this.movedSpeedX = 0;
            }

            if (Key.repeat(Key.gLeft) && (this.animationID == 0 || this.animationID == 47 || this.animationID == 48 || this.animationID == 1 || this.animationID == 2 || this.animationID == 3) && (!(var3 instanceof Hari) || var3.objId != 4 || !this.canBeHurt())) {
               this.animationID = 8;
            }

            if ((!(var3 instanceof Hari) || var3.objId != 4 || !this.canBeHurt()) && this.getVelX() < 0) {
               this.setVelX(0);
               this.worldCal.stopMoveX();
            }

            this.leftStopped = true;
         }

         if (this.collisionState == 0 && this.animationID == 4 && var3 instanceof Hari) {
            this.animationID = 0;
         }

         switch(this.collisionState) {
         case 1:
            this.xFirst = false;
         case 0:
         default:
            if (var3 instanceof GimmickObject) {
               this.isStopByObject = true;
            } else {
               this.isStopByObject = false;
            }
         }
      }

      this.posX = this.footPointX;
      this.posY = this.footPointY;
   }

   public void beStop(int var1, int var2, GameObject var3, boolean var4) {
      var1 = var2;
      if (this.isAntiGravity) {
         if (var2 == 1) {
            var1 = 0;
         } else {
            var1 = var2;
            if (var2 == 0) {
               var1 = 1;
            }
         }
      }

      switch(var1) {
      case 0:
         if (!this.isAntiGravity && this.velY < 0) {
            this.setVelY(0);
            this.worldCal.stopMoveY();
         }

         if (this.isAntiGravity && this.velY > 0) {
            this.setVelY(0);
            this.worldCal.stopMoveY();
         }

         if (this.isAntiGravity) {
            this.footPointY = var3.getCollisionRect().y0 - this.collisionRect.getHeight();
         } else {
            this.footPointY = var3.getCollisionRect().y1 + this.collisionRect.getHeight();
         }

         if ((this.collisionState == 0 || this.collisionState == 2) && this.faceDegree == 0 && !(var3 instanceof ItemObject)) {
            this.setDie(false);
         }
         break;
      case 1:
         if (this.collisionState == 0) {
            this.calDivideVelocity();
         }

         this.degreeRotateMode = 0;
         var1 = this.footPointY;
         if (this.hurtNoControl && this.collisionState == 1 && (this.velY < 0 && !this.isAntiGravity || this.velY > 0 && this.isAntiGravity)) {
            if (this.isAntiGravity) {
               this.footPointY = var3.getCollisionRect().y1;
            } else {
               this.footPointY = var3.getCollisionRect().y0;
            }
         } else {
            if (this.collisionState != 2 && !(var3 instanceof Spring)) {
               this.land();
            }

            if (this.isAntiGravity) {
               this.footPointY = var3.getCollisionRect().y1;
            } else {
               this.footPointY = var3.getCollisionRect().y0;
            }

            if (this.isFootOnObject(var3)) {
               this.checkedObject = true;
            }

            this.setVelY(0);
            this.worldCal.stopMoveY();
            if (this.collisionState != 2 || !this.isFootOnObject(var3)) {
               this.footOnObject = var3;
               this.collisionState = 2;
               this.collisionChkBreak = true;
            }

            if (this.isSidePushed != 4 || !var4) {
               if (this.isSidePushed == 3) {
                  this.footPointX = this.bePushedFootX;
                  System.out.println("~~RIGHT footPointX:" + this.footPointX);
                  if (this.getVelX() > 0) {
                     this.setVelX(0);
                     this.worldCal.stopMoveX();
                  }
               } else if (this.isSidePushed == 2) {
                  this.footPointX = this.bePushedFootX;
                  System.out.println("~~LEFT footPointX:" + this.footPointX);
                  if (this.getVelX() < 0) {
                     this.setVelX(0);
                     this.worldCal.stopMoveX();
                  }
               }
            }
         }

         var2 = this.footPointY;
         this.movedSpeedY = var2 - var1;
         this.onObjectContinue = true;
         break;
      case 2:
      case 3:
         if (var1 == 3) {
            var2 = this.footPointX;
            this.footPointX = var3.getCollisionRect().x0 - (this.collisionRect.getWidth() >> 1) + 1;
            this.footPointX = this.getNewPointX(this.footPointX, 0, this.getCurrentHeight() >> 1, this.faceDegree);
            var1 = this.footPointX;
            this.bePushedFootX = this.footPointX - 512;
            this.movedSpeedX = var1 - var2;
            if (!(var3 instanceof DekaPlatform)) {
               this.movedSpeedX = 0;
            }

            if (Key.repeat(Key.gRight) && (this.collisionState == 0 || this.collisionState == 2 || this.collisionState == 3) && !this.isAttacking) {
               this.animationID = 8;
            }

            if (this.getVelX() > 0) {
               this.setVelX(0);
               this.worldCal.stopMoveX();
            }

            this.rightStopped = true;
         } else {
            var1 = this.footPointX;
            this.footPointX = var3.getCollisionRect().x1 + (this.collisionRect.getWidth() >> 1) - 1;
            this.footPointX = this.getNewPointX(this.footPointX, 0, this.getCurrentHeight() >> 1, this.faceDegree);
            var2 = this.footPointX;
            this.bePushedFootX = this.footPointX;
            this.movedSpeedX = var2 - var1;
            if (!(var3 instanceof DekaPlatform)) {
               this.movedSpeedX = 0;
            }

            if (Key.repeat(Key.gLeft) && (this.collisionState == 0 || this.collisionState == 2 || this.collisionState == 3) && !this.isAttacking) {
               this.animationID = 8;
            }

            if (this.getVelX() < 0) {
               this.setVelX(0);
               this.worldCal.stopMoveX();
            }

            this.leftStopped = true;
         }

         if (this.collisionState == 0 && this.animationID == 4 && var3 instanceof Hari) {
            this.animationID = 0;
         }

         switch(this.collisionState) {
         case 1:
            this.xFirst = false;
         case 0:
         default:
            if (var3 instanceof GimmickObject) {
               this.isStopByObject = true;
            } else {
               this.isStopByObject = false;
            }
         }
      }

      this.posX = this.footPointX;
      this.posY = this.footPointY;
   }

   public void beStopbyDoor(int var1, int var2, GameObject var3) {
      var1 = var2;
      if (this.isAntiGravity) {
         if (var2 == 1) {
            var1 = 0;
         } else {
            var1 = var2;
            if (var2 == 0) {
               var1 = 1;
            }
         }
      }

      switch(var1) {
      case 0:
         if (!this.isAntiGravity && this.velY < 0) {
            this.setVelY(0);
            this.worldCal.stopMoveY();
         }

         if (this.isAntiGravity && this.velY > 0) {
            this.setVelY(0);
            this.worldCal.stopMoveY();
         }

         if (this.isAntiGravity) {
            this.footPointY = var3.getCollisionRect().y0 - this.collisionRect.getHeight();
         } else {
            this.footPointY = var3.getCollisionRect().y1 + this.collisionRect.getHeight();
         }

         if ((this.collisionState == 0 || this.collisionState == 2) && this.faceDegree == 0) {
            this.setDie(false);
         }
         break;
      case 1:
         if (this.collisionState == 0) {
            this.calDivideVelocity();
         }

         this.degreeRotateMode = 0;
         if (this.hurtNoControl && this.collisionState == 1 && (this.velY < 0 && !this.isAntiGravity || this.velY > 0 && this.isAntiGravity)) {
            if (this.isAntiGravity) {
               this.footPointY = var3.getCollisionRect().y1;
            } else {
               this.footPointY = var3.getCollisionRect().y0;
            }
         } else {
            if (this.collisionState != 2 && !(var3 instanceof Spring)) {
               this.land();
            }

            if (this.isAntiGravity) {
               this.footPointY = var3.getCollisionRect().y1;
            } else {
               this.footPointY = var3.getCollisionRect().y0;
            }

            if (this.isFootOnObject(var3)) {
               this.checkedObject = true;
            }

            if (this.collisionState != 2 || !this.isFootOnObject(var3)) {
               this.footOnObject = var3;
               this.collisionState = 2;
               this.collisionChkBreak = true;
            }
         }

         this.onObjectContinue = true;
         break;
      case 2:
      case 3:
         boolean var4;
         if (var1 == 3) {
            var1 = this.footPointX;
            this.footPointX = var3.getCollisionRect().x0 - (this.collisionRect.getWidth() >> 1) + 1;
            this.footPointX = this.getNewPointX(this.footPointX, 0, this.getCurrentHeight() >> 1, this.faceDegree);
            var2 = this.footPointX;
            this.movedSpeedX = var2 - var1;
            if (!(var3 instanceof DekaPlatform)) {
               this.movedSpeedX = 0;
            }

            if (Key.repeat(Key.gRight) && (this.collisionState == 0 || this.collisionState == 2 || this.collisionState == 3) && !this.isAttacking) {
               this.animationID = 8;
            }

            if (this.getVelX() > 0) {
               this.setVelX(0);
               this.worldCal.stopMoveX();
            }

            this.rightStopped = true;
            if (this.isAntiGravity) {
               var4 = false;
            } else {
               var4 = true;
            }

            this.faceDirection = var4;
         } else {
            var2 = this.footPointX;
            this.footPointX = var3.getCollisionRect().x1 + (this.collisionRect.getWidth() >> 1) - 1;
            this.footPointX = this.getNewPointX(this.footPointX, 0, this.getCurrentHeight() >> 1, this.faceDegree);
            var1 = this.footPointX;
            this.movedSpeedX = var1 - var2;
            if (!(var3 instanceof DekaPlatform)) {
               this.movedSpeedX = 0;
            }

            if (Key.repeat(Key.gLeft) && (this.collisionState == 0 || this.collisionState == 2 || this.collisionState == 3) && !this.isAttacking) {
               this.animationID = 8;
            }

            if (this.getVelX() < 0) {
               this.setVelX(0);
               this.worldCal.stopMoveX();
            }

            this.leftStopped = true;
            if (this.isAntiGravity) {
               var4 = true;
            } else {
               var4 = false;
            }

            this.faceDirection = var4;
         }

         if (this.collisionState == 0 && this.animationID == 4 && var3 instanceof Hari) {
            this.animationID = 0;
         }

         switch(this.collisionState) {
         case 1:
            this.xFirst = false;
         case 0:
         default:
            if (var3 instanceof GimmickObject) {
               this.isStopByObject = true;
            } else {
               this.isStopByObject = false;
            }
         }
      }

      this.posX = this.footPointX;
      this.posY = this.footPointY;
   }

   public void beTrans(int var1, int var2) {
      this.animationID = 4;
      this.collisionState = 1;
      this.transing = true;
      this.setBodyPositionX(var1);
      this.setBodyPositionY(var2);
      MapManager.setCameraMoving();
      this.calPreCollisionRect();
   }

   public boolean beUnseenPop() {
      boolean var2;
      if (this.collisionState == 0 && Math.abs(this.getVelX()) > 1024) {
         this.beSpring(this.getGravity() + 2048, 1);
         short var1 = 2048;
         if (2048 > 4352) {
            var1 = 4352;
         }

         if (this.getVelX() > 0) {
            this.beSpring(var1, 2);
         } else {
            this.beSpring(var1, 3);
         }

         SoundSystem.getInstance().playSequenceSe(37);
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void beWaterFall() {
      this.waterFalling = true;
      this.velY += GRAVITY / 10;
   }

   public void calDivideVelocity() {
      this.calDivideVelocity(this.faceDegree);
   }

   public void calDivideVelocity(int var1) {
      this.velX = this.totalVelocity * MyAPI.dCos(var1) / 100;
      this.velY = this.totalVelocity * MyAPI.dSin(var1) / 100;
   }

   public void calPreCollisionRect() {
      int var2 = this.getCollisionRectHeight();
      this.checkPositionX = this.getNewPointX(this.footPointX, 0, -var2 >> 1, this.faceDegree);
      this.checkPositionY = this.getNewPointY(this.footPointY, 0, -var2 >> 1, this.faceDegree);
      int var4 = this.checkPositionX;
      int var5 = this.checkPositionY;
      int var1 = this.checkPositionX;
      int var3 = this.checkPositionY;
      this.preCollisionRect.setTwoPosition(var4 - 512, var5 - (var2 >> 1), var1 + 512, var3 + (var2 >> 1));
   }

   public void calTotalVelocity() {
      this.calTotalVelocity(this.faceDegree);
   }

   public void calTotalVelocity(int var1) {
      int var4 = this.velX;
      int var3 = MyAPI.dCos(var1);
      int var2 = this.velY;
      var1 = (var4 * var3 + var2 * MyAPI.dSin(var1)) / 100;
      this.totalVelocity = var1;
   }

   public boolean canBeHurt() {
      boolean var1;
      if (this.hurtCount <= 0 && invincibleCount <= 0 && !this.isDead) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean canDoJump() {
      boolean var1;
      if (this.animationID != 5) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void cancelFootObject() {
      if (this.collisionState == 2) {
         getPlayerObj().footOnObject = null;
         this.onObjectContinue = false;
      }

   }

   public void cancelFootObject(GameObject var1) {
      if (this.collisionState == 2 && this.isFootOnObject(var1)) {
         getPlayerObj().collisionState = 1;
         getPlayerObj().footOnObject = null;
         this.onObjectContinue = false;
      }

   }

   public boolean changeRectDownCheck() {
      int var1 = 2048 / this.worldInstance.getTileHeight();

      boolean var6;
      while(true) {
         if (var1 < 0) {
            var6 = false;
            break;
         }

         ACWorld var7 = this.worldInstance;
         int var2 = this.collisionRect.x0;
         int var3 = this.collisionRect.y0;
         int var4 = this.worldInstance.getTileHeight();
         int var5 = this.currentLayer;
         var2 = var7.getWorldY(var2 + 512, var3 + var4 * var1, var5, 2);
         if (var2 != -1000) {
            var6 = true;
            break;
         }

         --var1;
      }

      return var6;
   }

   public boolean changeRectUpCheck() {
      int var1 = 2048 / this.worldInstance.getTileHeight();

      boolean var6;
      while(true) {
         if (var1 < 0) {
            var6 = false;
            break;
         }

         ACWorld var7 = this.worldInstance;
         int var3 = this.collisionRect.x0;
         int var5 = this.collisionRect.y0;
         int var4 = this.worldInstance.getTileHeight();
         int var2 = this.currentLayer;
         var2 = var7.getWorldY(var3 + 512, var5 - var4 * var1, var2, 0);
         if (var2 != -1000) {
            var6 = true;
            break;
         }

         --var1;
      }

      return var6;
   }

   public void changeVisible(boolean var1) {
      this.visible = var1;
   }

   public void checkBreatheReset() {
      int var2 = this.posY;
      int var1 = -this.collisionRect.getHeight();
      int var3 = this.faceDegree;
      var1 = this.getNewPointY(var2, 0, var1, var3);
      if (var1 + 256 < StageManager.getWaterLevel() << 6) {
         this.resetBreatheCount();
      }

   }

   public void checkWithObject(int var1, int var2, int var3, int var4) {
      int var9 = var3 - var1;
      int var10 = var4 - var2;
      if (var9 == 0 && var10 == 0) {
         this.footPointX = var3;
         this.footPointY = var4;
      } else {
         boolean var11;
         if (Math.abs(var9) >= Math.abs(var10)) {
            var11 = true;
         } else {
            var11 = false;
         }

         if (var11) {
            var3 = Math.abs(var9);
         } else {
            var3 = Math.abs(var10);
         }

         int var5 = var1;
         int var6 = var2;

         int var7;
         for(var4 = 0; var4 <= var3 && var4 < var3; var6 = var7) {
            var7 = var4 + 512;
            var4 = var7;
            if (var7 >= var3) {
               var4 = var3;
            }

            int var8 = var1 + var9 * var4 / var3;
            var7 = var2 + var10 * var4 / var3;
            getPlayerObj().moveDistance.x = (var8 >> 6) - (var5 >> 6);
            getPlayerObj().moveDistance.y = (var7 >> 6) - (var6 >> 6);
            this.footPointX = var8;
            this.footPointY = var7;
            this.collisionCheckWithGameObject(var8, var7);
            if (this.collisionChkBreak) {
               break;
            }

            var5 = var8;
         }
      }

   }

   public void close() {
      Animation.closeAnimationDrawer(this.waterFallDrawer);
      this.waterFallDrawer = null;
      Animation.closeAnimationDrawer(this.waterFlushDrawer);
      this.waterFlushDrawer = null;
      Animation.closeAnimationDrawer(this.drawer);
      this.drawer = null;
      Animation.closeAnimationDrawer(this.effectDrawer);
      this.effectDrawer = null;
      Animation.closeAnimation(this.dustEffectAnimation);
      this.dustEffectAnimation = null;
      Animation.closeAnimationDrawer(waterSprayDrawer);
      waterSprayDrawer = null;
      Animation.closeAnimationDrawer(moonStarDrawer);
      moonStarDrawer = null;
      //for (PlayerObject p : myPlayerStatic) p = null;
      this.closeImpl();
   }

   public abstract void closeImpl();

   public void collisionCheckWithGameObject() {
      this.collisionCheckWithGameObject(this.footPointX, this.footPointY);
   }

   public void collisionCheckWithGameObject(int var1, int var2) {
      this.collisionChkBreak = false;
      this.refreshCollisionRectWrap();
      if (this.isAttracting()) {
         CollisionRect var3 = this.attractRect;
         var3.setRect(var1 - 4800, var2 - 4800 - 768, 9600, 9600);
      }

      collisionChkWithAllGameObject(this);
      this.calPreCollisionRect();
   }

   public void collisionChk() {
      if (!this.noMoving) {
         switch(this.collisionState) {
         case 0:
            this.calDivideVelocity(this.faceDegree);
         default:
            this.posZ = this.currentLayer;
            this.worldCal.footDegree = this.faceDegree;
            this.posX = this.footPointX;
            this.posY = this.footPointY;
            if (this.collisionState == 2) {
               this.collisionLogicOnObject();
            } else if (this.isInWater) {
               this.worldCal.actionLogic(this.velX >> 1, this.velY >> 1, (int)((float)this.totalVelocity * 5.0F / 9.0F));
            } else if (this.movedSpeedX != 0) {
               this.worldCal.actionLogic(this.movedSpeedX, this.velY);
            } else {
               this.worldCal.actionLogic(this.velX, this.velY, this.totalVelocity);
            }

            this.footPointX = this.posX;
            this.footPointY = this.posY;
            this.faceDegree = this.worldCal.footDegree;
         }
      }

   }

   public void collisionLogicOnObject() {
      this.onObjectContinue = false;
      this.checkedObject = false;
      this.footObjectLogic = false;
      this.worldCal.actionState = 1;
      if (this.isInWater) {
         this.worldCal.actionLogic(this.velX >> 1, this.velY);
      } else {
         this.worldCal.actionLogic(this.velX, this.velY);
      }

      if (this.worldCal.actionState == 0) {
         this.onObjectContinue = false;
      } else if (!this.checkedObject && this.footOnObject != null && this.footOnObject.onObjectChk(this)) {
         this.footOnObject.doWhileCollisionWrap(this);
         this.onObjectContinue = true;
      }

      if (!this.onObjectContinue) {
         this.footOnObject = null;
         this.calTotalVelocity();
         if (this.collisionState == 2) {
            this.collisionState = 1;
            this.worldCal.actionState = 1;
         }
      } else if (this.collisionState == 2 && !this.piping) {
         this.velY = 0;
      }

   }

   public void dashRollingLogic() {
      this.animationID = 6;
      if (this.spinCount > 9) {
         this.animationID = 7;
      } else {
         if (Key.press(16777216)) {
            this.spinDownWaitCount = 0;
            this.spinCount = 12;
            this.animationID = 7;
            this.spinKeyCount = 20;
            this.drawer.restart();
            if (characterID != 3) {
               soundInstance.playSe(4);
            }
         } else if (Key.repeat(2097152 | Key.B_7 | Key.B_9) && this.spinKeyCount == 0) {
            this.spinCount = 12;
            this.animationID = 7;
            this.spinKeyCount = 20;
            this.drawer.restart();
            if (characterID != 3) {
               soundInstance.playSe(4);
            }
         }

         if (this.spinCount == 0 && this.spinKeyCount > 0) {
            --this.spinKeyCount;
         }
      }

      if (this.spinCount > 0) {
         if (this.spinDownWaitCount < 12) {
            ++this.spinDownWaitCount;
         } else {
            this.spinDownWaitCount = 12;
         }
      }

      if (this.spinCount > 0) {
         --this.spinCount;
         this.effectID = 1;
      } else {
         this.effectID = 0;
      }

      switch(this.collisionState) {
      case 0:
         this.totalVelocity = 0;
         break;
      default:
         this.velX = 0;
      }

      byte var1;
      if (!Key.repeat(Key.gDown | Key.B_7 | Key.B_9 | 2097152)) {
         this.effectID = -1;
         switch(this.collisionState) {
         case 0:
            this.totalVelocity = SPIN_START_SPEED_1;
            if (this.isInWater) {
               this.totalVelocity = SPIN_INWATER_START_SPEED_1;
            }

            if (this.spinCount > 0) {
               this.totalVelocity = this.spinLv2Calc();
               SoundSystem.getInstance().playSe(5);
            } else {
               SoundSystem.getInstance().playSe(5);
            }

            if (!this.faceDirection) {
               this.totalVelocity = -this.totalVelocity;
            }
            break;
         default:
            this.velX = SPIN_START_SPEED_1;
            if (this.isInWater) {
               this.totalVelocity = SPIN_INWATER_START_SPEED_1;
            }

            if (this.spinCount > 0) {
               this.velX = this.spinLv2Calc();
               SoundSystem.getInstance().playSe(5);
            } else {
               SoundSystem.getInstance().playSe(5);
            }

            if (!this.faceDirection) {
               if (this.isAntiGravity) {
                  var1 = 1;
               } else {
                  var1 = -1;
               }

               this.velX = var1 * this.velX;
            } else {
               if (this.isAntiGravity) {
                  var1 = -1;
               } else {
                  var1 = 1;
               }

               this.velX = var1 * this.velX;
            }
         }

         this.spinCount = 0;
         this.animationID = 4;
         this.dashRolling = false;
         this.ignoreFirstTouch = true;
         this.isAfterSpinDash = true;
      }

      switch(this.collisionState) {
      case 0:
         break;
      case 1:
      case 2:
      default:
         int var2 = this.velY;
         if (this.isAntiGravity) {
            var1 = -1;
         } else {
            var1 = 1;
         }

         this.velY = var2 + var1 * this.getGravity();
         break;
      case 3:
         this.velY = 100;
      }

   }

   public void dashRollingLogicCheck() {
      if (this.dashRolling) {
         this.dashRollingLogic();
      } else if (this.effectID == 0 || this.effectID == 1) {
         this.effectID = -1;
      }

   }

   public void didAfterEveryMove(int var1, int var2) {
      getPlayerObj().moveDistance.x = var1;
      getPlayerObj().moveDistance.y = var2;
      this.footPointX = this.posX;
      this.footPointY = this.posY;
      this.collisionCheckWithGameObject();
      this.posZ = this.currentLayer;
   }

   public void doAttackPose(GameObject var1, int var2) {
      if (!this.extraAttackFlag) {
         int var3;
         if (this.isAntiGravity) {
            var3 = 1;
         } else {
            var3 = -1;
         }

         int var4 = var3 * this.getVelY();
         int var5;
         if (var4 > 0) {
            var5 = -var4;
         } else {
            var5 = var4;
            if (var4 > -900) {
               var5 = -900;
            }
         }

         if (this.doJumpForwardly && !Key.repeat(16777216 | Key.gSelect | 8388608) || this.velY < 360) {
            var5 = -900;
         } else if (this.doJumpForwardly && Key.repeat(16777216 | Key.gSelect | 8388608)) {
            var5 = var4 + var3 * 200;
         }


         byte var6;
         if (characterID == 3) {
            if (!IsInvincibility() || this.myAnimationID < 14 || this.myAnimationID > 17) {
               if (this.isAntiGravity) {
                  var6 = -1;
               } else {
                  var6 = 1;
               }

               this.setVelY(var6 * var5);
            }
         } else {
            if (this.isAntiGravity) {
               var6 = -1;
            } else {
               var6 = 1;
            }

            this.setVelY(var6 * var5);
         }

         if (characterID != 3) {
            switch(var2) {
            case 1:
               this.cancelFootObject(this);
               this.collisionState = 1;
            }
         }
      }

   }

   public void doBeforeCollisionCheck() {
   }

   public void doBossAttackPose(GameObject var1, int var2) {
      if (this.collisionState == 1) {
         if (characterID != 3) {
            this.setVelX(-this.velX);
         }

         if (-this.velY < -ATTACK_POP_POWER) {
            this.setVelY(-ATTACK_POP_POWER);
         } else if (characterID != 2) {
            this.setVelY(-this.velY);
         } else if (this.getCharacterAnimationID() != 19 && this.getCharacterAnimationID() != 20 && this.getCharacterAnimationID() != 21 && this.getCharacterAnimationID() != 22) {
            this.setVelY(-this.velY);
         } else {
            this.setVelY(-this.velY - 325);
         }
      }

   }

   public boolean doBrake() {
      boolean var1;
      if (isTerminal && terminalType == 3 && terminalState == 1 && this.posX > 235136 && this.totalVelocity > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean doBreatheBubble() {
      boolean var1;
      if (this.collisionState == 1) {
         this.resetBreatheCount();
         this.animationID = 49;
         if (characterID == 1) {
            ((PlayerTails)player).flyCount = 0;
         }

         this.velX = 0;
         this.velY = 0;
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void doDripInAir() {
      if (this.collisionState == 1) {
         if (this.animationID != 4) {
            if (Math.abs(this.velX) < SPEED_LIMIT_LEVEL_1) {
               this.animationID = 1;
            } else if (Math.abs(this.velX) < SPEED_LIMIT_LEVEL_2) {
               this.animationID = 2;
            } else {
               this.animationID = 3;
            }
         } else {
            this.animationID = 4;
         }
      }

      this.bankwalking = false;
   }

   public void doHurt() {
      this.animationID = 44;
      if (this.collisionState == 2) {
         this.footPointY -= 128;
         this.prepareForCollision();
      }

      if (this.outOfControl && this.outOfControlObject != null && this.outOfControlObject.releaseWhileBeHurt()) {
         this.outOfControl = false;
         this.outOfControlObject = null;
      }

      this.hurtCount = 48;
      byte var1;
      if (this.velX == 0) {
         if (this.faceDirection) {
            var1 = -1;
         } else {
            var1 = 1;
         }

         this.velX = var1 * HURT_POWER_X;
      } else if (this.velX > 0) {
         this.velX = -HURT_POWER_X;
      } else {
         this.velX = HURT_POWER_X;
      }

      if (this.isAntiGravity) {
         this.velX = -this.velX;
      }

      if (this.isAntiGravity) {
         var1 = -1;
      } else {
         var1 = 1;
      }

      this.velY = var1 * HURT_POWER_Y;
      this.collisionState = 1;
      this.worldCal.actionState = 1;
      this.collisionChkBreak = true;
      this.worldCal.stopMove();
      this.onObjectContinue = false;
      this.footOnObject = null;
      this.hurtNoControl = true;
      this.attackAnimationID = 0;
      this.attackCount = 0;
      this.attackLevel = 0;
      this.dashRolling = false;
      MyAPI.vibrate();
      this.degreeRotateMode = 0;
   }

   public void doItemAttackPose(GameObject var1, int var2) {
      if (!this.extraAttackFlag) {
         short var3;
         if (this.isPowerShoot) {
            var3 = -1800;
         } else {
            var3 = -900;
         }

         int var4;
         if (this.isAntiGravity) {
            var4 = 1;
         } else {
            var4 = -1;
         }

         int var5 = var4 * this.getVelY();
         int var7;
         if (var5 > 0) {
            var7 = -var5;
         } else {
            var7 = var5;
            if (var5 > var3) {
               var7 = var3;
            }
         }

         if (this.doJumpForwardly && !Key.repeat(16777216 | Key.gSelect | 8388608) || this.velY < 360) {
            var7 = var3;
         } else if (this.doJumpForwardly && Key.repeat(16777216 | Key.gSelect | 8388608)) {
            var7 = var5 + var4 * 200;
         }

         if (characterID != 2 || this.myAnimationID < 19 || this.myAnimationID > 22) {
            byte var6;
            if (this.isAntiGravity) {
               var6 = -1;
            } else {
               var6 = 1;
            }

            this.setVelY(var6 * var7);
         }

         if (characterID != 3) {
            switch(var2) {
            case 1:
               this.cancelFootObject(this);
               this.collisionState = 1;
               this.animationID = 4;
            }
         }

         if (this.isPowerShoot) {
            this.isPowerShoot = false;
         }
      }

   }
   
   public boolean Jumped() {
   return animationID == 4;
   }

   public void doJump() {
      if (this.collisionState == 0) {
         this.calDivideVelocity();
      }

      this.collisionState = 1;
      this.worldCal.actionState = 1;
      int var2 = this.velY;
      int var1;
      if (this.isInWater) {
         var1 = JUMP_INWATER_START_VELOCITY;
      } else {
         var1 = JUMP_START_VELOCITY;
      }

      this.velY = var2 + var1 * MyAPI.dCos(this.faceDegreeChk()) / 100;
      var2 = this.velX;
      if (this.isInWater) {
         var1 = JUMP_INWATER_START_VELOCITY;
      } else {
         var1 = JUMP_START_VELOCITY;
      }

      this.velX = var2 + var1 * -MyAPI.dSin(this.faceDegreeChk()) / 100;
      if (this.faceDegree >= 0 && this.faceDegree <= 90) {
         if (this.isAntiGravity) {
            this.velY = Math.max(this.velY, -JUMP_PROTECT);
         } else {
            this.velY = Math.min(this.velY, JUMP_PROTECT);
         }
      }

      this.animationID = 4;
      soundInstance.playSe(11);
      this.smallJumpCount = 4;
      this.onBank = false;
      this.attackAnimationID = 0;
      this.attackCount = 0;
      this.attackLevel = 0;
      this.noVelMinus = false;
      this.doJumpForwardly = true;
      this.slipJumpOut();
      if (StageManager.getWaterLevel() > 0 && characterID == 2) {
         ((PlayerKnuckles)player).floatchk();
      }
      //if (MFMain.fatest) velY *= 2;
   }

   public void doJump(int var1) {
      if (this.collisionState == 0) {
         this.calDivideVelocity();
      }

      this.collisionState = 1;
      this.worldCal.actionState = 1;
      this.velY += MyAPI.dCos(this.faceDegreeChk()) * var1 / 100;
      this.velX += -MyAPI.dSin(this.faceDegreeChk()) * var1 / 100;
      if (this.isAntiGravity) {
         this.velY = Math.max(this.velY, -JUMP_PROTECT);
      } else {
         this.velY = Math.min(this.velY, JUMP_PROTECT);
      }

      this.animationID = 4;
      soundInstance.playSe(11);
      this.smallJumpCount = 4;
      this.onBank = false;
      this.attackAnimationID = 0;
      this.attackCount = 0;
      this.attackLevel = 0;
      this.noVelMinus = false;
      this.doJumpForwardly = true;
   }

   public void doJumpV() {
      if (this.collisionState == 0) {
         this.calDivideVelocity();
      }

      this.collisionState = 1;
      this.worldCal.actionState = 1;
      this.setVelX(0);
      int var1;
      if (this.isInWater) {
         var1 = JUMP_INWATER_START_VELOCITY;
      } else {
         var1 = JUMP_START_VELOCITY;
      }

      this.setVelY(var1);
      this.animationID = 4;
      soundInstance.playSe(11);
      this.smallJumpCount = 4;
      this.onBank = false;
      this.attackAnimationID = 0;
      this.attackCount = 0;
      this.attackLevel = 0;
      this.noVelMinus = false;
      this.doJumpForwardly = true;
      this.slipJumpOut();
   }

   public void doJumpV(int var1) {
      this.collisionState = 1;
      this.worldCal.actionState = 1;
      this.setVelY(var1);
      this.animationID = 4;
      soundInstance.playSe(11);
      this.smallJumpCount = 4;
      this.onBank = false;
      this.attackAnimationID = 0;
      this.attackCount = 0;
      this.attackLevel = 0;
      this.noVelMinus = false;
      this.doJumpForwardly = true;
   }

   public boolean doPoalMotion(int var1, int var2, boolean var3) {
      if (this.collisionState == 0) {
         this.collisionState = 1;
      }

      if (this.collisionState == 1) {
         this.animationID = 13;
         if (var3) {
            var3 = false;
         } else {
            var3 = true;
         }

         this.faceDirection = var3;
         this.footPointX = var1;
         this.footPointY = var2 + 2048;
         this.velX = 0;
         this.velY = 0;
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean doPoalMotion2(int var1, int var2, boolean var3) {
      if (this.collisionState == 0 && (this.faceDirection && var3 && this.totalVelocity >= 600 || !this.faceDirection && !var3 && this.totalVelocity <= -600)) {
         this.animationID = 31;
         this.faceDirection = var3;
         byte var5;
         if (this.faceDirection) {
            var5 = -1;
         } else {
            var5 = 1;
         }

         this.footPointX = var5 * 1024 + var1;
         this.setNoKey();
         byte var4;
         if (this.faceDirection) {
            var4 = -1;
         } else {
            var4 = 1;
         }

         this.totalVelocity = var4 * 300;
         this.worldCal.stopMoveX();
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public void doPullBarMotion(int var1) {
      this.animationID = 27;
      this.footPointY = var1 + 1792;
      this.velX = 0;
      this.velY = 0;
   }

   public void doPullMotion(int var1, int var2) {
      this.animationID = 24;
      this.footPointX = var1;
      this.footPointY = var2 + 2048;
      this.velX = 0;
      this.velY = 0;
      if (this.faceDirection) {
         this.footPointX -= 256;
      } else {
         this.footPointX += 256;
      }

   }

   public void doWalkPoseInAir() {
      if (this.collisionState == 1) {
         if (Math.abs(this.velX) < SPEED_LIMIT_LEVEL_1) {
            this.animationID = 1;
         } else if (Math.abs(this.velX) < SPEED_LIMIT_LEVEL_2) {
            this.animationID = 2;
         } else {
            this.animationID = 3;
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
    if (var1 != null) {
        var1.beHurt();
    }
}

   public void doWhileCollision(ACObject var1, ACCollision var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void doWhileLand(int var1) {
      this.faceDegree = var1;
      this.land();
      if (this.footOnObject != null) {
         this.worldCal.stopMove();
         this.footOnObject = null;
      }

      this.collisionState = 0;
      this.isSidePushed = 4;
      System.out.println("~~velx:" + (this.velX >> 6));
   }

   public void doWhileLeaveGround() {
      this.calDivideVelocity();
      this.collisionState = 1;
      if (isTerminal && terminalState >= 4) {
         this.collisionState = 4;
      }

   }

   public void doWhileTouchWorld(int var1, int var2) {
      if (this.worldCal.getActionState() == 1) {
         switch(var1) {
         case 0:
            if (this.collisionState == 2 && this.movedSpeedY < 0) {
               this.setDie(false);
            }
            break;
         case 1:
            if (this.isAntiGravity) {
               this.leftStopped = true;
            } else {
               this.rightStopped = true;
            }

            if (this.leftStopped && this.rightStopped) {
               this.setDie(false);
               return;
            }
         case 2:
         default:
            break;
         case 3:
            if (this.isAntiGravity) {
               this.rightStopped = true;
            } else {
               this.leftStopped = true;
            }

            if (this.leftStopped && this.rightStopped) {
               this.setDie(false);
               return;
            }
         }
      }

      if (this.worldCal.getActionState() == 0 || this.collisionState == 2) {
         switch(var1) {
         case 0:
            if (this.collisionState == 2 && this.movedSpeedY < 0) {
               this.setDie(false);
            }
            break;
         case 1:
            if (!this.speedLock) {
               this.totalVelocity = 0;
            }

            if (this.isAntiGravity) {
               this.leftStopped = true;
            } else {
               this.rightStopped = true;
            }

            if (this.leftStopped && this.rightStopped) {
               this.setDie(false);
            } else if ((Key.repeat(Key.gRight) && !this.isAntiGravity || Key.repeat(Key.gLeft) && this.isAntiGravity) && (this.animationID == 0 || this.animationID == 47 || this.animationID == 48 || this.animationID == 1 || this.animationID == 2 || this.animationID == 3)) {
               this.animationID = 8;
            }
         case 2:
         default:
            break;
         case 3:
            if (!this.speedLock) {
               this.totalVelocity = 0;
            }

            if (this.isAntiGravity) {
               this.rightStopped = true;
            } else {
               this.leftStopped = true;
            }

            if (this.leftStopped && this.rightStopped) {
               this.setDie(false);
            } else if ((Key.repeat(Key.gLeft) && !this.isAntiGravity || Key.repeat(Key.gRight) && this.isAntiGravity) && (this.animationID == 0 || this.animationID == 47 || this.animationID == 48 || this.animationID == 1 || this.animationID == 2 || this.animationID == 3)) {
               this.animationID = 8;
            }
         }
      }

   }

   public void draw(MFGraphics var1) {
      boolean var2;
      if (!this.drawAtFront() && this.visible) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.draw(var1, var2);
   }

   public void draw(MFGraphics var1, boolean var2) {
      if (var2) {
         switch(this.collisionState) {
         case 0:
            if (this.noRotateDraw()) {
               this.degreeForDraw = this.degreeStable;
            }
         default:
            if (this.isInWater) {
               this.drawer.setSpeed(1, 2);
            } else {
               this.drawer.setSpeed(1, 1);
            }

            if (this.animationID == 1) {
               if (this.isInSnow) {
                  this.drawer.setSpeed(1, 2);
               } else {
                  this.drawer.setSpeed(1, 1);
               }
            }

            this.drawCharacter(var1);
            if (characterID == 3) {
               if (this.animationID == 4 && !IsGamePause) {
                  if (this.ducting) {
                     if (this.ductingCount % 2 == 0) {
                        soundInstance.stopLoopSe();
                        soundInstance.playLoopSe(25);
                     }
                  } else {
                     soundInstance.playLoopSe(25);
                  }
               }

               if ((this.animationID != 4 || IsGamePause) && soundInstance.getPlayingLoopSeIndex() == 25) {
                  soundInstance.stopLoopSe();
               }
            }

            if (this.effectID > -1) {
               AnimationDrawer var9 = this.effectDrawer;
               int var3 = this.effectID;
               int var8 = this.footPointX;
               int var5 = camera.x;
               int var4 = this.footPointY;
               int var7 = camera.y;
               var2 = EFFECT_LOOP[this.effectID];
               int var6 = this.getTrans();
               var9.draw(var1, var3, (var8 >> 6) - var5, (var4 >> 6) - var7, var2, var6);
               if (this.effectDrawer.checkEnd()) {
                  this.effectDrawer.restart();
                  this.effectID = -1;
               }
            }

            this.waterFallDraw(var1, camera);
            this.waterFlushDraw(var1);
            if (this.drawer.checkEnd()) {
               switch(this.animationID) {
               case 9:
                  if (this.isInGravityCircle) {
                     this.animationID = 9;
                     this.drawer.restart();
                  } else {
                     this.animationID = 10;
                  }
                  break;
               case 15:
                  this.animationID = 16;
                  break;
               case 22:
                  this.animationID = 23;
                  break;
               case 23:
                  this.animationID = 22;
                  break;
               case 25:
                  this.animationID = 26;
                  break;
               case 26:
                  this.animationID = 25;
                  break;
               case 31:
                  this.animationID = 1;
                  break;
               case 35:
               case 37:
                  StageManager.setStagePass();
                  break;
               case 42:
                  this.animationID = 43;
                  break;
               case 43:
                  this.animationID = 10;
                  break;
               case 44:
                  this.animationID = 12;
                  break;
               case 45:
                  this.animationID = 41;
                  break;
               case 46:
                  if (Key.repeat(Key.gDown)) {
                     this.animationID = 5;
                  } else {
                     this.animationID = 0;
                  }
                  break;
               case 49:
                  this.animationID = 1;
                  break;
               case 53:
                  this.animationID = 0;
               }
            }
         }
      }

   }

   public void draw2(MFGraphics var1) {
      boolean var6;
      if (this.drawAtFront() && this.visible) {
         var6 = true;
      } else {
         var6 = false;
      }

      this.draw(var1, var6);
      this.drawCollisionRect(var1);
      int var2;
      int var3;
      int var4;
      int var5;
      AnimationDrawer var7;
      if (this.waterSprayFlag && StageManager.getCurrentZoneId() == 4 && waterSprayDrawer != null) {
         var7 = waterSprayDrawer;
         var5 = this.waterSprayX;
         var2 = camera.x;
         var3 = StageManager.getWaterLevel();
         var4 = camera.y;
         var7.draw(var1, 0, (var5 >> 6) - var2, var3 - var4, false, 0);
         if (waterSprayDrawer.checkEnd()) {
            this.waterSprayFlag = false;
            waterSprayDrawer.restart();
         }
      }

      if (!IsGamePause) {
         if (this.isDead) {
            var3 = this.velY;
            byte var8;
            if (this.isAntiGravity) {
               var8 = -1;
            } else {
               var8 = 1;
            }

            this.velY = var3 + var8 * this.getGravity();
            this.footPointX += this.velX;
            this.footPointY += this.velY;
         }

         if (this.isInWater && this.breatheNumCount >= 0 && this.breatheNumCount < 6) {
            MFImage var9 = breatheCountImage;
            var4 = this.breatheNumCount;
            var5 = this.posX;
            var3 = camera.x;
            if (this.breatheNumY > 16) {
               var2 = this.breatheNumY;
            } else {
               var2 = 16;
            }

            MyAPI.drawRegion(var1, var9, var4 * 16, 0, 16, 16, 0, (var5 >> 6) - var3, var2, 33);
            --this.breatheNumY;
         }
      }

      if (this.fading) {
         drawFadeBase(var1, 12);
      }

      if (terminalType == 3) {
         if (terminalState >= 2 && terminalState < 6) {
            var7 = moonStarDrawer;
            var3 = MOON_STAR_ORI_X_1;
            var2 = (MOON_STAR_DES_X_1 - MOON_STAR_ORI_X_1) * this.moonStarFrame1 / 207;
            var4 = this.moonStarFrame1 * 8 / 207;
            var7.draw(var1, 0, var2 + var3, var4 + 18, true, 0);
            ++this.moonStarFrame1;
         } else {
            this.moonStarFrame1 = 0;
         }

         if (terminalState == 7) {
            var7 = moonStarDrawer;
            var4 = MOON_STAR_ORI_X_1;
            var2 = (MOON_STAR_DES_X_1 - MOON_STAR_ORI_X_1) * this.moonStarFrame2 / 120;
            var3 = this.moonStarFrame2 * 8 / 120;
            var7.draw(var1, 1, var2 + var4, var3 + 18, true, 0);
            ++this.moonStarFrame2;
         } else {
            this.moonStarFrame2 = 0;
         }
      }

   }

   public boolean drawAtFront() {
      boolean var1;
      if (!this.slipping && !this.isDead) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void drawCharacter(MFGraphics var1) {
   }

   public void drawDrawerByDegree(MFGraphics var1, AnimationDrawer var2, int var3, int var4, int var5, boolean var6, int var7, boolean var8) {
      var1.saveCanvas();
      var1.translateCanvas(var4, var5);
      var1.rotateCanvas((float)var7);
      byte var9;
      if (!var8) {
         var9 = 0;
      } else {
         var9 = 2;
      }

      var2.draw(var1, var3, 0, 0, var6, var9);
      var1.restoreCanvas();
   }

   public void drawSheild1(MFGraphics var1) {
      if (!this.drawAtFront()) {
         this.drawSheildPrivate(var1);
      }

   }

   public void drawSheild2(MFGraphics var1) {
      if (this.drawAtFront()) {
         this.drawSheildPrivate(var1);
      }

   }

   public void dripDownUnderWater() {
   }

   public void ductIn() {
      this.ducting = true;
      this.pushOnce = true;
      this.ductingCount = 0;
   }

   public void ductOut() {
      this.ducting = false;
      this.pushOnce = false;
      this.ductingCount = 0;
   }

   protected void extraInputLogic() {
   }

   protected void extraLogicJump() {
   }

   protected void extraLogicOnObject() {
   }

   protected void extraLogicWalk() {
   }

   public void fallChk() {
      if (this.fallTime > 0) {
         --this.fallTime;
         if (this.animationID == 0) {
            this.animationID = 1;
         }
      } else if ((!this.isAntiGravity && this.faceDegree >= 45 && this.faceDegree <= 315 || this.isAntiGravity && (this.faceDegree <= 135 || this.faceDegree >= 225)) && Math.abs(this.totalVelocity) < 474) {
         if (this.totalVelocity == 0) {
            this.calDivideVelocity();
            this.velY += this.getGravity();
            this.calTotalVelocity();
         }

         this.fallTime = 7;
      }

   }

   public int getAnimationId() {
      return this.animationID;
   }

   protected int getAnimationOffset() {
      return this.getAnimationOffset(this.faceDegree);
   }

   protected int getAnimationOffset(int var1) {
      int var2 = 0;

      while(true) {
         if (var2 >= DEGREE_DIVIDE.length) {
            var1 = 0;
            break;
         }

         if (var1 < DEGREE_DIVIDE[var2]) {
            var1 = var2 % 2;
            break;
         }

         ++var2;
      }

      return var1;
   }

   public void getBallHobinScore() {
      scoreNum += 10;
      raceScoreNum += 10;
   }

   public int getBodyDegree() {
      return this.worldCal.footDegree;
   }

   public int getBodyOffset() {
      return 768;
   }

   public int getBodyPositionX() {
      return this.getFootPositionX();
   }

   public int getBodyPositionY() {
      int var2 = this.getFootPositionY();
      short var1;
      if (this.isAntiGravity) {
         var1 = 768;
      } else {
         var1 = -768;
      }

      return var2 + var1;
   }

   public void getBossScore() {
      scoreNum += 1000;
      raceScoreNum += 1000;
   }

   public ACWorldCollisionCalculator getCal() {
      return this.worldCal;
   }

   public int getCharacterAnimationID() {
      return this.myAnimationID;
   }

   public int getCheckPositionX() {
      return this.collisionRect.x0 + this.collisionRect.x1 >> 1;
   }

   public int getCheckPositionY() {
      return this.collisionRect.y0 + this.collisionRect.y1 >> 1;
   }

   public int getCollisionRectHeight() {
      short var1;
      if (this.animationID != 4 && this.animationID != 5 && this.animationID != 46 && this.animationID != 6 && this.animationID != 7 && this.animationID != 18 && this.animationID != 19 && this.animationID != 20) {
         var1 = 1536;
      } else {
         var1 = 1152;
      }

      return var1;
   }

   public int getCollisionRectWidth() {
      short var1;
      if (this.animationID == 21) {
         var1 = 1536;
      } else {
         var1 = 1024;
      }

      return var1;
   }

   public int getCurrentHeight() {
      return this.getCollisionRectHeight();
   }

   public int getDegreeDiff(int var1, int var2) {
      var2 = Math.abs(var1 - var2);
      var1 = var2;
      if (var2 > 180) {
         var1 = 360 - var2;
      }

      var2 = var1;
      if (var1 > 90) {
         var2 = 180 - var1;
      }

      return var2;
   }

   public void getEnemyScore() {
      scoreNum += 100;
      raceScoreNum += 100;
   }

   public int getFocusX() {
      return this.getNewPointX(this.footPointX, 0, -768, this.faceDegree) >> 6;
   }

   public int getFocusY() {
      if (FOCUS_MAX_OFFSET > 10) {
         if (this.focusMovingState == 0) {
            this.lookCount = 32;
         }

         if (this.lookCount == 0) {
            switch(this.focusMovingState) {
            case 1:
               if (this.focusOffsetY < FOCUS_MAX_OFFSET) {
                  this.focusOffsetY += 15;
                  if (this.focusOffsetY > FOCUS_MAX_OFFSET) {
                     this.focusOffsetY = FOCUS_MAX_OFFSET;
                  }
               }
               break;
            case 2:
               if (this.focusOffsetY > -FOCUS_MAX_OFFSET) {
                  this.focusOffsetY -= 15;
                  if (this.focusOffsetY < -FOCUS_MAX_OFFSET) {
                     this.focusOffsetY = -FOCUS_MAX_OFFSET;
                  }
               }
            }
         } else {
            --this.lookCount;
            if (this.focusOffsetY > 0) {
               this.focusOffsetY -= 15;
               if (this.focusOffsetY < 0) {
                  this.focusOffsetY = 0;
               }
            }

            if (this.focusOffsetY < 0) {
               this.focusOffsetY += 15;
               if (this.focusOffsetY > 0) {
                  this.focusOffsetY = 0;
               }
            }
         }
      }

      int var2 = this.getNewPointY(this.footPointY, 0, -768, this.faceDegree);
      byte var1;
      if (this.isAntiGravity) {
         var1 = 1;
      } else {
         var1 = -1;
      }

      int var3 = this.focusOffsetY;
      return (var2 >> 6) + var1 * var3;
   }

   public int getFootOffset() {
      return 256;
   }

   public int getFootPositionX() {
      return this.footPointX;
   }

   public int getFootPositionY() {
      return this.footPointY;
   }

   public int getFootX() {
      return this.posX;
   }

   public int getFootY() {
      return this.posY;
   }

   public int getGravity() {
      int var1;
      if (this.isInWater) {
         var1 = GRAVITY * 3 / 5;
      } else {
         var1 = GRAVITY;
      }

      return var1;
   }

   public int getHeadPositionY() {
      return this.getNewPointY(this.footPointY, 0, -1536, this.faceDegree);
   }

   public void getItem(int var1) {
      switch(var1) {
      case 0:
         addLife();
         playerLifeUpBGM();
         break;
      case 1:
         shieldType = 1;
         soundInstance.playSe(41);
         break;
      case 2:
         shieldType = 2;
         soundInstance.playSe(41);
         break;
      case 3:
         invincibleCount = 320;
         SoundSystem.getInstance().stopBgm(false);
         SoundSystem.getInstance().playBgm(44);
         break;
      case 4:
         speedCount = 320;
         SoundSystem.getInstance().setSoundSpeed(2.0F);
         if (SoundSystem.getInstance().getPlayingBGMIndex() != 43) {
            SoundSystem.getInstance().restartBgm();
         }
         break;
      case 5:
         if (this.hurtCount == 0) {
            getRing(ringRandomNum);
         }
         break;
      case 6:
         if (this.hurtCount == 0) {
            getRing(5);
         }
         break;
      case 7:
         if (this.hurtCount == 0) {
            getRing(10);
         }
      }

   }

   public int getMinDegreeToLeaveGround() {
      return 45;
   }

   protected int getNewPointX(int var1, int var2, int var3, int var4) {
      var2 = MyAPI.dCos(var4) * var2 / 100;
      var3 = MyAPI.dSin(var4) * var3 / 100;
      return var2 + var1 - var3;
   }

   protected int getNewPointY(int var1, int var2, int var3, int var4) {
      var2 = MyAPI.dSin(var4) * var2 / 100;
      var3 = MyAPI.dCos(var4) * var3 / 100;
      return var2 + var1 + var3;
   }

   public int getObjHeight() {
      int var1;
      if (this.needChangeRect()) {
         var1 = this.collisionRect.getHeight();
      } else {
         var1 = 1536;
      }

      return var1;
   }

   public void getPreItem(int var1) {
      for(int var2 = 0; var2 < 5; ++var2) {
         if (itemVec[var2][0] == -1) {
            itemVec[var2][0] = var1;
            itemVec[var2][1] = 20;
            break;
         }
      }

   }

   public int getPressToGround() {
      return GRAVITY << 1;
   }

   public int getRetPower() {
      int var1;
      if (this.animationID != 4) {
         var1 = this.movePower;
      } else {
         var1 = this.movePower >> 1;
      }

      return var1;
   }

   public int getSlopeGravity() {
      int var1;
      if (this.animationID != 4) {
         var1 = FAKE_GRAVITY_ON_WALK;
      } else {
         var1 = FAKE_GRAVITY_ON_BALL;
      }

      return var1;
   }

   protected int getTrans() {
      return this.getTrans(this.faceDegree);
   }

   protected int getTrans(int var1) {
      int var2 = TRANS[this.getTransId(var1)];
      var1 = this.getAnimationOffset(var1);
      if (!this.faceDirection) {
         if (var1 == 0) {
            var1 = var2;
            switch(var2) {
            case 0:
            case 3:
            case 5:
            case 6:
               var1 = var2 ^ 2;
            case 1:
            case 2:
            case 4:
               break;
            default:
               var1 = var2;
            }
         } else {
            switch(var2) {
            case 0:
               var1 = 4;
               break;
            case 1:
            case 2:
            case 4:
            default:
               var1 = var2;
               break;
            case 3:
               var1 = 7;
               break;
            case 5:
               var1 = 2;
               break;
            case 6:
               var1 = 1;
            }
         }

         var2 = var1;
      }

      return var2;
   }

   protected int getTransId(int var1) {
      int var2;
      for(var2 = 0; var2 < DEGREE_DIVIDE.length; ++var2) {
         if (var1 < DEGREE_DIVIDE[var2]) {
            var2 %= 8;
            break;
         }
      }

      return (var2 + 1) / 2 % 4;
   }

   public int getVelX() {
      int var1;
      if (this.collisionState == 0) {
         var1 = this.totalVelocity * MyAPI.dCos(this.faceDegree) / 100;
      } else {
         var1 = this.velX;
      }

      return var1;
   }

   public int getVelY() {
      int var1;
      if (this.collisionState == 0) {
         var1 = this.totalVelocity * MyAPI.dSin(this.faceDegree) / 100;
      } else {
         var1 = this.velY;
      }

      return var1;
   }

   public boolean getWaterFallState() {
      return this.waterFalling;
   }

   public void headInit() {
      if (GameState.guiAnimation == null) {
         StringBuilder var1 = new StringBuilder("/lang");
         var1.append(GlobalResource.languageConfig);
         var1.append("/gui");
         GameState.guiAnimation = new Animation(var1.toString());
      }

      headDrawer = GameState.guiAnimation.getDrawer(characterID, false, 0);
      this.isAttackBoss4 = false;
   }
   
   public void headInit(int i) {
      if (GameState.guiAnimation == null) {
         StringBuilder var1 = new StringBuilder("/lang");
         var1.append(GlobalResource.languageConfig);
         var1.append("/gui");
         GameState.guiAnimation = new Animation(var1.toString());
      }

      headDrawer = GameState.guiAnimation.getDrawer(i, false, 0);
      //this.isAttackBoss4 = false;
   }

   public boolean inRailState() {
      boolean var1;
      if (!this.railing && !this.railOut) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void initWaterFall() {
      if (this.waterFallDrawer == null) {
         MFImage var1 = null;
         if (StageManager.getCurrentZoneId() == 5) {
            var1 = MFImage.createImage("/animation/water_fall_5.png");
         }

         Animation var2;
         AnimationDrawer var3;
         if (var1 == null) {
            var2 = new Animation("/animation/water_fall");
            var3 = var2.getDrawer(0, true, 0);
            this.waterFallDrawer = var3;
         } else {
            var2 = new Animation(var1, "/animation/water_fall");
            var3 = var2.getDrawer(0, true, 0);
            this.waterFallDrawer = var3;
         }
      }

   }

   public void initWaterFlush() {
      if (this.waterFlushDrawer == null) {
         MFImage var1 = null;
         if (StageManager.getCurrentZoneId() == 5) {
            var1 = MFImage.createImage("/animation/water_flush_5.png");
         }

         Animation var2;
         AnimationDrawer var3;
         if (var1 == null) {
            var2 = new Animation("/animation/water_flush");
            var3 = var2.getDrawer(0, true, 0);
            this.waterFlushDrawer = var3;
         } else {
            var2 = new Animation(var1, "/animation/water_flush");
            var3 = var2.getDrawer(0, true, 0);
            this.waterFlushDrawer = var3;
         }
      }

   }

   public boolean isAttackingEnemy() {
      boolean var1;
      if (this instanceof PlayerAmy && this.getCharacterAnimationID() == 39) {
         var1 = false;
      } else if (this instanceof PlayerAmy && (this.getCharacterAnimationID() == 18 || this.getCharacterAnimationID() == 19 || this.getCharacterAnimationID() == 20 || this.getCharacterAnimationID() == 21 || this.getCharacterAnimationID() == 22 || this.getCharacterAnimationID() == 7)) {
         var1 = true;
      } else if (!(this instanceof PlayerSonic) || this.getCharacterAnimationID() != 13 && this.getCharacterAnimationID() != 14 && this.getCharacterAnimationID() != 15 && this.getCharacterAnimationID() != 4) {
         if (this instanceof PlayerTails && this.getCharacterAnimationID() == 11) {
            var1 = true;
         } else if (this instanceof PlayerKnuckles && (this.getCharacterAnimationID() == 11 || this.getCharacterAnimationID() == 12 || this.getCharacterAnimationID() == 13 || this.getCharacterAnimationID() == 19 || this.getCharacterAnimationID() == 20 || this.getCharacterAnimationID() == 21 || this.getCharacterAnimationID() == 22)) {
            var1 = true;
         } else if (this.animationID != 18 && this.animationID != 19 && this.animationID != 20 && this.animationID != 4 && this.animationID != 6 && this.animationID != 7 && invincibleCount <= 0) {
            var1 = false;
         } else {
            var1 = true;
         }
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isAttackingItem() {
      boolean var1;
      if (this instanceof PlayerAmy && (this.getCharacterAnimationID() == 21 || this.getCharacterAnimationID() == 22)) {
         getPlayerObj().setVelY(getPlayerObj().getVelY() - 325);
         var1 = true;
      } else if (this instanceof PlayerAmy && this.getCharacterAnimationID() == 39) {
         var1 = false;
      } else if (this instanceof PlayerAmy && this.getCharacterAnimationID() == 17) {
         var1 = false;
      } else if (this.animationID != 18 && this.animationID != 19 && this.animationID != 20 && this.animationID != 4) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean isAttackingItem(boolean var1) {
      if (!this.ignoreFirstTouch && !var1) {
         var1 = false;
      } else {
         var1 = this.isAttackingItem();
      }

      return var1;
   }

   public boolean isAttracting() {
      boolean var1;
      if (shieldType == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isBodyCenterOutOfWater() {
      int var1 = this.posY;
      int var2 = -this.collisionRect.getHeight();
      int var3 = this.faceDegree;
      var1 = this.getNewPointY(var1, 0, var2, var3);
      boolean var4;
      if (var1 < StageManager.getWaterLevel() << 6) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public boolean isControlObject(GameObject var1) {
      boolean var2;
      if (this.controlObjectLogic && var1 == this.outOfControlObject) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isFootObjectAndLogic(GameObject var1) {
      boolean var2;
      if (this.footObjectLogic && this.footOnObject == var1 && this.collisionState == 2) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isFootOnObject(GameObject var1) {
      boolean var2;
      if (this.outOfControl) {
         var2 = false;
      } else if (this.collisionState != 2) {
         var2 = false;
      } else if (this.footOnObject == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   protected boolean isHeadCollision() {
      boolean var3 = false;
      int var2 = this.worldInstance.getWorldY(this.footPointX, this.footPointY - 1536, 1, 2);
      int var1 = this.worldInstance.getWorldY(this.footPointX + 1024, this.footPointY - 1536, 1, 2);
      if (var2 >= 0) {
         var3 = true;
      }

      if (var1 >= 0) {
         var3 = true;
      }

      return var3;
   }

   public boolean isOnGound() {
      boolean var1;
      if (this.collisionState == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isOnSlip0() {
      return false;
   }

   public boolean isTerminalRunRight() {
      boolean var1;
      if (!isTerminal || terminalType != 0 && terminalType != 2 && (terminalType != 3 || terminalState != 0 || this.posX >= 235136)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void logic() {
        for (int i = 0; i < 5; ++i) {
            if (PlayerObject.itemVec[i][0] >= 0) {
                if (PlayerObject.itemVec[i][1] > 0) {
                    final int[] array = PlayerObject.itemVec[i];
                    --array[1];
                }
                if (PlayerObject.itemVec[i][1] == 0) {
                    this.getItem(PlayerObject.itemVec[i][0]);
                    PlayerObject.itemVec[i][0] = -1;
                }
            }
        }
        int degreeStable;
        if (this.isAntiGravity) {
            degreeStable = 180;
        }
        else {
            degreeStable = 0;
        }
        this.degreeStable = degreeStable;
        this.leftStopped = false;
        this.rightStopped = false;
        if (this.enteringSP && this.posY >> 6 < PlayerObject.camera.y) {
            GameState.enterSpStage(PlayerObject.ringNum, PlayerObject.currentMarkId, PlayerObject.timeCount);
            this.enteringSP = false;
        }
        if (this.hurtCount > 0) {
            --this.hurtCount;
        }
        if (PlayerObject.invincibleCount > 0) {
            --PlayerObject.invincibleCount;
            if (PlayerObject.invincibleCount == 0) {
                final int playingBGMIndex = SoundSystem.getInstance().getPlayingBGMIndex();
                SoundSystem.getInstance();
                if (playingBGMIndex == 44) {
                    SoundSystem.getInstance().stopBgm(false);
                    if (!PlayerObject.isTerminal) {
                        SoundSystem.getInstance().playBgm(StageManager.getBgmId());
                    }
                }
                final int playingBGMIndex2 = SoundSystem.getInstance().getPlayingBGMIndex();
                SoundSystem.getInstance();
                if (playingBGMIndex2 == 43) {
                    SoundSystem.getInstance().playNextBgm(StageManager.getBgmId());
                }
            }
        }
        this.preFocusX = this.getNewPointX(this.footPointX, 0, -768, this.faceDegree) >> 6;
        this.preFocusY = this.getNewPointY(this.footPointY, 0, -768, this.faceDegree) >> 6;
        Label_0361: {
            if (!this.setNoMoving) {
                break Label_0361;
            }
            if (this.collisionState == 0) {
                this.footPointX = this.noMovingPosition;
                this.setVelX(0);
                this.setVelY(0);
                this.animationID = 0;
            }
            else {
                if (this.collisionState == 1) {
                    this.footPointX = this.noMovingPosition;
                    this.velX = 0;
                    this.setNoKey();
                }
                break Label_0361;
            }
            return;
        }
        if (this.collisionState == 0) {
            this.deadPosX = this.footPointX;
            this.deadPosY = this.footPointY;
        }
        if (PlayerObject.characterID == 1) {
            if (this.myAnimationID != 12 && this.myAnimationID != 48 && this.myAnimationID != 49) {
                if (PlayerObject.soundInstance.getPlayingLoopSeIndex() == 15) {
                    PlayerObject.soundInstance.stopLoopSe();
                }
                this.resetFlyCount();
            }
            if (this.collisionState == 0) {
                if (PlayerObject.soundInstance.getPlayingLoopSeIndex() == 15) {
                    PlayerObject.soundInstance.stopLoopSe();
                }
                this.resetFlyCount();
            }
        }
        if (this.isDead) {
            if (this.isInWater && this.breatheNumCount >= 6) {
                ++this.drownCnt;
                if (this.drownCnt % 2 == 0) {
                    addGameObject((GameObject)new DrownBubble(41, this.footPointX, this.footPointY - 1536, 0, 0, 0, 0));
                }
            }
            final int n = 0;
            int n2;
            if (!this.isAntiGravity) {
                n2 = n;
                if (this.velY > 0) {
                    n2 = n;
                    if (this.footPointY > (MapManager.getCamera().y + MapManager.CAMERA_HEIGHT << 6) + 3072) {
                        this.footPointY = (MapManager.getCamera().y + MapManager.CAMERA_HEIGHT << 6) + 3072;
                        n2 = 1;
                    }
                }
            }
            else {
                n2 = n;
                if (this.footPointY < (MapManager.getCamera().y << 6) - 3072) {
                    this.footPointY = (MapManager.getCamera().y << 6) - 3072;
                    n2 = 1;
                }
            }
            if (n2 != 0 && !this.finishDeadStuff) {
                if (PlayerObject.stageModeState == 1) {
                    if (!ChargePlatform.isChargedByIndex(0)) {
                        StageManager.setStageGameover();
                    }
                    else {
                        StageManager.setStageRestart();
                    }
                }
                else if (PlayerObject.timeCount != PlayerObject.overTime || !GlobalResource.timeIsLimit()) {
                    if (PlayerObject.lifeNum > 0) {
                        --PlayerObject.lifeNum;
                        StageManager.setStageRestart();
                    }
                    else {
                        StageManager.setStageGameover();
                    }
                }
                this.finishDeadStuff = true;
            }
        }
        else {
            this.focusMovingState = 0;
            this.controlObjectLogic = false;
            if (this.outOfControl) {
                if (this.outOfControlObject != null) {
                    this.outOfControlObject.logic();
                    this.controlObjectLogic = true;
                }
            }
            else {
                final int waterLevel = StageManager.getWaterLevel();
                if (waterLevel > 0) {
                    if (PlayerObject.characterID == 2) {
                        ((PlayerKnuckles)PlayerObject.player).setPreWaterFlag(this.isInWater);
                    }
                    if (this.isInWater) {
                        if (!PlayerObject.IsGamePause) {
                            this.breatheCount += 63;
                            this.breatheNumCount = -1;
                            if (PlayerObject.characterID == 2 && this.collisionState == 4 && this.getNewPointY(this.posY, 0, -this.collisionRect.getHeight(), this.faceDegree) + 256 < waterLevel << 6) {
                                this.breatheCount = 0;
                                final int playingBGMIndex3 = SoundSystem.getInstance().getPlayingBGMIndex();
                                SoundSystem.getInstance();
                                if (playingBGMIndex3 == 21) {
                                    SoundSystem.getInstance().stopBgm(false);
                                    final PlayerObject player = PlayerObject.player;
                                    if (IsInvincibility()) {
                                        SoundSystem.getInstance().playBgm(44);
                                    }
                                    else if (this.isAttackBoss4) {
                                        SoundSystem.getInstance().playBgm(22);
                                    }
                                    else {
                                        SoundSystem.getInstance().playBgm(StageManager.getBgmId());
                                    }
                                }
                            }
                            Label_1167: {
                                if (this.breatheCount > BREATHE_TIME_COUNT) {
                        this.breatheNumCount = (this.breatheCount - 21000) / BREATHE_TO_DIE_PER_COUNT;
                        if (this.breatheCount == 0) {
                            int playingBGMIndex4 = SoundSystem.getInstance().getPlayingBGMIndex();
                            SoundSystem.getInstance();
                            if (playingBGMIndex4 != 21) {
                                if (this.isAttackBoss4) {
                                    soundInstance.playBgm(21);
                                } else {
                                    soundInstance.playBgm(21);
                                }
                                if (this.breatheNumCount < 6 && canBeHurt()) {
                                    setDie(true);
                                    return;
                                } else if (this.breatheNumCount != this.preBreatheNumCount) {
                                    this.breatheNumY = ((this.posY >> 6) - camera.y) - 30;
                                }
                            }
                        }
                        int playingBGMIndex5 = SoundSystem.getInstance().getPlayingBGMIndex();
                        SoundSystem.getInstance();
                        if (playingBGMIndex5 != 21) {
                            long startTime = ((this.breatheCount - BREATHE_TIME_COUNT) * 10000) / 10560;
                            if (this.isAttackBoss4) {
                                soundInstance.playBgmFromTime(startTime, 21);
                            } else {
                                soundInstance.playBgmFromTime(startTime, 21);
                            }
                        }
                        if (this.breatheNumCount >= 6 && canBeHurt()) {
                            setDie(true);
                            return;
                        } else if (this.breatheNumCount != this.preBreatheNumCount) {
                            this.breatheNumY = ((this.posY >> 6) - camera.y) - 30;
                        }
                    }
                                /*if (this.breatheCount > 21000) {
                                    this.breatheNumCount = (this.breatheCount - 21000) / 1760;
                                    while (true) {
                                        Label_1066: {
                                            if (this.breatheCount != 0) {
                                                break Label_1066;
                                            }
                                            final int playingBGMIndex4 = SoundSystem.getInstance().getPlayingBGMIndex();
                                            SoundSystem.getInstance();
                                            if (playingBGMIndex4 == 21) {
                                                break Label_1066;
                                            }
                                            if (this.isAttackBoss4) {
                                                PlayerObject.soundInstance.playBgm(21);
                                            }
                                            else {
                                                PlayerObject.soundInstance.playBgm(21);
                                            }
                                            if (this.breatheNumCount >= 6 && this.canBeHurt()) {
                                                this.setDie(true);
                                                return;
                                            }
                                            if (this.breatheNumCount != this.preBreatheNumCount) {
                                                this.breatheNumY = (this.posY >> 6) - PlayerObject.camera.y - 30;
                                            }
                                            break Label_1167;
                                        }
                                        final int playingBGMIndex5 = SoundSystem.getInstance().getPlayingBGMIndex();
                                        SoundSystem.getInstance();
                                        if (playingBGMIndex5 == 21) {
                                            continue;
                                        }
                                        final long n3 = (this.breatheCount - 21000) * 10000 / 10560;
                                        if (this.isAttackBoss4) {
                                            PlayerObject.soundInstance.playBgmFromTime(n3, 21);
                                            continue;
                                        }
                                        PlayerObject.soundInstance.playBgmFromTime(n3, 21);
                                        continue;
                                    }
                                }*/
                            }
                            this.preBreatheNumCount = this.breatheNumCount;
                            int n4 = this.getNewPointY(this.posY, 0, -this.collisionRect.getHeight() >> 1, this.faceDegree);
                            if (PlayerObject.characterID == 3) {
                                n4 = this.getNewPointY(this.posY, 0, -this.collisionRect.getHeight() * 3 / 4 - 128, this.faceDegree);
                            }
                            if (n4 + 256 <= waterLevel << 6) {
                                this.isInWater = false;
                                if (this.breatheNumCount >= 0 && SoundSystem.getInstance().getPlayingBGMIndex() == 21) {
                                    SoundSystem.getInstance().stopBgm(false);
                                    final PlayerObject player2 = PlayerObject.player;
                                    if (IsInvincibility()) {
                                        SoundSystem.getInstance().playBgm(44);
                                    }
                                    else if (this.isAttackBoss4) {
                                        SoundSystem.getInstance().playBgm(22);
                                    }
                                    else {
                                        SoundSystem.getInstance().playBgm(StageManager.getBgmId());
                                    }
                                }
                                if (PlayerObject.isNeedPlayWaterSE) {
                                    SoundSystem.getInstance().playSe(58);
                                }
                                this.waterSprayFlag = true;
                                this.waterSprayX = this.posX;
                                PlayerObject.waterSprayDrawer.restart();
                            }
                            ++this.breatheFrame;
                            this.breatheFrame %= 51;
                            if (this.breatheFrame == MyRandom.nextInt(1, 8) * 6) {
                                final int footPositionX = PlayerObject.player.getFootPositionX();
                                int n5;
                                if (this.faceDirection) {
                                    n5 = 384;
                                }
                                else {
                                    n5 = -384;
                                }
                                addGameObject((GameObject)new AspirateBubble(40, footPositionX + n5, PlayerObject.player.getFootPositionY() - 1536, 0, 0, 0, 0));
                            }
                        }
                    }
                    else if (PlayerObject.waterSprayDrawer != null) {
                        this.breatheCount = 0;
                        this.breatheNumCount = -1;
                        this.preBreatheNumCount = -1;
                        if (this.getNewPointY(this.posY, 0, -this.collisionRect.getHeight() >> 1, this.faceDegree) - 256 >= waterLevel << 6) {
                            this.isInWater = true;
                            if (PlayerObject.isNeedPlayWaterSE) {
                                SoundSystem.getInstance().playSe(58);
                            }
                            this.waterSprayFlag = true;
                            this.waterSprayX = this.posX;
                            PlayerObject.waterSprayDrawer.restart();
                        }
                    }
                }
                if (PlayerObject.speedCount > 0) {
                    --PlayerObject.speedCount;
                    this.movePower = PlayerObject.MOVE_POWER << 1;
                    this.movePowerInAir = PlayerObject.MOVE_POWER_IN_AIR << 1;
                    this.movePowerReverse = PlayerObject.MOVE_POWER_REVERSE << 1;
                    this.movePowerReserseBall = PlayerObject.MOVE_POWER_REVERSE_BALL << 1;
                    this.maxVelocity = PlayerObject.MAX_VELOCITY << 1;
                    if (PlayerObject.speedCount == 0 && SoundSystem.getInstance().getPlayingBGMIndex() != 42 && SoundSystem.getInstance().getPlayingBGMIndex() != 41 && SoundSystem.getInstance().getPlayingBGMIndex() != 26) {
                        SoundSystem.getInstance().setSoundSpeed(1.0f);
                        if (SoundSystem.getInstance().getPlayingBGMIndex() != 43) {
                            SoundSystem.getInstance().restartBgm();
                        }
                    }
                }
                else {
                    this.movePower = PlayerObject.MOVE_POWER;
                    this.movePowerInAir = PlayerObject.MOVE_POWER_IN_AIR;
                    this.movePowerReverse = PlayerObject.MOVE_POWER_REVERSE;
                    this.movePowerReserseBall = PlayerObject.MOVE_POWER_REVERSE_BALL;
                    this.maxVelocity = PlayerObject.MAX_VELOCITY;
                }
                if (this.isAntiGravity) {
                    if (!this.isDead && this.footPointY > MapManager.getPixelHeight() << 6) {
                        this.footPointY = MapManager.getCamera().y + MapManager.CAMERA_HEIGHT << 6;
                        if (this.getVelY() < 0) {
                            this.setVelY(0);
                        }
                    }
                }
                else if (!this.isDead && this.footPointY > MapManager.getPixelHeight() << 6) {
                    this.footPointY = (MapManager.getCamera().y + MapManager.CAMERA_HEIGHT << 6) + 3072;
                    this.setDie(false, -1600);
                }
                this.ignoreFirstTouch = false;
                if (this.dashRolling) {
                    this.dashRollingLogic();
                    if (this.dashRolling) {
                        this.collisionChk();
                        return;
                    }
                }
                else if (this.effectID == 0 || this.effectID == 1) {
                    this.effectID = -1;
                }
                if (this.railing) {
                    this.setNoKey();
                    if (this.railLine == null) {
                        this.velY += this.getGravity();
                        this.checkWithObject(this.footPointX, this.footPointY, this.footPointX + this.velX, this.footPointY + this.velY);
                    }
                    else {
                        final int footPointX = this.footPointX;
                        final int footPointY = this.footPointY;
                        int sin;
                        final int n6 = sin = this.railLine.sin(this.getGravity());
                        if (!this.railLine.directRatio()) {
                           sin = -n6;
                        }
                        if (sin != 0) {
                           final Direction oneDirection = this.railLine.getOneDirection();
                           this.totalVelocity += sin;
                           this.checkWithObject(this.footPointX, this.footPointY, this.footPointX + oneDirection.getValueX(this.railLine.cos(this.totalVelocity)), this.footPointY + oneDirection.getValueY(this.railLine.sin(this.totalVelocity)));
                        }
                        else {
                           final int footPointX2 = this.footPointX;
                           final int footPointY2 = this.footPointY;
                           final int footPointX3 = this.footPointX;
                           int n7;
                           if (this.totalVelocity < 0) {
                              n7 = -1;
                           }
                           else {
                              n7 = 1;
                           }
                           final int cos = this.railLine.cos(this.totalVelocity);
                           final int footPointY3 = this.footPointY;
                           int n8;
                           if (this.totalVelocity < 0) {
                              n8 = -1;
                           }
                           else {
                              n8 = 1;
                           }
                           this.checkWithObject(footPointX2, footPointY2, footPointX3 + n7 * cos, footPointY3 + n8 * this.railLine.sin(this.totalVelocity));
                        }
                        if (!this.railOut && this.railLine != null) {
                            this.velX = this.footPointX - footPointX;
                            this.velY = this.footPointY - footPointY;
                        }
                    }
                    if (this.railOut && this.velY == this.getGravity() - 1200) {
                        if (PlayerObject.characterID == 3) {
                           soundInstance.playSe(25);
                        }
                        else {
                           soundInstance.playSe(37);
                        }
                    }
                    if (this.railOut && this.velY > 0) {
                        this.railOut = false;
                        this.railing = false;
                        this.collisionState = 1;
                    }
               }
               else {
                  if (this.piping) {
                        final int footPointX4 = this.footPointX;
                        final int footPointY4 = this.footPointY;
                        switch (this.pipeState) {
                            case 0: {
                                if (this.footPointX < this.pipeDesX) {
                                    this.footPointX += 250;
                                    if (this.footPointX >= this.pipeDesX) {
                                        this.footPointX = this.pipeDesX;
                                    }
                                }
                                else if (this.footPointX > this.pipeDesX) {
                                    this.footPointX -= 250;
                                    if (this.footPointX <= this.pipeDesX) {
                                        this.footPointX = this.pipeDesX;
                                    }
                                }
                                if (this.footPointY < this.pipeDesY) {
                                    this.footPointY += 250;
                                    if (this.footPointY >= this.pipeDesY) {
                                        this.footPointY = this.pipeDesY;
                                    }
                                }
                                else if (this.footPointY > this.pipeDesY) {
                                    this.footPointY -= 250;
                                    if (this.footPointY <= this.pipeDesY) {
                                        this.footPointY = this.pipeDesY;
                                    }
                                }
                                if (this.footPointX == this.pipeDesX && this.footPointY == this.pipeDesY) {
                                    this.pipeState = 1;
                                    this.velX = this.nextVelX;
                                    this.velY = this.nextVelY;
                                    break;
                                }
                                break;
                            }
                            case 1: {
                                this.footPointX += this.velX;
                                this.footPointY += this.velY;
                                break;
                            }
                            case 2: {
                                this.footPointX += this.velX;
                                this.footPointY += this.velY;
                                if (this.velX != 0) {
                                    if (this.velX > 0 && this.footPointX > this.pipeDesX) {
                                        this.footPointX = this.pipeDesX;
                                    }
                                    else if (this.velX < 0 && this.footPointX < this.pipeDesX) {
                                        this.footPointX = this.pipeDesX;
                                    }
                                }
                                if (this.velY != 0) {
                                    if (this.velY > 0 && this.footPointY > this.pipeDesY) {
                                        this.footPointY = this.pipeDesY;
                                    }
                                    else if (this.velY < 0 && this.footPointY < this.pipeDesY) {
                                        this.footPointY = this.pipeDesY;
                                    }
                                }
                                if ((this.velX == 0 || this.footPointX == this.pipeDesX || this.nextVelX != 0) && (this.velY == 0 || this.footPointY == this.pipeDesY || this.nextVelY != 0)) {
                                    this.velX = this.nextVelX;
                                    this.velY = this.nextVelY;
                                    this.pipeState = 1;
                                    break;
                                }
                                break;
                            }
                        }
                        this.checkWithObject(footPointX4, footPointY4, this.footPointX, this.footPointY);
                        this.animationID = 4;
                        return;
                    }
                    this.bankLogic();
                    if (this.onBank) {
                        return;
                    }
                    if (PlayerObject.isTerminal) {
                        if (this.terminalCount > 0) {
                            --this.terminalCount;
                        }
                        if (this.animationID == 4) {
                            this.totalVelocity -= PlayerObject.MOVE_POWER_REVERSE_BALL;
                            if (this.totalVelocity < 0) {
                                this.totalVelocity = 0;
                            }
                        }
                        else if (this.totalVelocity > PlayerObject.MAX_VELOCITY) {
                            this.totalVelocity -= PlayerObject.MOVE_POWER_REVERSE_BALL;
                            if (this.totalVelocity <= PlayerObject.MAX_VELOCITY) {
                                this.totalVelocity = PlayerObject.MAX_VELOCITY;
                            }
                        }
                        this.noKeyFlag = true;
                    }
                    if (this.isCelebrate) {
                        if (this.faceDirection) {
                            if (this.collisionState == 0) {
                                this.setVelX(0);
                            }
                        }
                        else if (this.collisionState == 0) {
                            this.setVelX(0);
                        }
                        this.noKeyFlag = true;
                    }
                    if (StageManager.getStageID() != 11) {
                        if (!PlayerObject.isFirstTouchedWind && this.animationID == 29) {
                            PlayerObject.soundInstance.playSe(68);
                            PlayerObject.isFirstTouchedWind = true;
                            this.frameCnt = 0;
                        }
                        if (PlayerObject.isFirstTouchedWind) {
                            if (this.animationID == 29) {
                                ++this.frameCnt;
                                if (this.frameCnt > 4 && !PlayerObject.IsGamePause) {
                                    PlayerObject.soundInstance.playLoopSe(69);
                                }
                            }
                            else {
                                if (PlayerObject.soundInstance.getPlayingLoopSeIndex() == 69) {
                                    PlayerObject.soundInstance.stopLoopSe();
                                }
                                PlayerObject.isFirstTouchedWind = false;
                            }
                        }
                    }
                    if (StageManager.getCurrentZoneId() == 5) {
                        if (!PlayerObject.isFirstTouchedSandSlip && this.animationID == 30) {
                            PlayerObject.isFirstTouchedSandSlip = true;
                            this.frameCnt = 0;
                        }
                        if (PlayerObject.isFirstTouchedSandSlip) {
                            if (this.animationID == 30 && this.collisionState == 0) {
                                ++this.frameCnt;
                                if (this.frameCnt > 2 && !PlayerObject.IsGamePause) {
                                    PlayerObject.soundInstance.playLoopSe(71);
                                }
                            }
                            else {
                                if (PlayerObject.soundInstance.getPlayingLoopSeIndex() == 71) {
                                    PlayerObject.soundInstance.stopLoopSe();
                                }
                                PlayerObject.isFirstTouchedSandSlip = false;
                            }
                        }
                    }
                    if (this.ducting) {
                        ++this.ductingCount;
                        this.noKeyFlag = true;
                        this.animationID = 4;
                        this.attackAnimationID = this.animationID;
                        this.attackCount = 0;
                        this.attackLevel = 0;
                    }
                    if (this.noKeyFlag) {
                        Key.setKeyFunction(false);
                    }
                    if (this.hurtNoControl && this.animationID != 12 && this.animationID != 44) {
                        this.hurtNoControl = false;
                    }
                    switch (this.collisionState) {
                        default: {
                            this.extraInputLogic();
                            break;
                        }
                        case 0: {
                            this.inputLogicWalk();
                            break;
                        }
                        case 2: {
                            this.inputLogicOnObject();
                            break;
                        }
                        case 1: {
                            this.inputLogicJump();
                            if (!this.transing) {
                                break;
                            }
                            this.velX = 0;
                            this.velY = 0;
                            if (MapManager.isCameraStop()) {
                                this.transing = false;
                                break;
                            }
                            break;
                        }
                        case 3: {
                            this.inputLogicSand();
                            break;
                        }
                    }
                    if (this.noKeyFlag) {
                        Key.setKeyFunction(true);
                        this.noKeyFlag = false;
                    }
                    if (this.slipFlag) {
                        if (this.collisionState == 0) {
                            this.animationID = 30;
                        }
                        this.slipFlag = false;
                    }
                    this.calPreCollisionRect();
                    this.collisionChk();
                    if (this.animationID == 17) {
                        Effect.showEffect(this.dustEffectAnimation, 2, this.posX >> 6, this.posY >> 6, 0);
                    }
                    switch (this.collisionState) {
                        case 0: {
                            this.fallChk();
                            this.degreeForDraw = this.faceDegree;
                            if (this.noRotateDraw()) {
                                this.degreeForDraw = this.degreeStable;
                            }
                            if (PlayerObject.isTerminal) {
                                MapManager.setCameraDownLimit((this.posY >> 6) + 24);
                            }
                            if (PlayerObject.isTerminal && this.terminalCount == 0 && this.totalVelocity >= PlayerObject.MAX_VELOCITY) {
                                switch (PlayerObject.terminalType) {
                                    case 0: {
                                        if (this.animationID == 35 && this.drawer.checkEnd()) {
                                            this.animationID = 36;
                                        }
                                        if (this.animationID != 4 && this.animationID != 35 && this.animationID != 36) {
                                            this.animationID = 35;
                                            break;
                                        }
                                        break;
                                    }
                                    case 2: {
                                        if (StageManager.getCurrentZoneId() == 6) {
                                            StageManager.setStagePass();
                                            break;
                                        }
                                        if (!this.fading) {
                                            setFadeColor(16777215);
                                            fadeInit(0, 255);
                                            this.fading = true;
                                            break;
                                        }
                                        if (fadeChangeOver()) {
                                            StageManager.setStagePass();
                                            break;
                                        }
                                        break;
                                    }
                                    case 3: {
                                        this.terminalLogic();
                                        break;
                                    }
                                }
                            }
                            else {
                                switch (PlayerObject.terminalType) {
                                    case 3: {
                                        this.terminalLogic();
                                        break;
                                    }
                                }
                            }
                            if (this.isCelebrate) {
                                this.animationID = 37;
                                break;
                            }
                            break;
                        }
                        case 2:
                        case 3: {
                            this.degreeForDraw = this.faceDegree;
                            break;
                        }
                        case 1: {
                            if (this.noRotateDraw()) {
                                this.degreeForDraw = this.degreeStable;
                            }
                            this.terminalLogic();
                            if (PlayerObject.isTerminal && this.terminalCount == 0 && PlayerObject.terminalType == 1) {
                                StageManager.setStagePass();
                                break;
                            }
                            break;
                        }
                        case 4: {
                            this.terminalLogic();
                            break;
                        }
                    }
                    if (this.footPointX - 512 < MapManager.actualLeftCameraLimit << 6) {
                        this.footPointX = (MapManager.actualLeftCameraLimit << 6) + 512;
                        if (this.getVelX() < 0) {
                            this.setVelX(0);
                        }
                    }
                    if (MapManager.actualRightCameraLimit != MapManager.getPixelWidth() && this.footPointX + 512 > MapManager.actualRightCameraLimit << 6) {
                        this.footPointX = (MapManager.actualRightCameraLimit << 6) - 512;
                        if (this.getVelX() > 0) {
                            this.setVelX(0);
                        }
                    }
                    if (EnemyObject.isBossEnter) {
                        if (this.footPointY - 1536 + 1024 < MapManager.actualUpCameraLimit << 6) {
                            this.footPointY = (MapManager.actualUpCameraLimit << 6) + 1536 - 1024;
                            if (this.getVelY() < 0) {
                                this.setVelY(0);
                            }
                        }
                    }
                    else if (this.footPointY - 1536 < MapManager.actualUpCameraLimit << 6) {
                        this.footPointY = (MapManager.actualUpCameraLimit << 6) + 1536;
                        if (this.getVelY() < 0) {
                            this.setVelY(0);
                        }
                    }
                    if (PlayerObject.isDeadLineEffect && !this.isDead && this.footPointY > MapManager.actualDownCameraLimit << 6) {
                        this.footPointY = (MapManager.getCamera().y + MapManager.CAMERA_HEIGHT << 6) + 3072;
                        this.setDie(false, -1600);
                    }
                    if (this.leftStopped && this.rightStopped) {
                        this.setDie(false);
                    }
                }
            }
        }
    }

   public void lookUpCheck() {
      if (Key.repeat(4 | Key.gUp | 33554432)) {
         if (this.animationID == 38 && this.drawer.checkEnd()) {
            this.animationID = 39;
         }

         if (this.animationID != 38 && this.animationID != 39 && this.animationID == 0) {
            this.animationID = 38;
         }

         if (this.animationID == 39) {
            this.focusMovingState = 1;
         }
      } else {
         if (this.animationID == 40 && this.drawer.checkEnd()) {
            this.animationID = 0;
         }

         if (this.animationID == 38 || this.animationID == 39) {
            this.animationID = 40;
         }
      }

   }

   public void loseRing(int var1) {
      int var3 = this.getBodyPositionX();
      int var2 = this.getBodyPositionY();
      int var4 = this.currentLayer;
      boolean var5 = this.isAntiGravity;
      RingObject.hurtRingExplosion(var1, var3, var2, var4, var5);
   }

   public void moveOnObject(int var1, int var2) {
      this.moveOnObject(var1, var2, false);
   }

   public void moveOnObject(int var1, int var2, boolean var3) {
      int var7 = this.footPointX;
      int var4 = this.footPointY;
      this.posZ = this.currentLayer;
      this.worldCal.footDegree = this.faceDegree;
      this.posX = this.footPointX;
      this.posY = this.footPointY;
      int var6 = this.velX;
      int var5 = this.velY;
      this.worldCal.actionLogic(var1 - var7, var2 - var4);
      if (this.getAnimationId() != 12 && this.getAnimationId() != 44) {
         this.footPointX = this.posX;
         this.footPointY = this.posY;
         this.velX = var6;
         this.velY = var5;
         this.faceDegree = this.worldCal.footDegree;
      }

   }

   public boolean needChangeRect() {
      boolean var1;
      if (this.animationID != 4 || this.collisionState != 1 || (this.isAntiGravity || !this.changeRectUpCheck()) && (!this.isAntiGravity || !this.changeRectDownCheck())) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean needRetPower() {
      boolean var1;
      if ((Key.repeat(Key.gLeft | Key.gRight) || this.isTerminalRunRight() || this.isCelebrate) && this.animationID != 4 && !this.slipFlag) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public boolean noRotateDraw() {
      boolean var1;
      if (this.animationID != 0 && this.animationID != 5 && this.animationID != 46 && this.animationID != 50 && this.animationID != 51 && this.animationID != 6 && this.animationID != 7 && this.animationID != 30 && this.animationID != 8) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public void pipeIn(int var1, int var2, int var3, int var4) {
      this.piping = true;
      this.pipeState = 0;
      this.pipeDesX = var1;
      this.pipeDesY = var2 + 768;
      this.velX = 250;
      this.velY = 250;
      this.nextVelX = (var3 << 6) / 1;
      this.nextVelY = (var4 << 6) / 1;
      this.collisionChkBreak = true;
   }

   public void pipeOut() {
      if (this.piping) {
         this.piping = false;
         this.collisionState = 1;
         this.worldCal.actionState = 1;
      }

   }

   public void pipeSet(int var1, int var2, int var3, int var4) {
      if (this.piping) {
         this.pipeDesX = var1;
         this.pipeDesY = var2 + 768;
         crlFP32.actTanDegree(var4, var3);
         crlFP32.sqrt(var4 * var4 + var3 * var3);
         this.nextVelX = var3;
         this.nextVelY = var4;
         this.pipeState = 2;
         if (this.velX > 0 && this.footPointX > this.pipeDesX) {
            this.footPointX = this.pipeDesX;
         }

         if (this.velX < 0 && this.footPointX < this.pipeDesX) {
            this.footPointX = this.pipeDesX;
         }

         if (this.velY > 0 && this.footPointY > this.pipeDesY) {
            this.footPointY = this.pipeDesY;
         }

         if (this.velY < 0 && this.footPointY < this.pipeDesY) {
            this.footPointY = this.pipeDesY;
         }

         this.collisionChkBreak = true;
      } else {
         this.footPointX = var1;
         this.footPointY = var2;
         crlFP32.actTanDegree(var4, var3);
         crlFP32.sqrt(var4 * var4 + var3 * var3);
         this.nextVelX = var3;
         this.nextVelY = var4;
         this.velX = var3;
         this.velY = var4;
         this.pipeState = 1;
         this.piping = true;
         this.collisionChkBreak = true;
         this.worldCal.stopMove();
      }

   }

   public void prepareForCollision() {
      this.refreshCollisionRectWrap();
   }

   public void railIn(int var1, int var2) {
      this.railLine = null;
      this.velY = 0;
      this.velX = 0;
      this.worldCal.stopMoveX();
      this.setFootPositionX(var1);
      this.collisionChkBreak = true;
      this.railing = true;
      this.railOut = false;
      this.animationID = 21;
      this.setNoKey();
      if (characterID == 3) {
         soundInstance.playSe(25);
      } else {
         soundInstance.playSe(37);
      }

   }

   public void railOut(int var1, int var2) {
      if (this.railing) {
         this.railOut = true;
         this.railLine = null;
         this.velY = -1200;
         this.velX = 0;
         this.setVelX(0);
         this.setFootPositionX(var1);
         this.setFootPositionY(var2);
         this.collisionChkBreak = true;
         this.animationID = 4;
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
   }

   public void refreshCollisionRectWrap() {
      int var5 = this.getCollisionRectHeight();
      int var4 = this.getCollisionRectWidth();
      int var1 = this.faceDegree;
      short var2 = 0;
      if (this.animationID == 11) {
         short var7;
         if (this.getAnimationOffset() == 1) {
            var7 = -960;
         } else {
            var7 = -320;
         }

         byte var3 = 0;
         var2 = var7;
         var1 = var3;
      }

      this.checkPositionX = this.getNewPointX(this.footPointX, 0, -var5 >> 1, var1) + 0;
      this.checkPositionY = this.getNewPointY(this.footPointY, 0, -var5 >> 1, var1) + var2;
      int var6 = this.checkPositionX;
      int var8 = this.checkPositionY;
      int var9 = this.checkPositionX;
      var1 = this.checkPositionY;
      this.collisionRect.setTwoPosition(var6 - (var4 >> 1), var8 - (var5 >> 1), var9 + (var4 >> 1), var1 + (var5 >> 1));
   }

   public void releaseOutOfControl() {
      this.outOfControl = false;
      this.outOfControlObject = null;
   }

   public void resetBreatheCount() {
      this.breatheCount = 0;
      this.breatheNumCount = -1;
      this.preBreatheNumCount = -1;
   }

   public void resetFlyCount() {
   }

   public void resetPlayer() {
      this.footPointX = this.deadPosX;
      this.footPointY = this.deadPosY;
      this.worldCal.stopMove();
      StageManager.resetStageGameover();
      this.velX = 0;
      this.velY = 0;
      this.setVelX(this.velX);
      this.setVelY(this.velY);
      this.totalVelocity = 0;
      this.collisionState = 0;
      MapManager.setFocusObj(this);
      MapManager.focusQuickLocation();
      this.isDead = false;
      this.animationID = 0;
      timeStopped = false;
      invincibleCount = 240;
      preScoreNum = scoreNum;
      preLifeNum = lifeNum;
      timeCount = 0;
      lastTimeCount = timeCount;
   }

   public void resetPlayerDegree() {
      int var1 = this.degreeStable;
      this.faceDegree = var1;
      this.degreeForDraw = var1;
   }

   public void restartAniDrawer() {
      this.drawer.restart();
   }

   public void setAnimationId(int var1) {
      this.animationID = var1;
   }

   public void setAntiGravity() {
      boolean var5;
      if (this.isAntiGravity) {
         var5 = false;
      } else {
         var5 = true;
      }

      this.isAntiGravity = var5;
      this.worldCal.actionState = 1;
      this.collisionState = 1;
      if (this.faceDirection) {
         var5 = false;
      } else {
         var5 = true;
      }

      this.faceDirection = var5;
      int var3 = this.posX;
      int var1 = -this.collisionRect.getHeight();
      int var2 = this.faceDegree;
      var2 = this.getNewPointX(var3, 0, var1 >> 1, var2);
      var3 = this.posY;
      int var4 = -this.collisionRect.getHeight();
      var1 = this.faceDegree;
      var3 = this.getNewPointY(var3, 0, var4 >> 1, var1);
      short var6;
      if (this.isAntiGravity) {
         var6 = 180;
      } else {
         var6 = 0;
      }

      this.faceDegree = var6;
      var1 = this.collisionRect.getHeight();
      var4 = this.faceDegree;
      var1 = this.getNewPointX(var2, 0, var1 >> 1, var4);
      this.footPointX = var1;
      this.posX = var1;
      var2 = this.collisionRect.getHeight();
      var1 = this.faceDegree;
      var1 = this.getNewPointY(var3, 0, var2 >> 1, var1);
      this.footPointY = var1;
      this.posY = var1;
   }

   public void setAntiGravity(boolean var1) {
      this.orgGravity = this.isAntiGravity;
      this.isAntiGravity = var1;
      if (this.orgGravity != this.isAntiGravity) {
         this.worldCal.actionState = 1;
         this.collisionState = 1;
         if (this.faceDirection) {
            var1 = false;
         } else {
            var1 = true;
         }

         this.faceDirection = var1;
         short var2;
         if (this.isAntiGravity) {
            var2 = 180;
         } else {
            var2 = 0;
         }

         this.faceDegree = var2;
      }

   }

   public void setBank() {
      boolean var1;
      if (this.onBank) {
         var1 = false;
      } else {
         var1 = true;
      }

      this.onBank = var1;
      if (this.onBank && this.collisionState == 0) {
         this.calDivideVelocity();
      }

   }

   public void setBodyPositionX(int var1) {
      this.setFootPositionX(var1);
   }

   public void setBodyPositionY(int var1) {
      this.setFootPositionY(var1 + 768);
   }

   public void setCelebrate() {
      timeStopped = true;
      this.isCelebrate = true;
      MapManager.setCameraLeftLimit(MapManager.getCamera().x);
      MapManager.setCameraRightLimit(MapManager.getCamera().x + MapManager.CAMERA_WIDTH);
      if (this.faceDirection) {
         this.moveLimit = this.posX + 3840;
      } else {
         this.moveLimit = this.posX - 3840;
      }

   }

   public void setCharacterAnimationID(int var1) {
      this.myAnimationID = var1;
   }

   public void setCliffAnimation() {
      if (this.faceDirection) {
         this.animationID = 48;
      } else {
         this.animationID = 47;
      }

      this.drawer.restart();
   }

   public void setCollisionLayer(int var1) {
      if (var1 >= 0 && var1 <= 1) {
         this.currentLayer = var1;
      }

   }

   public void setCollisionState(byte var1) {
      if (this.collisionState == 0) {
         this.calDivideVelocity();
      }

      switch(var1) {
      case 1:
         this.faceDegree = this.degreeStable;
         this.worldCal.actionState = 1;
      default:
         this.collisionState = var1;
      }
   }

   public void setDie(boolean var1) {
      this.setDie(var1, -800);
   }

   public void setDie(boolean var1, int var2) {
      label16: {
         this.setDieInit(var1, var2);
         var2 = SoundSystem.getInstance().getPlayingBGMIndex();
         SoundSystem.getInstance();
         if (var2 != 21) {
            var2 = SoundSystem.getInstance().getPlayingBGMIndex();
            SoundSystem.getInstance();
            if (var2 != 44) {
               break label16;
            }
         }

         SoundSystem.getInstance().stopBgm(false);
      }

      if (!var1) {
         soundInstance.playSe(14);
      } else {
         soundInstance.playSe(60);
      }

   }

   public void setDieInit(boolean var1, int var2) {
      this.velX = 0;
      if (var1 && this.breatheNumCount >= 6) {
         this.velY = 0;
      } else {
         this.velY = var2;
      }

      if (this.isAntiGravity) {
         this.velY = -this.velY;
      }

      var2 = this.degreeStable;
      this.faceDegree = var2;
      this.degreeForDraw = var2;
      this.collisionState = 1;
      this.isDead = true;
      if (GameObject.player.isDead) MapManager.setFocusObj((Focusable)null);
      this.finishDeadStuff = false;
      this.animationID = 45;
      if (GameObject.player.isDead) {
      this.drawer.restart();
      timeStopped = true;
      this.worldCal.stopMove();
      this.collisionChkBreak = true;
      }
      this.hurtCount = 0;
      this.dashRolling = false;
      if (this.effectID == 0 || this.effectID == 1) {
         this.effectID = -1;
      }

      this.drownCnt = 0;
      if (GameObject.player.isDead) {
      if (stageModeState == 1 && StageManager.getStageID() == 10) {
         RocketSeparateEffect.clearInstance();
      }

      GameState.isThroughGame = true;
      shieldType = 0;
      invincibleCount = 0;
      speedCount = 0;
      }
      if (this.currentLayer == 0) {
         this.currentLayer = 1;
      } else if (this.currentLayer == 1) {
         this.currentLayer = 0;
      }

      this.resetFlyCount();
   }

   public void setDieWithoutSE() {
      this.setDieInit(false, -800);
   }

   public void setFaceDegree(int var1) {
      this.worldCal.footDegree = var1;
      this.faceDegree = var1;
   }

   public void setFall(int var1, int var2, int var3, int var4) {
      if (this instanceof PlayerTails) {
         ((PlayerTails)this).stopFly();
      }

      this.railing = true;
      this.setFootPositionX(var1);
      this.velX = 0;
      this.velY = 0;
      this.railLine = null;
      this.collisionChkBreak = true;
   }

   public void setFallOver() {
      this.railing = false;
   }

   public void setFootPositionX(int var1) {
      this.footPointX = var1;
      this.posX = var1;
   }

   public void setFootPositionY(int var1) {
      this.footPointY = var1;
      this.posY = var1;
   }

   public void setFurikoOutVelX(int var1) {
      this.velX = -JUMP_PROTECT * MyAPI.dCos(var1) / 100;
   }

   public void setHeadPositionY(int var1) {
      this.footPointY = this.getNewPointY(var1, 0, 1536, this.faceDegree);
   }

   public void setMeetingBoss(boolean var1) {
      if (var1) {
         var1 = false;
      } else {
         var1 = true;
      }

      this.setNoMoving = var1;
      this.noMovingPosition = this.footPointX;
      this.worldCal.stopMoveX();
      this.collisionChkBreak = true;
   }

   public void setNoKey() {
      this.noKeyFlag = true;
   }

   public void setOutOfControl(GameObject var1) {
      this.outOfControl = true;
      this.outOfControlObject = var1;
      this.piping = false;
   }

   public void setOutOfControlInPipe(GameObject var1) {
      this.outOfControl = true;
      this.outOfControlObject = var1;
   }

   public void setRailFlip() {
      this.velX = 0;
      this.velY = -3380;
      this.railLine = null;
      this.collisionChkBreak = true;
      this.railFlipping = true;
      SoundSystem.getInstance().playSe(54);
   }

   public boolean setRailLine(Line var1, int var2, int var3, int var4, int var5, int var6, int var7, GameObject var8) {
      boolean var9;
      if (!var8.getCollisionRect().collisionChk(this.footPointX, this.footPointY)) {
         var9 = false;
      } else if (this.railing && this.velY >= 0) {
         if (this.railLine == null) {
            this.totalVelocity = 0;
         }

         this.railLine = var1;
         this.calDivideVelocity();
         this.posX = var2;
         this.posY = var3;
         if (Math.abs(var5) <= 1) {
            this.velX = var4 * 1920 / var6;
            this.velY = 0;
            if (this.railFlipping) {
               this.railFlipping = false;
               this.setFootPositionY(this.railLine.getY(this.footPointX) + 768);
            } else {
               var2 = this.railLine.getY(this.footPointX);
               this.setFootPositionY(var2 - 512 + 768);
            }
         } else {
            this.velX = var4 * 1920 / var6;
            this.velY = var5 * 1920 / var7;
            this.setFootPositionY(this.railLine.getY(this.footPointX) + 768);
         }

         this.calTotalVelocity();
         System.out.println("~~1velX:" + this.velX + "|velY:" + this.velY);
         this.collisionChkBreak = true;
         var9 = true;
      } else {
         var9 = false;
      }

      return var9;
   }

   public void setSlideAni() {
   }

   public void setSlip() {
      if (this.collisionState == 0) {
         this.slipFlag = true;
         this.showWaterFlush = true;
         this.animationID = 30;
         this.setNoKey();
      }

   }

   public void setSlip0() {
   }

   public void setSqueezeEnable(boolean var1) {
      this.squeezeFlag = var1;
   }

   public void setStagePassRunOutofScreen() {
      MapManager.setFocusObj((Focusable)null);
      this.animationID = 3;
   }

   public void setTerminal(int var1) {
      this.terminalOffset = 0;
      terminalType = var1;
      this.terminalCount = 10;
      isTerminal = true;
      timeStopped = true;
      switch(terminalType) {
      case 0:
      case 2:
         if (this.collisionState == 0) {
            if (this.animationID == 4) {
               this.land();
            }

            if (this.totalVelocity > MAX_VELOCITY) {
               this.totalVelocity = MAX_VELOCITY;
            }
         }
         break;
      case 1:
         this.changeVisible(false);
         this.noMoving = true;
         break;
      case 3:
         terminalState = 0;
      }

   }

   public void setTerminalSingle(int var1) {
      terminalType = var1;
      this.terminalCount = 10;
      isTerminal = true;
      timeStopped = true;
   }

   public void setVelX(int var1) {
      if (this.collisionState == 0) {
         int var2 = this.totalVelocity * MyAPI.dCos(this.faceDegree) / 100;
         var2 = this.totalVelocity * MyAPI.dSin(this.faceDegree) / 100;
         int var3 = MyAPI.dCos(this.faceDegree);
         var1 = (var3 * var1 + MyAPI.dSin(this.faceDegree) * var2) / 100;
         this.totalVelocity = var1;
      } else {
         super.setVelX(var1);
      }

   }

   public void setVelXPercent(int var1) {
      if (this.collisionState == 0) {
         int var2 = this.totalVelocity * MyAPI.dCos(this.faceDegree) / 100;
         var2 = this.totalVelocity * MyAPI.dSin(this.faceDegree) / 100;
         int var3 = this.totalVelocity * var1 / 100;
         var1 = MyAPI.dCos(this.faceDegree);
         var1 = (var1 * var3 + MyAPI.dSin(this.faceDegree) * var2) / 100;
         this.totalVelocity = var1;
      } else {
         super.setVelX(this.totalVelocity * var1 / 100);
      }

   }

   public void setVelY(int var1) {
      if (this.collisionState == 0) {
         int var2 = this.totalVelocity * MyAPI.dCos(this.faceDegree) / 100;
         int var3 = this.totalVelocity * MyAPI.dSin(this.faceDegree) / 100;
         var3 = MyAPI.dCos(this.faceDegree);
         var1 = (var3 * var2 + MyAPI.dSin(this.faceDegree) * var1) / 100;
         this.totalVelocity = var1;
      } else {
         super.setVelY(var1);
      }

   }

   public void setVelYPercent(int var1) {
      if (this.collisionState == 0) {
         int var2 = this.totalVelocity * MyAPI.dCos(this.faceDegree) / 100;
         int var3 = this.totalVelocity * MyAPI.dSin(this.faceDegree) / 100;
         var1 = this.totalVelocity * var1 / 100;
         var3 = MyAPI.dCos(this.faceDegree);
         var1 = (var3 * var2 + MyAPI.dSin(this.faceDegree) * var1) / 100;
         this.totalVelocity = var1;
      } else {
         super.setVelY(this.totalVelocity * var1 / 100);
      }

   }

   public void slipJumpOut() {
   }

   protected boolean spinLogic() {
      if (!Key.repeat(Key.gLeft) && !Key.repeat(Key.gRight) && !isTerminal && this.animationID != -1 && this.animationID != 47 && this.animationID != 48) {
         if (Key.repeat(Key.gDown)) {
            if (Math.abs(this.getVelX()) <= 64 && this.getDegreeDiff(this.faceDegree, this.degreeStable) <= 45) {
               if (this.animationID != 5) {
                  this.animationID = 46;
               }

               if (this.collisionState == 3) {
                  if (this instanceof PlayerAmy) {
                     this.dashRolling = true;
                     this.spinDownWaitCount = 0;
                     if (characterID != 3) {
                        soundInstance.playSe(4);
                     }
                  }
               } else if (Key.press(16777216)) {
                  this.dashRolling = true;
                  this.spinDownWaitCount = 0;
                  if (characterID != 3) {
                     soundInstance.playSe(4);
                  }
               }

               if (!this.dashRolling) {
                  this.focusMovingState = 2;
               }
            } else {
               if (this.animationID != 4 && characterID != 3 && !this.isCrashFallingSand) {
                  soundInstance.playSe(4);
               }

               this.animationID = 4;
            }
         } else if (this.animationID == 5) {
            this.animationID = 46;
         }
      }

      if (this.animationID == 0 && this.getDegreeDiff(this.faceDegree, this.degreeStable) <= 45) {
         if (Key.press(2097152)) {
            this.dashRolling = true;
            if (characterID != 3) {
               soundInstance.playSe(4);
            }

            this.spinCount = 12;
            this.spinKeyCount = 20;
         } else if (Key.press(Key.B_7)) {
            this.faceDirection = false;
            this.dashRolling = true;
            this.spinKeyCount = 20;
            if (characterID != 3) {
               soundInstance.playSe(4);
            }

            this.spinCount = 12;
         } else if (Key.press(Key.B_9)) {
            this.faceDirection = true;
            this.dashRolling = true;
            this.spinKeyCount = 20;
            if (characterID != 3) {
               soundInstance.playSe(4);
            }

            this.spinCount = 12;
         }
      }

      return this.dashRolling;
   }

   protected boolean spinLogic2() {
      if (!Key.repeat(Key.gLeft) && !Key.repeat(Key.gRight) && !isTerminal && this.animationID != -1 && this.animationID != 47 && this.animationID != 48) {
         if (Key.repeat(Key.gDown)) {
            if (this.getDegreeDiff(this.faceDegree, this.degreeStable) <= 45 && this.animationID != 5) {
               this.animationID = 46;
            }
         } else if (this.animationID == 5) {
            this.animationID = 46;
         }
      }

      return this.dashRolling;
   }

   public boolean stagePassRunOutofScreenLogic() {
      boolean var1;
      if (!StageManager.isOnlyScoreCal && this.footPointX + 512 > camera.x + SCREEN_WIDTH + 800 << 6 || isStartStageEndFlag && stageEndFrameCnt > 80) {
         stagePassResultOutOffsetX -= 96;
         if (stagePassResultOutOffsetX < -1000) {
            var1 = true;
         } else {
            var1 = false;
         }
      } else {
         var1 = false;
      }

      return var1;
   }

   public void stopMove() {
      this.worldCal.stopMove();
   }

   public void terminalLogic() {
      if (terminalType == 3) {
         switch(terminalState) {
         case 0:
            if (this.posX > 235136) {
               terminalState = 1;
            }
            break;
         case 1:
            if (this.totalVelocity == 0 && this.animationID == 0) {
               terminalState = 2;
               this.terminalCount = 10;
            }
            break;
         case 2:
            if (this.terminalCount == 0) {
               StageManager.setOnlyScoreCal();
               StageManager.setStagePass();
               terminalState = 3;
            }
            break;
         case 3:
            if (this.terminalCount == 0 && StageManager.isScoreBarOut()) {
               terminalState = 4;
               this.collisionState = 4;
               int var1;
               if (this.isInWater) {
                  var1 = JUMP_INWATER_START_VELOCITY;
               } else {
                  var1 = JUMP_START_VELOCITY;
               }

               this.velY = var1;
               this.velX = 0;
               this.worldCal.actionState = 1;
               MapManager.setCameraUpLimit(MapManager.getCamera().y);
            }
            break;
         case 4:
            this.velY += this.getGravity();
            this.collisionState = 4;
            if (this.posY <= 25280) {
               this.velY = -100;
               terminalState = 5;
               this.terminalCount = 60;
            }
            break;
         case 5:
            this.collisionState = 4;
            if (this.posY <= 25280) {
               this.velY += 30;
            } else {
               this.velY -= 30;
            }

            if (this.terminalCount == 0) {
               this.velY = 0;
               this.velX = 0;
               MapManager.setCameraRightLimit(MapManager.getPixelWidth());
               MapManager.setFocusObj((Focusable)null);
               this.terminalCount = 30;
               terminalState = 6;
               this.posY -= 2112;
               this.footPointY = this.posY;
            }
            break;
         case 6:
            this.collisionState = 4;
            this.terminalOffset += 1600;
            if (this.terminalCount == 0) {
               this.terminalCount = 100;
               terminalState = 7;
            }
            break;
         case 7:
            if (this.terminalCount == 0) {
               StageManager.setStraightlyPass();
            }
         }
      }

   }

   public void waitingChk() {
      if (Key.repeat(Key.gSelect | 8388608 | Key.gLeft | Key.gRight | Key.gDown | Key.gUp | 16777216 | 33554432) || this.animationID != 0 && this.animationID != 50 && this.animationID != 51) {
         this.waitingCount = 0;
         this.waitingLevel = 0;
         this.isResetWaitAni = true;
      } else {
         ++this.waitingCount;
         if (this.waitingCount > 96) {
            if (this.waitingLevel == 0) {
               this.animationID = 50;
            }

            if (this.drawer.checkEnd() && this.waitingLevel == 0 || this.waitingLevel == 1) {
               this.waitingLevel = 1;
               this.animationID = 51;
            }
         }
      }

   }
}
