package breakout;

import breakout.gameObject.Ball;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
/**
 *
 */
public class InputHandler {
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private Pane root;
    private Game game;
    private boolean reset = false;
    private boolean nextLevel = false;

    public InputHandler(Pane root, Game game) {
        this.root = root;
        this.game = game;
        init();
    }
    private void init() {
        root.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft = true;
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight = true;
            } else if (event.getCode() == KeyCode.SPACE) {
                game.setIdle(false);
                for (Ball ball : game.getStickMap().keySet())
                    game.getStickMap().put(ball, false);
            } else if (event.getCode() == KeyCode.R) {
                reset = true;
            } else if (event.getCode() == KeyCode.ENTER) {
                nextLevel = true;
            }
        });
        root.getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft = false;
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight = false;
            } else if (event.getCode() == KeyCode.R) {
                reset = false;
            } else if (event.getCode() == KeyCode.ENTER) {
                nextLevel = false;
            }
        });
    }
    public void update() {
        if (moveRight || moveLeft) {
            game.getPaddle().move(moveLeft);
            for (Ball ball : game.getStickMap().keySet()) {
                if (game.getStickMap().get(ball)) {
                    if (moveLeft)
                        ball.setX(ball.getX() - game.getPaddle().getVx());
                    else
                        ball.setX(ball.getX() + game.getPaddle().getVx());
                }
            }
        }
    }
    public boolean isReset() {
        return reset;
    }
    public boolean isNextLevel() {
        return nextLevel;
    }
}
