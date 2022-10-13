package entities;

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import utils.Timer;

/**
 * Class represents Passive Demon game object
 */
public class PassiveDemon extends StationaryGameEntity implements DemonBehaviour {
    /**
     * name must match that in the csv file for this entity. This name will print on damage messages
     * involving this entity.
     */
    public final static String NAME = "Demon";
    private final static Image PASSIVE_DEMON_IMAGE = new Image("res/demon/demonRight.png");
    private final static Image PASSIVE_DEMON_INVINCIBLE_IMAGE = new Image("res/demon/demoninvIncibleRight.png");
    private final static Image PASSIVE_DEMON_FIRE = new Image("res/demon/demonFire.png");
    private final static double FIRE_DAMAGE = 10;
    private final static double STARTING_HEALTH = 40;
    private final static double ATTACK_RADIUS = 150;
    private final static int INVINCIBLE_TIMEOUT = 3000;
    private final FlameThrower flameThrower;
    private EntityState entityState;
    private Timer timer = null;

    /**
     * Constructor
     *
     * @param position representing top left co-ordinate of object in the scene
     */
    public PassiveDemon(Point position) {
        super(position, PASSIVE_DEMON_IMAGE, NAME, FIRE_DAMAGE);
        health = new HealthCalculator(STARTING_HEALTH, NAME);
        flameThrower = new FlameThrower(PASSIVE_DEMON_FIRE, ATTACK_RADIUS);
        this.entityState = EntityState.ATTACK;
    }

    @Override
    public Rectangle getFireBoundingBox() {
        return flameThrower.getBoundingBox();
    }

    public void onFrameUpdate(Rectangle playerBox) {
        flameThrower.checkForFire(super.getBoundingBox(), playerBox);
        // check timer and update state
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
        super.draw();
        flameThrower.draw();
        HEALTH_BAR_FONT.drawString((int) (Math.round(this.health.getRemainingHealthPercentage())) + "%",
                this.getTopLeftPosition().x, this.getTopLeftPosition().y + HEALTH_BAR_LOC_Y_OFFSET, COLOUR);
    }

}
