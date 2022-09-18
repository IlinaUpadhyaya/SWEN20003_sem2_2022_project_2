import bagel.*;
import bagel.util.Point;
import java.io.BufferedReader;
import java.io.FileReader;

public class Level1 extends Level {
    private static final Image BACKGROUND_IMAGE1 = new Image("res/background1.png");
    private final String WORLD_FILE_1 = "res/level1.csv";
    // uncomment line below and comment out line above if using Windows OS
    // private String worldFile = "res\\level1.csv";

    public Level1() {
        super(BACKGROUND_IMAGE1);
    }

    /**
     * Method used to read file and create objects (You can change this
     * method as you wish).
     */
    @Override
    void populateSceneEntities() {
        // read CSV
        try (BufferedReader br = new BufferedReader(new FileReader(WORLD_FILE_1))) {
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
                    case "Sinkhole":
                        Sinkhole sinkhole = new Sinkhole(xCoord, yCoord);
                        gameEntities.add(sinkhole);
                        break;
                    case "Tree":
                        Tree tree = new Tree(xCoord, yCoord);
                        gameEntities.add(tree);
                        break;
                    case "Demon":
                        Demon demon = new Demon(xCoord, yCoord);
                        gameEntities.add(demon);
                        break;
                    case "Navec":
                        Villain navec = new Villain(xCoord, yCoord);
                        gameEntities.add(navec);
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
        TITLE_MSG.drawString("CONGRATULATIONS!", TITLE_MSG_LOC.x,
                (MSG_Y_SHIFT - MSG_X_SHIFT) + TITLE_MSG_LOC.y);
    }

    @Override
    void drawStartScreen() {
        TITLE_MSG.drawString("SHADOW DIMENSION", TITLE_MSG_LOC.x, TITLE_MSG_LOC.y);
        INSTRUCT_MSG.drawString("PRESS SPACE TO START", MSG_X_SHIFT + TITLE_MSG_LOC.x,
                MSG_Y_SHIFT + TITLE_MSG_LOC.y);
        INSTRUCT_MSG.drawString("PRESS A TO ATTACK", MSG_X_SHIFT + TITLE_MSG_LOC.x,
                MSG_Y_SHIFT + TITLE_MSG_LOC.y + LINE_SEPARATION);
        INSTRUCT_MSG.drawString("DEFEAT NAVEC TO WIN", MSG_X_SHIFT + TITLE_MSG_LOC.x,
                MSG_Y_SHIFT + TITLE_MSG_LOC.y + (2 * LINE_SEPARATION));
    }
}
