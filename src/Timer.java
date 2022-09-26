public class Timer {
    private static final int REFRESH_RATE = 60;
    private static final double MS_PERSEC = 1000.0;
    private double msRemaining;

    public Timer(int milliseconds) {
        this.msRemaining = milliseconds;
    }

    public boolean isTimeUp() {
        /*must be called every update*/
        this.msRemaining -= MS_PERSEC / REFRESH_RATE;
        return this.msRemaining <= 0;
    }
}
