//
// Decompiled by FernFlower - 800ms
//
package com.sega.engine.action;

public abstract class ACWorld implements ACParam {
    private static final int MAX_SEARCH_BLOCK = 20;
    private ACBlock getBlock;
    private ACBlock nextBlock;

    public abstract void getCollisionBlock(ACBlock var1, int var2, int var3, int var4);

    public abstract ACDegreeGetter getDegreeGetterForObject();

    public abstract ACBlock getNewCollisionBlock();

    public abstract int getTileHeight();

    public abstract int getTileWidth();

    public abstract int getWorldHeight();

    public abstract int getWorldWidth();

    public int getWorldX(int var1, int var2, int var3, int var4) {
        if (var4 != 0 && var4 != 2) {
            if (this.getBlock == null) {
                this.getBlock = this.getNewCollisionBlock();
            }

            if (this.nextBlock == null) {
                this.nextBlock = this.getNewCollisionBlock();
            }

            ACUtilities.getQuaParam(var1, this.getTileWidth());
            this.getTileWidth();
            boolean var5;
            int var11;
            if (var4 == 3) {
                var5 = false;
            } else {
                var11 = ACBlock.rightSide;
            }

            this.getCollisionBlock(this.getBlock, var1, var2, var3);
            ACBlock var10 = this.getBlock;
            var11 = var10.getCollisionX(var2, (var4 + 2) % 4);
            if (var11 == -1000 || (var4 != 3 || var11 >= var1) && (var4 != 1 || var11 <= var1)) {
                var11 = this.getBlock.getCollisionX(var2, var4);
                int var9 = var11;
                int var7 = 0;
                ACUtilities.getQuaParam(var1, this.getTileWidth());
                this.getTileWidth();
                int var12;
                if (var4 == 3) {
                    boolean var6 = false;
                } else {
                    var12 = ACBlock.rightSide;
                }

                var12 = var1;

                while(true) {
                    int var8 = var12;
                    byte var13;
                    if (var9 == -1000 || var7 >= 20) {
                        var12 = var11;
                        if (var11 == -1000) {
                            label119: {
                                for(var7 = 0; var7 < 1; ++var7) {
                                    if (var4 == 3) {
                                        var13 = -1;
                                    } else {
                                        var13 = 1;
                                    }

                                    var8 -= var13 * this.getTileWidth();
                                    this.getCollisionBlock(this.getBlock, var8, var2, var3);
                                    var11 = this.getBlock.getCollisionX(var2, var4);
                                    var12 = var11;
                                    if (var11 != -1000) {
                                        break label119;
                                    }
                                }

                                var12 = var11;
                            }
                        }

                        if (var12 == -1000 || (var4 != 3 || var12 > var1) && (var4 != 1 || var12 < var1)) {
                            var1 = -1000;
                        } else {
                            var1 = var12;
                        }
                        break;
                    }

                    if (var4 == 3) {
                        var13 = -1;
                    } else {
                        var13 = 1;
                    }

                    var12 += var13 * this.getTileWidth();
                    this.getCollisionBlock(this.getBlock, var12, var2, var3);
                    var8 = this.getBlock.getCollisionX(var2, var4);
                    ++var7;
                    ACUtilities.getQuaParam(var12, this.getTileWidth());
                    this.getTileWidth();
                    if (var4 == 3) {
                        var5 = false;
                    } else {
                        var11 = ACBlock.rightSide;
                    }

                    var10 = this.nextBlock;
                    if (var4 == 3) {
                        var13 = -1;
                    } else {
                        var13 = 1;
                    }

                    this.getCollisionBlock(var10, var13 * this.getTileWidth() + var12, var2, var3);
                    var9 = this.nextBlock.getCollisionX(var2, var4);
                    var11 = var8;
                }
            } else {
                var1 = -1000;
            }
        } else {
            var1 = -1000;
        }

        return var1;
    }

    public int getWorldY(int var1, int var2, int var3, int var4) {
        if (var4 != 1 && var4 != 3) {
            if (this.getBlock == null) {
                this.getBlock = this.getNewCollisionBlock();
            }

            if (this.nextBlock == null) {
                this.nextBlock = this.getNewCollisionBlock();
            }

            int var5 = this.getTileHeight();
            ACUtilities.getQuaParam(var2, var5);
            this.getTileHeight();
            boolean var11;
            if (var4 == 0) {
                var11 = false;
            } else {
                var5 = ACBlock.downSide;
            }

            this.getCollisionBlock(this.getBlock, var1, var2, var3);
            ACBlock var10 = this.getBlock;
            var5 = var10.getCollisionY(var2, (var4 + 2) % 4);
            if (var5 == -1000 || (var4 != 0 || var5 >= var2) && (var4 != 2 || var5 <= var2)) {
                var5 = this.getBlock.getCollisionY(var1, var4);
                int var7 = 0;
                int var6 = this.getTileHeight();
                ACUtilities.getQuaParam(var2, var6);
                this.getTileHeight();
                if (var4 == 0) {
                    boolean var12 = false;
                } else {
                    var6 = ACBlock.downSide;
                }

                var6 = var2;
                int var9 = var5;

                while(true) {
                    int var8 = var6;
                    byte var13;
                    if (var9 == -1000 || var7 >= 20) {
                        var6 = var5;
                        if (var5 == -1000) {
                            label119: {
                                for(var7 = 0; var7 < 1; ++var7) {
                                    if (var4 == 0) {
                                        var13 = -1;
                                    } else {
                                        var13 = 1;
                                    }

                                    var8 -= var13 * this.getTileHeight();
                                    this.getCollisionBlock(this.getBlock, var1, var8, var3);
                                    var5 = this.getBlock.getCollisionY(var1, var4);
                                    var6 = var5;
                                    if (var5 != -1000) {
                                        break label119;
                                    }
                                }

                                var6 = var5;
                            }
                        }

                        if (var6 == -1000 || (var4 != 0 || var6 > var2) && (var4 != 2 || var6 < var2)) {
                            var1 = -1000;
                        } else {
                            var1 = var6;
                        }
                        break;
                    }

                    if (var4 == 0) {
                        var13 = -1;
                    } else {
                        var13 = 1;
                    }

                    var6 += var13 * this.getTileHeight();
                    this.getCollisionBlock(this.getBlock, var1, var6, var3);
                    var8 = this.getBlock.getCollisionY(var1, var4);
                    ++var7;
                    var5 = this.getTileHeight();
                    ACUtilities.getQuaParam(var6, var5);
                    this.getTileHeight();
                    if (var4 == 0) {
                        var11 = false;
                    } else {
                        var5 = ACBlock.downSide;
                    }

                    var10 = this.nextBlock;
                    if (var4 == 0) {
                        var13 = -1;
                    } else {
                        var13 = 1;
                    }

                    this.getCollisionBlock(var10, var1, var13 * this.getTileHeight() + var6, var3);
                    var9 = this.nextBlock.getCollisionY(var1, var4);
                    var5 = var8;
                }
            } else {
                var1 = -1000;
            }
        } else {
            var1 = -1000;
        }

        return var1;
    }

    public abstract int getZoom();
}

