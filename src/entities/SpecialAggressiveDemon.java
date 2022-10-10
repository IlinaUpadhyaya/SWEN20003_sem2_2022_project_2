package entities;

import bagel.Image;

public class SpecialAggressiveDemon extends AggressiveDemon {

    private final static Image SPECIAL_AGGRESIVE_DEMON_RIGHT = new Image("res/navec/navecRight.png");
    private final static Image SPECIAL_AGGRESIVE_DEMON_RIGHT_INVINCIBLE = new
            Image("res/navec/navecinvincibleRight.png");
    private final static Image SPECIAL_AGGRESIVE_DEMON_LEFT = new Image("res/navec/navecLeft.png");
    private final static Image SPECIAL_AGGRESIVE_DEMON_LEFT_INVINCIBLE = new
            Image("res/navec/navecinvincibleLeft.png");
    private final static Image SPECIAL_AGGRESIVE_DEMON_FIRE = new Image("res/navec/navecFire.png");
    private final static double FIRE_DAMAGE = 20;
    private final static double STARTING_HEALTH = 80;
    private final static double ATTACK_RADIUS = 200;

    public SpecialAggressiveDemon(double xCoOrd, double yCoOrd) {
        super(xCoOrd, yCoOrd);
        this.leftImage = SPECIAL_AGGRESIVE_DEMON_LEFT;
        this.rightImage = SPECIAL_AGGRESIVE_DEMON_RIGHT;
        this.leftInvincibleImage = SPECIAL_AGGRESIVE_DEMON_LEFT_INVINCIBLE;
        this.rightInvincibleImage = SPECIAL_AGGRESIVE_DEMON_RIGHT_INVINCIBLE;
        this.fireImage = SPECIAL_AGGRESIVE_DEMON_FIRE;
        this.setImageAndCalculate(this.rightImage);
        this.setName("Navec");
        this.setDamage(FIRE_DAMAGE);
        this.attackRadius = ATTACK_RADIUS;
        this.randomizeStateVariables();
        this.health = new HealthCalculator(STARTING_HEALTH, this.getName());
        this.flameThrower = new FlameThrower(this.fireImage, this.attackRadius);
        this.entityState = EntityState.ATTACK;
    }
}
