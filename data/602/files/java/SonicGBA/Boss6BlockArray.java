package SonicGBA;

import GameEngine.time.GameTime;

import Lib.Animation;
import com.sega.mobile.framework.device.MFGraphics;

class Boss6BlockArray extends GimmickObject {
   private static final int BLOCK_NUM = 9;
   private static final int BLOCK_SIZE = 2048;
   private static final int COLLISION_HEIGHT = 1536;
   private static final int COLLISION_WIDTH = 18432;
   private static final int DEEP_STANDARD_OFFSET = 8;
   private static final int TYPE_DEEP = 1;
   private static final int TYPE_NORMAL = 0;
   private Boss6Block[] block;
   private int[] blockOffsetY;
   private int blockOrgPosY;
   private int blockStartX;
   private boolean collisionFlag;
   private int deep_cn;
   private int normal_cn;
   private int playerPosY;
   private int preblockID = -1;
   private int type;

   protected Boss6BlockArray(int var1, int var2) {
      super(123, var1, var2, 0, 0, 0, 0);
      this.posX += 1024;
      this.blockStartX = this.posX - 8192;
      this.blockOrgPosY = this.posY;
      this.playerPosY = this.posY - 1024;
      this.block = new Boss6Block[9];
      this.blockOffsetY = new int[9];

      for(var1 = 0; var1 < 9; ++var1) {
         this.block[var1] = new Boss6Block(this.blockStartX + var1 * 2048, this.blockOrgPosY);
      }

      this.collisionFlag = false;
      this.deep_cn = GameTime.set(this, "deep_cn", 0);
      this.normal_cn = 0;
      this.preblockID = -1;
   }

   private void logicShake() {
      int var2 = (player.getFootPositionX() - this.blockStartX + 1024) / 2048;
      int var1;
      int[] var3;
      int[] var4;
      if (this.collisionFlag && this.type == 1) {
         switch(this.deep_cn) {
         case 0:
            if (var2 == 1) {
               this.blockOffsetY[0] = 256;

               for(var1 = 1; var1 < 9; ++var1) {
                  this.blockOffsetY[var1] = (9 - var1) * 2 << 6;
               }
            } else if (var2 == 7) {
               this.blockOffsetY[8] = 256;

               for(var1 = 0; var1 < 8; ++var1) {
                  this.blockOffsetY[var1] = (var1 + 1) * 2 << 6;
               }
            } else if (var2 == 0) {
               this.blockOffsetY[0] = 256;
               var3 = this.blockOffsetY;
               this.blockOffsetY[2] = 384;
               var3[1] = 384;
               var4 = this.blockOffsetY;
               var3 = this.blockOffsetY;
               this.blockOffsetY[5] = 256;
               var3[4] = 256;
               var4[3] = 256;
               var3 = this.blockOffsetY;
               var4 = this.blockOffsetY;
               this.blockOffsetY[8] = 128;
               var4[7] = 128;
               var3[6] = 128;
            } else if (var2 == 8) {
               this.blockOffsetY[8] = 256;
               var3 = this.blockOffsetY;
               this.blockOffsetY[6] = 384;
               var3[7] = 384;
               var3 = this.blockOffsetY;
               var4 = this.blockOffsetY;
               this.blockOffsetY[3] = 256;
               var4[4] = 256;
               var3[5] = 256;
               var3 = this.blockOffsetY;
               var4 = this.blockOffsetY;
               this.blockOffsetY[0] = 128;
               var4[1] = 128;
               var3[2] = 128;
            } else {
               for(var1 = 0; var1 < 9; ++var1) {
                  if (var1 != var2 - 4 && var1 != var2 + 4) {
                     if (var1 != var2 - 3 && var1 != var2 + 3) {
                        if (var1 != var2 - 2 && var1 != var2 + 2) {
                           if (var1 != var2 - 1 && var1 != var2 + 1) {
                              if (var1 == var2) {
                                 this.blockOffsetY[var1] = 1024;
                              } else {
                                 this.blockOffsetY[var1] = 128;
                              }
                           } else {
                              this.blockOffsetY[var1] = 896;
                           }
                        } else {
                           this.blockOffsetY[var1] = 640;
                        }
                     } else {
                        this.blockOffsetY[var1] = 256;
                     }
                  } else {
                     this.blockOffsetY[var1] = 128;
                  }
               }
            }

            if (this.blockOffsetY[0] >= 256) {
               this.blockOffsetY[0] = 256;
            }

            if (this.blockOffsetY[8] >= 256) {
               this.blockOffsetY[8] = 256;
            }

            this.deep_cn = Math.min(5, GameTime.advance(this, "deep_cn", this.deep_cn, 1));
            break;
         case 1:
            if (var2 == 1) {
               this.blockOffsetY[0] = 256;

               for(var1 = 1; var1 < 9; ++var1) {
                  this.blockOffsetY[var1] = 9 - var1 << 6;
               }
            } else if (var2 == 7) {
               this.blockOffsetY[8] = 256;

               for(var1 = 0; var1 < 8; ++var1) {
                  this.blockOffsetY[var1] = var1 + 1 << 6;
               }
            } else if (var2 == 0) {
               this.blockOffsetY[0] = 256;
               var3 = this.blockOffsetY;
               this.blockOffsetY[2] = 192;
               var3[1] = 192;
               var4 = this.blockOffsetY;
               var3 = this.blockOffsetY;
               this.blockOffsetY[5] = 128;
               var3[4] = 128;
               var4[3] = 128;
               var4 = this.blockOffsetY;
               var3 = this.blockOffsetY;
               this.blockOffsetY[8] = 64;
               var3[7] = 64;
               var4[6] = 64;
            } else if (var2 == 8) {
               this.blockOffsetY[8] = 256;
               var3 = this.blockOffsetY;
               this.blockOffsetY[6] = 192;
               var3[7] = 192;
               var4 = this.blockOffsetY;
               var3 = this.blockOffsetY;
               this.blockOffsetY[3] = 128;
               var3[4] = 128;
               var4[5] = 128;
               var3 = this.blockOffsetY;
               var4 = this.blockOffsetY;
               this.blockOffsetY[0] = 64;
               var4[1] = 64;
               var3[2] = 64;
            } else {
               for(var1 = 0; var1 < 9; ++var1) {
                  if (var1 != var2 - 4 && var1 != var2 + 4) {
                     if (var1 != var2 - 3 && var1 != var2 + 3) {
                        if (var1 != var2 - 2 && var1 != var2 + 2) {
                           if (var1 != var2 - 1 && var1 != var2 + 1) {
                              if (var1 == var2) {
                                 this.blockOffsetY[var1] = 512;
                              } else {
                                 this.blockOffsetY[var1] = 64;
                              }
                           } else {
                              this.blockOffsetY[var1] = 448;
                           }
                        } else {
                           this.blockOffsetY[var1] = 320;
                        }
                     } else {
                        this.blockOffsetY[var1] = 128;
                     }
                  } else {
                     this.blockOffsetY[var1] = 64;
                  }
               }
            }

            if (this.blockOffsetY[0] >= 256) {
               this.blockOffsetY[0] = 256;
            }

            if (this.blockOffsetY[8] >= 256) {
               this.blockOffsetY[8] = 256;
            }

            this.deep_cn = Math.min(5, GameTime.advance(this, "deep_cn", this.deep_cn, 1));
            break;
         case 2:
            for(var1 = 0; var1 < 9; ++var1) {
               this.blockOffsetY[var1] = 0;
            }

            this.deep_cn = Math.min(5, GameTime.advance(this, "deep_cn", this.deep_cn, 1));
            break;
         case 3:
            if (var2 == 1) {
               this.blockOffsetY[0] = 0;

               for(var1 = 1; var1 < 9; ++var1) {
                  this.blockOffsetY[var1] = -(9 - var1) << 6;
               }
            } else if (var2 == 7) {
               this.blockOffsetY[8] = 0;

               for(var1 = 0; var1 < 8; ++var1) {
                  this.blockOffsetY[var1] = -(var1 + 1) << 6;
               }
            } else if (var2 == 0) {
               this.blockOffsetY[0] = 0;
               var3 = this.blockOffsetY;
               this.blockOffsetY[2] = -192;
               var3[1] = -192;
               var3 = this.blockOffsetY;
               var4 = this.blockOffsetY;
               this.blockOffsetY[5] = -128;
               var4[4] = -128;
               var3[3] = -128;
               var3 = this.blockOffsetY;
               var4 = this.blockOffsetY;
               this.blockOffsetY[8] = -64;
               var4[7] = -64;
               var3[6] = -64;
            } else if (var2 == 8) {
               this.blockOffsetY[8] = 0;
               var3 = this.blockOffsetY;
               this.blockOffsetY[6] = -192;
               var3[7] = -192;
               var3 = this.blockOffsetY;
               var4 = this.blockOffsetY;
               this.blockOffsetY[3] = -128;
               var4[4] = -128;
               var3[5] = -128;
               var4 = this.blockOffsetY;
               var3 = this.blockOffsetY;
               this.blockOffsetY[0] = -64;
               var3[1] = -64;
               var4[2] = -64;
            } else {
               for(var1 = 0; var1 < 9; ++var1) {
                  if (var1 != var2 - 4 && var1 != var2 + 4) {
                     if (var1 != var2 - 3 && var1 != var2 + 3) {
                        if (var1 != var2 - 2 && var1 != var2 + 2) {
                           if (var1 != var2 - 1 && var1 != var2 + 1) {
                              if (var1 == var2) {
                                 this.blockOffsetY[var1] = -512;
                              } else {
                                 this.blockOffsetY[var1] = -64;
                              }
                           } else {
                              this.blockOffsetY[var1] = -448;
                           }
                        } else {
                           this.blockOffsetY[var1] = -320;
                        }
                     } else {
                        this.blockOffsetY[var1] = -128;
                     }
                  } else {
                     this.blockOffsetY[var1] = -64;
                  }
               }
            }

            if (this.blockOffsetY[0] <= 0) {
               this.blockOffsetY[0] = 0;
            }

            if (this.blockOffsetY[8] <= 0) {
               this.blockOffsetY[8] = 0;
            }

            this.deep_cn = Math.min(5, GameTime.advance(this, "deep_cn", this.deep_cn, 1));
            break;
         case 4:
            for(var1 = 0; var1 < 9; ++var1) {
               this.blockOffsetY[var1] = 0;
            }

            this.deep_cn = Math.min(5, GameTime.advance(this, "deep_cn", this.deep_cn, 1));
            break;
         case 5:
            this.type = 0;
            this.normal_cn = 0;
         }
      }

      if (this.collisionFlag && this.type == 0) {
         if (this.preblockID != var2) {
            this.normal_cn = 0;
         }

         this.preblockID = var2;
         this.deep_cn = GameTime.set(this, "deep_cn", 0);
         if (var2 == 1) {
            this.blockOffsetY[0] = 256;

            for(var1 = 1; var1 < 9; ++var1) {
               this.blockOffsetY[var1] = 9 - var1 << 6;
            }
         } else if (var2 == 7) {
            this.blockOffsetY[8] = 256;

            for(var1 = 0; var1 < 8; ++var1) {
               this.blockOffsetY[var1] = var1 + 1 << 6;
            }
         } else if (var2 == 0) {
            this.blockOffsetY[0] = 256;
            var3 = this.blockOffsetY;
            this.blockOffsetY[2] = 192;
            var3[1] = 192;
            var4 = this.blockOffsetY;
            var3 = this.blockOffsetY;
            this.blockOffsetY[5] = 128;
            var3[4] = 128;
            var4[3] = 128;
            var3 = this.blockOffsetY;
            var4 = this.blockOffsetY;
            this.blockOffsetY[8] = 64;
            var4[7] = 64;
            var3[6] = 64;
         } else if (var2 == 8) {
            this.blockOffsetY[8] = 256;
            var3 = this.blockOffsetY;
            this.blockOffsetY[6] = 192;
            var3[7] = 192;
            var4 = this.blockOffsetY;
            var3 = this.blockOffsetY;
            this.blockOffsetY[3] = 128;
            var3[4] = 128;
            var4[5] = 128;
            var4 = this.blockOffsetY;
            var3 = this.blockOffsetY;
            this.blockOffsetY[0] = 64;
            var3[1] = 64;
            var4[2] = 64;
         } else {
            for(var1 = 0; var1 < 9; ++var1) {
               if (var1 != var2 - 4 && var1 != var2 + 4) {
                  if (var1 != var2 - 3 && var1 != var2 + 3) {
                     if (var1 != var2 - 2 && var1 != var2 + 2) {
                        if (var1 != var2 - 1 && var1 != var2 + 1) {
                           if (var1 == var2) {
                              this.blockOffsetY[var1] = 512;
                           } else {
                              this.blockOffsetY[var1] = 64;
                           }
                        } else {
                           this.blockOffsetY[var1] = 448;
                        }
                     } else {
                        this.blockOffsetY[var1] = 320;
                     }
                  } else {
                     this.blockOffsetY[var1] = 128;
                  }
               } else {
                  this.blockOffsetY[var1] = 64;
               }
            }
         }

         if (this.blockOffsetY[0] >= 256) {
            this.blockOffsetY[0] = 256;
         }

         if (this.blockOffsetY[8] >= 256) {
            this.blockOffsetY[8] = 256;
         }
      }

   }

   public void doWhileCollision(PlayerObject var1, int var2) {
      this.collisionFlag = true;
      if (player.getVelY() > 1000) {
         this.type = 1;
         this.deep_cn = GameTime.set(this, "deep_cn", 0);
      } else if (player.getVelY() >= 0 && this.deep_cn == 5) {
         this.type = 0;
         this.deep_cn = GameTime.set(this, "deep_cn", 0);
      }

   }

   public void doWhileNoCollision() {
      this.collisionFlag = false;
      this.deep_cn = GameTime.set(this, "deep_cn", 0);
   }

   public void draw(MFGraphics var1) {
      for(int var2 = 0; var2 < 9; ++var2) {
         this.block[var2].draw(var1);
      }

      this.drawCollisionRect(var1);
   }

   public int getBossY(int var1) {
      var1 = (var1 - this.blockStartX + 1024) / 2048;
      return this.blockOrgPosY + this.blockOffsetY[var1];
   }

   public void logic() {
      this.refreshCollisionRect(this.posX, this.posY);
      if (this.collisionChkWithObject(player)) {
         this.doWhileCollisionWrap(player);
      } else if (this.deep_cn == 0) {
         this.doWhileNoCollision();
      }

      int var1;
      if (player.collisionState == 1) {
         for(var1 = 0; var1 < 9; ++var1) {
            this.blockOffsetY[var1] = 0;
         }
      }

      this.logicShake();

      for(var1 = 0; var1 < 9; ++var1) {
         this.block[var1].logic(this.blockStartX + var1 * 2048, this.blockOrgPosY + this.blockOffsetY[var1]);
      }

   }

   public void refreshCollisionRect(int var1, int var2) {
      CollisionRect var3 = this.collisionRect;
      var3.setRect(var1 - 9216, var2 - 768, 18432, 1536);
   }

   public void setDisplayState() {
      for(int var1 = 0; var1 < 9; ++var1) {
         this.block[var1].setDisplayState(false);
         Animation var4 = destroyEffectAnimation;
         int var2 = this.blockStartX;
         int var3 = this.posY;
         Effect.showEffect(var4, 0, var2 + var1 * 2048 >> 6, (var3 >> 6) - 10, 0);
      }

      PlayerObject var6 = player;
      PlayerObject var5 = player;
      var6.collisionState = 1;
      player.setAnimationId(10);
   }
}
