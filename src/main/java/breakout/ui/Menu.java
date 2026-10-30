package breakout.ui;

import breakout.input.InputHandler;
import breakout.level.LevelRepository;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu {
    private Pane root;
    private Group overlay = new Group();
    private InputHandler inputHandler;
    int selectedWorld = 1;

    public Menu(Pane root, InputHandler inputHandler, LevelRepository repository) {
        this.root = root;
        this.inputHandler = inputHandler;
        init();
    }

    private void init() {
        Text xTremeBreakoutX = new Text("xTreme BREAKOUT");
        xTremeBreakoutX.setFill(Color.WHITE);
        xTremeBreakoutX.setFont(new Font(40));
        xTremeBreakoutX.setX((root.getScene().getWidth()/2 - xTremeBreakoutX.getLayoutBounds().getCenterX()));
        xTremeBreakoutX.setY(root.getScene().getHeight()/2);
        Text startText = new Text("- press Enter -");
        startText.setFill(Color.WHITE);
        startText.setFont(new Font(20));
        startText.setX(root.getScene().getWidth()/2 - startText.getLayoutBounds().getCenterX());
        startText.setY(root.getScene().getHeight()/2 + 30);
        overlay.getChildren().add(xTremeBreakoutX);
        overlay.getChildren().add(startText);
        root.getChildren().add(overlay);
    }

    public void hide() {
        overlay.setVisible(false);
    }

    public void show() {
        overlay.setVisible(true);
    }

    public void displayWorldSelection(int worldAmount) {
        overlay.getChildren().clear();
        for (int i = 0; i < worldAmount; i++) {
            Text tmp = new Text("World " + (i + 1));
            tmp.setFill(Color.WHITE);
            tmp.setFont(new Font(20));
            tmp.setX(root.getScene().getWidth()/2 - tmp.getLayoutBounds().getCenterX());
            tmp.setY(root.getScene().getHeight()/4 + 30 * i);
            overlay.getChildren().add(tmp);
        }
    }
    public void displayVictory() {
    }
}
