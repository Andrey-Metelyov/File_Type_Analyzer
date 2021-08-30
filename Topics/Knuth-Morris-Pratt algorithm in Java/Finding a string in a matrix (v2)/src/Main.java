import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] size = scanner.nextLine().split(" ");
        int n = Integer.parseInt(size[0]);
        int m = Integer.parseInt(size[1]);
        String[] patternMatrix = new String[n];
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            patternMatrix[i] = scanner.nextLine();
            res.add(new ArrayList<>());
        }

        size = scanner.nextLine().split(" ");
        n = Integer.parseInt(size[0]);
        m = Integer.parseInt(size[1]);
        String[] textMatrix = new String[n];
        for (int i = 0; i < n; i++) {
            textMatrix[i] = scanner.nextLine();
        }
        int counter = 0;
        for (int i = 0; i < textMatrix.length - patternMatrix.length + 1; i++) {
            for (int j = 0; j < patternMatrix.length; j++) {
                List<Integer> prefix = KMPSearch(textMatrix[j + i], patternMatrix[j]);
                if (prefix.isEmpty()) {
                    break;
                }
                res.get(j).addAll(prefix);
            }
//            System.err.println(res);
            if (res.size() == patternMatrix.length) {
                boolean count = false;
                for (int v : res.get(0)) {
                    count = true;
                    for (int j = 1; j < res.size(); j++) {
                        if (!res.get(j).contains(v)) {
                            count = false;
                            break;
                        }
                    }
                    if (count) {
                        counter++;
                    }
                }
            }
            for (List<Integer> r : res) {
                r.clear();
            }
        }

        System.out.println(counter);
//        for (int r : res) {
//            System.err.println(r + " " + n + " " + m);
//            System.out.println(r / m + " " + r % m);
//        }
    }

    static class Pair {
        String text;
        String pattern;

        public Pair(String text, String pattern) {
            this.text = text;
            this.pattern = pattern;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(text, pair.text) && Objects.equals(pattern, pair.pattern);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text, pattern);
        }
    }

    static Map<Pair, List<Integer>> cacheStringsPatterns = new HashMap<>();

    public static List<Integer> KMPSearch(String text, String pattern) {
//        System.err.print(text + " " + pattern);
        Pair p = new Pair(text, pattern);
        if (cacheStringsPatterns.containsKey(p)) {
            return cacheStringsPatterns.get(p);
        }
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
//        System.err.println(": " + occurrences);
        cacheStringsPatterns.put(p, occurrences);
        return occurrences;
    }

    static Map<String, int[]> cachePatterns = new HashMap<>();

    public static int[] prefixFunction(String str) {
        if (cachePatterns.containsKey(str)) {
            return cachePatterns.get(str);
        }
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
        cachePatterns.put(str, prefixFunc);
        return prefixFunc;
    }
}
