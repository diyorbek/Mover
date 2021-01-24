import level.Level;
import level.LevelCollection;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private static Timer tmr = new Timer();
    private static Board board;
    private static Player player;
    private static LevelCollection levelCollection = new LevelCollection();
    private static Level currentLevel = levelCollection.getLevel(0);

    public static void main(String[] args) {
        if (currentLevel != null) {
            board = Board.createBoard(currentLevel.getMap());
            player = new Player('█', board);

            Board.randomizeObstacles(board);


            tmr.schedule(new MainLoop(), 0, 100);
        }
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
                    listenKeyboard();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void listenKeyboard() throws IOException {
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

                case 'D':
                    player.pushRight();
                    break;

                case 'A':
                    player.pushLeft();
                    break;

                case 'W':
                    player.pushUp();
                    break;

                case 'S':
                    player.pushDown();
                    break;

                default:
                    break;
            }
        }
    }
}