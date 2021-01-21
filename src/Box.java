public class Box extends Obstacle {
    Box() {
        super(ObstacleType.BOX, '#');
    }

    public static void randomlyPlaceOnBoard(Board board, int count) {
        for (int i = 0; i < count; i++) {
            int x, y;

            do {
                x = (int) (Math.random() * Board.WIDTH);
                y = (int) (Math.random() * Board.HEIGHT);
            } while (!board.isPositionEmpty(x, y));

            board.set(x, y, new Box());
        }
    }
}
