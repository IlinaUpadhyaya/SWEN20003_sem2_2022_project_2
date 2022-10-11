package main;

import bagel.Font;
import bagel.Image;
import bagel.Keys;
import bagel.Window;
import entities.DemonBehaviour;
import entities.Player;
import entities.StationaryGameEntity;

import java.util.ArrayList;

abstract class GameScene {
    protected final static String GAME_TITLE = "SHADOW DIMENSION";
    protected final static int TITLE_FONT_SIZE = 75;
    protected final static int INSTRUCTION_FONT_SIZE = 40;
    protected final static Font TITLE_FONT = new Font("res/frostbite.ttf", TITLE_FONT_SIZE);
    protected final static Font INSTRUCTION_FONT = new Font("res/frostbite.ttf", INSTRUCTION_FONT_SIZE);
    protected final static String INSTRUCTION_MESSAGE_LINE1 = "PRESS SPACE TO START";
    protected final static int TITLE_X = 260;
    protected final static int TITLE_Y = 250;
    protected final static int INS_X_OFFSET = 90;
    protected final static int INS_Y_OFFSET = 190;
    protected final static int LINE_SEPARATION = 60;
    private final static String END_MESSAGE = "GAME OVER!";
    protected Player fae;
    protected ArrayList<StationaryGameEntity> gameEntities = new ArrayList<StationaryGameEntity>();
    protected ArrayList<DemonBehaviour> enemies = new ArrayList<DemonBehaviour>();
    protected DemonBehaviour mainEnemy;

    public GameScene(String fileName) {
        populateSceneEntities(fileName);
    }

    public void onKeyInput(Keys key) {
        fae.handleKeyInput(key);
    }

    protected abstract void drawStartScreen();

    protected abstract void drawGameScreen();

    protected abstract boolean win();

    protected abstract boolean lose();

    protected abstract void populateSceneEntities(String fileName);

    protected void drawLoseScreen() {
        drawMessage(END_MESSAGE);
    }

    protected abstract void drawWinScreen();

    protected void drawMessage(String message) {
        TITLE_FONT.drawString(message, (Window.getWidth() / 2.0 - (TITLE_FONT.getWidth(message) / 2.0)),
                (Window.getHeight() / 2.0 + (TITLE_FONT_SIZE / 2.0)));
    }
}
