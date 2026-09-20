//
// Decompiled by FernFlower - 929ms
//
package com.sega.engine.action;

import com.sega.engine.lib.CrlFP32;

public class ACMapDegreeGetter extends ACDegreeGetter implements ACParam {
    private int searchRange;
    private ACWorld worldInstance;
    private int zoom;

    public ACMapDegreeGetter(ACWorld var1, int var2) {
        this.worldInstance = var1;
        this.searchRange = var2;
        this.zoom = var1.getZoom();
    }

    private int calDegreeByAllPoints(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7, boolean var8, int var9) {
        int var10 = var9;
        if (var7) {
            var10 = this.getDegreeByTwoPoint(var1, var2, var5, var6, var9);
        }

        var2 = var9;
        if (var8) {
            var2 = this.getDegreeByTwoPoint(var3, var4, var5, var6, var9);
        }

        if (var7 && var8) {
            var1 = this.getDegreeDiff(var10, var9);
            var3 = this.getDegreeDiff(var2, var9);
            if (var1 <= var3) {
                var1 = var10;
            } else {
                var1 = var2;
            }
        } else if (var7) {
            var1 = var10;
        } else {
            var1 = var9;
            if (var8) {
                var1 = var2;
            }
        }

        while(var1 < 0) {
            var1 += 360;
        }

        return var1 % 360;
    }

    private void getDegreeByPositionAndDirection(ACDegreeGetter.DegreeReturner var1, ACCollision var2, int var3, int var4, int var5, int var6, int var7) {
        if (var2 != null) {
        }

        var1.reset(var4, var5, var3);
        byte var8;
        int var9;
        int var10;
        int var11;
        int var12;
        int var13;
        int var14;
        boolean var15;
        boolean var16;
        switch (var7) {
            case 0:
            case 2:
                if (var7 == 0) {
                    var8 = 1;
                } else {
                    var8 = -1;
                }

                var13 = this.getNewY(var4, var5, var6, var7, var2);
                if (var13 != -1000) {
                    var5 = var13 + this.searchRange * var8;
                    var15 = false;
                    var9 = var4 - this.searchRange;

                    while(true) {
                        if (var9 >= var4) {
                            var10 = var5;
                            break;
                        }

                        var10 = this.searchRange * (var4 - var9) / this.searchRange;
                        var5 = var13 + var8 * var10;
                        var11 = var13 - var8 * var10;
                        var10 = this.getNewY(var9, var5, var6, var7, var2);
                        if (var10 != -1000 && (var7 == 0 && var10 >= var11 || var7 == 2 && var10 <= var11)) {
                            var15 = true;
                            break;
                        }

                        var9 += 1 << this.zoom;
                    }

                    var5 = var13 + this.searchRange * var8;
                    var16 = false;

                    for(var11 = var4 + this.searchRange; var11 > var4; var11 -= 1 << this.zoom) {
                        var12 = this.searchRange * (var11 - var4) / this.searchRange;
                        var5 = var13 + var8 * var12;
                        var14 = var13 - var8 * var12;
                        var12 = this.getNewY(var11, var5, var6, var7, var2);
                        if (var12 != -1000 && (var7 == 0 && var12 >= var14 || var7 == 2 && var12 <= var14)) {
                            var16 = true;
                            var5 = var12;
                            break;
                        }
                    }

                    if (var15 || var16) {
                        var1.degreeCalSuccess = true;
                        var1.newX = var4;
                        var1.newY = var13;
                        var1.degree = this.calDegreeByAllPoints(var9, var10, var11, var5, var4, var13, var15, var16, var3);
                    }
                }
                break;
            case 1:
            case 3:
                if (var7 == 3) {
                    var8 = 1;
                } else {
                    var8 = -1;
                }

                var13 = this.getNewX(var4, var5, var6, var7, var2);
                if (var13 != -1000) {
                    var4 = 0;
                    var15 = false;
                    var9 = var5 - this.searchRange;

                    while(true) {
                        if (var9 >= var5) {
                            var10 = var4;
                            break;
                        }

                        var10 = this.searchRange * (var5 - var9) / this.searchRange;
                        var4 = var13 + var8 * var10;
                        var11 = var13 - var8 * var10;
                        var10 = this.getNewX(var4, var9, var6, var7, var2);
                        if (var10 != -1000 && (var7 == 3 && var10 >= var11 || var7 == 1 && var10 <= var11)) {
                            var15 = true;
                            break;
                        }

                        var9 += 1 << this.zoom;
                    }

                    var4 = 0;
                    var16 = false;

                    for(var11 = var5 + this.searchRange; var11 > var5; var11 -= 1 << this.zoom) {
                        var12 = this.searchRange * (var11 - var5) / this.searchRange;
                        var4 = var13 + var8 * var12;
                        var14 = var13 - var8 * var12;
                        var12 = this.getNewX(var4, var11, var6, var7, var2);
                        if (var12 != -1000 && (var7 == 3 && var12 >= var14 || var7 == 1 && var12 <= var14)) {
                            var16 = true;
                            var4 = var12;
                            break;
                        }
                    }

                    if (var15 || var16) {
                        var1.degreeCalSuccess = true;
                        var1.newX = var13;
                        var1.newY = var5;
                        var1.degree = this.calDegreeByAllPoints(var10, var9, var4, var11, var13, var5, var15, var16, var3);
                    }
                }
        }

    }

    private void getDegreeByPositionX(ACDegreeGetter.DegreeReturner var1, ACCollision var2, int var3, int var4, int var5, int var6) {
        byte var8 = 3;
        byte var7 = var8;
        if (var3 > 0) {
            var7 = var8;
            if (var3 < 180) {
                var7 = 1;
            }
        }

        this.getDegreeByPositionAndDirection(var1, var2, var3, var4, var5, var6, var7);
    }

    private void getDegreeByPositionY(ACDegreeGetter.DegreeReturner var1, ACCollision var2, int var3, int var4, int var5, int var6) {
        byte var8 = 0;
        byte var7 = var8;
        if (var3 > 90) {
            var7 = var8;
            if (var3 < 270) {
                var7 = 2;
            }
        }

        this.getDegreeByPositionAndDirection(var1, var2, var3, var4, var5, var6, var7);
    }

    private int getDegreeByTwoPoint(int var1, int var2, int var3, int var4, int var5) {
        if (var1 == var3 && var2 == var4) {
            var1 = var5;
        } else {
            var3 = CrlFP32.actTanDegree(var4 - var2, var3 - var1);
            var1 = Math.abs(var5 - var3);
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

    private int getDegreeDiff(int var1, int var2) {
        var2 = Math.abs(var1 - var2);
        var1 = var2;
        if (var2 > 180) {
            var1 = 360 - var2;
        }

        return var1;
    }

    private int getDirectionByDegree(int var1) {
        while(var1 < 0) {
            var1 += 360;
        }

        var1 %= 360;
        byte var2;
        if (var1 < 315 && var1 > 45) {
            if (var1 > 225 && var1 < 315) {
                var2 = 3;
            } else if (var1 >= 135 && var1 <= 225) {
                var2 = 2;
            } else {
                var2 = 1;
            }
        } else {
            var2 = 0;
        }

        return var2;
    }

    private int getNewX(int var1, int var2, int var3, int var4, ACCollision var5) {
        if (var5 != null) {
            var1 = var5.getCollisionX(var1, var4);
        } else {
            var1 = this.worldInstance.getWorldX(var1, var2, var3, var4);
        }

        return var1;
    }

    private int getNewY(int var1, int var2, int var3, int var4, ACCollision var5) {
        if (var5 != null) {
            var1 = var5.getCollisionY(var1, var4);
        } else {
            var1 = this.worldInstance.getWorldY(var1, var2, var3, var4);
        }

        return var1;
    }

    public void getDegreeFromCollisionByPosition(ACDegreeGetter.DegreeReturner var1, ACCollision var2, int var3, int var4, int var5, int var6) {
        int var7 = this.getDirectionByDegree(var3);
        boolean var8;
        if (var7 != 0 && var7 != 2) {
            var8 = false;
        } else {
            var8 = true;
        }

        if (var8) {
            this.getDegreeByPositionY(var1, var2, var3, var4, var5, var6);
            if (var1.degreeCalSuccess) {
                return;
            }
        }

        this.getDegreeByPositionX(var1, var2, var3, var4, var5, var6);
        if (!var1.degreeCalSuccess && !var8) {
            this.getDegreeByPositionY(var1, var2, var3, var4, var5, var6);
            if (var1.degreeCalSuccess) {
            }
        }

    }

    public void getDegreeFromWorldByPosition(ACDegreeGetter.DegreeReturner var1, int var2, int var3, int var4, int var5) {
        int var6 = this.getDirectionByDegree(var2);
        boolean var7;
        if (var6 != 0 && var6 != 2) {
            var7 = false;
        } else {
            var7 = true;
        }

        if (var7) {
            this.getDegreeByPositionY(var1, (ACCollision)null, var2, var3, var4, var5);
            if (var1.degreeCalSuccess) {
                return;
            }
        }

        this.getDegreeByPositionX(var1, (ACCollision)null, var2, var3, var4, var5);
        if (!var1.degreeCalSuccess && !var7) {
            this.getDegreeByPositionY(var1, (ACCollision)null, var2, var3, var4, var5);
            if (var1.degreeCalSuccess) {
            }
        }

    }
}

