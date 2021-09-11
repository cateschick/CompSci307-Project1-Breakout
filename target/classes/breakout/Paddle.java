package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Paddle extends Main {
    public static final int PADDLE_WIDTH = 100;
    public static final int PADDLE_HEIGHT = 20;
    public static final Paint PADDLE_COLOR = Color.DARKBLUE;
    private static final double PADDLE_OFFSET = HEIGHT * 0.1;
    private static final int PADDLE_SPEED = 10;

    static void drawPaddle() {
        myPaddle = new Rectangle(PADDLE_WIDTH, PADDLE_HEIGHT);
        myPaddle.setFill(PADDLE_COLOR);
        myPaddle.setX((float) (WIDTH / 2 - Paddle.PADDLE_WIDTH / 2));
        myPaddle.setY(HEIGHT - Paddle.PADDLE_HEIGHT - Paddle.PADDLE_OFFSET);
    }
    // MOVE PADDLE
    static void handleKeyInput(KeyCode code) {
        double x = myPaddle.getX();

        if (code == KeyCode.RIGHT) {
            // Check to see if paddle is going outside bounds
            if (x + PADDLE_SPEED >= WIDTH) {
                myPaddle.setX(0);
            } else {
                myPaddle.setX(x + PADDLE_SPEED);
            }
        } else if (code == KeyCode.LEFT) {
            // Check to see if paddle is going outside bounds
            if ((x + PADDLE_WIDTH - PADDLE_SPEED) <= 0) {
                myPaddle.setX(WIDTH - PADDLE_WIDTH);
            } else {
                myPaddle.setX(x - PADDLE_SPEED);
            }
        }
    }
}