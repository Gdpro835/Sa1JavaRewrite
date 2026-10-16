package SonicGBA;

import GameEngine.time.GameTime;
import Lib.AnimationDrawer;
import State.GameState;
import java.lang.reflect.Field;

/** Ship/ShipSystem swing must depend on elapsed time, not on the render frame rate. */
public final class ShipFrameRateRegressionTests {
    private static Field field(Class<?> c, String name) throws Exception {
        Field f = c.getDeclaredField(name); f.setAccessible(true); return f;
    }
    private static void loadStage() throws Exception {
        GameTime.reset(); AnimationDrawer.setAllPause(false); AnimationDrawer.setWorldPaused(false);
        GameObject.IsGamePause = false; GameObject.stageModeState = 0;
        PlayerObject.setCharacter(0); StageManager.setStageID(5);
        GameState game = new GameState(); game.init();
        boolean loaded = false;
        for (int i = 0; i < 300 && !loaded; i++) loaded = StageManager.loadStageStep();
        if (!loaded) throw new AssertionError("stage did not load");
        GameObject.setPlayerPosition(7700, 2100);
    }
    /** Returns {degree, posX} of the ship after the given simulated seconds. */
    private static int[] swing(int fps, double seconds) throws Exception {
        loadStage();
        ShipSystem system = new ShipSystem(4096, 4096, 0, 0, 0, 0, 0);
        Ship ship = new Ship(4096, 4096, 0, system);
        Field degree = field(ShipSystem.class, "degree");
        int steps = (int) Math.round(seconds * fps);
        for (int i = 0; i < steps; i++) {
            GameTime.beginFrame(1.0 / fps);
            ship.logic();
        }
        return new int[]{degree.getInt(system), ship.posX};
    }
    public static void main(String[] args) throws Exception {
        int[] fpsList = {30, 60, 144, 240};
        int[] degrees = new int[fpsList.length];
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 0; i < fpsList.length; i++) {
            degrees[i] = swing(fpsList[i], 4.0)[0];
            System.out.println("SHIP fps=" + fpsList[i] + " degree=" + degrees[i]);
            min = Math.min(min, degrees[i]); max = Math.max(max, degrees[i]);
        }
        // 1 degree = 64 units; allow 5 degrees of integration/rounding drift across frame rates.
        if (max - min > 5 * 64) throw new AssertionError("ship swing depends on frame rate: " + min + ".." + max);
        System.out.println("PASS: ship swing frame-rate independence (" + fpsList.length + " frame rates, production ShipSystem).");
    }
}
