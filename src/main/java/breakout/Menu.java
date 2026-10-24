package breakout;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu {
    private Pane root;
    private Text startText;

    public Menu(Pane root, InputHandler inputHandler) {
        this.root = root;
        init();
    }

    private void init() {
        startText = new Text("Start");
        startText.setFill(Color.WHITE);
        startText.setFont(new Font(20));
        startText.setX(root.getScene().getWidth()/2);
        startText.setY(root.getScene().getHeight()/2);
        root.getChildren().add(startText);
    }
    public void close() {
        startText.setFill(Color.TRANSPARENT);
    }
}
