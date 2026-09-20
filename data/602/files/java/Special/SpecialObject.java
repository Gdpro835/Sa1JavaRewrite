package Special;

import Lib.Animation;
import Lib.AnimationDrawer;
import SonicGBA.CollisionRect;
import SonicGBA.GlobalResource;
import SonicGBA.PlayerObject;
import SonicGBA.StageManager;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.device.MFGraphics;
import java.util.Vector;

public abstract class SpecialObject implements SSDef {
    protected static final int CAMERA_TO_PLAYER = 6;
    public static final int COLLISION_RANGE_Z = 8;
    protected static final int D = 30;
    private static final String OBJ_RES_NAME = "/sp_obj";
    private static final int SCALE_LEFT = -240;
    private static final float SCALE_PARAM_1 = 320.0f;
    private static final int SCALE_RIGHT = 240;
    private static final int SHOW_RANGE = 150;
    public static final int Z_ZOOM = 3;
    protected static Vector decideObjects = new Vector();
    protected static int drawX;
    protected static int drawY;
    private static Vector extraObjects = new Vector();
    private static int firstObj;
    private static int lastObj;
    public static Animation objAnimation;
    private static SpecialObject[] objectArray;
    private static Vector paintObjects = new Vector();
    public static SpecialPlayer player;
    protected static AnimationDrawer ringDrawer;
    protected static float scale;
    protected CollisionRect collisionRect = new CollisionRect();
    protected AnimationDrawer drawer;
    protected int objID;
    protected int posX;
    protected int posY;
    protected int posZ;

    public abstract void close();

    public abstract void doWhileCollision(SpecialObject specialObject);

    public abstract void draw(MFGraphics mFGraphics);

    public abstract void logic();

    public abstract void refreshCollision(int i, int i2);

    public static void initObjects() {
        closeObjects();
        if (objAnimation == null) {
            if (GlobalResource.languageConfig != 8) {
                objAnimation = new Animation("/special_res/sp_obj");
            } else {
                objAnimation = new Animation("/lang8/special_res/sp_obj");
            }
        }
        player = new SpecialPlayer(PlayerObject.getCharacterID());
        Vector tmpVector = new Vector();
        int[][] currentStage = SSMapData.STAGE_LIST[STAGE_ID_TO_SPECIAL_ID[StageManager.getStageID()]];
        for (int[] info : currentStage) {
            SpecialObject tmpObj = getNewInstance(info[3], info[0], info[1], info[2]);
            if (tmpObj != null) {
                tmpVector.addElement(tmpObj);
            }
        }
        objectArray = new SpecialObject[tmpVector.size()];
        tmpVector.copyInto(objectArray);
    }

    public static void closeObjects() {
        extraObjects.removeAllElements();
        paintObjects.removeAllElements();
        decideObjects.removeAllElements();
        if (objectArray != null) {
            for (int i = 0; i < objectArray.length; i++) {
                if (objectArray[i] != null) {
                    objectArray[i].close();
                }
            }
            objectArray = null;
        }
        if (player != null) {
            player.close();
            player = null;
        }
    }

    public static SpecialObject getNewInstance(int id, int x, int y, int z) {
        SpecialObject re = null;
        switch (id) {
            case 0:
                re = new SSRing(x, y, z);
                break;
            case 1:
                re = new TrickRing(x, y, z);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                re = new SSSpring(id, x, y, z);
                break;
            case 9:
                re = new SSBomb(x, y, z);
                break;
            case 10:
                re = new SSCheckPoint(x, y, z);
                break;
            case 11:
                re = new SSGoal(x, y, z);
                break;
        }
        if (re != null) {
            re.refreshCollision(re.posX, re.posY);
        }
        return re;
    }

    protected static void calDrawPosition(int x, int y, int z) {
        int z2 = -(z - (player.posZ - 6));
        drawX = (x * 30) / (30 - z2);
        drawY = ((-y) * 30) / (30 - z2);
        scale = ((float) ((7200 / (30 - z2)) - (-7200 / (30 - z2)))) / SCALE_PARAM_1;
    }

    public static void objectLogic() {
        int objZ;
        int objZ2;
        int startZ = ((player.posZ - 6) - 15) + 1;
        int endZ = startZ + SHOW_RANGE;
        firstObj = -1;
        lastObj = 0;
        int i = 0;
        while (true) {
            if (i < objectArray.length) {
                if (objectArray[i] != null && startZ <= (objZ = objectArray[i].posZ) && endZ > objZ) {
                    firstObj = i;
                    lastObj = i;
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        paintObjects.removeAllElements();
        if (firstObj == -1) {
            paintObjects.addElement(player);
        } else {
            for (int i2 = firstObj; i2 < objectArray.length; i2++) {
                if (objectArray[i2] != null && startZ <= (objZ2 = objectArray[i2].posZ) && endZ > objZ2) {
                    lastObj = i2;
                }
            }
            boolean firstAdd = false;
            for (int i3 = firstObj; i3 <= lastObj; i3++) {
                if (objectArray[i3] != null) {
                    objectArray[i3].logic();
                    if (!firstAdd && player.posZ < objectArray[i3].posZ) {
                        firstAdd = true;
                        paintObjects.addElement(player);
                    }
                    paintObjects.addElement(objectArray[i3]);
                }
            }
            if (!firstAdd) {
                paintObjects.addElement(player);
            }
        }
        int i4 = 0;
        while (i4 < extraObjects.size()) {
            SpecialObject obj = (SpecialObject) extraObjects.elementAt(i4);
            obj.logic();
            if (obj.chkDestroy()) {
                extraObjects.removeElementAt(i4);
                i4--;
            } else {
                boolean added = false;
                int j = 0;
                while (true) {
                    if (j >= paintObjects.size()) {
                        break;
                    } else if (obj.posZ < ((SpecialObject) paintObjects.elementAt(j)).posZ) {
                        added = true;
                        paintObjects.insertElementAt(obj, j);
                        break;
                    } else {
                        j++;
                    }
                }
                if (!added) {
                    paintObjects.addElement(obj);
                }
            }
            i4++;
        }
        player.refreshCollisionWrap();
        for (int i5 = 0; i5 < paintObjects.size(); i5++) {
            SpecialObject obj2 = (SpecialObject) paintObjects.elementAt(i5);
            if (obj2 != player && obj2.posZ >= player.posZ - 8) {
                if (obj2.posZ <= player.posZ + 8) {
                    obj2.refreshCollisionWrap();
                    obj2.doCollisionCheckWith(player);
                } else {
                    return;
                }
            }
        }
    }

    public static void drawObjects(MFGraphics g) {
        if (ringDrawer != null && !AnimationDrawer.isAllPause()) {
            ringDrawer.moveOn();
        }
        for (int i = paintObjects.size() - 1; i >= 0; i--) {
            ((SpecialObject) paintObjects.elementAt(i)).draw(g);
        }
    }

    public static void addExtraObject(SpecialObject object) {
        extraObjects.addElement(object);
    }

    public SpecialObject(int id, int x, int y, int z) {
        this.objID = id;
        this.posX = x;
        this.posY = y;
        this.posZ = z >> 3;
    }

    public boolean chkDestroy() {
        return false;
    }

    public void doCollisionCheckWith(SpecialPlayer obj) {
        if (this.collisionRect.collisionChk(obj.collisionRect)) {
            doWhileCollision(obj);
        }
        if ((this instanceof SSRing) && this.collisionRect.collisionChk(obj.attackCollisionRect)) {
            doWhileCollision(obj);
        }
    }

    public void refreshCollisionWrap() {
        refreshCollision(this.posX, this.posY);
    }

    public void drawObj(MFGraphics g, AnimationDrawer drawer2, int xOffset, int yOffset) {
        Graphics g2 = (Graphics) g.getSystemGraphics();
        g2.save();
        g2.translate((float) ((drawX + (SCREEN_WIDTH >> 1)) - SpecialMap.getCameraOffsetX()), (float) ((drawY + (SCREEN_HEIGHT >> 1)) - SpecialMap.getCameraOffsetY()));
        g2.scale(scale, scale);
        drawer2.draw(g, xOffset, yOffset);
        g2.restore();
    }

    public void drawCollisionRect(MFGraphics g) {
    }
}
