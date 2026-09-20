package Special;

import com.sega.mobile.framework.device.MFGraphics;

/* compiled from: SpecialObject */
class SSCheckPoint extends SpecialObject {
    private boolean used = false;

    public SSCheckPoint(int x, int y, int z) {
        super(10, x, y, z);
    }

    public void close() {
    }

    public void doWhileCollision(SpecialObject collisionObj) {
        if (!this.used) {
            player.setCheckPoint();
            this.used = true;
        }
    }

    public void draw(MFGraphics g) {
    }

    public void logic() {
    }

    public void refreshCollision(int x, int y) {
        this.collisionRect.setRect(x - 150, y - 120, 300, SSDef.PLAYER_MOVE_HEIGHT);
    }
}
