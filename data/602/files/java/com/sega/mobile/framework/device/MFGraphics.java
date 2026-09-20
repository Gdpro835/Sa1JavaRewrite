package com.sega.mobile.framework.device;

import com.sega.mobile.framework.android.Font;
import com.sega.mobile.framework.android.Graphics;
import com.sega.mobile.framework.utility.MFUtility;

public class MFGraphics {
    public static final int BOTTOM = 32;
    public static final int FONT_LARGE = -3;
    public static final int FONT_MEDIUM = -2;
    public static final int FONT_SMALL = -1;
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
    private static Font currentFont;
    private static int drawLineD;
    private static int drawLineDX;
    private static int drawLineDY;
    private static int drawLineIncrE;
    private static int drawLineIncrNE;
    private static boolean drawLineMFlag;
    private static int drawLineStepX;
    private static int drawLineStepY;
    private static int drawLineX;
    private static int drawLineY;
    private static Font font_large;
    private static Font font_medium;
    private static Font font_small;
    private static int font_type;
    private int alphaValue;
    private int blueValue;
    private int clipHeight;
    private int clipWidth;
    private int clipX;
    private int clipY;
    private int[] drawEffectRGB;
    private boolean effectFlag;
    private boolean enableExceed = false;
    int[] fillRectRGB = new int[100];
    protected Graphics g;
    private int graphicsHeight;
    private int graphicsWidth;
    private int grayValue;
    private int greenValue;
    private boolean isFontGraphics = false;
    private int[] pixelInt;
    private int redValue;
    private int transX;
    private int transY;
    private int xOff = 0;
    private int yOff = 0;

    private MFGraphics() {
    }
    
    public Graphics getg() {
    return g;
    }

    protected static final void init() {
        if (font_small == null) {
            font_small = Font.getFont(0, 0, 8);
            font_medium = Font.getFont(0, 0, 0);
            font_large = Font.getFont(0, 0, 16);
            font_type = 8;
            currentFont = font_small;
        }
    }

    public void saveCanvas() {
        this.g.save();
    }

    public void restoreCanvas() {
        this.g.restore();
    }

    public void clipCanvas(int left, int top, int right, int bottom) {
        if (MFDevice.preScaleZoomOutFlag) {
            left >>= MFDevice.preScaleShift;
            top >>= MFDevice.preScaleShift;
            right >>= MFDevice.preScaleShift;
            bottom >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            left <<= MFDevice.preScaleShift;
            top <<= MFDevice.preScaleShift;
            right <<= MFDevice.preScaleShift;
            bottom <<= MFDevice.preScaleShift;
        }
        this.g.clipRect(left, top, right, bottom);
    }

    public void rotateCanvas(float degrees) {
        this.g.rotate(degrees);
    }

    public void rotateCanvas(float degrees, int px, int py) {
        if (MFDevice.preScaleZoomOutFlag) {
            px >>= MFDevice.preScaleShift;
            py >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            px <<= MFDevice.preScaleShift;
            py <<= MFDevice.preScaleShift;
        }
        this.g.rotate(degrees, (float) px, (float) py);
    }

    public void scaleCanvas(float sx, float sy) {
        this.g.scale(sx, sy);
    }

    public void scaleCanvas(float sx, float sy, int px, int py) {
        if (MFDevice.preScaleZoomOutFlag) {
            px >>= MFDevice.preScaleShift;
            py >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            px <<= MFDevice.preScaleShift;
            py <<= MFDevice.preScaleShift;
        }
        this.g.scale(sx, sy, (float) px, (float) py);
    }

    public void translateCanvas(int dx, int dy) {
        if (MFDevice.preScaleZoomOutFlag) {
            dx >>= MFDevice.preScaleShift;
            dy >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            dx <<= MFDevice.preScaleShift;
            dy <<= MFDevice.preScaleShift;
        }
        this.g.translate((float) dx, (float) dy);
    }

    public static final MFGraphics createMFGraphics(Object graphics, int width, int height) {
        if (MFDevice.preScaleZoomOutFlag) {
            width >>= MFDevice.preScaleShift;
            height >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            width <<= MFDevice.preScaleShift;
            height <<= MFDevice.preScaleShift;
        }
        MFGraphics ret = new MFGraphics();
        ret.setGraphics(graphics, width, height);
        ret.disableEffect();
        return ret;
    }

    public static final MFGraphics createMFFontGraphics(Object graphics, int width, int height) {
        MFGraphics ret = createMFGraphics(graphics, width, height);
        ret.isFontGraphics = true;
        return ret;
    }

    public final Object getSystemGraphics() {
        return this.g;
    }

    public final void setGraphics(Object graphics) {
        this.g = (Graphics) graphics;
        this.g.setFont(Font.getFont(0, 0, 8));
    }

    public final void setGraphics(Object graphics, int width, int height) {
        this.graphicsWidth = width;
        this.graphicsHeight = height;
        if (MFDevice.preScaleZoomOutFlag) {
            int width2 = width >> MFDevice.preScaleShift;
            int height2 = height >> MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            int width3 = width << MFDevice.preScaleShift;
            int height3 = height << MFDevice.preScaleShift;
        }
        reset();
        setGraphics(graphics);
    }

    public final void reset() {
        int screenWidth = this.graphicsWidth;
        int screenHeight = this.graphicsHeight;
        if (MFDevice.preScaleZoomOutFlag) {
            screenWidth >>= MFDevice.preScaleShift;
            screenHeight >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            screenWidth <<= MFDevice.preScaleShift;
            screenHeight <<= MFDevice.preScaleShift;
        }
        this.transX = 0;
        this.transY = 0;
        this.clipX = 0;
        this.clipY = 0;
        this.clipWidth = screenWidth;
        this.clipHeight = screenHeight;
    }

    public final void translate(int x, int y) {
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
        }
        this.transX += x;
        this.transY += y;
        this.clipX += x;
        this.clipY += y;
    }

    public final void setTranslate(int x, int y) {
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
        }
        translate(x - this.transX, y - this.transY);
    }

    public final int getTranslateX() {
        if (MFDevice.preScaleZoomOutFlag) {
            return this.transX << MFDevice.preScaleShift;
        }
        if (MFDevice.preScaleZoomInFlag) {
            return this.transX >> MFDevice.preScaleShift;
        }
        return this.transX;
    }

    public final int getTranslateY() {
        if (MFDevice.preScaleZoomOutFlag) {
            return this.transY << MFDevice.preScaleShift;
        }
        if (MFDevice.preScaleZoomInFlag) {
            return this.transY >> MFDevice.preScaleShift;
        }
        return this.transY;
    }

    public final void setClip(int x, int y, int width, int height) {
        int tx;
        int ty;
        if (MFDevice.useClearFont && !this.isFontGraphics) {
            MFDevice.fontGraphics.setClip((int) (((float) (MFDevice.horizontalOffset + x)) * MFDevice.scaleFactor), (int) (((float) (MFDevice.verticvalOffset + y)) * MFDevice.scaleFactor), (int) (((float) width) * MFDevice.scaleFactor), (int) (((float) height) * MFDevice.scaleFactor));
        }
        int screenWidth = this.graphicsWidth;
        int screenHeight = this.graphicsHeight;
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            width >>= MFDevice.preScaleShift;
            height >>= MFDevice.preScaleShift;
            screenWidth >>= MFDevice.preScaleShift;
            screenHeight >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            width <<= MFDevice.preScaleShift;
            height <<= MFDevice.preScaleShift;
            screenWidth <<= MFDevice.preScaleShift;
            screenHeight <<= MFDevice.preScaleShift;
        }
        this.clipX = this.transX + x;
        this.clipY = this.transY + y;
        this.clipWidth = width;
        this.clipHeight = height;
        if (!this.enableExceed) {
            int cx = this.clipX < 0 ? 0 : this.clipX;
            int cy = this.clipY < 0 ? 0 : this.clipY;
            if (this.clipX + width > screenWidth) {
                tx = screenWidth;
            } else {
                tx = this.clipX + width;
            }
            if (this.clipY + height > screenHeight) {
                ty = screenHeight;
            } else {
                ty = this.clipY + height;
            }
            this.g.setClip(cx, cy, tx - cx, ty - cy);
            return;
        }
        this.g.setClip(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
    }

    public final int getClipX() {
        if (MFDevice.preScaleZoomOutFlag) {
            return (this.clipX - this.transX) << MFDevice.preScaleShift;
        }
        if (MFDevice.preScaleZoomInFlag) {
            return (this.clipX - this.transX) >> MFDevice.preScaleShift;
        }
        return this.clipX - this.transX;
    }

    public final int getClipY() {
        if (MFDevice.preScaleZoomOutFlag) {
            return (this.clipY - this.transY) << MFDevice.preScaleShift;
        }
        if (MFDevice.preScaleZoomInFlag) {
            return (this.clipY - this.transY) >> MFDevice.preScaleShift;
        }
        return this.clipY - this.transY;
    }

    public final int getClipWidth() {
        if (MFDevice.preScaleZoomOutFlag) {
            return this.clipWidth << MFDevice.preScaleShift;
        }
        if (MFDevice.preScaleZoomInFlag) {
            return this.clipWidth >> MFDevice.preScaleShift;
        }
        return this.clipWidth;
    }

    public final int getClipHeight() {
        if (MFDevice.preScaleZoomOutFlag) {
            return this.clipHeight << MFDevice.preScaleShift;
        }
        if (MFDevice.preScaleZoomInFlag) {
            return this.clipHeight >> MFDevice.preScaleShift;
        }
        return this.clipHeight;
    }

    public final void drawImage(MFImage image, int x, int y, int anchor) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            imageWidth >>= MFDevice.preScaleShift;
            imageHeight >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            imageWidth <<= MFDevice.preScaleShift;
            imageHeight <<= MFDevice.preScaleShift;
        }
        caculateAnchorOffset(imageWidth, imageHeight, anchor);
        drawImageImpl(image, this.xOff + x, this.yOff + y);
    }

    public final void drawImage(MFImage image, int x, int y) {
        drawImage(image, x, y, 20);
    }

    public final void drawImage(MFImage image, int x, int y, int flipMode, int anchor) {
        drawRegion(image, 0, 0, image.getWidth(), image.getHeight(), flipMode, x, y, anchor);
    }

    public final void drawImage(MFImage image, int x, int y, int regionX, int regionY, int regionW, int regionH) {
        drawRegion(image, regionX, regionY, regionW, regionH, 0, x, y, 20);
    }

    public final void drawRegion(MFImage image, int regionX, int regionY, int regionW, int regionH, int flipMode, int x, int y, int anchor) {
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            regionX >>= MFDevice.preScaleShift;
            regionY >>= MFDevice.preScaleShift;
            regionW >>= MFDevice.preScaleShift;
            regionH >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            regionX <<= MFDevice.preScaleShift;
            regionY <<= MFDevice.preScaleShift;
            regionW <<= MFDevice.preScaleShift;
            regionH <<= MFDevice.preScaleShift;
        }
        if (flipMode <= 3) {
            caculateAnchorOffset(regionW, regionH, anchor);
        } else {
            caculateAnchorOffset(regionH, regionW, anchor);
        }
        drawRegionImpl(image, regionX, regionY, regionW, regionH, flipMode, x + this.xOff, y + this.yOff);
    }

    private final void caculateAnchorOffset(int width, int height, int anchor) {
        this.xOff = 0;
        this.yOff = 0;
        if ((anchor & 1) != 0) {
            this.xOff -= width >> 1;
        } else if ((anchor & 8) != 0) {
            this.xOff -= width;
        }
        if ((anchor & 2) != 0) {
            this.yOff -= height >> 1;
        } else if ((anchor & 32) != 0) {
            this.yOff -= height;
        }
    }

    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) {
        if (this.effectFlag) {
            if (this.alphaValue != 0) {
                if (!(this.redValue == 0 && this.greenValue == 0 && this.blueValue == 0)) {
                    for (int i = 0; i < rgbData.length; i++) {
                        rgbData[i] = caculateColorValue(rgbData[i]);
                    }
                }
                if (this.grayValue != 0) {
                    for (int i2 = 0; i2 < rgbData.length; i2++) {
                        rgbData[i2] = caculateGray(rgbData[i2]);
                    }
                }
                if (this.alphaValue != 255) {
                    for (int i3 = 0; i3 < rgbData.length; i3++) {
                        rgbData[i3] = caculateAlpha(rgbData[i3]);
                    }
                }
            } else {
                return;
            }
        }
        int[] drawRGB = rgbData;
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            width >>= MFDevice.preScaleShift;
            height >>= MFDevice.preScaleShift;
            drawRGB = new int[(width * height)];
            for (int i4 = 0; i4 < width; i4++) {
                for (int j = 0; j < height; j++) {
                    drawRGB[(j * width) + i4] = rgbData[(i4 << MFDevice.preScaleShift) + ((j << MFDevice.preScaleShift) * (width << MFDevice.preScaleShift))];
                }
            }
            offset = 0;
            scanlength = width;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            width <<= MFDevice.preScaleShift;
            height <<= MFDevice.preScaleShift;
            drawRGB = new int[(width * height)];
            for (int i5 = 0; i5 < width; i5++) {
                for (int j2 = 0; j2 < height; j2++) {
                    drawRGB[(j2 * width) + i5] = rgbData[(i5 >> MFDevice.preScaleShift) + ((j2 >> MFDevice.preScaleShift) * (width >> MFDevice.preScaleShift))];
                }
            }
            offset = 0;
            scanlength = width;
        }
        if (this.effectFlag) {
            drawRGBFlip(drawRGB, x + this.transX, y + this.transY, width, height, 0);
            return;
        }
        drawRGBImpl(drawRGB, offset, scanlength, x + this.transX, y + this.transY, width, height, processAlpha);
    }

    private void drawRGBImpl(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) {
        this.g.drawRGB(rgbData, offset, scanlength, x, y, width, height, processAlpha);
    }

    public void setColor(int color) {
        if (MFDevice.useClearFont && !this.isFontGraphics) {
            MFDevice.fontGraphics.setColor(color);
        }
        this.g.setColor(color);
    }

    public int getColor() {
        return this.g.getColor();
    }

    public void setColor(int red, int green, int blue) {
        if (MFDevice.useClearFont && !this.isFontGraphics) {
            MFDevice.fontGraphics.setColor(red, green, blue);
        }
        this.g.setColor((red << 16) | (green << 8) | blue);
    }

    public final void drawPixel(int x, int y) {
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
        }
        drawPixelImpl(this.transX + x, this.transY + y);
    }

    private final void drawPixelImpl(int x, int y) {
        if (this.effectFlag) {
            this.pixelInt[0] = this.g.getColor() | -16777216;
            if (!(this.alphaValue == 0 || (this.redValue == 0 && this.greenValue == 0 && this.blueValue == 0))) {
                this.pixelInt[0] = caculateColorValue(this.pixelInt[0]);
            }
            if (!(this.alphaValue == 0 || this.grayValue == 0)) {
                this.pixelInt[0] = caculateGray(this.pixelInt[0]);
            }
            if (!(this.alphaValue == 255 || this.alphaValue == 0)) {
                this.pixelInt[0] = caculateAlpha(this.pixelInt[0]);
            }
            if (this.alphaValue != 0) {
                this.g.drawRGB(this.pixelInt, 0, 1, x, y, 1, 1, true);
                return;
            }
            return;
        }
        this.g.drawLine(x, y, x, y);
    }

    public final void drawLine(int x1, int y1, int x2, int y2) {
        if (MFDevice.preScaleZoomOutFlag) {
            x1 >>= MFDevice.preScaleShift;
            y1 >>= MFDevice.preScaleShift;
            x2 >>= MFDevice.preScaleShift;
            y2 >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x1 <<= MFDevice.preScaleShift;
            y1 <<= MFDevice.preScaleShift;
            x2 <<= MFDevice.preScaleShift;
            y2 <<= MFDevice.preScaleShift;
        }
        if (this.effectFlag) {
            drawLinePerPixel(this.transX + x1, this.transY + y1, this.transX + x2, this.transY + y2);
        } else {
            this.g.drawLine(this.transX + x1, this.transY + y1, this.transX + x2, this.transY + y2);
        }
    }

    private final void drawLinePerPixel(int x1, int y1, int x2, int y2) {
        int i;
        drawLineStepX = x1 < x2 ? 1 : -1;
        if (y1 < y2) {
            i = 1;
        } else {
            i = -1;
        }
        drawLineStepY = i;
        drawLineDX = Math.abs(x2 - x1);
        drawLineDY = Math.abs(y2 - y1);
        drawLineMFlag = drawLineDX - drawLineDY > 0;
        if (drawLineMFlag) {
            drawLineD = drawLineDX - (drawLineDY << 1);
            drawLineIncrE = -(drawLineDY << 1);
            drawLineIncrNE = (drawLineDX - drawLineDY) << 1;
        } else {
            drawLineD = drawLineDY - (drawLineDX << 1);
            drawLineIncrE = -(drawLineDX << 1);
            drawLineIncrNE = (drawLineDY - drawLineDX) << 1;
        }
        drawLineX = x1;
        drawLineY = y1;
        drawPixelImpl(drawLineX, drawLineY);
        if (drawLineMFlag) {
            while (drawLineX != x2) {
                if (drawLineD > 0) {
                    drawLineD += drawLineIncrE;
                    drawLineX += drawLineStepX;
                } else {
                    drawLineD += drawLineIncrNE;
                    drawLineX += drawLineStepX;
                    drawLineY += drawLineStepY;
                }
                drawPixelImpl(drawLineX, drawLineY);
            }
            return;
        }
        while (drawLineY != y2) {
            if (drawLineD > 0) {
                drawLineD += drawLineIncrE;
                drawLineY += drawLineStepY;
            } else {
                drawLineD += drawLineIncrNE;
                drawLineX += drawLineStepX;
                drawLineY += drawLineStepY;
            }
            drawPixelImpl(drawLineX, drawLineY);
        }
    }

    public final void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        drawLine(x1, y1, x2, y2);
        drawLine(x1, y1, x3, y3);
        drawLine(x2, y2, x3, y3);
    }

    public final void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        if (MFDevice.preScaleZoomOutFlag) {
            x1 >>= MFDevice.preScaleShift;
            y1 >>= MFDevice.preScaleShift;
            x2 >>= MFDevice.preScaleShift;
            y2 >>= MFDevice.preScaleShift;
            x3 >>= MFDevice.preScaleShift;
            y3 >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x1 <<= MFDevice.preScaleShift;
            y1 <<= MFDevice.preScaleShift;
            x2 <<= MFDevice.preScaleShift;
            y2 <<= MFDevice.preScaleShift;
            x3 <<= MFDevice.preScaleShift;
            y3 <<= MFDevice.preScaleShift;
        }
        this.g.fillTriangle(this.transX + x1, this.transY + y1, this.transX + x2, this.transY + y2, this.transX + x3, this.transY + y3);
    }

    public final void drawRect(int x, int y, int w, int h) {
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            w >>= MFDevice.preScaleShift;
            h >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            w <<= MFDevice.preScaleShift;
            h <<= MFDevice.preScaleShift;
        }
        if (w >= 0 && h >= 0) {
            if (this.effectFlag) {
                fillRect(x, y, w, 1);
                fillRect(x, y, 1, h);
                fillRect(x + w, y, 1, h);
                fillRect(x, y + h, w, 1);
                return;
            }
            this.g.drawRect(this.transX + x, this.transY + y, w, h);
        }
    }

    public final void fillRect(int x, int y, int w, int h) {
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            w >>= MFDevice.preScaleShift;
            h >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            w <<= MFDevice.preScaleShift;
            h <<= MFDevice.preScaleShift;
        }
        if (this.effectFlag) {
            this.fillRectRGB[0] = this.g.getColor() | -16777216;
            if (!(this.alphaValue == 0 || (this.redValue == 0 && this.greenValue == 0 && this.blueValue == 0))) {
                this.fillRectRGB[0] = caculateColorValue(this.fillRectRGB[0]);
            }
            if (!(this.alphaValue == 0 || this.grayValue == 0)) {
                this.fillRectRGB[0] = caculateGray(this.fillRectRGB[0]);
            }
            if (!(this.alphaValue == 255 || this.alphaValue == 0)) {
                this.fillRectRGB[0] = caculateAlpha(this.fillRectRGB[0]);
            }
            if (this.alphaValue != 0) {
                for (int i = 0; i < this.fillRectRGB.length; i++) {
                    this.fillRectRGB[i] = this.fillRectRGB[0];
                }
                int x1 = this.transX + x < this.clipX ? this.clipX : this.transX + x;
                int y1 = this.transY + y < this.clipY ? this.clipY : this.transY + y;
                this.g.setClip(x1, y1, ((this.transX + x) + w) - x1, ((this.transY + y) + h) - y1);
                for (int i2 = 0; i2 < (w / 10) + 1; i2++) {
                    for (int j = 0; j < (h / 10) + 1; j++) {
                        this.g.drawRGB(this.fillRectRGB, 0, 10, this.transX + x + (i2 * 10), this.transY + y + (j * 10), 10, 10, true);
                    }
                }
                this.g.setClip(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
                return;
            }
            return;
        }
        this.g.fillRect(this.transX + x, this.transY + y, w, h);
    }

    public final void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            width >>= MFDevice.preScaleShift;
            height >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            width <<= MFDevice.preScaleShift;
            height <<= MFDevice.preScaleShift;
        }
        this.g.drawArc(this.transX + x, this.transY + y, width, height, startAngle, arcAngle);
    }

    public final void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            width >>= MFDevice.preScaleShift;
            height >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            width <<= MFDevice.preScaleShift;
            height <<= MFDevice.preScaleShift;
        }
        this.g.fillArc(this.transX + x, this.transY + y, width, height, startAngle, arcAngle);
    }

    public final void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            width >>= MFDevice.preScaleShift;
            height >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            width <<= MFDevice.preScaleShift;
            height <<= MFDevice.preScaleShift;
        }
        this.g.drawRoundRect(this.transX + x, this.transY + y, width, height, arcWidth, arcWidth);
    }

    public final void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        if (MFDevice.preScaleZoomOutFlag) {
            x >>= MFDevice.preScaleShift;
            y >>= MFDevice.preScaleShift;
            width >>= MFDevice.preScaleShift;
            height >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x <<= MFDevice.preScaleShift;
            y <<= MFDevice.preScaleShift;
            width <<= MFDevice.preScaleShift;
            height <<= MFDevice.preScaleShift;
        }
        this.g.fillRoundRect(this.transX + x, this.transY + y, width, height, arcWidth, arcWidth);
    }

    private static final Font getFont(int type) {
        switch (type) {
            case FONT_LARGE /*-3*/:
                return font_large;
            case -2:
                return font_medium;
            case -1:
                return font_small;
            default:
                return Font.getFont(type);
        }
    }

    public final void setFont(int font) {
        if (MFDevice.useClearFont && !this.isFontGraphics) {
            MFDevice.fontGraphics.setFont(font);
        }
        if (MFDevice.preScaleZoomOutFlag) {
            font >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            font <<= MFDevice.preScaleShift;
        }
        if (font_type != font) {
            currentFont = getFont(font);
            if (currentFont != null) {
                font_type = font;
            }
        }
        this.g.setFont(currentFont);
    }

    public final int getFont() {
        if (MFDevice.preScaleZoomOutFlag) {
            return font_type << MFDevice.preScaleShift;
        }
        if (MFDevice.preScaleZoomInFlag) {
            return font_type >> MFDevice.preScaleShift;
        }
        return font_type;
    }

    public final void drawString(String str, int x, int y, int anchor) {
        if (!MFDevice.useClearFont || this.isFontGraphics) {
            int stringWidth = stringWidth(str);
            int charHeight = charHeight();
            if (MFDevice.preScaleZoomOutFlag) {
                stringWidth >>= MFDevice.preScaleShift;
                charHeight >>= MFDevice.preScaleShift;
                x >>= MFDevice.preScaleShift;
                y >>= MFDevice.preScaleShift;
            } else if (MFDevice.preScaleZoomInFlag) {
                stringWidth <<= MFDevice.preScaleShift;
                charHeight <<= MFDevice.preScaleShift;
                x <<= MFDevice.preScaleShift;
                y <<= MFDevice.preScaleShift;
            }
            caculateAnchorOffset(stringWidth, charHeight, anchor);
            this.g.drawString(str, this.xOff + x + this.transX, this.yOff + y + this.transY, 20);
            return;
        }
        MFDevice.fontGraphics.saveCanvas();
        MFDevice.fontGraphics.scaleCanvas(MFDevice.scaleFactor, MFDevice.scaleFactor, MFDevice.drawRect.left, MFDevice.drawRect.top);
        MFDevice.fontGraphics.drawString(str, MFDevice.drawRect.left + x, MFDevice.drawRect.top + y, anchor);
        MFDevice.fontGraphics.restoreCanvas();
    }

    public final void drawSubstring(String str, int offset, int len, int x, int y, int anchor) {
        drawString(str.substring(offset, len), x, y, anchor);
    }

    public static final int charHeight(int font) {
        int ret = font;
        switch (font) {
            case FONT_LARGE:
                ret = 30;
                break;
        }
        if (MFDevice.preScaleZoomOutFlag) {
            return ret << MFDevice.preScaleShift;
        }
        return MFDevice.preScaleZoomInFlag ? ret >> MFDevice.preScaleShift : ret;
    }
    
    public static final int stringWidth(int font, String str) {
        if (MFDevice.preScaleZoomOutFlag) {
            if (font_type == font) {
                return currentFont.stringWidth(str) << MFDevice.preScaleShift;
            }
            return getFont(font).stringWidth(str) << MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            if (font_type == font) {
                return currentFont.stringWidth(str) >> MFDevice.preScaleShift;
            }
            return getFont(font).stringWidth(str) >> MFDevice.preScaleShift;
        } else if (font_type == font) {
            return currentFont.stringWidth(str);
        } else {
            return getFont(font).stringWidth(str);
        }
    }

    public final int charHeight() {
        return charHeight(font_type);
    }

    public final int stringWidth(String str) {
        return stringWidth(font_type, str);
    }

    public final void enableEffect() {
        this.pixelInt = new int[1];
        this.redValue = 0;
        this.greenValue = 0;
        this.blueValue = 0;
        this.alphaValue = 255;
        this.grayValue = 0;
        this.effectFlag = true;
    }

    public final void disableEffect() {
        this.effectFlag = false;
    }

    public final void enableExceedBoundary() {
        this.enableExceed = true;
        this.g.setClip(-MFDevice.horizontalOffset, -MFDevice.verticvalOffset, MFDevice.bufferWidth, MFDevice.bufferHeight);
    }

    public final boolean getExceedBoundaryFlag() {
        return this.enableExceed;
    }

    public final boolean getEffectFlag() {
        return this.effectFlag;
    }

    public final int getNativeCanvasLeft() {
        return -MFDevice.horizontalOffset;
    }

    public final int getNativeCanvasTop() {
        return -MFDevice.verticvalOffset;
    }

    public final int getNativeCanvasRight() {
        return MFDevice.bufferWidth - MFDevice.horizontalOffset;
    }

    public final int getNativeCanvasBottom() {
        return MFDevice.bufferHeight - MFDevice.verticvalOffset;
    }

    public final int getNativeCanvasWidth() {
        return MFDevice.bufferWidth;
    }

    public final int getNativeCanvasHeight() {
        return MFDevice.bufferHeight;
    }

    public final void disableExceedBoundary() {
        this.enableExceed = false;
        if (MFDevice.preScaleZoomOutFlag) {
            this.g.setClip(0, 0, (MFDevice.bufferWidth - (MFDevice.horizontalOffset << 1)) << MFDevice.preScaleShift, (MFDevice.bufferHeight - (MFDevice.verticvalOffset << 1)) << MFDevice.preScaleShift);
        } else if (MFDevice.preScaleZoomInFlag) {
            this.g.setClip(0, 0, (MFDevice.bufferWidth - (MFDevice.horizontalOffset << 1)) >> MFDevice.preScaleShift, (MFDevice.bufferHeight - (MFDevice.verticvalOffset << 1)) >> MFDevice.preScaleShift);
        }
        this.g.setClip(0, 0, MFDevice.bufferWidth - (MFDevice.horizontalOffset << 1), MFDevice.bufferHeight - (MFDevice.verticvalOffset << 1));
    }

    public final void clearScreen(int color) {
        this.g.setColor(color);
        enableExceedBoundary();
        this.g.fillRect(-MFDevice.horizontalOffset, -MFDevice.verticvalOffset, MFDevice.bufferWidth, MFDevice.bufferHeight);
        disableExceedBoundary();
    }

    public final void setHue(int rValue, int gValue, int bValue) {
        this.redValue = caculateValue(rValue);
        this.greenValue = caculateValue(gValue);
        this.blueValue = caculateValue(bValue);
    }

    public final void setAlpha(int alpha) {
        this.alphaValue = alpha;
        if (this.alphaValue == -1) {
            this.alphaValue = 255;
        }
        if (this.alphaValue < 0) {
            this.alphaValue = 0;
        }
        if (this.alphaValue > 255) {
            this.alphaValue = 255;
        }
        this.g.setAlpha(this.alphaValue);
    }

    public final int getAlpha() {
        return this.g.getAlpha();
    }

    public final void setGray(int gValue) {
        this.grayValue = gValue;
        if (this.grayValue < 0) {
            this.grayValue = 0;
        }
        if (this.grayValue > 255) {
            this.grayValue = 255;
        }
    }

    private final int caculateValue(int value) {
        if (value < 0) {
            if (value > -255) {
                return value;
            }
            return -255;
        } else if (value == 0) {
            return 0;
        } else {
            if (value > 255) {
                return 255;
            }
            return (65536 / (256 - value)) - 256;
        }
    }

    private final void drawImageImpl(MFImage image, int x, int y) {
        if (this.effectFlag) {
            this.drawEffectRGB = null;
            this.drawEffectRGB = new int[(image.getWidth() * image.getHeight())];
            image.getRGB(this.drawEffectRGB, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            if (!(this.alphaValue == 0 || (this.redValue == 0 && this.greenValue == 0 && this.blueValue == 0))) {
                for (int i = 0; i < this.drawEffectRGB.length; i++) {
                    this.drawEffectRGB[i] = caculateColorValue(this.drawEffectRGB[i]);
                }
            }
            if (!(this.alphaValue == 0 || this.grayValue == 0)) {
                for (int i2 = 0; i2 < this.drawEffectRGB.length; i2++) {
                    this.drawEffectRGB[i2] = caculateGray(this.drawEffectRGB[i2]);
                }
            }
            if (!(this.alphaValue == 255 || this.alphaValue == 0)) {
                for (int i3 = 0; i3 < this.drawEffectRGB.length; i3++) {
                    this.drawEffectRGB[i3] = caculateAlpha(this.drawEffectRGB[i3]);
                }
            }
            if (this.alphaValue != 0) {
                drawRGBFlip(this.drawEffectRGB, x + this.transX, y + this.transY, image.getWidth(), image.getHeight(), 0);
                return;
            }
            return;
        }
        this.g.drawImage(image.image, this.transX + x, this.transY + y, 20);
    }

    private final void drawRegionImpl(MFImage image, int regionX, int regionY, int regionW, int regionH, int flipMode, int x, int y) {
        if (!this.effectFlag) {
            this.g.drawRegion(image.image, regionX, regionY, regionW, regionH, flipMode, x + this.transX, y + this.transY, 20);
        } else if (this.alphaValue != 0) {
            this.drawEffectRGB = new int[(regionW * regionH)];
            image.getRGB(this.drawEffectRGB, 0, regionW, regionX, regionY, regionW, regionH);
            if (!(this.redValue == 0 && this.greenValue == 0 && this.blueValue == 0)) {
                for (int i = 0; i < this.drawEffectRGB.length; i++) {
                    this.drawEffectRGB[i] = caculateColorValue(this.drawEffectRGB[i]);
                }
            }
            if (this.grayValue != 0) {
                for (int i2 = 0; i2 < this.drawEffectRGB.length; i2++) {
                    this.drawEffectRGB[i2] = caculateGray(this.drawEffectRGB[i2]);
                }
            }
            if (this.alphaValue != 255) {
                for (int i3 = 0; i3 < this.drawEffectRGB.length; i3++) {
                    this.drawEffectRGB[i3] = caculateAlpha(this.drawEffectRGB[i3]);
                }
            }
            drawRGBFlip(this.drawEffectRGB, x + this.transX, y + this.transY, regionW, regionH, flipMode);
            this.drawEffectRGB = null;
        }
    }

    private final void drawRGBFlip(int[] argb, int x, int y, int width, int height, int flipMode) {
        int[] rgbData = new int[width];
        switch (flipMode) {
            case 0:
                for (int i = 0; i < height; i++) {
                    System.arraycopy(argb, i * width, rgbData, 0, width);
                    this.g.drawRGB(rgbData, 0, width, x, y + i, width, 1, true);
                }
                return;
            case 1:
                for (int i2 = 0; i2 < height; i2++) {
                    System.arraycopy(argb, i2 * width, rgbData, 0, width);
                    this.g.drawRGB(rgbData, 0, width, x, ((y + height) - 1) - i2, width, 1, true);
                }
                return;
            case 2:
                for (int i3 = 0; i3 < height; i3++) {
                    System.arraycopy(argb, i3 * width, rgbData, 0, width);
                    MFUtility.revertArray(rgbData);
                    this.g.drawRGB(rgbData, 0, width, x, y + i3, width, 1, true);
                }
                return;
            case 3:
                for (int i4 = 0; i4 < height; i4++) {
                    System.arraycopy(argb, i4 * width, rgbData, 0, width);
                    MFUtility.revertArray(rgbData);
                    this.g.drawRGB(rgbData, 0, width, x, ((y + height) - 1) - i4, width, 1, true);
                }
                return;
            case 4:
                for (int i5 = 0; i5 < height; i5++) {
                    System.arraycopy(argb, i5 * width, rgbData, 0, width);
                    this.g.drawRGB(rgbData, 0, 1, x + i5, y, 1, width, true);
                }
                return;
            case 5:
                for (int i6 = 0; i6 < height; i6++) {
                    System.arraycopy(argb, i6 * width, rgbData, 0, width);
                    this.g.drawRGB(rgbData, 0, 1, ((x + height) - 1) - i6, y, 1, width, true);
                }
                return;
            case 6:
                for (int i7 = 0; i7 < height; i7++) {
                    System.arraycopy(argb, i7 * width, rgbData, 0, width);
                    MFUtility.revertArray(rgbData);
                    this.g.drawRGB(rgbData, 0, 1, x + i7, y, 1, width, true);
                }
                return;
            case 7:
                for (int i8 = 0; i8 < height; i8++) {
                    System.arraycopy(argb, i8 * width, rgbData, 0, width);
                    MFUtility.revertArray(rgbData);
                    this.g.drawRGB(rgbData, 0, 1, ((x + height) - 1) - i8, y, 1, width, true);
                }
                return;
            default:
                return;
        }
    }

    private final int caculateColorValue(int argb) {
        return (-16777216 & argb) | (caculatePerColor((16711680 & argb) >>> 16, this.redValue) << 16) | (caculatePerColor((65280 & argb) >>> 8, this.greenValue) << 8) | caculatePerColor(argb & 255, this.blueValue);
    }

    private final int caculatePerColor(int color, int value) {
        if (value == 0) {
            return color;
        }
        int color2 = color + (((color + 1) * value) >> 8);
        if (color2 > 255) {
            return 255;
        }
        if (color2 < 0) {
            return 0;
        }
        return color2;
    }

    private final int caculateGray(int argb) {
        int color;
        int r = (16711680 & argb) >>> 16;
        int g2 = (65280 & argb) >>> 8;
        int b = argb & 255;
        if (r > g2) {
            color = r;
        } else {
            color = g2;
        }
        if (color <= b) {
            color = b;
        }
        int color2 = (this.grayValue * color) >> 8;
        if (color2 > 255) {
            color2 = 255;
        } else if (color2 < 0) {
            color2 = 0;
        }
        return color2 | (-16777216 & argb) | (color2 << 16) | (color2 << 8);
    }

    private final int caculateAlpha(int argb) {
        return (16777215 & argb) | (((((-16777216 & argb) >>> 24) * this.alphaValue) >> 8) << 24);
    }

    public void drawRegion(MFImage img, int x_src, int y_src, int width, int height, int rotate_x, int rotate_y, int degree, int scale_x, int scale_y, int x_dest, int y_dest, int anchor) {
        if (MFDevice.preScaleZoomOutFlag) {
            x_src >>= MFDevice.preScaleShift;
            y_src >>= MFDevice.preScaleShift;
            width >>= MFDevice.preScaleShift;
            height >>= MFDevice.preScaleShift;
            rotate_x >>= MFDevice.preScaleShift;
            rotate_y >>= MFDevice.preScaleShift;
            scale_x >>= MFDevice.preScaleShift;
            scale_y >>= MFDevice.preScaleShift;
            x_dest >>= MFDevice.preScaleShift;
            y_dest >>= MFDevice.preScaleShift;
        } else if (MFDevice.preScaleZoomInFlag) {
            x_src <<= MFDevice.preScaleShift;
            y_src <<= MFDevice.preScaleShift;
            width <<= MFDevice.preScaleShift;
            height <<= MFDevice.preScaleShift;
            rotate_x <<= MFDevice.preScaleShift;
            rotate_y <<= MFDevice.preScaleShift;
            scale_x <<= MFDevice.preScaleShift;
            scale_y <<= MFDevice.preScaleShift;
            x_dest <<= MFDevice.preScaleShift;
            y_dest <<= MFDevice.preScaleShift;
        }
        this.g.drawRegion(img.image, x_src, y_src, width, height, rotate_x, rotate_y, degree, scale_x, scale_y, x_dest, y_dest, anchor);
    }
}
