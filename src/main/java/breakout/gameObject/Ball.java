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
    private double radius;
    /**
     * velocity of the paddle on the x-axis per frame
     */
    private double vx;
    /**
     * velocity of the paddle on the y-axis per frame
     */
    private double vy;
    private double speed;
    private BallState ballState;
    private double stickOffset;

    public enum BallState {
        FREE,
        STICKING,
    }
    public enum collisionPoint {
        EDGE,
        X_AXIS,
        Y_AXIS
    }

    public Ball(double x, double y, double vx, double vy, double radius, Color color) {
        super(x, y, 2 * radius, 2 * radius, color);
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.speed = Math.sqrt(vx * vx + vy * vy);
        this.ballState = BallState.FREE;
    }

    /**
     * updates the position (x and y) of the ball based on the velocity.
     */
    public void update(Paddle paddle) {
        if (ballState == BallState.FREE) {
            x += vx;
            y += vy;
        }
        else if (ballState == BallState.STICKING) {
            x = paddle.getX() - stickOffset;
        }
    }

    public void setStickOffset(double stickOffset) {
        this.stickOffset = stickOffset;
    }

    public BallState getBallState() {
        return ballState;
    }

    public void setBallState(BallState ballState) {
        this.ballState = ballState;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        width = 2 * radius;
        height = 2 * radius;
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
        return Math.abs(overlapX - overlapY) < 0.1;
    }

    public double getRadians(Paddle paddle, int maxAngle) {
        double paddleCenterX = paddle.getX() + paddle.getWidth() / 2;
        double ballCenterX = x + radius;
        double ballPaddleDistance =  ballCenterX - paddleCenterX;
        double ratio = ballPaddleDistance / (paddle.getWidth() / 2.0);
        double angle = ratio * maxAngle;
        return Math.toRadians(angle);
    }

    public double getOverlapX(GameObject other) {
        return Math.min(x + width, other.x + other.width) - Math.max(x, other.x);
    }

    public double getOverlapY(GameObject other) {
        return Math.min(y + height, other.y + other.height) - Math.max(y, other.y);
    }
    /**
     * pushes the ball out of the object by the overlapping factor.
     *
     */
    public void resolveCollision(GameObject other, collisionPoint collisionPoint) {
        switch (collisionPoint) {
            case EDGE -> {
                if (x + (width / 2) < other.getX() + (other.getWidth() /2)) {
                    if (y + (height / 2) < other.getY() + (other.getHeight() / 2)) {  // top left corner
                        x = x - getOverlapX(other);
                        y = y - getOverlapY(other);
                    } else {  // bottom left corner
                        x = x - getOverlapX(other);
                        y = y + getOverlapY(other);
                    }
                } else {
                    if (y + (height / 2) > other.getY() + (other.getHeight() / 2)) { // bottom right corner
                        x = x + getOverlapX(other);
                        y = y + getOverlapY(other);
                    } else {  // top right corner
                        x = x + getOverlapX(other);
                        y = y - getOverlapY(other);
                    }
                }
            }
            case Y_AXIS -> {
                if (y + (height / 2) < other.getY() + (other.getHeight() / 2))
                    y = y - getOverlapY(other);
                else
                    y = y + getOverlapY(other);
        }
            case X_AXIS -> {
                if (x + (width / 2) < other.getX() + (other.getWidth() / 2))
                    x = x - getOverlapX(other);
                else
                    x = x + getOverlapX(other);
            }
        }
    }
}