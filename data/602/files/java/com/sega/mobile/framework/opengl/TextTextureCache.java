package com.sega.mobile.framework.opengl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.sega.mobile.framework.android.Font;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/** Screen-resolution masks for system text, independent of pixel-art sprite filtering.
 * Used only on the renderer thread. Bitmap uploads/compositing still belong to SpriteBatch.
 */
public final class TextTextureCache {
    private static final int MAX_ENTRIES = 128;
    private static final long BYTE_BUDGET = 8L * 1024L * 1024L;
    private static final long MAX_MASK_PIXELS = 1024L * 1024L;
    private static final int PADDING = 2; // Texture pixels, not scaled game pixels.
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Rect ink = new Rect();
    private final LinkedHashMap<String, Entry> entries = new LinkedHashMap<String, Entry>(32, 0.75f, true);
    private long bytes;

    public static final class Entry {
        public final Bitmap bitmap;
        public final float offsetX, offsetY, width, height;
        public final float rasterScale;
        private final long bytes;

        private Entry(Bitmap bitmap, float scale, int left, int top) {
            this.bitmap = bitmap;
            this.rasterScale = scale;
            this.offsetX = (left - PADDING) / scale;
            this.offsetY = (top - PADDING) / scale;
            this.width = bitmap.getWidth() / scale;
            this.height = bitmap.getHeight() / scale;
            this.bytes = bitmap.getByteCount();
        }
    }

    /** Largest singular value of the transform: rotation must not change text quality.
     * Integral buckets avoid rerasterizing a label for tiny changes of the zoom factor.
     */
    public static int rasterScale(float xx, float xy, float yx, float yy) {
        double a = (double) xx * xx + (double) yx * yx;
        double b = (double) xy * xy + (double) yy * yy;
        double c = (double) xx * xy + (double) yx * yy;
        double eigenvalue = (a + b + Math.sqrt((a - b) * (a - b) + 4 * c * c)) * 0.5;
        double scale = Math.sqrt(eigenvalue);
        if (Double.isNaN(scale) || Double.isInfinite(scale)) return 1;
        return (int) Math.max(1, Math.min(16, Math.ceil(scale - 1e-4)));
    }

    public Entry get(Font font, String text, int requestedScale, int maxTextureSize) {
        int bucket = Math.max(1, Math.min(16, requestedScale));
        int limit = Math.max(8, maxTextureSize);
        String key = font.getHeight() + ":" + bucket + ":" + limit + ":" + text;
        Entry entry = entries.get(key);
        if (entry != null) return entry;

        float scale = bucket;
        int left, top, width, height;
        // Measure before allocation. Oversized labels fall back to a smaller mask rather
        // than exceeding the GLES texture limit or allocating an unbounded bitmap.
        while (true) {
            paint.setAntiAlias(true);
            paint.setTextSize(font.getHeight() * scale);
            paint.getTextBounds(text, 0, text.length(), ink);
            Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
            left = Math.min(0, ink.left);
            top = Math.min(metrics.top, ink.top);
            int right = Math.max((int) Math.ceil(paint.measureText(text)), ink.right);
            int bottom = Math.max(metrics.bottom, ink.bottom);
            long w = Math.max(1L, (long) right - left + PADDING * 2L);
            long h = Math.max(1L, (long) bottom - top + PADDING * 2L);
            if (w <= limit && h <= limit && w * h <= MAX_MASK_PIXELS) {
                width = (int) w; height = (int) h;
                break;
            }
            float reduction = (float) Math.min(Math.min(limit / (double) w, limit / (double) h),
                    Math.sqrt(MAX_MASK_PIXELS / ((double) w * h)));
            scale *= Math.min(0.5f, reduction * 0.9f);
            if (scale <= 0f || Float.isNaN(scale) || Float.isInfinite(scale))
                throw new IllegalArgumentException("Text cannot fit the texture limits");
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // The mask is white/premultiplied; each outline/foreground pass supplies its own tint.
        paint.setColor(0xffffffff);
        new Canvas(bitmap).drawText(text, PADDING - left, PADDING - top, paint);
        entry = new Entry(bitmap, scale, left, top);
        entries.put(key, entry);
        bytes += entry.bytes;
        Iterator<Map.Entry<String, Entry>> iterator = entries.entrySet().iterator();
        while ((entries.size() > MAX_ENTRIES || bytes > BYTE_BUDGET) && iterator.hasNext()) {
            Entry oldest = iterator.next().getValue();
            bytes -= oldest.bytes;
            oldest.bitmap.recycle();
            iterator.remove();
        }
        return entry;
    }

    public long getBytes() { return bytes; }
    public int size() { return entries.size(); }

    public void clear() {
        for (Entry entry : entries.values()) entry.bitmap.recycle();
        entries.clear();
        bytes = 0;
    }
}
