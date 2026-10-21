package breakout;

import java.util.List;

public record LevelConfig(int level, List<String> levelLayout, int maxCol, double mapWidth, double brickWidth, double brickHeight) {

}
