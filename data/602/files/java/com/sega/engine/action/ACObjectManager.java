//
// Decompiled by FernFlower - 1140ms
//
package com.sega.engine.action;

import com.sega.engine.action.ACObjectManager.ObjectIterator;
import java.util.Vector;
import com.sega.engine.lib.Iterator;

public class ACObjectManager {
    private static int currentVecX;
    private static int currentVecY;
    private static int destroyHeightRange;
    private static int destroyWidthRange;
    private static int heightRange;
    private static int roomHeight;
    private static Vector[][] roomVec;
    private static int roomWidth;
    private static int vecHeight;
    private static int vecWidth;
    private static int widthRange;

    public static void addObject(ACObject var0) {
        if (var0 != null) {
            int var2 = var0.posX / roomWidth;
            int var1 = var0.posY / roomHeight;
            if (var2 >= 0 && var2 < vecWidth && var1 >= 0 && var1 < vecHeight) {
                roomVec[var2][var1].addElement(var0);
            }
        }

    }

    private static void checkObjectsWhenCameraMoved() {
        ACCamera var10 = ACCamera.getInstance();
        int var3 = (var10.x + (var10.showWidth >> 1)) / roomWidth;
        int var0 = var10.y;
        int var4 = ((var10.showHeight >> 1) + var0) / roomHeight;
        int var1;
        int var2;
        if (currentVecX != -1 && currentVecY != -1) {
            if (currentVecX != var3 || currentVecY != var4) {
                int var6 = var3 - currentVecX;
                int var5 = var4 - currentVecY;
                int var7;
                int var8;
                ACObject var11;
                if (var6 != 0) {
                    if (var6 > 0) {
                        var0 = -destroyWidthRange;
                    } else {
                        var0 = destroyWidthRange;
                    }

                    var7 = var0 + var3;
                    if (var7 >= 0 && var7 < vecWidth) {
                        for(var1 = -destroyHeightRange; var1 <= destroyHeightRange; ++var1) {
                            if (var4 + var1 >= 0 && var4 + var1 < vecHeight) {
                                for(var0 = 0; var0 < roomVec[var7][var4 + var1].size(); var0 = var2 + 1) {
                                    var11 = (ACObject)roomVec[var7][var4 + var1].elementAt(var0);
                                    int var9 = var11.posX / roomWidth;
                                    var8 = var11.posY / roomHeight;
                                    var2 = var0;
                                    if (var9 >= 0) {
                                        if (var9 >= vecWidth) {
                                            var2 = var0;
                                        } else {
                                            var2 = var0;
                                            if (var8 >= 0) {
                                                var2 = var0;
                                                if (var8 < vecHeight) {
                                                    if (var9 == var7) {
                                                        var2 = var0;
                                                        if (var8 == var4 + var1) {
                                                            continue;
                                                        }
                                                    }

                                                    roomVec[var7][var4 + var1].removeElementAt(var0);
                                                    var2 = var0 - 1;
                                                    roomVec[var9][var8].addElement(var11);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (var6 > 0) {
                        var0 = widthRange;
                    } else {
                        var0 = -widthRange;
                    }

                    var6 = var3 + var0;
                    if (var6 >= 0 && var6 < vecWidth) {
                        for(var1 = var4 - heightRange; var1 <= heightRange + var4; ++var1) {
                            if (var1 >= 0 && var1 < vecHeight) {
                                for(var0 = 0; var0 < roomVec[var6][var1].size(); var0 = var2 + 1) {
                                    var11 = (ACObject)roomVec[var6][var1].elementAt(var0);
                                    var8 = var11.posX / roomWidth;
                                    var7 = var11.posY / roomHeight;
                                    var2 = var0;
                                    if (var8 >= 0) {
                                        if (var8 >= vecWidth) {
                                            var2 = var0;
                                        } else {
                                            var2 = var0;
                                            if (var7 >= 0) {
                                                var2 = var0;
                                                if (var7 < vecHeight) {
                                                    if (var8 == var6) {
                                                        var2 = var0;
                                                        if (var7 == var1) {
                                                            continue;
                                                        }
                                                    }

                                                    roomVec[var6][var1].removeElementAt(var0);
                                                    var2 = var0 - 1;
                                                    roomVec[var8][var7].addElement(var11);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (var5 != 0) {
                    if (var5 > 0) {
                        var0 = -destroyHeightRange;
                    } else {
                        var0 = destroyHeightRange;
                    }

                    var6 = var0 + var4;
                    if (var6 >= 0 && var6 < vecHeight) {
                        for(var1 = -destroyWidthRange; var1 <= destroyWidthRange; ++var1) {
                            if (var3 + var1 >= 0 && var3 + var1 < vecWidth) {
                                for(var0 = 0; var0 < roomVec[var3 + var1][var6].size(); var0 = var2 + 1) {
                                    var11 = (ACObject)roomVec[var3 + var1][var6].elementAt(var0);
                                    var8 = var11.posX / roomWidth;
                                    var7 = var11.posY / roomHeight;
                                    var2 = var0;
                                    if (var8 >= 0) {
                                        if (var8 >= vecWidth) {
                                            var2 = var0;
                                        } else {
                                            var2 = var0;
                                            if (var7 >= 0) {
                                                var2 = var0;
                                                if (var7 < vecHeight) {
                                                    if (var8 == var3 + var1) {
                                                        var2 = var0;
                                                        if (var7 == var6) {
                                                            continue;
                                                        }
                                                    }

                                                    roomVec[var3 + var1][var6].removeElementAt(var0);
                                                    var2 = var0 - 1;
                                                    roomVec[var8][var7].addElement(var11);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (var5 > 0) {
                        var0 = heightRange;
                    } else {
                        var0 = -heightRange;
                    }

                    var5 = var4 + var0;
                    if (var5 >= 0 && var5 < vecHeight) {
                        for(var1 = var3 - widthRange; var1 <= widthRange + var3; ++var1) {
                            if (var1 >= 0 && var1 < vecWidth) {
                                for(var0 = 0; var0 < roomVec[var1][var5].size(); var0 = var2 + 1) {
                                    var11 = (ACObject)roomVec[var1][var5].elementAt(var0);
                                    var7 = var11.posX / roomWidth;
                                    var6 = var11.posY / roomHeight;
                                    var2 = var0;
                                    if (var7 >= 0) {
                                        if (var7 >= vecWidth) {
                                            var2 = var0;
                                        } else {
                                            var2 = var0;
                                            if (var6 >= 0) {
                                                var2 = var0;
                                                if (var6 < vecHeight) {
                                                    if (var7 == var1) {
                                                        var2 = var0;
                                                        if (var6 == var5) {
                                                            continue;
                                                        }
                                                    }

                                                    roomVec[var1][var5].removeElementAt(var0);
                                                    var2 = var0 - 1;
                                                    roomVec[var7][var6].addElement(var11);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for(var0 = var3 - widthRange; var0 <= widthRange + var3; ++var0) {
                if (var0 >= 0 && var0 < vecWidth) {
                    for(var1 = var4 - heightRange; var1 <= heightRange + var4; ++var1) {
                        if (var1 >= 0 && var1 < vecHeight) {
                            for(var2 = 0; var2 < roomVec[var0][var1].size(); ++var2) {
                                ACObject var10000 = (ACObject)roomVec[var0][var1].elementAt(var2);
                            }
                        }
                    }
                }
            }
        }

        currentVecX = var3;
        currentVecY = var4;
    }

    public static void getObjectsNear(ACObject var0, ObjectIterator var1) {
        var1.getVector().removeAllElements();
        int var5 = var0.posX / roomWidth;
        int var3 = var0.posY / roomHeight;
        if (var5 >= 0 && var5 < vecWidth && var3 >= 0 && var3 < vecHeight) {
            int var2 = var5 - widthRange;
            int var4 = var3 - heightRange;
            var5 += widthRange;
            int var6 = var3 + heightRange;
            var3 = var2;
            if (var2 < 0) {
                var3 = 0;
            }

            var2 = var4;
            if (var4 < 0) {
                var2 = 0;
            }

            var4 = var5;
            if (var5 >= vecWidth) {
                var4 = vecWidth - 1;
            }

            var5 = var6;
            if (var6 >= vecHeight) {
                var5 = vecHeight - 1;
            }

            if (roomVec != null) {
                while(true) {
                    if (var3 > var4) {
                        var1.init();
                        break;
                    }

                    for(var6 = var2; var6 <= var5; ++var6) {
                        var1.getVector().addElement(roomVec[var3][var6]);
                    }

                    ++var3;
                }
            }
        }

    }

    public static void getObjectsNearCamera(ObjectIterator var0) {
        var0.getVector().removeAllElements();
        ACCamera var6 = ACCamera.getInstance();
        int var4 = (var6.x + (var6.showWidth >> 1)) / roomWidth;
        int var2 = (var6.y + (var6.showHeight >> 1)) / roomHeight;
        if (var4 >= 0 && var4 < vecWidth && var2 >= 0 && var2 < vecHeight) {
            int var1 = var4 - widthRange;
            int var3 = var2 - heightRange;
            var4 += widthRange;
            int var5 = var2 + heightRange;
            var2 = var1;
            if (var1 < 0) {
                var2 = 0;
            }

            var1 = var3;
            if (var3 < 0) {
                var1 = 0;
            }

            var3 = var4;
            if (var4 >= vecWidth) {
                var3 = vecWidth - 1;
            }

            var4 = var5;
            if (var5 >= vecHeight) {
                var4 = vecHeight - 1;
            }

            if (roomVec != null) {
                while(true) {
                    if (var2 > var3) {
                        var0.init();
                        break;
                    }

                    for(var5 = var1; var5 <= var4; ++var5) {
                        var0.getVector().addElement(roomVec[var2][var5]);
                    }

                    ++var2;
                }
            }
        }

    }

    public static void init(ACWorld var0, int var1, int var2, int var3, int var4) {
        roomWidth = var1;
        roomHeight = var2;
        vecWidth = (var0.getWorldWidth() + var1 - 1) / var1;
        vecHeight = (var0.getWorldHeight() + var2 - 1) / var2;
        roomVec = new Vector[vecWidth][vecHeight];

        for(var1 = 0; var1 < vecWidth; ++var1) {
            for(var2 = 0; var2 < vecHeight; ++var2) {
                roomVec[var1][var2] = new Vector();
            }
        }

        widthRange = var3;
        heightRange = var4;
        destroyWidthRange = var3 + 1;
        destroyHeightRange = var4 + 1;
    }

    public static void objectsLogic() {
        checkObjectsWhenCameraMoved();
    }

public class ObjectIterator implements Iterator {
    private boolean firstGet;
    private int objIndex = 0;
    private int vecIndex = 0;
    private Vector objectVec = new Vector();
    private Vector currentVec = new Vector();

    public Vector getVector() {
        return this.objectVec;
    }

    public void init() {
        this.objIndex = 0;
        this.vecIndex = 0;
        findNextAvailiable();
        this.firstGet = false;
    }

    private void init(Vector objVec) {
        this.objectVec = objVec;
        init();
    }

    public boolean hasNext() {
        int vecIndex2 = this.vecIndex;
        int objIndex2 = this.objIndex;
        Vector currentVec2 = null;
        if (this.firstGet) {
            objIndex2++;
        }
        while (vecIndex2 < this.objectVec.size()) {
            currentVec2 = (Vector) this.objectVec.elementAt(vecIndex2);
            if (objIndex2 < currentVec2.size()) {
                break;
            }
            objIndex2 = 0;
            vecIndex2++;
        }
        return vecIndex2 < this.objectVec.size() || (currentVec2 != null && objIndex2 < currentVec2.size());
    }

    public Object next() {
        if (!this.firstGet) {
            this.firstGet = true;
        } else {
            this.objIndex++;
        }
        findNextAvailiable();
        if (this.vecIndex < this.objectVec.size() || this.objIndex < this.currentVec.size()) {
            Object re = this.currentVec.elementAt(this.objIndex);
            return re;
        }
        return null;
    }

    private void findNextAvailiable() {
        while (this.vecIndex < this.objectVec.size()) {
            this.currentVec = (Vector) this.objectVec.elementAt(this.vecIndex);
            if (this.objIndex >= this.currentVec.size()) {
                this.objIndex = 0;
                this.vecIndex++;
            } else {
                return;
            }
        }
    }

    public void remove() {
        if (this.vecIndex < this.objectVec.size() || this.objIndex < this.currentVec.size()) {
            this.currentVec.removeElementAt(this.objIndex);
            this.firstGet = false;
        }
    }
}

}

