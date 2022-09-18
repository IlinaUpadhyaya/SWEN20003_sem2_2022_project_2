import bagel.Image;
import bagel.util.Point;

public class Fire {
    private static final Image DEMON_FIRE = new Image("res/demon/demonFire.png");
    private Point firePosition;
    private Image image;

    public Fire(double x, double y) {
        this.firePosition = new Point(x, y);
        this.image = DEMON_FIRE;
    }

    protected boolean inRange(Point playerPos) {
        return false;
    }

    protected void shootFire(Point playerPos) {

    }
}
