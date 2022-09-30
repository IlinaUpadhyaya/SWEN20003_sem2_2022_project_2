import bagel.Image;
import bagel.Window;
import bagel.util.Point;

import java.io.BufferedReader;
import java.io.FileReader;

public class GameSceneOne extends GameScene {

    private final Image BACKGROUND_IMAGE1 = new Image("res/background0.png");
    protected final Point WIN_POSITION = new Point(950, 670);
    public GameSceneOne(String fileName) {
        super(fileName);
    }

    @Override
    protected void drawStartScreen() {
        TITLE_FONT.drawString("SHADOW DIMENSION", TITLE_MSG_LOC.x,
                TITLE_MSG_LOC.y);
        MSG_FONT.drawString("PRESS SPACE TO START",
                MSG_X_SHIFT + TITLE_MSG_LOC.x, MSG_Y_SHIFT + TITLE_MSG_LOC.y);
        MSG_FONT.drawString("USE ARROW KEYS TO FIND GATE",
                MSG_X_SHIFT + TITLE_MSG_LOC.x,
                MSG_Y_SHIFT + TITLE_MSG_LOC.y + LINE_SEPARATION);
    }

    @Override
    protected void drawGameScreen() {
        BACKGROUND_IMAGE1.draw(Window.getWidth() / 2.0,
                Window.getHeight() / 2.0);
        for (StationaryGameEntity drawable : gameEntities)
            drawable.draw();
    }

    @Override
    protected void drawLoseScreen() {
        TITLE_FONT.drawString("GAME OVER!", MSG_X_SHIFT + TITLE_MSG_LOC.x,
                (MSG_Y_SHIFT - MSG_X_SHIFT) + TITLE_MSG_LOC.y);
    }

    @Override
    protected void drawWinScreen() {
        TITLE_FONT.drawString("LEVEL COMPLETE!", TITLE_MSG_LOC.x,
                (MSG_Y_SHIFT - MSG_X_SHIFT) + TITLE_MSG_LOC.y);
    }

    protected void populateSceneEntities(String fileName) {
        Point topLeftBound = null;
        Point bottomRightBound = null;
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
                                        yCoord), WALL, "WALL", 0);
                        gameEntities.add(wall);
                        break;
                    case "Sinkhole":
                        StationaryGameEntity sinkhole =
                                new StationaryGameEntity(new Point(xCoord,
                                        yCoord),SINKHOLE,"SINKHOLE",
                                        SINKHOLE_DAMAGE);
                        gameEntities.add(sinkhole);
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
    }

    protected boolean win() {
        Point playerPos = fae.getTopLeftPosition();
        return playerPos.x >= WIN_POSITION.x && playerPos.y >= WIN_POSITION.y;
    }

    @Override
    protected boolean lose() {
        return fae.healthOver();
    }
}
