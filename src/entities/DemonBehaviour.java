package entities;

import bagel.Font;
import bagel.util.Rectangle;

/**
 * This interface is used to share demon constants
 */
public interface DemonBehaviour {
    int INVINCIBLE_TIMEOUT = 3000;
    Font HEALTH_BAR_FONT = new Font("res/frostbite.ttf", 15);
    int HEALTH_BAR_LOC_Y_OFFSET = 6;

    /**
     * Called to check if player is intersecting fire bounding box returned.
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
     * Called to register an attack by damagingClass on demon implementing this interface. Returns
     * True if health over (used to delete implementing class object from gameEntities by calling
     * class method).
     *
     * @param damage        : varies per attacking class
     * @param damagingClass
     * @return
     */
    boolean onAttacked(double damage, String damagingClass);

}
