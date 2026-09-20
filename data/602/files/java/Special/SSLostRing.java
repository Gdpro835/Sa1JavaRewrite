package Special;

import GameEngine.time.GameTime;

import com.sega.mobile.framework.device.MFGraphics;

/* compiled from: SpecialObject */
class SSLostRing extends SSRing {
    private static final int GRAVITY = 172;
    private int velX;
    private int velY;
    private int velZ;

    public SSLostRing(int x, int y, int z, int velX2, int velY2, int velZ2) {
        super(x, y, z << 3);
        this.velX = velX2;
        this.velY = velY2;
        this.velZ = velZ2;
    }

    public void close() {
    }

    public void doWhileCollision(SpecialObject collisionObj) {
    }

    public void draw(MFGraphics g) {
        calDrawPosition(this.posX >> 6, this.posY >> 6, this.posZ);
        drawObj(g, ringDrawer, 0, 8);
    }

    public void logic() {
        this.posZ = GameTime.advancePosition(this, "posZ", this.posZ, "velZ", this.velZ);
        this.posX = GameTime.advancePosition(this, "posX", this.posX, "velX", this.velX);
        this.posY = GameTime.advancePosition(this, "posY", this.posY, "velY", this.velY);
    }

    public void refreshCollision(int x, int y) {
    }

    public boolean chkDestory() {
        return this.posZ < ((player.posZ - 6) - 30) + 1;
    }
}
