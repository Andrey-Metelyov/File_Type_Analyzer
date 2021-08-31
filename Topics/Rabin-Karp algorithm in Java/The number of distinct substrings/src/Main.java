import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        long hash = 0;
        long pow = 1;
        int a = 53;
        int m = 1_000_000_000 + 9;
        long[] hashes = new long[string.length()];
        for (int i = 0; i < string.length(); i++) {
            hash += charToLong(string.charAt(i)) * pow;
            hash %= m;
            pow = (pow * a % m + m) % m;
            hashes[i] = hash;
        }
        System.err.println(Arrays.toString(hashes));

        Set<Long> existsHashes = new HashSet<>();
        int counter = 0;
        for (int i = 0; i < string.length(); i++) {
            for (int j = i + 1; j <= string.length(); j++) {
                System.err.print(i + " - " + j + ": ");
                for (int k = i; k < j; k++) {
                    System.err.print(string.charAt(k));
                }
                hash = substringHashOfPrehashedString(i, j, hashes, a, m);
                System.err.print(" " + hash + " ");
                if (!existsHashes.contains(hash)) {
                    counter++;
                    existsHashes.add(hash);
                    System.err.print("+");
                } else {
                    System.err.print("-");

                }
                System.err.println();
            }
        }
        System.out.println(counter + 1);
    }

    private static long substringHashOfPrehashedString(int start, int end, long[] hashes, int a, int m) {
        long firstHash = start > 0 ? hashes[start] : 0;
        long lastHash = hashes[end - 1];
        long pow = 1;
        for (int i = 1; i < start; i++) {
            pow *= a;
        }
        System.err.println(lastHash + "-" + firstHash);
        return (lastHash - firstHash + m) % m / pow;
    }

    private static long charToLong(char ch) {
        return ch - 'A' + 1;
    }

//    private static long hash(String string, int a, int m) {
//        long pow = 1;
//        long hash = 0;
//        for (int i = 0; i < string.length(); i++) {
//            hash += charToLong(string.charAt(i)) * pow;
//            hash %= m;
//            pow = pow * a % m;
//        }
//        return hash;
//    }
}