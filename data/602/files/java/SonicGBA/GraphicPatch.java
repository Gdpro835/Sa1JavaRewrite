package SonicGBA;

import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class GraphicPatch extends GimmickObject {
   private static final int HOLE_PATCH_HEIGHT = 48;
   private static final int HOLE_PATCH_OFFSET_X = -32;
   private static final int HOLE_PATCH_OFFSET_Y = -48;
   private static final int HOLE_PATCH_WIDTH = 56;
   private static final int MAP_PATCH_HEIGHT = 12288;
   private static final int MAP_PATCH_OFFSET_X = -6144;
   private static final int MAP_PATCH_OFFSET_Y = -2048;
   private static final int MAP_PATCH_WIDTH = 12288;
   private static final int RAIL_SPACE = 1024;
   private static Animation holePatchAnimation;
   private static Animation mapPatchAnimation;
   private static MFImage poalImage;
   private static AnimationDrawer railPatchDrawer;
   private static MFImage ropeImage;
   private int direction;
   private AnimationDrawer drawer;
   private int moveDistance;

   protected GraphicPatch(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);
      switch(this.iLeft) {
      case 0:
         if (WaterFall.waterFallDrawer3 == null) {
            WaterFall.waterFallDrawer3 = (new Animation("/animation/sand_03")).getDrawer(0, true, 0);
            WaterFall.waterFallDrawer3.setPause(true);
         }

         this.drawer = WaterFall.waterFallDrawer3;
         break;
      case 1:
         if (TransPoint.caveAnimation == null) {
            TransPoint.caveAnimation = new Animation("/animation/cave");
         }

         this.drawer = TransPoint.caveAnimation.getDrawer(1, true, 0);
         break;
      case 2:
         if (ropeImage == null) {
            ropeImage = MFImage.createImage("/gimmick/rope_patch.png");
         }
         break;
      case 3:
         if (mapPatchAnimation == null) {
            MFImage var12 = MapManager.image;
            String var9 = "/animation/patch_st" + StageManager.getCurrentZoneId();
            mapPatchAnimation = new Animation(var12, var9);
         }

         this.drawer = mapPatchAnimation.getDrawer(this.iTop, false, 0);
         this.mWidth = 12288;
         this.mHeight = 12288;
         break;
      case 4:
         if (holePatchAnimation == null) {
            holePatchAnimation = new Animation("/animation/hole_patch");
         }

         this.drawer = holePatchAnimation.getDrawer(this.iTop, false, 0);
         this.posX -= 32;
         var2 = this.posY;
         short var10;
         if (this.iTop == 0) {
            var10 = -48;
         } else {
            var10 = -512;
         }

         this.posY = var2 + var10;
         this.mWidth = 56;
         this.mHeight = 48;
         break;
      case 5:
         if (poalImage == null) {
            poalImage = MFImage.createImage("/gimmick/poal_patch.png");
         }
         break;
      case 6:
         if (railPatchDrawer == null) {
            Animation var8 = new Animation("/animation/rail_patch_2");
            AnimationDrawer var11 = var8.getDrawer();
            railPatchDrawer = var11;
         }

         this.direction = var6;
         System.out.println("6 height:" + var7);
         this.moveDistance = var7;
      }

   }

   public static void releaseAllResource() {
      Animation.closeAnimation(TransPoint.caveAnimation);
      TransPoint.caveAnimation = null;
      Animation.closeAnimation(mapPatchAnimation);
      Animation.closeAnimation(holePatchAnimation);
      Animation.closeAnimationDrawer(railPatchDrawer);
      ropeImage = null;
      mapPatchAnimation = null;
      holePatchAnimation = null;
      poalImage = null;
      railPatchDrawer = null;
   }

   public void close() {
      this.drawer = null;
   }

   public void draw(MFGraphics var1) {
      int var2;
      int var3;
      int var4;
      int var5;
      int var6;
      int var7;
      switch(this.iLeft) {
      case 0:
         var6 = this.collisionRect.x0;
         var2 = camera.x;
         var5 = this.collisionRect.y0;
         var7 = camera.y;
         var3 = this.collisionRect.getWidth();
         var4 = this.collisionRect.getHeight();
         MyAPI.setClip(var1, (var6 >> 6) - var2, (var5 >> 6) - var7, var3 >> 6, var4 >> 6);

         for(var2 = this.collisionRect.y0; var2 < this.collisionRect.y1; var2 += 6144) {
            this.drawInMap(var1, this.drawer, this.posX, var2);
         }

         var3 = SCREEN_WIDTH;
         var2 = SCREEN_HEIGHT;
         MyAPI.setClip(var1, 0, 0, var3, var2);
         break;
      case 1:
         this.drawInMap(var1, this.drawer);
         break;
      case 2:
         var3 = this.collisionRect.y0;

         for(var2 = 0; var3 < this.collisionRect.y1; var2 += 1024) {
            if (this.iTop == 0) {
               this.drawInMap(var1, ropeImage, this.posX + var2, var3, 20);
            } else {
               MFImage var13 = ropeImage;
               var4 = MyAPI.zoomIn(ropeImage.getWidth(), true);
               var6 = MyAPI.zoomIn(ropeImage.getHeight(), true);
               var5 = this.posX;
               var7 = this.mWidth;
               this.drawInMap(var1, var13, 0, 0, var4, var6, 2, var5 - var2 + var7, var3, 24);
            }

            var3 += 512;
         }

         return;
      case 3:
         this.drawInMap(var1, this.drawer, this.posX, this.posY);
         break;
      case 4:
         AnimationDrawer var11 = this.drawer;
         var3 = this.posX;
         var4 = this.posY;
         byte var12;
         if (this.iTop == 0) {
            var12 = -48;
         } else {
            var12 = 0;
         }

         this.drawInMap(var1, var11, var3 + 32, var4 - var12);
         break;
      case 5:
         var3 = this.collisionRect.x0;
         var6 = camera.x;
         var5 = this.collisionRect.y0;
         var4 = camera.y;
         var2 = this.collisionRect.getHeight();
         MyAPI.setClip(var1, (var3 >> 6) - var6, (var5 >> 6) - var4, 512, var2 >> 6);

         for(var2 = this.posY; var2 < this.posY + this.collisionRect.getHeight(); var2 += 1024) {
            this.drawInMap(var1, poalImage, this.posX + 128, var2, 17);
         }

         var2 = SCREEN_WIDTH;
         var3 = SCREEN_HEIGHT;
         MyAPI.setClip(var1, 0, 0, var2, var3);
         break;
      case 6:
         var7 = this.posX + RollIsland.DIRECTION[this.direction][0] * this.moveDistance / 1024 * 1024;
         var2 = this.posY + RollIsland.DIRECTION[this.direction][1] * this.moveDistance / 1024 * 1024;
         int var8 = this.posX;
         int var9 = this.posY;
         if (this.posX > var7) {
            var5 = var7;
            var6 = this.posX;
            var4 = this.posY;
            var3 = var2;
         } else {
            var6 = var7;
            var4 = var2;
            var5 = var8;
            var3 = var9;
            if (this.posY > var2) {
               var6 = var7;
               var4 = var2;
               var5 = var8;
               var3 = var9;
               if (RollIsland.DIRECTION[this.direction][1] * RollIsland.DIRECTION[this.direction][0] != -1) {
                  var5 = var7;
                  var6 = this.posX;
                  var4 = this.posY;
                  var3 = var2;
               }
            }
         }

         if (var5 == var6) {
            var7 = camera.y;
            var2 = SCREEN_WIDTH;
            var8 = Math.abs(var4 - var3);
            MyAPI.setClip(var1, 0, (var3 >> 6) - var7, var2, var8 >> 6);
         } else if (var3 == var4) {
            var2 = camera.x;
            var7 = Math.abs(var6 - var5);
            var8 = SCREEN_HEIGHT;
            MyAPI.setClip(var1, (var5 >> 6) - var2, 0, var7 >> 6, var8);
         } else {
            var7 = camera.x;
            var8 = Math.min(var3, var4);
            int var10 = camera.y;
            var9 = Math.abs(var6 - var5);
            var2 = Math.abs(var4 - var3);
            MyAPI.setClip(var1, (var5 >> 6) - var7 - 2, (var8 >> 6) - var10 - 2, (var9 >> 6) + 4, (var2 >> 6) + 4);
         }

         for(var2 = 0; var2 < this.moveDistance; var2 += 1024) {
            if (RollIsland.DIRECTION[this.direction][1] * RollIsland.DIRECTION[this.direction][0] == -1) {
               var8 = var5 + RollIsland.DIRECTION[this.direction][0] * var2;
               var7 = var3 + RollIsland.DIRECTION[this.direction][1] * var2;
            } else {
               if (RollIsland.DIRECTION[this.direction][0] != 0) {
                  var7 = var2;
               } else {
                  var7 = 0;
               }

               var8 = var5 + var7;
               if (RollIsland.DIRECTION[this.direction][1] != 0) {
                  var7 = var2;
               } else {
                  var7 = 0;
               }

               var7 += var3;
            }

            switch(this.direction) {
            case 0:
            case 4:
               railPatchDrawer.setActionId(1);
               break;
            case 1:
            case 5:
               railPatchDrawer.setActionId(7);
               break;
            case 2:
            case 6:
               railPatchDrawer.setActionId(0);
               break;
            case 3:
            case 7:
               railPatchDrawer.setActionId(6);
            }

            this.drawInMap(var1, railPatchDrawer, var8, var7);
         }

         MyAPI.setClip(var1, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
         switch(this.direction) {
         case 0:
         case 4:
            railPatchDrawer.setActionId(5);
            this.drawInMap(var1, railPatchDrawer, var5, var3);
            railPatchDrawer.setActionId(4);
            this.drawInMap(var1, railPatchDrawer, var6, var4);
            break;
         case 1:
         case 5:
            railPatchDrawer.setActionId(10);
            this.drawInMap(var1, railPatchDrawer, var5, var3);
            railPatchDrawer.setActionId(11);
            this.drawInMap(var1, railPatchDrawer, var6, var4);
            break;
         case 2:
         case 6:
            railPatchDrawer.setActionId(3);
            this.drawInMap(var1, railPatchDrawer, var5, var3);
            railPatchDrawer.setActionId(2);
            this.drawInMap(var1, railPatchDrawer, var6, var4);
            break;
         case 3:
         case 7:
            railPatchDrawer.setActionId(8);
            this.drawInMap(var1, railPatchDrawer, var5, var3);
            railPatchDrawer.setActionId(9);
            this.drawInMap(var1, railPatchDrawer, var6, var4);
         }
      }

   }

   public int getPaintLayer() {
      int var1;
      switch(this.iLeft) {
      case 1:
      case 6:
         var1 = 0;
         break;
      case 3:
         if (this.iTop == 1) {
            var1 = 2;
            break;
         }
      case 2:
      case 4:
      case 5:
      default:
         var1 = super.getPaintLayer();
      }

      return var1;
   }

   public void refreshCollisionRect(int var1, int var2) {
      int var3;
      int var4;
      CollisionRect var7;
      switch(this.iLeft) {
      case 3:
         var7 = this.collisionRect;
         var3 = this.mWidth;
         var4 = this.mHeight;
         var7.setRect(var1 - 6144, var2 - 2048, var3, var4);
         break;
      case 4:
      case 5:
      default:
         var7 = this.collisionRect;
         var4 = this.mWidth;
         var3 = this.mHeight;
         var7.setRect(var1, var2, var4, var3);
         break;
      case 6:
         int var5 = this.posX + RollIsland.DIRECTION[this.direction][0] * this.moveDistance;
         var4 = this.posY + RollIsland.DIRECTION[this.direction][1] * this.moveDistance;
         int var6 = this.posX;
         var3 = this.posY;
         var7 = this.collisionRect;
         var1 = Math.min(var6, var5);
         var2 = Math.min(var3, var4);
         var5 = Math.abs(var6 - var5);
         var3 = Math.abs(var3 - var4);
         var7.setRect(var1, var2, var5, var3);
      }

   }
}
