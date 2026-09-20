package GameEngine.time;

/** Variable-time list motion. A single position is shared by painting and hit testing. */
public final class MenuScroll {
    private static final double ROW = 24.0;
    private static final double ARROW_SPEED = 6.0 / GameTime.ASSET_TIME_UNIT_SECONDS;
    private static final double FRICTION = 3.0 / (GameTime.ASSET_TIME_UNIT_SECONDS * GameTime.ASSET_TIME_UNIT_SECONDS);
    private static final double MAX_FLING = 1200.0;
    private double position, velocity, lower, upper;
    private double dragOrigin, pointerOrigin, previousPointer;
    private double arrowOrigin, arrowTarget;
    private boolean dragging, dragged, arrowFinishing;
    private int arrowDirection;

    public void reset(double position) {
        this.position = position;
        velocity = 0.0;
        dragging = dragged = arrowFinishing = false;
        arrowDirection = 0;
    }

    /** A modal dialog cancels input/inertia, but preserves the list's visible position. */
    public void stop() {
        dragging = arrowFinishing = false;
        arrowDirection = 0;
        velocity = 0.0;
    }

    public void update(double seconds, boolean pointerDown, double pointerY, int direction,
            double lower, double upper, boolean allowFling) {
        if (Double.isNaN(seconds) || Double.isInfinite(seconds) || seconds < 0.0)
            throw new IllegalArgumentException("Invalid menu delta");
        this.lower = Math.min(lower, upper);
        this.upper = upper;
        if (seconds == 0.0) return;
        direction = direction < 0 ? -1 : direction > 0 ? 1 : 0;
        if (pointerDown) {
            if (!dragging) {
                dragged = Math.abs(velocity) > 1.0 || arrowFinishing || arrowDirection != 0;
                dragging = true;
                dragOrigin = position;
                pointerOrigin = previousPointer = pointerY;
                velocity = 0.0;
            }
            arrowDirection = 0;
            arrowFinishing = false;
            double offset = pointerY - pointerOrigin;
            dragged |= Math.abs(offset) > 4.0;
            // Absolute finger displacement is not a rate and must never be multiplied by dt.
            position = Math.max(this.lower - 32.0, Math.min(upper + 32.0, dragOrigin + offset));
            double measured = Math.max(-MAX_FLING, Math.min(MAX_FLING, (pointerY - previousPointer) / seconds));
            velocity += (measured - velocity) * (1.0 - Math.exp(-seconds / 0.04));
            previousPointer = pointerY;
            return;
        }
        if (direction != 0) {
            if (direction != arrowDirection) arrowOrigin = position;
            arrowDirection = direction;
            arrowFinishing = dragging = false;
            dragged = true;
            velocity = 0.0;
            position = clamp(position + direction * ARROW_SPEED * seconds);
            return;
        }
        if (dragging) {
            dragging = false;
            if (!allowFling || !dragged) velocity = 0.0;
        }
        if (arrowDirection != 0) {
            double rows = (position - arrowOrigin) / ROW;
            rows = arrowDirection > 0 ? Math.ceil(rows - 1e-9) : Math.floor(rows + 1e-9);
            arrowTarget = clamp(arrowOrigin + rows * ROW);
            arrowFinishing = true;
            arrowDirection = 0;
        }
        if (position < this.lower || position > upper) {
            double target = clamp(position);
            position = target + (position - target) * Math.exp(-12.0 * seconds);
            if (Math.abs(position - target) < 0.25) position = target;
            velocity = 0.0;
            arrowFinishing = false;
        } else if (arrowFinishing) {
            double remaining = arrowTarget - position;
            double step = Math.min(Math.abs(remaining), ARROW_SPEED * seconds);
            position += Math.copySign(step, remaining);
            if (step >= Math.abs(remaining)) arrowFinishing = false;
        } else if (velocity != 0.0) {
            // Analytic constant-friction travel; no per-frame velocity decay or raw look-ahead.
            double speed = Math.abs(velocity), time = Math.min(seconds, speed / FRICTION);
            position += Math.copySign(speed * time - 0.5 * FRICTION * time * time, velocity);
            velocity = Math.copySign(Math.max(0.0, speed - FRICTION * time), velocity);
            double bounded = clamp(position);
            if (bounded != position) velocity = 0.0;
            position = bounded;
        }
    }

    private double clamp(double value) { return Math.max(lower, Math.min(upper, value)); }
    public int pixels() { return (int) Math.round(position); }
    public double position() { return position; }
    public boolean consumedGesture() { return dragged; }
    public boolean moving() { return arrowDirection != 0 || arrowFinishing || Math.abs(velocity) > 1.0 || position < lower || position > upper; }
    public boolean canSelect() { return !dragged && (dragging || !moving()); }
    public boolean canScrollUp() { return position < upper - 0.25; }
    public boolean canScrollDown() { return position > lower + 0.25; }
}
