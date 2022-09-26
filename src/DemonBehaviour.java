import bagel.Font;
import bagel.util.Rectangle;

public interface DemonBehaviour {

    public static final int INVINCIBLE_TIMEOUT = 3000;
    public static final Font HEALTH_BAR_FONT = new Font("res/frostbite.ttf", 15);
    public static final int HEALTH_BAR_LOC_Y_OFFSET = 6;


    public Rectangle getFireBoundingBox();

    public void onFrameUpdate(Rectangle playerBox);

    public boolean onAttacked(double damage, String damagingClass);

    boolean healthOver();
}
