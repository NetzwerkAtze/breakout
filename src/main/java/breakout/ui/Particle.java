package breakout.ui;

import javafx.scene.paint.Color;

public class Particle {
    private double x;
    private double y;
    private double vx;
    private double vy;
    private int maxLifeDuration;
    private int remainingLifeDuration;
    private Color color;

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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
