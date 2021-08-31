class Util {
    public static int indexOf(String src, String tgt) {
        return indexOf(src, tgt, 0);
    }
    
    public static int indexOf(String src, String tgt, int index) {
            // your code here
        if (src.startsWith(tgt)) {
            return index;
        }
        if (src.length() < tgt.length()) {
            return -1;
        }
        
        return indexOf(src.substring(1), tgt, index + 1);
    }
}
