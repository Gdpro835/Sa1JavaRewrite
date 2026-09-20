package Common;

import Lib.Animation;
import Lib.AnimationDrawer;
import SonicGBA.SonicDef;
import com.sega.mobile.framework.device.MFGraphics;

public class NumberDrawer implements SonicDef {
    public static final int ANCHOR_BOTTOM = 16;
    public static final int ANCHOR_HCENTER = 2;
    public static final int ANCHOR_LEFT = 1;
    public static final int ANCHOR_RIGHT = 4;
    public static final int ANCHOR_TOP = 8;
    public static final int ANCHOR_VCENTER = 32;
    public static final int[] NUM_DRAW_SPACE = {6, 6, 6, 12};
    public static final int[] NUM_HEIGHT = {12, 12, 12, 16};
    public static final int[] NUM_SPACE = {8, 8, 8, 16};
    public static final int TYPE_BIG_WHITE = 3;
    public static final int TYPE_SMALL_RED = 2;
    public static final int TYPE_SMALL_WHITE = 0;
    public static final int TYPE_SMALL_YELLOW = 1;
    public static final int[] TYPE_TO_ANIMATION;
    private static AnimationDrawer numberDrawer = new Animation("/animation/number").getDrawer();

    static {
        int[] iArr = new int[4];
        iArr[1] = 11;
        iArr[2] = 21;
        iArr[3] = 32;
        TYPE_TO_ANIMATION = iArr;
    }

    public static int drawNum(MFGraphics g, int numType, int num, int x, int y, int anchor) {
        int divideNum = 10;
        int blockNum = 1;
        int i = 0;
        while (num / divideNum != 0) {
            blockNum++;
            divideNum *= 10;
            i++;
        }
        int divideNum2 = divideNum / 10;
        int offsetX = 0;
        if ((anchor & 2) != 0) {
            offsetX = (-((NUM_SPACE[numType] * (blockNum - 1)) >> 1)) - (NUM_DRAW_SPACE[numType] >> 1);
        } else if ((anchor & 4) != 0) {
            offsetX = (-((NUM_SPACE[numType] * (blockNum - 1)) >> 0)) - (NUM_DRAW_SPACE[numType] >> 0);
        }
        int offsetY = 0;
        if ((anchor & 32) != 0) {
            offsetY = (-NUM_HEIGHT[numType]) >> 1;
        } else if ((anchor & 16) != 0) {
            offsetY = -NUM_HEIGHT[numType];
        }
        for (int i2 = 0; i2 < blockNum; i2++) {
            int tmpNum = Math.abs(num / divideNum2) % 10;
            divideNum2 /= 10;
            numberDrawer.setActionId(TYPE_TO_ANIMATION[numType] + tmpNum);
            numberDrawer.draw(g, offsetX + x, y + offsetY);
            x += NUM_SPACE[numType];
        }
        return x;
    }

    public static int drawColon(MFGraphics g, int numType, int x, int y, int anchor) {
        int offsetX = 0;
        if ((anchor & 2) != 0) {
            offsetX = -(NUM_DRAW_SPACE[numType] >> 1);
        } else if ((anchor & 4) != 0) {
            offsetX = -(NUM_DRAW_SPACE[numType] >> 0);
        }
        int offsetY = 0;
        if ((anchor & 32) != 0) {
            offsetY = (-NUM_HEIGHT[numType]) >> 1;
        } else if ((anchor & 16) != 0) {
            offsetY = -NUM_HEIGHT[numType];
        }
        int id = 0;
        switch (numType) {
            case 0:
            case 1:
                id = 10;
                break;
            case 2:
                id = 31;
                break;
            case 3:
                id = 42;
                break;
        }
        numberDrawer.setActionId(id);
        numberDrawer.draw(g, offsetX + x, y + offsetY);
        return x;
    }
}
