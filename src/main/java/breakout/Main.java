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
    import javafx.scene.text.Font;
    import javafx.scene.text.Text;
    import javafx.stage.Stage;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;


    public class Main extends Application {
        private boolean moveLeft = false;
        private boolean moveRight = false;
        private int lives = 2;
        private boolean won = false;
        private boolean idle = true;

        @Override
        public void start(Stage primaryStage) {
            Pane root = new Pane();
            Scene scene = new Scene(root, 900, 600, Color.BLACK);

            Text wonText = new Text("YOU WON!");
            wonText.setY(scene.getHeight() / 2 - wonText.getLayoutBounds().getCenterY());
            wonText.setX(scene.getWidth() / 2 - wonText.getLayoutBounds().getCenterX());
            wonText.setFont(new Font(20));
            wonText.setFill(Color.TRANSPARENT);

            Text loseText = new Text("YOU LOST!");
            loseText.setY(scene.getHeight() / 2 - loseText.getLayoutBounds().getCenterY());
            loseText.setX(scene.getWidth() / 2 - loseText.getLayoutBounds().getCenterX());
            loseText.setFont(new Font(20));
            loseText.setFill(Color.TRANSPARENT);

            Ball ball = new Ball(300, 200, 3, -3, 5, Color.WHITE);
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
            root.getChildren().add(wonText);
            root.getChildren().add(loseText);

            scene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.LEFT) {
                    moveLeft = true;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    moveRight = true;
                } else if (event.getCode() == KeyCode.SPACE) {
                    idle = false;
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
                    if (!idle)
                        ball.update();
                    for (Brick brick : bricks) {
                        if (ball.collidesWith(brick) && !brick.isDestroyed()) {
                            if (ball.hitsOnY(brick))
                                ball.setVy(-ball.getVy());
                            else
                                ball.setVx(-ball.getVx());
                            brick.destroy();
                            rectangleMap.get(brick).setFill(Color.TRANSPARENT);
                            break;
                        }
                    }
                    if (bricks.stream().allMatch(Brick::isDestroyed)) {
                        won = true;
                        wonText.setFill(Color.WHITE);
                        this.stop();
                        }
                    if (ball.getX() < 0 || ball.getX() + ball.getWidth() > scene.getWidth())
                        ball.setVx(-ball.getVx());
                    if (ball.getY() < 0)
                        ball.setVy(-ball.getVy());
                    if (ball.collidesWith(paddle)) {
                        if (ball.hitsOnY(paddle))
                            ball.setVy(-ball.getVy());
                        else
                            ball.setVx(-ball.getVx());
                    }
                    ballShape.setCenterX(ball.getCenterX());
                    ballShape.setCenterY(ball.getCenterY());
                    if (moveRight || moveLeft)
                        paddle.move(moveLeft);
                    paddleShape.setX(paddle.getX());
                    paddleShape.setY(paddle.getY());
                    if (ball.getY() > scene.getHeight()) {
                        lives--;
                        idle = true;
                        ball.setY((int) scene.getHeight() / 2);
                        ball.setX((int) scene.getWidth() / 2);
                        ball.setVy(-ball.getVy());
                    }
                    if (lives == 0) {
                        loseText.setFill(Color.WHITE);
                        this.stop();
                    }
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