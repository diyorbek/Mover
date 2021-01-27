import level.Level;
import level.LevelCollection;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
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
    private static final int CURRENT_LEVEL_TIMER_PERIOD = 1000;
    private static int currentLevelTimeLimit; // milliseconds
    private static String formattedCurrentLevelTimeLimit;
    private static String timerStopsAt = "";

    private static Timer MAIN_LOOP_TIMER;
    private static final int MAIN_LOOP_TIMER_DELAY = 3000; // milliseconds

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Navigator.greet();
        initGame(currentLevelNum);
    }

    public static void initGame(int levelNum) {
        Level currentLevel = LEVEL_COLLECTION.getLevel(levelNum);

        targetBoard = Board.createBoard(currentLevel.getMap());
        board = Board.createBoard(currentLevel.getMap());
        activeBoard = board;

        player = new Player(PLAYER_AVATAR, activeBoard);

        Board.randomizeObstacles(activeBoard);

        // Displays preview until `MAIN_LOOP_TIMER_DELAY` ends
        previewCurrentLevelTarget();

        // Start level timer earlier
        // with margin of MAIN_LOOP_TIMER_DELAY
        // to initialize timer info
        currentLevelTimeLimit = currentLevel.getTimeLimit() * 1000 + MAIN_LOOP_TIMER_DELAY;
        CURRENT_LEVEL_TIMER = new Timer();
        CURRENT_LEVEL_TIMER.schedule(new CurrentLevelTimerLoop(), 0, CURRENT_LEVEL_TIMER_PERIOD);

        // Start displaying level with randomized board after delay
        // Add 10ms extra margin to catch up with CURRENT_LEVEL_TIMER's latest updates
        MAIN_LOOP_TIMER = new Timer();
        MAIN_LOOP_TIMER.schedule(new MainLoop(), MAIN_LOOP_TIMER_DELAY + 10);
    }

    public static void previewCurrentLevelTarget() {
        Navigator.clearConsole();
        System.out.println("Level " + currentLevelNum);
        targetBoard.display();
        System.out.println("Start in " + (MAIN_LOOP_TIMER_DELAY / 1000) + "s");
    }

    public static void finishCurrentLevel() {
        stopCurrentLevelTimer();
        MAIN_LOOP_TIMER.cancel();
        MAIN_LOOP_TIMER.purge();

        // Finish game after completing last level
        if (currentLevelNum + 1 > LEVEL_COLLECTION.getLength()) {
            Navigator.finishGame();
            return;
        }

        Navigator.finishLevel(currentLevelNum);
        initGame(++currentLevelNum);
    }

    public static void loseCurrentLevel() {
        Navigator.loseLevel();
        initGame(currentLevelNum = 1); // Restart from level 1
    }

    public static void stopCurrentLevelTimer() {
        CURRENT_LEVEL_TIMER.cancel();
        CURRENT_LEVEL_TIMER.purge();
    }

    static class CurrentLevelTimerLoop extends TimerTask {
        public void run() {
            if (currentLevelTimeLimit < 0) {
                stopCurrentLevelTimer();
                return;
            }

            if (timerStopsAt.equals("")) {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                long endTimestamp = new Date().getTime() + currentLevelTimeLimit;
                Date date = new Date(endTimestamp);
                timerStopsAt = formatter.format(date);
            }

            String formattedString = "";
            int mins = currentLevelTimeLimit / 1000 / 60;
            int seconds = (currentLevelTimeLimit / 1000) % 60;

            if (mins > 0) {
                formattedString += (mins + "m ");
            }
            formattedString += (seconds + "s");

            formattedCurrentLevelTimeLimit = formattedString;
            currentLevelTimeLimit -= CURRENT_LEVEL_TIMER_PERIOD;
        }
    }

    static class MainLoop extends TimerTask {
        public void run() {
            while (true) {
                Navigator.clearConsole();

                boolean shouldStopLoop = displayBoard();
                if (shouldStopLoop) {
                    return;
                }

                // Read user commands in bulk and execute
                String userInput = scanner.nextLine();
                for (int i = 0; i < userInput.length(); i++) {
                    executeUserCommand(userInput.charAt(i));
                }
            }
        }

        public boolean displayBoard() {
            if (isTargetVisible) {
                activeBoard = targetBoard;
            }

            System.out.println("Level " + currentLevelNum);
            activeBoard.display();
            System.out.println("Time left: " + formattedCurrentLevelTimeLimit);
            System.out.println("Timer stops at: " + timerStopsAt);

            if (isTargetVisible) {
                activeBoard = board;
                isTargetVisible = false;
            }

            if (Board.matches(activeBoard, targetBoard, PLAYER_AVATAR)) {
                finishCurrentLevel();
                return true;
            }

            if (currentLevelTimeLimit < 0) {
                loseCurrentLevel();
                return true;
            }

            return false;
        }

        public void executeUserCommand(char input) {
            switch (input) {
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