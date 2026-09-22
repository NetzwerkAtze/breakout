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

            @Override
            public void start(Stage primaryStage) {
                List<LevelConfig> levels = new ArrayList<>();
                levels.add(new LevelConfig(1,1));
                levels.add(new LevelConfig(4, 4));
                Pane root = new Pane();
                Scene scene = new Scene(root, 900, 600, Color.BLACK);
                Ball ball = new Ball(scene.getWidth() / 2, scene.getHeight() / 2, 4, -4, 5, Color.WHITE);
                Paddle paddle = new Paddle((scene.getWidth() - 80) / 2, scene.getHeight() - 20, 5, 80, 15, Color.WHITE, scene.getWidth());
           //     Game game = new Game(scene, paddle, ball, 2, 10 ,8);
                Game game = new Game(scene, paddle, ball, 2, levels.get(0).maxCol() ,levels.get(0).maxRow());
                GameRenderer gameRenderer = new GameRenderer(root, game);
                InputHandler inputHandler = new InputHandler(root, game);

                AnimationTimer timer = new AnimationTimer() {
                    @Override
                    public void handle(long l) {
                        game.update();
                        gameRenderer.update();
                        inputHandler.update();
                        //    this.stop();
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
        }