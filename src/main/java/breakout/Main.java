package breakout;

import breakout.gameObject.Ball;
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


public class Main extends Application {
    private boolean moveLeft = false;
    private boolean moveRight = false;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 900, 600, Color.BLACK);
        Ball ball = new Ball(300, 200, 2, -2, 10, Color.WHITE);
        Paddle paddle = new Paddle((int) ((scene.getWidth()) - 80) / 2, (int) scene.getHeight() - 20, 5, 80, 15, Color.WHITE, (int) scene.getWidth());
        Circle ballShape = new Circle(ball.getCenterX(), ball.getCenterY(), ball.getRadius(), ball.getColor());
        Rectangle paddleShape = new Rectangle(paddle.getWidth(), paddle.getHeight(), paddle.getColor());
        paddleShape.setX(paddle.getX());
        paddleShape.setY(paddle.getY() );
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
                if (ball.getX() < 0 || ball.getX() + ball.getWidth() > scene.getWidth())
                    ball.setVx(-ball.getVx());
                if (ball.getY() < 0)
                    ball.setVy(-ball.getVy());
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