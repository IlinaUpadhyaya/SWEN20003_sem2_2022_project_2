import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Player extends MovableGameEntity {
    private static final Image FAE_LEFT = new Image("res/fae/faeLeft.png");
    private static final Image FAE_RIGHT = new Image("res/fae/faeRight.png");
    private static final Image FAE_ATTACK_LEFT = new Image("res/fae/faeAttackLeft.png");
    private static final Image FAE_ATTACK_RIGHT = new Image("res/fae/faeAttackRight.png");
    protected final double PLAYER_MAX_HP = 100;
    // offset to adjust player bounding box leftwards
    private final int OFFSET = 10;

    public Player(double xCoord, double yCoord) {
        super(new Point(xCoord, yCoord), FAE_RIGHT);
        this.entityMaxHP = PLAYER_MAX_HP;
        this.entityHP = this.entityMaxHP;
        this.remainingHealthPercentage = 100.0;
    }

    @Override
    public String toString() {
        return "Fae";
    }

    private void updatePlayerPos(Point newPos) {
        Point currentPos = super.getPosition();
        if (newPos.x > currentPos.x) {
            super.setImage(this.FAE_RIGHT);
        } else if (newPos.x < currentPos.x) {
            super.setImage(this.FAE_LEFT);
        }
        super.setPosition(newPos);
    }

    public Point getXShiftedLocation(double xShift) {
        Point currentPos = super.getPosition();
        return new Point(xShift + currentPos.x, currentPos.y);
    }

    public Point getYShiftedLocation(double yShift) {
        Point currentPos = super.getPosition();
        return new Point(currentPos.x, currentPos.y + yShift);
    }

    public void updatePlayerXPos(double xShift) {
        Point newPos = getXShiftedLocation(xShift);
        updatePlayerPos(newPos);
    }

    public void updatePlayerYPos(double yShift) {
        Point newPos = getYShiftedLocation(yShift);
        updatePlayerPos(newPos);
    }

    protected Rectangle getProposedBoundingBox(Point point) {
        Rectangle boundingBox = super.getImage().getBoundingBoxAt(point);
        Rectangle adjBoundingBox = new Rectangle(boundingBox.left() - OFFSET, boundingBox.top(),
                boundingBox.right() - boundingBox.left(), boundingBox.bottom() - boundingBox.top());
        return adjBoundingBox;
    }
}
