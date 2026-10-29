package breakout.gameObject;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameObjectTest {

    @Test
    void overlappingObjects() {
        GameObject a = new GameObject(0,0,10,10, Color.BLUE);
        GameObject b = new GameObject(5,5,10,10, Color.RED);
        boolean result = a.collidesWith(b);
        assertTrue(result);
    }
    @Test
    void notOverlappingObjects() {
        GameObject a = new GameObject(0, 0, 10, 10, Color.BLUE);
        GameObject b = new GameObject(20, 20, 10, 10, Color.RED);
        boolean result = a.collidesWith(b);
        assertFalse(result);
    }
    @Test
    void meetOnCorner() {
        GameObject a = new GameObject(0, 0, 15, 15, Color.BLUE);
        GameObject b = new GameObject(10, 10, 10, 10, Color.BLUE);
        boolean result = a.collidesWith(b);
        assertTrue(result);
    }
    @Test
    void hitsOnY() {
        Ball a = new Ball(10,13,5,5,5,Color.RED);
        GameObject b = new GameObject(10, 10, 5, 5, Color.RED);
        boolean result = a.hitsOnY(b);
        assertTrue(result);
    }
    @Test
    void hitsFromSide() {
        Ball a = new Ball(12,10,5,5,5,Color.RED);
        GameObject b = new GameObject(10, 10, 5, 5, Color.RED);
        boolean result = a.hitsOnY(b);
        assertFalse(result);
    }
    @Test
    void hitsEdge() {
        Ball a = new Ball(7,7,5,5,5,Color.RED);
        GameObject b = new GameObject(10, 10, 15, 15, Color.RED);
        boolean result = a.hitsEdge(b);
        assertTrue(result);
    }
    @Test
    void ballMovingThroughBrickGap() {
        GameObject brick = new GameObject(100, 100, 60, 20, Color.GREY);
        GameObject brickTwo = new GameObject(162, 100, 60, 20, Color.GREY);
        Ball ball = new Ball(161, 110, 0, -8, 5, Color.WHITE);
    }
}
