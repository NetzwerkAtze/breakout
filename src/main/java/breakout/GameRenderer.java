package breakout;

import breakout.gameObject.Ball;
import breakout.gameObject.Brick;
import breakout.gameObject.GameObject;
import breakout.gameObject.PowerUp;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import java.util.*;

public class GameRenderer {
    private Pane root;
    private Game game;
    private Rectangle paddleShape;
    private Map<Brick, Rectangle> brickMap = new HashMap<>();
    private Map<PowerUp, Circle> powerUpMap = new HashMap<>();
    private Map<Ball, Circle> ballMap = new HashMap<>();
    private List<Polygon> heartShapes = new ArrayList<>();
    private Map<Brick, List<Group>> crackMap = new HashMap<>();
    private Text scoreText;
    private Text wonText;
    private Text levelCompleteText;
    private Text loseText;
    private Text livesText;
    private Text restartText;
    private Text levelText;
    private Text nextLevelText;
    private Text pausedText;
    private Text highscoreText;
    private HighscoreManager hsManager;
    private DropShadow dropShadow;
    Group boardLayer = new Group();
    Group playLayer = new Group();
    Group hudLayer = new Group();



    public GameRenderer(Pane root, Game game, HighscoreManager hsManager) {
        this.root = root;
        this.game = game;
        this.hsManager = hsManager;
        init();
    }

    private void init() {
        createDropShadow();
        createTexts();
        createHeartShapes();
        createWalls(8);
        createBrickShapes();
        createPowerUpShapes();
        updateBallShapes();
        createPaddleShape();
        root.getChildren().addAll(boardLayer);
        root.getChildren().addAll(playLayer);
        root.getChildren().addAll(hudLayer);
    }
    public void update() {
        updateDropShadow();
        updateBallShapes();
        updateTexts();
        updateBrickShapes();
        updatePowerUpShapes();
        updateHeartShapes();
        updatePaddleShape();
    }

    public void reset() {
        for (PowerUp powerUp : powerUpMap.keySet())
            playLayer.getChildren().remove(powerUpMap.get(powerUp));
        powerUpMap.clear();
        createPowerUpShapes();
        for (Polygon heart : heartShapes)
            heart.setFill(Color.RED);
        for (Brick brick : game.getBricks()) {
            brickMap.get(brick).setFill(makeGradient(brick.getColor()));
            for (int i = 0; i < crackMap.get(brick).size(); i++)
                crackMap.get(brick).get(i).setVisible(false);
        }
    }
    public void clear() {
        crackMap.values().forEach(list -> list.forEach(value -> root.getChildren().remove(value)));
        crackMap.clear();
        powerUpMap.clear();
        ballMap.clear();
        heartShapes.clear();
        crackMap.clear();
        root.getChildren().remove(boardLayer);
        root.getChildren().remove(playLayer);
        root.getChildren().remove(hudLayer);
    }
    public void displayVictory() {
        clear();
        wonText.setFill(Color.WHITE);
        root.getChildren().add(wonText);
    }
    public void createPowerUpShapes() {
        for (PowerUp pUp : game.getPowerUpHandler().getPowerUps()) {
            Circle pUpShape = new Circle(pUp.getX() + pUp.getWidth() / 2, pUp.getY() + pUp.getHeight() / 2, pUp.getHeight(), Color.TRANSPARENT);
            powerUpMap.put(pUp, pUpShape);
            playLayer.getChildren().add(pUpShape);
        }
    }

    private void updatePaddleShape() {
        paddleShape.setX(game.getPaddle().getX());
        paddleShape.setY(game.getPaddle().getY());
        paddleShape.setWidth(game.getPaddle().getWidth());
    }
    private void updateBallShapes() {
        updateBallShapeAmount();
        updateBallShapeSize();
        updateBallShapePosition();
    }
    private void updateBallShapeAmount() {
        for (Ball ball : game.getBalls()) {
            if (!ballMap.containsKey(ball)) {
                Circle ballShape = new Circle(ball.getCenterX(), ball.getCenterY(), ball.getRadius(), ball.getColor());
                ballShape.setEffect(dropShadow);
                ballMap.put(ball, ballShape);
                playLayer.getChildren().add(ballShape);
            }
        }
        if (!ballMap.isEmpty()) {
            Iterator<Ball> it = ballMap.keySet().iterator();
            while (it.hasNext()) {
                Ball ball = it.next();
                if (!game.getBalls().contains(ball)) {
                    playLayer.getChildren().remove(ballMap.get(ball));
                    it.remove();
                }
            }
        }
    }
    private void updateBallShapeSize() {
        for (Map.Entry<Ball, Circle> entry : ballMap.entrySet()) {
            if (entry.getKey().getRadius() != entry.getValue().getRadius())
                entry.getValue().setRadius(entry.getKey().getRadius());
        }
    }
    private void updateBallShapePosition() {
        for (Map.Entry<Ball, Circle> entry : ballMap.entrySet()) {
            entry.getValue().setCenterX(entry.getKey().getCenterX());
            entry.getValue().setCenterY(entry.getKey().getCenterY());
        }
    }

    private void updateBrickShapes() {
        for (Brick brick : game.getBricks()) {
            if (brick.isDestroyed()) {
                brickMap.get(brick).setFill(Color.TRANSPARENT);
                crackMap.getOrDefault(brick, List.of()).forEach(value -> value.setVisible(false));
                continue;
            }
            if (brick.getMaxHits() > brick.getHitsRemaining()) {
                for (int i = 0; i <= crackMap.get(brick).size() - brick.getHitsRemaining(); i++)
                    crackMap.get(brick).get(i).setVisible(true);
            }
        }
    }
    private void updatePowerUpShapes() {
        for (PowerUp pUp : game.getPowerUpHandler().getPowerUps()) {
            powerUpMap.get(pUp).setCenterX(pUp.getX() + pUp.getWidth() / 2);
            powerUpMap.get(pUp).setCenterY(pUp.getY() + pUp.getHeight() / 2);
            if (pUp.getPowerUpState() == PowerUp.PowerUpState.FALLING) {
                powerUpMap.get(pUp).setFill(pUp.getColor());
            } else if (pUp.getPowerUpState() == PowerUp.PowerUpState.ACTIVE || pUp.getPowerUpState() == PowerUp.PowerUpState.REMOVED || pUp.getPowerUpState() == PowerUp.PowerUpState.WAITING) {
                powerUpMap.get(pUp).setFill(Color.TRANSPARENT);
            }
        }
    }
    public LinearGradient makeGradient(Color color) {
    Stop[] stops = new Stop[] { new Stop(0, color.brighter().brighter()), new Stop(1, color.darker().darker())};
        return new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
    }
    private void addCracks(Brick brick) {
        Group crack1 = buildCrack(brick,
                new double[]{0.40, 0.0,  0.33, 0.35,  0.45, 0.55,  0.38, 0.80,  0.47, 1.0},
                new double[]{0.45, 0.55,  0.62, 0.65,  0.70, 0.90},
                new double[]{0.33, 0.35,  0.20, 0.45});

        Group crack2 = buildCrack(brick,
                new double[]{0.75, 0.0,  0.80, 0.30,  0.70, 0.50,  0.78, 0.75,  0.72, 1.0},
                new double[]{0.70, 0.50,  0.58, 0.40},
                new double[]{0.80, 0.30,  0.92, 0.45});

        List<Group> cracks = List.of(crack1, crack2);
        crackMap.put(brick, cracks);
        boardLayer.getChildren().addAll(cracks);
    }

    private Polyline buildLine(Brick brick, double... points) {
        Polyline line = new Polyline();
        for (int i = 0; i < points.length; i += 2) {
            line.getPoints().addAll(brick.getX() + brick.getWidth() * points[i], brick.getY() + brick.getHeight() * points[i+1]);
        }
        return line;
    }

    private Group buildCrack(Brick brick, double[]... lines) {
        Group crack = new Group();
        for (double[] points : lines)
            crack.getChildren().add(buildLine(brick, points));
        crack.setVisible(false);
        return crack;
    }

    private void updateTexts() {
        scoreText.setText("Score: " + Integer.toString(game.getGameState().getScore()));
        highscoreText.setText(("Highscore: " + hsManager.getHighscore()));
        if (game.getGameState().isPaused())
            pausedText.setFill(Color.WHITE);
        if (!game.getGameState().isPaused())
            pausedText.setFill(Color.TRANSPARENT);
        if (game.getGameState().gameOver()) {
            restartText.setFill(Color.WHITE);
            loseText.setFill(Color.WHITE);
        } else if (game.getGameState().isLevelWon()) {
            levelCompleteText.setFill(Color.WHITE);
            nextLevelText.setFill(Color.WHITE);
        } else {
            restartText.setFill(Color.TRANSPARENT);
            loseText.setFill(Color.TRANSPARENT);
        }

    }

    private Text generateText(double x, double y, String text) {
        Text tmp = new Text(text);
        tmp.setFont(new Font(15));
        tmp.setY(y - tmp.getLayoutBounds().getCenterY());
        tmp.setX(x - tmp.getLayoutBounds().getCenterX());
        tmp.setFill(Color.TRANSPARENT);
        hudLayer.getChildren().add(tmp);
        return tmp;
    }
    private Text generateText(double x, double y, String text, Color color) {
        Text tmp = new Text(text);
        tmp.setFont(new Font(20));
        tmp.setY(y);
        tmp.setX(x);
        tmp.setFill(color);
        hudLayer.getChildren().add(tmp);
        return tmp;
    }
    private void createTexts() {
        scoreText = generateText(15,20,"Score: " + Integer.toString(game.getGameState().getScore()),  Color.WHITE);
        highscoreText = generateText((root.getScene().getWidth() / 4) + 15,20, "Highscore: " + hsManager.getHighscore(), Color.WHITE);
        levelText = generateText((root.getScene().getWidth() / 4) * 2 + 15,20, "Level: " + Integer.toString(game.getGameState().getLevel()), Color.WHITE);
        wonText = generateText(root.getScene().getWidth() / 2,root.getScene().getHeight() / 2,"You Won!");
        levelCompleteText = generateText(root.getScene().getWidth() / 2,root.getScene().getHeight() / 2,"Level Complete!");
        loseText = generateText(root.getScene().getWidth() / 2,root.getScene().getHeight() / 2,"You Lost!");
        restartText = generateText(root.getScene().getWidth() / 2,loseText.getY() - (loseText.getLayoutBounds().getCenterY() * 3 - 3 * loseText.getY()),"- Press R to restart -");
        nextLevelText = generateText(root.getScene().getWidth() / 2,loseText.getY() - (loseText.getLayoutBounds().getCenterY() * 3 - 3 * loseText.getY()),"- Press Enter for next level -");
        pausedText = generateText(root.getScene().getWidth() / 2,root.getScene().getHeight() / 2,"- Paused. Press P to unpause. -");
        livesText = generateText((root.getScene().getWidth() / 4) * 3 + 15,  20, "Lives: ", Color.WHITE);
    }

    private void createHeartShapes() {
        for (int i = 0; i < game.getGameState().getLives(); i++) {
            Polygon heart = new Polygon();
            heart.getPoints().addAll(new Double[] {
                    livesText.getLayoutBounds().getMaxX() + 20 + 25 * i, livesText.getLayoutBounds().getMinY() + 5,
                    livesText.getLayoutBounds().getMaxX() + 20 * 3 / 2 + 25 * i, livesText.getLayoutBounds().getMinY() + 15,
                    livesText.getLayoutBounds().getMaxX() + 20 * 2 + 25 * i, livesText.getLayoutBounds().getMinY() + 5,
                    livesText.getLayoutBounds().getMaxX() + 20 * 3 / 2 + 25 * i, livesText.getLayoutBounds().getMaxY(),
            });
            heart.setFill(Color.RED);
            heartShapes.add(heart);
            heart.setFill(Color.RED);
            hudLayer.getChildren().add(heart);
        }
    }

    private void updateHeartShapes() {
        if (game.getGameState().getLives() < heartShapes.size())
            heartShapes.get(game.getGameState().getLives()).setFill(Color.TRANSPARENT);
    }

    private void createBrickShapes() {
        for (Brick brick : game.getBricks()) {
            Rectangle brickShape = new Rectangle(brick.getWidth(), brick.getHeight(), brick.getColor());
            brickShape.setFill(makeGradient(brick.getColor()));
            brickShape.setX(brick.getX());
            brickShape.setY(brick.getY());
            brickShape.setArcHeight(8);
            brickShape.setArcWidth(8);
            brickMap.put(brick, brickShape);
            boardLayer.getChildren().add(brickShape);
            addCracks(brick);
        }
    }
    public void createPaddleShape() {
        paddleShape = new Rectangle(game.getPaddle().getWidth(), game.getPaddle().getHeight(), game.getPaddle().getColor());
        paddleShape.setX(game.getPaddle().getX());
        paddleShape.setY(game.getPaddle().getY());
        paddleShape.setArcWidth(8);
        paddleShape.setArcHeight(8);
        playLayer.getChildren().add(paddleShape);
    }
    private void createWalls(int numberOfBricks) {
        for (GameObject wall : game.getWalls())
            buildWall(wall,wall.getWidth() > wall.getHeight(), numberOfBricks);
    }

    private void buildWall(GameObject wall, boolean isHorizontal, int wallAmount) {
        double wallWidth = isHorizontal ? wall.getWidth() / wallAmount : wall.getWidth();
        double wallHeight = isHorizontal ? wall.getHeight() : wall.getHeight() / wallAmount ;
        for (int i = 0; i < wallAmount; i++) {
            Rectangle wallShape = new Rectangle(wallWidth, wallHeight, wall.getColor());
            wallShape.setFill(makeGradient(wall.getColor()));
            if (isHorizontal) {
                wallShape.setX(wall.getX() + wallWidth * i);
                wallShape.setY(wall.getY());
            } else {
                wallShape.setX(wall.getX());
                wallShape.setY(wall.getY() + wallHeight * i);
            }
            wallShape.setArcHeight(8);
            wallShape.setArcWidth(8);
            boardLayer.getChildren().add(wallShape);
        }
    }

    private void createDropShadow() {
        dropShadow = new DropShadow(25, Color.rgb(255,255,255));
        dropShadow.setSpread(0.5);
    }

    private void updateDropShadow() {
        if (game.getPowerUpHandler().isStickyPaddle())
            paddleShape.setEffect(dropShadow);

        else
            paddleShape.setEffect(null);
    }
}
