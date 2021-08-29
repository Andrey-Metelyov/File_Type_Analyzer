package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.err.println("Arguments: " + Arrays.toString(args));
        if (args.length < 3) {
            System.out.println("Useage: java Main doc.pdf \"%PDF-\" \"PDF document\"");
            return;
        }
        String filename = args[0];
        String pattern = args[1];
        String description = args[2];
        if (hasPattern(filename, pattern)) {
            System.out.println(description);
        } else {
            System.out.println("Unknown file type");
        }
    }

    private static boolean hasPattern(String filename, String pattern) {
        try {
            byte[] allBytes = Files.readAllBytes(Paths.get(filename));
            byte[] patternBytes = pattern.getBytes();
            int i = 0;
            int j = 0;
            while (i < allBytes.length - patternBytes.length) {
                while (allBytes[i++] == patternBytes[j++]) {
                    if (j == patternBytes.length) {
                        return true;
                    }
                }
                j = 0;
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
