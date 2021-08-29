import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine();
        String[] size = scanner.nextLine().split(" ");
        int n = Integer.parseInt(size[0]);
        int m = Integer.parseInt(size[1]);
        String[] matrix = new String[n];
        String text = "";
        for (int i = 0; i < n; i++) {
            matrix[i] = scanner.nextLine();
            text += matrix[i];
        }

        List<Integer> res = KMPSearch(text, pattern);
        System.out.println(res.size());
        for (int r : res) {
            System.err.println(r + " " + n + " " + m);
            System.out.println(r / m + " " + r % m);
        }
    }

    public static List<Integer> KMPSearch(String text, String pattern) {
        System.err.println(text + " " + pattern);
        /* 1 */
        int[] prefixFunc = prefixFunction(pattern);
        ArrayList<Integer> occurrences = new ArrayList<Integer>();
        int j = 0;
        /* 2 */
        for (int i = 0; i < text.length(); i++) {
            /* 3 */
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = prefixFunc[j - 1];
            }
            /* 4 */
            if (text.charAt(i) == pattern.charAt(j)) {
                j += 1;
            }
            /* 5 */
            if (j == pattern.length()) {
                occurrences.add(i - j + 1);
                j = prefixFunc[j - 1];
            }
        }
        /* 6 */
        return occurrences;
    }

    public static int[] prefixFunction(String str) {
        /* 1 */
        int[] prefixFunc = new int[str.length()];

        /* 2 */
        for (int i = 1; i < str.length(); i++) {
            /* 3 */
            int j = prefixFunc[i - 1];

            while (j > 0 && str.charAt(i) != str.charAt(j)) {
                j = prefixFunc[j - 1];
            }

            /* 4 */
            if (str.charAt(i) == str.charAt(j)) {
                j += 1;
            }

            /* 5 */
            prefixFunc[i] = j;
        }

        /* 6 */
        return prefixFunc;
    }
}