public class Board {
    private final char BLANK_CELL = ' ';
    private final char HORIZONTAL_BORDER = '─';
    private final char VERTICAL_BORDER = '│';
    private final char TOP_LEFT_BORDER = '┌';
    private final char TOP_RIGHT_BORDER = '┐';
    private final char BOTTOM_LEFT_BORDER = '└';
    private final char BOTTOM_RIGHT_BORDER = '┘';

    public static final int WIDTH = 40;
    public static final int HEIGHT = 12;
    private final int matrixWidth = WIDTH + 2;
    private final int matrixHeight = HEIGHT + 2;

    private final char[][] matrix = new char[matrixHeight][matrixWidth];

    Board() {
        clearBoard();
    }

    private void clearBoard() {
        initMatrix();
        drawBorders();
    }

    private void initMatrix() {
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

    public void display() {
        for (int i = 0; i < matrixHeight; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }

    public void drawElement(int row, int column, char element) {
        if (isCellAvailable(row, column)) {
            setMatrixValue(row, column, element);
        } else {
            System.out.println("Error drawing element.");
        }
    }

    public void removeElement(int row, int column) {
        if (isInsideBoard(row, column)) {
            setMatrixValue(row, column, BLANK_CELL);
        } else {
            System.out.println("Error removing element.");
        }
    }

    public char getMatrixValue(int row, int column) {
        return matrix[row + 1][column + 1];
    }

    public void setMatrixValue(int row, int column, char value) {
        matrix[row + 1][column + 1] = value;
    }

    public boolean isCellAvailable(int row, int column) {
        return isInsideBoard(row, column) && matrix[row + 1][column + 1] == BLANK_CELL;
    }

    public boolean isInsideBoard(int row, int column) {
        return row < HEIGHT && column < WIDTH;
    }

    public void randomlyPlaceObstacles(int count, char obstacle) {
        for (int i = 0; i < count; i++) {
            int row, column;

            do {
                row = (int) (Math.random() * Board.HEIGHT);
                column = (int) (Math.random() * Board.WIDTH);
            } while (!isCellAvailable(row, column));

            drawElement(row, column, obstacle);
        }
    }
}
