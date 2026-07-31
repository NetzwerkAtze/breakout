package breakout.gameObject;

import javafx.scene.paint.Color;

public class GameObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Color color;

    public GameObject(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public boolean collidesWith(GameObject other) {
        return !(x + width < other.x || x > other.x + other.width || y + height < other.y || y > other.y + other.height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }
}
