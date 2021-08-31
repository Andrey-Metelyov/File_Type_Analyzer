import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String string = scanner.nextLine();
        int n = Integer.parseInt(scanner.nextLine());
        long hash = 0;
        long pow = 1;
        int a = 53;
        int m = 1_000_000_000 + 9;
        List<Long> hashes = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
//            System.err.println("hash=" + hash);
//            System.err.println("pow=" + pow);
//            System.err.println("add=" + charToLong(string.charAt(i)) * pow);
            hash += charToLong(string.charAt(i)) * pow;
            hash %= m;
            pow = (pow * a % m + m) % m;
//            System.out.print(hash + " ");
            hashes.add(hash);
        }
        System.out.println(hashes.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" ")));
        for (int i = 0; i < n; i++) {
            int first = scanner.nextInt();
            int last = scanner.nextInt();
            long firstHash = first > 0 ? hashes.get(first - 1) : 0;
            long lastHash = last > 0 ? hashes.get(last - 1) : 0;
            System.out.print((lastHash - firstHash + m) % m + " ");

        }
        System.out.println();
    }

    private static long charToLong(char ch) {
        return (long) (ch - 'A' + 1);
    }

//    private static long hash(String string, int a, int m) {
//        int pow = 1;
//        long hash = 0;
//        for (int i = 0; i < string.length(); i++) {
//            hash += charToLong(string.charAt(i)) * pow;
//            hash %= m;
//            pow = pow * a % m;
//        }
//        return hash;
//    }
}