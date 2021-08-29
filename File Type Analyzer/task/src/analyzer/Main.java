package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static java.lang.System.nanoTime;

public class Main {
    public static void main(String[] args) {
        System.err.println("Arguments: " + Arrays.toString(args));
        if (args.length < 4) {
            System.out.println("java Main --naive huge_doc.pdf \"%PDF-\" \"PDF document\"");
            return;
        }
        String algorithm = args[0];
        String filename = args[1];
        String pattern = args[2];
        String description = args[3];

        try {
            byte[] allBytes = Files.readAllBytes(Paths.get(filename));
//            System.err.println(Arrays.toString(allBytes));
            byte[] patternBytes = pattern.getBytes();
//            System.err.println(Arrays.toString(patternBytes));

            int result;
            long start = nanoTime();
            if (algorithm.equals("--naive")) {
                result = hasPattern(allBytes, patternBytes);
            } else {
                result = KMPSearchFirst(allBytes, patternBytes);
            }
            long elapsed = nanoTime() - start;
            System.err.println(result);
            if (result != -1) {
                System.out.println(description);
            } else {
                System.out.println("Unknown file type");
            }
            String el = String.format("%.3f", elapsed / 1000000.0);
            System.out.println("It took " + el + " seconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int hasPattern(byte[] allBytes, byte[] patternBytes) {
//        System.err.println(Arrays.toString(patternBytes));
//        System.err.println(Arrays.toString(allBytes));
        int i = 0;
        int j = 0;
        while (i < allBytes.length) {
            while (allBytes[i++] == patternBytes[j++]) {
                if (j == patternBytes.length) {
                    return i - j;
                }
            }
            i = i - j + 1;
            j = 0;
        }
        return -1;
    }

    public static int KMPSearchFirst(byte[] text, byte[] pattern) {
//        System.err.println(Arrays.toString(text) + " " + Arrays.toString(pattern));
        /* 1 */
        int[] prefixFunc = prefixFunction(pattern);
//        ArrayList<Integer> occurrences = new ArrayList<Integer>();
        int j = 0;
        /* 2 */
        for (int i = 0; i < text.length; i++) {
            /* 3 */
            while (j > 0 && text[i] != pattern[j]) {
                j = prefixFunc[j - 1];
            }
            /* 4 */
            if (text[i] == pattern[j]) {
                j += 1;
            }
            /* 5 */
            if (j == pattern.length) {
                return i - j + 1;
            }
        }
        /* 6 */
        return -1;
    }

    public static int[] prefixFunction(byte[] str) {
        /* 1 */
        int[] prefixFunc = new int[str.length];

        /* 2 */
        for (int i = 1; i < str.length; i++) {
            /* 3 */
            int j = prefixFunc[i - 1];

            while (j > 0 && str[i] != str[j]) {
                j = prefixFunc[j - 1];
            }

            /* 4 */
            if (str[i] == str[j]) {
                j += 1;
            }

            /* 5 */
            prefixFunc[i] = j;
        }

        /* 6 */
        return prefixFunc;
    }
}
