package breakout;

import breakout.gameObject.Ball;
import breakout.gameObject.GameObject;

public class CollisionHandler {
    private Game game;

    public CollisionHandler(Game game) {
        this.game = game;
    }
    public void resolveBallCollision(Ball ball, GameObject other, boolean isPaddle, Runnable onStraightHit) {
        if (game.getPowerUpHandler().isStickyPaddle() && isPaddle) {
            if (ball.getBallState() == Ball.BallState.FREE) {
                ball.setStickOffset(other.getX() - ball.getX());
                ball.setBallState(Ball.BallState.STICKING);
            }
        }
        if (ball.hitsEdge(other)) {
            ball.resolveCollision(other, true, false);
            if (((ball.getCenterY() < other.getY() + other.getHeight() / 2) && ball.getVy() > 0) || (ball.getCenterY() > other.getY() + other.getHeight() / 2) && ball.getVy() <= 0)
                ball.setVy(-ball.getVy());
            if (((ball.getCenterX() < other.getX() + other.getWidth() / 2) && ball.getVx() > 0) || (ball.getCenterX() > other.getX() + other.getWidth() / 2) && ball.getVx() <= 0)
                ball.setVx(-ball.getVx());
        } else if (ball.hitsOnY(other)) {
            ball.resolveCollision(other, false, true);
            onStraightHit.run();
        } else {
            ball.resolveCollision(other, false, false);
            if (((ball.getCenterX() < other.getX() + other.getWidth() / 2) && ball.getVx() > 0) || (ball.getCenterX() > other.getX() + other.getWidth() / 2) && ball.getVx() <= 0)
               ball.setVx(-ball.getVx());
        }
    }
}
