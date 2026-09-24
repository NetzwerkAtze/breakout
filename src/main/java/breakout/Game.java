package breakout;

import breakout.gameObject.Ball;
import breakout.gameObject.Brick;
import breakout.gameObject.Paddle;
import breakout.gameObject.PowerUp;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.util.*;

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
    private Random random = new Random();
    private List<PowerUp> powerUps = new ArrayList<>();
    private Map<Brick, PowerUp> brickPowerUpMap = new HashMap<>();
    private double paddleStartingWidth;

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
                generatePowerUps(brick);
            }
        }
        paddleStartingWidth = paddle.getWidth();
    }
    public void update() {
        if (!idle) {
            ball.update();

            if (!powerUps.isEmpty()) {
                for (PowerUp powerUp : powerUps) {
                    powerUp.update();
                    if (powerUp.getPowerUpState() == PowerUp.PowerUpState.EXPIRED) {
                        removeEffect(powerUp);
                        powerUp.setPowerUpState(PowerUp.PowerUpState.REMOVED);
                    }
                    if (powerUp.collidesWith(paddle)) {
                        applyEffect(powerUp);
                    }
                    else if (powerUp.getY() > scene.getHeight())
                        powerUp.setPowerUpState(PowerUp.PowerUpState.REMOVED);
                }
            }
        }
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
                if (brickPowerUpMap.get(brick) != null) {
                    brickPowerUpMap.get(brick).setPowerUpState(PowerUp.PowerUpState.FALLING);
                }
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
         if (lives == 0) {
            for (Brick brick : bricks)
                brick.repair();
            lives = startLives;
            levelWon = false;
            score = 0;
            ball.setY(scene.getHeight() / 2);
            ball.setX(scene.getWidth() / 2);
            ball.setVy(-ball.getVy());
            paddle.setX((scene.getWidth() - paddle.getWidth()) / 2);
            paddle.setWidth(paddleStartingWidth);
            idle = true;
            for (Brick brick : bricks)
                generatePowerUps(brick);
        }
    }
    public void applyEffect(PowerUp powerUp) {
        if (powerUp.getPowerUpState() == PowerUp.PowerUpState.FALLING) {
            if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_PADDLE) {
                paddle.setX(paddle.getX() - paddleStartingWidth / 2);
                paddle.setWidth(paddle.getWidth() + paddleStartingWidth);
            }
        }
        powerUp.setPowerUpState(PowerUp.PowerUpState.ACTIVE);
    }
    public void removeEffect(PowerUp powerUp) {
        if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_PADDLE) {
            paddle.setX(paddle.getX() + paddleStartingWidth / 2);
            paddle.setWidth(paddle.getWidth() - paddleStartingWidth);
        }
     //   powerUp.setPowerUpState(PowerUp.PowerUpState.REMOVED);
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
    public List<PowerUp> getPowerUps() {
        return powerUps;
    }
    public void generatePowerUps(Brick brick) {
        double chance = random.nextDouble();
        if (chance < 0.20) {
            PowerUp pUp = new PowerUp(brick.getX() + brickWidth / 2, brick.getY() + brickHeight / 2, Color.GREEN, 3, PowerUp.PowerUpType.BIGGER_PADDLE, 360);
            powerUps.add(pUp);
            brickPowerUpMap.put(brick,pUp);
        }
    }
}

