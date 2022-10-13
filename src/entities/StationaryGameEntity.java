package entities;

import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Colour;
import bagel.util.Point;
import bagel.util.Rectangle;

public class StationaryGameEntity {
    protected final static DrawOptions COLOUR = new DrawOptions();
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);
    private final static int ORANGE_BOUNDARY = 65;
    private final static int RED_BOUNDARY = 35;
    private final String name;
    protected HealthCalculator health;
    protected Point topLeftPosition;
    private Image image;
    private Rectangle boundingBox;
    private double damage;

    StationaryGameEntity(Point position, Image image, String name, double damage) {
        this(position, image, name);
        this.damage = damage;
    }

    StationaryGameEntity(Point position, Image image, String name) {
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
     * called to determine if entity should be deleted if health over.     *
     *
     * @return healthOver
     */
    public boolean healthOver() {
        return this.health.healthOver();
    }

    /**
     * draws the entity image and prepares the health bar variables only as health bar location and
     * size is different for the entities
     */
    public void draw() {
        this.image.drawFromTopLeft(topLeftPosition.x, topLeftPosition.y);
        if (this.health != null) {
            prepHealthBarForDrawing();
        }
    }

    /**
     * Images change in some stationary and some moving entities and the sizes may be different, so
     * we recalculate the bounding box on each change
     */
    protected void setImageAndCalculate(Image image) {
        this.image = image;
        this.recalculateBoundingBox();
    }

    /**
     * Also called by movable game entity on position change
     */
    protected void recalculateBoundingBox() {
        this.boundingBox = new Rectangle(this.topLeftPosition, this.image.getWidth(), this.image.getHeight());
    }

    protected String getName() {
        return name;
    }

    protected double getDamage() {
        return this.damage;
    }

    protected Image getImage() {
        return this.image;
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
