package utils;

/**
 * Keeps track of time elapsed vis-à-vis a pre-determined time interval in milliseconds passed into
 * the constructor
 */
public class Timer {
    private static final int REFRESH_RATE = 60;
    private static final double MS_PER_SEC = 1000.0;
    private double msRemaining;

    /**
     * argument is the time interval to be monitored
     */
    public Timer(int milliseconds) {
        this.msRemaining = milliseconds;
    }

    /**
     * Ensure that this method is called every frame update
     */
    public void clockTick() {
        this.msRemaining -= MS_PER_SEC / REFRESH_RATE;
    }

    /**
     * always returns true after the time interval being monitored has expired
     */
    public boolean isTimeUp() {
        return this.msRemaining <= 0;
    }
}
