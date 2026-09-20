package com.sega.mobile.platform;

import android.app.PendingIntent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import com.sega.engine.action.ACVersion;
import com.sega.mobile.platform.ChargePlatform;
import java.util.Hashtable;

public class TeleSmsChargeContent implements Chargeable {
    protected static String mSmsNum;
    private String infoStr;
    private ChargePlatform.PointInfo[] mPointList;

    private boolean sendMessage() {
        try {
            SmsManager.getDefault().sendTextMessage(mSmsNum, (String) null, this.mPointList[ChargePlatform.getIndex()].consumeCode, (PendingIntent) null, (PendingIntent) null);
            ChargePlatform.chargeFee(this.mPointList[ChargePlatform.getIndex()].price);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkSim() {
        return ((TelephonyManager) ChargePlatform.getContext().getSystemService("phone")).getSimState() == 5;
    }

    public void initCharge() {
        this.infoStr = null;
    }

    public void charge(int index) {
        ChargePlatform.notifyDismiss();
        if (!checkSim()) {
            ChargePlatform.notifyChargeFail();
            this.infoStr = "请检查SIM卡";
            ChargePlatform.notifyFinishCharge();
            return;
        }
        sendMessage();
        if (ChargePlatform.isChargedByIndex(0)) {
            ChargePlatform.notifyChargeSuccess();
        } else {
            ChargePlatform.notifyChargeFail();
        }
        ChargePlatform.notifyFinishCharge();
    }

    public void exitCharge() {
    }

    public String getChargeText(int index) {
        if (index < 0) {
            return null;
        }
        if (index >= this.mPointList.length) {
            return null;
        }
        return this.mPointList[index].infoStr;
    }

    public String getInfoText(int index) {
        return this.infoStr;
    }

    public String getConnectText(int index) {
        return "正在发送短信...";
    }

    public int loadConfig(Hashtable<String, String>[] table) {
        String str;
        String infoStr2;
        boolean repeat;
        String consumeCode;
        String strSmsNum = table[0].get("SMS");
        if (strSmsNum == null) {
            str = "";
        } else {
            str = strSmsNum;
        }
        mSmsNum = str;
        this.mPointList = new ChargePlatform.PointInfo[(table.length - 1)];
        for (int i = 1; i < table.length; i++) {
            int index = i - 1;
            String id = new StringBuilder().append(i).toString();
            String strType = table[i].get("收费点类型");
            String type = strType == null ? ACVersion.VERSION_STR : strType;
            String strPrice = table[i].get("收费点价格");
            int price = strPrice == null ? 0 : Integer.parseInt(strPrice);
            String strInfoStr = table[i].get("收费点提示文字");
            if (strInfoStr == null) {
                infoStr2 = "是否激活游戏?";
            } else {
                infoStr2 = strInfoStr;
            }
            if (table[i].get("可重复收费点") == null) {
                repeat = false;
            } else {
                repeat = table[i].get("可重复收费点").equals("true");
            }
            String strConsumeCode = table[i].get("消费代码");
            if (strConsumeCode == null) {
                consumeCode = "";
            } else {
                consumeCode = strConsumeCode;
            }
            this.mPointList[i - 1] = new ChargePlatform.PointInfo(index, id, type, infoStr2, price, repeat, consumeCode);
        }
        return table.length - 1;
    }
}
