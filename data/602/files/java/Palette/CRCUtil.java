package Palette;

final class CRCUtil {
    private static int[] crc_table;

    private static void make_crc_table() {
        crc_table = new int[256];
        for (int n = 0; n < 256; n++) {
            int c = n;
            for (int k = 0; k < 8; k++) {
                if ((c & 1) == 1) {
                    c = -306674912 ^ (c >>> 1);
                } else {
                    c >>>= 1;
                }
            }
            crc_table[n] = c;
        }
    }

    private static int update_crc(byte[] buf, int off, int len) {
        int c = -1;
        if (crc_table == null) {
            make_crc_table();
        }
        for (int n = off; n < len + off; n++) {
            c = crc_table[(buf[n] ^ c) & 255] ^ (c >>> 8);
        }
        return c;
    }

    static int checksum(byte[] buf, int off, int len) {
        return update_crc(buf, off, len) ^ -1;
    }

    CRCUtil() {
    }
}
