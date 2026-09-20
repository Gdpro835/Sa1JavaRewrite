package com.sega.mobile.framework.android;

import java.io.*;
import com.sega.mobile.framework.device.MFDevice;

/** Reads real PNG dimensions, not pixels. No Android Bitmap is created. */
public final class Image {
    private int width, height, alpha = -1;
    private Image(int width, int height) { this.width = width; this.height = height; }
    public static Image createImage(int w, int h) { return new Image(w, h); }
    public static Image createImage(InputStream in) throws IOException {
        DataInputStream data = new DataInputStream(in);
        if (data.readLong() != 0x89504e470d0a1a0aL) throw new IOException("Not a PNG");
        data.readInt();
        if (data.readInt() != 0x49484452) throw new IOException("Missing IHDR");
        return new Image(data.readInt(), data.readInt());
    }
    public static Image createImage(String url) throws IOException {
        InputStream in = MFDevice.getResourceAsStream(url);
        try { return createImage(in); } finally { if (in != null) in.close(); }
    }
    public static Image createImage(Image image, int x, int y, int w, int h, int transform) { return new Image(w, h); }
    public static Image createRGBImage(int[] rgb, int w, int h, boolean alpha) { return new Image(w, h); }
    public Graphics getGraphics() { return new Graphics(); }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getAlpha() { return alpha; }
    public void setAlpha(int value) { alpha = value; }
    public void getRGB(int[] rgb, int offset, int stride, int x, int y, int w, int h) { }
    public void earseColor(int color) { }
    public void close() { }
}
