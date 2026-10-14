package SonicGBA;

import GameEngine.time.GameTime;
import Lib.AnimationDrawer;
import State.GameState;
import java.lang.reflect.Field;

/** Eggman (Boss3) intro face/laugh timer must leave state 2 instead of laughing forever. */
public final class Boss3LaughRegressionTests {
    private static Field field(Class<?> c, String name) throws Exception {
        while (c != null) {
            try { Field f = c.getDeclaredField(name); f.setAccessible(true); return f; }
            catch (NoSuchFieldException e) { c = c.getSuperclass(); }
        }
        throw new NoSuchFieldException(name);
    }
    private static Boss3 load() throws Exception {
        GameTime.reset(); AnimationDrawer.setAllPause(false); AnimationDrawer.setWorldPaused(false);
        GameObject.IsGamePause = false; GameObject.stageModeState = 0;
        PlayerObject.setCharacter(0); StageManager.setStageID(5);
        GameState game = new GameState(); game.init();
        boolean loaded = false;
        for (int i = 0; i < 300 && !loaded; i++) loaded = StageManager.loadStageStep();
        if (!loaded) throw new AssertionError("Casino Paradise 3_2 did not load");
        Boss3 boss = null;
        for (Object obj : GameObject.bossObjVec) if (obj instanceof Boss3) boss = (Boss3)obj;
        if (boss == null) throw new AssertionError("Boss3 not found");
        // Enter the arena trigger (foot X >= StartPosX and foot Y >= 2016 px), as the player would.
        GameObject.setPlayerPosition(7700, 2100);
        return boss;
    }
    private static void intro(int fps) throws Exception {
        Boss3 boss = load();
        Field show = field(Boss3.class, "show_step");
        Field wait = field(Boss3.class, "boss_wait_cnt");
        Field state = field(Boss3.class, "state");
        int maxFrames = fps * 20;
        int frame = 0;
        while (state.getInt(boss) != 2 && frame < maxFrames) {
            GameTime.beginFrame(1.0 / fps); boss.logic(); frame++;
        }
        if (state.getInt(boss) != 2) throw new AssertionError("fps=" + fps + " intro never reached state 2");
        while (show.getInt(boss) != 3 && frame < maxFrames) {
            GameTime.beginFrame(1.0 / fps); boss.logic(); frame++;
        }
        if (show.getInt(boss) != 3) throw new AssertionError("fps=" + fps + " intro stuck: show=" + show.getInt(boss) + " wait=" + wait.getInt(boss) + " state=" + state.getInt(boss));
        if (wait.getInt(boss) < 42) throw new AssertionError("fps=" + fps + " left laugh before 42: wait=" + wait.getInt(boss));
        System.out.println("LAUGH fps=" + fps + " intro done at t=" + frame / (double)fps + "s wait=" + wait.getInt(boss));
    }
    public static void main(String[] args) throws Exception {
        for (int fps : new int[]{30, 60, 120, 144, 240}) intro(fps);
        System.out.println("PASS: Boss3 eternal-laugh regression (production Boss3, headless graphics).");
    }
}
