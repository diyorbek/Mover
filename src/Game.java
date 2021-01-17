import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private static int i = 0;
    private static Board board = new Board();
    private static Timer tmr = new Timer();

    public static void main(String[] args) {
        tmr.schedule(new MainLoop(), 0, 300);
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

                    if (c == 'q') {
                        tmr.cancel();
                        tmr.purge();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}