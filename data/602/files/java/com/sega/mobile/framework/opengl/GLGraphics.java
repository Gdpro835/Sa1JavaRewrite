package com.sega.mobile.framework.opengl;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.sega.mobile.framework.android.Font;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.android.Image;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/** Graphics compatibility facade issuing textured triangles, never a CPU frame buffer. */
public final class GLGraphics extends Graphics {
    private static final class Saved {
        final Matrix matrix;
        final Rect clip;
        Saved(Matrix matrix, Rect clip) { this.matrix = new Matrix(matrix); this.clip = new Rect(clip); }
    }
    private final SpriteBatch batch;
    private final Matrix matrix = new Matrix();
    private final ArrayDeque<Saved> stack = new ArrayDeque<Saved>();
    private final Rect clip = new Rect();
    private final float[] xy = new float[8];
    private final float[] uv = new float[8];
    private final Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final LinkedHashMap<String, Bitmap> textCache = new LinkedHashMap<String, Bitmap>(32, 0.75f, true);
    private int color = 0xff000000;
    private int alpha = 255;
    private int surfaceWidth, surfaceHeight;
    private Font font = Font.getFont(0, 0, 8);
    private boolean linear;

    public GLGraphics(SpriteBatch batch) { this.batch = batch; }

    public void viewport(int width, int height, float scale, float offsetX, float offsetY) {
        surfaceWidth = width; surfaceHeight = height;
        matrix.setScale(scale, scale);
        matrix.postTranslate(offsetX, offsetY);
        stack.clear();
        clip.set(0, 0, width, height);
        batch.clip(0, 0, width, height);
        alpha = 255;
        batch.effect(0, 0, 0, 0);
    }

    public void setEffect(int red, int green, int blue, int gray) {
        batch.effect(red, green, blue, gray);
    }

    @Override public void setFilterBitmap(boolean enabled) { linear = enabled; }
    @Override public void setAntiAlias(boolean enabled) { textPaint.setAntiAlias(enabled); }
    @Override public void setAlpha(int value) { alpha = Math.max(0, Math.min(255, value)); }
    @Override public int getAlpha() { return alpha; }
    @Override public void setColor(int value) { color = 0xff000000 | value; }
    @Override public void setColor(int r, int g, int b) { setColor((r << 16) | (g << 8) | b); }
    @Override public int getColor() { return color; }
    @Override public void setFont(Font value) { font = value; }
    @Override public Font getFont() { return font; }
    @Override public void save() { stack.push(new Saved(matrix, clip)); }
    @Override public void restore() {
        if (stack.isEmpty()) throw new IllegalStateException("Unbalanced graphics restore");
        Saved saved = stack.pop();
        matrix.set(saved.matrix); clip.set(saved.clip);
        batch.clip(clip.left, clip.top, clip.right, clip.bottom);
    }
    @Override public void translate(float x, float y) { matrix.preTranslate(x, y); }
    @Override public void rotate(float degrees) { matrix.preRotate(degrees); }
    @Override public void rotate(float degrees, float x, float y) { matrix.preRotate(degrees, x, y); }
    @Override public void scale(float x, float y) { matrix.preScale(x, y); }
    @Override public void scale(float x, float y, float px, float py) { matrix.preScale(x, y, px, py); }

    private Rect transformedClip(int x, int y, int w, int h) {
        RectF r = new RectF(x, y, x + Math.max(0, w), y + Math.max(0, h));
        matrix.mapRect(r);
        return new Rect(Math.max(0, (int) Math.floor(r.left)), Math.max(0, (int) Math.floor(r.top)),
                Math.min(surfaceWidth, (int) Math.ceil(r.right)), Math.min(surfaceHeight, (int) Math.ceil(r.bottom)));
    }
    @Override public void setClip(int x, int y, int w, int h) {
        clip.set(transformedClip(x, y, w, h));
        batch.clip(clip.left, clip.top, clip.right, clip.bottom);
    }
    @Override public void clipRect(int left, int top, int right, int bottom) {
        Rect r = transformedClip(left, top, right - left, bottom - top);
        if (!clip.intersect(r)) clip.setEmpty();
        batch.clip(clip.left, clip.top, clip.right, clip.bottom);
    }

    private void rectangle(float x, float y, float w, float h) {
        xy[0] = x; xy[1] = y; xy[2] = x + w; xy[3] = y;
        xy[4] = x + w; xy[5] = y + h; xy[6] = x; xy[7] = y + h;
        matrix.mapPoints(xy);
    }
    private int paintColor() { return (alpha << 24) | (color & 0xffffff); }
    private int imageColor(Image image) {
        int opacity = image.getAlpha() < 0 ? alpha : Math.max(0, Math.min(255, image.getAlpha()));
        return (opacity << 24) | 0xffffff;
    }
    @Override public void fillRect(int x, int y, int w, int h) {
        if (w <= 0 || h <= 0) return;
        rectangle(x, y, w, h);
        batch.quad(null, false, xy, uv, paintColor());
    }
    @Override public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        triangle(x1, y1, x2, y2, x3, y3);
    }
    private void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        xy[0] = x1; xy[1] = y1; xy[2] = x2; xy[3] = y2; xy[4] = x3; xy[5] = y3;
        matrix.mapPoints(xy, 0, xy, 0, 3);
        batch.triangle(xy, paintColor());
    }
    @Override public void drawLine(int x1, int y1, int x2, int y2) { line(x1, y1, x2, y2); }
    private void line(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1, dy = y2 - y1;
        float length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length == 0f) { rectangle(x1, y1, 1, 1); batch.quad(null, false, xy, uv, paintColor()); return; }
        float nx = -dy / length * 0.5f, ny = dx / length * 0.5f;
        xy[0] = x1 + nx; xy[1] = y1 + ny; xy[2] = x2 + nx; xy[3] = y2 + ny;
        xy[4] = x2 - nx; xy[5] = y2 - ny; xy[6] = x1 - nx; xy[7] = y1 - ny;
        matrix.mapPoints(xy);
        batch.quad(null, false, xy, uv, paintColor());
    }
    @Override public void drawRect(int x, int y, int w, int h) {
        drawLine(x, y, x + w, y); drawLine(x + w, y, x + w, y + h);
        drawLine(x + w, y + h, x, y + h); drawLine(x, y + h, x, y);
    }
    private void arc(float x, float y, float w, float h, float start, float sweep, boolean fill) {
        if (w <= 0f || h <= 0f || sweep == 0f) return;
        sweep = Math.max(-360f, Math.min(360f, sweep));
        int segments = Math.max(2, (int) Math.ceil(Math.abs(sweep) / 6f));
        float cx = x + w / 2f, cy = y + h / 2f;
        for (int i = 0; i < segments; i++) {
            double a = Math.toRadians(start + sweep * i / segments);
            double b = Math.toRadians(start + sweep * (i + 1) / segments);
            float ax = cx + (float) Math.cos(a) * w / 2f, ay = cy + (float) Math.sin(a) * h / 2f;
            float bx = cx + (float) Math.cos(b) * w / 2f, by = cy + (float) Math.sin(b) * h / 2f;
            if (fill) triangle(cx, cy, ax, ay, bx, by); else line(ax, ay, bx, by);
        }
    }
    @Override public void drawArc(int x, int y, int w, int h, int start, int sweep) { arc(x, y, w, h, start, sweep, false); }
    @Override public void fillArc(int x, int y, int w, int h, int start, int sweep) { arc(x, y, w, h, start, sweep, true); }
    private void roundRect(int x, int y, int w, int h, int aw, int ah, boolean fill) {
        int rx = Math.max(0, Math.min(w / 2, aw)), ry = Math.max(0, Math.min(h / 2, ah));
        if (rx == 0 || ry == 0) { if (fill) fillRect(x, y, w, h); else drawRect(x, y, w, h); return; }
        if (fill) {
            fillRect(x + rx, y, w - rx * 2, h);
            fillRect(x, y + ry, rx, h - ry * 2); fillRect(x + w - rx, y + ry, rx, h - ry * 2);
        } else {
            drawLine(x + rx, y, x + w - rx, y); drawLine(x + rx, y + h, x + w - rx, y + h);
            drawLine(x, y + ry, x, y + h - ry); drawLine(x + w, y + ry, x + w, y + h - ry);
        }
        arc(x, y, rx * 2, ry * 2, 180, 90, fill); arc(x + w - rx * 2, y, rx * 2, ry * 2, 270, 90, fill);
        arc(x + w - rx * 2, y + h - ry * 2, rx * 2, ry * 2, 0, 90, fill);
        arc(x, y + h - ry * 2, rx * 2, ry * 2, 90, 90, fill);
    }
    @Override public void drawRoundRect(int x, int y, int w, int h, int aw, int ah) { roundRect(x, y, w, h, aw, ah, false); }
    @Override public void fillRoundRect(int x, int y, int w, int h, int aw, int ah) { roundRect(x, y, w, h, aw, ah, true); }

    private static int anchoredX(int x, int w, int anchor) { return x - ((anchor & RIGHT) != 0 ? w : (anchor & HCENTER) != 0 ? w / 2 : 0); }
    private static int anchoredY(int y, int h, int anchor) { return y - ((anchor & BOTTOM) != 0 ? h : (anchor & VCENTER) != 0 ? h / 2 : 0); }

    @Override public void drawImage(Image image, int x, int y, int anchor) {
        drawRegion(image, 0, 0, image.getBitmap().getWidth(), image.getBitmap().getHeight(), 0, x, y, anchor);
    }
    @Override public void drawRegion(Image image, int sx, int sy, int w, int h, int transform, int x, int y, int anchor) {
        if (w <= 0 || h <= 0) return;
        Bitmap bitmap = image.getBitmap();
        int dw = SpriteTransform.swapsAxes(transform) ? h : w, dh = SpriteTransform.swapsAxes(transform) ? w : h;
        x = anchoredX(x, dw, anchor); y = anchoredY(y, dh, anchor);
        rectangle(x, y, dw, dh);
        SpriteTransform.textureCoordinates(transform, sx / (float) bitmap.getWidth(), sy / (float) bitmap.getHeight(),
                (sx + w) / (float) bitmap.getWidth(), (sy + h) / (float) bitmap.getHeight(), uv);
        batch.quad(bitmap, linear, xy, uv, imageColor(image));
    }
    @Override public void drawRegion(Image image, int sx, int sy, int w, int h, int rx, int ry,
            int degree, int scaleX, int scaleY, int x, int y, int anchor) {
        x = anchoredX(x, w, anchor); y = anchoredY(y, h, anchor);
        save();
        translate(x, y); rotate(degree, rx, ry); scale(scaleX, scaleY, w / 2f, h / 2f);
        drawRegion(image, sx, sy, w, h, 0, 0, 0, LEFT | TOP);
        restore();
    }
    @Override public void drawScreen(Image image, Rect source, Rect target) {
        Bitmap bitmap = image.getBitmap();
        Rect src = source == null ? new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()) : source;
        rectangle(target.left, target.top, target.width(), target.height());
        SpriteTransform.textureCoordinates(0, src.left / (float) bitmap.getWidth(), src.top / (float) bitmap.getHeight(),
                src.right / (float) bitmap.getWidth(), src.bottom / (float) bitmap.getHeight(), uv);
        batch.quad(bitmap, linear, xy, uv, imageColor(image));
    }
    @Override public void drawString(String text, int x, int y, int anchor) {
        if (text == null || text.length() == 0) return;
        String key = font.getHeight() + ":" + text;
        Bitmap bitmap = textCache.get(key);
        textPaint.setTextSize(font.getHeight());
        if (bitmap == null) {
            Paint.FontMetricsInt metrics = textPaint.getFontMetricsInt();
            bitmap = Bitmap.createBitmap(Math.max(1, (int) Math.ceil(textPaint.measureText(text)) + 2),
                    Math.max(1, metrics.descent - metrics.ascent + 2), Bitmap.Config.ARGB_8888);
            textPaint.setColor(0xffffffff);
            new android.graphics.Canvas(bitmap).drawText(text, 1, 1 - metrics.ascent, textPaint);
            textCache.put(key, bitmap);
            if (textCache.size() > 128) {
                Iterator<Map.Entry<String, Bitmap>> it = textCache.entrySet().iterator();
                Map.Entry<String, Bitmap> oldest = it.next();
                oldest.getValue().recycle(); it.remove();
            }
        }
        x = anchoredX(x, font.stringWidth(text), anchor); y = anchoredY(y, font.getHeight(), anchor);
        rectangle(x - 1, y - 1, bitmap.getWidth(), bitmap.getHeight());
        SpriteTransform.textureCoordinates(0, 0, 0, 1, 1, uv);
        batch.quad(bitmap, true, xy, uv, paintColor());
    }
    @Override public void drawRGB(int[] data, int offset, int stride, int x, int y, int w, int h, boolean processAlpha) {
        if (w <= 0 || h <= 0) return;
        Bitmap bitmap = Bitmap.createBitmap(data, offset, stride, w, h,
                processAlpha ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        rectangle(x, y, w, h);
        SpriteTransform.textureCoordinates(0, 0, 0, 1, 1, uv);
        batch.quad(bitmap, linear, xy, uv, (alpha << 24) | 0xffffff);
        // GL upload is synchronous; the batch only retains the GL texture name.
        bitmap.recycle();
    }
    public void release() {
        for (Bitmap bitmap : textCache.values()) bitmap.recycle();
        textCache.clear();
        stack.clear();
    }
}
