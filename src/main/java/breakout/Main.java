    package breakout;

    import breakout.game.Game;
    import breakout.game.Playfield;
    import breakout.input.InputHandler;
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
        private ScreenState screenState = ScreenState.TITLE;
        private Menu menu = new Menu(root, inputHandler, repository);
        private Game game;
        private int defaultLives = 3;
        private LevelId levelId = new LevelId(1,1);

        public enum ScreenState {
            TITLE,
            WORLD_SELECTION,
            LEVEL_SELECTION,
            PLAYING,
            VICTORY
        }

        @Override
        public void start(Stage primaryStage) {

            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long l) {
                    switch (screenState) {
                        case TITLE -> {
                            if (inputHandler.wasPressed(KeyCode.ENTER)) {
                                menu.displayWorldSelection(repository.getWorlds().size());
                                screenState = ScreenState.WORLD_SELECTION;
                            }
                        }
                        case WORLD_SELECTION ->  {
                            if (inputHandler.wasPressed(KeyCode.DOWN) && levelId.world() < repository.getWorlds().size())
                                levelId = levelId.withWorld(levelId.world() + 1);
                            if (inputHandler.wasPressed(KeyCode.UP) && levelId.world() > 1)
                                levelId = levelId.withWorld(levelId.world() - 1);
                            if (inputHandler.wasPressed(KeyCode.ENTER))
                                screenState = ScreenState.LEVEL_SELECTION;
                        }
                        case LEVEL_SELECTION ->  {
                            if (inputHandler.wasPressed(KeyCode.DOWN) && levelId.Level() < repository.getWorld(levelId.world()).size())
                                levelId = levelId.withLevel(levelId.Level() + 1);
                            if (inputHandler.wasPressed(KeyCode.UP) && levelId.Level() > 1)
                                levelId = levelId.withLevel(levelId.Level() - 1);
                            if (inputHandler.wasPressed(KeyCode.ENTER)) {
                                screenState = ScreenState.PLAYING;
                                loadLevel(levelId);
                                System.out.println(levelId);
                            }
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
                                if (inputHandler.wasPressed(KeyCode.ENTER)) {
                                    gameRenderer.clear();
                                    loadLevel(levelId);
                                }
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

        private void loadLevel(LevelId id) {
            menu.hide();
            if (id.world() > repository.getWorlds().size())
                screenState = ScreenState.VICTORY;
            else {
                game = new Game(defaultLives, repository.getLevelById(levelId), inputHandler, Playfield.calculatePlayfield(repository.getLevelById(levelId), scene.getWidth(), scene.getHeight()));
                gameRenderer = new GameRenderer(root, game, hsManager);
                screenState = ScreenState.PLAYING;
            }
        }

        private void openMenu() {
            gameRenderer.clear();
            menu.show();
            levelId = new LevelId(1,1);
            screenState = ScreenState.WORLD_SELECTION;
        }

        private void endFrame() {
            inputHandler.clearJustPressed();
        }
    }