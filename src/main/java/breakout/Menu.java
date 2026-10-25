package breakout;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu {
    private Pane root;
    private Group overlay = new Group();

    public Menu(Pane root, InputHandler inputHandler) {
        this.root = root;
        init();
    }

    private void init() {
        Text startText = new Text("Start");
        startText.setFill(Color.WHITE);
        startText.setFont(new Font(20));
        startText.setX(root.getScene().getWidth()/2 - startText.getLayoutBounds().getCenterX());
        startText.setY(root.getScene().getHeight()/2);
        overlay.getChildren().add(startText);
        root.getChildren().add(overlay);
    }
    public void hide() {
        overlay.setVisible(false);
    }
    public void show() {
        overlay.setVisible(true);
    }
}
