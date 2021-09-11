package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Ball extends Main {
    public static final Paint BALL_COLOR = Color.ORANGE;
    public static final double BALL_RADIUS = HEIGHT * 0.02;
    private static final int BALL_SPEED = 5;
    private static boolean BALL_X = true;
    private static boolean BALL_Y = true;

    // BALL GOES BEYOND BOTTOM SCREEN ? MISS
    static void checkForMiss(Circle myBall, Text myLives, Rectangle myPaddle) {
        double y = myBall.getCenterY();

        if (y >= HEIGHT) {
            // Subtract a life
            LIVES -= 1;
            myLives.setText("Lives Remaining: " + LIVES);

            // Move ball back to start & launch
            myBall.setCenterX((float) (myPaddle.getX() + (Paddle.PADDLE_WIDTH / 2)));
            myBall.setCenterY(myPaddle.getY() - BALL_RADIUS);
            BALL_Y = true;
            BALL_X = true;
        }
    }

    // BALL ANIMATION
    static void moveBall() {
        double x = myBall.getCenterX();
        double y = myBall.getCenterY();

        // Ball moving to the left
        if (!BALL_X) {
            // Left Up
            if (BALL_Y) {
                myBall.setCenterY(y - BALL_SPEED);
            }
            // Left Down
            else {
                myBall.setCenterY(y + BALL_SPEED);
            }
            myBall.setCenterX(x - BALL_SPEED);
        }
        // Ball moving to the right
        else {
            if (BALL_Y) {
                // Right Up
                myBall.setCenterY(y - BALL_SPEED);
            }
            else {
                // Right Down
                myBall.setCenterY(y + BALL_SPEED);
            }
            myBall.setCenterX(x + BALL_SPEED);

        }
    }

    // BALL HITS WALL
    static void checkForBounce() {
        double x = myBall.getCenterX();
        double y = myBall.getCenterY();

        // Right Wall Collision
        if (x >= WIDTH) {
            BALL_X = false;
        }
        // Top Wall Collision
        if (y <= 0) {
            BALL_Y = false;
        }
        // Left Wall Collision
        if (x <= 0) {
            BALL_X = true;
        }
    }

    /*
        This method initializes the ball on the screen
         */
    static void drawBall() {
        myBall = new Circle();
        myBall.setFill(BALL_COLOR);
        myBall.setRadius(BALL_RADIUS);
        myBall.setCenterX((float)(myPaddle.getX() + (Paddle.PADDLE_WIDTH / 2)));
        myBall.setCenterY(myPaddle.getY() - BALL_RADIUS);
    }

    // BALL HITS PADDLE OR BRICK
    static void checkForCollision(Main main) {
        if (main.isIntersecting(myPaddle, myBall)) {
            myPaddle.setFill(Color.YELLOW);
            // Change direction to Up
            BALL_Y = true;
            BALL_X = !BALL_X;
        }
        else {
            myPaddle.setFill(Paddle.PADDLE_COLOR);
        }
        // Get individual bricks
        for (Object brick : main.myBricks.getChildren()) {
            Rectangle r = (Rectangle) brick;
            if (main.isIntersecting(r, myBall)) {
                main.myBricks.getChildren().remove(brick);
                BALL_Y = !BALL_Y;
                Brick.BRICKS_BROKEN += 1;
                SCORE += Brick.POINTS_PER_BRICK;
                main.myScore.setText("Score: " + SCORE);
                break;
            }
        }
    }
}
