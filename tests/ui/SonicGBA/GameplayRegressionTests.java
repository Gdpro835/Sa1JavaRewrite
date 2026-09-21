package SonicGBA;

import GameEngine.Key;
import GameEngine.time.GameTime;
import Lib.Animation;
import Lib.AnimationDrawer;
import Lib.SoundSystem;
import PyxEditor.PyxAnimation;
import State.GameState;
import State.State;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGamePad;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.opengl.GLGraphics;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;

/** Real levels, players, goal/capsule, GameState and animation code. Only host/pixels/audio are doubles. */
public final class GameplayRegressionTests {
    private static int assertions;
    private static int interval;
    private static final double[] IRREGULAR = {0.007, 0.017, 0.033, 0.1, 0.011, 0.024};
    private static final GLGraphics sink = new GLGraphics();
    private static final MFGraphics graphics = MFGraphics.createMFGraphics(sink, 284, 160);

    /** Extra animation API probes, rendered INSIDE the production GameState world scope. */
    private static final class ProbeGameState extends GameState {
        AnimationDrawer worldProbe;
        Animation directProbe;
        PyxAnimation pyxProbe;
        @Override public void drawGame(MFGraphics g) {
            super.drawGame(g);
            if (worldProbe != null) worldProbe.draw(g, 0, 0);
            if (directProbe != null) directProbe.DrawAni(g, 0, 0, (short) 0);
            if (pyxProbe != null) pyxProbe.drawAction(g, 0, 0);
        }
    }

    private static void check(boolean value, String message) {
        assertions++;
        if (!value) throw new AssertionError(message);
    }
    private static Field field(Class<?> type, String name) throws Exception {
        while (type != null) {
            try { Field f = type.getDeclaredField(name); f.setAccessible(true); return f; }
            catch (NoSuchFieldException e) { type = type.getSuperclass(); }
        }
        throw new NoSuchFieldException(name);
    }
    private static int state(GameState game) throws Exception { return field(GameState.class, "state").getInt(game); }
    private static void call(GameState game, String name) throws Exception {
        Method m = GameState.class.getDeclaredMethod(name); m.setAccessible(true); m.invoke(game);
    }
    private static double delta(int fps) { return fps > 0 ? 1.0 / fps : IRREGULAR[interval++ % IRREGULAR.length]; }

    private static void rings() {
        GameTime.reset();
        AnimationDrawer.setAllPause(false);
        AnimationDrawer.setWorldPaused(false);
        GameObject.IsGamePause = false;
        GameObject.camera = MapManager.getCamera();
        RingObject ring = RingObject.getNewInstance(16, 16);
        ring.draw(graphics);
        int start = GameObject.ringDrawer.getCurrentFrame();
        double duration = Math.max(1, GameObject.ringDrawer.getAnimation().getFrameDuration(0, start)) * GameTime.ASSET_TIME_UNIT_SECONDS;
        GameTime.beginFrame(duration);
        AnimationDrawer.updateAll();
        // This is the explicit step used by GameObject.drawObjects for the shared ring drawer.
        GameObject.ringDrawer.moveOn();
        check(GameObject.ringDrawer.getCurrentFrame() != start,
                "shared rings advance explicitly even when automatic playback is disabled");
        int current = GameObject.ringDrawer.getCurrentFrame();
        for (int i = 0; i < 30; i++) ring.draw(graphics);
        GameObject.ringDrawer.moveOn();
        check(GameObject.ringDrawer.getCurrentFrame() == current, "ring/draw count cannot speed up shared playback");
    }

    private static void clearJingle(int fps) throws Exception {
        GameTime.reset();
        PlayerObject.stageModeState = 0;
        PlayerObject.setCharacter(0);
        StageManager.setStageID(0);
        PlayerObject.initMovingBar();
        int starts = SoundSystem.getInstance().bgmStarts;
        double time = 0;
        while (time < 2.0) {
            double dt = delta(fps); GameTime.beginFrame(dt); time += dt;
            PlayerObject.movingBar();
            int x = field(PlayerObject.class, "offsetx").getInt(null);
            int y = field(PlayerObject.class, "offsety").getInt(null);
            PlayerObject.movingBar();
            check(x == field(PlayerObject.class, "offsetx").getInt(null) && y == field(PlayerObject.class, "offsety").getInt(null),
                    "repeated result draw cannot advance the same bar twice");
        }
        check(SoundSystem.getInstance().bgmStarts == starts + 1, "clear jingle starts once without exact-position polling at " + fps);
        check(PlayerObject.movingBar(), "result banner reaches its exact resting position at " + fps);
    }

    private static ProbeGameState bootWorld(int stage, int character) throws Exception {
        GameTime.reset(); MFGamePad.resetKeys();
        PlayerObject.setCharacter(character);
        StageManager.setStageID(stage);
        GameObject.stageModeState = 0;
        GameObject.IsGamePause = false;
        AnimationDrawer.setAllPause(false);
        AnimationDrawer.setWorldPaused(false);
        ProbeGameState game = new ProbeGameState();
        game.init();
        boolean loaded = false;
        for (int i = 0; i < 100 && !loaded; i++) loaded = StageManager.loadStageStep();
        check(loaded && GameObject.player != null, "real level and player loaded");
        call(game, "initStageInfoClearRes");
        GameObject.player.headInit(); Key.touchkeypauseInit();
        field(GameState.class, "state").setInt(game, 0);
        State.fadeInit(0, 0); PlayerObject.initStageParam(); StageManager.stagePassInit();
        return game;
    }
    private static void frame(GameState game, double dt) {
        GameTime.beginFrame(dt); MFDevice.tick();
        AnimationDrawer.setWorldPaused(game.isWorldAnimationPaused());
        Animation.updateAll(); AnimationDrawer.updateAll(); PyxAnimation.updateAll();
        game.logic();
        graphics.reset(); sink.clearCommands(); game.draw(graphics);
    }
    private static void waitFor(GameState game, int fps, double seconds) {
        double time = 0;
        while (time + 1e-10 < seconds) {
            double dt = Math.min(delta(fps), seconds - time); frame(game, dt); time += dt;
        }
    }
    private static int pyxFrame(PyxAnimation animation) throws Exception {
        Object[] actions = (Object[]) field(PyxAnimation.class, "actionArray").get(animation);
        int id = field(PyxAnimation.class, "currentAction").getInt(animation);
        return field(actions[id].getClass(), "frame").getInt(actions[id]);
    }

    private static void pauseAndResume(int fps) throws Exception {
        ProbeGameState game = bootWorld(0, 0);
        game.worldProbe = new Animation("/animation/ring").getDrawer(0, true, 0);
        game.directProbe = new Animation("/animation/ring");
        game.directProbe.SetLoop(true);
        game.pyxProbe = new PyxAnimation("/animation/aaa.pyx", new Animation[]{new Animation("/animation/boss_extra")});
        game.pyxProbe.setAction(0); game.pyxProbe.setLoop(true);
        AnimationDrawer ui = new Animation("/animation/ring").getDrawer(0, true, 0);
        waitFor(game, fps, 0.5);
        game.pause();
        check(state(game) == 1 && game.isWorldAnimationPaused(), "real pause menu stops the world animation clock");
        int playerFrame = GameObject.player.drawer.getCurrentFrame();
        int worldFrame = game.worldProbe.getCurrentFrame(), directFrame = game.directProbe.GetFrameNo();
        int pyxFrame = pyxFrame(game.pyxProbe), ringFrame = GameObject.ringDrawer.getCurrentFrame();
        long worldClock = GameObject.systemClock;
        boolean frozen = true, uiMoved = false;
        ui.draw(graphics, 0, 0);
        int firstUiFrame = ui.getCurrentFrame();
        double time = 0;
        while (time < 1.0) {
            double dt = delta(fps); frame(game, dt); time += dt;
            ui.draw(graphics, 0, 0);
            frozen &= GameObject.player.drawer.getCurrentFrame() == playerFrame
                    && game.worldProbe.getCurrentFrame() == worldFrame
                    && game.directProbe.GetFrameNo() == directFrame && pyxFrame(game.pyxProbe) == pyxFrame
                    && GameObject.ringDrawer.getCurrentFrame() == ringFrame && GameObject.systemClock == worldClock;
            uiMoved |= ui.getCurrentFrame() != firstUiFrame;
        }
        check(frozen, "player, rings, direct actions and Pyx stay frozen throughout pause at " + fps);
        check(uiMoved, "pause-menu UI retains its own live animation clock at " + fps);
        MFGamePad.pressVisualKey(524288); frame(game, delta(fps));
        MFGamePad.releaseVisualKey(524288); waitFor(game, fps, 0.6);
        check(state(game) == 0 && !game.isWorldAnimationPaused(), "back/resume restores gameplay");
        check(GameObject.systemClock > worldClock, "world resumes instead of catching up paused time");
        check(!AnimationDrawer.isAllPause() && !AnimationDrawer.isWorldContext(), "world draw scope is restored after rendering");
        // Application interrupts can stop gameplay without setting the old IsGamePause flag.
        field(GameState.class, "interrupt_state").setInt(game, 0);
        field(GameState.class, "state").setInt(game, 18);
        call(game, "interruptInit");
        check(game.isWorldAnimationPaused(), "interrupt screen also freezes world animation");
        worldFrame = game.worldProbe.getCurrentFrame(); directFrame = game.directProbe.GetFrameNo();
        pyxFrame = pyxFrame(game.pyxProbe); worldClock = GameObject.systemClock;
        int mapFrame = MapManager.gameFrame;
        waitFor(game, fps, 0.5);
        check(game.worldProbe.getCurrentFrame() == worldFrame && game.directProbe.GetFrameNo() == directFrame
                && pyxFrame(game.pyxProbe) == pyxFrame && GameObject.systemClock == worldClock && MapManager.gameFrame == mapFrame,
                "interrupt freezes rendering-side world counters as well as all animation APIs");
        Animation.closeAnimationDrawer(ui);
    }

    private static void completionWhilePaused() throws Exception {
        for (int animation : new int[]{35, 37}) {
            ProbeGameState game = bootWorld(0, 0);
            PlayerObject player = GameObject.player;
            if (animation == 35) player.setTerminal(0); else player.setCelebrate();
            player.animationID = animation;
            player.draw(graphics, true);
            player.drawer.setEnd(); // Exact boundary: completion is pending when pause arrives.
            game.pause();
            waitFor(game, 60, 0.5);
            check(!StageManager.isStagePass(), "paused draw cannot commit a pending goal/capsule completion");
        }
    }

    private static void countdowns() throws Exception {
        GameTime.reset();
        for (String name : new String[]{"stageRestartFlag", "stageGameoverFlag"})
            field(StageManager.class, name).setBoolean(null, false);
        StageManager.setStageRestart(); StageManager.setStageGameover();
        for (int i = 0; i < 8; i++) { GameTime.beginFrame(0.1); StageManager.stageLogic(); }
        check(StageManager.isStageRestart() && StageManager.isStageGameover(),
                "variable-time terminal countdowns clamp at zero instead of stalling below it");
    }

    private static void awaitResults(ProbeGameState game, int fps, int cue, Cage cage) throws Exception {
        int cueStart = SoundSystem.getInstance().bgmCues.size();
        boolean entered = false, visible = false, animalsFreed = cage == null;
        double time = 0;
        while (time < 18 && state(game) != 35) {
            double dt = delta(fps); frame(game, dt); time += dt;
            if (state(game) == 4) {
                entered = true;
                visible |= field(PlayerObject.class, "offsetx").getInt(null) == 0
                        && field(PlayerObject.class, "offsety").getInt(null) == (160 / 2) - 36
                        && GameState.guiAniDrawer.getActionId() == 7 && !sink.commands.isEmpty();
            }
            if (cage != null) animalsFreed |= field(Cage.class, "animalCount").getInt(cage) == 0;
        }
        int cues = 0;
        for (int i = cueStart; i < SoundSystem.getInstance().bgmCues.size(); i++)
            if (SoundSystem.getInstance().bgmCues.get(i) == cue) cues++;
        check(entered, "finish signal reaches GameState's result state at " + fps);
        check(visible, "actual result banner and score rows are drawn at " + fps);
        check(cues == 1, "correct clear-act jingle is requested exactly once at " + fps);
        check(animalsFreed, "capsule releases all animal batches");
        check(state(game) == 35, "results finish and enter next-stage transition at " + fps);
    }
    private static void goal(int fps, int character) throws Exception {
        ProbeGameState game = bootWorld(0, character);
        Terminal goal = null;
        for (Vector[] column : GameObject.allGameObject)
            for (Vector cell : column)
                for (Object item : cell) if (item instanceof Terminal) goal = (Terminal) item;
        check(goal != null, "actual first-stage goal loaded");
        GameObject.setPlayerPosition((goal.posX >> 6) - 30, (goal.posY >> 6) - 24);
        PlayerObject player = GameObject.player;
        player.animationID = 0; player.myAnimationID = 0;
        player.totalVelocity = PlayerObject.MAX_VELOCITY; player.velX = PlayerObject.MAX_VELOCITY;
        MapManager.focusQuickLocation(); goal.doWhileCollision(player, 0);
        check(PlayerObject.isTerminal, "real goal collision starts the finish sequence");
        awaitResults(game, fps, 26, null);
    }
    private static void capsule(int fps, int character, boolean hammer) throws Exception {
        ProbeGameState game = bootWorld(1, character);
        waitFor(game, fps, 2);
        PlayerObject player = GameObject.player;
        // Bypass the boss fight only; use the real cage, button, loaded terrain and animal logic.
        Cage cage = new Cage(player.posX + (60 << 6), player.posY);
        GameObject.addGameObject(cage); cage.doWhileTouchGround(0, 0);
        CageButton button = (CageButton) field(Cage.class, "button").get(cage);
        player.setFootPositionX(button.posX); player.setFootPositionY(button.posY - 896);
        if (hammer) button.doWhileBeAttack(player, 0, 0); else button.doWhileCollision(player, 1);
        check(button.used && player.isCelebrate, "real capsule button starts celebration");
        awaitResults(game, fps, 27, cage);
    }

    public static void main(String[] args) throws Throwable {
        String mode = args.length == 0 ? "all" : args[0];
        PrintStream output = System.out;
        ByteArrayOutputStream trace = new ByteArrayOutputStream();
        System.setOut(new PrintStream(trace));
        try {
            if (mode.equals("all") || mode.equals("rings")) rings();
            if (mode.equals("all") || mode.equals("countdowns")) countdowns();
            if (mode.equals("all") || mode.equals("bar")) for (int fps : new int[]{10, 30, 60, 120, 144, -1}) clearJingle(fps);
            if (mode.equals("all") || mode.equals("pause")) {
                for (int fps : new int[]{30, 60, 144, -1}) pauseAndResume(fps);
                completionWhilePaused();
            }
            if (mode.equals("all") || mode.equals("goal")) {
                for (int character = 0; character < 4; character++) goal(60, character);
                for (int fps : new int[]{10, 30, 120, 144, -1}) goal(fps, 0);
            }
            if (mode.equals("all") || mode.equals("cage")) {
                for (int character = 0; character < 4; character++) capsule(60, character, false);
                for (int fps : new int[]{10, 30, 120, 144, -1}) capsule(fps, 0, false);
                capsule(60, 3, true);
            }
            String messages = trace.toString("UTF-8");
            check(!messages.contains("is out of bounds") && !messages.contains("m_FrameInfo") && !messages.contains("Exception"),
                    "production animation loaders/drawers must not silently report asset errors");
        } catch (Throwable failure) {
            output.print(trace.toString("UTF-8"));
            throw failure;
        } finally {
            System.setOut(output);
        }
        System.out.println("PASS: " + assertions + " gameplay regression assertions (real code/levels; headless host/pixels/audio).");
    }
}
