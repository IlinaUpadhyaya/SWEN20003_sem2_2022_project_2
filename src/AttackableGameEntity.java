import bagel.Image;
import bagel.util.Point;
import bagel.Font;

public abstract class AttackableGameEntity extends GameEntity {
    public HealthBar entityHealthBar;
    protected double entityHP;
    protected double remainingHealthPercentage;
    protected double entityMaxHP;
    protected Point HEALTH_BAR_LOC;
    protected Font HEALTH_BAR_FONT;

    public AttackableGameEntity(Point position, Image image) {
        super(position, image);
        this.entityHealthBar = new HealthBar(this);

    }
}
