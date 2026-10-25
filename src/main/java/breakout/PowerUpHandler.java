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
    private boolean stickyPaddle = false;
    private Random random = new Random();
    private List<PowerUp> powerUps = new ArrayList<>();
    private Map<Brick, PowerUp> brickPowerUpMap = new HashMap<>();
    private double paddleSizeIncrease = 100;

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
    }

    public void onBrickDestroyed(Brick brick)  {
        PowerUp pUp = brickPowerUpMap.get(brick);
        if (pUp != null && pUp.getPowerUpState() == PowerUp.PowerUpState.WAITING)
            pUp.setPowerUpState(PowerUp.PowerUpState.FALLING);
    }

    public void reset() {
        for (PowerUp powerUp : powerUps)
            if (powerUp.getPowerUpState() == PowerUp.PowerUpState.ACTIVE)
                removeEffect(powerUp);
        powerUps.clear();
        brickPowerUpMap.clear();
        for (Brick brick : game.getBricks())
            generatePowerUps(brick, game.getLevelConfig().powerUpDropchance());
    }

    private void init() {
        for (Brick brick : game.getBricks())
            generatePowerUps(brick, game.getLevelConfig().powerUpDropchance());
    }

    private void generatePowerUps(Brick brick, HashMap<PowerUp.PowerUpType, Double> powerUpWeigh) {
        double chance = random.nextDouble() * 100;
        for (Map.Entry<PowerUp.PowerUpType, Double> entry : powerUpWeigh.entrySet()) {
            chance -= entry.getValue();
            if (chance < 0) {
                PowerUp.PowerUpType type = entry.getKey();
                Color color = entry.getKey().getColor();
                PowerUp pUp = new PowerUp(brick.getX() + brick.getWidth() / 2, brick.getY() + brick.getHeight() / 2, color, fallingSpeed, type, powerUpDuration, powerUpSize);
                powerUps.add(pUp);
                brickPowerUpMap.put(brick, pUp);
                break;
            }
        }
    }

    public void applyEffect(PowerUp powerUp) {
        if (powerUp.getPowerUpState() == PowerUp.PowerUpState.FALLING) {
            if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_PADDLE) {
                game.getPaddle().setWidth(Math.min(game.getPaddle().getWidth() + paddleSizeIncrease, game.getPlayfield().right() - game.getPlayfield().left()));
                game.getPaddle().setX(Math.max((game.getPaddle().getX() - paddleSizeIncrease / 2), game.getPlayfield().left()));
                if (game.getPaddle().getX() + game.getPaddle().getWidth() > game.getPlayfield().right())
                    game.getPaddle().setX(game.getPlayfield().right() - game.getPaddle().getWidth());
            }
            else if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_BALL) {
                for (Ball ball : game.getBalls())
                    ball.setRadius(ball.getRadius() + game.getBallStartingRadius());
            }
            else if (powerUp.getPowerUpType() == PowerUp.PowerUpType.ANOTHER_BALL) {
                game.getBalls().add(new Ball(game.getBalls().getFirst().getX(),game.getBalls().getFirst().getY(), -game.getBalls().getFirst().getVx(), game.getBalls().getFirst().getVy(), game.getBalls().getFirst().getRadius(), Color.WHITE));
            }
            else if (powerUp.getPowerUpType() == PowerUp.PowerUpType.STICKY_PADDLE) {
                if (stickyPaddle) {
                    powerUps.stream()
                            .filter(pUp -> pUp != powerUp)
                            .filter(pUp -> pUp.getPowerUpType() == PowerUp.PowerUpType.STICKY_PADDLE)
                            .filter(pUp -> pUp.getPowerUpState() == PowerUp.PowerUpState.ACTIVE)
                            .forEach(pUp -> pUp.setMaxDuration(powerUp.getMaxDuration() + pUp.getMaxDuration()));
                    powerUp.setPowerUpState(PowerUp.PowerUpState.REMOVED);
                }
                else
                    stickyPaddle = true;
            }
            if (powerUp.getPowerUpState() != PowerUp.PowerUpState.REMOVED)
                powerUp.setPowerUpState(PowerUp.PowerUpState.ACTIVE);
        }
    }

    public void removeEffect(PowerUp powerUp) {
        if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_PADDLE) {
            game.getPaddle().setX(game.getPaddle().getX() + paddleSizeIncrease / 2);
            game.getPaddle().setWidth(game.getPaddle().getWidth() - paddleSizeIncrease);
        }
        if (powerUp.getPowerUpType() == PowerUp.PowerUpType.BIGGER_BALL) {
            for (Ball ball : game.getBalls())
                ball.setRadius(ball.getRadius() - game.getBallStartingRadius());
        }
        if (powerUp.getPowerUpType() == PowerUp.PowerUpType.STICKY_PADDLE)
            stickyPaddle = false;
        powerUp.setPowerUpState(PowerUp.PowerUpState.REMOVED);
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }

    public boolean isStickyPaddle() {
        return stickyPaddle;
    }
}
