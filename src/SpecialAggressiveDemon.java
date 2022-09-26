import bagel.Image;

public class SpecialAggressiveDemon extends AggressiveDemon{
    public SpecialAggressiveDemon(double xCoOrd, double yCoOrd) {
        super(xCoOrd, yCoOrd);
        this.leftImage = new Image("res/navec/navecLeft.png");
        this.rightImage = new Image("res/navec/navecRight.png");
        this.leftInvincibleImage = new Image("res/navec/navecInvincibleLeft" +
                ".png");
        this.rightInvincibleImage = new Image("res/navec" +
                "/navecInvincibleRight.png");
        this.fireImage = new Image("res/navec/navecFire.png");
        this.setImage(this.rightImage);
        this.setName("Navec");
        this.setDamage(20);
        this.attackRadius = 150;
        this.healthPoints = 40;
        this.randomizeStateVariables();
        this.health = new HealthCalculator(this.healthPoints, "AggressiveDemon");
        this.flameThrower = new FlameThrower(this.fireImage, this.attackRadius);
        this.entityState = EntityState.ATTACK;
    }

    @Override
    public String toString() {
        return "Navec";
    }
}
