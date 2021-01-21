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

    public boolean moveRight() {
        if (board.moveEntityRight(position.x, position.y)) {
            ++position.x;
            return true;
        }
        return false;
    }

    public boolean moveLeft() {
        if (board.moveEntityLeft(position.x, position.y)) {
            --position.x;
            return true;
        }
        return false;
    }

    public boolean moveUp() {
        if (board.moveEntityUp(position.x, position.y)) {
            --position.y;
            return true;
        }
        return false;
    }

    public boolean moveDown() {
        if (board.moveEntityDown(position.x, position.y)) {
            ++position.y;
            return true;
        }
        return false;
    }

    public boolean pushRight() {
        if (board.select(position.x + 1, position.y) instanceof Obstacle) {
            if (board.moveEntityRight(position.x + 1, position.y)) {
                return moveRight();
            }
        }
        return pullRight();
    }

    public boolean pushLeft() {
        if (board.select(position.x - 1, position.y) instanceof Obstacle) {
            if (board.moveEntityLeft(position.x - 1, position.y)) {
                return moveLeft();
            }
        }
        return pullLeft();
    }

    public boolean pushUp() {
        if (board.select(position.x, position.y - 1) instanceof Obstacle) {
            if (board.moveEntityUp(position.x, position.y - 1)) {
                return moveUp();
            }
        }
        return pullUp();
    }

    public boolean pushDown() {
        if (board.select(position.x, position.y + 1) instanceof Obstacle) {
            if (board.moveEntityDown(position.x, position.y + 1)) {
                return moveDown();
            }
        }
        return pullDown();
    }

    public boolean pullRight() {
        if (board.select(position.x - 1, position.y) instanceof Obstacle) {
            if (moveRight()) {
                return board.moveEntityRight(position.x - 2, position.y);
            }
        }
        return false;
    }

    public boolean pullLeft() {
        if (board.select(position.x + 1, position.y) instanceof Obstacle) {
            if (moveLeft()) {
                return board.moveEntityLeft(position.x + 2, position.y);
            }
        }
        return false;
    }

    public boolean pullUp() {
        if (board.select(position.x, position.y + 1) instanceof Obstacle) {
            if (moveUp()) {
                return board.moveEntityUp(position.x, position.y + 2);
            }
        }
        return false;
    }

    public boolean pullDown() {
        if (board.select(position.x, position.y - 1) instanceof Obstacle) {
            if (moveDown()) {
                return board.moveEntityDown(position.x, position.y - 2);
            }
        }
        return false;
    }
}
