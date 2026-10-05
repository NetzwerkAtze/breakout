package breakout;

import breakout.gameObject.Ball;
import breakout.gameObject.Brick;
import breakout.gameObject.PowerUp;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.util.*;

public class PowerUpHandler {
    private Scene scene;
    private Game game;
    private int powerUpDuration;
    private int powerUpSize;
    private double fallingSpeed;
    private double dropChance;
    private Random random = new Random();
    private List<PowerUp> powerUps = new ArrayList<>();
    private Map<Brick, PowerUp> brickPowerUpMap = new HashMap<>();

    /**
     * Constructor with default values.
     */
    public  PowerUpHandler(Scene scene, Game game) {
        this(scene, game, 3, 360, 6, 0.1);
    }

    public PowerUpHandler(Scene scene, Game game, int fallingSpeed, int  powerUpDuration, int powerUpSize, double dropChance) {
        this.scene = scene;
        this.game = game;
        this.fallingSpeed = fallingSpeed;
        this.powerUpDuration = powerUpDuration;
        this.powerUpSize = powerUpSize;
        this.dropChance = dropChance;
        init();
    }
    public void update() {
        if (!powerUps.isEmpty()) {
            for (PowerUp powerUp : powerUps) {
                if (powerUp.getPowerUpState() == PowerUp.PowerUpState.REMOVED)
                    continue;
                powerUp.update();
                if (powerUp.getPowerUpState() == PowerUp.PowerUpState.EXPIRED) {
                    removeEffect(powerUp);
                }
                if (powerUp.collidesWith(game.getPaddle())) {
                    applyEffect(powerUp);
                } else if (powerUp.getY() > scene.getHeight())
                    powerUp.setPowerUpState(PowerUp.PowerUpState.REMOVED);
            }
            for (Ball ball : game.getBalls()) {
                for (Brick brick : game.getBricks()) {
                    if (ball.collidesWith(brick) && !brick.isDestroyed()) {
                        if (brickPowerUpMap.get(brick) != null) {
                            brickPowerUpMap.get(brick).setPowerUpState(PowerUp.PowerUpState.FALLING);
                        }
                    }
                }
            }
        }
    }
    public void reset() {
        for (PowerUp powerUp : powerUps)
            if (powerUp.getPowerUpState() == PowerUp.PowerUpState.ACTIVE)
                removeEffect(powerUp);
        powerUps.clear();
        brickPowerUpMap.clear();
        for (Brick brick : game.getBricks())
            generatePowerUps(brick);
    }
    private void init() {
        for (Brick brick : game.getBricks())
            generatePowerUps(brick);
    }
    private void generatePowerUps(Brick brick) {
        double chance = random.nextDouble();
        Color color = null;
        PowerUp.PowerUpType type = null;
        PowerUp pUp;
        if (chance < dropChance) {
            color = Color.GREEN;
            type = PowerUp.PowerUpType.BIGGER_PADDLE;
        } else if (chance < dropChance * 2) {
            color = Color.BLUE;
            type = PowerUp.PowerUpType.BIGGER_BALL;
        } else if (chance < dropChance * 3) {
            color = Color.YELLOW;
            type = PowerUp.PowerUpType.ANOTHER_BALL;
        } else if (chance < dropChance * 8) {
            color = Color.RED;
            type = PowerUp.PowerUpType.STICKY_PADDLE;
        }
        if (color != null) {
            pUp = new PowerUp(brick.getX() + brick.getWidth() / 2, brick.getY() + brick.getHeight() / 2, color, fallingSpeed, type, powerUpDuration, powerUpSize);
            powerUps.add(pUp);
            brickPowerUpMap.put(brick, pUp);
        }
    }
    public void applyEffect(PowerUp powerUp) {
        if (powerUp.getPowerUpState() == PowerUp.PowerUpState.FALLING) {
            if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_PADDLE) {
                game.getPaddle().setX(game.getPaddle().getX() - game.getPaddleStartingWidth() / 2);
                game.getPaddle().setWidth(game.getPaddle().getWidth() + game.getPaddleStartingWidth());
            }
            else if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_BALL) {
                for (Ball ball : game.getBalls())
                    ball.setRadius(ball.getRadius() + game.getBallStartingRadius());
            }
            else if (powerUp.getPowerUpType() == PowerUp.PowerUpType.ANOTHER_BALL) {
                game.getBalls().add(new Ball(game.getBalls().getFirst().getX(),game.getBalls().getFirst().getY(), -game.getBalls().getFirst().getVx(), game.getBalls().getFirst().getVy(), game.getBalls().getFirst().getRadius(), Color.WHITE));
            }
            else if (powerUp.getPowerUpType() == PowerUp.PowerUpType.STICKY_PADDLE) {
                if (game.getGameState().isSticky()) {
                    powerUps.stream()
                            .filter(pUp -> pUp != powerUp)
                            .filter(pUp -> pUp.getPowerUpType() == PowerUp.PowerUpType.STICKY_PADDLE)
                            .filter(pUp -> pUp.getPowerUpState() == PowerUp.PowerUpState.ACTIVE)
                            .forEach(pUp -> pUp.setMaxDuration(powerUp.getMaxDuration() + pUp.getMaxDuration()));
                    powerUp.setPowerUpState(PowerUp.PowerUpState.REMOVED);
                }
                else
                    game.getGameState().setSticky(true);
            }
            if (powerUp.getPowerUpState() != PowerUp.PowerUpState.REMOVED)
                powerUp.setPowerUpState(PowerUp.PowerUpState.ACTIVE);
        }
    }
    public void removeEffect(PowerUp powerUp) {
        if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_PADDLE) {
            game.getPaddle().setX(game.getPaddle().getX() + game.getPaddleStartingWidth() / 2);
            game.getPaddle().setWidth(game.getPaddle().getWidth() - game.getPaddleStartingWidth());
        }
        if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_BALL) {
            for (Ball ball : game.getBalls())
                ball.setRadius(ball.getRadius() - game.getBallStartingRadius());
        }
        if (powerUp.getPowerUpType() == PowerUp.PowerUpType.STICKY_PADDLE)
            game.getGameState().setSticky(false);
        powerUp.setPowerUpState(PowerUp.PowerUpState.REMOVED);
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }
}
