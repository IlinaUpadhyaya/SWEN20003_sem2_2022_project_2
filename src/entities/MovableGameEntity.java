package entities;

import bagel.util.Point;
import bagel.util.Rectangle;
import utils.Timer;

import java.util.ArrayList;

public abstract class MovableGameEntity extends StationaryGameEntity {
    protected Point topLeft, bottomRight;
    protected ArrayList<StationaryGameEntity> gameEntities;
    protected double speed;
    protected Timer timer;
    protected EntityState entityState;

    MovableGameEntity(Point position) {
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

    protected boolean collidesWithGameEntity(Point proposedPos, String... gameEntityNames) {
        Rectangle boundingBox = this.getBoundingBoxAt(proposedPos);
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
}
