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
    private Random random = new Random();
    private List<PowerUp> powerUps = new ArrayList<>();
    private Map<Brick, PowerUp> brickPowerUpMap = new HashMap<>();
    private double paddleStartingWidth;
    private double ballStartingRadius;

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
        ballStartingRadius = balls.getFirst().getRadius();
    }
    public void update() {
        if (!idle) {
            for (Ball ball : balls)
                ball.update();
            if (!powerUps.isEmpty()) {
                for (PowerUp powerUp : powerUps) {
                    if (powerUp.getPowerUpState() == PowerUp.PowerUpState.REMOVED)
                        continue;
                    powerUp.update();
                    if (powerUp.getPowerUpState() == PowerUp.PowerUpState.EXPIRED) {
                        removeEffect(powerUp);
                    }
                    if (powerUp.collidesWith(paddle)) {
                        applyEffect(powerUp);
                    }
                    else if (powerUp.getY() > scene.getHeight())
                        powerUp.setPowerUpState(PowerUp.PowerUpState.REMOVED);
                }
            }
        }
        for (Ball ball : balls) {
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
             powerUps.clear();
             brickPowerUpMap.clear();
            for (Brick brick : bricks) {
                brick.repair();
                generatePowerUps(brick);
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
    public void applyEffect(PowerUp powerUp) {
        if (powerUp.getPowerUpState() == PowerUp.PowerUpState.FALLING) {
            if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_PADDLE) {
                paddle.setX(paddle.getX() - paddleStartingWidth / 2);
                paddle.setWidth(paddle.getWidth() + paddleStartingWidth);
            }
            else if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_BALL) {
                for (Ball ball : balls)
                    ball.setRadius(ball.getRadius() + ballStartingRadius);
            }
            else if (powerUp.getPowerUpType() == PowerUp.PowerUpType.ANOTHER_BALL) {
                balls.add(new Ball(balls.getFirst().getX(),balls.getFirst().getY(), -balls.getFirst().getVx(), balls.getFirst().getVy(), balls.getFirst().getRadius(), Color.WHITE));
            }
            powerUp.setPowerUpState(PowerUp.PowerUpState.ACTIVE);
        }
    }
    public void removeEffect(PowerUp powerUp) {
        if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_PADDLE) {
            paddle.setX(paddle.getX() + paddleStartingWidth / 2);
            paddle.setWidth(paddle.getWidth() - paddleStartingWidth);
        }
        if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_BALL) {
            for (Ball ball : balls)
                ball.setRadius(ball.getRadius() - ballStartingRadius);
        }
        powerUp.setPowerUpState(PowerUp.PowerUpState.REMOVED);
    }
    public void generatePowerUps(Brick brick) {
        double chance = random.nextDouble();
        if (chance < 0.10) {
            PowerUp pUp = new PowerUp(brick.getX() + brickWidth / 2, brick.getY() + brickHeight / 2, Color.GREEN, 3, PowerUp.PowerUpType.BIGGER_PADDLE, 360, 6);
            powerUps.add(pUp);
            brickPowerUpMap.put(brick, pUp);
        }
        else if (chance < 0.20) {
            PowerUp pUp = new PowerUp(brick.getX() + brickWidth / 2, brick.getY() + brickHeight / 2, Color.BLUE, 3, PowerUp.PowerUpType.BIGGER_BALL, 360, 6);
            powerUps.add(pUp);
            brickPowerUpMap.put(brick, pUp);
        } else if (chance < 1.0) {
            PowerUp pUp = new PowerUp(brick.getX() + brickWidth / 2, brick.getY() + brickHeight / 2, Color.YELLOW, 3, PowerUp.PowerUpType.ANOTHER_BALL, 360, 6);
            powerUps.add(pUp);
            brickPowerUpMap.put(brick, pUp);
        }
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
        return balls.getFirst();
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
    public List<Ball> getBalls() {
        return balls;
    }
}

