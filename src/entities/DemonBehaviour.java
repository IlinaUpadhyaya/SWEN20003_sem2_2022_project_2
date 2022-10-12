package entities;

import bagel.Font;
import bagel.util.Rectangle;

public interface DemonBehaviour {
    int INVINCIBLE_TIMEOUT = 3000;
    Font HEALTH_BAR_FONT = new Font("res/frostbite.ttf", 15);
    int HEALTH_BAR_LOC_Y_OFFSET = 6;

    /**
     * Must be called by player to check if player is intersecting fire bounding box returned.
     *
     * @return
     */
    Rectangle getFireBoundingBox();

    /**
     * Must be called every frame with player bounding box. Must check if player is within fire
     * range and if so set fire from relevant corner
     *
     * @param playerBox
     */

    void onFrameUpdate(Rectangle playerBox);

    /**
     * called to register an attack by damagingClass. returns True if health over and object should
     * disappear
     *
     * @param damage        : varies per attacking class
     * @param damagingClass
     * @return
     */
    boolean onAttacked(double damage, String damagingClass);

}
