package breakout.level;

public record LevelId(int world, int Level) {

    public LevelId withWorld(int newWorld) {
        return new LevelId(newWorld, this.Level);
    }

    public LevelId withLevel(int newLevel) {
        return new LevelId(this.world, newLevel);
    }
}
