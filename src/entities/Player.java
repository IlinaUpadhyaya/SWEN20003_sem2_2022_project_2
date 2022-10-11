package entities;

import bagel.Font;
import bagel.Image;
import bagel.Keys;
import bagel.util.Point;
import bagel.util.Rectangle;
import utils.Timer;

public class Player extends MovableGameEntity {
    private final static int HEALTH_X = 20;
    private final static int HEALTH_Y = 25;
    private final static int FONT_SIZE = 30;
    private final static double PLAYER_MAX_HEALTH = 100;
    private final static double PLAYER_DAMAGE_POINTS = 20;
    private final static double PLAYER_SPEED = 2;
    private final static int ATTACK_TIMEOUT = 1000;
    private final static int COOLDOWN_TIMEOUT = 2000;
    private final static int INVINCIBLE_TIMEOUT = 3000;
    private final static Image FAE_LEFT = new Image("res/fae/faeLeft.png");
    private final static Image FAE_RIGHT = new Image("res/fae/faeRight.png");
    private final static Image FAE_ATTACK_RIGHT = new Image("res/fae/faeAttackRight.png");
    private final static Image FAE_ATTACK_LEFT = new Image("res/fae/faeAttackLeft.png");
    private final static Font MSG_FONT = new Font("res/frostbite.ttf", FONT_SIZE);
    private final static String NAME = "Fae";
    private boolean invincible = false;
    private Timer invincibilityTimer = null;

    public Player(double xCoord, double yCoord) {
        super(new Point(xCoord, yCoord), FAE_RIGHT, NAME, PLAYER_DAMAGE_POINTS);
        this.speed = PLAYER_SPEED;
        health = new HealthCalculator(PLAYER_MAX_HEALTH, this.getName());
        this.entityState = EntityState.IDLE;
    }

    public void onFrameUpdate() {
        if (this.invincibilityTimer != null) {
            this.invincibilityTimer.clockTick();
            if (this.invincibilityTimer.isTimeUp()) {
                this.invincible = false;
                this.invincibilityTimer = null;
            }
        }

        if (this.timer != null) {
            this.timer.clockTick();
            switch (this.entityState) {
                case ATTACK:
                    if (this.timer.isTimeUp()) {
                        this.entityState = EntityState.COOLDOWN;
                        this.timer = new Timer(Player.COOLDOWN_TIMEOUT);
                        toggleImage();
                    }
                    break;

                case COOLDOWN:
                    if (this.timer.isTimeUp()) {
                        this.entityState = EntityState.IDLE;
                        this.timer = null;
                    }

                case IDLE:
                    break;
            }
        }

        // now do the same check with each enemy and absorb any fire damage
        Rectangle playerBoundingBox = getBoundingBox();
        StationaryGameEntity entityMarkedForDeletion = null;
        for (StationaryGameEntity gameEntity : gameEntities) {
            if (gameEntity instanceof DemonBehaviour) {
                DemonBehaviour demon = (DemonBehaviour) gameEntity;
                if (!this.invincible && playerBoundingBox.intersects(demon.getFireBoundingBox())) {
                    health.onDamage(gameEntity.getDamage(),
                            gameEntity.getName());
                    this.invincible = true;
                    this.invincibilityTimer = new Timer(Player.INVINCIBLE_TIMEOUT);
                }
                if (this.entityState == EntityState.ATTACK &&
                        playerBoundingBox.intersects(((StationaryGameEntity) demon).getBoundingBox())) {
                    boolean disappear = demon.onAttacked(this.getDamage(),
                            this.getName());
                    if (disappear) entityMarkedForDeletion = gameEntity;
                }
            }
        }
        if (entityMarkedForDeletion != null) gameEntities.remove(entityMarkedForDeletion);
    }

    public void handleKeyInput(Keys key) {
        Point proposedPosition = null;
        switch (key) {
            case A:
                if (this.entityState == EntityState.IDLE) {
                    entityState = EntityState.ATTACK;
                    toggleImage();
                    this.timer = new Timer(ATTACK_TIMEOUT);
                }
                return;
            case LEFT:
                proposedPosition = getXShiftedLocation(-this.speed);
                break;
            case RIGHT:
                proposedPosition = getXShiftedLocation(this.speed);
                break;
            case UP:
                proposedPosition = getYShiftedLocation(-this.speed);
                break;
            case DOWN:
                proposedPosition = getYShiftedLocation(this.speed);
                break;
            default:
                return;
        }
        if (withinBounds(proposedPosition) && !super.collidesWithGameEntity(proposedPosition, Wall.NAME, Tree.NAME)) {
            this.updatePlayerPos(proposedPosition);
            checkForSinkholeDamage();
        }
    }

    @Override
    public void draw() {
        super.draw();
        MSG_FONT.drawString((int) (Math.round(this.health.getRemainingHealthPercentage())) + "%", HEALTH_X,
                HEALTH_Y, COLOUR);
    }

    private void toggleImage() {
        if (this.getImage() == Player.FAE_RIGHT) {
            this.setImageAndCalculate(Player.FAE_ATTACK_RIGHT);
        }
        else if (this.getImage() == Player.FAE_LEFT) {
            this.setImageAndCalculate(Player.FAE_ATTACK_LEFT);
        }
        else if (this.getImage() == Player.FAE_ATTACK_RIGHT) {
            this.setImageAndCalculate(Player.FAE_RIGHT);
        }
        else if (this.getImage() == Player.FAE_ATTACK_LEFT) {
            this.setImageAndCalculate(Player.FAE_LEFT);
        }
    }

    private void updatePlayerPos(Point newPos) {
        Point currentPos = super.getTopLeftPosition();
        if (newPos.x >= currentPos.x) {
            if (this.entityState == EntityState.ATTACK)
                super.setImageAndCalculate(FAE_ATTACK_RIGHT);
            else
                super.setImageAndCalculate(FAE_RIGHT);
        }
        else if (newPos.x < currentPos.x) {
            if (this.entityState == EntityState.ATTACK)
                super.setImageAndCalculate(FAE_ATTACK_LEFT);
            else
                super.setImageAndCalculate(FAE_LEFT);
        }
        super.recalculateParameters(newPos);
    }

    private Point getXShiftedLocation(double xShift) {
        Point currentPos = super.getTopLeftPosition();
        return new Point(xShift + currentPos.x, currentPos.y);
    }

    private Point getYShiftedLocation(double yShift) {
        Point currentPos = super.getTopLeftPosition();
        return new Point(currentPos.x, currentPos.y + yShift);
    }

    private void checkForSinkholeDamage() {
        Rectangle playerBoundingBox = getBoundingBox();
        StationaryGameEntity entityMarkedForDeletion = null;

        for (StationaryGameEntity gameEntity : gameEntities) {
            if (gameEntity.getName().equals(Sinkhole.NAME)) {
                Sinkhole sinkhole = (Sinkhole) gameEntity;
                if (playerBoundingBox.intersects(sinkhole.getBoundingBox())) {
                    {
                        if (!this.invincible) {
                            health.onDamage(sinkhole.getDamage(), Sinkhole.NAME);
                            entityMarkedForDeletion = gameEntity;
                        }
                    }
                }
            }
        }
        if (entityMarkedForDeletion != null) gameEntities.remove(entityMarkedForDeletion);
    }
}
