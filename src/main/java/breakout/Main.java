        package breakout;

        import javafx.animation.AnimationTimer;
        import javafx.application.Application;
        import javafx.scene.Scene;
        import javafx.scene.input.KeyCode;
        import javafx.scene.layout.Pane;
        import javafx.scene.paint.Color;
        import javafx.stage.Stage;
        import java.util.ArrayList;
        import java.util.List;

        public class Main extends Application {

            private Pane root;
            private Scene scene;
            private Menu menu;
            private Game game;
            private GameRenderer gameRenderer;
            private InputHandler inputHandler;
            private HighscoreManager hsManager;
            private int nextLevelIndex = 0;
            List<LevelConfig> levels = new ArrayList<>();
            private boolean won = false;
            private boolean inMenu = true;


            @Override
            public void start(Stage primaryStage) {
                hsManager = new HighscoreManager();
                root = new Pane();
                scene = new Scene(root, 900, 600, Color.BLACK);
                inputHandler = new InputHandler(root);
                menu = new Menu(root, inputHandler);
                levels = new LevelRepository().getLevels();


                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long l) {
                        if (inMenu) {
                            if (inputHandler.wasPressed(KeyCode.ENTER)) {
                                loadLevel(nextLevelIndex);
                                menu.hide();
                                inMenu = false;
                            }
                        }
                        else {
                            if (inputHandler.wasPressed(KeyCode.R)) {
                                game.reset();
                                gameRenderer.reset();
                            }
                            if (game.getGameState().gameOver()) {
                                if (game.getGameState().getScore() > hsManager.getHighscore())
                                    hsManager.writeHighscore(game.getGameState().getScore());
                            } else if (game.getGameState().isLevelWon()) {
                                if (won) {
                                    if (game.getGameState().getScore() > hsManager.getHighscore())
                                        hsManager.writeHighscore(game.getGameState().getScore());
                                    gameRenderer.displayVictory();
                                    this.stop();
                                }
                                if (inputHandler.wasPressed(KeyCode.ENTER))
                                    loadLevel(nextLevelIndex);
                            } else {
                                game.update();
                                gameRenderer.update();
                            }
                            if (inputHandler.wasPressed(KeyCode.Q))     // for debugging
                                game.getGameState().setLevelWon(true);  // for debugging
                            if (inputHandler.wasPressed(KeyCode.ESCAPE)) {
                                gameRenderer.clear();
                                menu.show();
                                inMenu = true;
                                nextLevelIndex = 0;
                            }
                        }
                        inputHandler.getJustPressed().clear();
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
                if (levelIndex >= levels.size())
                    won = true;
                if (levelIndex < levels.size()) {
                    if (levelIndex > 0)
                        gameRenderer.clear();
                    game = new Game(scene, 3, levels.get(levelIndex), inputHandler);
                    gameRenderer = new GameRenderer(root, game, hsManager);
                    nextLevelIndex++;
                }
            }
        }