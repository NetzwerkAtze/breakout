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



    public GameRenderer(Pane root, Game game) {
        this.root = root;
        this.game = game;
        init();
    }

    private void init() {
        scoreText = new Text(15,20, "Score: " + Integer.toString(game.getScore()));
        scoreText.setFont(new Font(20));
        scoreText.setFill(Color.WHITE);

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

        root.getChildren().add(ballShape);
        root.getChildren().add(paddleShape);
        root.getChildren().add(scoreText);
    }
    public void update() {
        for (Brick brick : game.getBricks()) {
            if (brick.isDestroyed())
                rectangleMap.get(brick).setFill(Color.TRANSPARENT);
        }
        scoreText.setText("Score: " + Integer.toString(game.getScore()));
        ballShape.setCenterX(game.getBall().getCenterX());
        ballShape.setCenterY(game.getBall().getCenterY());
        paddleShape.setX(game.getPaddle().getX());
        paddleShape.setY(game.getPaddle().getY());
    }
    public void gameOver(boolean won){
        if (won) {
            Text wonText = new Text("YOU WON!");
            wonText.setY(root.getScene().getHeight() / 2 - wonText.getLayoutBounds().getCenterY());
            wonText.setX(root.getScene().getWidth() / 2 - wonText.getLayoutBounds().getCenterX());
            wonText.setFont(new Font(20));
            wonText.setFill(Color.WHITE);
            root.getChildren().add(wonText);
        }
        else {
            Text loseText = new Text("YOU LOST!");
            loseText.setY(root.getScene().getHeight() / 2 - loseText.getLayoutBounds().getCenterY());
            loseText.setX(root.getScene().getWidth() / 2 - loseText.getLayoutBounds().getCenterX());
            loseText.setFont(new Font(20));
            loseText.setFill(Color.WHITE);
            root.getChildren().add(loseText);
        }
    }
}
