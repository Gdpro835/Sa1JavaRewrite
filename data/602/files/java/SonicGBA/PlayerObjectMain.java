package SonicGBA;

import GameEngine.Key;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.Coordinate;
import com.sega.engine.lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import State.TitleState;

public abstract class PlayerObjectMain extends PlayerObject {
   public boolean PlayerObjectFlag = false;
   protected PlayerObjectMain myPlayer = null;
   public static PlayerObjectMain myPlayerStatic = null;
   public static int invincibleCount;

   public static PlayerObjectMain getPlayer(int i) {
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
      return (PlayerObjectMain)var0;
   }
   
   public static PlayerObjectMain getPlayer() {
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
      return (PlayerObjectMain)var0;
   }

   public void setPlayer(PlayerObjectMain var0) {
   myPlayer = var0;
   myPlayerStatic = var0;
   }

   public abstract void closeImpl();
   
}