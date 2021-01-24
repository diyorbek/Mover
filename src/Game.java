import level.Level;
import level.LevelCollection;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private static Timer TIMER;
    private static Board board;
    private static Board activeBoard;
    private static Board targetBoard;
    private static Player player;
    private static boolean isTargetVisible = false;
    private static final char PLAYER_AVATAR = '█';


    private static final LevelCollection LEVEL_COLLECTION = new LevelCollection();
    private static int currentLevelNum = 0;

    public static void main(String[] args) {
        initGame(currentLevelNum);
    }

    public static void initGame(int levelNum) {
        TIMER = new Timer();
        Level currentLevel = LEVEL_COLLECTION.getLevel(levelNum);

        if (currentLevel != null) {
            targetBoard = Board.createBoard(currentLevel.getMap());
            board = Board.createBoard(currentLevel.getMap());
            activeBoard = board;

            player = new Player(PLAYER_AVATAR, activeBoard);

            Board.randomizeObstacles(activeBoard);

            TIMER.schedule(new MainLoop(), 0, 100);
        }
    }

    public static void finishLevel() {
        TIMER.cancel();
        TIMER.purge();

        initGame(currentLevelNum);
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
            if (Board.matches(activeBoard, targetBoard, PLAYER_AVATAR)) {
                activeBoard.display();
                finishLevel();

                return;
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