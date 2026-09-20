package com.sega.mobile.framework.android;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import com.sega.MFLib.Main;
import SonicGBA.GlobalResource;

public class Graphics {
    public static final int BOTTOM = 32;
    public static final int HCENTER = 1;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int TOP = 16;
    public static final int TRANS_MIRROR = 2;
    public static final int TRANS_MIRROR_ROT180 = 1;
    public static final int TRANS_MIRROR_ROT270 = 4;
    public static final int TRANS_MIRROR_ROT90 = 7;
    public static final int TRANS_NONE = 0;
    public static final int TRANS_ROT180 = 3;
    public static final int TRANS_ROT270 = 6;
    public static final int TRANS_ROT90 = 5;
    public static final int VCENTER = 2;
    private Canvas mCanvas;
    private Font mFont = Font.getFont(0, 0, 8);
    private Matrix mMatrix = new Matrix();
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

    public Graphics() {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setTextSize((float) this.mFont.getHeight());
    }

    public void setFilterBitmap(boolean b) {
        this.mPaint.setFilterBitmap(b);
    }

    public void setAntiAlias(boolean b) {
        this.mPaint.setAntiAlias(b);
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    public static Graphics createBitmapGraphics(Bitmap bitmap) {
        Graphics g = new Graphics();
        g.mCanvas = new Canvas(bitmap);
        return g;
    }

    public void drawScreen(Image img, Rect orgRect, Rect targetRect) {
        this.mCanvas.drawBitmap(img.getBitmap(), orgRect, targetRect, this.mPaint);
    }

    public void setCanvas(Canvas canvas) {
        this.mCanvas = canvas;
    }

    public Canvas getCanvas() {
        return this.mCanvas;
    }

    public void save() {
        this.mCanvas.save();
    }

    public void restore() {
        this.mCanvas.restore();
    }

    public void clipRect(int left, int top, int right, int bottom) {
        this.mCanvas.clipRect(left, top, right, bottom);
    }

    public void rotate(float degrees) {
        this.mCanvas.rotate(degrees);
    }

    public void rotate(float degrees, float px, float py) {
        this.mCanvas.rotate(degrees, px, py);
    }

    public void scale(float sx, float sy) {
        this.mCanvas.scale(sx, sy);
    }

    public void scale(float sx, float sy, float px, float py) {
        this.mCanvas.scale(sx, sy, px, py);
    }

    public void translate(float dx, float dy) {
        this.mCanvas.translate(dx, dy);
    }

    public void setColor(int color) {
        this.mPaint.setColor(-16777216 | color);
    }

    public void setColor(int r, int g, int b) {
        this.mPaint.setColor((r << 16) + (g << 8) + b);
    }

    public void fillRect(int x, int y, int w, int h) {
        this.mPaint.setStyle(Paint.Style.FILL);
        if (this.mCanvas != null) {
            this.mCanvas.drawRect((float) x, (float) y, (float) (x + w), (float) (y + h), this.mPaint);
        }
    }

    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        this.mPath.reset();
        this.mPath.moveTo((float) x3, (float) y3);
        this.mPath.lineTo((float) x1, (float) y1);
        this.mPath.lineTo((float) x2, (float) y2);
        this.mPath.lineTo((float) x3, (float) y3);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mCanvas.drawPath(this.mPath, this.mPaint);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        if (this.mCanvas != null) {
            this.mCanvas.drawLine((float) x1, (float) y1, (float) x2, (float) y2, this.mPaint);
        }
    }

    public void setFont(Font font) {
        this.mFont = font;
        this.mPaint.setTextSize((float) font.getHeight());
    }

    public Font getFont() {
        return this.mFont;
    }

    public void drawString(String str, int x, int y, int anchor) {
        if ((anchor & 1) != 0) {
            x -= this.mFont.stringWidth(str) / 2;
        } else if ((anchor & 8) != 0) {
            x -= this.mFont.stringWidth(str);
        }
        if ((anchor & 2) != 0) {
            y -= this.mFont.getHeight() / 2;
        } else if ((anchor & 32) != 0) {
            y -= this.mFont.getHeight();
        }
        if (this.mCanvas != null) {
            this.mCanvas.drawText(str, (float) x, (float) (y - this.mFont.getFontAscent()), this.mPaint);
        }
    }

    public void drawImage(Image image, int x, int y, int anchor) {
        if ((anchor & 1) != 0) {
            x -= image.getWidth() / 2;
        } else if ((anchor & 8) != 0) {
            x -= image.getWidth();
        }
        if ((anchor & 2) != 0) {
            y -= image.getHeight() / 2;
        } else if ((anchor & 32) != 0) {
            y -= image.getHeight();
        }
        if (this.mCanvas != null) {
            int alpha = this.mPaint.getAlpha();
            if (image.getAlpha() != -1) {
                this.mPaint.setAlpha(image.getAlpha());
            }
            this.mCanvas.drawBitmap(image.getBitmap(), (float) x, (float) y, this.mPaint);
            this.mPaint.setAlpha(alpha);
        }
    }

    public void setClip(int x, int y, int w, int h) {
        if (this.mCanvas != null) {
            this.mCanvas.clipRect((float) x, (float) y, (float) (x + w), (float) (y + h), Region.Op.REPLACE);
        }
    }

    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) {
        this.mCanvas.drawBitmap(rgbData, offset, scanlength, x, y, width, height, processAlpha, this.mPaint);
    }

    public int getColor() {
        return this.mPaint.getColor();
    }

    public void drawRect(int x, int y, int w, int h) {
        this.mPaint.setStyle(Paint.Style.STROKE);
        if (this.mCanvas != null) {
            this.mCanvas.drawRect((float) x, (float) y, (float) (x + w), (float) (y + h), this.mPaint);
        }
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        this.mPaint.setStyle(Paint.Style.STROKE);
        if (this.mCanvas != null) {
            this.mCanvas.drawArc(new RectF((float) x, (float) y, (float) (x + width), (float) (y + height)), (float) startAngle, (float) arcAngle, false, this.mPaint);
        }
    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        this.mPaint.setStyle(Paint.Style.FILL);
        if (this.mCanvas != null) {
            this.mCanvas.drawArc(new RectF((float) x, (float) y, (float) (x + width), (float) (y + height)), (float) startAngle, (float) arcAngle, false, this.mPaint);
        }
    }

    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        this.mPaint.setStyle(Paint.Style.STROKE);
        if (this.mCanvas != null) {
            this.mCanvas.drawRoundRect(new RectF((float) x, (float) y, (float) (x + width), (float) (y + height)), (float) arcWidth, (float) arcHeight, this.mPaint);
        }
    }

    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        this.mPaint.setStyle(Paint.Style.FILL);
        if (this.mCanvas != null) {
            this.mCanvas.drawRoundRect(new RectF((float) x, (float) y, (float) (x + width), (float) (y + height)), (float) arcWidth, (float) arcHeight, this.mPaint);
        }
    }

    public void drawRegion(Image image, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) {
        this.mMatrix.reset();
        int drawWidth = width;
        int drawHeight = height;
        int xOffset = 0;
        int yOffset = 0;
        switch (transform) {
            case 0:
                xOffset = -x_src;
                yOffset = -y_src;
                break;
            case 1:
                this.mMatrix.preScale(-1.0f, 1.0f);
                this.mMatrix.preRotate(-180.0f);
                xOffset = -x_src;
                yOffset = drawHeight + y_src;
                break;
            case 2:
                this.mMatrix.preScale(-1.0f, 1.0f);
                xOffset = drawWidth + x_src;
                yOffset = -y_src;
                break;
            case 3:
                this.mMatrix.preRotate(180.0f);
                xOffset = drawWidth + x_src;
                yOffset = drawHeight + y_src;
                break;
            case 4:
                this.mMatrix.preScale(-1.0f, 1.0f);
                this.mMatrix.preRotate(-270.0f);
                drawWidth = height;
                drawHeight = width;
                xOffset = -y_src;
                yOffset = -x_src;
                break;
            case 5:
                this.mMatrix.preRotate(90.0f);
                drawWidth = height;
                drawHeight = width;
                xOffset = drawWidth + y_src;
                yOffset = -x_src;
                break;
            case 6:
                this.mMatrix.preRotate(270.0f);
                drawWidth = height;
                drawHeight = width;
                xOffset = -y_src;
                yOffset = drawHeight + x_src;
                break;
            case 7:
                this.mMatrix.preScale(-1.0f, 1.0f);
                this.mMatrix.preRotate(-90.0f);
                drawWidth = height;
                drawHeight = width;
                xOffset = drawWidth + y_src;
                yOffset = drawHeight + x_src;
                break;
        }
        if (anchor == 0) {
            anchor = 20;
        }
        if ((anchor & 32) != 0) {
            y_dest -= drawHeight;
        } else if ((anchor & 2) != 0) {
            y_dest -= drawHeight >>> 1;
        }
        if ((anchor & 8) != 0) {
            x_dest -= drawWidth;
        } else if ((anchor & 1) != 0) {
            x_dest -= drawWidth >>> 1;
        }
        this.mCanvas.save();
        this.mCanvas.clipRect(x_dest, y_dest, x_dest + drawWidth, y_dest + drawHeight);
        this.mCanvas.translate((float) (x_dest + xOffset), (float) (y_dest + yOffset));
        int alpha = this.mPaint.getAlpha();
        if (image.getAlpha() != -1) {
            this.mPaint.setAlpha(image.getAlpha());
        }
        this.mCanvas.drawBitmap(image.getBitmap(), this.mMatrix, this.mPaint);
        this.mPaint.setAlpha(alpha);
        this.mCanvas.restore();
    }

    public void drawRegion(Image image, int x_src, int y_src, int width, int height, int rotate_x, int rotate_y, int degree, int scale_x, int scale_y, int x_dest, int y_dest, int anchor) {
        this.mMatrix.reset();
        this.mCanvas.save();
        if ((anchor & 32) != 0) {
            y_dest -= height;
        } else if ((anchor & 2) != 0) {
            y_dest -= height >> 1;
        }
        if ((anchor & 8) != 0) {
            x_dest -= width;
        } else if ((anchor & 1) != 0) {
            x_dest -= width >> 1;
        }
        this.mCanvas.translate((float) (x_dest - x_src), (float) (y_dest - y_src));
        this.mCanvas.rotate((float) degree, (float) (rotate_x + x_src), (float) (rotate_y + y_src));
        this.mCanvas.scale((float) scale_x, (float) scale_y, (float) ((width / 2) + x_src), (float) ((height / 2) + y_src));
        this.mCanvas.clipRect(x_src, y_src, x_src + width, y_src + height);
        int alpha = this.mPaint.getAlpha();
        if (image.getAlpha() != -1) {
            this.mPaint.setAlpha(image.getAlpha());
        }
        this.mCanvas.drawBitmap(image.getBitmap(), this.mMatrix, this.mPaint);
        this.mPaint.setAlpha(alpha);
        this.mCanvas.restore();
    }
}
