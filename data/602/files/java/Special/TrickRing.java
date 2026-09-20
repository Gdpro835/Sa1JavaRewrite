package Special;

import Lib.Animation;
import Lib.SoundSystem;
import com.sega.mobile.framework.device.MFGraphics;

/* compiled from: SpecialObject */
class TrickRing extends SpecialObject {
    private static final int COLLISION_WIDTH = 60;
    private static final int[][] RING_GEN_PARAM;
    private boolean used = false;

    static {
        int[] iArr = new int[2];
        iArr[0] = 60;
        int[] iArr2 = new int[2];
        iArr2[1] = -60;
        int[] iArr3 = new int[2];
        iArr3[0] = -60;
        int[] iArr4 = new int[2];
        iArr4[1] = 60;
        int[] iArr5 = new int[2];
        iArr5[0] = 100;
        int[] iArr6 = new int[2];
        iArr6[1] = -100;
        int[] iArr7 = new int[2];
        iArr7[0] = -100;
        int[] iArr8 = new int[2];
        iArr8[1] = 100;
        RING_GEN_PARAM = new int[][]{iArr, new int[]{50, -50}, iArr2, new int[]{-50, -50}, iArr3, new int[]{-50, 50}, iArr4, new int[]{50, 50}, iArr5, iArr6, iArr7, iArr8};
    }

    public TrickRing(int x, int y, int z) {
        super(1, x, y, z);
        this.drawer = objAnimation.getDrawer(0, true, 0);
    }

    public void close() {
        Animation.closeAnimationDrawer(this.drawer);
        this.drawer = null;
    }

    public void doWhileCollision(SpecialObject collisionObj) {
        if (!this.used) {
            for (int i = 0; i < RING_GEN_PARAM.length; i++) {
                SpecialObject tmpObj = new SSFollowRing(this.posX + RING_GEN_PARAM[i][0], this.posY + RING_GEN_PARAM[i][1], this.posZ << 3, false);
                addExtraObject(tmpObj);
                decideObjects.addElement(tmpObj);
            }
            player.setTrikCount();
            SoundSystem.getInstance().playSe(39);
            this.drawer.setActionId(1);
            this.drawer.setLoop(false);
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
        this.collisionRect.setRect(x - 30, y - 30, 60, 60);
    }
}
