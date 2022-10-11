package entities;

import bagel.Image;
import bagel.util.Point;

public class Sinkhole extends StationaryGameEntity {
    protected final static Image SINKHOLE = new Image("res/sinkhole.png");
    protected final static double SINKHOLE_DAMAGE = 30;
    final static String NAME = "Sinkhole";
    public Sinkhole(Point position) {
        super(position, SINKHOLE, NAME, SINKHOLE_DAMAGE);
    }

}
