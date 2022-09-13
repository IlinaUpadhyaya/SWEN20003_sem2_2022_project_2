import bagel.*;
import bagel.util.Point;

public class Sinkhole extends StationaryGameEntity {
    private static final Image SINKHOLE = new Image("res/sinkhole.png");
    protected final double SINKHOLE_DAMAGE = 30;

    public Sinkhole(double x, double y) {
        super(new Point(x,y), SINKHOLE);
    }

    @Override
    public String toString() {
        return "Sinkhole";
    }
}
