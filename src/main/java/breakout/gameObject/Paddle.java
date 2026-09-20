package breakout.gameObject;

import javafx.scene.paint.Color;

public class Paddle extends GameObject {
    /** right maximum border of the map. */
    private double maxX;
    /** velocity of the paddle on the x-axis per frame */
    private double vx;

    /**
     *
     * @param x
     * @param y
     * @param vx velocity for x-axis movement
     * @param width
     * @param height
     * @param color
     * @param maxX
     */
    public Paddle(double x, double y, double vx, double width, double height, Color color, double maxX) {
        super(x, y, width, height, color);
        this.vx = vx;
        this.maxX = maxX;
    }

    /**
     * method to move the paddle left and right on the x axis.
     * @param isLeft boolean true, if the paddle is moving left
     */
    public void move(boolean isLeft) {
        if (isLeft && x > 0)
            x -= vx;
        else if (!isLeft && x < maxX - width)
            x += vx;
    }
}
