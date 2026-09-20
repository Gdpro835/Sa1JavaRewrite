//
// Decompiled by Jadx - 2544ms
//
package Lib;

import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;

public class AnimationData {
    public static final byte ANI_SIZE = 2;
    private static final boolean DEBUG = false;
    public static final byte FLIP_X = 2;
    public static final byte FLIP_Y = 4;
    public static final byte ROTATE_90 = 1;
    private byte[] TRANMODIF = {0, 6, 2, 4, 1, 7, 3, 5};
    Drawer drawer;
    MFImage[] images;
    Object[][] imagesmanager;
    private boolean isLoad;
    Object[][] object;
    String[][] stringmanager;

    public void close() {
        this.object = null;
        this.imagesmanager = null;
        this.images = null;
        this.stringmanager = null;
        this.drawer = null;
        System.gc();
    }

    public boolean isLoad() {
        return this.isLoad;
    }

    public void newObjectArray(int num) {
        this.object = null;
        System.gc();
        this.object = new Object[num][];
    }

    public void setMFImage(int imageid, MFImage img) {
        this.images[imageid] = img;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    public void releaseMFImageAll() {
        for (int i = 0; i < this.images.length; i++) {
            this.images[i] = null;
        }
        System.gc();
    }

    public int getMFImageNum() {
        return this.images.length;
    }

    public void releaseMFImage(int iObject) {
        if (this.object != null && this.object[iObject] != null) {
            Object[][] frame = (Object[][]) this.object[iObject][0];
            for (Object[] objArr : frame) {
                Object[][] layer = (Object[][]) objArr[0];
                for (Object[] objArr2 : layer) {
                    int imageid = ((byte[]) objArr2[1])[0] & 255;
                    this.images[imageid] = null;
                }
            }
            System.gc();
        }
    }

    public void releasePictureNameAll() {
    }

    public void readMFImage(int iObject) {
        if (this.object != null && this.object[iObject] != null) {
            Object[][] frame = (Object[][]) this.object[iObject][0];
            for (Object[] objArr : frame) {
                Object[][] layer = (Object[][]) objArr[0];
                for (Object[] objArr2 : layer) {
                    int imageid = ((byte[]) objArr2[1])[0] & 255;
                    getMFImage(imageid);
                }
            }
            System.gc();
        }
    }

    public int getObjectNum() {
        if (this.object != null) {
            return this.object.length;
        }
        return -1;
    }

    public int getActionNum(int objectIndex) {
        if (objectIndex < 0 || objectIndex >= this.object.length) {
            return -1;
        }
        Object[] action = (Object[]) this.object[objectIndex][1];
        return action.length;
    }

    public int getPageNum(int objectIndex, int actionIndex) {
        if (objectIndex < 0 || objectIndex >= this.object.length) {
            return -1;
        }
        Object[] action = (Object[]) this.object[objectIndex][1];
        if (actionIndex < 0 || actionIndex >= action.length) {
            return -1;
        }
        Object[][] page = (Object[][]) action[actionIndex];
        return page.length;
    }

    public int getDelayNum(int objectIndex, int actionIndex, int pageIndex) {
        if (objectIndex < 0 || objectIndex >= this.object.length) {
            return -1;
        }
        Object[] action = (Object[]) this.object[objectIndex][1];
        if (actionIndex < 0 || actionIndex >= action.length) {
            return -1;
        }
        Object[][] page = (Object[][]) action[actionIndex];
        if (pageIndex < 0 || pageIndex >= page.length) {
            return -1;
        }
        return ((byte[]) page[pageIndex][0])[1] & 255;
    }

    public int getActionTime(int objectIndex, int actionIndex) {
        int time = 0;
        if (objectIndex < 0 || objectIndex >= this.object.length) {
            return -1;
        }
        Object[] action = (Object[]) this.object[objectIndex][1];
        if (actionIndex < 0 || actionIndex >= action.length) {
            return -1;
        }
        Object[][] page = (Object[][]) action[actionIndex];
        for (Object[] objArr : page) {
            time += (((byte[]) objArr[0])[1] & 255) + 1;
        }
        return time;
    }

    public void printlnMFImagePath(boolean bAll) {
        for (int i = 0; i < this.imagesmanager.length; i++) {
            if ((!bAll && this.images[i] == null) || this.imagesmanager[i] != null) {
                System.out.println((String) this.imagesmanager[i][0]);
            }
        }
    }

    private void loadObject(DataInputStream dis, int i) {
        try {
            this.object[i] = new Object[3];
            int frames = dis.readByte() & 255;
            System.out.println("m_nFrames:" + frames);
            Object[][] objArr = (Object[][]) Array.newInstance((Class<?>) Object.class, frames, 3);
            for (int j = 0; j < frames; j++) {
                System.out.println("Frame:" + j);
                int layers = dis.readByte() & 255;
                System.out.println("layers:" + layers);
                Object[][] layer = (Object[][]) Array.newInstance((Class<?>) Object.class, layers, 3);
                for (int k = 0; k < layers; k++) {
                    byte[] type = new byte[1];
                    type[0] = dis.readByte();
                    System.out.println("type[0]:" + ((int) type[0]));
                    layer[k][0] = type;
                    byte[] b = (byte[]) null;
                    short[] s = (short[]) null;
                    if (type[0] == 0) {
                        b = new byte[]{dis.readByte(), dis.readByte(), dis.readByte()};
                        s = new short[]{dis.readShort(), dis.readShort()};
                    } else if (type[0] == 1) {
                        b = new byte[]{dis.readByte(), dis.readByte(), dis.readByte()};
                        s = new short[]{dis.readShort(), dis.readShort(), dis.readShort(), dis.readShort()};
                    } else if (type[0] == 2) {
                        b = new byte[]{dis.readByte(), dis.readByte(), dis.readByte()};
                        s = new short[]{dis.readShort(), dis.readShort(), dis.readShort(), dis.readShort()};
                    } else if (type[0] == 3) {
                        b = new byte[]{dis.readByte(), dis.readByte(), dis.readByte(), dis.readByte(), dis.readByte(), dis.readByte(), dis.readByte()};
                        s = new short[]{dis.readShort(), dis.readShort()};
                    } else if (type[0] == 4) {
                        s = new short[]{dis.readShort(), dis.readShort(), dis.readShort(), dis.readShort()};
                    } else if (type[0] == 5) {
                        s = new short[]{dis.readShort(), dis.readShort()};
                    } else if (type[0] == 6) {
                        b = new byte[]{dis.readByte(), dis.readByte()};
                        s = new short[]{dis.readShort(), dis.readShort()};
                    }
                    layer[k][1] = b;
                    layer[k][2] = s;
                }
                objArr[j][0] = layer;
                int rectarrays = dis.readByte() & 255;
                System.out.println("rectarrays:" + rectarrays);
                Object[] rectarray = new Object[rectarrays];
                for (int k2 = 0; k2 < rectarrays; k2++) {
                    int rects = dis.readByte() & 255;
                    Object[] rect = new Object[rects];
                    for (int l = 0; l < rects; l++) {
                        short[] s2 = new short[4];
                        s2[0] = dis.readShort();
                        s2[1] = dis.readShort();
                        s2[2] = dis.readShort();
                        s2[3] = dis.readShort();
                        rect[l] = s2;
                    }
                    rectarray[k2] = rect;
                }
                objArr[j][1] = rectarray;
                int crossarrays = dis.readByte() & 255;
                System.out.println("crossarrays:" + crossarrays);
                Object[] crossarray = new Object[crossarrays];
                for (int k3 = 0; k3 < crossarrays; k3++) {
                    int crosses = dis.readByte() & 255;
                    Object[] cross = new Object[crosses];
                    for (int l2 = 0; l2 < crosses; l2++) {
                        short[] s3 = new short[2];
                        s3[0] = dis.readShort();
                        s3[1] = dis.readShort();
                        cross[l2] = s3;
                    }
                    crossarray[k3] = cross;
                }
                objArr[j][2] = crossarray;
            }
            this.object[i][0] = objArr;
            int actions = dis.readByte() & 255;
            Object[] action = new Object[actions];
            for (int j2 = 0; j2 < actions; j2++) {
                int pages = dis.readByte() & 255;
                Object[][] page = (Object[][]) Array.newInstance((Class<?>) Object.class, pages, 2);
                for (int k4 = 0; k4 < pages; k4++) {
                    byte[] b2 = new byte[2];
                    b2[0] = dis.readByte();
                    b2[1] = dis.readByte();
                    page[k4][0] = b2;
                    short[] s4 = new short[2];
                    s4[0] = dis.readShort();
                    s4[1] = dis.readShort();
                    page[k4][1] = s4;
                }
                action[j2] = page;
            }
            this.object[i][1] = action;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean load(String datapath, int[] objectIndex, int[] imageIndex) {
        this.isLoad = DEBUG;
        InputStream in = MFDevice.getResourceAsStream(datapath);
        if (in == null) {
            return DEBUG;
        }
        DataInputStream dis = new DataInputStream(in);
        try {
            int objects = dis.readByte() & 255;
            newObjectArray(objects);
            int j = 0;
            for (int i = 0; i < objects; i++) {
                int len = dis.readInt();
                if (objectIndex == null || (j < objectIndex.length && objectIndex[j] == i)) {
                    loadObject(dis, i);
                    j++;
                } else {
                    dis.skip(len);
                }
            }
            this.imagesmanager = (Object[][]) Array.newInstance((Class<?>) Object.class, dis.readByte() & 255, 2);
            this.images = new MFImage[this.imagesmanager.length];
            for (int j2 = 0; j2 < this.imagesmanager.length; j2++) {
                short[][] clip = (short[][]) Array.newInstance((Class<?>) Short.TYPE, dis.readByte() & 255, 4);
                for (int k = 0; k < clip.length; k++) {
                    for (int l = 0; l < clip[k].length; l++) {
                        clip[k][l] = dis.readShort();
                    }
                }
                this.imagesmanager[j2][0] = dis.readUTF();
                this.imagesmanager[j2][1] = clip;
            }
            this.stringmanager = new String[dis.readByte() & 255][];
            for (int i2 = 0; i2 < this.stringmanager.length; i2++) {
                this.stringmanager[i2] = new String[dis.readByte() & 255];
                for (int j3 = 0; j3 < this.stringmanager[i2].length; j3++) {
                    this.stringmanager[i2][j3] = dis.readUTF();
                }
            }
            if (imageIndex == null) {
                for (int i3 = 0; i3 < this.imagesmanager.length; i3++) {
                    getMFImage(i3);
                }
            } else {
                for (int i4 : imageIndex) {
                    getMFImage(i4);
                }
            }
            dis.close();
            this.isLoad = true;
            return this.isLoad;
        } catch (Exception e) {
            e.printStackTrace();
            return DEBUG;
        }
    }

    public String getString(int iArray, int i) {
        return this.stringmanager[iArray][i];
    }

    public int getStringNum(int iArray) {
        return this.stringmanager[iArray].length;
    }

    public MFImage getMFImage(int imageid) {
        if (imageid >= this.images.length || imageid < 0) {
            return null;
        }
        if (this.images[imageid] == null) {
            try {
                this.images[imageid] = MFImage.createImage((String) this.imagesmanager[imageid][0]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return this.images[imageid];
    }

    public String getImageName(int imageid) {
        if (imageid >= this.images.length || imageid < 0) {
            return null;
        }
        return (String) this.imagesmanager[imageid][0];
    }

    public short[] getCross(int objectid, int frameid, int i, int j, int iX, int iY, int attr, int px, int py) {
        Object[][] frame = (Object[][]) this.object[objectid][0];
        Object[] crossarray = (Object[]) frame[frameid][2];
        if (i >= crossarray.length) {
            return null;
        }
        Object[] cross = (Object[]) crossarray[i];
        if (j >= cross.length) {
            return null;
        }
        int x = px + ((short[]) cross[j])[0];
        int y = py + ((short[]) cross[j])[1];
        if ((attr & 2) > 0) {
            x = -x;
        }
        if ((attr & 4) > 0) {
            y = -y;
        }
        short[] temp = {(short) (x + iX), (short) (y + iY)};
        return temp;
    }

    public short[][] getCrossArray(int objectid, int frameid, int i, int iX, int iY, int attr, int px, int py) {
        Object[][] frame = (Object[][]) this.object[objectid][0];
        Object[] crossarray = (Object[]) frame[frameid][2];
        if (i >= crossarray.length) {
            return null;
        }
        Object[] cross = (Object[]) crossarray[i];
        short[][] temp = new short[cross.length][];
        for (int j = 0; j < temp.length; j++) {
            temp[j] = getCross(objectid, frameid, i, j, iX, iY, attr, px, py);
        }
        return temp;
    }

    public int getCrossNum(int objectid, int frameid, int i) {
        Object[][] frame = (Object[][]) this.object[objectid][0];
        Object[] crossarray = (Object[]) frame[frameid][2];
        Object[] cross = (Object[]) crossarray[i];
        return cross.length;
    }

    public int getCrossArrayNum(int objectid, int frameid) {
        Object[][] frame = (Object[][]) this.object[objectid][0];
        Object[] crossarray = (Object[]) frame[frameid][2];
        return crossarray.length;
    }

    public short[] getRect(int objectid, int frameid, int i, int j, int iX, int iY, int attr, int px, int py) {
        Object[][] frame = (Object[][]) this.object[objectid][0];
        Object[] rectarray = (Object[]) frame[frameid][1];
        if (i >= rectarray.length) {
            return null;
        }
        Object[] rect = (Object[]) rectarray[i];
        if (j >= rect.length) {
            return null;
        }
        int x = px + ((short[]) rect[j])[0];
        int y = py + ((short[]) rect[j])[1];
        if ((attr & 2) > 0) {
            x = ((-x) - ((short[]) rect[j])[2]) + 1;
        }
        if ((attr & 4) > 0) {
            y = ((-y) - ((short[]) rect[j])[3]) + 1;
        }
        short[] temp = {(short) (x + iX), (short) (y + iY), ((short[]) rect[j])[2], ((short[]) rect[j])[3]};
        return temp;
    }

    public short[][] getRectArray(int objectid, int frameid, int i, int iX, int iY, int attr, int px, int py) {
        Object[][] frame = (Object[][]) this.object[objectid][0];
        Object[] rectarray = (Object[]) frame[frameid][1];
        if (i >= rectarray.length) {
            return null;
        }
        Object[] rect = (Object[]) rectarray[i];
        short[][] temp = new short[rect.length][];
        for (int j = 0; j < temp.length; j++) {
            temp[j] = getRect(objectid, frameid, i, j, iX, iY, attr, px, py);
        }
        return temp;
    }

    public int getRectNum(int objectid, int frameid, int i) {
        Object[][] frame = (Object[][]) this.object[objectid][0];
        Object[] rectarray = (Object[]) frame[frameid][1];
        Object[] rect = (Object[]) rectarray[i];
        return rect.length;
    }

    public int getRectArrayNum(int objectid, int frameid) {
        Object[][] frame = (Object[][]) this.object[objectid][0];
        Object[] rectarray = (Object[]) frame[frameid][1];
        return rectarray.length;
    }

    private void fillRect(MFGraphics g, int iX, int iY, Object[][] layer, int layerid, int attr, int px, int py) {
        byte[] rgb = (byte[]) layer[layerid][1];
        short[] rect = (short[]) layer[layerid][2];
        int x = px + rect[2];
        int y = py + rect[3];
        if ((attr & 2) > 0) {
            x = ((-x) - rect[0]) + 1;
        }
        if ((attr & 4) > 0) {
            y = ((-y) - rect[1]) + 1;
        }
        int color = g.getColor();
        g.setColor(rgb[0] & 255, rgb[1] & 255, rgb[2] & 255);
        g.fillRect(iX + x, iY + y, rect[0], rect[1]);
        g.setColor(color);
    }

    private void drawRect(MFGraphics g, int iX, int iY, Object[][] layer, int layerid, int attr, int px, int py) {
        byte[] rgb = (byte[]) layer[layerid][1];
        short[] rect = (short[]) layer[layerid][2];
        int x = px + rect[2];
        int y = py + rect[3];
        if ((attr & 2) > 0) {
            x = ((-x) - rect[0]) + 1;
        }
        if ((attr & 4) > 0) {
            y = ((-y) - rect[1]) + 1;
        }
        int color = g.getColor();
        g.setColor(rgb[0] & 255, rgb[1] & 255, rgb[2] & 255);
        g.drawRect(iX + x, iY + y, rect[0], rect[1]);
        g.setColor(color);
    }

    private void drawString(MFGraphics g, int iX, int iY, Object[][] layer, int layerid, int px, int py) {
        byte[] linergb = (byte[]) layer[layerid][1];
        if (this.stringmanager != null && this.stringmanager[linergb[0]] != null && this.stringmanager[linergb[0]][linergb[1]] != null) {
            short[] xy = (short[]) layer[layerid][2];
            int color = g.getColor();
            g.setColor(linergb[2] & 255, linergb[3] & 255, linergb[4] & 255);
            int font = g.getFont();
            if (linergb[5] == 0) {
                g.setFont(-1);
            } else if (linergb[5] == 1) {
                g.setFont(-2);
            } else if (linergb[5] == 2) {
                g.setFont(-3);
            }
            if (linergb[6] == 0) {
                g.drawString(this.stringmanager[linergb[0]][linergb[1]], iX + px + xy[0], iY + py + xy[1], 20);
            } else if (linergb[6] == 1) {
                g.drawString(this.stringmanager[linergb[0]][linergb[1]], iX + px + xy[0], iY + py + xy[1], 17);
            } else if (linergb[6] == 2) {
                g.drawString(this.stringmanager[linergb[0]][linergb[1]], iX + px + xy[0], iY + py + xy[1], 24);
            }
            g.setFont(font);
            g.setColor(color);
        }
    }

    private void callbackRect(MFGraphics g, int iX, int iY, Object[][] layer, int objectid, int frameid, int layerid, int attr, int px, int py) {
        short[] rect = (short[]) layer[layerid][2];
        int x = px + rect[2];
        int y = py + rect[3];
        if ((attr & 2) > 0) {
            int x2 = ((-x) - rect[0]) + 1;
        }
        if ((attr & 4) > 0) {
            int y2 = ((-y) - rect[1]) + 1;
        }
        if (this.drawer != null) {
            this.drawer.callbackRect(g, this, objectid, frameid, layerid, iX, iY, rect[0], rect[1], attr);
        }
    }

    private void callbackCross(MFGraphics g, int iX, int iY, Object[][] layer, int objectid, int frameid, int layerid, int attr, int px, int py) {
        short[] xy = (short[]) layer[layerid][2];
        int x = px + xy[0];
        int y = py + xy[1];
        if ((attr & 2) > 0) {
            int i = -x;
        }
        if ((attr & 4) > 0) {
            int i2 = -y;
        }
        if (this.drawer != null) {
            this.drawer.callbackCross(g, this, objectid, frameid, layerid, iX, iY, attr);
        }
    }

    private void drawIndex(MFGraphics g, int iX, int iY, MFImage[] imgs, Object[][] layer, int layerid, int attr, int px, int py) {
        byte[] index = (byte[]) layer[layerid][1];
        short[] xy = (short[]) layer[layerid][2];
        draw(g, iX, iY, imgs, index[0], index[1], attr, px + xy[0], py + xy[1]);
    }

    private void drawMFImage(MFGraphics g, int iX, int iY, MFImage[] imgs, Object[][] layer, int layerid, int attr, int px, int py) {
        MFImage image;
        int imageid = ((byte[]) layer[layerid][1])[0] & 255;
        if (imgs != null && imgs.length > imageid && imgs[imageid] != null) {
            image = imgs[imageid];
        } else {
            image = getMFImage(imageid);
        }
        if (image != null) {
            int clipid = ((byte[]) layer[layerid][1])[1] & 255;
            short[][] clip = (short[][]) this.imagesmanager[imageid][1];
            short[] rect = clip[clipid];
            int tran = ((byte[]) layer[layerid][1])[2];
            int x = px + ((short[]) layer[layerid][2])[0];
            int y = py + ((short[]) layer[layerid][2])[1];
            if ((tran & 1) > 0) {
                if ((attr & 2) > 0 && (attr & 4) > 0) {
                    x = ((-x) - rect[3]) + 1;
                    y = ((-y) - rect[2]) + 1;
                } else if ((attr & 2) > 0) {
                    x = ((-x) - rect[3]) + 1;
                    attr = (attr & (-3)) | 4;
                } else if ((attr & 4) > 0) {
                    y = ((-y) - rect[2]) + 1;
                    attr = (attr & (-5)) | 2;
                }
            } else {
                if ((attr & 2) > 0) {
                    x = ((-x) - rect[2]) + 1;
                }
                if ((attr & 4) > 0) {
                    y = ((-y) - rect[3]) + 1;
                }
                if ((attr & 1) > 0) {
                    int tmp = y;
                    y = ((-x) - rect[2]) + 1;
                    x = tmp;
                }
            }
            g.drawRegion(image, rect[0], rect[1], rect[2], rect[3], this.TRANMODIF[tran ^ attr], iX + x, iY + y, 0);
        }
    }

    public void draw(MFGraphics g, int iX, int iY, MFImage[] imgs, int objectid, int frameid, int attr, int px, int py) {
        Object[][] frame = (Object[][]) this.object[objectid][0];
        Object[][] layer = (Object[][]) frame[frameid][0];
        for (int layerid = 0; layerid < layer.length; layerid++) {
            switch (((byte[]) layer[layerid][0])[0]) {
                case 0:
                    drawMFImage(g, iX, iY, imgs, layer, layerid, attr, px, py);
                    break;
                case 1:
                    fillRect(g, iX, iY, layer, layerid, attr, px, py);
                    break;
                case 2:
                    drawRect(g, iX, iY, layer, layerid, attr, px, py);
                    break;
                case 3:
                    drawString(g, iX, iY, layer, layerid, px, py);
                    break;
                case 4:
                    if (this.drawer != null) {
                        callbackRect(g, iX, iY, layer, objectid, frameid, layerid, attr, px, py);
                        break;
                    } else {
                        break;
                    }
                case 5:
                    if (this.drawer != null) {
                        callbackCross(g, iX, iY, layer, objectid, frameid, layerid, attr, px, py);
                        break;
                    } else {
                        break;
                    }
                case 6:
                    drawIndex(g, iX, iY, imgs, layer, layerid, attr, px, py);
                    break;
            }
        }
    }

    public void draw(MFGraphics g, int iX, int iY, MFImage[] imgs, int objectid, int actionid, int pageid, int attr) {
        if (objectid < this.object.length) {
            Object[] action = (Object[]) this.object[objectid][1];
            if (actionid < action.length) {
                Object[][] page = (Object[][]) action[actionid];
                if (pageid < page.length) {
                    int frameid = ((byte[]) page[pageid][0])[0] & 255;
                    draw(g, iX, iY, imgs, objectid, frameid, attr, ((short[]) page[pageid][1])[0], ((short[]) page[pageid][1])[1]);
                }
            }
        }
    }
}
