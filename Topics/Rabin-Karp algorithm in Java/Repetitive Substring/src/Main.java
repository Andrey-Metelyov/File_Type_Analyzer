import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        int k = scanner.nextInt();

//        for (int i = 0; i < text.length() - k; i++) {
        for (int i = text.length() - k; i >= 0; i--) {
            String pattern = text.substring(i, i + k);
            List<Integer> occurrences = RabinKarp(text, pattern);
            if (occurrences.size() > 1) {
                System.out.println(pattern);
                return;
            }
        }
        System.out.println("does not contain");
    }
    /* 1 */
    public static long charToLong(char ch) {
        return (long)(ch - 'A' + 1);
    }

    public static List<Integer> RabinKarp(String text, String pattern) {
        /* 2 */
        int a = 53;
        long m = 1_000_000_000 + 9;

        /* 3 */
        long patternHash = 0;
        long currSubstrHash = 0;
        long pow = 1;

        for (int i = 0; i < pattern.length(); i++) {
            patternHash += charToLong(pattern.charAt(i)) * pow;
            patternHash %= m;

            currSubstrHash += charToLong(text.charAt(text.length() - pattern.length() + i)) * pow;
            currSubstrHash %= m;

            if (i != pattern.length() - 1) {
                pow = pow * a % m;
            }
        }

        /* 4 */
        ArrayList<Integer> occurrences = new ArrayList<>();

        for (int i = text.length(); i >= pattern.length(); i--) {
            if (patternHash == currSubstrHash) {
                boolean patternIsFound = true;

                for (int j = 0; j < pattern.length(); j++) {
                    if (text.charAt(i - pattern.length() + j) != pattern.charAt(j)) {
                        patternIsFound = false;
                        break;
                    }
                }
//                for (int j = pattern.length() - 1; j >= 0; j--) {
//                    if (text.charAt(i - pattern.length() + j) != pattern.charAt(j)) {
//                        patternIsFound = false;
//                        break;
//                    }
//                }

                if (patternIsFound) {
                    occurrences.add(i - pattern.length());
                }
            }

            if (i > pattern.length()) {
                /* 5 */
                currSubstrHash = (currSubstrHash - charToLong(text.charAt(i - 1)) * pow % m + m) * a % m;
                currSubstrHash = (currSubstrHash + charToLong(text.charAt(i - pattern.length() - 1))) % m;
            }
        }

        Collections.reverse(occurrences);
        return occurrences;
    }
}