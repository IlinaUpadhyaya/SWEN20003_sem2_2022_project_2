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
    private static final int INVINCIBLE_TIMEOUT = 3000;
    private final Font MSG_FONT = new Font("res/frostbite.ttf", 40);
    private Timer invincibleTimer, cooldownTimer, attackTimer;

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

    private void checkForAnyDamageAndAttack() {
        Rectangle playerBoundingBox = getBoundingBox();
        StationaryGameEntity drawableToDelete = null;

        // check for sinkhole damage
        // sinkhole collision should be checked with proposed position
        for (StationaryGameEntity gameEntity : gameEntities) {
            if ((gameEntity).getName().equals("SINKHOLE")) {
                StationaryGameEntity sinkhole = gameEntity;
                if (playerBoundingBox.intersects(sinkhole.getBoundingBox())) {
                    health.onDamage(sinkhole.getDamage(), "Sinkhole");
                    drawableToDelete = gameEntity;
                }
            }
        }
        if (drawableToDelete != null) {
            gameEntities.remove(drawableToDelete);
        }

        // now do the same check with each enemy and absorb any fire damage
        StationaryGameEntity entityMarkedForDeletion = null;
        for (StationaryGameEntity gameEntity : gameEntities) {
            if (gameEntity instanceof DemonBehaviour) {
                DemonBehaviour demon = (DemonBehaviour) gameEntity;
                if (playerBoundingBox.intersects(demon.getFireBoundingBox())) {
                    if (this.entityState != EntityState.INVINCIBLE) {
                        health.onDamage(gameEntity.getDamage(), gameEntity.getName());
                        entityState = EntityState.INVINCIBLE;
                        invincibleTimer = new Timer(INVINCIBLE_TIMEOUT);
                    }
                }
                if (this.entityState == EntityState.ATTACK &&
                        playerBoundingBox.intersects(((StationaryGameEntity) demon).getBoundingBox())) {
                    boolean shouldDisappear = demon.onAttacked(PLAYER_DAMAGE_POINTS, this.getName());
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
                if(entityState == EntityState.IDLE || entityState == EntityState.INVINCIBLE){
                    entityState = EntityState.ATTACK;
                    attackTimer = new Timer(ATTACK_TIMEOUT);
                }
                else if (entityState == EntityState.COOLDOWN){
                    break;
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
        if (withinBounds(proposedPosition) && !super.collidesWithGameEntity(proposedPosition, "WALL",
                "TREE")) this.updatePlayerPos(proposedPosition);
        checkForAnyDamageAndAttack();
    }

    public Player(double xCoord, double yCoord) {
        super(new Point(xCoord, yCoord));
        this.healthPoints = PLAYER_MAX_HEALTH;
        this.setDamage(PLAYER_DAMAGE_POINTS);
        this.speed = PLAYER_SPEED;
        this.setImage(FAE_RIGHT);
        this.setName("Fae");
        health = new HealthCalculator(this.healthPoints, this.getName());
        this.entityState = EntityState.IDLE;
    }

    public boolean healthOver() {
        return health.healthOver();
    }

    public void onFrameUpdate() {
        if (this.timer != null) {
            this.timer.onFrameUpdate();

            if (this.entityState == EntityState.ATTACK) {
                if (attackTimer.isTimeUp()) {
                    this.entityState = EntityState.COOLDOWN;
                    cooldownTimer = new Timer(COOLDOWN_TIMEOUT);
                }
            }
            else if (this.entityState == EntityState.COOLDOWN) {
                if (cooldownTimer.isTimeUp()) entityState = EntityState.IDLE;
            }
            else if (this.entityState == EntityState.INVINCIBLE) {
                if (invincibleTimer.isTimeUp()) this.entityState = EntityState.IDLE;
            }
        }
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
