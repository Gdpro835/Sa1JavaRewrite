package com.sega.mobile.framework.device;

import com.sega.mobile.framework.android.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MFImage {
    private int alphaColor = -1;
    private MFGraphics graphics;
    protected Image image;
    private String imageIdentifier;
    private boolean mutable = false;

    private MFImage() {
    }

    public static final MFImage createPaletteImage(String paletteFileName) {
        try {
            InputStream is = MFDevice.getResourceAsStream(paletteFileName);
            byte[] imageNameBytes = new byte[is.read()];
            is.read(imageNameBytes);
            ByteArrayOutputStream bis = new ByteArrayOutputStream();
            while (true) {
                int data = is.read();
                if (data == -1) {
                    break;
                }
                bis.write(data);
            }
            is.close();
            byte[] palData = bis.toByteArray();
            bis.close();
            InputStream is2 = MFDevice.getResourceAsStream(String.valueOf(paletteFileName.substring(0, paletteFileName.lastIndexOf(47) + 1)) + new String(imageNameBytes));
            ByteArrayOutputStream bis2 = new ByteArrayOutputStream();
            while (true) {
                int data2 = is2.read();
                if (data2 == -1) {
                    break;
                }
                bis2.write(data2);
            }
            is2.close();
            byte[] image2 = bis2.toByteArray();
            bis2.close();
            for (int i = 0; i < palData.length; i++) {
                image2[i + 37] = palData[i];
            }
            return createImage((InputStream) new ByteArrayInputStream(image2), paletteFileName);
        } catch (Throwable th) {
            Throwable th2 = th;
            return null;
        }
    }

    public static final MFImage createImage(String url) {
        return createImage(MFDevice.getResourceAsStream(url), url);
    }

    public static final MFImage createImage(InputStream is, String url) {
        if (is == null) {
            return null;
        }
        MFImage ret = null;
        try {
            ret = createImage(Image.createImage(is));
        } catch (Exception e) {
        }
        try {
            is.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        ret.imageIdentifier = String.valueOf(url) + "    " + ret.getWidth() + "x" + ret.getHeight();
        MFDebug.loadImage(ret.imageIdentifier);
        return ret;
    }

    public static final MFImage createImage(InputStream is) {
        return createImage(is, "bytes image");
    }

    public static final MFImage createImage(MFImage image2, int x, int y, int width, int height, int transform) {
        return createImage(Image.createImage(image2.image, x, y, width, height, transform));
    }

    public static final MFImage createImage(Image img) {
        MFImage ret = new MFImage();
        ret.image = img;
        return ret;
    }

    public static final MFImage createImage(int width, int height) {
        System.out.println("createImage with w & h");
        MFImage ret = new MFImage();
        ret.image = Image.createImage(width, height);
        ret.graphics = MFGraphics.createMFGraphics(ret.image.getGraphics(), width, height);
        ret.mutable = true;
        return ret;
    }

    public static final MFImage createImage(byte[] array) {
        ByteArrayInputStream bais = new ByteArrayInputStream(array);
        MFImage ret = createImage((InputStream) bais);
        try {
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static final MFImage createImage(byte[] array, int offset, int length) {
        ByteArrayInputStream bais = new ByteArrayInputStream(array, offset, length);
        MFImage ret = createImage((InputStream) bais);
        try {
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public final MFGraphics getGraphics() {
        return this.graphics;
    }

    public final int getWidth() {
        return this.image.getWidth();
    }

    public final int getHeight() {
        return this.image.getHeight();
    }

    public final void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) {
        this.image.getRGB(rgbData, offset, scanlength, x, y, width, height);
    }

    public boolean isMutable() {
        return this.mutable;
    }

    public void setAlphaColor(int color) {
        this.alphaColor = -16777216 | color;
    }

    public int getAlphaColor() {
        return this.alphaColor;
    }

    public void setAlpha(int a) {
        this.image.setAlpha(a);
    }

    public int getAlpha() {
        return this.image.getAlpha();
    }

    public void close() {
        this.image.close();
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        MFDebug.releaseImage(this.imageIdentifier);
        super.finalize();
    }
}
