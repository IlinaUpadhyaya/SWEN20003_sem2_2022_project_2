import bagel.*;
import bagel.Font;
import bagel.util.Point;

public class HealthBar {
    private final Point INSTRUCT_MSG_LOC = new Point(20, 25);
    private final Font INSTRUCT_MSG = new Font("res/frostbite.ttf", 30);

    public HealthBar(MovableGameEntity entity) {
        entity.entityHP = entity.entityMaxHP;
        entity.remainingHealthPercentage = 100.0;
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
        System.out.printf("%s inflicts %d damage points on %s.  %s's current health: %d/%d\n", attackEntity,
                (int)damage, damagedEntity, attackEntity, (int)Math.round(damagedEntity.entityHP),
                (int)damagedEntity.entityMaxHP);
    }

    public boolean healthOver(MovableGameEntity entity) {
        return entity.entityHP <= 0;
    }

    public void draw(MovableGameEntity entity) {
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
