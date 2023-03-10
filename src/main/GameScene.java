package main;

import bagel.Font;
import bagel.Keys;
import bagel.Window;
import entities.DemonBehaviour;
import entities.Player;
import entities.StationaryGameEntity;

import java.util.ArrayList;

abstract class GameScene {
    protected final static String GAME_TITLE = "SHADOW DIMENSION";
    protected final static int TITLE_FONT_SIZE = 75;
    protected final static Font TITLE_FONT = new Font("res/frostbite.ttf", TITLE_FONT_SIZE);
    protected final static String INSTRUCTION_MESSAGE_LINE1 = "PRESS SPACE TO START";
    protected final static int TITLE_X = 260;
    protected final static int TITLE_Y = 250;
    protected final static int INS_X_OFFSET = 90;
    protected final static int INS_Y_OFFSET = 190;
    protected final static int LINE_SEPARATION = 60;
    protected final static String TOP_LEFT = "TopLeft";
    protected final static String BOTTOM_RIGHT = "BottomRight";
    protected final static String ERROR_MSG = "Error during csv read method. Exiting...";
    private final static int INSTRUCTION_FONT_SIZE = 40;
    protected final static Font INSTRUCTION_FONT = new Font("res/frostbite.ttf", INSTRUCTION_FONT_SIZE);
    private final static String END_MESSAGE = "GAME OVER!";
    protected Player fae;
    protected ArrayList<StationaryGameEntity> gameEntities = new ArrayList<StationaryGameEntity>();
    protected ArrayList<DemonBehaviour> enemies = new ArrayList<>();

    GameScene(String fileName) {
        populateSceneEntities(fileName);
    }

    /*returns true if lost*/
    boolean lose() {
        return fae.healthOver();
    }

    void drawLoseScreen() {
        drawMessage(END_MESSAGE);
    }

    protected abstract void drawWinScreen();

    protected abstract void onKeyInput(Keys key);

    protected abstract void drawStartScreen();

    protected abstract void updateEntitiesAndDrawGameScreen();

    /*returns true if win*/
    protected abstract boolean win();

    /*reads csv file and populates entities*/
    protected abstract void populateSceneEntities(String fileName);

    protected void drawMessage(String message) {
        TITLE_FONT.drawString(message, (Window.getWidth() / 2.0 - (TITLE_FONT.getWidth(message) / 2.0)),
                (Window.getHeight() / 2.0 + (TITLE_FONT_SIZE / 2.0)));
    }
}
