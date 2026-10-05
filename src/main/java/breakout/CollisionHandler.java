package breakout;

import breakout.gameObject.Ball;
import breakout.gameObject.GameObject;

public class CollisionHandler {
    private Game game;

    public CollisionHandler(Game game) {
        this.game = game;
    }
    public void resolveBallCollision(Ball ball, GameObject other, boolean isPaddle, Runnable onStraightHit) {
        if (game.getGameState().isSticky() && isPaddle) {
            if (ball.getBallState() == Ball.BallState.FREE) {
                ball.setStickOffset(other.getX() - ball.getX());
                ball.setBallState(Ball.BallState.STICKING);
            }
        }
        if (ball.hitsEdge(other)) {
            ball.resolveCollision(other, true, false);
            ball.setVy(-ball.getVy());
            ball.setVx(-ball.getVx());
        } else if (ball.hitsOnY(other)) {
            ball.resolveCollision(other, false, true);
            onStraightHit.run();
        } else {
            ball.resolveCollision(other, false, false);
            ball.setVx(-ball.getVx());
        }
    }
}
