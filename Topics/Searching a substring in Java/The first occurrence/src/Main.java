import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine();
        String text = scanner.nextLine();
//        String pattern = "aca";
//        String text = "acbacad";
        System.out.println(indexOf(text, pattern));
    }

    public static int indexOf(String text, String pattern) {
        if (text.length() < pattern.length()) {
            return -1;
        }
        if (pattern.isEmpty()) {
            return 0;
        }

        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
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