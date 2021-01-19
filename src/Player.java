public class Player {
    private int[] currentPosition = new int[]{0, 0};
    private char avatar;

    Player(char avatar, Board board) {
        this.avatar = avatar;
        board.drawElement(currentPosition[0], currentPosition[1], this.avatar);
    }

    public int[] getCurrentPosition() {
        return currentPosition;
    }

    public void moveRight(Board board) {
        if (board.isCellAvailable(currentPosition[0], currentPosition[1] + 1)) {
            board.removeElement(currentPosition[0], currentPosition[1]);
            ++currentPosition[1];
            board.drawElement(currentPosition[0], currentPosition[1], this.avatar);
        }
    }
}
