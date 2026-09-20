package breakout.gameObject;

import javafx.scene.paint.Color;

public class GameObject {

    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected Color color;

    /**
     * Creates a game object with the given position, size and color.
     * @param x x-coordinate top left corner
     * @param y y-coordinate top left corner
     * @param width width of the object
     * @param height height of the object
     * @param color color of the object
     */
    public GameObject(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * checks if the shapes of the two objects overlap.
     * @param other the other object to check for collision
     * @return true if objects overlapping
     */
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
