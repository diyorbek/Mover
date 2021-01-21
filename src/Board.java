public class Board {
    public static final int WIDTH = 40;
    public static final int HEIGHT = 12;
    private final Entity[][] board = new Entity[HEIGHT][WIDTH];

    private final int matrixWidth = WIDTH + 2;
    private final int matrixHeight = HEIGHT + 2;
    private final char[][] matrix = new char[matrixHeight][matrixWidth];

    private final char BLANK_CELL = ' ';
    private final char HORIZONTAL_BORDER = '─';
    private final char VERTICAL_BORDER = '│';
    private final char TOP_LEFT_BORDER = '┌';
    private final char TOP_RIGHT_BORDER = '┐';
    private final char BOTTOM_LEFT_BORDER = '└';
    private final char BOTTOM_RIGHT_BORDER = '┘';

    Board() {
        initializeBoard();
        clearMatrix();
    }

    private void initializeBoard() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                board[i][j] = new EmptySpot();
            }
        }
    }

    public boolean isPositionEmpty(int x, int y) {
        return isInsideBoard(x, y) && board[y][x].getType() == EntityType.EMPTY_SPOT;
    }

    public boolean isInsideBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT;
    }

    public Entity select(int x, int y) {
        if (isInsideBoard(x, y)) {
            return board[y][x];
        }
        return null;
    }

    public boolean insert(int x, int y, Entity entity) {
        if (isPositionEmpty(x, y)) {
            board[y][x] = entity;
            return true;
        }
        System.out.println("Error setting entity.");
        return false;
    }

    public boolean erase(int x, int y) {
        if (isInsideBoard(x, y)) {
            board[y][x] = new EmptySpot();
            return true;
        }
        System.out.println("Error erasing entity.");
        return false;
    }

    private boolean canMoveEntity(Entity entity) {
        return entity != null && !(entity instanceof Obstacle);

    }

    public boolean moveEntityTo(int originX, int originY, int targetX, int targetY) {
        Entity origin = select(originX, originY);
        Entity target = select(targetX, targetY);

        if ((origin != null) && canMoveEntity(target)) {
            board[originY][originX] = target;
            board[targetY][targetX] = origin;

            return true;
        }

        return false;
    }

    public boolean moveEntityRight(int x, int y) {
        return moveEntityTo(x, y, x + 1, y);
    }

    public boolean moveEntityLeft(int x, int y) {
        return moveEntityTo(x, y, x - 1, y);
    }

    public boolean moveEntityUp(int x, int y) {
        return moveEntityTo(x, y, x, y - 1);
    }

    public boolean moveEntityDown(int x, int y) {
        return moveEntityTo(x, y, x, y + 1);
    }

    private void clearMatrix() {
        initializeMatrix();
        drawBorders();
    }

    private void initializeMatrix() {
        for (int i = 0; i < matrixHeight; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                matrix[i][j] = BLANK_CELL;
            }
        }
    }

    private void drawBorders() {
        matrix[0][0] = TOP_LEFT_BORDER;
        matrix[0][matrixWidth - 1] = TOP_RIGHT_BORDER;

        for (int i = 1; i < matrixWidth - 1; i++) {
            matrix[0][i] = HORIZONTAL_BORDER;
            matrix[matrixHeight - 1][i] = HORIZONTAL_BORDER;
        }

        matrix[matrixHeight - 1][0] = BOTTOM_LEFT_BORDER;
        matrix[matrixHeight - 1][matrixWidth - 1] = BOTTOM_RIGHT_BORDER;

        for (int i = 1; i < matrixHeight - 1; i++) {
            matrix[i][0] = VERTICAL_BORDER;
            matrix[i][matrixWidth - 1] = VERTICAL_BORDER;
        }
    }

    private void mirrorBoardToMatrix() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                matrix[i + 1][j + 1] = board[i][j].toChar();
            }
        }
    }

    public void display() {
        mirrorBoardToMatrix();

        for (int i = 0; i < matrixHeight; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }
}
