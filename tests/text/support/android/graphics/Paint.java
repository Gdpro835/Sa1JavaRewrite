package android.graphics;

/** Deterministic metrics double. NOT Android glyph rasterization or a screenshot oracle. */
public final class Paint {
    public static final int ANTI_ALIAS_FLAG = 1;
    public static final class FontMetricsInt { public int top, ascent, descent, bottom; }
    float size = 11;
    boolean aa;
    int color;
    public Paint() { }
    public Paint(int flags) { aa = (flags & ANTI_ALIAS_FLAG) != 0; }
    public void setTextSize(float value) { size = value; }
    public void setAntiAlias(boolean value) { aa = value; }
    public void setFilterBitmap(boolean value) { }
    public void setColor(int value) { color = value; }
    public float measureText(String text) { return text.length() * size * 0.6f; }
    public FontMetricsInt getFontMetricsInt() {
        FontMetricsInt m = new FontMetricsInt();
        m.ascent = -(int)Math.ceil(size * 0.8);
        m.descent = (int)Math.ceil(size * 0.2);
        m.top = m.ascent - 1; m.bottom = m.descent + 1;
        return m;
    }
    public void getTextBounds(String s, int start, int end, Rect r) {
        int left = s.charAt(start) == 'j' ? -(int)Math.ceil(size * 0.15) : 0;
        int top = -(int)Math.ceil(size * (s.indexOf('Á') >= 0 ? 1.25 : 0.8));
        int bottom = (int)Math.ceil(size * (s.indexOf('g') >= 0 ? 0.3 : 0.2));
        r.set(left, top, (int)Math.ceil(measureText(s.substring(start, end))), bottom);
    }
}
