package breakout;

import breakout.gameObject.PowerUp;

import java.util.Map;

public record World(double fieldWith, int maxCol, double brickWidth, double brickHeight, Map<PowerUp.PowerUpType, Double> powerUpDropChance) {
}
