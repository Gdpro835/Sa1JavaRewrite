package android.graphics;
public final class Canvas {
    private final Bitmap bitmap;
    public Canvas(Bitmap bitmap) { this.bitmap = bitmap; }
    public void drawText(String text, float x, float y, Paint paint) {
        bitmap.textSize = paint.size;
        bitmap.textAntialias = paint.aa;
        bitmap.textColor = paint.color;
        bitmap.drawX = x; bitmap.baseline = y;
        Rect ink = new Rect();
        paint.getTextBounds(text, 0, text.length(), ink);
        if (x + ink.left < 0 || x + ink.right > bitmap.width || y + ink.top < 0 || y + ink.bottom > bitmap.height)
            throw new AssertionError("Clipped glyph bearing/accent/descender");
    }
}
