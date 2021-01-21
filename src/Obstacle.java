enum ObstacleType {
    BOX
}

public class Obstacle extends Entity {
    private ObstacleType obstacleType;
    private boolean isMovable;

    Obstacle(ObstacleType obstacleType, boolean isMovable, char displayValue) {
        super(EntityType.OBSTACLE, displayValue);
        this.obstacleType = obstacleType;
        this.isMovable = isMovable;
    }


    public ObstacleType getObstacleType() {
        return obstacleType;
    }

    public boolean isMovable() {
        return isMovable;
    }
}

