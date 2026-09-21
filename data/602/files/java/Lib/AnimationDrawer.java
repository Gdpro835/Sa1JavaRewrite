package Lib;

import PyxEditor.PyxAnimation;
import GameEngine.time.GameTime;
import GameEngine.time.AnimationTimeline;
import com.sega.mobile.framework.device.MFGraphics;

public class AnimationDrawer {
    private static final java.util.WeakHashMap<AnimationDrawer, Boolean> active =
            new java.util.WeakHashMap<AnimationDrawer, Boolean>();
    public static void updateAll() {
        for (AnimationDrawer drawer : active.keySet()) if (drawer.started) drawer.advance(false);
    }
    private final AnimationTimeline timeline = new AnimationTimeline();
    private long lastAdvanceFrame = Long.MIN_VALUE;
    private final AnimationTimeline.Durations durations = new AnimationTimeline.Durations() {
        public int frameCount() { return ani.getFrameNum(actionId); }
        public double seconds(int frame) {
            double unit = mustKeepTime > 0 ? mustKeepTime / 1000.0 : GameTime.ASSET_TIME_UNIT_SECONDS;
            return Math.max(1, ani.getFrameDuration(actionId, frame)) * unit;
        }
    };
    private static boolean allPause = false;
    private static boolean worldContext;
    private static boolean worldPaused;
    private boolean worldAnimation = worldContext;

    /** Draw/update scope marks scene animations without freezing the pause-menu UI. */
    public static boolean setWorldContext(boolean world) {
        boolean previous = worldContext;
        worldContext = world;
        return previous;
    }
    public static boolean isWorldContext() { return worldContext; }
    public static void setWorldPaused(boolean paused) { worldPaused = paused; }
    public static boolean isWorldPaused() { return worldPaused; }
    private short actionId;
    private Animation ani;
    private short attr;
    private boolean end;
    private boolean endTrigger;
    private boolean loop;
    private boolean started;
    private boolean interpolateMotion;
    private short m_CurFrame;
    private boolean m_bPause;
    private int mustKeepTime = -1;
    private byte[] reARect;
    private byte[] reCRect;
    private int speedDivide = 1;
    private int speedMulti = 1;
    private byte transId;

    public static void setAllPause(boolean pause) {
        allPause = pause;
        PyxAnimation.setPause(pause);
    }

    public static boolean isAllPause() {
        return allPause;
    }

    /** UI-only keyframe position tweening; sprite poses and playback duration stay authored. */
    public void setInterpolateMotion(boolean enabled) {
        this.interpolateMotion = enabled;
    }

    public void mustKeepFrameTime(int keepTime) {
        this.mustKeepTime = keepTime;
    }

    public AnimationDrawer(Animation ani2) {
        active.put(this, Boolean.TRUE);
        this.ani = ani2;
        this.actionId = 0;
        this.attr = Const.TRANS[0];
        this.transId = 0;
        this.loop = true;
    }

    public AnimationDrawer(Animation ani2, int actionId2, boolean loop2, int transId2) {
        active.put(this, Boolean.TRUE);
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
        active.remove(this);
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
        started = true;
        worldAnimation |= worldContext;
        // Legacy shared animations (rings, torches, etc.) disable automatic playback,
        // then explicitly step once. A skipped automatic update must not consume that step.
        advance(true);
    }

    private void advance(boolean explicit) {
        if (ani == null || allPause || (worldAnimation && worldPaused) || (!explicit && m_bPause)) return;
        if (lastAdvanceFrame == GameTime.frameId()) return;
        lastAdvanceFrame = GameTime.frameId();
        endTrigger = false;
        timeline.advance(GameTime.deltaSeconds() * Math.abs(speedMulti / (double) speedDivide), loop, durations);
        m_CurFrame = (short) timeline.frame();
        end = timeline.ended();
        endTrigger = timeline.endTriggered();
    }

    public void draw(MFGraphics g, int x, int y, boolean zoomEnable) {
        if (ani == null) return;
        worldAnimation |= worldContext;
        started = true;
        ani.SetCurAni(actionId);
        ani.SetLoop(loop);
        int drawX = zoomEnable ? MyAPI.zoomOut(x) : x;
        int drawY = zoomEnable ? MyAPI.zoomOut(y) : y;
        int count = ani.getFrameNum(actionId);
        if (interpolateMotion && transId == 0 && count > 0) {
            int next = m_CurFrame + 1;
            if (next >= count) next = loop ? 0 : m_CurFrame;
            double unit = mustKeepTime > 0 ? mustKeepTime / 1000.0 : GameTime.ASSET_TIME_UNIT_SECONDS;
            // Long authored holds stay still; only the final unit approaches the next pose.
            double blend = Math.max(0.0, Math.min(1.0,
                    1.0 - (durations.seconds(m_CurFrame) - timeline.secondsInFrame()) / unit));
            ani.DrawAniInterpolated(g, actionId, m_CurFrame, (short) next, blend, drawX, drawY, attr);
        } else {
            ani.DrawAni(g, actionId, m_CurFrame, drawX, drawY, attr);
        }
    }

    /** Suspend automatic playback; explicit moveOn() retains its legacy stepping contract. */
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
        timeline.reset();
        // Reused menu/intro drawers must not play their entrance while still hidden.
        // draw() or an explicit moveOn() activates the restarted action again.
        started = false;
        lastAdvanceFrame = Long.MIN_VALUE;
        endTrigger = false;
        this.end = false;
        this.m_CurFrame = 0;
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
        timeline.finish();
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
