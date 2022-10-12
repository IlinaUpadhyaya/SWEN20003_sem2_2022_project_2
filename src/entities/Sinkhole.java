package entities;

import bagel.Image;
import bagel.util.Point;

/**
 * Class represents Sinkhole game object
 */
public class Sinkhole extends StationaryGameEntity {
    /**
     * name must match that in the csv file for this entity. This name will print on messages
     * involving this entity
     */
    public final static String NAME = "Sinkhole";
    protected final static Image SINKHOLE = new Image("res/sinkhole.png");
    protected final static double SINKHOLE_DAMAGE = 30;

    /**
     * Constructor
     *
     * @param position representing top left co-ordinate of object in the scene
     */
    public Sinkhole(Point position) {
        super(position, SINKHOLE, NAME, SINKHOLE_DAMAGE);
    }

}
