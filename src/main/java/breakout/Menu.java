package breakout;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu {
    private Pane root;

    public Menu(Pane root, InputHandler inputHandler) {
        this.root = root;
        init();
    }

    private void init() {
        Text t = new Text("Start");
        t.setFill(Color.WHITE);
        t.setFont(new Font(20));
        t.setX(root.getScene().getWidth()/2);
        t.setY(root.getScene().getHeight()/2);
        root.getChildren().add(t);
    }
}
