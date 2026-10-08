package breakout;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class HighscoreManager {
    private int highscore;
    private boolean newHighscore = false;

    public HighscoreManager() {
        init();
    }

    private void init() {
        Path highscorePath = Paths.get("highscore.txt");
        try {
            highscore = Integer.parseInt(Files.readString(highscorePath));
        } catch (IOException | NumberFormatException e) {
            highscore = 0;
        }
    }

    public void writeHighscore(int highscore) {
        try {
            Files.writeString(Path.of("highscore.txt"), Integer.toString(highscore), StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.highscore = highscore;
        newHighscore = true;
    }

    public int getHighscore() {
        return highscore;
    }

    public boolean isNewHighscore() {
        return newHighscore;
    }
}
