package analyzer.PatternSearch;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.floorMod;

public class PatternSearch {
//    public static void main(String[] args) {
//        String text = "DDABCD";
//        String pattern = "DDA";
//        RKSearch(text, pattern);
//    }

    public static List<Integer> RKSearch(String text, String pattern) {
        final int a = 3;
        final int m = 11;
        List<Integer> result = new ArrayList<>();
        char[] buffer = text.toCharArray();
        int patternLength = pattern.length();
        int pow = 1;
        for (int i = 1; i < patternLength; i++) {
            pow *= a;
        }
        int patternHash = hash(pattern, a, m);
        int suffixHash = hash(buffer, text.length() - patternLength, text.length(), a, m);
        int i = text.length() - pattern.length();
        while (i >= 0) {
//            System.out.println(patternHash);
//            System.out.println(suffixHash);
            int comparisons = 0;
            boolean isEqual = false;
            if (patternHash == suffixHash) {
                isEqual = true;
                for (int j = 0; j < patternLength; j++) {
                    comparisons++;
                    if (pattern.charAt(j) != buffer[i + j]) {
                        isEqual = false;
                        break;
                    }
                }

                if (isEqual) {
                    result.add(i);
                }
            }
            System.out.println(suffixHash + " " + comparisons + " " + (isEqual ? '1' : '0'));
            i--;
            if (i >= 0) {
                suffixHash =
                        (suffixHash - letterValue(buffer[i + patternLength]) * pow)
                                * a + letterValue(buffer[i]);
                suffixHash = floorMod(suffixHash, m);
//                System.out.println(suffixHash);
                System.out.println(hash(buffer, i, i + patternLength, a, m));
            }
        }
        return result;
    }

    private static int letterValue(char c) {
        return c - 'A' + 1;
    }

    private static int hash(final char[] buffer, int start, int end, int a, int m) {
        int result = 0;
        int pow = 1;

        System.out.print("hash of '");
        for (int i = start; i < end; i++) {
            System.out.print(buffer[i]);
            result += letterValue(buffer[i]) * pow;
            pow *= a;
        }
        System.out.println("'=" + result + '%' + m + '=' + result % m);

        return result % m;
    }


    private static int hash(final String string, int a, int m) {
        int result = 0;
        int pow = 1;

        for (int i = 0; i < string.length(); i++) {
            result += letterValue(string.charAt(i)) * pow;
            pow *= a;
        }

        return result % m;
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
