import bagel.Font;
import bagel.Image;
import bagel.util.Point;

public class Demon extends AttackableGameEntity {
    protected static final Image DEMON_LEFT = new Image("res/demon/demonLeft.png");
    protected final double DEMON_MAX_HP = 40;
    protected final double DEMON_ATTACK_DAMAGE = 10;
    protected final double DEMON_ATTACK_RANGE = 150;
    protected Fire demonFire = new Fire(this.getBoundingBox().topLeft().x, this.getBoundingBox().topLeft().y);

    public Demon(double xCoord, double yCoord) {
        super(new Point(xCoord, yCoord), DEMON_LEFT);
        this.entityMaxHP = DEMON_MAX_HP;
        this.entityHP = this.entityMaxHP;
        this.remainingHealthPercentage = 100.0;
        this.HEALTH_BAR_LOC = new Point(this.getPosition().x, this.getPosition().y - 6);
        this.HEALTH_BAR_FONT = new Font("res/frostbite.ttf", 15);
    }

    @Override
    public String toString() {
        return "Demon";
    }
}
