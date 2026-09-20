package com.sega.mobile.framework.android;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.framework.device.MFDevice;
import java.io.IOException;
import java.io.InputStream;
import com.sega.MFLib.Main;

public class Image {
    private int alpha = -1;
    private Bitmap mBitmap;

    public static Image createImage(int width, int height) {
        if (MFDevice.preScaleZoomOutFlag) {
            width >>= MFDevice.preScaleShift;
            height >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            width <<= MFDevice.preScaleShift;
            height <<= MFDevice.preScaleShift;
        }
        Image img = new Image();
        img.mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        return img;
    }

    public static Image createRGBImage(int[] rgb, int width, int height, boolean processAlpha) {
        int[] map = rgb;
        if (!processAlpha) {
            map = new int[rgb.length];
            for (int i = 0; i < rgb.length; i++) {
                map[i] = (16777215 & rgb[i]) | -16777216;
            }
        }
        Image img = new Image();
        img.mBitmap = Bitmap.createBitmap(map, width, height, Bitmap.Config.ARGB_8888);
        if (MFDevice.preScaleZoomOutFlag) {
            img.mBitmap = Bitmap.createScaledBitmap(img.mBitmap, (width >> MFDevice.preScaleShift) * Main.scale, (height >> MFDevice.preScaleShift) * Main.scale, true);
        } else if (MFDevice.preScaleZoomInFlag) {
            img.mBitmap = Bitmap.createScaledBitmap(img.mBitmap, (width << MFDevice.preScaleShift) * Main.scale, (height << MFDevice.preScaleShift) * Main.scale, true);
        }
        return img;
    }

    public static Image createImage(Image image, int x, int y, int width, int height, int transform) {
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            width >>= MFDevice.preScaleShift;
            height >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            width <<= MFDevice.preScaleShift;
            height <<= MFDevice.preScaleShift;
        }
        width *= Main.scale;
        height *= Main.scale;
        Matrix matrix = new Matrix();
        matrix.setTranslate((float) x, (float) y);
        switch (transform) {
            case 1:
                matrix.preScale(-1.0f, 1.0f);
                matrix.preRotate(-180.0f);
                break;
            case 2:
                matrix.preScale(-1.0f, 1.0f);
                break;
            case 3:
                matrix.preRotate(180.0f);
                break;
            case 4:
                matrix.preScale(-1.0f, 1.0f);
                matrix.preRotate(-270.0f);
                break;
            case 5:
                matrix.preRotate(90.0f);
                break;
            case 6:
                matrix.preRotate(270.0f);
                break;
            case 7:
                matrix.preScale(-1.0f, 1.0f);
                matrix.preRotate(-90.0f);
                break;
        }
        Image img = new Image();
        img.mBitmap = Bitmap.createBitmap(image.getBitmap(), x, y, width, height, matrix, false);
        return img;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public Graphics getGraphics() {
        return Graphics.createBitmapGraphics(this.mBitmap);
    }

    public int getHeight() {
        if (MFDevice.preScaleZoomOutFlag) {
            return this.mBitmap.getHeight() << MFDevice.preScaleShift;
        }
        if (MFDevice.preScaleZoomInFlag) {
            return this.mBitmap.getHeight() >> MFDevice.preScaleShift;
        }
        return this.mBitmap.getHeight();
    }

    public int getWidth() {
        if (MFDevice.preScaleZoomOutFlag) {
            return this.mBitmap.getWidth() << MFDevice.preScaleShift;
        }
        if (MFDevice.preScaleZoomInFlag) {
            return this.mBitmap.getWidth() >> MFDevice.preScaleShift;
        }
        return this.mBitmap.getWidth();
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setAlpha(int a) {
        this.alpha = a;
    }

    public void earseColor(int color) {
        this.mBitmap.eraseColor(color);
    }

    public void getRGB(int[] rgb, int offset, int scanlength, int x, int y, int w, int h) {
        if (MFDevice.preScaleZoomOutFlag) {
            scanlength >>= MFDevice.preScaleShift;
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            w >>= MFDevice.preScaleShift;
            h >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            scanlength <<= MFDevice.preScaleShift;
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            w <<= MFDevice.preScaleShift;
            h <<= MFDevice.preScaleShift;
        }
        if (MFDevice.preScaleZoomOutFlag) {
            int[] tmp = new int[(w * h)];
            this.mBitmap.getPixels(tmp, offset, scanlength, x, y, w, h);
            for (int i = 0; i < (w << MFDevice.preScaleShift); i++) {
                for (int j = 0; j < (h << MFDevice.preScaleShift); j++) {
                    rgb[(j * w) + i] = tmp[(i >> MFDevice.preScaleShift) + ((j >> MFDevice.preScaleShift) * w)];
                }
            }
        } else if (MFDevice.preScaleZoomInFlag) {
            int[] tmp2 = new int[(w * h)];
            this.mBitmap.getPixels(tmp2, offset, scanlength, x, y, w, h);
            for (int i2 = 0; i2 < (w >> MFDevice.preScaleShift); i2++) {
                for (int j2 = 0; j2 < (h >> MFDevice.preScaleShift); j2++) {
                    rgb[(j2 * w) + i2] = tmp2[(i2 << MFDevice.preScaleShift) + ((j2 << MFDevice.preScaleShift) * w)];
                }
            }
        } else {
            this.mBitmap.getPixels(rgb, offset, scanlength, x, y, w, h);
        }
    }

    public static Image createImage(InputStream is) throws IOException {
        Image img = new Image();
        img.mBitmap = BitmapFactory.decodeStream(is);
        if (MFDevice.preScaleZoomOutFlag) {
            Bitmap old = img.mBitmap;
            img.mBitmap = Bitmap.createScaledBitmap(old, (old.getWidth() >> MFDevice.preScaleShift) * Main.scale, (old.getHeight() >> MFDevice.preScaleShift) * Main.scale, true);
            old.recycle();
        } else if (MFDevice.preScaleZoomInFlag) {
            Bitmap old2 = img.mBitmap;
            img.mBitmap = Bitmap.createScaledBitmap(old2, (old2.getWidth() << MFDevice.preScaleShift) * Main.scale, (old2.getHeight() << MFDevice.preScaleShift) * Main.scale, false);
            old2.recycle();
        }
        return img;
    }

    public static Image createImage(String url) throws IOException {
        String str;
        AssetManager assets = MFMain.getInstance().getAssets();
        if (url.startsWith("/")) {
            str = url.substring(1);
        } else {
            str = url;
        }
        return createImage(assets.open(str));
    }

    public static Image createImage(String url, int w, int h) throws IOException {
        String str;
        AssetManager assets = MFMain.getInstance().getAssets();
        if (url.startsWith("/")) {
            str = url.substring(1);
        } else {
            str = url;
        }
        Bitmap src = BitmapFactory.decodeStream(assets.open(str));
        Image img = new Image();
        img.mBitmap = Bitmap.createScaledBitmap(src, w, h, true);
        src.recycle();
        return img;
    }

    public void close() {
        this.mBitmap.recycle();
    }
}
