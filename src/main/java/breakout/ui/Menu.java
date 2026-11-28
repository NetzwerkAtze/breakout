package breakout.ui;

import breakout.level.LevelRepository;
import javafx.scene.Group;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Menu {
    private Pane root;
    private Group overlay = new Group();
    private static int fontSize = 50;
    private Text startText = new Text("- press Enter -");
    private int frameTimer = 10;
    private double opacitiyValue = -0.01;


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
    public void showControlls() {
        // test muss af keycaps geändert werden
        Text tmp = new Text("TESTESTESTESTETS");
        tmp.setFont(new Font(fontSize));
        tmp.setX(root.getScene().getWidth()/2 - tmp.getLayoutBounds().getCenterX());
        tmp.setY(root.getScene().getHeight()/2 - fontSize);
        tmp.setFill(Color.web("#e8f0ff"));
        tmp.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.web("#4da3ff"), 20, 0.3, 0, 0));
        overlay.getChildren().add(tmp);
        StackPane keyR     = createKeyIcon("R", fontSize * 1.4);
        StackPane keyP     = createKeyIcon("P", fontSize * 1.4);
        StackPane keyLeft  = createKeyIcon("←", fontSize * 1.4);
        StackPane keyRight = createKeyIcon("→", fontSize * 1.4);
        StackPane keySpace = createKeyIcon("SPACE", fontSize * 4);
        StackPane keyEnter = createKeyIcon("ENTER", fontSize * 3);
    }
    public void endFrame() {
        if (startText.getOpacity() <= 0 || startText.getOpacity() >= 1.1)
            opacitiyValue = -opacitiyValue;
        startText.setOpacity(startText.getOpacity() + opacitiyValue);
    }
    private StackPane createKeyIcon(String label, double width) {
        Rectangle cap = new Rectangle(width, fontSize * 1.4);
        cap.setArcWidth(10);
        cap.setArcHeight(10);
        cap.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#2a3a5c")),
                new Stop(1, Color.web("#141c30"))));
        cap.setStroke(Color.web("#4da3ff"));
        cap.setStrokeWidth(1.5);

        Text text = new Text(label);
        text.setFont(new Font(fontSize * 0.6));
        text.setFill(Color.web("#e8f0ff"));

        StackPane key = new StackPane(cap, text);
        key.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.rgb(0, 0, 0, 0.6), 0, 0, 0, 3));
        return key;
    }
}
