package SonicGBA;

import GameEngine.Key;
import Lib.MyAPI;
import Lib.SoundSystem;

class Split extends GimmickObject {
   private static final int COLLISION_HEIGHT = 1024;
   private static final int COLLISION_WIDTH = 1024;
   private static final int DEGREE_VELOCITY = 2560;
   private static final int RADIUS = 512;
   private static final int SHOOT_SPEED = 2700;
   private boolean controlling;
   private int degree;

   protected Split(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      this.posY -= 256;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      if (this.firstTouch) {
         this.controlling = true;
         player.setAnimationId(4);
         player.setOutOfControlInPipe(this);
         SoundSystem var4 = soundInstance;
         SoundSystem var3 = soundInstance;
         var4.playSe(37);
      }

   }

   public void logic() {
      if (this.controlling) {
         this.firstTouch = false;
         this.degree += 2560;
         this.degree %= 23040;
         player.setBodyPositionX(this.posX + MyAPI.dCos(this.degree >> 6) * 512 / 100);
         player.setBodyPositionY(this.posY + MyAPI.dSin(this.degree >> 6) * 512 / 100);
         boolean var1 = false;
         short var2 = 0;
         int var3 = 0;
         PlayerObject var4;
         SoundSystem var5;
         SoundSystem var6;
         switch(this.iLeft) {
         case 0:
            if (Key.press(Key.gDown)) {
               player.setBodyPositionX(this.posX);
               player.setBodyPositionY(this.posY + 512);
               var1 = true;
               var2 = 0;
               var3 = 2700;
               var4 = player;
               if (PlayerObject.getCharacterID() == 3) {
                  var5 = soundInstance;
                  var6 = soundInstance;
                  var5.playSe(25);
               } else {
                  var6 = soundInstance;
                  var5 = soundInstance;
                  var6.playSe(4);
               }
            } else if (Key.press(Key.gRight)) {
               player.setBodyPositionX(this.posX + 512);
               player.setBodyPositionY(this.posY);
               var1 = true;
               var2 = 2700;
               var3 = 0 - GRAVITY;
               player.faceDirection = true;
               var4 = player;
               if (PlayerObject.getCharacterID() == 3) {
                  var6 = soundInstance;
                  var5 = soundInstance;
                  var6.playSe(25);
               } else {
                  var5 = soundInstance;
                  var6 = soundInstance;
                  var5.playSe(4);
               }
            }
            break;
         case 1:
            if (Key.press(Key.gLeft)) {
               player.setBodyPositionX(this.posX);
               player.setBodyPositionY(this.posY);
               var1 = true;
               var2 = -2700;
               var3 = 2700 - GRAVITY;
               player.faceDirection = false;
               var4 = player;
               if (PlayerObject.getCharacterID() == 3) {
                  var5 = soundInstance;
                  var6 = soundInstance;
                  var5.playSe(25);
               } else {
                  var6 = soundInstance;
                  var5 = soundInstance;
                  var6.playSe(4);
               }
            } else if (Key.press(Key.gRight)) {
               player.setBodyPositionX(this.posX);
               player.setBodyPositionY(this.posY);
               var1 = true;
               var2 = 2700;
               var3 = 2700 - GRAVITY;
               player.faceDirection = true;
               var4 = player;
               if (PlayerObject.getCharacterID() == 3) {
                  var6 = soundInstance;
                  var5 = soundInstance;
                  var6.playSe(25);
               } else {
                  var5 = soundInstance;
                  var6 = soundInstance;
                  var5.playSe(4);
               }
            }
            break;
         case 2:
            if (Key.press(Key.gRight)) {
               player.setBodyPositionX(this.posX + 512);
               player.setBodyPositionY(this.posY);
               var1 = true;
               var2 = 2700;
               var3 = 0 - GRAVITY;
               player.faceDirection = true;
               var4 = player;
               if (PlayerObject.getCharacterID() == 3) {
                  var5 = soundInstance;
                  var6 = soundInstance;
                  var5.playSe(25);
               } else {
                  var6 = soundInstance;
                  var5 = soundInstance;
                  var6.playSe(4);
               }
            }
            break;
         case 3:
            if (Key.press(Key.gDown)) {
               player.setBodyPositionX(this.posX);
               player.setBodyPositionY(this.posY + 512);
               var1 = true;
               var2 = 0;
               var3 = 2700;
               var4 = player;
               if (PlayerObject.getCharacterID() == 3) {
                  var6 = soundInstance;
                  var5 = soundInstance;
                  var6.playSe(25);
               } else {
                  var5 = soundInstance;
                  var6 = soundInstance;
                  var5.playSe(4);
               }
            }
         }

         if (var1) {
            player.collisionState = 1;
            player.setAnimationId(4);
            player.setVelX(var2);
            player.setVelY(var3);
            this.controlling = false;
            player.outOfControl = false;
         }
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      this.collisionRect.setRect(var1 - 512, var2 - 512, 1024, 1024);
   }
}
