package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.Line;
import Lib.SoundSystem;
import com.sega.engine.action.ACCollision;
import com.sega.engine.action.ACObject;
import com.sega.engine.action.ACWorldCollisionCalculator;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

public class GimmickObject extends GameObject {
   public static final byte GIMMICK_ACCELERATOR_FORWARD = 31;
   public static final byte GIMMICK_ACCELERATOR_FORWARD_DOWN = 33;
   public static final byte GIMMICK_ACCELERATOR_FORWARD_UP = 32;
   public static final byte GIMMICK_ADD_DOUBLE_MAX_SPEED = 36;
   public static final byte GIMMICK_AIR_ROOT = 112;
   public static final byte GIMMICK_ARM = 80;
   public static final byte GIMMICK_AROUND_ENTRANCE = 34;
   public static final byte GIMMICK_AROUND_EXIT = 35;
   public static final byte GIMMICK_BALLOON = 59;
   public static final byte GIMMICK_BALL_HOBIN = 49;
   public static final byte GIMMICK_BANE_ISLAND = 81;
   public static final byte GIMMICK_BANPER = 71;
   public static final byte GIMMICK_BAR_H = 51;
   public static final byte GIMMICK_BAR_V = 52;
   public static final byte GIMMICK_BELT = 69;
   public static final byte GIMMICK_BIG_FLOATING_ISLAND = 55;
   public static final byte GIMMICK_BLOCK = 99;
   public static final byte GIMMICK_BOSS4_ICE = 121;
   public static final byte GIMMICK_BOSS6_BLOCK = 122;
   public static final byte GIMMICK_BOSS6_BLOCK_ARRAY = 123;
   public static final byte GIMMICK_BREAK_ISLAND = 24;
   public static final byte GIMMICK_BUBBLE = 94;
   public static final byte GIMMICK_CAPER_BED = 23;
   public static final byte GIMMICK_CAPER_BLOCK = 25;
   public static final byte GIMMICK_CHANGE_LAYER_A = 17;
   public static final byte GIMMICK_CHANGE_LAYER_B = 18;
   public static final byte GIMMICK_CHANGE_RECT_REGION = 124;
   public static final byte GIMMICK_CORNER_BAR = 53;
   public static final byte GIMMICK_DAMAGE = 105;
   public static final byte GIMMICK_DASH_PANEL_HIGH = 100;
   public static final byte GIMMICK_DASH_PANEL_LOW = 101;
   public static final byte GIMMICK_DASH_PANEL_TATE = 47;
   public static final byte GIMMICK_DEGREE_CHANGE_180 = 38;
   public static final byte GIMMICK_DOOR_H = 64;
   public static final byte GIMMICK_DOOR_V = 63;
   public static final byte GIMMICK_DOWN_SHIMA = 85;
   public static final byte GIMMICK_DUCT_ROTATE = 39;
   public static final byte GIMMICK_FALL = 66;
   public static final byte GIMMICK_FALLING_ISLAND = 22;
   public static final byte GIMMICK_FAN = 103;
   public static final byte GIMMICK_FIRE_MT = 98;
   public static final byte GIMMICK_FLIPPER = 54;
   public static final byte GIMMICK_FLIPPER_V = 56;
   public static final byte GIMMICK_FLOATING_ISLAND = 21;
   public static final byte GIMMICK_FREE_FALL = 74;
   public static final byte GIMMICK_FURIKO = 76;
   public static final byte GIMMICK_F_SHIMA_FALL = 104;
   public static final byte GIMMICK_GOAL = 0;
   public static final byte GIMMICK_GRAPHIC_PATCH = 116;
   public static final byte GIMMICK_GRAVITY = 109;
   public static final byte GIMMICK_HARI_DOWN = 2;
   public static final byte GIMMICK_HARI_ISLAND = 61;
   public static final byte GIMMICK_HARI_LEFT = 3;
   public static final byte GIMMICK_HARI_MOVE_DOWN = 6;
   public static final byte GIMMICK_HARI_MOVE_UP = 5;
   public static final byte GIMMICK_HARI_RIGHT = 4;
   public static final byte GIMMICK_HARI_UP = 1;
   public static final byte GIMMICK_HEX_HOBIN = 48;
   public static final byte GIMMICK_HOBBY_FAIR = 41;
   public static final byte GIMMICK_ICE = 95;
   public static final byte GIMMICK_INVISIBLE_CAPER = 26;
   public static final byte GIMMICK_IRON_BALL = 82;
   public static final byte GIMMICK_IRON_BAR = 83;
   public static final byte GIMMICK_KASSHA = 75;
   public static final byte GIMMICK_LEAF = 30;
   public static final byte GIMMICK_MARKER = 7;
   public static final byte GIMMICK_MINUS_DOUBLE_MAX_SPEED = 37;
   public static final byte GIMMICK_MOVE = 65;
   public static final byte GIMMICK_NEJI = 78;
   public static final byte GIMMICK_NET_ITEM = 115;
   public static final byte GIMMICK_NOTHING = 119;
   public static final byte GIMMICK_NO_KEY = 113;
   public static final int GIMMICK_NUM = 110;
   public static final byte GIMMICK_PIPE = 106;
   public static final byte GIMMICK_PIPE_IN = 110;
   public static final byte GIMMICK_PIPE_OUT = 111;
   public static final byte GIMMICK_POAL = 42;
   public static final byte GIMMICK_POAL_LEFT = 44;
   public static final byte GIMMICK_POAL_RIGHT = 45;
   public static final byte GIMMICK_RAIL_FLIPPER = 73;
   public static final byte GIMMICK_RAIL_IN = 67;
   public static final byte GIMMICK_RAIL_OUT = 68;
   protected static final String GIMMICK_RES_PATH = "/gimmick";
   public static final byte GIMMICK_ROLL_ASHIBA = 86;
   public static final byte GIMMICK_ROLL_HOBIN = 50;
   public static final byte GIMMICK_ROLL_SHIMA = 91;
   public static final byte GIMMICK_ROPE_END = 117;
   public static final byte GIMMICK_ROPE_TURN = 118;
   public static final byte GIMMICK_SEE = 70;
   public static final byte GIMMICK_SHATTER = 77;
   public static final byte GIMMICK_SHIP = 60;
   public static final byte GIMMICK_SLIP_END = 20;
   public static final byte GIMMICK_SLIP_START = 19;
   public static final byte GIMMICK_SPIN = 96;
   public static final byte GIMMICK_SPLIT = 107;
   public static final byte GIMMICK_SPRING_DOWN = 9;
   public static final byte GIMMICK_SPRING_ISLAND = 57;
   public static final byte GIMMICK_SPRING_LEFT = 10;
   public static final byte GIMMICK_SPRING_LEFT_UP = 12;
   public static final byte GIMMICK_SPRING_LEFT_UP_BURY = 14;
   public static final byte GIMMICK_SPRING_RIGHT = 11;
   public static final byte GIMMICK_SPRING_RIGHT_UP = 13;
   public static final byte GIMMICK_SPRING_RIGHT_UP_BURY = 15;
   public static final byte GIMMICK_SPRING_UP = 8;
   public static final byte GIMMICK_SP_BANE = 102;
   public static final byte GIMMICK_STEAM = 79;
   public static final byte GIMMICK_STONE = 16;
   public static final byte GIMMICK_STONE_BALL = 92;
   public static final byte GIMMICK_SUBEYUKA = 84;
   public static final byte GIMMICK_TAIMATU = 87;
   public static final byte GIMMICK_TEA_CUP = 62;
   public static final byte GIMMICK_TOGE_SHIMA = 93;
   public static final byte GIMMICK_TUTORIAL = 120;
   public static final byte GIMMICK_UG_BANE = 108;
   public static final byte GIMMICK_UP_ARM = 88;
   public static final byte GIMMICK_UP_SHIMA = 89;
   public static final byte GIMMICK_VIEW_LIGHTS = 58;
   public static final byte GIMMICK_WALL = 114;
   public static final byte GIMMICK_WALL_WALKER_ENTRANCE_LEFT = 28;
   public static final byte GIMMICK_WALL_WALKER_ENTRANCE_RIGHT = 29;
   public static final byte GIMMICK_WARP = 72;
   public static final byte GIMMICK_WATER_FALL = 27;
   public static final byte GIMMICK_WATER_PILLAR = 40;
   public static final byte GIMMICK_WATER_PILLAR_2 = 43;
   public static final byte GIMMICK_WATER_SLIP = 46;
   public static final byte GIMMICK_WIND = 90;
   public static final byte GIMMICK_WIND_PARTS = 97;
   public static final int PLATFORM_OFFSET_Y = -256;
   private static final int WIND_ACCELERATE;
   private static final int WIND_VELOCITY;
   private static boolean damageEnable = false;
   public static Animation doorAnimation = null;
   protected static Animation firemtAnimation;
   private static int framecnt;
   private static boolean furikoEnable = false;
   public static MFImage hookImage;
   private static boolean ironBallEnable = false;
   public static MFImage platformImage = null;
   private static boolean rollHobinEnable = false;
   private static boolean rollPlatformEnable = false;
   public static MFImage rolllinkImage;
   private static boolean seabedvolcanoEnable = false;
   public static MFImage shipRingImage;
   private static boolean steamEnable = false;
   private static boolean torchFireEnable = false;
   private static boolean waterFallEnable = false;
   private static boolean waterSlipEnable = false;
   protected int iHeight;
   protected int iLeft;
   protected int iTop;
   protected int iWidth;
   protected boolean used;

   static {
      WIND_VELOCITY = -300 - GRAVITY;
      WIND_ACCELERATE = -GRAVITY * 3;
   }

   protected GimmickObject(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.objId = var1;
      this.posX = var2;
      this.posY = var3;
      this.iLeft = var4;
      this.iTop = var5;
      this.iWidth = var6;
      this.iHeight = var7;
      this.mWidth = var6 * 512;
      this.mHeight = var7 * 512;
      this.collisionRect.setRect(this.posX, this.posY, this.mWidth, this.mHeight);
   }

   public static GameObject getNewInstance(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      Object var7 = null;
      Object var8 = null;
      var1 <<= 6;
      var2 <<= 6;
      switch(var0) {
      case 0:
         var7 = new Terminal(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
         var7 = new Hari(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 7:
         var7 = new Marker(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
         var7 = new Spring(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 16:
         var7 = new Stone(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 19:
         var7 = new SlipStart(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 21:
         var7 = new Platform(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 22:
         var7 = new FallingPlatform(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 23:
         var7 = new CaperBed(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 24:
         var7 = new BreakPlatform(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 25:
         var7 = new CaperBlock(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 31:
      case 32:
      case 33:
      case 100:
         var7 = new Accelerate(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 42:
      case 44:
      case 45:
         var7 = new Poal(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 69:
         var7 = new Belt(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 71:
         var7 = new Banper(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 75:
         var7 = new RopeStart(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 76:
         var7 = new Furiko(var0, var1, var2, var3, var4, var5, var6);
         furikoEnable = true;
         break;
      case 77:
         var7 = new Shatter(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 78:
         var7 = new Neji(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 80:
         var7 = new Arm(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 102:
         if (stageModeState != 1) {
            var7 = new SpSpring(var0, var1, var2, var3, var4, var5, var6);
         } else {
            var7 = null;
         }
         break;
      case 105:
         var7 = new DamageArea(var0, var1, var2, var3, var4, var5, var6);
         damageEnable = true;
         break;
      case 106:
         var7 = new PipeSet(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 110:
         var7 = new PipeIn(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 111:
         var7 = new PipeOut(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 116:
         var7 = new GraphicPatch(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 117:
         var7 = new RopeEnd(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 118:
         var7 = new RopeTurn(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 120:
         var7 = new TutorialPoint(var0, var1, var2, var3, var4, var5, var6);
         break;
      case 124:
         var7 = new ChangeRectRegion(var0, var1, var2, var3, var4, var5, var6);
         break;
      default:
         switch(var0) {
         case 27:
            var7 = new WaterFall(var0, var1, var2, var3, var4, var5, var6);
            waterFallEnable = true;
            break;
         case 28:
         case 29:
            var7 = new Bank(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 40:
            var7 = new Fountain(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 43:
            var7 = new FallFlush(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 46:
            var7 = new WaterSlip(var0, var1, var2, var3, var4, var5, var6);
            waterSlipEnable = true;
            break;
         case 48:
            var7 = new HexHobin(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 49:
            var7 = new BallHobin(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 50:
            var7 = new RollHobin(var0, var1, var2, var3, var4, var5, var6);
            rollHobinEnable = true;
            break;
         case 51:
            var7 = new BarHorbinH(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 52:
            var7 = new BarHorbinV(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 53:
            var7 = new CornerHobin(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 54:
            var7 = new FlipH(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 56:
            var7 = new FlipV(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 57:
            var7 = new SpringPlatform(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 58:
            var7 = new LightFont(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 59:
            var7 = new Balloon(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 60:
            var7 = new ShipSystem(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 62:
            var7 = new TeaCup(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 63:
         case 64:
            var7 = new Door(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 67:
            var7 = new RailIn(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 68:
            var7 = new RailOut(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 72:
            var7 = new TransPoint(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 73:
            var7 = new RailFlipper(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 74:
            var7 = new FreeFallSystem(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 79:
            var7 = new SteamBase(var0, var1, var2, var3, var4, var5, var6);
            steamEnable = true;
            break;
         case 82:
            var7 = new IronBall(var0, var1, var2, var3, var4, var5, var6);
            ironBallEnable = true;
            break;
         case 83:
            var7 = new IronBar(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 87:
            var7 = new TorchFire(var0, var1, var2, var3, var4, var5, var6);
            torchFireEnable = true;
            break;
         case 91:
            if (var4 == 5) {
               var7 = new RollPlatformSpeedA(var0, var1, var2, var3, var4, var5, var6);
            } else if (var4 == 2) {
               var7 = new RollPlatformSpeedB(var0, var1, var2, var3, var4, var5, var6);
            } else if (var4 == 4) {
               var7 = new RollPlatformSpeedC(var0, var1, var2, var3, var4, var5, var6);
            }

            rollPlatformEnable = true;
            break;
         case 107:
            var7 = new Split(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 114:
            var7 = new BreakWall(var0, var1, var2, var3, var4, var5, var6);
            break;
         default:
            var7 = var8;
         }

         switch(var0) {
         case 30:
            var8 = new Leaf(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 47:
            var8 = new Accelerate(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 55:
            var8 = new DekaPlatform(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 61:
            var8 = new HariIsland(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 81:
            var8 = new SpringIsland(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 84:
            var8 = new Subeyuka(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 85:
            var8 = new DownIsland(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 86:
            var8 = new RollIsland(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 88:
            var8 = new UpArm(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 89:
            var8 = new UpPlatform(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 92:
            var8 = new StoneBall(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 93:
            var8 = new TogeShima(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 94:
            var8 = new Bubble(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 95:
            var8 = new Ice(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 97:
            var8 = new WindParts(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 98:
            if (var3 != 35 && var3 != 9 && var3 != -21) {
               var8 = new SeabedVolcanoBase(var0, var1, var2, var3, var4, var5, var6);
            } else {
               var8 = new SeabedVolcanoAsynBase(var0, var1, var2, var3, var4, var5, var6);
            }

            seabedvolcanoEnable = true;
            break;
         case 99:
            var8 = new Block(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 103:
            var8 = new Fan(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 104:
            var8 = new FinalShima(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 108:
            var8 = new UnseenSpring(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 109:
            var8 = new AntiGravity(var0, var1, var2, var3, var4, var5, var6);
            break;
         case 112:
            var8 = new AirRoot(var0, var1, var2, var3, var4, var5, var6);
            break;
         default:
            var8 = var7;
         }

         var7 = var8;
         if (var8 == null) {
            if (var0 == 90) {
               isFirstTouchedWind = false;
            }

            var7 = new GimmickObject(var0, var1, var2, var3, var4, var5, var6);
         }
      }

      if (var7 != null) {
         ((GameObject)var7).refreshCollisionRectWrap();
      }

      return (GameObject)var7;
   }

   public static void gimmickInit() {
      furikoEnable = false;
      ironBallEnable = false;
      torchFireEnable = false;
      rollPlatformEnable = false;
      steamEnable = false;
      waterFallEnable = false;
      waterSlipEnable = false;
      rollHobinEnable = false;
      damageEnable = false;
      seabedvolcanoEnable = false;
   }

   public static void gimmickStaticLogic() {
      if (furikoEnable) {
         Furiko.staticLogic();
      }

      if (damageEnable) {
         DamageArea.staticLogic();
      }

      MoveCalculator.staticLogic();
      if (waterFallEnable) {
         WaterFall.staticLogic();
      }

      if (waterSlipEnable) {
         WaterSlip.staticLogic();
      }

      if (torchFireEnable) {
         TorchFire.staticLogic();
      }

      if (steamEnable) {
         SteamBase.staticLogic();
      }

      if (ironBallEnable) {
         IronBall.staticLogic();
      }

      if (rollHobinEnable) {
         RollHobin.staticLogic();
      }

      if (rollPlatformEnable) {
         RollPlatformSpeedA.staticLogic();
         RollPlatformSpeedB.staticLogic();
         RollPlatformSpeedC.staticLogic();
      }

      if (seabedvolcanoEnable) {
         SeabedVolcanoBase.staticLogic();
         SeabedVolcanoAsynBase.staticLogic();
      }

   }

   public static void releaseGimmickResource() {
      doorAnimation = null;
      shipRingImage = null;
      platformImage = null;
      hookImage = null;
      Hari.releaseAllResource();
      Spring.releaseAllResource();
      Stone.releaseAllResource();
      Marker.releaseAllResource();
      BreakPlatform.releaseAllResource();
      CaperBed.releaseAllResource();
      CaperBlock.releaseAllResource();
      Accelerate.releaseAllResource();
      Poal.releaseAllResource();
      Furiko.releaseAllResource();
      Shatter.releaseAllResource();
      Neji.releaseAllResource();
      Arm.releaseAllResource();
      PipeIn.releaseAllResource();
      Terminal.releaseAllResource();
      Belt.releaseAllResource();
      DamageArea.releaseAllResource();
      GraphicPatch.releaseAllResource();
      TutorialPoint.releaseAllResource();
      RopeStart.releaseAllResource();
      Cage.releaseAllResource();
      FallFlush.releaseAllResource();
      WaterSlip.releaseAllResource();
      TorchFire.releaseAllResource();
      SteamBase.releaseAllResource();
      SteamPlatform.releaseAllResource();
      CageButton.releaseAllResource();
      IronBall.releaseAllResource();
      RailFlipper.releaseAllResource();
      LightFont.releaseAllResource();
      HexHobin.releaseAllResource();
      BallHobin.releaseAllResource();
      RollHobin.releaseAllResource();
      CornerHobin.releaseAllResource();
      BarHorbinV.releaseAllResource();
      BarHorbinH.releaseAllResource();
      Balloon.releaseAllResource();
      ShipSystem.releaseAllResource();
      Ship.releaseAllResource();
      ShipBase.releaseAllResource();
      FlipH.releaseAllResource();
      FlipV.releaseAllResource();
      TeaCup.releaseAllResource();
      Door.releaseAllResource();
      FreeFallSystem.releaseAllResource();
      FreeFallBar.releaseAllResource();
      FreeFallPlatform.releaseAllResource();
      BreakWall.releaseAllResource();
      SpringPlatform.releaseAllResource();
      RailIn.releaseAllResource();
      DekaPlatform.releaseAllResource();
      Subeyuka.releaseAllResource();
      HariIsland.releaseAllResource();
      SpringIsland.releaseAllResource();
      WindParts.releaseAllResource();
      StoneBall.releaseAllResource();
      DownIsland.releaseAllResource();
      RollIsland.releaseAllResource();
      TogeShima.releaseAllResource();
      Bubble.releaseAllResource();
      Ice.releaseAllResource();
      SeabedVolcanoBase.releaseAllResource();
      SeabedVolcanoAsynBase.releaseAllResource();
      SeabedVolcanoAsynPlatform.releaseAllResource();
      SeabedVolcanoPlatform.releaseAllResource();
      Accelerate.releaseAllResource();
      Fan.releaseAllResource();
      FinalShima.releaseAllResource();
      AirRoot.releaseAllResource();
      SpSpring.releaseAllResource();
      Boss4Ice.releaseAllResource();
      Boss6Block.releaseAllResource();
   }

   public void close() {
   }

   public void doBeforeCollisionCheck() {
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (var1 == player) {
         int var3;
         CollisionRect var11;
         switch(this.objId) {
         case 17:
            if (this.collisionRect.collisionChk(player.getCheckPositionX(), player.getCheckPositionY())) {
               if (!this.used) {
                  player.setCollisionLayer(0);
                  this.used = true;
               }
            } else {
               this.used = false;
            }
            break;
         case 18:
            if (this.collisionRect.collisionChk(player.getCheckPositionX(), player.getCheckPositionY())) {
               if (!this.used) {
                  player.setCollisionLayer(1);
                  this.used = true;
               }
            } else {
               this.used = false;
            }
            break;
         case 20:
            if (player instanceof PlayerSonic) {
               PlayerSonic var14 = (PlayerSonic)player;
               var14.slipEnd();
            } else if (player instanceof PlayerAmy) {
               PlayerAmy var15 = (PlayerAmy)player;
               var15.slipEnd();
            }
            break;
         case 26:
            if (!this.used && player.beUnseenPop()) {
               this.used = true;
            }
            break;
         case 34:
            if (this.collisionRect.collisionChk(player.getCheckPositionX(), player.getCheckPositionY())) {
               if (!this.used) {
                  player.ductIn();
                  this.used = true;
                  if (!(player instanceof PlayerAmy) && player.getAnimationId() != 4) {
                     soundInstance.playSe(4);
                  }
               }
            } else {
               this.used = false;
            }
            break;
         case 35:
            if (this.collisionRect.collisionChk(player.getCheckPositionX(), player.getCheckPositionY())) {
               if (!this.used) {
                  player.velX = 0;
                  player.ductOut();
                  this.used = true;
               }
            } else {
               this.used = false;
            }
            break;
         case 36:
            if (player.isOnGound() && this.collisionRect.collisionChk(player.getCheckPositionX(), player.getCheckPositionY())) {
               player.setVelX(1900);
            }
            break;
         case 37:
            if (player.isOnGound()) {
               player.setVelX(-1900);
            }
         case 38:
         default:
            break;
         case 65:
            if (!this.used) {
               PlayerObject var17 = player;
               Line var13 = new Line(this.posX, this.posY, this.posX + this.iLeft, this.posY + this.iTop);
               var2 = this.posX;
               int var7 = this.posY;
               int var4 = this.iLeft;
               int var6 = this.iTop;
               var3 = this.iWidth;
               int var5 = this.iHeight;
               if (var17.setRailLine(var13, var2, var7, var4, var6, var3, var5, this)) {
                  this.used = true;
                  soundInstance.playSe(37);
               }
            }
            break;
         case 66:
            if (this.firstTouch && StageManager.getCurrentZoneId() != 3) {
               player.setFall(this.posX - 256, this.posY, this.iLeft, this.iTop);
               player.stopMove();
            }
            break;
         case 70:
            if (!this.used) {
               var11 = this.collisionRect;
               var2 = player.getCheckPositionX();
               var3 = player.getCheckPositionY();
               if (var11.collisionChk(var2, var3)) {
                  var1 = player;
                  boolean var8;
                  if (this.iLeft == 0) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  var1.changeVisible(var8);
                  this.used = true;
               }
            }
            break;
         case 90:
            framecnt = GameTime.advanceOnce(GimmickObject.class, "framecnt", framecnt, 1);
            var11 = this.collisionRect;
            var2 = player.getCheckPositionX();
            var3 = player.getCheckPositionY();
            if (var11.collisionChk(var2, var3)) {
               if (StageManager.getStageID() == 11) {
                  player.collisionState = 1;
                  player.isInGravityCircle = true;
               }

               if (player.collisionState == 1) {
                  player.collisionState = 1;
                  if (StageManager.getStageID() == 11) {
                     player.worldCal.stopMoveY();
                     ACWorldCollisionCalculator var16 = player.worldCal;
                     ACWorldCollisionCalculator var12 = player.worldCal;
                     var16.actionState = 1;
                  }

                  if (player.getVelY() > WIND_VELOCITY) {
                     player.setVelY(player.getVelY() + WIND_ACCELERATE);
                  } else {
                     player.setVelY(WIND_VELOCITY);
                  }

                  if (StageManager.getStageID() == 11) {
                     player.setAnimationId(9);
                  } else {
                     player.setAnimationId(29);
                  }
               } else {
                  soundInstance.stopLoopSe();
                  framecnt = 0;
                  isFirstTouchedWind = false;
                  player.isInGravityCircle = false;
               }
            }
            break;
         case 96:
            if (!this.used) {
               player.setAnimationId(4);
               SoundSystem var10 = soundInstance;
               SoundSystem var9 = soundInstance;
               var10.playSe(37);
               this.used = true;
            }
         }
      }

   }

   public void doWhileCollision(ACObject var1, ACCollision var2, int var3, int var4, int var5, int var6, int var7) {
   }

   public void doWhileNoCollision() {
      switch(this.objId) {
      case 17:
      case 18:
      case 19:
      case 20:
      case 26:
      case 34:
      case 35:
      case 65:
      case 96:
         this.used = false;
         break;
      case 70:
         if (this.used) {
            this.used = false;
            if (this.iLeft == 0) {
               player.setFallOver();
            }
         }
         break;
      case 90:
         if (player.isInGravityCircle) {
            player.isInGravityCircle = false;
         }
      }

   }

   public void doWhileRail(PlayerObject var1, int var2) {
      int var3;
      switch(this.objId) {
      case 65:
         if (!this.used) {
            PlayerObject var13 = player;
            Line var12 = new Line(this.posX, this.posY, this.posX + this.iLeft, this.posY + this.iTop);
            int var6 = this.posX;
            var2 = this.posY;
            int var5 = this.iLeft;
            int var4 = this.iTop;
            var3 = this.iWidth;
            int var7 = this.iHeight;
            if (var13.setRailLine(var12, var6, var2, var5, var4, var3, var7, this)) {
               this.used = true;
               soundInstance.playSe(37);
            }
         }
         break;
      case 66:
         if (this.firstTouch) {
            player.setFall(this.posX - 256, this.posY, this.iLeft, this.iTop);
         }
         break;
      case 70:
         if (!this.used) {
            CollisionRect var11 = this.collisionRect;
            var3 = player.getCheckPositionX();
            var2 = player.getCheckPositionY();
            if (var11.collisionChk(var3, var2)) {
               var1 = player;
               boolean var8;
               if (this.iLeft == 0) {
                  var8 = true;
               } else {
                  var8 = false;
               }

               var1.changeVisible(var8);
               this.used = true;
            }
         }
         break;
      case 73:
         if (this.firstTouch) {
            player.setRailFlip();
         }
         break;
      case 96:
         if (!this.used) {
            player.setAnimationId(4);
            SoundSystem var10 = soundInstance;
            SoundSystem var9 = soundInstance;
            var10.playSe(37);
            this.used = true;
         }
      }

   }

   public void draw(MFGraphics var1) {
      this.drawCollisionRect(var1);
   }

   public void logic() {
   }

   public void refreshCollisionRect(int var1, int var2) {
      switch(this.objId) {
      case 20:
         this.collisionRect.setRect((this.mWidth >> 1) + var1 - 640, var2, 1280, this.mHeight);
         break;
      case 66:
         CollisionRect var3 = this.collisionRect;
         var3.setRect(var1 - 1024, var2, 1536, 64);
         break;
      case 70:
         this.collisionRect.setRect(var1, var2, this.mWidth, this.mHeight);
         break;
      case 73:
         this.collisionRect.setRect(this.posX, this.posY - 512, 512, 512);
      }

   }
}
