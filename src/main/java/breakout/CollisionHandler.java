package breakout;

import breakout.gameObject.Ball;
import breakout.gameObject.Brick;
import breakout.gameObject.GameObject;
import javafx.scene.Scene;

import java.util.Iterator;

public class CollisionHandler {
    private Scene scene;
    private Game game;

    public CollisionHandler(Scene scene, Game game) {
        this.scene = scene;
        this.game = game;
    }
    public void resolveBallCollision(Ball ball, GameObject other) {
        if (ball.hitsEdge(other)) {
            ball.resolveCollision(other, true, false);
            ball.setVy(-ball.getVy());
            ball.setVx(-ball.getVx());
        } else if (ball.hitsOnY(other)) {
            ball.resolveCollision(other, false, true);
            ball.setVy(-ball.getVy());
            if (other == game.getPaddle()) {
                ball.setVx(ball.getSpeed() * Math.sin(ball.getRadians(game.getPaddle(), game.getMaxAngle())));
                ball.setVy(-(ball.getSpeed() * Math.cos(ball.getRadians(game.getPaddle(), game.getMaxAngle()))));
            }
        } else {
            ball.resolveCollision(other, false, false);
            ball.setVx(-ball.getVx());
        }
    }
}
