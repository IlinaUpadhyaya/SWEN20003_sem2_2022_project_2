import bagel.*;
import bagel.util.Point;

public class Wall extends StationaryGameEntity {
    private static final Image WALL = new Image("res/wall.png");

    public Wall(double x, double y) {
        super(new Point(x,y), WALL);
    }
}
