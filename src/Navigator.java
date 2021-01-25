import java.util.Scanner;

public class Navigator {
    private static final Scanner scanner = new Scanner(System.in);

    public static void greet() {
        System.out.println("                                  \n" +
                "   ____ ___  ____ _   _____  _____\n" +
                "  / __ `__ \\/ __ \\ | / / _ \\/ ___/\n" +
                " / / / / / / /_/ / |/ /  __/ /    \n" +
                "/_/ /_/ /_/\\____/|___/\\___/_/     \n" +
                "                                  ");

        System.out.println("Welcome to Mover!");
        System.out.println("Game has multiple levels. At the beginning of each level\n" +
                "you will be shown a board wil boxes for 3 seconds.\n" +
                "Then boxes will be spread across the board randomly.\n" +
                "Your task is to move the boxes to turn the board to its initial state.\n" +
                "You have to manage to complete it before timer hits 0.\n\n" +
                "Controls: wsad\n" +
                "w - move up\n" +
                "s - move left\n" +
                "a - move down\n" +
                "d - move right\n" +
                "e - see target board\n\n" +
                "To push or pull a box hold SHIFT key and use controls.\n\n" +
                "Press ENTER to start Level 1 ...");

        scanner.nextLine();
    }


    public static void finishLevel(int levelNum) {
        System.out.println("\nLevel " + levelNum + " is completed!!!\n" +
                "Press ENTER to move to next level ...");
        scanner.nextLine();
    }

    public static void finishGame() {
        System.out.println("\nCONGRATULATIONS!!!\n" +
                "You have completed all the levels and win the game!\n\n" +
                "Press ENTER to exit the game ...");
        scanner.nextLine();
    }
}
