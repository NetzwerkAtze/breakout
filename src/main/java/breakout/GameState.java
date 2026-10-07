package breakout;

public class GameState {
    private int startingLives;
    private int lives;
    private boolean levelWon = false;
    private boolean idle = true;
    private boolean paused = false;
    private int score = 0;
    private  int level;

    public GameState(int startLives, int level) {
        this.startingLives = startLives;
        this.lives = startLives;
        this.level = level;
    }

    public void reset() {
        lives = startingLives;
        levelWon = false;
        score = 0;
        idle = true;
    }

    public void loseLife() {
        lives = Math.max(lives - 1, 0);
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public boolean isIdle() {
        return idle;
    }

    public int getScore() {
        return score;
    }
    public void increaseScore() {
        score++;
    }
    public void increaseScore(int score) {
        this.score += score;
    }

    public boolean isLevelWon() {
        return levelWon;
    }

    public void setLevelWon(boolean levelWon) {
        this.levelWon = levelWon;
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public int getLevel() {
        return level;
    }

    public int getLives() {
        return lives;
    }

    public boolean gameOver() {
        return lives == 0;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
