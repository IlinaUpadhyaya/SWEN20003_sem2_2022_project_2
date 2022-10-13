package entities;

import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

class FlameThrower {
    private final double attackRadius;
    private final Image fire;
    private Rectangle boundingBox;

    // top left co-ordinates of fire and rotation angle in radians
    private double x, y, rotation;
    private boolean firing = false;

    FlameThrower(Image fireImage, double attackRadius) {
        this.fire = fireImage;
        this.attackRadius = attackRadius;
        this.boundingBox = this.fire.getBoundingBox();
    }

    void draw() {
        DrawOptions drawOptions = new DrawOptions();
        drawOptions.setRotation(this.rotation);
        if (firing)
            fire.drawFromTopLeft(x, y, drawOptions);
    }

    Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    void checkForFire(Rectangle enemyBox, Rectangle playerBox) {
        FireLocation fireLocation = inRange(enemyBox, playerBox);
        if (fireLocation == null) {
            firing = false;
            return;
        } else firing = true;

        switch (fireLocation) {
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

    private double distanceBetweenPoints(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    private FireLocation inRange(Rectangle enemyBox, Rectangle playerBox) {
        Point enemyCenter = enemyBox.centre();
        Point playerCenter = playerBox.centre();
        if (distanceBetweenPoints(enemyCenter, playerCenter) > this.attackRadius) return null;
        if (playerCenter.x <= enemyCenter.x && playerCenter.y <= enemyCenter.y)
            return FireLocation.TOPLEFT;
        if (playerCenter.x <= enemyCenter.x)
            return FireLocation.BOTTOMLEFT;
        if (playerCenter.y <= enemyCenter.y)
            return FireLocation.TOPRIGHT;
        return FireLocation.BOTTOMRIGHT;
    }

}
