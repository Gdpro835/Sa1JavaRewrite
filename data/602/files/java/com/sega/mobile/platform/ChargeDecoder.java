package com.sega.mobile.platform;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

public class ChargeDecoder {
    public static Hashtable<String, String>[] loadChargeInfo() {
        try {
            InputStream pis = ChargePlatform.getContext().getAssets().open("p.dat");
            InputStream dis = ChargePlatform.getContext().getAssets().open("cc.dat");
            byte[] pBytes = new byte[pis.available()];
            pis.read(pBytes);
            pis.close();
            byte[] dBytes = new byte[dis.available()];
            dis.read(dBytes);
            dis.close();
            decrypt(pBytes, dBytes);
            return analyzeStr(new String(dBytes, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void decrypt(byte[] pBytes, byte[] kBytes) {
        int i = 0;
        int j = 0;
        while (i < kBytes.length) {
            kBytes[i] = (byte) (kBytes[i] - (pBytes[j] + i));
            i++;
            j = (j + 1) % pBytes.length;
        }
    }

    private static Hashtable<String, String>[] analyzeStr(String content) {
        Vector<Hashtable<String, String>> vec = new Vector<>();
        String[] chargeInfo = splitStr(content, "<!C>");
        String[] commonInfo = splitStr(chargeInfo[0], "<!L>");
        Hashtable<String, String> commonTable = new Hashtable<>();
        for (String splitStr : commonInfo) {
            String[] commonValue = splitStr(splitStr, "<!K>");
            commonTable.put(commonValue[0], commonValue[1]);
        }
        vec.addElement(commonTable);
        for (int i = 1; i < chargeInfo.length; i++) {
            String[] pointInfo = splitStr(chargeInfo[i], "<!L>");
            Hashtable<String, String> pointTable = new Hashtable<>();
            for (String splitStr2 : pointInfo) {
                String[] pointValue = splitStr(splitStr2, "<!K>");
                pointTable.put(pointValue[0], pointValue[1]);
            }
            vec.addElement(pointTable);
        }
        Hashtable[] ret = new Hashtable[vec.size()];
        for (int i2 = 0; i2 < ret.length; i2++) {
            ret[i2] = vec.elementAt(i2);
        }
        vec.removeAllElements();
        return ret;
    }

    private static String[] splitStr(String str, String symbol) {
        Vector<String> vec = new Vector<>();
        int index = str.indexOf(symbol);
        while (index != -1) {
            String addStr = str.substring(0, index);
            if (!addStr.equals("")) {
                vec.addElement(addStr);
            }
            str = str.substring(symbol.length() + index);
            index = str.indexOf(symbol);
        }
        if (!str.equals("")) {
            vec.addElement(str);
        }
        String[] ret = new String[vec.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = vec.elementAt(i);
        }
        vec.removeAllElements();
        return ret;
    }
}
