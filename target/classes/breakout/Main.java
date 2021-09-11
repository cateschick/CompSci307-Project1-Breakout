package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.util.Duration;

public class Main extends Application {
    // SETUP VARIABLES
    private static final String TITLE = "Welcome to BREAKOUT!";
    public static final int HEIGHT = 600;
    public static final int WIDTH = 400;
    private static Paint BACKGROUND = Color.LIGHTBLUE;
    private Text myLives;
    public Text myScore;
    public static int LIVES = 3;
    public static int SCORE = 0;
    // ANIMATION VARIABLES
    private static final int FRAMES_PER_SECOND = 60;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    private Timeline animation;
    // END STATE VARIABLES
    private static final String LOSS_TEXT = "You lost the game :(";
    private static final String WIN_TEXT = "You won the game!!! :)";
    // PIECES
    public static Circle myBall;
    public static Rectangle myPaddle;
    public Group myBricks;

    /*
    This function starts game play
     */
    @Override
    public void start(Stage stage) {
        // ATTACH SCENE
        Scene myScene = setupGame();
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();

        // ANIMATION
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step()));
        animation.play();
    }
    /*
    This function creates the initial game scene and returns it
     */
    private Scene setupGame() {
        // Draw pieces
        displayLives(LIVES);
        displayScore(SCORE);
        Paddle.drawPaddle();
        Ball.drawBall();

        // Organize scene
        Group root = new Group(myPaddle, myBall, myLives, myScore);
        myBricks = new Group();
        Brick.drawBricks(root, myBricks);

        // Scene to see displayed shapes
        Scene scene = new Scene(root, WIDTH, HEIGHT, BACKGROUND);

        // respond to input
        scene.setOnKeyPressed(e -> Paddle.handleKeyInput(e.getCode()));

        return scene;
    }

    /*
    This function displays the score in the bottom-right hand screen
     */
    private void displayScore(int s) {
        myScore = new Text();
        myScore.setText("Score: " + s);
        myScore.setX(WIDTH - (WIDTH * .18));
        myScore.setY(HEIGHT - 20);
    }

    /*
    This function displays the number of lives remaining in the bottom-left hand screen
     */
    private void displayLives(int lives) {
        myLives = new Text();
        myLives.setText("Lives Remaining: " + lives);
        myLives.setX(5);
        myLives.setY(HEIGHT - 20);
        myLives.setFill(Color.RED);
    }

    // ANIMATION STEP
    private void step() {
        // Move ball
        Ball.moveBall();
        Ball.checkForBounce();
        Ball.checkForMiss(myBall, myLives, myPaddle);
        Ball.checkForCollision(this);
        checkEndState();
    }

    private void checkEndState() {
        // WIN
        int numBricks = Brick.NUM_ROWS * Brick.PER_ROW;
        int left = numBricks - Brick.BRICKS_BROKEN;
        if (left == 0) {
            Text text = new Text(WIN_TEXT);
            text.setFill(Color.WHITE);
            myBall.setCenterX((float)(myPaddle.getX() + (Paddle.PADDLE_WIDTH / 2)));
            myBall.setCenterY(myPaddle.getY() - Ball.BALL_RADIUS);
            BACKGROUND = Color.GREEN;

            StackPane root = new StackPane();
            root.getChildren().add(text);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, WIDTH, HEIGHT, BACKGROUND));
            stage.show();

            animation.stop();
        }
        // LOSS
        if (LIVES == 0) {
            Text text = new Text(LOSS_TEXT);
            text.setFill(Color.BLACK);
            myBall.setCenterX((float)(myPaddle.getX() + (Paddle.PADDLE_WIDTH / 2)));
            myBall.setCenterY(myPaddle.getY() - Ball.BALL_RADIUS);
            BACKGROUND = Color.RED;

            StackPane root = new StackPane();
            root.getChildren().add(text);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, WIDTH, HEIGHT, BACKGROUND));
            stage.show();

            animation.stop();
        }
    }

    // CHECK FOR COLLISIONS
    public boolean isIntersecting(Shape a, Shape b) {
        return b.getBoundsInParent().intersects(a.getBoundsInParent());
    }
}
