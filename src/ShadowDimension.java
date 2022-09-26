import bagel.*;

import java.util.ArrayList;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2022
 * Please enter your name below
 * iupadhyaya
 */

public class ShadowDimension extends AbstractGame {
    // CONSTANTS
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final String WORLD_FILE1 = "res/level0.csv";
    private final String WORLD_FILE2 = "res/level1.csv";
    private final int LEVEL1_WIN_DISPLAY_TIME = 3000;
    private final ArrayList<Keys> POSSIBLE_KEYS = new ArrayList<Keys>();

    // private VARIABLES
    private GameState gameState;
    private Timer timer;
    private GameScene activeLevel, levelOne, levelTwo;

    public ShadowDimension() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        initKeys();
        levelOne = new GameSceneOne(WORLD_FILE1);
        levelTwo = new GameSceneTwo(WORLD_FILE2);
        this.gameState = GameState.LEVEL_1_ANNOUNCED;
        this.activeLevel = levelOne;
    }

    private void initKeys() {
        POSSIBLE_KEYS.add(Keys.ESCAPE);
        POSSIBLE_KEYS.add(Keys.SPACE);
        POSSIBLE_KEYS.add(Keys.A);
        POSSIBLE_KEYS.add(Keys.W);
        POSSIBLE_KEYS.add(Keys.L);
        POSSIBLE_KEYS.add(Keys.K);
        POSSIBLE_KEYS.add(Keys.LEFT);
        POSSIBLE_KEYS.add(Keys.RIGHT);
        POSSIBLE_KEYS.add(Keys.UP);
        POSSIBLE_KEYS.add(Keys.DOWN);
    }

    private Keys getKeyPress(Input input) {
        Keys keyPressed = null;
        for (Keys key : this.POSSIBLE_KEYS)
            if (input.wasPressed(key)) {
                keyPressed = key;
                break;
            }
        return keyPressed;
    }

    private void handleSpaceBarInput() {
        switch (this.gameState) {
            case LEVEL_1_ANNOUNCED:
                this.gameState = GameState.LEVEL_1_STARTED;
                break;
            case LEVEL_2_ANNOUNCED:
                this.gameState = GameState.LEVEL_2_STARTED;
                activeLevel = levelTwo;
                break;
            default:
                break;
        }
    }

    private void handleUserInput(Input input) {
        Keys keyPressed = this.getKeyPress(input);
        if (keyPressed == null) {
            if (input.isDown(Keys.LEFT))
                activeLevel.onKeyInput(Keys.LEFT);
            if (input.isDown(Keys.RIGHT))
                activeLevel.onKeyInput(Keys.RIGHT);
            if (input.isDown(Keys.UP))
                activeLevel.onKeyInput(Keys.UP);
            if (input.isDown(Keys.DOWN))
                activeLevel.onKeyInput(Keys.DOWN);
            if (input.isDown(Keys.A))
                activeLevel.onKeyInput(Keys.A);
            return;
        }

        switch (keyPressed) {
            case ESCAPE:
                Window.close();
                break;
            case SPACE:
                this.handleSpaceBarInput();
                break;
            case W:
                this.gameState = GameState.LEVEL_2_ANNOUNCED;
                break;
            case A:
            case L:
            case K:
            case LEFT:
            case RIGHT:
            case UP:
            case DOWN:
                activeLevel.onKeyInput(keyPressed);
                break;
            default:
                break;
        }
    }

    private void checkForWinLose() {
        switch (this.gameState) {
            case LEVEL_1_STARTED:
                if (levelOne.win()) {
                    this.gameState = GameState.LEVEL_1_WIN;
                    this.timer = new Timer(LEVEL1_WIN_DISPLAY_TIME);
                }
                if (levelOne.lose()) this.gameState = GameState.GAME_LOSE;
                break;
            case LEVEL_2_STARTED:
                if (levelTwo.win()) this.gameState =
                        GameState.LEVEL_2_WIN;
                if (levelTwo.lose()) this.gameState = GameState.GAME_LOSE;
                break;
            default:
                break;
        }
    }

    /**
     * draws, updates game state and exits game when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        this.handleUserInput(input);
        this.checkForWinLose();
        // now draw
        switch (this.gameState) {
            case LEVEL_1_ANNOUNCED:
                levelOne.drawStartScreen();
                break;

            case LEVEL_1_STARTED:
                levelOne.drawGameScreen();
                break;

            case LEVEL_1_WIN:
                if (this.timer.isTimeUp()) this.gameState =
                        GameState.LEVEL_2_ANNOUNCED;
                else
                    levelOne.drawWinScreen();
                break;

            case LEVEL_2_ANNOUNCED:
                levelTwo.drawStartScreen();
                break;

            case LEVEL_2_STARTED:
                levelTwo.drawGameScreen();
                break;

            case GAME_LOSE:
                levelOne.drawLoseScreen();
                break;

            default:
                break;
        }
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }
}
