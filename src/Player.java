public class Player extends Entity {
    private class Position {
        public int x, y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private final Position position = new Position(0, 0);
    private final Board board;

    Player(char displayValue, Board board) {
        super(EntityType.PLAYER, displayValue);
        this.board = board;
        this.board.insert(position.x, position.y, this);
    }

    public int[] getCurrentPosition() {
        return new int[]{position.x, position.y};
    }

    public void moveRight() {
        if (this.board.isPositionEmpty(position.x + 1, position.y)) {
            this.board.erase(position.x, position.y);
            ++position.x;
            this.board.insert(position.x, position.y, this);
        }
    }

    public boolean moveLeft() {
        if (board.moveEntityLeft(position.x, position.y)) {
            --position.x;
            return true;
        }
        return false;
    }

    public boolean pushLeft() {
        if (board.get(position.x - 1, position.y) instanceof Obstacle) {
            if (board.moveEntityLeft(position.x - 1, position.y)) {
                return moveLeft();
            }
        }
        return false;
    }

    public void moveUp() {
        if (this.board.isPositionEmpty(position.x, position.y - 1)) {
            this.board.erase(position.x, position.y);
            --position.y;
            this.board.insert(position.x, position.y, this);
        }
    }

    public void moveDown() {
        if (this.board.isPositionEmpty(position.x, position.y + 1)) {
            this.board.erase(position.x, position.y);
            ++position.y;
            this.board.insert(position.x, position.y, this);
        }
    }
}
