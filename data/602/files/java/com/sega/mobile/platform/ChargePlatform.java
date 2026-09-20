package com.sega.mobile.platform;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

public class ChargePlatform {
    public static boolean CUSTOM_IMAGE = false;
    public static final boolean DEBUG_SHOW_PROPERTY_INFO = false;
    public static final int DEBUG_SKIP_CHARGE = 0;
    static final int DIALOG_CONNECT = 1;
    static final int DIALOG_INFO = 2;
    static final int DIALOG_QUERY = 0;
    static final int RESULT_FAILURE = 2;
    static final int RESULT_NONE = 0;
    static final int RESULT_SUCCESS = 1;
    /* access modifiers changed from: private */
    public static Chargeable mChargeContent;
    /* access modifiers changed from: private */
    public static Context mContext;
    /* access modifiers changed from: private */
    public static int mCount;
    public static int mCurrentPoint = 0;
    /* access modifiers changed from: private */
    public static boolean mIsCharged;
    /* access modifiers changed from: private */
    public static boolean mIsExit;
    private static ChargeListener mListener;
    public static int mPointNum = 0;
    /* access modifiers changed from: private */
    public static ProgressDialog mProgressDialog;
    private static RecordInfo[] mRecordList;
    /* access modifiers changed from: private */
    public static int mResult;
    /* access modifiers changed from: private */
    public static boolean mSkipCharge;

    static class PointInfo {
        String consumeCode;
        String failStr;
        String id;
        int index;
        String infoStr;
        int price;
        boolean repeat;
        String successStr;
        String type;

        public PointInfo(int index2, String pointId, String pointType, String info, int price2, boolean pointRepeat, String pointConsumeCode) {
            this.index = index2;
            this.id = pointId;
            this.type = pointType;
            this.infoStr = info;
            this.price = price2;
            this.repeat = pointRepeat;
            this.consumeCode = pointConsumeCode;
        }
    }

    static class RecordInfo {
        boolean isBought;
        int paidMoney;
        int trialTimes;

        public RecordInfo(int money, int times, boolean bought) {
            this.paidMoney = money;
            this.trialTimes = times;
            this.isBought = bought;
        }
    }

    public static void init(Context midlet) {
        mContext = midlet;
        Hashtable[] table = ChargeDecoder.loadChargeInfo();
        if (table == null) {
            Log.e("Charge Platform", "读取收费点错误！");
            return;
        }
        mChargeContent = ChargeManager.getChargeContent();
        mPointNum = mChargeContent.loadConfig(table);
        mRecordList = new RecordInfo[mPointNum];
        for (int i = 0; i < mPointNum; i++) {
            mRecordList[i] = new RecordInfo(0, 0, false);
        }
        loadRecord();
    }

    public static boolean chargeByIndex(int index) {
        mCurrentPoint = index;
        if (isChargedByIndex(index)) {
            mRecordList[index].paidMoney = 0;
            mRecordList[index].isBought = false;
        }
        mRecordList[index].trialTimes++;
        saveRecord();
        if (mListener != null) {
            mListener.chargeStart(index);
        }
        mIsExit = false;
        mResult = 0;
        mCount = 0;
        mChargeContent.initCharge();
        showDialog(0);
        while (!mIsExit) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!isChargedByIndex(index) && mListener != null) {
            mListener.chargeFailed(index);
        }
        if (mListener != null) {
            mListener.chargeExit(index);
        }
        mChargeContent.exitCharge();
        if (mResult == 1) {
            return true;
        }
        return false;
    }

    public static boolean isChargedByIndex(int index) {
        /*if (mRecordList != null) {
            return mRecordList[index].isBought;
        }*/
        return !MFLib.MainState.DemoMode;
    }

    public static int getEnterTimesByIndex(int index) {
        if (mRecordList != null) {
            return mRecordList[index].trialTimes;
        }
        return 0;
    }

    public static void debugSetValue(int index, String value) {
        if (index == 0) {
            mSkipCharge = value.equals("true");
        }
    }

    public static String getValue(int valueId) {
        return "";
    }

    public static void setListener(ChargeListener listener) {
        mListener = listener;
    }

    static Context getContext() {
        return mContext;
    }

    static Activity getActivity() {
        return (Activity) mContext;
    }

    static void chargeFee(int money, int price) {
        mRecordList[mCurrentPoint].paidMoney += money;
        if (mRecordList[mCurrentPoint].paidMoney >= price) {
            mRecordList[mCurrentPoint].isBought = true;
        }
        saveRecord();
    }

    static void chargeFee(int price) {
        chargeFee(price, price);
    }

    static int getPaidMoney() {
        return mRecordList[mCurrentPoint].paidMoney;
    }

    static int getIndex() {
        return mCurrentPoint;
    }

    static void notifyFinishCharge() {
        mIsCharged = true;
    }

    static void notifyDismiss() {
        mProgressDialog.dismiss();
    }

    static void notifyChargeSuccess() {
        if (mListener != null) {
            mListener.chargeSuccessed(mCurrentPoint);
        }
        mResult = 1;
    }

    static void notifyChargeFail() {
        mResult = 2;
    }

    static void postActivity(Runnable runnable) {
        ((Activity) mContext).runOnUiThread(runnable);
    }

    /* access modifiers changed from: private */
    public static String getQueryText() {
        String queryText = mChargeContent.getChargeText(mCount);
        if (queryText == null) {
            queryText = "激活游戏？";
        } else if (queryText == "") {
            return null;
        }
        return queryText;
    }

    /* access modifiers changed from: private */
    public static String getConnectText() {
        String connectText = mChargeContent.getConnectText(mCount);
        if (connectText == null) {
            connectText = "正在连接......";
        } else if (connectText == "") {
            return null;
        }
        return connectText;
    }

    /* access modifiers changed from: private */
    public static String getInfoText() {
        String infoText = mChargeContent.getInfoText(mCount);
        if (infoText != null) {
            return infoText;
        }
        if (mResult == 2) {
            return "收费失败";
        }
        if (mResult == 1) {
            return "To choose another game mode, uninstal and instal the application again.";
        }
        return "You can reset the game to select a game mode again.";
    }

    private static Runnable getDialog(int d) {
        switch (d) {
            case 0:
                if (CUSTOM_IMAGE) {
                    return null;
                }
                if (getQueryText() == null) {
                    return new Runnable() {
                        public void run() {
                            if (ChargePlatform.mSkipCharge) {
                                ChargePlatform.chargeFee(100);
                                ChargePlatform.notifyChargeSuccess();
                                ChargePlatform.showDialog(2);
                                return;
                            }
                            ChargePlatform.showDialog(1);
                        }
                    };
                }
                return new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(ChargePlatform.mContext).setTitle("SELECT A GAME MODE").setMessage(ChargePlatform.getQueryText()).setCancelable(false).setOnKeyListener(new DialogInterface.OnKeyListener() {
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == 84 || keyCode == 82) {
                                    return true;
                                }
                                return false;
                            }
                        }).setPositiveButton("Full game", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (ChargePlatform.mSkipCharge) {
                                    ChargePlatform.chargeFee(100);
                                    ChargePlatform.notifyChargeSuccess();
                                    ChargePlatform.showDialog(2);
                                    return;
                                }
                                ChargePlatform.showDialog(1);
                            }
                        }).setNegativeButton("Game demo", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ChargePlatform.showDialog(2);
                            }
                        }).show();
                    }
                };
            case 1:
                return new Runnable() {
                    public void run() {
                        ChargePlatform.mProgressDialog = new ProgressDialog(ChargePlatform.mContext);
                        ChargePlatform.mProgressDialog.setTitle("请稍等片刻...");
                        ChargePlatform.mProgressDialog.setMessage(ChargePlatform.getConnectText());
                        ChargePlatform.mProgressDialog.setCancelable(false);
                        ChargePlatform.mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == 84 || keyCode == 82) {
                                    return true;
                                }
                                return false;
                            }
                        });
                        if (ChargePlatform.getConnectText() != null) {
                            ChargePlatform.mProgressDialog.show();
                        }
                        new Thread() {
                            public void run() {
                                ChargePlatform.mIsCharged = false;
                                ChargePlatform.mChargeContent.charge(ChargePlatform.mCount);
                                while (!ChargePlatform.mIsCharged) {
                                    try {
                                        Thread.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                ChargePlatform.notifyDismiss();
                                if (ChargePlatform.isChargedByIndex(ChargePlatform.mCurrentPoint)) {
                                    ChargePlatform.notifyChargeSuccess();
                                    ChargePlatform.showDialog(2);
                                } else if (ChargePlatform.mResult == 2) {
                                    ChargePlatform.showDialog(2);
                                } else {
                                    ChargePlatform.showDialog(0);
                                }
                                ChargePlatform.mCount = ChargePlatform.mCount + 1;
                            }
                        }.start();
                    }
                };
            case 2:
                return new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(ChargePlatform.mContext).setTitle("THANK YOU FOR PLAYING").setMessage(ChargePlatform.getInfoText()).setCancelable(false).setOnKeyListener(new DialogInterface.OnKeyListener() {
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                if (keyCode == 84 || keyCode == 82) {
                                    return true;
                                }
                                return false;
                            }
                        }).setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ChargePlatform.mIsExit = true;
                            }
                        }).show();
                    }
                };
            default:
                return null;
        }
    }

    /* access modifiers changed from: private */
    public static void showDialog(int dialog) {
        postActivity(getDialog(dialog));
    }

    private static void saveRecord() {
        try {
            FileOutputStream fos = mContext.openFileOutput("CHARGE_PLATFORM", 0);
            DataOutputStream dos = new DataOutputStream(fos);
            dos.writeInt(mPointNum);
            for (int i = 0; i < mPointNum; i++) {
                dos.writeInt(mRecordList[i].paidMoney);
                dos.writeInt(mRecordList[i].trialTimes);
                dos.writeBoolean(mRecordList[i].isBought);
            }
            dos.flush();
            dos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            FileNotFoundException fileNotFoundException = e;
            System.out.println("Fail to create save file");
        } catch (IOException e2) {
            IOException iOException = e2;
            System.out.println("Fail to create save file");
        }
    }

    private static void loadRecord() {
        try {
            FileInputStream fis = mContext.openFileInput("CHARGE_PLATFORM");
            DataInputStream dis = new DataInputStream(fis);
            mPointNum = dis.readInt();
            for (int i = 0; i < mPointNum; i++) {
                mRecordList[i].paidMoney = dis.readInt();
                mRecordList[i].trialTimes = dis.readInt();
                mRecordList[i].isBought = dis.readBoolean();
            }
            dis.close();
            fis.close();
        } catch (FileNotFoundException e) {
            FileNotFoundException fileNotFoundException = e;
            saveRecord();
        } catch (IOException e2) {
            IOException iOException = e2;
            System.out.println("Fail to get save file");
        }
    }
}
