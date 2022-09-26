import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;

public class AggressiveDemon extends MovableGameEntity implements DemonBehaviour {

    private Direction direction;

    protected double attackRadius = 150;
    protected FlameThrower flameThrower;

    private Random randomizer = new Random();
    protected Image leftImage, rightImage, leftInvincibleImage,
            rightInvincibleImage, fireImage;


    public AggressiveDemon(double xCoOrd, double yCoOrd) {
        super(new Point(xCoOrd, yCoOrd));
        this.leftImage = new Image("res/demon/demonLeft.png");
        this.rightImage = new Image("res/demon/demonRight.png");
        this.leftInvincibleImage = new Image("res/demon/demonInvincibleLeft" +
                ".png");
        this.rightInvincibleImage = new Image("res/demon" +
                "/demonInvincibleRight.png");
        this.setImage(this.rightImage); /*else no image set*/
        this.setName("AggressiveDemon");
        this.setDamage(20);
        this.fireImage = new Image("res/demon/demonFire.png");
        this.attackRadius = 150;
        this.healthPoints = 40;

        this.randomizeStateVariables();
        this.health = new HealthCalculator(this.healthPoints, "AggressiveDemon");
        this.flameThrower = new FlameThrower(this.fireImage, this.attackRadius);
        this.entityState = EntityState.ATTACK;
    }

    protected void randomizeStateVariables() {
        int randomNum = randomizer.nextInt(4);
        switch (randomNum) {
            case 0:
                this.direction = Direction.UP;
                break;
            case 1:
                this.direction = Direction.DOWN;
                break;
            case 2:
                this.direction = Direction.LEFT;
                this.setImage(this.leftImage);
                break;
            case 3:
                this.direction = Direction.RIGHT;
                this.setImage(this.rightImage);
                break;
        }
        this.speed = 0.2 + this.randomizer.nextDouble() * (0.7 - 0.2);
        this.setImage(this.leftImage);
    }

    @Override
    public Rectangle getFireBoundingBox() {
        return flameThrower.getBoundingBox();
    }

    private Point getNewPointInReverseDirection() {
        /*switch lateral image*/
        switch (direction) {
            case UP:
                direction = Direction.DOWN;
                break;
            case DOWN:
                direction = Direction.UP;
                break;
            case LEFT:
                direction = Direction.RIGHT;

                break;
            case RIGHT:
                direction = Direction.LEFT;
                break;
        }
        return getProposedLocation();
    }

    private Point getProposedLocation() {
        Point currentLoc = super.getTopLeftPosition();
        Point newPoint = null;
        switch (direction) {
            case UP:
                newPoint = new Point(currentLoc.x, currentLoc.y - speed);
                break;
            case DOWN:
                newPoint = new Point(currentLoc.x, currentLoc.y + speed);
                break;
            case LEFT:
                newPoint = new Point(currentLoc.x - speed, currentLoc.y);
                break;
            case RIGHT:
                newPoint = new Point(currentLoc.x + speed, currentLoc.y);
                break;
        }
        return newPoint;
    }

    public void onFrameUpdate(Rectangle playerBox) {
        flameThrower.checkForFire(super.getBoundingBox(), playerBox);
        Point proposedPoint = getProposedLocation();
        if (proposedPoint == null) {
            throw new NullPointerException();
        }
        if (super.collidesWithGameEntity(proposedPoint, "WALL", "TREE",
                "SINKHOLE")) {
            super.recalculateParameters(getNewPointInReverseDirection());
        } else if (!withinBounds(proposedPoint)) {
            super.recalculateParameters(getNewPointInReverseDirection());
        } else {
            super.recalculateParameters(proposedPoint);
        }
    }

    @Override
    public boolean onAttacked(double damage, String damagingClass) {
        if (this.entityState == EntityState.INVINCIBLE) {
            return this.healthOver();
        }
        this.entityState = EntityState.INVINCIBLE;
        if (this.direction == Direction.LEFT) this.setImage(this.leftInvincibleImage);
        else this.setImage(this.rightInvincibleImage);
        return this.health.healthOver();
    }

    @Override
    public boolean healthOver() {
        return this.health.healthOver();
    }

    @Override
    public String toString() {
        return "Demon";
    }

    @Override
    public void draw() {
        super.draw();
        flameThrower.draw();
        DrawOptions drawOptions = new DrawOptions();
        final double ORANGE_THRESHOLD = 65;
        final double RED_THRESHOLD = 35;

        if (this.health.getRemainingHealthPercentage() >= ORANGE_THRESHOLD) {
            drawOptions = drawOptions.setBlendColour(0, 0.8, 0.2);
        } else if (this.health.getRemainingHealthPercentage() >= RED_THRESHOLD) {
            drawOptions = drawOptions.setBlendColour(0.9, 0.6, 0);
        } else {
            drawOptions = drawOptions.setBlendColour(1, 0, 0);
        }
        HEALTH_BAR_FONT.drawString((int) (Math.round(this.health.getRemainingHealthPercentage())) + "%",
                this.getTopLeftPosition().x, this.getTopLeftPosition().y + HEALTH_BAR_LOC_Y_OFFSET, drawOptions);
    }
}
