import bagel.*;
import bagel.util.Point;
import java.util.ArrayList;

public abstract class Level {
    protected final Font TITLE_MSG = new Font("res/frostbite.ttf", 75);
    protected final Font INSTRUCT_MSG = new Font("res/frostbite.ttf", 40);
    protected final Point TITLE_MSG_LOC = new Point(260, 250);
    protected final int MSG_X_SHIFT = 90;
    protected final int MSG_Y_SHIFT = 190;
    protected final int LINE_SEPARATION = 60;

    protected Image backgroundImage;
    protected ArrayList<GameEntity> gameEntities = new ArrayList<>();
    protected Point topLeftBound;
    protected Point bottomRightBound;
    protected Player fae;

    public Level(Image bgImg) {
        this.backgroundImage = bgImg;
    }

    public void drawLoseScreen() {
        TITLE_MSG.drawString("GAME OVER!", MSG_X_SHIFT + TITLE_MSG_LOC.x,
                (MSG_Y_SHIFT - MSG_X_SHIFT) + TITLE_MSG_LOC.y);
    }
    public void drawLevel() {
        backgroundImage.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        fae.entityHealthBar.drawHealthBar(fae);
        for (GameEntity drawable : gameEntities) {
            drawable.draw();
        }
    }

    abstract void populateSceneEntities();
    abstract void drawWinScreen();
    abstract void drawStartScreen();
}
