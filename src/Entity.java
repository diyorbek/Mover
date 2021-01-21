enum EntityType {
    EMPTY_SPOT,
    OBSTACLE,
    PLAYER
}

public abstract class Entity {
    private EntityType type;
    private char displayValue;
//    public Position position;

    Entity(EntityType type, char displayValue) {
        this.type = type;
        this.displayValue = displayValue;
//        position = new Position(x, y);
    }

    public EntityType getType() {
        return type;
    }


    public char toChar() {
        return displayValue;
    }

//    private class Position {
//        public int x, y;
//
//        Position(int x, int y) {
//            this.x = x;
//            this.y = y;
//        }
//    }
}
