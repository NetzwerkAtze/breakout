    package breakout;

    import breakout.game.Game;
    import breakout.game.Playfield;
    import breakout.input.InputHandler;
    import breakout.level.LevelConfig;
    import breakout.level.LevelId;
    import breakout.level.LevelRepository;
    import breakout.storage.HighscoreManager;
    import breakout.ui.GameRenderer;
    import breakout.ui.Menu;
    import javafx.animation.AnimationTimer;
    import javafx.application.Application;
    import javafx.scene.Scene;
    import javafx.scene.input.KeyCode;
    import javafx.scene.layout.Pane;
    import javafx.scene.paint.Color;
    import javafx.stage.Stage;


    public class Main extends Application {

        private Pane root = new Pane();
        private Scene scene = new Scene(root, 900, 600, Color.BLACK);
        private GameRenderer gameRenderer;
        private InputHandler inputHandler = new InputHandler(root);
        private HighscoreManager hsManager = new HighscoreManager();
        private LevelRepository repository = new LevelRepository();
        private ScreenState screenState = ScreenState.MENU;
        private Menu menu = new Menu(root, inputHandler, repository);
        private Game game;
        private int defaultLives = 3;
        private int nextLevelIndex = 0;
        private LevelId nextLevelId;

        public enum ScreenState {
            MENU,
            PLAYING,
            VICTORY
        }

        @Override
        public void start(Stage primaryStage) {

            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    switch (screenState) {

                        case MENU ->   {
                            if (inputHandler.wasPressed(KeyCode.ENTER))
                                loadLevel(nextLevelIndex);
                        }
                        case PLAYING -> {
                            if (inputHandler.wasPressed(KeyCode.R)) {
                                game.reset();
                                gameRenderer.reset();
                            }
                            if (game.getGameState().gameOver()) {
                                if (game.getGameState().getScore() > hsManager.getHighscore())
                                    hsManager.writeHighscore(game.getGameState().getScore());
                            } else if (game.getGameState().isLevelWon()) {
                                if (inputHandler.wasPressed(KeyCode.ENTER))
                                    loadLevel(nextLevelIndex);
                            } else {
                                game.update();
                                gameRenderer.update();
                            }
                            if (inputHandler.wasPressed(KeyCode.Q))     // for debugging
                                game.getGameState().setLevelWon(true);  // for debugging
                            if (inputHandler.wasPressed(KeyCode.ESCAPE))
                                openMenu();
                        }
                        case VICTORY -> {
                            if (game.getGameState().getScore() > hsManager.getHighscore())
                                hsManager.writeHighscore(game.getGameState().getScore());
                            gameRenderer.displayVictory();
                            if (inputHandler.wasPressed(KeyCode.ESCAPE))
                                openMenu();
                        }
                    }
                    endFrame();
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

        private void loadLevel(int levelIndex) {
            menu.hide();
            if (levelIndex >= repository.getLevels().size())
                screenState = ScreenState.VICTORY;
            else {
                if (levelIndex > 0)
                    gameRenderer.clear();
                game = new Game(defaultLives, repository.getLevels().get(levelIndex), inputHandler, Playfield.calculatePlayfield(repository.getLevels().get(levelIndex), scene.getWidth(), scene.getHeight()));
                gameRenderer = new GameRenderer(root, game, hsManager);
                nextLevelIndex++;
                screenState = ScreenState.PLAYING;
            }
        }

        private void openMenu() {
            gameRenderer.clear();
            menu.show();
            nextLevelIndex = 0;
            screenState = ScreenState.MENU;
        }

        private void endFrame() {
            inputHandler.clearJustPressed();
        }
    }