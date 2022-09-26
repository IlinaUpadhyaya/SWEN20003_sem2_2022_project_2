import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Player extends MovableGameEntity {
    private static final Image FAE_LEFT = new Image("res/fae/faeLeft.png");
    private static final Image FAE_RIGHT = new Image("res/fae/faeRight.png");
    private static final Image FAE_ATTACK_RIGHT = new Image("res/fae/faeAttackRight.png");
    private static final Image FAE_ATTACK_LEFT = new Image("res/fae/faeAttackLeft.png");

    private static final double PLAYER_MAX_HEALTH = 100;
    private static final double PLAYER_DAMAGE_POINTS = 20;
    private static final double PLAYER_SPEED = 2;

    private static final int ATTACK_TIMEOUT = 1000;
    private static final int COOLDOWN_TIMEOUT = 2000;
    private final Font MSG_FONT = new Font("res/frostbite.ttf", 40);
    private void updatePlayerPos(Point newPos) {
        Point currentPos = super.getTopLeftPosition();
        if (newPos.x > currentPos.x) {
            if (this.entityState == EntityState.ATTACK)
                super.setImage(this.FAE_ATTACK_RIGHT);
            else
                super.setImage(this.FAE_RIGHT);
        } else if (newPos.x < currentPos.x) {
            if (this.entityState == EntityState.ATTACK)
                super.setImage(this.FAE_ATTACK_LEFT);
            else
                super.setImage(this.FAE_LEFT);
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

    /* sinkhole collision should be checked with proposed position ? */
    private void checkForAnyDamageAndAttack() {
        Rectangle playerBoundingBox = getBoundingBox();
        StationaryGameEntity drawableToDelete = null;

        // check for sinkhole damage
        for (StationaryGameEntity gameEntity : gameEntities) {
            if (((StationaryGameEntity) gameEntity).getName().equals("SINKHOLE")) {
                StationaryGameEntity sinkhole = (StationaryGameEntity) gameEntity;
                if (playerBoundingBox.intersects(sinkhole.getBoundingBox())) {
                    health.onDamage(sinkhole.getDamage(), "Sinkhole");
                    drawableToDelete = gameEntity;
                }
            }
        }
        if (drawableToDelete != null) {
            gameEntities.remove(drawableToDelete);
        }

        // now same check with each enemy and absorb any fire damage
        StationaryGameEntity entityMarkedForDeletion = null;
        for (StationaryGameEntity gameEntity : gameEntities) {
            if (gameEntity instanceof DemonBehaviour) {
                DemonBehaviour demon = (DemonBehaviour) gameEntity;
                if (playerBoundingBox.intersects(demon.getFireBoundingBox())) {
                    health.onDamage(gameEntity.getDamage(),
                            gameEntity.toString());
                }
                if (this.entityState == EntityState.ATTACK &&
                        playerBoundingBox.intersects(((StationaryGameEntity) demon).getBoundingBox())) {
                    boolean shouldDisappear = demon.onAttacked(this.getDamage(),
                            this.getName());
                    if (shouldDisappear) entityMarkedForDeletion = gameEntity;
                }
            }
        }
        if (entityMarkedForDeletion != null)
            this.gameEntities.remove(entityMarkedForDeletion);
    }

    public void handleKeyInput(Keys key) {
        Point proposedPosition = null;
        switch (key) {
            case A:
                if (this.entityState == EntityState.INVINCIBLE) {
                    entityState = EntityState.ATTACK;
                    this.timer = new Timer(ATTACK_TIMEOUT);
                }
                break;
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
                break;
        }
        if (key == Keys.A) return;
        if (withinBounds(proposedPosition) && !super.collidesWithGameEntity(proposedPosition,
                "WALL", "TREE"))
            this.updatePlayerPos(proposedPosition);

        checkForAnyDamageAndAttack();
    }

    public Player(double xCoord, double yCoord) {
        super(new Point(xCoord, yCoord));
        this.healthPoints = PLAYER_MAX_HEALTH;
        this.setDamage(PLAYER_DAMAGE_POINTS);
        this.speed = PLAYER_SPEED;
        this.setImage(FAE_RIGHT);
        this.setName("Fae");
        health = new HealthCalculator(this.healthPoints, this.toString());
        this.entityState = EntityState.INVINCIBLE;
    }

    public boolean healthOver() {
        return health.healthOver();
    }

    public void onFrameUpdate() {
        if (this.entityState == EntityState.ATTACK) {
            if (this.timer.isTimeUp()) {
                this.entityState = EntityState.IDLE;
                this.timer = new Timer(2000);
            }
        } else if (this.entityState == EntityState.IDLE) {
            if (this.timer.isTimeUp())
                this.entityState = EntityState.INVINCIBLE;
        }
    }

    @Override
    public String toString() {
        return "Fae";
    }

    @Override
    public void draw() {
        super.draw();
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
        MSG_FONT.drawString((int) (Math.round(this.health.getRemainingHealthPercentage())) + "%", 20, 25,
                drawOptions);
    }
}
