import GameEngine.time.GameTime;
import GameEngine.Key;
import GameEngine.TouchKeyRange;
import SonicGBA.GlobalResource;
import Lib.Animation;
import Lib.AnimationDrawer;
import State.State;
import State.TitleState;
import PlatformStandard.Standard2;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFComponent;
import com.sega.mobile.framework.device.MFGamePad;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.opengl.GLGraphics;
import java.lang.reflect.Field;

/** Runs the actual TitleState, input and animation code against real repository assets.
 * Only OS services/audio and pixel output are substituted. Not an Android/GPU runtime test.
 */
public final class MenuRegressionTests {
    private static int assertions;
    private static int interval;
    private static final double[] IRREGULAR = {0.007, 0.017, 0.033, 0.1, 0.011, 0.024};
    private static double referenceIntroSeconds = Double.NaN;
    private static final GLGraphics sink = new GLGraphics();
    private static final MFGraphics graphics = MFGraphics.createMFGraphics(sink, 284, 160);

    private static void check(boolean condition, String message) {
        assertions++;
        if (!condition) throw new AssertionError(message);
    }
    private static Field field(String name) throws Exception {
        Field f = TitleState.class.getDeclaredField(name);
        f.setAccessible(true);
        return f;
    }
    private static int number(TitleState title, String name) throws Exception { return field(name).getInt(title); }
    private static boolean flag(TitleState title, String name) throws Exception { return field(name).getBoolean(title); }
    private static void step(TitleState title, double seconds) {
        GameTime.beginFrame(seconds);
        MFDevice.tick();
        Animation.updateAll();
        AnimationDrawer.updateAll();
        title.logic();
        graphics.reset();
        sink.clearCommands();
        title.draw(graphics);
    }
    private static double delta(int fps) {
        return fps > 0 ? 1.0 / fps : IRREGULAR[interval++ % IRREGULAR.length];
    }
    private static void waitFor(TitleState title, int fps, double seconds) {
        double elapsed = 0.0;
        while (elapsed + 1e-10 < seconds) {
            double dt = Math.min(delta(fps), seconds - elapsed);
            step(title, dt);
            elapsed += dt;
        }
    }
    private static void key(TitleState title, int fps, int mask) {
        MFGamePad.pressVisualKey(mask);
        step(title, delta(fps));
        MFGamePad.releaseVisualKey(mask);
        step(title, delta(fps));
    }
    private static void click(TitleState title, int fps, TouchKeyRange target) {
        int x = target.range_x + target.range_w / 2, y = target.range_y + target.range_h / 2;
        for (MFComponent c : MFDevice.componentVector) c.pointerPressed(0, x, y);
        step(title, delta(fps));
        for (MFComponent c : MFDevice.componentVector) c.pointerReleased(0, x, y);
        step(title, delta(fps));
    }

    private static void characterSelection(int fps) throws Exception {
        GameTime.reset();
        MFGamePad.resetKeys();
        MFDevice.removeAllComponents();
        TitleState title = new TitleState();
        title.initCharacterSelectRes();
        field("state").setInt(null, 23);
        field("nextState").setInt(title, 23);
        State.fadeInit(0, 0);
        waitFor(title, fps, 1.5);
        check(number(title, "character_sel_frame_cnt") > 16, "selection entrance completes at " + fps);
        check(number(title, "character_id") == 0, "initial character at " + fps);
        key(title, fps, 32);
        waitFor(title, fps, 1.5);
        check(number(title, "character_id") == 1, "first right selection at " + fps);
        check(!flag(title, "character_move"), "character change must unlock input, not wait for a looping animation at " + fps);
        key(title, fps, 32);
        waitFor(title, fps, 1.5);
        check(number(title, "character_id") == 2, "second right selection at " + fps);
        check(!flag(title, "character_move"), "second character change completes at " + fps);
        key(title, fps, 16);
        waitFor(title, fps, 1.5);
        check(number(title, "character_id") == 1, "left selection still works at " + fps);
        check(!flag(title, "character_move"), "left character change completes at " + fps);
        click(title, fps, Key.touchcharselrightarrow);
        waitFor(title, fps, 1.5);
        check(number(title, "character_id") == 2 && !flag(title, "character_move"), "touch right works after keyboard navigation at " + fps);
        click(title, fps, Key.touchcharselleftarrow);
        waitFor(title, fps, 1.5);
        check(number(title, "character_id") == 1 && !flag(title, "character_move"), "touch left works repeatedly at " + fps);
        click(title, fps, Key.touchcharselselect);
        check(flag(title, "character_outer"), "touch confirm starts the chosen character's exit at " + fps);
        AnimationDrawer role = (AnimationDrawer) field("charSelRoleDrawer").get(title);
        check(role.getActionId() == 10 && !role.getLoop(), "confirmation plays the correct finite character animation at " + fps);
        title.initCharacterSelectRes();
        GameTime.beginFrame(0.1);
        AnimationDrawer.updateAll();
        AnimationDrawer box = (AnimationDrawer) field("charSelCaseDrawer").get(title);
        check(box.getCurrentFrame() == 0 && role.getCurrentFrame() == 0, "reused entrances do not run behind the previous screen at " + fps);
        waitFor(title, fps, 1.5);
        key(title, fps, 32);
        waitFor(title, fps, 1.5);
        check(number(title, "character_id") == 1 && !flag(title, "character_move"), "re-entered selection remains usable at " + fps);
        title.close();
        Key.touchCharacterSelectModeClose();
    }
    private static String draw(AnimationDrawer drawer) {
        sink.clearCommands();
        drawer.draw(graphics, 0, 0);
        return sink.commands.toString();
    }

    private static void openingMotion() {
        GameTime.reset();
        Animation[] assets = Animation.getInstanceFromQi("/animation/opening/opening.dat");
        AnimationDrawer drawer = assets[0].getDrawer(0, false, 0);
        String original = draw(drawer);
        GameTime.beginFrame(GameTime.ASSET_TIME_UNIT_SECONDS / 4);
        AnimationDrawer.updateAll();
        check(draw(drawer).equals(original), "ordinary sprite playback stays discrete unless explicitly enabled");
        drawer.setInterpolateMotion(true);
        String quarter = draw(drawer);
        check(!quarter.equals(original), "nested Qi opening layers move between authored poses");
        check(drawer.getCurrentFrame() == 0, "tweening does not speed up the animation timeline");
        check(draw(drawer).equals(quarter), "drawing twice does not advance an intro animation");
        GameTime.beginFrame(0);
        AnimationDrawer.updateAll();
        check(draw(drawer).equals(quarter), "paused intro retains exactly the same motion sample");
        drawer.restart();
        GameTime.beginFrame(0.1);
        AnimationDrawer.updateAll();
        check(drawer.getCurrentFrame() == 0 && !drawer.checkEnd(), "restarted hidden drawers wait for activation");
        check(draw(drawer).equals(original), "restart begins with the original first pose");
        Animation.closeAnimationDrawer(drawer);
        Animation.closeAnimationArray(assets);
    }

    private static void openingCompletes(int fps, int language, boolean skip) throws Exception {
        GameTime.reset();
        MFGamePad.resetKeys();
        MFDevice.removeAllComponents();
        GlobalResource.languageConfig = language;
        TitleState title = new TitleState();
        title.init();
        Key.touchOpeningInit();
        field("state").setInt(null, 3);
        field("nextState").setInt(title, 3);
        State.fadeInit(0, 0);
        boolean[] visited = new boolean[7];
        if (skip) key(title, fps, 16777216);
        int frames = 0;
        while (number(title, "state") != 1 && frames < fps * 60) {
            int phase = number(title, "openingState");
            if (phase >= 0 && phase < visited.length) visited[phase] = true;
            step(title, delta(fps));
            frames++;
        }
        check(number(title, "state") == 1, "intro reaches title at " + fps + ", language " + language + ", skip " + skip);
        if (!skip) {
            for (int phase = 0; phase <= 5; phase++) check(visited[phase], "intro visits phase " + phase + " at " + fps);
            if (language == 0) {
                if (Double.isNaN(referenceIntroSeconds)) referenceIntroSeconds = GameTime.elapsedSeconds();
                else check(Math.abs(GameTime.elapsedSeconds() - referenceIntroSeconds) < 0.25,
                        "intro duration is stable across render rates (apart from transition-frame granularity)");
            }
        } else {
            check(frames < fps * 2, "skipping intro does not wait for hidden animations");
        }
        title.close();
        GlobalResource.languageConfig = 0;
    }

    private static void splashProgress() throws Exception {
        GameTime.reset();
        MFGamePad.resetKeys();
        MFDevice.removeAllComponents();
        Standard2.splashinit(true);
        Field splash = Standard2.class.getDeclaredField("splashState");
        Field state = Standard2.class.getDeclaredField("state");
        Field count = Standard2.class.getDeclaredField("count");
        splash.setAccessible(true); state.setAccessible(true); count.setAccessible(true);
        for (int i = 0; i < 900; i++) {
            GameTime.beginFrame(1.0 / 60);
            Standard2.splashLogic();
            if (splash.getInt(null) == 3 && state.getInt(null) == 1 && count.getInt(null) == 10) break;
        }
        check(splash.getInt(null) == 3 && state.getInt(null) == 1, "both splash screens progress");
        sink.clearCommands();
        Standard2.splashDraw(graphics, 284, 160);
        String before = sink.commands.toString();
        int wholeCount = count.getInt(null);
        GameTime.beginFrame(0.005);
        Standard2.splashLogic();
        sink.clearCommands();
        Standard2.splashDraw(graphics, 284, 160);
        check(count.getInt(null) == wholeCount, "probe stays within a single authored time unit");
        check(!sink.commands.toString().equals(before), "splash motion uses fractional elapsed time, not 63-ms stairs");
        Standard2.close();
    }

    private static void splashFade() {
        GameTime.reset();
        Standard2.fadeInit(220, 0);
        GameTime.beginFrame(1.0 / 60);
        graphics.setColor(0x336699); graphics.setAlpha(93);
        sink.clearCommands();
        Standard2.drawFade(graphics);
        check(sink.rgbCalls == 0, "GPU splash fade must not allocate/upload bitmap tiles");
        check(sink.rectCalls == 1, "GPU splash fade is one solid rectangle");
        check(graphics.getColor() == 0x336699 && graphics.getAlpha() == 93, "splash fade restores graphics state");
    }

    public static void main(String[] arguments) throws Exception {
        for (int fps : new int[]{10, 30, 60, 120, 144, -1}) characterSelection(fps);
        splashProgress();
        splashFade();
        openingMotion();
        openingCompletes(60, 0, false);
        openingCompletes(144, 0, false);
        openingCompletes(60, 6, false);
        openingCompletes(60, 8, false);
        openingCompletes(60, 0, true);
        System.out.println("PASS: " + assertions + " menu integration assertions (real code/assets; headless host/graphics/audio).");
    }
}
