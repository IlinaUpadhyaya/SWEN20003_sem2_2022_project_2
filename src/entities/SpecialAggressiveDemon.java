package entities;

import bagel.Image;
import bagel.util.Point;

public class SpecialAggressiveDemon extends AggressiveDemon {
    private final static Image SPECIAL_AGGRESSIVE_DEMON_RIGHT = new Image("res/navec/navecRight.png");
    private final static Image SPECIAL_AGGRESSIVE_DEMON_RIGHT_INVINCIBLE =
            new Image("res/navec/navecInvincibleRight.png");
    private final static Image SPECIAL_AGGRESSIVE_DEMON_LEFT = new Image("res/navec/navecLeft.png");
    private final static Image SPECIAL_AGGRESSIVE_DEMON_LEFT_INVINCIBLE =
            new Image("res/navec/navecInvincibleLeft.png");
    private final static Image SPECIAL_AGGRESSIVE_DEMON_FIRE = new Image("res/navec/navecFire.png");
    private final static double FIRE_DAMAGE = 20;
    private final static double STARTING_HEALTH = 80;
    private final static double ATTACK_RADIUS = 200;
    private final static String NAME = "Navec";

    public SpecialAggressiveDemon(double xCoOrd, double yCoOrd) {
        super(new Point(xCoOrd, yCoOrd), SPECIAL_AGGRESSIVE_DEMON_RIGHT, NAME, SPECIAL_AGGRESSIVE_DEMON_LEFT,
                SPECIAL_AGGRESSIVE_DEMON_LEFT_INVINCIBLE, SPECIAL_AGGRESSIVE_DEMON_RIGHT_INVINCIBLE,
                SPECIAL_AGGRESSIVE_DEMON_FIRE,
                ATTACK_RADIUS, FIRE_DAMAGE, STARTING_HEALTH);
    }
}
