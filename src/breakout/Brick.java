package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Brick extends Main {
    public static final int NUM_ROWS = 4;
    public static final int PER_ROW = 10;
    public static final int ROW_SEPARATION = 2;
    public static final int COLUMN_SEPARATION = 5;
    public static final Paint BRICK_COLOR = Color.DARKBLUE;

    public static final double BRICK_WIDTH = (float) WIDTH / PER_ROW;
    public static final double BRICK_HEIGHT = 20;
    public static final int BRICK_OFFSET = 50;

    public static int BRICKS_BROKEN = 0;
    public static final int POINTS_PER_BRICK = 10;

    // DRAW J ROWS OF I BRICKS
    static void drawBricks(Group a, Group myBricks) {
        for (int j = 0; j < NUM_ROWS; j++) {
            for (int i = 0; i < PER_ROW; i++) {
                Rectangle r = new Rectangle(BRICK_WIDTH - ROW_SEPARATION, BRICK_HEIGHT - COLUMN_SEPARATION);
                r.setX(i * BRICK_WIDTH);
                r.setY(j * BRICK_HEIGHT + (double) BRICK_OFFSET);
                r.setFill(BRICK_COLOR);
                myBricks.getChildren().add(r);
            }
        }
        a.getChildren().add(myBricks);
    }
}
