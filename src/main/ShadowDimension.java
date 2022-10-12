package main;

import bagel.AbstractGame;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import utils.Timer;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2022
 * Please enter your name below
 * iupadhyaya

 * The Main class for the application which implements the update method as
 * a state machine. A debugging key code W has been left as is required.
 */
public class ShadowDimension extends AbstractGame {
    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String WORLD_FILE1 = "res/level0.csv";
    private final static String WORLD_FILE2 = "res/level1.csv";
    private final static int LEVEL1_WIN_DISPLAY_TIME = 3000;
    private final static Keys[] MOVEMENT_KEYS = {Keys.LEFT, Keys.RIGHT, Keys.UP, Keys.DOWN};
    private final static Keys[] COMMAND_KEYS = {Keys.A, Keys.W, Keys.K, Keys.L, Keys.ESCAPE, Keys.SPACE};
    private final GameScene levelOne;
    private final GameScene levelTwo;
    private GameState gameState;
    private Timer timer;
    private GameScene activeLevel;

    public ShadowDimension() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        levelOne = new GameSceneOne(WORLD_FILE1);
        levelTwo = new GameSceneTwo(WORLD_FILE2);
        this.gameState = GameState.LEVEL_1_ANNOUNCED;
        this.activeLevel = levelOne;
    }

    /**
     * The entry point for the program. No args are required
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
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
                levelOne.updateEntitiesAndDrawGameScreen();
                break;

            case LEVEL_1_WIN:
                if (this.timer == null) break;
                this.timer.clockTick();
                if (this.timer.isTimeUp()) {
                    this.gameState =
                            GameState.LEVEL_2_ANNOUNCED;
                    levelTwo.drawStartScreen();
                } else
                    levelOne.drawWinScreen();
                break;

            case LEVEL_2_ANNOUNCED:
                levelTwo.drawStartScreen();
                break;

            case LEVEL_2_STARTED:
                levelTwo.updateEntitiesAndDrawGameScreen();
                break;

            case LEVEL_2_WIN:
                levelTwo.drawWinScreen();
                break;

            case GAME_LOSE:
                levelOne.drawLoseScreen();
                break;

            default:
                break;
        }
    }


    private Keys getInputtedKey(Input input) {
        for (Keys key : COMMAND_KEYS)
            if (input.wasPressed(key)) {
                return key;
            }
        for (Keys key : MOVEMENT_KEYS)
            if (input.isDown(key)) {
                return key;
            }
        return null;
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
                // no action to be taken otherwise
                break;
        }
    }

    private void handleUserInput(Input input) {
        Keys keyInputted = this.getInputtedKey(input);
        if (keyInputted == null) return;
        switch (keyInputted) {
            case ESCAPE:
                Window.close();
                break;
            case SPACE:
                this.handleSpaceBarInput();
                break;
            case W: // for debugging level2
                this.gameState = GameState.LEVEL_2_ANNOUNCED;
                break;
            default:
                activeLevel.onKeyInput(keyInputted);
        }
    }

    private void checkForWinLose() {
        switch (this.gameState) {
            case LEVEL_1_STARTED:
                if (levelOne.win()) {
                    this.gameState = GameState.LEVEL_1_WIN;
                    this.timer = new Timer(LEVEL1_WIN_DISPLAY_TIME);
                } else if (levelOne.lose()) this.gameState = GameState.GAME_LOSE;
                break;

            case LEVEL_2_STARTED:
                if (levelTwo.win()) this.gameState =
                        GameState.LEVEL_2_WIN;
                else if (levelTwo.lose()) this.gameState = GameState.GAME_LOSE;
                break;

            default:
                break;
        }
    }
}
