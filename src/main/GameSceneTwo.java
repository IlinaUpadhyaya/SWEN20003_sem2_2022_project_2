package main;

import bagel.Image;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import entities.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

class GameSceneTwo extends GameScene {
    protected static final Image TREE = new Image("res/tree.png");
    private final static String INSTRUCTION_MESSAGE_LINE2 = "PRESS A To ATTACK";
    private final static String INSTRUCTION_MESSAGE_LINE3 = "DEFEAT NAVEC TO WIN";
    private final static Image BACKGROUND_IMAGE2 = new Image("res/background1.png");
    private final static int NO_OF_DEMON_TYPES = 2;
    private final static int AGGRESSIVE = 0;
    private final static String WIN_MESSAGE2 = "CONGRATULATIONS!";
    private int timeScale = 0;

    public GameSceneTwo(String fileName) {
        super(fileName);
    }

    @Override
    public void onKeyInput(Keys key) {
        switch (key) {
            // apply speed change to all movable enemies
            case K:
            case L:
                adjustTimeScale(key);
                for (StationaryGameEntity gameEntity : this.gameEntities) {
                    if (gameEntity instanceof Player) continue;
                    if (gameEntity instanceof AggressiveDemon) {
                        ((AggressiveDemon) gameEntity).updateSpeed(this.timeScale);
                    }
                }
                break;
            // all other key inputs passed to the player
            default:
                fae.handleKeyInput(key);
                break;
        }
    }

    @Override
    protected void drawStartScreen() {
        TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE_LINE1, TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE_LINE2, TITLE_X + INS_X_OFFSET,
                TITLE_Y + INS_Y_OFFSET + LINE_SEPARATION);
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE_LINE3, TITLE_X + INS_X_OFFSET,
                TITLE_Y + INS_Y_OFFSET + LINE_SEPARATION + LINE_SEPARATION);
    }

    @Override
    protected void drawGameScreen() {
        for (DemonBehaviour enemy : enemies)
            enemy.onFrameUpdate(fae.getBoundingBox());
        fae.onFrameUpdate();
        BACKGROUND_IMAGE2.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        for (StationaryGameEntity drawable : gameEntities)
            drawable.draw();
    }

    @Override
    protected boolean win() {
        return this.mainEnemy.healthOver();
    }

    @Override
    protected void drawWinScreen() {drawMessage(WIN_MESSAGE2);}

    @Override
    protected boolean lose() {
        return fae.healthOver();
    }

    protected void populateSceneEntities(String fileName) {
        Point topLeftBound = null;
        Point bottomRightBound = null;

        // read CSV
        Random coinTosser = new Random();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] cells = text.split(",");
                String entityType = cells[0];
                double xCoord = Double.parseDouble(cells[1]);
                double yCoord = Double.parseDouble(cells[2]);

                // create game entities
                switch (entityType) {
                    case "Fae":
                        fae = new Player(xCoord, yCoord);
                        gameEntities.add(fae);
                        break;
                    case "Wall":
                        StationaryGameEntity wall = new Wall(new Point(xCoord, yCoord));
                        gameEntities.add(wall);
                        break;
                    case "Sinkhole":
                        StationaryGameEntity sinkhole = new Sinkhole(new Point(xCoord, yCoord));
                        gameEntities.add(sinkhole);
                        break;
                    case "Tree":
                        StationaryGameEntity tree = new Tree(new Point(xCoord, yCoord));
                        gameEntities.add(tree);
                        break;
                    case "Demon":
                        // randomize type based on coin toss
                        StationaryGameEntity demon;
                        if (coinTosser.nextInt(NO_OF_DEMON_TYPES) == AGGRESSIVE)
                            demon = new AggressiveDemon(xCoord, yCoord);
                        else
                            demon = new PassiveDemon(xCoord, yCoord);
                        gameEntities.add(demon);
                        enemies.add((DemonBehaviour) demon);
                        break;
                    case "Navec":
                        StationaryGameEntity navec = new SpecialAggressiveDemon(xCoord, yCoord);
                        gameEntities.add(navec);
                        this.mainEnemy = (DemonBehaviour) navec;
                        enemies.add(this.mainEnemy);
                        break;
                    case "TopLeft":
                        topLeftBound = new Point(xCoord, yCoord);
                        break;
                    case "BottomRight":
                        bottomRightBound = new Point(xCoord, yCoord);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error during csv read method. Exiting...");
            System.exit(0);
        }
        fae.setGameEntities(gameEntities);
        fae.setBounds(topLeftBound, bottomRightBound);
        for (DemonBehaviour demon : enemies) {
            if (demon instanceof MovableGameEntity) {
                ((MovableGameEntity) demon).setGameEntities(gameEntities);
                ((MovableGameEntity) demon).setBounds(topLeftBound,
                        bottomRightBound);
            }
        }
    }

    private void adjustTimeScale(Keys key) {
        switch (key) {
            case K:
                this.timeScale -= 1;
                if (this.timeScale < -3) {
                    this.timeScale = -3;
                }
                System.out.printf("Slowed down, Speed:  %d\n", this.timeScale);
                break;
            case L:
                this.timeScale += 1;
                if (this.timeScale > 3) {
                    this.timeScale = 3;
                }
                System.out.printf("Sped up, Speed:  %d\n", this.timeScale);
                break;
            default:
                break;
        }
    }
}
