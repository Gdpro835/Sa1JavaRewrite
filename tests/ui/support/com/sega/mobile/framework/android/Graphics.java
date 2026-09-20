package com.sega.mobile.framework.android;

import java.util.ArrayList;

/** Command-recording graphics double. Does NOT validate GLES, textures or Android pixels. */
public class Graphics {
    public static final int LEFT = 4, TOP = 16, RIGHT = 8, BOTTOM = 32, HCENTER = 1, VCENTER = 2;
    public final ArrayList<String> commands = new ArrayList<String>();
    public int rgbCalls, rectCalls;
    public boolean fractionalTranslation, fractionalTranslationY;
    private int saveDepth;
    private int alpha = 255, color;
    private Font font = Font.getFont(11);
    public void clearCommands() { commands.clear(); rgbCalls = rectCalls = 0; fractionalTranslation = fractionalTranslationY = false; }
    public void setFilterBitmap(boolean value) { }
    public void setAntiAlias(boolean value) { }
    public void setAlpha(int value) { alpha = value; }
    public int getAlpha() { return alpha; }
    public void setColor(int value) { color = value; }
    public void setColor(int r, int g, int b) { color = r << 16 | g << 8 | b; }
    public int getColor() { return color; }
    public void setFont(Font value) { font = value; }
    public Font getFont() { return font; }
    public void save() { saveDepth++; }
    public void restore() {
        if (--saveDepth < 0) throw new AssertionError("Unbalanced graphics restore");
    }
    public int getSaveDepth() { return saveDepth; }
    public void translate(float x, float y) {
        fractionalTranslation |= x != Math.rint(x) || y != Math.rint(y);
        fractionalTranslationY |= y != Math.rint(y);
    }
    public void scale(float x, float y) { }
    public void scale(float x, float y, float px, float py) { }
    public void rotate(float angle) { }
    public void rotate(float angle, float px, float py) { }
    public void setClip(int x, int y, int w, int h) { }
    public void clipRect(int l, int t, int r, int b) { }
    public void fillRect(int x, int y, int w, int h) { rectCalls++; commands.add("rect:"+x+":"+y+":"+w+":"+h+":"+alpha); }
    public void drawRect(int x, int y, int w, int h) { }
    public void fillTriangle(int x, int y, int x2, int y2, int x3, int y3) { }
    public void drawLine(int x, int y, int x2, int y2) { }
    public void drawArc(int x, int y, int w, int h, int a, int sweep) { }
    public void fillArc(int x, int y, int w, int h, int a, int sweep) { }
    public void drawRoundRect(int x, int y, int w, int h, int a, int b) { }
    public void fillRoundRect(int x, int y, int w, int h, int a, int b) { }
    public void drawString(String text, int x, int y, int anchor) { }
    public void drawRGB(int[] data, int offset, int stride, int x, int y, int w, int h, boolean alpha) { rgbCalls++; }
    public void drawImage(Image image, int x, int y, int anchor) {
        drawRegion(image, 0, 0, image.getWidth(), image.getHeight(), 0, x, y, anchor);
    }
    public void drawRegion(Image image, int sx, int sy, int w, int h, int transform, int x, int y, int anchor) {
        commands.add("image:"+sx+":"+sy+":"+w+":"+h+":"+transform+":"+x+":"+y);
    }
    public void drawRegion(Image image, int sx, int sy, int w, int h, int rx, int ry, int degree, int scaleX, int scaleY, int x, int y, int anchor) {
        drawRegion(image, sx, sy, w, h, 0, x, y, anchor);
    }
}
