import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

public class ShadowDimensionTest extends AbstractGame {
    // Constants
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final Point WIN_POINT = new Point(950, 670);
    private final double PLAYER_STEP_SIZE = 2;

    // Variables
    private Level0 level0 = new Level0();
    private Level1 level1 = new Level1();
    private Boolean gameStarted = false;
    private Boolean gameEnded = false;
    private Boolean isLevel0 = true;

    public ShadowDimensionTest() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimensionTest game = new ShadowDimensionTest();
        game.level0.populateSceneEntities();
        game.run();
    }

    /**
     * Method to check for sinkhole collision
     */
    private void sinkholeCollision(Level level) {
        Rectangle playerBoundingBox = level.fae.getBoundingBox();
        GameEntity drawableToDelete = null;
        Sinkhole sinkhole = null;

        for (GameEntity drawable : level.gameEntities) {
            if (drawable.getClass() == Sinkhole.class) {
                sinkhole = (Sinkhole) drawable;
                if (playerBoundingBox.intersects(sinkhole.getBoundingBox())) {
                    level.fae.entityHealthBar.onDamage(sinkhole, level.fae, sinkhole.SINKHOLE_DAMAGE);
                    drawableToDelete = drawable;
                    break;
                }
            }
        }
        if (drawableToDelete != null) {
            level.gameEntities.remove(drawableToDelete);
        }
    }

    /**
     * Method to check for wall collision
     */
    private boolean wallCollision(Point point, Level level) {
        Rectangle playerBoundingBox = level.fae.getProposedBoundingBox(point);
        boolean collides = false;
        Wall wall;

        for (GameEntity drawable : level.gameEntities) {
            if (drawable.getClass() == Wall.class) {
                wall = (Wall) drawable;
                if (playerBoundingBox.intersects(wall.getBoundingBox())) {
                    collides = true;
                    break;
                }
            }
        }
        return collides;
    }

    /**
     * Method to check if player within perimeter bounds
     */
    private boolean playerWithinBounds(Point playerPos, Level level) {
        boolean withinXBounds = playerPos.x >= level.topLeftBound.x && playerPos.x <= level.bottomRightBound.x;
        boolean withinYBounds = playerPos.y >= level.topLeftBound.y && playerPos.y <= level.bottomRightBound.y;
        return withinXBounds && withinYBounds;
    }

    /**
     * Method to detect win
     */
    private boolean win() {
        Point playerPos = level0.fae.getPosition();
        return playerPos.x >= WIN_POINT.x && playerPos.y >= WIN_POINT.y;
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        // Check for keyboard inputs
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
            return;
        }
        if (input.wasPressed(Keys.SPACE)) {
            gameStarted = true;
        }
        if (gameStarted && !gameEnded) {
            if (input.isDown(Keys.LEFT)) {
                Point xShiftedPoint = level0.fae.getXShiftedLocation(-PLAYER_STEP_SIZE);
                if (playerWithinBounds(xShiftedPoint, level0) && !wallCollision(xShiftedPoint, level0))
                    level0.fae.updatePlayerXPos(-PLAYER_STEP_SIZE);
            } else if (input.isDown(Keys.RIGHT)) {
                Point xShiftedPoint = level0.fae.getXShiftedLocation(PLAYER_STEP_SIZE);
                if (playerWithinBounds(xShiftedPoint, level0) && !wallCollision(xShiftedPoint, level0))
                    level0.fae.updatePlayerXPos(PLAYER_STEP_SIZE);
            } else if (input.isDown(Keys.UP)) {
                Point yShiftedPoint = level0.fae.getYShiftedLocation(-PLAYER_STEP_SIZE);
                if (playerWithinBounds(yShiftedPoint, level0) && !wallCollision(yShiftedPoint, level0))
                    level0.fae.updatePlayerYPos(-PLAYER_STEP_SIZE);
            } else if (input.isDown(Keys.DOWN)) {
                Point yShiftedPoint = level0.fae.getYShiftedLocation(PLAYER_STEP_SIZE);
                if (playerWithinBounds(yShiftedPoint, level0) && !wallCollision(yShiftedPoint, level0))
                    level0.fae.updatePlayerYPos(PLAYER_STEP_SIZE);
            }
            if (!isLevel0) {
                if (input.isDown(Keys.A)) {

                }
            }

            sinkholeCollision(level0);
        }

        // Draw screens
        if (!gameStarted) {
            level0.drawStartScreen();
        } else if (win()) {
            gameEnded = true;
            level0.drawWinScreen();
        } else if (level0.fae.entityHealthBar.healthOver(level0.fae)) {
            gameEnded = true;
            level0.drawLoseScreen();
        } else {
            level0.drawLevel();
        }
    }
}
