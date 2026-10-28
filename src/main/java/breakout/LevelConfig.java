package breakout;


import java.util.List;

public record LevelConfig(int id, List<String> layout, World world) {
}
