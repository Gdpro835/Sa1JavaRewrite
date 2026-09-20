package SonicGBA;

import com.sega.engine.action.ACBlock;
import com.sega.engine.action.ACWorld;

public class CollisionBlock extends ACBlock implements SonicDef {
   private static final byte[] BLANK_COLLISION_INFO = new byte[8];
   private boolean FLIP_X;
   private boolean FLIP_Y;
   private byte[] collisionInfo;
   private int degree;
   public boolean extendsDegree;
   public boolean throughable;

   public CollisionBlock(ACWorld var1) {
      super(var1);
      this.collisionInfo = BLANK_COLLISION_INFO;
   }

   public void doBeforeCollisionCheck() {
   }

   public int getActualX(int var1) {
      while(var1 < 0) {
         var1 += 8;
      }

      int var2 = var1 % 8;
      var1 = var2;
      if (this.FLIP_Y) {
         var1 = 7 - var2;
      }

      var2 = (this.collisionInfo[var1] & 15) >> 0;
      if (var2 != 8 && var2 != 0) {
         var1 = var2;
         if (this.FLIP_X) {
            var1 = 7 - var2;
         }
      } else {
         var1 = var2;
      }

      return var1;
   }

   public int getActualY(int var1) {
      while(var1 < 0) {
         var1 += 8;
      }

      int var2 = var1 % 8;
      var1 = var2;
      if (this.FLIP_X) {
         var1 = 7 - var2;
      }

      var2 = (this.collisionInfo[var1] & 240) >> 4;
      if (var2 != 8 && var2 != 0) {
         var1 = var2;
         if (this.FLIP_Y) {
            var1 = 7 - var2;
         }
      } else {
         var1 = var2;
      }

      return var1;
   }

   public int getCollisionX(int var1) {
      while(var1 < 0) {
         var1 += 8;
      }

      int var2 = var1 % 8;
      var1 = var2;
      if (this.FLIP_Y) {
         var1 = 7 - var2;
      }

      var2 = (this.collisionInfo[var1] & 15) >> 0;
      var1 = 0;
      if (var2 == 8) {
         var1 = 0;
      } else if (var2 == 0) {
         var1 = -1;
      } else {
         if (var2 < 8) {
            var1 = var2;
         } else if (var2 > 8) {
            var1 = 15 - var2;
         }

         var2 = var1;
         if (this.FLIP_X) {
            var2 = 7 - var1;
         }

         var1 = var2;
      }

      return var1;
   }

   public int getCollisionXFromLeft(int var1) {
      if (this.throughable) {
         var1 = -1000;
      } else {
         while(true) {
            if (var1 >= 0) {
               int var2 = this.getHeight();
               var2 = var1 % var2 >> 6;
               var1 = var2;
               if (this.FLIP_Y) {
                  var1 = 7 - var2;
               }

               var2 = (this.collisionInfo[var1] & 15) >> 0;
               if (var2 == 8) {
                  var1 = 0;
               } else if (var2 == 0) {
                  var1 = -1000;
               } else if ((var2 <= 8 || this.FLIP_X) && (var2 >= 8 || !this.FLIP_X)) {
                  var1 = var2;
                  if (var2 > 8) {
                     var1 = 15 - var2;
                  }

                  var2 = var1;
                  if (this.FLIP_X) {
                     var2 = 7 - var1;
                  }

                  var1 = var2 << 6;
               } else {
                  var1 = 0;
               }
               break;
            }

            var1 += this.getHeight();
         }
      }

      return var1;
   }

   public int getCollisionXFromRight(int var1) {
      if (this.throughable) {
         var1 = -1000;
      } else {
         while(true) {
            if (var1 >= 0) {
               int var2 = this.getHeight();
               var2 = var1 % var2 >> 6;
               var1 = var2;
               if (this.FLIP_Y) {
                  var1 = 7 - var2;
               }

               var2 = (this.collisionInfo[var1] & 15) >> 0;
               if (var2 == 8) {
                  var1 = rightSide;
               } else if (var2 == 0) {
                  var1 = -1000;
               } else if ((var2 <= 8 || !this.FLIP_X) && (var2 >= 8 || this.FLIP_X)) {
                  var1 = var2;
                  if (var2 > 8) {
                     var1 = 15 - var2;
                  }

                  var2 = var1;
                  if (this.FLIP_X) {
                     var2 = 7 - var1;
                  }

                  var1 = (var2 + 1 << 6) - 1;
               } else {
                  var1 = rightSide;
               }
               break;
            }

            var1 += this.getHeight();
         }
      }

      return var1;
   }

   public int getCollisionY(int var1) {
      while(var1 < 0) {
         var1 += 8;
      }

      int var2 = var1 % 8;
      var1 = var2;
      if (this.FLIP_X) {
         var1 = 7 - var2;
      }

      var2 = (this.collisionInfo[var1] & 240) >> 4;
      var1 = 0;
      if (var2 == 8) {
         var1 = 0;
      } else if (var2 == 0) {
         var1 = -1;
      } else {
         if (var2 < 8) {
            var1 = var2;
         } else if (var2 > 8) {
            var1 = 15 - var2;
         }

         var2 = var1;
         if (this.FLIP_Y) {
            var2 = 7 - var1;
         }

         var1 = var2;
      }

      return var1;
   }

   public int getCollisionYFromDown(int var1) {
      if (this.throughable) {
         var1 = -1000;
      } else {
         while(true) {
            if (var1 >= 0) {
               int var2 = this.getWidth();
               var2 = var1 % var2 >> 6;
               var1 = var2;
               if (this.FLIP_X) {
                  var1 = 7 - var2;
               }

               var2 = (this.collisionInfo[var1] & 240) >> 4;
               if (var2 == 8) {
                  var1 = downSide;
               } else if (var2 == 0) {
                  var1 = -1000;
               } else if ((var2 <= 8 || !this.FLIP_Y) && (var2 >= 8 || this.FLIP_Y)) {
                  var1 = var2;
                  if (var2 > 8) {
                     var1 = 15 - var2;
                  }

                  var2 = var1;
                  if (this.FLIP_Y) {
                     var2 = 7 - var1;
                  }

                  var1 = (var2 + 1 << 6) - 1;
               } else {
                  var1 = downSide;
               }
               break;
            }

            var1 += this.getWidth();
         }
      }

      return var1;
   }

   public int getCollisionYFromUp(int var1) {
      while(var1 < 0) {
         var1 += this.getWidth();
      }

      int var2 = this.getWidth();
      var2 = var1 % var2 >> 6;
      var1 = var2;
      if (this.FLIP_X) {
         var1 = 7 - var2;
      }

      var2 = (this.collisionInfo[var1] & 240) >> 4;
      if (var2 == 8) {
         var1 = 0;
      } else if (var2 == 0) {
         var1 = -1000;
      } else if (var2 > 8 && !this.FLIP_Y || var2 < 8 && this.FLIP_Y) {
         var1 = 0;
      } else {
         var1 = var2;
         if (var2 > 8) {
            var1 = 15 - var2;
         }

         var2 = var1;
         if (this.FLIP_Y) {
            var2 = 7 - var1;
         }

         var1 = var2 << 6;
      }

      return var1;
   }

   public int getDegree() {
      int var2 = this.degree * 360 / 256;
      if (var2 != 0) {
         int var1 = var2;
         if (this.FLIP_X) {
            var1 = -var2;
         }

         var2 = var1;
         if (this.FLIP_Y) {
            var2 = -var1;
         }

         var2 = (var2 + 360) % 360;
      }

      return var2;
   }

   public int getDegree(int var1, int var2) {
      if (!this.extendsDegree) {
         int var3;
         int var4;
         label38: {
            var3 = this.getDegree();
            if (var3 != 90) {
               var4 = var3;
               if (var3 != 270) {
                  break label38;
               }
            }

            if (var2 == 0) {
               var4 = 0;
            } else {
               var4 = var3;
               if (var2 == 2) {
                  var4 = 180;
               }
            }
         }

         label33: {
            if (var4 != 180) {
               var3 = var4;
               if (var4 != 0) {
                  break label33;
               }
            }

            if (var2 == 1) {
               var3 = 90;
            } else {
               var3 = var4;
               if (var2 == 3) {
                  var3 = 270;
               }
            }
         }

         var1 = Math.abs(var1 - var3);
         var2 = var1;
         if (var1 > 180) {
            var2 = 360 - var1;
         }

         var1 = var3;
         if (var2 > 90) {
            var1 = (var3 + 180) % 360;
         }
      }

      return var1;
   }

   public int getDegreeNearby(int var1) {
      if (!this.extendsDegree) {
         int var3 = this.getDegree();
         var1 = Math.abs(var1 - var3);
         int var2 = var1;
         if (var1 > 180) {
            var2 = 360 - var1;
         }

         var1 = var3;
         if (var2 > 90) {
            var1 = (var3 + 180) % 360;
         }
      }

      return var1;
   }

   public boolean getReverseX(int var1, int var2) {
      boolean var7 = false;

      boolean var6;
      for(var6 = false; var1 < 0; var1 += 8) {
      }

      int var3 = var1 % 8;
      var1 = var3;
      if (this.FLIP_Y) {
         var1 = 7 - var3;
      }

      var3 = (this.collisionInfo[var1] & 15) >> 0;
      if (var3 > 8) {
         if (false) {
            var6 = false;
         } else {
            var6 = true;
         }
      } else if (var3 == 8 || var3 == 0) {
         boolean var4 = true;
         var3 = 0;

         boolean var8;
         while(true) {
            if (var3 >= 8) {
               var8 = var4;
               break;
            }

            int var5 = (this.collisionInfo[(var1 + var3) % 8] & 15) >> 0;
            if (var5 > 0) {
               if (var5 > 8) {
                  if (false) {
                     var7 = false;
                  } else {
                     var7 = true;
                  }

                  var8 = false;
                  break;
               }

               if (var5 < 8) {
                  var8 = false;
                  break;
               }
            }

            ++var3;
         }

         var6 = var7;
         if (var8) {
            if (var2 > 45 && var2 < 135) {
               var6 = true;
               return var6;
            }

            var6 = var7;
            if (var2 > 225) {
               var6 = var7;
               if (var2 < 315) {
                  var6 = false;
                  return var6;
               }
            }
         }
      }

      var7 = var6;
      if (this.FLIP_X) {
         if (var6) {
            var7 = false;
         } else {
            var7 = true;
         }
      }

      var6 = var7;
      return var6;
   }

   public boolean getReverseY(int var1, int var2) {
      boolean var7 = false;

      boolean var6;
      for(var6 = false; var1 < 0; var1 += 8) {
      }

      int var3 = var1 % 8;
      var1 = var3;
      if (this.FLIP_X) {
         var1 = 7 - var3;
      }

      label99: {
         var3 = (this.collisionInfo[var1] & 240) >> 4;
         if (var3 > 8) {
            if (false) {
               var6 = false;
            } else {
               var6 = true;
            }
         } else if (var3 == 8) {
            boolean var4 = true;
            var3 = 0;

            boolean var8;
            while(true) {
               if (var3 >= 8) {
                  var8 = var4;
                  break;
               }

               int var5 = (this.collisionInfo[(var1 + var3) % 8] & 240) >> 4;
               if (var5 >= 0) {
                  if (var5 > 8) {
                     if (false) {
                        var7 = false;
                     } else {
                        var7 = true;
                     }

                     var8 = false;
                     break;
                  }

                  if (var5 < 8) {
                     var8 = false;
                     break;
                  }
               }

               ++var3;
            }

            var6 = var7;
            if (var8) {
               if (var2 > 135 && var2 < 225) {
                  var6 = true;
                  return var6;
               }

               if (var2 < 45) {
                  break label99;
               }

               var6 = var7;
               if (var2 > 315) {
                  break label99;
               }
            }
         }

         var7 = var6;
         if (this.FLIP_Y) {
            if (var6) {
               var7 = false;
            } else {
               var7 = true;
            }
         }

         var6 = var7;
         return var6;
      }

      var6 = false;
      return var6;
   }

   public boolean needJudgeX(int var1) {
      boolean var2;
      if (this.degree != 0 && !this.extendsDegree) {
         var2 = false;
      } else {
         var2 = true;
         byte var3 = this.collisionInfo[var1 % 8];
         if ((var3 & 15) >> 0 != 8) {
            var2 = false;
         }
      }

      return var2;
   }

   public boolean needJudgeY(int var1) {
      boolean var3;
      if (this.degree != 0 && !this.extendsDegree) {
         var3 = false;
      } else {
         var3 = true;

         for(var1 = 0; var1 < this.collisionInfo.length; ++var1) {
            byte var2 = this.collisionInfo[var1];
            if ((var2 & 240) >> 4 != 8) {
               var3 = false;
               break;
            }
         }
      }

      return var3;
   }

   public void setProperty(CollisionBlock var1) {
      byte[] var6 = var1.collisionInfo;
      boolean var3 = var1.FLIP_X;
      boolean var4 = var1.FLIP_Y;
      int var2 = var1.degree;
      boolean var5 = var1.throughable;
      this.setProperty(var6, var3, var4, var2, var5);
   }

   public void setProperty(byte[] var1, boolean var2, boolean var3, int var4, boolean var5) {
      this.collisionInfo = var1;
      this.FLIP_X = var2;
      this.FLIP_Y = var3;
      int var6;
      if (var4 < 0) {
         var6 = var4 + 256;
      } else {
         var6 = var4;
      }

      this.degree = var6;
      if (this.degree == 255) {
         this.extendsDegree = true;
      } else {
         this.extendsDegree = false;
      }

      boolean var7 = true;
      var6 = 0;

      boolean var8;
      while(true) {
         if (var6 >= var1.length) {
            var8 = var7;
            break;
         }

         if ((var1[var6] & 240) >> 4 != 8 || (var1[var6] & 15) >> 0 != 8) {
            var8 = false;
            break;
         }

         ++var6;
      }

      if (var8 && (var4 == 0 || var4 == 180)) {
         this.extendsDegree = true;
      }

      this.throughable = var5;
   }
}
