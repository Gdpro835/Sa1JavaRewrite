package com.sega.mobile.framework.android;

/** Font metrics substitute; these tests do not check glyph pixels. */
public final class Font {
    private int size;
    private Font(int size) { this.size = size; }
    public static Font getFont(int face, int style, int size) { return new Font(size == 0 ? 26 : size == 16 ? 30 : 24); }
    public static Font getFont(int size) { return new Font(size); }
    public void setSize(int size) { this.size = size; }
    public int getHeight() { return size; }
    public int stringWidth(String s) { return s.length() * size / 2; }
    public int getFontAscent() { return -size; }
}
