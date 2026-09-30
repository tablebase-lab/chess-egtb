package dev.michalrelich.tablebase.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class KingFoldersGenerator {

    public static void generateNumberDirectories(int begin, int end, Path path) {
        Path original = path;

        for (int i = begin; i <= end; i++) {
            path = path.resolve(i + "");

            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.err.println("A problem occurred");
            }
            path = original;
        }

    }
}
