package entities;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import utils.Timer;

public class PassiveDemon extends StationaryGameEntity implements DemonBehaviour {

    private final static Image PASSIVE_DEMON_IMAGE = new Image("res/demon/demonRight.png");
    private final static Image PASSIVE_DEMON_INVINCIBLE_IMAGE = new Image("res/demon/demoninvincibleRight.png");
    private final static Image PASSIVE_DEMON_FIRE = new Image("res/demon/demonFire.png");

    private final static double FIRE_DAMAGE = 10;
    private final static double STARTING_HEALTH = 40;
    private final static double ATTACK_RADIUS = 150;
    private final static int INVINCIBLE_TIMEOUT = 3000;
    private EntityState entityState;
    private final FlameThrower flameThrower;
    private Timer timer = null;

    public PassiveDemon(double xCoOrd, double yCoOrd) {
        super(new Point(xCoOrd, yCoOrd), PASSIVE_DEMON_IMAGE, "Demon", FIRE_DAMAGE);
        health = new HealthCalculator(STARTING_HEALTH, this.getName());
        flameThrower = new FlameThrower(PASSIVE_DEMON_FIRE, ATTACK_RADIUS);
        this.entityState = EntityState.ATTACK;
    }

    @Override
    public Rectangle getFireBoundingBox() {
        return flameThrower.getBoundingBox();
    }

    public void onFrameUpdate(Rectangle playerBox) {
        // check timer and update state
        flameThrower.checkForFire(super.getBoundingBox(), playerBox);
        if (this.timer != null) {
            this.timer.clockTick();
            if (this.timer.isTimeUp()) {
                this.entityState = EntityState.ATTACK;
                this.timer = null;
                this.setImageAndCalculate(PASSIVE_DEMON_IMAGE);
            }
        }
    }

    @Override
    public boolean onAttacked(double damage, String damagingEntity) {
        if (this.entityState == EntityState.ATTACK) {
            this.health.onDamage(damage, damagingEntity);
            this.entityState = EntityState.INVINCIBLE;
            this.setImageAndCalculate(PASSIVE_DEMON_INVINCIBLE_IMAGE);
            this.timer = new Timer(PassiveDemon.INVINCIBLE_TIMEOUT);
        }
        return this.health.healthOver();
    }

    @Override
    public void draw() {
        if (disappeared) return;
        super.draw();
        flameThrower.draw();
        HEALTH_BAR_FONT.drawString((int) (Math.round(this.health.getRemainingHealthPercentage())) + "%",
                this.getTopLeftPosition().x, this.getTopLeftPosition().y + HEALTH_BAR_LOC_Y_OFFSET, COLOUR);
    }

}
