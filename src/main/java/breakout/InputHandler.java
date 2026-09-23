package breakout;

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
        if (moveRight || moveLeft)
            game.getPaddle().move(moveLeft);

    }
    public boolean isReset() {
        return reset;
    }
    public boolean isNextLevel() {
        return nextLevel;
    }
}
