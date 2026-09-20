package Lib;

import Palette.Palette;
import Palette.PalettedImage;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.device.MFImage;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class BitmapFont {
    private static final int DEFAULT_IMAGE_BUFFER_SIZE = 2500;
    private static final int MAX_LOOKUP_SIZE = 128;
    private static final int SHEET_BITS_CLIP_H = 7;
    private static final int SHEET_BITS_CLIP_W = 7;
    private static final int SHEET_BITS_CLIP_X = 9;
    private static final int SHEET_BITS_CLIP_Y = 9;
    private static final int SHEET_MASK_CLIP_H = 127;
    private static final int SHEET_MASK_CLIP_W = 127;
    private static final int SHEET_MASK_CLIP_X = 511;
    private static final int SHEET_MASK_CLIP_Y = 511;
    private static final int SHEET_ROTL_CLIP_H = 25;
    private static final int SHEET_ROTL_CLIP_W = 18;
    private static final int SHEET_ROTL_CLIP_X = 0;
    private static final int SHEET_ROTL_CLIP_Y = 9;
    private static final int TYPE_PNG = 0;
    private static final int TYPE_RAW = 1;
    private static byte[] imgData;
    private static DataInputStream stream;
    final byte[] advance;
    final int ascent;
    private final String charset;
    private final int[] clip;
    final int descent;
    final int height;
    private final int[] lookup;
    private final int max;
    private final int min;
    final int minAdvance;
    public String name = null;
    private final byte[] relX;
    private final byte[] relY;
    private MFImage sheet;
    private final int size;

    public BitmapFont(String resource) throws IOException {
        DataInputStream in = new DataInputStream(MFDevice.getResourceAsStream(resource));
        this.min = in.readUnsignedShort();
        this.max = in.readUnsignedShort();
        this.charset = in.readUTF();
        int tot = this.max - this.min;
        tot = tot > 128 ? 128 : tot;
        this.lookup = new int[tot];
        int n = 0;
        int i = this.min;
        while (n < tot) {
            this.lookup[n] = this.charset.indexOf((char) i);
            n++;
            i++;
        }
        this.ascent = in.readByte();
        this.descent = in.readByte();
        this.height = this.descent - this.ascent;
        System.out.println("height:" + this.height);
        this.size = in.readUnsignedShort();
        this.clip = new int[this.size];
        for (int n2 = 0; n2 < this.size; n2++) {
            this.clip[n2] = in.readInt();
        }
        this.relX = new byte[this.size];
        in.readFully(this.relX);
        this.relY = new byte[this.size];
        in.readFully(this.relY);
        this.advance = new byte[this.size];
        in.readFully(this.advance);
        this.minAdvance = findMinimum(this.advance, this.size);
        if (in.readByte() != 0) {
            throw new IllegalArgumentException();
        }
        this.sheet = loadImageFromStream(in);
        in.close();
    }

    public BitmapFont(DataInput in) throws IOException {
        this.min = in.readUnsignedShort();
        this.max = in.readUnsignedShort();
        this.charset = in.readUTF();
        int tot = this.max - this.min;
        tot = tot > 128 ? 128 : tot;
        this.lookup = new int[tot];
        int n = 0;
        int i = this.min;
        while (n < tot) {
            this.lookup[n] = this.charset.indexOf((char) i);
            n++;
            i++;
        }
        this.ascent = in.readByte();
        this.descent = in.readByte();
        this.height = this.descent - this.ascent;
        this.size = in.readUnsignedShort();
        this.clip = new int[this.size];
        for (int n2 = 0; n2 < this.size; n2++) {
            this.clip[n2] = in.readInt();
        }
        this.relX = new byte[this.size];
        in.readFully(this.relX);
        this.relY = new byte[this.size];
        in.readFully(this.relY);
        this.advance = new byte[this.size];
        in.readFully(this.advance);
        this.minAdvance = findMinimum(this.advance, this.size);
        if (in.readByte() != 0) {
            throw new IllegalArgumentException();
        }
        this.sheet = loadImageFromStream(in);
    }

    private BitmapFont(BitmapFont originalBitmap) {
        this.name = originalBitmap.name;
        this.min = originalBitmap.min;
        this.max = originalBitmap.max;
        this.charset = originalBitmap.charset;
        this.ascent = originalBitmap.ascent;
        this.descent = originalBitmap.descent;
        this.height = originalBitmap.height;
        this.size = originalBitmap.size;
        this.clip = originalBitmap.clip;
        this.relX = originalBitmap.relX;
        this.relY = originalBitmap.relY;
        this.advance = originalBitmap.advance;
        this.sheet = originalBitmap.sheet;
        this.lookup = originalBitmap.lookup;
        this.minAdvance = originalBitmap.minAdvance;
    }

    private static int findMinimum(byte[] prop, int size) {
        int measure = Integer.MAX_VALUE;
        for (int n = 0; n < size; n += TYPE_RAW) {
            if (prop[n] > 0 && prop[n] < measure) {
                measure = prop[n];
            }
        }
        return measure;
    }

    private static int findMaximum(byte[] prop, int size) {
        int measure = Integer.MIN_VALUE;
        for (int n = 0; n < size; n += TYPE_RAW) {
            if (prop[n] > measure) {
                measure = prop[n];
            }
        }
        return measure;
    }

    public int getIndex(char c) {
        int i = c - this.min;
        if (i < 0 || i >= this.lookup.length) {
            return this.charset.lastIndexOf(c);
        }
        return this.lookup[i];
    }

    public boolean hasContent(int n) {
        return (this.clip[n] & 33292288) != 0;
    }

    public int getW(char c) {
        int idx = getIndex(c);
        if (idx >= 0) {
            return this.advance[idx];
        }
        return 0;
    }

    public int getW(char[] text) {
        return getW(text, 0, text.length);
    }

    private boolean isHalfWidth(char input) {
        if (input <= '~') {
            return true;
        }
        if (input == ' ') {
            return true;
        }
        return false;
    }

    public int getW(char[] text, int offset, int length) {
        int total = 0;
        int n = 0;
        int i = offset;
        while (n < length) {
            int idx = getIndex(text[i]);
            if (idx >= 0) {
                total += this.advance[idx];
            }
            n++;
            i++;
        }
        return total;
    }

    public void paint(MFGraphics g, MFImage imgRef, int n, int x, int y) {
        int clipN = this.clip[n];
        g.drawRegion(imgRef == null ? this.sheet : imgRef, (clipN >> 0) & 511, (clipN >> 9) & 511, (clipN >> 18) & 127, (clipN >> 25) & 127, 0, x + this.relX[n], y + this.relY[n], 20);
    }

    public final MFImage loadImageFromStream(DataInput in) throws IOException {
        int dataSize = in.readUnsignedShort();
        if (imgData == null || imgData.length < dataSize) {
            imgData = new byte[(dataSize > DEFAULT_IMAGE_BUFFER_SIZE ? dataSize : DEFAULT_IMAGE_BUFFER_SIZE)];
        }
        in.readFully(imgData, 0, dataSize);
        return MFImage.createImage((InputStream) new ByteArrayInputStream(imgData));
    }

    public void close() {
        this.sheet = null;
        imgData = null;
    }

    public void DrawString(MFGraphics g, String str, int x, int y, int anchor) {
        DrawString(g, str, (MFImage) null, x, y, anchor);
    }

    public void DrawString(MFGraphics g, String str, MFImage img, int x, int y, int anchor) {
        int total = 0;
        int drawX = x;
        int strLen = str.length();
        char[] cArr = new char[strLen];
        char[] temp = str.toCharArray();
        for (int i = 0; i < strLen; i++) {
            int idx = getIndex(temp[i]);
            if (idx != -1) {
                total += this.advance[idx];
            }
        }
        if ((anchor & 1) != 0) {
            drawX -= (total + 1) >> 1;
        }
        if ((anchor & 8) != 0) {
            drawX -= total;
        }
        if ((anchor & 2) != 0) {
            y += this.height >> 1;
        }
        if ((anchor & 16) != 0) {
            y += 0;
        }
        if ((anchor & 32) != 0) {
            y += this.height;
        }
        for (int i2 = 0; i2 < strLen; i2++) {
            int idx2 = getIndex(temp[i2]);
            paint(g, img, idx2, drawX, y);
            if (idx2 != -1) {
                drawX += this.advance[idx2];
            }
        }
    }

    public BitmapFont getClone(Palette palette) {
        BitmapFont re = new BitmapFont(this);
        PalettedImage pImage = PalettedImage.createPalettedImage(imgData);
        pImage.setPalette(palette);
        re.sheet = pImage.getImage();
        return re;
    }
}
