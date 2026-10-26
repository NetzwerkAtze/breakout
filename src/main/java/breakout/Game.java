package breakout;

import breakout.gameObject.Ball;
import breakout.gameObject.Brick;
import breakout.gameObject.GameObject;
import breakout.gameObject.Paddle;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.*;

public class    Game {
    private Scene scene;
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
    private static final double WALL_THICKNESS = 23.5;
    private static final double MENU_BAR_HEIGHT = 30;
    private static final double BRICK_GAP = 1;

    public Game(Scene scene, int startLives, LevelConfig levelConfig, InputHandler inputHandler) {
        this.scene = scene;
        this.paddle = new Paddle((scene.getWidth() - 80) / 2, scene.getHeight() - 20, 5, 80, 15, Color.GREY, scene.getWidth());;
        balls.add(new Ball(scene.getWidth() / 2, scene.getHeight() / 2, 4, -4, 5, Color.WHITE));
        this.levelConfig = levelConfig;
        this.playfield = initPlayfield(levelConfig);
        this.inputHandler = inputHandler;
        buildMap();
        ballStartingRadius = balls.getFirst().getRadius();
        powerUpHandler = new PowerUpHandler(scene, this);
        collisionHandler = new CollisionHandler(this);
        gameState = new GameState(startLives, levelConfig.level());
    }
    public void buildMap() {
        buildWalls();
        buildBricks();
    }
    public void update() {
        if (inputHandler.wasPressed(KeyCode.P))
            gameState.setPaused(!gameState.isPaused());
        if (inputHandler.wasPressed(KeyCode.SPACE))
            gameState.setIdle(false);
        if (gameState.isPaused())
            return;
        if (inputHandler.isHeld(KeyCode.RIGHT) || inputHandler.isHeld(KeyCode.LEFT))
            paddle.move(inputHandler.isHeld(KeyCode.LEFT), playfield.left(), playfield.right());
        if (!gameState.isIdle()) {
            updateBalls();
        }
        powerUpHandler.update();
        if (bricks.stream().allMatch(Brick::isDestroyed)) {
            gameState.setLevelWon(true);
            gameState.setIdle(true);
        }
    }
    public void updateBalls() {
        Iterator<Ball> it = balls.iterator();
        while (it.hasNext()) {
            Ball ball = it.next();
            if (inputHandler.wasPressed(KeyCode.SPACE)) //releaseBalls
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
                }
            }
            if (ball.collidesWith(paddle)) {
                collisionHandler.resolveBallCollision(ball, paddle, true, () -> {
                    ball.setVx(ball.getSpeed() * Math.sin(ball.getRadians(paddle, maxAngle)));
                    if (ball.getY() + ball.getHeight() / 2 < paddle.getY() + paddle.getHeight() / 2)
                        ball.setVy(-(ball.getSpeed() * Math.cos(ball.getRadians(paddle, maxAngle))));
                    else
                        ball.setVy(ball.getSpeed() * Math.cos(ball.getRadians(paddle, maxAngle)));
                });
            }
            for (GameObject wall : walls) {
                if (ball.collidesWith(wall))
                    collisionHandler.resolveBallCollision(ball, wall, false, () -> { ball.setVy(-ball.getVy());});
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
         powerUpHandler.reset();
        for (Brick brick : bricks) {
            brick.repair();
        }
        gameState.reset();
        balls.getFirst().setY(scene.getHeight() / 2);
        balls.getFirst().setX(scene.getWidth() / 2);
        balls.getFirst().setVy(-Math.sqrt(balls.getFirst().getVy()*balls.getFirst().getVy()));
        paddle.setX((scene.getWidth() - paddle.getWidth()) / 2);
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
    private Playfield initPlayfield(LevelConfig config) {
        double gap = (scene.getWidth() - Math.min(config.mapWidth(), scene.getWidth())) / 2;
        return new Playfield(0 + gap + WALL_THICKNESS, scene.getWidth() - gap - WALL_THICKNESS, MENU_BAR_HEIGHT + WALL_THICKNESS);
    }

    public LevelConfig getLevelConfig() {
        return levelConfig;
    }

    public Playfield getPlayfield() {
        return playfield;
    }
    private void buildBricks() {
        double brickAllignmentOffset = ((playfield.right() - playfield.left()) - (levelConfig.maxCol() * (levelConfig.brickWidth() + BRICK_GAP) + BRICK_GAP)) / 2;
        for (int row = 0; row < levelConfig.levelLayout().size(); row++) {
            for (int col = 0; col < levelConfig.levelLayout().get(row).length(); col++) {
                char currentSymbol = levelConfig.levelLayout().get(row).charAt(col);
                if (Character.isDigit(currentSymbol)) {
                    Brick brick = new Brick(col * (levelConfig.brickWidth() + BRICK_GAP) + playfield.left() + brickAllignmentOffset + BRICK_GAP, (row * levelConfig.brickHeight()) + MENU_BAR_HEIGHT + BRICK_GAP + WALL_THICKNESS, levelConfig.brickWidth(), levelConfig.brickHeight(), rowColors[row % rowColors.length], Character.getNumericValue(currentSymbol));
                    bricks.add(brick);
                }
            }
        }
    }
    private void buildWalls() {
        //left wall
        walls.add(new GameObject(playfield.left() - WALL_THICKNESS, playfield.top() - WALL_THICKNESS + BRICK_GAP, WALL_THICKNESS,  (scene.getHeight() - (MENU_BAR_HEIGHT + BRICK_GAP)), Color.GREY));
        //right wall
        walls.add(new GameObject(playfield.right(), playfield.top() - WALL_THICKNESS + BRICK_GAP, WALL_THICKNESS, (scene.getHeight() - (MENU_BAR_HEIGHT + BRICK_GAP)), Color.GREY));
        // top wall
        walls.add(new GameObject(playfield.left(), MENU_BAR_HEIGHT + BRICK_GAP,(levelConfig.mapWidth() - WALL_THICKNESS * 2), WALL_THICKNESS, Color.GREY));
    }
}

