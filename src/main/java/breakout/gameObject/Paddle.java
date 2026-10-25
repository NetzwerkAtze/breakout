package breakout.gameObject;

import javafx.scene.paint.Color;

public class Paddle extends GameObject {
    /** right maximum border of the map. */
    private double maxX;
    /** velocity of the paddle on the x-axis per frame */
    private double vx;
    private double startingWidth;

    public Paddle(double x, double y, double vx, double width, double height, Color color, double maxX) {
        super(x, y, width, height, color);
        this.vx = vx;
        this.maxX = maxX;
        startingWidth = width;
    }

    /**
     * method to move the paddle left and right on the x-axis.
     * @param isLeft boolean true, if the paddle is moving left
     */
    public void move(boolean isLeft, double leftBorder, double rightBorder) {
        if (isLeft && x-vx > 0 + leftBorder)
            x -= vx;
        else if (!isLeft && x +vx  < rightBorder - width)
            x += vx;
    }

    public double getStartingWidth() {
        return startingWidth;
    }
}
