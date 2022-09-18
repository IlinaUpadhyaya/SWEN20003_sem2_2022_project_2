import bagel.Image;

public class Villain extends AggressiveDemon {
    private static final Image NAVEC_LEFT = new Image("res/navec/navecLeft.png");
    private static final Image NAVEC_RIGHT = new Image("res/navec/navecRight.png");
    protected final double NAVEC_ATTACK_DAMAGE = 20;
    protected final double NAVEC_ATTACK_RANGE = 200;

    public Villain(double xCoord, double yCoord) {
        super(xCoord, yCoord);
        this.setImage(NAVEC_LEFT);
    }

    @Override
    public String toString() {
        return "Navec";
    }
}
