package Special;

import Lib.Animation;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

/* compiled from: SpecialObject */
class SSSpring extends SpecialObject {
    private static final int COLLISION_WIDTH = 32;
    private static final int SPRING_POWER = 5;
    private static final int SPRING_UP_POWER = 8;
    private static final int SPRING_XY_POWER = 11;
    private int actionID = 4;
    private boolean used;

    public SSSpring(int id, int x, int y, int z) {
        super(id, x, y, z);
        switch (this.objID) {
            case 2:
                this.actionID = 4;
                break;
            case 3:
                this.actionID = 5;
                break;
            case 4:
                this.actionID = 6;
                break;
            case 5:
                this.actionID = 7;
                break;
            case 6:
                this.actionID = 8;
                break;
            case 7:
                this.actionID = 9;
                break;
        }
        this.drawer = objAnimation.getDrawer(this.actionID, true, 0);
        this.used = false;
    }

    public void close() {
        Animation.closeAnimationDrawer(this.drawer);
        this.drawer = null;
    }

    public void doWhileCollision(SpecialObject collisionObj) {
        if (!this.used) {
            switch (this.objID) {
                case 2:
                    player.beSpringX(0);
                    player.beSpringY(0);
                    player.beSpringZ(-8);
                    SoundSystem.getInstance().playSe(37);
                    break;
                case 3:
                    player.beSpringX(0);
                    player.beSpringY(0);
                    player.beSpringZ(5);
                    SoundSystem.getInstance().playSe(37);
                    break;
                case 4:
                    player.beSpringX(-11);
                    player.beSpringY(-11);
                    player.beSpringZ(5);
                    SoundSystem.getInstance().playSe(37);
                    break;
                case 5:
                    player.beSpringX(11);
                    player.beSpringY(-11);
                    player.beSpringZ(5);
                    SoundSystem.getInstance().playSe(37);
                    break;
                case 6:
                    player.beSpringX(-11);
                    player.beSpringY(11);
                    player.beSpringZ(5);
                    SoundSystem.getInstance().playSe(37);
                    break;
                case 7:
                    player.beSpringX(11);
                    player.beSpringY(11);
                    player.beSpringZ(5);
                    SoundSystem.getInstance().playSe(37);
                    break;
            }
            this.used = true;
        }
    }

    public void draw(MFGraphics g) {
        calDrawPosition(this.posX, this.posY, this.posZ);
        drawObj(g, this.drawer, 0, 0);
    }

    public void logic() {
    }

    public void refreshCollision(int x, int y) {
        this.collisionRect.setRect(x - 16, y - 16, 32, 32);
    }
}
