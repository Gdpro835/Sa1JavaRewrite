package Common;

import SonicGBA.GameObject;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;

public final class WaveInvertEffect {
    private static final int[] SIN_TABLE;
    private static long time;
    private static long timeTick;

    static {
        int[] iArr = new int[10];
        iArr[1] = 178;
        iArr[2] = 350;
        iArr[3] = 512;
        iArr[4] = 658;
        iArr[5] = 784;
        iArr[6] = 887;
        iArr[7] = 962;
        iArr[8] = 1008;
        iArr[9] = 1024;
        SIN_TABLE = iArr;
    }

    private static int sin(long degrees) {
        return (int) (Math.sin((((double) ((degrees % 360) * 2)) * 3.141592653589793d) / 360.0d) * 1024.0d);
    }

    public static void drawImage(MFGraphics g, MFImage img, int x_dest, int y_dest, int aX, int bX, int aY, int bY, int speed) {
        int height = img.getHeight();
        int width = img.getWidth();
        if (!GameObject.IsGamePause) {
            if (timeTick < ((long) speed)) {
                timeTick++;
            } else {
                time++;
                timeTick = 0;
            }
        }
        for (int n = 0; n < height; n++) {
            int offsetX = (sin((long) ((n * bX) >> 2)) * aX) >> 14;
            int offsetY = (sin(((((long) n) + time) * ((long) bY)) >> 2) * aY) >> 14;
            if (n + offsetY < 0) {
                offsetY = -n;
            } else if (n + offsetY >= height) {
                offsetY = (height - n) - 1;
            }
            g.setClip(x_dest, y_dest + n, width, 1);
            g.drawImage(img, (offsetX + x_dest) - (aX >> 4), y_dest - offsetY, 20);
        }
    }
}
