package com.sega.MFLib;

import android.app.AlertDialog;
import android.content.DialogInterface;
//import com.egame.sdk.control.EGame;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.framework.device.MFDevice;

public class Community {
    public static final int TYPE_FAIL_GAME = 1;
    public static final int TYPE_FINISH_GAME = 0;
    /* access modifiers changed from: private */
    public static boolean isPause;
    /* access modifiers changed from: private */
    public static int shareType;

    public static void init() {
        /*MFMain.getInstance().runOnUiThread(new Runnable() {
            public void run() {
                EGame.init(MFMain.getInstance(), 230840);
            }
        });*/
    }

    public static void exit() {
        //EGame.dropEGame();
    }

    public static void start() {
        MFDevice.setResponseInterruptFlag(false);
        //EGame.startEGame();
        isPause = true;
        do {
        } while (isPause);
        MFDevice.setResponseInterruptFlag(true);
    }

    public static void share(int type) {
        shareType = type;
        isPause = true;
        MFDevice.setResponseInterruptFlag(false);
        if (shareType == 1) {
            try {
                Thread.sleep(999);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        MFMain.getInstance().runOnUiThread(new Runnable() {
            public void run() {
                String str;
                AlertDialog.Builder title = new AlertDialog.Builder(MFMain.getInstance()).setTitle("提示");
                if (Community.shareType == 1) {
                    str = "哎呦，可惜了。没有剩余挑战次数了。不要紧，接下来可以接关继续游戏哦。在继续游戏之前做个评价吧。您的评价将帮助其他玩家更好地了解游戏。同时您也可以查看其他玩家的评论。";
                } else {
                    str = "恭喜通关！太令人激动了。赶快去分享您此刻或兴奋，或感动，或欣喜的游戏体验，让您的好友也一同感受这游戏带来的激动的时刻吧！";
                }
                title.setMessage(str).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        Community.isPause = false;
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //EGame.shareGame();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Community.isPause = false;
                    }
                }).show();
            }
        });
        do {
        } while (isPause);
        MFDevice.setResponseInterruptFlag(true);
    }

    public static void notifyPause() {
        isPause = true;
    }

    public static void notifyResume() {
        isPause = false;
    }
}
