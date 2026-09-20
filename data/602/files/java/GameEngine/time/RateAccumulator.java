package GameEngine.time;

/** Retains sub-integer displacement in the existing 1/64-pixel world coordinates. */
public final class RateAccumulator {
    private double remainder;

    public int integrate(double ratePerSecond, double deltaSeconds) {
        if (Double.isNaN(ratePerSecond) || Double.isInfinite(ratePerSecond)
                || Double.isNaN(deltaSeconds) || Double.isInfinite(deltaSeconds)
                || deltaSeconds < 0.0) {
            throw new IllegalArgumentException("Non-finite rate or invalid elapsed time");
        }
        double value = remainder + ratePerSecond * deltaSeconds;
        // Small tolerance prevents 60 equal intervals from losing a whole unit to rounding.
        int whole = value >= 0.0 ? (int) Math.floor(value + 1.0e-9)
                                : (int) Math.ceil(value - 1.0e-9);
        remainder = value - whole;
        return whole;
    }

    public void reset() { remainder = 0.0; }
    public double getRemainder() { return remainder; }
}
