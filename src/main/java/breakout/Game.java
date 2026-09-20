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
    private int lives;
    private boolean won = false;
    private boolean idle = true;
    private int menuBarHeight = 30;
    private int score = 0;
    private int maxAngle = 60;
    private int maxCol;
    private int maxRow;
    private double brickHeight = 14;
    private double brickWidth;

    Color[] rowColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.LIME, Color.LIGHTBLUE, Color.TEAL, Color.DARKBLUE, Color.PURPLE};


    public Game(Scene scene, Paddle paddle, Ball ball, int lives, int maxCol, int maxRow) {
        this.scene = scene;
        this.paddle = paddle;
        this.ball = ball;
        this.lives = lives;
        this.maxCol = maxCol;
        this.maxRow = maxRow;
        this.brickWidth = scene.getWidth() / maxCol - 1;
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
                    ball.setVy(-ball.getVy());
                    ball.setVx(-ball.getVx());
                }
                else if (ball.hitsOnY(brick))
                    ball.setVy(-ball.getVy());
                else
                    ball.setVx(-ball.getVx());
                brick.destroy();
                score++;
                break;
            }
        }
        if (bricks.stream().allMatch(Brick::isDestroyed)) {
            won = true;
        }

        if (ball.getX() < 0 || ball.getX() + ball.getWidth() > scene.getWidth())
            ball.setVx(-ball.getVx());

        if (ball.getY() < menuBarHeight)
            ball.setVy(-ball.getVy());

        if (ball.collidesWith(paddle)) {
            if (ball.hitsEdge(paddle)) {
                ball.setVy(-ball.getVy());
                ball.setVx(-ball.getVx());
            }
            else if (ball.hitsOnY(paddle)) {
                ball.setVx(ball.getSpeed() * Math.sin(ball.getRadians(paddle, maxAngle)));
                ball.setVy(-(ball.getSpeed() * Math.cos(ball.getRadians(paddle, maxAngle))));
            }
            else
                ball.setVx(-ball.getVx());
        }

        if (ball.getY() > scene.getHeight()) {
            lives--;
            idle = true;
            if (lives > 0) {
                ball.setY(scene.getHeight() / 2);
                ball.setX(scene.getWidth() / 2);
                ball.setVy(-ball.getVy());
            }
        }
    }
    public boolean hasWon() {
        return won;
    }
    public boolean gameOver() {
        return lives == 0 || won;
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

    public int getMaxCol() {
        return maxCol;
    }

    public int getMaxRow() {
        return maxRow;
    }
}

