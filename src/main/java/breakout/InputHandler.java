package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
/**
 *
 */
public class InputHandler {
    private Pane root;
    private boolean reset = false;
    private boolean enterConfirmed = false;
    private boolean exit = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean spaceBarPressed = false;
    private boolean pausedPressed = false;
    private boolean skipToNextLevelPressed = false;

    public InputHandler(Pane root) {
        this.root = root;
        init();
    }
    private void init() {
        root.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                movingLeft = true;
            } else if (event.getCode() == KeyCode.RIGHT) {
                movingRight = true;
            } else if (event.getCode() == KeyCode.SPACE) {
                spaceBarPressed = true;
            } else if (event.getCode() == KeyCode.R) {
                reset = true;
            } else if (event.getCode() == KeyCode.ENTER) {
                enterConfirmed = true;
            } else if (event.getCode() == KeyCode.P) {
                pausedPressed = true;
            } else if (event.getCode() == KeyCode.ESCAPE) {
                exit = true;
            } else if (event.getCode() == KeyCode.Q) {    // debug button
                skipToNextLevelPressed = true;
            }
        });
        root.getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                movingLeft = false;
            } else if (event.getCode() == KeyCode.RIGHT) {
                movingRight = false;
            } else if (event.getCode() == KeyCode.SPACE) {
                    spaceBarPressed = false;
            } else if (event.getCode() == KeyCode.R) {
                reset = false;
            } else if (event.getCode() == KeyCode.ENTER) {
                enterConfirmed = false;
            } else if (event.getCode() == KeyCode.P) {
                pausedPressed = false;
            } else if (event.getCode() == KeyCode.ESCAPE) {
                exit = false;
            }
        });
    }
    public boolean isReset() {
        return reset;
    }
    public boolean isEnterConfirmed() {
        return enterConfirmed;
    }
    public boolean isExit() {
        return exit;
    }
    public boolean isMovingLeft() {
        return movingLeft;
    }
    public boolean isMovingRight() {
        return movingRight;
    }
    public boolean isSpaceBarPressed() {
        return spaceBarPressed;
    }
    public boolean isPausedPressed() {
        return pausedPressed;
    }
    public boolean isSkipToNextLevelPressed() {
        return skipToNextLevelPressed;
    }
    public void resetPausedPress(){
        pausedPressed = false;
    }
    public void resetSkipToNextLevel() {
        skipToNextLevelPressed = false;
    }
}
