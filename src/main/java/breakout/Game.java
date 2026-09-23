package breakout;

import breakout.gameObject.Ball;
import breakout.gameObject.Brick;
import breakout.gameObject.Paddle;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Game {
    Scene scene;
    private Paddle paddle;
    private Ball ball;
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

    Color[] rowColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.LIME, Color.LIGHTBLUE, Color.TEAL, Color.DARKBLUE, Color.PURPLE};

    public Game(Scene scene, Paddle paddle, Ball ball, int startLives, int maxCol, int maxRow, int level) {
        this.scene = scene;
        this.paddle = paddle;
        this.ball = ball;
        this.startLives = startLives;
        this.lives = startLives;
        this.maxCol = maxCol;
        this.maxRow = maxRow;
        this.brickWidth = scene.getWidth() / maxCol - 1;
        this.level = level;
        init();
    }
    public void init() {
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow; row++) {
                Brick brick = new Brick(col * (brickWidth + 1), (row * brickHeight) + menuBarHeight + 1, brickWidth, brickHeight, rowColors[row % rowColors.length]);
                bricks.add(brick);
            }
        }
    }
    public void update() {
        if (!idle)
            ball.update();
        for (Brick brick : bricks) {
            if (ball.collidesWith(brick) && !brick.isDestroyed()) {
                if (ball.hitsEdge(brick)) {
                    ball.resolveCollision(brick, true, false);
                    ball.setVy(-ball.getVy());
                    ball.setVx(-ball.getVx());
                } else if (ball.hitsOnY(brick)) {
                    ball.resolveCollision(brick, false, true);
                    ball.setVy(-ball.getVy());
                } else {
                    ball.resolveCollision(brick, false, false);
                    ball.setVx(-ball.getVx());
                }
                brick.destroy();
                score++;
                break;
            }
        }
        if (bricks.stream().allMatch(Brick::isDestroyed)) {
            levelWon = true;
            idle = true;
        }

        if (ball.getX() < 0 || ball.getX() + ball.getWidth() > scene.getWidth())
            ball.setVx(-ball.getVx());

        if (ball.getY() < menuBarHeight)
            ball.setVy(-ball.getVy());

        if (ball.collidesWith(paddle)) {
            if (ball.hitsEdge(paddle)) {
                ball.resolveCollision(paddle, true, false);
                ball.setVy(-ball.getVy());
                ball.setVx(-ball.getVx());
            }
            else if (ball.hitsOnY(paddle)) {
                ball.resolveCollision(paddle, false, true);
                ball.setVx(ball.getSpeed() * Math.sin(ball.getRadians(paddle, maxAngle)));
                ball.setVy(-(ball.getSpeed() * Math.cos(ball.getRadians(paddle, maxAngle))));
            }
            else {
                ball.resolveCollision(paddle, false, false);
                ball.setVx(-ball.getVx());
            }
        }

        if (ball.getY() > scene.getHeight()) {
            if (lives > 0)
                lives--;
            idle = true;
            if (lives > 0) {
                ball.setY(scene.getHeight() / 2);
                ball.setX(scene.getWidth() / 2);
                ball.setVy(-ball.getVy());
            }
        }
    }
    public void reset() {
        for (Brick brick : bricks)
            brick.repair();
        lives = startLives;
        levelWon = false;
        score = 0;
        ball.setY(scene.getHeight() / 2);
        ball.setX(scene.getWidth() / 2);
        ball.setVy(-ball.getVy());
        paddle.setX((scene.getWidth() - paddle.getWidth()) / 2);
        idle = true;
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
    public Ball getBall() {
        return ball;
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
    public int getMaxCol() {
        return maxCol;
    }
    public int getMaxRow() {
        return maxRow;
    }
}

