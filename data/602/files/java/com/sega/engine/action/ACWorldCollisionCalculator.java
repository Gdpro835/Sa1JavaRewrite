//
// Decompiled by FernFlower - 2391ms
//
package com.sega.engine.action;

import com.sega.engine.lib.CrlFP32;
import com.sega.engine.lib.MyAPI;

public class ACWorldCollisionCalculator extends ACMoveCalculator implements ACParam {
    private static final int BLOCK_CHECK_RANGE = 1;
    private static final int DIRECTION_OFFSET_DOWN = 0;
    private static final int DIRECTION_OFFSET_LEFT = 1;
    private static final int DIRECTION_OFFSET_RIGHT = 3;
    private static final int DIRECTION_OFFSET_UP = 2;
    private static final int DOWN_SEARCH_BLOCK = 8;
    public static final byte JUMP_ACTION_STATE = 1;
    public static final byte WALK_ACTION_STATE = 0;
    public byte actionState;
    private int bodyCollisionPointOffsetX;
    private int[] bodyCollisionPointOffsetY;
    private int[] checkArrayForShowX;
    private int[] checkArrayForShowY;
    private int chkOffsetX;
    private int chkOffsetY;
    private int chkPointDegree;
    private int chkPointId;
    private int chkPointX;
    private int chkPointY;
    private ACCollisionData collisionData;
    protected ACDegreeGetter degreeGetter;
    private ACDegreeGetter.DegreeReturner degreeRe = new ACDegreeGetter.DegreeReturner();
    private int[] footCollisionPointOffsetX;
    private int footCollisionPointOffsetY;
    private int[] footCollisionPointResaultX;
    private int[] footCollisionPointResaultY;
    public int footDegree;
    private int footOffsetX;
    private int footOffsetY;
    private int footX;
    private int footY;
    protected ACBlock getBlock;
    private int[] headCollisionPointOffsetX;
    private int headCollisionPointOffsetY;
    private boolean isMoved;
    private int lastMoveDistanceX = -9999;
    private int lastMoveDistanceY = -9999;
    private ACWorldCollisionLimit limit;
    private int movePassiveX;
    private int movePassiveY;
    private int preBodyOffset = -1;
    private int preFootOffset = -1;
    private int preHeight = -1;
    private int preWidth = -1;
    private int priorityChkId;
    private int totalDistance;
    private ACWorldCalUser user;

    public ACWorldCollisionCalculator(ACObject var1, ACWorldCalUser var2) {
        super(var1, var2);
        this.user = var2;
        this.degreeGetter = this.worldInstance.getDegreeGetterForObject();
        this.actionState = 1;
        int var5 = var1.getObjWidth();
        int var4 = var1.getObjHeight();
        int var6 = var2.getFootOffset();
        int var3 = var2.getBodyOffset();
        this.calPosition(var5, var4, var6, var3);
        if (this.getBlock == null) {
            this.getBlock = this.worldInstance.getNewCollisionBlock();
        }

        this.collisionData = new ACCollisionData();
    }

    private void calChkOffset(int var1, int var2, int var3, int var4) {
        int var5 = -this.footCollisionPointOffsetX[var3];
        int var6 = -this.footCollisionPointOffsetY;
        var5 = ACUtilities.getRelativePointX(var1, var5, var6, var4);
        var6 = -this.footCollisionPointOffsetX[var3];
        var3 = -this.footCollisionPointOffsetY;
        var3 = ACUtilities.getRelativePointY(var2, var6, var3, var4);
        this.chkOffsetX = var1 - var5;
        this.chkOffsetY = var2 - var3;
    }

    private void calChkPointFromPos() {
        this.chkPointX = this.footX + this.chkOffsetX;
        this.chkPointY = this.footY + this.chkOffsetY;
    }

    private void calObjPositionFromFoot() {
        this.footX = this.chkPointX - this.chkOffsetX;
        this.footY = this.chkPointY - this.chkOffsetY;
    }

    private void calPosition(int var1, int var2, int var3, int var4) {
        this.footCollisionPointOffsetX = new int[3];
        this.footCollisionPointResaultX = new int[3];
        this.footCollisionPointResaultY = new int[3];
        this.footCollisionPointOffsetX[0] = -(var1 - var3 * 2 >> 1);
        this.footCollisionPointOffsetX[3 - 1] = var1 - var3 * 2 >> 1;
        this.priorityChkId = 1;
        if (3 > 2) {
            int var5 = (var1 - var3 * 2) / (3 - 1);

            for(var3 = 1; var3 < 3 - 1; ++var3) {
                this.footCollisionPointOffsetX[var3] = this.footCollisionPointOffsetX[var3 - 1] + var5;
            }
        }

        this.footCollisionPointOffsetY = 0;
        this.headCollisionPointOffsetX = this.footCollisionPointOffsetX;
        this.headCollisionPointOffsetY = -var2;
        this.bodyCollisionPointOffsetX = var1 >> 1;
        var3 = (var2 - var4 * 2 - 1) / this.worldInstance.getTileHeight() + 2;
        this.bodyCollisionPointOffsetY = new int[var3];
        this.bodyCollisionPointOffsetY[0] = -var4;
        this.bodyCollisionPointOffsetY[var3 - 1] = -var2 + var4;
        if (var3 > 2) {
            var2 = (var2 - var4 * 2) / (var3 - 1);

            for(var1 = 1; var1 < var3 - 1; ++var1) {
                this.bodyCollisionPointOffsetY[var1] = this.bodyCollisionPointOffsetY[var1 - 1] - var2;
            }
        }

    }

    private boolean canBeSideStop(int var1) {
        boolean var3 = false;
        boolean var2 = var3;
        if (var1 == 3) {
            label54: {
                if (this.getDirectionByDegree(this.footDegree) != 0 || this.moveDistanceX > 0 && this.acObj.velX > 0) {
                    var2 = var3;
                    if (this.getDirectionByDegree(this.footDegree) != 2) {
                        break label54;
                    }

                    if (this.moveDistanceX < 0) {
                        var2 = var3;
                        if (this.acObj.velX < 0) {
                            break label54;
                        }
                    }
                }

                var2 = true;
            }
        }

        var3 = var2;
        if (var1 == 1) {
            if (this.getDirectionByDegree(this.footDegree) != 0 || this.moveDistanceX < 0 && this.acObj.velX < 0) {
                var3 = var2;
                if (this.getDirectionByDegree(this.footDegree) != 2) {
                    return var3;
                }

                if (this.moveDistanceX > 0) {
                    var3 = var2;
                    if (this.acObj.velX > 0) {
                        return var3;
                    }
                }
            }

            var3 = true;
        }

        return var3;
    }

    private void checkInGround() {
        this.moveToNextPosition();
    }

    private void checkInMap() {
        if (this.moveDistanceX == 0 && this.moveDistanceY == 0 && !this.isMoved) {
            this.findTheFootPoint();
            this.user.didAfterEveryMove(0, 0);
        }

        while(this.moveDistanceX != 0 || this.moveDistanceY != 0 || this.isMoved) {
            this.footX = this.user.getFootX();
            this.footY = this.user.getFootY();
            this.footOffsetX = this.footX - this.acObj.posX;
            this.footOffsetY = this.footY - this.acObj.posY;
            this.findTheFootPoint();
            int var1 = this.footX;
            int var2 = this.footY;
            switch (this.actionState) {
                case 0:
                    this.checkInGround();
                    break;
                case 1:
                    this.checkInSky();
            }

            this.acObj.posX = this.footX - this.footOffsetX;
            this.acObj.posY = this.footY - this.footOffsetY;
            this.user.didAfterEveryMove(this.footX - var1, this.footY - var2);
            if (this.isMoved) {
                break;
            }
        }

    }

    private void checkInSky() {
        byte var19 = 0;
        boolean var1;
        if (Math.abs(this.moveDistanceX) > Math.abs(this.moveDistanceY)) {
            var1 = true;
        } else {
            var1 = false;
        }

        int var24 = this.chkPointX;
        int var23 = this.chkPointY;
        int var2;
        int var27;
        if (var1) {
            if (Math.abs(this.moveDistanceX) > this.worldInstance.getTileWidth() - (1 << this.worldInstance.getZoom())) {
                var2 = this.chkPointX;
                if (this.moveDistanceX > 0) {
                    var27 = 1;
                } else {
                    var27 = -1;
                }

                this.chkPointX = var2 + var27 * (this.worldInstance.getTileWidth() - (1 << this.worldInstance.getZoom()));
                this.chkPointY += this.moveDistanceY * (this.worldInstance.getTileWidth() - (1 << this.worldInstance.getZoom())) / Math.abs(this.moveDistanceX);
            } else {
                this.chkPointX += this.moveDistanceX;
                this.chkPointY += this.moveDistanceY;
            }
        } else if (Math.abs(this.moveDistanceY) > this.worldInstance.getTileHeight() - (1 << this.worldInstance.getZoom())) {
            var2 = this.chkPointY;
            if (this.moveDistanceY > 0) {
                var27 = 1;
            } else {
                var27 = -1;
            }

            this.chkPointY = var2 + var27 * (this.worldInstance.getTileHeight() - (1 << this.worldInstance.getZoom()));
            this.chkPointX += this.moveDistanceX * (this.worldInstance.getTileHeight() - (1 << this.worldInstance.getZoom())) / Math.abs(this.moveDistanceY);
        } else {
            this.chkPointX += this.moveDistanceX;
            this.chkPointY += this.moveDistanceY;
        }

        this.calObjPositionFromFoot();
        boolean var6 = false;
        int var9 = 0;
        int var7 = 0;
        int var8 = 0;
        boolean var15 = false;
        int var14 = 0;
        int var17 = 0;
        byte var16 = 0;
        byte var22 = 0;
        byte var20 = 0;
        boolean var21 = false;
        int var25 = this.getDirectionByDegree(this.footDegree);
        boolean var11;
        if (var25 != 1 && var25 != 3) {
            var11 = false;
        } else {
            var11 = true;
        }

        this.rightSideCollisionChk(this.footX, this.footY, var25, this.collisionData);
        if (var11) {
            var27 = this.collisionData.newPosY;
        } else {
            var27 = this.collisionData.newPosX;
        }

        int var3;
        int var4;
        int var5;
        if (var27 != -1000) {
            if (var11) {
                var9 = Math.abs(var27 - this.footY);
            } else {
                var9 = Math.abs(var27 - this.footX);
            }

            var6 = true;
            var2 = this.footDegree;
            var5 = this.collisionData.collisionX;
            var3 = this.collisionData.collisionY;
            var4 = this.acObj.posZ;
            var7 = this.getDegreeFromWorld((var2 + 270) % 360, var5, var3, var4);
            var8 = var27;
        }

        byte var13 = 1;
        this.leftSideCollisionChk(this.footX, this.footY, var25, this.collisionData);
        if (var11) {
            var27 = this.collisionData.newPosY;
        } else {
            var27 = this.collisionData.newPosX;
        }

        boolean var12 = var6;
        var4 = var7;
        byte var28 = var13;
        var5 = var8;
        var2 = var9;
        int var10;
        int var18;
        int var29;
        if (var27 != -1000) {
            if (var6) {
                label246: {
                    if (var11) {
                        var10 = Math.abs(var27 - this.footY);
                    } else {
                        var10 = Math.abs(var27 - this.footX);
                    }

                    var18 = ACUtilities.getTotalFromDegree(this.acObj.velX, this.acObj.velY, this.footDegree + 180);
                    if (var18 <= 0) {
                        var12 = var6;
                        var4 = var7;
                        var28 = var13;
                        var5 = var8;
                        var2 = var9;
                        if (var18 != 0) {
                            break label246;
                        }

                        var12 = var6;
                        var4 = var7;
                        var28 = var13;
                        var5 = var8;
                        var2 = var9;
                        if (var10 >= var9) {
                            break label246;
                        }
                    }

                    var2 = var10;
                    var28 = 3;
                    var7 = this.footDegree;
                    var5 = this.collisionData.collisionX;
                    var8 = this.collisionData.collisionY;
                    var4 = this.acObj.posZ;
                    var4 = this.getDegreeFromWorld((var7 + 90) % 360, var5, var8, var4);
                    var5 = var27;
                    var12 = var6;
                }
            } else {
                if (var11) {
                    var2 = Math.abs(var27 - this.footY);
                } else {
                    var2 = Math.abs(var27 - this.footX);
                }

                var12 = true;
                var28 = 3;
                var4 = this.footDegree;
                var5 = this.collisionData.collisionX;
                var7 = this.collisionData.collisionY;
                var29 = this.acObj.posZ;
                var4 = this.getDegreeFromWorld((var4 + 90) % 360, var5, var7, var29);
                var5 = var27;
            }
        }

        this.upSideCollisionChk(this.footX, this.footY, var25, this.collisionData);
        var8 = var17;
        var7 = var14;
        var29 = var16;
        boolean var30 = var15;
        if (!this.collisionData.isNoCollision()) {
            var7 = this.footDegree;
            var27 = this.collisionData.collisionX;
            var8 = this.collisionData.collisionY;
            var29 = this.acObj.posZ;
            var27 = this.getDegreeFromWorld((var7 + 180) % 360, var27, var8, var29);
            var9 = (var27 + 90) % 360;
            var29 = this.moveDistanceX;
            var8 = MyAPI.dCos(var9);
            var7 = this.moveDistanceY;
            var9 = MyAPI.dSin(var9);
            var9 = (var29 * var8 + var7 * var9) / 100;
            var8 = var17;
            var7 = var14;
            var29 = var16;
            var30 = var15;
            if (var9 > 0) {
                if (var12) {
                    if (var11) {
                        var7 = Math.abs(this.collisionData.newPosX - this.footX);
                        var8 = this.collisionData.newPosX;
                    } else {
                        var7 = Math.abs(this.collisionData.newPosY - this.footY);
                        var8 = this.collisionData.newPosY;
                    }

                    var29 = var27;
                    var30 = true;
                } else {
                    if (var11) {
                        this.footX = this.collisionData.newPosX;
                    } else {
                        this.footY = this.collisionData.newPosY;
                    }

                    this.calChkPointFromPos();
                    this.user.doWhileTouchWorld(0, var27);
                    var7 = this.moveDistanceX;
                    var8 = MyAPI.dCos(var27);
                    var29 = this.moveDistanceY;
                    var9 = MyAPI.dSin(var27);
                    var29 = (var7 * var8 + var29 * var9) / 100;
                    this.moveDistanceX = MyAPI.dCos(var27) * var29 / 100;
                    this.moveDistanceY = MyAPI.dSin(var27) * var29 / 100;
                    var8 = this.acObj.velX;
                    var29 = MyAPI.dCos(var27);
                    var7 = this.acObj.velY;
                    var9 = MyAPI.dSin(var27);
                    var29 = (var8 * var29 + var7 * var9) / 100;
                    this.acObj.velX = MyAPI.dCos(var27) * var29 / 100;
                    this.acObj.velY = MyAPI.dSin(var27) * var29 / 100;
                    var8 = var17;
                    var7 = var14;
                    var29 = var16;
                    var30 = var15;
                }
            }
        }

        this.downSideCollisionChk(this.footX, this.footY, var25, this.collisionData);
        var18 = var8;
        var17 = var7;
        var14 = var29;
        var10 = var20;
        int var31 = var22;
        boolean var32 = var21;
        var27 = var19;
        if (!this.collisionData.isNoCollision()) {
            var10 = this.footDegree;
            var9 = this.collisionData.collisionX;
            var14 = this.collisionData.collisionY;
            var27 = this.acObj.posZ;
            var9 = this.getDegreeFromWorld(var10, var9, var14, var27);
            var31 = (var9 + 90) % 360;
            var14 = this.moveDistanceX;
            var27 = MyAPI.dCos(var31);
            var10 = this.moveDistanceY;
            var31 = MyAPI.dSin(var31);
            int var26 = (var14 * var27 + var10 * var31) / 100;
            var18 = var8;
            var17 = var7;
            var14 = var29;
            var10 = var20;
            var31 = var22;
            var32 = var21;
            var27 = var19;
            if (var26 > 0) {
                if (var12) {
                    if (var11) {
                        var7 = Math.abs(this.collisionData.newPosX - this.footX);
                        var29 = this.collisionData.newPosX;
                    } else {
                        var7 = Math.abs(this.collisionData.newPosY - this.footY);
                        var29 = this.collisionData.newPosY;
                    }

                    var14 = var9;
                    var32 = true;
                    var31 = this.collisionData.chkPointID;
                    var27 = var19;
                    var10 = var9;
                    var17 = var7;
                    var18 = var29;
                } else {
                    this.actionState = 0;
                    var27 = this.collisionData.collisionX;
                    var10 = -this.footCollisionPointOffsetX[this.collisionData.chkPointID];
                    var14 = -this.footCollisionPointOffsetY;
                    this.footX = ACUtilities.getRelativePointX(var27, var10, var14, var9);
                    var10 = this.collisionData.collisionY;
                    var27 = -this.footCollisionPointOffsetX[this.collisionData.chkPointID];
                    var14 = -this.footCollisionPointOffsetY;
                    this.footY = ACUtilities.getRelativePointY(var10, var27, var14, var9);
                    this.calChkPointFromPos();
                    this.user.doWhileTouchWorld(2, var9);
                    var27 = var9;
                    var18 = var8;
                    var17 = var7;
                    var14 = var29;
                    var10 = var20;
                    var31 = var22;
                    var32 = var21;
                }
            }
        }

        if ((var32 || var30) && var12) {
            if (var2 < var17) {
                this.user.doWhileTouchWorld(var28, var4);
                if (var11) {
                    this.footY = var5;
                    this.calChkPointFromPos();
                    this.moveDistanceY = 0;
                    this.acObj.velY = 0;
                } else if (this.canBeSideStop(var28)) {
                    this.footX = var5;
                    this.calChkPointFromPos();
                    this.moveDistanceX = 0;
                    this.acObj.velX = 0;
                }

                this.upSideCollisionChk(this.footX, this.footY, var25, this.collisionData);
                if (!this.collisionData.isNoCollision()) {
                    var4 = this.footDegree;
                    var5 = this.collisionData.collisionX;
                    var3 = this.collisionData.collisionY;
                    var2 = this.acObj.posZ;
                    var2 = this.getDegreeFromWorld(var4 + 180, var5, var3, var2);
                    var29 = (var2 + 90) % 360;
                    var5 = this.moveDistanceX;
                    var3 = MyAPI.dCos(var29);
                    var4 = this.moveDistanceY;
                    var29 = MyAPI.dSin(var29);
                    var3 = (var5 * var3 + var4 * var29) / 100;
                    if (var3 > 0) {
                        if (var11) {
                            this.footX = this.collisionData.newPosX;
                        } else {
                            this.footY = this.collisionData.newPosY;
                        }

                        this.calChkPointFromPos();
                        this.user.doWhileTouchWorld(0, var2);
                        var29 = this.moveDistanceX;
                        var4 = MyAPI.dCos(var2);
                        var5 = this.moveDistanceY;
                        var3 = MyAPI.dSin(var2);
                        var3 = (var29 * var4 + var5 * var3) / 100;
                        this.moveDistanceX = MyAPI.dCos(var2) * var3 / 100;
                        this.moveDistanceY = MyAPI.dSin(var2) * var3 / 100;
                        var5 = this.acObj.velX;
                        var4 = MyAPI.dCos(var2);
                        var29 = this.acObj.velY;
                        var3 = MyAPI.dSin(var2);
                        var3 = (var5 * var4 + var29 * var3) / 100;
                        this.acObj.velX = MyAPI.dCos(var2) * var3 / 100;
                        this.acObj.velY = MyAPI.dSin(var2) * var3 / 100;
                    }
                }

                this.downSideCollisionChk(this.footX, this.footY, var25, this.collisionData);
                var2 = var27;
                if (!this.collisionData.isNoCollision()) {
                    var3 = this.footDegree;
                    var2 = this.collisionData.collisionX;
                    var5 = this.collisionData.collisionY;
                    var4 = this.acObj.posZ;
                    var3 = this.getDegreeFromWorld(var3, var2, var5, var4);
                    var29 = (var3 + 90) % 360;
                    var5 = this.moveDistanceX;
                    var4 = MyAPI.dCos(var29);
                    var2 = this.moveDistanceY;
                    var29 = MyAPI.dSin(var29);
                    var4 = (var5 * var4 + var2 * var29) / 100;
                    var2 = var27;
                    if (var4 > 0) {
                        this.actionState = 0;
                        var4 = this.collisionData.collisionX;
                        var2 = -this.footCollisionPointOffsetX[this.collisionData.chkPointID];
                        var27 = -this.footCollisionPointOffsetY;
                        this.footX = ACUtilities.getRelativePointX(var4, var2, var27, var3);
                        var27 = this.collisionData.collisionY;
                        var4 = -this.footCollisionPointOffsetX[this.collisionData.chkPointID];
                        var2 = -this.footCollisionPointOffsetY;
                        this.footY = ACUtilities.getRelativePointY(var27, var4, var2, var3);
                        this.calChkPointFromPos();
                        this.user.doWhileTouchWorld(2, var3);
                        var2 = var3;
                    }
                }
            } else {
                if (var32) {
                    this.actionState = 0;
                    var4 = this.collisionData.collisionX;
                    var2 = -this.footCollisionPointOffsetX[var31];
                    var27 = -this.footCollisionPointOffsetY;
                    this.footX = ACUtilities.getRelativePointX(var4, var2, var27, var10);
                    var27 = this.collisionData.collisionY;
                    var2 = -this.footCollisionPointOffsetX[var31];
                    var4 = -this.footCollisionPointOffsetY;
                    this.footY = ACUtilities.getRelativePointY(var27, var2, var4, var10);
                    this.calChkPointFromPos();
                    this.user.doWhileTouchWorld(2, var10);
                    var27 = var10;
                } else {
                    if (var11) {
                        this.footX = var18;
                    } else {
                        this.footY = var18;
                    }

                    this.calChkPointFromPos();
                    this.user.doWhileTouchWorld(0, var14);
                    var5 = this.moveDistanceX;
                    var29 = MyAPI.dCos(var14);
                    var4 = this.moveDistanceY;
                    var2 = MyAPI.dSin(var14);
                    var2 = (var5 * var29 + var4 * var2) / 100;
                    this.moveDistanceX = MyAPI.dCos(var14) * var2 / 100;
                    this.moveDistanceY = MyAPI.dSin(var14) * var2 / 100;
                    var4 = this.acObj.velX;
                    var2 = MyAPI.dCos(var14);
                    var5 = this.acObj.velY;
                    var29 = MyAPI.dSin(var14);
                    var2 = (var4 * var2 + var5 * var29) / 100;
                    this.acObj.velX = MyAPI.dCos(var14) * var2 / 100;
                    this.acObj.velY = MyAPI.dSin(var14) * var2 / 100;
                }

                this.rightSideCollisionChk(this.footX, this.footY, var25, this.collisionData);
                if (var11) {
                    var2 = this.collisionData.newPosY;
                } else {
                    var2 = this.collisionData.newPosX;
                }

                if (var2 != -1000) {
                    var4 = this.footDegree;
                    var5 = this.collisionData.collisionX;
                    var29 = this.collisionData.collisionY;
                    var7 = this.acObj.posZ;
                    var4 = this.getDegreeFromWorld((var4 + 270) % 360, var5, var29, var7);
                    this.user.doWhileTouchWorld(1, var4);
                    if (var11) {
                        this.footY = var2;
                        this.moveDistanceY = 0;
                        this.acObj.velY = 0;
                    } else if (this.canBeSideStop(var28)) {
                        this.footX = var2;
                        this.moveDistanceX = 0;
                        this.acObj.velX = 0;
                    }

                    this.calChkPointFromPos();
                }

                this.leftSideCollisionChk(this.footX, this.footY, var25, this.collisionData);
                if (var11) {
                    var4 = this.collisionData.newPosY;
                } else {
                    var4 = this.collisionData.newPosX;
                }

                var2 = var27;
                if (var4 != -1000) {
                    var5 = this.footDegree;
                    var7 = this.collisionData.collisionX;
                    var2 = this.collisionData.collisionY;
                    var29 = this.acObj.posZ;
                    var2 = this.getDegreeFromWorld((var5 + 90) % 360, var7, var2, var29);
                    this.user.doWhileTouchWorld(3, var2);
                    if (var11) {
                        this.footY = var4;
                        this.moveDistanceY = 0;
                        this.acObj.velY = 0;
                    } else if (this.canBeSideStop(var28)) {
                        this.footX = var4;
                        this.moveDistanceX = 0;
                        this.acObj.velX = 0;
                    }

                    this.calChkPointFromPos();
                    var2 = var27;
                }
            }
        } else {
            var2 = var27;
            if (var12) {
                this.user.doWhileTouchWorld(var28, var4);
                if (var11) {
                    this.footY = var5;
                    this.moveDistanceY = 0;
                    this.acObj.velY = 0;
                } else if (this.canBeSideStop(var28)) {
                    this.footX = var5;
                    this.moveDistanceX = 0;
                    this.acObj.velX = 0;
                }

                this.calChkPointFromPos();
                this.upSideCollisionChk(this.footX, this.footY, var25, this.collisionData);
                if (!this.collisionData.isNoCollision()) {
                    var2 = this.footDegree;
                    var3 = this.collisionData.collisionX;
                    var5 = this.collisionData.collisionY;
                    var4 = this.acObj.posZ;
                    var2 = this.getDegreeFromWorld(var2 + 180, var3, var5, var4);
                    var29 = (var2 + 90) % 360;
                    var5 = this.moveDistanceX;
                    var4 = MyAPI.dCos(var29);
                    var3 = this.moveDistanceY;
                    var29 = MyAPI.dSin(var29);
                    var3 = (var5 * var4 + var3 * var29) / 100;
                    if (var3 > 0) {
                        if (var11) {
                            this.footX = this.collisionData.newPosX;
                        } else {
                            this.footY = this.collisionData.newPosY;
                        }

                        this.calChkPointFromPos();
                        this.user.doWhileTouchWorld(0, var2);
                        var4 = this.moveDistanceX;
                        var3 = MyAPI.dCos(var2);
                        var29 = this.moveDistanceY;
                        var5 = MyAPI.dSin(var2);
                        var3 = (var4 * var3 + var29 * var5) / 100;
                        this.moveDistanceX = MyAPI.dCos(var2) * var3 / 100;
                        this.moveDistanceY = MyAPI.dSin(var2) * var3 / 100;
                        var4 = this.acObj.velX;
                        var5 = MyAPI.dCos(var2);
                        var29 = this.acObj.velY;
                        var3 = MyAPI.dSin(var2);
                        var3 = (var4 * var5 + var29 * var3) / 100;
                        this.acObj.velX = MyAPI.dCos(var2) * var3 / 100;
                        this.acObj.velY = MyAPI.dSin(var2) * var3 / 100;
                    }
                }

                this.downSideCollisionChk(this.footX, this.footY, var25, this.collisionData);
                var2 = var27;
                if (!this.collisionData.isNoCollision()) {
                    var3 = this.footDegree;
                    var4 = this.collisionData.collisionX;
                    var2 = this.collisionData.collisionY;
                    var5 = this.acObj.posZ;
                    var3 = this.getDegreeFromWorld(var3, var4, var2, var5);
                    var29 = (var3 + 90) % 360;
                    var4 = this.moveDistanceX;
                    var5 = MyAPI.dCos(var29);
                    var2 = this.moveDistanceY;
                    var29 = MyAPI.dSin(var29);
                    var4 = (var4 * var5 + var2 * var29) / 100;
                    var2 = var27;
                    if (var4 > 0) {
                        this.actionState = 0;
                        var4 = this.collisionData.collisionX;
                        var27 = -this.footCollisionPointOffsetX[this.collisionData.chkPointID];
                        var2 = -this.footCollisionPointOffsetY;
                        this.footX = ACUtilities.getRelativePointX(var4, var27, var2, var3);
                        var27 = this.collisionData.collisionY;
                        var4 = -this.footCollisionPointOffsetX[this.collisionData.chkPointID];
                        var2 = -this.footCollisionPointOffsetY;
                        this.footY = ACUtilities.getRelativePointY(var27, var4, var2, var3);
                        this.calChkPointFromPos();
                        this.user.doWhileTouchWorld(2, var3);
                        var2 = var3;
                    }
                }
            }
        }

        var27 = this.moveDistanceX;
        var3 = this.moveDistanceY;
        if (this.lastMoveDistanceX == 0 && this.chkPointX - var24 == 0 && this.lastMoveDistanceY == 0 && this.chkPointY - var23 == 0) {
            this.lastMoveDistanceX = -9999;
            this.lastMoveDistanceY = -9999;
            this.moveDistanceX = 0;
            this.moveDistanceY = 0;
        } else {
            this.lastMoveDistanceX = this.chkPointX - var24;
            this.lastMoveDistanceY = this.chkPointY - var23;
            this.moveDistanceX -= this.chkPointX - var24;
            this.moveDistanceY -= this.chkPointY - var23;
        }

        if (this.moveDistanceX * var27 <= 0) {
            this.moveDistanceX = 0;
        }

        if (this.moveDistanceY * var3 <= 0) {
            this.moveDistanceY = 0;
        }

        if (this.actionState == 0) {
            this.totalDistance = ACUtilities.getTotalFromDegree(this.moveDistanceX, this.moveDistanceY, var2);
            this.footDegree = var2;
            this.user.doWhileLand(this.footDegree);
        }

        this.calObjPositionFromFoot();
    }

    private void doSideCheckInGround(int var1) {
        int var2;
        int var3;
        int var4;
        int var5;
        int var6;
        ACWorldCalUser var7;
        byte var8;
        short var9;
        if (this.moveDistanceX > 0) {
            if (var1 == 0) {
                this.rightSideCollisionChk(this.footX, this.footY, var1, this.collisionData);
                var2 = this.collisionData.newPosX;
            } else {
                this.leftSideCollisionChk(this.footX, this.footY, var1, this.collisionData);
                var2 = this.collisionData.newPosX;
            }

            if (var2 != -1000) {
                this.footX = var2;
                this.calChkPointFromPos();
                var3 = this.footDegree;
                if (var1 == 0) {
                    var9 = 270;
                } else {
                    var9 = 90;
                }

                var6 = this.collisionData.collisionX;
                var5 = this.collisionData.collisionY;
                var4 = this.acObj.posZ;
                var2 = this.getDegreeFromWorld((var3 + var9) % 360, var6, var5, var4);
                var7 = this.user;
                if (var1 == 0) {
                    var8 = 1;
                } else {
                    var8 = 3;
                }

                var7.doWhileTouchWorld(var8, var2);
                this.moveDistanceX = 0;
                this.acObj.velX = 0;
            }
        } else if (this.moveDistanceX < 0) {
            if (var1 == 0) {
                this.leftSideCollisionChk(this.footX, this.footY, var1, this.collisionData);
                var2 = this.collisionData.newPosX;
            } else {
                this.rightSideCollisionChk(this.footX, this.footY, var1, this.collisionData);
                var2 = this.collisionData.newPosX;
            }

            if (var2 != -1000) {
                this.footX = var2;
                this.calChkPointFromPos();
                var3 = this.footDegree;
                if (var1 == 0) {
                    var9 = 90;
                } else {
                    var9 = 270;
                }

                var5 = this.collisionData.collisionX;
                var4 = this.collisionData.collisionY;
                var6 = this.acObj.posZ;
                var2 = this.getDegreeFromWorld((var3 + var9) % 360, var5, var4, var6);
                var7 = this.user;
                if (var1 == 0) {
                    var8 = 3;
                } else {
                    var8 = 1;
                }

                var7.doWhileTouchWorld(var8, var2);
                this.moveDistanceX = 0;
                this.acObj.velX = 0;
            }
        }

    }

    private void downSideCollisionChk(int var1, int var2, int var3, ACCollisionData var4) {
        var4.reset();
        if (this.limit == null || !this.limit.noDownCollision()) {
            int var7 = -1;
            int var8 = -1;
            int var5;
            int var6;
            int var9;
            int var10;
            int var11;
            int var12;
            int var13;
            switch (var3) {
                case 0:
                case 2:
                    var6 = -1000;
                    var5 = 0;

                    while(true) {
                        if (var5 >= this.footCollisionPointOffsetX.length) {
                            var8 = var6;
                            var9 = var7;
                            break;
                        }

                        var9 = ACUtilities.getRelativePointX(var1, this.footCollisionPointOffsetX[var5], this.footCollisionPointOffsetY, this.footDegree);
                        var12 = ACUtilities.getRelativePointY(var2, this.footCollisionPointOffsetX[var5], this.footCollisionPointOffsetY, this.footDegree);
                        var13 = this.getWorldY(var9, var12, (var3 + 0) % 4);
                        var11 = var7;
                        var10 = var6;
                        var9 = var8;
                        if (var13 != -1000) {
                            label78: {
                                var12 = Math.abs(var12 - var13);
                                if (var6 != -1000 && var12 <= var8) {
                                    var11 = var7;
                                    var10 = var6;
                                    var9 = var8;
                                    if (var5 != this.priorityChkId) {
                                        break label78;
                                    }
                                }

                                var9 = var5;
                                var8 = var13;
                                if (var5 == this.priorityChkId) {
                                    break;
                                }

                                var9 = var12;
                                var10 = var13;
                                var11 = var5;
                            }
                        }

                        ++var5;
                        var7 = var11;
                        var6 = var10;
                        var8 = var9;
                    }

                    if (var8 != -1000) {
                        var3 = ACUtilities.getRelativePointX(var1, this.footCollisionPointOffsetX[var9], this.footCollisionPointOffsetY, this.footDegree);
                        ACUtilities.getRelativePointY(var2, this.footCollisionPointOffsetX[var9], this.footCollisionPointOffsetY, this.footDegree);
                        this.worldInstance.getCollisionBlock(this.getBlock, var3, var8, this.acObj.posZ);
                        var4.collisionX = var3;
                        var4.collisionY = var8;
                        var4.newPosX = var1;
                        var4.newPosY = ACUtilities.getRelativePointY(var8, -this.footCollisionPointOffsetX[var9], -this.footCollisionPointOffsetY, this.footDegree);
                        var4.reBlock = this.getBlock;
                        var4.chkPointID = var9;
                    }
                    break;
                case 1:
                default:
                    var6 = -1000;
                    var5 = 0;

                    while(true) {
                        if (var5 >= this.footCollisionPointOffsetX.length) {
                            var8 = var6;
                            var9 = var7;
                            break;
                        }

                        var12 = ACUtilities.getRelativePointX(var1, this.footCollisionPointOffsetX[var5], this.footCollisionPointOffsetY, this.footDegree);
                        var9 = ACUtilities.getRelativePointY(var2, this.footCollisionPointOffsetX[var5], this.footCollisionPointOffsetY, this.footDegree);
                        var13 = this.getWorldX(var12, var9, (var3 + 0) % 4);
                        var11 = var7;
                        var10 = var6;
                        var9 = var8;
                        if (var13 != -1000) {
                            label79: {
                                var12 = Math.abs(var12 - var13);
                                if (var6 != -1000 && var12 <= var8) {
                                    var11 = var7;
                                    var10 = var6;
                                    var9 = var8;
                                    if (var5 != this.priorityChkId) {
                                        break label79;
                                    }
                                }

                                var9 = var5;
                                var8 = var13;
                                if (var5 == this.priorityChkId) {
                                    break;
                                }

                                var9 = var12;
                                var10 = var13;
                                var11 = var5;
                            }
                        }

                        ++var5;
                        var7 = var11;
                        var6 = var10;
                        var8 = var9;
                    }

                    if (var8 != -1000) {
                        ACUtilities.getRelativePointX(var1, this.footCollisionPointOffsetX[var9], this.footCollisionPointOffsetY, this.footDegree);
                        var1 = ACUtilities.getRelativePointY(var2, this.footCollisionPointOffsetX[var9], this.footCollisionPointOffsetY, this.footDegree);
                        this.worldInstance.getCollisionBlock(this.getBlock, var8, var1, this.acObj.posZ);
                        var4.collisionX = var8;
                        var4.collisionY = var1;
                        var4.newPosY = var2;
                        var4.newPosX = ACUtilities.getRelativePointX(var8, -this.footCollisionPointOffsetX[var9], -this.footCollisionPointOffsetY, this.footDegree);
                        var4.reBlock = this.getBlock;
                        var4.chkPointID = var9;
                    }
            }
        }

    }

    private void findTheFootPoint() {
        int var1;
        int var2;
        int var3;
        int var4;
        if (this.actionState == 1) {
            var2 = this.footX;
            var4 = this.footCollisionPointOffsetX[this.priorityChkId];
            var1 = this.footCollisionPointOffsetY;
            var3 = this.footDegree;
            this.chkPointX = ACUtilities.getRelativePointX(var2, var4, var1, var3);
            var1 = this.footY;
            var4 = this.footCollisionPointOffsetX[this.priorityChkId];
            var3 = this.footCollisionPointOffsetY;
            var2 = this.footDegree;
            this.chkPointY = ACUtilities.getRelativePointY(var1, var4, var3, var2);
            this.chkPointId = this.priorityChkId;
            this.chkPointDegree = this.footDegree;
            this.calChkOffset(this.chkPointX, this.chkPointY, this.chkPointId, this.chkPointDegree);
        } else {
            int var6 = this.getDirectionByDegree(this.footDegree);
            int var5;
            int var7;
            int var8;
            int var9;
            int[] var11;
            switch (var6) {
                case 0:
                case 2:
                    var7 = this.footDegree;
                    var3 = -1000;
                    var2 = -1;
                    var1 = 0;

                    while(true) {
                        if (var1 >= this.footCollisionPointOffsetX.length) {
                            var4 = var2;
                            break;
                        }

                        var11 = this.footCollisionPointResaultX;
                        var4 = this.footX;
                        var5 = this.footCollisionPointOffsetX[var1];
                        var8 = this.footCollisionPointOffsetY;
                        var4 = ACUtilities.getRelativePointX(var4, var5, var8, var7);
                        var11[var1] = var4;
                        var11 = this.footCollisionPointResaultY;
                        var4 = this.footCollisionPointResaultX[var1];
                        int var10 = this.footY;
                        var9 = this.footCollisionPointOffsetX[var1];
                        var8 = this.footCollisionPointOffsetY;
                        var5 = this.user.getPressToGround();
                        var5 = ACUtilities.getRelativePointY(var10, var9, var8 + var5, var7);
                        var11[var1] = this.getWorldY(var4, var5, var6);
                        var5 = var3;
                        var4 = var2;
                        if (this.footCollisionPointResaultY[var1] != -1000) {
                            label113: {
                                if (var3 != -1000 && (var6 != 0 || this.footCollisionPointResaultY[var1] >= var3) && (var6 != 2 || this.footCollisionPointResaultY[var1] <= var3)) {
                                    var5 = var3;
                                    var4 = var2;
                                    if (var1 != this.priorityChkId) {
                                        break label113;
                                    }
                                }

                                var5 = this.footCollisionPointResaultY[var1];
                                var3 = var5;
                                var4 = var1;
                                if (var1 == this.priorityChkId) {
                                    break;
                                }

                                var4 = var1;
                            }
                        }

                        ++var1;
                        var3 = var5;
                        var2 = var4;
                    }

                    if (var4 != -1) {
                        this.chkPointX = this.footCollisionPointResaultX[var4];
                        this.chkPointY = var3;
                        this.chkPointId = var4;
                        this.chkPointDegree = this.footDegree;
                        this.calChkOffset(this.chkPointX, this.chkPointY, this.chkPointId, this.chkPointDegree);
                        this.footDegree = this.getDegreeFromWorld(this.footDegree, this.chkPointX, this.chkPointY, this.acObj.posZ);
                        this.actionState = 0;
                        var4 = (this.footDegree + 90) % 360;
                        var1 = this.moveDistanceX;
                        var2 = MyAPI.dCos(var4);
                        var3 = this.moveDistanceY;
                        var4 = MyAPI.dSin(var4);
                        var2 = (var1 * var2 + var3 * var4) / 100;
                        var1 = this.user.getPressToGround();
                        var3 = CrlFP32.actTanDegree(this.acObj.velY, this.acObj.velX);
                        var3 = this.getDegreeDiff(this.footDegree, var3);
                        if (var2 + var1 < 0 && var3 > this.user.getMinDegreeToLeaveGround()) {
                            this.user.doWhileLeaveGround();
                            var3 = this.footX;
                            var1 = this.footCollisionPointOffsetX[this.priorityChkId];
                            var2 = this.footCollisionPointOffsetY;
                            this.chkPointX = ACUtilities.getRelativePointX(var3, var1, var2, var7);
                            var3 = this.footY;
                            var2 = this.footCollisionPointOffsetX[this.priorityChkId];
                            var1 = this.footCollisionPointOffsetY;
                            this.chkPointY = ACUtilities.getRelativePointY(var3, var2, var1, var7);
                            this.chkPointId = this.priorityChkId;
                            this.chkPointDegree = this.footDegree;
                            this.calChkOffset(this.chkPointX, this.chkPointY, this.chkPointId, this.chkPointDegree);
                            this.actionState = 1;
                        }
                    } else {
                        this.user.doWhileLeaveGround();
                        var2 = this.footX;
                        var3 = this.footCollisionPointOffsetX[this.priorityChkId];
                        var1 = this.footCollisionPointOffsetY;
                        this.chkPointX = ACUtilities.getRelativePointX(var2, var3, var1, var7);
                        var2 = this.footY;
                        var1 = this.footCollisionPointOffsetX[this.priorityChkId];
                        var3 = this.footCollisionPointOffsetY;
                        this.chkPointY = ACUtilities.getRelativePointY(var2, var1, var3, var7);
                        this.chkPointId = this.priorityChkId;
                        this.chkPointDegree = this.footDegree;
                        this.calChkOffset(this.chkPointX, this.chkPointY, this.chkPointId, this.chkPointDegree);
                        this.actionState = 1;
                    }
                    break;
                case 1:
                case 3:
                    var2 = -1000;
                    var7 = this.footDegree;
                    var3 = -1;
                    if (this.checkArrayForShowX == null) {
                        this.checkArrayForShowX = new int[this.footCollisionPointOffsetX.length];
                        this.checkArrayForShowY = new int[this.footCollisionPointOffsetX.length];
                    }

                    var1 = 0;

                    while(true) {
                        if (var1 >= this.footCollisionPointOffsetX.length) {
                            var4 = var2;
                            break;
                        }

                        var11 = this.footCollisionPointResaultY;
                        var4 = this.footY;
                        var5 = this.footCollisionPointOffsetX[var1];
                        var8 = this.footCollisionPointOffsetY;
                        var4 = ACUtilities.getRelativePointY(var4, var5, var8, var7);
                        var11[var1] = var4;
                        var11 = this.checkArrayForShowX;
                        var9 = this.footX;
                        var8 = this.footCollisionPointOffsetX[var1];
                        var5 = this.footCollisionPointOffsetY;
                        var4 = this.user.getPressToGround();
                        var11[var1] = ACUtilities.getRelativePointX(var9, var8, var5 + var4, var7);
                        this.checkArrayForShowY[var1] = this.footCollisionPointResaultY[var1];
                        var11 = this.footCollisionPointResaultX;
                        var5 = this.footX;
                        var8 = this.footCollisionPointOffsetX[var1];
                        var4 = this.footCollisionPointOffsetY;
                        var9 = this.user.getPressToGround();
                        var5 = ACUtilities.getRelativePointX(var5, var8, var4 + var9, var7);
                        var4 = this.footCollisionPointResaultY[var1];
                        var11[var1] = this.getWorldX(var5, var4, var6);
                        var5 = var2;
                        var4 = var3;
                        if (this.footCollisionPointResaultX[var1] != -1000) {
                            label112: {
                                if (var2 != -1000 && (var6 != 1 || this.footCollisionPointResaultX[var1] <= var2) && (var6 != 3 || this.footCollisionPointResaultX[var1] >= var2)) {
                                    var5 = var2;
                                    var4 = var3;
                                    if (var1 != this.priorityChkId) {
                                        break label112;
                                    }
                                }

                                var5 = this.footCollisionPointResaultX[var1];
                                var4 = var5;
                                var3 = var1;
                                if (var1 == this.priorityChkId) {
                                    break;
                                }

                                var4 = var1;
                            }
                        }

                        ++var1;
                        var2 = var5;
                        var3 = var4;
                    }

                    if (var3 != -1) {
                        this.chkPointX = var4;
                        this.chkPointY = this.footCollisionPointResaultY[var3];
                        this.chkPointId = var3;
                        this.chkPointDegree = this.footDegree;
                        this.footDegree = this.getDegreeFromWorld(this.footDegree, this.chkPointX, this.chkPointY, this.acObj.posZ);
                        this.calChkOffset(this.chkPointX, this.chkPointY, this.chkPointId, this.chkPointDegree);
                        this.actionState = 0;
                        var4 = (this.footDegree + 90) % 360;
                        var1 = this.moveDistanceX;
                        var2 = MyAPI.dCos(var4);
                        var3 = this.moveDistanceY;
                        var4 = MyAPI.dSin(var4);
                        var2 = (var1 * var2 + var3 * var4) / 100;
                        var1 = this.user.getPressToGround();
                        var3 = CrlFP32.actTanDegree(this.moveDistanceY, this.moveDistanceX);
                        var3 = this.getDegreeDiff(this.footDegree, var3);
                        if (var2 + var1 < 0 && var3 > this.user.getMinDegreeToLeaveGround()) {
                            this.user.doWhileLeaveGround();
                            var1 = this.footX;
                            var3 = this.footCollisionPointOffsetX[this.priorityChkId];
                            var2 = this.footCollisionPointOffsetY;
                            this.chkPointX = ACUtilities.getRelativePointX(var1, var3, var2, var7);
                            var1 = this.footY;
                            var3 = this.footCollisionPointOffsetX[this.priorityChkId];
                            var2 = this.footCollisionPointOffsetY;
                            this.chkPointY = ACUtilities.getRelativePointY(var1, var3, var2, var7);
                            this.chkPointId = this.priorityChkId;
                            this.chkPointDegree = this.footDegree;
                            this.calChkOffset(this.chkPointX, this.chkPointY, this.chkPointId, this.chkPointDegree);
                            this.actionState = 1;
                        }
                    } else {
                        this.user.doWhileLeaveGround();
                        var2 = this.footX;
                        var1 = this.footCollisionPointOffsetX[this.priorityChkId];
                        var3 = this.footCollisionPointOffsetY;
                        this.chkPointX = ACUtilities.getRelativePointX(var2, var1, var3, var7);
                        var1 = this.footY;
                        var3 = this.footCollisionPointOffsetX[this.priorityChkId];
                        var2 = this.footCollisionPointOffsetY;
                        this.chkPointY = ACUtilities.getRelativePointY(var1, var3, var2, var7);
                        this.chkPointId = this.priorityChkId;
                        this.chkPointDegree = this.footDegree;
                        this.calChkOffset(this.chkPointX, this.chkPointY, this.chkPointId, this.chkPointDegree);
                        this.actionState = 1;
                    }
            }
        }

    }

    private int getBlockDownSide(int var1, int var2) {
        return (var2 + 1) * this.worldInstance.getTileHeight() - 1;
    }

    private int getBlockLeftSide(int var1, int var2) {
        return (var1 + 0) * this.worldInstance.getTileWidth();
    }

    private int getBlockRightSide(int var1, int var2) {
        return (var1 + 1) * this.worldInstance.getTileWidth() - 1;
    }

    private int getBlockUpSide(int var1, int var2) {
        return (var2 + 0) * this.worldInstance.getTileHeight();
    }

    private int getDegreeFromWorld(int var1, int var2, int var3, int var4) {
        while(var1 < 0) {
            var1 += 360;
        }

        var1 %= 360;
        if (this.degreeGetter != null) {
            this.degreeGetter.getDegreeFromWorldByPosition(this.degreeRe, var1, var2, var3, var4);
            var1 = this.degreeRe.degree;
        }

        return var1;
    }

    private int getWorldX(int var1, int var2, int var3) {
        return this.worldInstance.getWorldX(var1, var2, this.acObj.posZ, var3);
    }

    private int getWorldY(int var1, int var2, int var3) {
        return this.worldInstance.getWorldY(var1, var2, this.acObj.posZ, var3);
    }

    private void leftSideCollisionChk(int var1, int var2, int var3, ACCollisionData var4) {
        var4.reset();
        if (this.limit == null || !this.limit.noSideCollision()) {
            int var5;
            int var6;
            int var7;
            int var8;
            int var9;
            int var10;
            int var11;
            int var12;
            int var13;
            int var14;
            ACWorld var15;
            ACBlock var16;
            switch (var3) {
                case 0:
                case 2:
                    var6 = -1000;
                    var8 = -1;
                    var7 = -1;
                    var5 = 0;

                    while(true) {
                        if (var5 >= this.bodyCollisionPointOffsetY.length) {
                            if (var6 != -1000) {
                                var5 = this.bodyCollisionPointOffsetX;
                                var7 = -this.bodyCollisionPointOffsetY[var8];
                                var8 = this.user.getBodyDegree();
                                var4.newPosX = ACUtilities.getRelativePointX(var6, var5, var7, var8);
                                var15 = this.worldInstance;
                                var16 = this.getBlock;
                                var7 = var4.collisionX;
                                var5 = var4.collisionY;
                                var6 = this.acObj.posZ;
                                var15.getCollisionBlock(var16, var7, var5, var6);
                                var4.reBlock = this.getBlock;
                            }
                            break;
                        }

                        var10 = -this.bodyCollisionPointOffsetX;
                        var9 = this.bodyCollisionPointOffsetY[var5];
                        var11 = this.user.getBodyDegree();
                        var12 = ACUtilities.getRelativePointX(var1, var10, var9, var11);
                        var11 = -this.bodyCollisionPointOffsetX;
                        var9 = this.bodyCollisionPointOffsetY[var5];
                        var10 = this.user.getBodyDegree();
                        var14 = ACUtilities.getRelativePointY(var2, var11, var9, var10);
                        var13 = this.getWorldX(var12, var14, (var3 + 1) % 4);
                        var11 = var8;
                        var10 = var6;
                        var9 = var7;
                        if (var13 != -1000) {
                            label67: {
                                var12 = Math.abs(var13 - var12);
                                if (var6 != -1000) {
                                    var11 = var8;
                                    var10 = var6;
                                    var9 = var7;
                                    if (var12 <= var7) {
                                        break label67;
                                    }
                                }

                                var10 = var13;
                                var11 = var5;
                                var9 = var12;
                                var4.collisionX = var13;
                                var4.collisionY = var14;
                                var4.newPosY = var2;
                            }
                        }

                        ++var5;
                        var8 = var11;
                        var6 = var10;
                        var7 = var9;
                    }
                case 1:
            }

            var6 = -1000;
            var8 = -1;
            var7 = -1;

            for(var5 = 0; var5 < this.bodyCollisionPointOffsetY.length; var7 = var9) {
                var9 = -this.bodyCollisionPointOffsetX;
                var10 = this.bodyCollisionPointOffsetY[var5];
                var11 = this.user.getBodyDegree();
                var13 = ACUtilities.getRelativePointX(var1, var9, var10, var11);
                var9 = -this.bodyCollisionPointOffsetX;
                var11 = this.bodyCollisionPointOffsetY[var5];
                var10 = this.user.getBodyDegree();
                var14 = ACUtilities.getRelativePointY(var2, var9, var11, var10);
                var12 = this.getWorldY(var13, var14, (var3 + 1) % 4);
                var11 = var8;
                var10 = var6;
                var9 = var7;
                if (var12 != -1000) {
                    label68: {
                        var14 = Math.abs(var12 - var14);
                        if (var6 != -1000) {
                            var11 = var8;
                            var10 = var6;
                            var9 = var7;
                            if (var14 <= var7) {
                                break label68;
                            }
                        }

                        var10 = var12;
                        var11 = var5;
                        var9 = var7 - var14;
                        var4.collisionX = var13;
                        var4.collisionY = var12;
                        var4.newPosX = var1;
                    }
                }

                ++var5;
                var8 = var11;
                var6 = var10;
            }

            if (var6 != -1000) {
                var1 = this.bodyCollisionPointOffsetX;
                var2 = -this.bodyCollisionPointOffsetY[var8];
                var3 = this.user.getBodyDegree();
                var4.newPosX = ACUtilities.getRelativePointY(var6, var1, var2, var3);
                var15 = this.worldInstance;
                var16 = this.getBlock;
                var3 = var4.collisionX;
                var2 = var4.collisionY;
                var1 = this.acObj.posZ;
                var15.getCollisionBlock(var16, var3, var2, var1);
                var4.reBlock = this.getBlock;
            }
        }

    }

    private void moveToNextPosition() {
        int var5 = ACUtilities.getQuaParam(this.chkPointX, this.worldInstance.getTileWidth());
        int var1 = ACUtilities.getQuaParam(this.chkPointY, this.worldInstance.getTileHeight());
        this.worldInstance.getCollisionBlock(this.getBlock, this.chkPointX, this.chkPointY, this.acObj.posZ);
        this.user.getBodyDegree();
        int var3 = this.chkPointX;
        int var2 = this.chkPointY;
        this.moveDistanceX = this.totalDistance * MyAPI.dCos(this.user.getBodyDegree()) / 100;
        this.moveDistanceY = this.totalDistance * MyAPI.dSin(this.user.getBodyDegree()) / 100;
        int var4 = this.getDirectionByDegree(this.user.getBodyDegree());
        int var6;
        int var7;
        short var11;
        if (var4 != 0 && var4 != 2) {
            if (this.moveDistanceY == 0) {
                this.moveDistanceX = 0;
            } else if (this.moveDistanceY > 0) {
                if (this.chkPointY + this.moveDistanceY >= this.getBlockUpSide(var5, var1 + 1)) {
                    this.chkPointY = this.getBlockUpSide(var5, var1 + 1);
                } else {
                    this.chkPointY += this.moveDistanceY;
                }
            } else if (this.chkPointY + this.moveDistanceY <= this.getBlockDownSide(var5, var1 - 1)) {
                this.chkPointY = this.getBlockDownSide(var5, var1 - 1);
            } else {
                this.chkPointY += this.moveDistanceY;
            }

            var5 = this.footDegree;
            var6 = this.chkPointX;
            if (var4 == 3) {
                var11 = 1;
            } else {
                var11 = -1;
            }

            var1 = this.getWorldX(var6 + var11 * (Math.abs(this.chkPointY - var2) + this.user.getPressToGround()), this.chkPointY, var4);
            if (var1 != -1000) {
                this.chkPointX = var1;
                this.footDegree = this.getDegreeFromWorld(this.footDegree, this.chkPointX, this.chkPointY, this.acObj.posZ);
            }

            this.calObjPositionFromFoot();
            if (var4 == this.getDirectionByDegree(this.footDegree)) {
                int var8;
                int var9;
                ACWorldCalUser var10;
                byte var12;
                if (this.moveDistanceY > 0) {
                    if (var4 == 1) {
                        this.rightSideCollisionChk(this.footX, this.footY, var4, this.collisionData);
                        var1 = this.collisionData.newPosY;
                    } else {
                        this.leftSideCollisionChk(this.footX, this.footY, var4, this.collisionData);
                        var1 = this.collisionData.newPosY;
                    }

                    if (var1 != -1000) {
                        this.footY = var1;
                        this.calChkPointFromPos();
                        var6 = this.footDegree;
                        if (var4 == 1) {
                            var11 = 270;
                        } else {
                            var11 = 90;
                        }

                        var9 = this.collisionData.collisionX;
                        var7 = this.collisionData.collisionY;
                        var8 = this.acObj.posZ;
                        var6 = this.getDegreeFromWorld((var6 + var11) % 360, var9, var7, var8);
                        var10 = this.user;
                        if (var4 == 1) {
                            var12 = 1;
                        } else {
                            var12 = 3;
                        }

                        var10.doWhileTouchWorld(var12, var6);
                        this.moveDistanceY = 0;
                        this.acObj.velY = 0;
                    }
                } else if (this.moveDistanceY < 0) {
                    if (var4 == 1) {
                        this.leftSideCollisionChk(this.footX, this.footY, var4, this.collisionData);
                        var1 = this.collisionData.newPosY;
                    } else {
                        this.rightSideCollisionChk(this.footX, this.footY, var4, this.collisionData);
                        var1 = this.collisionData.newPosY;
                    }

                    if (var1 != -1000) {
                        this.footY = var1;
                        this.calChkPointFromPos();
                        var6 = this.footDegree;
                        if (var4 == 1) {
                            var11 = 90;
                        } else {
                            var11 = 270;
                        }

                        var8 = this.collisionData.collisionX;
                        var9 = this.collisionData.collisionY;
                        var7 = this.acObj.posZ;
                        var6 = this.getDegreeFromWorld((var6 + var11) % 360, var8, var9, var7);
                        var10 = this.user;
                        if (var4 == 1) {
                            var12 = 3;
                        } else {
                            var12 = 1;
                        }

                        var10.doWhileTouchWorld(var12, var6);
                        this.moveDistanceY = 0;
                        this.acObj.velY = 0;
                    }
                }
            }

            this.footDegree = var5;
            var5 = this.chkPointX;
            byte var13;
            if (var4 == 3) {
                var13 = 1;
            } else {
                var13 = -1;
            }

            var1 = this.getWorldX(var5 + var13 * (Math.abs(this.chkPointY - var2) + this.user.getPressToGround()), this.chkPointY, var4);
            if (var1 != -1000) {
                this.chkPointX = var1;
            }
        } else {
            if (var4 == 2) {
            }

            var6 = this.chkPointX;
            if (this.moveDistanceX == 0) {
                this.moveDistanceY = 0;
            } else if (this.moveDistanceX > 0) {
                if (this.chkPointX + this.moveDistanceX >= this.getBlockLeftSide(var5 + 1, var1)) {
                    this.chkPointX = this.getBlockLeftSide(var5 + 1, var1);
                } else {
                    this.chkPointX += this.moveDistanceX;
                }
            } else if (this.chkPointX + this.moveDistanceX <= this.getBlockRightSide(var5 - 1, var1)) {
                this.chkPointX = this.getBlockRightSide(var5 - 1, var1);
            } else {
                this.chkPointX += this.moveDistanceX;
            }

            this.calObjPositionFromFoot();
            this.doSideCheckInGround(var4);
            this.calChkPointFromPos();
            var5 = this.footDegree;
            var6 = this.chkPointX;
            var7 = this.chkPointY;
            if (var4 == 0) {
                var11 = 1;
            } else {
                var11 = -1;
            }

            var1 = this.getWorldY(var6, var7 + var11 * (Math.abs(this.chkPointX - var3) + this.user.getPressToGround()), var4);
            if (var1 != -1000) {
                this.chkPointY = var1;
                this.footDegree = this.getDegreeFromWorld(this.footDegree, this.chkPointX, this.chkPointY, this.acObj.posZ);
            }

            this.calObjPositionFromFoot();
            if (var4 == this.getDirectionByDegree(this.footDegree)) {
                this.doSideCheckInGround(var4);
                this.calChkPointFromPos();
            }

            this.footDegree = var5;
            var5 = this.chkPointX;
            var6 = this.chkPointY;
            if (var4 == 0) {
                var11 = 1;
            } else {
                var11 = -1;
            }

            var1 = this.getWorldY(var5, var6 + var11 * (Math.abs(this.chkPointX - var3) + this.user.getPressToGround()), var4);
            if (var1 != -1000) {
                this.chkPointY = var1;
            }
        }

        this.calObjPositionFromFoot();
        var1 = this.getDirectionByDegree(this.footDegree);
        switch (var1) {
            case 0:
                var1 = this.getWorldY(this.footX, this.footY + this.worldInstance.getTileHeight(), 0);
                if (var1 != -1000) {
                    this.footY = var1;
                }
                break;
            case 1:
                var1 = this.getWorldX(this.footX - this.worldInstance.getTileHeight(), this.footY, 1);
                if (var1 != -1000) {
                    this.footX = var1;
                }
                break;
            case 2:
                var1 = this.getWorldY(this.footX, this.footY - this.worldInstance.getTileHeight(), 2);
                if (var1 != -1000) {
                    this.footY = var1;
                }
                break;
            case 3:
                var1 = this.getWorldX(this.footX + this.worldInstance.getTileHeight(), this.footY, 3);
                if (var1 != -1000) {
                    this.footX = var1;
                }
        }

        var1 = this.moveDistanceX;
        var1 = this.moveDistanceY;
        this.moveDistanceX -= this.chkPointX - var3;
        this.moveDistanceY -= this.chkPointY - var2;
        var1 = (this.moveDistanceX * MyAPI.dCos(this.user.getBodyDegree()) + this.moveDistanceY * MyAPI.dSin(this.user.getBodyDegree())) / 100;
        if (this.totalDistance * var1 <= 0) {
            this.moveDistanceX = 0;
            this.moveDistanceY = 0;
        }

        this.totalDistance = (this.moveDistanceX * MyAPI.dCos(this.user.getBodyDegree()) + this.moveDistanceY * MyAPI.dSin(this.user.getBodyDegree())) / 100;
    }

    private void rightSideCollisionChk(int var1, int var2, int var3, ACCollisionData var4) {
        var4.reset();
        if (this.limit == null || !this.limit.noSideCollision()) {
            int var5;
            int var6;
            int var7;
            int var8;
            int var9;
            int var10;
            int var11;
            int var12;
            int var13;
            int var14;
            ACBlock var15;
            ACWorld var16;
            switch (var3) {
                case 0:
                case 2:
                    var6 = -1000;
                    var8 = -1;
                    var7 = -1;
                    var5 = 0;

                    while(true) {
                        if (var5 >= this.bodyCollisionPointOffsetY.length) {
                            if (var6 != -1000) {
                                var5 = -this.bodyCollisionPointOffsetX;
                                var8 = -this.bodyCollisionPointOffsetY[var8];
                                var7 = this.user.getBodyDegree();
                                var4.newPosX = ACUtilities.getRelativePointX(var6, var5, var8, var7);
                                var16 = this.worldInstance;
                                var15 = this.getBlock;
                                var7 = var4.collisionX;
                                var5 = var4.collisionY;
                                var6 = this.acObj.posZ;
                                var16.getCollisionBlock(var15, var7, var5, var6);
                                var4.reBlock = this.getBlock;
                            }
                            break;
                        }

                        var11 = this.bodyCollisionPointOffsetX;
                        var10 = this.bodyCollisionPointOffsetY[var5];
                        var9 = this.user.getBodyDegree();
                        var12 = ACUtilities.getRelativePointX(var1, var11, var10, var9);
                        var10 = this.bodyCollisionPointOffsetX;
                        var11 = this.bodyCollisionPointOffsetY[var5];
                        var9 = this.user.getBodyDegree();
                        var14 = ACUtilities.getRelativePointY(var2, var10, var11, var9);
                        var13 = this.getWorldX(var12, var14, (var3 + 3) % 4);
                        var11 = var8;
                        var10 = var6;
                        var9 = var7;
                        if (var13 != -1000) {
                            label67: {
                                var12 = Math.abs(var13 - var12);
                                if (var6 != -1000) {
                                    var11 = var8;
                                    var10 = var6;
                                    var9 = var7;
                                    if (var12 <= var7) {
                                        break label67;
                                    }
                                }

                                var10 = var13;
                                var11 = var5;
                                var9 = var12;
                                var4.collisionX = var13;
                                var4.collisionY = var14;
                                var4.newPosY = var2;
                            }
                        }

                        ++var5;
                        var8 = var11;
                        var6 = var10;
                        var7 = var9;
                    }
                case 1:
            }

            var6 = -1000;
            var8 = -1;
            var7 = -1;

            for(var5 = 0; var5 < this.bodyCollisionPointOffsetY.length; var7 = var9) {
                var11 = this.bodyCollisionPointOffsetX;
                var9 = this.bodyCollisionPointOffsetY[var5];
                var10 = this.user.getBodyDegree();
                var14 = ACUtilities.getRelativePointX(var1, var11, var9, var10);
                var9 = this.bodyCollisionPointOffsetX;
                var10 = this.bodyCollisionPointOffsetY[var5];
                var11 = this.user.getBodyDegree();
                var12 = ACUtilities.getRelativePointY(var2, var9, var10, var11);
                var13 = this.getWorldY(var14, var12, (var3 + 3) % 4);
                var11 = var8;
                var10 = var6;
                var9 = var7;
                if (var13 != -1000) {
                    label68: {
                        var12 = Math.abs(var13 - var12);
                        if (var6 != -1000) {
                            var11 = var8;
                            var10 = var6;
                            var9 = var7;
                            if (var12 <= var7) {
                                break label68;
                            }
                        }

                        var10 = var13;
                        var11 = var5;
                        var9 = var12;
                        var4.collisionX = var13;
                        var4.collisionY = var14;
                        var4.newPosX = var1;
                    }
                }

                ++var5;
                var8 = var11;
                var6 = var10;
            }

            if (var6 != -1000) {
                var1 = -this.bodyCollisionPointOffsetX;
                var2 = -this.bodyCollisionPointOffsetY[var8];
                var3 = this.user.getBodyDegree();
                var4.newPosY = ACUtilities.getRelativePointY(var6, var1, var2, var3);
                var16 = this.worldInstance;
                var15 = this.getBlock;
                var3 = var4.collisionX;
                var1 = var4.collisionY;
                var2 = this.acObj.posZ;
                var16.getCollisionBlock(var15, var3, var1, var2);
                var4.reBlock = this.getBlock;
            }
        }

    }

    private void upSideCollisionChk(int var1, int var2, int var3, ACCollisionData var4) {
        var4.reset();
        if (this.limit == null || !this.limit.noTopCollision()) {
            int var5;
            int var6;
            int var7;
            int var8;
            int var9;
            int var10;
            int var11;
            int var12;
            int var13;
            switch (var3) {
                case 0:
                case 2:
                    var6 = -1000;
                    var7 = -1;
                    var9 = -1;

                    for(var5 = 0; var5 < this.headCollisionPointOffsetX.length; var7 = var8) {
                        var8 = ACUtilities.getRelativePointX(var1, this.headCollisionPointOffsetX[var5], this.headCollisionPointOffsetY, this.footDegree);
                        var12 = ACUtilities.getRelativePointY(var2, this.headCollisionPointOffsetX[var5], this.headCollisionPointOffsetY, this.footDegree);
                        var13 = this.getWorldY(var8, var12, (var3 + 2) % 4);
                        var11 = var9;
                        var10 = var6;
                        var8 = var7;
                        if (var13 != -1000) {
                            label66: {
                                var12 = Math.abs(var12 - var13);
                                if (var6 != -1000) {
                                    var11 = var9;
                                    var10 = var6;
                                    var8 = var7;
                                    if (var12 <= var7) {
                                        break label66;
                                    }
                                }

                                var10 = var13;
                                var11 = var5;
                                var8 = var12;
                            }
                        }

                        ++var5;
                        var9 = var11;
                        var6 = var10;
                    }

                    if (var6 != -1000) {
                        var3 = ACUtilities.getRelativePointX(var1, this.headCollisionPointOffsetX[var9], this.headCollisionPointOffsetY, this.footDegree);
                        ACUtilities.getRelativePointY(var2, this.headCollisionPointOffsetX[var9], this.headCollisionPointOffsetY, this.footDegree);
                        this.worldInstance.getCollisionBlock(this.getBlock, var3, var6, this.acObj.posZ);
                        var4.collisionX = var3;
                        var4.collisionY = var6;
                        var4.newPosX = var1;
                        var4.newPosY = ACUtilities.getRelativePointY(var6, -this.headCollisionPointOffsetX[var9], -this.headCollisionPointOffsetY, this.footDegree);
                        var4.reBlock = this.getBlock;
                    }
                    break;
                case 1:
                default:
                    var6 = -1000;
                    var8 = -1;
                    var7 = -1;

                    for(var5 = 0; var5 < this.headCollisionPointOffsetX.length; var8 = var9) {
                        var12 = ACUtilities.getRelativePointX(var1, this.headCollisionPointOffsetX[var5], this.headCollisionPointOffsetY, this.footDegree);
                        var9 = ACUtilities.getRelativePointY(var2, this.headCollisionPointOffsetX[var5], this.headCollisionPointOffsetY, this.footDegree);
                        var13 = this.getWorldX(var12, var9, (var3 + 2) % 4);
                        var11 = var7;
                        var10 = var6;
                        var9 = var8;
                        if (var13 != -1000) {
                            label67: {
                                var12 = Math.abs(var12 - var13);
                                if (var6 != -1000) {
                                    var11 = var7;
                                    var10 = var6;
                                    var9 = var8;
                                    if (var12 <= var8) {
                                        break label67;
                                    }
                                }

                                var10 = var13;
                                var11 = var5;
                                var9 = var12;
                            }
                        }

                        ++var5;
                        var7 = var11;
                        var6 = var10;
                    }

                    if (var6 != -1000) {
                        var8 = this.headCollisionPointOffsetX[var7];
                        var5 = this.headCollisionPointOffsetY;
                        var3 = this.footDegree;
                        ACUtilities.getRelativePointX(var1, var8, var5, var3);
                        var3 = this.headCollisionPointOffsetX[var7];
                        var5 = this.headCollisionPointOffsetY;
                        var1 = this.footDegree;
                        var1 = ACUtilities.getRelativePointY(var2, var3, var5, var1);
                        this.worldInstance.getCollisionBlock(this.getBlock, var6, var1, this.acObj.posZ);
                        var4.collisionX = var6;
                        var4.collisionY = var1;
                        var4.newPosY = var2;
                        var4.newPosX = ACUtilities.getRelativePointX(var6, -this.headCollisionPointOffsetX[var7], -this.headCollisionPointOffsetY, this.footDegree);
                        var4.reBlock = this.getBlock;
                    }
            }
        }

    }

    public void actionLogic(int var1, int var2) {
        int var3 = (MyAPI.dCos(this.footDegree) * var1 + MyAPI.dSin(this.footDegree) * var2) / 100;
        this.actionLogic(var1, var2, var3);
    }

    public void actionLogic(int var1, int var2, int var3) {
        int var6 = this.acObj.getObjWidth();
        int var5 = this.acObj.getObjHeight();
        int var7 = this.user.getFootOffset();
        int var4 = this.user.getBodyOffset();
        this.changeSize(var6, var5, var7, var4);
        this.footX = this.user.getFootX();
        this.footY = this.user.getFootY();
        this.moveDistanceX = var1;
        this.moveDistanceY = var2;
        switch (this.actionState) {
            case 0:
                this.totalDistance = var3;
            case 1:
            default:
                try {
                    this.checkInMap();
                } catch (Exception var9) {
                    var9.printStackTrace();
                }

        }
    }

    public void changeSize(int var1, int var2, int var3, int var4) {
        int var5 = var4;
        if (var4 < this.worldInstance.getTileHeight()) {
            var5 = this.worldInstance.getTileHeight();
        }

        if (var1 != this.preWidth || var2 != this.preHeight || var3 != this.preFootOffset || var5 != this.preBodyOffset) {
            this.calPosition(var1, var2, var3, var5);
            this.preWidth = var1;
            this.preHeight = var2;
            this.preFootOffset = var3;
            this.preBodyOffset = var5;
        }

    }

    public byte getActionState() {
        return this.actionState;
    }

    public int getDegreeDiff(int var1, int var2) {
        var2 = Math.abs(var1 - var2);
        var1 = var2;
        if (var2 > 180) {
            var1 = 360 - var2;
        }

        var2 = var1;
        if (var1 > 90) {
            var2 = 180 - var1;
        }

        return var2;
    }

    public int getDirectionByDegree(int var1) {
        while(var1 < 0) {
            var1 += 360;
        }

        var1 %= 360;
        byte var2;
        if (var1 <= 315 && var1 >= 45) {
            if (var1 >= 225 && var1 <= 315) {
                var2 = 3;
            } else if (var1 > 135 && var1 < 225) {
                var2 = 2;
            } else {
                var2 = 1;
            }
        } else {
            var2 = 0;
        }

        return var2;
    }

    public boolean getMovedState() {
        return this.isMoved;
    }

    public void setLimit(ACWorldCollisionLimit var1) {
        this.limit = var1;
    }

    public void setMovedState(boolean var1) {
        this.isMoved = var1;
    }

    public void stopMoveX() {
        super.stopMoveX();
        if (this.actionState == 0) {
            int var1 = this.totalDistance * MyAPI.dCos(this.user.getBodyDegree()) / 100;
            var1 = this.totalDistance * MyAPI.dSin(this.user.getBodyDegree()) / 100;
            this.totalDistance = ACUtilities.getTotalFromDegree(0, var1, this.user.getBodyDegree());
        }

    }

    public void stopMoveY() {
        super.stopMoveY();
        if (this.actionState == 0) {
            int var1 = this.totalDistance * MyAPI.dCos(this.user.getBodyDegree()) / 100;
            int var2 = this.totalDistance * MyAPI.dSin(this.user.getBodyDegree()) / 100;
            this.totalDistance = ACUtilities.getTotalFromDegree(var1, 0, this.user.getBodyDegree());
        }

    }
}

