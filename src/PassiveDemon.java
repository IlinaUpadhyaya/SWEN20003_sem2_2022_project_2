import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class PassiveDemon extends StationaryGameEntity implements DemonBehaviour {
    final static private Image PASSIVE_DEMON_IMAGE = new Image("res/demon/demonRight.png");
    final static private Image PASSIVE_DEMON_FIRE = new Image("res/demon/demonFire.png");
    private static final double FIRE_DAMAGE = 10;
    private double attackRadius = 150;
    private double maxHealthPoints;
    private HealthCalculator health;
    private EntityState entityState;
    private FlameThrower flameThrower;
    Timer timer;

    public PassiveDemon(double xCoOrd, double yCoOrd) {
        super(new Point(xCoOrd, yCoOrd), PASSIVE_DEMON_IMAGE, "Demon", FIRE_DAMAGE);
        this.maxHealthPoints = 40;
        health = new HealthCalculator(this.maxHealthPoints, "Demon");
        flameThrower = new FlameThrower(PASSIVE_DEMON_FIRE, this.attackRadius);
        this.entityState = EntityState.ATTACK;
    }

    @Override
    public Rectangle getFireBoundingBox() {
        return flameThrower.getBoundingBox();
    }

    public void onFrameUpdate(Rectangle playerBox) {
        if (this.timer != null) {
            this.timer.onFrameUpdate();
        }
        flameThrower.checkForFire(super.getBoundingBox(), playerBox);
        if (this.entityState == EntityState.INVINCIBLE) {
            if (timer.isTimeUp()) this.entityState = EntityState.ATTACK;
        }
    }

    @Override
    public boolean onAttacked(double damage, String damagingEntity) {
        if (this.entityState == EntityState.ATTACK) {
            this.health.onDamage(damage, damagingEntity);
        } else {
            this.entityState = EntityState.INVINCIBLE;
            timer = new Timer(INVINCIBLE_TIMEOUT);
        }
        return this.health.healthOver();
    }

    @Override
    public boolean healthOver() {
        return this.health.healthOver();
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
