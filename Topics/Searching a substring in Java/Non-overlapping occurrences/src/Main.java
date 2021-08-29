import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine();
        String text = scanner.nextLine();
        List<Integer> positions = new ArrayList<>();
        if (pattern.isEmpty()) {
            positions.add(0);
        } else {
            int index = 0;
            while ((index = indexOf(text, pattern, index)) != -1) {
                positions.add(index);
                index += pattern.length();
            }
        }
        System.out.println(positions.size());
        System.out.println(positions.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" ")));
    }

    public static int indexOf(String text, String pattern, int start) {
        if (text.length() - start < pattern.length()) {
            return -1;
        }
        if (pattern.isEmpty()) {
            return 0;
        }

        for (int i = start; i < text.length() - pattern.length() + 1; i++) {
            boolean patternIsFound = true;

            for (int j = 0; j < pattern.length(); j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    patternIsFound = false;
                    break;
                }
            }

            if (patternIsFound) {
                System.err.println(i);
                return i;
            }
        }

        return -1;
    }
}