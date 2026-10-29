package breakout.level;

import breakout.gameObject.PowerUp;

import java.util.*;

public class LevelRepository {
    private final double defaultBrickWidth = 60;
    private final double defaultBrickHeight = 20;


    private final Map<PowerUp.PowerUpType, Double> firstWorldPowerUpDropChance = Map.of();
    private final Map<PowerUp.PowerUpType, Double> secondWorldPowerUpDropChance = Map.of(PowerUp.PowerUpType.ANOTHER_BALL, 5.);
    private final List<LevelConfig> levels = new ArrayList<>();
    private final List<World> worlds = new ArrayList<>();

    public LevelRepository() {
        World firstWorld = new World(defaultBrickWidth, defaultBrickHeight, firstWorldPowerUpDropChance);
        World secondWorld = new World(defaultBrickWidth, defaultBrickHeight, secondWorldPowerUpDropChance );

        // first world
        levels.add(new LevelConfig(new LevelId(1,1), List.of(
                ".11111.",
                ".......",
                ".......",
                ".......",
                ".11111.",
                ".11111."
        ), firstWorld));
        levels.add(new LevelConfig(new LevelId(1,2), List.of(
                ".......",
                ".......",
                ".......",
                "...1...",
                "..111..",
                ".11111."
        ), firstWorld));
        levels.add(new LevelConfig(new LevelId(1,3), List.of(
                "1111111",
                ".11111.",
                "..111..",
                "...1...",
                "..111..",
                ".11111.",
                "1111111"
        ), firstWorld));

        //second World
        levels.add(new LevelConfig(new LevelId(2,1), List.of(
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
        ), secondWorld));
        levels.add(new LevelConfig(new LevelId(2,1), List.of(
                "...........",
                "...........",
                ".1.1.1.1.1.",
                "1.1.1.1.1.1",
                ".1.1.1.1.1.",
                "1.1.1.1.1.1"
        ), secondWorld));
    }

    public List<LevelConfig> getLevels() {
        return levels;
    }

    public List<World> getWorlds() {
        return worlds;
    }

    public  LevelConfig getLevelByNumber(int world, int number) {
        for (LevelConfig level : levels) {
            if (level.id().world() == world && level.id().Level() == number)
                return level;
        }
        return null;
    }
}
