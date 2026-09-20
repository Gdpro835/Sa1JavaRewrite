import GameEngine.Key;
import GameEngine.TouchKeyRange;
import GameEngine.time.GameTime;
import Lib.Animation;
import Lib.AnimationDrawer;
import State.State;
import State.TitleState;
import SonicGBA.GameObject;
import SonicGBA.GlobalResource;
import SonicGBA.StageManager;
import Lib.SoundSystem;
import Lib.MyAPI;
import com.sega.mobile.framework.device.MFComponent;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFGamePad;
import com.sega.mobile.framework.device.MFGraphics;
import com.sega.mobile.framework.opengl.GLGraphics;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/** Actual settings/main/stage/help menu code and assets, with the same headless boundaries as MenuRegressionTests. */
public final class OtherMenuRegressionTests {
    private static int assertions;
    private static final GLGraphics sink = new GLGraphics();
    private static final MFGraphics graphics = MFGraphics.createMFGraphics(sink, 284, 160);

    private static void check(boolean ok, String message) {
        assertions++;
        if (!ok) throw new AssertionError(message);
    }
    private static Field field(String name) throws Exception {
        Class<?> type = TitleState.class;
        while (type != null) {
            try { Field f = type.getDeclaredField(name); f.setAccessible(true); return f; }
            catch (NoSuchFieldException e) { type = type.getSuperclass(); }
        }
        throw new NoSuchFieldException(name);
    }
    private static int number(TitleState title, String name) throws Exception { return field(name).getInt(title); }
    private static void call(TitleState title, String name) throws Exception {
        Method method = TitleState.class.getDeclaredMethod(name);
        method.setAccessible(true);
        method.invoke(title);
    }
    private static void tick(TitleState title, double delta) {
        GameTime.beginFrame(delta);
        MFDevice.tick();
        Animation.updateAll();
        AnimationDrawer.updateAll();
        title.logic();
        draw(title);
    }
    private static void draw(TitleState title) {
        graphics.reset();
        sink.clearCommands();
        title.draw(graphics);
    }
    private static TitleState settings() throws Exception {
        GameTime.reset();
        MFGamePad.resetKeys();
        TitleState title = new TitleState();
        call(title, "optionInit");
        field("state").setInt(null, 9);
        field("nextState").setInt(title, 9);
        State.fadeInit(0, 0);
        return title;
    }
    private static void close(TitleState title) {
        title.close();
        if (Key.touchmenuoptionitems != null) Key.touchMenuOptionClose();
        if (Key.touchstageselectitem != null) Key.touchStageSelectModeClose();
        Key.touchPadOptionClose();
        Key.touchkeyboardClose();
        MFGamePad.resetKeys();
    }

    private static void pointer(int kind, int x, int y) {
        for (MFComponent c : new ArrayList<MFComponent>(MFDevice.componentVector)) {
            if (kind == 0) c.pointerPressed(0, x, y);
            else if (kind == 1) c.pointerDragged(0, x, y);
            else c.pointerReleased(0, x, y);
        }
    }
    private static void click(TitleState title, TouchKeyRange key) {
        int x = key.range_x + key.range_w / 2, y = key.range_y + key.range_h / 2;
        pointer(0, x, y); tick(title, 1.0 / 60);
        pointer(2, x, y); tick(title, 1.0 / 60);
    }
    private static void key(TitleState title, int mask) {
        MFGamePad.pressVisualKey(mask); tick(title, 1.0 / 60);
        MFGamePad.releaseVisualKey(mask); tick(title, 1.0 / 60);
    }

    private static void blueBand() throws Exception {
        TitleState title = settings();
        for (int i = 0; i < 160; i++) {
            tick(title, 1.0 / 60);
            int left = Integer.MAX_VALUE, right = Integer.MIN_VALUE;
            // The real lang0/mui asset's blue header tile: source (0,421), size 100x20.
            for (String command : sink.commands) {
                if (command.startsWith("image:0:421:100:20:0:")) {
                    int x = Integer.parseInt(command.split(":")[6]);
                    left = Math.min(left, x); right = Math.max(right, x + 100);
                }
            }
            check(left <= 0 && right >= 284, "blue header must cover both edges at every wrap phase; left=" + left);
        }
        close(title);
    }
    private static void arrowContinuity(int fps) throws Exception {
        TitleState title = settings();
        tick(title, 1.0 / fps);
        MFGamePad.pressVisualKey(8);
        int previous = 0;
        boolean moved = false;
        for (int i = 0; i < fps; i++) {
            tick(title, 1.0 / fps);
            int current = number(title, "optionDrawOffsetY") + number(title, "optionslide_y") + number(title, "optionArrowDriveY");
            check(current <= previous, "settings must not jump backward when a 24-pixel row is committed at " + fps + ": " + previous + " -> " + current);
            moved |= current < 0;
            previous = current;
        }
        check(moved, "settings arrow scrolls at " + fps);
        check(sink.getSaveDepth() == 0, "menu scroll restores its graphics transform at " + fps);
        close(title);
    }
    private static void drawIsReadOnly() throws Exception {
        TitleState title = settings();
        tick(title, 1.0 / 60);
        List<String> before = new ArrayList<String>(sink.commands);
        draw(title);
        check(sink.commands.equals(before), "redrawing settings must not advance its animated header");
        close(title);
    }
    private static void previewDoesNotReload() throws Exception {
        TitleState title = settings();
        title.touchPadInit();
        field("state").setInt(null, 28);
        field("nextState").setInt(title, 28);
        tick(title, 1.0 / 60);
        int loads = MFDevice.resourceReads;
        Object control = Key.touchdirectgamekey;
        for (int i = 0; i < 10; i++) tick(title, 1.0 / 60);
        check(MFDevice.resourceReads == loads, "idle control preview must not reload textures/JSON on every draw");
        check(control == Key.touchdirectgamekey, "drawing a control preview must not recreate input components");
        int oldSize = GlobalResource.touchKeyBoardSize;
        GlobalResource.touchKeyBoardSize = (oldSize + 1) % 6;
        tick(title, 1.0 / 60);
        check(MFDevice.resourceReads > loads, "changing control size refreshes its asset once");
        loads = MFDevice.resourceReads;
        for (int i = 0; i < 10; i++) tick(title, 1.0 / 60);
        check(MFDevice.resourceReads == loads, "new control size remains cached");
        for (int mode : new int[]{27, 30}) {
            field("state").setInt(null, mode); field("nextState").setInt(title, mode);
            title.touchPadInit(); tick(title, 1.0 / 60);
            loads = MFDevice.resourceReads;
            control = Key.touchdirectgamekey;
            draw(title); draw(title);
            check(MFDevice.resourceReads == loads && control == Key.touchdirectgamekey,
                    "position/opacity draw does not reload assets or reset input, state " + mode);
        }
        GlobalResource.touchKeyBoardSize = oldSize;
        close(title);
    }

    private static void stageContinuity(int fps) throws Exception {
        GameTime.reset();
        MFGamePad.resetKeys();
        GameObject.stageModeState = 1;
        TitleState title = new TitleState();
        field("stageDrawStartY").setInt(title, 8);
        call(title, "initStageSelectRes");
        field("state").setInt(null, 14);
        field("nextState").setInt(title, 14);
        State.fadeInit(0, 0);
        int music = SoundSystem.getInstance().bgmStarts;
        for (int i = 0; i < fps * 2; i++) tick(title, 1.0 / fps);
        check(number(title, "stage_select_state") == 1, "stage entrance completes at " + fps);
        check(graphics.getClipY() == 0 && graphics.getClipHeight() == 160,
                "stage list clip is restored for the header and return button at " + fps);
        check(SoundSystem.getInstance().bgmStarts == music + 1, "stage entrance starts music once at " + fps);
        int previous = 0;
        TitleState.preStageSelectState = 24;
        int labelY = number(title, "stageDrawStartY") + 2 * 24 - 4;
        pointer(0, 100, labelY); tick(title, 1.0 / fps);
        check(number(title, "optionMenuCursor") == 1, "stage hitbox includes the upper half of the visible second label at " + fps);
        // Turn this touch into a drag, never confirming/launching a level in the fixture.
        pointer(1, 100, labelY - 18); tick(title, 1.0 / fps);
        pointer(2, 100, labelY - 18); tick(title, 1.0 / fps);
        check(number(title, "state") == 14 && number(title, "optionMenuCursor") == -1, "stage dragging cannot activate a row");
        call(title, "initStageSelectRes");
        for (int i = 0; i < fps * 2; i++) tick(title, 1.0 / fps);
        MFGamePad.pressVisualKey(8);
        for (int i = 0; i < fps; i++) {
            tick(title, 1.0 / fps);
            int current = number(title, "stageDrawOffsetY") + number(title, "stageselectslide_y") + number(title, "stageSelectArrowDriveY");
            check(current <= previous, "stage scroll must not jump backward at " + fps);
            previous = current;
        }
        check(previous < -24, "stage list scrolls through multiple rows at " + fps);
        close(title);
        GameObject.stageModeState = 0;
    }

    private static void mainMenuMotion() throws Exception {
        GameTime.reset();
        MFGamePad.resetKeys();
        TitleState title = new TitleState();
        title.gotoMainmenu();
        for (int i = 0; i < 120; i++) tick(title, 1.0 / 60);
        int cursor = number(title, "mainMenuItemCursor");
        MFGamePad.pressVisualKey(8);
        tick(title, 1.0 / 60);
        check(number(title, "mainMenuItemCursor") != cursor, "main menu changes the selected row");
        check(number(title, "degree") == 20, "main menu transition starts exactly one rendered row apart");
        List<String> before = new ArrayList<String>(sink.commands);
        int degree = number(title, "degree");
        draw(title);
        check(number(title, "degree") == degree && sink.commands.equals(before), "main menu easing is update-owned");
        MFGamePad.releaseVisualKey(8);
        int previous = degree;
        for (int i = 0; i < 120; i++) {
            tick(title, 1.0 / 60);
            check(number(title, "degree") <= previous && number(title, "degree") >= 0, "main menu settles monotonically");
            previous = number(title, "degree");
        }
        check(previous == 0, "main menu reaches the exact resting position");
        close(title);
    }

    private static void settingsGestures() throws Exception {
        TitleState title = settings();
        tick(title, 1.0 / 60);
        int difficulty = GlobalResource.difficultyConfig;
        click(title, Key.touchmenuoptionitems[3]);
        check(GlobalResource.difficultyConfig != difficulty, "a one-frame touch can toggle a setting");
        click(title, Key.touchmenuoptionitems[3]);
        check(GlobalResource.difficultyConfig == difficulty, "setting toggles once per click");
        for (int page = 1; page <= 4; page++) {
            key(title, 32);
            check(number(title, "optionIndex") == page % 4, "settings tabs wrap and render, page " + page);
        }
        pointer(0, 100, 100); tick(title, 1.0 / 60);
        pointer(1, 100, 82); tick(title, 1.0 / 60);
        int dragged = number(title, "optionDrawOffsetY");
        pointer(2, 100, 82); tick(title, 1.0 / 60);
        check(dragged == -18 && number(title, "optionDrawOffsetY") == dragged, "settings release preserves the exact drag displacement");
        check(number(title, "state") == 9, "drag release cannot open the item under the finger");
        check(Key.touchmenuoptionitems[3].range_y == 52 + dragged, "settings touch rectangles follow the displayed rows");
        call(title, "optionInit");
        MFGamePad.pressVisualKey(8);
        MFGamePad.releaseVisualKey(8);
        tick(title, 1.0 / 60);
        check(sink.fractionalTranslationY, "fractional list position reaches the renderer instead of jumping by scaled whole pixels");
        for (int i = 0; i < 60; i++) tick(title, 1.0 / 60);
        check(number(title, "optionDrawOffsetY") == -24, "a press/release arriving within one update still scrolls one row");
        State.fadeInit(200, 0);
        tick(title, 1.0 / 60);
        int alpha = number(title, "fadeAlpha");
        draw(title); draw(title);
        check(number(title, "fadeAlpha") == alpha, "extra background/modal draws cannot accelerate the fade");
        close(title);
    }

    private static void languageAndPages() throws Exception {
        TitleState title = settings();
        tick(title, 1.0 / 60);
        GlobalResource.languageConfig = 0;
        title.newLanguageInit();
        field("state").setInt(null, 39); field("nextState").setInt(title, 39);
        tick(title, 1.0 / 60);
        click(title, Key.touchsecondensureyes);
        check(GlobalResource.languageConfig == 8, "language left wraps 0 to 8 without invalid action ids");
        click(title, Key.touchsecondensureno);
        check(GlobalResource.languageConfig == 0, "language right wraps 8 to 0");
        field("aboutStrings").set(null, MyAPI.loadText("/lang0/about"));
        title.helpInit();
        field("state").setInt(null, 34); field("nextState").setInt(title, 34);
        State.fadeInit(0, 0);
        tick(title, 1.0 / 60);
        int page = number(title, "helpIndex");
        key(title, 32);
        check(number(title, "helpIndex") == page + 1, "help advances to the next page");
        key(title, 16);
        check(number(title, "helpIndex") == page, "help returns to the previous page");
        int x = State.PageBackGroundOffsetX, y = State.PageBackGroundOffsetY, frame = State.PageFrameCnt;
        draw(title); draw(title);
        check(State.PageBackGroundOffsetX == x && State.PageBackGroundOffsetY == y && State.PageFrameCnt == frame,
                "drawing help is not a background animation clock");
        call(title, "creditInit");
        field("state").setInt(null, 35); field("nextState").setInt(title, 35);
        tick(title, 1.0 / 60);
        x = State.PageBackGroundOffsetX; y = State.PageBackGroundOffsetY; frame = State.PageFrameCnt;
        draw(title); draw(title);
        check(State.PageBackGroundOffsetX == x && State.PageBackGroundOffsetY == y && State.PageFrameCnt == frame,
                "drawing credits is not a background animation clock");
        close(title);
    }

    private static void pageBackgroundRates() throws Exception {
        TitleState title = settings();
        field("aboutStrings").set(null, MyAPI.loadText("/lang0/about"));
        for (boolean credits : new boolean[]{false, true}) {
            if (credits) call(title, "creditInit"); else title.helpInit();
            for (int fps : new int[]{30, 60, 144}) {
                GameTime.reset();
                MFGamePad.resetKeys();
                State.PageFrameCnt = GameTime.set(State.class, "PageFrameCnt", 0);
                State.PageBackGroundOffsetX = State.PageBackGroundOffsetY = 0;
                double elapsed = 0.0;
                while (elapsed + 1e-10 < 1.26) {
                    double dt = Math.min(1.0 / fps, 1.26 - elapsed);
                    GameTime.beginFrame(dt);
                    if (credits) call(title, "creditLogic"); else title.helpLogic();
                    elapsed += dt;
                }
                check(State.PageBackGroundOffsetX == 10, "page background advances by time, credits=" + credits + ", fps=" + fps);
                check(State.PageBackGroundOffsetY == 46, "page background wraps without draw-driven speed, credits=" + credits + ", fps=" + fps);
            }
        }
        close(title);
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 0 || args[0].equals("band")) blueBand();
        if (args.length == 0 || args[0].equals("scroll"))
            for (int fps : new int[]{30, 60, 120, 144}) arrowContinuity(fps);
        if (args.length == 0 || args[0].equals("draw")) drawIsReadOnly();
        if (args.length == 0 || args[0].equals("preview")) previewDoesNotReload();
        if (args.length == 0 || args[0].equals("stage")) for (int fps : new int[]{30, 60, 120, 144}) stageContinuity(fps);
        if (args.length == 0 || args[0].equals("main")) mainMenuMotion();
        if (args.length == 0 || args[0].equals("gestures")) settingsGestures();
        if (args.length == 0 || args[0].equals("pages")) languageAndPages();
        if (args.length == 0 || args[0].equals("backgrounds")) pageBackgroundRates();
        System.out.println("PASS: " + assertions + " additional menu assertions (real code/assets, headless graphics).");
    }
}
