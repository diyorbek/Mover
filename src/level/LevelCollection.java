package level;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LevelCollection {
    private final Level[] levels;
    private final int length;

    public LevelCollection() {
        File folder = new File("level");
        File[] files = folder.listFiles();

        int filesCount = 0;

        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            File file = files[i];
            if (file.getName().endsWith(".txt")) {
                filesCount++;
            }
        }

        levels = new Level[filesCount];
        for (int i = 0, j = 0; i < files.length; i++) {
            File file = files[i];

            if (file.getName().endsWith(".txt")) {
                try {
                    levels[j++] = new Level(file.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        length = levels.length;
    }

    public Level getLevel(int n) {
        return levels[n - 1];
    }

    public int getLength() {
        return length;
    }
}
