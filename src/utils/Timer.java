package utils;

/**
 * This is a countdown timer. Keeps track of time elapsed using the refresh rate.
 * The construction parameter is the countdown interval in milliseconds.
 * The refresh rate must always be correct.
 */

public class Timer {
    private static final int REFRESH_RATE = 60;
    private static final double MS_PER_SEC = 1000.0;
    private double msRemaining;

    /**
     * argument is the time interval to be monitored
     */
    public Timer(int countdownMS) {
        this.msRemaining = countdownMS;
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
