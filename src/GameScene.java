import bagel.Font;
import bagel.Image;
import bagel.Keys;
import bagel.util.Point;

import java.util.ArrayList;

public abstract class GameScene {
    protected static final Font TITLE_FONT = new Font("res/frostbite.ttf", 75);
    protected static final Font MSG_FONT = new Font("res/frostbite.ttf", 40);
    protected static final Point TITLE_MSG_LOC = new Point(260, 250);
    protected static final int MSG_XSHIFT = 90;
    protected static final int MSG_YSHIFT = 190;
    protected static final int LINE_SEPARATION = 60;
    protected static final double SINKHOLE_DAMAGE = 30;


    protected static final Image WALL = new Image("res/wall.png");
    protected static final Image SINKHOLE = new Image("res/sinkhole.png");


    protected Player fae;
    protected ArrayList<StationaryGameEntity> gameEntities = new ArrayList<StationaryGameEntity>();
    protected ArrayList<DemonBehaviour> enemies = new ArrayList<DemonBehaviour>();
    protected DemonBehaviour mainEnemy;


    public GameScene(String fileName) {
        populateSceneEntities(fileName);
    }

    protected abstract void drawStartScreen();

    protected abstract void drawGameScreen();

    protected abstract void drawLoseScreen();

    protected abstract void drawWinScreen();

    protected abstract boolean win();

    protected abstract boolean lose();

    protected abstract void populateSceneEntities(String fileName);

    public void onKeyInput(Keys key) {
        fae.handleKeyInput(key);
    }
}
