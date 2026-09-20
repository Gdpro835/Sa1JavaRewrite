package SonicGBA;

import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

class PipeSet extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 1024;
   private int posx;
   private int posy;
   private boolean terminal;
   private boolean touching;
   private int velx;
   private int vely;

   protected PipeSet(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.velx = var4 * 1920 / var6;
      this.vely = var5 * 1920 / var6;
      boolean var8;
      if (var7 == 127) {
         var8 = true;
      } else {
         var8 = false;
      }

      this.terminal = var8;
   }

   private void pipeSetLogic() {
      PlayerObject var1;
      if (this.terminal) {
         player.pipeSet(this.posX, this.posY, this.velx, this.vely);
         this.touching = true;
         var1 = player;
         if (PlayerObject.getCharacterID() == 3) {
            SoundSystem.getInstance().playSe(25);
         } else {
            SoundSystem.getInstance().playSe(4);
         }
      } else {
         player.pipeSet(this.posX, this.posY, this.velx, this.vely);
         var1 = player;
         if (PlayerObject.getCharacterID() == 3) {
            SoundSystem.getInstance().playSe(25);
         } else {
            SoundSystem.getInstance().playSe(4);
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.firstTouch) {
         if (StageManager.getStageID() == 11) {
            if (player.piping) {
               this.pipeSetLogic();
            }
         } else if (this.iHeight == 0 || player.piping) {
            this.pipeSetLogic();
         }
      }

   }

   public void doWhileNoCollision() {
      if (this.touching) {
         if (StageManager.getStageID() != 11) {
            player.pipeOut();
         }

         this.touching = false;
      }

   }

   public void draw(MFGraphics var1) {
      this.drawCollisionRect(var1);
   }

   public int getPaintLayer() {
      return 2;
   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 512, var2 - 512, 1024, 1024);
   }
}
