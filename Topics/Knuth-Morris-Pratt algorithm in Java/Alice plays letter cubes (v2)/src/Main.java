import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        String str = "";
        int count = 1;
        for (int i = 0; i < text.length(); i++) {
            str = text.charAt(i) + str;
            int[] prefix = prefixFunction(str);
            int max = max(prefix);
            int c = i + 1 - max;
//            System.err.println(c);
            count += c;
//            System.err.println(Arrays.toString(prefix));
        }
        System.out.println(count);
    }

    private static int max(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static int[] prefixFunction(String str) {
//        System.err.println(str);
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