package Common;

import GameEngine.time.GameTime;

import GameEngine.Def;
import Lib.MyAPI;
import SonicGBA.MapManager;
import com.sega.mobile.framework.device.MFGraphics;

public class WhiteBarDrawer implements Def {
    private static final int STAY_TIME = 60;
    public static final int TIPS_BAR_X_IN = 0;
    public static final int TIPS_BAR_Y_IN = 1;
    public static final int TIPS_MOVE_OUT = 3;
    public static final int TIPS_OVER = 4;
    public static final int TIPS_STAY = 2;
    private static final int WHITE_BAR_HEIGHT = 20;
    private static final int WHITE_BAR_VEL_X = -96;
    private static final int WHITE_BAR_VEL_Y = -36;
    private static final int WHITE_BAR_WIDTH = SCREEN_WIDTH;
    private static final int WHITE_BAR_Y_DES = ((SCREEN_HEIGHT >> 1) - 60);
    private static final int WORDS_INIT_X = 14;
    private static final int WORDS_VEL_X = -8;
    private static WhiteBarDrawer instance;
    private boolean isPause = false;
    private boolean pauseFlag;
    private int tipsCount;
    private int tipsState = 4;
    private int whiteBarX;
    private int whiteBarY;
    private BarWord wordDrawer;
    private int wordID;
    private int wordX;

    public static WhiteBarDrawer getInstance() {
        if (instance == null) {
            instance = new WhiteBarDrawer();
        }
        return instance;
    }

    public void initBar(BarWord wordDrawer2, int wordID2) {
        this.whiteBarX = SCREEN_WIDTH - 26;
        this.whiteBarY = (SCREEN_HEIGHT >> 1) + 48;
        this.tipsState = 0;
        this.tipsCount = GameTime.set(this, "tipsCount", 0);
        this.wordX = 14;
        this.wordDrawer = wordDrawer2;
        this.pauseFlag = false;
        this.wordID = wordID2;
    }

    public void drawBar(MFGraphics g) {
        if (!this.isPause) {
            switch (this.tipsState) {
                case 0:
                    this.whiteBarX = GameTime.advance(this, "whiteBarX", this.whiteBarX, -(96));
                    if (this.whiteBarX <= 0) {
                        this.whiteBarX = 0;
                        this.tipsCount = GameTime.advance(this, "tipsCount", this.tipsCount, 1);
                    }
                    if (GameTime.event(this, "tipsCount", "drawBar:62", GameTime.crosses(this, "tipsCount", this.tipsCount, 2))) {
                        this.tipsCount = GameTime.set(this, "tipsCount", 0);
                        this.tipsState = 1;
                        break;
                    }
                    break;
                case 1:
                    this.whiteBarY = GameTime.advance(this, "whiteBarY", this.whiteBarY, -(36));
                    if (this.whiteBarY <= WHITE_BAR_Y_DES) {
                        this.whiteBarY = WHITE_BAR_Y_DES;
                        this.tipsCount = GameTime.advance(this, "tipsCount", this.tipsCount, 1);
                        this.tipsState = 2;
                        this.tipsCount = GameTime.set(this, "tipsCount", 0);
                        break;
                    }
                    break;
                case 2:
                    if (!this.pauseFlag) {
                        this.tipsCount = GameTime.advance(this, "tipsCount", this.tipsCount, 1);
                    }
                    if (this.tipsCount > 60) {
                        this.tipsState = 3;
                        this.tipsCount = GameTime.set(this, "tipsCount", 0);
                        break;
                    }
                    break;
                case 3:
                    this.whiteBarX = GameTime.advance(this, "whiteBarX", this.whiteBarX, -(96));
                    if (this.whiteBarX <= (-WHITE_BAR_WIDTH)) {
                        this.whiteBarX = -WHITE_BAR_WIDTH;
                        this.tipsState = 4;
                        this.tipsCount = GameTime.advance(this, "tipsCount", this.tipsCount, 1);
                        break;
                    }
                    break;
            }
        }
        g.setColor(MapManager.END_COLOR);
        MyAPI.fillRect(g, this.whiteBarX, this.whiteBarY - 2, WHITE_BAR_WIDTH, 20);
        if (!this.isPause) {
            this.wordX = GameTime.advance(this, "wordX", this.wordX, WORDS_VEL_X);
        }
        int wordDistance = this.wordDrawer.getWordLength(this.wordID);
        if (wordDistance > 0) {
            if (this.wordX < (-wordDistance)) {
                this.wordX += wordDistance;
            }
            MyAPI.setClip(g, this.whiteBarX, this.whiteBarY - 2, WHITE_BAR_WIDTH, 20);
            for (int i = this.wordX; i < SCREEN_WIDTH; i += wordDistance) {
                this.wordDrawer.drawWord(g, this.wordID, this.whiteBarX + i, this.whiteBarY);
            }
            MyAPI.setClip(g, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        }
    }

    public int getState() {
        return this.tipsState;
    }

    public int getCount() {
        return this.tipsCount;
    }

    public void setPauseCount(boolean pause) {
        this.isPause = pause;
    }

    public void setPause(boolean flag) {
        this.pauseFlag = flag;
        if (this.tipsState == 2 && !this.pauseFlag) {
            this.tipsCount = GameTime.set(this, "tipsCount", 60);
        }
    }

    public int getBarX() {
        return this.whiteBarX;
    }
}
