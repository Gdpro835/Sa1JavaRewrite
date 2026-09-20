//
// Decompiled by FernFlower - 966ms
//
package com.sega.engine.action;

public abstract class ACCollision implements ACParam {
    protected ACObject acObj;
    private boolean horizontalCollision;
    private int horizontalDistance;
    private boolean isLeftCollision;
    private boolean isUpCollision;
    private int objDownHighPointY = -1000;
    private int objLeftHighPointX = -1000;
    private int objRightHighPointX = -1000;
    private int objUpHighPointY = -1000;
    private int thisDownHighPointX = -1000;
    private int thisDownHighPointY = -1000;
    private int thisLeftHighPointX = -1000;
    private int thisLeftHighPointY = -1000;
    private int thisRightHighPointX = -1000;
    private int thisRightHighPointY = -1000;
    private int thisUpHighPointX = -1000;
    private int thisUpHighPointY = -1000;
    private boolean verticalCollision;
    private int verticalDistance;
    protected int worldZoom;

    public ACCollision(ACObject var1, ACWorld var2) {
        this.setObject(var1);
        this.worldZoom = var2.getZoom();
    }

    private void doCheckCollisionX(ACCollision var1, int var2) {
        this.horizontalCollision = false;
        if (this.getLeftX() + this.getWidth() >= var1.getLeftX() && this.getLeftX() <= var1.getLeftX() + var1.getWidth()) {
            int var5 = Math.max(this.getTopY(), var1.getTopY());
            int var8 = Math.min(this.getTopY() + this.getHeight(), var1.getTopY() + var1.getHeight());
            this.thisLeftHighPointX = -1000;
            this.thisRightHighPointX = -1000;
            this.thisLeftHighPointY = -1000;
            this.objLeftHighPointX = -1000;
            this.objRightHighPointX = -1000;
            this.thisRightHighPointY = -1000;
            int var4 = 0;

            int var3;
            int var7;
            for(var3 = 0; var5 < var8; var3 = var7) {
                var7 = this.getCollisionX(var5, 3);
                int var11 = var1.getCollisionX(var5, (3 + 2) % 4);
                int var10 = this.getCollisionX(var5, (3 + 2) % 4);
                int var9 = var1.getCollisionX(var5, 3);
                int var6 = var4;
                if (var7 != -1000) {
                    var6 = var4;
                    if (var11 != -1000) {
                        var6 = var4;
                        if (var7 <= var11) {
                            label83: {
                                if (this.thisLeftHighPointX != -1000) {
                                    var6 = var4;
                                    if (Math.abs(var7 - var11) <= var4) {
                                        break label83;
                                    }
                                }

                                this.thisLeftHighPointX = var7;
                                this.objLeftHighPointX = var11;
                                var6 = Math.abs(var7 - var11);
                                this.thisLeftHighPointY = var5;
                            }
                        }
                    }
                }

                var7 = var3;
                if (var10 != -1000) {
                    var7 = var3;
                    if (var9 != -1000) {
                        var7 = var3;
                        if (var10 >= var9) {
                            label86: {
                                if (this.thisRightHighPointX != -1000) {
                                    var7 = var3;
                                    if (Math.abs(var10 - var9) <= var3) {
                                        break label86;
                                    }
                                }

                                this.thisRightHighPointX = var10;
                                this.objRightHighPointX = var9;
                                var7 = Math.abs(var10 - var9);
                                this.thisRightHighPointY = var5;
                            }
                        }
                    }
                }

                var5 += 1 << this.worldZoom;
                var4 = var6;
            }

            boolean var14 = true;
            boolean var12 = true;
            boolean var13;
            if (this.thisLeftHighPointX != -1000 && this.thisRightHighPointX != -1000) {
                if (var4 == var3) {
                    if (var2 >= 0) {
                        var12 = false;
                        var13 = var14;
                    } else {
                        var12 = true;
                        var13 = var14;
                    }
                } else if (var4 < var3) {
                    var12 = true;
                    var13 = var14;
                } else {
                    var12 = false;
                    var13 = var14;
                }
            } else {
                var13 = false;
            }

            if (var13) {
                this.horizontalCollision = true;
                this.isLeftCollision = var12;
                if (var12) {
                    this.horizontalDistance = var4;
                } else {
                    this.horizontalDistance = var3;
                }
            }
        }

    }

    private void doCheckCollisionY(ACCollision var1, int var2) {
        this.verticalCollision = false;
        if (this.getTopY() + this.getHeight() >= var1.getTopY() && this.getTopY() <= var1.getTopY() + var1.getHeight()) {
            int var3;
            if (this.getLeftX() > var1.getLeftX()) {
                var3 = this.getLeftX();
            } else {
                var3 = var1.getLeftX();
            }

            int var4;
            if (this.getLeftX() + this.getWidth() < var1.getLeftX() + var1.getWidth()) {
                var4 = this.getLeftX() + this.getWidth();
            } else {
                var4 = var1.getLeftX() + var1.getWidth();
            }

            this.thisUpHighPointY = -1000;
            this.objUpHighPointY = -1000;
            this.thisUpHighPointX = -1000;
            this.thisDownHighPointY = -1000;
            this.objDownHighPointY = -1000;
            this.thisDownHighPointX = -1000;
            int var7 = 0;

            int var5;
            int var6;
            for(var6 = 0; var3 < var4; var7 = var5) {
                int var10 = this.getCollisionY(var3, 0);
                int var8 = this.getCollisionY(var3, (0 + 2) % 4);
                int var9 = var1.getCollisionY(var3, 0);
                int var11 = var1.getCollisionY(var3, (0 + 2) % 4);
                var5 = var7;
                if (var10 != -1000) {
                    var5 = var7;
                    if (var11 != -1000) {
                        var5 = var7;
                        if (var10 <= var11) {
                            label88: {
                                if (this.thisUpHighPointY != -1000) {
                                    var5 = var7;
                                    if (Math.abs(var10 - var11) <= var7) {
                                        break label88;
                                    }
                                }

                                this.thisUpHighPointY = var10;
                                this.objUpHighPointY = var11;
                                var5 = Math.abs(var10 - var11);
                                this.thisUpHighPointX = var3;
                            }
                        }
                    }
                }

                var7 = var6;
                if (var8 != -1000) {
                    var7 = var6;
                    if (var9 != -1000) {
                        var7 = var6;
                        if (var8 >= var9) {
                            label91: {
                                if (this.thisDownHighPointY != -1000) {
                                    var7 = var6;
                                    if (Math.abs(var8 - var9) <= var6) {
                                        break label91;
                                    }
                                }

                                this.thisDownHighPointY = var8;
                                this.objDownHighPointY = var9;
                                var7 = Math.abs(var8 - var9);
                                this.thisDownHighPointX = var3;
                            }
                        }
                    }
                }

                var3 += 1 << this.worldZoom;
                var6 = var7;
            }

            if (this.thisUpHighPointY != -1000 && this.thisDownHighPointY != -1000) {
                boolean var12;
                if (var7 == var6) {
                    if (var2 >= 0) {
                        var12 = false;
                    } else {
                        var12 = true;
                    }
                } else if (var7 < var6) {
                    var12 = true;
                } else {
                    var12 = false;
                }

                this.verticalCollision = true;
                this.isUpCollision = var12;
                if (var12) {
                    this.verticalDistance = var7;
                } else {
                    this.verticalDistance = var6;
                }
            }
        }

    }

    private void doHorizontalCollision(ACCollision var1) {
        int var2;
        int var3;
        int var4;
        int var5;
        if (this.isLeftCollision) {
            var3 = this.thisLeftHighPointX;
            var4 = this.thisLeftHighPointY;
            var2 = this.objLeftHighPointX;
            var5 = this.thisLeftHighPointY;
            this.doObjCollision(var1, 3, var3, var4, var2, var5);
            var5 = this.objLeftHighPointX;
            var3 = this.thisLeftHighPointY;
            var2 = this.thisLeftHighPointX;
            var4 = this.thisLeftHighPointY;
            var1.doObjCollision(this, 1, var5, var3, var2, var4);
        } else {
            var5 = this.thisRightHighPointX;
            var2 = this.thisRightHighPointY;
            var3 = this.objRightHighPointX;
            var4 = this.thisRightHighPointY;
            this.doObjCollision(var1, 1, var5, var2, var3, var4);
            var3 = this.objRightHighPointX;
            var2 = this.thisRightHighPointY;
            var4 = this.thisRightHighPointX;
            var5 = this.thisRightHighPointY;
            var1.doObjCollision(this, 3, var3, var2, var4, var5);
        }

    }

    private void doObjCollision(ACCollision var1, int var2, int var3, int var4, int var5, int var6) {
        if (this.acObj != null && var1.getObject() != null) {
            this.acObj.doWhileCollision(var1.getObject(), this, var2, var3, var4, var5, var6);
        }

    }

    private void doVerticalCollision(ACCollision var1) {
        int var2;
        int var3;
        int var4;
        int var5;
        if (this.isUpCollision) {
            var2 = this.thisUpHighPointX;
            var3 = this.thisUpHighPointY;
            var4 = this.thisUpHighPointX;
            var5 = this.objUpHighPointY;
            this.doObjCollision(var1, 0, var2, var3, var4, var5);
            var2 = this.thisUpHighPointX;
            var4 = this.objUpHighPointY;
            var3 = this.thisUpHighPointX;
            var5 = this.thisUpHighPointY;
            var1.doObjCollision(this, 2, var2, var4, var3, var5);
        } else {
            var5 = this.thisDownHighPointX;
            var2 = this.thisDownHighPointY;
            var3 = this.thisDownHighPointX;
            var4 = this.objDownHighPointY;
            this.doObjCollision(var1, 2, var5, var2, var3, var4);
            var4 = this.thisDownHighPointX;
            var3 = this.objDownHighPointY;
            var5 = this.thisDownHighPointX;
            var2 = this.thisDownHighPointY;
            var1.doObjCollision(this, 0, var4, var3, var5, var2);
        }

    }

    public void doCheckCollisionWithCollision(ACCollision var1, int var2, int var3) {
        this.acObj.doBeforeCollisionCheck();
        var1.getObject().doBeforeCollisionCheck();
        this.update();
        var1.update();
        this.horizontalDistance = Integer.MAX_VALUE;
        this.verticalDistance = Integer.MAX_VALUE;
        this.doCheckCollisionX(var1, var2);
        this.doCheckCollisionY(var1, var3);
        if (this.horizontalCollision && this.verticalCollision) {
            if (this.horizontalDistance <= this.verticalDistance) {
                this.doHorizontalCollision(var1);
                this.acObj.doBeforeCollisionCheck();
                var1.getObject().doBeforeCollisionCheck();
                this.update();
                var1.update();
                this.doCheckCollisionY(var1, var3);
                if (this.verticalCollision) {
                    this.doVerticalCollision(var1);
                }
            } else {
                this.doVerticalCollision(var1);
                this.acObj.doBeforeCollisionCheck();
                var1.getObject().doBeforeCollisionCheck();
                this.update();
                var1.update();
                this.doCheckCollisionX(var1, var2);
                if (this.horizontalCollision) {
                    this.doHorizontalCollision(var1);
                }
            }
        } else if (this.horizontalCollision) {
            this.doHorizontalCollision(var1);
        } else if (this.verticalCollision) {
            this.doVerticalCollision(var1);
        }

    }

    public int getCollisionX(int var1, int var2) {
        if (var1 >= this.getTopY() && var1 < this.getTopY() + this.getHeight()) {
            int var4 = var1 - this.getTopY();
            short var3 = -1000;
            var1 = var3;
            switch (var2) {
                case 1:
                    var1 = this.getCollisionXFromRight(var4);
                case 2:
                    break;
                case 3:
                    var1 = this.getCollisionXFromLeft(var4);
                    break;
                default:
                    var1 = var3;
            }

            if (var1 != -1000) {
                var1 += this.getLeftX();
            } else {
                var1 = -1000;
            }
        } else {
            var1 = -1000;
        }

        return var1;
    }

    public abstract int getCollisionXFromLeft(int var1);

    public abstract int getCollisionXFromRight(int var1);

    public int getCollisionY(int var1, int var2) {
        if (var1 >= this.getLeftX() && var1 < this.getLeftX() + this.getWidth()) {
            int var4 = var1 - this.getLeftX();
            short var3 = -1000;
            var1 = var3;
            switch (var2) {
                case 0:
                    var1 = this.getCollisionYFromUp(var4);
                case 1:
                    break;
                case 2:
                    var1 = this.getCollisionYFromDown(var4);
                    break;
                default:
                    var1 = var3;
            }

            if (var1 != -1000) {
                var1 += this.getTopY();
            } else {
                var1 = -1000;
            }
        } else {
            var1 = -1000;
        }

        return var1;
    }

    public abstract int getCollisionYFromDown(int var1);

    public abstract int getCollisionYFromUp(int var1);

    public abstract int getHeight();

    public abstract int getLeftX();

    public ACObject getObject() {
        return this.acObj;
    }

    public abstract int getTopY();

    public abstract int getWidth();

    public void setObject(ACObject var1) {
        this.acObj = var1;
    }

    public abstract void update();
}

