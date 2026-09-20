package Lib;

import com.sega.mobile.framework.device.MFDevice;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Record {
    public static final String BP_RECORD = "SONIC_BP_RECORD";
    public static final String EMERALD_RECORD = "EMERALD_RECORD";
    public static final String HIGHSCORE_RECORD = "SONIC_HIGHSCORE_RECORD";
    private static final boolean ONE_RECORD = true;
    public static final String PARAM_RECORD = "SONIC_PARAM_RECORD";
    public static final String STAGE_RECORD = "SONIC_STAGE_RECORD";
    public static final String SYSTEM_RECORD = "SONIC_SYSTEM_RECORD";
    private static final String TOTAL_RECORD_NAME = "SONIC_RECORD";

    public static void initRecord() {
    }

    public static void saveRecord(String recordId, byte[] content) {
        if (content != null) {
            MFDevice.saveRecord(recordId, content);
        }
    }

    public static byte[] loadRecord(String recordId) {
        return MFDevice.loadRecord(recordId);
    }

    public static ByteArrayInputStream loadRecordStream(String recordId) {
        byte[] data = loadRecord(recordId);
        if (data == null) {
            return null;
        }
        return new ByteArrayInputStream(data);
    }

    public static void saveRecordStream(String recordId, ByteArrayOutputStream ds) {
        if (ds != null) {
            byte[] data = ds.toByteArray();
            saveRecord(recordId, data);
            //exportToExternalStorage(recordId, data);
            //exportToInternalStorage(recordId, data);
        }
    }

    private static void exportToExternalStorage(String str, byte[] bArr) {
        FileOutputStream fileOutputStream;
        File file = new File("/sdcard/Sonic Advance/" + str + ".dat");
        file.getParentFile().mkdirs();
        Throwable th = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Throwable th2) {
        }
    }

    private static void exportToInternalStorage(String str, byte[] bArr) {
        FileOutputStream fileOutputStream;
        File file = new File("/storage/emulated/0/Sonic Advance/" + str + ".dat");
        file.getParentFile().mkdirs();
        Throwable th = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Throwable th2) {
        }
    }
}
