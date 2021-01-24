package level;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Level {
    private final int mapWidth = 40;
    private final int mapHeight = 40;
    private final char[][] map = new char[mapHeight][mapWidth];

    Level(String sourceFilePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(sourceFilePath);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        String lineString;
        int currentLine = 0;

        while ((lineString = bufferedReader.readLine()) != null) {
            map[currentLine++] = lineString.toCharArray();
        }

        fileInputStream.close();
    }

    public char[][] getMap() {
        return map;
    }
}
