package SonicGBA;

import GameEngine.Key;
import GameEngine.time.GameTime;
import Lib.Animation;
import Lib.AnimationDrawer;
import State.GameState;
import State.State;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGamePad;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.opengl.GLGraphics;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Jump-height regression using the production player, stage and collision code.
 * Legacy reference (original frame-stepped code, measured at the authored step): held jump 75.75 px,
 * released jump 47.34375 px. Headless doubles only; not Android device timing.
 */
public final class PhysicsRegressionTests {
    private static final MFGraphics g = MFGraphics.createMFGraphics(new GLGraphics(), 284, 160);
    private static final double LEGACY_HELD_PX = 75.75;
    private static final double LEGACY_SHORT_PX = 47.34375;
    private static final double TOLERANCE_PX = 2.0;
    private static int failures;

    private static Field field(Class<?> c, String name) throws Exception {
        while (c != null) {
            try { Field f = c.getDeclaredField(name); f.setAccessible(true); return f; }
            catch (NoSuchFieldException e) { c = c.getSuperclass(); }
        }
        throw new NoSuchFieldException(name);
    }

    private static GameState boot(int level, int character) throws Exception {
        GameTime.reset(); MFGamePad.resetKeys();
        PlayerObject.setCharacter(character); StageManager.setStageID(level);
        GameObject.stageModeState = 0; GameObject.IsGamePause = false;
        AnimationDrawer.setAllPause(false);
        GameState game = new GameState(); game.init();
        boolean ready = false;
        for (int i = 0; i < 100 && !ready; i++) ready = StageManager.loadStageStep();
        if (!ready) throw new AssertionError("level not loaded");
        Method m = GameState.class.getDeclaredMethod("initStageInfoClearRes"); m.setAccessible(true); m.invoke(game);
        GameObject.player.headInit(); Key.touchkeypauseInit();
        field(GameState.class, "state").setInt(game, 0);
        State.fadeInit(0, 0); PlayerObject.initStageParam(); StageManager.stagePassInit();
        return game;
    }

    private static void tick(GameState game, double dt) throws Exception {
        GameTime.beginFrame(dt); MFDevice.tick();
        Animation.class.getMethod("updateAll").invoke(null);
        AnimationDrawer.class.getMethod("updateAll").invoke(null);
        game.logic(); g.reset(); game.draw(g);
    }

    /** Returns the maximum rise above the ground in pixels for one jump. */
    private static double jumpHeight(int character, double dt, boolean held) throws Exception {
        GameState game = boot(0, character);
        for (double t = 0; t < 1.26; t += dt) tick(game, dt);
        PlayerObject p = GameObject.player;
        int ground = p.getFootPositionY(), low = ground;
        MFGamePad.pressVisualKey(16777216);
        boolean airborne = false;
        for (double time = 0; time < 3.0; time += dt) {
            tick(game, dt);
            if (!held || time > 1.5) MFGamePad.releaseVisualKey(16777216);
            low = Math.min(low, p.getFootPositionY());
            airborne |= p.collisionState != 0;
            if (airborne && p.collisionState == 0) break;
        }
        if (!airborne) throw new AssertionError("character " + character + " never left the ground");
        return (ground - low) / 64.0;
    }

    private static void check(String name, double actual, double expected, double tol) {
        boolean ok = Math.abs(actual - expected) <= tol;
        System.out.println((ok ? "ok   " : "FAIL ") + name + " actual=" + actual + " expected=" + expected + " tol=" + tol);
        if (!ok) failures++;
    }

    public static void main(String[] args) throws Exception {
        double[] steps = {1.0 / 30, 1.0 / 60, 1.0 / 144, 0.0097};
        for (int character = 0; character < 4; character++) {
            for (double dt : steps) {
                double held = jumpHeight(character, dt, true);
                double short_ = jumpHeight(character, dt, false);
                check("held jump char=" + character + " dt=" + dt, held, LEGACY_HELD_PX, TOLERANCE_PX);
                // Release timing is sampled per frame, so the short hop gets a wider window.
                check("short jump char=" + character + " dt=" + dt, short_, LEGACY_SHORT_PX, 4.0);
            }
        }
        if (failures != 0) throw new AssertionError(failures + " jump assertions failed");
        System.out.println("PASS: jump height assertions (production player/stage; headless graphics).");
    }
}
