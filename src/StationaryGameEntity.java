import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

class StationaryGameEntity {
    private Point topLeftPosition;
    private Point centerPosition;
    private Image image;
    private Rectangle boundingBox;
    private String name;
    private double damage;

    public StationaryGameEntity(Point position, Image image, String name) {
        this.topLeftPosition = position;
        this.name = name;
        this.setImage(image);
    }

    public StationaryGameEntity(Point position, Image image, String name,
                                double damage) {
        this(position, image, name);
        this.damage = damage;
    }

    public StationaryGameEntity(Point position) {
        this.topLeftPosition = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    /** topleft position is used for drawing and used to move player
        * in movable game entity.
         * centerPosition used only for boundingBox generation */
    protected Point getTopLeftPosition() {
        return topLeftPosition;
    }

    protected Point getCenterPosition() {
        return centerPosition;
    }

    protected Image getImage() {
        return image;
    }

    void setImage(Image image) {
        this.centerPosition = new Point(topLeftPosition.x + image.getWidth() / 2,
                topLeftPosition.y + image.getHeight() / 2);
        this.image = image;
        this.boundingBox = this.image.getBoundingBoxAt(this.centerPosition);
    }

     void recalculateParameters(Point newPos) {
        this.topLeftPosition = newPos;
        this.centerPosition = new Point(newPos.x + image.getWidth() / 2,
                newPos.y + image.getHeight() / 2);
        this.boundingBox = this.image.getBoundingBoxAt(this.centerPosition);
    }

    /* returns bounding Box centered at image center */
    protected Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    public double getDamage() {
        return this.damage;
    }

    public void draw() {
        image.drawFromTopLeft(topLeftPosition.x, topLeftPosition.y);
    }

}
