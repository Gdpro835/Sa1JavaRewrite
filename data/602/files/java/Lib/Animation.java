//
// Decompiled by Jadx - 999ms
//
package Lib;

import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.io.FileNotFoundException;

public class Animation {
    private static final boolean SEPERATE_PNG = false;
    private static Animation[] animationInstance;
    public static boolean isFrameWanted;
    public static boolean isImageWanted;
    protected String fileName;
    private ImageInfo[] imageInfo;
    private boolean isAnimationQi;
    public boolean isDoubleScale;
    private Action[] m_Actions;
    private int m_CurAni;
    private Frame[] m_Frames;
    private int m_OldAni;
    private int m_nActions;
    private int m_nFrames;
    private Animation[] qiAnimationArray;
    protected int refCount;
    private static final int TRANS_OFFSET = 1;
    private static final String[] DOUBLE_ANIMATION_NAME = {"chr_sonic", "chr_tails_01", "chr_tails_02", "chr_knuckles_01", "chr_knuckles_02", "chr_amy_01", "chr_amy_02"};
    private static String tmpPath = "";
    private static int[] imageIdArray = new int[10];

    private Animation() {
        this.isDoubleScale = false;
        this.fileName = null;
        this.isAnimationQi = false;
        this.m_CurAni = 0;
        this.m_OldAni = 0;
        this.refCount = 0;
    }

    public Animation(String fileName) {
        this.isDoubleScale = false;
        this.fileName = null;
        this.isAnimationQi = false;
        int i = 0;
        while (true) {
            if (i >= DOUBLE_ANIMATION_NAME.length) {
                break;
            }
            if (!fileName.endsWith(DOUBLE_ANIMATION_NAME[i])) {
                i++;
            } else {
                this.isDoubleScale = true;
                break;
            }
        }
        this.m_CurAni = 0;
        this.m_OldAni = 0;
        this.fileName = fileName;
        SetClipImg(String.valueOf(fileName) + ".png");
        LoadAnimation(String.valueOf(fileName) + ".dat");
    }

    public Animation(MFImage image, String fileName) {
        this.isDoubleScale = false;
        this.fileName = null;
        this.isAnimationQi = false;
        int i = 0;
        while (true) {
            if (i >= DOUBLE_ANIMATION_NAME.length) {
                break;
            }
            if (!fileName.endsWith(DOUBLE_ANIMATION_NAME[i])) {
                i++;
            } else {
                this.isDoubleScale = true;
                break;
            }
        }
        this.m_CurAni = 0;
        this.m_OldAni = 0;
        this.fileName = fileName;
        this.imageInfo = new ImageInfo[1];
        this.imageInfo[0] = new ImageInfo(image, (ImageInfo) null);
        LoadAnimation(String.valueOf(fileName) + ".dat");
    }

    protected void close() {
        if (this.imageInfo != null) {
            for (int i = 0; i < this.imageInfo.length; i++) {
                if (this.imageInfo[i] != null) {
                    this.imageInfo[i].close();
                }
                this.imageInfo[i] = null;
            }
        }
        this.imageInfo = null;
    }

    public void setImage(MFImage image, int id) {
        this.imageInfo[id].img_clip = image;
    }

    public void SetCurAni(int no) {
        if (no >= this.m_nActions) {
            no = 0;
        }
        this.m_CurAni = no;
        if (this.m_OldAni != this.m_CurAni) {
            this.m_OldAni = this.m_CurAni;
            this.m_Actions[this.m_CurAni].JumpFrame((byte) 0);
            this.m_Actions[this.m_CurAni].SetPause(false);
        }
    }

    public void SetCurFrame(short frameId) {
        this.m_Actions[this.m_CurAni].SetFrame(frameId);
    }

    public int GetCurAni() {
        return this.m_CurAni;
    }

    public void JumpFrame(byte no) {
        this.m_Actions[this.m_CurAni].JumpFrame(no);
    }

    public short GetFrameNo() {
        return this.m_Actions[this.m_CurAni].GetFrameNo();
    }

    public byte[] GetARect() {
        return this.m_Actions[this.m_CurAni].GetARect();
    }

    public byte[] GetCRect() {
        return this.m_Actions[this.m_CurAni].GetCRect();
    }

    public boolean IsEnd() {
        return this.m_Actions[this.m_CurAni].IsEnd();
    }

    public void SetLoop(boolean loop) {
        this.m_Actions[this.m_CurAni].SetLoop(loop);
    }

    public void SetPause(boolean pause) {
        this.m_Actions[this.m_CurAni].SetPause(pause);
    }

    public void SetClipImg(String fn) {
        try {
            this.imageInfo = new ImageInfo[1];
            this.imageInfo[0] = new ImageInfo(fn, (ImageInfo) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetClipImg(MFImage img) {
        this.imageInfo = new ImageInfo[1];
        this.imageInfo[0] = new ImageInfo(img, (ImageInfo) null);
    }

    public void LoadAnimation(String fileName) {
        InputStream in = MFDevice.getResourceAsStream(fileName);
if (in == null) {
    InputStream jsonIn = MFDevice.getResourceAsStream(fileName.replace(".dat", ".json"));
    if (jsonIn != null) {
        try {
            in = JsonRecompiler.recompileJson(jsonIn);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
        LoadAnimation(in);
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void LoadAnimation(InputStream in) {
        if (in != null) {
            try {
                this.imageInfo[0].loadInfo(in);
                this.m_nFrames = (byte) in.read();
                if (this.m_nFrames < 0) {
                    this.m_nFrames += 256;
                }
                this.m_Frames = new Frame[this.m_nFrames];
                for (int i = 0; i < this.m_nFrames; i++) {
                    this.m_Frames[i] = new Frame(this, this);
                    this.m_Frames[i].LoadFrame(in);
                    this.m_Frames[i].SetClips(this.imageInfo[0].m_Clips);
                }
                this.m_nActions = (byte) in.read();
                if (this.m_nActions < 0) {
                    this.m_nActions += 256;
                }
                this.m_Actions = new Action[this.m_nActions];
                for (int i2 = 0; i2 < this.m_nActions; i2++) {
                    this.m_Actions[i2] = new Action(this, this);
                    this.m_Actions[i2].LoadAction(in);
                    this.m_Actions[i2].SetFrames(this.m_Frames);
                }
                if (this.isDoubleScale) {
                    this.imageInfo[0].doubleParam();
                    return;
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            this.m_nFrames = 1;
            this.m_Frames = new Frame[this.m_nFrames];
            for (int i3 = 0; i3 < this.m_nFrames; i3++) {
                this.m_Frames[i3] = new Frame(this, this);
                this.m_Frames[i3].LoadFrame(in);
                this.m_Frames[i3].SetClips(this.imageInfo[0].m_Clips);
            }
            this.m_nActions = 1;
            this.m_Actions = new Action[this.m_nActions];
            for (int i4 = 0; i4 < this.m_nActions; i4++) {
                this.m_Actions[i4] = new Action(this, this);
                this.m_Actions[i4].LoadAction();
                this.m_Actions[i4].SetFrames(this.m_Frames);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void LoadAnimationG2(DataInputStream ds) {
        this.isAnimationQi = true;
        try {
            ds.readInt();
            this.m_nFrames = ds.readByte();
            if (this.m_nFrames < 0) {
                this.m_nFrames += 256;
            }
            this.m_Frames = new Frame[this.m_nFrames];
            for (int i = 0; i < this.m_nFrames; i++) {
                this.m_Frames[i] = new Frame(this, this);
                this.m_Frames[i].loadFrameG2(ds);
                int rectarrays = ds.readByte() & 255;
                for (int k = 0; k < rectarrays; k++) {
                    int rectsNum = ds.readByte() & 255;
                    for (int l = 0; l < rectsNum; l++) {
                        ds.readShort();
                        ds.readShort();
                        ds.readShort();
                        ds.readShort();
                    }
                }
                int crossarrays = ds.readByte() & 255;
                for (int k2 = 0; k2 < crossarrays; k2++) {
                    int crossesNum = ds.readByte() & 255;
                    for (int l2 = 0; l2 < crossesNum; l2++) {
                        ds.readShort();
                        ds.readShort();
                    }
                }
            }
            this.m_nActions = ds.readByte();
            this.m_Actions = new Action[this.m_nActions];
            for (int i2 = 0; i2 < this.m_nActions; i2++) {
                this.m_Actions[i2] = new Action(this, this);
                this.m_Actions[i2].loadActionG2(ds);
                this.m_Actions[i2].SetFrames(this.m_Frames);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Animation[] getInstanceFromQi(String fileName) {
        Exception e;
        DataInputStream ds = null;
        tmpPath = MyAPI.getPath(fileName);
        isFrameWanted = false;
        InputStream in = MFDevice.getResourceAsStream(fileName);
if (in == null) {
    InputStream jsonIn = MFDevice.getResourceAsStream(fileName.replace(".dat", ".json"));
    if (jsonIn != null) {
        try {
            in = JsonRecompiler.recompileJson(jsonIn);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
        DataInputStream ds2 = null;
        try {
            try {
                ds = new DataInputStream(in);
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            int animationNum = ds.readByte();
            animationInstance = new Animation[animationNum];
            for (int i = 0; i < animationNum; i++) {
                animationInstance[i] = new Animation();
                animationInstance[i].LoadAnimationG2(ds);
            }
            int imageNum = ds.readByte();
            ImageInfo[] imageInfo = new ImageInfo[imageNum];
            for (int i2 = 0; i2 < imageNum; i2++) {
                isImageWanted = true;
                imageInfo[i2] = new ImageInfo((ImageInfo) null);
                imageInfo[i2].loadInfo(ds);
            }
            for (int i3 = 0; i3 < animationNum; i3++) {
                animationInstance[i3].imageInfo = imageInfo;
                animationInstance[i3].qiAnimationArray = animationInstance;
            }
        } catch (Exception e3) {
            e = e3;
            ds2 = ds;
            e.printStackTrace();
            if (ds2 != null) {
                try {
                    ds2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return animationInstance;
        } catch (Throwable th2) {
            ds2 = ds;
            if (ds2 != null) {
                try {
                    ds2.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th2;
        }
        if (ds != null) {
            try {
                ds.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            return animationInstance;
        }
        return animationInstance;
    }

    public static Animation[] getInstanceFromQi(String fileName, boolean[] index) {
        Exception e;
        DataInputStream ds = null;
        int j = 0;
        for (int i = 0; i < imageIdArray.length; i++) {
            imageIdArray[i] = -1;
        }
        InputStream in = MFDevice.getResourceAsStream(fileName);
if (in == null) {
    InputStream jsonIn = MFDevice.getResourceAsStream(fileName.replace(".dat", ".json"));
    if (jsonIn != null) {
        try {
            in = JsonRecompiler.recompileJson(jsonIn);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
        DataInputStream ds2 = null;
        try {
            try {
                ds = new DataInputStream(in);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e2) {
            e = e2;
        }
        try {
            int animationNum = ds.readByte();
            animationInstance = new Animation[animationNum];
            for (int i2 = 0; i2 < animationNum; i2++) {
                isFrameWanted = index[i2];
                animationInstance[i2] = new Animation();
                animationInstance[i2].LoadAnimationG2(ds);
            }
            int imageNum = ds.readByte();
            ImageInfo[] imageInfo = new ImageInfo[imageNum];
            for (int i3 = 0; i3 < imageNum; i3++) {
                isImageWanted = false;
                while (true) {
                    if (j < imageIdArray.length) {
                        if (imageIdArray[j] != i3) {
                            j = imageIdArray[j] != -1 ? j + 1 : 0;
                        } else {
                            isImageWanted = true;
                            break;
                        }
                    }
                }
                imageInfo[i3] = new ImageInfo((ImageInfo) null);
                imageInfo[i3].loadInfo(ds);
            }
            for (int i4 = 0; i4 < animationNum; i4++) {
                animationInstance[i4].imageInfo = imageInfo;
                animationInstance[i4].qiAnimationArray = animationInstance;
            }
        } catch (Exception e3) {
            e = e3;
            ds2 = ds;
            e.printStackTrace();
            if (ds2 != null) {
                try {
                    ds2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            return animationInstance;
        } catch (Throwable th2) {
            ds2 = ds;
            if (ds2 != null) {
                try {
                    ds2.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th2;
        }
        if (ds != null) {
            try {
                ds.close();
                ds2 = ds;
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            return animationInstance;
        }
        ds2 = ds;
        return animationInstance;
    }

    public void DrawAni(MFGraphics g, int x, int y, short attr) {
        this.m_Actions[this.m_CurAni].Draw(g, x, y, attr);
    }

    public void DrawAni(MFGraphics g, short no, int x, int y, boolean loop, short attr) {
        SetCurAni(no);
        SetLoop(loop);
        DrawAni(g, x, y, attr);
    }

    public void DrawAni(MFGraphics g, byte frame, int x, int y, short attr) {
        this.m_Actions[this.m_CurAni].Draw(g, frame, x, y, attr);
    }

    public void DrawAni(MFGraphics g, short ani, short frame, int x, int y, short attr) {
        this.m_Actions[ani].Draw(g, frame, x, y, attr);
    }

    public AnimationDrawer getDrawer(int actionId, boolean loop, int trans) {
        this.refCount++;
        return new AnimationDrawer(this, actionId, loop, trans);
    }

    public AnimationDrawer getDrawer() {
        this.refCount++;
        return new AnimationDrawer(this);
    }

    public int getActionNum() {
        return this.m_nActions;
    }

    public int getFrameNum() {
        return this.m_Actions[this.m_CurAni].getFrameNum();
    }

    public int getFrameNum(int actionId) {
        return this.m_Actions[actionId].getFrameNum();
    }

    public boolean isTimeOver(int time) {
        return this.m_Actions[this.m_CurAni].isTimeOver(time);
    }

    public boolean isTimeOver(int actionId, int frameId, int time) {
        return this.m_Actions[this.m_CurAni].isTimeOver(time);
    }

    public int getWidthWithFrameId(int actionId, int currentFrame) {
        return this.m_Frames[this.m_Actions[actionId].m_FrameInfo[currentFrame][0]].getWidth();
    }

    public int getHeightWithFrameId(int actionId, int currentFrame) {
        return this.m_Frames[this.m_Actions[actionId].m_FrameInfo[currentFrame][0]].getHeight();
    }

    public static void closeAnimation(Animation animation) {
        if (animation != null) {
            animation.close();
        }
    }

    public static void closeAnimationArray(Animation[] animation) {
        if (animation != null) {
            for (int i = 0; i < animation.length; i++) {
                closeAnimation(animation[i]);
                animation[i] = null;
            }
        }
    }

    public static void closeAnimationDrawer(AnimationDrawer drawer) {
        if (drawer != null) {
            drawer.close();
        }
    }

    public static void closeAnimationDrawerArray(AnimationDrawer[] drawer) {
        if (drawer != null) {
            for (int i = 0; i < drawer.length; i++) {
                closeAnimationDrawer(drawer[i]);
                drawer[i] = null;
            }
        }
    }
    public class Action {
    private MFImage img_clip;
    protected Animation m_Ani;
    private byte[][] m_FrameInfo;
    private boolean m_bPause;
    private short m_nFrames;
    final Animation thisimageIdArray;
    private short m_Timer = 0;
    private short m_CurFrame = 0;
    private byte m_OldFrame = 0;
    private boolean m_bLoop = true;

    public Action(Animation animation, Animation ani) {
        this.thisimageIdArray = animation;
        this.m_Ani = ani;
    }

    public void SetLoop(boolean loop) {
        this.m_bLoop = loop;
    }

    public boolean GetLoop() {
        return this.m_bLoop;
    }

    public byte[] GetARect() {
        if (this.m_nFrames == 0) {
            return null;
        }
        int i = this.m_FrameInfo[this.m_CurFrame][0];
        if (i < 0) {
            i += 256;
        }
        return this.thisimageIdArray.m_Frames[i].GetARect();
    }

    public byte[] GetCRect() {
        byte[] rect = (byte[]) null;
        try {
            int i = this.m_FrameInfo[this.m_CurFrame][0];
            if (i < 0) {
                i += 256;
            }
            byte[] rect2 = this.thisimageIdArray.m_Frames[i].GetCRect();
            return rect2;
        } catch (Exception e) {
            e.printStackTrace();
            return rect;
        }
    }

    public void JumpFrame(byte frame) {
        if (frame < this.m_nFrames) {
            this.m_Timer = (short) 0;
            this.m_CurFrame = frame;
        }
    }

    public short GetFrameNo() {
        return this.m_CurFrame;
    }

    public void LoadAction(InputStream in) {
        try {
            this.m_nFrames = (byte) in.read();
            this.m_FrameInfo = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, this.m_nFrames, 2);
            for (int i = 0; i < this.m_nFrames; i++) {
                for (int j = 0; j < 2; j++) {
                    this.m_FrameInfo[i][j] = (byte) in.read();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadActionG2(DataInputStream ds) throws IOException {
        this.m_nFrames = ds.readByte();
        if (this.m_nFrames < 0) {
            this.m_nFrames = (short) (this.m_nFrames + 256);
        }
        this.m_FrameInfo = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, this.m_nFrames, 2);
        for (int i = 0; i < this.m_nFrames; i++) {
            for (int j = 0; j < 2; j++) {
                this.m_FrameInfo[i][j] = ds.readByte();
            }
            ds.readShort();
            ds.readShort();
        }
    }

    public void LoadAction() {
        this.m_nFrames = (short) 1;
        this.m_FrameInfo = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, this.m_nFrames, 2);
        for (int i = 0; i < this.m_nFrames; i++) {
            this.m_FrameInfo[i][0] = 0;
            this.m_FrameInfo[i][1] = 100;
        }
    }

    public boolean IsEnd() {
        if (this.m_bLoop) {
            return false;
        }
        return this.m_Timer >= this.m_FrameInfo[this.m_CurFrame][1] && this.m_CurFrame == this.m_nFrames - 1;
    }

    public void SetFrames(Animation.Frame[] frames) {
        this.thisimageIdArray.m_Frames = frames;
    }

    public void SetPause(boolean pause) {
        this.m_bPause = pause;
    }

    public void Draw(MFGraphics g, int x, int y, short attr) {
        if (this.m_nFrames != 0) {
            int i = this.m_FrameInfo[this.m_CurFrame][0];
            if (i < 0) {
                i += 256;
            }
            this.thisimageIdArray.m_Frames[i].Draw(g, x, y, attr);
            if (!this.m_bPause) {
                this.m_Timer = (short) (this.m_Timer + 1);
                if (this.m_Timer >= this.m_FrameInfo[this.m_CurFrame][1]) {
                    this.m_CurFrame = (short) (this.m_CurFrame + 1);
                    if (this.m_CurFrame >= this.m_nFrames) {
                        if (this.m_bLoop) {
                            this.m_Timer = (short) 0;
                            this.m_CurFrame = (short) 0;
                            return;
                        } else {
                            this.m_CurFrame = (byte) (this.m_nFrames - 1);
                            return;
                        }
                    }
                    this.m_Timer = (short) 0;
                }
            }
        }
    }

    public void Draw(MFGraphics g, short frame, int x, int y, short attr) {
        try {
            if (frame < this.m_nFrames) {
                this.m_CurFrame = frame;
                int i = this.m_FrameInfo[frame][0];
                if (i < 0) {
                    i += 256;
                }
                this.thisimageIdArray.m_Frames[i].Draw(g, x, y, attr);
            }
        } catch (Exception e) {
            System.out.println("frame:" + ((int) frame));
            System.out.println("m_FrameInfo.length:" + this.m_FrameInfo.length);
            System.out.println("m_FrameInfo[frame][0]:" + ((int) this.m_FrameInfo[frame][0]));
            System.out.println("m_Frames.length:" + this.thisimageIdArray.m_Frames.length);
        }
    }

    public void SetFrame(short frame) {
        if (frame < this.m_nFrames) {
            this.m_CurFrame = frame;
        }
    }

    public int getFrameNum() {
        return this.m_nFrames;
    }

    public boolean isTimeOver(int time) {
        return this.m_nFrames == 0 || time >= this.m_FrameInfo[this.m_CurFrame][1];
    }
}
    public class Frame {
    private int color;
    private int[] functionID;
    private Animation m_Ani;
    private short[][] m_ClipInfo;
    private short m_img_cx;
    private short m_img_cy;
    private byte m_nClips;
    final Animation this$0;
    private byte[] rect1 = new byte[4];
    private byte[] rect2 = new byte[4];
    private byte[] tmp_rect1 = new byte[4];
    private byte[] tmp_rect2 = new byte[4];
    private short frameWidth = -1;
    private short frameHeight = -1;

    public Frame(Animation animation, Animation ani) {
        this.this$0 = animation;
        this.m_Ani = ani;
    }

    public byte[] GetARect() {
        if (this.rect1[0] == 0 && this.rect1[1] == 0 && this.rect1[2] == 1 && this.rect1[3] == 1) {
            return null;
        }
        for (int i = 0; i < 4; i++) {
            this.tmp_rect1[i] = this.rect1[i];
        }
        return this.tmp_rect1;
    }

    public byte[] GetCRect() {
        if (this.rect2[0] == 0 && this.rect2[1] == 0 && this.rect2[2] == 1 && this.rect2[3] == 1) {
            return null;
        }
        for (int i = 0; i < 4; i++) {
            this.tmp_rect2[i] = this.rect2[i];
        }
        return this.tmp_rect2;
    }

    public void LoadFrame(InputStream in) {
        if (in != null) {
            try {
                in.read(this.rect1, 0, 4);
                in.read(this.rect2, 0, 4);
                this.m_nClips = (byte) in.read();
                this.m_ClipInfo = (short[][]) Array.newInstance((Class<?>) Short.TYPE, this.m_nClips, 5);
                this.functionID = new int[this.m_nClips];
                for (int i = 0; i < this.m_nClips; i++) {
                    for (int j = 0; j < 4; j++) {
                        this.m_ClipInfo[i][j] = (byte) in.read();
                        if (j > 1 && this.m_ClipInfo[i][j] < 0) {
                            short[] sArr = this.m_ClipInfo[i];
                            sArr[j] = (short) (sArr[j] + 256);
                        }
                    }
                    this.m_ClipInfo[i][4] = 0;
                    int tmp_attr = (short) (this.m_ClipInfo[i][3] << 8);
                    if (tmp_attr == 8192 || tmp_attr == 28672) {
                        short[] sArr2 = this.m_ClipInfo[i];
                        sArr2[0] = (short) (sArr2[0] - 1);
                    } else if (tmp_attr == 16384 || tmp_attr == 4096) {
                        short[] sArr3 = this.m_ClipInfo[i];
                        sArr3[1] = (short) (sArr3[1] - 1);
                    } else if (tmp_attr == 24576) {
                        short[] sArr4 = this.m_ClipInfo[i];
                        sArr4[0] = (short) (sArr4[0] - 1);
                        short[] sArr5 = this.m_ClipInfo[i];
                        sArr5[1] = (short) (sArr5[1] - 1);
                    } else if (tmp_attr == 12288 || tmp_attr == 12288) {
                        short[] sArr6 = this.m_ClipInfo[i];
                        sArr6[0] = (short) (sArr6[0] - 1);
                        short[] sArr7 = this.m_ClipInfo[i];
                        sArr7[1] = (short) (sArr7[1] - 1);
                    }
                    if (this.this$0.isDoubleScale) {
                        this.m_ClipInfo[i][0] = (short) (this.m_ClipInfo[i][0] << 1);
                        this.m_ClipInfo[i][1] = (short) (this.m_ClipInfo[i][1] << 1);
                    } else {
                        this.m_ClipInfo[i][0] = this.m_ClipInfo[i][0];
                        this.m_ClipInfo[i][1] = this.m_ClipInfo[i][1];
                    }
                }
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        this.m_nClips = (byte) 1;
        this.m_ClipInfo = (short[][]) Array.newInstance((Class<?>) Short.TYPE, 1, 4);
        this.m_ClipInfo[0][0] = 0;
        this.m_ClipInfo[0][1] = 0;
        this.m_ClipInfo[0][2] = 0;
        this.m_ClipInfo[0][3] = 0;
    }

    public void loadFrameG2(DataInputStream ds) throws IOException {
        int j = 0;
        this.m_nClips = ds.readByte();
        this.m_ClipInfo = (short[][]) Array.newInstance((Class<?>) Short.TYPE, this.m_nClips, 5);
        this.functionID = new int[this.m_nClips];
        for (int i = 0; i < this.m_nClips; i++) {
            this.functionID[i] = ds.readByte();
            switch (this.functionID[i]) {
                case 0:
                    this.m_ClipInfo[i][4] = ds.readByte();
                    this.m_ClipInfo[i][2] = ds.readByte();
                    this.m_ClipInfo[i][3] = ds.readByte();
                    this.m_ClipInfo[i][3] = (short) (this.m_ClipInfo[i][3] << 4);
                    if (this.m_ClipInfo[i][4] < 0) {
                        short[] sArr = this.m_ClipInfo[i];
                        sArr[4] = (short) (sArr[4] + 256);
                    }
                    if (this.m_ClipInfo[i][2] < 0) {
                        short[] sArr2 = this.m_ClipInfo[i];
                        sArr2[2] = (short) (sArr2[2] + 256);
                    }
                    this.m_ClipInfo[i][0] = ds.readShort();
                    this.m_ClipInfo[i][1] = ds.readShort();
                    if (Animation.isFrameWanted) {
                        while (true) {
                            if (j >= Animation.imageIdArray.length) {
                                break;
                            }
                            if (this.this$0.imageIdArray[j] != -1) {
                                j = Animation.imageIdArray[j] != this.m_ClipInfo[i][4] ? j + 1 : 0;
                            } else {
                                Animation.imageIdArray[j] = this.m_ClipInfo[i][4];
                                break;
                            }
                        }
                    } else {
                        break;
                    }
                    break;
                case 1:
                    this.m_ClipInfo[i][2] = ds.readShort();
                    this.m_ClipInfo[i][3] = ds.readShort();
                    this.color = 0;
                    this.color |= (ds.readByte() << 16) & 16711680;
                    this.color |= (ds.readByte() << 8) & 65280;
                    this.color |= (ds.readByte() << 0) & 255;
                    this.m_ClipInfo[i][0] = ds.readShort();
                    this.m_ClipInfo[i][1] = ds.readShort();
                    break;
                case 2:
                    this.m_ClipInfo[i][2] = ds.readShort();
                    this.m_ClipInfo[i][3] = ds.readShort();
                    this.color = 0;
                    this.color |= (ds.readByte() << 16) & 16711680;
                    this.color |= (ds.readByte() << 8) & 65280;
                    this.color |= (ds.readByte() << 0) & 255;
                    this.m_ClipInfo[i][0] = ds.readShort();
                    this.m_ClipInfo[i][1] = ds.readShort();
                    break;
                case 6:
                    this.m_ClipInfo[i][2] = ds.readByte();
                    this.m_ClipInfo[i][3] = ds.readByte();
                    if (this.m_ClipInfo[i][3] < 0) {
                        short[] sArr3 = this.m_ClipInfo[i];
                        sArr3[3] = (short) (sArr3[3] + 256);
                    }
                    this.m_ClipInfo[i][0] = ds.readShort();
                    this.m_ClipInfo[i][1] = ds.readShort();
                    break;
            }
        }
    }

    public int getHeight() {
        int var1;
        if (this.m_nClips <= 0) {
            var1 = 0;
        } else {
            int var4 = Integer.MAX_VALUE;
            int var3 = Integer.MIN_VALUE;

            int var2;
            for(var1 = 0; var1 < this.m_nClips; var4 = var2) {
                var2 = var4;
                if (this.m_ClipInfo[var1][1] < var4) {
                    var2 = this.m_ClipInfo[var1][1];
                }

                short[][] var6 = this.this$0.imageInfo[this.m_ClipInfo[var1][4]].getClips();
                short var7 = (short)(this.m_ClipInfo[var1][3] << 8);
                byte var5 = 3;
                if ((var7 & 4096) != 0) {
                    var5 = 2;
                }

                var4 = var3;
                if (var6[this.m_ClipInfo[var1][2]][var5] + this.m_ClipInfo[var1][1] > var3) {
                    var4 = var6[this.m_ClipInfo[var1][2]][var5] + this.m_ClipInfo[var1][1];
                }

                ++var1;
                var3 = var4;
            }

            this.frameHeight = (short)Math.abs(var3 - var4);
            if (this.this$0.isDoubleScale) {
                this.frameHeight = (short)(this.frameHeight << 1);
            }

            var1 = this.frameHeight;
        }

        return var1;
    }

    public int getWidth() {
        int var1;
        if (this.m_nClips <= 0) {
            var1 = 0;
        } else {
            int var5 = Integer.MAX_VALUE;
            var1 = Integer.MIN_VALUE;

            int var4;
            for(int var2 = 0; var2 < this.m_nClips; var1 = var4) {
                int var3 = var5;
                if (this.m_ClipInfo[var2][0] < var5) {
                    var3 = this.m_ClipInfo[var2][0];
                }

                short[][] var6 = this.this$0.imageInfo[this.m_ClipInfo[var2][4]].getClips();
                var4 = (short)(this.m_ClipInfo[var2][3] << 8);
                byte var7 = 2;
                if ((var4 & 4096) != 0) {
                    var7 = 3;
                }

                var4 = var1;
                if (var6[this.m_ClipInfo[var2][2]][var7] + this.m_ClipInfo[var2][0] > var1) {
                    var4 = var6[this.m_ClipInfo[var2][2]][var7] + this.m_ClipInfo[var2][0];
                }

                ++var2;
                var5 = var3;
            }

            this.frameWidth = (short)Math.abs(var1 - var5);
            if (this.this$0.isDoubleScale) {
                this.frameWidth = (short)(this.frameWidth << 1);
            }

            var1 = this.frameWidth;
        }

        return var1;
    }

    public void SetClips(short[][] clip) {
    }

    public void Draw(MFGraphics g, int x, int y, short attr) {
        if (this.m_nClips != 0) {
            for (int i = 0; i < this.m_nClips; i++) {
                switch (this.functionID[i]) {
                    case 0:
                        if (this.this$0.isDoubleScale) {
                            g.saveCanvas();
                            g.translateCanvas(x, y);
                            g.scaleCanvas(0.5f, 0.5f);
                            DrawImage(g, i, 0, 0, attr);
                            g.restoreCanvas();
                            break;
                        } else {
                            DrawImage(g, i, x, y, attr);
                            break;
                        }
                    case 1:
                        fillRect(g, i, x, y, attr);
                        break;
                    case 2:
                        drawRect(g, i, x, y, attr);
                        break;
                    case 6:
                        try {
                            short s = this.m_ClipInfo[i][0];
                            short s2 = this.m_ClipInfo[i][1];
                            if (this.this$0.isDoubleScale) {
                                g.saveCanvas();
                                g.translateCanvas(x + s, y + s2);
                                g.scaleCanvas(0.5f, 0.5f);
                                this.this$0.qiAnimationArray[this.m_ClipInfo[i][2]].m_Frames[this.m_ClipInfo[i][3]].Draw(g, 0, 0, attr);
                                g.restoreCanvas();
                                break;
                            } else {
                                this.this$0.qiAnimationArray[this.m_ClipInfo[i][2]].m_Frames[this.m_ClipInfo[i][3]].Draw(g, x + s, y + s2, attr);
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println(String.valueOf((int) this.m_ClipInfo[i][3]) + "is out of bounds");
                            break;
                        }
                }
            }
        }
    }

    public void DrawImage(MFGraphics mFGraphics, int i, int i2, int i3, short s) {
        if (this.m_nClips != 0) {
            MFImage image = this.this$0.imageInfo[this.m_ClipInfo[i][4]].getImage();
            short[] sArr = this.this$0.imageInfo[this.m_ClipInfo[i][4]].getClips()[this.m_ClipInfo[i][2]];
            short s2 = (short) (this.m_ClipInfo[i][3] << 8);
            short s3 = this.m_ClipInfo[i][0];
            short s4 = this.m_ClipInfo[i][1];
            int i4 = s3;
            int i5 = s4;
            switch (getMIDPTransId(s)) {
                case 1:
                    i4 = s3;
                    i5 = (-s4) - sArr[3];
                    break;
                case 2:
                    i4 = (-s3) - sArr[2];
                    i5 = s4;
                    break;
                case 3:
                    i4 = (-s3) - sArr[2];
                    i5 = (-s4) - sArr[3];
                    break;
                case 4:
                    i4 = s4;
                    i5 = s3;
                    break;
                case 5:
                    int i6 = (-s4) - sArr[3];
                    i5 = s3;
                    i4 = i6;
                    break;
                case 6:
                    i4 = s4;
                    i5 = (-s3) - sArr[2];
                    break;
                case 7:
                    int i7 = (-s4) - sArr[3];
                    int i8 = (-s3) - sArr[2];
                    i4 = i7;
                    i5 = i8;
                    break;
            }
            int i9 = s2 ^ s;
            if ((i9 & 4096) != 0) {
                if (((s2 & 8192) != 0) ^ ((s2 & 16384) != 0)) {
                    i9 ^= 24576;
                }
            } else if ((s2 & 4096) != 0 && (s & 4096) != 0) {
                i9 ^= 24576;
            }
            Const.DrawImage(mFGraphics, i4 + i2, i5 + i3, image, sArr[0], sArr[1], sArr[2], sArr[3], i9);
        }
    }

    private void fillRect(MFGraphics mFGraphics, int i, int i2, int i3, int i4) {
        short s = this.m_ClipInfo[i][0];
        short s2 = this.m_ClipInfo[i][1];
        int i5 = s;
        if ((i4 & 8192) > 0) {
            i5 = ((-s) - this.m_ClipInfo[i][2]) + 1;
        }
        int i6 = s2;
        if ((i4 & 16384) > 0) {
            i6 = ((-s2) - this.m_ClipInfo[i][3]) + 1;
        }
        int color = mFGraphics.getColor();
        mFGraphics.setColor(this.color);
        mFGraphics.fillRect(i2 + i5, i3 + i6, this.m_ClipInfo[i][2], this.m_ClipInfo[i][3]);
        mFGraphics.setColor(color);
    }

    private void drawRect(MFGraphics mFGraphics, int i, int i2, int i3, int i4) {
        short s = this.m_ClipInfo[i][0];
        short s2 = this.m_ClipInfo[i][1];
        int i5 = s;
        if ((i4 & 8192) > 0) {
            i5 = ((-s) - this.m_ClipInfo[i][2]) + 1;
        }
        int i6 = s2;
        if ((i4 & 16384) > 0) {
            i6 = ((-s2) - this.m_ClipInfo[i][3]) + 1;
        }
        int color = mFGraphics.getColor();
        mFGraphics.setColor(this.color);
        mFGraphics.fillRect(i2 + i5, i3 + i6, this.m_ClipInfo[i][2], this.m_ClipInfo[i][3]);
        mFGraphics.setColor(color);
    }

    private int getMIDPTransId(int tmp_attr) {
        int attr = 0;
        if ((tmp_attr & 4096) != 0) {
            int attr2 = 0 | 6;
            if ((tmp_attr & 8192) != 0) {
                attr2 ^= 1;
            }
            if ((tmp_attr & 16384) != 0) {
                return attr2 ^ 2;
            }
            return attr2;
        }
        if ((tmp_attr & 8192) != 0) {
            attr = 0 | 2;
        }
        if ((tmp_attr & 16384) != 0) {
            return attr | 1;
        }
        return attr;
    }
}
    public static class ImageInfo {
    private MFImage[] imageSeperate;
    protected MFImage img_clip;
    private short[][] m_Clips;
    private short m_nClips;

    private ImageInfo() {
    }

    ImageInfo(ImageInfo ImageInfo) {
        this();
    }

    private ImageInfo(MFImage image) {
        this.img_clip = image;
    }

    ImageInfo(MFImage mFImage, ImageInfo ImageInfo) {
        this(mFImage);
    }

    private ImageInfo(String imageFileName) {
        try {
            this.img_clip = MFImage.createImage(imageFileName);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    ImageInfo(String str, ImageInfo ImageInfo) {
        this(str);
    }

    public void close() {
        this.img_clip = null;
        if (this.imageSeperate != null) {
            for (int i = 0; i < this.imageSeperate.length; i++) {
                this.imageSeperate[i] = null;
            }
        }
        this.imageSeperate = null;
    }

    public void loadInfo(DataInputStream ds) throws IOException {
        this.m_nClips = ds.readByte();
        if (this.m_nClips < 0) {
            this.m_nClips = (short) (this.m_nClips + 256);
        }
        this.m_Clips = (short[][]) Array.newInstance((Class<?>) Short.TYPE, this.m_nClips, 4);
        for (int i = 0; i < this.m_nClips; i++) {
            for (int j = 0; j < 4; j++) {
                this.m_Clips[i][j] = ds.readShort();
            }
        }
        String fileName = ds.readUTF();
        if (Animation.isImageWanted) {
            String tmpFileName = MyAPI.getFileName(fileName);
            String fileName2 = String.valueOf(Animation.tmpPath) + tmpFileName;
            this.img_clip = MFImage.createImage(fileName2);
            System.out.println("image fileName:" + tmpFileName);
        }
    }

    public void loadInfo(InputStream ds) throws IOException {
        if (ds == null) {
            this.m_nClips = (short) 1;
            this.m_Clips = (short[][]) Array.newInstance((Class<?>) Short.TYPE, this.m_nClips, 4);
            for (int i = 0; i < this.m_nClips; i++) {
                this.m_Clips[i][0] = 0;
                this.m_Clips[i][1] = 0;
                this.m_Clips[i][2] = (short) (this.img_clip.getWidth() & 65535);
                this.m_Clips[i][3] = (short) (this.img_clip.getHeight() & 65535);
            }
            return;
        }
        this.m_nClips = (byte) ds.read();
        if (this.m_nClips < 0) {
            this.m_nClips = (short) (this.m_nClips + 256);
        }
        this.m_Clips = (short[][]) Array.newInstance((Class<?>) Short.TYPE, this.m_nClips, 4);
        for (int i2 = 0; i2 < this.m_nClips; i2++) {
            for (int j = 0; j < 4; j++) {
                this.m_Clips[i2][j] = Const.ReadShort(ds);
            }
        }
    }

    public MFImage getImage() {
        return this.img_clip;
    }

    public short[][] getClips() {
        return this.m_Clips;
    }

    public void doubleParam() {
        for (int i = 0; i < this.m_nClips; i++) {
            for (int j = 0; j < 4; j++) {
                this.m_Clips[i][j] = (short) (this.m_Clips[i][j] << 1);
            }
        }
    }

    public void separateImage() {
        this.imageSeperate = new MFImage[this.m_nClips];
        for (int i = 0; i < this.m_nClips; i++) {
            this.imageSeperate[i] = MFImage.createImage(this.img_clip, this.m_Clips[i][0], this.m_Clips[i][1], this.m_Clips[i][2], this.m_Clips[i][3], 0);
            this.m_Clips[i][0] = 0;
            this.m_Clips[i][1] = 0;
        }
        this.img_clip = null;
    }

    public MFImage getSeparateImage(int id) {
        return this.imageSeperate[id];
    }
}
}
