package pong.gameObject;

import javafx.scene.paint.Color;

public class Ball extends GameObject{

    private int radius;
    private int vx;
    private int vy;

    public Ball(int x, int y, int vx, int vy, int radius, Color color) {
        super(x, y, 2 * radius, 2 * radius, color);
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
    }

    public void update() {
        x += vx;
        y += vy;
    }
}
