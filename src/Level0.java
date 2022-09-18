import bagel.*;
import bagel.util.Point;
import java.io.BufferedReader;
import java.io.FileReader;

public class Level0 extends Level {
    private static final Image BACKGROUND_IMAGE0 = new Image("res/background0.png");
    private final String WORLD_FILE_0 = "res/level0.csv";
    // uncomment line below and comment out line above if using Windows OS
    // private String worldFile = "res\\level0.csv";

    public Level0() {
        super(BACKGROUND_IMAGE0);
    }

    /**
     * Method used to read file and create objects (You can change this
     * method as you wish).
     */
    @Override
    void populateSceneEntities() {
        // read CSV
        try (BufferedReader br = new BufferedReader(new FileReader(WORLD_FILE_0))) {
            String text;

            while ((text = br.readLine()) != null) {
                String[] cells = text.split(",");
                String entityType = cells[0];
                double xCoord = Double.parseDouble(cells[1]);
                double yCoord = Double.parseDouble(cells[2]);

                // create game entities + bounding boxes
                switch (entityType) {
                    case "Fae":
                        fae = new Player(xCoord, yCoord);
                        gameEntities.add(fae);
                        break;
                    case "Wall":
                        Wall wall = new Wall(xCoord, yCoord);
                        gameEntities.add(wall);
                        break;
                    case "Sinkhole":
                        Sinkhole sinkhole = new Sinkhole(xCoord, yCoord);
                        gameEntities.add(sinkhole);
                        break;
                    case "TopLeft":
                        this.topLeftBound = new Point(xCoord, yCoord);
                        break;
                    case "BottomRight":
                        this.bottomRightBound = new Point(xCoord, yCoord);
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
    }

    @Override
    void drawWinScreen() {
        TITLE_MSG.drawString("LEVEL COMPLETE!", TITLE_MSG_LOC.x,
                (MSG_Y_SHIFT - MSG_X_SHIFT) + TITLE_MSG_LOC.y);
    }

    @Override
    void drawStartScreen() {
        TITLE_MSG.drawString("SHADOW DIMENSION", TITLE_MSG_LOC.x, TITLE_MSG_LOC.y);
        INSTRUCT_MSG.drawString("PRESS SPACE TO START", MSG_X_SHIFT + TITLE_MSG_LOC.x,
                MSG_Y_SHIFT + TITLE_MSG_LOC.y);
        INSTRUCT_MSG.drawString("USE ARROW KEYS TO FIND GATE", MSG_X_SHIFT + TITLE_MSG_LOC.x,
                MSG_Y_SHIFT + TITLE_MSG_LOC.y + LINE_SEPARATION);
    }
}
