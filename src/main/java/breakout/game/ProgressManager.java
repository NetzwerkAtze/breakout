package breakout.game;

import breakout.level.LevelId;
import breakout.level.LevelRepository;

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
    public int getHighestUnlockedLevelFromWorld(int world) {
        int level = 0;
        for (LevelId id : unlockedLevel) {
            if (id.world() == world) {
                if (id.Level() > level)
                    level = id.Level();
            }
        }
        return level;
    }
    public int getHighestUnlockedWorld() {
        int highestWorld = 1;
        for (LevelId id : unlockedLevel) {
            if (id.world() > highestWorld)
                highestWorld = id.world();
        }
        return highestWorld;
    }

    /**
     * debug tool
     */
    public void unlockAll(LevelRepository repository) {
        for (int world = 1 ; world <= repository.getWorlds().size(); world++) {
            for (int level = 1; level <= repository.getWorld(world).size(); level++) {
                unlockedLevel.add(new LevelId(world, level));
            }
        }
    }
}
