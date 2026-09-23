package breakout;

import breakout.gameObject.Brick;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.HashMap;
import java.util.Map;

public class GameRenderer {
    private Pane root;
    private Game game;
    private Circle ballShape;
    private Rectangle paddleShape;
    private Map<Brick, Rectangle> rectangleMap = new HashMap<>();
    Text scoreText;
    Text wonText;
    Text levelCompleteText;
    Text loseText;
    Text livesText;
    Text restartText;
    Text levelText;
    Text nextLevelText;



    public GameRenderer(Pane root, Game game) {
        this.root = root;
        this.game = game;
        init();
    }

    private void init() {
        scoreText = new Text(15,20, "Score: " + Integer.toString(game.getScore()));
        scoreText.setFont(new Font(20));
        scoreText.setFill(Color.WHITE);

        levelText = new Text((root.getScene().getWidth() / 3) * 2 + 15,20, "Level: " + Integer.toString(game.getLevel()));
        levelText.setFont(new Font(20));
        levelText.setFill(Color.WHITE);

        livesText = new Text(root.getScene().getWidth() / 3 + 15,  20, "Lives: " + Integer.toString(game.getLives()));
        livesText.setFont(new Font(20));
        livesText.setFill(Color.WHITE);

        wonText = new Text("You Won!");
        wonText.setFont(new Font(20));
        wonText.setY(root.getScene().getHeight() / 2 - wonText.getLayoutBounds().getCenterY());
        wonText.setX(root.getScene().getWidth() / 2 - wonText.getLayoutBounds().getCenterX());
        wonText.setFill(Color.TRANSPARENT);

        levelCompleteText = new Text("Level Complete!");
        levelCompleteText.setFont(new Font(20));
        levelCompleteText.setY(root.getScene().getHeight() / 2 - levelCompleteText.getLayoutBounds().getCenterY());
        levelCompleteText.setX(root.getScene().getWidth() / 2 - levelCompleteText.getLayoutBounds().getCenterX());
        levelCompleteText.setFill(Color.TRANSPARENT);

        loseText = new Text("You Lost!");
        loseText.setFont(new Font(20));
        loseText.setY(root.getScene().getHeight() / 2 - loseText.getLayoutBounds().getCenterY());
        loseText.setX(root.getScene().getWidth() / 2 - loseText.getLayoutBounds().getCenterX());
        loseText.setFill(Color.TRANSPARENT);

        restartText = new Text("- Press R to restart -");
        restartText.setFont(new Font(15));
        restartText.setY(loseText.getY() - (loseText.getLayoutBounds().getCenterY() * 3 - 3 * loseText.getY()));
        restartText.setX(root.getScene().getWidth() / 2 - restartText.getLayoutBounds().getCenterX());
        restartText.setFill(Color.TRANSPARENT);

        nextLevelText = new Text("- Press Enter for next level -");
        nextLevelText.setFont(new Font(15));
        nextLevelText.setY(loseText.getY() - (loseText.getLayoutBounds().getCenterY() * 3 - 3 * loseText.getY()));
        nextLevelText.setX(root.getScene().getWidth() / 2 - nextLevelText.getLayoutBounds().getCenterX());
        nextLevelText.setFill(Color.TRANSPARENT);

        for (Brick brick : game.getBricks()) {
            Rectangle brickShape = new Rectangle(game.getBrickWidth(), game.getBrickHeight(), brick.getColor());
            brickShape.setX(brick.getX());
            brickShape.setY(brick.getY());
            rectangleMap.put(brick, brickShape);
            root.getChildren().add(brickShape);
        }
        ballShape = new Circle(game.getBall().getCenterX(), game.getBall().getCenterY(), game.getBall().getRadius(), game.getBall().getColor());
        paddleShape = new Rectangle(game.getPaddle().getWidth(), game.getPaddle().getHeight(), game.getPaddle().getColor());
        paddleShape.setX(game.getPaddle().getX());
        paddleShape.setY(game.getPaddle().getY());

        root.getChildren().add(wonText);
        root.getChildren().add(levelCompleteText);
        root.getChildren().add(loseText);
        root.getChildren().add(ballShape);
        root.getChildren().add(paddleShape);
        root.getChildren().add(scoreText);
        root.getChildren().add(livesText);
        root.getChildren().add(restartText);
        root.getChildren().add(levelText);
        root.getChildren().add(nextLevelText);
    }
    public void update() {
        for (Brick brick : game.getBricks()) {
            if (brick.isDestroyed())
                rectangleMap.get(brick).setFill(Color.TRANSPARENT);
            if (!brick.isDestroyed())
                rectangleMap.get(brick).setFill(brick.getColor());
        }
        scoreText.setText("Score: " + Integer.toString(game.getScore()));
        livesText.setText("Lives: " + Integer.toString(game.getLives()));
        ballShape.setCenterX(game.getBall().getCenterX());
        ballShape.setCenterY(game.getBall().getCenterY());
        paddleShape.setX(game.getPaddle().getX());
        paddleShape.setY(game.getPaddle().getY());
        if (game.gameOver()) {
            if (game.isLevelWon()) {
                levelCompleteText.setFill(Color.WHITE);
                nextLevelText.setFill(Color.WHITE);
            }
            else {
                restartText.setFill(Color.WHITE);
                loseText.setFill(Color.WHITE);
            }
        } else {
            restartText.setFill(Color.TRANSPARENT);
            loseText.setFill(Color.TRANSPARENT);
        }
    }
    public void nextLevel() {
        for (Brick brick : game.getBricks())
            root.getChildren().remove(rectangleMap.get(brick));
        root.getChildren().remove(loseText);
        root.getChildren().remove(ballShape);
        root.getChildren().remove(paddleShape);
        root.getChildren().remove(scoreText);
        root.getChildren().remove(livesText);
        root.getChildren().remove(restartText);
        root.getChildren().remove(levelText);
        root.getChildren().remove(nextLevelText);
        root.getChildren().remove(levelCompleteText);
    }
    public void displayVictory() {
        nextLevel();
        wonText.setFill(Color.WHITE);
    }
}
