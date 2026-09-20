package Special;

import com.sega.mobile.framework.device.MFGraphics;

/* compiled from: SpecialObject */
class SSGoal extends SpecialObject {
    private boolean used = false;

    public SSGoal(int x, int y, int z) {
        super(11, x, y, z);
    }

    public void close() {
    }

    public void doWhileCollision(SpecialObject collisionObj) {
        if (!this.used) {
            player.setGoal();
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
