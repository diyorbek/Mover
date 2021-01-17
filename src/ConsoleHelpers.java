import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ConsoleHelpers {
    public static String ttyConfig;

    public static void clearConsole() {
        final String os = System.getProperty("os.name");

        try {
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setTerminalToCBreak() throws IOException, InterruptedException {
        ttyConfig = stty("-g");
        // set the console to be character-buffered instead of line-buffered
        stty("-icanon min 1");
        // disable character echoing
        stty("-echo");
    }

    /**
     * Execute the stty command with the specified arguments
     * against the current active terminal.
     */
    public static String stty(final String args)
            throws IOException, InterruptedException {
        String cmd = "stty " + args + " < /dev/tty";

        return exec(new String[]{
                "sh",
                "-c",
                cmd
        });
    }

    /**
     * Execute the specified command and return the output
     * (both stdout and stderr).
     */
    public static String exec(final String[] cmd)
            throws IOException, InterruptedException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        Process p = Runtime.getRuntime().exec(cmd);
        InputStream in = p.getInputStream();

        int c;

        while ((c = in.read()) != -1) {
            bout.write(c);
        }

        in = p.getErrorStream();

        while ((c = in.read()) != -1) {
            bout.write(c);
        }

        p.waitFor();

        return new String(bout.toByteArray());
    }

}
