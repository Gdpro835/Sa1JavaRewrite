package com.sega.mobile.framework.device;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MFOutputStream {
    private ByteArrayOutputStream mBytes = new ByteArrayOutputStream();
    private DataOutputStream mStream = new DataOutputStream(this.mBytes);

    public void writeByte(byte b) {
        try {
            this.mStream.writeByte(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] toByteArray() {
        return this.mBytes.toByteArray();
    }

    public void writeInt(int i) {
        try {
            this.mStream.writeInt(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeUTF(String str) {
        try {
            this.mStream.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int size() {
        return this.mBytes.size();
    }

    public void writeBoolean(boolean b) {
        try {
            this.mStream.writeBoolean(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.mBytes.close();
            this.mStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(int b) {
        try {
            this.mStream.write(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
