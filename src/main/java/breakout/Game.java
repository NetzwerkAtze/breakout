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
    private int startLives;
    private int lives;
    private boolean levelWon = false;
    private boolean idle = true;
    private int menuBarHeight = 30;
    private int score = 0;
    private int maxAngle = 60;
    private int maxCol;
    private int maxRow;
    private double brickHeight = 14;
    private double brickWidth;
    private  int level;
    private double paddleStartingWidth;
    private double ballStartingRadius;
    private boolean sticky;
    private boolean moveRight;
    private boolean moveLeft;
    private boolean releaseBalls;
    private PowerUpHandler powerUpHandler;
    private CollisionHandler collisionHandler;

    Color[] rowColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.LIME, Color.LIGHTBLUE, Color.TEAL, Color.DARKBLUE, Color.PURPLE};

    public Game(Scene scene, Paddle paddle, Ball ball, int startLives, int maxCol, int maxRow, int level) {
        this.scene = scene;
        this.paddle = paddle;
        balls.add(ball);
        this.startLives = startLives;
        this.lives = startLives;
        this.maxCol = maxCol;
        this.maxRow = maxRow;
        this.brickWidth = scene.getWidth() / maxCol - 1;
        this.level = level;
        this.sticky = false;
        this.moveLeft = false;
        this.moveRight = false;
        this.releaseBalls = false;
        init();
    }
    public void init() {
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow; row++) {
                Brick brick = new Brick(col * (brickWidth + 1), (row * brickHeight) + menuBarHeight + 1, brickWidth, brickHeight, rowColors[row % rowColors.length]);
                bricks.add(brick);
            }
        }
        paddleStartingWidth = paddle.getWidth();
        ballStartingRadius = balls.getFirst().getRadius();
        powerUpHandler = new PowerUpHandler(scene, this);
        collisionHandler = new CollisionHandler(this);
    }
    public void update() {
        if (moveRight || moveLeft)
            paddle.move(moveLeft);
        if (!idle) {
            for (Ball ball : balls) {
                if (releaseBalls)
                    ball.setBallState(Ball.BallState.FREE);
                ball.update(paddle);
            }
            powerUpHandler.update();
        }
        for (Ball ball : balls) {
            for (Brick brick : bricks) {

                if (ball.collidesWith(brick) && !brick.isDestroyed()) {
                    collisionHandler.resolveBallCollision(ball, brick, false, () -> ball.setVy(-ball.getVy()));
                    brick.destroy();
                    score++;
                    break;
                }
            }
        }
        if (bricks.stream().allMatch(Brick::isDestroyed)) {
            levelWon = true;
            idle = true;
        }
        Iterator<Ball> it = balls.iterator();
        while (it.hasNext()) {
            Ball ball = it.next();
            if (ball.getX() < 0 || ball.getX() + ball.getWidth() > scene.getWidth())
                ball.setVx(-ball.getVx());

            if (ball.getY() < menuBarHeight)
                ball.setVy(-ball.getVy());

            if (ball.collidesWith(paddle)) {
                collisionHandler.resolveBallCollision(ball, paddle, true, () -> {
                    ball.setVx(ball.getSpeed() * Math.sin(ball.getRadians(paddle, maxAngle)));
                    ball.setVy(-(ball.getSpeed() * Math.cos(ball.getRadians(paddle, maxAngle))));
                });
            }
            if (ball.getY() > scene.getHeight()) {
                if (balls.size() > 1)
                    it.remove();
                else if (lives > 0) {
                    lives--;
                    idle = true;
                }
                if (lives > 0 && balls.size() == 1) {
                    ball.setY(scene.getHeight() / 2);
                    ball.setX(scene.getWidth() / 2);
                    ball.setVy(-ball.getVy());
                }
            }
        }
    }
    public void reset() {
         if (lives == 0) {
             powerUpHandler.reset();
            for (Brick brick : bricks) {
                brick.repair();
            }
            lives = startLives;
            levelWon = false;
            score = 0;
            balls.getFirst().setY(scene.getHeight() / 2);
            balls.getFirst().setX(scene.getWidth() / 2);
            balls.getFirst().setVy(-balls.getFirst().getVy());
            paddle.setX((scene.getWidth() - paddle.getWidth()) / 2);
            paddle.setWidth(paddleStartingWidth);
            idle = true;
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
    public boolean isLevelWon() {
        return levelWon;
    }

    public boolean gameOver() {
        return lives == 0 || levelWon;
    }

    public int getScore() {
        return score;
    }

    public double getBrickHeight() {
        return brickHeight;
    }

    public double getBrickWidth() {
        return brickWidth;
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public int getLives() {
        return lives;
    }

    public int getLevel() {
        return level;
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

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    public boolean isSticky() {
        return sticky;
    }

    public int getMaxAngle() {
        return maxAngle;
    }
}

