import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        long hash = 0;
        long pow = 1;
        final int a = 53;
        final int m = 1_000_000_000 + 9;
        long[] hashes = new long[string.length()];
        for (int i = 0; i < string.length(); i++) {
            hash += charToLong(string.charAt(i)) * pow;
            hash %= m;
//            pow = (pow * a % m + m) % m;
            pow = (pow * a) % m;
            hashes[i] = hash;
        }
        System.err.println(Arrays.toString(hashes));

        Set<Long> existsHashes = new HashSet<>();
        int counter = 0;
        pow = 1;
        for (int i = 0; i < string.length(); i++) {
            pow = (pow * a) % m;
            for (int j = i + 1; j <= string.length(); j++) {
                hash = substringHashOfPrehashedString(i, j, hashes, a, m);
                System.err.print(i + " - " + j + ": ");
                for (int k = i; k < j; k++) {
                    System.err.print(string.charAt(k));
                }
                System.err.print(" " + hash + " (" + hash(string.substring(i, j), a, m) + ") ");
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
        long firstHash = start > 0 ? hashes[start - 1] : 0;
        long lastHash = hashes[end - 1];
        long pow = 1;
        for (int i = 0; i < start; i++) {
            pow = pow * a % m;
        }
        System.err.println(lastHash + "-" + firstHash + "=" + (lastHash - firstHash) + "/" + pow);
        return ((lastHash - firstHash + m) % m) / pow;
    }

    private static long charToLong(char ch) {
        return ch - 'A' + 1;
    }

    private static long hash(String string, int a, int m) {
        long pow = 1;
        long hash = 0;
        for (int i = 0; i < string.length(); i++) {
            hash += (charToLong(string.charAt(i)) * pow) % m;
            pow = (pow * a) % m;
        }
        return hash;
    }
}