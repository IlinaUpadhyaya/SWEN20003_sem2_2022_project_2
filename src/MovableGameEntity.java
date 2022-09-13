import bagel.DrawOptions;
import bagel.Font;
import bagel.Image;
import bagel.util.Point;

public abstract class MovableGameEntity extends GameEntity {
    private final Point INSTRUCT_MSG_LOC = new Point(20, 25);
    private final Font INSTRUCT_MSG = new Font("res/frostbite.ttf", 30);
    protected double entityHP;
    protected double remainingHealthPercentage;
    protected double entityMaxHP;

    public MovableGameEntity(Point position, Image image) {
        super(position, image);
    }

    public void onDamage(GameEntity attackEntity, MovableGameEntity damagedEntity, double damage) {
        damagedEntity.entityHP -= damage;
        damagedEntity.remainingHealthPercentage = (damagedEntity.entityHP / damagedEntity.entityMaxHP) * 100;

        // verify damaged entity's HP is not negative and set value to 0 if it is
        if (damagedEntity.entityHP < 0.0) {
            damagedEntity.entityHP = 0;
            damagedEntity.remainingHealthPercentage = (damagedEntity.entityHP / damagedEntity.entityMaxHP) * 100;
        }

        // print console log message
        System.out.printf("%s inflicts %d damage points on %s.  %s's current health: %d/%d\n", attackEntity.toString(),
                (int)damage, damagedEntity.toString(), damagedEntity.toString(),
                (int)Math.round(damagedEntity.entityHP), (int)damagedEntity.entityMaxHP);
    }

    public boolean healthOver(MovableGameEntity entity) {
        return entity.entityHP <= 0;
    }

    public void drawHealthBar(MovableGameEntity entity) {
        DrawOptions drawOptions = new DrawOptions();
        final double ORANGE_THRESHOLD = 65;
        final double RED_THRESHOLD = 35;

        if(entity.remainingHealthPercentage > ORANGE_THRESHOLD) {
            drawOptions.setBlendColour(0, 0.8, 0.2);
        }
        else if (entity.remainingHealthPercentage <= ORANGE_THRESHOLD && entity.remainingHealthPercentage >
                RED_THRESHOLD) {
            drawOptions.setBlendColour(0.9, 0.6, 0);
        }
        else if (entity.remainingHealthPercentage <= RED_THRESHOLD) {
            drawOptions.setBlendColour(1, 0, 0);
        }

        INSTRUCT_MSG.drawString((int)Math.round(entity.remainingHealthPercentage) + "%", INSTRUCT_MSG_LOC.x,
                INSTRUCT_MSG_LOC.y, drawOptions);
    }
}
