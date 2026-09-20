package Special;

import Lib.Animation;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

/* compiled from: SpecialObject */
class SSRing extends SpecialObject {
    private static final int COLLISION_WIDTH = 40;
    protected static final int OFFSET_Y = 8;
    private static Animation ringAnimation;
    protected boolean used;

    public SSRing(int x, int y, int z) {
        super(0, x, y, z);
        if (ringAnimation == null) {
            ringAnimation = new Animation("/animation/ring");
            ringDrawer = ringAnimation.getDrawer(0, true, 0);
            ringDrawer.setPause(true);
        }
        this.drawer = ringAnimation.getDrawer(1, false, 0);
        this.used = false;
    }

    public void draw(MFGraphics g) {
        calDrawPosition(this.posX, this.posY, this.posZ);
        if (!this.used) {
            drawObj(g, ringDrawer, 0, 8);
        } else {
            drawObj(g, this.drawer, 0, 8);
        }
    }

    public void doWhileCollision(SpecialObject collisionObj) {
        if (!this.used) {
            this.used = true;
            player.getRing(1);
            SoundSystem.getInstance().playSe(12);
        }
    }

    public void logic() {
    }

    public void refreshCollision(int x, int y) {
        this.collisionRect.setRect(this.posX - 20, y - 20, 40, 40);
    }

    public void close() {
    }
}
