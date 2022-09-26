import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.ArrayList;

public abstract class MovableGameEntity extends StationaryGameEntity {
    protected Point topLeft, bottomRight;
    protected ArrayList<StationaryGameEntity> gameEntities;
    protected double healthPoints, speed;
    protected HealthCalculator health;
    protected Timer timer;

    protected EntityState entityState;

    public MovableGameEntity(Point position) {
        super(position);
    }

    public void setBounds(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public void setGameEntities(ArrayList<StationaryGameEntity> gameEntities) {
        this.gameEntities = gameEntities;
    }

    protected boolean withinBounds(Point proposedPos) {
        boolean withinXBounds =
                proposedPos.x > topLeft.x && proposedPos.x < bottomRight.x;
        boolean withinYBounds =
                proposedPos.y > topLeft.y && proposedPos.y < bottomRight.y;
        return withinXBounds && withinYBounds;
    }

    /**/
    protected boolean collidesWithGameEntity(Point proposedPos,
                                             String... gameEntityNames) {
        Point centerPos = super.getCenterPosition();
        Point topLeft = super.getTopLeftPosition();
        double xShift = proposedPos.x - topLeft.x;
        double yShift = proposedPos.y - topLeft.y;
        Point newCenter = new Point(centerPos.x + xShift,
                centerPos.y + yShift);
        Rectangle boundingBox = super.getImage().getBoundingBoxAt(newCenter);
        boolean collides = false;
        outer:
        for (String entityName : gameEntityNames) {
            for (StationaryGameEntity gameEntity : gameEntities) {
                if (gameEntity.getName().equals(entityName)) {
                    if (boundingBox.intersects(gameEntity.getBoundingBox())) {
                        collides = true;
                        break outer;
                    }
                }
            }
        }
        return collides;
    }

    public void adjustTimeScale(Keys key) {
        switch (key) {
            case K:
                this.speed -= this.speed / 2;
                if (this.speed < -3.0) speed = -3.0;
                break;
            case L:
                this.speed += this.speed / 2;
                if (this.speed > 3.0) speed = 3.0;
                break;
            default:
                break;
        }
    }
}
