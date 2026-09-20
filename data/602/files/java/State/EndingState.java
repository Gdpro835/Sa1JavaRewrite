//
// Decompiled by FernFlower - 1134ms
//
package State;

import Ending.NormalEnding;
import Ending.SpecialEnding;
import Ending.SuperSonicEnding;
import SonicGBA.PlayerObject;
import com.sega.mobile.framework.device.MFGraphics;

public class EndingState extends State {
    private int endingState;
    private NormalEnding normalEndingInstance;
    private SpecialEnding specialEndingInstance;
    private SuperSonicEnding superSonicEndingInstance;

    public EndingState(int var1) {
        this.endingState = var1;
        switch (var1) {
            case 0:
                PlayerObject.resetGameParam();
                this.normalEndingInstance = new NormalEnding();
                this.normalEndingInstance.init(0, PlayerObject.getCharacterID());
                break;
            case 1:
                PlayerObject.resetGameParam();
                this.superSonicEndingInstance = new SuperSonicEnding();
                break;
            case 2:
                this.specialEndingInstance = new SpecialEnding(3, 5);
        }

    }

    public void close() {
        switch (this.endingState) {
            case 0:
                this.normalEndingInstance.close();
                break;
            case 1:
                this.superSonicEndingInstance.close();
                break;
            case 2:
                this.specialEndingInstance.close();
        }

    }

    public void draw(MFGraphics var1) {
        switch (this.endingState) {
            case 0:
                this.normalEndingInstance.draw(var1);
                break;
            case 1:
                this.superSonicEndingInstance.draw(var1);
                break;
            case 2:
                this.specialEndingInstance.draw(var1);
        }

    }

    public void init() {
    }

    public void logic() {
        switch (this.endingState) {
            case 0:
                this.normalEndingInstance.logic();
                break;
            case 1:
                this.superSonicEndingInstance.logic();
                break;
            case 2:
                this.specialEndingInstance.logic();
        }

    }

    public void pause() {
        switch (this.endingState) {
            case 0:
                this.normalEndingInstance.pause();
                break;
            case 1:
                this.superSonicEndingInstance.pause();
                break;
            case 2:
                this.specialEndingInstance.pause();
        }

    }
}

