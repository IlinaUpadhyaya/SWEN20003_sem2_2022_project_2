package utils;

public class Timer {
    private static final int REFRESH_RATE = 60;
    private static final double MS_PERSEC = 1000.0;
    private double msRemaining;

    public Timer(int milliseconds) {
        this.msRemaining = milliseconds;
    }

    /*must be called every frame update*/
    public void clockTick() {
        this.msRemaining -= MS_PERSEC / REFRESH_RATE;
    }

    public boolean isTimeUp() {
        return this.msRemaining <= 0;
    }
}
