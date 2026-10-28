package breakout;

import breakout.gameObject.PowerUp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelRepository {
    private final double defaultBrickWidth = 60;
    private final double defaultBrickHeight = 20;

    private final double smallPlayfieldWidth = 550;
    private final int smallPlayfieldMaxCol = 7;

    private final double maximumPlayfieldWidth = 900;
    private final int maximumPlayfieldMaxCol = 14;

    private final Map<PowerUp.PowerUpType, Double> firstWorldPowerUpDropChance = new HashMap<>(Map.of(PowerUp.PowerUpType.BIGGER_PADDLE, 20.));
    private final World firstWorld = new World(smallPlayfieldWidth, smallPlayfieldMaxCol, defaultBrickWidth, defaultBrickHeight, firstWorldPowerUpDropChance);
    private final List<LevelConfig> levels = new ArrayList<>();

    public LevelRepository() {
        levels.add(new LevelConfig(101, List.of(
                ".......",
                ".......",
                ".......",
                ".......",
                ".11111.",
                ".11111."
        ), firstWorld));
        levels.add(new LevelConfig(102, List.of(
                ".......",
                ".......",
                ".......",
                "...1...",
                "..111..",
                ".11111."
        ), firstWorld));
        levels.add(new LevelConfig(103, List.of(
                "1111111",
                ".11111.",
                "..111..",
                "...1...",
                "..111..",
                ".11111.",
                "2222222"
        ), firstWorld));
    }

    public List<LevelConfig> getLevels() {
        return levels;
    }

    public  LevelConfig getLevelByNumber(int world, int number) {
        for (LevelConfig level : levels) {
            if (level.id() == world * 100 + number)
                return level;
        }
        return null;
    }
}
