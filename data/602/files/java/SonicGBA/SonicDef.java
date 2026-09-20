package SonicGBA;

import GameEngine.Def;

public interface SonicDef extends Def {
   boolean ALPHA_CHANGE_STATE = true;
   String ANIMATION_PATH = "/animation";
   int DIRECTION_DOWN = 1;
   int DIRECTION_LEFT = 2;
   int DIRECTION_NONE = 4;
   int DIRECTION_RIGHT = 3;
   int DIRECTION_UP = 0;
   int MENU_SPACE = 20;
   int NO_WATER = -1;
   int OVER_TIME = 599999;
   int PI = 201;
   String STAGE_PATH = "/map";
   int TILE_HEIGHT = 8;
   int TILE_WIDTH = 8;
   int TILE_WIDTH_ZOOM = 512;
   int TILE_ZOOM = 3;
   String TITLE_PATH = "/title";
   String TOUCH_ANI_PACH = "/tuch";
   String UTL_PATH = "/utl";
   String UTL_RES = "/utl_res";
   int ZOOM = 6;
}
