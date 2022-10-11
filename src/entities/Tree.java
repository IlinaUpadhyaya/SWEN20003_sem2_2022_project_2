package entities;

import bagel.Image;
import bagel.util.Point;

public class Tree extends StationaryGameEntity{
    protected final static Image TREE = new Image("res/tree.png");
    final static String NAME = "Tree";

    public Tree(Point position) {
        super(position, TREE, NAME);
    }
}
