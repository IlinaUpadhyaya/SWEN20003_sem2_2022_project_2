package entities;

import bagel.Image;
import bagel.util.Point;

/**
 * Class represents Wall game object
 */
public class Wall extends StationaryGameEntity {

    /**
     * name must match that in the csv file for this entity. This name will print on messages
     * involving this entity
     */
    public final static String NAME = "Wall";
    protected final static Image WALL = new Image("res/wall.png");

    /**
     * Constructor
     *
     * @param position representing top left co-ordinate of object in the scene
     */
    public Wall(Point position) {
        super(position, WALL, NAME);
    }
}
