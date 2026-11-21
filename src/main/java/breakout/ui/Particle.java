package breakout.ui;

import javafx.scene.paint.Color;

public class Particle {
    double x;
    double y;
    double vx;
    double vy;
    int maxLifeDuration;
    int remainingLifeDuration;
    Color color;

    public Particle(double x, double y, double vx, double vy, int maxLifeDuration, Color color) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.maxLifeDuration = maxLifeDuration;
        this.color = color;
        remainingLifeDuration = maxLifeDuration;
    }
    public void update(){
        x += vx;
        y += vy;
        remainingLifeDuration--;
    }
    public boolean isAlive() {
        return remainingLifeDuration > 0;
    }
}
