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

    private final HashMap<PowerUp.PowerUpType, Double> firstWorldPuPWeigh = new HashMap<>(Map.of(PowerUp.PowerUpType.BIGGER_PADDLE, 20.));

    private final List<LevelConfig> levels = new ArrayList<>();

    public LevelRepository() {
        levels.add(new LevelConfig(1, List.of(
                ".......",
                ".......",
                ".......",
                ".......",
                ".11111.",
                ".11111."
        ), smallPlayfieldMaxCol, smallPlayfieldWidth, defaultBrickWidth, defaultBrickHeight, firstWorldPuPWeigh));
        levels.add(new LevelConfig(2, List.of(
                ".......",
                ".......",
                ".......",
                "...1...",
                "..111..",
                ".11111."
        ), smallPlayfieldMaxCol, smallPlayfieldWidth, defaultBrickWidth, defaultBrickHeight, firstWorldPuPWeigh));
        levels.add(new LevelConfig(3, List.of(
                "1111111",
                ".11111.",
                "..111..",
                "...1...",
                "..111..",
                ".11111.",
                "1111111"
        ), smallPlayfieldMaxCol, smallPlayfieldWidth, defaultBrickWidth, defaultBrickHeight, firstWorldPuPWeigh));
    }

    public List<LevelConfig> getLevels() {
        return levels;
    }

    public  LevelConfig getLevelByNumber(int number) {
        for (LevelConfig level : levels) {
            if (level.level() == number)
                return level;
        }
        return null;
    }
}
