package SonicGBA;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import com.sega.engine.lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import State.TitleState;

public abstract class PlayerObject2 extends PlayerObject {
   public static PlayerObject myPlayer = null;
   public static int invincibleCount;
   
   public void setPlayer(PlayerObject var0) {
   myPlayer = var0;
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
   
   public void beHurt() {
   beHurtNoRingLose();
   }
   
   public abstract void closeImpl();
   
}