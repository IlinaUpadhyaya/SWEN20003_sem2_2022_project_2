package entities;

import bagel.Image;
import bagel.util.Point;

public class Wall extends StationaryGameEntity{
    protected final static Image WALL = new Image("res/wall.png");
    final static String NAME = "Wall";

    public Wall(Point position) {
        super(position, WALL, NAME);
    }
}
