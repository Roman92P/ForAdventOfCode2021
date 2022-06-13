package org.pashkov.AoCUtills;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Roman Pashkov created on 13.06.2022 inside the package - org.pashkov.AoCUtills
 */
public class AoCInputReader {
    public static List<String> readInputFromFile(String filename) {
        Path path = Paths.get(filename);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
