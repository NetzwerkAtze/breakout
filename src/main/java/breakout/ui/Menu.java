package breakout.ui;

import breakout.level.LevelRepository;
import javafx.scene.Group;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
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
        xTremeBreakoutX.setFill(Color.web("#e8f0ff"));
        xTremeBreakoutX.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.web("#4da3ff"), 20, 0.3, 0, 0));
        xTremeBreakoutX.setX((root.getScene().getWidth()/2 - xTremeBreakoutX.getLayoutBounds().getCenterX()));
        xTremeBreakoutX.setY(root.getScene().getHeight()/2);
        Text startText = new Text("- press Enter -");
        startText.setFill(Color.BLACK);
        startText.setFont(new Font(20));
        startText.setX(root.getScene().getWidth()/2 - startText.getLayoutBounds().getCenterX());
        startText.setY(root.getScene().getHeight()/2 + 30);
        startText.setFill(Color.web("#e8f0ff"));
        startText.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.web("#4da3ff"), 20, 0.3, 0, 0));
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

    public void displayWorldSelection(int worldNumber) {
        overlay.getChildren().clear();
        Text tmp = new Text("World " + (worldNumber));
        tmp.setFont(new Font(fontSize));
        tmp.setFill(Color.web("#e8f0ff"));
        tmp.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.web("#4da3ff"), 20, 0.3, 0, 0));
        tmp.setX(root.getScene().getWidth()/2 - tmp.getLayoutBounds().getCenterX());
        tmp.setY(root.getScene().getHeight()/2);
        overlay.getChildren().add(tmp);
    }
    public void displayLevelSelection(int worldNumber, int levelNumber) {
        overlay.getChildren().clear();
        Text tmp = new Text("World " + (worldNumber));
        tmp.setFont(new Font(fontSize));
        tmp.setX(root.getScene().getWidth()/2 - tmp.getLayoutBounds().getCenterX());
        tmp.setY(root.getScene().getHeight()/2 - fontSize);
        tmp.setFill(Color.web("#e8f0ff"));
        tmp.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.web("#4da3ff"), 20, 0.3, 0, 0));
        overlay.getChildren().add(tmp);
        Text tmp2 = new Text("Level " + (levelNumber));
        tmp2.setFont(new Font(fontSize));
        tmp2.setX(root.getScene().getWidth()/2 - tmp2.getLayoutBounds().getCenterX());
        tmp2.setY(root.getScene().getHeight()/2 + fontSize);
        tmp2.setFill(Color.web("#e8f0ff"));
        tmp2.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.web("#4da3ff"), 20, 0.3, 0, 0));
        overlay.getChildren().add(tmp2);
    }
}
