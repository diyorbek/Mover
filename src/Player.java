public class Player {
    private final int[] currentPosition = new int[]{0, 0};
    private final char avatar;
    private final Board board;

    Player(char avatar, Board board) {
        this.avatar = avatar;
        this.board = board;

        this.board.drawElement(currentPosition[0], currentPosition[1], this.avatar);
    }

    public int[] getCurrentPosition() {
        return currentPosition;
    }

    public void moveRight() {
        if (this.board.isCellAvailable(currentPosition[0], currentPosition[1] + 1)) {
            this.board.removeElement(currentPosition[0], currentPosition[1]);
            ++currentPosition[1];
            this.board.drawElement(currentPosition[0], currentPosition[1], this.avatar);
        }
    }

    public void moveLeft() {
        if (this.board.isCellAvailable(currentPosition[0], currentPosition[1] - 1)) {
            this.board.removeElement(currentPosition[0], currentPosition[1]);
            --currentPosition[1];
            this.board.drawElement(currentPosition[0], currentPosition[1], this.avatar);
        }
    }

    public void moveUp() {
        if (this.board.isCellAvailable(currentPosition[0] - 1, currentPosition[1])) {
            this.board.removeElement(currentPosition[0], currentPosition[1]);
            --currentPosition[0];
            this.board.drawElement(currentPosition[0], currentPosition[1], this.avatar);
        }
    }

    public void moveDown() {
        if (this.board.isCellAvailable(currentPosition[0] + 1, currentPosition[1] - 1)) {
            this.board.removeElement(currentPosition[0], currentPosition[1]);
            ++currentPosition[0];
            this.board.drawElement(currentPosition[0], currentPosition[1], this.avatar);
        }
    }
}
