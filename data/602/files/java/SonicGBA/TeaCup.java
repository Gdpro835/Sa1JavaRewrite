//
// Decompiled by Procyon - 910ms
//
package SonicGBA;

import Lib.MyAPI;
import com.sega.mobile.framework.device.MFGraphics;
import Lib.SoundSystem;
import Lib.crlFP32;
import com.sega.mobile.framework.device.MFImage;

class TeaCup extends GimmickObject
{
    private static final int COLLISION_HEIGHT = 2560;
    private static final int COLLISION_OFFSET_Y = -1536;
    private static final int COLLISION_WIDTH = 6144;
    private static final int COLLISION_Y1_ORIBINAL = 768;
    private static final int COLLISION_Y1_ROLLING_2 = 256;
    private static final int MAX_RADIUS = 2240;
    private static final int MIN_RADIUS = 320;
    private static final int MOVE_FRAME = 37;
    private static final int MOVE_LENGTH = 7168;
    private static final int MOVE_SPEED = 192;
    private static final int ROLLING_2_COUNT = 16;
    private static final byte STATE_ROLLING = 1;
    private static final byte STATE_ROLLING_2 = 2;
    private static final byte STATE_SHOOT = 3;
    private static final byte STATE_STAY = 0;
    private static MFImage teacupImage;
    private int changeTime;
    private int changeTimeCount;
    private int collisionHeight;
    private int count;
    private int degreeSpeed;
    private byte drawCount;
    private int playerDegree;
    private int playerX;
    private int prePosY;
    private byte state;
    
    protected TeaCup(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) {
        super(n, n2, n3, n4, n5, n6, n7);
        while (true) {
            if (TeaCup.teacupImage != null) {
                break;
            }
            try {
                TeaCup.teacupImage = MFImage.createImage("/gimmick/teacup_" + StageManager.getCurrentZoneId() + ".png");
                this.changeTime = 0;
                this.state = 0;
                this.degreeSpeed = 0;
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                continue;
            }
            break;
        }
    }
    
    public static void releaseAllResource() {
        TeaCup.teacupImage = null;
    }
    
    public void close() {
    }
    
    public void doWhileCollision(final PlayerObject playerObject, int sqrt) {
        if (playerObject == TeaCup.player) {
            switch (this.state) {
                case 0: {
                    this.playerX = TeaCup.player.getFootPositionX() - this.posX;
                    if (this.playerX > 2240) {
                        this.playerX = 2240;
                    }
                    else if (this.playerX < -2240) {
                        this.playerX = -2240;
                    }
                    sqrt = crlFP32.sqrt(5017600 - this.playerX * this.playerX);
                    this.playerDegree = crlFP32.actTanDegree(this.playerX, sqrt >> 3);
                    playerObject.beStop(this.collisionRect.y0, 1, (GameObject)this);
                    playerObject.setNoKey();
                    this.prePosY = this.posY;
                    this.state = 1;
                    this.changeTime = 2;
                    SoundSystem.getInstance().playLongSe(72);
                    break;
                }
            }
        }
    }
    
    public void draw(final MFGraphics mfGraphics) {
        this.drawInMap(mfGraphics, TeaCup.teacupImage, 0, this.drawCount * 40, 96, 40, 0, this.posX, this.posY - 1536, 17);
        this.drawCollisionRect(mfGraphics);
    }
    
    public int getPaintLayer() {
        return 2;
    }
    
    public void logic() {
        if (!TeaCup.player.isDead) {
            if (this.changeTime > 0) {
                ++this.changeTimeCount;
                if (this.changeTimeCount >= this.changeTime) {
                    this.drawCount = (byte)((this.drawCount + 1) % 2);
                    this.changeTimeCount = 0;
                }
            }
            if (this.count > 0) {
                --this.count;
            }
            switch (this.state) {
                case 0: {
                    this.collisionHeight = 768;
                    break;
                }
                case 1: {
                    TeaCup.player.setNoKey();
                    TeaCup.player.setAnimationId(4);
                    this.playerDegree += this.degreeSpeed;
                    this.playerDegree %= 360;
                    TeaCup.player.setFootPositionX(this.posX + (2240 - (this.posY - this.prePosY) * 1920 / 7168) * MyAPI.dSin(this.playerDegree) / 100);
                    this.checkWithPlayer(this.posX, this.posY, this.posX, this.posY + 192);
                    this.posY += 192;
                    if (this.posY >= this.prePosY + 7168) {
                        this.posY = this.prePosY + 7168;
                        TeaCup.player.setFootPositionX(this.posX);
                        this.state = 2;
                        this.count = 16;
                        this.changeTime = 1;
                        this.collisionHeight = 256;
                    }
                    this.degreeSpeed = (this.posY - this.prePosY) * 60 / 7168 + 0;
                    break;
                }
                case 2: {
                    TeaCup.player.setNoKey();
                    TeaCup.player.setAnimationId(4);
                    this.playerDegree += this.degreeSpeed;
                    this.playerDegree %= 360;
                    TeaCup.player.setFootPositionX(this.posX + MyAPI.dSin(this.playerDegree) * 320 / 100);
                    this.checkWithPlayer(this.posX, this.posY, this.posX, this.posY);
                    if (this.count == 0) {
                        TeaCup.player.setFootPositionX(this.posX);
                        this.state = 3;
                        this.changeTime = 2;
                        final PlayerObject player = TeaCup.player;
                        final PlayerObject player2 = TeaCup.player;
                        player.beSpring(1800, 0);
                        TeaCup.player.isPowerShoot = true;
                        TeaCup.player.collisionState = 1;
                        TeaCup.player.setAnimationId(4);
                        break;
                    }
                    break;
                }
                case 3: {
                    if (this.changeTime <= 0 || this.changeTimeCount != 0) {
                        break;
                    }
                    ++this.changeTime;
                    if (this.changeTime > 6) {
                        this.changeTime = 0;
                        SoundSystem.getInstance().stopLongSe();
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public void refreshCollisionRect(final int n, final int n2) {
        this.collisionRect.setRect(n - 3072, n2 - this.collisionHeight, 6144, 1024);
    }
}