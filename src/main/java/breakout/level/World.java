package breakout.level;

import breakout.gameObject.PowerUp;

import java.util.Map;

public record World(double brickWidth, double brickHeight, Map<PowerUp.PowerUpType, Double> powerUpDropChance) {
    public World {
        powerUpDropChance = Map.copyOf(powerUpDropChance);
    }
}
