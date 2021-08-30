package analyzer.PatternSearch;

public class PatternSearch {
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
