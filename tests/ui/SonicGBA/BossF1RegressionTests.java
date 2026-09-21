package SonicGBA;

import GameEngine.time.GameTime;
import Lib.AnimationDrawer;
import State.GameState;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.opengl.GLGraphics;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/** Real final-stage BossF1/resources/collider; pixels/audio/host remain headless adapters. */
public final class BossF1RegressionTests {
    private static final double PHASE = 256.0 / 15.0 * GameTime.ASSET_TIME_UNIT_SECONDS;
    private static final double[] IRREGULAR = {0.007, 0.017, 0.033, 0.1, 0.011, 0.024};
    private static int checks, interval;
    private static final GLGraphics sink = new GLGraphics();
    private static final MFGraphics graphics = MFGraphics.createMFGraphics(sink, 284, 160);

    private static void check(boolean ok, String message) {
        checks++;
        if (!ok) throw new AssertionError(message);
    }
    private static Field field(Class<?> type, String name) throws Exception {
        while (type != null) {
            try { Field f = type.getDeclaredField(name); f.setAccessible(true); return f; }
            catch (NoSuchFieldException e) { type = type.getSuperclass(); }
        }
        throw new NoSuchFieldException(name);
    }
    private static void set(BossF1 boss, String name, int value) throws Exception { field(BossF1.class, name).setInt(boss, value); }
    private static int number(BossF1 boss, String name) throws Exception { return field(BossF1.class, name).getInt(boss); }
    private static int[][] links(BossF1 boss) throws Exception { return (int[][]) field(BossF1.class, "ballPos").get(boss); }
    private static double delta(int fps) { return fps > 0 ? 1.0 / fps : IRREGULAR[interval++ % IRREGULAR.length]; }
    private static BossF1 prepare(boolean deploy) throws Exception {
        GameTime.reset();
        AnimationDrawer.setAllPause(false); AnimationDrawer.setWorldPaused(false);
        PlayerObject.setCharacter(0); StageManager.setStageID(12);
        GameObject.stageModeState = 0; GameObject.IsGamePause = false;
        GameState game = new GameState(); game.init();
        boolean loaded = false;
        for (int i = 0; i < 100 && !loaded; i++) loaded = StageManager.loadStageStep();
        check(loaded, "final-stage resources load");
        BossF1 boss = null;
        for (Object item : GameObject.bossObjVec) if (item instanceof BossF1) boss = (BossF1) item;
        check(boss != null, "actual BossF1 found in the final-stage map");
        GameObject.setPlayerPosition(32, 32); // Player outside this isolated attack fixture.
        GameObject.camera = MapManager.getCamera();
        GameObject.camera.x = 722; GameObject.camera.y = 544;
        field(BossF1.class, "displayFlag").setBoolean(boss, true);
        field(BossF1.class, "isDisplayBall").setBoolean(boss, true);
        if (deploy) {
            set(boss, "state", 1); set(boss, "show_step", 3);
            boss.posX = 55296; boss.posY = 43008;
        } else {
            set(boss, "state", 2); set(boss, "pro_step", 0);
            Method pose = BossF1.class.getDeclaredMethod("battlePose");
            pose.setAccessible(true); pose.invoke(boss);
        }
        return boss;
    }
    private static int[] pose(BossF1 boss) throws Exception {
        int[] result = new int[14]; result[0] = boss.posX; result[1] = boss.posY;
        int[][] links = links(boss);
        for (int i = 0; i < 6; i++) { result[2 + i * 2] = links[i][0]; result[3 + i * 2] = links[i][1]; }
        return result;
    }
    private static void step(BossF1 boss, double dt) { GameTime.beginFrame(dt); boss.logic(); }
    private static void advance(BossF1 boss, int fps, double duration) {
        double time = 0;
        while (time + 1e-10 < duration) {
            double dt = Math.min(delta(fps), duration - time); step(boss, dt); time += dt;
        }
    }

    private static void continuity(int fps, boolean deployment) throws Exception {
        BossF1 boss = prepare(deployment);
        int[][] links = links(boss);
        BossF1Ball ball = (BossF1Ball) field(BossF1.class, "ball").get(boss);
        double lastX = links[4][0] / 64.0, lastY = links[4][1] / 64.0;
        double maxSpeed = 0, maxRadiusError = 0, time = 0;
        boolean colliderAligned = true, subpixels = false, balanced = true;
        boolean[] phases = new boolean[4];
        while (time < 10.0) {
            double dt = delta(fps); step(boss, dt); time += dt;
            double x = links[4][0] / 64.0, y = links[4][1] / 64.0;
            maxSpeed = Math.max(maxSpeed, Math.hypot(x - lastX, y - lastY) / dt);
            if (number(boss, "state") == 2 || number(boss, "show_step") >= 4) {
                maxRadiusError = Math.max(maxRadiusError, Math.abs(Math.hypot(links[4][0] - boss.posX,
                        links[4][1] - boss.posY) - 4992));
                colliderAligned &= ball.posX == links[4][0] && ball.posY == links[4][1]
                        && ball.collisionRect.x0 + 1024 == ball.posX && ball.collisionRect.y0 + 1024 == ball.posY;
            }
            if (number(boss, "state") == 2) phases[number(boss, "pro_step")] = true;
            sink.clearCommands(); boss.draw(graphics);
            subpixels |= sink.fractionalTranslation;
            balanced &= sink.getSaveDepth() == 0;
            lastX = x; lastY = y;
        }
        check(maxSpeed < 260, "no ball teleport at quarter/cycle/deployment boundaries, fps=" + fps + ", speed=" + maxSpeed);
        check(maxRadiusError < 1.0, "78-pixel chain radius stays fixed (within 1/64-pixel rounding)");
        check(colliderAligned, "ball drawing and damage collider share one position");
        check(subpixels, "chain and boss retain sub-pixel placement on the graphics path");
        check(balanced, "BossF1 restores its sub-pixel transform");
        for (boolean visited : phases) check(visited, "all four attack phases remain reachable");
    }

    private static void rateIndependence() throws Exception {
        double[] samples = {0.18, 0.73, PHASE, PHASE * 2, PHASE * 3, PHASE * 4, PHASE * 4 + 0.25, 10.5};
        int[][] reference = null;
        for (int fps : new int[]{60, 10, 30, 90, 120, 144, -1}) {
            BossF1 boss = prepare(false);
            int[][] current = new int[samples.length][];
            double time = 0;
            for (int i = 0; i < samples.length; i++) {
                advance(boss, fps, samples[i] - time); time = samples[i];
                current[i] = pose(boss);
                if (reference != null) {
                    boolean same = true;
                    for (int k = 0; k < current[i].length; k++) same &= Math.abs(current[i][k] - reference[i][k]) <= 1;
                    check(same, "same elapsed time gives the same boss/chain pose, fps=" + fps + ", t=" + time);
                }
            }
            if (reference == null) reference = current;
        }
        int[][] endpoints = {{53760, 42880, 48768, 42880}, {56832, 43136, 56832, 48128},
                {56832, 42880, 61824, 42880}, {53760, 43136, 53760, 48128}};
        for (int i = 0; i < endpoints.length; i++) {
            int[] value = reference[i + 2], expected = endpoints[i];
            check(value[0] == expected[0] && value[1] == expected[1] && value[10] == expected[2] && value[11] == expected[3],
                    "authored attack endpoint and period preserved: phase " + i);
        }
    }

    private static void velocityJoins() throws Exception {
        double h = 0.01;
        for (boolean deploy : new boolean[]{false, true}) {
            double start = deploy ? 17.0 * GameTime.ASSET_TIME_UNIT_SECONDS : 0.0;
            double[] boundaries = deploy ? new double[]{start, start + PHASE, start + PHASE * 2}
                    : new double[]{PHASE, PHASE * 2, PHASE * 3, PHASE * 4};
            for (double boundary : boundaries) {
                BossF1 boss = prepare(deploy);
                advance(boss, 144, boundary - h * 2);
                int[] p0 = pose(boss);
                advance(boss, 144, h);
                int[] p1 = pose(boss);
                advance(boss, 144, h * 2);
                int[] p2 = pose(boss);
                advance(boss, 144, h);
                int[] p3 = pose(boss);
                double beforeX = (p1[10] - p0[10]) / (64.0 * h), beforeY = (p1[11] - p0[11]) / (64.0 * h);
                double afterX = (p3[10] - p2[10]) / (64.0 * h), afterY = (p3[11] - p2[11]) / (64.0 * h);
                check(Math.hypot(afterX - beforeX, afterY - beforeY) < 25,
                        "no velocity snap at swing/deployment join " + boundary + ", intro=" + deploy);
            }
        }
    }

    private static void pauseAndDraw() throws Exception {
        BossF1 boss = prepare(false);
        advance(boss, 60, 0.37);
        int[] before = pose(boss);
        GameObject.IsGamePause = true;
        advance(boss, 144, 1.0);
        check(java.util.Arrays.equals(before, pose(boss)), "pause freezes pendulum time and its collider");
        for (int i = 0; i < 4; i++) boss.draw(graphics);
        check(java.util.Arrays.equals(before, pose(boss)), "drawing does not advance the pendulum");
        GameObject.IsGamePause = false;
        step(boss, 0);
        check(java.util.Arrays.equals(before, pose(boss)), "zero delta cannot advance or change a swing phase");
        step(boss, 1.0 / 60);
        check(!java.util.Arrays.equals(before, pose(boss)), "resume continues without catching up the paused second");
    }
    public static void main(String[] args) throws Exception {
        for (int fps : new int[]{10, 30, 60, 90, 120, 144, -1}) continuity(fps, false);
        for (int fps : new int[]{30, 60, 144, -1}) continuity(fps, true);
        rateIndependence(); velocityJoins(); pauseAndDraw();
        System.out.println("PASS: " + checks + " BossF1 motion assertions (real final-stage actor/resources, headless graphics).");
    }
}
