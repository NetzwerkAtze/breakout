package breakout.gameObject;

import javafx.scene.paint.Color;

public class Brick extends GameObject {
    /** if a block is still intact or got destroyed */
    private boolean isDestroyed;

    public Brick (int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        isDestroyed = false;
    }
    public void destroy() {
        isDestroyed = true;
    }
    public boolean isDestroyed(){
        return isDestroyed;
    }
}
