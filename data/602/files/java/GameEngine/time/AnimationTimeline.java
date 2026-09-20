package GameEngine.time;

/** Per-instance animation state, independent of draw calls and shared sprite data. */
public final class AnimationTimeline {
    private double remainder;
    private int frame;
    private boolean ended;
    private boolean endTriggered;

    public interface Durations {
        int frameCount();
        double seconds(int frame);
    }

    public void advance(double seconds, boolean loop, Durations durations) {
        if (Double.isNaN(seconds) || Double.isInfinite(seconds) || seconds < 0.0)
            throw new IllegalArgumentException("Invalid animation delta");
        endTriggered = false;
        int count = durations.frameCount();
        if (count <= 0 || seconds <= 0.0 || (ended && !loop)) return;
        remainder += seconds;
        if (loop && seconds > 1.0) {
            double cycle = 0.0;
            for (int i = 0; i < count; i++) cycle += duration(durations, i);
            remainder %= cycle;
        }
        // Durations are validated/clamped so zero-length malformed frames cannot hang.
        while (remainder + 1.0e-12 >= duration(durations, frame)) {
            remainder -= duration(durations, frame);
            if (++frame == count) {
                if (loop) {
                    frame = 0;
                } else {
                    frame = count - 1;
                    remainder = 0.0;
                    ended = true;
                    endTriggered = true;
                    break;
                }
            }
        }
    }

    private static double duration(Durations durations, int frame) {
        double seconds = durations.seconds(frame);
        return Double.isNaN(seconds) || Double.isInfinite(seconds) ? 0.001 : Math.max(0.001, seconds);
    }

    public int frame() { return frame; }
    public boolean ended() { return ended; }
    public boolean endTriggered() { return endTriggered; }
    public void finish() { ended = true; }
    public void reset() { seek(0); }
    public void seek(int targetFrame) {
        if (targetFrame < 0) throw new IllegalArgumentException("Negative animation frame");
        frame = targetFrame;
        remainder = 0.0;
        ended = false;
        endTriggered = false;
    }
}
