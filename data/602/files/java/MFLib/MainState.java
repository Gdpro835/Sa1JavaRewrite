//
// Decompiled by Jadx - 825ms
//
package MFLib;

import GameEngine.Def;
import GameEngine.Key;
import Lib.SoundSystem;
import Lib.AnimationDrawer;
import SonicGBA.GlobalResource;
import SonicGBA.PlayerObject;
import SonicGBA.StageManager;
import State.State;
import State.TitleState;
import com.sega.MFLib.Main;
import com.sega.mobile.framework.MFGameState;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.platform.ChargePlatform;
import com.sega.mobile.platform.ChargeListener;

public class MainState implements MFGameState, Def {
    private boolean pauseFlag = false;
    private static String gameVersion = "";
    private static final String MF_VERSION = "";
    public static boolean DemoMode = false;
    //public static boolean tipfps60 = false;

    public MainState(Main main) {
        // Activity instances are not retained across Android recreation.
    }
    
    public void onEnter() {
        init_bp();
        MFDevice.setAntiAlias(false);
        MFDevice.setFilterBitmap(false);
        MFDevice.setVibrationFlag(true);
        MFDevice.setUseMultitouch(true);
        ChargePlatform.init(MFMain.getInstance());
        ChargePlatform.setListener(new ChargeListenerImpl(this));
        State.stateInit();
        //PlayerObject.setCharacter(0);
        //StageManager.setStageID(0);
        State.setState(0);
        String versionStr = MFMain.getInstance().getAppProperty("MIDlet-Version");
        if (!versionStr.startsWith("1.0")) {
            gameVersion = "ver:" + versionStr;
        }
        MFDevice.enableClearFont();
    }

    private static void init_bp() {
    }

    public void onExit() {
    }

    public void onPause() {
        State.pauseTrigger();
        this.pauseFlag = true;
    }

    private void pauseCheck() {
        if (this.pauseFlag) {
            Key.clear();
            State.statePause();
            this.pauseFlag = false;
        }
    }

    public void onRender(MFGraphics g) {
        State.stateDraw(g);
    }

    public void onUpdate(double deltaSeconds) {
        Lib.Animation.updateAll();
        AnimationDrawer.updateAll();
        PyxEditor.PyxAnimation.updateAll();
        pauseCheck();
        State.stateLogic();
        pauseCheck();
    }

    public void onResume() {
        SoundSystem.getInstance().updateVolumeState();
    }

    public void onRender(MFGraphics g, int layer) {
        TitleState.drawTitle(g, layer);
    }

    public void onKeyDown(int key) {
    }
    
public static class ChargeListenerImpl implements ChargeListener {
    final MainState this$0;

    ChargeListenerImpl(MainState mainState) {
        this.this$0 = mainState;
    }

    public void chargeSuccessed(int arg0) {
    }

    public void chargeStart(int arg0) {
    }

    public void chargeFailed(int arg0) {
    }

    public void chargeExit(int arg0) {
    }
}

}
