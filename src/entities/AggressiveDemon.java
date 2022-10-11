package entities;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import utils.Timer;

import java.util.Random;

public class AggressiveDemon extends MovableGameEntity implements DemonBehaviour {
    private final static Image AGGRESSIVE_DEMON_RIGHT = new Image("res/demon/demonRight.png");
    private final static Image AGGRESSIVE_DEMON_RIGHT_INVINCIBLE = new Image("res/demon/demonInvincibleRight.png");
    private final static Image AGGRESSIVE_DEMON_LEFT = new Image("res/demon/demonLeft.png");
    private final static Image AGGRESSIVE_DEMON_LEFT_INVINCIBLE = new Image("res/demon/demonInvincibleLeft.png");
    private final static Image AGGRESSIVE_DEMON_FIRE = new Image("res/demon/demonFire.png");
    private final static double FIRE_DAMAGE = 10;
    private final static double STARTING_HEALTH = 40;
    private final static double ATTACK_RADIUS = 150;
    private final static String NAME = "Demon";
    private final static double MIN_SPEED = 0.2;
    private final static double MAX_SPEED = 0.7;
    private final static int NUM_DIRECTIONS = 4;
    protected double attackRadius;
    protected FlameThrower flameThrower;
    protected Image leftImage, rightImage, leftInvincibleImage, rightInvincibleImage, fireImage;
    private Direction direction;
    private double originalSpeed;
    private final Random randomizer = new Random();

    AggressiveDemon(Point position, Image rightImage, String name, Image leftImage, Image leftInvincibleImage,
                    Image rightInvincibleImage, Image fireImage, double attackRadius, double damage,
                    double startingHealth) {
        super(position, rightImage, name, damage);
        this.leftImage = leftImage;
        this.rightImage = rightImage;
        this.leftInvincibleImage = leftInvincibleImage;
        this.rightInvincibleImage = rightInvincibleImage;
        this.fireImage = fireImage;
        this.attackRadius = attackRadius;
        this.health = new HealthCalculator(startingHealth, name);
        this.initialiseState();
    }

    public AggressiveDemon(double xCoOrd, double yCoOrd) {
        this(new Point(xCoOrd, yCoOrd), AGGRESSIVE_DEMON_RIGHT, NAME, AGGRESSIVE_DEMON_LEFT,
                AGGRESSIVE_DEMON_LEFT_INVINCIBLE, AGGRESSIVE_DEMON_RIGHT_INVINCIBLE, AGGRESSIVE_DEMON_FIRE,
                ATTACK_RADIUS, FIRE_DAMAGE, STARTING_HEALTH);
    }

    protected void initialiseState() {
        this.randomizeStateVariables();
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
        if (super.collidesWithGameEntity(proposedPoint, Wall.NAME, Tree.NAME,
                Sinkhole.NAME)) {
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
                else
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
        else
            this.setImageAndCalculate(this.rightInvincibleImage);
        this.health.onDamage(damage, damagingClass);
        return this.health.healthOver();
    }

    @Override
    public void draw() {
        super.draw();
        flameThrower.draw();
        HEALTH_BAR_FONT.drawString((int) (Math.round(this.health.getRemainingHealthPercentage())) + "%",
                this.getTopLeftPosition().x, this.getTopLeftPosition().y + HEALTH_BAR_LOC_Y_OFFSET, COLOUR);
    }

    public void updateSpeed(int timeScale) {
        if (timeScale == 0) speed = originalSpeed;
        else if (timeScale > 0) speed = originalSpeed * Math.pow(1.5, timeScale);
        else speed = originalSpeed * Math.pow(0.5, -timeScale);
    }

    protected void randomizeStateVariables() {
        int randomNum = randomizer.nextInt(NUM_DIRECTIONS);
        this.setImageAndCalculate(this.rightImage);
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
                break;
        }
        this.speed = MIN_SPEED + this.randomizer.nextDouble() * (MAX_SPEED - MIN_SPEED);
        this.originalSpeed = this.speed;
    }

    private Point getNewPointInReverseDirection() {
        // switch lateral image
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
