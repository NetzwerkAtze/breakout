package breakout.game;

import breakout.gameObject.Ball;
import breakout.gameObject.Brick;
import breakout.gameObject.GameObject;
import breakout.gameObject.Paddle;
import breakout.input.InputHandler;
import breakout.level.LevelConfig;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.*;

public class    Game {
    private Paddle paddle;
    private List<Ball> balls = new ArrayList<>();;
    private List<Brick> bricks = new ArrayList<>();
    private List<GameObject> walls = new ArrayList<>();
    private final int maxAngle = 60;
    private double ballStartingRadius;
    private PowerUpHandler powerUpHandler;
    private CollisionHandler collisionHandler;
    private InputHandler inputHandler;
    private GameState gameState;
    private Color[] rowColors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.LIME, Color.LIGHTBLUE, Color.TEAL, Color.DARKBLUE, Color.PURPLE};
    private LevelConfig levelConfig;
    private Playfield playfield;
    private static final double MENU_GAP = 1;

    public Game(int startLives, LevelConfig levelConfig, InputHandler inputHandler, Playfield playfield) {
        this.paddle = new Paddle((playfield.right() + playfield.left() - 80) / 2, playfield.bottom() - 20, 5, 80, 15, Color.GREY, playfield.right() - playfield.left());
        balls.add(new Ball((playfield.right() + playfield.left()) / 2 - 2.5, (playfield.bottom() + playfield.top()) / 2, Math.random() > 0.5 ? -4 : 4, -4, 5, Color.WHITE));
        this.levelConfig = levelConfig;
        this.inputHandler = inputHandler;
        this.playfield = playfield;
        buildMap();
        ballStartingRadius = balls.getFirst().getRadius();
        powerUpHandler = new PowerUpHandler(this);
        collisionHandler = new CollisionHandler();
        gameState = new GameState(startLives, levelConfig.id().Level());
    }

    public void update() {
        handleInputs();
        if (gameState.isPaused())
            return;
        if (!gameState.isIdle()) {
            updateBalls();
        }
        powerUpHandler.update();
        if (bricks.stream().allMatch(Brick::isDestroyed)) {
            gameState.setLevelWon(true);
            gameState.setIdle(true);
        }
    }
    private void buildMap() {
        buildWalls();
        buildBricks();
    }

    private void updateBalls() {
        Iterator<Ball> it = balls.iterator();
        while (it.hasNext()) {
            Ball ball = it.next();
            ball.update(paddle);
            handleBrickCollision(ball);
            handlePaddleCollision(ball);
            handleWallCollision(ball);
            if (handleBallLoss(ball))
                    it.remove();
        }
    }
    private void handleWallCollision(Ball ball){
        for (GameObject wall : walls) {
            if (ball.collidesWith(wall))
                collisionHandler.resolveBallCollision(ball, wall, () -> { ball.setVy(-ball.getVy());});
        }
    }

    public void reset() {
         powerUpHandler.reset();
        for (Brick brick : bricks) {
            brick.repair();
        }
        gameState.reset();
        resetBall(balls.getFirst());
        while (balls.size() > 1)
            balls.removeLast();
        paddle.setX(((playfield.right() + playfield.left()) - paddle.getWidth()) / 2);
        paddle.setWidth(paddle.getStartingWidth());
        gameState.setIdle(true);
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

    public double getBallStartingRadius() {
        return ballStartingRadius;
    }

    public PowerUpHandler getPowerUpHandler() {
        return powerUpHandler;
    }

    public List<GameObject> getWalls() {
        return walls;
    }

    public GameState getGameState() {
        return gameState;
    }

    public LevelConfig getLevelConfig() {
        return levelConfig;
    }

    public Playfield getPlayfield() {
        return playfield;
    }
    private void buildBricks() {
        double brickAllignmentOffset = ((playfield.right() - playfield.left()) - (levelConfig.world().maxCol() * (levelConfig.world().brickWidth() + Playfield.BRICK_GAP) + Playfield.BRICK_GAP)) / 2;
        for (int row = 0; row < levelConfig.layout().size(); row++) {
            for (int col = 0; col < levelConfig.layout().get(row).length(); col++) {
                char currentSymbol = levelConfig.layout().get(row).charAt(col);
                if (Character.isDigit(currentSymbol)) {
                    Brick brick = new Brick(col * (levelConfig.world().brickWidth() + Playfield.BRICK_GAP) + playfield.left() + brickAllignmentOffset + Playfield.BRICK_GAP, (row * levelConfig.world().brickHeight()) + Playfield.BRICK_GAP + playfield.top(), levelConfig.world().brickWidth(), levelConfig.world().brickHeight(), rowColors[row % rowColors.length], Character.getNumericValue(currentSymbol));
                    bricks.add(brick);
                }
            }
        }
    }
    private void buildWalls() {
        //left wall
        walls.add(new GameObject(playfield.left() - Playfield.WALL_THICKNESS, playfield.top() - Playfield.WALL_THICKNESS + Playfield.BRICK_GAP, Playfield.WALL_THICKNESS, playfield.bottom()- (Playfield.MENU_BAR_HEIGHT + Playfield.BRICK_GAP), Color.GREY));
        //right wall
        walls.add(new GameObject(playfield.right(), playfield.top() - Playfield.WALL_THICKNESS + Playfield.BRICK_GAP, Playfield.WALL_THICKNESS, (playfield.bottom() - (Playfield.MENU_BAR_HEIGHT + Playfield.BRICK_GAP)), Color.GREY));
        // top wall
        walls.add(new GameObject(playfield.left(), Playfield.MENU_BAR_HEIGHT + MENU_GAP,playfield.right() - playfield.left(), Playfield.WALL_THICKNESS, Color.GREY));
    }

    private void handleInputs() {
        if (inputHandler.wasPressed(KeyCode.P))
            gameState.setPaused(!gameState.isPaused());
        if (!gameState.isPaused()) {
            if (inputHandler.wasPressed(KeyCode.SPACE)) {
                gameState.setIdle(false);
                for (Ball ball : balls)
                    ball.setBallState(Ball.BallState.FREE);
            }
            if (inputHandler.isHeld(KeyCode.RIGHT) || inputHandler.isHeld(KeyCode.LEFT))
                paddle.move(inputHandler.isHeld(KeyCode.LEFT), playfield.left(), playfield.right());
        }
    }
    private void handleBrickCollision(Ball ball) {
        for (Brick brick : bricks) {
            if (ball.collidesWith(brick) && !brick.isDestroyed()) {
                collisionHandler.resolveBallCollision(ball, brick, () -> ball.setVy(-ball.getVy()));
                brick.hit();
                if (brick.isDestroyed()) {
                    powerUpHandler.onBrickDestroyed(brick);
                    gameState.increaseScore();
                }
                break;
            }
        }
    }

    private void handlePaddleCollision(Ball ball) {
        if (ball.collidesWith(paddle)) {
            if (powerUpHandler.isStickyPaddle()) {
                if (ball.getBallState() == Ball.BallState.FREE) {
                    ball.setStickOffset(paddle.getX() - ball.getX());
                    ball.setBallState(Ball.BallState.STICKING);
                }
            }
            collisionHandler.resolveBallCollision(ball, paddle, () -> {
                ball.setVx(ball.getSpeed() * Math.sin(ball.getRadians(paddle, maxAngle)));
                if (ball.getY() + ball.getHeight() / 2 < paddle.getY() + paddle.getHeight() / 2)
                    ball.setVy(-(ball.getSpeed() * Math.cos(ball.getRadians(paddle, maxAngle))));
                else
                    ball.setVy(ball.getSpeed() * Math.cos(ball.getRadians(paddle, maxAngle)));
            });
        }
    }

    /**
     *
     * @param ball
     * @return true if ball needs to be removed from balls.
     */
    private boolean handleBallLoss(Ball ball) {
        if (ball.getY() > playfield.bottom()) {
            if (balls.size() > 1)
                return true;
            else if (gameState.isAlive()) {
                gameState.loseLife();
                gameState.setIdle(true);
            }
            if (gameState.isAlive() && balls.size() == 1) {
                resetBall(ball);
            }
        }
        return false;
    }

    private void resetBall(Ball ball) {
        ball.setY((playfield.bottom() + playfield.top()) / 2);
        ball.setX((playfield.right() + playfield.left()) / 2);
        ball.setVy(-Math.sqrt(balls.getFirst().getVy()*balls.getFirst().getVy()));
        if (Math.random() < 0.5)
            ball.setVx(-ball.getVx());
    }
}

