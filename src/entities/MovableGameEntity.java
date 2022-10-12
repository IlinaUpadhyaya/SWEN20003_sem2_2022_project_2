package entities;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import utils.Timer;

import java.util.ArrayList;

/**
 * Encapsulates general movable functionality used by demons
 */
public abstract class MovableGameEntity extends StationaryGameEntity {
    protected Point topLeft, bottomRight;
    protected ArrayList<StationaryGameEntity> gameEntities;
    protected double speed;
    protected Timer timer;
    protected EntityState entityState;

    MovableGameEntity(Point position, Image image, String name, double damage) {
        super(position, image, name, damage);
    }

    /**
     * called by GameScene to set these params after reading from file
     * @param topLeft
     * @param bottomRight
     */
    public void setBounds(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    /**
     * we pass the gameEntities array backed list reference so that collissions can be determined
     * in this class.
     * @param gameEntities
     */
    public void setGameEntities(ArrayList<StationaryGameEntity> gameEntities) {
        this.gameEntities = gameEntities;
    }

    protected boolean withinBounds(Point proposedPos) {
        return !(proposedPos.x < topLeft.x) && !(proposedPos.x > bottomRight.x) &&
                !(proposedPos.y < topLeft.y) && !(proposedPos.y > bottomRight.y);
    }

    /**/
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
