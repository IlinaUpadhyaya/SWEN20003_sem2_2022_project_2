import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

class GameEntity {
    private Point position;
    private Image image;
    private Rectangle boundingBox;

    public GameEntity(Point position, Image image) {
        this.position = position;
        this.image = image;
        this.boundingBox = this.image.getBoundingBoxAt(this.position);
    }

    public Point getPosition() {
        return position;
    }

    protected Image getImage() {
        return image;
    }

    protected void setImage(Image image) {
        this.image = image;
    }

    protected void setPosition(Point pos) {
        this.position = pos;
        this.boundingBox = this.image.getBoundingBoxAt(this.position);
    }

    protected Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    public void draw() {
        image.drawFromTopLeft(position.x, position.y);
    }
}
