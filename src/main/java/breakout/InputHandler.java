package breakout;

import breakout.gameObject.Ball;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
/**
 *
 */
public class InputHandler {
    private Pane root;
    private Game game;
    private boolean reset;
    private boolean nextLevel;

    public InputHandler(Pane root, Game game) {
        this.root = root;
        this.game = game;
        reset = false;
        nextLevel = false;
        init();
    }
    private void init() {
        root.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                game.setMoveLeft(true);
            } else if (event.getCode() == KeyCode.RIGHT) {
                game.setMoveRight(true);
            } else if (event.getCode() == KeyCode.SPACE) {
                game.setIdle(false);
                game.setReleaseBalls(true);
            } else if (event.getCode() == KeyCode.R) {
                reset = true;
            } else if (event.getCode() == KeyCode.ENTER) {
                nextLevel = true;
            }
        });
        root.getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                game.setMoveLeft(false);
            } else if (event.getCode() == KeyCode.RIGHT) {
                game.setMoveRight(false);
            } else if (event.getCode() == KeyCode.SPACE) {
                    game.setReleaseBalls(false);
            } else if (event.getCode() == KeyCode.R) {
                reset = false;
            } else if (event.getCode() == KeyCode.ENTER) {
                nextLevel = false;
            }
        });
    }
    public boolean isReset() {
        return reset;
    }
    public boolean isNextLevel() {
        return nextLevel;
    }
}
