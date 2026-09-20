package com.sega.mobile.framework.device;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MFInputStream {
    private ByteArrayInputStream mBytes;
    private DataInputStream mStream;

    public MFInputStream(byte[] b) {
        this.mBytes = new ByteArrayInputStream(b);
        this.mStream = new DataInputStream(this.mBytes);
    }

    public MFInputStream(InputStream is) {
        this.mStream = new DataInputStream(is);
    }

    public byte readByte() {
        try {
            return this.mStream.readByte();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public short readShort() {
        try {
            return this.mStream.readShort();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void readFully(byte[] b) {
        try {
            this.mStream.readFully(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int readInt() {
        try {
            return this.mStream.readInt();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void readFully(byte[] b, int offset, int length) {
        try {
            this.mStream.readFully(b, offset, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readUTF() {
        try {
            return this.mStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            if (this.mBytes != null) {
                this.mBytes.close();
            }
            this.mStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int readUnsignedShort() {
        try {
            return this.mStream.readUnsignedShort();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int read() {
        try {
            return this.mStream.read();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void read(byte[] b) {
        try {
            this.mStream.read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean readBoolean() {
        try {
            return this.mStream.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void skip(int offset) {
        try {
            this.mStream.skip((long) offset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        try {
            return this.mStream.available();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void skipBytes(int offset) {
        try {
            this.mStream.skipBytes(offset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
