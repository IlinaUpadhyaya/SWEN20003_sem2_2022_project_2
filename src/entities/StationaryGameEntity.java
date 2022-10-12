package entities;

import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

public class StationaryGameEntity {
    protected final static DrawOptions COLOUR = new DrawOptions();
    protected final static Colour GREEN = new Colour(0, 0.8, 0.2);
    protected final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    protected final static Colour RED = new Colour(1, 0, 0);
    protected final static int ORANGE_BOUNDARY = 65;
    protected final static int RED_BOUNDARY = 35;
    protected HealthCalculator health;
    private Point topLeftPosition;
    private Image image;
    private Rectangle boundingBox;
    private final String name;
    private double damage;

    public StationaryGameEntity(Point position, Image image, String name, double damage) {
        this(position, image, name);
        this.damage = damage;
    }

    public StationaryGameEntity(Point position, Image image, String name) {
        this.topLeftPosition = position;
        this.name = name;
        this.setImageAndCalculate(image);
    }

    public Point getTopLeftPosition() {
        return topLeftPosition;
    }

    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    /**
     * called to determine if win/lose/dissapear
     *
     * @return
     */
    public boolean healthOver() {
        return this.health.healthOver();
    }

    /**
     * draws the entity image and prepares the health bar variables only as health bar draw is
     * different for the entities
     */
    public void draw() {
        this.image.drawFromTopLeft(topLeftPosition.x, topLeftPosition.y);
        if (this.health != null) {
            prepHealthBarForDrawing();
        }
    }

    /**
     * images change in dynamic entities and the sizes may be different, so we recalculate the
     * bounding box in each change
     */
    void setImageAndCalculate(Image image) {
        this.image = image;
        this.recalculateBoundingBox();
    }

    String getName() {
        return name;
    }

    /**
     * sets the new topLeft Position passed in and recalculates the
     * bounding box
     */
    void setNewPosition(Point newPos) {
        this.topLeftPosition = newPos;
        this.recalculateBoundingBox();
    }

    double getDamage() {
        return this.damage;
    }

    protected Image getImage() {
        return this.image;
    }

    protected Rectangle getBoundingBoxAt(Point newTopLeft) {
        return new Rectangle(newTopLeft, this.image.getWidth(),
                this.image.getHeight());
    }

    private void recalculateBoundingBox() {
        this.boundingBox = new Rectangle(this.topLeftPosition, this.image.getWidth(),
                this.image.getHeight());
    }

    private void prepHealthBarForDrawing() {
        if (this.health.getRemainingHealthPercentage() >= ORANGE_BOUNDARY) {
            COLOUR.setBlendColour(GREEN);
        } else if (this.health.getRemainingHealthPercentage() >= RED_BOUNDARY) {
            COLOUR.setBlendColour(ORANGE);
        } else {
            COLOUR.setBlendColour(RED);
        }
    }

}
