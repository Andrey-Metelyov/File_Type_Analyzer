import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static int a = 53;
    static int m = 1_000_000_000 + 9;
//    static long[] hashes;
    static long[] powers;
//    static boolean debug = true;
    static boolean debug = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
//        String string = "abacabad";
//        hashes = new long[string.length()];
        powers = new long[string.length()];
        long hash = 0;
        long pow = 1;
        for (int i = 0; i < string.length(); i++) {
            powers[i] = pow;
//            hash += charToLong(string.charAt(i)) * pow;
//            hash %= m;
            pow = (pow * a % m + m) % m;
//            hashes[i] = hash;
        }
//        // if (debug) System.err.println(Arrays.toString(hashes));

        Set<Long> existsHashes = new HashSet<>();
        int counter = 0;
        for (int i = 1; i < string.length(); i++) {
            int window = i;
            hash = 0;
            // if (debug) System.err.println("window=" + window);
            for (int j = 0; j < window; j++) {
                hash += charToLong(string.charAt(j)) * powers[window - j - 1];
                // if (debug) System.err.println("0. " + hash);
            }
            // if (debug) System.err.println(string.substring(0, window) + ": " + hash);
            if (!existsHashes.contains(hash)) {
                counter++;
                existsHashes.add(hash);
                // if (debug) System.err.println("+");
            } else {
                // if (debug) System.err.println("-");
            }
            for (int j = 1; j + window <= string.length(); j++) {
//                hash += m;
                // if (debug) System.err.println("-" + string.charAt(j - 1));
                System.err.print("1. " + hash + "-");
                hash -= (charToLong(string.charAt(j - 1)) * powers[window - 1]) % m;
                // if (debug) System.err.println(+ charToLong(string.charAt(j - 1))
//                            + "*" + powers[window - 1]
//                            + "=" + hash);
                // if (debug) System.err.print("2. " + hash);
                hash *= a;
                // if (debug) System.err.println("*" + a + "=" + hash);
                // if (debug) System.err.println("+" + string.charAt(j + window - 1));
                // if (debug) System.err.print("3. " + hash + "+"
//                        + charToLong(string.charAt(j + window - 1)) + "=");
                hash += charToLong(string.charAt(j + window - 1));
                // if (debug) System.err.println(hash);
                // if (debug) System.err.print("4. " + hash + "%" + m + "=");
                hash %= m;
                // if (debug) System.err.println(hash);
                // if (debug) System.err.println(string.substring(j, j + window) + ": " + hash);
                if (!existsHashes.contains(hash)) {
                    counter++;
                    existsHashes.add(hash);
                    // if (debug) System.err.println("+");
                } else {
                    // if (debug) System.err.println("-");
                }
            }
//            for (int j = i + 1; j <= string.length(); j++) {
//                // if (debug) System.err.print(i + " - " + j + ": ");
//                for (int k = i; k < j; k++) {
//                    // if (debug) System.err.print(string.charAt(k));
//                }
//                // if (debug) System.err.println();
//                hash = substringHashOfPrehashedString(i, j);
////                // if (debug) System.err.print(" " + hash + " ");
//                if (!existsHashes.contains(hash * powers[i])) {
//                    counter++;
//                    existsHashes.add(hash);
//                    // if (debug) System.err.print("+");
//                } else {
//                    // if (debug) System.err.print("-");
//
//                }
//                // if (debug) System.err.println();
//            }
        }
        System.out.println(counter + 2);
    }

//    public static long substringHashOfPrehashedString(int start, int end) {
//        long firstHash = start > 0 ? hashes[start - 1] : 0;
//        long lastHash = hashes[end - 1];
//        // if (debug) System.err.println(lastHash + "-" + firstHash
//                + "=" + (lastHash - firstHash)
//                + "=" + (lastHash - firstHash + m) % m);
//        return (lastHash - firstHash + m) % m;
//    }

    private static long charToLong(char ch) {
        return ch - 'A' + 1;
    }
}