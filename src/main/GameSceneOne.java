package main;

import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import entities.Player;
import entities.Sinkhole;
import entities.StationaryGameEntity;
import entities.Wall;

import java.io.BufferedReader;
import java.io.FileReader;

class GameSceneOne extends GameScene {
    private final static String INSTRUCTION_MESSAGE_LINE2 = "USE ARROW KEYS TO FIND GATE";
    private final static int WIN_X = 950;
    private final static int WIN_Y = 670;
    private final Image BACKGROUND_IMAGE1 = new Image("res/background0.png");
    private final static String WIN_MESSAGE1 = "LEVEL COMPLETE!";

    public GameSceneOne(String fileName) {
        super(fileName);
    }

    @Override
    protected void drawStartScreen() {
        TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE_LINE1, TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE_LINE2, TITLE_X + INS_X_OFFSET,
                TITLE_Y + INS_Y_OFFSET + LINE_SEPARATION);
    }

    @Override
    protected void drawGameScreen() {
        fae.onFrameUpdate();
        BACKGROUND_IMAGE1.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        for (StationaryGameEntity drawable : gameEntities)
            drawable.draw();
    }

    protected boolean win() {
        Point playerPos = fae.getTopLeftPosition();
        return playerPos.x >= WIN_X && playerPos.y >= WIN_Y;
    }

    @Override
    protected void drawWinScreen() {drawMessage(WIN_MESSAGE1);}

    @Override
    protected boolean lose() {
        return fae.healthOver();
    }

    protected void populateSceneEntities(String fileName) {
        Point topLeftBound = null;
        Point bottomRightBound = null;

        // read CSV
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
}
