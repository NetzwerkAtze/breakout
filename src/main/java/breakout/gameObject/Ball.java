package breakout.gameObject;

import javafx.scene.paint.Color;

/**
 * vx describes velocity on x axis.
 * vy describes velocity on y axis.
 */
public class Ball extends GameObject {

    /** ball radius */
    private int radius;
    /** velocity of the paddle on the x-axis per frame */
    private int vx;
    /** velocity of the paddle on the y-axis per frame */
    private int vy;

    public Ball(int x, int y, int vx, int vy, int radius, Color color) {
        super(x, y, 2 * radius, 2 * radius, color);
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * updates the position (x and y) of the ball based on the velocity.
     */
    public void update() {
        x += vx;
        y += vy;
    }

    public int getRadius() {
        return radius;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }
    public int getCenterX() {
        return x + radius;
    }
    public int getCenterY() {
        return y + radius;
    }
    public boolean hitsOnY(GameObject other) {
        int overlapX = Math.min(x + width, other.x + other.width) - Math.max(x, other.x);
        int overlapY = Math.min(y + height, other.y + other.height) - Math.max(y, other.y);
        return overlapX > overlapY;
    }
}
