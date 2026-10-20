package breakout;

import java.util.ArrayList;
import java.util.List;

public class LevelRepository {

    private final List<LevelConfig> levels = new ArrayList<>();

    public LevelRepository() {
        levels.add(new LevelConfig(1, List.of(
                "..............",
                "....111111....",
                "..1111111111..",
                "11111111111111"
        ), 14));
        levels.add(new LevelConfig(2, List.of(
                "...",
                ".1.",
                "222"
        ), 3));
        levels.add(new LevelConfig(3, List.of(
                "....111111....",
                "..1111111111..",
                ".............."
        ), 14));
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
