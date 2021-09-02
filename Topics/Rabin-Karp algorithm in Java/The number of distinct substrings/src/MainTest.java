import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class MainTest {
    final int a = 53;
    final int m = 1_000_000_000 + 9;

//    @Test
//    public void test_getHashes() {
//        String string = "abacabad";
//
//        long[] hashes = Main.getHashes(string, a, m);
//
//        assertArrayEquals(new long[] {
//                33L,
//                1835L,
//                94532L,
//                5305227L,
//                265691100L,
//                484337736L,
//                908248414L,
//                508901936L,
//        }, hashes);
//    }

    @Test
    public void test_substringHashOfPrehashedString() {
        long[] hashes = {
                33L,
                1835L,
                94532L,
                5305227L,
                265691100L,
                484337736L,
                908248414L,
                508901936L,
        };
        assertEquals(Main.substringHashOfPrehashedString(0, 1), 33);
        assertEquals(Main.substringHashOfPrehashedString(0, 2), 1835);
        assertEquals(Main.substringHashOfPrehashedString(1, 2), 1802);
        assertEquals(Main.substringHashOfPrehashedString(5, 6), 218646636);
        assertEquals(Main.substringHashOfPrehashedString(1, 6), 484337703);
    }
}