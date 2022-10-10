package entities;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import utils.Timer;

import java.util.Random;

public class AggressiveDemon extends MovableGameEntity implements DemonBehaviour {
    private final static Image AGGRESIVE_DEMON_RIGHT = new Image("res/demon/demonRight.png");
    private final static Image AGGRESIVE_DEMON_RIGHT_INVINCIBLE = new Image("res/demon/demoninvincibleRight.png");
    private final static Image AGGRESIVE_DEMON_LEFT = new Image("res/demon/demonLeft.png");
    private final static Image AGGRESIVE_DEMON_LEFT_INVINCIBLE = new Image("res/demon/demoninvincibleLeft.png");
    private final static Image AGGRESIVE_DEMON_FIRE = new Image("res/demon/demonFire.png");
    private final static double FIRE_DAMAGE = 10;
    private final static double STARTING_HEALTH = 40;
    private final static double ATTACK_RADIUS = 150;
    protected double attackRadius;
    protected FlameThrower flameThrower;
    protected Image leftImage, rightImage, leftInvincibleImage, rightInvincibleImage, fireImage;
    private Direction direction;
    private final Random randomizer = new Random();
    private Timer timer = null;
    private double originalSpeed;

    public AggressiveDemon(double xCoOrd, double yCoOrd) {
        super(new Point(xCoOrd, yCoOrd));
        this.leftImage = AGGRESIVE_DEMON_LEFT;
        this.rightImage = AGGRESIVE_DEMON_RIGHT;
        this.leftInvincibleImage = AGGRESIVE_DEMON_LEFT_INVINCIBLE;
        this.rightInvincibleImage = AGGRESIVE_DEMON_RIGHT_INVINCIBLE;
        this.setImageAndCalculate(this.rightImage); /*else no image set*/
        this.setName("Demon");
        this.fireImage = AGGRESIVE_DEMON_FIRE;
        this.setDamage(FIRE_DAMAGE);
        this.attackRadius = ATTACK_RADIUS;
        this.randomizeStateVariables();
        this.health = new HealthCalculator(STARTING_HEALTH, this.getName());
        this.flameThrower = new FlameThrower(this.fireImage, this.attackRadius);
        this.entityState = EntityState.ATTACK;
    }

    @Override
    public Rectangle getFireBoundingBox() {
        return flameThrower.getBoundingBox();
    }

    public void onFrameUpdate(Rectangle playerBox) {
        flameThrower.checkForFire(super.getBoundingBox(), playerBox);
        Point proposedPoint = getProposedLocation();
        if (proposedPoint == null) {
            throw new NullPointerException();
        }
        if (super.collidesWithGameEntity(proposedPoint, "WALL", "TREE", "SINKHOLE")) {
            super.recalculateParameters(getNewPointInReverseDirection());
        } else if (!withinBounds(proposedPoint)) {
            super.recalculateParameters(getNewPointInReverseDirection());
        } else {
            super.recalculateParameters(proposedPoint);
        }
        if (this.timer != null) {
            this.timer.clockTick();
            if (this.timer.isTimeUp()) {
                this.entityState = EntityState.ATTACK;
                this.timer = null;
                if (this.direction == Direction.LEFT)
                    this.setImageAndCalculate(this.leftImage);
                else if (this.direction == Direction.RIGHT)
                    this.setImageAndCalculate(this.rightImage);
                else if (this.direction == Direction.UP || this.direction == Direction.DOWN)
                    this.setImageAndCalculate(this.rightImage);
            }
        }
    }

    @Override
    public boolean onAttacked(double damage, String damagingClass) {
        if (this.entityState == EntityState.INVINCIBLE) {
            return this.healthOver();
        }

        this.entityState = EntityState.INVINCIBLE;
        this.timer = new Timer(AggressiveDemon.INVINCIBLE_TIMEOUT);

        if (this.direction == Direction.LEFT)
            this.setImageAndCalculate(this.leftInvincibleImage);
        else if (this.direction == Direction.RIGHT)
            this.setImageAndCalculate(this.rightInvincibleImage);
        else if (this.direction == Direction.UP || this.direction == Direction.DOWN)
            this.setImageAndCalculate(this.rightInvincibleImage);

        this.health.onDamage(damage, damagingClass);
        return this.health.healthOver();
    }

    @Override
    public void draw() {
        if (disappeared) return;
        super.draw();
        flameThrower.draw();
        HEALTH_BAR_FONT.drawString((int)(Math.round(this.health.getRemainingHealthPercentage())) + "%",
                this.getTopLeftPosition().x, this.getTopLeftPosition().y + HEALTH_BAR_LOC_Y_OFFSET, COLOUR);
    }

    public void updateSpeed(int timeScale) {
        if (timeScale == 0) speed = originalSpeed;
        else if (timeScale > 0) speed = originalSpeed * Math.pow(1.5, timeScale);
        else speed = originalSpeed * Math.pow(0.5, -timeScale);
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
                this.setImageAndCalculate(this.leftImage);
                break;
            case 3:
                this.direction = Direction.RIGHT;
                this.setImageAndCalculate(this.rightImage);
                break;
        }
        this.speed = 0.2 + this.randomizer.nextDouble() * (0.7 - 0.2);
        this.originalSpeed = this.speed;
        this.setImageAndCalculate(this.leftImage);
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
                if (this.entityState == EntityState.INVINCIBLE) this.setImageAndCalculate(this.rightInvincibleImage);
                else this.setImageAndCalculate(this.rightImage);
                break;
            case RIGHT:
                direction = Direction.LEFT;
                if (this.entityState == EntityState.INVINCIBLE) this.setImageAndCalculate(this.leftInvincibleImage);
                else this.setImageAndCalculate(this.leftImage);
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
}
