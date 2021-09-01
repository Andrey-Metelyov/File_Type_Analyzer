import java.util.Scanner;

public class Main {
    final static int a = 53;
    final static int m = 1_000_000_000 + 9;
    static long[] powers;
    static long[] hashes;

    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        int t = scanner.nextInt();

        precalculate(text);
        int counter = 0;
        for (int c = 0; c < t; c++) {
            int i = scanner.nextInt();
            int j = scanner.nextInt();
            int k = scanner.nextInt();
            int n = scanner.nextInt();
            if (i == j && k == n) {
                counter++;
                continue;
            }
            long first = substringHashOfPrehashedString(i, j);
            long second = substringHashOfPrehashedString(k, n);
//            System.err.println(first);
//            System.err.println(second);
//            System.err.println(k - i - 1);
//            System.err.println(powers[k - i - 1]);
//            System.err.println(first * powers[k - i - 1] % m);
            if (k > i) {
                if (first * powers[k - i] % m == second) {
                    counter++;
                }
            } else {
                if (first == second * powers[i - k] % m) {
                    counter++;
                }
            }
        }
        System.out.println(counter);
    }

    private static long[] precalculate(String text) {
        hashes = new long[text.length()];
        powers = new long[text.length()];

        long hash = 0;
        long pow = 1;
        for (int i = 0; i < hashes.length; i++) {
            powers[i] = pow;
            hash += charToLong(text.charAt(i)) * pow;
            hash %= m;
            hashes[i] = hash;
            pow = pow * a % m;
        }
        return hashes;
    }

    private static long substringHashOfPrehashedString(int start, int end) {
        long firstHash = start > 0 ? hashes[start - 1] : 0;
        long lastHash = hashes[end - 1];

//        System.err.println(end + "-" + start);
//        System.err.println(lastHash + "-" + firstHash);
        return (lastHash - firstHash + m) % m;
    }

    public static long charToLong(char ch) {
        return (long)(ch - 'A' + 1);
    }

//    public static List<Integer> RabinKarp(String text, String pattern) {
//        /* 2 */
//        int a = 53;
//        long m = 1_000_000_000 + 9;
//
//        /* 3 */
//        long patternHash = 0;
//        long currSubstrHash = 0;
//        long pow = 1;
//
//        for (int i = 0; i < pattern.length(); i++) {
//            patternHash += charToLong(pattern.charAt(i)) * pow;
//            patternHash %= m;
//
//            currSubstrHash += charToLong(text.charAt(text.length() - pattern.length() + i)) * pow;
//            currSubstrHash %= m;
//
//            if (i != pattern.length() - 1) {
//                pow = pow * a % m;
//            }
//        }
//
//        /* 4 */
//        ArrayList<Integer> occurrences = new ArrayList<>();
//
//        for (int i = text.length(); i >= pattern.length(); i--) {
//            if (patternHash == currSubstrHash) {
//                boolean patternIsFound = true;
//
//                for (int j = 0; j < pattern.length(); j++) {
//                    if (text.charAt(i - pattern.length() + j) != pattern.charAt(j)) {
//                        patternIsFound = false;
//                        break;
//                    }
//                }
////                for (int j = pattern.length() - 1; j >= 0; j--) {
////                    if (text.charAt(i - pattern.length() + j) != pattern.charAt(j)) {
////                        patternIsFound = false;
////                        break;
////                    }
////                }
//
//                if (patternIsFound) {
//                    occurrences.add(i - pattern.length());
//                }
//            }
//
//            if (i > pattern.length()) {
//                /* 5 */
//                currSubstrHash = (currSubstrHash - charToLong(text.charAt(i - 1)) * pow % m + m) * a % m;
//                currSubstrHash = (currSubstrHash + charToLong(text.charAt(i - pattern.length() - 1))) % m;
//            }
//        }
//
//        Collections.reverse(occurrences);
//        return occurrences;
//    }
}