package breakout;

import breakout.gameObject.Ball;
import breakout.gameObject.Brick;
import breakout.gameObject.Paddle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main extends Application {
    private boolean moveLeft = false;
    private boolean moveRight = false;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 900, 600, Color.BLACK);
        Ball ball = new Ball(300, 200, 2, -2, 5, Color.WHITE);
        Paddle paddle = new Paddle((int) ((scene.getWidth()) - 80) / 2, (int) scene.getHeight() - 20, 5, 80, 15, Color.WHITE, (int) scene.getWidth());
        List<Brick> bricks = new ArrayList<>();
        Map<Brick, Rectangle> rectangleMap = new HashMap<>();
        int maxCol = 10;
        int maxRows = 5;
        int brickWidth = (int) scene.getWidth() / maxCol - 1;
        int brickHeight = 14;
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRows; row++) {
                Brick brick = new Brick(col * (brickWidth + 1), (row * brickHeight) + 1, brickWidth, brickHeight, Color.BLUE);
                bricks.add(brick);
                Rectangle brickShape = new Rectangle(brickWidth, brickHeight, brick.getColor());
                brickShape.setX(brick.getX());
                brickShape.setY(brick.getY());
                rectangleMap.put(brick, brickShape);
                root.getChildren().add(brickShape);
            }
        }
        Circle ballShape = new Circle(ball.getCenterX(), ball.getCenterY(), ball.getRadius(), ball.getColor());
        Rectangle paddleShape = new Rectangle(paddle.getWidth(), paddle.getHeight(), paddle.getColor());
        paddleShape.setX(paddle.getX());
        paddleShape.setY(paddle.getY());
        root.getChildren().add(ballShape);
        root.getChildren().add(paddleShape);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft = true;
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight = true;

            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft = false;
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight = false;
            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                ball.update();
                for (Brick brick : bricks) {
                    if (ball.collidesWith(brick) && !brick.isDestroyed()) {
                        if (ball.overlapX(brick))
                            ball.setVy(-ball.getVy());
                        else
                            ball.setVx(-ball.getVx());
                        brick.destroy();
                        rectangleMap.get(brick).setFill(Color.TRANSPARENT);
                        break;
                    }
                }
                if (ball.getX() < 0 || ball.getX() + ball.getWidth() > scene.getWidth())
                    ball.setVx(-ball.getVx());
                if (ball.getY() < 0)
                    ball.setVy(-ball.getVy());
                if (ball.collidesWith(paddle))
                    ball.setVy((-ball.getVy()));
                ballShape.setCenterX(ball.getCenterX());
                ballShape.setCenterY(ball.getCenterY());
                if (moveRight || moveLeft)
                    paddle.move(moveLeft);
                paddleShape.setX(paddle.getX());
                paddleShape.setY(paddle.getY());

            }
        };
        timer.start();

        primaryStage.setTitle("Breakout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}