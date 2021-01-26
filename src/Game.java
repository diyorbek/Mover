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
    private static final char PLAYER_AVATAR = '█';

    private static final LevelCollection LEVEL_COLLECTION = new LevelCollection();
    private static int currentLevelNum = 1;

    private static Timer CURRENT_LEVEL_TIMER;
    private static int currentLevelTimeLimit;
    private static String formattedCurrentLevelTimeLimit;

    private static Timer PREVIEW_LOOP_TIMER;
    private static int previewTimeLimit = 3; // seconds

    private static Timer MAIN_LOOP_TIMER;
    private static int MAIN_LOOP_TIMER_DELAY = 3000; // milliseconds
    private static int MAIN_LOOP_TIMER_PERIOD = 100; // milliseconds

    public static void main(String[] args) {
        Navigator.greet();

        initGame(currentLevelNum);
    }

    public static void initGame(int levelNum) {
        Level currentLevel = LEVEL_COLLECTION.getLevel(levelNum);
        currentLevelTimeLimit = currentLevel.getTimeLimit();
        previewTimeLimit = 3;

        targetBoard = Board.createBoard(currentLevel.getMap());
        board = Board.createBoard(currentLevel.getMap());
        activeBoard = board;

        player = new Player(PLAYER_AVATAR, activeBoard);

        Board.randomizeObstacles(activeBoard);

        // Display preview for 3 seconds
        PREVIEW_LOOP_TIMER = new Timer();
        PREVIEW_LOOP_TIMER.schedule(new PreviewLoop(), 0, 1000);

        // Start level timer
        CURRENT_LEVEL_TIMER = new Timer();
        CURRENT_LEVEL_TIMER.schedule(new CurrentLevelTimerLoop(), MAIN_LOOP_TIMER_DELAY, 1000);

        // Start displaying level with randomized board
        MAIN_LOOP_TIMER = new Timer();
        MAIN_LOOP_TIMER.schedule(new MainLoop(), MAIN_LOOP_TIMER_DELAY, MAIN_LOOP_TIMER_PERIOD);
    }

    public static void finishLevel() {
        MAIN_LOOP_TIMER.cancel();
        MAIN_LOOP_TIMER.purge();
        CURRENT_LEVEL_TIMER.cancel();
        CURRENT_LEVEL_TIMER.purge();

        if (currentLevelNum + 1 > LEVEL_COLLECTION.getLength()) {
            Navigator.finishGame();

            return;
        } else {
            Navigator.finishLevel(currentLevelNum);
        }

        initGame(++currentLevelNum);
    }

    public static void loseLevel() {
        MAIN_LOOP_TIMER.cancel();
        MAIN_LOOP_TIMER.purge();
        CURRENT_LEVEL_TIMER.cancel();
        CURRENT_LEVEL_TIMER.purge();

        Navigator.loseLevel();

        initGame(currentLevelNum = 1);
    }

    static class PreviewLoop extends TimerTask {
        public void run() {
            ConsoleHelpers.clearConsole();

            if (previewTimeLimit < 0) {
                PREVIEW_LOOP_TIMER.cancel();
                PREVIEW_LOOP_TIMER.purge();
            }

            System.out.println("Level " + currentLevelNum);
            targetBoard.display();
            System.out.println("Start in " + (previewTimeLimit--) + "s");
        }
    }

    static class CurrentLevelTimerLoop extends TimerTask {
        public void run() {
            String formattedString = "";
            int mins = currentLevelTimeLimit / 60;
            int seconds = currentLevelTimeLimit % 60;

            if (mins > 0) {
                formattedString += (mins + "m ");
            }
            formattedString += (seconds + "s");

            formattedCurrentLevelTimeLimit = formattedString;
            currentLevelTimeLimit--;
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
            System.out.println("Level " + currentLevelNum);

            if (isTargetVisible) {
                activeBoard = targetBoard;
            }

            activeBoard.display();
            System.out.println("Time left: " + formattedCurrentLevelTimeLimit);

            if (isTargetVisible) {
                activeBoard = board;
                isTargetVisible = false;
            }

            if (Board.matches(activeBoard, targetBoard, PLAYER_AVATAR)) {
                finishLevel();

                return;
            }

            if (currentLevelTimeLimit < 0) {
                loseLevel();

                return;
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