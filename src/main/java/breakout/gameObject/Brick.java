package breakout.gameObject;

import javafx.scene.paint.Color;

public class Brick extends GameObject {

    private int maxHits;
    private int hitsRemaining;

    public Brick (double x, double y, Color color) {
        super(x, y, 60, 20, color);
        maxHits = 1;
        hitsRemaining = maxHits;
    }

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
    }

    public boolean isDestroyed(){
        return hitsRemaining <= 0;
    }
}
