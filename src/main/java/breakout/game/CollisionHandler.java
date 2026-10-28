package breakout.game;

import breakout.gameObject.Ball;
import breakout.gameObject.GameObject;

public class CollisionHandler {

    public CollisionHandler() {
    }
    public void resolveBallCollision(Ball ball, GameObject other, Runnable onStraightHit) {
        if (ball.hitsEdge(other)) {
            ball.resolveCollision(other, Ball.collisionPoint.EDGE);
            if (((ball.getCenterY() < other.getY() + other.getHeight() / 2) && ball.getVy() > 0) || (ball.getCenterY() > other.getY() + other.getHeight() / 2) && ball.getVy() <= 0)
                ball.setVy(-ball.getVy());
            if (((ball.getCenterX() < other.getX() + other.getWidth() / 2) && ball.getVx() > 0) || (ball.getCenterX() > other.getX() + other.getWidth() / 2) && ball.getVx() <= 0)
                ball.setVx(-ball.getVx());
        } else if (ball.hitsOnY(other)) {
            ball.resolveCollision(other, Ball.collisionPoint.Y_AXIS);
            onStraightHit.run();
        } else {
            ball.resolveCollision(other, Ball.collisionPoint.X_AXIS);
            if (((ball.getCenterX() < other.getX() + other.getWidth() / 2) && ball.getVx() > 0) || (ball.getCenterX() > other.getX() + other.getWidth() / 2) && ball.getVx() <= 0)
               ball.setVx(-ball.getVx());
        }
    }
}
