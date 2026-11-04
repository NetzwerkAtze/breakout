package breakout.game;

import breakout.level.LevelId;

import java.util.ArrayList;
import java.util.List;

public class ProgressManager {
    List<LevelId> unlockedLevel = new ArrayList<>();

    public ProgressManager() {
        unlockedLevel.add(new LevelId(1,1));
    }

    public void unlockLevel(LevelId id) {
        unlockedLevel.add(id);
    }

    public List<LevelId> getUnlockedLevel() {
        return unlockedLevel;
    }
}
