import bagel.Image;
import bagel.util.Point;

public class Demon extends MovableGameEntity {
    private static final Image DEMON_LEFT = new Image("res/demon/demonLeft.png");
    protected final double DEMON_MAX_HP = 40;
    protected final double DEMON_ATTACK_DAMAGE = 10;
    protected final double DEMON_ATTACK_RANGE = 150;

    private Boolean isPassive;
    protected double speed;

    public Demon(double xCoord, double yCoord) {
        super(new Point(xCoord, yCoord), DEMON_LEFT);
        this.entityMaxHP = DEMON_MAX_HP;
        this.entityHP = this.entityMaxHP;
        this.remainingHealthPercentage = 100.0;
    }

    @Override
    public String toString() {
        return "Demon";
    }

    protected boolean inRange(Point playerPos) {
        return false;
    }

    protected void shootFire(Point playerPos) {

    }
}
