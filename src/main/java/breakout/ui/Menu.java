package breakout.ui;

import breakout.input.InputHandler;
import breakout.level.LevelRepository;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu {
    private Pane root;
    private Group overlay = new Group();
    private static int fontSize = 50;


    public Menu(Pane root, LevelRepository repository) {
        this.root = root;
        init();
    }

    private void init() {
        Text xTremeBreakoutX = new Text("xTreme BREAKOUT");
        xTremeBreakoutX.setFill(Color.BLACK);
        xTremeBreakoutX.setFont(new Font(40));
        xTremeBreakoutX.setX((root.getScene().getWidth()/2 - xTremeBreakoutX.getLayoutBounds().getCenterX()));
        xTremeBreakoutX.setY(root.getScene().getHeight()/2);
        Text startText = new Text("- press Enter -");
        startText.setFill(Color.BLACK);
        startText.setFont(new Font(20));
        startText.setX(root.getScene().getWidth()/2 - startText.getLayoutBounds().getCenterX());
        startText.setY(root.getScene().getHeight()/2 + 30);
        overlay.getChildren().add(xTremeBreakoutX);
        overlay.getChildren().add(startText);
        overlay.getChildren().forEach(node -> node.setEffect(new Glow(10000)));
        root.getChildren().add(overlay);
    }

    public void hide() {
        overlay.setVisible(false);
    }

    public void show() {
        overlay.setVisible(true);
    }

    public void displayWorldSelection(int worldNumber) {
        overlay.getChildren().clear();
        Text tmp = new Text("World " + (worldNumber));
        tmp.setFill(Color.BLACK);
        tmp.setFont(new Font(fontSize));
        tmp.setEffect(new Glow(10000));
        tmp.setX(root.getScene().getWidth()/2 - tmp.getLayoutBounds().getCenterX());
        tmp.setY(root.getScene().getHeight()/2);
        overlay.getChildren().add(tmp);
    }
    public void displayLevelSelection(int worldNumber, int levelNumber) {
        overlay.getChildren().clear();
        Text tmp = new Text("World " + (worldNumber));
        tmp.setFill(Color.BLACK);
        tmp.setFont(new Font(fontSize));
        tmp.setX(root.getScene().getWidth()/2 - tmp.getLayoutBounds().getCenterX());
        tmp.setY(root.getScene().getHeight()/2 - fontSize);
        tmp.setEffect(new Glow(10000));
        overlay.getChildren().add(tmp);
        Text tmp2 = new Text("Level " + (levelNumber));
        tmp2.setFill(Color.BLACK);
        tmp2.setFont(new Font(fontSize));
        tmp2.setX(root.getScene().getWidth()/2 - tmp2.getLayoutBounds().getCenterX());
        tmp2.setY(root.getScene().getHeight()/2 + fontSize);
        tmp2.setEffect(new Glow(10000));
        overlay.getChildren().add(tmp2);
    }
}
