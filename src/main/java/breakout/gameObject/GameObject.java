package breakout.gameObject;

import javafx.scene.paint.Color;

public class GameObject {

    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected Color color;

    public GameObject(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public boolean collidesWith(GameObject other) {
        return !(x + width < other.x || x > other.x + other.width || y + height < other.y || y > other.y + other.height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
