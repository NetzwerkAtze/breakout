package breakout;

import breakout.gameObject.Ball;
import breakout.gameObject.Brick;
import breakout.gameObject.Paddle;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.util.*;

public class    Game {
    private Scene scene;
    private Paddle paddle;
    private List<Ball> balls = new ArrayList<>();;
    private List<Brick> bricks = new ArrayList<>();
    private List<Brick> walls = new ArrayList<>();
    private int menuBarHeight = 30;
    private int maxAngle = 60;
    private double wallBorderThickness = 23.5;
    private double paddleStartingWidth;
    private double ballStartingRadius;
    private boolean moveRight;
    private boolean moveLeft;
    private boolean releaseBalls;
    private PowerUpHandler powerUpHandler;
    private CollisionHandler collisionHandler;
    private GameState gameState;
    private Color[] rowColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.LIME, Color.LIGHTBLUE, Color.TEAL, Color.DARKBLUE, Color.PURPLE};
    private LevelConfig levelConfig;

    public Game(Scene scene, Paddle paddle, Ball ball, int startLives, LevelConfig levelConfig) {
        this.scene = scene;
        this.paddle = paddle;
        balls.add(ball);
        this.moveLeft = false;
        this.moveRight = false;
        this.releaseBalls = false;
        this.levelConfig = levelConfig;
        init();
        paddleStartingWidth = paddle.getWidth();
        ballStartingRadius = balls.getFirst().getRadius();
        powerUpHandler = new PowerUpHandler(scene, this);
        collisionHandler = new CollisionHandler(this);
        gameState = new GameState(startLives, levelConfig.level());
    }
    public void init() {
        for (int row = 0; row < levelConfig.levelLayout().size(); row++) {
            for (int col = 0; col < levelConfig.levelLayout().get(row).length(); col++) {
                char currentSymbol = levelConfig.levelLayout().get(row).charAt(col);
                if (Character.isDigit(currentSymbol)) {
                    Brick brick = new Brick(col * (60 + 1) + 23.5, (row * 20) + menuBarHeight + 1 + 23.5, 60,20, rowColors[row % rowColors.length], Character.getNumericValue(currentSymbol));
                    bricks.add(brick);
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            walls.add(new Brick(0, menuBarHeight + 1 + ((scene.getHeight() - (menuBarHeight + 1)) / 6) * i, 23.5, (scene.getHeight() - (menuBarHeight + 1)) / 6, Color.GREY, Integer.MAX_VALUE));
            walls.add(new Brick(scene.getWidth() - 23.5, menuBarHeight + 1 + ((scene.getHeight() - (menuBarHeight + 1)) / 6) * i, 23.5, (scene.getHeight() - (menuBarHeight + 1)) / 6, Color.GREY, Integer.MAX_VALUE));
            walls.add(new Brick(23 + ((scene.getWidth() - 46) / 6) * i, menuBarHeight + 1,(scene.getWidth() - 46) / 6, 23.5, Color.GREY, Integer.MAX_VALUE));
        }
    }
    public void update() {
        if (gameState.isPaused())
            return;
        if (moveRight || moveLeft)
            paddle.move(moveLeft, walls.getFirst().getWidth());
        if (!gameState.isIdle()) {
            for (Ball ball : balls) {
                if (releaseBalls)
                    ball.setBallState(Ball.BallState.FREE);
                ball.update(paddle);
                for (Brick brick : bricks) {
                    if (ball.collidesWith(brick) && !brick.isDestroyed()) {
                        collisionHandler.resolveBallCollision(ball, brick, false, () -> ball.setVy(-ball.getVy()));
                        brick.hit();
                        if (brick.isDestroyed()) {
                            powerUpHandler.onBrickDestroyed(brick);
                            gameState.increaseScore();
                        }
                        break;
                    }
                }
            }
        }
        powerUpHandler.update();
        if (bricks.stream().allMatch(Brick::isDestroyed)) {
            gameState.setLevelWon(true);
            gameState.setIdle(true);
        }
        Iterator<Ball> it = balls.iterator();
        while (it.hasNext()) {
            Ball ball = it.next();
            if (ball.getX() < 0 + wallBorderThickness || ball.getX() + ball.getWidth() > scene.getWidth() - wallBorderThickness) {
                ball.setX(ball.getX() < 0 + wallBorderThickness ? wallBorderThickness : scene.getWidth() - wallBorderThickness - ball.getWidth());
                ball.setVx(-ball.getVx());
            }

            if (ball.getY() < menuBarHeight + wallBorderThickness) {
                ball.setVy(-ball.getVy());

            }

            if (ball.collidesWith(paddle)) {
                collisionHandler.resolveBallCollision(ball, paddle, true, () -> {
                    ball.setVx(ball.getSpeed() * Math.sin(ball.getRadians(paddle, maxAngle)));
                    ball.setVy(-(ball.getSpeed() * Math.cos(ball.getRadians(paddle, maxAngle))));
                });
            }
            if (ball.getY() > scene.getHeight()) {
                if (balls.size() > 1)
                    it.remove();
                else if (gameState.isAlive()) {
                    gameState.loseLife();
                    gameState.setIdle(true);
                }
                if (gameState.isAlive() && balls.size() == 1) {
                    ball.setY(scene.getHeight() / 2);
                    ball.setX(scene.getWidth() / 2);
                    ball.setVy(-ball.getVy());
                }
            }
        }
    }
    public void reset() {
         if (!gameState.isAlive()) {
             powerUpHandler.reset();
            for (Brick brick : bricks) {
                brick.repair();
            }
            gameState.reset();
            balls.getFirst().setY(scene.getHeight() / 2);
            balls.getFirst().setX(scene.getWidth() / 2);
            balls.getFirst().setVy(-balls.getFirst().getVy());
            paddle.setX((scene.getWidth() - paddle.getWidth()) / 2);
            paddle.setWidth(paddleStartingWidth);
        }
    }

    public void setReleaseBalls(boolean releaseBalls) {
        this.releaseBalls = releaseBalls;
    }

    public void setMoveRight(boolean right){
        moveRight = right;
    }

    public void setMoveLeft(boolean left){
        moveLeft = left;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public double getPaddleStartingWidth() {
        return paddleStartingWidth;
    }

    public double getBallStartingRadius() {
        return ballStartingRadius;
    }

    public PowerUpHandler getPowerUpHandler() {
        return powerUpHandler;
    }

    public List<Brick> getWalls() {
        return walls;
    }

    public GameState getGameState() {
        return gameState;
    }
}

