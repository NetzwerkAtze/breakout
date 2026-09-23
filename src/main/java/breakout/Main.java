        package breakout;

        import breakout.gameObject.Ball;
        import breakout.gameObject.Paddle;
        import javafx.animation.AnimationTimer;
        import javafx.application.Application;
        import javafx.scene.Scene;
        import javafx.scene.layout.Pane;
        import javafx.scene.paint.Color;
        import javafx.stage.Stage;

        import java.util.ArrayList;
        import java.util.List;

        public class Main extends Application {

            private Pane root;
            private Scene scene;
            private Game game;
            private GameRenderer gameRenderer;
            private InputHandler inputHandler;
            private Ball ball;
            private Paddle paddle;
            private int currenLevel = 0;
            List<LevelConfig> levels = new ArrayList<>();


            @Override
            public void start(Stage primaryStage) {
                root = new Pane();
                scene = new Scene(root, 900, 600, Color.BLACK);
                levels.add(new LevelConfig(1,1));
                levels.add(new LevelConfig(1,1));
                levels.add(new LevelConfig(1,1));
                loadLevel(currenLevel);

                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long l) {
                        game.update();
                        gameRenderer.update();
                        inputHandler.update();
                        if (inputHandler.isReset() && ((game.getLives() == 0 || game.hasWon())))
                            game.reset();
                        if (inputHandler.isNextLevel() && game.hasWon())
                            loadLevel(currenLevel);
                    }
                };
                timer.start();
                primaryStage.setTitle("Breakout");
                primaryStage.setScene(scene);
                primaryStage.show();
            }
            public static void main(String[] args) {
                launch(args);
            }
            public void loadLevel(int levelIndex) {
                if (levelIndex < levels.size()) {
                    ball = new Ball(scene.getWidth() / 2, scene.getHeight() / 2, 4, -4, 5, Color.WHITE);
                    paddle = new Paddle((scene.getWidth() - 80) / 2, scene.getHeight() - 20, 5, 80, 15, Color.WHITE, scene.getWidth());
                    if (levelIndex > 0)
                        gameRenderer.nextLevel();
                    game = new Game(scene, paddle, ball, 3, levels.get(levelIndex).maxCol(), levels.get(levelIndex).maxRow(), currenLevel + 1);
                    gameRenderer = new GameRenderer(root, game);
                    inputHandler = new InputHandler(root, game);
                    currenLevel++;
                }
            }
        }