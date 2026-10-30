package breakout.level;

import breakout.gameObject.PowerUp;

import java.util.*;
import java.util.logging.Level;

public class LevelRepository {
    private final double defaultBrickWidth = 60;
    private final double defaultBrickHeight = 20;


    private final Map<PowerUp.PowerUpType, Double> firstWorldPowerUpDropChance = Map.of();
    private final Map<PowerUp.PowerUpType, Double> secondWorldPowerUpDropChance = Map.of(PowerUp.PowerUpType.ANOTHER_BALL, 5.);
    private final List<List<LevelConfig>> levels = new ArrayList<>();
    // mache levels zu list of lists damit jede welt 1 list hat dann easier zugriff
    private final List<LevelId> levelIds = new ArrayList<>();
    private final List<World> worlds = new ArrayList<>();

    public LevelRepository() {
        World firstWorld = new World(defaultBrickWidth, defaultBrickHeight, firstWorldPowerUpDropChance);
        World secondWorld = new World(defaultBrickWidth, defaultBrickHeight, secondWorldPowerUpDropChance );

        // first world
        levels.add(List.of(
            new LevelConfig(new LevelId(1,1), List.of(
                ".......",
                ".......",
                ".......",
                ".....1.",
                "....11.",
                "...111.",
                "..1111.",
                ".11111."
            ), firstWorld),
            new LevelConfig(new LevelId(1,2), List.of(
                ".......",
                ".......",
                "1111111",
                ".......",
                ".......",
                "1111111",
                ".......",
                ".......",
                "1111111"
            ), firstWorld),
            new LevelConfig(new LevelId(1,3), List.of(
                ".......",
                ".......",
                ".......",
                "...1...",
                "..111..",
                ".11111."
            ), firstWorld),
            new LevelConfig(new LevelId(1,4), List.of(
                "1111111",
                ".11111.",
                "..111..",
                "...1...",
                "..111..",
                ".11111.",
                "1111111"
            ), firstWorld)));

        //second World
        levels.add(List.of(
                new LevelConfig(new LevelId(2,1), List.of(
                        "...........",
                "...........",
                "11111111111",
                "...........",
                "...........",
                "...........",
                "22222222222",
                "...........",
                "...........",
                "...........",
                "11111111111"
         ), secondWorld),
            new LevelConfig(new LevelId(2,1), List.of(
                "...........",
                "...........",
                ".1.1.1.1.1.",
                "1.1.1.1.1.1",
                ".1.1.1.1.1.",
                "1.1.1.1.1.1"
            ), secondWorld)));
    }

    public List<List<LevelConfig>> getLevels() {
        return levels;
    }

    public  LevelConfig getLevelById(LevelId id) {
        for (List<LevelConfig> list : levels) {
            for (LevelConfig conf : list) {
                if (conf.id().equals(id))
                    return conf;
            }
        }
        return null;
    }
}
