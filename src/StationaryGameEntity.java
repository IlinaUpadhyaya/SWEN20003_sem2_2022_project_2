import bagel.Image;
import bagel.util.Point;

public abstract class StationaryGameEntity extends GameEntity{
    public StationaryGameEntity(Point position, Image image) {
        super(position, image);
    }
}
