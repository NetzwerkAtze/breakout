package breakout;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 *
 */
public class InputHandler {
    private Pane root;
    private ArrayList<KeyCode> held = new ArrayList<>();
    private ArrayList<KeyCode> justPressed = new ArrayList<>();

    public InputHandler(Pane root) {
        this.root = root;
        init();
    }
    private void init() {
        root.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                addKey(KeyCode.LEFT);
            } else if (event.getCode() == KeyCode.RIGHT) {
                addKey(KeyCode.RIGHT);
            } else if (event.getCode() == KeyCode.SPACE) {
                addKey(KeyCode.SPACE);
            } else if (event.getCode() == KeyCode.R) {
                addKey(KeyCode.R);
            } else if (event.getCode() == KeyCode.ENTER) {
                addKey(KeyCode.ENTER);
            } else if (event.getCode() == KeyCode.P) {
                addKey(KeyCode.P);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                addKey(KeyCode.ESCAPE);
            } else if (event.getCode() == KeyCode.Q) {
                addKey(KeyCode.Q);// debug button
            }
        });
        root.getScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                removeKey(KeyCode.LEFT);
            } else if (event.getCode() == KeyCode.RIGHT) {
                removeKey(KeyCode.RIGHT);
            } else if (event.getCode() == KeyCode.SPACE) {
                removeKey(KeyCode.SPACE);
            } else if (event.getCode() == KeyCode.R) {
                removeKey(KeyCode.R);
            } else if (event.getCode() == KeyCode.ENTER) {
                removeKey(KeyCode.ENTER);
            } else if (event.getCode() == KeyCode.P) {
                removeKey(KeyCode.P);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                removeKey(KeyCode.ESCAPE);
            } else if (event.getCode() == KeyCode.Q) {
                removeKey(KeyCode.Q);
            }
        });
    }
    public boolean isHeld(KeyCode key) {
        return held.contains(key);
    }
    public boolean wasPressed(KeyCode key) {
        return justPressed.contains(key);
    }
    private void addKey(KeyCode key) {
        if (!held.contains(key) && !justPressed.contains(key)){
            held.add(key);
            justPressed.add(key);
        }
    }
    private void removeKey(KeyCode key) {
        held.remove(key);
        justPressed.remove(key);
    }

    public ArrayList<KeyCode> getJustPressed() {
        return justPressed;
    }
}
