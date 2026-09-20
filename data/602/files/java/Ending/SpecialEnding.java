package Ending;

import GameEngine.time.GameTime;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyRandom;
import Lib.SoundSystem;
import SonicGBA.SonicDef;
import State.State;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import java.lang.reflect.Array;

public class SpecialEnding extends State implements SonicDef {
    private static final boolean[] ANIMATION_LOOP;
    private static final String[] CHARACTER_ANIMATION_NAME = {"/sonic_ed", "/tails_ed", "/knuckles_ed", "/amy_ed"};
    private static final String[] CHARACTER_SP_ANIMATION_NAME = {"/sonic_sp_ed", "/tails_sp_ed", "/knuckles_sp_ed", "/amy_sp_ed"};
    private static final int CLOUD_NUM = 10;
    private static final int CLOUD_TYPE = 0;
    private static final int[] CLOUD_VELOCITY = {5, 4, 3};
    private static final int CLOUD_X = 1;
    private static final int CLOUD_Y = 2;
    private static final String ENDING_ANIMATION_PATH = "/animation/ending";
    private static final int LOOP = 0;
    private static final int NORMAL_ANIMATION = 0;
    private static final int NO_LOOP = 1;
    private static final String[] PALETTE_IMAGE_NAME = {"/sonic_", "/tails_", "/knuckles_", "/amy_"};
    private static final int PILOT_SONIC = 0;
    private static final int PILOT_TAILS = 2;
    private static final int PLANE_ACC_POWER = 3;
    private static final int PLANE_STABLE_Y = ((SCREEN_HEIGHT >> 1) + 30);
    private static final int TOUCH_POINT_X = ((SCREEN_WIDTH >> 1) + 40);
    private static final int TOUCH_POINT_Y = ((PLANE_STABLE_Y - 34) + 10);
    private static final int PLANE_START_TO_TOUCH_X = ((TOUCH_POINT_X - -21) + 11);
    private static final int PLANE_START_X = (SCREEN_WIDTH + 70);
    private static final int PLANE_TOUCH_VELOCITY = 10;
    private static final int PLANE_VELOCITY = -3;
    private static final int PLAYER_EMERALD = 3;
    private static final int PLAYER_EMERALD_INTRO = 2;
    private static final int PLAYER_FALLING = 0;
    private static final int PLAYER_OFFSET_TO_PLANE_X = 11;
    private static final int PLAYER_OFFSET_TO_PLANE_Y = 34;
    private static final int PLAYER_START_Y = -10;
    private static final int PLAYER_TOUCHING = 1;
    private static final int[][][] PLAYER_TO_ANIMATION;
    private static final int SP_ANIMATION = 1;
    private static final boolean[] SP_ANIMATION_LOOP;
    private static final int STATE_INIT = 0;
    private static final int STATE_INTERRUPT = 5;
    private static final int STATE_PLANE_IN = 1;
    private static final int STATE_PLAYER_IN = 2;
    private static final int STATE_SHOW_EMERALD = 4;
    private static final int STATE_TOUCH = 3;
    private static final int TOUCH_FRAME = 7;
    private AnimationDrawer characterDrawer;
    private int characterID;
    private AnimationDrawer characterSpDrawer;
    private int cloudCount = 0;
    private AnimationDrawer cloudDrawer;
    private int[][] cloudInfo = ((int[][]) Array.newInstance(Integer.TYPE, new int[]{10, 3}));
    private int count;
    private AnimationDrawer dustDrawer;
    private boolean dusting = false;
    private MFImage endingBackGround;
    private boolean isOverFromInterrupt;
    private int pilotHeadID;
    private boolean pilotSmile;
    private AnimationDrawer planeDrawer;
    private AnimationDrawer planeHeadDrawer;
    private boolean planeShocking;
    private int planeVelY;
    private int planeX;
    private int planeY;
    private int playerActionID;
    private int playerX;
    private int playerY;
    private int state;

    static {
        boolean[] zArr = new boolean[8];
        zArr[0] = true;
        zArr[2] = true;
        zArr[4] = true;
        zArr[5] = true;
        zArr[7] = true;
        ANIMATION_LOOP = zArr;
        boolean[] zArr2 = new boolean[4];
        zArr2[1] = true;
        SP_ANIMATION_LOOP = zArr2;
        int[] iArr = new int[3];
        iArr[1] = 1;
        iArr[2] = 1;
        int[] iArr2 = new int[3];
        iArr2[0] = 1;
        iArr2[2] = 1;
        int[] iArr3 = new int[3];
        iArr3[0] = 1;
        iArr3[1] = 1;
        int[][] iArr4 = {new int[3], iArr, iArr2, iArr3};
        int[] iArr5 = new int[3];
        iArr5[0] = 1;
        iArr5[1] = 2;
        int[] iArr6 = new int[3];
        iArr6[1] = 1;
        iArr6[2] = 1;
        int[] iArr7 = new int[3];
        iArr7[0] = 1;
        iArr7[2] = 1;
        int[] iArr8 = new int[3];
        iArr8[0] = 1;
        iArr8[1] = 1;
        int[][] iArr9 = {iArr5, iArr6, iArr7, iArr8};
        int[] iArr10 = new int[3];
        iArr10[1] = 1;
        iArr10[2] = 1;
        int[] iArr11 = new int[3];
        iArr11[0] = 1;
        iArr11[2] = 1;
        int[] iArr12 = new int[3];
        iArr12[0] = 1;
        iArr12[1] = 1;
        int[][] iArr13 = {new int[3], iArr10, iArr11, iArr12};
        int[] iArr14 = new int[3];
        iArr14[0] = 1;
        iArr14[1] = 2;
        int[] iArr15 = new int[3];
        iArr15[0] = 1;
        iArr15[2] = 1;
        int[] iArr16 = new int[3];
        iArr16[0] = 1;
        iArr16[1] = 1;
        PLAYER_TO_ANIMATION = new int[][][]{iArr4, iArr9, iArr13, new int[][]{iArr14, new int[]{1, 3, 1}, iArr15, iArr16}};
    }

    public SpecialEnding(int characterID2, int emeraldID) {
        this.characterID = characterID2;
        this.characterDrawer = new Animation(ENDING_ANIMATION_PATH + CHARACTER_ANIMATION_NAME[characterID2]).getDrawer();
        if (emeraldID > 0) {
            this.characterSpDrawer = new Animation(MFImage.createPaletteImage(ENDING_ANIMATION_PATH + PALETTE_IMAGE_NAME[characterID2] + (emeraldID + 1) + ".pal"), ENDING_ANIMATION_PATH + CHARACTER_SP_ANIMATION_NAME[characterID2]).getDrawer();
        } else {
            this.characterSpDrawer = new Animation(ENDING_ANIMATION_PATH + CHARACTER_SP_ANIMATION_NAME[characterID2]).getDrawer();
        }
        this.endingBackGround = MFImage.createImage("/animation/ending/ending_bg.png");
        this.planeDrawer = new Animation("/animation/ending/ending_plane").getDrawer();
        this.cloudDrawer = new Animation("/animation/ending/ending_cloud").getDrawer();
        this.planeHeadDrawer = new Animation("/animation/ending/plane_head").getDrawer();
        this.dustDrawer = new Animation("/animation/ending/effect_dust").getDrawer(3, false, 0);
        this.planeY = PLANE_STABLE_Y;
        this.planeX = PLANE_START_X;
        this.pilotHeadID = characterID2 == 0 ? 2 : 0;
        this.isOverFromInterrupt = false;
    }

    public void logic() {
        this.count = GameTime.advance(this, "count", this.count, 1);
        if (this.planeShocking) {
            this.planeVelY = GameTime.advance(this, "planeVelY", this.planeVelY, -(3));
            this.planeY = GameTime.advancePosition(this, "planeY", this.planeY, "planeVelY", this.planeVelY);
            if (this.planeVelY < 0 && this.planeY <= PLANE_STABLE_Y) {
                this.planeShocking = false;
                this.planeY = PLANE_STABLE_Y;
            }
        }
        switch (this.state) {
            case 0:
                this.playerX = TOUCH_POINT_X;
                this.playerY = -10;
                this.playerActionID = 0;
                this.state = 1;
                return;
            case 1:
                this.planeX = GameTime.advance(this, "planeX", this.planeX, -(3));
                if (this.planeX <= PLANE_START_TO_TOUCH_X) {
                    this.playerX += this.planeX - PLANE_START_TO_TOUCH_X;
                    this.state = 2;
                    this.count = GameTime.set(this, "count", 0);
                    return;
                }
                return;
            case 2:
                this.planeX = GameTime.advance(this, "planeX", this.planeX, -(3));
                this.playerY = ((this.count * (TOUCH_POINT_Y - -10)) / 7) - 10;
                if (this.count >= 7) {
                    this.state = 3;
                    this.dusting = true;
                    this.playerActionID = 1;
                    this.planeVelY = 10;
                    this.planeShocking = true;
                    SoundSystem.getInstance().playBgm(38, false);
                    return;
                }
                return;
            case 3:
                this.planeX = GameTime.advance(this, "planeX", this.planeX, -(3));
                this.playerX = this.planeX - 11;
                this.playerY = this.planeY - 34;
                return;
            case 4:
                this.planeX = GameTime.advance(this, "planeX", this.planeX, -(3));
                this.playerX = this.planeX - 11;
                this.playerY = this.planeY - 34;
                return;
            default:
                return;
        }
    }

    public boolean isOver() {
        return this.playerX < -110 || this.isOverFromInterrupt;
    }

    public void draw(MFGraphics g) {
        g.drawImage(this.endingBackGround, 0, -135, 0);
        cloudLogic();
        cloudDraw(g);
        drawPlane(g);
    }

    private void drawPlane(MFGraphics g) {
        int i;
        boolean z = true;
        this.planeDrawer.draw(g, this.planeX, this.planeY);
        AnimationDrawer animationDrawer = this.planeHeadDrawer;
        if (this.pilotSmile) {
            i = 1;
        } else {
            i = 0;
        }
        animationDrawer.draw(g, this.pilotHeadID + i, this.planeX + 3, this.planeY - 22, true, 0);
        AnimationDrawer currentDrawer = PLAYER_TO_ANIMATION[this.characterID][this.playerActionID][0] == 0 ? this.characterDrawer : this.characterSpDrawer;
        int i2 = PLAYER_TO_ANIMATION[this.characterID][this.playerActionID][1];
        int i3 = this.playerX;
        int i4 = this.playerY;
        if (PLAYER_TO_ANIMATION[this.characterID][this.playerActionID][2] != 0) {
            z = false;
        }
        currentDrawer.draw(g, i2, i3, i4, z, 0);
        if (currentDrawer.checkEnd()) {
            switch (this.playerActionID) {
                case 1:
                    this.state = 4;
                    this.playerActionID = 2;
                    break;
                case 2:
                    this.playerActionID = 3;
                    break;
            }
        }
        if (this.dusting) {
            this.dustDrawer.draw(g, this.playerX, this.playerY);
            if (this.dustDrawer.checkEnd()) {
                this.dusting = false;
            }
        }
    }

    private void cloudLogic() {
        if (this.cloudCount > 0) {
            this.cloudCount = Math.max(0, GameTime.advance(this, "cloudCount", this.cloudCount, -(1)));
        }
        for (int i = 0; i < 10; i++) {
            if (this.cloudInfo[i][0] != 0) {
                int[] iArr = this.cloudInfo[i];
                iArr[1] = GameTime.advance(iArr, "cloudX", iArr[1], CLOUD_VELOCITY[this.cloudInfo[i][0] - 1]);
                if (this.cloudInfo[i][1] >= SCREEN_WIDTH + 75) {
                    this.cloudInfo[i][0] = 0;
                }
            }
            if (this.cloudInfo[i][0] == 0 && this.cloudCount == 0) {
                this.cloudInfo[i][0] = MyRandom.nextInt(1, 3);
                this.cloudInfo[i][1] = -60;
                this.cloudInfo[i][2] = MyRandom.nextInt(20, SCREEN_HEIGHT - 40);
                this.cloudCount = GameTime.set(this, "cloudCount", MyRandom.nextInt(8, 20));
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

    public void close() {
        this.endingBackGround = null;
        Animation.closeAnimationDrawer(this.characterDrawer);
        this.characterDrawer = null;
        Animation.closeAnimationDrawer(this.characterSpDrawer);
        this.characterSpDrawer = null;
        Animation.closeAnimationDrawer(this.dustDrawer);
        this.dustDrawer = null;
        Animation.closeAnimationDrawer(this.planeDrawer);
        this.planeDrawer = null;
        Animation.closeAnimationDrawer(this.cloudDrawer);
        this.cloudDrawer = null;
        Animation.closeAnimationDrawer(this.planeHeadDrawer);
        this.planeHeadDrawer = null;
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
        this.state = 5;
    }

    public void setOverFromInterrupt() {
        this.isOverFromInterrupt = true;
    }
}
