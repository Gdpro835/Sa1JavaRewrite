package SonicGBA;

import com.sega.engine.action.ACBlock;
import com.sega.engine.action.ACDegreeGetter;
import com.sega.engine.action.ACUtilities;
import com.sega.engine.action.ACWorld;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGamePad;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;

public class CollisionMap extends ACWorld implements SonicDef {
    private static final byte[] BLANK_BLOCK = new byte[8];
    public static final String COLLISION_FILE_NAME = ".co";
    private static final byte[] FULL_BLOCK = new byte[8];
    private static final int GRID_NUM_PER_MODEL = 12;
    private static final int LOAD_COLLISION_INFO = 2;
    private static final int LOAD_MODEL_INFO = 1;
    private static final int LOAD_OPEN_FILE = 0;
    private static final int LOAD_OVER = 3;
    public static final String MODEL_FILE_NAME = ".ci";
    private static CollisionMap instance;
    private static int loadStep = 0;
    private byte[][] collisionInfo;
    private MyDegreeGetter degreeGetter;
    private byte[] directionInfo;
    private DataInputStream ds;
    private short[][][] modelInfo;

    public static CollisionMap getInstance() {
        if (instance == null) {
            instance = new CollisionMap();
        }
        return instance;
    }

    static {
        for (int i = 0; i < FULL_BLOCK.length; i++) {
            FULL_BLOCK[i] = -120;
        }
    }

    public boolean loadCollisionInfoStep(String stageName) {
        switch (loadStep) {
            case 0:
                this.ds = new DataInputStream(MFDevice.getResourceAsStream("/map/" + stageName + MODEL_FILE_NAME));
                break;
            case 1:
                this.modelInfo = (short[][][]) Array.newInstance((Class<?>) Short.TYPE, MapManager.mapModel.length, GRID_NUM_PER_MODEL, GRID_NUM_PER_MODEL);
                for (int i = 0; i < this.modelInfo.length; i ++) {
                    try {
                        for (int y = 0; y < 12; y ++) {
                            for (int x = 0; x < 12; x ++) {
                                this.modelInfo[i][x][y] = (short) (this.ds.readShort() & 65535);
                            }
                        }
                    } catch (Exception e) {
                        if (this.ds != null) {
                            try {
                                this.ds.close();
                                break;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                break;
                            }
                        }
                    } catch (Throwable th) {
                        if (this.ds != null) {
                            try {
                                this.ds.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
                if (this.ds != null) {
                    try {
                        this.ds.close();
                        break;
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        break;
                    }
                }
                break;
            case 2:
                this.ds = new DataInputStream(MFDevice.getResourceAsStream("/map/" + stageName + COLLISION_FILE_NAME));
                try {
                    int collisionKindNum = this.ds.readShort();
                    this.collisionInfo = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, collisionKindNum, 8);
                    this.directionInfo = new byte[collisionKindNum];
                    for (int i2 = 0; i2 < collisionKindNum; i2 ++) {
                        for (int n = 0; n < 8; n ++) {
                            this.collisionInfo[i2][n] = this.ds.readByte();
                        }
                        this.directionInfo[i2] = this.ds.readByte();
                    }
                    if (this.ds != null) {
                        try {
                            this.ds.close();
                            break;
                        } catch (IOException e5) {
                            e5.printStackTrace();
                            break;
                        }
                    }
                } catch (Exception e6) {
                    if (this.ds != null) {
                        try {
                            this.ds.close();
                            break;
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            break;
                        }
                    }
                } catch (Throwable th2) {
                    if (this.ds != null) {
                        try {
                            this.ds.close();
                        } catch (IOException e8) {
                            e8.printStackTrace();
                        }
                    }
                    throw th2;
                }
                break;
            case 3:
                loadStep = 0;
                return true;
        }
        if (1 != 0) {
            loadStep ++;
        }
        return false;
    }

    public void getCollisionInfoWithBlock(int blockX, int blockY, int currentLayer, ACBlock block) {
        getCollisionBlock(block, getTileWidth() * blockX, getTileHeight() * blockY, currentLayer);
    }

    private int getBlockIndexWithBlock(int blockX, int blockY, int currentLayer) {
        if (currentLayer == 1) {
            return getTileId(MapManager.mapBack, blockX, blockY);
        }
        return getTileId(MapManager.mapFront, blockX, blockY);
    }

    private int getTileId(short[][] mapArray, int x, int y) {
        return this.modelInfo[mapArray[x / 12][y / 12]][x % 12][y % 12];
    }

    public void closeMap() {
        this.collisionInfo = null;
        this.directionInfo = null;
        this.modelInfo = null;
    }

    private CollisionMap() {
    }

    public void getCollisionBlock(ACBlock block, int x, int y, int layer) {
        if (block instanceof CollisionBlock) {
            int blockX = ACUtilities.getQuaParam(x - (MapManager.mapOffsetX << 6), getTileWidth());
            int blockY = ACUtilities.getQuaParam(y, getTileHeight());
            CollisionBlock myBlock = (CollisionBlock) block;
            myBlock.setPosition((getTileWidth() * blockX) + (MapManager.mapOffsetX << 6), getTileHeight() * blockY);
            if (blockX < 0) {
                myBlock.setProperty(BLANK_BLOCK, false, false, 64, false);
            } else if (blockY < 0 || blockY >= MapManager.mapHeight * 12) {
                myBlock.setProperty(BLANK_BLOCK, false, false, 0, false);
            } else {
                int tileId = getBlockIndexWithBlock((MapManager.getConvertX(blockX / 12) * 12) + (blockX % 12), blockY, layer);
                int cell_id = tileId & 8191;
                myBlock.setProperty(this.collisionInfo[cell_id], (tileId & MFGamePad.KEY_NUM_8) != 0, (32768 & tileId) != 0, this.directionInfo[cell_id], (tileId & 8192) != 0);
            }
        }
    }

    public ACDegreeGetter getDegreeGetterForObject() {
        if (this.degreeGetter == null) {
            this.degreeGetter = new MyDegreeGetter(this);
        }
        return this.degreeGetter;
    }

    public ACBlock getNewCollisionBlock() {
        return new CollisionBlock(this);
    }

    public int getTileHeight() {
        return 512;
    }

    public int getTileWidth() {
        return 512;
    }

    public int getWorldHeight() {
        return MapManager.getPixelHeight() << 6;
    }

    public int getWorldWidth() {
        return MapManager.getPixelWidth() << 6;
    }

    public int getZoom() {
        return 6;
    }
}
