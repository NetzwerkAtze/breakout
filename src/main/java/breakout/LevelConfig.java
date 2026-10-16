package breakout;

import java.util.List;

public record LevelConfig(String name, int level, List<String> levelLayout, int maxCol) {
}
