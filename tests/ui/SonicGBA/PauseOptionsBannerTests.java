package SonicGBA;

import GameEngine.time.GameTime;
import Lib.Animation;
import Lib.AnimationDrawer;
import State.GameState;
import State.SpecialStageState;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGamePad;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.opengl.GLGraphics;
import java.lang.reflect.Field;

/**
 * Blue options banner in the ordinary-stage pause menu and special-stage pause menu.
 * Checks real states/draw paths; headless graphics only.
 */
public final class PauseOptionsBannerTests {
    private static int assertions;
    private static final GLGraphics sink = new GLGraphics();
    private static final MFGraphics g = MFGraphics.createMFGraphics(sink, 284, 160);

    private static void check(boolean ok, String message) {
        assertions++;
        if (!ok) throw new AssertionError(message);
    }
    private static Field field(Class<?> c, String name) throws Exception {
        while (c != null) {
            try { Field f = c.getDeclaredField(name); f.setAccessible(true); return f; }
            catch (NoSuchFieldException e) { c = c.getSuperclass(); }
        }
        throw new NoSuchFieldException(name);
    }
    private static void call(Object target, String name) throws Exception {
        java.lang.reflect.Method m = target.getClass().getDeclaredMethod(name);
        m.setAccessible(true);
        m.invoke(target);
    }
    private static void tick(Object state, double dt, boolean stage) throws Exception {
        GameTime.beginFrame(dt);
        MFDevice.tick();
        Animation.updateAll();
        AnimationDrawer.updateAll();
        if (stage) ((SpecialStageState) state).logic(); else ((GameState) state).logic();
        sink.clearCommands();
        g.reset();
        if (stage) ((SpecialStageState) state).draw(g); else ((GameState) state).draw(g);
    }
    /** Returns {left, right} covered by the blue header tile (source 0,421, 100x20). */
    private static int[] band() {
        int left = Integer.MAX_VALUE, right = Integer.MIN_VALUE;
        for (String command : sink.commands) {
            if (command.startsWith("image:0:421:100:20:0:")) {
                int x = Integer.parseInt(command.split(":")[6]);
                left = Math.min(left, x);
                right = Math.max(right, x + 100);
            }
        }
        return new int[]{left, right};
    }
    private static void assertBand(String label, int fps, int[] b) {
        check(b[0] != Integer.MAX_VALUE, label + " drew no blue header at " + fps + " fps");
        check(b[0] <= 0 && b[1] >= 284, label + " header must cover both edges at " + fps + " fps; left=" + b[0] + " right=" + b[1]);
    }

    private static void stagePause(int fps) throws Exception {
        GameTime.reset(); MFGamePad.resetKeys();
        PlayerObject.setCharacter(0); StageManager.setStageID(0);
        GameObject.stageModeState = 0; GameObject.IsGamePause = false;
        AnimationDrawer.setAllPause(false); AnimationDrawer.setWorldPaused(false);
        GameState game = new GameState(); game.init();
        boolean ready = false;
        for (int i = 0; i < 100 && !ready; i++) ready = StageManager.loadStageStep();
        check(ready, "stage did not load for pause options");
        call(game, "optionInit");
        field(GameState.class, "state").setInt(game, 7);
        for (int i = 0; i < fps * 2; i++) {
            tick(game, 1.0 / fps, false);
            assertBand("stage pause options", fps, band());
            int offset = field(GameState.class, "optionOffsetX").getInt(game);
            check(offset >= 0 && offset < 100, "banner offset must stay wrapped in [0,100): " + offset);
        }
    }

    private static void specialPause(int fps) throws Exception {
        GameTime.reset(); MFGamePad.resetKeys();
        AnimationDrawer.setAllPause(false); AnimationDrawer.setWorldPaused(false);
        SpecialStageState special = new SpecialStageState();
        special.init();
        call(special, "optionInit");
        field(SpecialStageState.class, "state").setInt(special, 8);
        for (int i = 0; i < fps * 2; i++) {
            tick(special, 1.0 / fps, true);
            assertBand("special-stage pause options", fps, band());
        }
    }

    public static void main(String[] args) throws Exception {
        for (int fps : new int[]{30, 60, 120, 144}) {
            stagePause(fps);
            specialPause(fps);
        }
        System.out.println("PASS: " + assertions + " pause options banner assertions (real states, headless graphics).");
    }
}
