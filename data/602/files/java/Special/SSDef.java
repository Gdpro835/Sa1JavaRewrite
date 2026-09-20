package Special;

import SonicGBA.SonicDef;

public interface SSDef extends SonicDef {
    public static final int PLAYER_MOVE_HEIGHT = 240;
    public static final int PLAYER_MOVE_WIDTH = 300;
    public static final int PLAYER_VELOCITY_DASH = 6;
    public static final int PLAYER_VELOCITY_STANDARD = 4;
    public static final String SPECIAL_ANIMATION_PATH = "/animation/special";
    public static final String SPECIAL_FILE_PATH = "/special_res";
    public static final int SSOBJ_BNBK_ID = 2;
    public static final int SSOBJ_BNGO_ID = 3;
    public static final int SSOBJ_BNLD_ID = 6;
    public static final int SSOBJ_BNLU_ID = 4;
    public static final int SSOBJ_BNRD_ID = 7;
    public static final int SSOBJ_BNRU_ID = 5;
    public static final int SSOBJ_BOMB_ID = 9;
    public static final int SSOBJ_CHAO_ID = 8;
    public static final int SSOBJ_CHECKPT = 10;
    public static final int SSOBJ_GOAL = 11;
    public static final int SSOBJ_NUM = 12;
    public static final int SSOBJ_RING_ID = 0;
    public static final int SSOBJ_TRIC_ID = 1;
    public static final int[][] RING_TARGET = {new int[]{60, 120}, new int[]{70, 140}, new int[]{80, 160}, new int[]{80, 160}, new int[]{90, 180}, new int[]{90, 180}, new int[]{100, 200}};
    public static final int[] STAGE_ID_TO_SPECIAL_ID = {0, 0, 1, 1, 2, 2, 3, 4, 5, 5, 6, 6};
}
