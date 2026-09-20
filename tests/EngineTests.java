import GameEngine.time.AnimationTimeline;
import GameEngine.time.FrameClock;
import GameEngine.time.GameTime;
import GameEngine.time.MenuScroll;
import GameEngine.time.RateAccumulator;
import com.sega.mobile.framework.opengl.SpriteTransform;
import com.sega.engine.action.*;
import java.util.Arrays;

/** Headless regression tests of the production time and movement code. No Android stubs. */
public final class EngineTests {
    private static int assertions;
    private static final double UNIT = GameTime.ASSET_TIME_UNIT_SECONDS;
    private interface Step { void update(); }

    private static void check(boolean condition, String message) {
        assertions++;
        if (!condition) throw new AssertionError(message);
    }
    private static void near(double actual, double expected, double tolerance, String message) {
        check(Math.abs(actual - expected) <= tolerance, message + ": expected " + expected + ", got " + actual);
    }
    private static void simulate(double seconds, double fps, Step step) {
        double elapsed = 0;
        int index = 0;
        while (elapsed + 1e-10 < seconds) {
            double dt = fps > 0 ? 1.0 / fps : new double[]{0.007, 0.017, 0.033, 0.1, 0.011, 0.024}[index++ % 6];
            dt = Math.min(dt, seconds - elapsed);
            GameTime.beginFrame(dt);
            step.update();
            elapsed += dt;
        }
    }

    private static void clock() {
        FrameClock clock = new FrameClock();
        near(clock.advance(1_000_000_000L), 0, 0, "first frame");
        near(clock.advance(1_016_000_000L), 0.016, 1e-12, "actual delta");
        near(clock.advance(1_057_000_000L), 0.041, 1e-12, "variable delta, not 63 ms ticks");
        near(clock.advance(6_057_000_000L), 0.1, 1e-12, "hitch cap");
        near(clock.getDroppedSeconds(), 4.9, 1e-12, "dropped time is observable");
        clock.reset();
        near(clock.advance(600_000_000_000L), 0, 0, "no background catch-up");
        near(clock.advance(599_000_000_000L), 0, 0, "non-increasing timestamp");
        near(clock.advance(599_010_000_000L), 0.01, 1e-12, "clock recovers");
    }

    private static void clockDoesNotSkipDisplayFrames() {
        for (int fps : new int[]{30, 60, 90, 120, 144}) {
            FrameClock clock = new FrameClock();
            clock.advance(0L);
            long previous = 0L;
            int updates = 0;
            double elapsed = 0.0;
            boolean accurate = true;
            for (int frame = 1; frame <= fps; frame++) {
                long now = Math.round(frame * 1_000_000_000.0 / fps);
                double delta = clock.advance(now);
                if (delta > 0.0) updates++;
                accurate &= Math.abs(delta - (now - previous) / 1_000_000_000.0) < 1e-12;
                elapsed += delta;
                previous = now;
            }
            check(updates == fps, "no 15-FPS gate: every display callback advances time at " + fps);
            check(accurate, "short deltas are not quantized to an authored animation frame at " + fps);
            near(elapsed, 1.0, 1e-12, "no time lost to frame skipping at " + fps);
            near(clock.getDroppedSeconds(), 0.0, 0.0, "display intervals are never dropped at " + fps);
        }
    }

    private static void rates() {
        for (final double fps : new double[]{10, 12, 20, 30, 60, 90, 120, 144, -1}) {
            GameTime.reset();
            final Object owner = new Object();
            final int[] values = new int[5];
            simulate(6.3, fps, new Step() {
                public void update() {
                    values[0] = GameTime.advance(owner, "x", values[0], 320);
                    values[1] = GameTime.advance(owner, "left", values[1], -1);
                    values[2] = GameTime.advance(owner, "timer", values[2], 1);
                    values[3] = GameTime.milliseconds(owner, "milliseconds", values[3], 1);
                    values[4] = GameTime.advanceOnce(owner, "shared", values[4], 1);
                    values[4] = GameTime.advanceOnce(owner, "shared", values[4], 1);
                }
            });
            check(Arrays.equals(values, new int[]{32000, -100, 100, 6300, 100}), "rates / two players at " + fps + ": " + Arrays.toString(values));
        }
        GameTime.reset();
        Object owner = new Object();
        GameTime.beginFrame(UNIT / 2);
        int timer = GameTime.advance(owner, "reset", 0, 1);
        check(timer == 0, "fractional timer starts below one");
        timer = GameTime.set(owner, "reset", 0);
        GameTime.beginFrame(UNIT / 2);
        timer = GameTime.advance(owner, "reset", timer, 1);
        check(timer == 0, "same-value reset discards old carry");
        GameTime.beginFrame(0);
        check(GameTime.advance(owner, "reset", timer, 100) == timer, "pause cannot advance counters");
        final int[] slow = new int[1];
        GameTime.reset();
        simulate(6.3, 60, new Step() {
            public void update() {
                double dt = GameTime.deltaSeconds();
                GameTime.beginFrame(dt / 16);
                slow[0] += GameTime.distance(slow, "slow", 320);
            }
        });
        check(slow[0] == 2000, "bullet time scales elapsed time, not rendering frequency");
        GameTime.beginFrame(UNIT / 2);
        check(GameTime.velocityFromDisplacement(160) == 320, "measured rail displacement returns velocity units");
        GameTime.beginFrame(0);
        check(GameTime.velocityFromDisplacement(160) == 0, "zero-time velocity conversion is safe");
        RateAccumulator fraction = new RateAccumulator();
        boolean rejected = false;
        try { fraction.integrate(Double.NaN, 0.1); } catch (IllegalArgumentException expected) { rejected = true; }
        check(rejected, "non-finite rate rejected");
        rejected = false;
        try { GameTime.beginFrame(-0.1); } catch (IllegalArgumentException expected) { rejected = true; }
        check(rejected, "negative time rejected");
    }

    private static void events() {
        for (double fps : new double[]{10, 12, 20, 30, 60, 120, 144, -1}) {
            GameTime.reset();
            final Object owner = new Object();
            final int[] values = new int[5];
            simulate(6.3, fps, new Step() {
                public void update() {
                    if (GameTime.event(owner, "timer", "initialShot", values[0] == 0)) values[1]++;
                    values[0] = GameTime.advance(owner, "timer", values[0], 1);
                    if (GameTime.event(owner, "timer", "phase", GameTime.crosses(owner, "timer", values[0], 17))) values[2]++;
                    values[3] += GameTime.periods(owner, "timer", "emitter", values[0], 10, 0);
                    for (int event : GameTime.steps(owner, "timer", "switchTimeline", values[0])) {
                        if (event == 6 || event == 12 || event == 24) values[4]++;
                    }
                }
            });
            check(values[1] == 1, "no repeated initial shot at " + fps);
            check(values[2] == 1, "one crossed phase at " + fps);
            // The timer is advanced before the emitter: boundaries 10..100, no FPS-dependent initial shot.
            check(values[3] == 10, "periodic boundaries at " + fps + ": " + values[3]);
            check(values[4] == 3, "switch timeline events at " + fps);
        }
        GameTime.reset();
        Object owner = new Object();
        GameTime.beginFrame(0.1);
        int timer = GameTime.advance(owner, "count", 1, 1);
        check(timer == 2 && GameTime.crosses(owner, "count", timer, 2), "crossed timer threshold");
        timer = GameTime.advance(owner, "count", timer, 1);
        check(timer == 4 && GameTime.crosses(owner, "count", timer, 3), "skipped integer still fires");
        GameTime.set(owner, "count", 0);
        check(GameTime.event(owner, "count", "zero", true), "event armed");
        check(!GameTime.event(owner, "count", "zero", true), "event not duplicated");
        GameTime.set(owner, "count", 0);
        check(GameTime.event(owner, "count", "zero", true), "event re-armed on same-value reset");
        GameTime.reset();
        final int[] cycle = new int[2];
        simulate(6.3, 144, new Step() {
            public void update() {
                cycle[0] = GameTime.advance(cycle, "phase", cycle[0], 1);
                cycle[0] = GameTime.wrap(cycle, "phase", cycle[0], 10);
                if (GameTime.event(cycle, "phase", "atThree", GameTime.crosses(cycle, "phase", cycle[0], 3))) cycle[1]++;
            }
        });
        check(cycle[0] == 0 && cycle[1] == 10, "cyclic timers retain fractions and re-arm events");
    }

    private static void wrappedEvents() {
        for (double fps : new double[]{10, 30, 60, 144, -1}) {
            GameTime.reset();
            final int[] state = new int[4];
            simulate(6.3, fps, new Step() {
                public void update() {
                    state[0] = GameTime.advance(state, "cycle", state[0], 1);
                    state[0] = GameTime.wrap(state, "cycle", state[0], 10);
                    state[1] += GameTime.periods(state, "cycle", "ten", state[0], 10, 0);
                    state[2] += GameTime.periods(state, "cycle", "four", state[0], 4, 0);
                    for (int value : GameTime.steps(state, "cycle", "switch", state[0])) if (value == 0) state[3]++;
                }
            });
            check(state[1] == 10, "wrapped phase zero once per cycle at " + fps);
            check(state[2] == 30, "non-divisible wrap period at " + fps);
            check(state[3] == 10, "wrapped switch timelines at " + fps);
        }
        GameTime.reset();
        Object owner = new Object();
        int current = GameTime.set(owner, "descending", 5);
        GameTime.beginFrame(UNIT);
        current = GameTime.advance(owner, "descending", current, -1);
        check(current == 4 && GameTime.periodic(owner, "descending", "pulse", current, 2, 0), "descending arrival at boundary");
        GameTime.beginFrame(UNIT);
        current = GameTime.advance(owner, "descending", current, -1);
        check(current == 3 && !GameTime.periodic(owner, "descending", "pulse", current, 2, 0), "no descending departure event");
    }

    private static class EmptyWorld extends ACWorld {
        private final ACDegreeGetter degrees = new ACDegreeGetter() {
            public void getDegreeFromCollisionByPosition(DegreeReturner result, ACCollision c, int degree, int x, int y, int layer) { result.reset(x, y, degree); }
            public void getDegreeFromWorldByPosition(DegreeReturner result, int degree, int x, int y, int layer) { result.reset(x, y, degree); }
        };
        public int getZoom() { return 6; }
        public int getTileWidth() { return 512; }
        public int getTileHeight() { return 512; }
        public int getWorldWidth() { return 1 << 24; }
        public int getWorldHeight() { return 1 << 24; }
        public ACDegreeGetter getDegreeGetterForObject() { return degrees; }
        public void getCollisionBlock(ACBlock block, int x, int y, int z) { block.setPosition(x / 512 * 512, y / 512 * 512); }
        public ACBlock getNewCollisionBlock() {
            return new ACBlock(this) {
                public int getCollisionXFromLeft(int y) { return NO_COLLISION; }
                public int getCollisionXFromRight(int y) { return NO_COLLISION; }
                public int getCollisionYFromDown(int x) { return NO_COLLISION; }
                public int getCollisionYFromUp(int x) { return NO_COLLISION; }
            };
        }
    }
    private static final class FloorWorld extends EmptyWorld {
        static final int FLOOR = 16384;
        private static final class Block extends ACBlock {
            boolean solid;
            Block(ACWorld world) { super(world); }
            public int getCollisionXFromLeft(int y) { return solid ? 0 : NO_COLLISION; }
            public int getCollisionXFromRight(int y) { return solid ? 511 : NO_COLLISION; }
            public int getCollisionYFromDown(int x) { return solid ? 511 : NO_COLLISION; }
            public int getCollisionYFromUp(int x) { return solid ? 0 : NO_COLLISION; }
        }
        public ACBlock getNewCollisionBlock() { return new Block(this); }
        public void getCollisionBlock(ACBlock block, int x, int y, int z) {
            super.getCollisionBlock(block, x, y, z);
            ((Block) block).solid = y >= FLOOR;
        }
    }
    private static final class Body extends ACObject implements ACWorldCalUser {
        int segments, largest, landings;
        Body(ACWorld world) { super(world); setRect(1024, 2048); }
        public void doBeforeCollisionCheck() { }
        public void doWhileCollision(ACObject obj, ACCollision collision, int a, int b, int c, int d, int e) { }
        public void didAfterEveryMove(int dx, int dy) { segments++; largest = Math.max(largest, Math.max(Math.abs(dx), Math.abs(dy))); }
        public int getBodyDegree() { return 0; }
        public int getBodyOffset() { return 512; }
        public int getFootOffset() { return 128; }
        public int getFootX() { return posX; }
        public int getFootY() { return posY; }
        public int getMinDegreeToLeaveGround() { return 45; }
        public int getPressToGround() { return 256; }
        public void doWhileLand(int degree) { landings++; velY = 0; }
        public void doWhileLeaveGround() { }
        public void doWhileTouchWorld(int direction, int position) { }
    }

    private static void movement() {
        for (double fps : new double[]{10, 12, 20, 30, 60, 90, 120, 144, -1}) {
            GameTime.reset();
            final Body body = new Body(new EmptyWorld());
            final ACMoveCalculator movement = new ACMoveCalculator(body, body);
            body.velY = -800;
            simulate(1.26, fps, new Step() {
                public void update() {
                    body.velY = GameTime.advance(body, "velY", body.velY, 96);
                    movement.moveVelocity(320, body.velY);
                }
            });
            check(body.velY == 1120, "constant gravity velocity at " + fps);
            near(body.posY, -800 * 20 + 0.5 * 96 * 20 * 20, 1, "constant acceleration trajectory at " + fps);
            check(body.posX == 6400, "movement calculator rate at " + fps);
            check(body.largest <= 512, "spatial sweep bound at " + fps);
            int oldX = body.posX, oldY = body.posY;
            GameTime.beginFrame(0.001);
            movement.actionLogic(777, -333);
            check(body.posX == oldX + 777 && body.posY == oldY - 333, "geometric correction is never dt-scaled");
        }
        GameTime.reset();
        final Body body = new Body(new EmptyWorld());
        final ACWorldCollisionCalculator world = new ACWorldCollisionCalculator(body, body);
        body.setPosition(10000, 20000);
        simulate(0.63, 120, new Step() { public void update() { world.moveVelocity(640, 128); } });
        check(body.posX == 16400 && body.posY == 21280, "actual world calculator in an empty map");
        GameTime.beginFrame(0);
        world.actionLogic(128, -64);
        check(body.posX == 16528 && body.posY == 21216, "world correction also works at zero dt");

        for (double fps : new double[]{10, 12, 20, 30, 60, 120, 144, -1}) {
            GameTime.reset();
            final Body falling = new Body(new FloorWorld());
            final ACWorldCollisionCalculator collision = new ACWorldCollisionCalculator(falling, falling);
            falling.setPosition(8192, 10000);
            simulate(1.26, fps, new Step() {
                public void update() {
                    if (collision.actionState == 1) falling.velY = GameTime.advance(falling, "velY", falling.velY, 96);
                    collision.moveVelocity(0, falling.velY);
                }
            });
            check(falling.landings > 0, "floor collision at " + fps);
            near(falling.posY, FloorWorld.FLOOR, 1, "no floor tunneling at " + fps);
        }

        GameTime.reset();
        Body fast = new Body(new FloorWorld());
        fast.setPosition(8192, 10000);
        fast.velY = 20000;
        ACWorldCollisionCalculator fastCollision = new ACWorldCollisionCalculator(fast, fast);
        GameTime.beginFrame(0.1);
        fastCollision.moveVelocity(0, fast.velY);
        near(fast.posY, FloorWorld.FLOOR, 1, "100 ms high-speed floor sweep");
        check(fast.landings > 0, "high-speed collision callback");

        GameTime.reset();
        Object owner = new Object();
        GameTime.beginFrame(0.01);
        int velocity = GameTime.advance(owner, "velY", 0, 96);
        velocity = -1200; // A jump/attack impulse must remain instantaneous.
        near(GameTime.integratedVelocity(owner, "velY", velocity), -1200, 0, "impulse is not averaged with gravity");
        for (double fps : new double[]{30, 60, 144}) {
            GameTime.reset();
            final int[] drag = {10000};
            simulate(0.63, fps, new Step() { public void update() { drag[0] = GameTime.damp(drag, "drag", drag[0], 0.8); } });
            near(drag[0], 10000 * Math.pow(0.8, 10), 1, "exponential drag at " + fps);
        }
    }

    private static void animation() {
        final AnimationTimeline.Durations durations = new AnimationTimeline.Durations() {
            public int frameCount() { return 3; }
            public double seconds(int frame) { return (frame + 1) * UNIT; }
        };
        AnimationTimeline timeline = new AnimationTimeline();
        timeline.advance(0.1, true, durations);
        check(timeline.frame() == 1, "animation advances a variable amount");
        near(timeline.secondsInFrame(), 0.1 - UNIT, 1e-12, "animation retains time inside its current pose");
        near(timeline.secondsInFrame(), 0.1 - UNIT, 1e-12, "reading motion progress cannot consume time");
        timeline.advance(0.3, true, durations);
        check(timeline.frame() == 0, "animation retains overflow across multiple frames and loop");
        timeline.reset();
        timeline.advance(6 * UNIT, false, durations);
        check(timeline.frame() == 2 && timeline.ended() && timeline.endTriggered(), "non-looping animation ends exactly");
        timeline.advance(0, false, durations);
        check(timeline.ended() && !timeline.endTriggered(), "end trigger is one-shot");
        timeline.seek(1);
        check(!timeline.ended() && timeline.frame() == 1, "seeking clears the end latch");
        near(timeline.secondsInFrame(), 0.0, 0.0, "seek resets motion progress");
        timeline.advance(UNIT, false, durations);
        check(timeline.frame() == 1, "seek restarts the selected frame duration");
        timeline.advance(UNIT, false, durations);
        check(timeline.frame() == 2, "seek preserves following frame timing");
        for (double fps : new double[]{10, 12, 20, 30, 60, 120, 144, -1}) {
            GameTime.reset();
            final AnimationTimeline cursor = new AnimationTimeline();
            simulate(1.0, fps, new Step() { public void update() { cursor.advance(GameTime.deltaSeconds(), true, durations); } });
            check(cursor.frame() == 2, "animation position at " + fps);
        }
        timeline.reset();
        timeline.advance(10000, true, durations);
        check(timeline.frame() >= 0 && timeline.frame() < 3, "large animation delta is bounded");
        boolean rejected = false;
        try { timeline.advance(Double.POSITIVE_INFINITY, true, durations); } catch (IllegalArgumentException expected) { rejected = true; }
        check(rejected, "infinite animation delta rejected");
        timeline.reset();
        timeline.advance(0.01, false, new AnimationTimeline.Durations() {
            public int frameCount() { return 3; }
            public double seconds(int frame) { return 0; }
        });
        check(timeline.ended(), "zero-duration data cannot hang");
    }

    private static void smoothing() {
        int reference = 0, reverseReference = 0;
        for (final double fps : new double[]{10, 30, 60, 120, 144, -1}) {
            GameTime.reset();
            final int[] values = {100, -100, 500, 2, 0};
            simulate(0.315, fps, new Step() {
                public void update() {
                    values[0] = GameTime.approach(values, "positive", values[0], 110, 1, 8, 1);
                    values[1] = GameTime.approach(values, "negative", values[1], -110, 1, 8, 1);
                    values[2] = GameTime.approach(values, "slow", values[2], 1000, 1, 10, 1);
                    values[3] = GameTime.reverseApproach(values, "reverse", values[3], 0, 10000, 1, 3, 1);
                    values[4] = GameTime.reverseApproach(values, "fromRest", values[4], 0, 10000, 1, 3, 1);
                }
            });
            if (fps == 10) { reference = values[2]; reverseReference = values[3]; }
            check(values[2] == reference, "owner-aware integer smoothing invariant at " + fps);
            check(values[3] == reverseReference, "reverse smoothing invariant at " + fps);
            check(values[0] > 100 && values[1] < -100 && values[4] > 0, "no integer smoothing stall at " + fps);
            simulate(2.52, fps, new Step() {
                public void update() {
                    values[0] = GameTime.approach(values, "positive", values[0], 110, 1, 8, 1);
                    values[1] = GameTime.approach(values, "negative", values[1], -110, 1, 8, 1);
                    values[2] = GameTime.approach(values, "slow", values[2], 1000, 1, 10, 1);
                    values[3] = GameTime.reverseApproach(values, "reverse", values[3], 0, 10000, 1, 3, 1);
                }
            });
            check(values[0] == 110 && values[1] == -110 && values[2] == 1000 && values[3] == 10000,
                    "integer smoothing reaches exact endpoints at " + fps);
            GameTime.beginFrame(0);
            check(GameTime.approach(values, "pause", 50, 0, 1, 2, 1) == 50, "paused smoothing");
        }
        GameTime.reset();
        Object owner = new Object();
        GameTime.beginFrame(UNIT / 8);
        int value = GameTime.approach(owner, "reused", 0, 2, 1, 8, 1);
        value = GameTime.set(owner, "reused", 0);
        GameTime.beginFrame(UNIT);
        check(GameTime.approach(owner, "reused", value, 0, 1, 8, 1) == 0, "pooled smoothing reset");
    }

    private static void menuScroll() {
        for (final double fps : new double[]{10, 30, 60, 120, 144, -1}) {
            GameTime.reset();
            final MenuScroll scroll = new MenuScroll();
            scroll.reset(0);
            final double[] previous = {0};
            final boolean[] monotonic = {true};
            simulate(0.18, fps, new Step() { public void update() {
                scroll.update(GameTime.deltaSeconds(), false, 0, -1, -400, 0, false);
                monotonic[0] &= scroll.position() <= previous[0];
                previous[0] = scroll.position();
            }});
            simulate(0.8, fps, new Step() { public void update() {
                scroll.update(GameTime.deltaSeconds(), false, 0, 0, -400, 0, false);
                monotonic[0] &= scroll.position() <= previous[0];
                previous[0] = scroll.position();
            }});
            check(monotonic[0], "no backwards jump at a menu row boundary, fps " + fps);
            near(scroll.position(), -24, 1e-9, "short arrow press finishes one full row, fps " + fps);
            check(!scroll.moving(), "arrow movement ends, fps " + fps);
            simulate(10, fps, new Step() { public void update() {
                scroll.update(GameTime.deltaSeconds(), false, 0, -1, -400, 0, false);
            }});
            near(scroll.position(), -400, 1e-9, "held arrow stops at list boundary, fps " + fps);
            scroll.reset(0);
            scroll.update(0.01, true, 100, 0, -400, 0, true);
            final double[] elapsed = {0};
            simulate(0.3, fps, new Step() { public void update() {
                elapsed[0] += GameTime.deltaSeconds();
                scroll.update(GameTime.deltaSeconds(), true, 100 - 80 * elapsed[0], 0, -400, 0, true);
            }});
            near(scroll.position(), -24, 1e-8, "drag follows absolute pointer displacement, fps " + fps);
            check(scroll.consumedGesture() && !scroll.canSelect(), "drag cannot click a menu item, fps " + fps);
            simulate(0.5, fps, new Step() { public void update() {
                scroll.update(GameTime.deltaSeconds(), false, 0, 0, -400, 0, true);
            }});
            double speed = 80 * (1 - Math.exp(-0.3 / 0.04));
            near(scroll.position(), -24 - speed * speed / (2 * (3 / (UNIT * UNIT))), 1e-8,
                    "fling speed uses elapsed seconds, not an integer frame counter, fps " + fps);
            scroll.update(0.01, true, 100, 0, -400, 0, true);
            check(scroll.canSelect(), "a new stationary touch can select again, fps " + fps);
            scroll.update(0.01, true, 150, 0, -400, 0, true);
            simulate(1.0, fps, new Step() { public void update() {
                scroll.update(GameTime.deltaSeconds(), false, 0, 0, -400, 0, true);
            }});
            near(scroll.position(), 0, 0, "elastic overscroll settles at the exact edge, fps " + fps);
            double frozen = scroll.position();
            scroll.update(0, false, 0, -1, -400, 0, false);
            near(scroll.position(), frozen, 0, "paused menu cannot scroll, fps " + fps);
        }
        GameTime.reset();
        Object fade = new Object();
        GameTime.beginFrame(1.0 / 60);
        int value = GameTime.approachOnce(fade, "alpha", 200, 0, 1, 3, 3);
        check(GameTime.approachOnce(fade, "alpha", value, 0, 1, 3, 3) == value,
                "a modal and its background cannot advance one shared fade twice");
        value = GameTime.set(fade, "alpha", 200);
        check(GameTime.approachOnce(fade, "alpha", value, 0, 1, 3, 3) < value,
                "explicit fade restart re-arms a same-frame update");
    }

    private static void transforms() {
        int[][] expected = {{0,1,2,3},{3,2,1,0},{1,0,3,2},{2,3,0,1},{0,3,2,1},{3,0,1,2},{1,2,3,0},{2,1,0,3}};
        float[] uv = new float[8];
        for (int t = 0; t < 8; t++) {
            SpriteTransform.textureCoordinates(t, 0.1f, 0.2f, 0.7f, 0.9f, uv);
            for (int corner = 0; corner < 4; corner++) {
                int source = expected[t][corner];
                near(uv[corner * 2], source == 0 || source == 3 ? 0.1f : 0.7f, 1e-7, "transform " + t + " u");
                near(uv[corner * 2 + 1], source < 2 ? 0.2f : 0.9f, 1e-7, "transform " + t + " v");
            }
            check(SpriteTransform.swapsAxes(t) == (t >= 4), "rotation dimensions " + t);
        }
        boolean rejected = false;
        try { SpriteTransform.textureCoordinates(8, 0, 0, 1, 1, uv); } catch (IllegalArgumentException expectedError) { rejected = true; }
        check(rejected, "invalid MIDP transform rejected");
    }

    public static void main(String[] arguments) {
        clock(); clockDoesNotSkipDisplayFrames(); rates(); events(); wrappedEvents(); movement(); animation(); smoothing(); menuScroll(); transforms();
        System.out.println("PASS: " + assertions + " assertions; clocks, rates, events, production movement/sweeps, animation, integer smoothing, all 8 sprite transforms.");
    }
}
