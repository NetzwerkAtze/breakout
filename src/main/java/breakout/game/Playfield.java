package breakout.game;

import breakout.level.LevelConfig;

public record Playfield(double left, double right, double top, double bottom) {
    public static final double WALL_THICKNESS = 23.5;
    public static final double MENU_BAR_HEIGHT = 30;
    public static final double BRICK_GAP = 1;


    public static Playfield calculatePlayfield(LevelConfig config, double windowWidth, double windowHeight) {
        int brickAmount = config.layout().getFirst().length();
        double fieldWidth = (brickAmount * config.world().brickWidth()) + (brickAmount + 1 * BRICK_GAP) + 2 * WALL_THICKNESS;
        double gap = (windowWidth - Math.min(fieldWidth, windowWidth)) / 2;
        return new Playfield(0 + gap + WALL_THICKNESS, windowWidth - gap - WALL_THICKNESS, MENU_BAR_HEIGHT + WALL_THICKNESS, windowHeight);
    }
}
