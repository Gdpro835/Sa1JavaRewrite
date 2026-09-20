package GameEngine.time;

/** Monotonic, variable-step clock. There is deliberately no fixed-step accumulator. */
public final class FrameClock {
    // Upper bound on simulated time after a hitch, NOT a minimum frame interval or FPS cap.
    public static final double MAX_DELTA_SECONDS = 0.100;
    private long previousNanos = Long.MIN_VALUE;
    private double droppedSeconds;

    public double advance(long nowNanos) {
        if (previousNanos == Long.MIN_VALUE) {
            previousNanos = nowNanos;
            return 0.0;
        }
        long elapsed = nowNanos - previousNanos;
        previousNanos = nowNanos;
        if (elapsed <= 0L) return 0.0;
        double seconds = elapsed / 1000000000.0;
        if (seconds > MAX_DELTA_SECONDS) {
            droppedSeconds += seconds - MAX_DELTA_SECONDS;
            seconds = MAX_DELTA_SECONDS;
        }
        return seconds;
    }

    /** Call on pause/resume and surface loss; background time is not simulation time. */
    public void reset() {
        previousNanos = Long.MIN_VALUE;
    }

    public double getDroppedSeconds() {
        return droppedSeconds;
    }
}
