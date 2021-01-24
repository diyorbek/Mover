import level.Level;
import level.LevelCollection;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private static Board board;
    private static Board activeBoard;
    private static Board targetBoard;
    private static Player player;
    private static boolean isTargetVisible = false;
    private static final char PlayerAvatar = '█';

    private static final Timer tmr = new Timer();
    private static final LevelCollection levelCollection = new LevelCollection();
    private static final Level currentLevel = levelCollection.getLevel(0);

    public static void main(String[] args) {
        if (currentLevel != null) {
            targetBoard = Board.createBoard(currentLevel.getMap());
            board = Board.createBoard(currentLevel.getMap());
            activeBoard = board;

            player = new Player(PlayerAvatar, activeBoard);

            Board.randomizeObstacles(activeBoard);

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
            if (Board.matches(activeBoard, targetBoard, PlayerAvatar)) {
                tmr.cancel();
                tmr.purge();
            }

            if (isTargetVisible) {
                activeBoard = targetBoard;
            }

            activeBoard.display();

            if (isTargetVisible) {
                activeBoard = board;
                isTargetVisible = false;
            }

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

                case 'e':
                    isTargetVisible = true;
                    break;

                default:
                    break;
            }
        }
    }
}