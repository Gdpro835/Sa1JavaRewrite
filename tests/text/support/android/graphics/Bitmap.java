package android.graphics;

/** Allocation/paint-call recorder only: no pixels or Android/GLES behavior is emulated. */
public final class Bitmap {
    public enum Config { ARGB_8888 }
    public static int allocations;
    public final int width, height;
    public float textSize, drawX, baseline;
    public boolean textAntialias, recycled;
    public int textColor;
    private Bitmap(int w, int h) { width = w; height = h; allocations++; }
    public static Bitmap createBitmap(int w, int h, Config config) {
        if (w <= 0 || h <= 0) throw new AssertionError("Invalid bitmap size");
        return new Bitmap(w, h);
    }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getByteCount() { return width * height * 4; }
    public void recycle() { recycled = true; }
    public boolean isRecycled() { return recycled; }
}
