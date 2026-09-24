package breakout.gameObject;

import javafx.scene.paint.Color;

public class PowerUp extends GameObject {

    private double vy;
    private PowerUpType powerUpType;
    private int maxDuration;
    private int duration;
    private PowerUpState powerUpState;

    public enum PowerUpType {
        BIGGER_PADDLE
    }
    public enum PowerUpState {
        WAITING,
        FALLING,
        REMOVED,
        ACTIVE,
        EXPIRED,
    }

    public PowerUp(double x, double y, Color color, double vy, PowerUpType powerUpType, int maxDuration) {
        super(x, y, 6, 6, color);
        this.vy = vy;
        this.powerUpType = powerUpType;
        this.duration = 0;
        this.maxDuration = maxDuration;
        this.powerUpState = PowerUpState.WAITING;
    }
    public void update() {
        if (powerUpState == PowerUpState.FALLING)
            y += vy;
        if (powerUpState == PowerUpState.ACTIVE)
            duration++;
        if (duration == maxDuration)
            powerUpState = PowerUpState.EXPIRED;
    }
    public PowerUpType getPowerUpType() {
        return powerUpType;
    }
    public PowerUpState getPowerUpState() {
        return powerUpState;
    }
    public void setPowerUpState(PowerUpState powerUpState) {
        this.powerUpState = powerUpState;
    }
}
