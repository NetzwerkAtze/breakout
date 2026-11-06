package breakout.level;

import breakout.gameObject.PowerUp;

import java.util.*;

public class LevelRepository {
    private final double defaultBrickWidth = 60;
    private final double defaultBrickHeight = 20;


    private final Map<PowerUp.PowerUpType, Double> firstWorldPowerUpDropChance = Map.of();
    private final Map<PowerUp.PowerUpType, Double> secondWorldPowerUpDropChance = Map.of();
    private final Map<PowerUp.PowerUpType, Double> thirdWorldPowerUpDropChance = Map.of();
    private final List<List<LevelConfig>> levels = new ArrayList<>();

    public LevelRepository() {
        World firstWorld = new World(defaultBrickWidth, defaultBrickHeight, firstWorldPowerUpDropChance);
        World secondWorld = new World(defaultBrickWidth, defaultBrickHeight, secondWorldPowerUpDropChance );

        // first world
        levels.add(List.of(
                new LevelConfig(new LevelId(1,1), List.of(
                        ".......",
                        ".......",
                        ".......",
                        ".......",
                        ".......",
                        "...1...",
                        "..111..",
                        ".11111.",
                        "1111111"
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
                        ".......",
                        ".......",
                        "1.....1",
                        "11...11",
                        "111.111",
                        "1111111"
                ), firstWorld),
                new LevelConfig(new LevelId(1,4), List.of(
                        ".......",
                        ".......",
                        "1111111",
                        ".....1.",
                        "....1..",
                        "...1...",
                        "..1....",
                        ".1.....",
                        "1111111"
                ), firstWorld),
                new LevelConfig(new LevelId(1,5), List.of(
                        ".......",
                        ".......",
                        "1111111",
                        "..111..",
                        ".11.11.",
                        "1.....1",
                        "11...11",
                        ".11.11.",
                        "..111.."
                ), firstWorld),
                new LevelConfig(new LevelId(1,6), List.of(
                        ".......",
                        ".......",
                        "1111111",
                        "11...11",
                        "1.1.1.1",
                        "1..1..1",
                        "1.1.1.1",
                        "11...11",
                        "1111111"
                ), firstWorld),
                new LevelConfig(new LevelId(1,7), List.of(
                        ".......",
                        ".......",
                        ".......",
                        ".......",
                        ".......",
                        "1111111",
                        "1111111",
                        "1111111",
                        "1111111"
                ), firstWorld),
                new LevelConfig(new LevelId(1,8), List.of(
                        ".......",
                        ".......",
                        "1111111",
                        ".11111.",
                        "..111..",
                        "...1...",
                        "..111..",
                        ".11111.",
                        "1111111"
                ), firstWorld)
        ));

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
                new LevelConfig(new LevelId(2,2), List.of(
                        "...........",
                        "...........",
                        "...........",
                        "...........",
                        "..1111111..",
                        ".111111111.",
                        "111..3..111",
                        "111..3..111",
                        ".111111111.",
                        "...11111...",
                        "...2.2.2..."
                ), secondWorld),
                new LevelConfig(new LevelId(2,3), List.of(
                        "...........",
                        "...........",
                        "....111....",
                        "..11...11..",
                        ".11.....11.",
                        "1....1....1",
                        "....333....",
                        "...1...1...",
                        "..1.....1..",
                        ".1.......1.",
                        "22222222222"
                ), secondWorld),
                new LevelConfig(new LevelId(2,4), List.of(
                        "...........",
                        "...........",
                        "...........",
                        "...........",
                        "22222222222",
                        "1.........1",
                        "1..2...2..1",
                        "1..2...2..1",
                        "1..2...2..1",
                        "1..2...2..1",
                        "2222...2222"
                ), secondWorld),
                new LevelConfig(new LevelId(2,5), List.of(
                        "...........",
                        "...........",
                        "...........",
                        "...22222...",
                        "...22322...",
                        "...........",
                        "...........",
                        "11111.11111",
                        "11111.11111",
                        "...........",
                        "..........."
                ), secondWorld),
                new LevelConfig(new LevelId(2,6), List.of(
                        "...........",
                        "...........",
                        "...........",
                        "...........",
                        "...333.....",
                        "...111.....",
                        "...111...22",
                        "22.111...11",
                        "11.111.2.11",
                        "11.111.1.11",
                        "11.111.1.11"
                ), secondWorld),
                new LevelConfig(new LevelId(2,7), List.of(
                        "...........",
                        "...........",
                        "222222222..",
                        "...........",
                        "...........",
                        "..222222222",
                        "...........",
                        "...........",
                        "222222222..",
                        "...........",
                        "..........."
                ), secondWorld),
                new LevelConfig(new LevelId(2,8), List.of(
                        ".....1.....",
                        "....1.1....",
                        "...1...1...",
                        "..1..1..1..",
                        ".1..121..1.",
                        "1..12421..1",
                        ".1..121..1.",
                        "..1..1..1..",
                        "...1...1...",
                        "....1.1....",
                        ".....1....."
                ), secondWorld)
        ));
        //third World
        levels.add(List.of(
                new LevelConfig(new LevelId(3,1), List.of(
                        "...........",
                        "...........",
                        "...11111...",
                        ".11.111.11.",
                        ".1.1...1.1.",
                        "11.1...1.11",
                        "11.1...1.11",
                        "11.1...1.11",
                        "...........",
                        "...........",
                        "..........."
                ), secondWorld),
                new LevelConfig(new LevelId(3,2), List.of(
                        "...........",
                        "11111111111",
                        "1.1.1.1.1.2",
                        "2.1.1.1.1..",
                        "..1.1.2.1..",
                        "..2.1...1..",
                        "....1...2..",
                        "....2......",
                        "...........",
                        "...........",
                        "..........."
                ), secondWorld),
                new LevelConfig(new LevelId(3,3), List.of(
                        "...........",
                        "...........",
                        "11.......11",
                        "11.......11",
                        "...........",
                        "....222....",
                        "....232....",
                        "....222....",
                        "...........",
                        "11.......11",
                        "11.......11"
                ), secondWorld),
                new LevelConfig(new LevelId(3,4), List.of(
                        "...........",
                        "...........",
                        ".111111111.",
                        ".1.......1.",
                        ".1.22222.1.",
                        ".1.2...2.1.",
                        ".1.2.3.2.1.",
                        ".1.2...2.1.",
                        ".1.22.22.1.",
                        ".1.......1.",
                        ".1111.1111."
                ), secondWorld),
                new LevelConfig(new LevelId(3,5), List.of(
                        "...........",
                        "...........",
                        "11..22..11.",
                        ".11..22..11",
                        "..11..22..1",
                        "...11..22..",
                        "....11..22.",
                        ".....11..22",
                        "......11..2",
                        "...........",
                        "..........."
                ), secondWorld),
                new LevelConfig(new LevelId(3,6), List.of(
                        "...........",
                        "...........",
                        ".2.2.2.2.2.",
                        "...........",
                        "..1.1.1.1..",
                        "...........",
                        ".1.1.3.1.1.",
                        "...........",
                        "..1.1.1.1..",
                        "...........",
                        "..........."
                ), secondWorld),
                new LevelConfig(new LevelId(3,7), List.of(
                        "...........",
                        ".....1.....",
                        "....1.1....",
                        "...1.2.1...",
                        "..1.2.2.1..",
                        ".1.2.3.2.1.",
                        "..1.2.2.1..",
                        "...1.2.1...",
                        "....1.1....",
                        ".....1.....",
                        "..........."
                ), secondWorld),
                new LevelConfig(new LevelId(3,8), List.of(
                        "...........",
                        "...........",
                        "11.......11",
                        ".11..3..11.",
                        "..11...11..",
                        "...11.11...",
                        "....111....",
                        ".....2.....",
                        ".....2.....",
                        "..2222222..",
                        "..........."
                ), secondWorld)
        ));
        //fourth World
        levels.add(List.of(
                new LevelConfig(new LevelId(4,1), List.of(
                        "...........",
                        "...........",
                        "...........",
                        "...........",
                        "..........3",
                        ".........33",
                        "........233",
                        ".......2233",
                        "......22233",
                        ".....122233",
                        "....1122234"
                ), secondWorld),
                new LevelConfig(new LevelId(4,2), List.of(
                        "...........",
                        "...........",
                        "...........",
                        ".11.....11.",
                        "..11...11..",
                        "...22.22...",
                        "....232....",
                        "....232....",
                        "...22.22...",
                        "..11...11..",
                        ".11.....11."
                ), secondWorld),
                new LevelConfig(new LevelId(4,3), List.of(
                        "...........",
                        "...........",
                        "..2222222..",
                        "..1.1.1.1..",
                        "..1.1.1.1..",
                        "..1.141.1..",
                        "..1.1.1.1..",
                        "..1.1.1.1..",
                        "..222.222..",
                        "...........",
                        "..........."
                ), firstWorld),
                new LevelConfig(new LevelId(4,4), List.of(
                        "...........",
                        "...........",
                        ".22..22....",
                        "23.22.42...",
                        ".22..22....",
                        "...22..22..",
                        "..24.22.32.",
                        "...22..22..",
                        "...........",
                        "...........",
                        "..........."
                ), firstWorld),
                new LevelConfig(new LevelId(4,5), List.of(
                        "...........",
                        "...........",
                        "...........",
                        "22222......",
                        "2...2......",
                        "2.4.2......",
                        "22.22.22222",
                        "..........2",
                        "......2.4.2",
                        "......22222",
                        "..........."
                ), firstWorld),
                new LevelConfig(new LevelId(4,6), List.of(
                        "...........",
                        "...........",
                        "...........",
                        "..12.1111..",
                        "..12.1111..",
                        "..12..211..",
                        "..111.211..",
                        "..111.211..",
                        "..112.211..",
                        "...........",
                        "..........."
                ), firstWorld),
                new LevelConfig(new LevelId(4,7), List.of(
                        "...........",
                        "...........",
                        "....1.1....",
                        ".111.2.111.",
                        "1221.2.1221",
                        ".111.2.111.",
                        "..11.3.11..",
                        "..11.2.11..",
                        "...1.2.1...",
                        ".....1.....",
                        "..........."
                ), firstWorld),
                new LevelConfig(new LevelId(4,8), List.of(
                        "...........",
                        "...........",
                        "..222.222..",
                        ".2...2...2.",
                        "2.........2",
                        "2....4....2",
                        ".2...3...2.",
                        "..2.....2..",
                        "...2...2...",
                        "....2.2....",
                        ".....3....."
                ), firstWorld)
        ));
    }

    public List<List<LevelConfig>> getWorlds() {
        return levels;
    }
    public List<LevelConfig> getWorld(int world) {
        return levels.get(world - 1);
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
