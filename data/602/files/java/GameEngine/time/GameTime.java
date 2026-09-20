package GameEngine.time;

import java.util.HashMap;
import java.util.WeakHashMap;

/** Simulation-thread time context. 63 ms is an asset unit, NOT an update interval. */
public final class GameTime {
    public static final double ASSET_TIME_UNIT_SECONDS = 0.063;
    private static double deltaSeconds;
    private static double elapsedSeconds;
    private static long frameId;
    private static final WeakHashMap<Object, HashMap<String, Channel>> channels =
            new WeakHashMap<Object, HashMap<String, Channel>>();

    private static final class Event {
        boolean condition;
        boolean initialized;
        long cycle;
    }
    private static final class Channel {
        final RateAccumulator fraction = new RateAccumulator();
        final HashMap<String, Event> events = new HashMap<String, Event>();
        int expected;
        int before;
        double frameChange;
        long wrapOffset;
        int modulus;
        boolean initialized;
        long lastFrame = Long.MIN_VALUE;
        boolean condition;
        Event event(String name) {
            Event event = events.get(name);
            if (event == null) { event = new Event(); events.put(name, event); }
            return event;
        }
    }

    private GameTime() { }

    public static void beginFrame(double seconds) {
        if (Double.isNaN(seconds) || Double.isInfinite(seconds) || seconds < 0.0) {
            throw new IllegalArgumentException("Invalid simulation delta");
        }
        deltaSeconds = seconds;
        elapsedSeconds += seconds;
        frameId++;
    }
    public static double deltaSeconds() { return deltaSeconds; }
    public static double assetUnits() { return deltaSeconds / ASSET_TIME_UNIT_SECONDS; }
    public static double elapsedSeconds() { return elapsedSeconds; }
    public static long frameId() { return frameId; }
    public static long milliseconds() { return (long) (elapsedSeconds * 1000.0); }

    private static Channel channel(Object owner, String name) {
        HashMap<String, Channel> state = channels.get(owner);
        if (state == null) {
            state = new HashMap<String, Channel>();
            channels.put(owner, state);
        }
        Channel value = state.get(name);
        if (value == null) { value = new Channel(); state.put(name, value); }
        return value;
    }

    /** Integrate a rate, retaining sub-integer coordinates and timer fractions. */
    public static int advance(Object owner, String name, int current, double assetRate) {
        Channel value = channel(owner, name);
        if (!value.initialized || value.expected != current) {
            value.fraction.reset();
            if (value.initialized) value.events.clear();
            value.frameChange = 0.0;
            value.wrapOffset = 0;
            value.modulus = 0;
        }
        if (value.lastFrame != frameId) {
            value.frameChange = 0.0;
            value.before = current;
        }
        value.frameChange += assetRate * assetUnits();
        value.initialized = true;
        value.lastFrame = frameId;
        value.expected = current + value.fraction.integrate(
                assetRate / ASSET_TIME_UNIT_SECONDS, deltaSeconds);
        return value.expected;
    }

    /** Shared timers (e.g. a power-up with two players) advance only once per frame. */
    public static int advanceOnce(Object owner, String name, int current, double assetRate) {
        Channel value = channel(owner, name);
        return value.lastFrame == frameId ? current : advance(owner, name, current, assetRate);
    }

    public static int milliseconds(Object owner, String name, int current, int direction) {
        return advance(owner, name, current, direction * 1000.0 * ASSET_TIME_UNIT_SECONDS);
    }
    public static int distance(Object owner, String axis, double assetVelocity) {
        return channel(owner, axis).fraction.integrate(assetVelocity / ASSET_TIME_UNIT_SECONDS, deltaSeconds);
    }
    /** Average continuous velocity over this frame (exact for constant acceleration).
     * A direct velocity assignment is an impulse, detected by expected != velocity.
     */
    public static double integratedVelocity(Object owner, String name, double velocity) {
        Channel value = channel(owner, name);
        if (value.initialized && value.lastFrame == frameId && value.expected == velocity)
            return velocity + value.fraction.getRemainder() - value.frameChange * 0.5;
        return velocity;
    }
    public static int advancePosition(Object owner, String axis, int position, String velocityChannel, double velocity) {
        return advance(owner, axis, position, integratedVelocity(owner, velocityChannel, velocity));
    }

    /** Non-consuming look-ahead for collision probes; never use this instead of integration. */
    public static int displacement(double assetVelocity) { return (int) Math.round(assetVelocity * assetUnits()); }
    /** Convert a measured movement back to an authored velocity, e.g. on leaving a rail. */
    public static int velocityFromDisplacement(int distance) {
        return assetUnits() <= 0.0 ? 0 : (int) Math.round(distance / assetUnits());
    }
    public static double precise(Object owner, String name, int current) {
        Channel value = channel(owner, name);
        return current + (value.initialized && value.expected == current ? value.fraction.getRemainder() : 0.0);
    }

    /** Exponential drag, independent of the number of updates. */
    public static int damp(Object owner, String name, int current, double retentionPerAssetUnit) {
        if (assetUnits() == 0.0) return current;
        double exact = precise(owner, name, current);
        return advance(owner, name, current,
                exact * (Math.pow(retentionPerAssetUnit, assetUnits()) - 1.0) / assetUnits());
    }

    /** Elapsed-time version of the authored affine exponential approach. */
    public static double approachValue(double current, double target, int numerator, int denominator, double minimum) {
        if (denominator <= numerator || denominator <= 0 || assetUnits() <= 0.0) return current;
        double difference = target - current;
        if (difference == 0.0) return target;
        double fraction = numerator / (double) denominator;
        double movement = fraction <= 0.0 ? Math.max(0.0, minimum) * assetUnits()
                : (Math.abs(difference) + Math.max(0.0, minimum) / fraction)
                * (1.0 - Math.pow(1.0 - fraction, assetUnits()));
        return current + Math.copySign(Math.min(Math.abs(difference), movement), difference);
    }
    /** Integer controllers must keep fractional output, otherwise menus/cameras stall at high FPS. */
    public static int approach(Object owner, String name, int current, double target, int numerator, int denominator, double minimum) {
        if (assetUnits() <= 0.0) return current;
        double exact = precise(owner, name, current);
        double next = approachValue(exact, target, numerator, denominator, minimum);
        return advance(owner, name, current, (next - exact) / assetUnits());
    }
    public static int reverseApproach(Object owner, String name, int current, int origin, int target, int numerator, int denominator, int minimum) {
        if (denominator <= numerator || denominator <= 0 || assetUnits() <= 0.0) return current;
        double exact = precise(owner, name, current);
        if (exact == target) return target;
        double direction = target >= exact ? 1.0 : -1.0;
        double z = (exact - origin) * direction;
        double start = z, remaining = assetUnits();
        double lambda = Math.log1p(denominator / (double) (denominator - numerator) * 0.5);
        double speed = Math.max(0, minimum);
        double threshold = speed / lambda;
        if (z < -threshold) {
            double timeToLinear = speed == 0 ? Double.POSITIVE_INFINITY : Math.log(-z / threshold) / lambda;
            double time = Math.min(remaining, timeToLinear);
            z *= Math.exp(-lambda * time);
            remaining -= time;
        }
        if (speed > 0 && remaining > 0 && z < threshold) {
            double time = Math.min(remaining, (threshold - z) / speed);
            z += speed * time;
            remaining -= time;
        }
        if (remaining > 0) z *= Math.exp(lambda * remaining);
        double movement = Math.min(Math.abs(target - exact), Math.max(0, z - start));
        return advance(owner, name, current, direction * movement / assetUnits());
    }

    /** Timer equality including a threshold skipped by a long frame. This is a LEVEL predicate. */
    public static boolean crosses(Object owner, String name, int current, int threshold) {
        if (current == threshold) return true;
        Channel value = channel(owner, name);
        return value.initialized && value.expected == current
                && ((value.before < threshold && current > threshold)
                    || (value.before > threshold && current < threshold));
    }
    /** Latch the complete event condition, after all other guards have been evaluated. */
    public static boolean event(Object owner, String timer, String site, boolean condition) {
        Event event = channel(owner, timer).event(site);
        boolean fired = condition && !event.condition;
        event.condition = condition;
        return fired;
    }

    /** Number of crossed periodic boundaries. Unlike modulo polling this cannot repeat a shot. */
    public static int periods(Object owner, String timer, String site, int current, int period, int offset) {
        if (period <= 0) throw new IllegalArgumentException("Period must be positive");
        Channel value = channel(owner, timer);
        Event event = value.event(site);
        long position = current + value.wrapOffset;
        long previous = event.initialized ? event.cycle : value.initialized && value.lastFrame == frameId
                ? value.before + value.wrapOffset : position;
        long count;
        if (!event.initialized && !(value.initialized && value.lastFrame == frameId)) {
            count = (current - (long) offset) % period == 0 ? 1 : 0;
        } else if (position >= previous) {
            count = boundaries(position, period, offset, value.modulus) - boundaries(previous, period, offset, value.modulus);
        } else {
            // Descending timers include the arrival boundary, not the one just left.
            count = boundaries(previous - 1, period, offset, value.modulus) - boundaries(position - 1, period, offset, value.modulus);
        }
        event.initialized = true;
        event.cycle = position;
        return (int) Math.min(Integer.MAX_VALUE, Math.max(0L, count));
    }
    private static long boundaries(long position, int period, int offset, int modulus) {
        if (modulus == 0) return (long) Math.floor((position - (double) offset) / period);
        long cycle = (long) Math.floor(position / (double) modulus);
        long phase = position - cycle * modulus;
        int start = (int) (((offset % (long) period) + period) % period);
        long perCycle = start >= modulus ? 0 : 1 + (modulus - 1L - start) / period;
        return cycle * perCycle + (phase < start ? 0 : 1 + (phase - start) / period);
    }
    /** Integer boundary events for legacy switch timelines, not physics substeps. */
    public static int[] steps(Object owner, String timer, String site, int current) {
        Channel value = channel(owner, timer);
        Event event = value.event(site);
        long position = current + value.wrapOffset;
        long previous = event.initialized ? event.cycle : value.initialized
                ? value.before + value.wrapOffset : position - 1;
        event.cycle = position;
        event.initialized = true;
        int count = (int) Math.min(4096L, Math.abs(position - previous));
        int[] result = new int[count];
        int direction = position >= previous ? 1 : -1;
        for (int i = 0; i < count; i++) {
            long crossed = previous + direction * (i + 1L);
            result[i] = value.modulus == 0 ? (int) crossed : (int) (((crossed % value.modulus) + value.modulus) % value.modulus);
        }
        return result;
    }

    public static boolean periodic(Object owner, String timer, String site, int current, int period, int offset) {
        return periods(owner, timer, site, current, period, offset) > 0;
    }
    public static boolean once(Object owner, String site, boolean condition) {
        Channel value = channel(owner, "event:" + site);
        boolean result = condition && !value.condition;
        value.condition = condition;
        return result;
    }
    /** Rebase a cyclic field without losing its fractional carry or crossed boundary. */
    public static int wrap(Object owner, String name, int current, int modulus) {
        if (modulus <= 0) throw new IllegalArgumentException("Modulus must be positive");
        int wrapped = (int) (((current % (long) modulus) + modulus) % modulus);
        Channel value = channel(owner, name);
        if (value.initialized && value.expected == current) {
            long shift = (long) current - wrapped;
            value.before -= shift;
            value.expected = wrapped;
            value.wrapOffset += shift;
            if (shift != 0) for (Event event : value.events.values()) event.condition = false;
        }
        value.modulus = modulus;
        return wrapped;
    }

    /** Explicit assignment/reset also clears fractional carry and re-arms that timer's events. */
    public static int set(Object owner, String name, int value) { reset(owner, name); return value; }
    public static void reset(Object owner, String name) {
        HashMap<String, Channel> state = channels.get(owner);
        if (state != null) state.remove(name);
    }
    public static void reset() {
        channels.clear(); deltaSeconds = 0.0; elapsedSeconds = 0.0; frameId = 0L;
    }
}
