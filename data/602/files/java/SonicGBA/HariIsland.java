package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

class HariIsland extends GimmickObject {
   private static MFImage image;
   private static MFImage image2;
   private int collisionHeight;
   private int collisionWidth;
   private int damageDirection;
   private boolean isH;
   private MoveCalculator mCalc;
   private MFImage thisImage;

   protected HariIsland(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var1, var2, var3, var4, var5, var6, var7);

      label134: {
         Exception var10000;
         label133: {
            boolean var10001;
            try {
               var1 = StageManager.getCurrentZoneId();
            } catch (Exception var23) {
               var10000 = var23;
               var10001 = false;
               break label133;
            }

            switch(var1) {
            case 3:
               label126: {
                  try {
                     if (image != null) {
                        break label126;
                     }
                  } catch (Exception var22) {
                     var10000 = var22;
                     var10001 = false;
                     break;
                  }

                  try {
                     image = MFImage.createImage("/gimmick/hari_island_up_3.png");
                  } catch (Exception var21) {
                     var10000 = var21;
                     var10001 = false;
                     break;
                  }
               }

               try {
                  this.damageDirection = 1;
               } catch (Exception var20) {
                  var10000 = var20;
                  var10001 = false;
                  break;
               }

               try {
                  this.thisImage = image;
               } catch (Exception var19) {
                  var10000 = var19;
                  var10001 = false;
                  break;
               }
            case 4:
            default:
               break label134;
            case 5:
               label139: {
                  label140: {
                     try {
                        if (this.iLeft == 0) {
                           break label140;
                        }
                     } catch (Exception var18) {
                        var10000 = var18;
                        var10001 = false;
                        break label139;
                     }

                     label108: {
                        try {
                           if (image2 != null) {
                              break label108;
                           }
                        } catch (Exception var17) {
                           var10000 = var17;
                           var10001 = false;
                           break label139;
                        }

                        try {
                           image2 = MFImage.createImage("/gimmick/hari_island_down_5.png");
                        } catch (Exception var16) {
                           var10000 = var16;
                           var10001 = false;
                           break label139;
                        }
                     }

                     try {
                        this.damageDirection = 0;
                     } catch (Exception var15) {
                        var10000 = var15;
                        var10001 = false;
                        break label139;
                     }

                     try {
                        this.thisImage = image2;
                        break label134;
                     } catch (Exception var14) {
                        var10000 = var14;
                        var10001 = false;
                        break label139;
                     }
                  }

                  label96: {
                     try {
                        if (image != null) {
                           break label96;
                        }
                     } catch (Exception var13) {
                        var10000 = var13;
                        var10001 = false;
                        break label139;
                     }

                     try {
                        image = MFImage.createImage("/gimmick/hari_island_up_5.png");
                     } catch (Exception var12) {
                        var10000 = var12;
                        var10001 = false;
                        break label139;
                     }
                  }

                  try {
                     this.damageDirection = 1;
                  } catch (Exception var11) {
                     var10000 = var11;
                     var10001 = false;
                     break label139;
                  }

                  try {
                     this.thisImage = image;
                     break label134;
                  } catch (Exception var10) {
                     var10000 = var10;
                     var10001 = false;
                  }
               }
            }
         }

         Exception var9 = var10000;
         var9.printStackTrace();
      }

      switch(StageManager.getCurrentZoneId()) {
      case 3:
         this.damageDirection = 1;
      case 4:
      default:
         break;
      case 5:
         if (this.iLeft == 0) {
            this.damageDirection = 1;
         } else {
            this.damageDirection = 0;
         }
      }

      this.collisionWidth = MyAPI.zoomIn(image.getWidth()) << 6;
      this.collisionHeight = MyAPI.zoomIn(image.getHeight()) << 6;
      boolean var8;
      if (this.mWidth >= this.mHeight) {
         this.isH = true;
         if (this.iLeft == 0) {
            var8 = false;
         } else {
            var8 = true;
            this.mWidth += 64;
         }
      } else {
         this.isH = false;
         if (this.iTop == 0) {
            var8 = false;
         } else {
            var8 = true;
         }
      }

      if (this.isH) {
         var1 = this.posX;
      } else {
         var1 = this.posY;
      }

      if (this.isH) {
         var2 = this.mWidth;
      } else {
         var2 = this.mHeight;
      }

      this.mCalc = new MoveCalculator(var1, var2, var8);
   }

   public static void releaseAllResource() {
      image = null;
      image2 = null;
   }

   public void close() {
      this.thisImage = null;
      this.mCalc = null;
   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      var1.beStop(0, var2, this);
      if (var2 == this.damageDirection) {
         var1.beHurt();
      }

      if (player.isFootOnObject(this) && this.worldInstance.getWorldY(var1.collisionRect.x0, var1.collisionRect.y0, 1, 0) != -1000 && this.worldInstance.getWorldY(var1.collisionRect.x1, var1.collisionRect.y0, 1, 0) != -1000) {
         var1.setDie(false);
      }

   }

   public void draw(MFGraphics var1) {
      this.drawInMap(var1, this.thisImage, 3);
   }

   public int getPaintLayer() {
      return 0;
   }

   public void logic() {
      this.mCalc.logic();
      int var2 = this.posX;
      int var1 = this.posY;
      if (this.isH) {
         this.posX = this.mCalc.getPosition();
      } else {
         this.posY = this.mCalc.getPosition();
      }

      this.checkWithPlayer(var2, var1, this.posX, this.posY);
   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var7 = this.collisionRect;
      int var6 = this.collisionWidth;
      int var3 = this.collisionHeight;
      int var4 = this.collisionWidth;
      int var5 = this.collisionHeight;
      var7.setRect(var1 - (var6 >> 1), var2 - (var3 >> 1), var4, var5);
   }
}
