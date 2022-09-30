import bagel.Image;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

public class GameSceneTwo extends GameScene {
    private final Image BACKGROUND_IMAGE2 = new Image("res/background1.png");

    protected static final Image TREE = new Image("res/tree.png");

    public GameSceneTwo(String fileName) {
        super(fileName);
    }

    @Override
    protected void drawStartScreen() {
        TITLE_FONT.drawString("SHADOW DIMENSION", TITLE_MSG_LOC.x,
                TITLE_MSG_LOC.y);
        MSG_FONT.drawString("PRESS SPACE TO START",
                MSG_X_SHIFT + TITLE_MSG_LOC.x, MSG_Y_SHIFT + TITLE_MSG_LOC.y);
        MSG_FONT.drawString("PRESS A TO ATTACK",
                MSG_X_SHIFT + TITLE_MSG_LOC.x,
                MSG_Y_SHIFT + TITLE_MSG_LOC.y + LINE_SEPARATION);
        MSG_FONT.drawString("DEFEAT NAVEC TO WIN",
                MSG_X_SHIFT + TITLE_MSG_LOC.x,
                MSG_Y_SHIFT * 2 + TITLE_MSG_LOC.y + LINE_SEPARATION);
    }

    @Override
    protected void drawGameScreen() {
        for (DemonBehaviour enemy : enemies)
            enemy.onFrameUpdate(fae.getBoundingBox());
        fae.onFrameUpdate();
        BACKGROUND_IMAGE2.draw(Window.getWidth() / 2.0,
                Window.getHeight() / 2.0);
        for (StationaryGameEntity drawable : gameEntities)
            drawable.draw();
    }

    @Override
    protected void drawLoseScreen() {

    }

    @Override
    protected void drawWinScreen() {
        TITLE_FONT.drawString("CONGRATULATIONS!", TITLE_MSG_LOC.x,
                (MSG_Y_SHIFT - MSG_X_SHIFT) + TITLE_MSG_LOC.y);
    }

    @Override
    protected boolean win() {
        return this.mainEnemy.healthOver();
    }

    @Override
    protected boolean lose() {
        return fae.healthOver();
    }

    protected void populateSceneEntities(String fileName) {
        Point topLeftBound = null;
        Point bottomRightBound = null;
        // read CSV
        Random coinTosser = new Random();
        try (BufferedReader br =
                     new BufferedReader(new FileReader(fileName))) {
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
                        StationaryGameEntity wall =
                                new StationaryGameEntity(new Point(xCoord,
                                        yCoord), WALL, "WALL");
                        gameEntities.add(wall);
                        break;
                    case "Sinkhole":
                        StationaryGameEntity sinkhole =
                                new StationaryGameEntity(new Point(xCoord,
                                        yCoord), SINKHOLE, "SINKHOLE", SINKHOLE_DAMAGE);
                        gameEntities.add(sinkhole);
                        break;
                    case "Tree":
                        StationaryGameEntity tree =
                                new StationaryGameEntity(new Point(xCoord,
                                        yCoord), TREE, "TREE", 0);
                        gameEntities.add(tree);
                        break;
                    case "Demon":
                        /*randomize type based on coin toss*/
                        StationaryGameEntity demon;
                        if (coinTosser.nextInt(2) == 1)
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

    @Override
    public void onKeyInput(Keys key) {
        switch (key) {
            // implement speed change onto all moving enemies
            case K:
            case L:
                for (StationaryGameEntity gameEntity : this.gameEntities) {
                    if (gameEntity instanceof Player) continue;
                    if (gameEntity instanceof AggressiveDemon) {
                        ((AggressiveDemon) gameEntity).adjustTimeScale(key);
                    }
                }
                break;

            default:
                fae.handleKeyInput(key);
                break;
        }
    }
}
