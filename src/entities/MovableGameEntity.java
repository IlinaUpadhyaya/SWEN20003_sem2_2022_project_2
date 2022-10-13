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
    private Point topLeft, bottomRight;
    protected ArrayList<StationaryGameEntity> gameEntities;
    protected double speed;
    protected Timer timer;
    protected EntityState entityState;

    MovableGameEntity(Point position, Image image, String name, double damage) {
        super(position, image, name, damage);
    }

    /**
     * called by GameScene to set these params after reading from file
     *
     * @param topLeft: top left bound of game screen
     * @param bottomRight: bottom right bound of game screen
     */
    public void setBounds(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    /**
     * we pass the gameEntities array backed list reference at runtime once so that collisions can
     * be determined in each frame update. Setting this once saves the performance overhead of
     * passing in the reference for every frame refresh.
     *
     * @param gameEntities: array list of game entities
     */
    public void setGameEntities(ArrayList<StationaryGameEntity> gameEntities) {
        this.gameEntities = gameEntities;
    }

    protected Rectangle getBoundingBoxAt(Point newTopLeft) {
        return new Rectangle(newTopLeft, this.getImage().getWidth(),
                this.getImage().getHeight());
    }

    /**
     * sets the new topLeft Position passed in and recalculates the
     * bounding box
     */
    protected void setNewPosition(Point newPos) {
        this.topLeftPosition = newPos;
        this.recalculateBoundingBox();
    }

    /**
     * determines if the proposed position is within bounds
     */
    protected boolean withinBounds(Point proposedPos) {
        return !(proposedPos.x < topLeft.x) && !(proposedPos.x > bottomRight.x) &&
                !(proposedPos.y < topLeft.y) && !(proposedPos.y > bottomRight.y);
    }

    /**
     * determines if this entity would intersect the bounding boxes of the other entities
     * of the gameEntityNames passed in
     */
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
