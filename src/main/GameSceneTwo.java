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
    private final static String INSTRUCTION_MESSAGE_LINE2 = "PRESS A To ATTACK";
    private final static String INSTRUCTION_MESSAGE_LINE3 = "DEFEAT NAVEC TO WIN";
    private final static Image BACKGROUND_IMAGE2 = new Image("res/background1.png");
    private final static String WIN_MESSAGE2 = "CONGRATULATIONS!";
    private final static int NO_OF_DEMON_TYPES = 2;
    private final static int INS_X = 350;
    private final static int INS_Y = 350;
    private final static int AGGRESSIVE = 0;
    private int timeScale = 0;
    private StationaryGameEntity mainEnemy;

    GameSceneTwo(String fileName) {
        super(fileName);
    }

    @Override
    protected void drawWinScreen() {
        drawMessage(WIN_MESSAGE2);
    }

    @Override
    protected void onKeyInput(Keys key) {
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
            // all other key inputs passed to player
            default:
                fae.handleKeyInput(key);
                break;
        }
    }

    @Override
    protected void drawStartScreen() {
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE_LINE1, INS_X, INS_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE_LINE2, INS_X, INS_Y + LINE_SEPARATION);
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE_LINE3, INS_X, INS_Y + LINE_SEPARATION + LINE_SEPARATION);
    }

    @Override
    protected void updateEntitiesAndDrawGameScreen() {
        // first update all entities for this frame
        updateEntities();
        // now draw
        BACKGROUND_IMAGE2.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        for (StationaryGameEntity drawable : gameEntities)
            drawable.draw();
    }

    @Override
    protected boolean win() {
        return mainEnemy.healthOver();
    }

    protected void populateSceneEntities(String fileName) {
        Point topLeftBound = null;
        Point bottomRightBound = null;
        Random coinTosser = new Random();

        // Read CSV
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] cells = text.split(",");
                String entityType = cells[0];
                double xCoord = Double.parseDouble(cells[1]);
                double yCoord = Double.parseDouble(cells[2]);
                Point position = new Point(xCoord, yCoord);

                // create game entities
                switch (entityType) {
                    case Player.NAME:
                        fae = new Player(position);
                        gameEntities.add(fae);
                        break;
                    case Wall.NAME:
                        StationaryGameEntity wall = new Wall(position);
                        gameEntities.add(wall);
                        break;
                    case Sinkhole.NAME:
                        StationaryGameEntity sinkhole = new Sinkhole(position);
                        gameEntities.add(sinkhole);
                        break;
                    case Tree.NAME:
                        StationaryGameEntity tree = new Tree(position);
                        gameEntities.add(tree);
                        break;
                    case PassiveDemon.NAME:
                        // randomize type based on coin toss
                        StationaryGameEntity demon;
                        if (coinTosser.nextInt(NO_OF_DEMON_TYPES) == AGGRESSIVE)
                            demon = new AggressiveDemon(position);
                        else
                            demon = new PassiveDemon(position);
                        gameEntities.add(demon);
                        enemies.add((DemonBehaviour) demon);
                        break;
                    case SpecialAggressiveDemon.NAME:
                        StationaryGameEntity navec = new SpecialAggressiveDemon(position);
                        gameEntities.add(navec);
                        this.mainEnemy = navec;
                        enemies.add((DemonBehaviour) navec);
                        break;
                    case TOP_LEFT:
                        topLeftBound = position;
                        break;
                    case BOTTOM_RIGHT:
                        bottomRightBound = position;
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(ERROR_MSG);
            System.exit(0);
        }

        // these are set once at runtime, so they don't have to be passed in each frame update call
        for (StationaryGameEntity entity : gameEntities) {
            if (entity instanceof MovableGameEntity) {
                ((MovableGameEntity) entity).setGameEntities(gameEntities);
                ((MovableGameEntity) entity).setBounds(topLeftBound,
                        bottomRightBound);
            }
        }
    }

    private void updateEntities() {
        for (DemonBehaviour enemy : enemies)
            enemy.onFrameUpdate(fae.getBoundingBox());
        fae.onFrameUpdate();
    }

    private void adjustTimeScale(Keys key) {
        switch (key) {
            case K:
                if (this.timeScale <= -3) break;
                this.timeScale -= 1;
                System.out.printf("Slowed down, Speed:  %d\n", this.timeScale);
                break;
            case L:
                if (this.timeScale >= 3) break;
                this.timeScale += 1;
                System.out.printf("Sped up, Speed:  %d\n", this.timeScale);
                break;
            default:
                break;
        }
    }
}
