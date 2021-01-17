public class Board {
    private int width = 40;
    private int height = 12;
    private int matrixWidth = width + 2;
    private int matrixHeight = height + 2;

    private char[][] matrix = new char[matrixHeight][matrixWidth];

    Board() {
        for (int i = 0; i < matrixHeight; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                matrix[i][j] = ' ';
            }
        }

        drawBorders();
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
}
