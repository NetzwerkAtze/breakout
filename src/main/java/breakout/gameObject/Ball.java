package breakout.gameObject;

import javafx.scene.paint.Color;

/**
 * vx describes velocity on x axis.
 * vy describes velocity on y axis.
 */
public class Ball extends GameObject {

    /**
     * ball radius
     */
    private int radius;
    /**
     * velocity of the paddle on the x-axis per frame
     */
    private double vx;
    /**
     * velocity of the paddle on the y-axis per frame
     */
    private double vy;

    private double speed;

    public Ball(double x, double y, double vx, double vy, int radius, Color color) {
        super(x, y, 2 * radius, 2 * radius, color);
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.speed = Math.sqrt(vx * vx + vy * vy);
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

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getCenterX() {
        return x + radius;
    }

    public double getCenterY() {
        return y + radius;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean hitsOnY(GameObject other) {
        double overlapX = Math.min(x + width, other.x + other.width) - Math.max(x, other.x);
        double overlapY = Math.min(y + height, other.y + other.height) - Math.max(y, other.y);
        return overlapX > overlapY;
    }

    public boolean hitsEdge(GameObject other) {
        double overlapX = Math.min(x + width, other.x + other.width) - Math.max(x, other.x);
        double overlapY = Math.min(y + height, other.y + other.height) - Math.max(y, other.y);
        return Math.abs(overlapX - overlapY) < 0.001;
    }
    public double getRadians(Paddle paddle, int maxAngle) {
        double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
        double ballCenterX = x + radius;
        double ballPaddleDistance =  ballCenterX - paddleCenterX;
        double ratio = ballPaddleDistance / (paddle.getWidth() / 2.0);
        double angle = ratio * maxAngle;
        return Math.toRadians(angle);
    }
}