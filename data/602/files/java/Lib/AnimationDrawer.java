package Lib;

import PyxEditor.PyxAnimation;
import com.sega.mobile.framework.device.MFGraphics;

public class AnimationDrawer {
    private static int ZOOM = 6;
    private static final int STANDARD_FRAME_SPEED = (1 << ZOOM);
    private static boolean allPause = false;
    private short actionId;
    private int actualTime;
    private Animation ani;
    private short attr;
    private boolean end;
    private boolean endTrigger;
    private boolean loop;
    private long lostFrameTime;
    private short m_CurFrame;
    private int m_Timer;
    private boolean m_bPause;
    private int mustKeepTime = -1;
    private byte[] reARect;
    private byte[] reCRect;
    private int speedDivide = 1;
    private int speedMulti = 1;
    long startTime;
    private byte transId;

    public static void setAllPause(boolean pause) {
        allPause = pause;
        PyxAnimation.setPause(pause);
    }

    public static boolean isAllPause() {
        return allPause;
    }

    public void mustKeepFrameTime(int keepTime) {
        this.mustKeepTime = keepTime;
    }

    public AnimationDrawer(Animation ani2) {
        this.ani = ani2;
        this.actionId = 0;
        this.attr = Const.TRANS[0];
        this.transId = 0;
        this.loop = true;
    }

    public AnimationDrawer(Animation ani2, int actionId2, boolean loop2, int transId2) {
        this.ani = ani2;
        this.actionId = (short) actionId2;
        this.attr = Const.TRANS[transId2];
        this.transId = (byte) transId2;
        this.loop = loop2;
    }

    /* access modifiers changed from: protected */
    public void close() {
        if (this.ani != null) {
            this.ani.refCount--;
            if (this.ani.refCount <= 0) {
                this.ani.close();
            }
        }
        this.ani = null;
    }

    public void setActionId(int actionId2) {
        if (this.actionId != ((short) actionId2)) {
            restart();
        }
        this.actionId = (short) actionId2;
    }

    public int getActionId() {
        return this.actionId;
    }

    public int getTransId() {
        return this.transId;
    }

    public boolean getLoop() {
        return this.loop;
    }

    public void setLoop(boolean loop2) {
        this.loop = loop2;
    }

    public void setTrans(int transId2) {
        this.attr = Const.TRANS[transId2];
        this.transId = (byte) transId2;
    }

    public void draw(MFGraphics g, int actionId2, int x, int y, boolean loop2, int transId2, boolean zoomEnable) {
        if (this.actionId != ((short) actionId2)) {
            restart();
        }
        this.actionId = (short) actionId2;
        this.attr = Const.TRANS[transId2];
        this.transId = (byte) transId2;
        this.loop = loop2;
        draw(g, x, y, zoomEnable);
    }

    public void draw(MFGraphics g, int x, int y, int transId2, boolean zoomEnable) {
        this.attr = Const.TRANS[transId2];
        this.transId = (byte) transId2;
        draw(g, x, y, zoomEnable);
    }

    public void draw(MFGraphics g, int x, int y) {
        draw(g, x, y, true);
    }

    public void drawWithoutZoom(MFGraphics g, int x, int y) {
        draw(g, x, y, false);
    }

    public void draw(MFGraphics g, int actionId2, int x, int y, boolean loop2, int transId2) {
        draw(g, actionId2, x, y, loop2, transId2, true);
    }

    public void drawWithoutZoom(MFGraphics g, int actionId2, int x, int y, boolean loop2, int transId2) {
        draw(g, actionId2, x, y, loop2, transId2, false);
    }

    public void draw(MFGraphics g, int x, int y, int transId2) {
        draw(g, x, y, transId2, true);
    }

    public void drawWithoutZoom(MFGraphics g, int x, int y, int transId2) {
        draw(g, x, y, transId2, false);
    }

    public void moveOn() {
        this.actualTime += (STANDARD_FRAME_SPEED * this.speedMulti) / this.speedDivide;
        this.m_Timer = (byte) (this.actualTime >> ZOOM);
        this.endTrigger = false;
        this.ani.SetCurAni(this.actionId);
        this.ani.SetCurFrame(this.m_CurFrame);
        if (this.ani.isTimeOver(this.m_Timer)) {
            boolean trigger = false;
            if (this.m_CurFrame < this.ani.getFrameNum(this.actionId) && !this.end) {
                trigger = true;
            }
            if (this.mustKeepTime == -1) {
                this.m_CurFrame = (short) (this.m_CurFrame + 1);
            } else if (this.lostFrameTime < ((long) (-this.mustKeepTime))) {
                this.lostFrameTime += (long) this.mustKeepTime;
            } else {
                if (this.lostFrameTime > ((long) this.mustKeepTime)) {
                    this.m_CurFrame = (short) (this.m_CurFrame + 1);
                    this.lostFrameTime -= (long) this.mustKeepTime;
                }
                this.m_CurFrame = (short) (this.m_CurFrame + 1);
            }
            if (this.m_CurFrame < this.ani.getFrameNum(this.actionId)) {
                this.m_Timer = 0;
                this.actualTime %= STANDARD_FRAME_SPEED;
            } else if (this.loop) {
                this.m_Timer = 0;
                this.m_CurFrame = 0;
                this.actualTime %= STANDARD_FRAME_SPEED;
            } else {
                this.m_CurFrame = (byte) (this.ani.getFrameNum(this.actionId) - 1);
                this.end = true;
                if (trigger) {
                    this.endTrigger = true;
                }
            }
        }
    }

    public void draw(MFGraphics g, int x, int y, boolean zoomEnable) {
        this.ani.SetCurAni(this.actionId);
        this.ani.SetLoop(this.loop);
        if (zoomEnable) {
            this.ani.DrawAni(g, this.actionId, this.m_CurFrame, MyAPI.zoomOut(x), MyAPI.zoomOut(y), this.attr);
        } else {
            this.ani.DrawAni(g, this.actionId, this.m_CurFrame, x, y, this.attr);
        }
        if (this.mustKeepTime != -1) {
            if (this.startTime == 0) {
                this.startTime = System.currentTimeMillis() - ((long) this.mustKeepTime);
            }
            long nowTime = System.currentTimeMillis();
            this.lostFrameTime += (nowTime - this.startTime) - ((long) this.mustKeepTime);
            this.startTime = nowTime;
        }
        if (this.m_bPause || allPause) {
            this.lostFrameTime = 0;
        } else {
            moveOn();
        }
    }

    public void setPause(boolean pause) {
        this.m_bPause = pause;
    }

    public boolean checkEnd() {
        return this.end;
    }

    public boolean checkEndTrigger() {
        return this.endTrigger;
    }

    public void restart() {
        this.startTime = 0;
        this.end = false;
        this.m_CurFrame = 0;
        this.actualTime = 0;
    }

    public int getCurrentFrameWidth() {
        return MyAPI.zoomIn(this.ani.getWidthWithFrameId(this.actionId, this.m_CurFrame));
    }

    public int getCurrentFrameHeight() {
        return MyAPI.zoomIn(this.ani.getHeightWithFrameId(this.actionId, this.m_CurFrame));
    }

    public int getCurrentFrame() {
        return this.m_CurFrame;
    }

    public Animation getAnimation() {
        return this.ani;
    }

    public void setEnd() {
        this.end = true;
    }

    public byte[] getARect() {
        if (this.reARect == null) {
            this.reARect = new byte[4];
        }
        this.ani.SetCurAni(this.actionId);
        this.ani.SetCurFrame(this.m_CurFrame);
        byte[] animationrect = this.ani.GetARect();
        if (animationrect == null) {
            return null;
        }
        getRect(this.reARect, animationrect);
        return this.reARect;
    }

    public byte[] getCRect() {
        if (this.reCRect == null) {
            this.reCRect = new byte[4];
        }
        this.ani.SetCurAni(this.actionId);
        this.ani.SetCurFrame(this.m_CurFrame);
        byte[] animationrect = this.ani.GetCRect();
        if (animationrect == null) {
            return null;
        }
        getRect(this.reCRect, animationrect);
        return this.reCRect;
    }

    private void getRect(byte[] getter, byte[] source) {
        if (source != null && getter != null) {
            switch (this.transId) {
                case 2:
                    getter[0] = (byte) ((-source[0]) - source[2]);
                    getter[1] = source[1];
                    getter[2] = source[2];
                    getter[3] = source[3];
                    return;
                default:
                    getter[0] = source[0];
                    getter[1] = source[1];
                    getter[2] = source[2];
                    getter[3] = source[3];
                    return;
            }
        }
    }

    public void setSpeedReset() {
        this.speedMulti = 1;
        this.speedDivide = 1;
    }

    public void setSpeed(int multi, int divide) {
        this.speedMulti = multi;
        this.speedDivide = divide;
        if (this.speedDivide == 0) {
            this.speedDivide = 1;
        }
        if (this.speedMulti == 0) {
            this.speedMulti = 1;
        }
    }
}
