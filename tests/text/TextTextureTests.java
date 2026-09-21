import android.graphics.Bitmap;
import com.sega.mobile.framework.android.Font;
import com.sega.mobile.framework.opengl.TextTextureCache;

/** Production cache/math/font wrapper, with deterministic Android metrics/allocation doubles. */
public final class TextTextureTests {
    private static int checks;
    private static void check(boolean ok, String message) {
        checks++;
        if (!ok) throw new AssertionError(message);
    }
    private static void near(double actual, double expected, double tolerance, String message) {
        check(Math.abs(actual - expected) <= tolerance, message + ": " + actual + " != " + expected);
    }
    private static void scale() {
        check(TextTextureCache.rasterScale(1, 0, 0, 1) == 1, "native layer stays at native resolution");
        check(TextTextureCache.rasterScale(4.5f, 0, 0, 4.5f) == 5, "letterboxed game text is rasterized for display scale");
        check(TextTextureCache.rasterScale(0, -4.5f, 4.5f, 0) == 5, "rotation preserves raster quality");
        check(TextTextureCache.rasterScale(2, 0, 0, 6) == 6, "nonuniform scaling uses the larger axis");
        check(TextTextureCache.rasterScale(1, 1, 0, 1) == 2, "shear uses the singular value, not just column length");
        check(TextTextureCache.rasterScale(4.000001f, 0, 0, 4.000001f) == 4, "tiny rounding noise does not churn cache entries");
        check(TextTextureCache.rasterScale(0.25f, 0, 0, 0.25f) == 1, "small labels retain at least a logical-resolution mask");
        check(TextTextureCache.rasterScale(Float.NaN, 0, 0, 1) == 1, "invalid transform has a bounded fallback");
        check(TextTextureCache.rasterScale(10000, 0, 0, 10000) <= 16, "raster zoom is bounded");
    }
    private static void maskAndBaseline() {
        TextTextureCache cache = new TextTextureCache();
        Font font = Font.getFont(11);
        String tip = "Chaos Emeralds can be";
        TextTextureCache.Entry low = cache.get(font, tip, 1, 2048);
        TextTextureCache.Entry screen = cache.get(font, tip, 5, 2048);
        check(low != screen, "a scale change creates a new-resolution mask");
        check(screen.bitmap.textSize == 55, "11-pixel game font is not stretched from an 11-pixel bitmap");
        check(screen.bitmap.getHeight() > low.bitmap.getHeight() * 3, "screen-resolution glyph details are retained");
        check(screen.bitmap.textAntialias, "system text retains AA independently of the pixel-art scene switch");
        check(screen.bitmap.textColor == 0xffffffff, "cached mask is reusable across outline and foreground colors");
        near(screen.offsetX + screen.bitmap.drawX / screen.rasterScale, 0, 1e-5, "logical text origin stays fixed");
        near(-font.getFontAscent() + screen.offsetY + screen.bitmap.baseline / screen.rasterScale,
                -font.getFontAscent(), 1e-5, "logical baseline matches the old Canvas path");
        int allocated = Bitmap.allocations;
        for (int i = 0; i < 9; i++) check(cache.get(font, tip, 5, 2048) == screen, "bold/outline passes share one rasterization");
        check(Bitmap.allocations == allocated, "steady text does not rerasterize every draw");
        TextTextureCache.Entry overhang = cache.get(font, "jÁg", 5, 2048);
        check(overhang.offsetX < -0.4f, "negative glyph bearing is kept");
        near(overhang.offsetY + overhang.bitmap.baseline / overhang.rasterScale, 0, 1e-5, "accent/descender padding keeps baseline");
        cache.clear();
        check(screen.bitmap.isRecycled() && low.bitmap.isRecycled() && overhang.bitmap.isRecycled(), "context release recycles masks");
        check(cache.getBytes() == 0 && cache.size() == 0, "context release clears accounting");
    }
    private static void limits() {
        TextTextureCache cache = new TextTextureCache();
        Font font = Font.getFont(14);
        StringBuilder longText = new StringBuilder();
        for (int i = 0; i < 2000; i++) longText.append('W');
        TextTextureCache.Entry entry = cache.get(font, longText.toString(), 16, 128);
        check(entry.bitmap.getWidth() <= 128 && entry.bitmap.getHeight() <= 128, "oversized labels fit the GPU limit");
        check(entry.rasterScale < 16, "oversized labels lower raster resolution before allocation");
        check(!entry.bitmap.isRecycled(), "newest mask remains valid");
        TextTextureCache.Entry oldest = cache.get(font, "oldest", 2, 2048);
        for (int i = 0; i < 160; i++) cache.get(font, "text " + i, 2, 2048);
        check(cache.size() <= 128 && oldest.bitmap.isRecycled(), "entry-count LRU is bounded");
        cache.clear();
        for (int i = 0; i < 60; i++) cache.get(font, "Screen resolution paragraph number " + i, 8, 2048);
        check(cache.getBytes() <= 8L * 1024 * 1024, "higher-resolution masks have a byte budget as well as an entry limit");
        check(cache.size() < 60, "byte budget actually evicts large masks");
        cache.clear();
    }
    public static void main(String[] args) {
        scale(); maskAndBaseline(); limits();
        System.out.println("PASS: " + checks + " text raster/cache assertions (API doubles; not Android glyph/pixel validation).");
    }
}
