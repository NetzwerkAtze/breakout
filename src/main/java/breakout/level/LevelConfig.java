package breakout.level;


import java.util.List;

public record LevelConfig(LevelId id, List<String> layout, World world) {
}
