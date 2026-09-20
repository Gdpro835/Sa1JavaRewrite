package Special;

import Lib.Animation;
import SonicGBA.SonicDebug;
import com.sega.mobile.framework.device.MFGraphics;

/* compiled from: SpecialObject */
class SSBomb extends SpecialObject {
    private static final int COLLISION_HEIGHT = 6;
    private static final int COLLISION_WIDTH = 8;
    private boolean used = false;

    public SSBomb(int x, int y, int z) {
        super(9, x, y, z);
        this.drawer = objAnimation.getDrawer(2, true, 0);
    }

    public void close() {
        Animation.closeAnimationDrawer(this.drawer);
        this.drawer = null;
    }

    public void doWhileCollision(SpecialObject collisionObj) {
        if (!this.used) {
            player.beHurt();
            player.velZ = 4;
            this.drawer.setActionId(3);
            this.drawer.setLoop(false);
            this.used = true;
        }
    }

    public void draw(MFGraphics g) {
        calDrawPosition(this.posX, this.posY, this.posZ);
        drawObj(g, this.drawer, 0, 0);
        drawCollisionRect(g, (drawX + (SCREEN_WIDTH >> 1)) - SpecialMap.getCameraOffsetX(), (drawY + (SCREEN_HEIGHT >> 1)) - SpecialMap.getCameraOffsetY());
    }

    public void logic() {
    }

    public void refreshCollision(int x, int y) {
        this.collisionRect.setRect(x - 4, y - 3, 8, 6);
    }

    public void drawCollisionRect(MFGraphics g, int x, int y) {
        if (SonicDebug.showCollisionRect) {
            g.setColor(16711680);
            g.drawRect(x - 4, y - 3, 8, 6);
            g.drawRect((x - 4) + 1, (y - 3) + 1, 6, 4);
        }
    }
}
