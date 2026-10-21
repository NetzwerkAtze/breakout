package breakout;

import java.util.ArrayList;
import java.util.List;

public class LevelRepository {
    private final double defaultBrickWidth = 60;
    private final double defaultBrickHeight = 20;

    private final double smallPlayfieldWidth = 550;
    private final int smallPlayfieldMaxCol = 7;

    private final double maximumPlayfieldWidth = 900;
    private final int maximumPlayfieldMaxCol = 14;

    private final List<LevelConfig> levels = new ArrayList<>();

    public LevelRepository() {
        levels.add(new LevelConfig(1, List.of(
                ".......",
                ".......",
                ".......",
                ".......",
                ".11111.",
                ".11111."
        ), smallPlayfieldMaxCol, smallPlayfieldWidth, defaultBrickWidth, defaultBrickHeight));
        levels.add(new LevelConfig(2, List.of(
                ".......",
                ".......",
                ".......",
                "...1...",
                "..111..",
                ".11111."
        ), smallPlayfieldMaxCol, smallPlayfieldWidth, defaultBrickWidth, defaultBrickHeight));
        levels.add(new LevelConfig(3, List.of(
                "....111111....",
                "..1111111111..",
                ".............."
        ), 14,900, 60, 20));
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
