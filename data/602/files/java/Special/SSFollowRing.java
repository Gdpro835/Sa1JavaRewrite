package Special;

import Lib.MyAPI;

/* compiled from: SpecialObject */
class SSFollowRing extends SSRing {
    private static final int FOLLOW_RING_MOVEMENT_Z = 4;
    private boolean decided = false;
    private boolean follow = false;
    private int sleepCount = 10;

    public SSFollowRing(int x, int y, int z, boolean follow2) {
        super(x, y, z);
    }

    public void setFollowOrNot(boolean flag) {
        this.decided = true;
        this.follow = flag;
    }

    public void logic() {
        if (this.sleepCount > 0) {
            this.sleepCount--;
        }
        if (this.follow && !this.used && this.sleepCount == 0) {
            this.posX = MyAPI.calNextPosition((double) this.posX, (double) (player.posX >> 6), 1, 4, 3.0d);
            this.posY = MyAPI.calNextPosition((double) this.posY, (double) (player.posY >> 6), 1, 4, 3.0d);
            this.posZ = MyAPI.calNextPosition((double) this.posZ, (double) player.posZ, 1, 4, 4.0d);
        }
    }

    public void doWhileCollision(SpecialObject collisionObj) {
        if (this.decided && this.follow && this.sleepCount == 0) {
            super.doWhileCollision(collisionObj);
        }
    }

    public boolean chkDestroy() {
        if (!this.decided) {
            return false;
        }
        return (this.posZ < ((player.posZ - 6) - 30) + 1 && !this.follow) || (this.used && this.drawer.checkEnd());
    }
}
