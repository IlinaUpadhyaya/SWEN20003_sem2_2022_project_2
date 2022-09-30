import bagel.Font;
import bagel.util.Rectangle;

public interface DemonBehaviour {
    int INVINCIBLE_TIMEOUT = 3000;
    Font HEALTH_BAR_FONT = new Font("res/frostbite.ttf", 15);
    int HEALTH_BAR_LOC_Y_OFFSET = 6;

    Rectangle getFireBoundingBox();

    void onFrameUpdate(Rectangle playerBox);

    boolean onAttacked(double damage, String damagingClass);

    boolean healthOver();
}
