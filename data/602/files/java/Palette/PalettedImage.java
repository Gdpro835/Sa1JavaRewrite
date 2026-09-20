package Palette;

import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PalettedImage {
    private int CRCOffset;
    private MFImage image = null;
    private byte[] imgData;
    private boolean needRebuild;
    private Palette p;
    private int paletteColors;
    private int paletteOffset;

    private PalettedImage() {
    }

    public Palette getPalette() {
        return this.p;
    }

    public MFImage getImage() {
        if (this.needRebuild) {
            rebuild();
        }
        return this.image;
    }

    public static PalettedImage createPalettedImage(InputStream in) {
        byte[] data = null;
        try {
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
        }
        return createPalettedImage(data);
    }

    public static PalettedImage createPalettedImage(byte[] data) {
        PalettedImage pImage = new PalettedImage();
        pImage.imgData = data;
        pImage.analyze();
        pImage.image = MFImage.createImage((InputStream) new ByteArrayInputStream(pImage.imgData));
        return pImage;
    }

    public static PalettedImage createPalettedImage(String path) {
        InputStream in = MFDevice.getResourceAsStream(path);
        if (in == null) {
            return null;
        }
        return createPalettedImage(in);
    }

    public void dispose() {
    }

    public int getHeight() {
        return this.image.getHeight();
    }

    public int getWidth() {
        return this.image.getWidth();
    }

    private void analyze() {
        int offset = 8;
        while (true) {
            if (this.imgData[offset + 4] == 80 && this.imgData[offset + 5] == 76 && this.imgData[offset + 6] == 84 && this.imgData[offset + 7] == 69) {
                break;
            }
            offset += readInt(offset) + 8 + 4;
        }
        int chunkLen = readInt(offset);
        this.paletteColors = chunkLen / 3;
        this.paletteOffset = offset + 8;
        this.CRCOffset = offset + 8 + chunkLen;
        this.p = new Palette(this.paletteColors);
        System.out.println("paletteColors:" + this.paletteColors);
        int i = 0;
        int off = this.paletteOffset;
        while (i < this.paletteColors) {
            this.p.setEntry(i, ((this.imgData[off] & 255) << 16) | ((this.imgData[off + 1] & 255) << 8) | (this.imgData[off + 2] & 255));
            i++;
            off += 3;
        }
    }

    private int readInt(int offset) {
        return ((this.imgData[offset] & 255) << 24) | ((this.imgData[offset + 1] & 255) << 16) | ((this.imgData[offset + 2] & 255) << 8) | (this.imgData[offset + 3] & 255);
    }

    private void CRCChecksum() {
        int checksum = CRCUtil.checksum(this.imgData, this.paletteOffset - 4, (this.paletteColors * 3) + 4);
        this.imgData[this.CRCOffset] = (byte) ((checksum >> 24) & 255);
        this.imgData[this.CRCOffset + 1] = (byte) ((checksum >> 16) & 255);
        this.imgData[this.CRCOffset + 2] = (byte) ((checksum >> 8) & 255);
        this.imgData[this.CRCOffset + 3] = (byte) (checksum & 255);
    }

    private void rebuild() {
        CRCChecksum();
        this.image = null;
        this.image = MFImage.createImage((InputStream) new ByteArrayInputStream(this.imgData));
        this.imgData = null;
        this.needRebuild = false;
        System.gc();
    }

    public void setPalette(Palette p2) {
        this.p = p2;
        int i = 0;
        int offset = this.paletteOffset;
        while (i < p2.colors.length) {
            this.imgData[offset] = (byte) ((p2.colors[i] >> 16) & 255);
            this.imgData[offset + 1] = (byte) ((p2.colors[i] >> 8) & 255);
            this.imgData[offset + 2] = (byte) (p2.colors[i] & 255);
            i++;
            offset += 3;
        }
        this.needRebuild = true;
    }
}
