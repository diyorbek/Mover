enum ObstacleType {
    BOX
}

public class Obstacle extends Entity {
    private ObstacleType obstacleType;

    Obstacle(ObstacleType obstacleType, char displayValue) {
        super(EntityType.OBSTACLE, displayValue);
        this.obstacleType = obstacleType;
    }
}

