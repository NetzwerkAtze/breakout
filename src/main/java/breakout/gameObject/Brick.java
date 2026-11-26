package breakout.gameObject;

import javafx.scene.paint.Color;

public class Brick extends GameObject {

    private int maxHits;
    private int hitsRemaining;
    private boolean burst = false;

    public Brick (double x, double y, double width, double height, Color color, int maxHits) {
        super(x, y, width, height, color);
        this.maxHits = maxHits;
        hitsRemaining = maxHits;
    }
    public void hit() {
        hitsRemaining = Math.max(0, hitsRemaining - 1);
    }

    public int getHitsRemaining() {
        return hitsRemaining;
    }

    public int getMaxHits() {
        return maxHits;
    }

    public void repair() {
        hitsRemaining = maxHits;
        burst = false;
    }

    public boolean isDestroyed(){
        return hitsRemaining <= 0;
    }

    public boolean isBurst() {
        return burst;
    }

    public void setBurst(boolean burst) {
        this.burst = burst;
    }
}
