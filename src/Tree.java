import bagel.*;
import bagel.util.Point;

public class Tree extends GameEntity {
    private static final Image TREE = new Image("res/tree.png");

    public Tree(double x, double y) {
        super(new Point(x,y), TREE);
    }
}
