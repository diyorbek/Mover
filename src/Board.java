public class Board {
    private final int width = 40;
    private final int height = 12;
    private final int matrixWidth = width + 2;
    private final int matrixHeight = height + 2;

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
                matrix[i][j] = ' ';
            }
        }
    }

    private void drawBorders() {
        matrix[0][0] = '┌';
        matrix[0][matrixWidth - 1] = '┐';

        for (int i = 1; i < matrixWidth - 1; i++) {
            matrix[0][i] = '─';
            matrix[matrixHeight - 1][i] = '─';
        }

        matrix[matrixHeight - 1][0] = '└';
        matrix[matrixHeight - 1][matrixWidth - 1] = '┘';

        for (int i = 1; i < matrixHeight - 1; i++) {
            matrix[i][0] = '│';
            matrix[i][matrixWidth - 1] = '│';
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

    public void drawElement(char element, int row, int column) {
        if (row < height && column < width) {
            matrix[row + 1][column + 1] = element;
        } else {
            System.out.println("Error drawing element.");
        }
    }
}
