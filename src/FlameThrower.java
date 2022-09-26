import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class FlameThrower {
    private double attackRadius;
    private Image fire;
    private Rectangle boundingBox;

    // top left coordinates of fire after rotation
    private double x, y, rotation;
    private boolean firing = false;

    public FlameThrower(Image fireImage, double attackRadius) {
        this.fire = fireImage;
        this.attackRadius = attackRadius;
        this.boundingBox = this.fire.getBoundingBox();
    }

    public void draw() {
        DrawOptions drawOptions = new DrawOptions();
        drawOptions.setRotation(this.rotation);
        if (firing)
            fire.drawFromTopLeft(x, y, drawOptions);
    }

    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    private FireLocation fireLocation;

    private double distanceBetweenPoints(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    private FireLocation inRange(Rectangle enemyBox, Rectangle playerBox) {
        Point enemyCenter = enemyBox.centre();
        // does radius intersect left right lines
        boolean outsideTopAndBottom = false;
        boolean outsideLeftAndRight = false;
        if (enemyCenter.x + this.attackRadius < playerBox.left())
            outsideLeftAndRight = true;
        if (enemyCenter.x - this.attackRadius > playerBox.right())
            outsideLeftAndRight = true;
        if (enemyCenter.y + this.attackRadius < playerBox.top())
            outsideTopAndBottom = true;
        if (enemyCenter.y - this.attackRadius > playerBox.bottom())
            outsideTopAndBottom = true;
        if (outsideLeftAndRight || outsideTopAndBottom) return null;

        double minDistance = this.attackRadius;
        FireLocation loc = null;
        double dist = this.attackRadius;
        dist = distanceBetweenPoints(enemyCenter,
                new Point(playerBox.left(), playerBox.top()));
        if (minDistance > dist) {
            loc = FireLocation.BOTTOMRIGHT;
            minDistance = dist;
        }

        dist = distanceBetweenPoints(enemyCenter,
                new Point(playerBox.right(), playerBox.top()));
        if (minDistance > dist) {
            loc = FireLocation.BOTTOMLEFT;
            minDistance = dist;
        }

        dist = distanceBetweenPoints(enemyCenter,
                new Point(playerBox.left(), playerBox.bottom()));
        if (minDistance > dist) {
            loc = FireLocation.TOPRIGHT;
            minDistance = dist;
        }

        dist = distanceBetweenPoints(enemyCenter,
                new Point(playerBox.right(), playerBox.bottom()));
        if (minDistance > dist) {
            loc = FireLocation.TOPLEFT;
            minDistance = dist;
        }

        if (minDistance < this.attackRadius) return loc;

        return null;
    }

    // these are image center locations
    void checkForFire(Rectangle enemyBox, Rectangle playerBox) {
        this.fireLocation = inRange(enemyBox, playerBox);
        if (this.fireLocation == null) {
            firing = false;
            return;
        } else firing = true;
        switch (this.fireLocation) {
            case TOPLEFT:
                x = enemyBox.left() - this.fire.getWidth();
                y = enemyBox.top() - this.fire.getHeight();
                this.boundingBox = new Rectangle(x, y, this.fire.getWidth(), this.fire.getHeight());
                this.rotation = 0;
                break;

            case TOPRIGHT:
                x = enemyBox.right();
                y = enemyBox.top() - this.fire.getHeight();
                this.boundingBox = new Rectangle(x, y, this.fire.getWidth(), this.fire.getHeight());
                this.rotation = Math.PI / 2;
                break;

            case BOTTOMLEFT:
                x = enemyBox.left() - this.fire.getWidth();
                y = enemyBox.bottom();
                this.boundingBox = new Rectangle(x, y, this.fire.getWidth(), this.fire.getHeight());
                this.rotation = -Math.PI / 2.0;
                break;

            case BOTTOMRIGHT:
                x = enemyBox.right();
                y = enemyBox.bottom();
                this.boundingBox = new Rectangle(x, y, this.fire.getWidth(), this.fire.getHeight());
                this.rotation = -Math.PI;
                break;
        }
    }

}
