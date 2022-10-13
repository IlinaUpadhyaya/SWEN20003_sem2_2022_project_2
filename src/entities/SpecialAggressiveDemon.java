package entities;

import bagel.Image;
import bagel.util.Point;

/**
 * Class represents Navec Demon game object. Differs from Aggressive Demon in terms of parameters
 */
public class SpecialAggressiveDemon extends AggressiveDemon {
    /**
     * name must match that in the csv file for this entity. This name will print on messages
     * involving this entity
     */
    public final static String NAME = "Navec";
    private final static Image SPECIAL_AGGRESIVE_DEMON_RIGHT = new Image("res/navec/navecRight.png");
    private final static Image SPECIAL_AGGRESSIVE_DEMON_RIGHT_INVINCIBLE =
            new Image("res/navec/navecInvincibleRight.png");
    private final static Image SPECIAL_AGGRESSIVE_DEMON_LEFT = new Image("res/navec/navecLeft.png");
    private final static Image SPECIAL_AGGRESSIVE_DEMON_LEFT_INVINCIBLE =
            new Image("res/navec/navecInvincibleLeft.png");
    private final static Image SPECIAL_AGGRESSIVE_DEMON_FIRE = new Image("res/navec/navecFire.png");
    private final static double FIRE_DAMAGE = 20;
    private final static double STARTING_HEALTH = 80;
    private final static double ATTACK_RADIUS = 200;

    /**
     * Constructor
     *
     * @param position: the starting top left coordinate of the object in the scene
     */
    public SpecialAggressiveDemon(Point position) {
        super(position, SPECIAL_AGGRESIVE_DEMON_RIGHT, NAME, SPECIAL_AGGRESSIVE_DEMON_LEFT,
                SPECIAL_AGGRESSIVE_DEMON_LEFT_INVINCIBLE, SPECIAL_AGGRESSIVE_DEMON_RIGHT_INVINCIBLE,
                SPECIAL_AGGRESSIVE_DEMON_FIRE, ATTACK_RADIUS, FIRE_DAMAGE, STARTING_HEALTH);
    }
}
