package breakout;

import breakout.gameObject.PowerUp;

import java.util.HashMap;
import java.util.List;

public record LevelConfig(int level, List<String> levelLayout, int maxCol, double mapWidth, double brickWidth, double brickHeight, HashMap<PowerUp.PowerUpType, Double> powerUpDropchance) {

}
