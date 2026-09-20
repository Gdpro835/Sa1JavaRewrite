package com.sega.MFLib;

import MFLib.MainState;
import SonicGBA.GlobalResource;
import Special.SSDef;
import State.State;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import com.sega.mobile.framework.MFGameState;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;

public class Main extends MFMain {
    public static boolean BULLET_TIME;
    public static boolean notifyShowed = false;
    public static boolean showFlag;
    public static boolean fullscreen = false;
    public static int scale = 1;
    public static int DeviceScale = 1;
    public static boolean use568 = false;

    /* access modifiers changed from: protected */
    public void onPause() {
        Community.notifyPause();
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        Community.notifyResume();
        super.onResume();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 25) {
            if (GlobalResource.soundSwitchConfig == 0) {
                return true;
            }
            if (!State.IsInInterrupt) {
                State.setSoundVolumnDown();
            }
            return super.onKeyDown(keyCode, event);
        } else if (keyCode == 24) {
            if (GlobalResource.soundSwitchConfig == 0) {
                return true;
            }
            if (!State.IsInInterrupt) {
                State.setSoundVolumnUp();
            }
            return super.onKeyDown(keyCode, event);
        } else if (keyCode == 82) {
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void showExitConfirm() {
        setExitConfirmStr("Sonic Advance", "Do you want to exit the game?", "yes", "cancel");
    }

    public MFGameState getEntryGameState() {
        DeviceScale = (int) (MFDevice.getDeviceWidth() / MFDevice.getDeviceHeight());
        scale = scale == 0 ? DeviceScale : scale;
        if (!fullscreen) MFDevice.setCanvasSize((use568 ? 568 : (int) (Math.max(240, (MFDevice.getDeviceWidth() * 160) / MFDevice.getDeviceHeight()) * scale)), (use568 ? 320 : (int) (160 * scale)));
        else MFDevice.setCanvasSize(MFDevice.getDeviceWidth(), MFDevice.getDeviceHeight());
        MFDevice.setFullscreenMode(fullscreen);
        MFDevice.setEnableCustomBack(true);
        return new MainState(this);
    }

    public boolean logicDeviceSuspend() {
        return true;
    }

    public void drawDeviceSuspend(MFGraphics g) {
    }

    public static void showTouchNotify() {
        if (!notifyShowed) {
            MFDevice.setResponseInterruptFlag(false);
            MFMain.getInstance().runOnUiThread(new Runnable() {
                public void run() {
                    new AlertDialog.Builder(MFMain.getInstance()).setCancelable(false).setTitle("过关技巧").setMessage("无法顺利跳跃障碍物时，请先按A键，再按方向键。").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Main.showFlag = true;
                        }
                    }).show();
                }
            });
            do {
            } while (!showFlag);
            notifyShowed = true;
        }
    }
}
