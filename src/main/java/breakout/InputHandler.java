package breakout;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class InputHandler {
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private Pane root;
    private Game game;

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
            }
        });
        root.getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                moveLeft = false;
            } else if (event.getCode() == KeyCode.RIGHT) {
                moveRight = false;
            }
        });
    }
    public void update() {
        if (moveRight || moveLeft)
            game.getPaddle().move(moveLeft);
    }
}
