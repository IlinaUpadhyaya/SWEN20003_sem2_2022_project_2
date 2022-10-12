package entities;

import bagel.Image;
import bagel.util.Point;

/**
 * Class represents Tree game object
 */
public class Tree extends StationaryGameEntity {
    /**
     * name must match that in the csv file for this entity. This name will print on messages
     * involving this entity
     */
    public final static String NAME = "Tree";
    protected final static Image TREE = new Image("res/tree.png");

    /**
     * Constructor
     *
     * @param position representing top left co-ordinate of object in the scene
     */
    public Tree(Point position) {
        super(position, TREE, NAME);
    }
}
