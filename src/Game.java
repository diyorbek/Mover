import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private static Timer tmr = new Timer();

    private static Board board = new Board();
    private static Player player = new Player('█', board);

    public static void main(String[] args) {
        Box.randomlyPlaceOnBoard(board, 10);

        tmr.schedule(new MainLoop(), 0, 100);
    }

    static class MainLoop extends TimerTask {
        public void run() {
            try {
                ConsoleHelpers.setTerminalToCBreak();
                ConsoleHelpers.clearConsole();

                displayBoard();
            } catch (IOException | InterruptedException e) {
                System.err.println("IOException");
            } finally {
                try {
                    ConsoleHelpers.stty(ConsoleHelpers.ttyConfig.trim());
                } catch (Exception e) {
                    System.err.println("Exception restoring tty config");
                }
            }
        }

        public void displayBoard() {
            board.display();

            try {
                if (System.in.available() != 0) {
                    int c = System.in.read();

                    switch (c) {
                        case 'd':
                            player.moveRight();
                            break;

                        case 'a':
                            player.moveLeft();
                            break;

                        case 'w':
                            player.moveUp();
                            break;

                        case 's':
                            player.moveDown();
                            break;

                        case 'q': {
                            tmr.cancel();
                            tmr.purge();
                            break;
                        }

                        default:
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}